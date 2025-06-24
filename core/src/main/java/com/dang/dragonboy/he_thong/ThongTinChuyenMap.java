package com.dang.dragonboy.he_thong;

import com.dang.dragonboy.nhan_vat.NhanVat;

public class ThongTinChuyenMap {
    public final NhanVat nhanVat;
    public final String mapTruoc; // ID của map vừa thoát

    public ThongTinChuyenMap(NhanVat nhanVat, String mapTruoc) {
        this.nhanVat = nhanVat;
        this.mapTruoc = mapTruoc;
    }
}
