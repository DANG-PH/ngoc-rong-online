package com.dang.dragonboy.giao_dien_trong;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dang.dragonboy.giao_dien_ngoai.ManHinhSplash;
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.he_thong.ThongTinChuyenMap;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.MapLangAru;
import com.dang.dragonboy.hien_thi.QuanLyCamera;


public class ManHinhLangAru implements Screen {
    private Main game;
    private ShapeRenderer shapeRenderer;
    private ThongTinChuyenMap thongtin;
    private SpriteBatch batch;
    private Texture nen;
    private BitmapFont font, fontText , fontDauThan;
    private GlyphLayout layout;
    private float thoiGianTichLuy = 0;
    private NhanVat nhanVat;
    private VeHUD hud;
    private QuanLyCamera camManager;
    private float rongMap,caoMap;

    private Texture sky, nuixa, nui, nuicay, nuithap;
    private Texture[] mdtd = new Texture[5];
    private Texture[] dtd = new Texture[3];
    private Texture[] ldtd = new Texture[3];
    private Texture dochanhtinh;
    private Texture cayco, cayco1;
    private Texture caycoi1,caycoi2;
    private Texture light;
    private Texture khoi;

    public ManHinhLangAru(Main game, ThongTinChuyenMap thongtin) {
        this.game = game;
        this.thongtin =  thongtin;
        if ("nhagohan".equals(thongtin.mapTruoc)){
            thongtin.nhanVat.datToaDo(500,300);
            thongtin.nhanVat.setflip("phai");
        }
        nhanVat = thongtin.nhanVat;
        hud = thongtin.hud;
        camManager = thongtin.camManager;
        // Tạo map và load địa hình
        MapLangAru map = new MapLangAru();
        map.taiDuLieuMap();
        nhanVat.setDanhSachDat(map.LayDanhSachDat());
        nhanVat.setGioiHanToaDo(map.getChieuRongMap(), map.getChieuCaoMap());
        this.rongMap = map.getChieuRongMap();
        this.caoMap = map.getChieuCaoMap();
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        // Font có viền đen dành riêng cho dòng chữ "Đậu thần cấp ..."
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchinh.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param2.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ";
        param2.size = 22;
        param2.color = Color.WHITE;
        param2.borderWidth = 1f;
        param2.borderColor = new Color(0.4f, 0.4f, 0.4f, 1f);
        fontDauThan = generator2.generateFont(param2);
        generator2.dispose();
        layout = new GlyphLayout();

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
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        thoiGianTichLuy += delta * 15f;
        if (thoiGianTichLuy > 1_000_000f) thoiGianTichLuy = 0f;
        camManager.updateMainCamera(nhanVat.getX(), nhanVat.getY(), rongMap, caoMap);

        nhanVat.capNhat();

        shapeRenderer.setProjectionMatrix(camManager.camera.combined);
        shapeRenderer.setColor(5 / 255f, 194 / 255f, 168 / 255f, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(25 / 255f, 176 / 255f, 248 / 255f, 1); // Trái đất
        shapeRenderer.rect(0, 460, 1420, 400);
        shapeRenderer.end();

        batch.setProjectionMatrix(camManager.camera.combined);
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

        batch.end();
        if (hud.timeHienRongThan<=300-2.1f && hud.timeHienRongThan>0) {
            shapeRenderer.setProjectionMatrix(camManager.uiCamera.combined);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            if (hud.ngocRongUoc.equals("1saoden")) {
                shapeRenderer.setColor(0f, 0f, 0f, 0.6f);
            } else {
                shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(0,0,1020,610);
            shapeRenderer.end();
            shapeRenderer.setProjectionMatrix(camManager.camera.combined);
        }
        batch.begin();

        //background gần
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
        for (int i = 0; i < 5; i++) {
            batch.draw(khoi, i*287, 0, 287, 170);
            batch.draw(khoi, -10+i*287, 0, 287, 200);
        }
        batch.end();
        if (nhanVat.getX()> 610 && nhanVat.getX()< 1060) {
            shapeRenderer.setProjectionMatrix(camManager.camera.combined);
            Gdx.gl.glEnable(GL20.GL_BLEND); // Câu lệnh để pha alpha tùy ý

            shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(760, 300, 230, 60);
            shapeRenderer.end();

            batch.begin();
            layout.setText(fontDauThan,"Nhà Gôhan");
            fontDauThan.draw(batch,layout,760+(230-layout.width)/2f,250+65+20);
            batch.end();
        }
        batch.begin();
        // Kiểm tra nếu đứng trong vùng "Làng Aru" và bấm Enter thì chuyển màn
        if (nhanVat.getX() > 760 && nhanVat.getX() < 990 && nhanVat.getY() >= 0 && nhanVat.getY() <= 400) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                ThongTinChuyenMap info = new ThongTinChuyenMap(nhanVat, "langaru",hud,camManager);
                game.setScreen(new ManHinhSplash(game, new ManHinhChoiTiep(game,nhanVat.getTen(),nhanVat.getHanhtinh(),nhanVat.getNhanvat(),info)));
            }
        }
        nhanVat.ve(batch, thoiGianTichLuy);
        nhanVat.veDiemCanDen(batch);
        batch.end();

        batch.setProjectionMatrix(camManager.uiCamera.combined);
        batch.begin();
        hud.update(delta); // cập nhật trạng thái HUD
        hud.render(batch);        // vẽ HUD
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
