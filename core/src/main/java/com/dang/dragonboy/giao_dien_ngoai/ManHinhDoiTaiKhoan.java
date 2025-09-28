package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.InputAdapter;

import com.dang.dragonboy.nhan_vat.*;
import com.dang.dragonboy.du_lieu.*;
import com.dang.dragonboy.hien_thi.*;

import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.network.*;
import com.google.gson.Gson;

enum TrangThaiManHinh {
    NONE,
    DANGKY,
    THONGBAO,
}

public class ManHinhDoiTaiKhoan implements Screen {
    private TrangThaiManHinh trangThaiManHinh = TrangThaiManHinh.NONE;
    private Main game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font, fontText, fontThuong;
    private GlyphLayout layout;
    private Texture sky, nuixa, nui, nuicay, nuithap;
    private Texture logo, nutdn, nutclick;
    private Texture khungdangnhap;

    private float scrollX_cay = 0;
    private float scrollX_thap = 0;

    private boolean isOkPressed = false;
    private boolean isDongPressed = false;
    private boolean isQuenMKPressed = false;
    private boolean isDangKyPressed = false;

    private float thoiGianHienNutClick = 0;
    private boolean chuyenManHinhOK = false;
    private boolean chuyenManHinhDong = false;

    private Texture anhThongBao;
    private boolean isThongBaoOKPressed = false;

    private String tenTaiKhoanDky = "";
    private String matKhauDky = "";
    private boolean oNhapTaiKhoanDuocChonDky = false;
    private boolean oNhapMatKhauDuocChonDky = false;

    private String tenTaiKhoan = "";
    private String matKhau = "";
    private boolean oNhapTaiKhoanDuocChon = false;
    private boolean oNhapMatKhauDuocChon = false;

    private int nutManHinhDangKyChon = -1;

    public ManHinhDoiTaiKhoan(Main game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        layout = new GlyphLayout();
    }

    @Override public void show() {
        sky = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "sky_" + "traidat" + ".png");
        nuixa = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuixa_" + "traidat" + ".png");
        nui = new Texture("hud/giaodienngoai/" + "traidat" + "/" + "nui_" + "traidat" + ".png");
        nuicay = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuicay_" + "traidat" + ".png");
        nuithap = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuithap_" + "traidat" + ".png");

