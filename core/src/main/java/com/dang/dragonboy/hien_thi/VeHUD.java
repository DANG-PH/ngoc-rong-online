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


public class VeHUD {

    private DuLieuNguoiChoi duLieuNguoiChoi;

    private Texture ochat, ochatclick;
    private Texture thanhhp;
    private Texture odauthan, odauthanclick;
    private Texture oskill, oskillclick;
    private Texture nutpopup;

    private BitmapFont font,fontChucnang,fontDauThan,fontNhiemVu,fontNhiemVu1,fontNhiemVuChuaLam,fontMotaNhiemVu,fontvangngoc,fontsm;
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

    public void setDuLieuNguoiChoi(DuLieuNguoiChoi data) {
        this.duLieuNguoiChoi = data; // truyền data vào để xử lí hud
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
        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ";
        param.size = 18;
        font = generator.generateFont(param);
        generator.dispose();
        // Font có viền đen dành riêng cho dòng chữ "Đậu thần cấp ..."
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchinh.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param2.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ";
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ";
        param3.size = 14;
        param3.color = new Color(94 / 255f, 86 / 255f, 74 / 255f, 1f);
        fontChucnang = generator3.generateFont(param3);
        param3.size = 16;
        fontNhiemVu = generator3.generateFont(param3);
        param3.size = 15;
        param3.color = new Color(0f / 255f, 123f / 255f, 255f / 255f, 1f);
        fontNhiemVu1 = generator3.generateFont(param3);
        param3.color = new Color(94 / 255f, 86 / 255f, 74 / 255f, 1f);
        fontNhiemVuChuaLam = generator3.generateFont(param3);
        param3.color = new Color(0f / 255f, 85f / 255f, 38f / 255f, 1f);
        param3.size = 15;
        fontMotaNhiemVu = generator3.generateFont(param3);
        param3.color = new Color(1,1,0,1);
        fontvangngoc = generator3.generateFont(param3);
        param3.size = 15;
        fontsm = generator3.generateFont(param3);
        generator3.dispose();
    }

    public void render(SpriteBatch batch) {
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
        shapeRenderer.rect(screenWidth - 75- 10 + 10, 10 + 10, 53 , 53);
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
            if (skillIcons != null && skillIcons[i] != null) {
                batch.draw(skillIcons[i].icon, x + 5, skillY + 5, oskillW - 10, oskillH - 10);
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
        font.draw(batch, layout, screenWidth - 75- 10 + 33, 10 + 43);
    }

    public void setSkillIcons(SkillIcon[] skillIcons) {
        this.skillIcons = skillIcons;
    }
    public void clickOChat() {
        thoiGianClickOChat = 0.2f;
    }
    public void clickODauThan() {
        thoiGianClickODauThan = 0.2f;
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
            } else if (x > 350 && x <= 1020) {
                tatPopupNhanVat();
            }
            // cac nut chuc nang
            for (int i = 0; i < 5; i++) {
                if (x >= 2+i*68+3 && x <= 2+i*68+3 + 68 && y >= 450 && y <= 450 + 52){
                    chucNangDangChon=i;
                }
            }
        }
        // === VÙNG SKILL (nếu muốn bắt click skill sau) ===
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
            for (int i = 0; i < 8; i++){
                batch.draw(hanh_trang_dang_mac,3,395-i*49,344,50);
            }
            for (int i = 0; i < 12; i++){
                batch.draw(hanh_trang,3,52-i*49,344,50);
            }
        }
        if (chucNangDangChon == 2){
            layout.setText(fontNhiemVu,"Kỹ năng đang phát triển!");
            fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);
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
    private String formatVangNgoc(long so) {
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
