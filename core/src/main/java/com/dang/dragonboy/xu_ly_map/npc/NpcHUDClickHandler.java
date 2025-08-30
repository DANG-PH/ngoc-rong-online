package com.dang.dragonboy.xu_ly_map.npc;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.*;

public class NpcHUDClickHandler {
    public static void xuLyClick(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat,int x, int y) {
        if (npc.getTen().equals("admin_haidang")) {
            int soNut = npc.getChucNang().length;
//            if (npc.npcHUDrender.ui_npc instanceof admin_haidang) {
//                admin_haidang ui = (admin_haidang) npc.npcHUDrender.ui_npc;
//            }
            admin_haidang ui = (admin_haidang) npc.npcHUDrender.ui_npc;
            for (int i = 0; i < soNut; i++){
                float nutX = (Gdx.graphics.getWidth()-(soNut-1)*120-114)/2f + i * 120;
                if (x>=nutX && x <= nutX+114 && y>=5 && y<=5+114 && !ui.dangBatManHinhGacha){
                    ui.timeClickNut = 0.3f;
                    ui.nutDangChon = i;
                }
            }
            if (ui.dangBatManHinhGacha) {
                if (x>510 && !ui.dangGacha) {
                    ui.gacha(1);
                }
            }
        }
    }
}
