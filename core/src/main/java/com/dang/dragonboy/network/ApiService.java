package com.dang.dragonboy.network;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {
    private static final String BASE_URL = "http://localhost:8080/api/auth";
    // nhớ đổi localhost thành IP thật nếu chạy trên máy khác

    public static boolean register(String username, String password) {
        try {
            URL url = new URL(BASE_URL + "/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            String jsonInput = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            return code == 200; // thành công nếu backend trả 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
