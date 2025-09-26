package com.dang.dragonboy.network;

import com.google.gson.Gson;
import com.dang.dragonboy.item.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiItemService {
    private static final String BASE_URL = "http://localhost:8080/api/items";
    private static final Gson gson = new Gson();

    // Lưu danh sách item của user
    public static boolean saveItems(String username, List<ItemCanLuu> items) {
        try {
            URL url = new URL(BASE_URL + "/add-multiple/" + username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Convert list item thành JSON
            String jsonInput = gson.toJson(items);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status == 200) {
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
    public static List<ItemCanLuu> getItems(String username) {
        try {
            URL url = new URL(BASE_URL + "/user/" + username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status == 200) {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    String jsonResponse = response.toString();

                    // parse JSON array -> List<Item>
                    ItemCanLuu[] itemsArray = gson.fromJson(jsonResponse, ItemCanLuu[].class);
                    return java.util.Arrays.asList(itemsArray);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return java.util.Collections.emptyList();
    }

    public static boolean addItem(String username, Item item) {
        try {
            URL url = new URL(BASE_URL + "/add/" + username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            String jsonInput = gson.toJson(item);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            return status == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateItem(Long itemId, Item item) {
        try {
            URL url = new URL(BASE_URL + "/" + itemId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            String jsonInput = gson.toJson(item);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            return status == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteItem(Long itemId) {
        try {
            URL url = new URL(BASE_URL + "/" + itemId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int status = conn.getResponseCode();
            return status == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
