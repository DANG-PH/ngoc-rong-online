package com.dang.dragonboy.he_thong;

import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.hien_thi.VeHUD;

public class ThongTinChuyenMap {
    public final NhanVat nhanVat;
    public final String mapTruoc; // ID của map vừa thoát
    public final VeHUD hud;

    public ThongTinChuyenMap(NhanVat nhanVat, String mapTruoc , VeHUD hud) {
        this.nhanVat = nhanVat;
        this.mapTruoc = mapTruoc;
        this.hud = hud;
    }
}
