package com.dang.dragonboy.websocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.Npc;

public class KhungChung {
    public static void renderKhungTrai(Texture avatar, VeHUD veHUD, SpriteBatch batch, String moTaTren, String ten, String moTaGiua, int loai, String[] chucNang) {
        float daoDong = (float) Math.sin(veHUD.getDuLieuNguoiChoi().nhanVat.thoiGianTichLuy) * 1.3f;
        batch.draw(veHUD.popupNhanVat, 0, 0, 350, 610);
        batch.draw(avatar,0,505 + daoDong,avatar.getWidth()*0.52f,avatar.getHeight()*0.52f);

        // nutX
        float nutXW = veHUD.nutX.getWidth() * 0.5f;
        float nutXH = veHUD.nutX.getHeight() * 0.55f;
        batch.draw(veHUD.nutX, 350 - nutXW - 6, 610 - nutXH - 2, nutXW, nutXH - 5);

        // ===== Vẽ vàng, ngọc =====
        batch.draw(veHUD.vang, 10, 8, 20, 20);
        batch.draw(veHUD.ngoc, 275, 7, 20, 20);

        // → Định dạng rút gọn vàng
        veHUD.layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(veHUD.getDuLieuNguoiChoi().getVang()));
        veHUD.fontvangngoc.draw(batch, veHUD.layout, 10 + 20 + 10, 22);

        // → Định dạng rút gọn ngọc
        veHUD.layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(veHUD.getDuLieuNguoiChoi().getNgoc()));
        veHUD.fontvangngoc.draw(batch, veHUD.layout, 275 + 20 + 10, 22);

        if (loai == 1) {
            // Loại 1: Trung tâm là mô tả ( text ) , dưới là các nút chữ nhật ( chức năng ), trên là tên + mô tả sơ

            veHUD.fontMotaChucNangNpc.setColor(1.0f, 0.956f, 0.863f, 1f);
            veHUD.layout.setText(veHUD.fontMotaChucNangNpc, moTaTren);
            veHUD.fontMotaChucNangNpc.draw(batch, veHUD.layout, 150, 550);

            veHUD.fontMotaChucNangNpc.setColor(1.0f, 0.956f, 0.863f, 1f);
            veHUD.layout.setText(veHUD.fontMotaChucNangNpc, ten);
            veHUD.fontMotaChucNangNpc.draw(batch, veHUD.layout, 150, 590);

            veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            veHUD.layout.setText(veHUD.fontTenSkill, moTaGiua);
            veHUD.fontTenSkill.draw(batch, veHUD.layout, (350 - veHUD.layout.width) / 2f, 444 + 35);

            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachO = 49;

            batch.flush();
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
            Gdx.gl.glScissor(0, (int) viewY, 350, (int) viewHeight);
            float totalHeight = chucNang.length * KhoangCachO;
            veHUD.maxScrollPhai = Math.max(0, totalHeight - viewHeight);
            float startY = viewY + viewHeight - KhoangCachO + veHUD.scrollYPhai;
            for (int i = 0; i < chucNang.length; i++) {
                float y = startY - i * KhoangCachO;
                Texture tex = (veHUD.playerDuocChon.chucNangPlayer.ordinal() - 1 == i) ? veHUD.o_chi_so_co_ban_click : veHUD.o_chi_so_co_ban;
                batch.draw(tex, 3, y, 344, 50);
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                veHUD.layout.setText(veHUD.fontTenSkill, chucNang[i]);
                veHUD.fontTenSkill.draw(batch, veHUD.layout, 3 + (344 - veHUD.layout.width) / 2f, y + (48 - veHUD.layout.height) / 2f + veHUD.layout.height);
            }
            batch.flush();
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        }
    }
}
