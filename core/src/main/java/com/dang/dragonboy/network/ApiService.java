package com.dang.dragonboy.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.dang.dragonboy.du_lieu.LocalStorage;
import com.dang.dragonboy.du_lieu.State_Management;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonElement;
import java.util.*;
import com.google.gson.JsonParser;

public class ApiService {
    private static final String BASE_URL = "https://backendgamewebnestjs-production.up.railway.app/api/auth";
    private static final Gson gson = new Gson();

    public static boolean register(String username, String password) {
        try {
            // 1. Tạo URL tới API register

            URL url = new URL(BASE_URL + "/register");
            // 👉 Lúc này chỉ mới tạo 1 đối tượng URL, nó chứa đường dẫn http://localhost:8080/api/auth/register.
            // 👉 Chưa có kết nối gì tới server cả.

            // 2. Mở kết nối HTTP tới server
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 👉 Bắt đầu mở kết nối đến server ở địa chỉ URL trên.
            // 👉 Giờ đây `conn` là "đường ống" để mình gửi/nhận dữ liệu với server.

            // 3. Gửi dữ liệu kiểu POST (tạo mới tài khoản)
            conn.setRequestMethod("POST");
            // 👉 Mặc định HTTP request là GET.
            // 👉 Ở đây đổi thành POST để báo server: "Tôi sẽ gửi dữ liệu mới lên".
            // 👉 Nếu backend không có API POST /register → sẽ báo lỗi.

            // 4. Báo server biết dữ liệu gửi là JSON + Java Spring
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

//            // 4. Báo server biết dữ liệu gửi là JSON + NestJS
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Accept", "application/json");
            // 👉 Thêm 1 cái header HTTP:
            //    Content-Type: application/json
            // 👉 Nghĩa là dữ liệu trong request body sẽ là JSON.
            // 👉 Nếu không có cái này, server có thể không hiểu dữ liệu gửi lên.

            // 5. Cho phép gửi dữ liệu trong request body
            conn.setDoOutput(true);
            // 👉 Mặc định request không gửi body (chỉ GET thôi).
            // 👉 Với POST cần gửi dữ liệu → phải bật "output mode".
            // 👉 Sau khi bật, mình có thể lấy được OutputStream để ghi dữ liệu.

            // 6. Tạo dữ liệu JSON gửi lên
            String jsonInput = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            // 👉 Chuẩn bị 1 chuỗi JSON, ví dụ:
            // {"username":"DangDepTrai","password":"123456"}
            // 👉 Tạm thời chưa gửi, chỉ mới tạo string.

            System.out.println("Json gui len: "+jsonInput);

            // 7. Gửi dữ liệu JSON vào request body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8"); // 👉 Chuyển chuỗi JSON thành mảng byte (UTF-8).
                os.write(input, 0, input.length);           // 👉 Ghi toàn bộ mảng byte này vào OutputStream.
                // 👉 Lúc này dữ liệu JSON đã thực sự "chảy" từ client → server.
            }
            // 👉 Sau khi đóng try-with-resources, `os` tự đóng luôn.

            // 8. Kiểm tra server trả về mã 200 (OK) thì coi như đăng ký thành công
            return conn.getResponseCode() == 201;
            // 👉 Chờ server phản hồi.
            // 👉 Nếu server trả về "HTTP/1.1 200 OK" → hàm này trả true.
            // 👉 Nếu server trả về lỗi (400, 500, 404, …) → hàm này trả false.

        } catch (Exception e) {
            e.printStackTrace();  // 9. Nếu có lỗi thì in ra màn hình (ví dụ: không kết nối được server, JSON sai, …)
            return false;
        }
    }

    public static UserResponse login(String username, String password) {
        try {
            // === 1️⃣ GỬI REQUEST LOGIN ===
            URL url = new URL(BASE_URL + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);

            String jsonInput = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}", username, password
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                // ✅ Đăng nhập thành công → đọc access_token
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                );
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                br.close();
//
//                Server trả về JSON (text)
//                    ↓
//               Đọc vào biến response (String)
//                    ↓
//            JsonParser.parseString(response) → biến thành JSON thật sự
//                    ↓
//            .getAsJsonObject() để truy cập các field
//                    ↓
//            .get("access_token").getAsString() để lấy token

                // Ví dụ: {"access_token":"eyJhbGciOi..."}
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                String token = jsonObject.get("access_token").getAsString();

                // === 2️⃣ GỌI /PROFILE VỚI TOKEN ===
                UserResponse profile = getProfile(token);
                if (profile != null) {
                    State_Management.setToken(token); // Lưu token nếu cần
                    LocalStorage.saveLastUser(username, token); // Lưu vào file JSON
                }
                return profile;
            } else {
                System.err.println("Đăng nhập thất bại, mã lỗi HTTP: " + status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // login fail
    }

    // 🟨 2️⃣ GET PROFILE → nhận token, trả về UserResponse
    public static UserResponse getProfile(String token) {
        try {
            URL profileUrl = new URL(BASE_URL + "/profile");
            HttpURLConnection conn = (HttpURLConnection) profileUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status == 200) {
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                );
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                br.close();
                return gson.fromJson(response.toString(), UserResponse.class);
            } else {
                System.err.println("Token không hợp lệ hoặc hết hạn: HTTP " + status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveGame(UserResponse user) {
        try {
            Gson gson = new Gson();

            // chỉ gửi username + vang + ngoc
            JsonObject json = new JsonObject();
            json.addProperty("username", user.username);
            json.addProperty("vang", user.vang);
            json.addProperty("ngoc", user.ngoc);
            json.addProperty("sucManh", user.sucManh);
            json.addProperty("mapHienTai", user.mapHienTai);
            json.addProperty("x",user.x);
            json.addProperty("y",user.y);
            json.addProperty("coDeTu",user.coDeTu);
            // convert deTu object sang JsonElement rồi add
            JsonElement deTuJson = gson.toJsonTree(user.deTu);
            json.add("deTu", deTuJson);

            String jsonInput = gson.toJson(json);

//            System.out.println("SaveGame JSON gửi đi: " + jsonInput);

            URL url = new URL(BASE_URL + "/saveGame");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            if (code == 200 || code == 201) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
//                    System.out.println("SaveGame response: " + response.toString());
                }
                return true;
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) error.append(line.trim());
//                    System.err.println("SaveGame error: " + error.toString());
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
//                System.out.println("Save game async: success");
            } else {
//                System.out.println("Save game async: failed");
            }
        }).start();
    }

    public static UserResponse getBalance(String username) {
        try {
            URL url = new URL(BASE_URL + "/balance/" + username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    String jsonResponse = response.toString();
                    return gson.fromJson(jsonResponse, UserResponse.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Integer useVangNapTuWeb(String username, long amount) {
        try {
            URL url = new URL(BASE_URL + "/useVangNapTuWeb");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("amount", amount);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String response = br.readLine();
                    JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
                    return jsonResponse.get("used").getAsInt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer useNgocNapTuWeb(String username, long amount) {
        try {
            URL url = new URL(BASE_URL + "/useNgocNapTuWeb");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("amount", amount);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String response = br.readLine();
                    JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
                    return jsonResponse.get("used").getAsInt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ====== Gọi API lấy danh sách item web ======
    public static List<Integer> getItemsWeb(String username) {
        try {
            URL url = new URL(BASE_URL + "/getItemsWeb?username=" + username);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) response.append(line.trim());

                    // parse JSON array thành List<Integer>
                    Integer[] arr = gson.fromJson(response.toString(), Integer[].class);
                    return java.util.Arrays.asList(arr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return java.util.Collections.emptyList(); // nếu lỗi thì trả list rỗng
    }

    // ====== Gọi API sử dụng item web ======
    public static boolean useItemWeb(String username, int itemId) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + "/useItemWeb");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json"); // chuẩn JSON
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); // để gửi body

            // JSON body thủ công
            String jsonInputString = String.format("{\"username\":\"%s\",\"itemId\":%d}", username, itemId);
            System.out.println("Sending JSON: " + jsonInputString);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
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
                    System.out.println("UseItemWeb success: " + response.toString());
                }
                return true;
            } else {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    StringBuilder error = new StringBuilder();
                    while ((line = br.readLine()) != null) error.append(line.trim());
                    System.err.println("UseItemWeb failed: " + error.toString());
                }
            }

        } catch (Exception e) {
            System.err.println("Exception in useItemWeb: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }
        return false;
    }
}
