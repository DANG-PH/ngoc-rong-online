package com.dang.dragonboy.he_thong;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;

import com.dang.dragonboy.hien_thi.*;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_dungle.TrangThaiChucNang_admin_dungle;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_haidang.admin_haidang;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_dungle.admin_dungle;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.TrangThaiChucNang_admin_thanhle;

public class ThaoTac extends InputAdapter {
    private int yCuKeoPhai = 0;
    private int yCuKeoTrai = 0;
    private final NhanVat nhanVat;
    private final VeHUD hud;
    private final QuanLyCamera camera;

    public ThaoTac(NhanVat nhanVat, VeHUD hud,  QuanLyCamera camera) {
        this.nhanVat = nhanVat;
        this.hud = hud;
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (hud.timeChoHopThe == 0 && !hud.dangHienDauThan && !hud.daClickVaoNpc && !hud.dangHienPopup && !hud.dangHienRuongDo && !hud.dangHienDieuUocRongThan && hud.timeChoBienKhi == 0) {
            switch (keycode) {
                case Input.Keys.LEFT:
                    if (nhanVat.diChuyenDenMucTieu) {
                        nhanVat.diChuyenDenMucTieu = false;
                        nhanVat.phimTraiDangGiu = false;
                        nhanVat.phimPhaiDangGiu = false;
                        nhanVat.phimNhayDangGiu = false;
                    }
                    nhanVat.diTrai();
                    nhanVat.setFlipTrai();
                    break;
                case Input.Keys.RIGHT:
                    if (nhanVat.diChuyenDenMucTieu) {
                        nhanVat.diChuyenDenMucTieu = false;
                        nhanVat.phimTraiDangGiu = false;
                        nhanVat.phimPhaiDangGiu = false;
                        nhanVat.phimNhayDangGiu = false;
                    }
                    nhanVat.diPhai();
                    nhanVat.setFlipPhai();
                    break;
                case Input.Keys.UP:
                    if (nhanVat.diChuyenDenMucTieu) {
                        nhanVat.diChuyenDenMucTieu = false;
                        nhanVat.phimTraiDangGiu = false;
                        nhanVat.phimPhaiDangGiu = false;
                        nhanVat.phimNhayDangGiu = false;
                    }
                    nhanVat.nhanNhay();
                    break;

                // Bấm phím số 1–5 để chọn skill
                case Input.Keys.NUM_1:
                    if (hud.skillDangChon == 0) {
                        hud.dungSkill(0);
                    } else {
                        hud.chonSkill(0);
                    }
                    break;
                case Input.Keys.NUM_2:
                    if (hud.skillDangChon == 1) {
                        hud.dungSkill(1);
                    } else {
                        hud.chonSkill(1);
                    }
                    break;
                case Input.Keys.NUM_3:
                    if (hud.skillDangChon == 2) {
                        hud.dungSkill(2);
                    } else {
                        hud.chonSkill(2);
                    }
                    break;
                case Input.Keys.NUM_4:
                    if (hud.skillDangChon == 3) {
                        hud.dungSkill(3);
                    } else {
                        hud.chonSkill(3);
                    }
                    break;
                case Input.Keys.NUM_5:
                    if (hud.skillDangChon == 4) {
                        hud.dungSkill(4);
                    } else {
                        hud.chonSkill(4);
                    }
                    break;
            }
        }
        return true;
    }


    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                nhanVat.dungTrai();
                break;
            case Input.Keys.RIGHT:
                nhanVat.dungPhai();
                break;
            case Input.Keys.UP:
                nhanVat.thaNhay();
                break;
        }
        return true;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int y = Gdx.graphics.getHeight() - screenY;
        if (button == Input.Buttons.LEFT && !hud.dangHienPopup && !hud.dangHienRuongDo && !hud.daClickVaoNpc) {
            camera.batDauKeoCamera(screenX, screenY);
        }

        if (hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU ||
            hud.dangHienRuongDo ||
            hud.dangHienPopupNhanVatPhai )
        {
            if (!hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin2 && !hud.DangHienPopupThongTin3) {
                if (screenX >= 1020 - 360 && screenX <= 1020 && y > 0 && y <= 444) {
                    hud.dangChonHanhTrangTrai = false;
                    hud.dangChonHanhTrangPhai = true;
                }
                if (screenX > 0 && screenX <= 350 && y > 0 && y <= 444) {
                    hud.dangChonHanhTrangTrai = true;
                    hud.dangChonHanhTrangPhai = false;
                }
            }
        }
        // Kéo hành trang sư phụ bên trái (chỉ khi không mở đệ tử)
        if (button == Input.Buttons.LEFT &&
            (hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.HANH_TRANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.KY_NANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.CHUC_NANG) &&
            !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) &&
            !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME) &&
            !hud.dangHienRuongDo &&
            !hud.daClickVaoNpc &&
            screenX > 0 && screenX <= 350 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrangPhai = true;
            hud.keoHanhTrangTrai = false;
            yCuKeoPhai = screenY;
        }

        // Kéo hành trang đệ tử bên trái
        if (button == Input.Buttons.LEFT &&
            hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU &&
            hud.dangChonHanhTrangTrai &&
            screenX > 0 && screenX <= 350 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrangTrai = true;
            yCuKeoTrai = screenY;
        }

        // Kéo hành trang ruong do bên trái
        if (button == Input.Buttons.LEFT &&
            hud.dangHienRuongDo &&
            hud.dangChonHanhTrangTrai &&
            screenX > 0 && screenX <= 350 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrangTrai = true;
            yCuKeoTrai = screenY;
        }

        // Kéo hành trang sư phụ bên phải (chỉ khi mở popup đệ tử hoac ruong do, và đang chọn sư phụ)
        if (button == Input.Buttons.LEFT &&
            (hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU || hud.dangHienRuongDo || hud.dangHienPopupNhanVatPhai) &&
            hud.dangChonHanhTrangPhai &&
            !hud.DangHienPopupThongTin1 &&
            screenX >= 1020 - 360 && screenX <= 1020 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrangPhai = true;
            hud.keoHanhTrangTrai = false;
            yCuKeoPhai = screenY;
        }

        //npc keo duoc
        if (button == Input.Buttons.LEFT &&
            hud.daClickVaoNpc &&
            hud.dangHienPopupNhanVatPhai &&
            screenX > 0 && screenX <= 350 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrangTrai = true;
            yCuKeoTrai = screenY;
        }

        return true;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int y = Gdx.graphics.getHeight() - screenY;
        camera.keoCamera(screenX, screenY);
        if (!hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin2 && !hud.DangHienPopupThongTin3) {
            if (hud.keoHanhTrangPhai) {
                int deltaY = screenY - yCuKeoPhai;
                hud.scrollYPhai -= deltaY * 1.5f;
                // giới hạn scrollY
                if (hud.scrollYPhai < 0) hud.scrollYPhai = 0;
                if (hud.scrollYPhai > hud.maxScrollPhai) hud.scrollYPhai = hud.maxScrollPhai;
                yCuKeoPhai = screenY;
                hud.vuaKeoHanhTrangPhai = true;
            }
            if (hud.keoHanhTrangTrai && screenX > 0 && screenX <= 350 && y > 0 && y <= 444) {
                int deltaY = screenY - yCuKeoTrai;
                hud.scrollYTrai -= deltaY * 1.5f;
                // giới hạn scrollYTrai
                if (hud.scrollYTrai < 0) hud.scrollYTrai = 0;
                if (hud.scrollYTrai > hud.maxScrollTrai) hud.scrollYTrai = hud.maxScrollTrai;
                yCuKeoTrai = screenY;
                hud.vuaKeoHanhTrangTrai = true;
            } else {
                hud.keoHanhTrangTrai = false;
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int y = Gdx.graphics.getHeight() - screenY;
        if (!hud.vuaKeoHanhTrangPhai && !hud.vuaKeoHanhTrangTrai) {
            float viewportWidth = camera.camera.viewportWidth;
            float viewportHeight = camera.camera.viewportHeight;

            float camX = camera.camera.position.x;
            float camY = camera.camera.position.y;

            float worldX = camX - viewportWidth * 0.5f + screenX;
            float worldY = camY - viewportHeight * 0.5f + y;

            hud.xuLyClick(screenX, y, worldX, worldY);
        }
//        if (hud.daClickVaoNpc) {
//            hud.npcHienTai.xuLyClick(screenX, y);
//        }
        if (button == Input.Buttons.LEFT) {
            camera.ketThucKeoCamera();
            hud.keoHanhTrangPhai = false;
            hud.vuaKeoHanhTrangPhai = false;
            hud.keoHanhTrangTrai = false;
            hud.vuaKeoHanhTrangTrai = false;
        }
        if (!hud.dangHienPopup && !hud.dangHienRuongDo && !hud.daClickVaoNpc && !hud.dangHienDauThan && hud.timeChoBienKhi == 0 && !laClickTrenHUD(screenX, y) && !camera.vuaKeoCamera &&!hud.vuaThoatNpc && !hud.vuaTatPopup && !hud.vuaTatRuongDo && !hud.dangHienKhungChat && hud.timeChoHopThe == 0 && !hud.dangHienDieuUocRongThan ) {
            float viewportWidth = camera.camera.viewportWidth;
            float viewportHeight = camera.camera.viewportHeight;

            float camX = camera.camera.position.x;
            float camY = camera.camera.position.y;

            float worldX = camX - viewportWidth * 0.5f + screenX;
            float worldY = camY - viewportHeight * 0.5f + y;

            nhanVat.diChuyenDenMucTieu = true;
            nhanVat.setToaDoMucTieu(worldX, worldY);
        }
        hud.vuaTatPopup = false;
        hud.vuaTatRuongDo = false;
        hud.vuaThoatNpc = false;
        return true;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        // chỉ xử lý khi đang mở popup và chọn mục hành trang
        if (hud.dangHienPopup && (hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.HANH_TRANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.KY_NANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.CHUC_NANG) && !hud.dangHienThongBao && !hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin && !hud.DangHienPopupThongTin2 && !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME)) {
            if (!(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU)) {
                hud.scrollPhai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            } else {
                if (hud.dangChonHanhTrangPhai) {
                    hud.scrollPhai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                    return true;
                }
                if (hud.dangChonHanhTrangTrai) {
                    hud.scrollTrai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                    return true;
                }
            }
        }
        if (hud.dangHienRuongDo && !hud.dangHienThongBao && !hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin && !hud.DangHienPopupThongTin3 && !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME)) {
            if (hud.dangChonHanhTrangPhai) {
                hud.scrollPhai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            }
            if (hud.dangChonHanhTrangTrai) {
                hud.scrollTrai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            }
        }
        if (hud.dangHienPopupNhanVatPhai && !hud.dangHienThongBao && !hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin && !hud.DangHienPopupThongTin3 && !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME)) {
            if (hud.dangChonHanhTrangPhai) {
                hud.scrollPhai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            }
            if (hud.dangChonHanhTrangTrai) {
                hud.scrollTrai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        if (hud.dangHienKhungChat) {
            if (character == '\b') {
                if (!hud.tinNhanChat.isEmpty()) {
                    hud.tinNhanChat = hud.tinNhanChat.substring(0, hud.tinNhanChat.length() - 1);
                }
            } else if (Character.toString(character).matches("[a-zA-Z0-9 :]")) {
                if (hud.tinNhanChat.length() < 100) {
                    hud.tinNhanChat += character;
                }
            }
        }
        if (hud.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CSMM) {
            if (character == '\b') {
                if (!hud.soNgocNguoiChoiNhap.isEmpty()) {
                    hud.soNgocNguoiChoiNhap = hud.soNgocNguoiChoiNhap.substring(0, hud.soNgocNguoiChoiNhap.length() - 1);
                }
            } else if (Character.toString(character).matches("[a-zA-Z0-9 /]")) {
                if (hud.soNgocNguoiChoiNhap.length() < 100) {
                    hud.soNgocNguoiChoiNhap += character;
                }
            }
        }
        if (hud.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CHAN_LE) {
            if (character == '\b') {
                if (!hud.soVangNguoiChoiNhapChanLe.isEmpty()) {
                    hud.soVangNguoiChoiNhapChanLe = hud.soVangNguoiChoiNhapChanLe.substring(0, hud.soVangNguoiChoiNhapChanLe.length() - 1);
                }
            } else if (Character.toString(character).matches("[a-zA-Z0-9 /]")) {
                if (hud.soVangNguoiChoiNhapChanLe.length() < 100) {
                    hud.soVangNguoiChoiNhapChanLe += character;
                }
            }
        }
        if (hud.npcHienTai != null) {
            if (hud.npcHienTai.npcHUDrender.ui_npc instanceof admin_haidang) {
                admin_haidang ui = (admin_haidang) hud.npcHienTai.npcHUDrender.ui_npc;
                if (ui.dangHienChatDoiVeQuay) {
                    if (character == '\b') {
                        if (!ui.tinNhanChat.isEmpty()) {
                            ui.tinNhanChat = ui.tinNhanChat.substring(0, ui.tinNhanChat.length() - 1);
                        }
                    } else if (Character.toString(character).matches("[a-zA-Z0-9]")) {
                        if (ui.tinNhanChat.length() < 100) {
                            ui.tinNhanChat += character;
                        }
                    }
                }
            }
            if (hud.npcHienTai.npcHUDrender.ui_npc instanceof admin_dungle) {
                admin_dungle ui = (admin_dungle) hud.npcHienTai.npcHUDrender.ui_npc;
                if (ui.trangThai == TrangThaiChucNang_admin_dungle.DOI_GIFT_CODE) {
                    if (character == '\b') {
                        if (!ui.tinNhanChat.isEmpty()) {
                            ui.tinNhanChat = ui.tinNhanChat.substring(0, ui.tinNhanChat.length() - 1);
                        }
                    } else if (Character.toString(character).matches("[a-zA-Z0-9]")) {
                        if (ui.tinNhanChat.length() < 100) {
                            ui.tinNhanChat += character;
                        }
                    }
                }
            }
        }
        return true;
    }
    public boolean laClickTrenHUD(float x, float y) {
        // === VÙNG Ô SKILL ===
        int oskillW = 50;
        int oskillH = 50;
        float skillBaseX = 30;
        float skillY = 25f;
        for (int i = 0; i < 5; i++) {
            float x_ve = skillBaseX + i * 65f;
            if (x >= x_ve && x <= x_ve + oskillW && y >= skillY && y <= skillY + oskillH) {
                return true;
            }
        }

        // === VÙNG Ô CHAT ===
        int ochatW = 60;
        int ochatH = 60;
        float ochatX = Gdx.graphics.getWidth() - ochatW - 15;
        float ochatY = Gdx.graphics.getHeight() - 10 - ochatH;
        if (x >= ochatX && x <= ochatX + ochatW && y >= ochatY && y <= ochatY + ochatH) {
            return true;
        }

        // === VÙNG Ô ĐẬU THẦN ===
        int odauthanW = 75;
        int odauthanH = 75;
        float odauthanX = Gdx.graphics.getWidth() - odauthanW - 10;
        float odauthanY = 10;
        if (x >= odauthanX && x <= odauthanX + odauthanW && y >= odauthanY && y <= odauthanY + odauthanH) {
            return true;
        }

        // === VÙNG MỞ POPUP ===
        float nutPopupX = 0f;
        float nutPopupY = Gdx.graphics.getHeight() / 4f * 3;
        if (x >= nutPopupX && x <= nutPopupX + 25 && y >= nutPopupY && y <= nutPopupY + 35) {
            return true;
        }

        return false; // không trúng vùng nào
    }
}
