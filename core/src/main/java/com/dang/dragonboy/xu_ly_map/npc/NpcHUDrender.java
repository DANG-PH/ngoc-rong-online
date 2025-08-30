package com.dang.dragonboy.xu_ly_map.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.*;

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
        if (npc.getTen().equals("admin_haidang")) {
            if (ui_npc == null) {
                ui_npc = new admin_haidang(npc, veHUD, duLieuNguoiChoi, nhanVat);
            }
            ui_npc.render(batch);
            ui_npc.capNhat();
        }
    }
}

