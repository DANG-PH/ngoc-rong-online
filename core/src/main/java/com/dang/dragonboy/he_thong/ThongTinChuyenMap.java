package com.dang.dragonboy.he_thong;

import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.hien_thi.QuanLyCamera;

public class ThongTinChuyenMap {
    public final NhanVat nhanVat;
    public final String mapTruoc; // ID của map vừa thoát
    public final VeHUD hud;
    public final QuanLyCamera camManager;

    public ThongTinChuyenMap(NhanVat nhanVat, String mapTruoc , VeHUD hud,QuanLyCamera camManager) {
        this.nhanVat = nhanVat;
        this.mapTruoc = mapTruoc;
        this.hud = hud;
        this.camManager = camManager;
    }
}
