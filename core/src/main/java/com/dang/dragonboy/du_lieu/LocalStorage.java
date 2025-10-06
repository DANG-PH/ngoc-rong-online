package com.dang.dragonboy.du_lieu;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocalStorage {

    // Lưu trong thư mục người dùng, đảm bảo build ra .jar vẫn ghi được
    private static final String FILE_PATH = System.getProperty("user.home")
        + File.separator + "DragonBoy"
        + File.separator + "user_data.json";

    private static final Gson gson = new Gson();

    static {
        // Tạo thư mục nếu chưa tồn tại
        File folder = new File(System.getProperty("user.home") + File.separator + "DragonBoy");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    // Lưu dữ liệu
    public static void saveLastUser(String username, String token) {
        Map<String, String> data = new HashMap<>();
        data.put("lastUsername", username);
        data.put("access_token", token);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
            System.out.println("✅ Đã lưu user: " + username + " - token: " + token);
            System.out.println("📂 File lưu tại: " + new File(FILE_PATH).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Đọc dữ liệu
    public static Map<String, String> loadLastUser() {
        File f = new File(FILE_PATH);
        if (!f.exists()) return null;

        try (FileReader reader = new FileReader(f)) {
            return gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
