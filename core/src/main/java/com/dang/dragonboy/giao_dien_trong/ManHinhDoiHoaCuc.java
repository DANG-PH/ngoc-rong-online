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
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.giao_dien_ngoai.ManHinhSplash;
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.he_thong.ThaoTac;
import com.dang.dragonboy.he_thong.ThongTinChuyenMap;
import com.dang.dragonboy.hien_thi.SkillNhanVat;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.MultiplayerRenderer;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
import com.dang.dragonboy.websocket.GameSocket;
import com.dang.dragonboy.xu_ly_map.MapCoBan;
import com.dang.dragonboy.xu_ly_map.MapDoiHoaCuc;
import com.dang.dragonboy.hien_thi.QuanLyCamera;
import com.dang.dragonboy.xu_ly_map.MapLangAru;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.NpcOffset;
import com.dang.dragonboy.xu_ly_map.npc.NpcTaiAnh;

import java.util.List;
import java.util.Map;


public class ManHinhDoiHoaCuc implements Screen {
    private Main game;
    private ShapeRenderer shapeRenderer;
    private ThongTinChuyenMap thongtin;
    private SpriteBatch batch;
    private BitmapFont fontDauThan;
    private GlyphLayout layout;
    private float thoiGianTichLuy = 0;
    private NhanVat nhanVat;
    private VeHUD hud;
    private QuanLyCamera camManager;
    private float rongMap,caoMap;

    private Texture mapDoiHoaCuc,mapDoiHoaCucSau,mapDoiHoaCucSau1;
    private Texture sky, nuixa, nui, nuicay, nuithap, thapkirin;
    private Texture[] mdtd = new Texture[5];
    private Texture[] dtd = new Texture[3];
    private Texture[] ldtd = new Texture[3];
    private Texture light;
    private Texture khoi;
    private float scrollX_khoi = 0,scrollX_khoi1 = 0;

    static MapCoBan map, mapLangAru;
    private List<Npc> danhSachNpc;
    private Map<String, NpcTaiAnh> npcTaiAnhMap;

    public ManHinhDoiHoaCuc(Main game, ThongTinChuyenMap thongtin) {
        this.game = game;
        this.thongtin =  thongtin;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        layout = new GlyphLayout();
        if (thongtin != null) {
            if ("langaru".equals(thongtin.mapTruoc)) {
                thongtin.nhanVat.datToaDo(5, 175);
                if (thongtin.hud.getDuLieuNguoiChoi().coDeTu()) {
                    thongtin.hud.getDuLieuNguoiChoi().deTu.datToaDo(5 + (thongtin.nhanVat.getFlipX() ? 50f : -50f), 175);
                }
                mapLangAru = thongtin.mapTr;
                GameSocket.guiSetMap( "Làng Aru", "Đồi Hoa Cúc", thongtin.nhanVat);
            }
            nhanVat = thongtin.nhanVat;
            hud = thongtin.hud;
            camManager = thongtin.camManager;
            // Tạo map và load địa hình
            if (thongtin.mapSau == null) {
                map = new MapDoiHoaCuc();
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
                map = thongtin.mapSau;
            }
            this.rongMap = map.getChieuRongMap();
            this.caoMap = map.getChieuCaoMap();
            this.hud.mapHienTai = map;
        } else {
            hud = new VeHUD(layout);
            camManager = new QuanLyCamera();
            NhanVatCauHinh config = Doi_avt_ao_quan(State_Management.getUserResponse().hanhTinh, State_Management.getUserResponse().nhanVat + "_base", "set_base", "set_base");
            NhanVat haidang = new NhanVat(
                State_Management.getUserResponse().x, State_Management.getUserResponse().y,
                config.dau_dung, config.dau_chay,
                config.than_dung, config.than_nhay, config.than_roi, config.than_chay,
                config.chan_dung, config.chan_nhay, config.chan_roi, config.chan_chay,
                config.than_bay, config.chan_bay,
                config.chan_gong,config.than_thu,
                config.lechMap,
                config.avt,
                null, null, null, null, null, null, null, null,
                1,
                State_Management.getUserResponse().hanhTinh, State_Management.getUserResponse().nhanVat
            );
            nhanVat = haidang;
            nhanVat.setTen(State_Management.getUserResponse().gameName); // set tên nhân vật trong nhanvat.java
            hud.setNhanVat(nhanVat);// load cái này để đổi avt theo ct
            hud.setCamera(camManager);
            nhanVat.setHUD(hud);
            SkillNhanVat[] traidatIcons = loadSkillIcons("xayda");
            hud.setSkillIcons(traidatIcons);
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
            hud.setDuLieuNguoiChoi(duLieu);
            // Tạo map và load địa hình
            map = new MapDoiHoaCuc();
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
            this.rongMap = map.getChieuRongMap();
            this.caoMap = map.getChieuCaoMap();
            this.hud.mapHienTai = map;
            System.out.print("hello");
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new ThaoTac(nhanVat, hud,camManager));
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

        sky = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "sky_" + "traidat" + ".png");
        nuixa = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuixa_" + "traidat" + ".png");
        nui = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nui_" +"traidat" + ".png");
        nuicay = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuicay_" + "traidat" + ".png");
        nuithap = new Texture("hud/giaodienngoai/"+"traidat"+ "/" + "nuithap_" + "traidat" + ".png");
        thapkirin = new Texture("map/traidat/doi_hoa_cuc/thapkirin.png");

