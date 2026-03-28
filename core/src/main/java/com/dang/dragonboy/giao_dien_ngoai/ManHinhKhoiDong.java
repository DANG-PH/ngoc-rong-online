package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.dang.dragonboy.he_thong.Main;

public class ManHinhKhoiDong implements Screen {
    private Main game;
    private Texture logogame, logoptit1, logoptit2, logochu1, logochu2, nen;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font, fontTo;
    private float thoiGian = 0f;

    // fields update
    private volatile boolean updateChecked = false;
    private volatile boolean needUpdate = false;
    private volatile boolean isDownloading = false;
    private volatile float downloadProgress = 0f;
    private String downloadUrl = null;
    private String serverVersion = null;
    private String localVersion = null;
    private static final float TIMEOUT_CHECK = 8f;

    // ── Text cycling ──
    private float textTimer = 0f;
    private int textIndex = 0;
    private float textAlpha = 1f;
    private boolean fadingOut = false;
    private static final float SHOW_TIME = 2.5f;   // giữ text bao lâu
    private static final float FADE_TIME = 0.4f;   // thời gian fade
    private static final String[] TIPS = {
        "Dragon Boy - Game nhập vai thế giới ảo",
        "Trang bị hiếm không tự đến - hãy kiên nhẫn và cày cuốc!",
        "PK sòng phẳng, chơi đẹp - đó mới là cao thủ thực sự.",
        "Chơi game quá 180 phút có thể ảnh hưởng đến sức khỏe.",
        "Khám phá hàng trăm quái vật và kỹ năng độc đáo!",
        "Giao dịch vật phẩm - cùng nhau mở ra một thế giới mới!",
        "Hãy nghỉ ngơi 10 phút sau mỗi giờ chơi.",
        "Cập nhật thường xuyên để có trải nghiệm tốt nhất.",
    };

    // Cho DEV
    private static final boolean DEBUG_DOWNLOAD_UI = false;

    public ManHinhKhoiDong(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchinh.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ ễ ằ ừ — ẳ ữ ỗ ằ ễ ỗ ừ ẵ ê : ĩ ≤";
        param.size = 18;
        font = generator.generateFont(param);
        param.size = 22;
        fontTo = generator.generateFont(param);
        generator.dispose();
    }

    @Override
    public void show() {
        logogame  = new Texture("hud/giaodienngoai/chung/logogame.png");
        logoptit1 = new Texture("hud/giaodienngoai/chung/logoptit1.png");
        logoptit2 = new Texture("hud/giaodienngoai/chung/logoptit2.png");
        logochu1  = new Texture("hud/giaodienngoai/chung/logochu1.png");
        logochu2  = new Texture("hud/giaodienngoai/chung/logochu2.png");
        nen       = new Texture("hud/giaodienngoai/chung/nen.png");
        new Thread(this::checkUpdate).start();

        if (DEBUG_DOWNLOAD_UI) {
            serverVersion = "v2.8.9";
            isDownloading = true;
            // Giả lập progress tăng dần
            new Thread(() -> {
                try {
                    while (downloadProgress < 1f) {
                        downloadProgress += 0.01f;
                        Thread.sleep(500); // tăng 1% mỗi 500ms
                    }
                } catch (InterruptedException ignored) {}
            }).start();
        }
    }

    @Override
    public void render(float delta) {
        thoiGian += delta;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // ── Vẽ logo fade-in ──────────────────────────────────────────
        float alpha = (thoiGian <= 0.8f) ? 1 - (0.8f - thoiGian) / 0.8f : 1f;
        batch.begin();
        float scaledWidth  = logogame.getWidth();
        float scaledHeight = logogame.getHeight();
        float x = (Gdx.graphics.getWidth()  - scaledWidth)  / 2f;
        float y = (Gdx.graphics.getHeight() - scaledHeight) / 2f;
        batch.setColor(1f, 1f, 1f, alpha);
        batch.draw(logogame, x, y, scaledWidth, scaledHeight);
        batch.setColor(1f, 1f, 1f, 1f);
        batch.end();

        // ── Vẽ progress bar khi đang download ────────────────────────
        if (isDownloading) {
            float barX      = 100f;
            float barY      = 50f;
            float barWidth  = Gdx.graphics.getWidth() - 200f;
            float barHeight = 20f;

            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix()); // ← FIX BUG 2
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1f);
            shapeRenderer.rect(barX, barY, barWidth, barHeight);
            shapeRenderer.setColor(0f, 0.8f, 0.2f, 1f);
            shapeRenderer.rect(barX, barY, barWidth * downloadProgress, barHeight);
            shapeRenderer.end();

