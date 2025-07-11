package com.dang.dragonboy.xu_ly_map;

public class MapNhaBroly extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // Thêm nền đất chính
        danhSachDat.add(new HitboxDat(0, 175, 1420, 0));
    }
    @Override
    public float getChieuRongMap() {
        return 1420f;
    }
    @Override
    public float getChieuCaoMap() {
        return 760f;
    }
}
