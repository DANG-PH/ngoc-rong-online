package com.dang.dragonboy.network;

import com.dang.dragonboy.he_thong.AppConfig;
import com.dang.dragonboy.network.Templates.GameTemplates;
import java.awt.Desktop;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GoogleOAuth2Desktop {
    private static final String CLIENT_ID     = AppConfig.get("google.client.id");
    private static final String CLIENT_SECRET = AppConfig.get("google.client.secret");
    public interface Callback {
        void onSuccess(String idToken);
        void onFailure(String error);
    }

    /*
    setSoTimeout(180000)
    openBrowser(authUrl)
    accept()  ←─── BLOCK tại đây chờ...
                   │
                   │  có request trong 3p
                   ▼
    // Đọc HTTP request        ← chạy tiếp
    BufferedReader reader = ...
    requestLine = reader.readLine()

    // Parse code
    ...

    // Trả HTML
    out.write(...)
    client.close()

    // Gọi callback
    callback.onSuccess(idToken)  hoặc  callback.onFailure(...)
    Còn nếu không có request trong 3p:
    accept()  ←─── chờ hết 180s
                   │
                   │  timeout!
                   ▼
    catch (SocketTimeoutException e) {
        callback.onFailure("Hết thời gian đăng nhập")  ← nhảy thẳng vào đây
    }
    * */
    public static void login(Callback callback) {
        new Thread(() -> {
            java.net.ServerSocket serverSocket = null;
            try {
                String nonce = generateNonce();

                // Dùng ServerSocket thuần thay HttpServer
                serverSocket = new java.net.ServerSocket(0); // port random
                serverSocket.setSoTimeout(180000); // 3 phút
                int port = serverSocket.getLocalPort();

                String redirectUri = "http://127.0.0.1:" + port + "/callback";
                String authUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                    + "?client_id="    + URLEncoder.encode(CLIENT_ID, "UTF-8")
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
                    + "&response_type=code"
                    + "&scope="        + URLEncoder.encode("openid email profile", "UTF-8")
                    + "&nonce="        + nonce
                    + "&prompt=login";

                openBrowser(authUrl);

                java.net.Socket client = serverSocket.accept(); // block đến khi có kết nối

                // Đọc HTTP request
                java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(client.getInputStream()));
                String requestLine = reader.readLine(); // "GET /callback?code=xxx HTTP/1.1"

                // Parse code từ request line
                String code = null;
                String error = null;
                if (requestLine != null && requestLine.contains("?")) {
                    String query = requestLine.split(" ")[1]; // "/callback?code=xxx"
                    query = query.substring(query.indexOf("?") + 1); // "code=xxx"
                    Map<String, String> params = parseQuery(query);
                    code = params.get("code");
                    error = params.get("error");
                }

                // Trả HTML về browser
                String html = code != null ? GameTemplates.SUCCESS_PAGE : GameTemplates.ERROR_PAGE;
                byte[] htmlBytes = html.getBytes(StandardCharsets.UTF_8);
                java.io.OutputStream out = client.getOutputStream();
                out.write(("HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html; charset=UTF-8\r\n"
                    + "Content-Length: " + htmlBytes.length + "\r\n"
                    + "Connection: close\r\n"
                    + "\r\n").getBytes());
                out.write(htmlBytes);
                out.flush();
                client.close();

                if (code != null) {
                    String idToken = exchangeCodeForToken(code, redirectUri);
                    callback.onSuccess(idToken);
                } else {
                    callback.onFailure(error != null ? error : "Không nhận được code");
                }

            } catch (java.net.SocketTimeoutException e) {
                callback.onFailure("Hết thời gian đăng nhập");
            } catch (Exception e) {
                callback.onFailure(e.getMessage());
            } finally {
                if (serverSocket != null) {
                    try { serverSocket.close(); } catch (Exception ignored) {}
                }
            }
        }).start();
    }

    // Tạo chuỗi ngẫu nhiên để chống replay attack.
    // Google sẽ nhét nonce này vào id_token trả về,
    // TODO: cần kiểm tra nonce trong token có khớp không sau khi nhận về.(mình kiểm tra có khớp không → đảm bảo token này do chính mình request.)
    private static String generateNonce() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    private static Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null) return map;  // null thì trả map rỗng

        for (String pair : query.split("&")) {
            // split("&") → ["id_token=ABC", "token_type=Bearer"]
            // mỗi vòng lặp xử lý 1 cặp

            String[] kv = pair.split("=", 2);
            // split("=", 2) → ["id_token", "ABC"]
            // số 2 giới hạn tách tối đa 2 phần
            // → tránh bị tách nhầm nếu value có dấu =
            // ví dụ "token=AB=CD" → ["token", "AB=CD"] -> đúng
            //                    chứ không → ["token", "AB", "CD"] -> sai

            if (kv.length == 2) {
                try {
                    map.put(kv[0], URLDecoder.decode(kv[1], "UTF-8"));
                    // URLDecoder: giải mã ký tự đặc biệt trong URL
                    // "hello%20world" → "hello world"
                    // "a%3Db"        → "a=b"
                }
                catch (Exception ignored) {}
                // nếu 1 param lỗi thì bỏ qua, không crash cả hàm
            }
        }

        return map;
        // kết quả: { "id_token" → "ABC", "token_type" → "Bearer" }
    }

    private static void openBrowser(String url) {
        try {
            System.out.println("Opening browser: " + url);

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI(url));
                    return;
                }
            }

            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec(new String[]{"open", url});
            } else {
                Runtime.getRuntime().exec(new String[]{"xdg-open", url});
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Không mở được browser. Copy link này:");
            System.out.println(url);
        }
    }

    private static String exchangeCodeForToken(String code, String redirectUri) throws Exception {
        String body = "code="          + URLEncoder.encode(code, "UTF-8")
            + "&client_id="            + URLEncoder.encode(CLIENT_ID, "UTF-8")
            + "&client_secret="          + URLEncoder.encode(CLIENT_SECRET, "UTF-8")
            + "&redirect_uri="         + URLEncoder.encode(redirectUri, "UTF-8")
            + "&grant_type=authorization_code";

        HttpURLConnection conn = (HttpURLConnection) new URL("https://oauth2.googleapis.com/token").openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));

        Scanner sc = new Scanner(conn.getInputStream(), "UTF-8");
        String response = sc.useDelimiter("\\A").next();
        sc.close();

        // Parse id_token từ JSON response
        for (String part : response.replace("{", "").replace("}", "").split(",")) {
            part = part.trim();
            if (part.startsWith("\"id_token\"")) {
                return part.split(":", 2)[1].replace("\"", "").trim();
            }
        }
        throw new Exception("Không tìm thấy id_token. Response: " + response);
    }
}

