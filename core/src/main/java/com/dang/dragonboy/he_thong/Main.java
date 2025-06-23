package com.dang.dragonboy.he_thong;

import com.badlogic.gdx.Game;

import com.dang.dragonboy.giao_dien_ngoai.ManHinhKhoiDong;

public class Main extends Game {
    @Override
    public void create() {
        this.setScreen(new ManHinhKhoiDong(this));
    }

    @Override
    public void render() {
        super.render(); // gọi render của màn hình hiện tại
    }

    @Override
    public void dispose() {
        // Nếu có assetManager dùng chung thì dispose ở đây
    }
}
