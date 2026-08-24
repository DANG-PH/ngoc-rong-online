package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class QuanLyCamera {
    private float camYBanDau = 305f;
    private float camXBanDau = 510f;
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

    // Độ phân giải ảo cố định (khớp với cửa sổ PC 1020x610) — toàn bộ toạ độ pixel
    // hard-code trong UI/HUD được viết theo hệ quy chiếu này. Dùng FitViewport với
    // world size cố định để game hiển thị đúng (letterbox 2 bên) trên mọi tỉ lệ màn hình,
    // thay vì để UI vỡ layout khi chạy trên màn hình điện thoại có tỉ lệ khác PC.
    public static final float VIRTUAL_WIDTH = 1020f;
    public static final float VIRTUAL_HEIGHT = 610f;

    public QuanLyCamera() {
        // Camera chính (thế giới/bản đồ). Từng thử ExtendViewport để lấp đầy màn hình rộng, nhưng
        // world lấp đầy trong khi UI (HUD/popup) vẫn neo theo khung 1020x610 cố định tạo ra cảm
        // giác KHÔNG ĐỒNG NHẤT (nền chạm mép, popup/nút thì lơ lửng giữa màn hình) — nhìn rối hơn
        // cả để letterbox đều 2 bên. Quay lại FitViewport đồng nhất cho cả world lẫn UI; phần viền
        // 2 bên xử lý riêng bằng cách vẽ nền phủ kín toàn màn hình thật (xem các file ManHinh*).
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.update();

        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT); // GỐC TỌA ĐỘ Ở GÓC TRÁI DƯỚI
        uiViewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, uiCamera);
        uiCamera.update();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        uiViewport.update(width, height);
    }
    public float getOffsetY() {
        return camera.position.y - camYBanDau;
    }
    public float getOffsetX() {
        return camera.position.x - camXBanDau;
    }

    // Lưu ý: từ ThaoTac, screenX/screenY truyền vào đây thực chất là toạ độ ĐÃ unproject qua
    // uiViewport (hệ ảo 1020x610, gốc dưới-trái), không phải toạ độ pixel màn hình thô nữa.
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
                // Trục Y ở đây đã ở hệ toạ độ ảo gốc dưới-trái (bị đảo chiều so với screenY thô
                // top-down trước kia), nên công thức đảo dấu so với bản gốc để giữ đúng chiều kéo.
                doLechY = (diemBatDauY - screenY) * doNhanCam;
                keoCamera = true;
            }
        }
    }

    // glScissor luôn nhận toạ độ pixel THẬT của framebuffer, không đi qua ma trận chiếu của
    // batch/shapeRenderer như draw() — nên không thể truyền thẳng toạ độ ảo (1020x610) vào đây.
    // Hàm này quy đổi 1 hình chữ nhật trong hệ ảo (gốc dưới-trái, giống toạ độ vẽ UI bình thường)
    // sang đúng vùng pixel thật trên màn hình theo vị trí/scale hiện tại của uiViewport, rồi mới
    // gọi glScissor. Dùng thay cho việc gọi Gdx.gl.glScissor(...) trực tiếp với số ảo.
    public void scissor(float vx, float vy, float vw, float vh) {
        float scale = uiViewport.getScreenWidth() / VIRTUAL_WIDTH;
        int screenX = uiViewport.getScreenX() + Math.round(vx * scale);
        int screenY = uiViewport.getScreenY() + Math.round(vy * scale);
        int screenW = Math.round(vw * scale);
        int screenH = Math.round(vh * scale);
        Gdx.gl.glScissor(screenX, screenY, screenW, screenH);
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
