package com.dang.dragonboy.nhan_vat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.websocket.PlayerState;
import com.dang.dragonboy.websocket.WorldState;

import java.util.List;
import java.util.Map;

public class MultiplayerRenderer {

    public static void render(SpriteBatch batch, float thoiGian, VeHUD veHUD) {
        NhanVat nhanVat = veHUD.getDuLieuNguoiChoi().nhanVat;
        for (PlayerState ps : WorldState.players.values()) {

            // bỏ qua bản thân
            if (ps.userId == State_Management.getAuth_id()) continue;

            ps.capNhat(veHUD);
            ps.ve(batch, thoiGian, veHUD);
        }
    }

    public static void checkClick(float x_check, float y_check, VeHUD veHUD) {
        NhanVat nhanVat = veHUD.getDuLieuNguoiChoi().nhanVat;
        for (PlayerState ps : WorldState.players.values()) {

            // bỏ qua bản thân
            if (ps.userId == State_Management.getAuth_id()) continue;

            ps.checkClick(x_check, y_check, nhanVat);
        }
    }
}

