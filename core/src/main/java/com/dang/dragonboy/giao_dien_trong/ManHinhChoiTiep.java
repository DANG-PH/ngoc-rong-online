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
import com.badlogic.gdx.Input;

//He thong
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.he_thong.ThaoTac;
import com.dang.dragonboy.he_thong.ThongTinChuyenMap;
//Giao dien ngoai
import com.dang.dragonboy.giao_dien_ngoai.ManHinhSplash;
//NhanVat
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
//HUD
import com.dang.dragonboy.hien_thi.QuanLyCamera;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.hien_thi.SkillIcon;
import com.dang.dragonboy.xu_ly_map.MapLangAru;
import com.dang.dragonboy.xu_ly_map.MapNhaGohan;
// dữ liệu
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;


public class ManHinhChoiTiep implements Screen {
    //quan trong
    private Main game;
    private NhanVat nhanVat;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font, fontText , fontDauThan;
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

    private Texture[] caccaydau = new Texture[7];
    public int capcaydau;
    private Texture cui_dot_lua;
    private Texture[] lua =  new Texture[4];
    private int frameLua = 0;
    private float timeLua = 0f;
    private Texture duiga;

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

    public ManHinhChoiTiep(Main game , String tenNhanVat, String hanhtinh ,String nhanvat) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        camManager = new QuanLyCamera();

        layout = new GlyphLayout();

        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ";
        param.size = 18;
        font = generator.generateFont(param);
        param.size = 17;
        fontText = generator.generateFont(param);
        generator.dispose();
        // Font có viền đen dành riêng cho dòng chữ "Đậu thần cấp ..."
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchinh.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param2.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể";
        param2.size = 22;
        param2.color = Color.WHITE;
        param2.borderWidth = 1f;
        param2.borderColor = new Color(0.4f, 0.4f, 0.4f, 1f);

        fontDauThan = generator2.generateFont(param2);
        generator2.dispose();

        nutdn = new Texture("hud/giaodienngoai/chung/nutdangnhap3.png");
        nutclick = new Texture("hud/giaodienngoai/chung/nutclick2.png");
        muiTen = new Texture("hud/giaodienngoai/chung/muitenvang.png");


        sky = new Texture("hud/giaodienngoai/"+hanhtinh+ "/" + "sky_" + hanhtinh + ".png");
        nuixa = new Texture("hud/giaodienngoai/"+hanhtinh+ "/" + "nuixa_" + hanhtinh + ".png");
        nui = new Texture("hud/giaodienngoai/"+hanhtinh+ "/" + "nui_" +hanhtinh + ".png");
        nuicay = new Texture("hud/giaodienngoai/"+hanhtinh+ "/" + "nuicay_" + hanhtinh + ".png");
        nuithap = new Texture("hud/giaodienngoai/"+hanhtinh+ "/" + "nuithap_" + hanhtinh + ".png");

        cayco = new Texture("map/"+hanhtinh+ "/chung/cayco/" + "cayco3_" + hanhtinh + ".png");
        cayco1 = new Texture("map/"+hanhtinh+ "/chung/cayco/"  + "cayco5_" + hanhtinh + ".png");

        for (int i = 0; i < 5; i++) {
            mdtd[i] = new Texture("map/"+hanhtinh+ "/chung/dat/"  + "matdat_" + hanhtinh + (i + 1) + ".png");
        }
        for (int i = 0; i < 3; i++) {
            dtd[i] = new Texture("map/"+hanhtinh+ "/chung/dat/"  + "dat_" + hanhtinh+ (i + 1) + ".png");
            ldtd[i] = new Texture("map/"+hanhtinh+ "/chung/dat/"  + "longdat_" + hanhtinh+ (i + 1) + ".png");
        }
        dochanhtinh = new Texture("map/"+hanhtinh+ "/chung/dat/"  + "doc_" + hanhtinh + ".png");

        caycoi1 = new Texture("map/"+hanhtinh+ "/chung/caycoi/"  + "caycoi1_"+hanhtinh + ".png") ;
        caycoi2 = new Texture("map/"+hanhtinh+ "/chung/caycoi/" + "caycoi2_"+hanhtinh + ".png");

        light = new Texture("hieuung/hieuungmap/light.png");
        khoi = new Texture("hieuung/hieuungmap/khoimay.png");
        for (int i = 0; i < 4; i++) {
            lua[i] = new Texture( "hieuung/hieuungmap/lua"+(i+1)+".png");
        }
        cui_dot_lua = new Texture("map/"+hanhtinh+"/chung/trangtri/cuinuongduiga.png");
        duiga = new Texture("map/"+hanhtinh+"/chung/trangtri/duiga.png");
        for (int i = 0; i < 7; i++) {
            caccaydau[i] = new Texture( "map/"+hanhtinh+"/chung/trangtri/caydau"+(i+1)+".png");
        }

