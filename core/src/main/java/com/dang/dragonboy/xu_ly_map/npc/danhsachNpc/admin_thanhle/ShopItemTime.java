package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle;

public class ShopItemTime {
    public final Long startAt;
    public final Long endAt;

    public ShopItemTime(Long startAt, Long endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public long thoiGianConLai(long now) {
        if (endAt == null) return -1;
        return Math.max(0, endAt - now);
    }
}
