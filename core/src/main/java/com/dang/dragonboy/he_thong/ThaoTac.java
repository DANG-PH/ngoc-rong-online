package com.dang.dragonboy.he_thong;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.math.Vector3;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.hien_thi.QuanLyCamera;

public class ThaoTac extends InputAdapter {
    private int yCuKeo = 0;
    private int yCuKeoDeTu = 0;
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
        if (hud.timeChoHopThe == 0 && !hud.dangHienPopup) {
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
                    hud.chonSkill(0);
                    break;
                case Input.Keys.NUM_2:
                    hud.chonSkill(1);
                    break;
                case Input.Keys.NUM_3:
                    hud.chonSkill(2);
                    break;
                case Input.Keys.NUM_4:
                    hud.chonSkill(3);
                    break;
                case Input.Keys.NUM_5:
                    hud.chonSkill(4);
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
        if (button == Input.Buttons.LEFT && !hud.dangHienPopup) {
            camera.batDauKeoCamera(screenX, screenY);
        }
        if (hud.dangHienPopupDeTu) {
            if ( screenX >= 1020 - 360 && screenX <= 1020 && y > 0 && y <= 444) {
                hud.dangChonHanhTrangDeTu = false;
                hud.dangChonHanhTrangSuPhu = true;
            }
            if ( screenX > 0 && screenX <= 350 && y > 0 && y <= 444) {
                hud.dangChonHanhTrangDeTu = true;
                hud.dangChonHanhTrangSuPhu = false;
            }
        }
        // Kéo hành trang sư phụ bên trái (chỉ khi không mở đệ tử)
        if (button == Input.Buttons.LEFT &&
            (hud.getChucNangDangChon() == 1 || hud.getChucNangDangChon() == 2 || hud.getChucNangDangChon() == 4 ) &&
            !hud.dangHienPopupDeTu &&
            !hud.DangHienPopupThongTin1 &&
            !hud.dangHienChonMiniGame &&
            screenX > 0 && screenX <= 350 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrang = true;
            hud.keoHanhTrangDeTu = false;
            yCuKeo = screenY;
        }

        // Kéo hành trang đệ tử bên trái
        if (button == Input.Buttons.LEFT &&
            hud.dangHienPopupDeTu &&
            hud.dangChonHanhTrangDeTu &&
            !hud.DangHienPopupThongTin1 &&
            screenX > 0 && screenX <= 350 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrangDeTu = true;
            yCuKeoDeTu = screenY;
        }

        // Kéo hành trang sư phụ bên phải (chỉ khi mở popup đệ tử, và đang chọn sư phụ)
        if (button == Input.Buttons.LEFT &&
            hud.dangHienPopupDeTu &&
            hud.dangChonHanhTrangSuPhu &&
            !hud.DangHienPopupThongTin1 &&
            screenX >= 1020 - 360 && screenX <= 1020 &&
            y > 0 && y <= 444) {

            hud.keoHanhTrang = true;
            hud.keoHanhTrangDeTu = false;
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
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int y = Gdx.graphics.getHeight() - screenY;
        if (!hud.vuaKeoHanhTrang && !hud.vuaKeoHanhTrangDeTu) {
            hud.xuLyClick(screenX, y);
        }
        if (button == Input.Buttons.LEFT) {
            camera.ketThucKeoCamera();
            hud.keoHanhTrang = false;
            hud.vuaKeoHanhTrang = false;
            hud.vuaKeoHanhTrangDeTu = false;
        }
        if (!hud.dangHienPopup && !hud.laClickTrenHUD(screenX, y) && !camera.vuaKeoCamera && !hud.vuaTatPopup && !hud.dangHienKhungChat && hud.timeChoHopThe == 0 ) {
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
        return true;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        // chỉ xử lý khi đang mở popup và chọn mục hành trang
        if (hud.isDangHienPopup() && (hud.getChucNangDangChon() == 1 || hud.getChucNangDangChon() == 2 || hud.getChucNangDangChon() == 4 || hud.getChucNangDangChon() == 3) && !hud.dangHienThongBao && !hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin && !hud.DangHienPopupThongTin2 && !hud.dangHienChonMiniGame) {
            if (!hud.dangHienPopupDeTu) {
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
        if (hud.dangHienMiniGameThamGia) {
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
        if (hud.dangHienMiniGameThamGiaChanLe) {
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
        return true;
    }
}
