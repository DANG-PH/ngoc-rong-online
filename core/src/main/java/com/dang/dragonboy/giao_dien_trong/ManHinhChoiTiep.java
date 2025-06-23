package com.dang.dragonboy.giao_dien_trong;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.Color;

//He thong
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.he_thong.ThaoTac;
//NhanVat
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
//HUD
import com.dang.dragonboy.hien_thi.QuanLyCamera;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.hien_thi.SkillIcon;


public class ManHinhChoiTiep implements Screen {
    //quan trong
    private Main game;
    private NhanVat nhanVat;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font, fontText;
    private GlyphLayout layout;
    //camera
    private QuanLyCamera camManager;
    private float camYBanDau = -1;  // vẫn giữ để tính parallax
    //canh vat , nguoi
    private Texture sky, nuixa, nui, nuicay, nuithap;
    private Texture cayco, cayco1;
    private Texture[] mdtd = new Texture[5];
    private Texture[] dtd = new Texture[3];
    private Texture[] ldtd = new Texture[3];
    private Texture dochanhtinh;
    private Texture caycoi1,caycoi2;
    private Texture light;
    private Texture khoi;
    private Texture ruongdo;
    private Texture nhagohan;
    private Texture[] dauTraiDat = new Texture[3];
    private Texture muiTen;

    private Texture npcdau, npcthan ,npcchan;
    private float muiTenY = 0;
    private float muiTenGoc = 0;
    private int muiTenX = 0, muiTenYBase = -100;
    private Texture nutdn, nutclick;
    private float thoiGianHienNutClick = 0;
    private int HanhTinhDuocChon;
    private float thoiGianTichLuy = 0;
    //HUD
    private VeHUD hudRenderer;

    public ManHinhChoiTiep(Main game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        camManager = new QuanLyCamera();

        layout = new GlyphLayout();

        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ";
        param.size = 18;
        font = generator.generateFont(param);
        param.size = 17;
        fontText = generator.generateFont(param);
        generator.dispose();

        hudRenderer = new VeHUD(font, layout);
        SkillIcon[] traidatIcons = loadSkillIcons("traidat");  // Load tạm icon trái đất
        hudRenderer.setSkillIcons(traidatIcons);             // Gán vào HUD

        nutdn = new Texture("hud/giaodienngoai/chung/nutdangnhap3.png");
        nutclick = new Texture("hud/giaodienngoai/chung/nutclick2.png");
        muiTen = new Texture("hud/giaodienngoai/chung/muitenvang.png");


        sky = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "sky_" + "traidat" + ".png");
        nuixa = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuixa_" + "traidat" + ".png");
        nui = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nui_" +"traidat" + ".png");
        nuicay = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuicay_" + "traidat" + ".png");
        nuithap = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuithap_" + "traidat" + ".png");

        cayco = new Texture("map/"+"traidat"+ "/chung/cayco/" + "cayco3_" + "traidat" + ".png");
        cayco1 = new Texture("map/"+"traidat"+ "/chung/cayco/"  + "cayco5_" + "traidat" + ".png");

        for (int i = 0; i < 5; i++) {
            mdtd[i] = new Texture("map/"+"traidat"+ "/chung/dat/"  + "matdat_" + "traidat" + (i + 1) + ".png");
        }
        for (int i = 0; i < 3; i++) {
            dtd[i] = new Texture("map/"+"traidat"+ "/chung/dat/"  + "dat_" + "traidat"+ (i + 1) + ".png");
            ldtd[i] = new Texture("map/"+"traidat"+ "/chung/dat/"  + "longdat_" + "traidat"+ (i + 1) + ".png");
        }
        dochanhtinh = new Texture("map/"+"traidat"+ "/chung/dat/"  + "doc_" + "traidat" + ".png");

        caycoi1 = new Texture("map/"+"traidat"+ "/chung/caycoi/"  + "caycoi1_"+"traidat" + ".png") ;
        caycoi2 = new Texture("map/"+"traidat"+ "/chung/caycoi/" + "caycoi2_"+"traidat" + ".png");

        light = new Texture("hieuung/hieuungmap/light.png");
        khoi = new Texture("hieuung/hieuungmap/khoimay.png");

        ruongdo = new Texture("map/traidat/chung/trangtri/ruongdo.png");
        nhagohan = new Texture("map/traidat/chung/nhacua/nhacua2_earth.png");

        String id = "caitrang_goku_black_rose";

        NhanVatCauHinh config = NhanVatXuLy.xuly_id(id);

        NhanVat goku = new NhanVat(
            100, 175,
            config.dau_dung, config.dau_chay,
            config.than_dung, config.than_nhay, config.than_roi, config.than_chay,
            config.chan_dung, config.chan_nhay, config.chan_roi, config.chan_chay,
            config.than_bay, config.chan_bay,
            config.lechMap
        );
        nhanVat = goku;

