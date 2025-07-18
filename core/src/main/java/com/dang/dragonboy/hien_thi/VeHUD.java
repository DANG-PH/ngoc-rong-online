package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DeTu;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.du_lieu.ThemItemTest;
import java.text.DecimalFormat;
import com.badlogic.gdx.graphics.GL20;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.audio.Music;

public class VeHUD {
    private HUDClickHandler clickHandler;
    private HUDPopupRenderer popupRenderer;
    private HUDXulyitem xulyitem;
    private HUDPopupThongTin popupThongTin;
    private HUDPopupHanhTrang popupHanhTrang;

    private DuLieuNguoiChoi duLieuNguoiChoi;

    public Texture thanhhpnv,thanhkinv;
    public TextureRegion thanhhpnv1,thanhkinv1;
    public Texture saoden, saoxanh;
    public Texture ochat, ochatclick;
    public Texture thanhhp;
    public Texture odauthan, odauthanclick;
    public Texture oskill, oskillclick;
    public Texture nutpopup;

    public BitmapFont font,fontChucnang,fontDauThan,fontNhiemVu,fontNhiemVu1,fontNhiemVuChuaLam,fontMotaNhiemVu,fontvangngoc,fontsm,fontSkilldaco,fontSkillchuaco,fontMotaSkill,fontCapSKill,fontMotaNoiTai,fontTiemNang ,fontTenSkill,fontchat,fontMotaNganSkill,fontMotaNganSkill1,fontSkillchuaco1,fontMotaHanhTrang,fontMotaHanhTrang1,fontText ;
    private GlyphLayout layout;

    public SkillIcon[] skillIcons;

    private float thoiGianClickOChat = 0;
    private float thoiGianClickODauThan = 0;
    private int skillDangChon = -1; // -1: chưa chọn

    private ShapeRenderer shapeRenderer;

    public Texture popupNhanVat;
    public Texture nutX;
    public boolean dangHienPopup = false;
    public boolean vuaMoPopup = false;
    private NhanVat nhanVat;
    public Texture texAvt = null;
    public Texture nutchucnang,nutchucnangclick;
    public int chucNangDangChon = 0;
    public int chucNangDeTuDangChon = 0;
    public Texture vang,ngoc;
    public Texture thanhtheluc,thanhtheluc2;
    public TextureRegion thanhtheluc1,thanhtheluc3;

    public Texture hanh_trang,hanh_trang_click,hanh_trang_dang_mac,hanh_trang_dang_mac_click;

    public float scrollY = 0f;
    public float scrollYDeTu = 0f;
    public float maxScroll = 0f;
    public float maxScrollDeTu = 0f;
    private final float scrollSpeed = 30f; // số pixel cuộn mỗi lần
    public int hangTrangDangChon = -1;
    public int hangTrangDeTuDangChon = -1;

    public Texture o_noi_tai,o_noi_tai_click,o_chi_so_co_ban,o_chi_so_co_ban_click;
    public int oChiSoDangChon = -1;
    public Texture[] iconchisocoban = new Texture[5];
    public Texture iconnoitai;

    public Texture nutvuong,nutvuongclick;
    boolean DangHienPopupThongTin = false;
    boolean DangHienPopupThongTin1 = false;
    public float TimeChoHienPopup = 0;
    public boolean vuaMoPopupThongTin = false;
    public float PopupThongTinX = 0;
    public float PopupThongTinY = 0;
    public float PopupThongTinW = 0;
    public float PopupThongTinH = 0;
    public float PopupHanhTrangX = 0;
    public float PopupHanhTrangY = 0;
    String itemDangChon;
    Item itemm = null;
    public float PopupHanhTrangW = 0;
    public float PopupHanhTrangH = 0;
    float nutClickTimer = 0;
    float nutClickTimer1 = 0;
    int oChiSoDangChonTamThoi = -1;
    int giaTriTangTamThoi = 0;
    long chiPhiTamThoi = 0;
    public int nutduocchon =-1;

    public boolean HienPopUpGanSkill = false;
    float TimeChoHienPopupGanSkill = 0;
    private Texture[] oSkills;
    float nutClickTimer2 = 0;

    public Texture ao,quan,gang,giay,rada,iconct,giaplt,vanbay;
    public Texture aoDeTu,quanDeTu,gangDeTu,giayDeTu,radaDeTu,iconctDeTu;

