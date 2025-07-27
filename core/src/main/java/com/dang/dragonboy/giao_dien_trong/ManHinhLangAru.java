package com.dang.dragonboy.giao_dien_trong;

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
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        thoiGianTichLuy += delta * 15f;
        camManager.updateMainCamera(nhanVat.getX(), nhanVat.getY(), rongMap, caoMap);
        nhanVat.capNhat();
        shapeRenderer.setProjectionMatrix(camManager.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.rect(0, 0, 800, 30);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.rect(800, 0, 300, 60);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.rect(1100, 0, 400, 10);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 1f);
        shapeRenderer.rect(500, 380, 200, 70);
        shapeRenderer.end();
        batch.setProjectionMatrix(camManager.camera.combined);
        batch.begin();
        layout.setText(fontDauThan,"Làng Aru Cập Nhật Sau");
        fontDauThan.draw(batch, layout, 420, 300);

        nhanVat.ve(batch, thoiGianTichLuy);
        nhanVat.veDiemCanDen(batch);
        batch.end();

        batch.setProjectionMatrix(camManager.uiCamera.combined);
        batch.begin();
        hud.update(delta); // cập nhật trạng thái HUD
        hud.render(batch);        // vẽ HUD
        hud.renderPopup(batch);   // vẽ popup
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        nen.dispose();
    }
}
