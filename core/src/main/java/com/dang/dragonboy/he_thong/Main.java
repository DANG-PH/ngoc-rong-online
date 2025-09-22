package com.dang.dragonboy.he_thong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import com.dang.dragonboy.giao_dien_ngoai.ManHinhKhoiDong;
import com.dang.dragonboy.giao_dien_trong.ManHinhNhaGohan;
import com.dang.dragonboy.network.*;
import com.dang.dragonboy.du_lieu.*;

public class Main extends Game {

    public static AssetManager assetManager;  // AssetManager dùng chung toàn game

    @Override
    public void create() {
        assetManager = new AssetManager();

        // load trước các texture nặng (NPC Hải Đăng : gacha, vé quay…)
        for (int i = 0; i < 16; i++) {
            assetManager.load("hieuung/hieuunggame/gacha/1 ("+(i+2)+").png", Texture.class);
        }
        assetManager.load("hieuung/hieuunggame/gacha/1 (1).png", Texture.class);
        assetManager.load("hieuung/hieuunggame/gacha/gacha1.png", Texture.class);
        assetManager.load("vatpham/vatphamgame/ve_quay_npc_haidang/vequay.png", Texture.class);
        assetManager.load("vatpham/vatphamgame/ve_quay_npc_haidang/vequaykhoa.png", Texture.class);
        assetManager.load("hieuung/hieuunggame/gacha/shopthuong.png", Texture.class);
        assetManager.load("hieuung/hieuunggame/gacha/shopvip.png", Texture.class);

        // có thể load thêm các texture khác ở đây...

        // chờ load xong hết
        assetManager.finishLoading();

        // sau đó mới chuyển màn hình
//        this.setScreen(new ManHinhNhaGohan(this,"admin","traidat","Goku",null));
        // hoặc dùng màn hình khởi động rồi mới vào game
         this.setScreen(new ManHinhKhoiDong(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        UserResponse currentUser = State_Management.getUserResponse();
        if (currentUser != null) {
            // Dùng bản đồng bộ để chắc chắn gửi xong trước khi JVM thoát
            ApiService.saveGame(currentUser);
            System.out.println("Đã lưu dữ liệu lần cuối trước khi thoát game!");
        }

        if (assetManager != null) {
            assetManager.dispose();
        }
    }
}
