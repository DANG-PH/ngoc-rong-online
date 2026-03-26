package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

import com.dang.dragonboy.he_thong.Main;

public class ManHinhChonMayChu implements Screen {
    private Main game;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font, fontText, fontThuong;
    private SpriteBatch batch;
    private Texture sky, nuixa, nui, nuicay, nuithap;
    private Texture nutdn, nutclick;
    private Texture nutsv, nutclicksv;
    private float scrollX_cay = 0;
    private float scrollX_thap = 0;
    private GlyphLayout layout;
    private Texture formmaychu;
    private int nutDuocClick = -1;     // nút nào đang được click (0-6)
    private float thoiGianClick = 0;   // thời gian đếm sau khi click
    private boolean dangChuyenManHinh = false;
    private boolean dangDoiMayChu = false;
    public ManHinhChonMayChu(Main game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        layout = new GlyphLayout();
    }

    @Override
    public void show() {

        sky = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "sky_" + "traidat" + ".png");
        nuixa = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuixa_" + "traidat" + ".png");
        nui = new Texture("hud/giaodienngoai/" + "traidat" + "/" + "nui_" + "traidat" + ".png");
        nuicay = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuicay_" + "traidat" + ".png");
        nuithap = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuithap_" + "traidat" + ".png");

        nutdn = new Texture("hud/giaodienngoai/chung/nutdangnhap3.png");
        nutclick = new Texture("hud/giaodienngoai/chung/nutclick2.png");

        nutsv = new Texture("hud/giaodienngoai/chung/maychubutton.png");
        nutclicksv = new Texture("hud/giaodienngoai/chung/maychuclickbutton.png");

