package com.dang.dragonboy.xu_ly_map;

import java.util.ArrayList;
import java.util.List;

public abstract class MapCoBan {
    protected List<HitboxDat> danhSachDat = new ArrayList<>();

    public List<HitboxDat> getDanhSachDat() {
        return danhSachDat;
    }

    // Map nào cũng phải có hàm này để load dữ liệu
    public abstract void taiDuLieuMap();
}
