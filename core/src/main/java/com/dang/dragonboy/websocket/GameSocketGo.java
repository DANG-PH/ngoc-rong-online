package com.dang.dragonboy.websocket;

import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.he_thong.AppConfig;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.network.proto.GameProto.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

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
 * PROTOCOL: Protobuf (thay thế custom binary cũ).
 * - Mỗi packet là 1 Envelope serialized.
 * - Envelope.oneof payload xác định loại message (thay cho byte msgType đầu packet cũ).
 * - GameProto.java được generate từ network/proto/game.proto.
 *
 * Khi sửa game.proto, chạy lại từ root LibGDX project:
 *   protoc --java_out=core/src/main/java core/src/main/java/com/dang/dragonboy/network/proto/game.proto
 */
public class GameSocketGo {

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
     *
     * THAY ĐỔI SO VỚI CUSTOM BINARY:
     *   - Cũ: tự build ByteBuffer [0x00][version uint16][userId int32][token string][sessionId string]
     *   - Mới: Envelope.newBuilder().setHandshake(...).build().toByteArray()
     */
    private static void sendHandshake(String token) {
        try {
            int userId = State_Management.getUserResponse().id.intValue();
            String sessionId = State_Management.gameSessionId;

            Envelope env = Envelope.newBuilder()
                .setHandshake(Handshake.newBuilder()
                    .setProtocolVersion(1)
                    .setUserId(userId)
                    .setToken(token)
                    .setGameSessionId(sessionId)
                    .build())
                .build();

            client.send(ByteBuffer.wrap(env.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gửi player-move tới Go.
     *
     * Flow: client A move → Go → broadcast tới các client cùng map → các client nhận PlayerSyncBatch.
     *
     * THAY ĐỔI SO VỚI CUSTOM BINARY:
     *   - Cũ: tự tính size, allocate ByteBuffer, writeString thủ công
     *   - Mới: PlayerMove.newBuilder() → proto lo hết serialization
     *   - chanPath → setChanField() vì "chan" là keyword Go trong proto
     */
    public static void guiPlayerMove(NhanVat nhanVat) {
        if (!isConnected()) return;

        try {
            String mapId = State_Management.getCurrentMap();

            Envelope env = Envelope.newBuilder()
                .setPlayerMove(PlayerMove.newBuilder()
                    .setMapId(mapId)
                    .setX((float) nhanVat.getX())
                    .setY((float) nhanVat.getY())
                    .setTrangthai(trangthaiToInt(nhanVat.getTrangThai().name()))
                    .setDir(nhanVat.getFlipX() ? -1 : 1)
                    .setDau(nhanVat.dauPath != null ? nhanVat.dauPath : "")
                    .setThan(nhanVat.thanPath != null ? nhanVat.thanPath : "")
                    .setChanField(nhanVat.chanPath != null ? nhanVat.chanPath : "") // ← "chan" → chanField trong proto
                    .setTimeChoHienBay((float) nhanVat.timeChoHienBay)
                    .setLechDauX((float) nhanVat.lechDauX)
                    .setLechDauY((float) nhanVat.lechDauY)
                    .setLechThanX((float) nhanVat.lechThanX)
                    .setLechThanY((float) nhanVat.lechThanY)
                    .setLechChanX((float) nhanVat.lechChanX)
                    .setLechChanY((float) nhanVat.lechChanY)
                    .setDangMangVanBay(nhanVat.dangMangVanBay)
                    .setTenVanBay(nhanVat.tenVanBay != null ? nhanVat.tenVanBay : "")
                    .setRong((float) nhanVat.rong)
                    .setCao((float) nhanVat.cao)
                    .setAvatar(nhanVat.doiavatar() != null ? nhanVat.doiavatar() : "")
                    .build())
                .build();

            client.send(ByteBuffer.wrap(env.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ====================================================================
    // RECEIVE - Server → Client
    // ====================================================================

    /**
     * THAY ĐỔI SO VỚI CUSTOM BINARY:
     *   - Cũ: đọc byte đầu làm msgType, switch(msgType)
     *   - Mới: Envelope.parseFrom(data), switch(env.getPayloadCase())
     */
    private static void handleBinaryMessage(ByteBuffer bytes) {
        try {
            byte[] data = new byte[bytes.remaining()];
            bytes.get(data);
            Envelope env = Envelope.parseFrom(data);

            switch (env.getPayloadCase()) {
                case HANDSHAKE_ACK:
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

                case HANDSHAKE_NACK:
                    int reason = env.getHandshakeNack().getReason().getNumber();
                    System.out.println("GO handshake REJECTED, reason=" + reason);
                    handshakeOk = false;
                    // Reason = 3 (session) → master Socket.IO chắc chắn đã invalid → đừng retry.
                    // Reason khác → có thể retry.
                    if (reason == NackReason.NACK_REASON_SESSION_VALUE) {
                        isManualDisconnect = true;
                    }
                    break;

                case PLAYER_SYNC_BATCH:
                    handlePlayerSyncBatch(env.getPlayerSyncBatch());
                    break;

                default:
                    System.out.println("GO unknown payload case: " + env.getPayloadCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * THAY ĐỔI SO VỚI CUSTOM BINARY:
     *   - Cũ: nhận ByteBuffer, đọc thủ công từng field (getInt, getFloat, readString...)
     *   - Mới: nhận PlayerSyncBatch đã parse sẵn, gọi getter trực tiếp
     *   - count không cần đọc thủ công — batch.getPlayersCount() tự có
     *   - chan → getChanField()
     *   - trangthai: int → intToTrangthai() thay vì byteToTrangthai()
     */
    private static void handlePlayerSyncBatch(PlayerSyncBatch batch) {
        try {
            for (int i = 0; i < batch.getPlayersCount(); i++) {
                PlayerSync p = batch.getPlayers(i);

                WorldState.onPlayerSyncBinary(
                    p.getUserId(),
                    p.getX(),
                    p.getY(),
                    intToTrangthai(p.getTrangthai()),
                    (byte) p.getDir(),
                    p.getDau(),
                    p.getThan(),
                    p.getChanField(), // ← getChanField() thay vì "chan"
                    p.getTimeChoHienBay(),
                    p.getLechDauX(), p.getLechDauY(),
                    p.getLechThanX(), p.getLechThanY(),
                    p.getLechChanX(), p.getLechChanY(),
                    p.getDangMangVanBay(),
                    p.getTenVanBay(),
                    p.getRong(),
                    p.getCao(),
                    p.getAvatar(),
                    p.getServerTime()
                );

                // Chỉ calibrate clock 1 lần cho cả batch
                if (i == 0) {
                    long now = System.currentTimeMillis();
                    long newOffset = p.getServerTime() + lastRtt / 2 - now;

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
    // Trangthai enum mapping (khớp Go trangthai.go)
    //
    // THAY ĐỔI SO VỚI CUSTOM BINARY:
    //   - trangthaiToByte(String) → trangthaiToInt(String)  (proto dùng int32, không có uint8)
    //   - byteToTrangthai(byte)   → intToTrangthai(int)
    // ====================================================================

    private static int trangthaiToInt(String name) {
        switch (name) {
            case "DUNG_YEN":   return 0;
            case "DI_CHUYEN":  return 1;
            case "NHAY":       return 2;
            case "ROI":        return 3;
            case "BAY_NGANG":  return 4;
            case "THU":        return 5;
            case "GONG":       return 6;
            default:           return 0;
        }
    }

    private static String intToTrangthai(int v) {
        switch (v) {
            case 0: return "DUNG_YEN";
            case 1: return "DI_CHUYEN";
            case 2: return "NHAY";
            case 3: return "ROI";
            case 4: return "BAY_NGANG";
            case 5: return "THU";
            case 6: return "GONG";
            default: return "DUNG_YEN";
        }
    }
}