    private float dauThanRenderH= 53f;
    public String avatardangmac = "Goku_base";
    public String aodangmac = "set_base";
    public String quandangmac = "set_base";
    public String skha = "thuong";
    public String skhq = "thuong";
    public String skhg = "thuong";
    public String skhj = "thuong";
    public String skhrada = "thuong";
    public boolean vuaMoNappa = false;
    public boolean vanBayDau = true;
    public boolean lanDau = true;
    public int[] chisovuathao;
    public boolean dangMacGiapLuyenTap = false;
    public float timeMacGiapLuyenTap;
    private boolean daCongChiMang = false;

    public float nuthanhtrangchon = -1;
    public float nutClickTimer3 = 0;

    public Texture anhThongBao,nutdn,nutclick;
    public float isThongBaoOKPressed = 0;
    public boolean dangHienThongBao = false;

    public Texture khungchat,duoichat;
    public boolean dangHienKhungChat = false;

    public String tinNhanChat = "";
    public boolean dangHienTinNhanChat = false;
    public float timeHienTinNhan = 0;

    public boolean dangHienGioiThieuGame = false;
    public float timeDoTre = 0;
    private boolean chuaNhanQuaLanDau = true;

    public boolean dangHienThongBaoGame = false;
    public boolean dangHienThongBaoLienHeAdmin = false;
    public boolean dangHienThongBaoCapNhat = false;
    public boolean dangHienThongBaox2x3 = false;
    public boolean dangHienThongBaoGiftCode = false;
    public boolean dangHienThongBaoEvent = false;

    public boolean dangHienPopupDeTu = false;
    public boolean dangChonHanhTrangSuPhu = false;
    public boolean dangChonHanhTrangDeTu = false;

    public boolean dangChonNhacNen = false;
    public Music[] nhacNen = new Music[10];

    public void setDuLieuNguoiChoi(DuLieuNguoiChoi data) {
        this.duLieuNguoiChoi = data;
        duLieuNguoiChoi.setNhanVat(nhanVat);
        // Gọi thêm item từ file ngoài
        ThemItemTest.themItemTest(duLieuNguoiChoi, nhanVat);
        clickHandler = new HUDClickHandler(this, duLieuNguoiChoi, nhanVat);
        popupRenderer= new HUDPopupRenderer(this, layout,duLieuNguoiChoi);
        xulyitem =  new HUDXulyitem(this, layout,duLieuNguoiChoi,nhanVat);
        popupThongTin = new HUDPopupThongTin(this, layout,duLieuNguoiChoi,nhanVat);
        popupHanhTrang = new HUDPopupHanhTrang(this, layout,duLieuNguoiChoi,nhanVat);
        duLieuNguoiChoi.taoDeTu("Đệ tử");
    }
    public void setSkillIcons(SkillIcon[] skillIcons) {
        this.skillIcons = skillIcons;
        oSkills = new Texture[5];
        for (int i = 0; i < oSkills.length; i++) {
            oSkills[i] = skillIcons[i].icon;
        }
    }
    public void scroll(int amount) {
        // amount âm là cuộn lên, dương là cuộn xuống
        scrollY += amount * scrollSpeed;

        // Giới hạn scroll
        scrollY = Math.max(0, Math.min(scrollY, maxScroll));
    }

    public void scrollDeTu(int amount) {
        // amount âm là cuộn lên, dương là cuộn xuống
        scrollYDeTu += amount * scrollSpeed;

        // Giới hạn scroll
        scrollYDeTu = Math.max(0, Math.min(scrollYDeTu, maxScrollDeTu));
    }

