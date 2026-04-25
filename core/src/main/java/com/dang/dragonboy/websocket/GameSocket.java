package com.dang.dragonboy.websocket;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.network.DTO.ItemCanLuu;
import com.dang.dragonboy.nhan_vat.NhanVat;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import java.util.*;
import com.google.gson.Gson;
import java.net.URL;
import java.net.HttpURLConnection;

public class GameSocket {

    public static Socket socket;
    private static boolean eventsRegistered = false;
    public static boolean isReconnecting = false;
    public static boolean isManualDisconnect = false;
    public static final int MAX_RETRY = 5;
    public static int retryCount = 0;

    public static void connect(String token) {
        if (isConnected()) return;
        if (isReconnecting) return;

        try {
            IO.Options opts = new IO.Options();
            opts.transports = new String[]{"websocket"};
            opts.reconnection = false;

            // Gửi token + gameSessionId qua auth thay vì extraHeaders
            Map<String, String> auth = new HashMap<>();
            auth.put("token", token);
            auth.put("gameSessionId", State_Management.gameSessionId);
            opts.auth = auth;

            socket = IO.socket("https://ws.dangpham.id.vn/ws-game", opts);

            socket.on(Socket.EVENT_CONNECT, args -> {
                System.out.println("WS CONNECTED");
                retryCount = 0;
                isReconnecting = false;
                if (!eventsRegistered) {
                    // Lần đầu connect
                    registerGameEvents();
                    eventsRegistered = true;
                    isManualDisconnect = false;
                } else {
                    // Reconnect (dưới hoặc trên 10s)
                    // Push lại state thật vì A, C, D có thể đang thấy state cũ
                    syncMyState();
                }
            });

            socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                System.out.println("WS CONNECT ERROR: " + args[0]);
                scheduleReconnect();
            });

            socket.on(Socket.EVENT_DISCONNECT, args -> {
                String reason = args.length > 0 ? args[0].toString() : "";
                if (!reason.equals("io server disconnect")) {
                    scheduleReconnect();
                }
            });

