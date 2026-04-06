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

    private static final String CLIENT_ID    = "977963570920-h0qat6jqr0j309m1326blhmu7516g0rj.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";

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
            try {
                // Tạo nonce chống replay attack
                String nonce = generateNonce();

                // Tạo local server chờ redirect về
                CompletableFuture<String> future = new CompletableFuture<>();
                HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);

                server.createContext("/callback", exchange -> {
                    try {
                        String query = exchange.getRequestURI().getQuery();

                        if (query != null && query.contains("id_token")) {
                            // Lần 2: JS gửi lại với id_token trong query
                            Map<String, String> params = parseQuery(query);
                            String idToken = params.get("id_token");

                            String html = GameTemplates.SUCCESS_PAGE;
                            sendHtml(exchange, html);

                            if (!future.isDone()) future.complete(idToken);

                        } else {
                            // Lần 1: Google redirect về với fragment
                            String html = GameTemplates.LOADING_PAGE;
                            sendHtml(exchange, html);
                        }
                    } catch (Exception e) {
                        // Nuốt lỗi, tránh crash — connection đóng sớm là bình thường
                        System.out.println("callback exchange lỗi (bình thường): " + e.getMessage());
                    }
                });

                // có req về thì server tạo 1 thread(java) mới để xử lí
                server.setExecutor(Executors.newFixedThreadPool(4)); // tối đa 4 thread
                server.start();

                // Mở browser
                String authUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                    + "?client_id="     + URLEncoder.encode(CLIENT_ID, "UTF-8")
                    + "&redirect_uri="  + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                    + "&response_type=" + URLEncoder.encode("token id_token", "UTF-8")  // ← thêm encode
                    + "&scope="         + URLEncoder.encode("openid email profile", "UTF-8")
                    + "&nonce="         + nonce
                    + "&prompt=login";

//                không có prompt        → Google tự đăng nhập luôn nếu đã có session
//                prompt=select_account  → luôn hiện màn hình chọn tài khoản
//                prompt=login           → luôn bắt nhập lại password
//                prompt=consent         → luôn hiện màn hình xin quyền

                Desktop.getDesktop().browse(new URI(authUrl));

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
}

