package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;

public class HUDVeGlow {
    private VeHUD veHUD;
    public HUDVeGlow(VeHUD veHUD) {
        this.veHUD = veHUD;
    }
    public void capNhatThoiGian(float delta) {
        if (veHUD.timeGlow > 0) {
            veHUD.timeGlow -= delta;
            if (veHUD.timeGlow <= 0) {
                veHUD.timeGlow = 0;
                if (veHUD.vuaClickTatPopup) {
                    veHUD.tatPopupNhanVat();
                    veHUD.hangTrangDangChon = -1;
                    veHUD.oChiSoDangChon = -1;
                    veHUD.vuaClickTatPopup = false;
                    veHUD.scrollY = 0;
                    veHUD.scrollYDeTu = 0;
                }
                if (veHUD.vuaClickMoPopup) {
                    veHUD.hienPopupNhanVat();
                    veHUD.vuaClickMoPopup =false;
                }
            }
        }
    }
    public void veGlow(ShapeRenderer shapeRenderer, float x, float y, float timeGlow) {
        if (timeGlow <= 0) return;

        Gdx.gl.glEnable(GL20.GL_BLEND); // Cho phép alpha
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        int soVong = 10; // số vòng tròn đồng tâm
        float banKinhGoc = 10; // bán kính nhỏ nhất
        float maxThem = 30;    // mức tăng bán kính cho vòng lớn nhất
        float alphaBase = Math.min(timeGlow / 0.5f, 1f); // alpha giảm theo thời gian

        for (int i = 0; i < soVong; i++) {
            float tiLe = (float) i / soVong;
            float radius = banKinhGoc + tiLe * maxThem;
            float alpha = (1 - tiLe) * alphaBase * 0.6f; // vòng ngoài mờ hơn
            shapeRenderer.setColor(1f, 1f, 0f, alpha); // màu vàng, alpha mờ dần
            shapeRenderer.circle(x, y, radius);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
