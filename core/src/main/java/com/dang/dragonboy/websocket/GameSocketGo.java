package com.dang.dragonboy.websocket;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.he_thong.AppConfig;
import com.dang.dragonboy.nhan_vat.NhanVat;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * Connection tới Go game-service (binary WebSocket).
 *
 * KIẾN TRÚC:
 * - GameSocket (Socket.IO) là MASTER → connect đầu tiên, handle login/logout/trade/skill/...
 * - GameSocketGo (binary WS) là SLAVE → CHỈ handle player-move (gửi) + playerSync (nhận).
 *
 * LIFECYCLE:
 * - Mất Socket.IO → kill luôn Go (do GameSocket gọi disconnectGo()).
 * - Mất Go → tự retry, KHÔNG động Socket.IO.
 *
 * BINARY PROTOCOL:
 * - BigEndian (network byte order).
 * - Mỗi packet: [msgType byte][payload bytes].
 * - String: [uint16 length BE][UTF-8 bytes].
 * - Float32: 4 bytes IEEE 754 BE.
 *
 * Handshake (FIRST PACKET sau khi WS connect):
 *   [0x00][PROTOCOL_VERSION uint16][userId int32][token string][gameSessionId string]
 *
 * Server reply:
 *   Ack:  [0x80]
 *   Nack: [0x81][reason uint8]
 */
public class GameSocketGo {

    // ---------- Protocol constants (PHẢI khớp với Go server) ----------
    private static final int PROTOCOL_VERSION = 1;

    // Client → Server
    private static final byte MSG_HANDSHAKE   = (byte) 0x00;
    private static final byte MSG_PLAYER_MOVE = (byte) 0x01;

    // Server → Client
    private static final byte MSG_HANDSHAKE_ACK  = (byte) 0x80;
    private static final byte MSG_HANDSHAKE_NACK = (byte) 0x81;
    private static final byte MSG_PLAYER_SYNC    = (byte) 0x82;
    private static final byte MSG_PLAYER_SYNC_BATCH = (byte) 0x83;

    // Trangthai enum (khớp với Go enums/trangthai.go và Java TrangThai.java)
    private static final byte TRANGTHAI_DUNG_YEN   = 0;
    private static final byte TRANGTHAI_DI_CHUYEN  = 1;
    private static final byte TRANGTHAI_NHAY       = 2;
    private static final byte TRANGTHAI_ROI        = 3;
    private static final byte TRANGTHAI_BAY_NGANG  = 4;
    private static final byte TRANGTHAI_THU        = 5;
    private static final byte TRANGTHAI_GONG       = 6;

    // ---------- State ----------
    private static WebSocketClient client;
    private static volatile boolean handshakeOk = false;
    private static volatile boolean isReconnecting = false;
    private static volatile boolean isManualDisconnect = false;
    private static int retryCount = 0;
    private static final int MAX_RETRY = 5;

    // CONFIG: đổi thành URL Go server thật khi deploy
    private static final String GO_WS_URL = AppConfig.get("ws.go.url");
    // Production: "wss://ws-go.dangpham.id.vn/ws-game"

    // ---------- Clock sync ----------
    public static volatile long clockOffset = 0;
    public static long lastRtt = 40;
    // Độ dao động của RTT giữa các lần ping liên tiếp (jitter)
    // Ví dụ cùng lastRtt ~40ms nhưng:
    //   Mạng ổn: 37, 42, 41, 40, 39   → rttJitter thấp ~2ms
    //   Mạng yếu: 10, 70, 80, 65, 20  → rttJitter cao ~50ms
    //
    // lastRtt dùng để tính clockOffset (đồng bộ đồng hồ client-server).
    // rttJitter dùng để tính render delay (jitter buffer).
    // Hai mục đích khác nhau:
    //   - clockOffset trả lời: "server đang ở thời điểm nào?"
    //   - renderDelay trả lời: "cần trễ bao lâu để buffer luôn có đủ 2 snapshot?"
    // clockOffset không cover được render delay vì dù đồng hồ đồng bộ hoàn hảo,
    // packet vẫn đến không đều (lúc 10ms lúc 70ms) → nếu render delay quá nhỏ
    // thì buffer rỗng → giật, dù clockOffset hoàn toàn chính xác.
    public static long rttJitter = 10;
    private static volatile long pingSentAt = 0;
    private static long lastClockSyncAt = 0;
    public static volatile boolean clockReady = false;
    private static volatile boolean waitingPong = false;

