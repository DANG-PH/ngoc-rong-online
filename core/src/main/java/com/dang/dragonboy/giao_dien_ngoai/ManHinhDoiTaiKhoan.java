package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.InputAdapter;

import com.badlogic.gdx.math.MathUtils;
import com.dang.dragonboy.du_lieu.LocalStorage;
import com.dang.dragonboy.network.DTO.ProfileResult;
import com.dang.dragonboy.network.DTO.UserResponse;

import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.network.*;

enum TrangThaiManHinh {
    NONE,
    DANGKY_STEP1,
    DANGKY_STEP2,
    DANGKY_STEP3,
    VERIFY_OTP,
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

    private String tenEmailDky = "";
    private String RealnameDky = "";
    private boolean oNhapEmailDuocChonDky = false;
    private boolean oNhapRealnameDuocChonDky = false;

    private String GameNameDky = "";
    private boolean oNhapGameNameDuocChonDky = false;

    private String maOTP = "";
    private String maCapcha = "";
    private String maRandomCapcha = ""; // random Capcha mỗi lần login
    private boolean oNhapMaOTP = false;
    private boolean oNhapMaCapcha = false;

    private String tenTaiKhoan = "";
    private String matKhau = "";
    private boolean oNhapTaiKhoanDuocChon = false;
    private boolean oNhapMatKhauDuocChon = false;

    private int nutManHinhDangKyChon = -1;

    private String sessionId = "";

    private boolean isGooglePressed = false;

    public ManHinhDoiTaiKhoan(Main game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        layout = new GlyphLayout();
    }

