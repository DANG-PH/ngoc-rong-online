package com.dang.dragonboy.du_lieu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class LocalStorage {

    // Gdx.files.local trỏ tới thư mục ghi được riêng của app trên từng nền tảng
    // (desktop: thư mục chạy app; Android: getFilesDir() trong sandbox của app)
    private static final String FILE_PATH = "DragonBoy/user_data.json";

    private static final Gson gson = new Gson();

    // Lưu dữ liệu
    public static void saveLastUser(String username, String token) {
        Map<String, String> data = new HashMap<>();
        data.put("lastUsername", username);
        data.put("access_token", token);

        try {
            FileHandle file = Gdx.files.local(FILE_PATH);
            file.writeString(gson.toJson(data), false);
            System.out.println("✅ Đã lưu user: " + username + " - token: " + token);
            System.out.println("📂 File lưu tại: " + file.file().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Đọc dữ liệu
    public static Map<String, String> loadLastUser() {
        FileHandle file = Gdx.files.local(FILE_PATH);
        if (!file.exists()) return null;

        try {
            return gson.fromJson(file.readString(), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