    public VeHUD(GlyphLayout layout) {
        this.layout = layout;
        shapeRenderer = new ShapeRenderer();
        // Load HUD textures
        saoden = new Texture("hud/giaodientrong/saoden.png");
        saoxanh = new Texture("hud/giaodientrong/saoxanh.png");
        ochat = new Texture("hud/giaodientrong/ochat.png");
        ochatclick = new Texture("hud/giaodientrong/ochatclick.png");
        thanhhp = new Texture("hud/giaodientrong/thanhhp1.png");
        odauthan = new Texture("hud/giaodientrong/odauthan.png");
        odauthanclick = new Texture("hud/giaodientrong/odauthanclick.png");
        oskill = new Texture("hud/giaodientrong/oskill.png");
        oskillclick = new Texture("hud/giaodientrong/oskillclick.png");
        nutpopup = new Texture("hud/giaodientrong/nutpopup.png");
        popupNhanVat = new Texture("hud/giaodientrong/popupnhanvat.jpg");
        nutX = new Texture("hud/giaodientrong/nutX.png");
        nutchucnang = new Texture("hud/giaodientrong/nutchucnang.png");
        nutchucnangclick = new Texture("hud/giaodientrong/nutchucnangclick.png");
        vang = new Texture("hud/giaodientrong/vang.png");
        ngoc = new Texture("hud/giaodientrong/ngoc.png");
        thanhtheluc = new Texture("hud/giaodientrong/ttluc.jpg");
        thanhtheluc2 = new Texture("hud/giaodientrong/ttluc2.jpg");
        hanh_trang = new Texture("hud/giaodientrong/ohanhtrang.jpg");
        hanh_trang_click = new Texture("hud/giaodientrong/ohanhtrangclick.jpg");
        hanh_trang_dang_mac = new Texture("hud/giaodientrong/ohanhtrangdangmac.jpg");
        hanh_trang_dang_mac_click = new Texture("hud/giaodientrong/ohanhtrangdangmacclick.jpg");
        o_chi_so_co_ban =  new Texture("hud/giaodientrong/ochiso.png");
        o_chi_so_co_ban_click = new Texture("hud/giaodientrong/ochisoclick.png");
        o_noi_tai = new Texture("hud/giaodientrong/onoitai.png");
        o_noi_tai_click = new Texture("hud/giaodientrong/onoitaiclick.png");
        for (int i = 0; i < 5; i++){
            iconchisocoban[i] = new Texture("kynang/iconkynang/chung/"+(i+1)+".png");
        }
        iconnoitai = new Texture("kynang/iconkynang/chung/noitai.png");
        nutvuong = new Texture("hud/giaodientrong/nutvuong.png");
        nutvuongclick = new Texture("hud/giaodientrong/nutvuongclick.png");
        anhThongBao = new Texture("hud/giaodienngoai/chung/khungthongbao.png");
        nutdn = new Texture("hud/giaodienngoai/chung/nutdangnhap3.png");
        nutclick = new Texture("hud/giaodienngoai/chung/nutclick2.png");
        khungchat = new Texture("hud/giaodienngoai/chung/khungchat.png");
        duoichat = new Texture("hud/giaodienngoai/chung/duoichat.png");
        thanhhpnv = new Texture("hud/giaodientrong/thanhhp.png");
        thanhkinv = new Texture("hud/giaodientrong/thanhki.png");
        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ ễ ằ ừ — ẳ ữ ỗ";
        param.size = 18;
        font = generator.generateFont(param);
        fontText = generator.generateFont(param);
        param.size = 19;
        fontTenSkill = generator.generateFont(param);
        param.color = (new Color(0.4118f, 0.4588f, 0.9137f, 1f));
        fontSkilldaco = generator.generateFont(param);
        param.color = (new Color(0.0902f, 0.7490f, 0.0039f, 1f));
        fontSkillchuaco = generator.generateFont(param);
        param.color = new Color(0x00 / 255f, 0xcb / 255f, 0x00 / 255f, 1f);
        fontSkillchuaco1 = generator.generateFont(param);
        generator.dispose();
        // Font có viền đen dành riêng cho dòng chữ "Đậu thần cấp ..."
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchinh.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param2.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ";
        param2.size = 22;
        param2.color = Color.WHITE;
        param2.borderWidth = 1f;
        param2.borderColor = new Color(0.4f, 0.4f, 0.4f, 1f);
        fontDauThan = generator2.generateFont(param2);
        generator2.dispose();
        // font cho mấy chữ chức năng
        FreeTypeFontGenerator generator3 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchucnang.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param3.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ";
        param3.size = 14;
        fontchat = generator3.generateFont(param3);
        param3.color = new Color(94 / 255f, 86 / 255f, 74 / 255f, 1f);
        fontChucnang = generator3.generateFont(param3);
        param3.size = 16;
        fontNhiemVu = generator3.generateFont(param3);
        param3.size = 15;
        param3.color = new Color(0f / 255f, 123f / 255f, 255f / 255f, 1f);
        fontNhiemVu1 = generator3.generateFont(param3);
        param3.size = 14;
        fontCapSKill = generator3.generateFont(param3);
        param3.size = 15;
        fontMotaNganSkill1 = generator3.generateFont(param3);
        param3.color = new Color(94 / 255f, 86 / 255f, 74 / 255f, 1f);
        fontNhiemVuChuaLam = generator3.generateFont(param3);
        param3.color = new Color(0f / 255f, 85f / 255f, 38f / 255f, 1f);
        param3.size = 15;
        fontMotaNhiemVu = generator3.generateFont(param3);
        param3.size = 14;
        fontMotaNoiTai = generator3.generateFont(param3);
        param3.size = 14;
        fontMotaSkill = generator3.generateFont(param3);
        param3.color = new Color(1,1,0,1);
        fontvangngoc = generator3.generateFont(param3);
        param3.color = new Color(0,0,0,1);
        fontMotaHanhTrang = generator3.generateFont(param3);
        param3.color = new Color(0.933f, 0.502f, 0.510f, 1f);
        fontMotaHanhTrang1 = generator3.generateFont(param3);
        param3.color = new Color(1,1,1,1);
        param3.size = 14;
        fontTiemNang = generator3.generateFont(param3);
        param3.color = new Color(1,1,0,1);
        param3.size = 14;
        fontsm = generator3.generateFont(param3);
        param3.color = new Color(0x83 / 255f, 0xc6 / 255f, 0x29 / 255f, 1f);
        param3.size = 15;
        fontMotaNganSkill = generator3.generateFont(param3);
        generator3.dispose();

        String[] tenFile = {
            "", // 0 là tắt nhạc nên để trống
            "khauthitamphi.mp3",
            "demngayxaem.mp3",
            "ketheoduoianhsang.mp3",
            "thaproitudo.mp3",
            "dieuanhbiet.mp3",
            "dandan.mp3",
            "saominhchuanamtaynhau.mp3",
            "thoigiansetraloi.mp3",
            "suthatdaboquen.mp3"
        };

        for (int i = 1; i < tenFile.length; i++) {
            nhacNen[i] = Gdx.audio.newMusic(Gdx.files.internal("nhacnen/" + tenFile[i]));
            nhacNen[i].setLooping(true);
            nhacNen[i].setVolume(0.5f);
        }
    }

