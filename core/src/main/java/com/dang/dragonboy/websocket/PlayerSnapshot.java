package com.dang.dragonboy.websocket;

/**
 * Snapshot của 1 player tại 1 thời điểm.
 * Buffer trong PlayerState lưu list snapshot này để interpolate mượt qua jitter.
 *
 * Chỉ lưu field cần thiết cho rendering position + animation đồng bộ.
 * Các state local-only (dangClickPlayer, timeChoBienKhi, ...) KHÔNG cần lưu vào snapshot.
 */

// Dùng khi dùng cách snapshot lerp
public class PlayerSnapshot {
    public long time; // System.currentTimeMillis() khi nhận packet

    // Position
    public float x, y;

    // Trạng thái + animation (từ server)
    public String trangthai;
    public int dir;
    public String dau, than, chan;
    public float timeChoHienBay;
    public float lechDauX, lechDauY;
    public float lechThanX, lechThanY;
    public float lechChanX, lechChanY;
    public boolean dangMangVanBay;
    public String tenVanBay;
    public float rong, cao;
    public String avatar;
}
