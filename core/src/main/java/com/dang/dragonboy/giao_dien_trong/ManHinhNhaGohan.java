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
import java.util.List;
import java.util.Map;

//He thong
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.he_thong.ThaoTac;
import com.dang.dragonboy.he_thong.ThongTinChuyenMap;
//Giao dien ngoai
import com.dang.dragonboy.giao_dien_ngoai.ManHinhSplash;
//NhanVat
import com.dang.dragonboy.nhan_vat.MultiplayerRenderer;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
//HUD
import com.dang.dragonboy.hien_thi.QuanLyCamera;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.hien_thi.SkillNhanVat;
import com.dang.dragonboy.websocket.GameSocket;
import com.dang.dragonboy.xu_ly_map.MapCoBan;
import com.dang.dragonboy.xu_ly_map.MapNhaGohan;
// dữ liệu
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
//xu ly map
import com.dang.dragonboy.xu_ly_map.npc.LoaiNPC;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.NpcTaiAnh;
import com.dang.dragonboy.xu_ly_map.npc.NpcOffset;


public class ManHinhNhaGohan implements Screen {
    //quan trong
    private final Main game;
    private final NhanVat nhanVat;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private BitmapFont font, fontText , fontDauThan;
    private final GlyphLayout layout;
    //camera
    private final QuanLyCamera camManager;
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

    private Texture nutdn, nutclick;
    private float thoiGianHienNutClick = 0;
    private int HanhTinhDuocChon;
    private float thoiGianTichLuy = 0;

    static MapCoBan map, mapLangAru;
    private List<Npc> danhSachNpc;
    private Map<String, NpcTaiAnh> npcTaiAnhMap;

    //HUD
    private VeHUD hudRenderer;

    public ManHinhNhaGohan(Main game , String tenNhanVat, String hanhtinh , String nhanvat, ThongTinChuyenMap thongtin) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        if (thongtin==null) {
            camManager = new QuanLyCamera();
        } else {
            camManager = thongtin.camManager;
        }

        layout = new GlyphLayout();

