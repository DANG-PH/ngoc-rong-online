package com.dang.dragonboy.giao_dien_ngoai;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.dang.dragonboy.giao_dien_trong.ManHinhNhaBroly;
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.giao_dien_trong.ManHinhNhaGohan;

public class ManHinhChoiMoi implements Screen {

    private Main game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font, fontText;
    private GlyphLayout layout;

    private Texture nutdn, nutclick;
    private Texture bgTexture;
    private Texture matrang;
    private Texture bautroixd;
    private Texture sky, nuixa, nui, nuicay, nuithap;
    private Texture cayco, cayco1;
    private Texture[] mdtd = new Texture[5];
    private Texture[] dtd = new Texture[3];
    private Texture[] ldtd = new Texture[3];

    private Texture[] dauTraiDat = new Texture[3];
    private Texture muiTen;

    private Texture traidatchan, traidatthan;

    private int hanhTinhDuocChon = 0;
    private int nhanVatDuocChon = 0;

    private String tenNguoiChoi = "";
    private boolean oNhapDuocChon = false;

    private String[][] hanhtinhVaNhanvat = {
        {"Trái đất", "Goku", "Krillin", "Yamcha"},
        {"Xayda", "Cađíc", "Rađíc", "Kakalot"},
        {"Namek", "Ốc tiêu", "Piccolo", "Kami"}
    };

    private float muiTenY = 0;
    private float muiTenGoc = 0;
    private int muiTenX = 0, muiTenYBase = -100;
    private float thoiGianTichLuy = 0;
    private boolean isMayChuPressed = false;
    private boolean isDongPressed = false;
    private boolean isTaoMoiPressed = false;
    private float thoiGianHienNutClick = 0; // giữ bao lâu
    private boolean chuyenManHinhMayChu = false;
    private boolean chuyenManHinhDong = false;
    private boolean chuyenManHinhTaoMoi = false;
    public ManHinhChoiMoi(Main game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        layout = new GlyphLayout();
    }