    public void render(SpriteBatch batch) {
        batch.end();
        int widthDeTu = (int)(thanhtheluc2.getWidth() * (duLieuNguoiChoi.deTu.getTheLuc()/100f));
        thanhtheluc1 = new TextureRegion(thanhtheluc2, 0, 0, widthDeTu, thanhtheluc2.getHeight());
        int widthSuPhu = (int)(thanhtheluc2.getWidth() * (duLieuNguoiChoi.getTheLuc()/100f));
        thanhtheluc3 = new TextureRegion(thanhtheluc2, 0, 0, widthSuPhu, thanhtheluc2.getHeight());
        int widthHP = (int)(thanhhpnv.getWidth() * (duLieuNguoiChoi.getHpHienTai()/duLieuNguoiChoi.getHpToiDa()));
        thanhhpnv1= new TextureRegion(thanhhpnv, 0, 0, widthHP, thanhhpnv.getHeight());
        int widthKI = (int)(thanhkinv.getWidth() * (duLieuNguoiChoi.getKiHienTai()/duLieuNguoiChoi.getKiToiDa()));
        thanhkinv1= new TextureRegion(thanhkinv, 0, 0, widthKI, thanhkinv.getHeight());
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (!dangHienKhungChat) {
            // RENDER SAU ẢNH ĐẬU THẦN ( trắng )
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1f, 1f, 1f, 1f);
            shapeRenderer.rect(screenWidth - 75 - 10 + 10, 10 + 10, 53, dauThanRenderH);
            shapeRenderer.end();
        }
        batch.begin();

        // thanh HP (góc trái trên)
        int thanhhpW = 308;
        int thanhhpH = 80;
        float hpX = 0;
        float hpY = screenHeight - thanhhpH;
        batch.draw(thanhhp, hpX, hpY, thanhhpW, thanhhpH);
        batch.draw(thanhhpnv1,165, screenHeight - 80 - 5 + 55);
        batch.draw(thanhkinv1,165, screenHeight - 80 - 5 + 55-20);

