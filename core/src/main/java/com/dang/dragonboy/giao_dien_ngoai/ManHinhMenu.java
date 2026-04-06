package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.dang.dragonboy.du_lieu.LocalStorage;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.giao_dien_trong.ManHinhDoiHoaCuc;
import com.dang.dragonboy.giao_dien_trong.ManHinhLangAru;
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.giao_dien_trong.ManHinhNhaGohan;
import com.dang.dragonboy.network.ApiService;
import com.dang.dragonboy.network.DTO.UserResponse;
import com.dang.dragonboy.network.TrangThaiApiGetBan;
import com.dang.dragonboy.websocket.GameSocket;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

enum TrangThaiManHinhMenu {
    NONE,
    BAN,
    SERVER_ERROR,
    FORCE_LOGOUT
}

public class ManHinhMenu implements Screen {
    private TrangThaiManHinhMenu trangThaiManHinh = TrangThaiManHinhMenu.NONE;
    private Main game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Texture anhThongBao;
    private float timeClickNutThongBao = 0f;
    private Texture sky, nuixa, nui, nuicay, nuithap;
    private Texture logo, nutdn, nutclick, nutdn1, nutclick1;
    private BitmapFont font, fontSplash, fontThuong;
    private GlyphLayout layout;

    private float scrollX_cay = 0;
    private float scrollX_thap = 0;
    private boolean[] nutDangDuocBam = new boolean[4];
    private float nutClickTimer = 0f;
    private int nutDuocChon = -1;

    private Boolean serverOnline = null; // null = chưa check

    private Object mayChu; // biến lưu máy chủ đc chọn
    public ManHinhMenu(Main game ,Object mayChu, boolean isForceLogout) {
        this.game = game;
        this.mayChu = (mayChu != null) ? mayChu : 1; // Nếu null thì mặc định là 1
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        layout = new GlyphLayout();

        if (isForceLogout) {
            this.trangThaiManHinh = TrangThaiManHinhMenu.FORCE_LOGOUT;
        }
    }

    @Override
    public void render(float delta) {
        if (serverOnline != null && !serverOnline && trangThaiManHinh != TrangThaiManHinhMenu.SERVER_ERROR) trangThaiManHinh = TrangThaiManHinhMenu.SERVER_ERROR;
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (trangThaiManHinh == TrangThaiManHinhMenu.NONE) {
                int[] ys = {320, 255, 190, 125};

                int soNut = ys.length;

                if (State_Management.getUserResponse() == null) soNut = 3;

                for (int i = 0; i < soNut; i++) {
                    int btnX = 352, btnY = ys[i], btnWidth = 325, btnHeight = 50;
                    if (mouseX >= btnX && mouseX <= btnX + btnWidth &&
                        mouseY >= btnY && mouseY <= btnY + btnHeight) {
                        nutDangDuocBam[i] = true;
                        nutClickTimer = 0.1f;
                        nutDuocChon = i;
                    }
                }
            } else if (trangThaiManHinh == TrangThaiManHinhMenu.BAN) {
                float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
                float nutY = 70;
                if (mouseX >= nutX && mouseX <= nutX + 140 &&
                    mouseY >= nutY && mouseY <= nutY + 50) {
                    timeClickNutThongBao = 0.1f;
                }
            } else if (trangThaiManHinh == TrangThaiManHinhMenu.SERVER_ERROR) {
                float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
                float nutY = 70;
                if (mouseX >= nutX && mouseX <= nutX + 140 &&
                    mouseY >= nutY && mouseY <= nutY + 50) {
                    timeClickNutThongBao = 0.1f;
                }
            } else if (trangThaiManHinh == TrangThaiManHinhMenu.FORCE_LOGOUT) {
                float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
                float nutY = 70;
                if (mouseX >= nutX && mouseX <= nutX + 140 &&
                    mouseY >= nutY && mouseY <= nutY + 50) {
                    timeClickNutThongBao = 0.1f;
                }
            }
        }