    private static void startClockSync() {
        pingSentAt = System.currentTimeMillis();
        waitingPong = true;
        client.sendPing();
    }

    /**
     * Connect tới Go server. Gọi SAU KHI Socket.IO connect thành công và đã có gameSessionId.
     */
    public static void connect(String token) {
        if (isConnected()) return;
        if (isReconnecting) return;

        try {
            isManualDisconnect = false;
            handshakeOk = false;

            URI uri = new URI(GO_WS_URL);
            client = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("GO WS CONNECTED, sending handshake...");
                    sendHandshake(token);
                }

                @Override
                public void onMessage(String message) {
                    // Go chỉ gửi binary, ignore text.
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    handleBinaryMessage(bytes);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("GO WS CLOSED: code=" + code + " reason=" + reason);
                    handshakeOk = false;
                    if (!isManualDisconnect) {
                        scheduleReconnect();
                    }
                }

                @Override
                public void onWebsocketPong(org.java_websocket.WebSocket conn, org.java_websocket.framing.Framedata f) {
                    if (!waitingPong) return;
                    long rtt = System.currentTimeMillis() - pingSentAt;
                    if (rtt > 0 && rtt < 5000) {
                        if (lastRtt == 40) lastRtt = rtt;        // lần đầu
                        else lastRtt = (lastRtt * 7 + rtt * 3) / 10;  // EMA smooth
                    }

                    //  rttDiff = độ dao động giữa 2 lần ping liên tiếp:
                    //  lần 1: 40ms
                    //  lần 2: 42ms → rttDiff = |42 - 40| = 2ms
                    //  lần 3: 80ms → rttDiff = |80 - 42| = 38ms  ← spike
                    //  lần 4: 45ms → rttDiff = |45 - 80| = 35ms
                    //  rttJitter = EMA của rttDiff, tức là mức dao động trung bình của ping theo thời gian.
                    long rttDiff = Math.abs(rtt - lastRtt);
                    rttJitter = (rttJitter * 7 + rttDiff * 3) / 10; // EMA giống lastRtt
                    waitingPong = false;
                    PlayerState.updateRenderDelay();
                }
                @Override
                public void onError(Exception ex) {
                    System.out.println("GO WS ERROR: " + ex.getMessage());
                }
            };

            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
            scheduleReconnect();
        }
    }

    /**
     * Disconnect chủ động — KHÔNG retry. Gọi khi Socket.IO disconnect (master mất).
     */
    public static void disconnect() {
        isManualDisconnect = true;
        handshakeOk = false;
        retryCount = 0;
        if (client != null) {
            try {
                client.close();
            } catch (Exception ignored) {}
            client = null;
        }
    }

    public static boolean isConnected() {
        return client != null && client.isOpen() && handshakeOk;
    }

    private static void scheduleReconnect() {
        if (isReconnecting || isManualDisconnect) return;
        if (retryCount >= MAX_RETRY) return;

        isReconnecting = true;
        retryCount++;

        long delay = 5_000L; // 5s, giống Socket.IO retry

        new Thread(() -> {
            try { Thread.sleep(delay); } catch (InterruptedException ignored) {}
            isReconnecting = false;
            if (isManualDisconnect) return;

            // Go server cần gameSessionId. Nếu Socket.IO master vẫn connected → session vẫn valid.
            // Nếu master cũng dead → đừng reconnect Go (sẽ fail auth).
            if (!GameSocket.isConnected()) {
                System.out.println("Socket.IO master not connected, skipping Go reconnect");
                return;
            }
            connect(State_Management.getToken());
        }, "GameSocketGo-Reconnect").start();
    }

    // ====================================================================
    // SEND - Client → Server
    // ====================================================================

    /**
     * Handshake: first packet ngay sau khi WS open.
     * Format: [0x00][version uint16][userId int32][token string][sessionId string]
     */
    private static void sendHandshake(String token) {
        try {
            int userId = State_Management.getUserResponse().id.intValue();
            String sessionId = State_Management.gameSessionId;

            // Tính size buffer.
            byte[] tokenBytes = token.getBytes(StandardCharsets.UTF_8);
            byte[] sessionBytes = sessionId.getBytes(StandardCharsets.UTF_8);
            int size = 1 + 2 + 4 + 2 + tokenBytes.length + 2 + sessionBytes.length;

            ByteBuffer buf = ByteBuffer.allocate(size).order(ByteOrder.BIG_ENDIAN);
            buf.put(MSG_HANDSHAKE);
            buf.putShort((short) PROTOCOL_VERSION);
            buf.putInt(userId);
            buf.putShort((short) tokenBytes.length);
            buf.put(tokenBytes);
            buf.putShort((short) sessionBytes.length);
            buf.put(sessionBytes);

            buf.flip();
            client.send(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gửi player-move tới Go.
     * Format khớp với Go internal/shared/messages/player_move.go
     *
     * Flow: client A move → Go → broadcast tới các client cùng map → các client nhận MsgPlayerSync.
     */
    public static void guiPlayerMove(NhanVat nhanVat) {
        if (!isConnected()) return;

        try {
            String mapId = State_Management.getCurrentMap(); // CHÚ Ý: cần có hàm này
            byte trangthai = trangthaiToByte(nhanVat.getTrangThai().name());
            byte dir = (byte) (nhanVat.getFlipX() ? -1 : 1);

            byte[] mapBytes = stringBytes(mapId);
            byte[] dauBytes = stringBytes(nhanVat.dauPath);
            byte[] thanBytes = stringBytes(nhanVat.thanPath);
            byte[] chanBytes = stringBytes(nhanVat.chanPath);
            byte[] tenVanBayBytes = stringBytes(nhanVat.tenVanBay);
            byte[] avatarBytes = stringBytes(nhanVat.doiavatar());

            // Tính tổng size để allocate đúng (tránh resize)
            int size = 1                          // msgType
                + 2 + mapBytes.length             // mapID
                + 4 + 4                           // x, y
                + 1 + 1                           // trangthai, dir
                + 2 + dauBytes.length
                + 2 + thanBytes.length
                + 2 + chanBytes.length
                + 4                               // timeChoHienBay
                + 4 * 6                           // lechDau/Than/Chan X/Y
                + 1                               // dangMangVanBay
                + 2 + tenVanBayBytes.length
                + 4 + 4                           // rong, cao
                + 2 + avatarBytes.length;

            ByteBuffer buf = ByteBuffer.allocate(size).order(ByteOrder.BIG_ENDIAN);
            buf.put(MSG_PLAYER_MOVE);

            writeString(buf, mapBytes);
            buf.putFloat((float) nhanVat.getX());
            buf.putFloat((float) nhanVat.getY());
            buf.put(trangthai);
            buf.put(dir);
            writeString(buf, dauBytes);
            writeString(buf, thanBytes);
            writeString(buf, chanBytes);
            buf.putFloat((float) nhanVat.timeChoHienBay);
            buf.putFloat((float) nhanVat.lechDauX);
            buf.putFloat((float) nhanVat.lechDauY);
            buf.putFloat((float) nhanVat.lechThanX);
            buf.putFloat((float) nhanVat.lechThanY);
            buf.putFloat((float) nhanVat.lechChanX);
            buf.putFloat((float) nhanVat.lechChanY);
            buf.put((byte) (nhanVat.dangMangVanBay ? 1 : 0));
            writeString(buf, tenVanBayBytes);
            buf.putFloat((float) nhanVat.rong);
            buf.putFloat((float) nhanVat.cao);
            writeString(buf, avatarBytes);

            buf.flip();
            client.send(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ====================================================================
    // RECEIVE - Server → Client
    // ====================================================================

    private static void handleBinaryMessage(ByteBuffer bytes) {
        bytes.order(ByteOrder.BIG_ENDIAN);

        if (bytes.remaining() < 1) return;
        byte msgType = bytes.get();

        switch (msgType) {
            case MSG_HANDSHAKE_ACK:
                System.out.println("GO handshake OK");
                handshakeOk = true;
                retryCount = 0;
                startClockSync();
                try {
                    NhanVat nv = State_Management.getNhanVat();
                    if (nv != null && State_Management.getVeHUD() != null) {
                        guiPlayerMove(nv);
                    }
                } catch (Exception ignored) {}
                break;

            case MSG_HANDSHAKE_NACK:
                int reason = bytes.remaining() > 0 ? (bytes.get() & 0xFF) : 0;
                System.out.println("GO handshake REJECTED, reason=" + reason);
                handshakeOk = false;
                // Reason = 3 (session) → master Socket.IO chắc chắn đã invalid → đừng retry.
                // Reason khác → có thể retry.
                if (reason == 3 /* NackReasonSession */) {
                    isManualDisconnect = true;
                }
                break;

//            case MSG_PLAYER_SYNC:
//                handlePlayerSync(bytes);
//                break;

            case MSG_PLAYER_SYNC_BATCH:
                handlePlayerSyncBatch(bytes);
                break;

            default:
                System.out.println("GO unknown msgType: 0x" + String.format("%02x", msgType));
        }
    }

    /**
     * Decode PlayerSync và gọi WorldState.onPlayerSync.
     *
     * Format khớp với Go messages/player_move.go encode:
     *   [int32 userId][float32 x][float32 y][uint8 trangthai][int8 dir]
     *   [string dau][string than][string chan]
     *   [float32 timeChoHienBay]
     *   [float32 lechDauX/Y][float32 lechThanX/Y][float32 lechChanX/Y]
     *   [string tenVanBay]
     *   [float32 rong][float32 cao]
     *   [string avatar]
     *
     * Convert sang JSONObject để compat với WorldState.onPlayerSync hiện tại
     * (đang nhận args[0] = JSONObject từ Socket.IO). KHÔNG phải sửa WorldState.
     */
//    private static void handlePlayerSync(ByteBuffer buf) {
//        try {
//            int userId = buf.getInt();
//            float x = buf.getFloat();
//            float y = buf.getFloat();
//            byte trangthai = buf.get();
//            byte dir = buf.get();
//            String dau = readString(buf);
//            String than = readString(buf);
//            String chan = readString(buf);
//            float timeChoHienBay = buf.getFloat();
//            float lechDauX = buf.getFloat();
//            float lechDauY = buf.getFloat();
//            float lechThanX = buf.getFloat();
//            float lechThanY = buf.getFloat();
//            float lechChanX = buf.getFloat();
//            float lechChanY = buf.getFloat();
//            boolean dangMangVanBay = buf.get() != 0;
//            String tenVanBay = readString(buf);
//            float rong = buf.getFloat();
//            float cao = buf.getFloat();
//            String avatar = readString(buf);
//            long serverTime = buf.getLong();
//
////            // Build JSONObject để gọi cùng handler với Socket.IO.
////            org.json.JSONObject data = new org.json.JSONObject();
////            data.put("userId", userId);
////            data.put("x", x);
////            data.put("y", y);
////            data.put("trangthai", byteToTrangthai(trangthai));
////            data.put("dir", (int) dir);
////            data.put("dau", dau);
////            data.put("than", than);
////            data.put("chan", chan);
////            data.put("timeChoHienBay", timeChoHienBay);
////            data.put("lechDauX", lechDauX);
////            data.put("lechDauY", lechDauY);
////            data.put("lechThanX", lechThanX);
////            data.put("lechThanY", lechThanY);
////            data.put("lechChanX", lechChanX);
////            data.put("lechChanY", lechChanY);
////            data.put("dangMangVanBay", dangMangVanBay);
////            data.put("tenVanBay", tenVanBay);
////            data.put("rong", rong);
////            data.put("cao", cao);
////            data.put("avatar", avatar);
////
////            // Gọi cùng callback với Socket.IO playerSync — KHÔNG phải sửa WorldState.
////            // KHÔNG postRunnable vì WorldState.onPlayerSync hiện tại comment "Race condition không
////            // đáng kể với game → không cần postRunnable" — giữ nguyên behavior.
////            WorldState.onPlayerSync(new Object[]{ data });
//
//            WorldState.onPlayerSyncBinary(
//                userId, x, y, byteToTrangthai(trangthai), dir,
//                dau, than, chan, timeChoHienBay,
//                lechDauX, lechDauY, lechThanX, lechThanY, lechChanX, lechChanY,
//                dangMangVanBay, tenVanBay, rong, cao, avatar, serverTime
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private static void handlePlayerSyncBatch(ByteBuffer buf) {
        try {
            int count = buf.getShort() & 0xFFFF; // uint16
            for (int i = 0; i < count; i++) {
                int userId = buf.getInt();
                float x = buf.getFloat();
                float y = buf.getFloat();
                byte trangthai = buf.get();
                byte dir = buf.get();
                String dau = readString(buf);
                String than = readString(buf);
                String chan = readString(buf);
                float timeChoHienBay = buf.getFloat();
                float lechDauX = buf.getFloat();
                float lechDauY = buf.getFloat();
                float lechThanX = buf.getFloat();
                float lechThanY = buf.getFloat();
                float lechChanX = buf.getFloat();
                float lechChanY = buf.getFloat();
                boolean dangMangVanBay = buf.get() != 0;
                String tenVanBay = readString(buf);
                float rong = buf.getFloat();
                float cao = buf.getFloat();
                String avatar = readString(buf);
                long serverTime = buf.getLong();

                WorldState.onPlayerSyncBinary(
                    userId, x, y, byteToTrangthai(trangthai), dir,
                    dau, than, chan, timeChoHienBay,
                    lechDauX, lechDauY, lechThanX, lechThanY, lechChanX, lechChanY,
                    dangMangVanBay, tenVanBay, rong, cao, avatar, serverTime
                );

                // Chỉ calibrate 1 lần cho cả batch
                if (i == 0) {
                    long now = System.currentTimeMillis();
                    long newOffset = serverTime + lastRtt / 2 - now;

                    if (!clockReady) {
                        clockOffset = newOffset;

                        lastClockSyncAt = now;
                        clockReady = true;
                        if (client != null && client.isOpen() && !waitingPong) {
                            startClockSync();
                        }
                    } else if (now - lastClockSyncAt > 5_000) {
                        long delta = Math.abs(newOffset - clockOffset);
                        if (delta > 200) {
                            // Hard reset - giống Unity Netcode hardResetThresholdSec=0.2
                            clockOffset = newOffset;
                        } else if (delta > 100) {
                            clockOffset = (long)(clockOffset * 0.2 + newOffset * 0.8); // nhanh hơn
                        } else if (delta > 50) {
                            clockOffset = (long)(clockOffset * 0.4 + newOffset * 0.6); // nhanh hơn
                        } else {
                            clockOffset = (long)(clockOffset * 0.6 + newOffset * 0.4); // ổn định
                        }
                        lastClockSyncAt = now;
                        if (client != null && client.isOpen() && !waitingPong) {
                            startClockSync();
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hiển thị ms như game moba/fps
    public static long getPing() {
        return lastRtt;
    }

    // ====================================================================
    // Binary helpers
    // ====================================================================

    private static byte[] stringBytes(String s) {
        if (s == null) s = "";
        return s.getBytes(StandardCharsets.UTF_8);
    }

    private static void writeString(ByteBuffer buf, byte[] strBytes) {
        buf.putShort((short) strBytes.length);
        buf.put(strBytes);
    }

    private static String readString(ByteBuffer buf) {
        int len = buf.getShort() & 0xFFFF; // unsigned uint16
        byte[] bytes = new byte[len];
        buf.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    // ====================================================================
    // Trangthai enum mapping (khớp Go enums/trangthai.go)
    // ====================================================================

    private static byte trangthaiToByte(String name) {
        switch (name) {
            case "DUNG_YEN":   return TRANGTHAI_DUNG_YEN;
            case "DI_CHUYEN":  return TRANGTHAI_DI_CHUYEN;
            case "NHAY":       return TRANGTHAI_NHAY;
            case "ROI":        return TRANGTHAI_ROI;
            case "BAY_NGANG":  return TRANGTHAI_BAY_NGANG;
            case "THU":        return TRANGTHAI_THU;
            case "GONG":       return TRANGTHAI_GONG;
            default:           return TRANGTHAI_DUNG_YEN;
        }
    }

    private static String byteToTrangthai(byte b) {
        switch (b) {
            case TRANGTHAI_DUNG_YEN:   return "DUNG_YEN";
            case TRANGTHAI_DI_CHUYEN:  return "DI_CHUYEN";
            case TRANGTHAI_NHAY:       return "NHAY";
            case TRANGTHAI_ROI:        return "ROI";
            case TRANGTHAI_BAY_NGANG:  return "BAY_NGANG";
            case TRANGTHAI_THU:        return "THU";
            case TRANGTHAI_GONG:       return "GONG";
            default:                   return "DUNG_YEN";
        }
    }
}
