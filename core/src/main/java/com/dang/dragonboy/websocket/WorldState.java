package com.dang.dragonboy.websocket;

import org.json.JSONArray;
import org.json.JSONObject;
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
            ps.x = (float) obj.optDouble("x", ps.x);
            ps.y = (float) obj.optDouble("y", ps.y);
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

        return ps;
    }
}
