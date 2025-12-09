package com.dang.dragonboy.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.dang.dragonboy.du_lieu.LocalStorage;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.network.DTO.DeTuTheoUser;
import com.dang.dragonboy.network.DTO.ItemWeb;
import com.dang.dragonboy.network.DTO.UserResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonArray;

public class ApiService {
    private static final String BASE_URL = "http://localhost:3000";
    private static final Gson gson = new Gson();

    public static boolean register(String username, String password, String realname, String email) {
        try {
            URL url = new URL(BASE_URL + "/auth/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

//            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);

            String jsonInput = String.format(
                "{\"username\":\"%s\",\"realname\":\"%s\",\"password\":\"%s\",\"email\":\"%s\"}",
                username, realname, password, email
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            return conn.getResponseCode() == 201;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String login(String username, String password) {
        try {
            // === 1️⃣ GỬI REQUEST LOGIN ===
            URL url = new URL(BASE_URL + "/auth/login");
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
                String sessionId = jsonObject.get("sessionId").getAsString();

                if (sessionId != null) {
                    State_Management.setSessionId(sessionId); // Lưu token nếu cần
//                    LocalStorage.saveLastUser(username, token); // Lưu vào file JSON
                }
                return sessionId;
            } else {
                System.err.println("Đăng nhập thất bại, mã lỗi HTTP: " + status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // login fail
    }

    public static UserResponse verifyOTP(String sessionId, String OTP) {
        try {
            URL url = new URL(BASE_URL + "/auth/verify-otp");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);

            String jsonInput = String.format(
                "{\"sessionId\":\"%s\",\"otp\":\"%s\"}", sessionId, OTP
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

                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                String access_token = jsonObject.get("access_token").getAsString();
                String refresh_token = jsonObject.get("refresh_token").getAsString();
                int auth_id = jsonObject.get("auth_id").getAsInt();
                String role = jsonObject.get("role").getAsString();

                if (access_token != null) {
                    State_Management.setToken(access_token); // Lưu token nếu cần
                    State_Management.setRole(role);
                    State_Management.setAuth_id(auth_id);
                    State_Management.setRefresh_token(refresh_token);
                    LocalStorage.saveLastUser(State_Management.getSessionId(), access_token); // Lưu vào file JSON
//                    System.out.println("verify otp thanh cong");
                    return ApiService.getProfile(access_token);
                }
                return null;
            } else {
                System.err.println("verify otp thất bại, mã lỗi HTTP: " + status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // login fail
    }

    // 🟨 2️⃣ GET PROFILE → nhận token, trả về UserResponse
    public static UserResponse getProfile(String token) {
        try {
            URL profileUrl = new URL(BASE_URL + "/user/profile/" + State_Management.getAuth_id());
            HttpURLConnection conn = (HttpURLConnection) profileUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status != 200) {
                System.err.println("Token không hợp lệ hoặc hết hạn: HTTP " + status);
                return null;
            }

            BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
            br.close();

            JsonObject root = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject u = root.getAsJsonObject("user");

            UserResponse user = new UserResponse();
            user.id = u.get("id").getAsLong();

            user.vang = parseProtoLong(u.getAsJsonObject("vang"));
            user.ngoc = parseProtoLong(u.getAsJsonObject("ngoc"));
            user.sucManh = parseProtoLong(u.getAsJsonObject("sucManh"));
            user.vangNapTuWeb = parseProtoLong(u.getAsJsonObject("vangNapTuWeb"));
            user.ngocNapTuWeb = parseProtoLong(u.getAsJsonObject("ngocNapTuWeb"));

            user.x = u.get("x").getAsFloat();
            user.y = u.get("y").getAsFloat();
            user.mapHienTai = u.get("mapHienTai").getAsString();

            user.daVaoTaiKhoanLanDau = u.get("daVaoTaiKhoanLanDau").getAsBoolean();
            user.coDeTu = u.get("coDeTu").getAsBoolean();
            if ( user.coDeTu ) {
                user.deTu = getDeTu(token);
            }
            State_Management.setAuth_id(u.get("auth_id").getAsInt());

            if (u.has("danhSachVatPhamWeb")) {
                user.danhSachVatPhamWeb = new Gson().fromJson(
                    u.getAsJsonArray("danhSachVatPhamWeb"),
                    new TypeToken<List<Integer>>(){}.getType()
                );
            }

            State_Management.setUserResponse(user);
            String[] role_biBan = getProfileAuth(token);

            State_Management.getUserResponse().biBan = role_biBan[1].equals("true") ? true : false;
            State_Management.getUserResponse().role = role_biBan[0];
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DeTuTheoUser getDeTu(String token) {
        try {
            URL profileUrl = new URL(BASE_URL + "/detu/de-tu/");
            HttpURLConnection conn = (HttpURLConnection) profileUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status != 200) {
                System.err.println("Token không hợp lệ hoặc hết hạn: HTTP " + status);
                return null;
            }

            BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
            br.close();

            JsonObject root = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject u = root.getAsJsonObject("detu");

            DeTuTheoUser deTuTheoUser = new DeTuTheoUser();

            deTuTheoUser.sucManh = parseProtoLong(u.getAsJsonObject("sucManh"));

            return deTuTheoUser;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getProfileAuth(String token) {
        try {
            URL profileUrl = new URL(BASE_URL + "/auth/profile/" + State_Management.getAuth_id());
            HttpURLConnection conn = (HttpURLConnection) profileUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status != 200) {
                System.err.println("Token không hợp lệ hoặc hết hạn: HTTP " + status);
                return null;
            }

            BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
            br.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            String role = jsonObject.get("role").getAsString();
            String biBan = jsonObject.get("biBan").getAsString();

            String[] a = new String[2];
            a[0] = role;
            a[1] = biBan;

            return a;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long parseProtoLong(JsonObject obj) {
        long low = obj.get("low").getAsLong();
        long high = obj.get("high").getAsLong();
        return (high << 32) + (low & 0xffffffffL);
    }

    public static boolean saveGame(UserResponse user) {
        if (user.coDeTu) {
            saveGameDeTu(user.deTu);
        }
        try {
            Gson gson = new Gson();

            JsonObject userObj = new JsonObject();
            userObj.addProperty("vang", user.vang);
            userObj.addProperty("ngoc", user.ngoc);
            userObj.addProperty("sucManh", user.sucManh);
            userObj.addProperty("vangNapTuWeb", user.vangNapTuWeb);
            userObj.addProperty("ngocNapTuWeb", user.ngocNapTuWeb);
            userObj.addProperty("x", (int) user.x);
            userObj.addProperty("y", (int) user.y);
            userObj.addProperty("mapHienTai", user.mapHienTai);
            userObj.addProperty("daVaoTaiKhoanLanDau", user.daVaoTaiKhoanLanDau);
            userObj.addProperty("coDeTu", user.coDeTu);

            // danhSachVatPhamWeb (array)
            JsonArray arr = new JsonArray();
            for (int id : user.danhSachVatPhamWeb) {
                arr.add(id);
            }
            userObj.add("danhSachVatPhamWeb", arr);

            // ĐÓNG GÓI LẠI THEO FORMAT SERVER MUỐN
            JsonObject root = new JsonObject();
            root.add("user", userObj);

            String jsonInput = gson.toJson(root);

            URL url = new URL(BASE_URL + "/user/save-game");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            System.out.println(State_Management.getToken());
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();

            if (code == 200 || code == 201) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveGameDeTu(DeTuTheoUser deTuTheoUser) {
        try {
            Gson gson = new Gson();

            JsonObject json = new JsonObject();
            json.addProperty("sucManh", deTuTheoUser.sucManh);

            String jsonInput = gson.toJson(json);

            URL url = new URL(BASE_URL + "/detu/save-game");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            System.out.println(State_Management.getToken());
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();

            if (code == 200 || code == 201) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean taoDeTu() {
        try {
            Gson gson = new Gson();

            JsonObject json = new JsonObject();

            String jsonInput = gson.toJson(json);

            URL url = new URL(BASE_URL + "/detu/create-de-tu");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            System.out.println(State_Management.getToken());
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();

            if (code == 200 || code == 201) {
                return true;
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

    public static long[] getBalance() {
        try {
            URL url = new URL(BASE_URL + "/user/balance-web");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) sb.append(line);

                JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

                long vang = parseProtoLong(json.getAsJsonObject("vangNapTuWeb"));
                long ngoc = parseProtoLong(json.getAsJsonObject("ngocNapTuWeb"));

                return new long[] { vang, ngoc };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean useVangNapTuWeb(long amount) {
        try {
            URL url = new URL(BASE_URL + "/user/use-vang-web");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            json.addProperty("amount", amount);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) sb.append(line);

                JsonObject jsonResponse = JsonParser.parseString(sb.toString()).getAsJsonObject();

                long vang = parseProtoLong(jsonResponse.getAsJsonObject("vangNapTuWeb"));
                long ngoc = parseProtoLong(jsonResponse.getAsJsonObject("ngocNapTuWeb"));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean useNgocNapTuWeb(long amount) {
        try {
            URL url = new URL(BASE_URL + "/user/use-ngoc-web");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            json.addProperty("amount", amount);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) sb.append(line);

                JsonObject jsonResponse = JsonParser.parseString(sb.toString()).getAsJsonObject();

                long vang = parseProtoLong(jsonResponse.getAsJsonObject("vangNapTuWeb"));
                long ngoc = parseProtoLong(jsonResponse.getAsJsonObject("ngocNapTuWeb"));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ====== Gọi API lấy danh sách item web ======
    public static List<Integer> getItemsWeb(String username) {
        try {
            URL url = new URL(BASE_URL + "/user/item-web");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());

            int status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) response.append(line.trim());

                    String jsonResponse = response.toString();
//                    System.out.println("Response JSON: " + jsonResponse);

                    JsonObject obj = gson.fromJson(jsonResponse, JsonObject.class);
                    JsonArray itemsArray = obj.getAsJsonArray("itemWebs");
                    ItemWeb[] items = gson.fromJson(itemsArray, ItemWeb[].class);

                    if (items == null) {
//                        System.out.println("Deserialization failed, items is null!");
                    } else {
//                        System.out.println("Deserialization success, items length: " + items.length);
                    }

                    List<ItemWeb> list = Arrays.asList(items);
                    List<Integer> ids = new ArrayList<>();
                    for (ItemWeb item : list) {
                        ids.add(item.itemId);
//                        System.out.println(item.itemId);
                    }
                    return ids;
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
            URL url = new URL(BASE_URL + "/user/use-item-web");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json"); // chuẩn JSON
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setDoOutput(true); // để gửi body

            // JSON body thủ công
            String jsonInputString = String.format("{\"itemId\":%d}",  itemId);
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
