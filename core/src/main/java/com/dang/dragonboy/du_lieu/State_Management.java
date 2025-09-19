package com.dang.dragonboy.du_lieu;

import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;

public class State_Management {
    private static DuLieuNguoiChoi duLieuNguoiChoi;
    private static VeHUD veHUD;
    private static NhanVat nhanVat;

    public static void setDuLieuStateManagement(NhanVat nhanvatt, VeHUD veHUDD,DuLieuNguoiChoi duLieuNguoiChoii) {
        duLieuNguoiChoi = duLieuNguoiChoii;
        veHUD = veHUDD;
        nhanVat = nhanvatt;
    }

    public DuLieuNguoiChoi getDuLieuNguoiChoi() {
        return duLieuNguoiChoi;
    }

    public VeHUD getVeHUD() {
        return veHUD;
    }

    public NhanVat getNhanVat() {
        return nhanVat;
    }
}
