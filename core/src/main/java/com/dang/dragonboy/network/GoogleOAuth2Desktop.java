package com.dang.dragonboy.network;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.network.Templates.GameTemplates;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

public class GoogleOAuth2Desktop {

    private static final String CLIENT_ID     = "977963570920-mtgqrosdib14ulgd185mkbcgk7gi4cpf.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    public interface Callback {
        void onSuccess(String idToken);
        void onFailure(String error);
    }

    /*
    login() chạy trong Thread mới
    │
    ├─ tạo future (hộp rỗng)
    ├─ tạo HttpServer lắng nghe cổng 8888
    ├─ đăng ký xử lý /callback (chưa chạy, chỉ đăng ký)
    ├─ server.start()
    ├─ mở browser → https://accounts.google.com/...
    │
    ├─ future.get(3 phút)  ◄─── CHẶN LẠI, ngủ chờ ở đây
    │         │
    │         │            ┌─── Thread HTTP (do server tạo) ───────────────┐
    │         │            │                                                │
    │         │            │  Google redirect về /callback#id_token=ABC    │
    │         │            │  → query rỗng → vào nhánh else                │
    │         │            │  → trả HTML + JS cho browser                  │
    │         │            │                                                │
    │         │            │  JS chạy trong browser:                       │
    │         │            │    hash = "#id_token=ABC"                     │
    │         │            │    redirect → /callback?id_token=ABC          │
    │         │            │                                                │
    │         │            │  Browser gọi lại /callback?id_token=ABC       │
    │         │            │  → query có id_token → vào nhánh if           │
    │         │            │  → parseQuery → idToken = "ABC"               │
    │         │            │  → trả HTML "Đăng nhập thành công"            │
    │         │            │  → future.complete("ABC")  ← bỏ vào hộp      │
    │         │            └────────────────────────────────────────────────┘
    │         │
    │         └─── thức dậy, idToken = "ABC"
    │
    ├─ server.stop()
    ├─ callback.onSuccess(idToken) -> xong
    * */

    public static void login(Callback callback) {
        new Thread(() -> {
            HttpServer server = null;
            try {
                // Tạo nonce chống replay attack
                String nonce = generateNonce();

                // Tạo local server chờ redirect về
                CompletableFuture<String> future = new CompletableFuture<>();
                server = HttpServer.create(new InetSocketAddress(0), 0);
                int port = server.getAddress().getPort();
                String redirectUri = "http://127.0.0.1:" + port + "/callback";
                server.createContext("/callback", exchange -> {
                    try {
                        String query = exchange.getRequestURI().getQuery();
                        Map<String, String> params = parseQuery(query);
                        String code = params.get("code");
                        String error = params.get("error");

                        if (code != null) {
                            String idToken = exchangeCodeForToken(code, redirectUri);
                            sendHtml(exchange, GameTemplates.SUCCESS_PAGE);
                            if (!future.isDone()) future.complete(idToken);
                        } else {
                            // Google trả về error=access_denied khi user bấm Cancel
                            String errorMsg = error != null ? error : "Không nhận được code từ Google";
                            sendHtml(exchange, GameTemplates.ERROR_PAGE);
                            if (!future.isDone()) future.completeExceptionally(new Exception(errorMsg));
                        }
                    } catch (Exception e) {
                        System.out.println("callback lỗi: " + e.getMessage());
                    }
                });

                // có req về thì server tạo 1 thread(java) mới để xử lí
                server.setExecutor(Executors.newFixedThreadPool(4)); // tối đa 4 thread
                server.start();

                // Mở browser
                String authUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                    + "?client_id="             + URLEncoder.encode(CLIENT_ID, "UTF-8")
                    + "&redirect_uri="          + URLEncoder.encode(redirectUri, "UTF-8")
                    + "&response_type=code"     // ← đổi thành "code"
                    + "&scope="                 + URLEncoder.encode("openid email profile", "UTF-8")
                    + "&nonce="                 + nonce
                    + "&prompt=login";

//                không có prompt        → Google tự đăng nhập luôn nếu đã có session
//                prompt=select_account  → luôn hiện màn hình chọn tài khoản
//                prompt=login           → luôn bắt nhập lại password
//                prompt=consent         → luôn hiện màn hình xin quyền

                System.out.println("Desktop supported = " + Desktop.isDesktopSupported());
                System.out.println("OS = " + System.getProperty("os.name"));
                openBrowser(authUrl);

                // Chờ tối đa 3 phút
                String idToken = future.get(3, TimeUnit.MINUTES);
                server.stop(0);

                if (idToken != null) {
                    callback.onSuccess(idToken);
                } else {
                    callback.onFailure("Không lấy được id_token");
                }

            } catch (TimeoutException e) {
                callback.onFailure("Hết thời gian đăng nhập");
            } catch (Exception e) {
                callback.onFailure(e.getMessage());
            } finally {
                if (server != null) {
                    server.stop(0);
                    System.out.println("Server stopped");
                }
            }
        }).start();
    }

    /*
    exchange chứa cả request lẫn response của một kết nối cụ thể.
    Browser                          Server Java
      │                                   │
      │  GET /callback ─────────────────► │
      │                                   │  exchange = cái kết nối này
      │                                   │  exchange.getRequestURI()  → đọc request
      │                                   │  exchange.getResponseBody() → ghi response
      │  ◄──────── HTML ─────────────────  │
    exchange.getResponseBody() trả về stream ghi thẳng vào socket của browser đang chờ — server biết ghi vào đâu vì exchange đại diện cho đúng cái kết nối đó.
    Mỗi request đến thì server tạo 1 exchange riêng, nên không bao giờ nhầm giữa các browser/request khác nhau.

    exchange là đại diện cho 1 cặp request - response.
    Giống req và res trong NestJS/Express, nhưng gộp làm 1:
    typescript// NestJS - tách riêng 2 object
    handler(@Req() req, @Res() res) {
        req.query      // đọc query
        res.send(html) // gửi response
    }
    java// Java low level - gộp chung 1 object
    handler(HttpExchange exchange) {
        exchange.getRequestURI().getQuery() // đọc query
        exchange.getResponseBody()          // gửi response
    }
    * */
    private static void sendHtml(HttpExchange exchange, String html) throws Exception {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
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
