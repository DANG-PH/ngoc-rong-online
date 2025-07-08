package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import java.text.DecimalFormat;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import java.util.ArrayList;

public class VeHUD {

    private DuLieuNguoiChoi duLieuNguoiChoi;

    private Texture saoden, saoxanh;
    private Texture ochat, ochatclick;
    private Texture thanhhp;
    private Texture odauthan, odauthanclick;
    private Texture oskill, oskillclick;
    private Texture nutpopup;

    private BitmapFont font,fontChucnang,fontDauThan,fontNhiemVu,fontNhiemVu1,fontNhiemVuChuaLam,fontMotaNhiemVu,fontvangngoc,fontsm,fontSkilldaco,fontSkillchuaco,fontMotaSkill,fontCapSKill,fontMotaNoiTai,fontTiemNang ,fontTenSkill,fontMotaNganSkill,fontMotaNganSkill1,fontSkillchuaco1,fontMotaHanhTrang ;
    private GlyphLayout layout;

    private SkillIcon[] skillIcons;

    private float thoiGianClickOChat = 0;
    private float thoiGianClickODauThan = 0;
    private int skillDangChon = -1; // -1: chưa chọn

    private ShapeRenderer shapeRenderer;

    private Texture popupNhanVat;
    private Texture nutX;
    private boolean dangHienPopup = false;
    private boolean vuaMoPopup = false;
    private NhanVat nhanVat;
    private Texture texAvt = null;
    private Texture nutchucnang,nutchucnangclick;
    private int chucNangDangChon = 0;
    private Texture vang,ngoc;
    private Texture thanhtheluc;

    private Texture hanh_trang,hanh_trang_click,hanh_trang_dang_mac,hanh_trang_dang_mac_click;

    private float scrollY = 0f;
    private float maxScroll = 0f;
    private final float scrollSpeed = 30f; // số pixel cuộn mỗi lần
    private int hangTrangDangChon = -1;

    private Texture o_noi_tai,o_noi_tai_click,o_chi_so_co_ban,o_chi_so_co_ban_click;
    private int oChiSoDangChon = -1;
    private Texture[] iconchisocoban = new Texture[5];
    private Texture iconnoitai;

    private Texture nutvuong,nutvuongclick;
    boolean DangHienPopupThongTin = false;
    boolean DangHienPopupThongTin1 = false;
    float TimeChoHienPopup = 0;
    private boolean vuaMoPopupThongTin = false;
    float PopupThongTinX = 0;
    float PopupThongTinY = 0;
    float PopupThongTinW = 0;
    float PopupThongTinH = 0;
    float PopupHanhTrangX = 0;
    float PopupHanhTrangY = 0;
    String itemDangChon;
    Item itemm = null;
    float PopupHanhTrangW = 0;
    float PopupHanhTrangH = 0;
    float nutClickTimer = 0;
    float nutClickTimer1 = 0;
    int oChiSoDangChonTamThoi = -1;
    int giaTriTangTamThoi = 0;
    long chiPhiTamThoi = 0;
    private int nutduocchon =-1;

    private boolean HienPopUpGanSkill = false;
    float TimeChoHienPopupGanSkill = 0;
    private Texture[] oSkills;
    float nutClickTimer2 = 0;

    private Texture ao,quan,gang,giay,rada,iconct,giaplt,vanbay;

    private float dauThanRenderH= 53f;
    private String avatardangmac = "Goku_base";
    private String aodangmac = "set_base";
    private String quandangmac = "set_base";
    private String skha = "thuong";
    private String skhq = "thuong";
    private String skhg = "thuong";
    private String skhj = "thuong";
    private String skhrada = "thuong";
    private boolean vuaMoNappa = false;
    private boolean vanBayDau = true;
    private boolean lanDau = true;
    private int[] chisovuathao;
    private boolean dangMacGiapLuyenTap = false;
    private float timeMacGiapLuyenTap;
    private boolean daCongChiMang = false;

    float nuthanhtrangchon = -1;
    float nutClickTimer3 = 0;