        if (timeClickNutThongBao > 0) {
            timeClickNutThongBao -= delta;
            if (timeClickNutThongBao <= 0) {
                if (trangThaiManHinh == TrangThaiManHinhMenu.SERVER_ERROR) {
                    serverOnline = true;
                }
                trangThaiManHinh = TrangThaiManHinhMenu.NONE;
            }
        }

        if (nutClickTimer > 0) {
            nutClickTimer -= delta;
            if (nutClickTimer <= 0) {
                for (int i = 0; i < nutDangDuocBam.length; i++) {
                    nutDangDuocBam[i] = false;
                }
            }
        }
        if (nutClickTimer <= 0 && nutDuocChon != -1) {
            Screen nextScreen = null;
            String token = State_Management.getToken();
            UserResponse user = State_Management.getUserResponse();
            if (user != null) {
                switch (nutDuocChon) {
                    case 0:
                        TrangThaiApiGetBan trangThai = ApiService.isNotBanned(token);
                        if (trangThai == TrangThaiApiGetBan.PASS) {
                            if (!user.daVaoTaiKhoanLanDau) {
                                nextScreen = new ManHinhSplash(game, new ManHinhNhaGohan(game, "admin", "traidat", "Goku", null));
                            } else {
                                if (user.mapHienTai.equals("Nhà Gôhan")) {
                                    nextScreen = new ManHinhSplash(game, new ManHinhNhaGohan(game, "admin", "traidat", "Goku", null));
                                } else if (user.mapHienTai.equals("Làng Aru")) {
                                    nextScreen = (new ManHinhSplash(game, new ManHinhLangAru(game, null)));
                                } else if (user.mapHienTai.equals("Đồi Hoa Cúc")) {
                                    nextScreen = (new ManHinhSplash(game, new ManHinhDoiHoaCuc(game, null)));
                                }
                            }
                            if (!GameSocket.isConnected()) {
                                new Thread(() -> {
                                    try {
                                        URL url = new URL("https://api.dangpham.id.vn/game/play");
                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                        conn.setRequestMethod("POST");
                                        conn.setRequestProperty("Authorization", "Bearer " + token);
                                        conn.setRequestProperty("Content-Type", "application/json");
                                        conn.setDoOutput(true);

                                        int responseCode = conn.getResponseCode();

                                        if (responseCode == 200 || responseCode == 201) {
                                            Scanner scanner = new Scanner(conn.getInputStream());
                                            StringBuilder responseBody = new StringBuilder();
                                            while (scanner.hasNextLine())
                                                responseBody.append(scanner.nextLine());
                                            scanner.close();

                                            JSONObject json = new JSONObject(responseBody.toString());
                                            String gameSessionId = json.getString("gameSessionId");
                                            State_Management.gameSessionId = gameSessionId;

                                            conn.disconnect();
                                            GameSocket.connect(token);
                                        } else {
                                            System.out.println("/play thất bại: " + responseCode);
                                            conn.disconnect();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }).start();
                            }
                        } else if (trangThai == TrangThaiApiGetBan.BAN) {
                            trangThaiManHinh = TrangThaiManHinhMenu.BAN;
                        } else if (trangThai == TrangThaiApiGetBan.SERVER_ERROR) {
                            serverOnline = false;
                        }
                        break;
                    case 1:
                        nextScreen = new ManHinhChoiMoi(game);
                        break;
                    case 2:
                        nextScreen = new ManHinhDoiTaiKhoan(game);
                        break;
                    case 3:
                        nextScreen = new ManHinhChonMayChu(game);
                        break;
                }
                nutDuocChon = -1;
                if (nextScreen != null) {
                    game.setScreen(new ManHinhSplash(game, nextScreen));
                }
            } else {
                switch (nutDuocChon) {
                    case 0:
                        nextScreen = new ManHinhChoiMoi(game);
                        break;
                    case 1:
                        nextScreen = new ManHinhDoiTaiKhoan(game);
                        break;
                    case 2:
                        nextScreen = new ManHinhChonMayChu(game);
                        break;
                }
                nutDuocChon = -1;
                if (nextScreen != null) {
                    game.setScreen(new ManHinhSplash(game, nextScreen));
                }
            }
        }
        scrollX_cay -= 30 * delta;
        scrollX_thap -= 60 * delta;
        if (scrollX_cay <= -340) scrollX_cay += 340;
        if (scrollX_thap <= -340) scrollX_thap += 340;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(25 / 255f, 176 / 255f, 248 / 255f, 1);
        shapeRenderer.rect(0, 460, 1020, 150);
        shapeRenderer.end();

        batch.begin();
        for (int i = 0; i < 4; i++) {
            batch.draw(sky, i * 255, 310, 255, 150);
            batch.draw(nuixa, i * 255, 310, 255, 150);
        }
        for (int i = 0; i < 2; i++) {
            batch.draw(nui, i * 510, 280, 510, 170);
        }
        for (int i = 0; i < 4; i++) {
            batch.draw(nuicay, scrollX_cay + i * 340, 140, 340, 220);
        }
        for (int i = 0; i < 4; i++) {
            batch.draw(nuithap, scrollX_thap + i * 340, 0, 340, 280);
        }

        if (trangThaiManHinh == TrangThaiManHinhMenu.NONE) {
            batch.draw(logo, 355, 360, 320, 200);
            String[] labels;
            if (State_Management.getUserResponse() != null) {
                String username = new String(
                    Base64.getDecoder().decode(State_Management.getSessionId()),
                    StandardCharsets.UTF_8
                );
                State_Management.getUserResponse().username = username;
                labels = new String[]{
                    "TK. " + username,
                    "Chơi mới",
                    "Đổi tài khoản",
                    "Máy chủ: Vũ trụ " + mayChu
                };
                if ("HAIDANG1".equals(mayChu)) {
                    labels[2] = "Máy chủ: HAIDANG1";
                }
                int[] ys = {320, 255, 190, 125};
                int[] textYs = {350, 284, 219, 154};

                font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                for (int i = 0; i < labels.length; i++) {
                    Texture tex = nutDangDuocBam[i] ? nutclick : nutdn;
                    batch.draw(tex, 352, ys[i], 325, 50);
                    layout.setText(font, labels[i]);
                    float textX = 352 + (325 - layout.width) / 2;
                    font.draw(batch, layout, textX, textYs[i]);
                }
            } else {
                labels = new String[]{
                    "Chơi mới",
                    "Đổi tài khoản",
                    "Máy chủ: Vũ trụ " + mayChu
                };

                if ("HAIDANG1".equals(mayChu)) {
                    labels[3] = "Máy chủ: HAIDANG1";
                }
                int[] ys = {320, 255, 190};
                int[] textYs = {350, 284, 219};

                font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                for (int i = 0; i < labels.length; i++) {
                    Texture tex = nutDangDuocBam[i] ? nutclick : nutdn;
                    batch.draw(tex, 352, ys[i], 325, 50);
                    layout.setText(font, labels[i]);
                    float textX = 352 + (325 - layout.width) / 2;
                    font.draw(batch, layout, textX, textYs[i]);
                }
            }
        } else if (trangThaiManHinh == TrangThaiManHinhMenu.BAN) {
            batch.draw(anhThongBao, (Gdx.graphics.getWidth() - 740) / 2f, 85, 740, 168);
            layout.setText(font, "Tài khoản đã bị khóa, vui lòng liên hệ admin để được hỗ trợ");
            font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, 180);
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 70;
            batch.draw(timeClickNutThongBao > 0 ? nutclick1 : nutdn1, nutX, nutY, 140, 50);
            layout.setText(font, "OK");
            font.draw(batch, layout, nutX + (140 - layout.width) / 2f, nutY + 30);
        } else if (trangThaiManHinh == TrangThaiManHinhMenu.SERVER_ERROR) {
            batch.draw(anhThongBao, (Gdx.graphics.getWidth() - 740) / 2f, 85, 740, 168);
            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            layout.setText(font, "Máy chủ mất kết nối, vui lòng thử lại sau");
            font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, 180);
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 70;
            batch.draw(timeClickNutThongBao > 0 ? nutclick1 : nutdn1, nutX, nutY, 140, 50);
            layout.setText(font, "OK");
            font.draw(batch, layout, nutX + (140 - layout.width) / 2f, nutY + 30);
        } else if (trangThaiManHinh == TrangThaiManHinhMenu.FORCE_LOGOUT) {  // THÊM
            batch.draw(anhThongBao, (Gdx.graphics.getWidth() - 740) / 2f, 85, 740, 168);
            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            layout.setText(font, State_Management.getForceLogoutMessage());
            font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, 180);
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 70;
            batch.draw(timeClickNutThongBao > 0 ? nutclick1 : nutdn1, nutX, nutY, 140, 50);
            layout.setText(font, "OK");
            font.draw(batch, layout, nutX + (140 - layout.width) / 2f, nutY + 30);
        }

        layout.setText(fontSplash, "Xóa dữ liệu");
        fontSplash.setColor(1, 1, 1, 1);
        fontSplash.draw(batch, layout, Gdx.graphics.getWidth() - layout.width - 10, 30);

        layout.setText(fontThuong, "nronline.vercel.app");
        fontThuong.setColor(1, 1, 1, 1);
        fontThuong.draw(batch, layout, Gdx.graphics.getWidth() - layout.width - 10, 600);

        layout.setText(fontThuong, "V0.0.0");
        fontThuong.draw(batch, layout, Gdx.graphics.getWidth() - layout.width - 10, 580);
        batch.end();
    }

    @Override public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        sky.dispose(); nuixa.dispose(); nui.dispose();
        nuicay.dispose(); nuithap.dispose(); logo.dispose();
        nutdn.dispose(); nutclick.dispose();
        font.dispose(); fontSplash.dispose(); fontThuong.dispose();
        anhThongBao.dispose();
    }

    @Override public void show() {
        sky = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "sky_" + "traidat" + ".png");
        nuixa = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuixa_" + "traidat" + ".png");
        nui = new Texture("hud/giaodienngoai/" + "traidat" + "/" + "nui_" + "traidat" + ".png");
        nuicay = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuicay_" + "traidat" + ".png");
        nuithap = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuithap_" + "traidat" + ".png");

        logo = new Texture("hud/giaodienngoai/chung/chuberong.png");
        nutdn = new Texture("hud/giaodienngoai/chung/nutdangnhap2.png");
        nutclick = new Texture("hud/giaodienngoai/chung/nutclick.png");

        nutdn1 = new Texture("hud/giaodienngoai/chung/nutdangnhap3.png");
        nutclick1 = new Texture("hud/giaodienngoai/chung/nutclick2.png");

        anhThongBao = new Texture("hud/giaodienngoai/chung/khungthongbao.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?., Xóa dữ liệu Đă ậ ở ớ ổ à ả á ế ủ vũ trụ ơ : ị ò ê đ ư ợ ỗ ợ ể ã máy chủ mất kết nối vui lòng thử lại sau tài khoản được đăng nhập tại nơi khác kết nối bị gián đoạn quay về màn hình chính";
        font = generator.generateFont(parameter);
        parameter.size = 14;
        fontSplash = generator.generateFont(parameter);
        generator.dispose();

        FreeTypeFontGenerator genThuong = new FreeTypeFontGenerator(Gdx.files.internal("font/fontthuong.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramThuong = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramThuong.size = 25;
        paramThuong.characters = parameter.characters;
        fontThuong = genThuong.generateFont(paramThuong);
        genThuong.dispose();
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
