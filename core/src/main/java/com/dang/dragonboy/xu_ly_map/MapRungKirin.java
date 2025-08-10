package com.dang.dragonboy.xu_ly_map;

public class MapRungKirin extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // Thêm nền đất chính
        danhSachDat.add(new HitboxDat(0, -38-96, 864, 175+38+96));
    }
    @Override
    public float getChieuRongMap() {
        return 2976f;
    }
    @Override
    public float getChieuCaoMap() {
        return 1000f;
    }
}