            // Chuẩn bị nội dung dòng fade
            // textIndex=0 → hiện header, sau đó mới chạy tips
            String fadeText = (textIndex == 0)
                ? "Đang tải phiên bản " + serverVersion + "..."
                : TIPS[textIndex - 1];

            // Cập nhật cycling
            textTimer += delta;
            if (!fadingOut && textTimer >= SHOW_TIME) {
                fadingOut = true;
                textTimer = 0f;
            }
            if (fadingOut) {
                textAlpha = 1f - (textTimer / FADE_TIME);
                if (textTimer >= FADE_TIME) {
                    fadingOut = false;
                    textTimer = 0f;
                    textAlpha = 1f;
                    textIndex = (textIndex + 1) % (TIPS.length + 1); // +1 cho slot header
                }
            }

            batch.begin();

            // % cố định phía trên bar
            fontTo.setColor(0f, 0f, 0f, 1f);
            String pct = "DOWNLOAD NEW VERSION - HAI DANG GAME... " +(int)(downloadProgress * 100) + "%";
            GlyphLayout pctLayout = new GlyphLayout(fontTo, pct);
            fontTo.draw(batch, pct,
                (Gdx.graphics.getWidth() - pctLayout.width) / 2f, barY + 48f);

            // Dòng fade bên dưới bar
            font.setColor(0.2f, 0.2f, 0.2f, textAlpha);
            GlyphLayout fadeLayout = new GlyphLayout(font, fadeText);
            font.draw(batch, fadeText,
                (Gdx.graphics.getWidth() - fadeLayout.width) / 2f, barY - 20f);