        logo = new Texture("hud/giaodienngoai/chung/chuberong.png");
        nutdn = new Texture("hud/giaodienngoai/chung/nutdangnhap3.png");
        nutclick = new Texture("hud/giaodienngoai/chung/nutclick2.png");
        khungdangnhap = new Texture("hud/giaodienngoai/chung/khungdangnhap.png");
        anhThongBao = new Texture("hud/giaodienngoai/chung/khungthongbao.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ẩ ể http://";
        param.size = 18;
        font = generator.generateFont(param);
        param.size = 17;
        fontText = generator.generateFont(param);
        generator.dispose();

        FreeTypeFontGenerator genThuong = new FreeTypeFontGenerator(Gdx.files.internal("font/fontthuong.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramThuong = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramThuong.size = 25;
        paramThuong.characters = param.characters;
        fontThuong = genThuong.generateFont(paramThuong);
        genThuong.dispose();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                    if (oNhapTaiKhoanDuocChon) {
                        if (character == '\b') {
                            if (!tenTaiKhoan.isEmpty()) {
                                tenTaiKhoan = tenTaiKhoan.substring(0, tenTaiKhoan.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9]")) {
                            if (tenTaiKhoan.length() < 20) {
                                tenTaiKhoan += character;
                            }
                        }
                    } else if (oNhapMatKhauDuocChon) {
                        if (character == '\b') {
                            if (!matKhau.isEmpty()) {
                                matKhau = matKhau.substring(0, matKhau.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9]")) {
                            if (matKhau.length() < 20) {
                                matKhau += character;
                            }
                        }
                    }
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY) {
                    if (oNhapTaiKhoanDuocChonDky) {
                        if (character == '\b') {
                            if (!tenTaiKhoanDky.isEmpty()) {
                                tenTaiKhoanDky = tenTaiKhoanDky.substring(0, tenTaiKhoanDky.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9]")) {
                            if (tenTaiKhoanDky.length() < 20) {
                                tenTaiKhoanDky += character;
                            }
                        }
                    } else if (oNhapMatKhauDuocChonDky) {
                        if (character == '\b') {
                            if (!matKhauDky.isEmpty()) {
                                matKhauDky = matKhauDky.substring(0, matKhauDky.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9]")) {
                            if (matKhauDky.length() < 20) {
                                matKhauDky += character;
                            }
                        }
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        thoiGianHienNutClick -= delta;
        if (thoiGianHienNutClick <= 0) {
            if (trangThaiManHinh == TrangThaiManHinh.NONE) {
//                if (chuyenManHinhOK) {
//                    // check api đăng nhập
//                    // 2 biến cần check trong sql là tenTaiKhoan và matKhau
//                    UserResponse user = ApiService.login(tenTaiKhoan, matKhau);
//                    if (user != null) {
//                        System.out.println("Đăng nhập thành công!");
//                        // Truy cập dữ liệu backend trả về
//
//                        State_Management.setUserResponse(user);
//                        System.out.println("x: "+State_Management.getUserResponse().x+", y: "+State_Management.getUserResponse().y);
//                        game.setScreen(new ManHinhMenu(game, null));
//                    } else {
//                        System.out.println("Đăng nhập thất bại!");
//                    }
//                    chuyenManHinhOK = false;
//                }
                if (chuyenManHinhOK) {
                    chuyenManHinhOK = false;

                    new Thread(() -> {
                        // chạy ở thread phụ
                        UserResponse user = ApiService.login(tenTaiKhoan, matKhau);

                        // gửi nhiệm vụ về main thread
                        Gdx.app.postRunnable(() -> {
                            // chỗ này chạy ở main thread
                            if (user != null) {
                                System.out.println("Đăng nhập thành công!");
                                State_Management.setUserResponse(user);
                                game.setScreen(new ManHinhMenu(game, null));
                            } else {
                                System.out.println("Đăng nhập thất bại!");
                            }
                        });
                    }).start();
                }
                else if (chuyenManHinhDong) {
                    game.setScreen(new ManHinhMenu(game, null));
                    chuyenManHinhDong = false;
                } else if (isQuenMKPressed) {
                    trangThaiManHinh = TrangThaiManHinh.THONGBAO;
                } else if (isDangKyPressed) {
                    trangThaiManHinh = TrangThaiManHinh.DANGKY;
                }
            }

            if (trangThaiManHinh == TrangThaiManHinh.DANGKY) {
                if (nutManHinhDangKyChon == 1) {
                    trangThaiManHinh = TrangThaiManHinh.NONE;
                    nutManHinhDangKyChon = -1;
                }
                if (nutManHinhDangKyChon == 0) {
//                    //call api
//                    boolean ok = ApiService.register(tenTaiKhoanDky, matKhauDky);
//                    if (ok) {
//                        System.out.println("Đăng ký thành công!");
//                    } else {
//                        System.out.println("Đăng ký thất bại!");
//                    }
                    nutManHinhDangKyChon = -1;

                    new Thread(() -> {
                        boolean ok = ApiService.register(tenTaiKhoanDky, matKhauDky);

                        Gdx.app.postRunnable(() -> {
                            if (ok) {
                                System.out.println("Đăng ký thành công!");
                            } else {
                                System.out.println("Đăng ký thất bại!");
                            }
                        });
                    }).start();
                }
            }

            if (trangThaiManHinh == TrangThaiManHinh.THONGBAO) {
                if (isThongBaoOKPressed) {
                    trangThaiManHinh = TrangThaiManHinh.NONE;
                    isThongBaoOKPressed = false;
                }
            }

            isOkPressed = false;
            isDongPressed = false;
            isQuenMKPressed = false;
            isDangKyPressed = false;
        }

        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            float oX = (Gdx.graphics.getWidth() - 320) / 2f ;
            float oY1 = 272;
            float oY2 = 196;

            if (mouseX >= oX && mouseX <= oX + 340 && mouseY >= oY1 && mouseY <= oY1 + 40) {
                if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                    oNhapTaiKhoanDuocChon = true;
                    oNhapMatKhauDuocChon = false;
                } else {
                    oNhapTaiKhoanDuocChonDky = true;
                    oNhapMatKhauDuocChonDky = false;
                }
            } else if (mouseX >= oX && mouseX <= oX + 340 && mouseY >= oY2 && mouseY <= oY2 + 40) {
                if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                    oNhapTaiKhoanDuocChon = false;
                    oNhapMatKhauDuocChon = true;
                } else {
                    oNhapTaiKhoanDuocChonDky = false;
                    oNhapMatKhauDuocChonDky = true;
                }
            } else {
                if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                    oNhapTaiKhoanDuocChon = false;
                    oNhapMatKhauDuocChon = false;
                } else {
                    oNhapTaiKhoanDuocChonDky = false;
                    oNhapMatKhauDuocChonDky = false;
                }
            }
            if (trangThaiManHinh == TrangThaiManHinh.THONGBAO) {
                float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
                float nutY = 70;
                if (mouseX >= nutX && mouseX <= nutX + 140 &&
                    mouseY >= nutY && mouseY <= nutY + 50) {
                    isThongBaoOKPressed = true;
                    thoiGianHienNutClick = 0.1f;
                }
                return;
            }

            float kdnW = 365;
            float kdnH = 210;
            float okX = (Gdx.graphics.getWidth() - kdnW) / 2f + 18;
            float okY = kdnH - 110;
            float quenX = (Gdx.graphics.getWidth() - kdnW) / 2f + 200;
            float quenY = kdnH - 110;

            if (mouseX >= 20 && mouseX <= 155 && mouseY >= 20 && mouseY <= 70) {
                isDongPressed = true;
                thoiGianHienNutClick = 0.1f;
                chuyenManHinhDong = true;
            }

            if (mouseX >= okX && mouseX <= okX + 142 && mouseY >= okY && mouseY <= okY + 48) {
                if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                    isOkPressed = true;
                    thoiGianHienNutClick = 0.1f;
                    chuyenManHinhOK = true;
                }
                if (trangThaiManHinh == TrangThaiManHinh.DANGKY) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 0;
                }
            }

            if (mouseX >= quenX && mouseX <= quenX + 142 && mouseY >= quenY && mouseY <= quenY + 48) {
                if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                    isQuenMKPressed = true;
                    thoiGianHienNutClick = 0.1f;
                }
                if (trangThaiManHinh == TrangThaiManHinh.DANGKY) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 1;
                }
            }

            if (mouseX >= 1020-20-135 && mouseX <= 1020-20 && mouseY >= 20 && mouseY <= 70) {
                isDangKyPressed = true;
                thoiGianHienNutClick = 0.1f;
            }
        }

