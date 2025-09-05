package com.dang.dragonboy.xu_ly_map.npc;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_haidang.*;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.*;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_dungle.*;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.thay_hieu.*;

public class NpcHUDClickHandler {
    public static void xuLyClick(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat,float x, float y) {
        switch (npc.getTen()) {
            case "admin_haidang":
                click_admin_haidang(npc, veHUD, duLieuNguoiChoi, nhanVat, x, y);
                break;
            case "admin_thanhle":
                click_admin_thanhle(npc, veHUD, duLieuNguoiChoi, nhanVat, x, y);
                break;
            case "admin_dungle":
                click_admin_dungle(npc, veHUD, duLieuNguoiChoi, nhanVat, x, y);
                break;
            case "thay_hieu":
                click_thay_hieu(npc, veHUD, duLieuNguoiChoi, nhanVat, x, y);
                break;
        }
    }

    public static void click_admin_haidang(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat,float x, float y) {
        int soNut = npc.getChucNang().length;
//            if (npc.npcHUDrender.ui_npc instanceof admin_haidang) {
//                admin_haidang ui = (admin_haidang) npc.npcHUDrender.ui_npc;
//            }
        admin_haidang ui = (admin_haidang) npc.npcHUDrender.ui_npc;
        // Thoát + chức năng chính NPC
        boolean duDieuKienThoat_Npc = ui.trangThai == TrangThaiChucNang_admin_haidang.NONE;
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
                ui.nutChucNangDangChon = i;
            }
        }
        //gacha
        if (ui.trangThai == TrangThaiChucNang_admin_haidang.GACHA) {
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
                    }
                }
            }
        }
        if (ui.trangThai == TrangThaiChucNang_admin_haidang.GACHA_THONG_BAO) {
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 50;
            if (x >= nutX && x <= nutX + 140 && y >= nutY && y <= nutY + 50) {
                ui.timeBamNutOk = 0.3f;
            }
        }
        // quy đổi vé
        if (ui.trangThai == TrangThaiChucNang_admin_haidang.QUY_DOI) {
            if (!ui.dangHienChatDoiVeQuay) {
                for (int i = 0; i < 2; i++) {
                    float nutX = (Gdx.graphics.getWidth() - 680) / 2f + 12.5f;
                    float nutY = 416 - i * 46;
                    if (x >= nutX && x <= nutX + 200 && y >= nutY && y <= nutY + 36) {
                        ui.chucNangQuyDoiVeDangChon = i;
                        if (i == 1) {
                            ui.tongVang = 0;
                            ui.tongVeKhoa = 0;
                        } else {
                            ui.tongNgoc = 0;
                            ui.tongVeVip = 0;
                        }
                    }
                }
                if (x >= (Gdx.graphics.getWidth() - 680) / 2f + 12.5f && x <= (Gdx.graphics.getWidth() - 680) / 2f + 12.5f + 200 && y >= 65 + 30 && y <= 65 + 30 + 36) {
                    ui.timeChoTatChucNang = 0.3f;
                }
                if (x >= (Gdx.graphics.getWidth() - 680) / 2f + 12.5f && x <= (Gdx.graphics.getWidth() - 680) / 2f + 12.5f + 200 && y >= 65 + 30 + 38 && y <= 65 + 30 + 36 + 38) {
                    ui.timeChoHienChatDoiVeQuay = 0.3f;
                }
               for (int i = 0; i < 3; i++) {
                   float nutW = 68 * 0.7f,nutH = 52 * 0.7f;
                   float nutX = 396f - 0.4f + (140 - nutW)/2f + 151*i, nutY = 260 + 3f;
                   if (x >= nutX && x <= nutX + nutW && y >= nutY && y <= nutY + nutH && ui.timeChoThemItemVeQuay <= 0) {
                       ui.nutThemDangChon = i;
                       ui.timeChoThemItemVeQuay = 0.2f;
                   }
               }
               if (x >= 396+(435-140*0.7f) && x <= 396+(435-140*0.7f)+140*0.7f && y >= 100 && y <= 100 + 48 * 0.7f) {
                   if (ui.chucNangQuyDoiVeDangChon == 0) {
                       ui.timeChoMuaItemThuong = 0.3f;
                   } else {
                       ui.timeChoMuaItemVip = 0.3f;
                   }
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
        if (ui.trangThai == TrangThaiChucNang_admin_haidang.HUONG_DAN) {
            float nutX = (Gdx.graphics.getWidth()-114)/2f;
            float nutY = 120 - 115;
            if (checkChuotTrongNut(x,y,nutX,nutY,114,114)) {
                ui.timeBamNutOkHuongDan = 0.3f;
            }
        }
    }

    public static void click_admin_thanhle(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat,float x, float y) {
        admin_thanhle ui = (admin_thanhle) npc.npcHUDrender.ui_npc;
        int soNut = npc.getChucNang().length;
        if (ui.trangThai == TrangThaiChucNang_admin_thanhle.NONE) {
            if (!checkChuotTrongNut(x,y,(Gdx.graphics.getWidth()-soNut*120)/2f,5,120*soNut,114)) {
                veHUD.daClickVaoNpc = false;
                veHUD.vuaThoatNpc = true;
            }
            if (checkChuotTrongNut(x,y,(Gdx.graphics.getWidth()-soNut*120)/2f+120,5,114,114)) {
                ui.nutChucNangDangChon = 1;
                ui.timeClickNut = 0.3f;
            }
        }
    }

    public static void click_admin_dungle(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat,float x, float y) {
        admin_dungle ui = (admin_dungle) npc.npcHUDrender.ui_npc;
        int soNut = npc.getChucNang().length;
        if (ui.trangThai == TrangThaiChucNang_admin_dungle.NONE) {
            if (!checkChuotTrongNut(x,y,(Gdx.graphics.getWidth()-soNut*120)/2f,5,120*soNut,114)) {
                veHUD.daClickVaoNpc = false;
                veHUD.vuaThoatNpc = true;
            }
            for (int i = 0; i <= 3; i++) {
                if (checkChuotTrongNut(x, y, (Gdx.graphics.getWidth() - soNut * 120) / 2f + 120*i, 5, 114, 114)) {
                    ui.nutChucNangDangChon = i;
                    ui.timeClickNut = 0.3f;
                }
            }
        }
        if (ui.trangThai == TrangThaiChucNang_admin_dungle.DOI_GIFT_CODE) {
            float nX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 12;
            if (x >= nX - 81 && x <= nX - 81 + 140 && y >= nutY && y <= nutY + 48) {
                ui.timeChoDoiGiftCode = 0.3f;
                ui.nutDuocChonKhiChat = 0;
            }
            if (x >= nX + 81 && x <= nX + 81 + 140 && y >= nutY && y <= nutY + 48) {
                ui.timeChoDoiGiftCode = 0.3f;
                ui.nutDuocChonKhiChat = 1;
            }
        }
        if (ui.trangThai == TrangThaiChucNang_admin_dungle.NHAN_QUA_THANH_CONG) {
            if (checkChuotTrongNut(x,y,(Gdx.graphics.getWidth()-114)/2f,5,114,114)) {
                ui.timeClickNut = 0.3f;
                ui.nutChucNangDangChon = 0;
            }
        }
    }

    public static void click_thay_hieu(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat,float x, float y) {
        thay_hieu ui = (thay_hieu) npc.npcHUDrender.ui_npc;
        int soNut = 0;
        switch (ui.trangThai){
            case NONE -> soNut = npc.getChucNang().length;
            case CHUC_NANG_CHUYEN_HOA ->  soNut = 3;
            case CHUC_NANG_PHA_LE -> soNut = 3;
        }
        for (int i = 0; i <= soNut; i++) {
            if (checkChuotTrongNut(x, y, (Gdx.graphics.getWidth() - soNut * 120) / 2f + 120*i, 5, 114, 114)) {
                ui.nutChucNangDangChon = i;
                ui.timeClickNut = 0.3f;
            }
        }
        if (!checkChuotTrongNut(x,y,(Gdx.graphics.getWidth()-soNut*120)/2f,5,120*soNut,114)) {
            switch (ui.trangThai) {
                case NONE:
                    veHUD.daClickVaoNpc = false;
                    veHUD.vuaThoatNpc = true;
                    break;
                case CHUC_NANG_PHA_LE:
                    ui.trangThai = TrangThaiChucNang_thay_hieu.NONE;
                    break;
                case CHUC_NANG_CHUYEN_HOA:
                    ui.trangThai = TrangThaiChucNang_thay_hieu.NONE;
                    break;
            }
        }
    }


    public static boolean checkChuotTrongNut(float chuotX, float chuotY,float nutX, float nutY, float nutW ,float nutH) {
        if (chuotX >= nutX && chuotX <= nutX + nutW && chuotY >= nutY && chuotY <= nutY + nutH) {
            return true;
        }
        return false;
    }
}
