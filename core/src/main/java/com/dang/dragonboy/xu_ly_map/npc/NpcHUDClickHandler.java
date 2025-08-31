package com.dang.dragonboy.xu_ly_map.npc;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.*;

public class NpcHUDClickHandler {
    public static void xuLyClick(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat,float x, float y) {
        if (npc.getTen().equals("admin_haidang")) {
            click_admin_haidang(npc,veHUD,duLieuNguoiChoi,nhanVat,x,y);
        }
    }

    public static void click_admin_haidang(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat,float x, float y) {
        int soNut = npc.getChucNang().length;
//            if (npc.npcHUDrender.ui_npc instanceof admin_haidang) {
//                admin_haidang ui = (admin_haidang) npc.npcHUDrender.ui_npc;
//            }
        admin_haidang ui = (admin_haidang) npc.npcHUDrender.ui_npc;
        // Thoát + chức năng chính NPC
        boolean duDieuKienThoat_Npc = !ui.dangBatManHinhGacha && !ui.dangThongBaoSauGacha && !ui.dangQuyDoiVe;
        if (duDieuKienThoat_Npc) {
            float nutX = (Gdx.graphics.getWidth()-(soNut-1)*120-114)/2f;
            if (x<nutX || x>nutX+360 || y>120) {
                veHUD.daClickVaoNpc = false;
                veHUD.vuaThoatNpc = true;
            }
        }
        for (int i = 0; i < soNut; i++){
            float nutX = (Gdx.graphics.getWidth()-(soNut-1)*120-114)/2f + i * 120;
            if (x>=nutX && x <= nutX+114 && y>=5 && y<=5+114 && duDieuKienThoat_Npc){
                ui.timeClickNut = 0.3f;
                ui.nutDangChon = i;
            }
        }
        //gacha
        if (ui.dangBatManHinhGacha && !ui.dangThongBaoSauGacha) {
            for (int i = 0; i < 3; i++) {
                float nutWidth = 140;
                float nutHeight = 50;
                float nutX = (Gdx.graphics.getWidth() - ui.anhGachaBase.getWidth()) / 2f + 8 + (ui.anhGachaBase.getWidth() - nutWidth) / 2f - 3f;
                float nutY = 55 + 300 - i * 60;
                if (x >= nutX && x <= nutX + 140 && y >= nutY && y <= nutY + nutHeight) {
                    if (i != 2) {
                        ui.nutGachaDangChon = i;
                        ui.timeChoTruocGacha = 0.3f;
                    } else {
                        ui.timeChoTatChucNang = 0.3f;
                        ui.chucNangCanTat = 0;
                    }
                }
            }
        }
        if (ui.dangThongBaoSauGacha) {
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 50;
            if (x >= nutX && x <= nutX + 140 && y >= nutY && y <= nutY + 50) {
                ui.timeBamNutOk = 0.3f;
            }
        }
        // quy đổi vé
        if (ui.dangQuyDoiVe) {
            if (!ui.dangHienChatDoiVeQuay) {
                for (int i = 0; i < 2; i++) {
                    float nutX = (Gdx.graphics.getWidth() - 680) / 2f + 12.5f;
                    float nutY = 416 - i * 46;
                    if (x >= nutX && x <= nutX + 200 && y >= nutY && y <= nutY + 36) {
                        ui.chucNangQuyDoiVeDangChon = i;
                    }
                }
                if (x >= (Gdx.graphics.getWidth() - 680) / 2f + 12.5f && x <= (Gdx.graphics.getWidth() - 680) / 2f + 12.5f + 200 && y >= 65 + 30 && y <= 65 + 30 + 36) {
                    ui.timeChoTatChucNang = 0.3f;
                    ui.chucNangCanTat = 1;
                }
                if (x >= (Gdx.graphics.getWidth() - 680) / 2f + 12.5f && x <= (Gdx.graphics.getWidth() - 680) / 2f + 12.5f + 200 && y >= 65 + 30 + 38 && y <= 65 + 30 + 36 + 38) {
                    ui.timeChoHienChatDoiVeQuay = 0.3f;
                }
            }
            if (ui.dangHienChatDoiVeQuay) {
                float nX = (Gdx.graphics.getWidth() - 140) / 2f;
                float nutY = 12;
                if (x >= nX - 81 && x <= nX - 81 + 140 && y >= nutY && y <= nutY + 48) {
                    ui.timeChoHienChatDoiVeQuay = 0.3f;
                    ui.nutDuocChonKhiChat = 0;
                }
                if (x >= nX + 81 && x <= nX + 81 + 140 && y >= nutY && y <= nutY + 48) {
                    ui.timeChoHienChatDoiVeQuay = 0.3f;
                    ui.nutDuocChonKhiChat = 1;
                }
            }
        }
    }

}
