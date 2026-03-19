package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.websocket.GameSocket;

import javax.swing.plaf.nimbus.State;
import java.awt.*;

public class HUDTradeItem {
    public static final Texture nutTron = new Texture("hud/giaodientrong/myTexture2dnut.png");
    public static final Texture nutTronClick = new Texture("hud/giaodientrong/myTexture2dnutF.png");
    public static float timeClickChapNhanGiaoDich = 0f;

    public static void render(SpriteBatch batch, VeHUD veHUD) {
        capNhat();

        if (!veHUD.dangCoYeuCauGiaoDich) return;

        Texture nutTronVe = timeClickChapNhanGiaoDich > 0f ? nutTronClick : nutTron;
        float xNut = 720;
        float yNut = 550;
        float wNut = nutTronVe.getWidth()*1.4f;
        float hNut = nutTronVe.getHeight()*1.4f;
        batch.draw(nutTronVe, xNut, yNut, wNut, hNut);
        veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.layout.setText(veHUD.font, "Có");
        veHUD.font.draw(batch, veHUD.layout, xNut+(wNut-veHUD.layout.width)/2f, yNut+(hNut+veHUD.layout.height)/2f);

        veHUD.layout.setText(veHUD.fontsm,(int)veHUD.timeChapNhanGiaoDich+"");
        veHUD.fontsm.draw(batch,veHUD.layout,xNut+(wNut-veHUD.layout.width)/2f,yNut - 10f);

        batch.end();
        ShapeRenderer shapeRenderer = veHUD.shapeRenderer;
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(xNut + 50, yNut, 150, hNut);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++) {
            shapeRenderer.rect(xNut + 50 - i, yNut - i, 150 + i * 2, hNut + i * 2);
        }
        shapeRenderer.end();
        batch.begin();

        String text = veHUD.playerGiaoDich.gameName + " muốn giao dịch? ";

        float khungX = xNut + 50 + 10f;   // padding trái 10px
        float khungY = yNut;
        float khungWidth = 130f;
        float khungHeight = hNut;

        BitmapFont font = veHUD.fontchat;
        veHUD.layout.setText(font, text);
        float textWidth = veHUD.layout.width;

        // Nếu text ngắn → vẽ bình thường
        if (textWidth <= khungWidth) {
            float textY = khungY + (khungHeight + veHUD.layout.height) / 2f;
            font.setColor(Color.BLACK);
            font.draw(batch, text, khungX, textY);
            return;
        }

        // ===== LOGIC SCROLL MƯỢT 2 GIÂY =====
        float startTime = 20f;
        float runDuration = 2f;
        float endTime = startTime - runDuration;

        float time = veHUD.timeChapNhanGiaoDich;
        float delta = Gdx.graphics.getDeltaTime();

        if (!veHUD.tradeTextDone && time <= startTime && time > endTime) {
            float speed = (textWidth - khungWidth) / runDuration; // px/s
            veHUD.scrollX_trade -= speed * delta;
        }

        if (time <= endTime-1f && !veHUD.tradeTextDone) {
            veHUD.tradeTextDone = true;
            veHUD.scrollX_trade = 0f;
        }

        // ===== CẮT BẰNG glScissor =====
        batch.flush();
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);

        int scissorX = (int) khungX;
        int scissorY = (int) khungY ;
        int scissorW = (int) khungWidth;
        int scissorH = (int) khungHeight;

        Gdx.gl.glScissor(scissorX, scissorY, scissorW, scissorH);

        float textY = khungY + (khungHeight + veHUD.layout.height) / 2f;
        font.setColor(Color.BLACK);
        font.draw(batch, text, khungX + veHUD.scrollX_trade, textY);

        batch.flush();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);

    }

    public static void checkClick(float x, float y, float worldX, float worldY) {
        VeHUD veHUD = State_Management.getVeHUD();
        if (!veHUD.dangCoYeuCauGiaoDich) return;

        float xNut = 720;
        float yNut = 550;
        float wNut = nutTron.getWidth()*1.4f;
        float hNut = nutTron.getHeight()*1.4f;

        if (HUDClickHandler.checkChuotTrongNut(x, y, xNut, yNut, wNut, hNut)) {
            timeClickChapNhanGiaoDich = 0.3f;
        }
    }

    public static void capNhat() {
        VeHUD veHUD = State_Management.getVeHUD();

        if (timeClickChapNhanGiaoDich > 0f) {
            timeClickChapNhanGiaoDich -= Gdx.graphics.getDeltaTime();
            if (timeClickChapNhanGiaoDich <= 0f) {
                timeClickChapNhanGiaoDich = 0f;
                try {
                    GameSocket.tradeAccept(veHUD.playerGiaoDich.userId);
                    veHUD.timeChapNhanGiaoDich = 0;
                    veHUD.dangCoYeuCauGiaoDich = false;
                } catch (Exception e) {

                }
            }
        }
    }
}
