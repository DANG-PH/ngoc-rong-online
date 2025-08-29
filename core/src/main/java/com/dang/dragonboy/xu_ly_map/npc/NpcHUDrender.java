package com.dang.dragonboy.xu_ly_map.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;

public class NpcHUDrender {
    public static void renderHUDnpc(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat, SpriteBatch batch) {
        if (npc.getTen().equals("admin_haidang")) {
            batch.draw(veHUD.nutvuong,500,5);
        }
    }
}
