package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class VeHUD {
    private Texture ochat, ochatclick;
    private Texture thanhhp;
    private Texture odauthan, odauthanclick;
    private Texture oskill, oskillclick;
    private Texture nutpopup;

    private BitmapFont font;
    private GlyphLayout layout;

    private SkillIcon[] skillIcons;

    private float thoiGianClickOChat = 0;
    private float thoiGianClickODauThan = 0;
    private int skillDangChon = -1; // -1: chưa chọn

    private ShapeRenderer shapeRenderer;

    private Texture popupNhanVat;
    private Texture nutX;
    private boolean dangHienPopup = false;

    public VeHUD(BitmapFont font, GlyphLayout layout) {
        this.font = font;
        this.layout = layout;
        shapeRenderer = new ShapeRenderer();
        // Load HUD textures
        ochat = new Texture("hud/giaodientrong/ochat.png");
        ochatclick = new Texture("hud/giaodientrong/ochatclick.png");
        thanhhp = new Texture("hud/giaodientrong/thanhhp1.png");
        odauthan = new Texture("hud/giaodientrong/odauthan.png");
        odauthanclick = new Texture("hud/giaodientrong/odauthanclick.png");
        oskill = new Texture("hud/giaodientrong/oskill.png");
        oskillclick = new Texture("hud/giaodientrong/oskillclick.png");
        nutpopup = new Texture("hud/giaodientrong/nutpopup.png");
        popupNhanVat = new Texture("hud/giaodientrong/popupnhanvat.jpg");
        nutX = new Texture("hud/giaodientrong/nutX.png");
    }

    public void render(SpriteBatch batch) {
        batch.end();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        // KHUNG NÂU BASE
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(78f / 255f, 47f / 255f, 31f / 255f, 1f);
        shapeRenderer.rect(165, screenHeight - 80 - 5 + 50, 130 , 25);
        shapeRenderer.rect(155, screenHeight - 80 - 5 + 50 - 25, 130 , 25);
        shapeRenderer.end();

        // THANH HP (đỏ)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(213f / 255f, 34f / 255f, 0f / 255f, 1f);
        shapeRenderer.setColor(189f / 255f, 29f / 255f, 0f / 255f, 1f);
        shapeRenderer.rect(165, screenHeight - 80 - 5 + 50, 50 , 25);
        shapeRenderer.end();

        // THANH KI (xanh)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f / 255f, 157f / 255f, 212f / 255f, 1f);
        shapeRenderer.rect(155, screenHeight - 80 - 5 + 50 - 25, 110 , 25);
        shapeRenderer.end();

        // RENDER SAU ẢNH ĐẬU THẦN ( trắng )
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f,1f,1f, 1f);
        shapeRenderer.rect(screenWidth - 75- 10 + 10, 10 + 10, 53 , 53);
        shapeRenderer.end();

        batch.begin();
        // ô chat (góc phải trên)
        int ochatW = 60;
        int ochatH = 60;
        float ochatX = screenWidth - ochatW - 15;
        float ochatY = screenHeight-10-ochatH;
        Texture texOChat = (thoiGianClickOChat > 0) ? ochatclick : ochat;
        batch.draw(texOChat, ochatX, ochatY, ochatW, ochatH);

        // thanh HP (góc trái trên)
        int thanhhpW = 300;
        int thanhhpH = 80;
        float hpX = 5;
        float hpY = screenHeight - thanhhpH - 5;
        batch.draw(thanhhp, hpX, hpY, thanhhpW, thanhhpH);
        // ô đậu thần (góc phải dưới)
        int odauthanW = 75;
        int odauthanH = 75;
        float odauthanX = screenWidth - odauthanW - 10;
        float odauthanY = 10;
        Texture texODauThan = (thoiGianClickODauThan > 0) ? odauthanclick : odauthan;
        batch.draw(texODauThan, odauthanX, odauthanY, odauthanW, odauthanH);

        // ô skill (hàng ngang phía dưới)
        int oskillW = 50;
        int oskillH = 50;
        float skillBaseX = 30;
        float skillY = 25f;

        for (int i = 0; i < 5; i++) {
            float x = skillBaseX + i * (20f + 45f);
            Texture texSkill = (skillDangChon == i) ? oskillclick : oskill;
            batch.draw(texSkill, x, skillY, oskillW, oskillH);

            // icon kỹ năng
            if (skillIcons != null && skillIcons[i] != null) {
                batch.draw(skillIcons[i].icon, x + 5, skillY + 5, oskillW - 10, oskillH - 10);
            }

            // số kỹ năng
            font.setColor(Color.WHITE);
            String text = (i + 1) + "";
            layout.setText(font, text);
            font.draw(batch, layout, x + (oskillW - layout.width) / 2, skillY + oskillH + 15f);
        }
        // nút popup thông tin nhân vật (bên trái trên)
        float nutpopupX = 0f;
        float nutpopupY = screenHeight / 4f *3;
        batch.draw(nutpopup, nutpopupX, nutpopupY, 25, 35);

        // Tên nhân vật ngay ở thanhhp.png
        font.setColor(0f / 255f, 83f / 255f, 37f / 255f, 1f);
        layout.setText(font, "HaiDang");
        font.draw(batch, layout, 52, screenHeight - 80 - 5 + 50);

        // số đậu thần
        font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        layout.setText(font, "0");
        font.draw(batch, layout, screenWidth - 75- 10 + 33, 10 + 43);
    }

    public void setSkillIcons(SkillIcon[] skillIcons) {
        this.skillIcons = skillIcons;
    }
    public void clickOChat() {
        thoiGianClickOChat = 0.2f;
    }
    public void clickODauThan() {
        thoiGianClickODauThan = 0.2f;
    }
    public void chonSkill(int index) {
        if (index >= 0 && index < 5) {
            skillDangChon = index;
        }
    }
    public void update(float delta) {
        if (thoiGianClickOChat > 0) {
            thoiGianClickOChat -= delta;
        }
        if (thoiGianClickODauThan > 0) {
            thoiGianClickODauThan -= delta;
        }
    }
    public void xuLyClick(int x, int y) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // === VÙNG Ô CHAT ===
        int ochatW = 60;
        int ochatH = 60;
        float ochatX = screenWidth - ochatW - 15;
        float ochatY = screenHeight-10-ochatH;
        if (x >= ochatX && x <= ochatX + 60 && y >= ochatY && y <= ochatY + 60) {
            clickOChat();
        }

        // === VÙNG Ô ĐẬU THẦN ===
        int odauthanW = 75;
        int odauthanH = 75;
        float odauthanX = screenWidth - odauthanW - 10;
        float odauthanY = 10;
        if (x >= odauthanX && x <= odauthanX + 75 && y >= odauthanY && y <= odauthanY + 75) {
            clickODauThan();
        }
        // Vùng mở popup
        float nutPopupX = 0f;
        float nutPopupY = screenHeight / 4f * 3;
        if (x >= nutPopupX && x <= nutPopupX + 25 && y >= nutPopupY && y <= nutPopupY + 35) {
            hienPopupNhanVat();
        }

        // Vùng nutX để tắt popup
        if (dangHienPopup) {
            float nutXW = nutX.getWidth() * 0.5f;
            float nutXH = nutX.getHeight() * 0.55f;
            float nutXX = 350 - nutXW - 6;
            float nutXY = 610 - nutXH - 5;
            if (x >= nutXX && x <= nutXX + nutXW && y >= nutXY && y <= nutXY + nutXH) {
                tatPopupNhanVat();
            }
        }
        // === VÙNG SKILL (nếu muốn bắt click skill sau) ===
    }
    public void hienPopupNhanVat() {
        dangHienPopup = true;
    }

    public void tatPopupNhanVat() {
        dangHienPopup = false;
    }

    public boolean isDangHienPopup() {
        return dangHienPopup;
    }
    public void renderPopup(SpriteBatch batch) {
        if (!dangHienPopup) return;

        batch.draw(popupNhanVat, 0, 0, 350, 610);
        float nutXW = nutX.getWidth() * 0.5f;
        float nutXH = nutX.getHeight() * 0.55f;
        batch.draw(nutX, 350 - nutXW - 6, 610 - nutXH - 5, nutXW, nutXH - 5);
    }
    public void dispose() {
        ochat.dispose();
        ochatclick.dispose();
        thanhhp.dispose();
        odauthan.dispose();
        odauthanclick.dispose();
        oskill.dispose();
        oskillclick.dispose();
        nutpopup.dispose();
        popupNhanVat.dispose();
        nutX.dispose();
    }
}