        mapDoiHoaCuc = new Texture("map/"+"traidat"+"/doi_hoa_cuc/doihoacuc.png");
        mapDoiHoaCucSau = new Texture("map/"+"traidat"+"/doi_hoa_cuc/doihoacucsau.png");
        mapDoiHoaCucSau1 = new Texture("map/"+"traidat"+"/doi_hoa_cuc/doihoacuctruoc.png");

        light = new Texture("hieuung/hieuungmap/light.png");
        khoi = new Texture("hieuung/hieuungmap/khoimay.png");

        nhanVat.setDanhSachDat(map.LayDanhSachDat());
        nhanVat.setGioiHanToaDo(map.getChieuRongMap(), map.getChieuCaoMap(),5,0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        thoiGianTichLuy += delta * 15f;
        if (thoiGianTichLuy > 1_000_000f) thoiGianTichLuy = 0f;
        camManager.updateMainCamera(nhanVat.getX(), nhanVat.getY(), rongMap, caoMap,0,38+96);

        nhanVat.capNhat();

        float camOffsetY = camManager.getOffsetY();
        float camOffsetX = camManager.getOffsetX();

        shapeRenderer.setProjectionMatrix(camManager.camera.combined);
        shapeRenderer.setColor(5 / 255f, 194 / 255f, 168 / 255f, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(25 / 255f, 176 / 255f, 248 / 255f, 1); // Trái đất
        shapeRenderer.rect(0, 300 + camOffsetY, 3000, 800);
        shapeRenderer.end();

        batch.setProjectionMatrix(camManager.camera.combined);
        batch.begin();
        // background xa

        // Layer 1: Sky + nuixa
        for (int i = 0; i < 12; i++) {
            float skyY = 310 + camOffsetY * 0.95f;
            batch.draw(sky, i * 255 + camOffsetX*0.98f, skyY, 255, 150);
            batch.draw(nuixa, i * 255 + camOffsetX*0.98f, skyY, 255, 150);
        }

        // Layer 2: Nui + thap Kirin
        for (int i = 0; i < 9; i++) {
            float nuiY = 280 + camOffsetY * 0.85f;
            batch.draw(nui, i * 510 + camOffsetX*0.95f, nuiY, 510, 170);
        }
        for (int i = 0; i < 9; i++) {
            float kirinY = 250 + camOffsetY * 0.85f;
            batch.draw(thapkirin,600 + camOffsetX*0.95f,kirinY+i*(thapkirin.getHeight()*0.5f),thapkirin.getWidth()*0.5f,thapkirin.getHeight()*0.5f);
        }
        // Layer 3: Nuicay & Nuithap
        for (int i = 0; i < 12; i++) {
            float nuicayY = 200 + camOffsetY * 0.75f;
            batch.draw(nuicay, i * 340 + camOffsetX*0.9f, nuicayY, 340, 190);
        }
        batch.end();
        float nuiY = 200 + camOffsetY * 0.75f; // Y của ảnh núi
        shapeRenderer.setColor(22f / 255f, 118f / 255f, 21f / 255f, 1f);
        shapeRenderer.setProjectionMatrix(camManager.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, nuiY - 100, 3000, 100); // nằm ngay dưới ảnh
        shapeRenderer.end();
        batch.begin();
        for (int i = 0; i < 10; i++) {
            float nuithapY = 130 + camOffsetY * 0.5f;
            batch.draw(nuithap, i * 340 + camOffsetX*0.8f, nuithapY, 340, 190);
        }
        batch.end();
        float nuiTY = 130 + camOffsetY * 0.5f; // Y của ảnh núi
        shapeRenderer.setColor(23f / 255f, 94f / 255f, 28f / 255f, 1f);
        shapeRenderer.setProjectionMatrix(camManager.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, nuiTY - 500, 3000, 500); // nằm ngay dưới ảnh
        shapeRenderer.end();
        batch.begin();
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
        batch.draw(mapDoiHoaCuc,0,-38-96,mapDoiHoaCuc.getWidth()/2f,mapDoiHoaCuc.getHeight()/2f);
        for (int i = 0; i < 3; i++) {
            batch.draw(light,150+i*(600*1.1f+180),515,600*1.1f,500*1.1f);
        }

        if (danhSachNpc != null) {
            for (int i = 0; i < danhSachNpc.size(); i++) {
                danhSachNpc.get(i).checkClick(nhanVat.x_check_npc, nhanVat.y_check_npc);
                danhSachNpc.get(i).ve(batch, thoiGianTichLuy);
            }
            map.capNhatNpc();
        }

        MultiplayerRenderer.render(batch, thoiGianTichLuy, hud);
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

        batch.draw(mapDoiHoaCucSau,0,-38-96,mapDoiHoaCucSau.getWidth()/2f,mapDoiHoaCucSau.getHeight()/2f);
        batch.draw(mapDoiHoaCucSau1,0-camOffsetX*0.5f-100f,-38-96-50f,mapDoiHoaCucSau1.getWidth()/2f,mapDoiHoaCucSau1.getHeight()/2f);

        batch.end();

        renderCacMap();
        setLaiToaDoNhanVat();
        checkQuaMap();

        batch.begin();

        scrollX_khoi -= 40 * delta;
        scrollX_khoi1 -= 45 * delta;
        if (scrollX_khoi <= -287) scrollX_khoi += 287;
        if (scrollX_khoi1 <= -287) scrollX_khoi1 += 287;
        for (int i = 0; i < 14; i++) {
            batch.draw(khoi, i*287 + scrollX_khoi, -38-96, 287, 200);
            batch.draw(khoi, -10+i*287 + scrollX_khoi1,-38-96, 287, 220);
        }
        batch.end();

        batch.setProjectionMatrix(camManager.uiCamera.combined);
        batch.begin();
        hud.update(delta); // cập nhật trạng thái HUD
        hud.render(batch);        // vẽ HUD
        batch.end();
    }

    public void renderCacMap() {
        float xLangAru = 10;
        if (nhanVat.getX()> xLangAru-150 && nhanVat.getX()< xLangAru-150+450) {
            shapeRenderer.setProjectionMatrix(camManager.camera.combined);
            Gdx.gl.glEnable(GL20.GL_BLEND); // Câu lệnh để pha alpha tùy ý

            shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(xLangAru, 300, 230, 60);
            shapeRenderer.end();

            batch.begin();
            layout.setText(fontDauThan,"Làng Aru");
            fontDauThan.draw(batch,layout,xLangAru+(230-layout.width)/2f,250+65+20);
            batch.end();
        }
    }
    public void setLaiToaDoNhanVat() {
        if (nhanVat.getX()<100 && nhanVat.getY()<300) {
            nhanVat.setGioiHanToaDo(rongMap,caoMap,-200,0);
        } else {
            nhanVat.setGioiHanToaDo(rongMap,caoMap,5,0);
        }
    }

    public void checkQuaMap() {
        if (nhanVat.getX()<-5-nhanVat.getRong() && nhanVat.getY()<300) {
            ThongTinChuyenMap info = new ThongTinChuyenMap(hud.getDuLieuNguoiChoi(),nhanVat, "doihoacuc",hud,camManager, map, mapLangAru);
            game.setScreen(new ManHinhSplash(game, new ManHinhLangAru(game, info)));
        }
    }

    private NhanVatCauHinh Doicaitrang(String TenCaiTrang){
        return NhanVatXuLy.xuly_id("caitrang_"+TenCaiTrang);
    }
    private NhanVatCauHinh Doi_avt_ao_quan(String HanhTinh, String TenAvatar , String ao, String quan){
        return NhanVatXuLy.xuly_id("avatar_"+HanhTinh+"+"+TenAvatar+"+"+ao+"+"+quan);
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

    @Override
    public void dispose() {
        batch.dispose();
    }
}