/*
 * ============================================================
 * FLOW THAY ĐỔI: Implicit → Authorization Code
 * ============================================================
 *
 * FLOW CŨ (Implicit Flow — response_type=token id_token):
 *   - Google redirect về /callback#id_token=ABC (fragment)
 *   - Server Java KHÔNG đọc được fragment vì browser không gửi # lên server
 *   - Phải dùng trick: trả HTML + JS về browser
 *   - JS đọc fragment rồi redirect lại /callback?id_token=ABC (query)
 *   - Server mới đọc được id_token
 *   - Vấn đề 1: Google đã tắt Implicit Flow cho Desktop App → lỗi unsupported_response_type
 *   - Vấn đề 2: nếu cố áp dụng follow đó thì server java phải lắng nghe 1 port
 *               -> xuất hiện 2 vấn đề conflict nhau
 *               -> nếu server chỉ bind 1 port (ví dụ 8888) thì logic chạy ổn nhưng nếu muốn scale lên multi cùng lúc hoặc port đang bị chặn,...
 *               -> nếu server bind port mà os cấp random thì lúc này gg cloud platform cần add 65k port(điều này gần như là không thể)
 *
 * FLOW MỚI (Authorization Code Flow — response_type=code):
 *   - Google redirect về /callback?code=ABC (query, không phải fragment)
 *   - Server Java đọc code trực tiếp từ query → không cần JS trick nữa
 *   - Server gọi POST https://oauth2.googleapis.com/token với:
 *       code, client_id, client_secret, redirect_uri, grant_type=authorization_code
 *   - Google trả về JSON: { "id_token": "...", "access_token": "..." }
 *   - Parse id_token từ JSON → hoàn tất
 *   - LOADING_PAGE không còn cần thiết nữa
 *
 * ============================================================
 * exchangeCodeForToken(String code, String redirectUri)
 * ============================================================
 *
 *   Đổi authorization code (dùng 1 lần, hết hạn nhanh) lấy id_token thật.
 *
 *   Input:
 *     - code       : code Google trả về trong query /callback?code=ABC
 *     - redirectUri: phải khớp chính xác với redirect_uri đã gửi lúc mở browser
 *                    (Google dùng để xác minh, không phải để redirect thêm lần nữa)
 *
 *   POST body gửi lên https://oauth2.googleapis.com/token:
 *     - code             : authorization code
 *     - client_id        : định danh app
 *     - client_secret    : Desktop App vẫn cần secret (khác với PKCE flow)
 *     - redirect_uri     : phải khớp với lúc request
 *     - grant_type       : cố định là "authorization_code"
 *
 *   Response JSON:
 *     { "access_token": "...", "id_token": "...", "expires_in": 3599, ... }
 *
 *   Parse thủ công (không dùng thư viện JSON) → tìm field "id_token" → trả về.
 *
 * ============================================================
 * LƯU Ý BẢO MẬT
 * ============================================================
 *
 *   - client_secret nằm trong code → chỉ an toàn cho desktop app (người dùng tin tưởng)
 *   - Về lý thuyết user có thể decompile và lấy secret → nhưng đây là limitation
 *     của Desktop OAuth, Google chấp nhận rủi ro này (documented)
 *   - TODO: kiểm tra nonce trong id_token có khớp với nonce đã gửi không
 *           → đảm bảo token do chính request này tạo ra, không phải replay attack
 */
