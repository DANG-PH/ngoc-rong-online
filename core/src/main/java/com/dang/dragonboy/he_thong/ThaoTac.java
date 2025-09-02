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
    private int yCuKeo = 0;
    private int yCuKeoDeTu = 0;
    private int yCuKeoRuongDo = 0;
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
        if (button == Input.Buttons.LEFT && !hud.dangHienPopup && !hud.dangHienRuongDo) {
            camera.batDauKeoCamera(screenX, screenY);
        }
        if (hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) {
            if ( screenX >= 1020 - 360 && screenX <= 1020 && y > 0 && y <= 444) {
                hud.dangChonHanhTrangDeTu = false;
                hud.dangChonHanhTrangSuPhu = true;
            }
            if ( screenX > 0 && screenX <= 350 && y > 0 && y <= 444) {
                hud.dangChonHanhTrangDeTu = true;
                hud.dangChonHanhTrangSuPhu = false;
            }
        }
        if (hud.dangHienRuongDo) {
            if ( screenX >= 1020 - 360 && screenX <= 1020 && y > 0 && y <= 444) {
                hud.dangChonHanhTrangRuongDo = false;
                hud.dangChonHanhTrangSuPhu = true;
            }
            if ( screenX > 0 && screenX <= 350 && y > 0 && y <= 444) {
                hud.dangChonHanhTrangRuongDo = true;
                hud.dangChonHanhTrangSuPhu = false;
            }
        }
        // Kéo hành trang sư phụ bên trái (chỉ khi không mở đệ tử)
        if (button == Input.Buttons.LEFT &&
            (hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.HANH_TRANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.KY_NANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.CHUC_NANG) &&
            !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) &&
            !hud.DangHienPopupThongTin1 &&
            !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME) &&
            !hud.dangHienRuongDo &&
            screenX > 0 && screenX <= 350 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrang = true;
            hud.keoHanhTrangDeTu = false;
            hud.keoHanhTrangRuongDo = false;
            yCuKeo = screenY;
        }

        // Kéo hành trang đệ tử bên trái
        if (button == Input.Buttons.LEFT &&
            hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU &&
            hud.dangChonHanhTrangDeTu &&
            !hud.DangHienPopupThongTin1 &&
            screenX > 0 && screenX <= 350 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrangDeTu = true;
            yCuKeoDeTu = screenY;
        }

        // Kéo hành trang ruong do bên trái
        if (button == Input.Buttons.LEFT &&
            hud.dangHienRuongDo &&
            hud.dangChonHanhTrangRuongDo &&
            !hud.DangHienPopupThongTin1 &&
            screenX > 0 && screenX <= 350 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrangRuongDo = true;
            yCuKeoRuongDo = screenY;
        }

        // Kéo hành trang sư phụ bên phải (chỉ khi mở popup đệ tử hoac ruong do, và đang chọn sư phụ)
        if (button == Input.Buttons.LEFT &&
            (hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU || hud.dangHienRuongDo) &&
            hud.dangChonHanhTrangSuPhu &&
            !hud.DangHienPopupThongTin1 &&
            screenX >= 1020 - 360 && screenX <= 1020 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrang = true;
            hud.keoHanhTrangDeTu = false;
            hud.keoHanhTrangRuongDo = false;
            yCuKeo = screenY;
        }

        return true;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        camera.keoCamera(screenX, screenY);
        if (hud.keoHanhTrang) {
            int deltaY = screenY - yCuKeo;
            hud.scrollY -= deltaY*1.5f;
            // giới hạn scrollY
            if (hud.scrollY < 0) hud.scrollY = 0;
            if (hud.scrollY > hud.maxScroll) hud.scrollY = hud.maxScroll;
            yCuKeo = screenY;
            hud.vuaKeoHanhTrang = true;
        }
        if (hud.keoHanhTrangDeTu) {
            int deltaY = screenY - yCuKeoDeTu;
            hud.scrollYDeTu -= deltaY*1.5f;
            // giới hạn scrollYDeTu
            if (hud.scrollYDeTu < 0) hud.scrollYDeTu = 0;
            if (hud.scrollYDeTu > hud.maxScrollDeTu) hud.scrollYDeTu = hud.maxScrollDeTu;
            yCuKeoDeTu = screenY;
            hud.vuaKeoHanhTrangDeTu = true;
        }
        if (hud.keoHanhTrangRuongDo) {
            int deltaY = screenY - yCuKeoRuongDo;
            hud.scrollYRuongDo -= deltaY*1.5f;
            // giới hạn scrollYRuongDo
            if (hud.scrollYRuongDo < 0) hud.scrollYRuongDo = 0;
            if (hud.scrollYRuongDo > hud.maxScrollRuongDo) hud.scrollYRuongDo = hud.maxScrollRuongDo;
            yCuKeoRuongDo = screenY;
            hud.vuaKeoHanhTrangRuongDo = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int y = Gdx.graphics.getHeight() - screenY;
        if (!hud.vuaKeoHanhTrang && !hud.vuaKeoHanhTrangDeTu && !hud.vuaKeoHanhTrangRuongDo) {
            hud.xuLyClick(screenX, y);
        }
        if (hud.daClickVaoNpc) {
            hud.npcHienTai.xuLyClick(screenX, y);
        }
        if (button == Input.Buttons.LEFT) {
            camera.ketThucKeoCamera();
            hud.keoHanhTrang = false;
            hud.vuaKeoHanhTrang = false;
            hud.vuaKeoHanhTrangDeTu = false;
            hud.vuaKeoHanhTrangRuongDo = false;
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
                hud.scroll((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            } else {
                if (hud.dangChonHanhTrangSuPhu) {
                    hud.scroll((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                    return true;
                }
                if (hud.dangChonHanhTrangDeTu) {
                    hud.scrollDeTu((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                    return true;
                }
            }
        }
        if (hud.dangHienRuongDo && !hud.dangHienThongBao && !hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin && !hud.DangHienPopupThongTin3 && !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME)) {
            if (hud.dangChonHanhTrangSuPhu) {
                hud.scroll((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            }
            if (hud.dangChonHanhTrangRuongDo) {
                hud.scrollRuongDo((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
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
