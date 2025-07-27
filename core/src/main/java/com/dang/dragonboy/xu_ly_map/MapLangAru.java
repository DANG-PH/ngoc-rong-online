package com.dang.dragonboy.xu_ly_map;

public class MapLangAru extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // Thêm nền đất chính
        danhSachDat.add(new HitboxDat(0, 0, 800, 30));
        danhSachDat.add(new HitboxDat(800, 0, 300, 60));
        danhSachDat.add(new HitboxDat(1100, 0, 400, 10));
        danhSachDat.add(new HitboxDat(500, 380, 200, 70));

        // Thêm bục cao trong map
//        danhSachDat.add(new HitboxDat(400, 300, 120, 10));
//        danhSachDat.add(new HitboxDat(800, 420, 150, 10));
    }
    @Override
    public float getChieuRongMap() {
        return 1500f;
    }
    @Override
    public float getChieuCaoMap() {
        return 610f;
    }
}
