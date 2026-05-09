package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.dang.dragonboy.he_thong.Main;

import java.util.function.Supplier;

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
    private Supplier<Screen> manHinhTiepTheoSupplier;
    // Constructor dùng cho splash khởi động ban đầu → menu
    public ManHinhSplash(Main game) {
        this(game, null, false);
    }
    // Splash khi bấm nút: có màn tiếp theo & hiển thị logo/chữ
    public ManHinhSplash(Main game, Screen manHinhTiepTheo) {
        this(game, manHinhTiepTheo, true);
    }
    // Constructor mới dùng Supplier - tạo Screen LAZY
    public ManHinhSplash(Main game, Supplier<Screen> supplier) {
        this.game = game;
        this.manHinhTiepTheoSupplier = supplier;
        this.hienLogoVaChu = true;
        layout = new GlyphLayout();
        batch = new SpriteBatch();
    }
    // Constructor chính
    public ManHinhSplash(Main game, Screen manHinhTiepTheo, boolean hienLogoVaChu) {
        this.game = game;
        this.manHinhTiepTheo = manHinhTiepTheo;
        this.hienLogoVaChu = hienLogoVaChu;
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
            /*
            Chỗ này có trade off
            Tại sao lúc mới vào game cần gọi new Constructor map ở đây
            Còn khi vào game thì khi chuyển map gọi trực tiếp new Map rồi truyền map đó vào đây

            Tại vì constructor của các map khởi tạo lag chủ yếu ở phần VeHUD, NhanVat, DuLieuNguoiChoi
            Những cái này khi vừa vào game phải tạo từ đầu, dẫn đến nếu gọi new Map ở màn hình menu lúc vừa vào game
            -> Dẫn đến đơ tại màn hình menu
            Còn khi đã vào game rồi, chuyển map thì k cần tạo NhanVat, VeHUD, ... nữa
            Constructor thì dùng các data cũ
            nên giảm lag và có thể new trực tiếp

            Constructor ở đây có tác dụng có thể fetch api gọi để lấy data NPC

            nên là khi đã trong game thì npc sẽ luôn được xuất hiện
            còn khi vừa vào game thì gọi constructor ở đây ( tại vì k muốn màn hình menu bị đơ dẫn đến UX kém)
            nhưng trade off là khi vừa vào game gọi constructor ở đây thì sẽ fetch api chậm hơn trước khi vào game nếu mạng kém
            */
            if (manHinhTiepTheoSupplier != null) {
                game.setScreen(manHinhTiepTheoSupplier.get()); // tạo ở đây, lag bị che
            } else if (manHinhTiepTheo != null) {
                game.setScreen(manHinhTiepTheo);
            } else {
                game.setScreen(new ManHinhMenu(game, null, null));
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

    @Override public void show() {
        splashImage = new Texture("hud/giaodienngoai/chung/nr5s.png");
        logo = new Texture("hud/giaodienngoai/chung/chuberong.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        parameter.characters = "Xóa dữ liệu ờ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?., ";
        font = generator.generateFont(parameter);
        generator.dispose();
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
