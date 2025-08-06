package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.dang.dragonboy.he_thong.Main;

public class ManHinhKhoiDong implements Screen {
    private Main game; // ko có dòng này thì biến ko thể xài setScreen
    private Texture logogame,logoptit1,logoptit2,logochu1,logochu2;
    private SpriteBatch batch;
    private float thoiGian = 0f;
    //bắt buộc phải truyền kiểu class Main vào vì java là nn lập trình tĩnh , bên kia this ko cần vì nằm trong class main rồi
    public ManHinhKhoiDong(Main game) {
        this.game = game; //ko có dòng này thì game của class = null nên setScreen ko có tác dụng phải gán để các file sau còn xài được render và các thứ của hàm Main
        logogame = new Texture("hud/giaodienngoai/chung/logogame.png");
        logoptit1 = new Texture("hud/giaodienngoai/chung/logoptit1.png");
        logoptit2 = new Texture("hud/giaodienngoai/chung/logoptit2.png");
        logochu1 = new Texture("hud/giaodienngoai/chung/logochu1.png");
        logochu2 = new Texture("hud/giaodienngoai/chung/logochu2.png");
        batch = new SpriteBatch();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // dùng bình thường để test game
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

        // Dùng khi phát hành
//        thoiGian += delta;
//
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        float alpha;
//        if (thoiGian<=0.8f) {
//            alpha = 1-(0.8f-thoiGian)/0.8f;
//        } else {
//            alpha = 1;
//        }
//        batch.begin();
//        float scaledWidth = logogame.getWidth();
//        float scaledHeight = logogame.getHeight();
//        float x = (Gdx.graphics.getWidth() - scaledWidth) / 2f;
//        float y = (Gdx.graphics.getHeight() - scaledHeight) / 2f;
//        batch.setColor(1f, 1f, 1f, alpha);
//        batch.draw(logogame, x, y, scaledWidth, scaledHeight);
//        batch.setColor(1f, 1f, 1f, 1f);
//        batch.end();
//
//        if (thoiGian > 3f) {
//            game.setScreen(new ManHinhSplash(game));
//        }

        // Dùng khi show dự án
//        thoiGian += delta;
//
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        if (thoiGian>3f) {
//            float alpha;
//            if (thoiGian <= 4.5f) {
//                alpha = 1 - (4.5f - thoiGian) / 1.5f;
//            } else {
//                alpha = 1;
//            }
//            batch.begin();
//            float scaledWidth1 = logoptit1.getWidth()*0.24f;
//            float scaledHeight1 = logoptit1.getHeight()*0.24f;
//            float x1 = (Gdx.graphics.getWidth() - scaledWidth1) / 2f - 200f;
//            float y1 = (Gdx.graphics.getHeight() - scaledHeight1) / 2f + 40f;
//            float scaledWidth2 = logoptit2.getWidth();
//            float scaledHeight2 = logoptit2.getHeight();
//            float x2 = (Gdx.graphics.getWidth() - scaledWidth2) / 2f + 200f;
//            float y2 = (Gdx.graphics.getHeight() - scaledHeight2) / 2f + 30f;
//            batch.setColor(1f, 1f, 1f, alpha);
//            batch.draw(logoptit1, x1, y1, scaledWidth1, scaledHeight1);
//            batch.draw(logochu1,  x1+(scaledWidth1-logochu1.getWidth()*0.40f)/2f, y1-35,logochu1.getWidth()*0.40f,logochu1.getHeight()*0.40f);
//            batch.draw(logoptit2, x2, y2, scaledWidth2, scaledHeight2);
//            batch.draw(logochu2,  x2+(scaledWidth2-logochu2.getWidth()*0.40f)/2f, y2-50,logochu2.getWidth()*0.40f,logochu2.getHeight()*0.40f);
//            batch.setColor(1f, 1f, 1f, 1f);
//            batch.end();
//
//            if (thoiGian > 6f) {
//                game.setScreen(new ManHinhSplash(game));
//            }
//        } else {
//            float alpha;
//            if (thoiGian<=3f && thoiGian>2.2f) {
//                alpha = (3f-thoiGian)/0.8f;
//            } else {
//                if (thoiGian<0.8f) {
//                    alpha = 1-(0.8f-thoiGian)/0.8f;
//                } else {
//                    alpha = 1;
//                }
//            }
//            batch.begin();
//            float scaledWidth = logogame.getWidth();
//            float scaledHeight = logogame.getHeight();
//            float x = (Gdx.graphics.getWidth() - scaledWidth) / 2f;
//            float y = (Gdx.graphics.getHeight() - scaledHeight) / 2f;
//            batch.setColor(1f, 1f, 1f, alpha);
//            batch.draw(logogame, x, y, scaledWidth, scaledHeight);
//            batch.setColor(1f, 1f, 1f, 1f);
//            batch.end();
//        }
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
        logoptit1.dispose();
        logoptit2.dispose();
        logochu1.dispose();
        logochu2.dispose();
    }
}
