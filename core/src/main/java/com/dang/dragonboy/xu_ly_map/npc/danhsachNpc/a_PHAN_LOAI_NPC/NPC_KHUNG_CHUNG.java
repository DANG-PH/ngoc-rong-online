package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.a_PHAN_LOAI_NPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.*;
import java.util.*;

public class NPC_KHUNG_CHUNG {
    public static void renderKhungNpc(Npc npc, VeHUD veHUD, NhanVat nhanVat, SpriteBatch batch, String text, BitmapFont fontVeKhung) {
        veHUD.layout.setText(
            fontVeKhung,
            text,
            fontVeKhung.getColor(),
            550,
            Align.center,
            true
        );

        float daoDong = (float) Math.sin(nhanVat.thoiGianTichLuy) * 1.3f;
        batch.draw(npc.taiAnh.avtNpc,(Gdx.graphics.getWidth() - 600) / 2f+30,120+veHUD.layout.height+35*2+daoDong,npc.taiAnh.avtNpc.getWidth()*0.5f,npc.taiAnh.avtNpc.getHeight()*0.5f);
        batch.end();
        veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        veHUD.shapeRenderer.setColor(1, 1, 1, 1);
        veHUD.shapeRenderer.rect((Gdx.graphics.getWidth() - 600) / 2f, 120, 600, veHUD.layout.height+35*2);
        veHUD.shapeRenderer.end();
        veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        veHUD.shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++) {
            veHUD.shapeRenderer.rect((Gdx.graphics.getWidth() - 600) / 2f - i, 120 - i, 600 + i * 2, veHUD.layout.height+35*2 + i * 2);
        }
        veHUD.shapeRenderer.end();
        batch.begin();
        fontVeKhung.draw(batch,veHUD.layout,(Gdx.graphics.getWidth() - 600) / 2f+25,120+ veHUD.layout.height+35);
    }
    public static void renderKhungNut(Npc npc, VeHUD veHUD,SpriteBatch batch, String[] textDung, int nutChucNangDangChon, float timeClickNut) {
        int soNut = textDung.length;

        for (int i = 0; i < soNut; i++) {
            float nutX = (Gdx.graphics.getWidth()-(soNut-1)*120-114)/2f + i * 120;
            float nutY = 120 - 115;
            if (nutChucNangDangChon == i) {
                Texture nutVe = timeClickNut > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                batch.draw(nutVe, nutX, nutY, 114, 114);
            } else {
                batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
            }

            veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            veHUD.layout.setText(
                veHUD.font,
                textDung[i],
                veHUD.font.getColor(),
                100,
                Align.center,
                true
            );
            veHUD.font.draw(batch,veHUD.layout,nutX+7f,5+(114+veHUD.layout.height)/2f);
        }
    }
}
