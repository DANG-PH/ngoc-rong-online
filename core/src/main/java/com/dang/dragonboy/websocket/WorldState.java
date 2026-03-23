package com.dang.dragonboy.websocket;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.network.ApiItemService;
import com.dang.dragonboy.network.DTO.ItemCanLuu;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
            String tinNhan = obj.optString("tinNhan","");
            State_Management.getDuLieuNguoiChoi().veHUD.setTinNhanPet(tinNhan, 2f);

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
            // Set thông tin giao dịch
            veHUD.dangGiaoDich = false;

            for (Item item : duLieuNguoiChoi.hanhTrangGiaoDich) {
                duLieuNguoiChoi.hanhTrangGiaoDich.remove(item);
                duLieuNguoiChoi.getHanhTrang().add(item);
            }

            duLieuNguoiChoi.hanhTrangGiaoDichPlayer2.clear();

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

            duLieuNguoiChoi.hanhTrangGiaoDichPlayer2.clear();

            int fromUserId = obj.optInt("from",-1);
            List<String> listIdItem = new ArrayList<>();
            JSONArray items = obj.optJSONArray("items");
            if (items != null) {
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    String itemUuid = item.optString("itemUuid");
                    listIdItem.add(itemUuid);
                }
            }
            if (listIdItem.size() > 0) {
                new Thread(() -> {
                    List<ItemCanLuu> itemss = ApiItemService.getItemsByItemUuids(listIdItem);

                    if (itemss != null && !itemss.isEmpty()) {
                        // Post về Main Thread trước khi tạo Item (có Texture)
                        Gdx.app.postRunnable(() -> {
                            Gson gson = new Gson();
                            for (ItemCanLuu item : itemss) {
                                Item itemClient = new Item(
                                    item.maItem, item.ten, LoaiItem.valueOf(item.loai),
                                    item.linkTexture,
                                    item.moTa, item.soLuong,
                                    gson.fromJson(item.chiso, int[].class),
                                    item.hanhTinh, Long.parseLong(item.sucManhYeuCau),
                                    item.setKichHoat, item.soSaoPhaLe,
                                    item.soSaoPhaLeCuongHoa, item.soCap, item.hanSuDung
                                );
                                itemClient.uuid = item.uuid;
                                duLieuNguoiChoi.hanhTrangGiaoDichPlayer2.add(itemClient);
                            }
                            System.out.println("Đã add xong, size: "
                                + duLieuNguoiChoi.hanhTrangGiaoDichPlayer2.size());
                        });
                    } else {
                        System.out.println("itemss null hoặc rỗng");
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
