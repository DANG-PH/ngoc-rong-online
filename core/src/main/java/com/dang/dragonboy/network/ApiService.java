package com.dang.dragonboy.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.dang.dragonboy.du_lieu.LocalStorage;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.network.DTO.DeTuTheoUser;
import com.dang.dragonboy.network.DTO.ItemWeb;
import com.dang.dragonboy.network.DTO.UseItemResponse;
import com.dang.dragonboy.network.DTO.UserResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.*;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonArray;

public class ApiService {
    private static final String BASE_URL = "https://api.dangpham.id.vn";
    private static final Gson gson = new Gson();

    public static boolean healthCheck() {
        try {
            URL url = new URL(BASE_URL + "/health");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public static TrangThaiApiGetBan isNotBanned(String token) {
        try {
            URL url = new URL(BASE_URL + "/auth/ban");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);

            int status = conn.getResponseCode();

            if (status < 200 || status >= 300) {
                return TrangThaiApiGetBan.SERVER_ERROR;
            }

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonObject json = JsonParser
                .parseString(response.toString())
                .getAsJsonObject();

            if (!json.has("success") || json.get("success").isJsonNull()) {
                return TrangThaiApiGetBan.SERVER_ERROR;
            }

            return json.get("success").getAsBoolean() ? TrangThaiApiGetBan.PASS : TrangThaiApiGetBan.BAN;

        } catch (Exception e) {
            return TrangThaiApiGetBan.SERVER_ERROR;
        }
    }

    public static boolean register(String username, String password, String realname, String email, String gameName) {
        try {
            URL url = new URL(BASE_URL + "/auth/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

//            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);

            String jsonInput = String.format(
                "{\"username\":\"%s\",\"realname\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"gameName\":\"%s\"}",
                username, realname, password, email, gameName
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
            conn.setRequestProperty("x-platform", "game");
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
            conn.setRequestProperty("x-platform", "game");
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
            user.gameName = u.get("gameName").getAsString();

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
            State_Management.getUserResponse().username = role_biBan[2];

            LocalStorage.saveLastUser(State_Management.getUserResponse().username, token); // Lưu vào file JSON
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
            String username = jsonObject.get("username").getAsString();

            String[] a = new String[3];
            a[0] = role;
            a[1] = biBan;
            a[2] = username;

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
            userObj.addProperty("gameName", user.gameName);

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

    public static void taoDeTu() {
        new Thread(() -> {
            try {
                Gson gson = new Gson();
                JsonObject json = new JsonObject();
                String jsonInput = gson.toJson(json);

                URL url = new URL(BASE_URL + "/detu/create-de-tu");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
                }

                conn.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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
    public static List<Integer> getItemsWeb() {
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
    public static List<Integer> useItemWeb(List<Integer> itemIds) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + "/user/use-item-web");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + State_Management.getToken());
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);

            Gson gson = new Gson();

            // build request body
            Map<String, Object> body = new HashMap<>();
            body.put("itemIds", itemIds);

            String jsonInputString = gson.toJson(body);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();

            if (status == 200) {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {

                    UseItemResponse response = gson.fromJson(br, UseItemResponse.class);

                    if (response != null && response.successItemIds != null) {
                        return response.successItemIds;
                    }
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    StringBuilder error = new StringBuilder();
                    while ((line = br.readLine()) != null) error.append(line.trim());
                    System.err.println("Batch failed: " + error.toString());
                }
            }

        } catch (Exception e) {
            System.err.println("Exception in useItemWebBatch: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return new ArrayList<>();
    }

    public static LoginWithGoogleResponse loginWithGoogle(String idToken) {
        HttpURLConnection conn = null;
        try {
            String body = "{\"tokenFromGoogle\":\"" + idToken + "\"}";

            URL url = new URL(BASE_URL + "/auth/login-google");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("x-platform", "game");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            int status = conn.getResponseCode();

            if (status == 200 || status == 201) {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {

                    JsonObject json = JsonParser.parseReader(br).getAsJsonObject();

                    LoginWithGoogleResponse result = new LoginWithGoogleResponse();
                    result.accessToken  = json.get("access_token").getAsString();
                    result.refreshToken = json.get("refresh_token").getAsString();
                    result.authId       = json.get("auth_id").getAsInt();
                    result.register     = json.has("register") && json.get("register").getAsBoolean();
                    return result;
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder error = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) error.append(line.trim());
                }
            }

        } catch (Exception e) {
        } finally {
            if (conn != null) conn.disconnect();
        }
        return null;
    }

    public static class LoginWithGoogleResponse {
        public String accessToken;
        public String refreshToken;
        public int authId;
        public boolean register;
    }
}
