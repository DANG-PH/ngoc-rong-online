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
                if (x>=nutX && x <= nutX+114 && y>=5 && y<=5+114 && !ui.dangBatManHinhGacha  && !ui.dangThongBaoSauGacha){
                    ui.timeClickNut = 0.3f;
                    ui.nutDangChon = i;
                }
            }
            if (ui.dangBatManHinhGacha && !ui.dangThongBaoSauGacha) {
                for (int i = 0; i < 3; i++) {
                    float nutWidth = 140;
                    float nutHeight = 50;
                    float nutX = (Gdx.graphics.getWidth() - ui.anhGachaBase.getWidth()) / 2f + 8 + (ui.anhGachaBase.getWidth() - nutWidth) / 2f - 3f;
                    float nutY = 55 + 300 - i * 60;
                    if (x >= nutX && x <= nutX + 140 && y >= nutY && y <= nutY + nutHeight) {
                        ui.nutGachaDangChon = i;
                        ui.timeChoTruocGacha = 0.3f;
                    }
                }
            }
            if (!ui.dangBatManHinhGacha && !ui.dangThongBaoSauGacha) {
                float nutX = (Gdx.graphics.getWidth()-(soNut-1)*120-114)/2f;
                if (x<nutX || x>nutX+360 || y>120) {
                    veHUD.daClickVaoNpc = false;
                    veHUD.vuaThoatNpc = true;
                }
            }
            if (ui.dangThongBaoSauGacha) {
                float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
                float nutY = 50;
                if (x >= nutX && x <= nutX + 140 && y >= nutY && y <= nutY + 50) {
                    ui.timeBamNutOk = 0.3f;
                }
            }
        }
    }
}
