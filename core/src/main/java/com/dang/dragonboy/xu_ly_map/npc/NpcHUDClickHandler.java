package com.dang.dragonboy.xu_ly_map.npc;

import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;

public class NpcHUDClickHandler {
    public static void xuLyClick(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat,int x, int y) {
        if (npc.getTen().equals("admin_haidang")) {
            if (x>510) {
                System.out.println("1");
            }
        }
    }
}
