package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class QuanLyCamera {
    private float camYBanDau = -1f;
    public OrthographicCamera camera;
    public Viewport viewport;

    public OrthographicCamera uiCamera;
    public Viewport uiViewport;

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

    public void updateMainCamera(float x, float y, float mapWidth, float mapHeight) {
        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;

        float camX = Math.max(halfWidth, Math.min(x, mapWidth - halfWidth));
        float camY = Math.max(halfHeight, Math.min(y, mapHeight - halfHeight));

        camera.position.set(camX, camY, 0);
        camera.update();
    }
}
