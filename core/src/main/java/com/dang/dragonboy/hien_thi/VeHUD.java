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
import com.dang.dragonboy.du_lieu.ThemItemTest;
import java.text.DecimalFormat;
import com.badlogic.gdx.graphics.GL20;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VeHUD {
    private HUDClickHandler clickHandler;
    private HUDPopupRenderer popupRenderer;
    private DuLieuNguoiChoi duLieuNguoiChoi;

    public Texture saoden, saoxanh;
    public Texture ochat, ochatclick;
    public Texture thanhhp;
    public Texture odauthan, odauthanclick;
    public Texture oskill, oskillclick;
    public Texture nutpopup;

    public BitmapFont font,fontChucnang,fontDauThan,fontNhiemVu,fontNhiemVu1,fontNhiemVuChuaLam,fontMotaNhiemVu,fontvangngoc,fontsm,fontSkilldaco,fontSkillchuaco,fontMotaSkill,fontCapSKill,fontMotaNoiTai,fontTiemNang ,fontTenSkill,fontMotaNganSkill,fontMotaNganSkill1,fontSkillchuaco1,fontMotaHanhTrang ;
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
    public Texture vang,ngoc;
    public Texture thanhtheluc;

    public Texture hanh_trang,hanh_trang_click,hanh_trang_dang_mac,hanh_trang_dang_mac_click;

    public float scrollY = 0f;
    public float maxScroll = 0f;
    private final float scrollSpeed = 30f; // số pixel cuộn mỗi lần
    public int hangTrangDangChon = -1;

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
    public boolean vanBayDau = true;
    private boolean lanDau = true;
    private int[] chisovuathao;
    private boolean dangMacGiapLuyenTap = false;
    public float timeMacGiapLuyenTap;
    private boolean daCongChiMang = false;

    public float nuthanhtrangchon = -1;
    public float nutClickTimer3 = 0;

    public void setDuLieuNguoiChoi(DuLieuNguoiChoi data) {
        this.duLieuNguoiChoi = data;
        duLieuNguoiChoi.setNhanVat(nhanVat);
        // Gọi thêm item từ file ngoài
        ThemItemTest.themItemTest(duLieuNguoiChoi, nhanVat);
        clickHandler = new HUDClickHandler(this, duLieuNguoiChoi, nhanVat);
        popupRenderer= new HUDPopupRenderer(this, layout,duLieuNguoiChoi);
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ";
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
                            case 6 : goGiapLuyenTap(true);break;
                            case 0 : goAo(true);break;
                            case 1 : goQuan(true);break;
                            case 2 : goGang(true);break;
                            case 3 : goGiay(true);break;
                            case 4 : goRada(true);break;
                            case 5 : goCaiTrang(NhanVatXuLy.getDangMacCaiTrang(),true);break;
                        }
                    }
                    DangHienPopupThongTin1 = false;
                    TimeChoHienPopup = 0;
                } else if (nuthanhtrangchon == 3) {
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
        Map<String, String> setkichhoat = new HashMap<>();
        setkichhoat.put("Nappa", "(5 món +80% HP)");
        setkichhoat.put("Songoku", "(5 món +100% Kamejoko)");
        PopupHanhTrangH = 0;
        if (itemm!=null) {
            if ("giapluyentap".equals(itemDangChon)) {
                layout.setText(fontTenSkill, itemm.getTenItem());
                PopupHanhTrangH += layout.height + 15;
                layout.setText(fontSkillchuaco, "Hiệu lực trong " + (int) (timeMacGiapLuyenTap / 60f) + " phút");
                PopupHanhTrangH += layout.height + 15;
                for (int i = 6; i <= 12; i++) {
                    if (itemm.getChiso()[i] > 0) {
                        layout.setText(fontSkillchuaco, chisoduoccong[i] + "+" + itemm.getChiso()[i] + "%");
                        PopupHanhTrangH += layout.height + 15;
                    }
                }
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + itemm.getSucManhYeuCau());
                PopupHanhTrangH += layout.height + 10;
                layout.setText(font, "____________________________________");
                PopupHanhTrangH += layout.height + 15;
                layout.setText(
                    fontMotaHanhTrang,
                    itemm.getMoTa(),
                    fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupHanhTrangH += layout.height + 14;
                if (itemm.getSoSaoPhaLe() > 0) {
                    PopupHanhTrangH += 20;
                }
            }
            if ("ao".equals(itemDangChon) || "quan".equals(itemDangChon) || "giay".equals(itemDangChon) || "gang".equals(itemDangChon) || "rada".equals(itemDangChon)) {
                layout.setText(fontTenSkill, itemm.getTenItem());
                PopupHanhTrangH += layout.height + 15;
                for (int i = 9; i < 12; i++ ){
                    if (itemm.getChiso()[i] > 0) {
                        layout.setText(fontSkillchuaco,chisoduoccong[i] + "+"+itemm.getChiso()[i]);
                        PopupHanhTrangH += layout.height + 15;
                    }
                    if (itemm.getChiso()[i-6] > 0){
                        layout.setText(fontSkillchuaco,chisoduoccong[i-6] + "+"+itemm.getChiso()[i-6]);
                        PopupHanhTrangH += layout.height + 15;
                    }
                }

                for (int i = 6; i < 9; i++) {
                    if (itemm.getChiso()[i] > 0) {
                        layout.setText(fontSkillchuaco, chisoduoccong[i] + "+" + itemm.getChiso()[i] + "%");
                        PopupHanhTrangH += layout.height + 15;
                    }
                }
                if (itemm.getSetkichhoat() != null){
                    boolean fullSetNappa = "Nappa".equals(skha)
                        && "Nappa".equals(skhq)
                        && "Nappa".equals(skhg)
                        && "Nappa".equals(skhj)
                        && "Nappa".equals(skhrada);
                    if (fullSetNappa) {
                        layout.setText(fontSkillchuaco, "Set " + itemm.getSetkichhoat());
                        PopupHanhTrangH += layout.height + 12;
                        layout.setText(fontSkillchuaco, setkichhoat.get(itemm.getSetkichhoat()));
                        PopupHanhTrangH += layout.height + 12;
                        layout.setText(fontSkillchuaco, "Không thể giao dịch");
                        PopupHanhTrangH += layout.height + 12;
                    } else {
                        layout.setText(fontTenSkill, "Set " + itemm.getSetkichhoat());
                        PopupHanhTrangH += layout.height + 12;
                        layout.setText(fontTenSkill, setkichhoat.get(itemm.getSetkichhoat()));
                        PopupHanhTrangH += layout.height + 12;
                        layout.setText(fontTenSkill, "Không thể giao dịch");
                        PopupHanhTrangH += layout.height + 12;
                    }
                }
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + itemm.getSucManhYeuCau());
                PopupHanhTrangH += layout.height + 10;
                layout.setText(font, "____________________________________");
                PopupHanhTrangH += layout.height + 15;
                layout.setText(
                    fontMotaHanhTrang,
                    itemm.getMoTa(),
                    fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupHanhTrangH += layout.height + 42;
                if (itemm.getSoSaoPhaLe() > 0) {
                    PopupHanhTrangH += 20;
                }
            }
            if ("caitrang".equals(itemDangChon) || "avatar".equals(itemDangChon)) {
                layout.setText(fontTenSkill, itemm.getTenItem());
                PopupHanhTrangH += layout.height + 15;
                for (int i = 6; i <= 12; i++) {
                    if (itemm.getChiso()[i] > 0) {
                        layout.setText(fontSkillchuaco, chisoduoccong[i] + "+" + itemm.getChiso()[i] + "%");
                        PopupHanhTrangH += layout.height + 15;
                    }
                }
                layout.setText(fontTenSkill, "Không thể giao dịch");
                PopupHanhTrangH += layout.height + 12;
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + itemm.getSucManhYeuCau());
                PopupHanhTrangH += layout.height + 10;
                layout.setText(font, "____________________________________");
                PopupHanhTrangH += layout.height + 15;
                layout.setText(
                    fontMotaHanhTrang,
                    itemm.getMoTa(),
                    fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupHanhTrangH += layout.height + 28;
            }
            if ("vanbay".equals(itemDangChon)) {
                layout.setText(fontTenSkill, itemm.getTenItem());
                PopupHanhTrangH += layout.height + 15;
                layout.setText(fontTenSkill, itemm.getMoTa());
                PopupHanhTrangH += layout.height + 15;
                layout.setText(fontTenSkill, "Không thể giao dịch");
                PopupHanhTrangH += layout.height + 12;
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + itemm.getSucManhYeuCau());
                PopupHanhTrangH += layout.height + 10;
                layout.setText(font, "____________________________________");
                PopupHanhTrangH += layout.height + 15;
                layout.setText(
                    fontMotaHanhTrang,
                    itemm.getMoTa(),
                    fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupHanhTrangH += layout.height + 28;
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
            DecimalFormat dinhDang = new DecimalFormat("#,###");
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
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(itemm.getSucManhYeuCau()));
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
            } else if ("ao".equals(itemDangChon) || "quan".equals(itemDangChon) || "giay".equals(itemDangChon) || "gang".equals(itemDangChon) || "rada".equals(itemDangChon)) {
                float offsetY = 10;
                if (itemm.getTexture() != null) {
                    batch.draw(itemm.getTexture(), PopupHanhTrangX + 15, PopupHanhTrangY + PopupHanhTrangH - itemm.getTexture().getHeight() * 0.5f - offsetY, itemm.getTexture().getWidth() * 0.5f, itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                if (itemm.getSoCap() > 0){
                    fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    layout.setText(fontTenSkill, itemm.getTenItem());
                    fontTenSkill.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 50, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                    layout.setText(fontTenSkill, "[+"+itemm.getSoCap()+"]");
                    fontTenSkill.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                    offsetY += layout.height + 12;
                } else {
                    fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    layout.setText(fontTenSkill, itemm.getTenItem());
                    fontTenSkill.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                    offsetY += layout.height + 12;
                }
                for (int i = 9; i < 12; i++ ){
                    if (itemm.getChiso()[i] > 0) {
                        layout.setText(fontSkillchuaco,chisoduoccong[i] + "+"+itemm.getChiso()[i]);
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                    if (itemm.getChiso()[i-6] > 0){
                        layout.setText(fontSkillchuaco,chisoduoccong[i-6] + "+"+itemm.getChiso()[i-6]);
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                if (itemm.getSetkichhoat() != null){
                    boolean fullSetNappa = "Nappa".equals(skha)
                        && "Nappa".equals(skhq)
                        && "Nappa".equals(skhg)
                        && "Nappa".equals(skhj)
                        && "Nappa".equals(skhrada);
                    if (fullSetNappa) {
                        layout.setText(fontSkillchuaco, "Set " + itemm.getSetkichhoat());
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                        layout.setText(fontSkillchuaco, setkichhoat.get(itemm.getSetkichhoat()));
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                        layout.setText(fontSkillchuaco, "Không thể giao dịch");
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    } else {
                        fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                        layout.setText(fontSkillchuaco, "Set " + itemm.getSetkichhoat());
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                        layout.setText(fontTenSkill, setkichhoat.get(itemm.getSetkichhoat()));
                        fontTenSkill.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                        layout.setText(fontSkillchuaco, "Không thể giao dịch");
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                for (int i = 6; i < 9; i++) {
                    if (itemm.getChiso()[i] > 0) {
                        layout.setText(fontSkillchuaco, chisoduoccong[i] + "+" + itemm.getChiso()[i]+"%");
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(itemm.getSucManhYeuCau()));
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
                offsetY += layout.height + 32;

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
            } else if ("caitrang".equals(itemDangChon) || "avatar".equals(itemDangChon)) {
                float offsetY = 10;
                if (itemm.getTexture() != null) {
                    if (itemm.getTexture().getHeight()*0.5f < 60) {
                        batch.draw(itemm.getTexture(), PopupHanhTrangX + 15, PopupHanhTrangY + PopupHanhTrangH - itemm.getTexture().getHeight() * 0.5f - offsetY, itemm.getTexture().getWidth() * 0.5f, itemm.getTexture().getHeight() * 0.5f);
                    } else {
                        batch.draw(itemm.getTexture(), PopupHanhTrangX + 15, PopupHanhTrangY + PopupHanhTrangH - itemm.getTexture().getHeight() * 0.38f - offsetY, itemm.getTexture().getWidth() * 0.38f, itemm.getTexture().getHeight() * 0.38f);
                    }
                    offsetY += 10;
                }
                fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(fontTenSkill, itemm.getTenItem());
                fontTenSkill.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;

                for (int i = 6; i <= 12; i++) {
                    if (itemm.getChiso()[i] > 0) {
                        layout.setText(fontSkillchuaco, chisoduoccong[i] + "+" + itemm.getChiso()[i] + "%");
                        fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                layout.setText(fontSkillchuaco, "Không thể giao dịch");
                fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(itemm.getSucManhYeuCau()));
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
            } else if ("vanbay".equals(itemDangChon)) {
                float offsetY = 10;
                if (itemm.getTexture() != null) {
                    batch.draw(itemm.getTexture(), PopupHanhTrangX + 15, PopupHanhTrangY + PopupHanhTrangH - itemm.getTexture().getHeight() * 0.5f - offsetY, itemm.getTexture().getWidth() * 0.5f, itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(fontTenSkill, itemm.getTenItem());
                fontTenSkill.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;

                layout.setText(fontSkillchuaco, itemm.getMoTa());
                fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;
                layout.setText(fontSkillchuaco, "Không thể giao dịch");
                fontSkillchuaco.draw(batch, layout, PopupHanhTrangW + PopupHanhTrangX - layout.width - 15, PopupHanhTrangY + PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;
                layout.setText(fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(itemm.getSucManhYeuCau()));
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
            }
        }
        if (itemm!=null) {
            if (oHanhTrangDangChon != 7) {
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
            } else {
                float nutX = 1 ;
                float nutY = y - 115;
                if (nuthanhtrangchon == 3) {
                    Texture nutVe = nutClickTimer3 > 0 ? nutvuongclick : nutvuong;
                    batch.draw(nutVe, nutX, nutY, 114, 114);
                } else {
                    batch.draw(nutvuong, nutX, nutY, 114, 114);
                }
                layout.setText(font, "Đóng");
                font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
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
            danhSach.set(indexx, vanBayCu);
        } else {
            danhSach.set(indexx, duLieuNguoiChoi.getHanhTrangDangMac().get(7));
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