        ruongdo = new Texture("map/"+hanhtinh+"/chung/trangtri/ruongdo.png");
        nhagohan = new Texture("map/"+hanhtinh+"/chung/nhacua/nhacua2_earth.png");
        hudRenderer = new VeHUD(layout);
        // load skill + thuộc tính nhân vật
        SkillIcon[] traidatIcons = loadSkillIcons("xayda");
        hudRenderer.setSkillIcons(traidatIcons);
        //NhanVatCauHinh config = Doi_avt_ao_quan(hanhtinh,nhanvat+"_base","set_cam","set_cam") ;
        NhanVatCauHinh config = Doicaitrang("vegito_xeno");
        NhanVat haidang = new NhanVat(
            100, 175,
            config.dau_dung, config.dau_chay,
            config.than_dung, config.than_nhay, config.than_roi, config.than_chay,
            config.chan_dung, config.chan_nhay, config.chan_roi, config.chan_chay,
            config.than_bay, config.chan_bay,
            config.lechMap,
            config.avt,
            6
        );
        nhanVat = haidang;
        nhanVat.setTen(tenNhanVat); // set tên nhân vật trong nhanvat.java
        hudRenderer.setNhanVat(nhanVat);// load cái này để đổi avt theo ct
        // load du lieu nguoi dung
        int[] capSkill = new int[9];
        for (int i = 0; i < 9; i++) {
            capSkill[i] = nhanVat.getCapSkill(i + 1); // nếu skill 1-9
        }
        String[] tenSkill = new String[9];
        for (int i = 0; i < 9; i++) {
            tenSkill[i] = nhanVat.getTenSkill(i+1,"xayda"); // nếu skill 1-9
        }
        String[][] motaSkill = new String[9][];
        for (int i = 0; i < 9; i++) {
            motaSkill[i] = nhanVat.getMotaSkill(i + 1, "xayda");
        }
        DuLieuNguoiChoi duLieu = new DuLieuNguoiChoi(
            nhanVat.getTen(),
            nhanVat.getSucManh(),
            nhanVat.getTheLuc(),
            nhanVat.getHpHienTai(), nhanVat.getHpToiDa(), nhanVat.getHpGoc(),
            nhanVat.getKiHienTai(), nhanVat.getKiToiDa(), nhanVat.getKiGoc(),
            nhanVat.getSucDanhGoc(), nhanVat.getGiapGoc(),
            nhanVat.getSucDanhNhanVat(),nhanVat.getGiapNhanVat(),
            nhanVat.getChiMangGoc(), nhanVat.getChiMangNhanVat(),
            nhanVat.getSatThuongChiMang(),
            nhanVat.getTiemNangNhanVat(),nhanVat.getDiemSoiDongNhanVat(),
            nhanVat.getSoDauThan(),
            nhanVat.getVang(),
            nhanVat.getNgoc(),
            nhanVat.getCapBac(),
            capSkill,tenSkill,motaSkill,
            nhanVat.getCapcaydau()
        );
        hudRenderer.setDuLieuNguoiChoi(duLieu);
        capcaydau = duLieu.getCapCayDau();
        // Tạo map và load địa hình
        MapNhaGohan map = new MapNhaGohan();
        map.taiDuLieuMap();
        nhanVat.setDanhSachDat(map.LayDanhSachDat());
        nhanVat.setGioiHanToaDo(map.getChieuRongMap(), map.getChieuCaoMap());
        nhanVat.doiVanBay("phuong_hoang_lua");
        npcdau = new Texture("nhanvat/npc/ong_gohan/dau.png");
        npcthan = new Texture("nhanvat/npc/ong_gohan/than.png");
        npcchan = new Texture("nhanvat/npc/ong_gohan/chan.png");

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new ThaoTac(nhanVat, hudRenderer));
    }
    private NhanVatCauHinh Doicaitrang(String TenCaiTrang){
        return NhanVatXuLy.xuly_id("caitrang_"+TenCaiTrang);
    }
    private NhanVatCauHinh Doi_avt_ao_quan(String HanhTinh, String TenAvatar , String ao, String quan){
        return NhanVatXuLy.xuly_id("avatar_"+HanhTinh+"+"+TenAvatar+"+"+ao+"+"+quan);
    }
    private void drawText(BitmapFont font, String text, float x, float y, Color color) {
        font.setColor(color);
        layout.setText(font, text);
        font.draw(batch, layout, x, y);
        font.setColor(Color.WHITE);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Cập nhật camera theo vị trí nhân vật
        float targetX = nhanVat.getX();
        float targetY = nhanVat.getY();

        // Giới hạn camera trong vùng bản đồ (1420x760)
        camManager.updateMainCamera(nhanVat.getX(), nhanVat.getY(), 1420, 760);
        batch.setProjectionMatrix(camManager.camera.combined);
        shapeRenderer.setColor(5 / 255f, 194 / 255f, 168 / 255f, 1);
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
        shapeRenderer.rect(0, 460, 1420, 400);
        shapeRenderer.end();

        //thoi gian
        thoiGianTichLuy += delta * 15f;
        timeLua += delta;
        if (timeLua > 0.12f){
            frameLua = (frameLua + 1)%lua.length;
            timeLua = 0;
        }

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
        // cây đậu + đùi gà + lửa + củi
        float caydauW = caccaydau[capcaydau].getWidth() * 0.5f;
        float caydauH = caccaydau[capcaydau].getHeight() * 0.48f;
        batch.draw(caccaydau[capcaydau],600,192,caydauW, caydauH);
        String text = "Đậu thần cấp " + (capcaydau + 1);
        layout.setText(fontDauThan, text);
        fontDauThan.draw(batch, layout,
            600 + (caccaydau[capcaydau].getWidth() * 0.5f - layout.width) / 2f,
            192 + caccaydau[capcaydau].getHeight() * 0.5f + 15
        );
        batch.draw(cui_dot_lua,1170,180,66,48);
        Texture luaa = lua[frameLua];
        float luaaW = luaa.getWidth() * 0.5f;
        float luaaH = luaa.getHeight() * 0.5f;
        batch.draw(luaa,1203,203,luaaW,luaaH);
        batch.draw(duiga,1178,205,33,24);
        nhanVat.ve(batch, thoiGianTichLuy);
        batch.end();
        if (targetX > 410 && targetX < 850) {
            shapeRenderer.setProjectionMatrix(camManager.camera.combined);
            Gdx.gl.glEnable(GL20.GL_BLEND); // Câu lệnh để pha alpha tùy ý

            shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(
                600 + (caccaydau[capcaydau].getWidth() * 0.5f) / 2f -90,
                192 + caccaydau[capcaydau].getHeight() * 0.5f + 35,
                180,
                80);
            shapeRenderer.end();

            batch.begin();
            layout.setText(fontDauThan,"Có thể thu hoạch");
            fontDauThan.draw(batch,layout,
                600 + (caccaydau[capcaydau].getWidth() * 0.5f - layout.width) / 2f,
                192 + caccaydau[capcaydau].getHeight() * 0.5f + 90
            );
            layout.setText(fontDauThan,(2*(capcaydau+1)+3)+"/"+(2*(capcaydau+1)+3));
            fontDauThan.draw(batch,layout,
                600 + (caccaydau[capcaydau].getWidth() * 0.5f - layout.width) / 2f,
                192 + caccaydau[capcaydau].getHeight() * 0.5f + 65
            );
            batch.end();
        }
        if (targetX > 610 && targetX < 1060) {
            shapeRenderer.setProjectionMatrix(camManager.camera.combined);
            Gdx.gl.glEnable(GL20.GL_BLEND); // Câu lệnh để pha alpha tùy ý

            shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(760, 300, 230, 60);
            shapeRenderer.end();

            batch.begin();
            layout.setText(fontDauThan,"Làng Aru");
            fontDauThan.draw(batch,layout,760+(230-layout.width)/2f,250+65+20);
            batch.end();
        }
        // Kiểm tra nếu đứng trong vùng "Làng Aru" và bấm Enter thì chuyển màn
        if (targetX > 760 && targetX < 990 && targetY >= 0 && targetY <= 400) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                ThongTinChuyenMap info = new ThongTinChuyenMap(nhanVat, "nhagohan",hudRenderer);
                game.setScreen(new ManHinhSplash(game, new ManHinhLangAru(game, info)));
            }
        }
        // 2. Vẽ UI cố định
        batch.setProjectionMatrix(camManager.uiCamera.combined);
        batch.begin();
        hudRenderer.render(batch);
        hudRenderer.update(delta);
        hudRenderer.renderPopup(batch);
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
        SkillIcon[] skillIcons = new SkillIcon[9];
        for (int i = 0; i < 9; i++) {
            String path = "kynang/iconkynang/"+hanhTinh+"/skill" + (i + 1) + "_" + hanhTinh.toLowerCase() + ".png";
            skillIcons[i] = new SkillIcon(path);
        }
        return skillIcons;
    }

    @Override public void resize(int width, int height) {
        camManager.resize(width, height);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        fontText.dispose();
        nutdn.dispose();
        nutclick.dispose();
        muiTen.dispose();
        sky.dispose();
        nuixa.dispose();
        nui.dispose();
        nuicay.dispose();
        nuithap.dispose();
        cayco.dispose();
        cayco1.dispose();
        dochanhtinh.dispose();
        caycoi1.dispose();
        caycoi2.dispose();
        light.dispose();
        khoi.dispose();
        ruongdo.dispose();
        nhagohan.dispose();
        cui_dot_lua.dispose();
        duiga.dispose();
        npcdau.dispose();
        npcthan.dispose();
        npcchan.dispose();

        for (Texture tex : mdtd) tex.dispose();
        for (Texture tex : dtd) tex.dispose();
        for (Texture tex : ldtd) tex.dispose();
        for (Texture tex : lua) tex.dispose();
        for (Texture tex : caccaydau) tex.dispose();
    }
}