        npcdau = new Texture("nhanvat/npc/ong_gohan/dau.png");
        npcthan = new Texture("nhanvat/npc/ong_gohan/than.png");
        npcchan = new Texture("nhanvat/npc/ong_gohan/chan.png");

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new ThaoTac(nhanVat, hudRenderer));
    }

    private void drawText(BitmapFont font, String text, float x, float y, Color color) {
        font.setColor(color);
        layout.setText(font, text);
        font.draw(batch, layout, x, y);
        font.setColor(Color.WHITE);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Cập nhật camera theo vị trí nhân vật
        float targetX = nhanVat.getX();
        float targetY = nhanVat.getY();

        // Giới hạn camera trong vùng bản đồ (1420x760)
        camManager.updateMainCamera(nhanVat.getX(), nhanVat.getY(), 1420, 760);
        batch.setProjectionMatrix(camManager.camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Chỉnh màu shapeRenderer theo hành tinh
        HanhTinhDuocChon = 0;
        switch (HanhTinhDuocChon) {
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
        shapeRenderer.setColor(5 / 255f, 194 / 255f, 168 / 255f, 1);
        shapeRenderer.end();

        thoiGianTichLuy += delta * 15f;
        nhanVat.capNhat();

        // 1. Vẽ thế giới (map, nhân vật,...)
        batch.begin();
        // background xa
        float camOffsetY = camManager.getOffsetY();

        // Layer 1: Sky + nuixa
        for (int i = 0; i < 6; i++) {
            float skyY = 310 + camOffsetY * 1.0f;
            batch.draw(sky, i * 255, skyY, 255, 150);
            batch.draw(nuixa, i * 255, skyY, 255, 150);
        }

        // Layer 2: Nui
        for (int i = 0; i < 4; i++) {
            float nuiY = 280 + camOffsetY * 0.8f;
            batch.draw(nui, i * 510, nuiY, 510, 170);
        }

        // Layer 3: Nuicay & Nuithap
        for (int i = 0; i < 5; i++) {
            float nuicayY = 215 + camOffsetY * 0.5f;
            float nuithapY = 145 + camOffsetY * 0.3f;
            batch.draw(nuicay, i * 340, nuicayY, 340, 190);
            batch.draw(nuithap, i * 340, nuithapY, 340, 190);
        }

        //background gần
        batch.draw(caycoi1,960,200,230,234);
        batch.draw(nhagohan,250,200,246,224);
        for (int i = 0; i < 24; i++) {
            batch.draw(cayco, i * 75, 200, 50, 50);
        }

        for (int i = 0; i < 28; i++) {
            batch.draw(cayco1, i * 50 , 200, 50, 12);
        }


        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                batch.draw(mdtd[j], j * 50 + i * 250, 150, 50, 50);
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                batch.draw(dtd[j], j * 50 + i * 150, 100, 50, 50);
                batch.draw(ldtd[j], j * 50 + i * 150, 75, 50, 50);
                batch.draw(ldtd[2-j], j * 50 + i * 150, 25, 50, 50);
                batch.draw(ldtd[2-j], j * 50 + i * 150, 0, 50, 50);
            }
        }
        batch.draw(light,150,340,600,500);
        batch.draw(ruongdo,120,190,50,35);
        tenNpc(font,"Rương đồ",120,230,50,35);
        batch.draw(caycoi2,500,-130,200,200);
        batch.draw(caycoi2,630,-80,200,200);
        batch.draw(dochanhtinh,670,0,400,165);
        batch.draw(caycoi2,850,-130,200,200);
        batch.setColor(50f, 50f, 50f, 0.65f);
        batch.draw(khoi, 400, -30, 1150, 350);
        batch.draw(khoi, 670, -30, 1150, 350);
        batch.draw(khoi, -50, -40, 1150, 450);
        batch.draw(khoi, -50, -20, 1150, 400);
        batch.draw(khoi, 0, -50, 1150, 350);
        batch.draw(khoi, -50, -50, 1150, 300);
        batch.draw(khoi, 120, -75, 1150, 350);

        batch.setColor(1f, 1f, 1f, 1f); // Trả về mặc định
        veNhanVatDung(batch, 400, 188 , npcdau,npcthan,npcchan,0f,8.5f,0f,-20f);
        tenNpc(font,"Ông Gôhan",400,260,40,30);
        nhanVat.ve(batch, thoiGianTichLuy);
        batch.end();

        // 2. Vẽ UI cố định
        batch.setProjectionMatrix(camManager.uiCamera.combined);
        batch.begin();
        hudRenderer.render(batch);
        hudRenderer.update(delta);
        batch.end();

    }
    private void veNhanVatDung(SpriteBatch batch, float x, float y, Texture dau,Texture than, Texture chan ,float thanXOffset,float thanYOffset , float dauXOffset ,float dauYOffset) {
        float doDaoDong = (float) Math.sin(thoiGianTichLuy) * 1.08f;
        float scale = 0.5f;

        float chanW = chan.getWidth() * scale;
        float chanH = chan.getHeight() * scale;
        float thanW = than.getWidth() * scale;
        float thanH = than.getHeight() * scale;
        float dauW = dau.getWidth() * scale;
        float dauH = dau.getHeight() * scale;

        batch.draw(chan, x, y, chanW, chanH);

        float thanX = x + chanW / 2f - thanW / 2f;
        float thanY = y + chanH + doDaoDong;

        float dauX = x + chanW / 2f - dauW / 2f;
        float dauY = thanY + thanH;

        batch.draw(dau, dauX + dauXOffset, dauY + dauYOffset, dauW, dauH);
        batch.draw(than, thanX + thanXOffset, thanY - 10.2f + thanYOffset, thanW, thanH);
    }
    private void tenNpc(BitmapFont font,String ten,float toadoX,float toadoY, float width,float height){
        layout.setText(font,ten);
        drawText(font, ten, toadoX + (width - layout.width) / 2, toadoY + height, Color.YELLOW);
    }
    private SkillIcon[] loadSkillIcons(String hanhTinh) {
        SkillIcon[] icons = new SkillIcon[5];
        for (int i = 0; i < 5; i++) {
            String path = "kynang/iconkynang/"+hanhTinh+"/skill" + (i + 1) + "_" + hanhTinh.toLowerCase() + ".png";
            icons[i] = new SkillIcon(path);
        }
        return icons;
    }

    @Override public void resize(int width, int height) {
        camManager.resize(width, height);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
