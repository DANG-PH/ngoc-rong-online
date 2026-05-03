package com.dang.dragonboy.giao_dien_trong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.hien_thi.HUDRongThan;
import com.dang.dragonboy.hien_thi.QuanLyCamera;
import com.dang.dragonboy.network.DTO.RongThanState;

public class MapToi {
    public static void veMapToi(ShapeRenderer shapeRenderer, QuanLyCamera camManager, String MAP_NAME) {
        RongThanState rongThanState = State_Management.getRongThanState();
        // Điều kiện cũ: Phải đúng map mới thành map tối
        boolean duDieuKienMapToiCu = rongThanState != null && rongThanState.map.equals(MAP_NAME);
        // Điều kiện mới: chỉ cần có map đang tối thì các map khác cũng tối theo (behavior như phim)
        boolean duDieuKienMapToiMoi = rongThanState != null;
        if (!duDieuKienMapToiMoi) return;

        shapeRenderer.setProjectionMatrix(camManager.uiCamera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(0f, 0f, 0f,
            rongThanState.ngocRongUoc.equals("Ngọc Rồng Sao Đen") ? 0.6f : 0.5f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, 1020, 610);
        shapeRenderer.end();
        shapeRenderer.setProjectionMatrix(camManager.camera.combined);
    }

    public static void veRongThan(SpriteBatch batch, String MAP_NAME) {
        RongThanState rongThanState = State_Management.getRongThanState();
        // Vẽ rồng thần khác behavior của map tối
        // Map tối có thể all map nhưng rồng thân chỉ được xuất hiện ở map của người ước
        if (rongThanState != null && rongThanState.map.equals(MAP_NAME)) {
            HUDRongThan.veRongThan(batch, rongThanState.ngocRongUoc, rongThanState.nguoiUocId,(float) rongThanState.x,(float) rongThanState.y);
        }
    }
}