        formmaychu = new Texture("hud/giaodienngoai/chung/formmaychu.jpg");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ẩ ế ồ ạ ả ể ẩ ờ ừ ở http://";
        param.size = 18;
        font = generator.generateFont(param);
        param.size = 15;
        fontText = generator.generateFont(param);
        generator.dispose();
    }

    @Override
    public void render(float delta) {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (Gdx.input.justTouched() && !dangChuyenManHinh) {
            float baseX = (Gdx.graphics.getWidth() - 680) / 2f + 12.5f;

            if (dangDoiMayChu) {
                // Click "máy chủ tiêu chuẩn" để quay lại
                if (mouseX >= baseX && mouseX <= baseX + 200 && mouseY >= 416 && mouseY <= 416 + 36) {
                    dangDoiMayChu = false;
                }
                // Click nút vào HAIDANG1
                float haidangX = (Gdx.graphics.getWidth() - 400) / 2f + 150;
                float haidangY = 395;
                if (mouseX >= haidangX && mouseX <= haidangX + 145 && mouseY >= haidangY && mouseY <= haidangY + 46) {
                    nutDuocClick = 8;
                    thoiGianClick = 0;
                    dangChuyenManHinh = true;
                }
            } else {
                // Click "máy chủ admin" để chuyển sang
                if (mouseX >= baseX && mouseX <= baseX + 200 && mouseY >= 370 && mouseY <= 370 + 36) {
                    dangDoiMayChu = true;
                } else {
                    // Click các server thường (0–6)
                    for (int i = 0; i < 7; i++) {
                        int row = i % 4;
                        int col = i / 4;
                        float x = (Gdx.graphics.getWidth() - 400) / 2f + 150 + (col == 1 ? 165 : 0);
                        float y = 395 - row * 60;
                        if (mouseX >= x && mouseX <= x + 145 && mouseY >= y && mouseY <= y + 46) {
                            nutDuocClick = i;
                            thoiGianClick = 0;
                            dangChuyenManHinh = true;
                            break;
                        }
                    }
                }
            }
        }

        if (dangChuyenManHinh) {
            thoiGianClick += delta;
            if (thoiGianClick >= 0.1f) {
                if (nutDuocClick == 8) {
                    game.setScreen(new ManHinhMenu(game, "HAIDANG1", false));
                } else {
                    int j = nutDuocClick % 4;
                    int i = nutDuocClick / 4;
                    int mayChu = 2 * j + (i == 0 ? 1 : 2);
                    game.setScreen(new ManHinhMenu(game, mayChu, false));
                }
            }
        }

        scrollX_cay -= 30 * delta;
        scrollX_thap -= 60 * delta;
        if (scrollX_cay <= -340) scrollX_cay += 340;
        if (scrollX_thap <= -340) scrollX_thap += 340;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(25 / 255f, 176 / 255f, 248 / 255f, 1);
        shapeRenderer.rect(0, 460, 1020, 150);
        shapeRenderer.end();

        batch.begin();
        for (int i = 0; i < 4; i++) {
            batch.draw(sky, i * 255, 310, 255, 150);
            batch.draw(nuixa, i * 255, 310, 255, 150);
        }
        for (int i = 0; i < 2; i++) {
            batch.draw(nui, i * 510, 280, 510, 170);
        }
        for (int i = 0; i < 4; i++) {
            batch.draw(nuicay, scrollX_cay + i * 340, 140, 340, 220);
        }
        for (int i = 0; i < 4; i++) {
            batch.draw(nuithap, scrollX_thap + i * 340, 0, 340, 280);
        }
        batch.draw(formmaychu,(Gdx.graphics.getWidth()-680)/2,65,680,400);
        //tieu de chon may chu
        batch.draw(nutsv,(Gdx.graphics.getWidth()-190)/2,490,190,36);
        font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        layout.setText(font, "Chọn máy chủ");
        font.draw(batch, layout, (Gdx.graphics.getWidth()-190)/2f + (190-layout.width)/2f, 490 + 25);
        //footer
        fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        layout.setText(fontText, "© 2025 Chiến Binh Rồng Thiêng | Phạm Hải Đăng");
        fontText.draw(batch, layout, (Gdx.graphics.getWidth()-680)/2 + 5, 65 + 20);
        if (dangDoiMayChu){
            fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
            layout.setText(fontText,
                "Máy chủ admin: Chào mừng nhà phát triển trở lại!",
                fontText.getColor(), // dùng lại màu đã set
                165,                 // wrapWidth
                Align.left,          // căn trái mặc định
                true);               // bật tự xuống dòng
            fontText.draw(batch, layout, (Gdx.graphics.getWidth()-680)/2 +(200-layout.width)/2f, 330 + 20);
            batch.draw(nutsv,(Gdx.graphics.getWidth()-680)/2 + 12.5f,416,200,36);
            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            layout.setText(font, "Máy chủ tiêu chuẩn");
            font.draw(batch, layout, (Gdx.graphics.getWidth()-680)/2f + 12.5f + (200-layout.width)/2f, 416 + 23);
            //nút server cho admin
            batch.draw(nutclicksv,(Gdx.graphics.getWidth()-680)/2 + 12.5f,370,200,36);
            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            layout.setText(font, "Máy chủ admin");
            font.draw(batch, layout, (Gdx.graphics.getWidth()-680)/2f + 12.5f + (200-layout.width)/2f, 370 + 23);
            Texture tex = (nutDuocClick == 8) ? nutclick : nutdn;
            batch.draw(tex, (Gdx.graphics.getWidth() - 400) / 2 + 150, 395, 145, 46);
            layout.setText(font, "HAIDANG1");
            font.draw(batch, layout, (Gdx.graphics.getWidth() - 400) / 2 + 150+ (145 - layout.width) / 2f,395 + 28);
            batch.end();
            return;
        }
        fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
        layout.setText(fontText,
            "Máy chủ tiêu chuẩn: Hành trình rồng thiêng đang chờ bạn!",
            fontText.getColor(), // dùng lại màu đã set
            165,                 // wrapWidth
            Align.left,          // căn trái mặc định
            true);               // bật tự xuống dòng
        fontText.draw(batch, layout, (Gdx.graphics.getWidth()-680)/2 +(200-layout.width)/2f, 330 + 20);
        //nút server thường
        batch.draw(nutclicksv,(Gdx.graphics.getWidth()-680)/2 + 12.5f,416,200,36);
        font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        layout.setText(font, "Máy chủ tiêu chuẩn");
        font.draw(batch, layout, (Gdx.graphics.getWidth()-680)/2f + 12.5f + (200-layout.width)/2f, 416 + 23);
        //nút server cho admin
        batch.draw(nutsv,(Gdx.graphics.getWidth()-680)/2 + 12.5f,370,200,36);
        font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        layout.setText(font, "Máy chủ admin");
        font.draw(batch, layout, (Gdx.graphics.getWidth()-680)/2f + 12.5f + (200-layout.width)/2f, 370 + 23);
        //vẽ các server ở form máy chủ tiêu chuẩn
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                for (int j = 0; j < 4; j++) {
                    int index = i * 4 + j; // từ 0 đến 3
                    int maychu = 2 * j + 1;
                    float x = (Gdx.graphics.getWidth() - 400) / 2 + 150;
                    float y = 395 - j * 60;
                    Texture tex = (nutDuocClick == index) ? nutclick : nutdn;
                    batch.draw(tex, x, y, 145, 46);
                    layout.setText(font, "Vũ trụ " + maychu);
                    font.draw(batch, layout, x + (145 - layout.width) / 2f, y + 28);
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    int index = i * 4 + j; // từ 4 đến 6
                    int maychu = 2 * j + 2;
                    float x = (Gdx.graphics.getWidth() - 400) / 2 + 150 + 165;
                    float y = 395 - j * 60;
                    Texture tex = (nutDuocClick == index) ? nutclick : nutdn;
                    batch.draw(tex, x, y, 145, 46);
                    layout.setText(font, "Vũ trụ " + maychu);
                    font.draw(batch, layout, x + (145 - layout.width) / 2f, y + 28);
                }
            }
        }

        batch.end();

    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        sky.dispose();
        nuixa.dispose();
        nui.dispose();
        nuicay.dispose();
        nuithap.dispose();
        nutdn.dispose();
        nutclick.dispose();
        nutsv.dispose();
        nutclicksv.dispose();
        formmaychu.dispose();
        font.dispose();
        fontText.dispose();
        if (fontThuong != null) fontThuong.dispose(); // nếu dùng
        shapeRenderer.dispose();
        batch.dispose();
    }
}
