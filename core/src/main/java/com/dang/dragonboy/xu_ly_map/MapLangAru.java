package com.dang.dragonboy.xu_ly_map;

public class MapLangAru extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // Thêm nền đất chính
        danhSachDat.add(new HitboxDat(0, 0, 1420, 10));

        // Thêm bục cao trong map
//        danhSachDat.add(new HitboxDat(400, 300, 120, 10));
//        danhSachDat.add(new HitboxDat(800, 420, 150, 10));
    }
    @Override
    public float getChieuRongMap() {
        return 1020f;
    }
    @Override
    public float getChieuCaoMap() {
        return 610f;
    }
}
