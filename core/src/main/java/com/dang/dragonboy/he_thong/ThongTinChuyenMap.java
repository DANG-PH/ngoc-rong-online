package com.dang.dragonboy.he_thong;

import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.hien_thi.QuanLyCamera;
import com.dang.dragonboy.xu_ly_map.MapCoBan;

public class ThongTinChuyenMap {
    public final NhanVat nhanVat;
    public final String mapTruoc; // ID của map vừa thoát
    public final VeHUD hud;
    public final QuanLyCamera camManager;
    public final MapCoBan mapTr, mapSau;
//    public final MapCoBan mapNhaGohan;
//    public final MapCoBan mapLangAru;
//    public final MapCoBan mapDoiHoaCuc;

    public ThongTinChuyenMap(NhanVat nhanVat, String mapTruoc , VeHUD hud,QuanLyCamera camManager, MapCoBan mapTr, MapCoBan mapSau) {
        this.nhanVat = nhanVat;
        this.mapTruoc = mapTruoc;
        this.hud = hud;
        this.camManager = camManager;
        this.mapTr = mapTr;
        this.mapSau = mapSau;
    }
}
