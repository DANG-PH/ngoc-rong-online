package com.dang.dragonboy.du_lieu;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocalStorage {

    private static final String FILE_PATH =
        System.getProperty("user.dir") + "/core/src/main/java/com/dang/dragonboy/du_lieu/user_data.json";

    private static final Gson gson = new Gson();

    // Lưu dữ liệu
    public static void saveLastUser(String username, String token) {
        Map<String, String> data = new HashMap<>();
        data.put("lastUsername", username);
        data.put("access_token", token);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
            System.out.println("✅ Đã lưu user: " + username + " - token: " + token);
            System.out.println("📂 File lưu tại: " + new java.io.File(FILE_PATH).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Đọc dữ liệu
    public static Map<String, String> loadLastUser() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            return null;
        }
    }
}
