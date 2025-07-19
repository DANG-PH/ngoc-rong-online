package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.dang.dragonboy.he_thong.Main;

public class ManHinhSplash implements Screen {
    private Main game;
    private Texture splashImage;
    private BitmapFont font;
    private SpriteBatch batch;
    private float thoiGian = 0f;
    private float gocXoay = 0f;
    private GlyphLayout layout;
    private Texture logo;
    private Screen manHinhTiepTheo;
    private boolean hienLogoVaChu;
    // Constructor dùng cho splash khởi động ban đầu → menu
    public ManHinhSplash(Main game) {
        this(game, null, false);
    }
    // Splash khi bấm nút: có màn tiếp theo & hiển thị logo/chữ
    public ManHinhSplash(Main game, Screen manHinhTiepTheo) {
        this(game, manHinhTiepTheo, true);
    }
    // Constructor chính
    public ManHinhSplash(Main game, Screen manHinhTiepTheo, boolean hienLogoVaChu) {
        this.game = game;
        this.manHinhTiepTheo = manHinhTiepTheo;
        this.hienLogoVaChu = hienLogoVaChu;

        splashImage = new Texture("hud/giaodienngoai/chung/nr5s.png");
        logo = new Texture("hud/giaodienngoai/chung/chuberong.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        parameter.characters = "Xóa dữ liệu ờ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?., ";
        font = generator.generateFont(parameter);
        generator.dispose();

        layout = new GlyphLayout();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        thoiGian += delta;
        gocXoay += 1200 * delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        float size = 25;
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;
        if (hienLogoVaChu) {
            float logoWidth = 320, logoHeight = 200;
            batch.draw(logo, centerX - logoWidth / 2f + 4, centerY , logoWidth, logoHeight);

            layout.setText(font, "Xin chờ...");
            font.draw(batch, layout, centerX - layout.width / 2f, centerY - 35);
        }

        batch.draw(
            splashImage,
            (Gdx.graphics.getWidth() - size) / 2f,
            (Gdx.graphics.getHeight() - size) / 2f,
            size / 2f, size / 2f,
            size, size,
            1, 1,
            gocXoay,
            0, 0,
            splashImage.getWidth(), splashImage.getHeight(),
            false, false
        );

        layout.setText(font, "Xóa dữ liệu");
        font.setColor(1, 1, 1, 1);
        font.draw(batch, layout, Gdx.graphics.getWidth() - layout.width - 10, 30);
        batch.end();

        if (thoiGian > 1f) {
            if (manHinhTiepTheo != null) {
                game.setScreen(manHinhTiepTheo);
            } else {
                game.setScreen(new ManHinhMenu(game,null));
            }
        }
    }

    @Override
    public void dispose() {
        splashImage.dispose();
        font.dispose();
        batch.dispose();
        logo.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