        scrollX_cay -= 30 * delta;
        scrollX_thap -= 60 * delta;
        if (scrollX_cay <= -340) scrollX_cay += 340;
        if (scrollX_thap <= -340) scrollX_thap += 340;

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
        batch.end();

        if (trangThaiManHinh == TrangThaiManHinh.THONGBAO) {
            batch.begin();
            batch.draw(anhThongBao, (Gdx.graphics.getWidth() - 740) / 2f, 85, 740, 168);
            layout.setText(font, "Để lấy lại mật khẩu xin vui lòng truy cập website https://chienbinhrongthieng.online");
            font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, 180);
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 70;
            batch.draw(isThongBaoOKPressed ? nutclick : nutdn, nutX, nutY, 140, 50);
            layout.setText(font, "OK");
            font.draw(batch, layout, nutX + (140 - layout.width) / 2f, nutY + 30);
            batch.end();
        }

        if (trangThaiManHinh == TrangThaiManHinh.NONE) {

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(108f / 255f, 74f / 255f, 0f, 1f);
            shapeRenderer.rect((Gdx.graphics.getWidth() - 369) / 2f, 214 - 49, 369, 214);
            shapeRenderer.end();

            batch.begin();
            float kdnW = 365;
            float kdnH = 210;
            batch.draw(khungdangnhap, (Gdx.graphics.getWidth() - kdnW) / 2f, kdnH - 43, kdnW, kdnH);
            // Hiển thị ô tên tài khoản
            float oX = (Gdx.graphics.getWidth() - 320) / 2f;
            float oY1 = 272;
            float oY2 = 196;
            if (tenTaiKhoan.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
                layout.setText(fontText, "Tên tài khoản");
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1); // nâu
                layout.setText(fontText, tenTaiKhoan);
            }
            fontText.draw(batch, layout, oX + 10, oY1 + 25);

            // Hiển thị ô mật khẩu (ẩn bằng ***)
            if (matKhau.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f); // xám
                layout.setText(fontText, "Mật khẩu");
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1); // nâu
                layout.setText(fontText, "*".repeat(matKhau.length()));
            }
            fontText.draw(batch, layout, oX + 10, oY2 + 25);

            batch.draw(logo, 355, 325, 320, 210);

            layout.setText(fontThuong, "chienbinhrongthieng.online");
            fontThuong.setColor(1, 1, 1, 1);
            fontThuong.draw(batch, layout, Gdx.graphics.getWidth() - layout.width - 10, 600);
            layout.setText(fontThuong, "V0.0.0");
            fontThuong.draw(batch, layout, Gdx.graphics.getWidth() - layout.width - 10, 580);

            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            float nutOkX = (Gdx.graphics.getWidth() - kdnW) / 2f + 18;
            float nutOkY = kdnH - 110;
            float nutQuenX = (Gdx.graphics.getWidth() - kdnW) / 2f + 200;
            float nutQuenY = kdnH - 110;
            int nutDongX = 20;
            int nutDongY = 20;

            batch.draw(isOkPressed ? nutclick : nutdn, nutOkX, nutOkY, 142, 48);
            layout.setText(font, "OK");
            font.draw(batch, layout, nutOkX + (142 - layout.width) / 2, nutOkY + 30);

            batch.draw(isDongPressed ? nutclick : nutdn, nutDongX, nutDongY, 135, 50);
            layout.setText(font, "Đóng");
            font.draw(batch, layout, nutDongX + (135 - layout.width) / 2, nutDongY + 30);

            batch.draw(isDangKyPressed ? nutclick : nutdn, 1020 - nutDongX - 135, nutDongY, 135, 50);
            layout.setText(font, "Đăng ký");
            font.draw(batch, layout, 1020 - nutDongX - 135 + (135 - layout.width) / 2, nutDongY + 30);

            batch.draw(isQuenMKPressed ? nutclick : nutdn, nutQuenX, nutQuenY, 142, 48);
            layout.setText(font, "Quên M.Khẩu");
            font.draw(batch, layout, nutQuenX + (142 - layout.width) / 2, nutQuenY + 30);

            batch.end();
        }

        if (trangThaiManHinh == TrangThaiManHinh.DANGKY) {

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(108f / 255f, 74f / 255f, 0f, 1f);
            shapeRenderer.rect((Gdx.graphics.getWidth() - 369) / 2f, 214 - 49, 369, 214);
            shapeRenderer.end();

            batch.begin();
            float kdnW = 365;
            float kdnH = 210;
            batch.draw(khungdangnhap, (Gdx.graphics.getWidth() - kdnW) / 2f, kdnH - 43, kdnW, kdnH);
            // Hiển thị ô tên tài khoản
            float oX = (Gdx.graphics.getWidth() - 320) / 2f;
            float oY1 = 272;
            float oY2 = 196;
            if (tenTaiKhoanDky.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
                layout.setText(fontText, "Tên tài khoản đăng ký");
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1); // nâu
                layout.setText(fontText, tenTaiKhoanDky);
            }
            fontText.draw(batch, layout, oX + 10, oY1 + 25);

            // Hiển thị ô mật khẩu (ẩn bằng ***)
            if (matKhauDky.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f); // xám
                layout.setText(fontText, "Mật khẩu đăng ký");
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1); // nâu
                layout.setText(fontText, "*".repeat(matKhauDky.length()));
            }
            fontText.draw(batch, layout, oX + 10, oY2 + 25);

            batch.draw(logo, 355, 325, 320, 210);

            layout.setText(fontThuong, "chienbinhrongthieng.online");
            fontThuong.setColor(1, 1, 1, 1);
            fontThuong.draw(batch, layout, Gdx.graphics.getWidth() - layout.width - 10, 600);
            layout.setText(fontThuong, "V0.0.0");
            fontThuong.draw(batch, layout, Gdx.graphics.getWidth() - layout.width - 10, 580);

            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            float nutOkX = (Gdx.graphics.getWidth() - kdnW) / 2f + 18;
            float nutOkY = kdnH - 110;
            float nutQuenX = (Gdx.graphics.getWidth() - kdnW) / 2f + 200;
            float nutQuenY = kdnH - 110;

            batch.draw(thoiGianHienNutClick > 0 && nutManHinhDangKyChon == 0 ? nutclick : nutdn, nutOkX, nutOkY, 142, 48);
            layout.setText(font, "OK");
            font.draw(batch, layout, nutOkX + (142 - layout.width) / 2, nutOkY + 30);

            batch.draw(thoiGianHienNutClick > 0 && nutManHinhDangKyChon == 1 ? nutclick : nutdn, nutQuenX, nutQuenY, 142, 48);
            layout.setText(font, "Đăng nhập");
            font.draw(batch, layout, nutQuenX + (142 - layout.width) / 2, nutQuenY + 30);

            batch.end();
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        fontText.dispose();
        fontThuong.dispose();
        sky.dispose();
        nuixa.dispose();
        nui.dispose();
        nuicay.dispose();
        nuithap.dispose();
        logo.dispose();
        nutdn.dispose();
        nutclick.dispose();
        khungdangnhap.dispose();
        anhThongBao.dispose();
    }
}
