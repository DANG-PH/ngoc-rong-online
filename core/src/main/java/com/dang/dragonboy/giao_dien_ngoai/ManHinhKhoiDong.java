package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.dang.dragonboy.he_thong.Main;

public class ManHinhKhoiDong implements Screen {
    private Main game; // ko có dòng này thì biến ko thể xài setScreen
    private Texture logogame;
    private SpriteBatch batch;
    private float thoiGian = 0f;
    //bắt buộc phải truyền kiểu class Main vào vì java là nn lập trình tĩnh , bên kia this ko cần vì nằm trong class main rồi
    public ManHinhKhoiDong(Main game) {
        this.game = game; //ko có dòng này thì game của class = null nên setScreen ko có tác dụng phải gán để các file sau còn xài được render và các thứ của hàm Main
        logogame = new Texture("hud/giaodienngoai/chung/logogame.png");
        batch = new SpriteBatch();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        thoiGian += delta;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        float scaledWidth = logogame.getWidth();
        float scaledHeight = logogame.getHeight();
        float x = (Gdx.graphics.getWidth() - scaledWidth) / 2f;
        float y = (Gdx.graphics.getHeight() - scaledHeight) / 2f;
        batch.draw(logogame, x, y, scaledWidth, scaledHeight);
        batch.end();

        if (thoiGian > 2f) {
            game.setScreen(new ManHinhSplash(game));
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        logogame.dispose();
        batch.dispose();
    }
}