        if (!dangHienKhungChat) {
            // ô chat (góc phải trên)
            int ochatW = 60;
            int ochatH = 60;
            float ochatX = screenWidth - ochatW - 15;
            float ochatY = screenHeight-10-ochatH;
            Texture texOChat = (thoiGianClickOChat > 0) ? ochatclick : ochat;
            batch.draw(texOChat, ochatX, ochatY, ochatW, ochatH);


            // ô skill (hàng ngang phía dưới)
            int oskillW = 50;
            int oskillH = 50;
            float skillBaseX = 30;
            float skillY = 25f;

            for (int i = 0; i < 5; i++) {
                float x = skillBaseX + i * (65f);
                Texture texSkill = (skillDangChon == i) ? oskillclick : oskill;
                batch.draw(texSkill, x, skillY, oskillW, oskillH);

                // icon kỹ năng
                if (oSkills[i] != null) {
                    batch.draw(oSkills[i], x + 6.9f, skillY + 6.9f, oskillW - 13.8f, oskillH - 13.8f);
                }

                // số kỹ năng
                font.setColor(Color.WHITE);
                String text = (i + 1) + "";
                layout.setText(font, text);
                font.draw(batch, layout, x + (oskillW - layout.width) / 2, skillY + oskillH + 15f);
            }

            // nút popup thông tin nhân vật (bên trái trên)
            float nutpopupX = 0f;
            float nutpopupY = screenHeight / 4f * 3;
            batch.draw(nutpopup, nutpopupX, nutpopupY, 25, 35);

            // ô đậu thần (góc phải dưới)
            int odauthanW = 75;
            int odauthanH = 75;
            float odauthanX = screenWidth - odauthanW - 10;
            float odauthanY = 10;
            Texture texODauThan = (thoiGianClickODauThan > 0) ? odauthanclick : odauthan;
            batch.draw(texODauThan, odauthanX, odauthanY, odauthanW, odauthanH);

            // số đậu thần
            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            layout.setText(font, duLieuNguoiChoi.getSoDauThan()+"");
            font.draw(batch, layout, odauthanX+(odauthanW- layout.width)/2f , odauthanY + 43);

        }
        // Tên nhân vật ngay ở thanhhp.png
        font.setColor(0f / 255f, 83f / 255f, 37f / 255f, 1f);
        layout.setText(font, duLieuNguoiChoi.getTen());
        font.draw(batch, layout, 2.5f+(155- layout.width)/2f, screenHeight - 80 + 50);

        //khung chat
        if (dangHienKhungChat) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            shapeRenderer.rect((Gdx.graphics.getWidth() - 528) / 2f - 2f, 35 -1f, 528 +4f, 149 +3f);
            shapeRenderer.end();
            batch.begin();
            batch.draw(khungchat,(Gdx.graphics.getWidth() - 528) / 2f,35 , 528, 149);
            float nX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 12;
            fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            batch.draw(isThongBaoOKPressed>0 && nutduocchon==1? nutclick : nutdn, nX-81, nutY, 140, 48);
            layout.setText(fontTenSkill, "OK");
            fontTenSkill.draw(batch, layout, nX-81 + (140 - layout.width) / 2f, nutY + 29);
            batch.draw(isThongBaoOKPressed>0 && nutduocchon==2? nutclick : nutdn, nX+81, nutY, 140, 48);
            layout.setText(fontTenSkill, "Đóng");
            fontTenSkill.draw(batch, layout, nX+81 + (140 - layout.width) / 2f, nutY + 29);

            fontTenSkill.setColor(0f / 255f, 85f / 255f, 38f / 255f, 1f);
            layout.setText(fontTenSkill, "Chat");
            fontTenSkill.draw(batch, layout, (Gdx.graphics.getWidth() - 528) / 2f + 15, 35 + 115);

            // Các thông số
            float khungX = (Gdx.graphics.getWidth() - 528) / 2f + 25;
            float khungY = 35;
            float khungWidth = 465;
            float khungHeight = 68;

