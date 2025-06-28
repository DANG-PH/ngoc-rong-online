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
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import java.text.DecimalFormat;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.Gdx;

public class VeHUD {

    private DuLieuNguoiChoi duLieuNguoiChoi;

    private Texture ochat, ochatclick;
    private Texture thanhhp;
    private Texture odauthan, odauthanclick;
    private Texture oskill, oskillclick;
    private Texture nutpopup;

    private BitmapFont font,fontChucnang,fontDauThan,fontNhiemVu,fontNhiemVu1,fontNhiemVuChuaLam,fontMotaNhiemVu,fontvangngoc,fontsm,fontSkilldaco,fontSkillchuaco,fontMotaSkill,fontCapSKill,fontMotaNoiTai,fontTiemNang ,fontTenSkill,fontMotaNganSkill,fontMotaNganSkill1,fontSkillchuaco1 ;
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
    float TimeChoHienPopup = 0;
    private boolean vuaMoPopupThongTin = false;
    float PopupThongTinX = 0;
    float PopupThongTinY = 0;
    float PopupThongTinW = 0;
    float PopupThongTinH = 0;
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

    private float dauThanRenderH= 53f;

    public void setDuLieuNguoiChoi(DuLieuNguoiChoi data) {
        this.duLieuNguoiChoi = data; // truyền data vào để xử lí hud
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ";
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ";
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ";
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
        param3.color = new Color(1,1,1,1);
        param3.size = 14;
        fontTiemNang = generator3.generateFont(param3);
        param3.color = new Color(1,1,0,1);
        param3.size = 15;
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
                    case 0 -> duLieuNguoiChoi.tangHpGoc(giaTriTangTamThoi);
                    case 1 -> duLieuNguoiChoi.tangKiGoc(giaTriTangTamThoi);
                    case 2 -> duLieuNguoiChoi.tangSucDanhGoc(giaTriTangTamThoi);
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
        if (TimeChoHienPopupGanSkill > 0 ){
            TimeChoHienPopupGanSkill -= Gdx.graphics.getDeltaTime();
            if (TimeChoHienPopupGanSkill <= 0) {
                HienPopUpGanSkill = true;
            }
        }
        if (DangHienPopupThongTin){
            TimeChoHienPopup-=Gdx.graphics.getDeltaTime();
        }
        if (chucNangDangChon != 2){
            DangHienPopupThongTin = false;
        }
        if (dauThanRenderH < 53f){
            dauThanRenderH+=10.6f*Gdx.graphics.getDeltaTime();
            dauThanRenderH = Math.min(dauThanRenderH, 53f);
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
            } else if (x > 350 && x <= 1020 && !DangHienPopupThongTin && !HienPopUpGanSkill) {
                tatPopupNhanVat();
                hangTrangDangChon = -1;
                oChiSoDangChon = -1;
            }
            // cac nut chuc nang
            for (int i = 0; i < 5; i++) {
                if (x >= 2+i*68+3 && x <= 2+i*68+3 + 68 && y >= 450 && y <= 450 + 52){
                    chucNangDangChon=i;
                }
            }
        }
        if (dangHienPopup && chucNangDangChon == 1) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 8 + 12;

            // Kiểm tra có click vào vùng hành trang không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {

                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = scrollY + (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    hangTrangDangChon = index;
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
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;

            int soTrangBi = 8;
            int soKhac = 12;
            int tongSoTrangBi = soTrangBi + soKhac;

            float totalHeight = tongSoTrangBi * KhoangCachItem;
            maxScroll = Math.max(0, totalHeight - viewHeight);

            batch.flush();
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
            Gdx.gl.glScissor(0, (int)viewY, 350, (int)viewHeight);

            // Vị trí bắt đầu vẽ từ trên xuống
            float startY = viewY + viewHeight - KhoangCachItem + scrollY;

            for (int i = 0; i < soTrangBi; i++) {
                float y = startY - i * KhoangCachItem;
                Texture tex = (hangTrangDangChon == i) ? hanh_trang_dang_mac_click : hanh_trang_dang_mac;
                batch.draw(tex, 3, y, 344, 50);
            }

            for (int i = 0; i < soKhac; i++) {
                float y = startY - (soTrangBi + i) * KhoangCachItem;
                Texture tex = (hangTrangDangChon == soTrangBi + i) ? hanh_trang_click : hanh_trang;
                batch.draw(tex, 3, y, 344, 50);
            }

            batch.flush();
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
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
