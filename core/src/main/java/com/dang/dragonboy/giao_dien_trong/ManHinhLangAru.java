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
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.he_thong.ThongTinChuyenMap;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.MapLangAru;


public class ManHinhLangAru implements Screen {
    private Main game;
    private ThongTinChuyenMap thongtin;
    private SpriteBatch batch;
    private Texture nen;
    private BitmapFont font, fontText , fontDauThan;
    private GlyphLayout layout;
    private float thoiGianTichLuy = 0;
    private NhanVat nhanVat;

    public ManHinhLangAru(Main game, ThongTinChuyenMap thongtin) {
        this.game = game;
        this.thongtin =  thongtin;
        if ("nhagohan".equals(thongtin.mapTruoc)){
            thongtin.nhanVat.datToaDo(500,300);
        }
        nhanVat = thongtin.nhanVat;
        // Tạo map và load địa hình
        MapLangAru map = new MapLangAru();
        map.taiDuLieuMap();

        nhanVat.setDanhSachDat(map.getDanhSachDat());
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
        batch.begin();
        layout.setText(fontDauThan,"Làng Aru Cập Nhật Sau");
        fontDauThan.draw(batch,layout,420,300);
        nhanVat.capNhat();
        nhanVat.ve(batch, thoiGianTichLuy);
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
