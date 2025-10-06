package com.dang.dragonboy.he_thong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import com.dang.dragonboy.giao_dien_ngoai.ManHinhKhoiDong;
import com.dang.dragonboy.giao_dien_trong.ManHinhNhaGohan;
import com.dang.dragonboy.network.*;
import com.dang.dragonboy.du_lieu.*;
import java.util.*;
import com.dang.dragonboy.item.*;

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


        Map<String, String> savedUser = LocalStorage.loadLastUser();
        if (savedUser != null) {
            String token = savedUser.get("access_token");
            String username = savedUser.get("lastUsername");

            // ✅ Gọi API /profile để kiểm tra token còn hạn không
            UserResponse profile = ApiService.getProfile(token);

            if (profile != null) {
                State_Management.setUserResponse(profile);
            } else {
                System.out.println("Token hết hạn hoặc không hợp lệ → yêu cầu đăng nhập lại");
            }
        } else {
            System.out.println("Chưa có người dùng nào, yêu cầu đăng nhập");
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        UserResponse currentUser = State_Management.getUserResponse();
        if (currentUser != null) {
            String username = currentUser.username;

            // Gom danh sách tất cả item
            List<ItemCanLuu> allItems = new ArrayList<>();

            // Thêm tất cả item từ các danh sách vào allItems
            if (State_Management.getDuLieuNguoiChoi() != null) {
                DuLieuNguoiChoi duLieu = State_Management.getDuLieuNguoiChoi();

                // Hành trang
                if (duLieu.getHanhTrang() != null) {
                    for (Item item : duLieu.getHanhTrang()) {
                        ItemCanLuu converted = State_Management.getDuLieuNguoiChoi().convertItem(item, "hanhtrang");
                        if (converted != null) allItems.add(converted);
                    }
                }

                // Hành trang đang mặc
                if (duLieu.getHanhTrangDangMac() != null) {
                    for (Item item : duLieu.getHanhTrangDangMac()) {
                        ItemCanLuu converted = State_Management.getDuLieuNguoiChoi().convertItem(item, "hanhtrangdangmac");
                        if (converted != null) allItems.add(converted);
                    }
                }

                // Rương đồ
                if (duLieu.getHanhTrangRuongDo() != null) {
                    for (Item item : duLieu.getHanhTrangRuongDo()) {
                        ItemCanLuu converted = State_Management.getDuLieuNguoiChoi().convertItem(item, "ruongdo");
                        if (converted != null) allItems.add(converted);
                    }
                }

                // Hành trang đệ tử
                if (duLieu.coDeTu() && duLieu.deTu != null && duLieu.deTu.getHanhTrangDangMac() != null) {
                    for (Item item : duLieu.deTu.getHanhTrangDangMac()) {
                        ItemCanLuu converted = State_Management.getDuLieuNguoiChoi().convertItem(item, "hanhtrangdetu");
                        if (converted != null) allItems.add(converted);
                    }
                }
            }

            // Gửi lên backend
            boolean success = ApiItemService.saveItems(State_Management.getUserResponse().username, allItems);
            if (success) {
                System.out.println("Đã lưu toàn bộ item của user " + State_Management.getUserResponse().username + " thành công!");
            } else {
                System.err.println("Lỗi khi lưu item cho user " + State_Management.getUserResponse().username);
            }

            // Lưu dữ liệu user cuối
            ApiService.saveGame(currentUser);
            System.out.println("Đã lưu dữ liệu lần cuối trước khi thoát game!");
        }

        if (assetManager != null) {
            assetManager.dispose();
        }
    }
}
