package com.dang.dragonboy.xu_ly_map;

public class MapNhaGohan extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // Thêm nền đất chính
        danhSachDat.add(new HitboxDat(0, 0, 1420, 175));
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