            if (tinNhanChat.isEmpty()) {
                fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
                layout.setText(fontText, "chat");
            } else {
                fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1f);
                layout.setText(fontText, tinNhanChat);
            }
            float textWidth = layout.width;
            float offsetX = 0;

            if (textWidth > khungWidth) {
                offsetX = textWidth - khungWidth;
            }
            batch.flush();
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
            Gdx.gl.glScissor((int) khungX, (int) khungY, (int) khungWidth, (int) khungHeight);
            fontText.draw(batch, layout, khungX - offsetX, khungY + khungHeight );
            batch.flush();
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        }
    }

    public void clickOChat() {
        thoiGianClickOChat = 0.2f;
    }
    public void clickODauThan() {
        thoiGianClickODauThan = 0.2f;
        if (dauThanRenderH>=53f && duLieuNguoiChoi.getSoDauThan()>0){
            duLieuNguoiChoi.giamDau();
            duLieuNguoiChoi.tangHpHienTai(duLieuNguoiChoi.getDauHoiHPKI());
            duLieuNguoiChoi.tangKiHienTai(duLieuNguoiChoi.getDauHoiHPKI());
            dauThanRenderH = 0f;
        }
    }
    public void chonSkill(int index) {
        if (index >= 0 && index < 5 && !dangHienKhungChat) {
            skillDangChon = index;
        }
    }
    public void update(float delta) {
        if (timeDoTre >0) {
            timeDoTre -= delta;
            if (timeDoTre <=0){
                if (dangHienThongBaoGame) {
                    switch (oChiSoDangChon) {
                        case 0 -> dangHienThongBaoLienHeAdmin = true;
                        case 1 -> dangHienThongBaoCapNhat = true;
                        case 2 -> dangHienThongBaox2x3 = true;
                        case 3 -> dangHienThongBaoGiftCode = true;
                        case 4 -> dangHienThongBaoEvent = true;
                    }
                } else if (dangHienPopupDeTu) {
                    switch (oChiSoDangChon) {
                        case 0 -> duLieuNguoiChoi.deTu.setTrangthai("Đi theo");
                        case 1 -> duLieuNguoiChoi.deTu.setTrangthai("Bảo vệ");
                        case 2 -> duLieuNguoiChoi.deTu.setTrangthai("Tấn công");
                        case 3 -> duLieuNguoiChoi.deTu.setTrangthai("Về nhà");
                    }
                } else if (dangChonNhacNen) {
                    if (oChiSoDangChon == 0) {
                        for (int i = 1; i < nhacNen.length; i++) {
                            if (nhacNen[i].isPlaying()) nhacNen[i].stop();
                        }
                    } else if (oChiSoDangChon >= 1 && oChiSoDangChon < nhacNen.length) {
                        // Tắt nhạc cũ nếu có
                        for (int i = 1; i < nhacNen.length; i++) {
                            if (nhacNen[i].isPlaying()) nhacNen[i].stop();
                        }
                        // Phát nhạc mới
                        nhacNen[oChiSoDangChon].play();
                    }
                    dangChonNhacNen = false;
                } else {
                    if (oChiSoDangChon == 0) {
                        dangHienGioiThieuGame = true;
                    } else if (oChiSoDangChon == 2) {
                        dangHienThongBaoGame = true;
                    } else if (oChiSoDangChon == 3) {
                        dangHienPopupDeTu = true;
                        scrollYDeTu = 0;
                        hangTrangDangChon = -1;
                    } else if (oChiSoDangChon == 7) {
                        dangChonNhacNen = true;
                    }
                }
                oChiSoDangChon = -1;
                scrollY = 0;
            }
        }
        if (dangHienTinNhanChat) {
            timeHienTinNhan += delta;
            if (timeHienTinNhan >= 3f) {
                dangHienTinNhanChat = false;
                timeHienTinNhan = 0;
                tinNhanChat = "";
            }
        }
        if (thoiGianClickOChat > 0) {
            thoiGianClickOChat -= delta;
            if (thoiGianClickOChat<=0){
                if (!dangHienTinNhanChat) {
                    dangHienKhungChat = true;
                }
            }
        }
        if (thoiGianClickODauThan > 0) {
            thoiGianClickODauThan -= delta;
        }
        if (nutClickTimer > 0) {
            nutClickTimer -= Gdx.graphics.getDeltaTime();
            if (nutClickTimer <= 0) {
                switch (oChiSoDangChonTamThoi) {
                    case 0 -> duLieuNguoiChoi.tangHpGoc(giaTriTangTamThoi,true);
                    case 1 -> duLieuNguoiChoi.tangKiGoc(giaTriTangTamThoi,true);
                    case 2 -> duLieuNguoiChoi.tangSucDanhGoc(giaTriTangTamThoi,true);
                    case 3 -> duLieuNguoiChoi.tangGiapGoc(giaTriTangTamThoi);
                    case 4 -> duLieuNguoiChoi.tangChiMangGoc(giaTriTangTamThoi);
                }
                duLieuNguoiChoi.giamTiemNang((long) chiPhiTamThoi);
                DangHienPopupThongTin = false;
                TimeChoHienPopup = 0;
            }
        }
        if (nutClickTimer1 > 0){
            nutClickTimer1 -= Gdx.graphics.getDeltaTime();
            if (nutClickTimer1 <= 0) {
                if (nutduocchon == 1) {
                    DangHienPopupThongTin = false;
                    TimeChoHienPopup = 0;
                    TimeChoHienPopupGanSkill = 0.2f;
                } else if (nutduocchon == 2) {
                    DangHienPopupThongTin = false;
                    TimeChoHienPopup = 0;
                }
            }
        }
        if (nutClickTimer2 > 0 ){
            nutClickTimer2 -= Gdx.graphics.getDeltaTime();
            if (nutClickTimer2 <= 0){
                Texture skillGan = skillIcons[oChiSoDangChon - 6].icon;
                // Xóa các ô đang chứa cùng icon
                for (int i = 0; i < 5; i++) {
                    if (oSkills[i] == skillGan) {
                        oSkills[i] = null;
                    }
                }
                // Gán vào ô được chọn
                oSkills[nutduocchon] = skillGan;;
                HienPopUpGanSkill = false;
            }
        }
        if (nutClickTimer3 > 0){
            nutClickTimer3 -= Gdx.graphics.getDeltaTime();
            if (nutClickTimer3 <= 0) {
                if (nuthanhtrangchon == 1) {
                    if (nhanVat.getHanhtinh().equals(itemm.getHanhtinh()) && duLieuNguoiChoi.getSucManh()>=itemm.getSucManhYeuCau()) {
                        xulyitem.macDo(hangTrangDangChon);
                    }
                    DangHienPopupThongTin1 = false;
                    TimeChoHienPopup = 0;
                } else if (nuthanhtrangchon == 2) {
                    dangHienThongBao = true;
                    DangHienPopupThongTin1 = false;
                    TimeChoHienPopup = 0;
                } else if (nuthanhtrangchon == 3) {
                    DangHienPopupThongTin1 = false;
                    TimeChoHienPopup = 0;
                }
            }
        }
        if (isThongBaoOKPressed>0) {
            isThongBaoOKPressed -= Gdx.graphics.getDeltaTime();
            if (isThongBaoOKPressed <= 0) {
                if (dangHienThongBao) {
                    if (nutduocchon == 1) {
                        if (hangTrangDangChon >= 8) {
                            ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();
                            danhSach.remove(hangTrangDangChon - 8);
                        } else {
                            switch (hangTrangDangChon) {
                                case 6:
                                    xulyitem.goGiapLuyenTap(true);
                                    break;
                                case 0:
                                    xulyitem.goAo(true);
                                    break;
                                case 1:
                                    xulyitem.goQuan(true);
                                    break;
                                case 2:
                                    xulyitem.goGang(true);
                                    break;
                                case 3:
                                    xulyitem.goGiay(true);
                                    break;
                                case 4:
                                    xulyitem.goRada(true);
                                    break;
                                case 5:
                                    xulyitem.goCaiTrang(NhanVatXuLy.getDangMacCaiTrang(), true);
                                    break;
                            }
                        }
                        dangHienThongBao = false;
                        nutduocchon = -1;
                    } else if (nutduocchon == 2) {
                        dangHienThongBao = false;
                        nutduocchon = -1;
                    }
                } else if (dangHienKhungChat) {
                    if (nutduocchon == 2){
                        dangHienKhungChat = false;
                        nutduocchon = -1;
                        tinNhanChat = "";
                    } else if (nutduocchon == 1) {
                        if (!tinNhanChat.isEmpty()){
                            dangHienTinNhanChat = true;
                            dangHienKhungChat = false;
                            nutduocchon = -1;
                        }
                    }
                } else if (dangHienGioiThieuGame) {
                    if (chuaNhanQuaLanDau) {
                        duLieuNguoiChoi.tangNgoc(1_000_000);
                        duLieuNguoiChoi.tangVang(1_000_000_000);
                        duLieuNguoiChoi.tangSucManh(10_000_000_000L);
                        chuaNhanQuaLanDau = false;
                    }
                    nutduocchon = -1;
                }
            }
        }
        if (TimeChoHienPopupGanSkill > 0 ){
            TimeChoHienPopupGanSkill -= Gdx.graphics.getDeltaTime();
            if (TimeChoHienPopupGanSkill <= 0) {
                HienPopUpGanSkill = true;
            }
        }
        if (DangHienPopupThongTin || DangHienPopupThongTin1){
            TimeChoHienPopup-=Gdx.graphics.getDeltaTime();
        }
        if (chucNangDangChon != 2){
            DangHienPopupThongTin = false;
        }
        if (dauThanRenderH < 53f){
            dauThanRenderH+=10.6f*Gdx.graphics.getDeltaTime();
            dauThanRenderH = Math.min(dauThanRenderH, 53f);
        }

        if (dangMacGiapLuyenTap){
            nhanVat.tangHanSuDungGiapLuyenTap();
            timeMacGiapLuyenTap = nhanVat.getHanSuDungGiapLuyenTap();
            nhanVat.setHanSuDungGiapLuyenTap(timeMacGiapLuyenTap);
            if (daCongChiMang){
                duLieuNguoiChoi.giamSatThuongChiMang(30);
                duLieuNguoiChoi.giamChiMang(15);
                daCongChiMang = false;
            }
        } else {
            nhanVat.giamHanSuDungGiapLuyenTap();
            timeMacGiapLuyenTap = nhanVat.getHanSuDungGiapLuyenTap();
            nhanVat.setHanSuDungGiapLuyenTap(timeMacGiapLuyenTap);
            if (timeMacGiapLuyenTap>60f && !daCongChiMang){
                duLieuNguoiChoi.tangSatThuongChiMang(30);
                duLieuNguoiChoi.tangChiMang(15);
                daCongChiMang = true;
            }
            if (daCongChiMang && timeMacGiapLuyenTap<=60f){
                duLieuNguoiChoi.giamSatThuongChiMang(30);
                duLieuNguoiChoi.giamChiMang(15);
                daCongChiMang = false;
            }
        }
    }
    public void xuLyClick(int x, int y) {
        clickHandler.xuLyClick(x, y);
    }

    public void hienPopupNhanVat() {
        dangHienPopup = true;
        vuaMoPopup = true;
    }

    public void tatPopupNhanVat() {
        dangHienPopup = false;
    }

    public boolean isDangHienPopup() {
        return dangHienPopup;
    }
    public void setNhanVat(NhanVat nhanVat) {
        this.nhanVat = nhanVat;
        if (texAvt != null) texAvt.dispose(); // nếu có thì giải phóng cũ
        String path = nhanVat.doiavatar();
        texAvt = new Texture(path); // load luôn tại đây
        this.ao = nhanVat.ao;
        this.quan = nhanVat.quan;
        this.gang = nhanVat.gang;
        this.giay = nhanVat.giay;
        this.rada = nhanVat.rada;
        this.iconct = nhanVat.iconct;
        this.giaplt = nhanVat.giaplt;
        this.vanbay = nhanVat.vanbay;
        avatardangmac = nhanVat.getNhanvat()+"_base";
    }
    public void renderPopup(SpriteBatch batch) {
        popupRenderer.renderPopup(batch);
    }
    public int getChucNangDangChon() {
        return chucNangDangChon;
    }
    public String formatVangNgoc(long so) {
        if (so < 0) return "10.000.000"; // hoặc return "???", hoặc format lại số âm tùy ý
        if (so >= 1_000_000_000) {
            return String.format("%.1ftỷ", so / 1_000_000_000.0);
        } else if (so >= 1_000_000) {
            return String.format("%.1ftr", so / 1_000_000.0);
        } else if (so >= 1_000) {
            return String.format("%dK", so / 1_000);
        } else {
            return String.valueOf(so);
        }
    }
    void PopupThongTin(ShapeRenderer shapeRenderer, SpriteBatch batch, float x, float y , float width, float height , int oChiSoDangChon) {
        popupThongTin.PopupThongTin(shapeRenderer,batch,x,y,width,height,oChiSoDangChon);
    }
    void PopupHanhTrang(ShapeRenderer shapeRenderer,SpriteBatch batch, float x, float y , float width , int oHanhTrangDangChon) {
        popupHanhTrang.PopupHanhTrang(shapeRenderer,batch,x,y,width,oHanhTrangDangChon);
    }
    long tinhChiPhiTiemNang(int oChiSo, int chiSoGoc, int soLanTang, int buocTang) {
        long tong = 0;
        for (int j = 1; j <= soLanTang; j++) {
            switch (oChiSo) {
                case 0: // HP
                case 1: // KI
                    tong += chiSoGoc + buocTang * j + 1000;
                    break;
                case 2: // Sức đánh
                    tong += (chiSoGoc + j - 1) * 100L;
                    break;
                case 3: // Giáp
                    tong += 500000 + (chiSoGoc + j - 1) * 100000L;
                    break;
                case 4:  // Crit
                    tong = 30000000 + (duLieuNguoiChoi.getChiMangGoc() - 1) * 5000000000L;
                    break;
            }
        }
        return tong;
    }
    public NhanVatCauHinh Doicaitrang(String TenCaiTrang){
        return NhanVatXuLy.xuly_id("caitrang_"+TenCaiTrang);
    }
    public NhanVatCauHinh Doi_avt_ao_quan(String HanhTinh, String TenAvatar , String ao, String quan){
        return NhanVatXuLy.xuly_id("avatar_"+HanhTinh+"+"+TenAvatar+"+"+ao+"+"+quan);
    }

    public void dispose() {
        ochat.dispose();
        ochatclick.dispose();
        thanhhp.dispose();
        odauthan.dispose();
        odauthanclick.dispose();
        oskill.dispose();
        oskillclick.dispose();
        nutpopup.dispose();
        popupNhanVat.dispose();
        nutX.dispose();
        if (texAvt != null) texAvt.dispose();
        for (int i = 1; i < nhacNen.length; i++) {
            if (nhacNen[i] != null) nhacNen[i].dispose();
        }
    }
}
