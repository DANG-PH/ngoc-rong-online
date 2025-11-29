package com.dang.dragonboy.network;

import com.dang.dragonboy.network.DTO.ItemCanLuu;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;
import com.dang.dragonboy.du_lieu.State_Management;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ApiItemService {
    private static final String BASE_URL = "http://localhost:3000/item";
    private static final Gson gson = new Gson();

    // Lưu danh sách item của user
    public static boolean saveItems(String username, List<ItemCanLuu> items) {
        try {
            URL url = new URL(BASE_URL + "/items");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setDoOutput(true);

            // Convert list item thành JSON
            Map<String, Object> payload = new HashMap<>();
            payload.put("items", items); // items là List<ItemCanLuu>
            String jsonInput = gson.toJson(payload);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    System.out.println("SaveItems response: " + response);
                }
                return true;
            } else {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        error.append(line.trim());
                    }
                    System.err.println("SaveItems failed: " + error);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách item từ server
    public static List<ItemCanLuu> getItems() {
        try {
            URL url = new URL(BASE_URL + "/user-items");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            int status = conn.getResponseCode();
            System.out.println("HTTP status: " + status);

            if (status == 200 || status == 201) {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    String jsonResponse = response.toString();
//                    System.out.println("Response JSON: " + jsonResponse);

                    // Parse JSON object trước
                    JsonObject obj = gson.fromJson(jsonResponse, JsonObject.class);

                    // Lấy array bên trong key "items"
                    JsonArray itemsArray = obj.getAsJsonArray("items");

                    // Chuyển JsonArray thành List<ItemCanLuu>
                    ItemCanLuu[] items = gson.fromJson(itemsArray, ItemCanLuu[].class);
                    return Arrays.asList(items);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
