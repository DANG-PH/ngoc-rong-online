package com.dang.dragonboy.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;

public class ApiService {
    private static final String BASE_URL = "http://localhost:8080/api/auth";
    private static final Gson gson = new Gson();

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

            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static UserResponse login(String username, String password) {
        try {
            URL url = new URL(BASE_URL + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setConnectTimeout(5000); // timeout 5 giây
            conn.setReadTimeout(5000);    // timeout 5 giây
            conn.setDoOutput(true);

            String jsonInput = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}", username, password
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();

            if (status == 200) {
                // ✅ login thành công, parse thành UserResponse
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    String jsonResponse = response.toString();
                    System.out.println("Server response: " + jsonResponse);
                    return gson.fromJson(jsonResponse, UserResponse.class);
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        error.append(line.trim());
                    }
                    System.err.println("Login failed, status: " + status + ", error: " + error.toString());
                }
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // login fail
    }

    public static boolean saveGame(UserResponse user) {
        try {
            Gson gson = new Gson();

            // chỉ gửi username + vang + ngoc
            JsonObject json = new JsonObject();
            json.addProperty("username", user.username);
            json.addProperty("vang", user.vang);
            json.addProperty("ngoc", user.ngoc);

            String jsonInput = gson.toJson(json);

            URL url = new URL(BASE_URL + "/saveGame");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            if (code == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    System.out.println("SaveGame response: " + response.toString());
                }
                return true;
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) error.append(line.trim());
                    System.err.println("SaveGame error: " + error.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveGameAsync(UserResponse user) {
        new Thread(() -> {
            boolean success = saveGame(user);
            if (success) {
                System.out.println("Save game async: success");
            } else {
                System.out.println("Save game async: failed");
            }
        }).start();
    }
}