    public void setDuLieuNguoiChoi(DuLieuNguoiChoi data) {
        this.duLieuNguoiChoi = data; // truyền data vào để xử lí hud
        duLieuNguoiChoi.setNhanVat(nhanVat);
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "phuong_hoang_lua",
            "Thú cưỡi cực VIP",
            LoaiItem.VANBAY,
            new Texture("vatpham/vanbay/phuong_hoang_lua/phuonghoanglua.png"),
            "Dùng để bay và hồi phục HP, KI",
            1,
            new int[]{0, 0, 0, 0, 0,   0,  0,  0,  0,  0,  0,  0,  0},
                    //hp,ki,sd,cr,giap,crd,hp%,ki%,sd%,hpg,kig,sdg,giam st
            nhanVat.getHanhtinh(),
            1500000,
            null,
            0,
            0,
            0,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "goku_black",
            "Cải trang",
            LoaiItem.CAITRANG,
            new Texture("nhanvat/caitrang/goku_black/dung.png"),
            "Cải trang thành Goku Black",
            1,
            new int[]{0, 0, 0, 0, 0, 0, 25, 25, 25,0,0,0,  0},
            nhanVat.getHanhtinh(),
            1500000,
            null,
            0,
            0,
            0,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "vegito_xeno",
            "Cải trang hợp thể",
            LoaiItem.CAITRANG,
            new Texture("nhanvat/caitrang/vegito_xeno/dung.png"),
            "Cải trang thành Vegito Xeno SSJ3",
            1,
            new int[]{0, 0, 0, 20, 1010, 0, 70, 70, 70,0,0,0,  0},
            nhanVat.getHanhtinh(),
            1500000,
            null,
            0,
            0,
            0,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "avt_vip",
            "AVATAR VIP",
            LoaiItem.AVATAR,
            new Texture("nhanvat/"+nhanVat.getHanhtinh()+"/avatar/avt_vip/dung.png"),
            "Dùng để thay đổi khuôn mặt",
            1,
            new int[]{0, 0, 0, 0, 10, 0, 15, 15, 0,0,0,0,  0},
            nhanVat.getHanhtinh(),
            1500000,
            null,
            0,
            0,
            0,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "set_cam",
            "Áo võ kame",
            LoaiItem.AO,
            new Texture("vatpham/do/aoquan/"+nhanVat.getHanhtinh()+"/set_cam/ao.png"),
            "Giúp giảm sát thương",
            1,
            new int[]{0, 0, 0, 0, 10, 0, 15, 0, 0,0,0,0,  0},
            "traidat",
            150000,
            null,
            3,
            3,
            3,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "set_cam",
            "Quần võ kame",
            LoaiItem.QUAN,
            new Texture("vatpham/do/aoquan/"+nhanVat.getHanhtinh()+"/set_cam/quan.png"),
            "Giúp tăng HP",
            1,
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0,5000,0,0,  0},
            "traidat",
            150000,
            null,
            3,
            0,
            3,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "set_cam",
            "Găng võ kame",
            LoaiItem.GANG,
            new Texture("vatpham/do/gang/"+nhanVat.getHanhtinh()+"/gang1.png"),
            "Giúp tăng sức đánh",
            1,
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,5,  0},
            "traidat",
            150000,
            null,
            3,
            0,
            3,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "set_cam",
            "Giày võ kame",
            LoaiItem.GIAY,
            new Texture("vatpham/do/giay/"+nhanVat.getHanhtinh()+"/giay1.png"),
            "Giúp tăng MP",
            1,
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0,0,200,0,  0},
            "traidat",
            150000,
            null,
            3,
            0,
            3,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "rada1",
            "rada cap 1",
            LoaiItem.RADA,
            new Texture("vatpham/do/rada/rada1.png"),
            "Giúp tăng Chí Mạng",
            1,
            new int[]{0, 0, 0, 1, 0, 0, 0, 0, 0,0,0,0,  0},
            "traidat",
            15000,
            null,
            3,
            0,
            3,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "set_huy_diet",
            "Áo hủy diệt",
            LoaiItem.AO,
            new Texture("vatpham/do/aoquan/"+nhanVat.getHanhtinh()+"/set_huy_diet/ao.png"),
            "Giúp giảm sát thương",
            1,
            new int[]{0, 0, 0, 0, 2500, 0,35, 0, 0,0,0,0,  0},
            "traidat",
            40_000_000_000L,
            "Nappa",
            7,
            7,
            7,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "set_huy_diet",
            "Quần hủy diệt",
            LoaiItem.QUAN,
            new Texture("vatpham/do/aoquan/"+nhanVat.getHanhtinh()+"/set_huy_diet/quan.png"),
            "Giúp tăng HP",
            1,
            new int[]{0, 0, 0, 0, 0, 0, 35, 0, 0,120000,0,0,  0},
            "traidat",
            40_000_000_000L,
            "Nappa",
            7,
            7,
            7,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "set_huy_diet",
            "Găng hủy diệt",
            LoaiItem.GANG,
            new Texture("vatpham/do/gang/"+nhanVat.getHanhtinh()+"/ganghuydiet.png"),
            "Giúp tăng sức đánh",
            1,
            new int[]{0, 0, 0, 0, 0, 0, 35, 0, 0,0,0,9000,  0},
            "traidat",
            40_000_000_000L,
            "Nappa",
            7,
            7,
            7,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "set_huy_diet",
            "Giày hủy diệt",
            LoaiItem.GIAY,
            new Texture("vatpham/do/giay/"+nhanVat.getHanhtinh()+"/giayhuydiet.png"),
            "Giúp tăng MP",
            1,
            new int[]{0, 0, 0, 0, 0, 0, 35, 0, 0,0,100000,0,  0},
            "traidat",
            40_000_000_000L,
            "Nappa",
            7,
            7,
            7,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "rada_huy_diet",
            "Rada hủy diệt",
            LoaiItem.RADA,
            new Texture("vatpham/do/rada/radahuydiet.png"),
            "Giúp tăng Chí Mạng",
            1,
            new int[]{0, 0, 0, 20, 0, 0, 35, 0, 0,0,0,0,  0},
            "traidat",
            40_000_000_000L,
            "Nappa",
            7,
            7,
            7,
            -1
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "glt_c3",
            "Giáp luyện tập cấp 3",
            LoaiItem.GIAPLUYENTAP,
            new Texture("vatpham/vatphamgame/giapluyentap/gltc3.png"),
            "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra sẽ tăng sức đánh 30% và Crit 15%, ST Crit 30%",
            1,
            new int[]{0, 0, 0, 0, 0, 0, 35, 0, 30, 0, 0, 0,  0},
            "traidat",
            1_000_000_000L,
            null,
            7,
            7,
            0,
            0
        ));
        duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
            "glt_c1",
            "Giáp luyện tập cấp 1",
            LoaiItem.GIAPLUYENTAP,
            new Texture("vatpham/vatphamgame/giapluyentap/gltc1.png"),
            "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra sẽ tăng sức đánh 10% và Crit 15%, ST Crit 30%",
            1,
            new int[]{0, 0, 0, 0, 0, 0, 25, 0, 10, 0, 0, 0,  0},
            "traidat",
            10_000_000L,
            null,
            7,
            5,
            0,
            0
        ));
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
        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ";
        param.size = 18;
        font = generator.generateFont(param);
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ";
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ";
        param3.size = 14;
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
    }

    public void render(SpriteBatch batch) {
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
                    macDo(hangTrangDangChon);
                    DangHienPopupThongTin1 = false;
                    TimeChoHienPopup = 0;
                } else if (nuthanhtrangchon == 2) {
                    if (hangTrangDangChon >=8 ){
                    ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();
                    danhSach.remove(hangTrangDangChon-8);
                    } else {
                        switch (hangTrangDangChon){
                            case 6 : goGiapLuyenTap(true);
                            case 0 : goAo(false);
                            case 1 : goQuan(false);
                            case 2 : goGang(false);
                            case 3 : goGiay(false);
                            case 4 : goRada(false);
                            case 5 : goCaiTrang(NhanVatXuLy.getDangMacCaiTrang(),false);
                        }
                    }
                    DangHienPopupThongTin1 = false;
                    TimeChoHienPopup = 0;
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
            if (timeMacGiapLuyenTap>10f && !daCongChiMang){
                duLieuNguoiChoi.tangSatThuongChiMang(30);
                duLieuNguoiChoi.tangChiMang(15);
                daCongChiMang = true;
            }
            if (daCongChiMang && timeMacGiapLuyenTap<=10f){
                duLieuNguoiChoi.giamSatThuongChiMang(30);
                duLieuNguoiChoi.giamChiMang(15);
                daCongChiMang = false;
            }
        }
        batch.end();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        // KHUNG NÂU BASE
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(78f / 255f, 47f / 255f, 31f / 255f, 1f);
        shapeRenderer.rect(165, screenHeight - 80 - 5 + 50, 130 , 25);
        shapeRenderer.rect(155, screenHeight - 80 - 5 + 50 - 25, 130 , 25);
        shapeRenderer.end();

        // THANH HP (đỏ)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float hpPercent = (float) duLieuNguoiChoi.getHpHienTai() / duLieuNguoiChoi.getHpToiDa();
        shapeRenderer.setColor(189f / 255f, 29f / 255f, 0f / 255f, 1f);
        shapeRenderer.rect(165, screenHeight - 80 - 5 + 50, 130 * hpPercent , 25);
        shapeRenderer.end();

        // THANH KI (xanh)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float kiPercent = (float) duLieuNguoiChoi.getKiHienTai() / duLieuNguoiChoi.getKiToiDa();
        shapeRenderer.setColor(0f / 255f, 157f / 255f, 212f / 255f, 1f);
        shapeRenderer.rect(155, screenHeight - 80 - 5 + 50 - 25, 130 * kiPercent , 25);
        shapeRenderer.end();

        // RENDER SAU ẢNH ĐẬU THẦN ( trắng )
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f,1f,1f, 1f);
        shapeRenderer.rect(screenWidth - 75- 10 + 10, 10 + 10, 53 , dauThanRenderH);
        shapeRenderer.end();

        batch.begin();
        layout.setText(fontsm,nhanVat.getHanSuDungGiapLuyenTap()+"");
        fontsm.draw(batch,layout,500,300);
        // ô chat (góc phải trên)
        int ochatW = 60;
        int ochatH = 60;
        float ochatX = screenWidth - ochatW - 15;
        float ochatY = screenHeight-10-ochatH;
        Texture texOChat = (thoiGianClickOChat > 0) ? ochatclick : ochat;
        batch.draw(texOChat, ochatX, ochatY, ochatW, ochatH);

        // thanh HP (góc trái trên)
        int thanhhpW = 300;
        int thanhhpH = 80;
        float hpX = 5;
        float hpY = screenHeight - thanhhpH - 5;
        batch.draw(thanhhp, hpX, hpY, thanhhpW, thanhhpH);
        // ô đậu thần (góc phải dưới)
        int odauthanW = 75;
        int odauthanH = 75;
        float odauthanX = screenWidth - odauthanW - 10;
        float odauthanY = 10;
        Texture texODauThan = (thoiGianClickODauThan > 0) ? odauthanclick : odauthan;
        batch.draw(texODauThan, odauthanX, odauthanY, odauthanW, odauthanH);

        // ô skill (hàng ngang phía dưới)
        int oskillW = 50;
        int oskillH = 50;
        float skillBaseX = 30;
        float skillY = 25f;

        for (int i = 0; i < 5; i++) {
            float x = skillBaseX + i * (20f + 45f);
            Texture texSkill = (skillDangChon == i) ? oskillclick : oskill;
            batch.draw(texSkill, x, skillY, oskillW, oskillH);

            // icon kỹ năng
            if (oSkills[i] != null) {
                batch.draw(oSkills[i], x + 5, skillY + 5, oskillW - 10, oskillH - 10);
            }

            // số kỹ năng
            font.setColor(Color.WHITE);
            String text = (i + 1) + "";
            layout.setText(font, text);
            font.draw(batch, layout, x + (oskillW - layout.width) / 2, skillY + oskillH + 15f);
        }
        // nút popup thông tin nhân vật (bên trái trên)
        float nutpopupX = 0f;
        float nutpopupY = screenHeight / 4f *3;
        batch.draw(nutpopup, nutpopupX, nutpopupY, 25, 35);

        // Tên nhân vật ngay ở thanhhp.png
        font.setColor(0f / 255f, 83f / 255f, 37f / 255f, 1f);
        layout.setText(font, duLieuNguoiChoi.getTen());
        font.draw(batch, layout, 5+(155- layout.width)/2f, screenHeight - 80 - 5 + 50);

        // số đậu thần
        font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        layout.setText(font, duLieuNguoiChoi.getSoDauThan()+"");
        font.draw(batch, layout, odauthanX+(odauthanW- layout.width)/2f , odauthanY + 43);

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
        if (index >= 0 && index < 5) {
            skillDangChon = index;
        }
    }
    public void update(float delta) {
        if (thoiGianClickOChat > 0) {
            thoiGianClickOChat -= delta;
        }
        if (thoiGianClickODauThan > 0) {
            thoiGianClickODauThan -= delta;
        }
    }
    public void xuLyClick(int x, int y) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // === VÙNG Ô CHAT ===
        int ochatW = 60;
        int ochatH = 60;
        float ochatX = screenWidth - ochatW - 15;
        float ochatY = screenHeight-10-ochatH;
        if (x >= ochatX && x <= ochatX + 60 && y >= ochatY && y <= ochatY + 60) {
            clickOChat();
        }

        // === VÙNG Ô ĐẬU THẦN ===
        int odauthanW = 75;
        int odauthanH = 75;
        float odauthanX = screenWidth - odauthanW - 10;
        float odauthanY = 10;
        if (x >= odauthanX && x <= odauthanX + 75 && y >= odauthanY && y <= odauthanY + 75) {
            clickODauThan();
        }
        // Vùng mở popup
        float nutPopupX = 0f;
        float nutPopupY = screenHeight / 4f * 3;
        if (x >= nutPopupX && x <= nutPopupX + 25 && y >= nutPopupY && y <= nutPopupY + 35) {
            hienPopupNhanVat();
        }

        if (dangHienPopup) {
            if (vuaMoPopup) {
                vuaMoPopup = false;
                return;
            }
            // nutX để tắt popup
            float nutXW = nutX.getWidth() * 0.5f;
            float nutXH = nutX.getHeight() * 0.55f;
            float nutXX = 350 - nutXW - 6;
            float nutXY = 610 - nutXH - 2;
            if (x >= nutXX && x <= nutXX + nutXW && y >= nutXY && y <= nutXY + nutXH) {
                tatPopupNhanVat();
                hangTrangDangChon = -1;
                oChiSoDangChon = -1;
            } else if (x > 350 && x <= 1020 && !DangHienPopupThongTin && !HienPopUpGanSkill && !DangHienPopupThongTin1) {
                tatPopupNhanVat();
                hangTrangDangChon = -1;
                oChiSoDangChon = -1;
            }
            // cac nut chuc nang
            for (int i = 0; i < 5; i++) {
                if (x >= 2+i*68+3 && x <= 2+i*68+3 + 68 && y >= 450 && y <= 450 + 52){
                    chucNangDangChon=i;
                    scrollY = 0;
                }
            }
        }
        if (dangHienPopup && chucNangDangChon == 1 && !DangHienPopupThongTin1) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 8 + 12;

            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {
                float relativeY = y - viewY;
                float realY = scrollY + (viewHeight - relativeY);
                int index = (int) (realY / KhoangCachItem);
                hangTrangDangChon = index;
                if (hangTrangDangChon >= 8) {
                    int indexx = hangTrangDangChon - 8;
                    ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();
                    if (indexx < danhSach.toArray().length) {
                        Item item = danhSach.get(indexx);
                        itemm = item;
                        if (item.getLoai() == LoaiItem.CAITRANG) {
                            itemDangChon = "caitrang";
                        } else if (item.getLoai() == LoaiItem.AVATAR) {
                            itemDangChon = "avatar";
                        } else if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                            itemDangChon = "giapluyentap";
                        } else if (item.getLoai() == LoaiItem.AO) {
                            itemDangChon = "ao";
                        } else if (item.getLoai() == LoaiItem.QUAN) {
                            itemDangChon = "quan";
                        } else if (item.getLoai() == LoaiItem.GANG) {
                            itemDangChon = "gang";
                        } else if (item.getLoai() == LoaiItem.GIAY) {
                            itemDangChon = "giay";
                        } else if (item.getLoai() == LoaiItem.RADA) {
                            itemDangChon = "rada";
                        } else if (item.getLoai() == LoaiItem.VANBAY) {
                            itemDangChon = "vanbay";
                        }
                    } else {
                        itemm = null;
                    }
                    if (itemm != null) {
                        DangHienPopupThongTin1 = true;
                        PopupHanhTrangX = 5;
                        PopupHanhTrangW = 360;
                        PopupHanhTrangY = viewY + viewHeight - (index + 1) * KhoangCachItem + scrollY;
                        PopupHanhTrangH = 0;
                        TimeChoHienPopup = 0.3f;
                        vuaMoPopupThongTin = true;
                    }
                } else {
                    ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrangDangMac();
                    if (vanBayDau && hangTrangDangChon==7){
                        String idCu = "candauvan";
                        String tenCu = "Cân đẩu vân";
                        String motacu = "Ván bay cân đẩu vân";
                        int[] chisocu = new int[] {0,0,0,0,0,0,0,0,0,0,0,0};
                        LoaiItem loaiCu = LoaiItem.VANBAY;
                        Item vanBayCu = new Item(idCu, tenCu, loaiCu, vanbay, motacu, 1, chisocu,"traidat",0, null,0,0,0,-1);
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(vanBayCu,7);
                    }
                    if (danhSach.get(hangTrangDangChon) != null){
                        Item item = danhSach.get(hangTrangDangChon);
                        itemm = item;
                        if (item.getLoai() == LoaiItem.CAITRANG) {
                            itemDangChon = "caitrang";
                        } else if (item.getLoai() == LoaiItem.AVATAR) {
                            itemDangChon = "avatar";
                        } else if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                            itemDangChon = "giapluyentap";
                        } else if (item.getLoai() == LoaiItem.AO) {
                            itemDangChon = "ao";
                        } else if (item.getLoai() == LoaiItem.QUAN) {
                            itemDangChon = "quan";
                        } else if (item.getLoai() == LoaiItem.GANG) {
                            itemDangChon = "gang";
                        } else if (item.getLoai() == LoaiItem.GIAY) {
                            itemDangChon = "giay";
                        } else if (item.getLoai() == LoaiItem.RADA) {
                            itemDangChon = "rada";
                        } else if (item.getLoai() == LoaiItem.VANBAY) {
                            itemDangChon = "vanbay";
                        }
                    } else {
                        itemm = null;
                    }
                    if (itemm != null) {
                        DangHienPopupThongTin1 = true;
                        PopupHanhTrangX = 5;
                        PopupHanhTrangW = 360;
                        PopupHanhTrangY = viewY + viewHeight - (index + 1) * KhoangCachItem + scrollY;
                        PopupHanhTrangH = 0;
                        TimeChoHienPopup = 0.3f;
                        vuaMoPopupThongTin = true;
                    }
                }
            }
        }
        if (dangHienPopup && chucNangDangChon == 2 && !DangHienPopupThongTin && !HienPopUpGanSkill) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 61;
            int tongSoO = 15;

            // Kiểm tra có click vào vùng hành trang không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {

                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = scrollY + (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    oChiSoDangChon = index;
                    DangHienPopupThongTin = true;
                    if (index >= 0 && index < 5) {
                        PopupThongTinX = 5;
                        PopupThongTinY = viewY + viewHeight - (index + 1) * KhoangCachItem + scrollY;
                        PopupThongTinW = 360;
                        PopupThongTinH = 80;
                        TimeChoHienPopup = 0.3f;
                        if ((PopupThongTinY-120)<0){
                            PopupThongTinY = 125;
                        }
                        if ((PopupThongTinY+PopupThongTinH)>590){
                            PopupThongTinY = 590-PopupThongTinH;
                        }
                        vuaMoPopupThongTin = true;
                    } else if (index == 5) {
                        PopupThongTinW = 600;
                        PopupThongTinX = (1020-PopupThongTinW)/2f;
                        PopupThongTinY = 125;
                        PopupThongTinH = 90;
                        TimeChoHienPopup = 0.3f;
                        vuaMoPopupThongTin = true;
                    } else {
                        PopupThongTinX = 5;
                        PopupThongTinY = viewY + viewHeight - (index + 1) * KhoangCachItem + scrollY;
                        PopupThongTinW = 360;
                        PopupThongTinH = 250;
                        TimeChoHienPopup = 0.3f;
                        if ((PopupThongTinY-120)<0){
                            PopupThongTinY = 125;
                        }
                        if ((PopupThongTinY+PopupThongTinH)>590){
                            PopupThongTinY = 590-PopupThongTinH;
                        }
                        vuaMoPopupThongTin = true;
                    }
                }
            }
        }
        // popup hanh trang
        if (DangHienPopupThongTin1) {
            if (vuaMoPopupThongTin) {
                vuaMoPopupThongTin = false;
                return;
            }
            if (x > 0 && x <= 360 && (y > PopupHanhTrangY + PopupHanhTrangH || y < PopupHanhTrangY - 130)) {
                DangHienPopupThongTin1 = false;
                TimeChoHienPopup = 0;
            } else if (x > 360 && x <= 1020) {
                DangHienPopupThongTin1 = false;
                TimeChoHienPopup = 0;
            }
        }
        if (DangHienPopupThongTin1) {
            float yNut = PopupHanhTrangY - 115;
            if (x > 1 && x < 115 && y >= yNut && y <= yNut + PopupHanhTrangH){
                nutClickTimer3 = 0.3f;
                nuthanhtrangchon=1;
            } else if (x > 121 && x < 115+120 && y >= yNut && y <= yNut + PopupHanhTrangH){
                nutClickTimer3 = 0.3f;
                nuthanhtrangchon=2;
            }
        }
        // Tắt popup thông tin
        if (DangHienPopupThongTin) {
            if (vuaMoPopupThongTin) {
                vuaMoPopupThongTin = false;
                return;
            }
            if (oChiSoDangChon != 5) {
                if (x > 0 && x <= 360 && (y > PopupThongTinY + PopupThongTinH || y < PopupThongTinY - 130)) {
                    DangHienPopupThongTin = false;
                    TimeChoHienPopup = 0;
                } else if (x > 360 && x <= 1020) {
                    DangHienPopupThongTin = false;
                    TimeChoHienPopup = 0;
                }
            } else {
                if (y > PopupThongTinY + PopupThongTinH) {
                    DangHienPopupThongTin = false;
                    TimeChoHienPopup = 0;
                } else if (x < PopupThongTinX || x > PopupThongTinX + PopupThongTinW) {
                    DangHienPopupThongTin = false;
                    TimeChoHienPopup = 0;
                }
            }
        }
        if (DangHienPopupThongTin){
            if (oChiSoDangChon >= 0 && oChiSoDangChon < 4) {
                int[] buocTangTheoChiSo = {20, 20, 1, 1};
                int[] gioiHanToiDa = {550000, 550000, 25000, 3000}; // Giới hạn HP, KI, Sức đánh, Giáp
                int buocTang = buocTangTheoChiSo[oChiSoDangChon];

                int chiSoGoc = switch (oChiSoDangChon) {
                    case 0 -> duLieuNguoiChoi.getHpGoc();
                    case 1 -> duLieuNguoiChoi.getKiGoc();
                    case 2 -> duLieuNguoiChoi.getSucDanhGoc();
                    case 3 -> duLieuNguoiChoi.getGiapGoc();
                    default -> 0;
                };

                float yNut = PopupThongTinY - 115;
                for (int i = 0; i < 3; i++) {
                    int soLanTang = (int) Math.pow(10, i);
                    int giaTriTang = buocTang * soLanTang;
                    long chiPhi = tinhChiPhiTiemNang(oChiSoDangChon, chiSoGoc, soLanTang, buocTang);

                    boolean trongVungNut = (x >= 1 + i * 120 && x <= 1 + i * 120 + 114 && y >= yNut && y <= yNut + 114);
                    boolean duTiemNang = duLieuNguoiChoi.getTiemNangNhanVat() >= chiPhi;
                    boolean khongVuotGioiHan = chiSoGoc + giaTriTang <= gioiHanToiDa[oChiSoDangChon];

                    if (trongVungNut && duTiemNang && khongVuotGioiHan) {
                        nutClickTimer = 0.3f;
                        oChiSoDangChonTamThoi = oChiSoDangChon;
                        giaTriTangTamThoi = giaTriTang;
                        chiPhiTamThoi = chiPhi;
                        nutduocchon=i;
                    }
                }
            } else if (oChiSoDangChon==4){
                int gioiHanToiDa = 10; // Crit tối đa 10%
                float yNut = PopupThongTinY - 115;
                long chiPhi = tinhChiPhiTiemNang(4, duLieuNguoiChoi.getChiMangGoc(), 1, 1);
                if (chiPhi<=0){chiPhi=10000000;}
                boolean trongVungNut = (x >= 1  && x <= 1 + 114 && y >= yNut && y <= yNut + 114);
                boolean duTiemNang = duLieuNguoiChoi.getTiemNangNhanVat() >= chiPhi;
                boolean khongVuotGioiHan = duLieuNguoiChoi.getChiMangGoc() + 1 <= gioiHanToiDa;

                if (trongVungNut && duTiemNang && khongVuotGioiHan) {
                    nutClickTimer = 0.3f;
                    oChiSoDangChonTamThoi = oChiSoDangChon;
                    giaTriTangTamThoi = 1;
                    chiPhiTamThoi = chiPhi;
                }
            } else if (oChiSoDangChon > 5 && oChiSoDangChon <= 15){
                float yNut = PopupThongTinY - 115;
                int capskill = duLieuNguoiChoi.getCapSkill(oChiSoDangChon-6);
                if (capskill >= 1){
                    if (x > 1 && x < 115 && y >= yNut && y <= yNut + PopupThongTinH){
                        nutClickTimer1 = 0.3f;
                        nutduocchon=1;
                    } else if (x > 121 && x < 115+120 && y >= yNut && y <= yNut + PopupThongTinH){
                        nutClickTimer1 = 0.3f;
                        nutduocchon=2;
                    }
                }
                else {
                    if (x > 1 && x < 115 && y >= yNut && y <= yNut + PopupThongTinH){
                        nutClickTimer1 = 0.3f;
                        nutduocchon=2;
                    }
                }
            }
        }
        if (HienPopUpGanSkill){
            if (x < 210 || x > 210 + 4*120 + 114 ){
                HienPopUpGanSkill = false;
            } else if ( x >= 210 && x<=210 + 4*120 && y>120){
                HienPopUpGanSkill = false;
            } else {
                for (int i = 0; i < 5; i++){
                    if (x>=210 + i * 120 && x <= 210 + i * 120+114 && y>=5 && y<=5+114){
                        nutClickTimer2 = 0.3f;
                        nutduocchon = i;
                    }
                }
            }
        }
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
        if (!dangHienPopup || texAvt == null) return;
        //avt + nut X
        batch.draw(popupNhanVat, 0, 0, 350, 610);
        float nutXW = nutX.getWidth() * 0.5f;
        float nutXH = nutX.getHeight() * 0.55f;
        batch.draw(nutX, 350 - nutXW - 6, 610 - nutXH - 2, nutXW, nutXH - 5);

        float texAvtW = texAvt.getWidth() * 0.52f;
        float texAvtH = texAvt.getHeight() * 0.52f;
        batch.draw(texAvt, 0, 505, texAvtW, texAvtH);

        // ===== Vẽ vàng, ngọc =====
        batch.draw(vang, 10, 8, 20, 20);
        batch.draw(ngoc, 275, 7, 20, 20);

        // → Định dạng rút gọn vàng
        String vangHienThi = formatVangNgoc(duLieuNguoiChoi.getVang());
        layout.setText(fontvangngoc, vangHienThi);
        fontvangngoc.draw(batch, layout, 10 + 20 + 10, 22);

        // → Định dạng rút gọn ngọc
        String ngocHienThi = formatVangNgoc(duLieuNguoiChoi.getNgoc());
        layout.setText(fontvangngoc, ngocHienThi);
        fontvangngoc.draw(batch, layout, 275 + 20 + 10, 22);

        // chuc nang
        String[] TextChucnang1 = {
            "Nhiệm",
            "Hành",
            "Kỹ",
            "Bang",
            "Chức"
        };
        String[] TextChucnang2 = {
            "Vụ",
            "Trang",
            "Năng",
            "Hội",
            "năng"
        };
        for (int i = 0; i < 5; i++) {
            Texture nutcn = chucNangDangChon==i ? nutchucnangclick : nutchucnang;
            batch.draw(nutcn, 2+i*68+3, 450, 68, 52);
            layout.setText(fontChucnang,TextChucnang1[i]);
            fontChucnang.draw(batch,layout,2+i*68+3+(68- layout.width)/2f,450 + 41);
            layout.setText(fontChucnang,TextChucnang2[i]);
            fontChucnang.draw(batch,layout,2+i*68+3+(68- layout.width)/2f,450 + 20);
        }
        // noi dung theo chuc nang
        if (chucNangDangChon == 0){
            // Tên nhân vật + thể lực + Cấp bậc + Sức mạnh
            font.setColor(1,1,1,1);
            layout.setText(font, duLieuNguoiChoi.getTen());
            font.draw(batch,layout,125,595);

            layout.setText(fontsm,"Thể lực");
            fontsm.draw(batch,layout,125,570);
            batch.draw(thanhtheluc ,125+68,556);
            layout.setText(fontsm, duLieuNguoiChoi.getCapBac());
            fontsm.draw(batch,layout,125,545);
            // ===== Vẽ sức mạnh =====
            DecimalFormat dinhDang = new DecimalFormat("#,###");

            long sucManh = duLieuNguoiChoi.getSucManh();
            String sucManhHienThi = dinhDang.format(sucManh);
            layout.setText(fontsm, "Sức mạnh: " + sucManhHienThi);
            fontsm.draw(batch, layout, 125, 520);
            // chỉnh OOP theo nhiệm vụ sau cấu trúc ( mô tả, nhiệm vụ cần làm , mô tả dài )
            //mo ta
            layout.setText(fontNhiemVu,"Nhiệm vụ tập luyện"); // cần thay đổi sau
            fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);

            // nhiệm vụ có nhiều phần , chỉ hiện thị phần hiện tại và đã làm
            layout.setText(fontNhiemVu1,"- Đánh ngã 5 mộc nhân (0/5)"); // cần thay đổi sau
            fontNhiemVu1.draw(batch,layout,20,385);
            layout.setText(fontNhiemVuChuaLam,"- ...");
            fontNhiemVuChuaLam.draw(batch,layout,20,385-25);

            // mo ta dai
            layout.setText(fontMotaNhiemVu,
                "Mộc nhân được đặt nhiều tại Làng Aru, " +
                    "ngay trước nhà ông Gohan " +
                    "Hãy đánh ngã 5 mộc nhân, sau đó quay " +
                    "về nhà báo cáo với ông Gohan " +
                    "Để đánh, hãy click đôi vào đối tượng \n" +
                    "Thưởng 3 k sức mạnh \n" +
                    "Thưởng 3 k tiềm năng",
                fontMotaNhiemVu.getColor(), // dùng lại màu đã set // cần thay đổi sau
                290,                 // wrapWidth
                Align.left,          // căn trái mặc định
                true);               // bật tự xuống dòng
            fontMotaNhiemVu.draw(batch,layout,20,385-25-30);
        }
        if (chucNangDangChon == 1){
            // chỉ số nhân vật
            layout.setText(fontsm,"HP: "+(int)duLieuNguoiChoi.getHpHienTai()+" / "+(int)duLieuNguoiChoi.getHpToiDa());
            fontsm.draw(batch,layout,125,595);
            layout.setText(fontsm,"KI: "+(int)duLieuNguoiChoi.getKiHienTai()+" / "+(int)duLieuNguoiChoi.getKiToiDa());
            fontsm.draw(batch,layout,125,570);
            layout.setText(fontsm,"Sức đánh: "+(int)duLieuNguoiChoi.getSucDanhNhanVat()+", Crit: "+duLieuNguoiChoi.getChiMangNhanVat()+"%");
            fontsm.draw(batch,layout,125,545);
            layout.setText(fontsm,"Giáp: "+(int)duLieuNguoiChoi.getGiapNhanVat()+", ST Crit: "+(int)duLieuNguoiChoi.getSatThuongChiMang()+"%");
            fontsm.draw(batch,layout,125,520);

            // ô hành trang
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;

            int soTrangBi = 8;
            ArrayList<Item> danhSachItem = duLieuNguoiChoi.getHanhTrang();
            int soKhac = 22;
            int tongSoTrangBi = soTrangBi + soKhac;

            float totalHeight = tongSoTrangBi * KhoangCachItem;
            maxScroll = Math.max(0, totalHeight - viewHeight);

            batch.flush();
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
            Gdx.gl.glScissor(0, (int)viewY, 350, (int)viewHeight);
            // Vị trí bắt đầu vẽ từ trên xuống
            float startY = viewY + viewHeight - KhoangCachItem + scrollY;
            Texture[] itemNhanVat = {
                ao,quan,gang,giay,rada,iconct,giaplt,vanbay
            };
            ArrayList<Item> danhSachDangMac = duLieuNguoiChoi.getHanhTrangDangMac();
            for (int i = 0; i < soTrangBi; i++) {
                Item item = danhSachDangMac.get(i);
                float y = startY - i * KhoangCachItem;
                Texture tex = (hangTrangDangChon == i) ? hanh_trang_dang_mac_click : hanh_trang_dang_mac;
                batch.draw(tex, 3, y, 344, 50);
                if (itemNhanVat[i]!=null){
                    batch.draw(itemNhanVat[i],3+(70-itemNhanVat[i].getWidth()*0.5f)/2f,y+(49-itemNhanVat[i].getHeight()*0.5f)/2f,itemNhanVat[i].getWidth()*0.5f,itemNhanVat[i].getHeight()*0.5f);
                }
                String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng","Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
                if (item != null) {
                    layout.setText(fontMotaSkill, item.getTenItem());
                    fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);
                }
                if (i == 7 && vanBayDau){
                    layout.setText(fontMotaSkill,"Cân đẩu vân");
                    fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);
                    layout.setText(fontCapSKill,"Dùng để bay không tốn KI");
                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 7 && !vanBayDau){
                    layout.setText(fontCapSKill,item.getMoTa());
                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 6 && item != null) {
                    layout.setText(fontCapSKill,"Hiệu lực trong " + (int) (timeMacGiapLuyenTap / 60f) + " phút");
                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 5 && item != null) {
                    int kc = 0;
                    int soChiso = 0;
                    for (int j = 6; j <= 12; j++) {
                        if (item.getChiso()[j] > 0) {
                            String prefix = (soChiso == 0) ? "" : ",";
                            layout.setText(fontCapSKill, prefix + chisoduoccong[j] + "+" + item.getChiso()[j] + "%");
                            fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
                            kc += layout.width + 1;
                            soChiso++;
                        }
                    }
                }
                if (i == 0 && item != null){
                    layout.setText(fontCapSKill,"Giáp+"+item.getChiso()[4]);
                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 1 && item != null){
                    layout.setText(fontCapSKill,"HP+"+item.getChiso()[9]);
                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 2 && item != null){
                    layout.setText(fontCapSKill,"Tấn công+"+item.getChiso()[11]);
                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 3 && item != null){
                    layout.setText(fontCapSKill,"KI+"+item.getChiso()[10]);
                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 4 && item != null){
                    layout.setText(fontCapSKill,"Chí mạng+"+item.getChiso()[3]+"%");
                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
            }
            for (int i = 0; i < soKhac; i++) {
                float y = startY - (soTrangBi + i) * KhoangCachItem;
                // Vẽ ô nền
                Texture tex = (hangTrangDangChon == soTrangBi + i) ? hanh_trang_click : hanh_trang;
                batch.draw(tex, 3, y, 344, 50);
                // Nếu có item trong danh sách thì vẽ icon
                if (i < danhSachItem.size()) {
                    Item item = danhSachItem.get(i);
                    if (item != null) {
                        Texture icon = item.getTexture();
                        if (icon != null) {
                            float iconWidth = icon.getWidth() * 0.5f;
                            float iconHeight = icon.getHeight() * 0.5f;
                            batch.draw(icon,
                                3 + (70 - iconWidth) / 2f,
                                y + (49 - iconHeight) / 2f,
                                iconWidth,
                                iconHeight);
                        }
                        String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng","Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};

                        layout.setText(fontMotaSkill, item.getTenItem());
                        fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);

                        if (item.getLoai() == LoaiItem.VANBAY){
                            layout.setText(fontCapSKill,item.getMoTa());
                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                        }
                        if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                            layout.setText(fontCapSKill,"Hiệu lực trong " + (int) (timeMacGiapLuyenTap / 60f) + " phút");
                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                        }
                        if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {
                            int kc = 0;
                            int soChiso = 0;
                            for (int j = 6; j <= 12; j++) {
                                if (item.getChiso()[j] > 0) {
                                    String prefix = (soChiso == 0) ? "" : ",";
                                    layout.setText(fontCapSKill, prefix + chisoduoccong[j] + "+" + item.getChiso()[j] + "%");
                                    fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
                                    kc += layout.width + 1;
                                    soChiso++;
                                }
                            }
                        }
                        if (item.getLoai() == LoaiItem.AO){
                            layout.setText(fontCapSKill,"Giáp+"+item.getChiso()[4]);
                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                        }
                        if (item.getLoai() == LoaiItem.QUAN){
                            layout.setText(fontCapSKill,"HP+"+item.getChiso()[9]);
                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                        }
                        if (item.getLoai() == LoaiItem.GANG){
                            layout.setText(fontCapSKill,"Tấn công+"+item.getChiso()[11]);
                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                        }
                        if (item.getLoai() == LoaiItem.GIAY){
                            layout.setText(fontCapSKill,"KI+"+item.getChiso()[10]);
                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                        }
                        if (item.getLoai() == LoaiItem.RADA){
                            layout.setText(fontCapSKill,"Chí mạng+"+item.getChiso()[3]+"%");
                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                        }
                    }
                }
            }
            batch.flush();
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
            batch.end();
            if (DangHienPopupThongTin1 && TimeChoHienPopup <= 0) {
                PopupHanhTrang(shapeRenderer,batch, PopupHanhTrangX, PopupHanhTrangY ,PopupHanhTrangW ,hangTrangDangChon);
            }
            batch.begin();
        }


        if (chucNangDangChon == 2){
            DecimalFormat dinhDang = new DecimalFormat("#,###");
            layout.setText(fontTiemNang,"Top 1");
            fontTiemNang.draw(batch,layout,105+(225-layout.width)/2f,600);
            layout.setText(fontvangngoc,"Điểm tiềm năng");
            fontvangngoc.draw(batch,layout,105+(225-layout.width)/2f,575);
            layout.setText(fontTiemNang,dinhDang.format(duLieuNguoiChoi.getTiemNangNhanVat()));
            fontTiemNang.draw(batch,layout,105+(225-layout.width)/2f,550);
            layout.setText(fontvangngoc,"Năng động: "+dinhDang.format(duLieuNguoiChoi.getDiemSoiDongNhanVat()));
            fontvangngoc.draw(batch,layout,105+(225-layout.width)/2f,525);
            String[] textChiSoCoBan1 = {
                "HP gốc: " + dinhDang.format(duLieuNguoiChoi.getHpGoc()),
                "KI gốc: " + dinhDang.format(duLieuNguoiChoi.getKiGoc()),
                "Sức đánh gốc: " + dinhDang.format(duLieuNguoiChoi.getSucDanhGoc()),
                "Giáp gốc: " + dinhDang.format(duLieuNguoiChoi.getGiapGoc()),
                "Crit gốc: " + duLieuNguoiChoi.getChiMangGoc() +"%"
            };
            String[] textChiSoCoBan2 = {
                dinhDang.format(duLieuNguoiChoi.getHpGoc()+1000)+" tiềm năng: tăng 20",
                dinhDang.format(duLieuNguoiChoi.getKiGoc()+1000)+" tiềm năng: tăng 20",
                dinhDang.format(duLieuNguoiChoi.getSucDanhGoc()*100)+" tiềm năng: tăng 1",
                dinhDang.format(500000+duLieuNguoiChoi.getGiapGoc()*100000)+" tiềm năng: tăng 1",
                formatVangNgoc(30000000+(duLieuNguoiChoi.getChiMangGoc()-1)*5000000000L)+" tiềm năng: tăng 1"
            };
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachO = 61;

            int oChiSoCoBan = 5;
            int oNoiTai = 1;
            int oSKill = 9;
            int tongO = oChiSoCoBan + oNoiTai + oSKill;

            float totalHeight = tongO * KhoangCachO;
            maxScroll = Math.max(0, totalHeight - viewHeight);

            batch.flush();
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
            Gdx.gl.glScissor(0, (int)viewY, 350, (int)viewHeight);

            // Vị trí bắt đầu vẽ từ trên xuống
            float startY = viewY + viewHeight - KhoangCachO + scrollY;

            for (int i = 0; i < oChiSoCoBan; i++) {
                float y = startY - i * KhoangCachO;
                Texture tex = (oChiSoDangChon == i) ? o_chi_so_co_ban_click :o_chi_so_co_ban;
                batch.draw(tex, 350-288-3, y, 288, 62);
                batch.draw(oskill,3,y+2,350-288-3-3,58);
                batch.draw(iconchisocoban[i], 3 + 8, y+2 + 8, 350-288-3-3 - 16, 58 - 16);
                layout.setText(fontSkilldaco,textChiSoCoBan1[i]);
                fontSkilldaco.draw(batch,layout,350-288+8,y+50);
                layout.setText(fontMotaSkill,textChiSoCoBan2[i]);
                fontMotaSkill.draw(batch,layout,350-288+8,y+22);
            }

            for (int i = 0; i < oNoiTai; i++) {
                float y = startY - (oChiSoCoBan + i) * KhoangCachO;
                Texture tex = (oChiSoDangChon == oChiSoCoBan + i) ? o_noi_tai_click : o_noi_tai;
                batch.draw(tex, 350-288-3, y, 288, 62);
                layout.setText(
                    fontMotaNoiTai,
                    "Khiên năng lượng +55% tốc độ hồi phục [15 đến 55]",
                    fontMotaNoiTai.getColor(), // dùng lại màu đã set
                    250,                 // wrapWidth
                    Align.left,          // căn trái mặc định
                    true);               // bật tự xuống dòng
                fontMotaNoiTai.draw(batch, layout, 350 - 288 + 8, y + 45);
                batch.draw(oskill,3,y+2,350-288-3-3,58);
                batch.draw(iconnoitai, 3 + 8, y+2 + 8, 350-288-3-3 - 16, 58 - 16);
            }
            for (int i = 0; i < oSKill; i++) {
                float y = startY - (oChiSoCoBan + oNoiTai + i) * KhoangCachO;
                Texture tex = (oChiSoDangChon == oChiSoCoBan + oNoiTai + i) ?  o_chi_so_co_ban_click :o_chi_so_co_ban;
                batch.draw(tex, 350-288-3, y, 288, 62);
                int capskill = duLieuNguoiChoi.getCapSkill(i);
                if (capskill >= 1) {
                    layout.setText(fontSkilldaco, duLieuNguoiChoi.getTenSkill(i));
                    fontSkilldaco.draw(batch, layout, 350 - 288 + 8, y + 50);
                    layout.setText(fontMotaSkill, "Đã mở khóa skill");
                    fontMotaSkill.draw(batch, layout, 350 - 288 + 8, y + 22);
                    layout.setText(fontCapSKill, "Cấp: "+capskill);
                    fontCapSKill.draw(batch, layout, 350 - 60, y + 50);
                } else {
                    layout.setText(fontSkillchuaco, duLieuNguoiChoi.getTenSkill(i));
                    fontSkillchuaco.draw(batch, layout, 350 - 288 + 8, y + 50);
                    layout.setText(fontMotaSkill, "Chưa mở khóa skill");
                    fontMotaSkill.draw(batch, layout, 350 - 288 + 8, y + 22);
                }
                batch.draw(oskill,3,y+2,350-288-3-3,58);
                if (skillIcons != null && skillIcons[i] != null) {
                    batch.draw(skillIcons[i].icon, 3 + 8, y+2 + 8, 350-288-3-3 - 16, 58 - 16);
                }
            }
            batch.flush();
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
            batch.end();
            if (DangHienPopupThongTin && TimeChoHienPopup <= 0) {
                PopupThongTin(shapeRenderer,batch, PopupThongTinX, PopupThongTinY ,PopupThongTinW , PopupThongTinH ,oChiSoDangChon);
            }
            batch.begin();
            if (HienPopUpGanSkill) {
                for (int i = 0; i < 5; i++) {
                    if (nutduocchon==i) {
                        Texture nutVe = nutClickTimer2 > 0 ? nutvuongclick : nutvuong;
                        batch.draw(nutVe , 210 + i * 120, 5, 114, 114);
                    }
                    else {
                        batch.draw(nutvuong , 210 + i * 120, 5, 114, 114);
                    }
                    layout.setText(font,"Vào");
                    font.draw(batch,layout,210 + i * 120+(114- layout.width)/2f,5+114-40);
                    layout.setText(font,"phím "+(i+1));
                    font.draw(batch,layout,210 + i * 120+(114- layout.width)/2f,5+114-65);
                }
            }
        }
        if (chucNangDangChon == 3){
            layout.setText(fontNhiemVu,"Bang hội đang phát triển!");
            fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);
        }
        if (chucNangDangChon == 4){
            layout.setText(fontNhiemVu,"Chức năng đang phát triển!");
            fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);
        }
    }
    public int getChucNangDangChon() {
        return chucNangDangChon;
    }
    private String formatVangNgoc(long so) {
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
    void PopupThongTin(ShapeRenderer shapeRenderer,SpriteBatch batch, float x, float y , float width, float height , int oChiSoDangChon) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++) {
            shapeRenderer.rect(x - i, y - i, width + i * 2, height + i * 2);
        }
        shapeRenderer.end();
        batch.begin();
        if (oChiSoDangChon<5){
            if (oChiSoDangChon != 4) {
                DecimalFormat dinhDang = new DecimalFormat("#,###");
                String[] tenChiSo = {"HP", "KI", "SĐ", "Giáp"};
                int[] buocTangTheoChiSo = {20, 20, 1, 1}; // mỗi lần tăng bao nhiêu cho từng chỉ số
                int buocTang = buocTangTheoChiSo[oChiSoDangChon];
                //xử lí text thông tin
                String[] textChiSoCoBan2 = {
                    dinhDang.format(duLieuNguoiChoi.getHpGoc()+1000),
                    dinhDang.format(duLieuNguoiChoi.getKiGoc()+1000),
                    dinhDang.format(duLieuNguoiChoi.getSucDanhGoc()*100),
                    dinhDang.format(500000+duLieuNguoiChoi.getGiapGoc()*100000),
                };
                layout.setText(fontCapSKill,"Sử dụng "+textChiSoCoBan2[oChiSoDangChon]+" tiềm năng");
                fontCapSKill.draw(batch,layout,PopupThongTinX+(PopupThongTinW- layout.width)/2f,PopupThongTinY+53);
                layout.setText(fontCapSKill,"để tăng "+buocTang+" "+tenChiSo[oChiSoDangChon]+" gốc");
                fontCapSKill.draw(batch,layout,PopupThongTinX+(PopupThongTinW- layout.width)/2f,PopupThongTinY+33);
                //xử lí nút
                float[] chiSoGocArray = {
                    duLieuNguoiChoi.getHpGoc(),
                    duLieuNguoiChoi.getKiGoc(),
                    duLieuNguoiChoi.getSucDanhGoc(),
                    duLieuNguoiChoi.getGiapGoc()
                };
                int chiSoGoc = (int) chiSoGocArray[oChiSoDangChon];
                String ten = tenChiSo[oChiSoDangChon];

                int[] tangGiaTri = new int[3];
                long[] chiPhiTiemNang = new long[3];

                for (int i = 0; i < 3; i++) {
                    int soLanTang = (int) Math.pow(10, i); // x1, x10, x100
                    tangGiaTri[i] = buocTang * soLanTang;
                    chiPhiTiemNang[i] = tinhChiPhiTiemNang(oChiSoDangChon, chiSoGoc, soLanTang, buocTang);
                }
                for (int i = 0; i < 3; i++) {
                    float nutX = 1 + i * 120;
                    float nutY = y - 115;
                    if (nutduocchon==i) {
                        Texture nutVe = nutClickTimer > 0 ? nutvuongclick : nutvuong;
                        batch.draw(nutVe , nutX, nutY, 114, 114);
                    }
                    else {
                        batch.draw(nutvuong , nutX, nutY, 114, 114);
                    }

                    font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);

                    layout.setText(font, "Tăng");
                    font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 30);

                    layout.setText(font, tangGiaTri[i] + ten);
                    font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 55);

                    layout.setText(font, "-"+formatVangNgoc(chiPhiTiemNang[i]));
                    font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 80);
                }

            } else {
                layout.setText(fontCapSKill,"Sử dụng "+formatVangNgoc(30000000+(duLieuNguoiChoi.getChiMangGoc()-1)*5000000000L)+" tiềm năng");
                fontCapSKill.draw(batch,layout,PopupThongTinX+(PopupThongTinW- layout.width)/2f,PopupThongTinY+53);
                layout.setText(fontCapSKill,"để tăng "+1+" "+"chí mạng gốc");
                fontCapSKill.draw(batch,layout,PopupThongTinX+(PopupThongTinW- layout.width)/2f,PopupThongTinY+33);
                Texture nutVe = nutClickTimer > 0 ? nutvuongclick : nutvuong;
                batch.draw(nutVe,1,y - 115, 114, 114);
                layout.setText(font, "Tăng");
                font.draw(batch, layout, 1 + (114 - layout.width) / 2f, y - 115 + 114 - 30);
                layout.setText(font, "1 Crit");
                font.draw(batch, layout, 1 + (114 - layout.width) / 2f, y - 115 + 114 - 55);
                layout.setText(font, formatVangNgoc(30000000 + (duLieuNguoiChoi.getChiMangGoc() - 1) * 5000000000L) + "");
                font.draw(batch, layout, 1 + (114 - layout.width) / 2f, y - 115 + 114 - 80);
            }
        } else if (oChiSoDangChon>5 && oChiSoDangChon <= 15) {
            int capskill = duLieuNguoiChoi.getCapSkill(oChiSoDangChon-6);
            if (capskill >= 1){
                // Mo ta skill
                fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(fontTenSkill,duLieuNguoiChoi.getTenSkill(oChiSoDangChon-6));
                fontTenSkill.draw(batch,layout,PopupThongTinX+(PopupThongTinW- layout.width)/2f,PopupThongTinY+PopupThongTinH-23);

                layout.setText(fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[0]);
                fontMotaNganSkill.draw(batch,layout,PopupThongTinX+(PopupThongTinW- layout.width)/2f,PopupThongTinY+PopupThongTinH-48);

                layout.setText(font,"____________________________________");
                for (int i = 0; i < 2; i++) {
                    font.draw(batch, layout, PopupThongTinX + (PopupThongTinW - layout.width) / 2f, PopupThongTinY + PopupThongTinH - 60 - i*1);
                }

                layout.setText(fontSkilldaco,"Cấp độ: "+capskill);
                fontSkilldaco.draw(batch,layout,PopupThongTinX + (PopupThongTinW - layout.width) / 2f,PopupThongTinY + PopupThongTinH - 95);

                layout.setText(fontMotaNganSkill1,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[1]);
                fontMotaNganSkill1.draw(batch,layout,PopupThongTinX + (PopupThongTinW - layout.width) / 2f,PopupThongTinY + PopupThongTinH - 120);

                layout.setText(fontMotaNganSkill1,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[2]);
                fontMotaNganSkill1.draw(batch,layout,PopupThongTinX + (PopupThongTinW - layout.width) / 2f,PopupThongTinY + PopupThongTinH - 145);

                layout.setText(fontMotaNganSkill1,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[3]);
                fontMotaNganSkill1.draw(batch,layout,PopupThongTinX + (PopupThongTinW - layout.width) / 2f,PopupThongTinY + PopupThongTinH - 170);

                layout.setText(font,"____________________________________");
                for (int i = 0; i < 2; i++) {
                    font.draw(batch, layout, PopupThongTinX + (PopupThongTinW - layout.width) / 2f, PopupThongTinY + PopupThongTinH - 182 - i*1);
                }

                layout.setText(fontTenSkill,"Đã mở khóa skill");
                fontTenSkill.draw(batch,layout,PopupThongTinX+(PopupThongTinW- layout.width)/2f,PopupThongTinY+PopupThongTinH-217);

                for (int i = 0; i < 2; i++) {
                    float nutX = 1 + i * 120;
                    float nutY = y - 115;
                    if (nutduocchon == i+1) {
                        Texture nutVe = nutClickTimer1 > 0 ? nutvuongclick : nutvuong;
                        batch.draw(nutVe, nutX, nutY, 114, 114);
                    } else {
                        batch.draw(nutvuong, nutX, nutY, 114, 114);
                    }

                    font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    if (i == 0) {
                        layout.setText(font, "Gán ô");
                        font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 40);

                        layout.setText(font, "Phím tắt");
                        font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 68);
                    } else {
                        layout.setText(font, "Đóng");
                        font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                    }
                }
            } else {
                // Mo ta skill
                fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(fontTenSkill,duLieuNguoiChoi.getTenSkill(oChiSoDangChon-6));
                fontTenSkill.draw(batch,layout,PopupThongTinX+(PopupThongTinW- layout.width)/2f,PopupThongTinY+PopupThongTinH-23);

                layout.setText(fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[0]);
                fontMotaNganSkill.draw(batch,layout,PopupThongTinX+(PopupThongTinW- layout.width)/2f,PopupThongTinY+PopupThongTinH-48);

                layout.setText(font,"____________________________________");
                for (int i = 0; i < 2; i++) {
                    font.draw(batch, layout, PopupThongTinX + (PopupThongTinW - layout.width) / 2f, PopupThongTinY + PopupThongTinH - 60 - i*1);
                }

                layout.setText(fontSkilldaco,"Chưa học");
                fontSkilldaco.draw(batch,layout,PopupThongTinX + (PopupThongTinW - layout.width) / 2f,PopupThongTinY + PopupThongTinH - 95);

                layout.setText(fontSkillchuaco1,"Để học cần đủ sức mạnh");
                fontSkillchuaco1.draw(batch,layout,PopupThongTinX + (PopupThongTinW - layout.width) / 2f,PopupThongTinY + PopupThongTinH - 120);

                layout.setText(fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[1]);
                fontMotaNganSkill.draw(batch,layout,PopupThongTinX + (PopupThongTinW - layout.width) / 2f,PopupThongTinY + PopupThongTinH - 145);

                layout.setText(fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[2]);
                fontMotaNganSkill.draw(batch,layout,PopupThongTinX + (PopupThongTinW - layout.width) / 2f,PopupThongTinY + PopupThongTinH - 170);

                layout.setText(fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[3]);
                fontMotaNganSkill.draw(batch,layout,PopupThongTinX + (PopupThongTinW - layout.width) / 2f,PopupThongTinY + PopupThongTinH - 195);

                layout.setText(font,"____________________________________");
                for (int i = 0; i < 2; i++) {
                    font.draw(batch, layout, PopupThongTinX + (PopupThongTinW - layout.width) / 2f, PopupThongTinY + PopupThongTinH - 207 - i*1);
                }

                Texture nutVe = nutClickTimer1 > 0 ? nutvuongclick : nutvuong;
                batch.draw(nutVe,1,y - 115, 114, 114);
                layout.setText(font, "Đóng");
                font.draw(batch, layout, 1 + (114 - layout.width) / 2f, y - 115 + 114 - 52);
            }
        }
        batch.end();
    }
    void PopupHanhTrang(ShapeRenderer shapeRenderer,SpriteBatch batch, float x, float y , float width , int oHanhTrangDangChon) {
        String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng","Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
        PopupHanhTrangH = 0;
        if (itemm!=null) {
            if ("giapluyentap".equals(itemDangChon)) {
                layout.setText(fontTenSkill, itemm.getTenItem());
                PopupHanhTrangH += layout.height + 10;
                layout.setText(fontSkillchuaco, "Hiệu lực trong " + (int) (timeMacGiapLuyenTap / 60f) + " phút");
                PopupHanhTrangH += layout.height + 10;
                for (int i = 6; i <= 12; i++) {
                    if (itemm.getChiso()[i] > 0) {
                        layout.setText(fontSkillchuaco, chisoduoccong[i] + "+" + itemm.getChiso()[i] + "%");
                        PopupHanhTrangH += layout.height + 10;
                    }
                }
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + itemm.getSucManhYeuCau());
                PopupHanhTrangH += layout.height + 10;
                layout.setText(font, "____________________________________");
                PopupHanhTrangH += layout.height + 10;
                layout.setText(
                    fontMotaHanhTrang,
                    itemm.getMoTa(),
                    fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupHanhTrangH += layout.height + 10;
                if (itemm.getSoSaoPhaLe() > 0) {
                    PopupHanhTrangH += 50;
                }
            }
            // --- VẼ BACKGROUND BẰNG SHAPERENDERER ---
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.rect(x, y, width, PopupHanhTrangH);
            shapeRenderer.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            for (int i = 0; i < 2; i++) {
                shapeRenderer.rect(x - i, y - i, width + i * 2, PopupHanhTrangH + i * 2);
            }
            shapeRenderer.end();

            // --- GIỚI HẠN VỊ TRÍ POPUP ---
            if ((PopupHanhTrangY - 120) < 0) {
                PopupHanhTrangY = 125;
            }
            if ((PopupHanhTrangY + PopupHanhTrangH) > 590) {
                PopupHanhTrangY = 590 - PopupHanhTrangH;
            }

            batch.begin();
            // --- VẼ THÔNG TIN ITEM ---
            if ("giapluyentap".equals(itemDangChon)) {
                float offsetY = 10;
                if (itemm.getTexture() != null) {
                    batch.draw(itemm.getTexture(), PopupHanhTrangX + 15, PopupHanhTrangY + PopupHanhTrangH - itemm.getTexture().getHeight() * 0.5f - offsetY, itemm.getTexture().getWidth() * 0.5f, itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(fontTenSkill, itemm.getTenItem());
                fontTenSkill.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;

                layout.setText(fontSkillchuaco, "Hiệu lực trong " + (int) (timeMacGiapLuyenTap / 60f) + " phút");
                fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;

                for (int i = 6; i <= 12; i++) {
                    if (itemm.getChiso()[i] > 0 && i != 8) {
                        layout.setText(fontSkillchuaco, chisoduoccong[i] + "+" + itemm.getChiso()[i] + "%");
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + itemm.getSucManhYeuCau());
                fontMotaHanhTrang.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                offsetY += layout.height;
                layout.setText(font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    font.draw(batch, layout, PopupHanhTrangX + (PopupHanhTrangW - layout.width) / 2f, PopupHanhTrangY + PopupHanhTrangH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    fontMotaHanhTrang,
                    itemm.getMoTa(),
                    fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                fontMotaHanhTrang.draw(batch, layout, PopupHanhTrangX + (PopupHanhTrangW - layout.width) / 2f, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                offsetY += layout.height + 30;

                if (itemm.getSoSaoPhaLe() > 0) {
                    float saoxanhW = saoxanh.getWidth() * 0.5f;
                    float saoxanhH = saoxanh.getHeight() * 0.5f;
                    float spacing = 40f;
                    int soSao = itemm.getSoSaoPhaLe();

                    float totalW = (soSao - 1) * spacing + saoxanhW;

                    float startX = PopupHanhTrangX + (PopupHanhTrangW - totalW) / 2f;

                    for (int i = 0; i < soSao; i++) {
                        float drawX = startX + i * spacing;
                        if (i < itemm.getSoSaoPhaLeCuongHoa()) {
                            batch.draw(saoxanh, drawX, PopupHanhTrangY + PopupHanhTrangH - offsetY, saoxanhW, saoxanhH);
                        } else {
                            batch.draw(saoden, drawX, PopupHanhTrangY + PopupHanhTrangH - offsetY, saoxanhW, saoxanhH);
                        }
                    }
                }
            }
        }
        if (itemm!=null) {
            for (int i = 0; i < 2; i++) {
                float nutX = 1 + i * 120;
                float nutY = y - 115;
                if (nuthanhtrangchon == i + 1) {
                    Texture nutVe = nutClickTimer3 > 0 ? nutvuongclick : nutvuong;
                    batch.draw(nutVe, nutX, nutY, 114, 114);
                } else {
                    batch.draw(nutvuong, nutX, nutY, 114, 114);
                }

                font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                if (i == 0) {
                    if (oHanhTrangDangChon < 8) {
                        layout.setText(font, "Lấy ra");
                        font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                    } else {
                        layout.setText(font, "Sử dụng");
                        font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                    }
                } else {
                    layout.setText(font, "Bỏ ra");
                    font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                }
            }
        }
        batch.end();
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
    private NhanVatCauHinh Doicaitrang(String TenCaiTrang){
        return NhanVatXuLy.xuly_id("caitrang_"+TenCaiTrang);
    }
    private NhanVatCauHinh Doi_avt_ao_quan(String HanhTinh, String TenAvatar , String ao, String quan){
        return NhanVatXuLy.xuly_id("avatar_"+HanhTinh+"+"+TenAvatar+"+"+ao+"+"+quan);
    }

    public void macDo(int index) {
        if (index <= 7 && index >= 0){
            if (index == 5) {
                goCaiTrang(NhanVatXuLy.getDangMacCaiTrang(),false);
            } else if (index == 0) {
                goAo(false);
            } else if (index == 1) {
                goQuan(false);
            } else if (index == 2) {
                goGang(false);
            } else if (index == 3) {
                goGiay(false);
            } else if (index == 4) {
                goRada(false);
            } else if (index == 6) {
                goGiapLuyenTap(false);
            }
        }
        if (index >= 8) {
            int indexx = hangTrangDangChon - 8;
            ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();

            if (indexx < danhSach.size()) {
                Item item = danhSach.get(indexx);
                itemm = item;
                if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {
                    if (item.getLoai() == LoaiItem.CAITRANG ){
                        itemDangChon = "caitrang";
                    } else { itemDangChon = "avatar"; }

                    boolean loaiCaiTrangDangMac = NhanVatXuLy.getDangMacCaiTrang(); // cái đang mặc
                    boolean laCaiTrang = item.getLoai() == LoaiItem.CAITRANG;       // cái sắp mặc
                    if (iconct == null) {
                        // Gán cải trang mới, không có cái cũ
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,5);
                        iconct = item.getTexture();
                        nhanVat.setIdCaiTrang(item.getId());
                        nhanVat.setTenCaiTrang(item.getTenItem());
                        nhanVat.setMoTaCaiTrang(item.getMoTa());
                        nhanVat.setChisoCaiTrang(item.getChiso());
                        nhanVat.setHanSuDungCaiTrang(item.getHanSuDung());
                        nhanVat.setHanhTinhCaiTrang(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauCaiTrang(item.getSucManhYeuCau());
                        avatardangmac = laCaiTrang ? nhanVat.getNhanvat() + "_base" : item.getId();

                        NhanVatCauHinh c2 = laCaiTrang ? Doicaitrang(item.getId()) : Doi_avt_ao_quan(nhanVat.getHanhtinh(), item.getId(), aodangmac, quandangmac);
                        nhanVat.fixCaiTrang(
                            c2.dau_dung, c2.dau_chay,
                            c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                            c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                            c2.than_bay, c2.chan_bay,
                            c2.lechMap,
                            c2.avt
                        );
                        texAvt = new Texture(nhanVat.doiavatar());
                        tangchiso(item.getChiso());
                        danhSach.remove(indexx);
                    } else {
                        macCaiTrangMoi(item, indexx, danhSach, loaiCaiTrangDangMac, laCaiTrang);
                    }
                } else if (item.getLoai() == LoaiItem.AO) {
                    itemDangChon = "ao";
                    if (ao == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,0);
                        ao = item.getTexture();
                        nhanVat.setIdAo(item.getId());
                        nhanVat.setTenAo(item.getTenItem());
                        nhanVat.setMoTaAo(item.getMoTa());
                        nhanVat.setChisoAo(item.getChiso());
                        aodangmac = item.getId();
                        skha = item.getSetkichhoat();
                        nhanVat.setSoSaoAo(item.getSoSaoPhaLe());
                        nhanVat.setSoCapAo(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaAo(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhAo(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauAo(item.getSucManhYeuCau());
                        if (!NhanVatXuLy.getDangMacCaiTrang()) {
                            NhanVatCauHinh c2 = Doi_avt_ao_quan(nhanVat.getHanhtinh(), avatardangmac, item.getId(), quandangmac);
                            nhanVat.fixCaiTrang(
                                c2.dau_dung, c2.dau_chay,
                                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                                c2.than_bay, c2.chan_bay,
                                c2.lechMap,
                                c2.avt
                            );
                        }
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacAo(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macAoMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.QUAN) {
                    itemDangChon = "quan";
                    if (quan == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,1);
                        quan = item.getTexture();
                        nhanVat.setIdQuan(item.getId());
                        nhanVat.setTenQuan(item.getTenItem());
                        nhanVat.setMoTaQuan(item.getMoTa());
                        nhanVat.setChisoQuan(item.getChiso());
                        quandangmac = item.getId();
                        skhq = item.getSetkichhoat();
                        nhanVat.setSoSaoQuan(item.getSoSaoPhaLe());
                        nhanVat.setSoCapQuan(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaQuan(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhQuan(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauQuan(item.getSucManhYeuCau());
                        if (!NhanVatXuLy.getDangMacCaiTrang()) {
                            NhanVatCauHinh c2 = Doi_avt_ao_quan(nhanVat.getHanhtinh(), avatardangmac, aodangmac, item.getId());
                            nhanVat.fixCaiTrang(
                                c2.dau_dung, c2.dau_chay,
                                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                                c2.than_bay, c2.chan_bay,
                                c2.lechMap,
                                c2.avt
                            );
                        }
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacQuan(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macQuanMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.GANG) {
                    itemDangChon = "gang";
                    if (gang == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,2);
                        gang = item.getTexture();
                        nhanVat.setIdGang(item.getId());
                        nhanVat.setTenGang(item.getTenItem());
                        nhanVat.setMoTaGang(item.getMoTa());
                        nhanVat.setChisoGang(item.getChiso());
                        skhg = item.getSetkichhoat();
                        nhanVat.setSoSaoGang(item.getSoSaoPhaLe());
                        nhanVat.setSoCapGang(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaGang(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhGang(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauGang(item.getSucManhYeuCau());
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacGang(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macGangMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.GIAY) {
                    itemDangChon = "giay";
                    if (giay == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,3);
                        giay = item.getTexture();
                        nhanVat.setIdGiay(item.getId());
                        nhanVat.setTenGiay(item.getTenItem());
                        nhanVat.setMoTaGiay(item.getMoTa());
                        nhanVat.setChisoGiay(item.getChiso());
                        skhj = item.getSetkichhoat();
                        nhanVat.setSoSaoGiay(item.getSoSaoPhaLe());
                        nhanVat.setSoCapGiay(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaGiay(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhGiay(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauGiay(item.getSucManhYeuCau());
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacGiay(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macGiayMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.RADA) {
                    itemDangChon = "rada";
                    if (rada == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,4);
                        rada = item.getTexture();
                        nhanVat.setIdRada(item.getId());
                        nhanVat.setTenRada(item.getTenItem());
                        nhanVat.setMoTaRada(item.getMoTa());
                        nhanVat.setChisoRada(item.getChiso());
                        skhrada = item.getSetkichhoat();
                        nhanVat.setSoSaoRada(item.getSoSaoPhaLe());
                        nhanVat.setSoCapRada(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaRada(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhRada(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauRada(item.getSucManhYeuCau());
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacRada(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macRadaMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                    itemDangChon = "giapluyentap";
                    if (giaplt == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,6);
                        giaplt = item.getTexture();
                        nhanVat.setIdGiapLuyenTap(item.getId());
                        nhanVat.setTenGiapLuyenTap(item.getTenItem());
                        nhanVat.setMoTaGiapLuyenTap(item.getMoTa());
                        nhanVat.setChisoGiapLuyenTap(item.getChiso());
                        nhanVat.setSoSaoGiapLuyenTap(item.getSoSaoPhaLe());
                        nhanVat.setSoSaoCuongHoaGlt(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanSuDungGiapLuyenTap(timeMacGiapLuyenTap);
                        nhanVat.setHanhTinhGiapLuyenTap(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauGiapLuyenTap(item.getSucManhYeuCau());
                        dangMacGiapLuyenTap = true;
                        if (!lanDau) {
                            duLieuNguoiChoi.giamSucDanhPt(chisovuathao[8]);
                            duLieuNguoiChoi.tangHpPt(item.getChiso()[6]);
                            duLieuNguoiChoi.checkGiapLuyenTap(true,0);
                        } else {
                            lanDau = false;
                            duLieuNguoiChoi.checkGiapLuyenTap(true,0);
                            duLieuNguoiChoi.tangHpPt(item.getChiso()[6]);
                        }
                        danhSach.remove(indexx);
                    } else {
                        macGiapLuyenTapMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.VANBAY) {
                    itemDangChon = "vanbay";
                    macVanBayMoi(item, indexx, danhSach);
                }
            }
        }
    }
    private void macAoMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu thông tin áo cũ
        String idCu = nhanVat.getIdAo();
        String tenCu = nhanVat.getTenAo();
        String motacu = nhanVat.getMoTaAo();
        int[] chisocu = nhanVat.getChisoAo();
        String skhaCu = skha; // lưu lại skha cũ
        int sosaocu = nhanVat.getSoSaoAo();
        int socapcu = nhanVat.getSoCapAo();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaAo();
        String hanhtinhcu = nhanVat.getHanhTinhAo();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauAo();
        LoaiItem loaiCu = LoaiItem.AO;
        Item aoCu = new Item(idCu, tenCu, loaiCu, ao, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhaCu,sosaocu ,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ trước
        huyHieuUngSet(skhaCu);
        duLieuNguoiChoi.dangMacAo(false);
        // 3. Giảm chỉ số áo cũ
        giamchiso(chisocu);

        // 4. Cập nhật áo mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,0);
        ao = item.getTexture();
        nhanVat.setIdAo(item.getId());
        nhanVat.setTenAo(item.getTenItem());
        nhanVat.setMoTaAo(item.getMoTa());
        nhanVat.setChisoAo(item.getChiso());
        skha = item.getSetkichhoat();
        aodangmac = item.getId();
        nhanVat.setSoSaoAo(item.getSoSaoPhaLe());
        nhanVat.setSoCapAo(item.getSoCap());
        nhanVat.setSoSaoCuongHoaAo(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhAo(item.getHanhtinh());
        nhanVat.setSucManhYeuCauAo(item.getSucManhYeuCau());

        // 5. Load avatar nếu không cải trang
        if (!NhanVatXuLy.getDangMacCaiTrang()) {
            NhanVatCauHinh c2 = Doi_avt_ao_quan(nhanVat.getHanhtinh(), avatardangmac, item.getId(), quandangmac);
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );
        }

        // 6. Tăng chỉ số áo mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacAo(true);
        // 7. Kích hoạt lại set nếu đủ
        kichHoatSetHienTai();

        // 8. Đưa áo cũ vào hành trang
        danhSach.set(indexx, aoCu);
    }

    private void goAo(boolean vut) {
        if (ao == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,0);
        // 1. Lưu áo cũ
        String idCu = nhanVat.getIdAo();
        String tenCu = nhanVat.getTenAo();
        String motacu = nhanVat.getMoTaAo();
        int[] chisocu = nhanVat.getChisoAo();
        String skhaCu = skha;
        int sosaocu = nhanVat.getSoSaoAo();
        int socapcu = nhanVat.getSoCapAo();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaAo();
        String hanhtinhcu = nhanVat.getHanhTinhAo();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauAo();
        LoaiItem loaiCu = LoaiItem.AO;
        Item aoCu = new Item(idCu, tenCu, loaiCu, ao, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhaCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSet(skhaCu);
        duLieuNguoiChoi.dangMacAo(false);
        // 3. Giảm chỉ số áo
        giamchiso(chisocu);

        // 4. Cập nhật trạng thái không mặc
        ao = null;
        skha = "mac_dinh";
        aodangmac = "set_base";

        // 5. Cập nhật giao diện nếu không cải trang
        if (!NhanVatXuLy.getDangMacCaiTrang()){
            NhanVatCauHinh c2 = Doi_avt_ao_quan(nhanVat.getHanhtinh(), avatardangmac, "set_base", quandangmac);
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );
        }

        // 6. Kích hoạt lại set nếu đủ 4 món còn lại
        kichHoatSetHienTai();

        // 7. Trả áo cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(aoCu);
        }
    }

    private void macQuanMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu thông tin quần cũ
        String idCu = nhanVat.getIdQuan();
        String tenCu = nhanVat.getTenQuan();
        String motacu = nhanVat.getMoTaQuan();
        int[] chisocu = nhanVat.getChisoQuan();
        String skhqCu = skhq; // lưu lại skhq cũ
        int sosaocu = nhanVat.getSoSaoQuan();
        int socapcu = nhanVat.getSoCapQuan();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaQuan();
        String hanhtinhcu = nhanVat.getHanhTinhQuan();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauQuan();
        LoaiItem loaiCu = LoaiItem.QUAN;
        Item quanCu = new Item(idCu, tenCu, loaiCu, quan, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhqCu,sosaocu ,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ trước khi gỡ chỉ số
        huyHieuUngSet(skhqCu);
        duLieuNguoiChoi.dangMacQuan(false);
        // 3. Giảm chỉ số quần cũ
        giamchiso(chisocu);

        // 4. Gán quần mới vào
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,1);
        quan = item.getTexture();
        nhanVat.setIdQuan(item.getId());
        nhanVat.setTenQuan(item.getTenItem());
        nhanVat.setMoTaQuan(item.getMoTa());
        nhanVat.setChisoQuan(item.getChiso());
        skhq = item.getSetkichhoat();
        quandangmac = item.getId();
        nhanVat.setSoSaoQuan(item.getSoSaoPhaLe());
        nhanVat.setSoCapQuan(item.getSoCap());
        nhanVat.setSoSaoCuongHoaQuan(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhQuan(item.getHanhtinh());
        nhanVat.setSucManhYeuCauQuan(item.getSucManhYeuCau());

        // 5. Load avatar nếu không cải trang
        if (!NhanVatXuLy.getDangMacCaiTrang()) {
            NhanVatCauHinh c2 = Doi_avt_ao_quan(nhanVat.getHanhtinh(), avatardangmac, aodangmac, item.getId());
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );
        }

        // 6. Tăng chỉ số quần mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacQuan(true);
        // 7. Kích hoạt lại set mới nếu đủ
        kichHoatSetHienTai();

        // 8. Đưa quần cũ vào hành trang
        danhSach.set(indexx, quanCu);
    }

    private void goQuan(boolean vut) {
        if (quan == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,1);
        // 1. Lưu thông tin quần cũ
        String idCu = nhanVat.getIdQuan();
        String tenCu = nhanVat.getTenQuan();
        String motacu = nhanVat.getMoTaQuan();
        int[] chisocu = nhanVat.getChisoQuan();
        String skhqCu = skhq;
        int sosaocu = nhanVat.getSoSaoQuan();
        int socapcu = nhanVat.getSoCapQuan();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaQuan();
        String hanhtinhcu = nhanVat.getHanhTinhQuan();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauQuan();
        LoaiItem loaiCu = LoaiItem.QUAN;
        Item quanCu = new Item(idCu, tenCu, loaiCu, quan, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhqCu, sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSet(skhqCu);
        duLieuNguoiChoi.dangMacQuan(false);
        // 3. Giảm chỉ số quần
        giamchiso(chisocu);

        // 4. Cập nhật lại trạng thái không mặc gì
        skhq = "mac_dinh";
        quan = null;
        quandangmac = "set_base";

        // 5. Cập nhật giao diện nếu không cải trang
        if (!NhanVatXuLy.getDangMacCaiTrang()) {
            NhanVatCauHinh c2 = Doi_avt_ao_quan(nhanVat.getHanhtinh(), avatardangmac, aodangmac, "set_base");
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );
        }

        // 6. Kiểm tra lại set còn lại có đủ không
        kichHoatSetHienTai();

        // 7. Trả quần cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(quanCu);
        }
    }

    private void macGangMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu găng cũ
        String idCu = nhanVat.getIdGang();
        String tenCu = nhanVat.getTenGang();
        String motacu = nhanVat.getMoTaGang();
        int[] chisocu = nhanVat.getChisoGang();
        String skhgCu = skhg;
        int sosaocu = nhanVat.getSoSaoGang();
        int socapcu = nhanVat.getSoCapGang();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGang();
        String hanhtinhcu = nhanVat.getHanhTinhGang();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGang();
        LoaiItem loaiCu = LoaiItem.GANG;
        Item gangCu = new Item(idCu, tenCu, loaiCu, gang, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhgCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ
        huyHieuUngSet(skhgCu);
        duLieuNguoiChoi.dangMacGang(false);
        // 3. Giảm chỉ số găng cũ
        giamchiso(chisocu);

        // 4. Gán găng mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,2);
        gang = item.getTexture();
        nhanVat.setIdGang(item.getId());
        nhanVat.setTenGang(item.getTenItem());
        nhanVat.setMoTaGang(item.getMoTa());
        nhanVat.setChisoGang(item.getChiso());
        skhg = item.getSetkichhoat();
        nhanVat.setSoSaoGang(item.getSoSaoPhaLe());
        nhanVat.setSoCapGang(item.getSoCap());
        nhanVat.setSoSaoCuongHoaGang(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhGang(item.getHanhtinh());
        nhanVat.setSucManhYeuCauGang(item.getSucManhYeuCau());

        // 5. Tăng chỉ số găng mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacGang(true);
        // 6. Kích hoạt lại set nếu đủ
        kichHoatSetHienTai();

        // 7. Đưa găng cũ vào hành trang
        danhSach.set(indexx, gangCu);
    }

    private void goGang(boolean vut) {
        if (gang == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,2);
        // 1. Lưu găng cũ
        String idCu = nhanVat.getIdGang();
        String tenCu = nhanVat.getTenGang();
        String motacu = nhanVat.getMoTaGang();
        int[] chisocu = nhanVat.getChisoGang();
        String skhgCu = skhg;
        int sosaocu = nhanVat.getSoSaoGang();
        int socapcu = nhanVat.getSoCapGang();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGang();
        String hanhtinhcu = nhanVat.getHanhTinhGang();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGang();
        LoaiItem loaiCu = LoaiItem.GANG;
        Item gangCu = new Item(idCu, tenCu, loaiCu, gang, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhgCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSet(skhgCu);
        duLieuNguoiChoi.dangMacGang(false);
        // 3. Giảm chỉ số găng
        giamchiso(chisocu);

        // 4. Cập nhật trạng thái không mặc
        gang = null;
        skhg = "mac_dinh";

        // 5. Kích hoạt lại set nếu vẫn còn đủ 4 món
        kichHoatSetHienTai();

        // 6. Trả găng cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(gangCu);
        }
    }

    private void macGiayMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu giày cũ
        String idCu = nhanVat.getIdGiay();
        String tenCu = nhanVat.getTenGiay();
        String motacu = nhanVat.getMoTaGiay();
        int[] chisocu = nhanVat.getChisoGiay();
        String skhjCu = skhj;
        int sosaocu = nhanVat.getSoSaoGiay();
        int socapcu = nhanVat.getSoCapGiay();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGiay();
        String hanhtinhcu = nhanVat.getHanhTinhGiay();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGiay();
        LoaiItem loaiCu = LoaiItem.GIAY;
        Item giayCu = new Item(idCu, tenCu, loaiCu, giay, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhjCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ
        huyHieuUngSet(skhjCu);
        duLieuNguoiChoi.dangMacGiay(false);
        // 3. Giảm chỉ số giày cũ
        giamchiso(chisocu);

        // 4. Gán giày mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,3);
        giay = item.getTexture();
        nhanVat.setIdGiay(item.getId());
        nhanVat.setTenGiay(item.getTenItem());
        nhanVat.setMoTaGiay(item.getMoTa());
        nhanVat.setChisoGiay(item.getChiso());
        skhj = item.getSetkichhoat();
        nhanVat.setSoSaoGiay(item.getSoSaoPhaLe());
        nhanVat.setSoCapGiay(item.getSoCap());
        nhanVat.setSoSaoCuongHoaGiay(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhGiay(item.getHanhtinh());
        nhanVat.setSucManhYeuCauGiay(item.getSucManhYeuCau());

        // 5. Tăng chỉ số giày mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacGiay(true);
        // 6. Kích hoạt lại set nếu đủ
        kichHoatSetHienTai();

        // 7. Thay giày cũ vào hành trang
        danhSach.set(indexx, giayCu);
    }


    private void goGiay(boolean vut) {
        if (giay == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,3);
        // 1. Lưu giày cũ
        String idCu = nhanVat.getIdGiay();
        String tenCu = nhanVat.getTenGiay();
        String motacu = nhanVat.getMoTaGiay();
        int[] chisocu = nhanVat.getChisoGiay();
        String skhjCu = skhj;
        int sosaocu = nhanVat.getSoSaoGiay();
        int socapcu = nhanVat.getSoCapGiay();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGiay();
        String hanhtinhcu = nhanVat.getHanhTinhGiay();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGiay();
        LoaiItem loaiCu = LoaiItem.GIAY;
        Item giayCu = new Item(idCu, tenCu, loaiCu, giay, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhjCu, sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSet(skhjCu);
        duLieuNguoiChoi.dangMacGiay(false);
        // 3. Giảm chỉ số giày
        giamchiso(chisocu);

        // 4. Cập nhật lại trạng thái
        giay = null;
        skhj = "mac_dinh";

        // 5. Kích hoạt lại set nếu vẫn còn đủ
        kichHoatSetHienTai();

        // 6. Trả giày cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(giayCu);
        }
    }
    private void macRadaMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu rada cũ
        String idCu = nhanVat.getIdRada();
        String tenCu = nhanVat.getTenRada();
        String motacu = nhanVat.getMoTaRada();
        int[] chisocu = nhanVat.getChisoRada();
        String skhradaCu = skhrada;
        int sosaocu = nhanVat.getSoSaoRada();
        int socapcu = nhanVat.getSoCapRada();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaRada();
        String hanhtinhcu = nhanVat.getHanhTinhRada();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauRada();
        LoaiItem loaiCu = LoaiItem.RADA;
        Item radaCu = new Item(idCu, tenCu, loaiCu, rada, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhradaCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ (nếu có)
        huyHieuUngSet(skhradaCu);
        duLieuNguoiChoi.dangMacRada(false);
        // 3. Giảm chỉ số rada cũ
        giamchiso(chisocu);

        // 4. Gán rada mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,4);
        rada = item.getTexture();
        nhanVat.setIdRada(item.getId());
        nhanVat.setTenRada(item.getTenItem());
        nhanVat.setMoTaRada(item.getMoTa());
        nhanVat.setChisoRada(item.getChiso());
        skhrada = item.getSetkichhoat();
        nhanVat.setSoSaoRada(item.getSoSaoPhaLe());
        nhanVat.setSoCapRada(item.getSoCap());
        nhanVat.setSoSaoCuongHoaRada(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhRada(item.getHanhtinh());
        nhanVat.setSucManhYeuCauRada(item.getSucManhYeuCau());

        // 5. Tăng chỉ số rada mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacRada(true);
        // 6. Kích hoạt lại hiệu ứng set nếu đủ
        kichHoatSetHienTai();

        // 7. Thay rada cũ vào hành trang
        danhSach.set(indexx, radaCu);
    }


    private void goRada(boolean vut) {
        if (rada == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,4);
        // 1. Lưu rada cũ
        String idCu = nhanVat.getIdRada();
        String tenCu = nhanVat.getTenRada();
        String motacu = nhanVat.getMoTaRada();
        int[] chisocu = nhanVat.getChisoRada();
        String skhradaCu = skhrada;
        int sosaocu = nhanVat.getSoSaoRada();
        int socapcu = nhanVat.getSoCapRada();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaRada();
        String hanhtinhcu = nhanVat.getHanhTinhRada();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauRada();
        LoaiItem loaiCu = LoaiItem.RADA;
        Item radaCu = new Item(idCu, tenCu, loaiCu, rada, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhradaCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ
        huyHieuUngSet(skhradaCu);
        duLieuNguoiChoi.dangMacRada(false);
        // 3. Giảm chỉ số rada
        giamchiso(chisocu);

        // 4. Cập nhật trạng thái không mặc
        rada = null;
        skhrada = "mac_dinh";

        // 5. Kích hoạt lại hiệu ứng set nếu còn đủ món
        kichHoatSetHienTai();

        // 6. Trả rada cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(radaCu);
        }
    }

    private void macCaiTrangMoi(Item item, int indexx, ArrayList<Item> danhSach, boolean caiTrangDangMac, boolean laCaiTrangMoi) {
        // 1. Lưu cải trang cũ
        String idCu = nhanVat.getIdCaiTrang();
        String tenCu = nhanVat.getTenCaiTrang();
        String motacu = nhanVat.getMoTaCaiTrang();
        int[] chisocu = nhanVat.getChisoCaiTrang();
        float hansudung = nhanVat.getHanSuDungCaiTrang();
        String hanhtinhcu = nhanVat.getHanhTinhCaiTrang();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauCaiTrang();
        LoaiItem loaiCu = caiTrangDangMac ? LoaiItem.CAITRANG : LoaiItem.AVATAR;

        Item caiTrangCu = new Item(idCu, tenCu, loaiCu, iconct, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,hansudung);
        giamchiso(chisocu);

        // 2. Gán cải trang mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,5);
        iconct = item.getTexture();
        nhanVat.setIdCaiTrang(item.getId());
        nhanVat.setTenCaiTrang(item.getTenItem());
        nhanVat.setMoTaCaiTrang(item.getMoTa());
        nhanVat.setChisoCaiTrang(item.getChiso());
        nhanVat.setHanSuDungCaiTrang(item.getHanSuDung());
        nhanVat.setHanhTinhCaiTrang(item.getHanhtinh());
        nhanVat.setSucManhYeuCauCaiTrang(item.getSucManhYeuCau());
        if (laCaiTrangMoi) {
            avatardangmac = nhanVat.getNhanvat() + "_base";
        } else {
            avatardangmac = item.getId(); // Đây là avatar, cần lưu ID để load lại đúng sau này
        }
        // 3. Load config cải trang mới
        NhanVatCauHinh c2 = laCaiTrangMoi
            ? Doicaitrang(item.getId())
            : Doi_avt_ao_quan(nhanVat.getHanhtinh(), item.getId(), aodangmac, quandangmac);
        nhanVat.fixCaiTrang(
            c2.dau_dung, c2.dau_chay,
            c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
            c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
            c2.than_bay, c2.chan_bay,
            c2.lechMap,
            c2.avt
        );

        texAvt = new Texture(nhanVat.doiavatar());
        tangchiso(item.getChiso());

        // 4. Thay vào hành trang
        danhSach.set(indexx, caiTrangCu);

    }

    private void goCaiTrang(boolean laCaiTrang,boolean vut) {
        if (iconct == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,5);
        avatardangmac = nhanVat.getNhanvat()+"_base";
        String idCu = nhanVat.getIdCaiTrang();
        String tenCu = nhanVat.getTenCaiTrang();
        String motacu = nhanVat.getMoTaCaiTrang();
        int[] chisocu = nhanVat.getChisoCaiTrang();
        float hansudung = nhanVat.getHanSuDungCaiTrang();
        String hanhtinhcu = nhanVat.getHanhTinhCaiTrang();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauCaiTrang();
        LoaiItem loai = laCaiTrang ? LoaiItem.CAITRANG : LoaiItem.AVATAR;

        Item caiTrangCu = new Item(idCu, tenCu, loai, iconct, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,hansudung);
        giamchiso(chisocu);
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(caiTrangCu);
        }

        NhanVatCauHinh c2 = Doi_avt_ao_quan(nhanVat.getHanhtinh(), nhanVat.getNhanvat() + "_base", aodangmac, quandangmac);
        nhanVat.fixCaiTrang(
            c2.dau_dung, c2.dau_chay,
            c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
            c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
            c2.than_bay, c2.chan_bay,
            c2.lechMap,
            c2.avt
        );
        texAvt = new Texture(nhanVat.doiavatar());
        iconct = null;
    }

    private void macGiapLuyenTapMoi(Item item, int indexx, ArrayList<Item> danhSach){
        String idCu = nhanVat.getIdGiapLuyenTap();
        String tenCu = nhanVat.getTenGiapLuyenTap();
        String motacu = nhanVat.getMoTaGiapLuyenTap();
        int[] chisocu = nhanVat.getChisoGiapLuyenTap();
        int sosaocu = nhanVat.getSoSaoGiapLuyenTap();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGlt();
        float hansudung = nhanVat.getHanSuDungGiapLuyenTap();
        String hanhtinhcu = nhanVat.getHanhTinhGiapLuyenTap();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGiapLuyenTap();
        LoaiItem loaiCu = LoaiItem.GIAPLUYENTAP;
        Item giapLuyenTapCu = new Item(idCu, tenCu, loaiCu, giaplt, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,sosaocu,sosaocuonghoacu,0,hansudung);
        duLieuNguoiChoi.giamHpPt(chisocu[6]);
        giaplt = item.getTexture();
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,6);
        nhanVat.setIdGiapLuyenTap(item.getId());
        nhanVat.setTenGiapLuyenTap(item.getTenItem());
        nhanVat.setMoTaGiapLuyenTap(item.getMoTa());
        nhanVat.setChisoGiapLuyenTap(item.getChiso());
        nhanVat.setSoSaoGiapLuyenTap(item.getSoSaoPhaLe());
        nhanVat.setSoSaoCuongHoaGlt(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanSuDungGiapLuyenTap(item.getHanSuDung());
        nhanVat.setHanhTinhGiapLuyenTap(item.getHanhtinh());
        nhanVat.setSucManhYeuCauGiapLuyenTap(item.getSucManhYeuCau());
        duLieuNguoiChoi.tangHpPt(item.getChiso()[6]);
        duLieuNguoiChoi.checkGiapLuyenTap(true,0);
        danhSach.set(indexx, giapLuyenTapCu);
        dangMacGiapLuyenTap = true;
    }


    private void goGiapLuyenTap(boolean vut) {
        if (giaplt == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,6);
        String idCu = nhanVat.getIdGiapLuyenTap();
        String tenCu = nhanVat.getTenGiapLuyenTap();
        String motacu = nhanVat.getMoTaGiapLuyenTap();
        int[] chisocu = nhanVat.getChisoGiapLuyenTap();
        int sosaocu = nhanVat.getSoSaoGiapLuyenTap();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGlt();
        float hansudung = nhanVat.getHanSuDungGiapLuyenTap();
        String hanhtinhcu = nhanVat.getHanhTinhGiapLuyenTap();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGiapLuyenTap();
        LoaiItem loaiCu = LoaiItem.GIAPLUYENTAP;
        Item giapLuyenTapCu = new Item(idCu, tenCu, loaiCu, giaplt, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,sosaocu,sosaocuonghoacu,0,hansudung);
        tangchiso(chisocu);
        chisovuathao = chisocu;
        duLieuNguoiChoi.checkGiapLuyenTap(false,chisocu[8]);
        duLieuNguoiChoi.giamHpPt(chisocu[6]);
        duLieuNguoiChoi.giamHpPt(chisocu[6]);
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(giapLuyenTapCu);
        }
        giaplt = null;
        dangMacGiapLuyenTap = false;
    }

    private void macVanBayMoi(Item item, int indexx, ArrayList<Item> danhSach){
        if (!vanBayDau) {
            String idCu = nhanVat.getIdVanBay();
            String tenCu = nhanVat.getTenVanBay();
            String motacu = nhanVat.getMoTaVanBay();
            int[] chisocu = nhanVat.getChisoVanBay();
            String hanhtinhcu = nhanVat.getHanhTinhVanBay();
            long sucmanhyeucaucu = nhanVat.getSucManhYeuCauVanBay();
            LoaiItem loaiCu = LoaiItem.VANBAY;
            Item vanBayCu = new Item(idCu, tenCu, loaiCu, vanbay, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,-1);

            tangchiso(chisocu);
            danhSach.set(indexx, vanBayCu);
        } else {
            String idCu = "candauvan";
            String tenCu = "Cân đẩu vân";
            String motacu = "Ván bay cân đẩu vân";
            int[] chisocu = new int[] {0,0,0,0,0,0,0,0,0,0,0,0};
            LoaiItem loaiCu = LoaiItem.VANBAY;
            Item vanBayCu = new Item(idCu, tenCu, loaiCu, vanbay, motacu, 1, chisocu,"traidat",0, null,0,0,0,-1);

            tangchiso(chisocu);
            danhSach.set(indexx, vanBayCu);
            vanBayDau = false;
        }
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,7);
        vanbay = item.getTexture();
        nhanVat.setIdVanBay(item.getId());
        nhanVat.setTenVanBay(item.getTenItem());
        nhanVat.setMoTaVanBay(item.getMoTa());
        nhanVat.setChisoVanBay(item.getChiso());
        nhanVat.setHanhTinhVanBay(item.getHanhtinh());
        nhanVat.setSucManhYeuCauVanBay(item.getSucManhYeuCau());
        nhanVat.doiVanBay(item.getId());
        giamchiso(item.getChiso());
    }
    private void tangchiso(int[] chiso){
        duLieuNguoiChoi.tangHp(chiso[0]); // hp thường
        duLieuNguoiChoi.tangKi(chiso[1]); // ki thường
        duLieuNguoiChoi.tangSucDanh(chiso[2]); // sức đánh thường
        duLieuNguoiChoi.tangChiMang(chiso[3]); // crit
        duLieuNguoiChoi.tangGiap(chiso[4]); // giáp
        duLieuNguoiChoi.tangSatThuongChiMang(chiso[5]); // st crit
        duLieuNguoiChoi.tangHpGoc(chiso[9],false);
        duLieuNguoiChoi.tangKiGoc(chiso[10],false);
        duLieuNguoiChoi.tangSucDanhGoc(chiso[11],false);
        duLieuNguoiChoi.tangHpPt(chiso[6]); // HP %
        duLieuNguoiChoi.tangKiPt(chiso[7]); // KI %
        duLieuNguoiChoi.tangSucDanhPt(chiso[8]); // Sức đánh %
        duLieuNguoiChoi.tangGiamSatThuongNhanVat(chiso[12]);
    }

    private void giamchiso(int[] chiso){
        duLieuNguoiChoi.giamHp(chiso[0]);
        duLieuNguoiChoi.giamKi(chiso[1]);
        duLieuNguoiChoi.giamSucDanh(chiso[2]);
        duLieuNguoiChoi.giamChiMang(chiso[3]);
        duLieuNguoiChoi.giamGiap(chiso[4]);
        duLieuNguoiChoi.giamSatThuongChiMang(chiso[5]);
        duLieuNguoiChoi.giamHpPt(chiso[6]);
        duLieuNguoiChoi.giamKiPt(chiso[7]);
        duLieuNguoiChoi.giamSucDanhPt(chiso[8]);
        duLieuNguoiChoi.giamHpGoc(chiso[9]);
        duLieuNguoiChoi.giamKiGoc(chiso[10]);
        duLieuNguoiChoi.giamSucDanhGoc(chiso[11]);
        duLieuNguoiChoi.giamGiamSatThuongNhanVat(chiso[12]);
    }
    private void huyHieuUngSet(String setDangMac) {
        if ("Nappa".equals(setDangMac) && vuaMoNappa) {
            duLieuNguoiChoi.setNappa(false);
            duLieuNguoiChoi.giamHpPt(80); // giảm đúng 80%
            vuaMoNappa = false;
        }
    }
    private void kichHoatSetHienTai() {
        boolean fullSetNappa = "Nappa".equals(skha)
            && "Nappa".equals(skhq)
            && "Nappa".equals(skhg)
            && "Nappa".equals(skhj)
            && "Nappa".equals(skhrada);

        if (fullSetNappa && !vuaMoNappa) {
            duLieuNguoiChoi.setNappa(true);
            duLieuNguoiChoi.tangHpPt(80); // tăng đúng 80%
            vuaMoNappa = true;
        }
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
    }
}