            socket.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void scheduleReconnect() {
        if (isReconnecting || isManualDisconnect) return;
        if (retryCount >= MAX_RETRY) return;

        isReconnecting = true;
        retryCount++;

//        long delay = (long) Math.pow(2, retryCount) * 1000L;
        long delay = 5 * 1000L;

        new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {}

            if (isManualDisconnect) return;

            callPlayThenConnect();
        }).start();
    }

    private static void callPlayThenConnect() {
        new Thread(() -> {
            try {
                if (isManualDisconnect) return;

                URL url = new URL("https://api.dangpham.id.vn/game/play");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                int responseCode = conn.getResponseCode();

                if (responseCode == 200 || responseCode == 201) {
                    // Đọc gameSessionId từ response
                    Scanner scanner = new Scanner(conn.getInputStream());
                    StringBuilder responseBody = new StringBuilder();
                    while (scanner.hasNextLine()) responseBody.append(scanner.nextLine());
                    scanner.close();

                    JSONObject json = new JSONObject(responseBody.toString());
                    String gameSessionId = json.getString("gameSessionId");
                    State_Management.gameSessionId = gameSessionId;

                    System.out.println("/play success → gameSessionId: " + gameSessionId);
                    isReconnecting = false;

                    if (socket != null) {
                        socket.off();
                        socket.close();
                    }
                    eventsRegistered = false;
                    connect(State_Management.getToken());

                } else if (responseCode == 401) {
                    System.out.println("Token expired, need re-login");
                    handleTokenExpired();
                } else {
                    System.out.println("/play failed: " + responseCode);
                    isReconnecting = false;
                    scheduleReconnect();
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                isReconnecting = false;
                scheduleReconnect();
            }
        }).start();
    }

    public static void disconnect() {
        eventsRegistered = false;
        isManualDisconnect = true;
        if (socket != null) socket.disconnect();
    }

    private static void handleTokenExpired() {
        isManualDisconnect = true;
        State_Management.setForceLogout(true);
        State_Management.setForceLogoutMessage("Phiên đăng nhập đã hết hạn hoặc bị thu hồi");
    }

    private static void registerGameEvents() {
        // ===== PLAYER STATE (đụng Texture → postRunnable) =====
        socket.on("mapSnapshot", args ->
            Gdx.app.postRunnable(() -> WorldState.onMapSnapshot(args)));

        socket.on("playerSpawn", args ->
            Gdx.app.postRunnable(() -> {
                System.out.println("===== playerSpawn =====");
                System.out.println(args[0]);
                WorldState.onPlayerSpawn(args);
            }));

        socket.on("playerDespawn", args ->
            Gdx.app.postRunnable(() -> {
                System.out.println("===== playerDespawn =====");
                System.out.println(args[0]);
                WorldState.onPlayerDespawn(args);
            }));

        // Race condition không đáng kể với game → không cần postRunnable
        socket.on("playerSync", args -> WorldState.onPlayerSync(args));
        socket.on("playerChat", args -> WorldState.onPlayerChat(args));

        // ===== SKILL (không đụng Texture) =====
        socket.on("useSkill", args -> WorldState.onUseSkill(args));
        socket.on("cancelSkill", args -> WorldState.onCancelSkill(args));
        socket.on("syncSkills", args -> WorldState.onSyncSkills(args));

        // ===== DEO LUNG (đụng Texture → postRunnable) =====
        socket.on("useCosmetic", args ->
            Gdx.app.postRunnable(() -> WorldState.onPlayerUseCosmetic(args)));
        socket.on("cancelCosmetic", args ->
            Gdx.app.postRunnable(() -> WorldState.onPlayerCancelCosmetic(args)));

        // ===== ITEM (không đụng Texture) =====
        socket.on("addItem", args -> WorldState.onAddItem(args));

        // ===== TRADE (không đụng Texture) =====
        socket.on("trade:request", args -> WorldState.onTradeItem(args));
        socket.on("trade:open", args -> WorldState.onTradeOpen(args));
        socket.on("trade:cancelled", args -> WorldState.onTradeCancel(args));
        socket.on("trade:offer:update", args -> WorldState.onTradeUpdate(args));
        socket.on("trade:bothLocked", WorldState::onTradeBothLock);
        socket.on("trade:check:ok", WorldState::onTradeCheckOk);
        socket.on("trade:success", WorldState::onTradeSuccess);

        // ===== NOTIFICATION (không đụng Texture) =====
        socket.on("notification", args -> WorldState.onNotification(args));

        // ===== Gọi Rồng Thần =====
        socket.on("uocRongThanResult", args -> WorldState.onRongThanResult(args));
        socket.on("uocRongThan", args -> WorldState.onRongThan(args));

        // ===== FORCE LOGOUT =====
        socket.on("force_logout", args -> {
            try {
                JSONObject data = (JSONObject) args[0];
                String message = data.getString("message");

                System.out.println("FORCE LOGOUT: " + message);

                State_Management.setForceLogout(true);
                State_Management.setForceLogoutMessage(message);

                retryCount = MAX_RETRY;
                eventsRegistered = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean isConnected() {
        return socket != null && socket.connected();
    }

    public static void guiSetMap(String oldMap, String newMap, NhanVat nhanVat) {
        try {
            JSONObject data = new JSONObject();
            data.put("oldMap", oldMap);
            data.put("map", newMap);
            data.put("x", nhanVat.getX());
            data.put("y", nhanVat.getY());
            data.put("trangthai", nhanVat.getTrangThai().name());
            data.put("dir", nhanVat.getFlipX() ? -1 : 1);
            data.put("dau", nhanVat.dauPath);
            data.put("than", nhanVat.thanPath);
            data.put("chan", nhanVat.chanPath);
            data.put("lechDauX", nhanVat.lechDauX);
            data.put("lechDauY", nhanVat.lechDauY);
            data.put("lechThanX", nhanVat.lechThanX);
            data.put("lechThanY", nhanVat.lechThanY);
            data.put("lechChanX", nhanVat.lechChanX);
            data.put("lechChanY", nhanVat.lechChanY);
            data.put("timeChoHienBay", nhanVat.timeChoHienBay);
            data.put("frameVanBay", nhanVat.frameVanBay);
            data.put("dangMangVanBay", nhanVat.dangMangVanBay);
            data.put("tenVanBay", nhanVat.tenVanBay);
            data.put("rong", nhanVat.rong);
            data.put("cao", nhanVat.cao);
            data.put("avatar", nhanVat.doiavatar());

            socket.emit("setMap", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void guiPlayerMove(NhanVat nhanVat) throws Exception {
        JSONObject data = new JSONObject();
        data.put("x", nhanVat.getX());
        data.put("y", nhanVat.getY());
        data.put("trangthai", nhanVat.getTrangThai().name());
        data.put("dir", nhanVat.getFlipX() ? -1 : 1);
        data.put("dau", nhanVat.dauPath);
        data.put("than", nhanVat.thanPath);
        data.put("chan", nhanVat.chanPath);
        data.put("lechDauX", nhanVat.lechDauX);
        data.put("lechDauY", nhanVat.lechDauY);
        data.put("lechThanX", nhanVat.lechThanX);
        data.put("lechThanY", nhanVat.lechThanY);
        data.put("lechChanX", nhanVat.lechChanX);
        data.put("lechChanY", nhanVat.lechChanY);
        data.put("timeChoHienBay", nhanVat.timeChoHienBay);
        data.put("frameVanBay", nhanVat.frameVanBay);
        data.put("dangMangVanBay", nhanVat.dangMangVanBay);
        data.put("tenVanBay", nhanVat.tenVanBay);
        data.put("rong", nhanVat.rong);
        data.put("cao", nhanVat.cao);
        data.put("avatar", nhanVat.doiavatar());

        socket.emit("player-move", data);
    }

    // Gộp 3 loại vào 1 event, field tương ứng enum CosmeticField
    public static final String FIELD_DEO_LUNG = "deoLungDung";
    public static final String FIELD_HUY_HIEU = "huyHieuDung";
    public static final String FIELD_AURA     = "auraDung";

    public static void guiCosmetic(String field, String maItem) throws Exception {
        JSONObject data = new JSONObject();
        data.put("field", field);
        data.put("value", maItem);
        socket.emit("use-cosmetic", data);
    }

    public static void guiCancelCosmetic(String field) throws Exception {
        JSONObject data = new JSONObject();
        data.put("field", field);
        socket.emit("cancel-cosmetic", data);
    }

    public static void guiChat(String tinNhan) throws Exception {
        JSONObject data = new JSONObject();
        data.put("message", tinNhan);

        socket.emit("player-chat", data);
    }

    public static void guiNotification(String tinNhan) throws Exception {
        JSONObject data = new JSONObject();
        data.put("tinNhan", tinNhan);

        socket.emit("send-notification", data);
    }

    public static void guiUocRongThan(String ngocRongUoc) {
        try {
            JSONObject data = new JSONObject();
            data.put("ngocRongUoc", ngocRongUoc);

            socket.emit("uoc-rong-than", data);
        } catch (Exception e) {
            // log, alert
        }
    }

    public static void guiUocXongRongThan() {
        try {
            socket.emit("uoc-xong");
        } catch (Exception e) {
            // log, alert
        }
    }

    public static void guiReqTradeItem(int targetId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("targetId", targetId);

        socket.emit("trade:request", data);
    }

    public static void tradeAccept(int fromUserId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("fromUserId", fromUserId);

        socket.emit("trade:accept", data);
    }

    public static void tradeCancel(int withUserId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("withUserId", withUserId);

        socket.emit("trade:cancel", data);
    }

    public static void tradeOfferAdd(int withUserId, String itemUuid) throws Exception {
        JSONObject data = new JSONObject();
        data.put("withUserId", withUserId);
        data.put("itemUuid", itemUuid);

        socket.emit("trade:offer:add", data);
    }

    public static void tradeOfferRemove(int withUserId, String itemUuid) throws Exception {
        JSONObject data = new JSONObject();
        data.put("withUserId", withUserId);
        data.put("itemUuid", itemUuid);

        socket.emit("trade:offer:remove", data);
    }

    public static void tradeLock(int withUserId) throws Exception {
        KhungGiaoDich.textNutGiaoDich = "Đợi...";
        JSONObject data = new JSONObject();
        data.put("withUserId", withUserId);

        socket.emit("trade:lock", data);
    }

    public static void tradeCheck(int withUserId) throws Exception {
        DuLieuNguoiChoi duLieuNguoiChoi = State_Management.getDuLieuNguoiChoi();
        JSONObject data = new JSONObject();
        data.put("withUserId", withUserId);
        data.put("oConTrongBanThan", duLieuNguoiChoi.MAXHANHTRANG - duLieuNguoiChoi.getHanhTrang().size());

        socket.emit("trade:check", data);
    }

    public static void tradeConfirm(int withUserId) throws Exception {
        DuLieuNguoiChoi duLieuNguoiChoi = State_Management.getDuLieuNguoiChoi();
        JSONObject data = new JSONObject();
        data.put("withUserId", withUserId);

        socket.emit("trade:confirm", data);
    }

    public static void addItem(int tmpId, Item item, String viTri) throws Exception {
        ItemCanLuu converted = State_Management.getDuLieuNguoiChoi().convertItem(item, viTri);

        Gson gson = new Gson();
        JSONObject itemJson = new JSONObject(gson.toJson(converted)); // ← fix chỗ này

        JSONObject data = new JSONObject();
        data.put("tmpId", tmpId);
        data.put("item", itemJson);

        socket.emit("add-item", data);
    }

    public static void useSkill(String skillId, int timeSkill) throws Exception {
        JSONObject data = new JSONObject();
        data.put("skillId", skillId);
        data.put("timeSkill", timeSkill);

        socket.emit("use-skill", data);
    }

    public static void cancelSkill(String skillId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("skillId", skillId);

        socket.emit("cancel-skill", data);
    }

    private static void syncMyState() {
        try {
            JSONObject data = new JSONObject();

            String deoLung = State_Management.getVeHUD().deoLungDangDung != null
                ? State_Management.getVeHUD().deoLungDangDung.getId() : null;
            data.put(FIELD_DEO_LUNG, deoLung != null ? deoLung : JSONObject.NULL);

            String huyHieu = State_Management.getVeHUD().huyHieuDangDung != null
                ? State_Management.getVeHUD().huyHieuDangDung.getId() : null;
            data.put(FIELD_HUY_HIEU, huyHieu != null ? huyHieu : JSONObject.NULL);

            String aura = State_Management.getVeHUD().auraDangDung != null
                ? State_Management.getVeHUD().auraDangDung.getId() : null;
            data.put(FIELD_AURA, aura != null ? aura : JSONObject.NULL);

            socket.emit("sync-my-state", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
