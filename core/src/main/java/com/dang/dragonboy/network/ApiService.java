package com.dang.dragonboy.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.du_lieu.LocalStorage;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.he_thong.AppConfig;
import com.dang.dragonboy.network.DTO.*;
import com.dang.dragonboy.xu_ly_map.MapDataCache;
import com.dang.dragonboy.xu_ly_map.MapIdHelper;
import com.dang.dragonboy.xu_ly_map.NpcServerData;
import com.google.gson.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;

import com.google.gson.reflect.TypeToken;

public class ApiService {
    private static final String BASE_URL = AppConfig.get("api.base.url");
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

                    UserResponse user = ApiService.getProfile(access_token);
                    return user;
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

            // Prefetch trước và đưa vào cache để giải quyết bài toán delay NPC
            // Đọc comment phần thoiGian > 1f trong ManHinhSplash
            // Để ở đây thì vì verify OTP vì xử lí đc cả case login lẫn auto-login khi token lưu vào disk và user vào lại game lần sau k cần login verifyotp nữa
            if (user != null) {
                int mapId = MapIdHelper.layMapId(user.mapHienTai);
                if (mapId != 0) {
                    ApiService.layNpcCuaMap(mapId, data -> {
                        MapDataCache.getInstance().luu(mapId, data);
                    });
                }
            }
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

    public static void layNpcCuaMap(int mapId, Consumer<List<NpcServerData>> onHoanThanh) {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL +"/game-data/map/npcs?map_id=" + mapId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int status = conn.getResponseCode();
                if (status != 200) {
                    Gdx.app.error("ApiService", "layNpcCuaMap thất bại, HTTP: " + status);
                    return;
                }

                BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                );
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
                br.close();

                JsonArray npcsArray = JsonParser.parseString(response.toString())
                    .getAsJsonObject()
                    .getAsJsonArray("npcs");

                List<NpcServerData> danhSach = new ArrayList<>();
                for (var element : npcsArray) {
                    JsonObject obj = element.getAsJsonObject();
                    NpcServerData npc = new NpcServerData();
                    npc.id         = obj.get("id").getAsInt();
                    npc.ten_npc    = obj.get("ten_npc").getAsString();
                    npc.loai_npc   = obj.get("loai_npc").getAsString();
                    npc.x          = obj.get("x").getAsFloat();
                    npc.y          = obj.get("y").getAsFloat();
                    npc.is_active  = obj.get("is_active").getAsBoolean();
                    danhSach.add(npc);
                }

                Gdx.app.postRunnable(() -> onHoanThanh.accept(danhSach));

            } catch (Exception e) {
                Gdx.app.error("ApiService", "layNpcCuaMap exception", e);
            }
        }).start();
    }

    public static void layShopCuaNpc(int npcBaseId, Consumer<List<ShopItemServerData>> onHoanThanh) {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL +"/game-data/npc-shop?npc_base_id=" + npcBaseId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int status = conn.getResponseCode();
                if (status != 200) {
                    Gdx.app.error("ApiService", "layShopCuaNpc thất bại, HTTP: " + status);
                    return;
                }

                BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                );
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
                br.close();

                JsonArray itemsArray = JsonParser.parseString(response.toString())
                    .getAsJsonObject()
                    .getAsJsonArray("items");

                List<ShopItemServerData> danhSach = new ArrayList<>();
                for (var element : itemsArray) {
                    JsonObject obj = element.getAsJsonObject();
                    ShopItemServerData item = new ShopItemServerData();
                    item.id         = obj.get("id").getAsInt();
                    item.item_base_id = obj.get("item_base_id").getAsInt();
                    item.ten_item     = obj.get("ten_item").getAsString();
                    item.ma_item      = obj.get("ma_item").getAsString();
                    JsonElement giaEl = obj.get("gia");
                    item.gia = giaEl.isJsonObject()
                        ? ApiService.parseProtoLong(giaEl.getAsJsonObject())
                        : giaEl.getAsLong();
                    item.loaiTien   = obj.get("loaiTien").getAsString();
                    item.tab        = obj.get("tab").getAsString();
                    item.is_active  = obj.get("is_active").getAsBoolean();
                    danhSach.add(item);
                }

                onHoanThanh.accept(danhSach);

            } catch (Exception e) {
                Gdx.app.error("ApiService", "layShopCuaNpc exception", e);
            }
        }).start();
    }
}