    private void capNhatTaiNguyenTheoHanhTinh() {
        String tenHanhTinh = hanhtinhVaNhanvat[hanhTinhDuocChon][0];
        switch (hanhTinhDuocChon) {
            case 0:
                tenHanhTinh="traidat";
                break;
            case 1:
                tenHanhTinh="xayda";
                break;
            case 2:
                tenHanhTinh="namek";
                break;
        }
        sky = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "sky_" + tenHanhTinh+ ".png");
        nuixa = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "nuixa_" + tenHanhTinh + ".png");
        nui = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "nui_" +tenHanhTinh + ".png");
        nuicay = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "nuicay_" + tenHanhTinh + ".png");
        nuithap = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "nuithap_" + tenHanhTinh + ".png");

        cayco = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "cayco1_" + tenHanhTinh+ ".png");
        cayco1 = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "cayco2_" + tenHanhTinh + ".png");

        for (int i = 0; i < 5; i++) {
            mdtd[i] = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "matdat_" + tenHanhTinh + (i + 1) + ".png");
        }
        for (int i = 0; i < 3; i++) {
            dtd[i] = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "dat_" + tenHanhTinh+ (i + 1) + ".png");
            ldtd[i] = new Texture("hud/giaodienngoai/"+tenHanhTinh+ "/" + "longdat_" + tenHanhTinh+ (i + 1) + ".png");
        }

        traidatchan = new Texture("hud/giaodienngoai/"+tenHanhTinh+"/chan" + tenHanhTinh + ".png");
        traidatthan = new Texture("hud/giaodienngoai/"+tenHanhTinh+"/than" + tenHanhTinh + ".png");

        for (int i = 0; i < 3; i++) {
            dauTraiDat[i] = new Texture("hud/giaodienngoai/"+tenHanhTinh+"/dau_" + tenHanhTinh + "_" + (i + 1) + ".png");
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Chỉnh màu shapeRenderer theo hành tinh
        switch (hanhTinhDuocChon) {
            case 0:
                shapeRenderer.setColor(25 / 255f, 176 / 255f, 248 / 255f, 1); // Trái đất
                break;
            case 1:
                shapeRenderer.setColor(224f / 255f, 173f / 255f, 109f / 255f, 1f); // Xayda
                break;
            case 2:
                shapeRenderer.setColor(5 / 255f, 194 / 255f, 168 / 255f, 1); // Namek (xanh lá)
                break;
        }
        shapeRenderer.rect(0, 460, 1020, 150);
        shapeRenderer.end();
        muiTenGoc += delta * 20f;
        thoiGianTichLuy += delta * 15f;
        muiTenY = (float) Math.sin(muiTenGoc) * 2;

        batch.begin();
        if (hanhTinhDuocChon == 1) { // Xayda
            for (int i = 0; i < 5; i++){
                batch.draw(bautroixd,i*255,455,255,150);
            }
            batch.draw(matrang, 750, 490, 64, 64);
        }
        for (int i = 0; i < 4; i++) {
            batch.draw(sky, i * 255, 310, 255, 150);
            batch.draw(nuixa, i * 255, 310, 255, 150);
        }
        for (int i = 0; i < 2; i++) {
            batch.draw(nui, i * 510, 280, 510, 170);
        }
        for (int i = 0; i < 4; i++) {
            batch.draw(nuicay, i * 340, 205, 340, 170);
            batch.draw(nuithap, i * 340, 125, 340, 180);
        }
        for (int i = 0; i < 21; i++) {
            batch.draw(cayco, i * 50, 125, 50, 50);
        }
        for (int i = 0; i < 2; i++) {
            batch.draw(cayco1, 10 + i * 600, 125, 50, 50);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                batch.draw(mdtd[j], j * 50 + i * 250, 75, 50, 50);
            }
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                batch.draw(dtd[j], j * 50 + i * 150, 25, 50, 50);
                batch.draw(ldtd[j], j * 50 + i * 150, 0, 50, 50);
            }
        }

        // Ô nhập tên
        batch.draw(bgTexture, 362, 480, 320, 50);
        if (tenNguoiChoi.isEmpty()) {
            fontText.setColor(0.5f, 0.5f, 0.5f, 1); // xám nhạt
            layout.setText(fontText, "Tên nhân vật");
        } else {
            fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1); // màu nâu
            layout.setText(fontText, tenNguoiChoi);
        }
        fontText.draw(batch, layout, 372, 510);

        // Các nút chọn hành tinh
        for (int i = 0; i < hanhtinhVaNhanvat.length; i++) {
            int x = 294 + i * 155;
            int y = 385;
            boolean selected = (hanhTinhDuocChon == i);
            batch.draw(selected ? nutclick : nutdn, x, y, 135, 50);
            layout.setText(font, hanhtinhVaNhanvat[i][0]);
            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            font.draw(batch, layout, x + (135 - layout.width) / 2, y + 30);
        }

        // Các nút chọn nhân vật
        for (int j = 1; j <= 3; j++) {
            int x = 294 + (j - 1) * 155;
            int y = 300;
            boolean selected = (nhanVatDuocChon == j - 1);
            batch.draw(selected ? nutclick : nutdn, x, y, 135, 50);
            String tenNV = hanhtinhVaNhanvat[hanhTinhDuocChon][j];
            layout.setText(font, tenNV);
            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            font.draw(batch, layout, x + (135 - layout.width) / 2, y + 30);
        }

        // Mũi tên chọn
        batch.draw(muiTen, muiTenX + 3 + (135 - 32) / 2, muiTenYBase + 52 + muiTenY, 28, 28);

        veNhanVatDung(batch, 205, 105);
        // Nút "Máy chủ" góc trái trên
        int nutMayChuX = 20;
        int nutMayChuY = Gdx.graphics.getHeight() - 70;
        Texture textureMayChu = (isMayChuPressed &&  thoiGianHienNutClick> 0) ? nutclick : nutdn;
        batch.draw(textureMayChu, nutMayChuX, nutMayChuY, 135, 50);
        layout.setText(font, "Máy chủ");
        font.draw(batch, layout, nutMayChuX + (135 - layout.width) / 2, nutMayChuY + 30);

        // Nút "Đóng" góc trái dưới
        int nutDongX = 20;
        int nutDongY = 20;
        Texture textureDong = (isDongPressed && thoiGianHienNutClick > 0) ? nutclick : nutdn;
        batch.draw(textureDong, nutDongX, nutDongY, 135, 50);
        layout.setText(font, "Đóng");
        font.draw(batch, layout, nutDongX + (135 - layout.width) / 2, nutDongY + 30);

        // Nút "Tạo mới" giữa dưới
        int nutTaoMoiX = Gdx.graphics.getWidth() / 2 - 135 / 2;
        int nutTaoMoiY = 20;
        Texture textureTaoMoi = (isTaoMoiPressed && thoiGianHienNutClick > 0) ? nutclick : nutdn;
        batch.draw(textureTaoMoi, nutTaoMoiX, nutTaoMoiY, 135, 50);
        layout.setText(font, "Tạo mới");
        font.draw(batch, layout, nutTaoMoiX + (135 - layout.width) / 2, nutTaoMoiY + 30);
        batch.end();

        // Xử lý chuột click chọn
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= 362 && mouseX <= 682 && mouseY >= 480 && mouseY <= 530) {
                oNhapDuocChon = true;
            } else {
                oNhapDuocChon = false;
            }
            // Click nút Máy chủ
            if (mouseX >= 20 && mouseX <= 20 + 135 &&
                mouseY >= Gdx.graphics.getHeight() - 70 && mouseY <= Gdx.graphics.getHeight() - 20) {
                isMayChuPressed = true;
                thoiGianHienNutClick = 0.2f;
                chuyenManHinhMayChu = true; // Ghi nhớ chuyển sau

            }

            // Click nút Đóng
            if (mouseX >= 20 && mouseX <= 20 + 135 &&
                mouseY >= 20 && mouseY <= 70) {
                isDongPressed = true;
                thoiGianHienNutClick = 0.2f;
                chuyenManHinhDong = true; // Ghi nhớ chuyển sau

            }

            // Click nút Tạo mới
            if (mouseX >= nutTaoMoiX && mouseX <= nutTaoMoiX + 135 &&
                mouseY >= 20 && mouseY <= 70) {
                if (!tenNguoiChoi.isEmpty()) {
                    isTaoMoiPressed = true;
                    thoiGianHienNutClick = 0.2f;
                    chuyenManHinhTaoMoi = true; // Ghi nhớ chuyển sau
                }
            }
            for (int i = 0; i < 3; i++) {
                int x = 294 + i * 155;
                int y = 385;
                if (mouseX >= x && mouseX <= x + 135 && mouseY >= y && mouseY <= y + 50) {
                    hanhTinhDuocChon = i;
                    capNhatTaiNguyenTheoHanhTinh();
                    muiTenX = x;
                    muiTenYBase = y;
                    return;
                }
            }



            for (int j = 0; j < 3; j++) {
                int x = 294 + j * 155;
                int y = 300;
                if (mouseX >= x && mouseX <= x + 135 && mouseY >= y && mouseY <= y + 50) {
                    nhanVatDuocChon = j;
                    muiTenX = x;
                    muiTenYBase = y;
                    return;
                }
            }
        }
        thoiGianHienNutClick -= delta;
        if (thoiGianHienNutClick <= 0) {
            // Chuyển màn nếu cần
            if (chuyenManHinhMayChu) {
                game.setScreen(new ManHinhChonMayChu(game));
                chuyenManHinhMayChu = false;
            }
            if (chuyenManHinhDong) {
                game.setScreen(new ManHinhMenu(game,null, false));
                chuyenManHinhDong = false;
            }
            if (chuyenManHinhTaoMoi) {
//                String nhanvat = hanhtinhVaNhanvat[hanhTinhDuocChon][nhanVatDuocChon+1];
//                switch (hanhTinhDuocChon){
//                    case 0:
//                        game.setScreen(new ManHinhSplash(game, new ManHinhNhaGohan(game,tenNguoiChoi,"traidat",nhanvat,null)));
//                        break;
//                    case 1:
//                        game.setScreen(new ManHinhSplash(game, new ManHinhNhaBroly(game,tenNguoiChoi,"xayda",nhanvat)));
//                        break;
//                    case 2:
//                        game.setScreen(new ManHinhSplash(game, new ManHinhNhaGohan(game,tenNguoiChoi,"traidat",nhanvat,null)));
//                        break;
//                }
                ManHinhDoiTaiKhoan nextScreen = new ManHinhDoiTaiKhoan(game);
                nextScreen.setTenTaiKhoan(tenNguoiChoi);
                game.setScreen(new ManHinhSplash(game, nextScreen));
                chuyenManHinhTaoMoi = false;
            }

            // Reset nút nhấn
            isMayChuPressed = false;
            isDongPressed = false;
            isTaoMoiPressed = false;
            }
    }

    private void veNhanVatDung(SpriteBatch batch, float x, float y) {
        float doDaoDong = (float) Math.sin(thoiGianTichLuy) * 1.08f;
        float scale = 0.5f;

        float chanW = traidatchan.getWidth() * scale;
        float chanH = traidatchan.getHeight() * scale;
        float thanW = traidatthan.getWidth() * scale;
        float thanH = traidatthan.getHeight() * scale;
        Texture dau = dauTraiDat[nhanVatDuocChon];
        float dauW = dau.getWidth() * scale;
        float dauH = dau.getHeight() * scale;

        batch.draw(traidatchan, x, y, chanW, chanH);

        float thanX = x + chanW / 2f - thanW / 2f;
        float thanY = y + chanH + doDaoDong;

        float dauX = x + chanW / 2f - dauW / 2f;
        float dauY = thanY + thanH;
        float dauYOffset = -14f;
        float dauXOffset = 0f;
        if (nhanVatDuocChon==0 && hanhTinhDuocChon==0){
            dauYOffset = -16f;
        }
        if (nhanVatDuocChon==2 && hanhTinhDuocChon==0){
            dauYOffset = -21f;
        }
        if (nhanVatDuocChon==0 && hanhTinhDuocChon==1){
            dauXOffset = -2f;
        }
        if (nhanVatDuocChon==1 && hanhTinhDuocChon==1){
            dauXOffset = -6f;
            dauYOffset = -28f;
        }
        batch.draw(dau, dauX + dauXOffset, dauY + dauYOffset, dauW, dauH);
        batch.draw(traidatthan, thanX, thanY - 10.2f, thanW, thanH);
    }

    @Override public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                if (oNhapDuocChon) {
                    if (character == '\b') {
                        if (!tenNguoiChoi.isEmpty()) {
                            tenNguoiChoi = tenNguoiChoi.substring(0, tenNguoiChoi.length() - 1);
                        }
                    } else if (Character.toString(character).matches("[a-zA-Z0-9 ăâđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ]")) {
                        if (tenNguoiChoi.length() < 20) {
                            tenNguoiChoi += character;
                        }
                    }
                }
                return true;
            }
        });
        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ";
        param.size = 18;
        font = generator.generateFont(param);
        param.size = 17;
        fontText = generator.generateFont(param);
        generator.dispose();

        nutdn = new Texture("hud/giaodienngoai/chung/nutdangnhap3.png");
        nutclick = new Texture("hud/giaodienngoai/chung/nutclick2.png");
        muiTen = new Texture("hud/giaodienngoai/chung/muitenvang.png");
        bgTexture = new Texture("hud/giaodienngoai/chung/input.png");
        matrang = new Texture("hud/giaodienngoai/xayda/matrang.png");
        bautroixd = new Texture("hud/giaodienngoai/xayda/bautroixayda.jpg");
        capNhatTaiNguyenTheoHanhTinh();
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        nutdn.dispose();
        nutclick.dispose();
        font.dispose();
        fontText.dispose();
        bgTexture.dispose();
        muiTen.dispose();
        matrang.dispose();
        bautroixd.dispose();
        sky.dispose();
        nuixa.dispose();
        nui.dispose();
        nuicay.dispose();
        nuithap.dispose();
        cayco.dispose();
        cayco1.dispose();

        for (Texture t : mdtd) t.dispose();
        for (Texture t : dtd) t.dispose();
        for (Texture t : ldtd) t.dispose();

        traidatchan.dispose();
        traidatthan.dispose();

        for (Texture t : dauTraiDat) t.dispose();
    }
}