    // Dùng cho ManHinhChoiMoi khi người chơi bấm tạo mới tk, và set màn sang màn này
    public void setTenTaiKhoan(String tenNhanVat) {
        this.tenTaiKhoanDky = tenNhanVat;
        this.RealnameDky = tenNhanVat;
        this.GameNameDky = tenNhanVat;
        this.trangThaiManHinh = TrangThaiManHinh.DANGKY_STEP1;
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
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ" + "ớờởỡợắằẳẵặấầẩẫậếềểễệốồổỗộứừửữựíìỉĩịóòỏõọúùủũụ" + "ỚỜỞỠỢẮẰẲẴẶẤẦẨẪẬẾỀỂỄỆỐỒỔỖỘỨỪỬỮỰÍÌỈĨỊÓÒỎÕỌÚÙỦŨỤ" + ".,;:!?'\"-()[]{}/@#$%^&*+=<>|\\~`" + "http://https://www._" + " —–·•…" + "0123456789";
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
                        } else if (Character.toString(character).matches("[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]")) {
                            if (tenTaiKhoan.length() < 30) {
                                tenTaiKhoan += character;
                            }
                        }
                    } else if (oNhapMatKhauDuocChon) {
                        if (character == '\b') {
                            if (!matKhau.isEmpty()) {
                                matKhau = matKhau.substring(0, matKhau.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]")) {
                            if (matKhau.length() < 30) {
                                matKhau += character;
                            }
                        }
                    }
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP1) {
                    if (oNhapTaiKhoanDuocChonDky) {
                        if (character == '\b') {
                            if (!tenTaiKhoanDky.isEmpty()) {
                                tenTaiKhoanDky = tenTaiKhoanDky.substring(0, tenTaiKhoanDky.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]")) {
                            if (tenTaiKhoanDky.length() < 30) {
                                tenTaiKhoanDky += character;
                            }
                        }
                    } else if (oNhapMatKhauDuocChonDky) {
                        if (character == '\b') {
                            if (!matKhauDky.isEmpty()) {
                                matKhauDky = matKhauDky.substring(0, matKhauDky.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]")) {
                            if (matKhauDky.length() < 30) {
                                matKhauDky += character;
                            }
                        }
                    }
                }
                else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP2) {
                    if (oNhapEmailDuocChonDky) {
                        if (character == '\b') {
                            if (!tenEmailDky.isEmpty()) {
                                tenEmailDky = tenEmailDky.substring(0, tenEmailDky.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]")) {
                            if (tenEmailDky.length() < 50) {
                                tenEmailDky += character;
                            }
                        }
                    } else if (oNhapRealnameDuocChonDky) {
                        if (character == '\b') {
                            if (!RealnameDky.isEmpty()) {
                                RealnameDky = RealnameDky.substring(0, RealnameDky.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]")) {
                            if (RealnameDky.length() < 30) {
                                RealnameDky += character;
                            }
                        }
                    }
                }
                else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP3) {
                    if (oNhapGameNameDuocChonDky) {
                        if (character == '\b') {
                            if (!GameNameDky.isEmpty()) {
                                GameNameDky = GameNameDky.substring(0, GameNameDky.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]")) {
                            if (GameNameDky.length() < 50) {
                                GameNameDky += character;
                            }
                        }
                    }
                }
                else if (trangThaiManHinh == TrangThaiManHinh.VERIFY_OTP) {
                    if (oNhapMaOTP) {
                        if (character == '\b') {
                            if (!maOTP.isEmpty()) {
                                maOTP= maOTP.substring(0, maOTP.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]")) {
                            if (maOTP.length() < 50) {
                                maOTP += character;
                            }
                        }
                    } else if (oNhapMaCapcha) {
                        if (character == '\b') {
                            if (!maCapcha.isEmpty()) {
                                maCapcha = maCapcha.substring(0, maCapcha.length() - 1);
                            }
                        } else if (Character.toString(character).matches("[a-zA-Z0-9 !@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]")) {
                            if (maCapcha.length() < 30) {
                                maCapcha += character;
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
            if (trangThaiManHinh == TrangThaiManHinh.VERIFY_OTP) {
                if (nutManHinhDangKyChon == 1) {
                    trangThaiManHinh = TrangThaiManHinh.NONE;
                    nutManHinhDangKyChon = -1;
                }
                if (nutManHinhDangKyChon == 0) {
                    nutManHinhDangKyChon = -1;
                    boolean duDieuKien = maCapcha.equals(this.maRandomCapcha);
                    duDieuKien = true; // Dev
                    if (duDieuKien) {
                        new Thread(() -> {
                            ProfileResult profileResult = ApiService.verifyOTP(this.sessionId, maOTP);

                            Gdx.app.postRunnable(() -> {
                                if (profileResult != null && profileResult.status == ProfileResult.Status.OK) {
                                    System.out.println("Đăng nhập thành công!");
                                    State_Management.setUserResponse(profileResult.user);
                                    game.setScreen(new ManHinhMenu(game, null, null));
                                } else {
                                    System.out.println("Đăng nhập thất bại!");
                                }
                            });
                        }).start();
                    } else {
                        System.out.println("Sai ma capcha");
                    }
                }
            }

            if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                if (chuyenManHinhOK) {
                    chuyenManHinhOK = false;

                    new Thread(() -> {
                        // chạy ở thread phụ
                        String sessionId = ApiService.login(tenTaiKhoan, matKhau);

                        // gửi nhiệm vụ về main thread
                        Gdx.app.postRunnable(() -> {
                            // chỗ này chạy ở main thread
//                            if (user != null) {
//                                System.out.println("Đăng nhập thành công!");
//                                State_Management.setUserResponse(user);
//                                game.setScreen(new ManHinhMenu(game, null));
//                            } else {
//                                System.out.println("Đăng nhập thất bại!");
//                            }
                            if (sessionId != null) {
                                this.sessionId = sessionId;
                                trangThaiManHinh = TrangThaiManHinh.VERIFY_OTP;
                                this.maRandomCapcha = taoCaptcha6KyTu();
                            }
                        });
                    }).start();
                }
                else if (chuyenManHinhDong) {
                    game.setScreen(new ManHinhMenu(game, null, null));
                    chuyenManHinhDong = false;
                } else if (isQuenMKPressed) {
                    trangThaiManHinh = TrangThaiManHinh.THONGBAO;
                } else if (isDangKyPressed) {
                    trangThaiManHinh = TrangThaiManHinh.DANGKY_STEP1;
                }
            }

            if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP3) {
                if (nutManHinhDangKyChon == 1) {
                    nutManHinhDangKyChon = -1;
                    trangThaiManHinh = TrangThaiManHinh.DANGKY_STEP2;
                }
                if (nutManHinhDangKyChon == 0) {
                    nutManHinhDangKyChon = -1;
                    new Thread(() -> {
                        boolean ok = ApiService.register(tenTaiKhoanDky, matKhauDky, RealnameDky, tenEmailDky, GameNameDky);

                        Gdx.app.postRunnable(() -> {
                            if (ok) {
                                System.out.println("Đăng ký thành công!");
                                trangThaiManHinh = TrangThaiManHinh.NONE;
                                tenTaiKhoanDky = "";
                                matKhauDky = "";
                                tenEmailDky = "";
                                RealnameDky = "";
                                GameNameDky = "";
                            } else {
                                System.out.println("Đăng ký thất bại!");
                            }
                        });
                    }).start();
                }
            }

            if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP2) {
                if (nutManHinhDangKyChon == 1) {
                    trangThaiManHinh = TrangThaiManHinh.DANGKY_STEP1;
                    nutManHinhDangKyChon = -1;
                }
                if (nutManHinhDangKyChon == 0) {
                    nutManHinhDangKyChon = -1;
                    trangThaiManHinh = TrangThaiManHinh.DANGKY_STEP3;
                }
            }

            if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP1) {
                if (nutManHinhDangKyChon == 1) {
                    trangThaiManHinh = TrangThaiManHinh.NONE;
                    nutManHinhDangKyChon = -1;
                }
                if (nutManHinhDangKyChon == 0) {
                    nutManHinhDangKyChon = -1;
                    trangThaiManHinh = TrangThaiManHinh.DANGKY_STEP2;
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
            isGooglePressed = false;
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
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP1) {
                    oNhapTaiKhoanDuocChonDky = true;
                    oNhapMatKhauDuocChonDky = false;
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP2) {
                    oNhapEmailDuocChonDky = true;
                    oNhapRealnameDuocChonDky = false;
                } else if (trangThaiManHinh == TrangThaiManHinh.VERIFY_OTP) {
                    oNhapMaOTP = true;
                    oNhapMaCapcha = false;
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP3) {
                    oNhapGameNameDuocChonDky = true;
                }
            } else if (mouseX >= oX && mouseX <= oX + 340 && mouseY >= oY2 && mouseY <= oY2 + 40) {
                if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                    oNhapTaiKhoanDuocChon = false;
                    oNhapMatKhauDuocChon = true;
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP1) {
                    oNhapTaiKhoanDuocChonDky = false;
                    oNhapMatKhauDuocChonDky = true;
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP2) {
                    oNhapEmailDuocChonDky = false;
                    oNhapRealnameDuocChonDky = true;
                } else if (trangThaiManHinh == TrangThaiManHinh.VERIFY_OTP) {
                    oNhapMaOTP = false;
                    oNhapMaCapcha = true;
                }
            } else {
                if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                    oNhapTaiKhoanDuocChon = false;
                    oNhapMatKhauDuocChon = false;
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP1) {
                    oNhapTaiKhoanDuocChonDky = false;
                    oNhapMatKhauDuocChonDky = false;
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP2) {
                    oNhapEmailDuocChonDky = false;
                    oNhapRealnameDuocChonDky = false;
                } else if (trangThaiManHinh == TrangThaiManHinh.VERIFY_OTP) {
                    oNhapMaOTP = false;
                    oNhapMaCapcha = false;
                } else if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP3) {
                    oNhapGameNameDuocChonDky = false;
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
                if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP1) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 0;
                }
                if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP2) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 0;
                }
                if (trangThaiManHinh == TrangThaiManHinh.VERIFY_OTP) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 0;
                } if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP3) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 0;
                }
            }

            if (mouseX >= quenX && mouseX <= quenX + 142 && mouseY >= quenY && mouseY <= quenY + 48) {
                if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                    isQuenMKPressed = true;
                    thoiGianHienNutClick = 0.1f;
                }
                if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP1) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 1;
                }
                if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP2) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 1;
                }
                if (trangThaiManHinh == TrangThaiManHinh.VERIFY_OTP) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 1;
                } if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP3) {
                    thoiGianHienNutClick = 0.1f;
                    nutManHinhDangKyChon = 1;
                }
            }

            if (mouseX >= 1020-20-135 && mouseX <= 1020-20 && mouseY >= 20 && mouseY <= 70) {
                isDangKyPressed = true;
                thoiGianHienNutClick = 0.1f;
            }

            if (trangThaiManHinh == TrangThaiManHinh.NONE) {
                float nutGoogleX = (Gdx.graphics.getWidth() - 200) / 2f;
                float nutGoogleY = 20;
                if (mouseX >= nutGoogleX && mouseX <= nutGoogleX + 200
                    && mouseY >= nutGoogleY && mouseY <= nutGoogleY + 50) {
                    isGooglePressed = true;
                    thoiGianHienNutClick = 0.1f;

                    GoogleOAuth2Desktop.login(new GoogleOAuth2Desktop.Callback() {
                        @Override
                        public void onSuccess(String idToken) {
                            new Thread(() -> {
                                ApiService.LoginWithGoogleResponse res =
                                    ApiService.loginWithGoogle(idToken);

                                if (res != null) {
                                    State_Management.setToken(res.accessToken);
                                    State_Management.setAuth_id(res.authId);
                                    State_Management.setRefresh_token(res.refreshToken);

                                    // Gọi getProfile
                                    ProfileResult user = ApiService.getProfile(res.accessToken);

                                    Gdx.app.postRunnable(() -> {
                                        if (user != null && user.status == ProfileResult.Status.OK) {
                                            game.setScreen(new ManHinhMenu(game, null, null));
                                        } else if (user != null && user.status == ProfileResult.Status.BANNED) {
                                            game.setScreen(new ManHinhMenu(game, null, ManHinhMenu.TrangThai.BAN));
                                        } else {
                                            System.out.println("Google login thất bại: không lấy được profile");
                                        }
                                    });
                                } else {
                                    Gdx.app.postRunnable(() ->
                                        System.out.println("Google login thất bại"));
                                }
                            }).start();
                        }

                        @Override
                        public void onFailure(String error) {
                            Gdx.app.postRunnable(() ->
                                System.out.println("Lỗi: " + error));
                        }
                    });
                }
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
            layout.setText(font, "Để lấy lại mật khẩu xin vui lòng truy cập website https://nronline.vercel.app");
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

            layout.setText(fontThuong, "nronline.vercel.app");
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

            float nutGoogleX = (Gdx.graphics.getWidth() - 200) / 2f;
            float nutGoogleY = nutDongY;
            batch.draw(isGooglePressed ? nutclick : nutdn, nutGoogleX, nutGoogleY, 200, 48);
            layout.setText(font, "Đăng nhập Google");
            font.draw(batch, layout, nutGoogleX + (200 - layout.width) / 2, nutGoogleY + 30);
            batch.end();
        }

        if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP1) {

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

            layout.setText(fontThuong, "nronline.vercel.app");
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

        if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP2) {

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
            if (tenEmailDky.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
                layout.setText(fontText, "Email đăng ký");
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1); // nâu
                layout.setText(fontText, tenEmailDky);
            }
            fontText.draw(batch, layout, oX + 10, oY1 + 25);

            if (RealnameDky.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f); // xám
                layout.setText(fontText, "Tên thật đăng ký");
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1); // nâu
                layout.setText(fontText, RealnameDky);
            }
            fontText.draw(batch, layout, oX + 10, oY2 + 25);

            batch.draw(logo, 355, 325, 320, 210);

            layout.setText(fontThuong, "nronline.vercel.app");
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
            layout.setText(font, "Quay lại");
            font.draw(batch, layout, nutQuenX + (142 - layout.width) / 2, nutQuenY + 30);

            batch.end();
        }

        if (trangThaiManHinh == TrangThaiManHinh.DANGKY_STEP3) {

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(108f / 255f, 74f / 255f, 0f, 1f);
            shapeRenderer.rect((Gdx.graphics.getWidth() - 369) / 2f, 214 - 49, 369, 214);
            shapeRenderer.end();

            batch.begin();
            float kdnW = 365;
            float kdnH = 210;
            batch.draw(khungdangnhap, (Gdx.graphics.getWidth() - kdnW) / 2f, kdnH - 43, kdnW, kdnH);

            float oX = (Gdx.graphics.getWidth() - 320) / 2f;
            float oY1 = 272; // ô trên → nhập GameName
            float oY2 = 196; // ô dưới → text cố định "Chào mừng bạn!"

            // Ô trên: nhập tên nhân vật
            if (GameNameDky.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
                layout.setText(fontText, "Tên nhân vật trong game");
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1f);
                layout.setText(fontText, GameNameDky);
            }
            fontText.draw(batch, layout, oX + 10, oY1 + 25);

            // Ô dưới: text cố định, không nhập được
            fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
            layout.setText(fontText, "Chào mừng bạn tới NRO!");
            fontText.draw(batch, layout, oX + 10, oY2 + 25);

            batch.draw(logo, 355, 325, 320, 210);

            layout.setText(fontThuong, "nronline.vercel.app");
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
            layout.setText(font, "Đăng ký");
            font.draw(batch, layout, nutOkX + (142 - layout.width) / 2, nutOkY + 30);

            batch.draw(thoiGianHienNutClick > 0 && nutManHinhDangKyChon == 1 ? nutclick : nutdn, nutQuenX, nutQuenY, 142, 48);
            layout.setText(font, "Quay lại");
            font.draw(batch, layout, nutQuenX + (142 - layout.width) / 2, nutQuenY + 30);

            batch.end();
        }

        if (trangThaiManHinh == TrangThaiManHinh.VERIFY_OTP) {

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
            if (maOTP.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
                layout.setText(fontText, "Mã OTP");
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1); // nâu
                layout.setText(fontText, maOTP);
            }
            fontText.draw(batch, layout, oX + 10, oY1 + 25);

            if (maCapcha.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f); // xám
                layout.setText(fontText, "Nhập "+this.maRandomCapcha);
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1); // nâu
                layout.setText(fontText, maCapcha);
            }
            fontText.draw(batch, layout, oX + 10, oY2 + 25);

            batch.draw(logo, 355, 325, 320, 210);

            layout.setText(fontThuong, "nronline.vercel.app");
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
            layout.setText(font, "Quay lại");
            font.draw(batch, layout, nutQuenX + (142 - layout.width) / 2, nutQuenY + 30);

            batch.end();
        }
    }

    public static String taoCaptcha6KyTu() {
        String CAPTCHA_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = MathUtils.random(CAPTCHA_CHARS.length() - 1);
            sb.append(CAPTCHA_CHARS.charAt(index));
        }

        return sb.toString();
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
