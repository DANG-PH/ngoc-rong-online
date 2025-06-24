package com.dang.dragonboy.xu_ly_map;

import java.util.ArrayList;
import java.util.List;

public abstract class MapCoBan {
    protected List<HitboxDat> danhSachDat = new ArrayList<>();

    public List<HitboxDat> LayDanhSachDat() {
        return danhSachDat;
    }

    // Map nào cũng phải có hàm này để load dữ liệu
    public abstract void taiDuLieuMap();

    public float getChieuRongMap() {
        return 1020f;
    }

    public float getChieuCaoMap() {
        return 610f;
    }
}