        if (thongtin == null) {
            hudRenderer = new VeHUD(layout);
            // load skill + thuộc tính nhân vật
            //NhanVatCauHinh config = Doi_avt_ao_quan(hanhtinh,nhanvat+"_base","set_cam","set_cam") ;
            NhanVatCauHinh config = Doi_avt_ao_quan(hanhtinh, nhanvat + "_base", "set_base", "set_base");
            NhanVat haidang = new NhanVat(
                100, 175,
                config.dau_dung, config.dau_chay,
                config.than_dung, config.than_nhay, config.than_roi, config.than_chay,
                config.chan_dung, config.chan_nhay, config.chan_roi, config.chan_chay,
                config.than_bay, config.chan_bay,
                config.chan_gong,config.than_thu,
                config.lechMap,
                config.avt,
                null, null, null, null, null, null, null, null,
                1,
                hanhtinh, nhanvat
            );
            nhanVat = haidang;
            nhanVat.setTen(tenNhanVat); // set tên nhân vật trong nhanvat.java
            hudRenderer.setNhanVat(nhanVat);// load cái này để đổi avt theo ct
            hudRenderer.setCamera(camManager);
            nhanVat.setHUD(hudRenderer);
            SkillNhanVat[] traidatIcons = loadSkillIcons("xayda");
            hudRenderer.setSkillIcons(traidatIcons);
            // load du lieu nguoi dung
            int[] capSkill = new int[9];
            for (int i = 0; i < 9; i++) {
                capSkill[i] = nhanVat.getCapSkill(i + 1); // nếu skill 1-9
            }
            String[] tenSkill = new String[9];
            for (int i = 0; i < 9; i++) {
                tenSkill[i] = nhanVat.getTenSkill(i + 1, "xayda"); // nếu skill 1-9
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
                nhanVat.getSucDanhNhanVat(), nhanVat.getGiapNhanVat(),
                nhanVat.getChiMangGoc(), nhanVat.getChiMangNhanVat(),
                nhanVat.getSatThuongChiMang(),
                nhanVat.getTiemNangNhanVat(), nhanVat.getDiemSoiDongNhanVat(),
                nhanVat.getSoDauThan(),
                nhanVat.getVang(),
                nhanVat.getNgoc(),
                nhanVat.getCapBac(),
                capSkill, tenSkill, motaSkill,
                nhanVat.getCapcaydau(),
                nhanVat.getGiamSatThuongNhanVat()
            );
            hudRenderer.setDuLieuNguoiChoi(duLieu);
            // Tạo map và load địa hình
            map = new MapNhaGohan();
            map.khoiTao(() -> {
                // callback này chạy trên GL thread khi API xong
                // lúc này splash vẫn đang hiện → che hoàn toàn
                this.danhSachNpc = map.LayDanhSachNpc();
                this.npcTaiAnhMap = map.getNpcTaiAnhMap();
                // KHÔNG setup npc.setNhanVat ở đây vì nhanVat chưa chắc ready
                // chỉ lưu data thôi
                for (Npc npc : danhSachNpc) {
                    NpcTaiAnh taiAnhNpc = npcTaiAnhMap.get(npc.getTen());
                    NpcOffset offsetNpc = map.getNpcOffset(npc.getTen());
                    npc.setNpcTaiAnh(taiAnhNpc);
                    npc.setNpcOffset(offsetNpc);
                    npc.setNhanVat(nhanVat);
                }
            });
        } else {
            this.hudRenderer = thongtin.hud;
            this.nhanVat = thongtin.nhanVat;
            if ("langaru".equals(thongtin.mapTruoc)){
                thongtin.nhanVat.datToaDo(875,175);
                if (thongtin.hud.getDuLieuNguoiChoi().coDeTu()) {
                    thongtin.hud.getDuLieuNguoiChoi().deTu.datToaDo(875 + (nhanVat.getFlipX() ? 50f : -50f), 175);
                }
                this.mapLangAru = thongtin.mapTr;
                GameSocket.guiSetMap( "Làng Aru", "Nhà Gôhan", thongtin.nhanVat);
            }
            if (thongtin.mapSau != null) {
                this.map = thongtin.mapSau;
            } else {
                this.map = new MapNhaGohan();
                map.khoiTao(() -> {
                    // callback này chạy trên GL thread khi API xong
                    // lúc này splash vẫn đang hiện → che hoàn toàn
                    this.danhSachNpc = map.LayDanhSachNpc();
                    this.npcTaiAnhMap = map.getNpcTaiAnhMap();
                    // KHÔNG setup npc.setNhanVat ở đây vì nhanVat chưa chắc ready
                    // chỉ lưu data thôi
                    for (Npc npc : danhSachNpc) {
                        NpcTaiAnh taiAnhNpc = npcTaiAnhMap.get(npc.getTen());
                        NpcOffset offsetNpc = map.getNpcOffset(npc.getTen());
                        npc.setNpcTaiAnh(taiAnhNpc);
                        npc.setNpcOffset(offsetNpc);
                        npc.setNhanVat(nhanVat);
                    }
                });
            }
            if (daAnDuiGa()) {
                map.themNpc("dui_ga", LoaiNPC.DUIGA, 1178, 205);
            }
        }
        this.hudRenderer.mapHienTai = map;
        capcaydau = nhanVat.getCapcaydau();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new ThaoTac(nhanVat, hudRenderer,camManager));

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
        for (int i = 0; i < 4; i++) {
            lua[i] = new Texture( "hieuung/hieuungmap/lua"+(i+1)+".png");
        }
        cui_dot_lua = new Texture("map/"+"traidat"+"/chung/trangtri/cuinuongduiga.png");
        duiga = new Texture("map/"+"traidat"+"/chung/trangtri/duiga.png");
        for (int i = 0; i < 7; i++) {
            caccaydau[i] = new Texture( "map/"+"traidat"+"/chung/trangtri/caydau"+(i+1)+".png");
        }

        ruongdo = new Texture("map/"+"traidat"+"/chung/trangtri/ruongdo.png");
        nhagohan = new Texture("map/"+"traidat"+"/chung/nhacua/nhacua2_earth.png");

        nhanVat.setDanhSachDat(map.LayDanhSachDat());
        nhanVat.setGioiHanToaDo(map.getChieuRongMap(), map.getChieuCaoMap(),5,0);
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
        camManager.updateMainCamera(nhanVat.getX(), nhanVat.getY(), 1420, 760,0,0);
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
        if (thoiGianTichLuy > 1_000_000f) thoiGianTichLuy = 0f;
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
        float camOffsetX = camManager.getOffsetX();

        // Layer 1: Sky + nuixa
        for (int i = 0; i < 6; i++) {
            float skyY = 310 + camOffsetY * 1.0f;
            batch.draw(sky, i * 255 + camOffsetX*0.95f, skyY, 255, 150);
            batch.draw(nuixa, i * 255 + camOffsetX*0.95f, skyY, 255, 150);
        }

        // Layer 2: Nui
        for (int i = 0; i < 4; i++) {
            float nuiY = 280 + camOffsetY * 0.8f;
            batch.draw(nui, i * 510 + camOffsetX*0.9f, nuiY, 510, 170);
        }

        // Layer 3: Nuicay & Nuithap
        for (int i = 0; i < 5; i++) {
            float nuicayY = 215 + camOffsetY * 0.5f;
            float nuithapY = 145 + camOffsetY * 0.3f;
            batch.draw(nuicay, i * 340 + camOffsetX*0.8f, nuicayY, 340, 190);
            batch.draw(nuithap, i * 340 + camOffsetX*0.65f, nuithapY, 340, 190);
        }

        batch.end();
        if (hudRenderer.timeHienRongThan<=300-2.1f && hudRenderer.timeHienRongThan>0) {
            shapeRenderer.setProjectionMatrix(camManager.uiCamera.combined);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            if (hudRenderer.ngocRongUoc.equals("1saoden")) {
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
        batch.draw(caycoi2,500,-130,200,200);
        batch.draw(caycoi2,630,-80,200,200);
        batch.draw(dochanhtinh,670,0,400,165);
        batch.draw(caycoi2,850,-130,200,200);

        for (int i = 0; i < 5; i++) {
            batch.draw(khoi, i*287, 0, 287, 170);
            batch.draw(khoi, -10+i*287, 0, 287, 200);
        }

        batch.draw(cui_dot_lua,1170,180,66,48);
        Texture luaa = lua[frameLua];
        float luaaW = luaa.getWidth() * 0.5f;
        float luaaH = luaa.getHeight() * 0.5f;
        batch.draw(luaa,1203,203,luaaW,luaaH);

        if (danhSachNpc != null) {
            map.capNhatNpc();
            for (int i = 0; i < danhSachNpc.size(); i++) {
                danhSachNpc.get(i).checkClick(nhanVat.x_check_npc, nhanVat.y_check_npc);
                danhSachNpc.get(i).ve(batch, thoiGianTichLuy);
            }
        }

        MultiplayerRenderer.render(batch, thoiGianTichLuy, hudRenderer);
        nhanVat.ve(batch, thoiGianTichLuy);

        boolean duocVeDiemCanDen = true;
        if (danhSachNpc != null) {
            for (Npc npc : danhSachNpc) {
                if (npc.dangClickNpc) {
                    duocVeDiemCanDen = false;
                }
            }
        }
        if (duocVeDiemCanDen) {
            nhanVat.veDiemCanDen(batch);
        }
        batch.end();
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
                ThongTinChuyenMap info = new ThongTinChuyenMap(hudRenderer.getDuLieuNguoiChoi(),nhanVat, "nhagohan",hudRenderer,camManager, map, mapLangAru);
                game.setScreen(new ManHinhSplash(game, new ManHinhLangAru(game, info)));
            }
        }
        // 2. Vẽ UI cố định
        batch.setProjectionMatrix(camManager.uiCamera.combined);
        batch.begin();
        hudRenderer.render(batch);
        hudRenderer.update(delta);
        batch.end();
    }

    public boolean daAnDuiGa() {
        List<Npc> npcs = map.LayDanhSachNpc();
        for (Npc npc : npcs) {
            if (npc.getTen().equals("dui_ga")) {
                return false;
            }
        }
        return true;
    }

    private SkillNhanVat[] loadSkillIcons(String hanhTinh) {
        SkillNhanVat[] skillIcons = new SkillNhanVat[9];
        for (int i = 0; i < 9; i++) {
            String path = "kynang/iconkynang/"+hanhTinh+"/skill" + (i + 1) + "_" + hanhTinh.toLowerCase() + ".png";
            skillIcons[i] = new SkillNhanVat(path);
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
        for (NpcTaiAnh npcTaiAnhItem : npcTaiAnhMap.values()) {
            npcTaiAnhItem.dispose();
        }

        for (Texture tex : mdtd) tex.dispose();
        for (Texture tex : dtd) tex.dispose();
        for (Texture tex : ldtd) tex.dispose();
        for (Texture tex : lua) tex.dispose();
        for (Texture tex : caccaydau) tex.dispose();
        hudRenderer.dispose();
        nhanVat.dispose();
    }
}
