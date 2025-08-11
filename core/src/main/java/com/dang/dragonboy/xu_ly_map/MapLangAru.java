package com.dang.dragonboy.xu_ly_map;

public class MapLangAru extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // Thêm nền đất chính
        danhSachDat.add(new HitboxDat(-70, -38, 2540, 175+38));
    }
    @Override
    public float getChieuRongMap() {
        return 2400f;
    }
    @Override
    public float getChieuCaoMap() {
        return 1000f;
    }
}
