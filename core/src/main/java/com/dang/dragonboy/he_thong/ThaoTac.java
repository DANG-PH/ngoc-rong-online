package com.dang.dragonboy.he_thong;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;

import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.hien_thi.QuanLyCamera;

public class ThaoTac extends InputAdapter {

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
        if (hud.timeChoHopThe == 0) {
            switch (keycode) {
                case Input.Keys.LEFT:
                    nhanVat.diTrai();
                    nhanVat.setFlipTrai();
                    break;
                case Input.Keys.RIGHT:
                    nhanVat.diPhai();
                    nhanVat.setFlipPhai();
                    break;
                case Input.Keys.UP:
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

        if (button == Input.Buttons.LEFT && !hud.dangHienPopup) {
            camera.batDauKeoCamera(screenX, screenY);
        }
        return true;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        camera.keoCamera(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int y = Gdx.graphics.getHeight() - screenY;
        hud.xuLyClick(screenX, y);

        if (button == Input.Buttons.LEFT) {
            camera.ketThucKeoCamera();
        }
        return true;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        // chỉ xử lý khi đang mở popup và chọn mục hành trang
        if (hud.isDangHienPopup() && (hud.getChucNangDangChon() == 1 || hud.getChucNangDangChon() == 2 || hud.getChucNangDangChon() == 4 || hud.getChucNangDangChon() == 3)) {
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
            } else if (Character.toString(character).matches("[a-zA-Z0-9 ]")) {
                if (hud.tinNhanChat.length() < 100) {
                    hud.tinNhanChat += character;
                }
            }
        }
        return true;
    }
}
