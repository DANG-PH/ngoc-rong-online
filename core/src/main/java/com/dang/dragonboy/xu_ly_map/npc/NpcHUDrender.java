package com.dang.dragonboy.xu_ly_map.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.*;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_dungle.admin_dungle;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.admin_thanhle;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_haidang.admin_haidang;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.thay_hieu.thay_hieu;

public class NpcHUDrender {
    public renderUInpc ui_npc;
    public DuLieuNguoiChoi duLieuNguoiChoi;
    public VeHUD veHUD;
    public NhanVat nhanVat;
    public Npc npc;

    public NpcHUDrender(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        this.npc = npc;
        this.nhanVat = nhanVat;
        this.veHUD = veHUD;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
    }

    public void renderHUDnpc(SpriteBatch batch) {
        if (ui_npc == null) {
            switch (npc.getTen()) {
                case "admin_haidang":
                    ui_npc = new admin_haidang(npc, veHUD, duLieuNguoiChoi, nhanVat);
                    break;
                case "admin_thanhle":
                    ui_npc = new admin_thanhle(npc, veHUD, duLieuNguoiChoi, nhanVat);
                    break;
                case "admin_dungle":
                    ui_npc = new admin_dungle(npc, veHUD, duLieuNguoiChoi, nhanVat);
                    break;
                case "thay_hieu":
                    ui_npc = new thay_hieu(npc, veHUD, duLieuNguoiChoi, nhanVat);
                    break;
                default:
                    return;
            }
        }
        ui_npc.render(batch);
        ui_npc.capNhat();
    }
}

