package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class QuanLyCamera {
    private float camYBanDau = -1f;
    private float camXBanDau = -1f;
    public OrthographicCamera camera;
    public Viewport viewport;

    public OrthographicCamera uiCamera;
    public Viewport uiViewport;
    private boolean dangKeoCamera = false;
    private float diemBatDauX, diemBatDauY;
    private float doLechX = 0, doLechY = 0;
    private float doNhanCam = 1.5f; // độ nhạy kéo
    public boolean keoCamera = false;
    public boolean vuaKeoCamera = false;
    private float camXKhiBatDau, camYKhiBatDau;
    public QuanLyCamera() {
        // Camera chính
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        camera.update();

        // Camera UI
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // GỐC TỌA ĐỘ Ở GÓC TRÁI DƯỚI
        uiViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCamera);
        uiCamera.update();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        uiViewport.update(width, height);
        uiCamera.setToOrtho(false, width, height); // Đảm bảo gốc tọa độ vẫn ở góc trái dưới khi resize
    }
    public float getOffsetY() {
        if (camYBanDau < 0) {
            camYBanDau = camera.position.y;
        }
        return camera.position.y - camYBanDau;
    }
    public float getOffsetX() {
        if (camXBanDau < 0) {
            camXBanDau = camera.position.x;
        }
        return camera.position.x - camXBanDau;
    }

    public void batDauKeoCamera(int screenX, int screenY) {
        dangKeoCamera = true;
        diemBatDauX = screenX;
        diemBatDauY = screenY;
        camXKhiBatDau = camera.position.x; // lưu vị trí camera hiện tại
        camYKhiBatDau = camera.position.y;
    }

    public void updateMainCamera(float x, float y, float mapWidth, float mapHeight, float vuotKeoX, float vuotKeoY) {
        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;

        float camX, camY;

        if (dangKeoCamera) {
            // Khi kéo -> tính từ vị trí camera lúc bắt đầu kéo
            camX = camXKhiBatDau + doLechX;
            camY = camYKhiBatDau + doLechY;

            // Cho phép vượt giới hạn
            camX = Math.max(halfWidth - vuotKeoX, Math.min(camX, mapWidth - halfWidth));
            camY = Math.max(halfHeight - vuotKeoY, Math.min(camY, mapHeight - halfHeight));

        } else {
            // Bình thường: camera theo nhân vật
            camX = Math.max(halfWidth, Math.min(x, mapWidth - halfWidth));
            camY = Math.max(halfHeight, Math.min(y, mapHeight - halfHeight));
        }

        camera.position.set(camX, camY, 0);
        camera.update();
    }

    public void keoCamera(int screenX, int screenY) {
        if (dangKeoCamera) {
            float dx = Math.abs(screenX - diemBatDauX);
            float dy = Math.abs(screenY - diemBatDauY);

            if (dx > 5 || dy > 5) {
                doLechX = (diemBatDauX - screenX) * doNhanCam;
                doLechY = (screenY - diemBatDauY) * doNhanCam;
                keoCamera = true;
            }
        }
    }

    public void ketThucKeoCamera() {
        if (keoCamera) {
            vuaKeoCamera = true;
        } else {
            vuaKeoCamera = false;
        }
        dangKeoCamera = false;
        keoCamera = false;
        doLechX = 0;
        doLechY = 0;
    }
}