            font.setColor(1f, 1f, 1f, 1f);
            batch.end();
        }

        // ── Chuyển màn hình: đủ 3s + không download + (đã check xong HOẶC timeout 8s) ──
        if (thoiGian > 3f && !isDownloading && !needUpdate
            && (updateChecked || thoiGian > TIMEOUT_CHECK)) { // ← FIX BUG 1
            game.setScreen(new ManHinhSplash(game));
        }

        // ── Kích hoạt download khi phát hiện có update ───────────────
        if (updateChecked && needUpdate && !isDownloading) {
            needUpdate = false;
            isDownloading = true;

            new Thread(() -> {
                try {
                    System.out.println("Downloading new version...");
                    downloadNewJar(downloadUrl);
                    startNewJar();
                    Gdx.app.exit();
                } catch (Exception e) {
                    e.printStackTrace();
                    isDownloading = false;
                }
            }).start();
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        logogame.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        logoptit1.dispose();
        logoptit2.dispose();
        logochu1.dispose();
        logochu2.dispose();
        nen.dispose();
    }

    private void checkUpdate() {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("app/version.txt");
            localVersion = java.nio.file.Files.exists(path)
                ? java.nio.file.Files.readString(path).trim()
                : "dev";

            java.net.URL url = new java.net.URL(
                "https://raw.githubusercontent.com/DANG-PH/NRO_ONLINE/master/version.json");
            java.io.BufferedReader reader =
                new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
            String json = reader.lines().collect(java.util.stream.Collectors.joining());

            org.json.JSONObject obj = new org.json.JSONObject(json);
            serverVersion = obj.getString("version");
            downloadUrl   = obj.getString("jar");

            if (!localVersion.equals("dev") && !serverVersion.equals(localVersion)) {
                needUpdate = true;
            } else {
                System.out.println("VERSION DEV - bỏ qua update");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateChecked = true;
    }

    private void downloadNewJar(String urlStr) throws Exception {
        // ── Follow redirect (GitHub Releases trả về HTTP 302) ────────
        java.net.HttpURLConnection connection =
            (java.net.HttpURLConnection) new java.net.URL(urlStr).openConnection();
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.connect();

        int fileSize = connection.getContentLength(); // -1 nếu server không trả về
        java.io.InputStream in = connection.getInputStream();
        java.nio.file.Path newJar = java.nio.file.Paths.get("app/nro_new.jar");

        // ── Tải từng chunk, cập nhật progress ────────────────────────
        try (java.io.OutputStream out = java.nio.file.Files.newOutputStream(newJar)) {
            byte[] buffer = new byte[8192];
            long downloaded = 0;
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                downloaded += bytesRead;
                if (fileSize > 0) {
                    downloadProgress = (float) downloaded / fileSize; // render() đọc liên tục
                }
            }
        }
        in.close();

        // ── Kiểm tra file tải về có đủ kích thước không ──────────────
        long downloadedSize = java.nio.file.Files.size(newJar);
        if (fileSize > 0 && downloadedSize < fileSize * 0.99) {
            // Xóa file lỗi, ném exception → catch bên render() → isDownloading = false
            java.nio.file.Files.deleteIfExists(newJar);
            throw new Exception("Download không hoàn chỉnh: " + downloadedSize + "/" + fileSize + " bytes");
        }

        downloadProgress = 1f; // đảm bảo hiện 100% trước khi thoát
    }

    private void startNewJar() throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        String jarName = "NgocRongOnline-1.0.0.jar";

        if (os.contains("win")) {
            String script =
                "@echo off\r\n" +
                    "cd /d \"%~dp0\"\r\n" +
                    "cd ..\r\n" +

                    // Kill đúng PID từ file
                    "if exist \"app\\app.pid\" (\r\n" +
                    "    set /p OLD_PID=<\"app\\app.pid\"\r\n" +
                    "    taskkill /PID %OLD_PID% /F >nul 2>&1\r\n" +
                    "    del \"app\\app.pid\"\r\n" +
                    ")\r\n" +
                    "timeout /t 2 /nobreak > nul\r\n" +

                    // Vòng lặp chờ xóa được jar cũ
                    ":waitloop\r\n" +
                    "del /f /q \"app\\" + jarName + "\" 2>nul\r\n" +
                    "if exist \"app\\" + jarName + "\" (\r\n" +
                    "    timeout /t 1 /nobreak > nul\r\n" +
                    "    goto waitloop\r\n" +
                    ")\r\n" +

                    "rename \"app\\nro_new.jar\" \"" + jarName + "\"\r\n" +
                    "(echo " + serverVersion + ")> \"app\\version.txt\"\r\n" +
                    "start \"\" runtime\\bin\\javaw -jar \"app\\" + jarName + "\"\r\n" +
                    "del \"%~f0\"\r\n";

            java.nio.file.Path scriptPath = java.nio.file.Paths.get("app/updater.bat");
            java.nio.file.Files.writeString(scriptPath, script);
            new ProcessBuilder("cmd", "/c", scriptPath.toAbsolutePath().toString()).start();

        } else {
            String script =
                "#!/bin/bash\n" +
                    "cd \"$(dirname \"$0\")/..\"\n" +

                    // Kill đúng PID từ file
                    "if [ -f \"app/app.pid\" ]; then\n" +
                    "    kill -9 $(cat app/app.pid) 2>/dev/null\n" +
                    "    rm -f app/app.pid\n" +
                    "fi\n" +
                    "sleep 1\n" +

                    // Xóa và thay thế
                    "rm -f \"app/" + jarName + "\"\n" +
                    "mv \"app/nro_new.jar\" \"app/" + jarName + "\"\n" +
                    "echo '" + serverVersion + "' > app/version.txt\n" +
                    "runtime/bin/java -jar \"app/" + jarName + "\" &\n" +
                    "rm -- \"$0\"\n";

            java.nio.file.Path scriptPath = java.nio.file.Paths.get("app/updater.sh");
            java.nio.file.Files.writeString(scriptPath, script);
            scriptPath.toFile().setExecutable(true);
            new ProcessBuilder("bash", "app/updater.sh").start();
        }
    }
}
