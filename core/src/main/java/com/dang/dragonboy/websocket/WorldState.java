package com.dang.dragonboy.websocket;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.network.ApiItemService;
import com.dang.dragonboy.network.ApiService;
import com.dang.dragonboy.network.DTO.ItemCanLuu;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WorldState
 * ----------
 * Lưu toàn bộ trạng thái player trong map hiện tại.
 * Được cập nhật hoàn toàn từ WebSocket.
 *
 * Thread-safe vì WebSocket != render thread.
 */
public class WorldState {

    /**
     * Danh sách player trong world
     * key   : userId
     * value : PlayerState
     */
    private static final ExecutorService tradeExecutor = Executors.newSingleThreadExecutor();
    private static final Gson gson = new Gson();

    public static ConcurrentHashMap<Integer, PlayerState> players =
        new ConcurrentHashMap<>();

    /* =========================================================
     * MAP SNAPSHOT
     * ---------------------------------------------------------
     * Server gửi toàn bộ danh sách player khi:
     * - Vào map
     * - Reload map
     * - Reconnect
     * ========================================================= */
    public static void onMapSnapshot(Object... args) {
        players.clear();
        if (args.length == 0) return;

        try {
            JSONArray arr = toJsonArray(args[0]);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                PlayerState ps = parsePlayer(obj);
                if (ps != null) {
                    players.put(ps.userId, ps);
//                    System.out.println("[MapSnapshot] Thêm player userId=" + ps.userId
//                        + " gameName=" + ps.gameName
//                        + " x=" + ps.x + " y=" + ps.y
//                        + " trangthai=" + ps.trangthai
//                        + " chan=" + ps.chan
//                        + " than=" + ps.than
//                        + " dau=" + ps.dau);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================================================
     * PLAYER SPAWN
     * ---------------------------------------------------------
     * Khi 1 player mới xuất hiện trong map
     * ========================================================= */
    public static void onPlayerSpawn(Object... args) {
        if (args.length == 0) return;

        try {
            JSONObject obj = toJsonObject(args[0]);
            PlayerState ps = parsePlayer(obj);
            if (ps != null) {
                players.put(ps.userId, ps);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================================================
     * PLAYER DESPAWN
     * ---------------------------------------------------------
     * Khi player rời map / disconnect
     * ========================================================= */
    public static void onPlayerDespawn(Object... args) {
        if (args.length == 0) return;

        try {
            JSONObject obj = toJsonObject(args[0]);
            int userId = obj.optInt("userId", -1);
            if (userId != -1) {
                players.remove(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================================================
     * PLAYER SYNC (REALTIME UPDATE)
     * ---------------------------------------------------------
     * Update liên tục vị trí, trạng thái, frame animation
     * ========================================================= */
    public static void onPlayerSync(Object... args) {
        if (args.length == 0) return;

        try {
            JSONObject obj = toJsonObject(args[0]);
            int userId = obj.optInt("userId", -1);
            if (userId == -1) return;

            PlayerState ps = players.get(userId);
            if (ps == null) return;

            // ===== Transform =====
            ps.serverX = (float) obj.optDouble("x", ps.serverX);
            ps.serverY = (float) obj.optDouble("y", ps.serverY);
            ps.dir = obj.optInt("dir", ps.dir);

            // ===== State =====
            ps.trangthai = obj.optString("trangthai", ps.trangthai);

            // ===== Skin =====
            ps.dau = obj.optString("dau", ps.dau);
            ps.than = obj.optString("than", ps.than);
            ps.chan = obj.optString("chan", ps.chan);

            // ===== Animation =====
            ps.frameVanBay = obj.optInt("frameVanBay", ps.frameVanBay);
            ps.timeChoHienBay = (float) obj.optDouble("timeChoHienBay", ps.timeChoHienBay);
            ps.dangMangVanBay = (boolean) obj.optBoolean("dangMangVanBay", ps.dangMangVanBay);
            ps.tenVanBay = obj.optString("tenVanBay", ps.tenVanBay);

            // ===== Offset =====
            ps.lechThanX = (float) obj.optDouble("lechThanX", ps.lechThanX);
            ps.lechThanY = (float) obj.optDouble("lechThanY", ps.lechThanY);
            ps.lechDauX  = (float) obj.optDouble("lechDauX", ps.lechDauX);
            ps.lechDauY  = (float) obj.optDouble("lechDauY", ps.lechDauY);
            ps.lechChanX = (float) obj.optDouble("lechChanX", ps.lechChanX);
            ps.lechChanY = (float) obj.optDouble("lechChanY", ps.lechChanY);

            ps.rong = (float) obj.optDouble("rong", ps.rong);
            ps.cao = (float) obj.optDouble("cao", ps.cao);

            ps.avatar = obj.optString("avatar", ps.avatar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onPlayerChat(Object... args) {
        if (args.length == 0) return;

        try {
            JSONObject obj = toJsonObject(args[0]);
            int userId = obj.optInt("userId", -1);
            if (userId == -1) return;

            PlayerState ps = players.get(userId);
            if (ps == null) return;

            ps.dangHienTinNhan = true;
            ps.timeHienTinNhan = 0f;
            ps.tinNhanHien = obj.optString("message", ps.tinNhanHien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onNotification(Object... args) {
        if (args.length == 0) return;

        try {
            JSONObject obj = toJsonObject(args[0]);

            String type = obj.optString("type", "");

            if ("NAP_TIEN".equals(type)) {
                handleNapTien(obj);
            } else {
                // fallback: chỉ hiển thị message
                String tinNhan = obj.optString("tinNhan", "");
                State_Management.getDuLieuNguoiChoi()
                    .veHUD.setTinNhanPet(tinNhan, 2f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onTradeItem(Object... args) {
        if (args.length == 0) return;
        try {
            JSONObject obj = toJsonObject(args[0]);
            int fromUserId = obj.optInt("fromUserId",-1);
            PlayerState ps = players.get(fromUserId);
            if (ps == null) return;

            VeHUD veHUD = State_Management.getVeHUD();
            veHUD.dangCoYeuCauGiaoDich = true;
            veHUD.timeChapNhanGiaoDich = 20f;
            veHUD.playerGiaoDich = ps;
            veHUD.scrollX_trade = 0f;
            veHUD.tradeTextDone = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onTradeOpen(Object... args) {
        if (args.length == 0) return;
        try {
            JSONObject obj = toJsonObject(args[0]);
            int fromUserId = obj.optInt("with",-1);
            PlayerState ps = players.get(fromUserId);
            if (ps == null) return;

            VeHUD veHUD = State_Management.getVeHUD();
            // Set thông tin giao dịch
            veHUD.scrollYPhai = 0;
            veHUD.oChiSoDangChon = -1;
            veHUD.dangHienKhungChung = false;

            veHUD.dangGiaoDich = true;
            veHUD.scrollYTrai = 0;
            veHUD.scrollYPhai = 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onTradeCancel(Object... args) {
        if (args.length == 0) return;
        try {
            JSONObject obj = toJsonObject(args[0]);

            VeHUD veHUD = State_Management.getVeHUD();
            DuLieuNguoiChoi duLieuNguoiChoi = veHUD.getDuLieuNguoiChoi();

            veHUD.dangGiaoDich = false;
            veHUD.scrollYTrai = 0;
            veHUD.scrollYPhai = 0;

            // Copy ra list mới trước, rồi mới xử lý
            List<Item> itemsToReturn = new ArrayList<>(duLieuNguoiChoi.hanhTrangGiaoDich);
            duLieuNguoiChoi.hanhTrangGiaoDich.clear(); // xóa sạch trước

            for (Item item : itemsToReturn) {
                duLieuNguoiChoi.themItemVaoHanhTrangNoSave(item); // rồi trả về hành trang
            }

            duLieuNguoiChoi.hanhTrangGiaoDichPlayer2.clear();

            KhungGiaoDich.textNutGiaoDich = "Khóa";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onTradeUpdate(Object... args) {
        if (args.length == 0) return;
        try {
            JSONObject obj = toJsonObject(args[0]);

            VeHUD veHUD = State_Management.getVeHUD();
            DuLieuNguoiChoi duLieuNguoiChoi = veHUD.getDuLieuNguoiChoi();

            String action = obj.optString("action", "");
            String itemUuid = obj.optString("itemUuid", "");

            if (action.isEmpty() || itemUuid.isEmpty()) return;

            switch (action) {
                case "add" -> {
                    tradeExecutor.submit(() -> {
                        List<String> listt = new ArrayList<>();
                        listt.add(itemUuid);
                        List<ItemCanLuu> itemData = ApiItemService.getItemsByItemUuids(listt);

                        // Check cả null lẫn empty trước khi get(0)
                        if (itemData == null || itemData.isEmpty()) return;

                        Gdx.app.postRunnable(() -> {
                            boolean exists = duLieuNguoiChoi.hanhTrangGiaoDichPlayer2
                                .stream().anyMatch(i -> i.uuid.equals(itemUuid));
                            if (exists) return;

                            duLieuNguoiChoi.hanhTrangGiaoDichPlayer2.add(buildItem(itemData.get(0)));
                        });
                    });
                }
                case "remove" -> {
                    // Không cần gọi API
                    Gdx.app.postRunnable(() ->
                        duLieuNguoiChoi.hanhTrangGiaoDichPlayer2
                            .removeIf(i -> i.uuid.equals(itemUuid))
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onTradeBothLock(Object... args) {
        try {
            VeHUD veHUD = State_Management.getVeHUD();
            DuLieuNguoiChoi duLieuNguoiChoi = veHUD.getDuLieuNguoiChoi();
            System.out.println("HELLO");

            KhungGiaoDich.textNutGiaoDich = "Check...";
            GameSocket.tradeCheck(veHUD.playerGiaoDich.userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onTradeCheckOk(Object... args) {
        try {
            VeHUD veHUD = State_Management.getVeHUD();
            DuLieuNguoiChoi duLieuNguoiChoi = veHUD.getDuLieuNguoiChoi();

            KhungGiaoDich.textNutGiaoDich = "Gửi";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onTradeSuccess(Object... args) {
        try {
            VeHUD veHUD = State_Management.getVeHUD();
            DuLieuNguoiChoi duLieuNguoiChoi = veHUD.getDuLieuNguoiChoi();

            veHUD.dangGiaoDich = false;
            veHUD.scrollYTrai = 0;
            veHUD.scrollYPhai = 0;

            duLieuNguoiChoi.hanhTrangGiaoDich.clear(); // xóa sạch item đã gửi player2

            for (Item item : duLieuNguoiChoi.hanhTrangGiaoDichPlayer2) {
                duLieuNguoiChoi.themItemVaoHanhTrangNoSave(item);
            }

            duLieuNguoiChoi.hanhTrangGiaoDichPlayer2.clear();

            KhungGiaoDich.textNutGiaoDich = "Khóa";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Item buildItem(ItemCanLuu item) {
        Item itemClient = new Item(
            item.maItem, item.ten, LoaiItem.valueOf(item.loai),
            item.linkTexture, item.moTa, item.soLuong,
            gson.fromJson(item.chiso, int[].class),
            item.hanhTinh, Long.parseLong(item.sucManhYeuCau),
            item.setKichHoat, item.soSaoPhaLe,
            item.soSaoPhaLeCuongHoa, item.soCap, item.hanSuDung
        );
        itemClient.uuid = item.uuid;
        return itemClient;
    }

    public static void onAddItem(Object... args) {
        if (args.length == 0) return;
        try {
            System.out.println("CHECK DONE");
            JSONObject obj = toJsonObject(args[0]);

            VeHUD veHUD = State_Management.getVeHUD();
            DuLieuNguoiChoi duLieuNguoiChoi = veHUD.getDuLieuNguoiChoi();

            int tmpId = obj.optInt("tmpId",-1);
            String uuid = obj.optString("uuid","");

            for (Item item : duLieuNguoiChoi.getHanhTrang()) {
                if (item.tmpId == tmpId) {
                    item.uuid = uuid;
                    item.tmpId = -1;
                    System.out.println("DONE UUID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onUseSkill(Object... args) {
        if (args.length == 0) return;
        try {
            JSONObject obj = toJsonObject(args[0]);
            int userId = obj.optInt("userId",-1);
            String skillId = obj.optString("skillId");

            if (State_Management.getAuth_id() == userId) return;
            PlayerState ps = players.get(userId);
            if (ps == null) return;

            switch (skillId) {
                case "Tái tạo năng lượng" -> {
                    ps.dangTtnl = true;
                }
                case "Biến hình" -> {
                    ps.timeChoBienKhi = 2f;
                }
                case "Huýt sáo" -> {
//                    ps.timeHuytSao = 45f;
                    ps.dangHuytSao = true;
                    ps.chuaSetTimeHuytSao = true;
                }
            }

            System.out.println("Vẽ animation skill");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onCancelSkill(Object... args) {
        if (args.length == 0) return;
        try {
            JSONObject obj = toJsonObject(args[0]);
            int userId = obj.optInt("userId",-1);
            String skillId = obj.optString("skillId");

            if (State_Management.getAuth_id() == userId) return;
            PlayerState ps = players.get(userId);
            if (ps == null) return;

            switch (skillId) {
                case "Tái tạo năng lượng" -> {
                    ps.huyTtnl();
                }
                case "Biến hình" -> {
                    ps.huyBienKhi();
                }
                case "Huýt sáo" -> {
                    ps.huyHuytSao();
                }
            }

            System.out.println("Hủy animation skill");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onSyncSkills(Object... args) {
        if (args.length == 0) return;
        try {
            JSONArray arr = toJsonArray(args[0]);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                int userId    = obj.optInt("userId", -1);
                String skillId = obj.optString("skillId");
                long startedAt = obj.optLong("startedAt");
                long now = System.currentTimeMillis();
                long elapsedMs = now - startedAt;
                float elapsed = elapsedMs / 1000f;

                if (State_Management.getAuth_id() == userId) return;
                PlayerState ps = players.get(userId);
                if (ps == null) return;

                switch (skillId) {
                    case "Tái tạo năng lượng" -> {
                        ps.dangTtnl = true;
                    }
                    case "Biến hình" -> {
                        float totalTime = 2f;
                        float remaining = totalTime - elapsed;
                        if (remaining > 0) {
                            ps.timeChoBienKhi = remaining;
                        }
                    }
                    case "Huýt sáo" -> {
//                        ps.timeHuytSao = 45f;
                        ps.dangHuytSao = true;
                        ps.chuaSetTimeHuytSao = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= HELPER ================= */

    /** Convert dữ liệu WebSocket → JSONArray an toàn */
    private static JSONArray toJsonArray(Object data) {
        try {
            if (data instanceof JSONArray) return (JSONArray) data;
            if (data instanceof String) return new JSONArray((String) data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    /** Convert dữ liệu WebSocket → JSONObject an toàn */
    private static JSONObject toJsonObject(Object data) {
        try {
            if (data instanceof JSONObject) return (JSONObject) data;
            if (data instanceof String) return new JSONObject((String) data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /** Parse JSON → PlayerState (dùng cho snapshot / spawn) */
    private static PlayerState parsePlayer(JSONObject obj) {
        if (obj == null) return null;

        PlayerState ps = new PlayerState();
        ps.userId = obj.optInt("userId", -1);
        if (ps.userId == -1) return null;

        ps.x = (float) obj.optDouble("x", 0);
        ps.y = (float) obj.optDouble("y", 0);
        ps.serverX = ps.x;
        ps.serverY = ps.y;
        ps.dir = obj.optInt("dir", 1);
        ps.trangthai = obj.optString("trangthai", "DUNG_YEN");

        ps.dau = obj.optString("dau", null);
        ps.than = obj.optString("than", null);
        ps.chan = obj.optString("chan", null);

        // ===== Animation =====
        ps.frameVanBay = obj.optInt("frameVanBay", ps.frameVanBay);
        ps.timeChoHienBay = (float) obj.optDouble("timeChoHienBay", ps.timeChoHienBay);
        ps.dangMangVanBay = (boolean) obj.optBoolean("dangMangVanBay", ps.dangMangVanBay);
        ps.tenVanBay = obj.optString("tenVanBay", ps.tenVanBay);

        // ===== Offset =====
        ps.lechThanX = (float) obj.optDouble("lechThanX", ps.lechThanX);
        ps.lechThanY = (float) obj.optDouble("lechThanY", ps.lechThanY);
        ps.lechDauX  = (float) obj.optDouble("lechDauX", ps.lechDauX);
        ps.lechDauY  = (float) obj.optDouble("lechDauY", ps.lechDauY);
        ps.lechChanX = (float) obj.optDouble("lechChanX", ps.lechChanX);
        ps.lechChanY = (float) obj.optDouble("lechChanY", ps.lechChanY);

        ps.rong = (float) obj.optDouble("rong", ps.rong);
        ps.cao = (float) obj.optDouble("cao", ps.cao);

        ps.gameName = (String) obj.optString("gameName", ps.gameName);

        ps.avatar = obj.optString("avatar", ps.avatar);

        return ps;
    }

    private static void handleNapTien(JSONObject obj) {
        JSONObject data = obj.optJSONObject("data");
        if (data == null) return;

        DuLieuNguoiChoi duLieu = State_Management.getDuLieuNguoiChoi();
        String loai = data.optString("loai", "");

        // Có 2 cách
        // Cách 1(đang dùng): lấy data từ socket và thêm trực tiếp
        // => ưu: server giảm tải, client tốc độ cực nhanh
        // => nhược: phải sync lại khi cần để tránh mất data hoặc sai UX/UI
        // Cách 2: lấy data từ backend thay vì websocket
        // => ưu: data có thể đúng hơn ở vài trường hợp
        // => nhược: tốc độ chậm hơn rõ rệt, server phải chịu tải nhiều hơn
        if ("ITEM".equals(loai)) {
            int itemId = data.optInt("itemId", -1);
            int quantity = data.optInt("quantity", 1);

            for (int i = 0; i < quantity; i++) {
                duLieu.danhSachVatPhamWeb.add(itemId);
            }

        } else {
            long soLuong = data.optLong("soLuong", 0);

            if ("VANG".equals(loai)) {
                duLieu.vangNapTuWeb += soLuong;
            } else if ("NGOC".equals(loai)) {
                duLieu.ngocNapTuWeb += soLuong;
            }
        }

        // hiển thị message
        String tinNhan = obj.optString("tinNhan", "");
        duLieu.veHUD.setTinNhanPet(tinNhan, 2f);
    }
}
