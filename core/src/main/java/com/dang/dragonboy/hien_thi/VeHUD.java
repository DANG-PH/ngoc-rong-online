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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DeTu;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
import com.dang.dragonboy.nhan_vat.DeTuXuLy;
import com.dang.dragonboy.nhan_vat.DeTuCauHinh;
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
import java.util.LinkedList;
import com.dang.dragonboy.he_thong.TrangThaiChu;

public class VeHUD {
    private HUDClickHandler clickHandler;
    private HUDPopupRenderer popupRenderer;
    private HUDXulyitem xulyitem;
    private HUDPopupThongTin popupThongTin;
    private HUDPopupHanhTrang popupHanhTrang;

    private DuLieuNguoiChoi duLieuNguoiChoi;
    private QuanLyCamera camManager;

    public Texture thanhhpnv, thanhkinv;
    public TextureRegion thanhhpnv1, thanhkinv1;
    public Texture saoden, saoxanh;
    public Texture ochat, ochatclick;
    public Texture thanhhp;
    public Texture odauthan, odauthanclick;
    public Texture oskill, oskillclick;
    public Texture nutpopup;

    public BitmapFont font, fontChucnang, fontDauThan, fontNhiemVu, fontNhiemVu1, fontNhiemVuChuaLam, fontMotaNhiemVu, fontvangngoc, fontsm, fontSkilldaco, fontSkillchuaco, fontMotaSkill, fontCapSKill, fontMotaNoiTai, fontTiemNang, fontTenSkill, fontchat, fontMotaNganSkill, fontMotaNganSkill1, fontSkillchuaco1, fontMotaHanhTrang, fontMotaHanhTrang1, fontText;
    private GlyphLayout layout;

    public SkillIcon[] skillIcons;

    private float thoiGianClickOChat = 0;
    private float thoiGianClickODauThan = 0;
    private int skillDangChon = -1; // -1: chưa chọn

    private ShapeRenderer shapeRenderer;

    public Texture popupNhanVat;
    public Texture nutX;
    public boolean dangHienPopup = false;
    //    public boolean vuaMoPopup = false;
    public boolean vuaTatPopup = false;
    private NhanVat nhanVat;
    public Texture texAvt = null;
    public Texture nutchucnang, nutchucnangclick;
    public int chucNangDangChon = 0;
    public int chucNangDeTuDangChon = 0;
    public Texture vang, ngoc;
    public Texture thanhtheluc, thanhtheluc2;
    public TextureRegion thanhtheluc1, thanhtheluc3;

    public Texture hanh_trang, hanh_trang_click, hanh_trang_dang_mac, hanh_trang_dang_mac_click;

    public float scrollY = 0f;
    public float scrollYDeTu = 0f;
    public float maxScroll = 0f;
    public float maxScrollDeTu = 0f;
    private final float scrollSpeed = 30f; // số pixel cuộn mỗi lần
    public int hangTrangDangChon = -1;
    public int hangTrangDeTuDangChon = -1;

    public Texture o_noi_tai, o_noi_tai_click, o_chi_so_co_ban, o_chi_so_co_ban_click;
    public int oChiSoDangChon = -1;
    public Texture[] iconchisocoban = new Texture[5];
    public Texture iconnoitai;

    public Texture nutvuong, nutvuongclick;
    public boolean DangHienPopupThongTin = false;
    public boolean DangHienPopupThongTin1 = false;
    public boolean DangHienPopupThongTin2 = false;
    public float TimeChoHienPopup = 0;
    public float TimeChoHienPopup1 = 0;
    public boolean vuaMoPopupThongTin = false;
    public float PopupThongTinX = 0;
    public float PopupThongTinY = 0;
    public float PopupThongTinW = 0;
    public float PopupThongTinH = 0;
    public float PopupHanhTrangX = 0;
    public float PopupHanhTrangY = 0;
    public float PopupHanhTrangXdetu = 0;
    public float PopupHanhTrangYdetu = 0;
    String itemDangChon;
    Item itemm = null;
    public float PopupHanhTrangW = 0;
    public float PopupHanhTrangH = 0;
    public float PopupHanhTrangWdetu = 0;
    public float PopupHanhTrangHdetu = 0;
    float nutClickTimer = 0;
    float nutClickTimer1 = 0;
    int oChiSoDangChonTamThoi = -1;
    int giaTriTangTamThoi = 0;
    long chiPhiTamThoi = 0;
    public int nutduocchon = -1;

    public boolean HienPopUpGanSkill = false;
    float TimeChoHienPopupGanSkill = 0;
    private Texture[] oSkills;
    float nutClickTimer2 = 0;

    public Texture ao, quan, gang, giay, rada, iconct, giaplt, vanbay;
    public Texture aoDeTu, quanDeTu, gangDeTu, giayDeTu, radaDeTu, iconctDeTu;

    private float dauThanRenderH = 53f;
    public String avatardangmac = "Goku_base";
    public String aodangmac = "set_base";
    public String quandangmac = "set_base";
    public String aodetudangmac = "set_base";
    public String quandetudangmac = "set_base";
    public String skha = "thuong";
    public String skhq = "thuong";
    public String skhg = "thuong";
    public String skhj = "thuong";
    public String skhrada = "thuong";
    public String skha_detu = "thuong";
    public String skhq_detu = "thuong";
    public String skhg_detu = "thuong";
    public String skhj_detu = "thuong";
    public String skhrada_detu = "thuong";
    public boolean vuaMoNappa = false;
    public boolean vuaMoDeTuNappa = false;
    public boolean dangMacGiapLuyenTap = false;
    public Item itemGiapLuyenTapVuaCoi = null;
    private boolean daCongChiMang = false;

    public float nuthanhtrangchon = -1;
    public float nutClickTimer3 = 0;

    public Texture anhThongBao, nutdn, nutclick;
    public float isThongBaoOKPressed = 0;
    public boolean dangHienThongBao = false;

    public Texture khungchat, duoichat;
    public boolean dangHienKhungChat = false;

    public String tinNhanChat = "";
    public boolean dangHienTinNhanChat = false;

    public float timeHienTinNhan = 0;

    public String tinNhanPet = "";
    public boolean dangHienTinNhanPet = false;
    public float timeHienTinNhanPet = 0;
    private boolean daRoiPetXuong = false;
    private boolean daDuocBayLen = false;

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

    public boolean dangHienChonMiniGame = false;
    public boolean dangHienMiniGame = false;
    public boolean dangHienMiniGameHuongDanThem = false;
    public boolean dangHienMiniGameThamGia = false;
    public float timeMiniGame = 60f;
    public int ketQuaGiaiTruoc;
    public int soNguoiChoiChon;
    public int soNgocCuoc;
    public int soNgocDuocNhanGanNhat;
    public String soNgocNguoiChoiNhap = "";
    public boolean dangHienMiniGameChanLe = false;
    public boolean dangHienMiniGameHuongDanThemChanLe = false;
    public boolean dangHienMiniGameThamGiaChanLe = false;
    public float timeMiniGameChanLe = 30f;
    public int ketQuaGiaiTruocChanLe;
    public String NguoiChoiChonChanLe = "";
    public int soVangCuocChanLe;
    public int soVangDuocNhanGanNhatChanLe;
    public String soVangNguoiChoiNhapChanLe = "";

    public boolean dangHopThe = false;
    public float timeChoHopThe = 0;
    public Texture nenflash;
    public boolean veNenFlash;
    public Texture dautrai, dauphai, thantrai, thanphai, chantrai, chanphai;
    public Texture dau1, than1, chan1, dau2, than2, chan2, dau3, than3, chan3, dau4, than4, chan4, dau5, than5, chan5, dau6, than6, chan6, than7, chan7;
    public Texture dau1p, than1p, chan1p, dau2p, than2p, chan2p, dau3p, than3p, chan3p, dau4p, than4p, chan4p, dau5p, than5p, chan5p, dau6p, than6p, chan6p, than7p, chan7p;
    public Texture[] chan = new Texture[6];
    public Texture[] than = new Texture[6];
    public Texture[] dau = new Texture[6];
    public Texture[] chanp = new Texture[6];
    public Texture[] thanp = new Texture[6];
    public Texture[] daup = new Texture[6];
    // Offset riêng cho từng bước
    public float[] offsetX = {80f, 72f, 66f, 60f, 54f, 50f};
    public float[] offsetThanX = {7f, 7f, 6f, -3f, -7f, 7.5f};
    public float[] offsetThanY = {5f, 15f, 7f, 11f, 5f, 9f};
    public float[] offsetDauX = {2f, 4f, 0f, 3f, 1f, -1f};
    public float[] offsetDauY = {7f, 21f, 21f, 18f, 8f, 15f};
    public boolean dangHopTheThuong = false;
    public float timeHopTheTHuong;
    private Texture dauGotenks;
    public float delayHopTheThuong, delayHopTheBongTai;

    public float timeGlow = 0;
    public float clickX;
    public float clickY;
    public boolean vuaClickTatPopup = false;
    public boolean vuaClickMoPopup = false;

    public boolean keoHanhTrang = false;
    public boolean vuaKeoHanhTrang = false;
    public boolean keoHanhTrangDeTu = false;
    public boolean vuaKeoHanhTrangDeTu = false;

    public String trangthaide;
    public boolean renderDeTu = false;
    private float timeDoiDauThan = 0f;
    private float timeQuanTamSuPhu = 0f;

    LinkedList<TrangThaiChu> lichSuTrangThaiChu = new LinkedList<>();

    public Texture avtPetTheoHanhTinh;
    public Texture[] framesPet = new Texture[2];

    public boolean daRanDomChatDeTu = false;
    public String bongTaiDangDung = "";

    public String ngocRongUoc = "";
    public boolean dangHienDieuUocRongThan = false;
    public float timeHienRongThan = 0f;
    public Texture[] hieuUngRongThan = new Texture[21];

    public void setDuLieuNguoiChoi(DuLieuNguoiChoi data) {
        this.duLieuNguoiChoi = data;
        duLieuNguoiChoi.setNhanVat(nhanVat);
        nhanVat.setDuLieuNguoiChoi(duLieuNguoiChoi);
        // Gọi thêm item từ file ngoài
        ThemItemTest.themItemTest(duLieuNguoiChoi, nhanVat);
        clickHandler = new HUDClickHandler(this, duLieuNguoiChoi, nhanVat);
        popupRenderer = new HUDPopupRenderer(this, layout, duLieuNguoiChoi,nhanVat);
        xulyitem = new HUDXulyitem(this, layout, duLieuNguoiChoi, nhanVat);
        popupThongTin = new HUDPopupThongTin(this, layout, duLieuNguoiChoi, nhanVat);
        popupHanhTrang = new HUDPopupHanhTrang(this, layout, duLieuNguoiChoi, nhanVat);
        duLieuNguoiChoi.taoDeTu("Đệ tử");
        duLieuNguoiChoi.deTu.setVeHUD(this);
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
        o_chi_so_co_ban = new Texture("hud/giaodientrong/ochiso.png");
        o_chi_so_co_ban_click = new Texture("hud/giaodientrong/ochisoclick.png");
        o_noi_tai = new Texture("hud/giaodientrong/onoitai.png");
        o_noi_tai_click = new Texture("hud/giaodientrong/onoitaiclick.png");
        for (int i = 0; i < 5; i++) {
            iconchisocoban[i] = new Texture("kynang/iconkynang/chung/" + (i + 1) + ".png");
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
        nenflash = new Texture("hud/giaodientrong/nenflash.jpg");
        dautrai = new Texture("hieuung/hieuunggame/hop_the/dautrai.png");
        thantrai = new Texture("hieuung/hieuunggame/hop_the/thantrai.png");
        chantrai = new Texture("hieuung/hieuunggame/hop_the/chantrai.png");
        dauphai = new Texture("hieuung/hieuunggame/hop_the/dauphai.png");
        thanphai = new Texture("hieuung/hieuunggame/hop_the/thanphai.png");
        chanphai = new Texture("hieuung/hieuunggame/hop_the/chanphai.png");
        // Bên trái
        dau1 = dautrai;
        than1 = new Texture("hieuung/hieuunggame/hop_the/1_thantrai.png");
        chan1 = chantrai;
        dau2 = dautrai;
        than2 = new Texture("hieuung/hieuunggame/hop_the/2_thantrai.png");
        chan2 = new Texture("hieuung/hieuunggame/hop_the/2_chantrai.png");
        dau3 = dautrai;
        than3 = new Texture("hieuung/hieuunggame/hop_the/3_thantrai.png");
        chan3 = new Texture("hieuung/hieuunggame/hop_the/3_chantrai.png");
        dau4 = dauphai;
        than4 = new Texture("hieuung/hieuunggame/hop_the/2_thanphai.png");
        chan4 = new Texture("hieuung/hieuunggame/hop_the/4_chantrai.png");
        dau5 = dauphai;
        than5 = new Texture("hieuung/hieuunggame/hop_the/1_thanphai.png");
        chan5 = chanphai;
        dau6 = dau5;
        than6 = than1;
        chan6 = new Texture("hieuung/hieuunggame/hop_the/6_chantrai.png");
        than7 = new Texture("hieuung/hieuunggame/hop_the/7_thantrai.png");
        chan7 = new Texture("hieuung/hieuunggame/hop_the/7_chantrai.png");
        // Bên phải
        dau1p = dauphai;
        than1p = new Texture("hieuung/hieuunggame/hop_the/1_thanphai.png");
        chan1p = chanphai;
        dau2p = dauphai;
        than2p = new Texture("hieuung/hieuunggame/hop_the/2_thanphai.png");
        chan2p = new Texture("hieuung/hieuunggame/hop_the/2_chanphai.png");
        dau3p = dauphai;
        than3p = new Texture("hieuung/hieuunggame/hop_the/3_thanphai.png");
        chan3p = new Texture("hieuung/hieuunggame/hop_the/3_chanphai.png");
        dau4p = dautrai;
        than4p = than2;
        chan4p = new Texture("hieuung/hieuunggame/hop_the/4_chanphai.png");
        dau5p = dau1;
        than5p = than1;
        chan5p = chan1;
        dau6p = dau5p;
        than6p = than1p;
        chan6p = new Texture("hieuung/hieuunggame/hop_the/6_chanphai.png");
        than7p = new Texture("hieuung/hieuunggame/hop_the/7_thanphai.png");
        chan7p = new Texture("hieuung/hieuunggame/hop_the/7_chanphai.png");
        // Bên trái
        dau[0] = dau1;
        than[0] = than1;
        chan[0] = chan1;
        dau[1] = dau2;
        than[1] = than2;
        chan[1] = chan2;
        dau[2] = dau3;
        than[2] = than3;
        chan[2] = chan3;
        dau[3] = dau4;
        than[3] = than4;
        chan[3] = chan4;
        dau[4] = dau5;
        than[4] = than5;
        chan[4] = chan5;
        dau[5] = dau6;
        than[5] = than6;
        chan[5] = chan6;
        // Bên phải
        daup[0] = dau1p;
        thanp[0] = than1p;
        chanp[0] = chan1p;
        daup[1] = dau2p;
        thanp[1] = than2p;
        chanp[1] = chan2p;
        daup[2] = dau3p;
        thanp[2] = than3p;
        chanp[2] = chan3p;
        daup[3] = dau4p;
        thanp[3] = than4p;
        chanp[3] = chan4p;
        daup[4] = dau5p;
        thanp[4] = than5p;
        chanp[4] = chan5p;
        daup[5] = dau6p;
        thanp[5] = than6p;
        chanp[5] = chan6p;
        for (int i = 0; i < 21; i++) {
            hieuUngRongThan[i] = new Texture("hieuung/hieuunggame/rong_than/"+(i+1)+".png");
        }
        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ ễ ằ ừ — ẳ ữ ỗ ằ ễ ỗ ừ ẵ ê : ĩ";
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ ễ ằ ừ — ẳ ữ ỗ ằ ễ ỗ ừ ẵ ê : ĩ";
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ ễ ằ ừ — ẳ ữ ỗ ằ ễ ỗ ừ ẵ ê : ĩ";
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
        param3.color = new Color(1, 1, 0, 1);
        fontvangngoc = generator3.generateFont(param3);
        param3.color = new Color(0, 0, 0, 1);
        fontMotaHanhTrang = generator3.generateFont(param3);
        param3.color = new Color(0.933f, 0.502f, 0.510f, 1f);
        fontMotaHanhTrang1 = generator3.generateFont(param3);
        param3.color = new Color(1, 1, 1, 1);
        param3.size = 14;
        fontTiemNang = generator3.generateFont(param3);
        param3.color = new Color(1, 1, 0, 1);
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
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (duLieuNguoiChoi.coDeTu()) {
            // Ghi nhận trạng thái hiện tại của nhân vật
            TrangThaiChu trangThaiMoi = new TrangThaiChu(
                nhanVat.getX(),
                nhanVat.getY()
            );

            // Thêm vào danh sách
            lichSuTrangThaiChu.addFirst(trangThaiMoi);

            // Giữ lại tối đa 100 phần tử (tùy bạn)
            if (lichSuTrangThaiChu.size() > 100) {
                lichSuTrangThaiChu.removeLast();
            }

            // Điều kiện để render đệ tử
            if (!duLieuNguoiChoi.deTu.getTrangthai().equals("Về nhà") && !dangHopThe) {
                renderDeTu = true;
            } else {
                renderDeTu = duLieuNguoiChoi.deTu.getTrangthai().equals("Về nhà")
                    && duLieuNguoiChoi.deTu.getTimeHienChat() > 0
                    && !dangHopThe;
            }

            // Gọi cập nhật AI dùng lịch sử
            duLieuNguoiChoi.deTu.capNhatAI(
                deltaTime,
                lichSuTrangThaiChu,
                0.35f // Delay theo giây (ví dụ 1 giây)
            );
        }
        chonDieuUocRongThan(batch);
        batch.end();
        if (duLieuNguoiChoi.coDeTu()) {
            if (duLieuNguoiChoi.deTu.getTheLuc()<20 && timeDoiDauThan == 0 && !duLieuNguoiChoi.deTu.getTrangthai().equals("Về nhà")) {
                duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Sư phụ ơi con cần đậu thần",2f);
                timeDoiDauThan = 12f;
            }
            if (duLieuNguoiChoi.getKiHienTai()==0 && timeQuanTamSuPhu == 0 && !duLieuNguoiChoi.deTu.getTrangthai().equals("Về nhà")) {
                duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Sư phụ ơi người hết KI rồi",4f);
                timeQuanTamSuPhu = 30f;
            }
            int widthDeTu = (int) (thanhtheluc2.getWidth() * (duLieuNguoiChoi.deTu.getTheLuc() / 100f));
            thanhtheluc1 = new TextureRegion(thanhtheluc2, 0, 0, widthDeTu, thanhtheluc2.getHeight());
        }
        int widthSuPhu = (int)(thanhtheluc2.getWidth() * (duLieuNguoiChoi.getTheLuc()/100f));
        thanhtheluc3 = new TextureRegion(thanhtheluc2, 0, 0, widthSuPhu, thanhtheluc2.getHeight());
        if (dangHopThe) {
            int widthHP = (int) (thanhhpnv.getWidth() *
                (duLieuNguoiChoi.getHpHienTai() / (float) duLieuNguoiChoi.getHpHopThe()));
            thanhhpnv1 = new TextureRegion(thanhhpnv, 0, 0, widthHP, thanhhpnv.getHeight());

            int widthKI = (int) (thanhkinv.getWidth() *
                (duLieuNguoiChoi.getKiHienTai() / (float) duLieuNguoiChoi.getKiHopThe()));
            thanhkinv1 = new TextureRegion(thanhkinv, 0, 0, widthKI, thanhkinv.getHeight());
        } else {
            int widthHP = (int) (thanhhpnv.getWidth() *
                (duLieuNguoiChoi.getHpHienTai() / (float) duLieuNguoiChoi.getHpToiDa()));
            thanhhpnv1 = new TextureRegion(thanhhpnv, 0, 0, widthHP, thanhhpnv.getHeight());

            int widthKI = (int) (thanhkinv.getWidth() *
                (duLieuNguoiChoi.getKiHienTai() / (float) duLieuNguoiChoi.getKiToiDa()));
            thanhkinv1 = new TextureRegion(thanhkinv, 0, 0, widthKI, thanhkinv.getHeight());
        }
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (!dangHienKhungChat) {
            // RENDER SAU ẢNH ĐẬU THẦN ( trắng )
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1f, 1f, 1f, 1f);
            shapeRenderer.rect(screenWidth - 75 - 10 + 10, 10 + 10, 53, dauThanRenderH);
            shapeRenderer.end();
            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.setColor(0f, 0f, 0f, 0.55f);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.triangle(0,  screenHeight / 4f * 3 - 10, 0,  screenHeight / 4f * 3 + 38 + 10, 19+10f, screenHeight / 4f * 3 + 19f);
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
            batch.draw(nutpopup, nutpopupX, nutpopupY, 22, 38);

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
        if (veNenFlash) {
            batch.setColor(1, 1, 1, 0.7f);
            batch.draw(nenflash,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            batch.setColor(1, 1, 1, 1);
        }
        if (timeHopTheTHuong > 0) {
            batch.draw(dauGotenks,50,screenHeight / 4f * 3+17.5f,dauGotenks.getWidth()*0.52f,dauGotenks.getHeight()*0.52f);
            font.setColor(1,1,1,1);
            layout.setText(font,(int)(timeHopTheTHuong/60f)+"'");
            font.draw(batch,layout,68,screenHeight / 4f * 3+17.5f-7);
        }
        batch.end();
        if (vuaClickMoPopup && timeGlow>0) {
            veGlow(shapeRenderer,clickX,clickY,timeGlow);
        }
        batch.begin();
        renderPopup(batch);
        renderPetChat(batch);
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
            duLieuNguoiChoi.deTu.tangHpHienTai(duLieuNguoiChoi.getDauHoiHPKI());
            duLieuNguoiChoi.deTu.tangKiHienTai(duLieuNguoiChoi.getDauHoiHPKI());
            if (!duLieuNguoiChoi.deTu.getTrangthai().equals("Về nhà")) {
                duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Cám ơn sư phụ", 1f);
            }
            duLieuNguoiChoi.deTu.tangTheLuc(30);
            dauThanRenderH = 0f;
        }
    }
    public void chonSkill(int index) {
        if (index >= 0 && index < 5 && !dangHienKhungChat) {
            skillDangChon = index;
        }
    }
    public void update(float delta) {
        if (timeHienRongThan>0) {
            timeHienRongThan-=delta;
            if (timeHienRongThan<=0) {
                timeHienRongThan = 0;
            }
        }
        timeMiniGame -= delta;
        timeMiniGameChanLe -= delta;
        if (timeMiniGame<=0) {
            int conSoMayMan = MathUtils.random(1, 99);
            if (soNguoiChoiChon == conSoMayMan) {
                duLieuNguoiChoi.tangNgoc(soNgocCuoc*90);
                soNgocDuocNhanGanNhat = soNgocCuoc*90;
                dangHienTinNhanPet = true;
                timeHienTinNhanPet = 2f;
                tinNhanPet = "Chúc mừng người chơi "+duLieuNguoiChoi.getTen()+" đã may mắn nhận được "+formatVangNgoc(soNgocCuoc*90)+" ngọc xanh từ tính năng Mini Game";
            }
            soNguoiChoiChon = 0;
            soNgocCuoc = 0;
            ketQuaGiaiTruoc = conSoMayMan;
            timeMiniGame = 60f;
        }
        if (timeMiniGameChanLe<=0) {
            int conSoMayMan = MathUtils.random(1, 99);
            if ((NguoiChoiChonChanLe.equals("chan") && conSoMayMan%2==0) || (NguoiChoiChonChanLe.equals("le") && conSoMayMan%2!=0)) {
                duLieuNguoiChoi.tangVang((int)(soVangCuocChanLe*1.9f));
                soVangDuocNhanGanNhatChanLe = (int)(soVangCuocChanLe*1.9f);
                dangHienTinNhanPet = true;
                timeHienTinNhanPet = 2f;
                tinNhanPet = "Chúc mừng người chơi "+duLieuNguoiChoi.getTen()+" đã may mắn nhận được "+formatVangNgoc((int)(soVangCuocChanLe*1.9f))+" vàng từ tính năng Mini Game";
            }
            NguoiChoiChonChanLe = "";
            soVangCuocChanLe = 0;
            ketQuaGiaiTruocChanLe = conSoMayMan;
            timeMiniGameChanLe = 30f;
        }
        if (timeDoiDauThan > 0) {
            timeDoiDauThan -= delta;
            if (timeDoiDauThan <= 0) {
                timeDoiDauThan = 0;
            }
        }
        if (timeQuanTamSuPhu > 0) {
            timeQuanTamSuPhu -= delta;
            if (timeQuanTamSuPhu <= 0) {
                timeQuanTamSuPhu = 0;
            }
        }
        if (timeGlow > 0) {
            timeGlow -= delta;
            if (timeGlow <= 0) {
                timeGlow = 0;
                if (vuaClickTatPopup) {
                    tatPopupNhanVat();
                    hangTrangDangChon = -1;
                    oChiSoDangChon = -1;
                    vuaClickTatPopup = false;
                    scrollY = 0;
                    scrollYDeTu = 0;
                }
                if (vuaClickMoPopup) {
                    hienPopupNhanVat();
                    vuaClickMoPopup =false;
                }
            }
        }
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
                    if (oChiSoDangChon!=4) {
                        switch (oChiSoDangChon) {
                            case 0 -> trangthaide = "Đi theo";
                            case 1 -> trangthaide = "Bảo vệ";
                            case 2 -> trangthaide = "Tấn công";
                            case 3 -> trangthaide = "Về nhà";
                        }
                        capNhatTrangThaiDeTu();
                    }
                    if (oChiSoDangChon == 4 && !dangHopTheThuong && delayHopTheThuong == 0 && !dangHopThe) {
                        timeChoHopThe = 2f;
                        dangHienPopup = false;
                        scrollY = 0;
                        hangTrangDangChon = -1;
                        dangHopTheThuong = true;
                    } else if (dangHopThe) {
                        dangHienTinNhanPet = true;
                        timeHienTinNhanPet = 2f;
                        tinNhanPet = "Bạn đang hợp thể rồi";
                    } else if (delayHopTheThuong>0) {
                        dangHienTinNhanPet = true;
                        timeHienTinNhanPet = 2f;
                        tinNhanPet = "Cần chờ "+(int)(delayHopTheThuong/60f)+" phút nữa để hợp thể gotenks";
                    }
                } else if (dangChonNhacNen) {
                    String[] chucNang = {"Tắt nhạc","Khẩu thị tâm phi","Đếm ngày xa em","Kẻ theo đuổi ánh sáng","Tháp rơi tự do","Điều anh biết","DanDan Kokoro Hikareteku","Sao mình chưa nắm tay nhau","Thời gian sẽ trả lời","Sự thật đã bỏ quên"};
                    if (oChiSoDangChon == 0) {
                        for (int i = 1; i < nhacNen.length; i++) {
                            if (nhacNen[i].isPlaying()) nhacNen[i].stop();
                            dangHienTinNhanPet = true;
                            timeHienTinNhanPet = 2f;
                            tinNhanPet = "Bạn vừa tắt nhạc";
                        }
                    } else if (oChiSoDangChon >= 1 && oChiSoDangChon < nhacNen.length) {
                        // Tắt nhạc cũ nếu có
                        for (int i = 1; i < nhacNen.length; i++) {
                            if (nhacNen[i].isPlaying()) nhacNen[i].stop();
                        }
                        // Phát nhạc mới
                        nhacNen[oChiSoDangChon].play();
                        dangHienTinNhanPet = true;
                        timeHienTinNhanPet = 2f;
                        tinNhanPet = "Đang phát bài "+chucNang[oChiSoDangChon];
                    }
                    dangChonNhacNen = false;
                } else {
                    if (duLieuNguoiChoi.coDeTu()) {
                        if (oChiSoDangChon == 0) {
                            dangHienGioiThieuGame = true;
                        } else if (oChiSoDangChon == 1) {
                            dangHienChonMiniGame = true;
                        } else if (oChiSoDangChon == 2) {
                            dangHienThongBaoGame = true;
                        } else if (oChiSoDangChon == 3) {
                            dangHienPopupDeTu = true;
                            scrollYDeTu = 0;
                            hangTrangDangChon = -1;
                            hangTrangDeTuDangChon = -1;
                        } else if (oChiSoDangChon == 7) {
                            dangChonNhacNen = true;
                        }
                    } else {
                        if (oChiSoDangChon == 0) {
                            dangHienGioiThieuGame = true;
                        } else if (oChiSoDangChon == 1) {
                            dangHienChonMiniGame = true;
                        } else if (oChiSoDangChon == 2) {
                            dangHienThongBaoGame = true;
                        } else if (oChiSoDangChon == 6) {
                            dangChonNhacNen = true;
                        }
                    }
                }
                oChiSoDangChon = -1;
                scrollY = 0;
            }
        }
        if (dangHienTinNhanChat) {
            timeHienTinNhan += delta;
            if (timeHienTinNhan >= 2f) {
                dangHienTinNhanChat = false;
                timeHienTinNhan = 0;
                tinNhanChat = "";
                daRanDomChatDeTu = false;
            }
        }
        if (dangHienTinNhanPet) {
            if (timeHienTinNhanPet == 2) {
                daDuocBayLen = false;
            }
            timeHienTinNhanPet -= delta;
            if (timeHienTinNhanPet <= 0) {
                dangHienTinNhanPet = false;
                timeHienTinNhanPet = 0;
                daRoiPetXuong = false;
                tinNhanPet = "";
            }
            if (timeHienTinNhanPet <= 0.3f  && !daDuocBayLen) {
                daDuocBayLen = true;
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
                int[] gioiHanToiDa = {550000, 550000, 25000, 3000, 10};
                String[] chiSoCong = {"HP","KI","Sức đánh","Giáp","Chí Mạng"};
                int chiSoGoc = switch (oChiSoDangChon) {
                    case 0 -> duLieuNguoiChoi.getHpGoc();
                    case 1 -> duLieuNguoiChoi.getKiGoc();
                    case 2 -> duLieuNguoiChoi.getSucDanhGoc();
                    case 3 -> duLieuNguoiChoi.getGiapGoc();
                    case 4 -> duLieuNguoiChoi.getChiMangGoc();
                    default -> 0;
                };
                boolean duTiemNang = duLieuNguoiChoi.getTiemNangNhanVat() >= chiPhiTamThoi;
                boolean khongVuotGioiHan = chiSoGoc + giaTriTangTamThoi <= gioiHanToiDa[oChiSoDangChon];
                if (duTiemNang && khongVuotGioiHan) {
                    switch (oChiSoDangChonTamThoi) {
                        case 0 -> duLieuNguoiChoi.tangHpGoc(giaTriTangTamThoi, true);
                        case 1 -> duLieuNguoiChoi.tangKiGoc(giaTriTangTamThoi, true);
                        case 2 -> duLieuNguoiChoi.tangSucDanhGoc(giaTriTangTamThoi, true);
                        case 3 -> duLieuNguoiChoi.tangGiapGoc(giaTriTangTamThoi);
                        case 4 -> duLieuNguoiChoi.tangChiMangGoc(giaTriTangTamThoi);
                    }
                    duLieuNguoiChoi.giamTiemNang((long) chiPhiTamThoi);
                } else if (!duTiemNang) {
                    dangHienTinNhanPet = true;
                    timeHienTinNhanPet = 2f;
                    tinNhanPet = "Cần thêm "+formatVangNgoc(chiPhiTamThoi-duLieuNguoiChoi.getTiemNangNhanVat())+" tiềm năng nữa";
                } else {
                    dangHienTinNhanPet = true;
                    timeHienTinNhanPet = 2f;
                    tinNhanPet = "Bạn đã đạt giới hạn "+chiSoCong[oChiSoDangChon];
                }
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
                if (!dangHienMiniGame && !dangHienChonMiniGame && !dangHienMiniGameChanLe) {
                    // mac do
                    if (nuthanhtrangchon == 1) {
                        if ((nhanVat.getHanhtinh().equals(itemm.getHanhtinh()) || itemm.getHanhtinh().equals("all")) && duLieuNguoiChoi.getSucManh() >= itemm.getSucManhYeuCau()) {
                            if (!itemDangChon.equals("bongtai") && !itemDangChon.equals("ngocrong")) {
                                xulyitem.macDo(hangTrangDangChon);
                            }
                            if (itemDangChon.equals("bongtai")) {
                                if (timeHopTheTHuong == 0 && delayHopTheBongTai == 0 && duLieuNguoiChoi.coDeTu()) {
                                    timeChoHopThe = 1.5f;
                                    dangHienPopup = false;
                                    scrollY = 0;
                                    hangTrangDangChon = -1;
                                    dangHopTheThuong = false;
                                    if (!dangHopThe) {
                                        bongTaiDangDung = itemm.getId();
                                    }
                                } else {
                                    if (delayHopTheBongTai > 0) {
                                        dangHienTinNhanPet = true;
                                        timeHienTinNhanPet = 2f;
                                        tinNhanPet = "Vui lòng đợi " + (int) delayHopTheBongTai + " giây nữa";
                                    }
                                    if (timeHopTheTHuong > 0) {
                                        dangHienTinNhanPet = true;
                                        timeHienTinNhanPet = 2f;
                                        tinNhanPet = "Vui lòng chờ tách hợp thể Fusion dance";
                                    }
                                    if (!duLieuNguoiChoi.coDeTu()) {
                                        dangHienTinNhanPet = true;
                                        timeHienTinNhanPet = 2f;
                                        tinNhanPet = "Bạn chưa có đệ tử";
                                    }
                                }
                            }
                            if (itemDangChon.equals("ngocrong")) {
                                if (itemm.getId().equals("nr3s") || itemm.getId().equals("nr2s") || itemm.getId().equals("nr1s")) {
                                    System.out.print("Đã đúng id");
                                    String[] idsCanTim = new String[0];
                                    if (itemm.getId().equals("nr3s")) {
                                        idsCanTim = new String[]{"nr3s", "nr4s", "nr5s", "nr6s", "nr7s"};
                                        ngocRongUoc = "3sao";
                                    } else if (itemm.getId().equals("nr2s")) {
                                        idsCanTim = new String[]{"nr2s","nr3s", "nr4s", "nr5s", "nr6s", "nr7s"};
                                        ngocRongUoc = "2sao";
                                    } else if (itemm.getId().equals("nr1s")) {
                                        idsCanTim = new String[]{"nr1s","nr2s","nr3s", "nr4s", "nr5s", "nr6s", "nr7s"};
                                        ngocRongUoc = "1sao";
                                    }
                                    boolean duTatCa = true;
                                    ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();
                                    for (String idCanTim : idsCanTim) {
                                        boolean timThay = false;
                                        for (Item item : danhSach) {
                                            if (item != null && idCanTim.equals(item.getId())) {
                                                timThay = true;
                                                break;
                                            }
                                        }
                                        if (!timThay) {
                                            duTatCa = false; // thiếu ít nhất 1 id
                                            break;
                                        }
                                    }
                                    if (duTatCa) {
                                        dangHienTinNhanPet = true;
                                        timeHienTinNhanPet = 2f;
                                        tinNhanPet = "Bạn vừa gọi rồng thần";
                                        for (String idCanTim : idsCanTim) {
                                            for (Item item : danhSach) {
                                                if (item != null && idCanTim.equals(item.getId())) {
                                                    item.giamSoLuong(1);
                                                    if (item.getSoLuong() == 0) {
                                                        danhSach.remove(item);
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                        dangHienDieuUocRongThan = true;
                                        timeHienRongThan = 300f;
                                        dangHienPopup = false;
                                    } else {
                                        dangHienTinNhanPet = true;
                                        timeHienTinNhanPet = 2f;
                                        tinNhanPet = "Không đủ ngọc rồng";
                                        ngocRongUoc = "";
                                    }
                                } else {
                                    dangHienTinNhanPet = true;
                                    timeHienTinNhanPet = 2f;
                                    tinNhanPet = "Chỉ được gọi rồng bằng ngọc rồng 1, 2, 3 sao";
                                }
                            }
                        } else if (!nhanVat.getHanhtinh().equals(itemm.getHanhtinh())) {
                            dangHienTinNhanPet = true;
                            timeHienTinNhanPet = 2f;
                            String ht;
                            switch (itemm.getHanhtinh()) {
                                case "traidat": ht = "Trái Đất"; break;
                                case "xayda" : ht = "Sayda"; break;
                                case "namek" : ht = "Namếc"; break;
                                default: ht = "";
                            }
                            tinNhanPet = "Đồ này dành cho hành tinh "+ht;
                        } else if (duLieuNguoiChoi.getSucManh() < itemm.getSucManhYeuCau()) {
                            dangHienTinNhanPet = true;
                            timeHienTinNhanPet = 2f;
                            tinNhanPet = "Bạn cần thêm "+formatVangNgoc(itemm.getSucManhYeuCau()-duLieuNguoiChoi.getSucManh())+" sức mạnh nữa";
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
                    } else if (nuthanhtrangchon == 4) {
                        if (duLieuNguoiChoi.deTu.getSucManh() >= 1_500_000) {
                            if (duLieuNguoiChoi.deTu.getSucManh() >= itemm.getSucManhYeuCau()) {
                                boolean duDieuKien = false;
                                if (duLieuNguoiChoi.deTu.getHanhtinh().equals(itemm.getHanhtinh()) || itemm.getHanhtinh().equals("all")) {
                                    duDieuKien = true;
                                }
                                if (duDieuKien) {
                                    xulyitem.macDoChoDe(hangTrangDangChon);
                                    if (!dangHienPopupDeTu) {
                                        chucNangDangChon = 4;
                                        dangHienPopupDeTu = true;
                                        scrollY = 0;
                                        scrollYDeTu = 0;
                                    }
                                    if (!duLieuNguoiChoi.deTu.getTrangthai().equals("Về nhà")) {
                                        duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Cám ơn sư phụ", 3f);
                                    }
                                } else {
                                    if (!duLieuNguoiChoi.deTu.getHanhtinh().equals(itemm.getHanhtinh())) {
                                        dangHienTinNhanPet = true;
                                        timeHienTinNhanPet = 2f;
                                        String ht;
                                        switch (itemm.getHanhtinh()) {
                                            case "traidat": ht = "Trái Đất"; break;
                                            case "xayda" : ht = "Sayda"; break;
                                            case "namek" : ht = "Namếc"; break;
                                            default: ht = "";
                                        }
                                        tinNhanPet = "Đệ tử không thể mặc đồ "+ht;
                                    }
                                }
                            } else {
                                dangHienTinNhanPet = true;
                                timeHienTinNhanPet = 2f;
                                tinNhanPet = "Đệ tử cần thêm "+formatVangNgoc(itemm.getSucManhYeuCau()-duLieuNguoiChoi.deTu.getSucManh())+" sức mạnh nữa";
                            }
                        } else {
                            dangHienTinNhanPet = true;
                            timeHienTinNhanPet = 2f;
                            tinNhanPet = "Đệ tử cần lên 1tr5 sức mạnh";
                        }
                        hangTrangDangChon = -1;
                        hangTrangDeTuDangChon = -1;
                        DangHienPopupThongTin1 = false;
                        TimeChoHienPopup = 0;
                    } else if (nuthanhtrangchon == 5) {
                        xulyitem.goDoChoDe(hangTrangDeTuDangChon);
                        DangHienPopupThongTin2 = false;
                        TimeChoHienPopup = 0;
                    } else if (nuthanhtrangchon == 6) {
                        DangHienPopupThongTin2 = false;
                        TimeChoHienPopup = 0;
                    }
                } else if (dangHienMiniGame) {
                    if (dangHienMiniGame && !dangHienMiniGameHuongDanThem && !dangHienMiniGameThamGia) {
                        if (nuthanhtrangchon == 2) {
                            dangHienMiniGame = false;
                        } else if (nuthanhtrangchon == 1) {
                            dangHienMiniGameHuongDanThem = true;
                        } else {
                            dangHienMiniGameThamGia = true;
                        }
                    } else if (dangHienMiniGame && dangHienMiniGameHuongDanThem && !dangHienMiniGameThamGia) {
                        if (nuthanhtrangchon == 1) {
                            dangHienMiniGameHuongDanThem = false;
                        }
                    }
                } else if (dangHienMiniGameChanLe) {
                    if (dangHienMiniGameChanLe && !dangHienMiniGameHuongDanThemChanLe && !dangHienMiniGameThamGiaChanLe) {
                        if (nuthanhtrangchon == 2) {
                            dangHienMiniGameChanLe = false;
                        } else if (nuthanhtrangchon == 1) {
                            dangHienMiniGameHuongDanThemChanLe = true;
                        } else {
                            dangHienMiniGameThamGiaChanLe = true;
                        }
                    } else if (dangHienMiniGameChanLe && dangHienMiniGameHuongDanThemChanLe && !dangHienMiniGameThamGiaChanLe) {
                        if (nuthanhtrangchon == 1) {
                            dangHienMiniGameHuongDanThemChanLe = false;
                        }
                    }
                } else if (dangHienChonMiniGame) {
                    if (nuthanhtrangchon == 0) {
                        dangHienMiniGame = true;
                    } else if (nuthanhtrangchon == 1){
                        dangHienMiniGameChanLe = true;
                    }
                }
            }
        }
        if (timeChoHopThe>0) {
            timeChoHopThe -= Gdx.graphics.getDeltaTime();
            int tick = (int)(timeChoHopThe * 18);
            if (tick % 2 == 0) {
                veNenFlash = true;
            } else {
                veNenFlash = false;
            }
            if (timeChoHopThe <= 0) {
                timeChoHopThe = 0;
                veNenFlash = false;
                if (!dangHopThe) {
                    dangHopThe = true;
                    String hopTheDuocChon = dangHopTheThuong ? "hop_the_thuong_"+nhanVat.getHanhtinh() : (bongTaiDangDung.equals("bongtaic1") ? "bong_tai_1_"+nhanVat.getHanhtinh() : bongTaiDangDung.equals("bongtaic2") ? "bong_tai_2_"+nhanVat.getHanhtinh() : "bong_tai_3_"+nhanVat.getHanhtinh() );
                    NhanVatCauHinh c2 = Doicaitrang(hopTheDuocChon);
                    nhanVat.fixCaiTrang(
                        c2.dau_dung, c2.dau_chay,
                        c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                        c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                        c2.than_bay, c2.chan_bay,
                        c2.lechMap,
                        c2.avt
                    );
                    texAvt = new Texture(nhanVat.doiavatar());
                    if (bongTaiDangDung.equals("bongtaic1")) {
                        duLieuNguoiChoi.setHpHienTai((duLieuNguoiChoi.getHpToiDa() + duLieuNguoiChoi.deTu.getHpToiDa()));
                        duLieuNguoiChoi.setKiHienTai((duLieuNguoiChoi.getKiToiDa() + duLieuNguoiChoi.deTu.getKiToiDa()));
                    } else if (bongTaiDangDung.equals("bongtaic2")) {
                        duLieuNguoiChoi.setHpHienTai((duLieuNguoiChoi.getHpToiDa() + duLieuNguoiChoi.deTu.getHpToiDa()) * 1.1f);
                        duLieuNguoiChoi.setKiHienTai((duLieuNguoiChoi.getKiToiDa() + duLieuNguoiChoi.deTu.getKiToiDa()) * 1.1f);
                    } else if (bongTaiDangDung.equals("bongtaic3")) {
                        duLieuNguoiChoi.setHpHienTai((duLieuNguoiChoi.getHpToiDa() + duLieuNguoiChoi.deTu.getHpToiDa()) * 1.2f);
                        duLieuNguoiChoi.setKiHienTai((duLieuNguoiChoi.getKiToiDa() + duLieuNguoiChoi.deTu.getKiToiDa()) * 1.2f);
                    }
                    if (dangHopTheThuong) {
                        timeHopTheTHuong = 600f;
                        dangHienPopupDeTu = false;
                        chucNangDangChon = 1;
                    }
                } else {
                    dangHopThe = false;
                    trangthaide = "Bảo vệ";
                    capNhatTrangThaiDeTu();
                    delayHopTheBongTai = 10f;
                    delayHopTheThuong = 600f;
                    ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrangDangMac();
                    if (NhanVatXuLy.getDangMacCaiTrang() && !NhanVatXuLy.getDangMacAvatar() && danhSach.get(5) != null) {
                        NhanVatCauHinh c2 = Doicaitrang(danhSach.get(5).getId());
                        nhanVat.fixCaiTrang(
                            c2.dau_dung, c2.dau_chay,
                            c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                            c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                            c2.than_bay, c2.chan_bay,
                            c2.lechMap,
                            c2.avt
                        );
                        texAvt = new Texture(nhanVat.doiavatar());
                    } else {
                        NhanVatCauHinh c2 = Doi_avt_ao_quan(nhanVat.getHanhtinh(), avatardangmac, aodangmac, quandangmac);
                        nhanVat.fixCaiTrang(
                            c2.dau_dung, c2.dau_chay,
                            c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                            c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                            c2.than_bay, c2.chan_bay,
                            c2.lechMap,
                            c2.avt
                        );
                        texAvt = new Texture(nhanVat.doiavatar());
                    }
                }
            }
        }
        if (dangHopThe) {
            if (bongTaiDangDung.equals("bongtaic1")) {
                duLieuNguoiChoi.setHpHopThe(duLieuNguoiChoi.getHpToiDa() + duLieuNguoiChoi.deTu.getHpToiDa());
                duLieuNguoiChoi.setKiHopThe(duLieuNguoiChoi.getKiToiDa() + duLieuNguoiChoi.deTu.getKiToiDa());
                duLieuNguoiChoi.setSdHopThe(duLieuNguoiChoi.getSucDanhNhanVat() + duLieuNguoiChoi.deTu.getSucDanhDeTu());
            } else if(bongTaiDangDung.equals("bongtaic2")) {
                duLieuNguoiChoi.setHpHopThe((duLieuNguoiChoi.getHpToiDa() + duLieuNguoiChoi.deTu.getHpToiDa())*1.1f);
                duLieuNguoiChoi.setKiHopThe((duLieuNguoiChoi.getKiToiDa() + duLieuNguoiChoi.deTu.getKiToiDa())*1.1f);
                duLieuNguoiChoi.setSdHopThe((duLieuNguoiChoi.getSucDanhNhanVat() + duLieuNguoiChoi.deTu.getSucDanhDeTu())*1.1f);
            } else if(bongTaiDangDung.equals("bongtaic3")) {
                duLieuNguoiChoi.setHpHopThe((duLieuNguoiChoi.getHpToiDa() + duLieuNguoiChoi.deTu.getHpToiDa())*1.2f);
                duLieuNguoiChoi.setKiHopThe((duLieuNguoiChoi.getKiToiDa() + duLieuNguoiChoi.deTu.getKiToiDa())*1.2f);
                duLieuNguoiChoi.setSdHopThe((duLieuNguoiChoi.getSucDanhNhanVat() + duLieuNguoiChoi.deTu.getSucDanhDeTu())*1.2f);
            }
        } else {
            duLieuNguoiChoi.setHpHopThe(duLieuNguoiChoi.getHpToiDa());
            duLieuNguoiChoi.setKiHopThe(duLieuNguoiChoi.getKiToiDa());
            if (duLieuNguoiChoi.getHpHienTai()>duLieuNguoiChoi.getHpToiDa()){
                duLieuNguoiChoi.setHpHienTai(duLieuNguoiChoi.getHpToiDa());
            }
            if (duLieuNguoiChoi.getKiHienTai()>duLieuNguoiChoi.getKiToiDa()){
                duLieuNguoiChoi.setKiHienTai(duLieuNguoiChoi.getKiToiDa());
            }
            duLieuNguoiChoi.setSdHopThe(duLieuNguoiChoi.getSucDanhNhanVat());
        }
        if (dangMacGiapLuyenTap) {
            duLieuNguoiChoi.getHanhTrangDangMac().get(6).tangHanSuDung();
            nhanVat.setHanSuDungGiapLuyenTap(duLieuNguoiChoi.getHanhTrangDangMac().get(6).getHanSuDung());
            float sdConLai;
            switch (duLieuNguoiChoi.getHanhTrangDangMac().get(6).getId()) {
                case "glt_c3": sdConLai = 0.7f; break;
                case "glt_c2": sdConLai = 0.8f; break;
                case "glt_c1": sdConLai = 0.9f; break;
                default:       sdConLai = 1f;   break;
            }
            if (dangHopThe) {
                duLieuNguoiChoi.setSdHopThe(
                    (duLieuNguoiChoi.getSdHopThe()) * sdConLai
                );
            } else {
                duLieuNguoiChoi.setSdHopThe(duLieuNguoiChoi.getSucDanhNhanVat() * sdConLai);
            }
            if (daCongChiMang){
                duLieuNguoiChoi.giamSatThuongChiMang(30);
                duLieuNguoiChoi.giamChiMang(15);
                daCongChiMang = false;
            }
        } else if (itemGiapLuyenTapVuaCoi!=null) {
            if (itemGiapLuyenTapVuaCoi.getHanSuDung()>0f) {
                itemGiapLuyenTapVuaCoi.giamHanSuDung();
                nhanVat.setHanSuDungGiapLuyenTap(itemGiapLuyenTapVuaCoi.getHanSuDung());
                float sdCongThem;
                switch (itemGiapLuyenTapVuaCoi.getId()) {
                    case "glt_c3":
                        sdCongThem = 1.3f;
                        break;
                    case "glt_c2":
                        sdCongThem = 1.2f;
                        break;
                    case "glt_c1":
                        sdCongThem = 1.1f;
                        break;
                    default:
                        sdCongThem = 1f;
                        break;
                }
                if (dangHopThe) {
                    duLieuNguoiChoi.setSdHopThe(
                        (duLieuNguoiChoi.getSdHopThe()) * sdCongThem
                    );
                } else {
                    duLieuNguoiChoi.setSdHopThe(duLieuNguoiChoi.getSucDanhNhanVat() * sdCongThem);
                }
            } else {
                if (dangHopThe) {
                    duLieuNguoiChoi.setSdHopThe(
                        (duLieuNguoiChoi.getSdHopThe())
                    );
                } else {
                    duLieuNguoiChoi.setSdHopThe(duLieuNguoiChoi.getSucDanhNhanVat());
                }
            }
            if (itemGiapLuyenTapVuaCoi.getHanSuDung()>60f && !daCongChiMang){
                duLieuNguoiChoi.tangSatThuongChiMang(30);
                duLieuNguoiChoi.tangChiMang(15);
                daCongChiMang = true;
            }
            if (daCongChiMang && itemGiapLuyenTapVuaCoi.getHanSuDung()<=60f){
                duLieuNguoiChoi.giamSatThuongChiMang(30);
                duLieuNguoiChoi.giamChiMang(15);
                daCongChiMang = false;
            }
        }
        if (timeHopTheTHuong > 0) {
            timeHopTheTHuong -= delta;
            if (timeHopTheTHuong <= 1.5f) {
                int tick = (int) (timeHopTheTHuong * 18);
                if (tick % 2 == 0) {
                    veNenFlash = true;
                } else {
                    veNenFlash = false;
                }
            }
            if (timeHopTheTHuong <= 0) {
                timeHopTheTHuong = 0;
                veNenFlash = false;
                dangHopTheThuong = false;
                dangHopThe = false;
                trangthaide = "Bảo vệ";
                capNhatTrangThaiDeTu();
                delayHopTheBongTai = 10f;
                delayHopTheThuong = 600f;
                ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrangDangMac();
                if (NhanVatXuLy.getDangMacCaiTrang() && !NhanVatXuLy.getDangMacAvatar() && danhSach.get(5) != null) {
                    NhanVatCauHinh c2 = Doicaitrang(danhSach.get(5).getId());
                    nhanVat.fixCaiTrang(
                        c2.dau_dung, c2.dau_chay,
                        c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                        c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                        c2.than_bay, c2.chan_bay,
                        c2.lechMap,
                        c2.avt
                    );
                    texAvt = new Texture(nhanVat.doiavatar());
                } else {
                    NhanVatCauHinh c2 = Doi_avt_ao_quan(nhanVat.getHanhtinh(), avatardangmac, aodangmac, quandangmac);
                    nhanVat.fixCaiTrang(
                        c2.dau_dung, c2.dau_chay,
                        c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                        c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                        c2.than_bay, c2.chan_bay,
                        c2.lechMap,
                        c2.avt
                    );
                    texAvt = new Texture(nhanVat.doiavatar());
                }
            }
        }
        if (delayHopTheThuong > 0) {
            delayHopTheThuong -= delta;
            if (delayHopTheThuong <= 0) {
                delayHopTheThuong = 0;
            }
        }
        if (delayHopTheBongTai > 0) {
            delayHopTheBongTai -= delta;
            if (delayHopTheBongTai <= 0) {
                delayHopTheBongTai = 0;
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
                                case 7:
                                    xulyitem.goVanBay(true);
                                    break;
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
                        if (duLieuNguoiChoi.coDeTu()) {
                            duLieuNguoiChoi.deTu.tangSucManh(50_000_000_000L);
                        }
                        dangHienTinNhanPet = true;
                        timeHienTinNhanPet = 2f;
                        tinNhanPet = "Bạn vừa nhận quà VIP từ admin HAIDANG";
                        chuaNhanQuaLanDau = false;
                    } else {
                        dangHienTinNhanPet = true;
                        timeHienTinNhanPet = 2f;
                        tinNhanPet = "Bạn đã nhận quà này rồi";
                    }
                    nutduocchon = -1;
                } else if (dangHienMiniGameThamGia) {
                    if (nutduocchon == 2){
                        dangHienMiniGameThamGia = false;
                        nutduocchon = -1;
                        soNgocNguoiChoiNhap = "";
                    } else if (nutduocchon == 1) {
                        if (!soNgocNguoiChoiNhap.isEmpty()){
                            if (soNgocNguoiChoiNhap.contains("/") && soNgocNguoiChoiNhap.split("/").length == 2) {
                                try {
                                    String[] parts = soNgocNguoiChoiNhap.split("/");
                                    int soNgoc = Integer.parseInt(parts[0].trim());
                                    int soChon = Integer.parseInt(parts[1].trim());
                                    if (soNgoc >= 1 && soChon>=1 && soChon <= 99 && soNgocCuoc == 0 && soNguoiChoiChon == 0 && duLieuNguoiChoi.getNgoc()>=soNgoc) {
                                        soNgocCuoc = soNgoc;
                                        duLieuNguoiChoi.giamNgoc(soNgoc);
                                        soNguoiChoiChon = soChon;
                                    }
                                    dangHienMiniGameThamGia = false;
                                    soNgocNguoiChoiNhap = "";
                                    nutduocchon = -1;
                                } catch (NumberFormatException e) {
                                    dangHienMiniGameThamGia = false;
                                    soNgocNguoiChoiNhap = "";
                                    nutduocchon = -1;
                                }
                            } else {
                                dangHienMiniGameThamGia = false;
                                soNgocNguoiChoiNhap = "";
                                nutduocchon = -1;
                            }
                        }
                    }
                } else if (dangHienMiniGameThamGiaChanLe) {
                    if (nutduocchon == 2){
                        dangHienMiniGameThamGiaChanLe = false;
                        nutduocchon = -1;
                        soVangNguoiChoiNhapChanLe = "";
                    } else if (nutduocchon == 1) {
                        if (!soVangNguoiChoiNhapChanLe.isEmpty()){
                            if (soVangNguoiChoiNhapChanLe.contains("/") && soVangNguoiChoiNhapChanLe.split("/").length == 2) {
                                try {
                                    String[] parts = soVangNguoiChoiNhapChanLe.split("/");
                                    int soVang = Integer.parseInt(parts[0].trim());
                                    String duDoan = parts[1].trim();
                                    if (soVang >= 1 && (duDoan.equals("chan") || duDoan.equals("le")) && soVangCuocChanLe == 0 && "".equals(NguoiChoiChonChanLe) && duLieuNguoiChoi.getVang()>=soVang) {
                                        soVangCuocChanLe = soVang;
                                        duLieuNguoiChoi.giamVang(soVang);
                                        NguoiChoiChonChanLe = duDoan;
                                    }
                                    dangHienMiniGameThamGiaChanLe = false;
                                    soVangNguoiChoiNhapChanLe = "";
                                    nutduocchon = -1;
                                } catch (NumberFormatException e) {
                                    dangHienMiniGameThamGiaChanLe = false;
                                    soVangNguoiChoiNhapChanLe = "";
                                    nutduocchon = -1;
                                }
                            } else {
                                dangHienMiniGameThamGiaChanLe = false;
                                soVangNguoiChoiNhapChanLe = "";
                                nutduocchon = -1;
                            }
                        }
                    }
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
        if (DangHienPopupThongTin2){
            TimeChoHienPopup1-=Gdx.graphics.getDeltaTime();
        }
        if (chucNangDangChon != 2){
            DangHienPopupThongTin = false;
        }
        if (dauThanRenderH < 53f){
            dauThanRenderH+=10.6f*Gdx.graphics.getDeltaTime();
            dauThanRenderH = Math.min(dauThanRenderH, 53f);
        }
    }
    public void xuLyClick(int x, int y) {
        clickHandler.xuLyClick(x, y);
    }

    public void hienPopupNhanVat() {
        dangHienPopup = true;
//        vuaMoPopup = true;
    }

    public void tatPopupNhanVat() {
        dangHienPopup = false;
        vuaTatPopup = true;
    }

    public boolean isDangHienPopup() {
        return dangHienPopup;
    }
    public void setNhanVat(NhanVat nhanVat) {
        this.nhanVat = nhanVat;
        avtPetTheoHanhTinh = new Texture("nhanvat/npc/npc_pet/"+nhanVat.getHanhtinh()+"/avt.png");
        if (!nhanVat.getTen().equals("ADMIN")) {
            for (int i = 0; i < 2; i++) {
                framesPet[i] = new Texture("nhanvat/npc/npc_pet/" + "xayda" + "/" + (i + 1) + ".png");
            }
        } else {
            for (int i = 0; i < 2; i++) {
                framesPet[i] = new Texture("nhanvat/npc/npc_pet/" + "admin" + "/" + (i + 1) + ".png");
            }
        }
        dauGotenks = new Texture("nhanvat/caitrang/hop_the_thuong_"+nhanVat.getHanhtinh()+"/daudung.png");
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
    public void renderPetChat(SpriteBatch batch) {
        if (dangHienTinNhanPet) {
            batch.setProjectionMatrix(camManager.camera.combined);
            float offsetY;
            if (timeHienTinNhanPet>=1.7f && !daRoiPetXuong) {
                offsetY = (timeHienTinNhanPet-1.7f)/0.3f*Gdx.graphics.getHeight();
            } else if (timeHienTinNhanPet<1.7f && timeHienTinNhanPet>0.3f) {
                offsetY = 0;
                daRoiPetXuong = true;
            } else {
                if (daDuocBayLen) {
                    offsetY = (0.3f - timeHienTinNhanPet) / 0.3f * Gdx.graphics.getHeight();
                } else {
                    offsetY = 0;
                }
            }
            layout.setText(
                fontchat,
                tinNhanPet,
                new Color(0, 0, 0, 1),
                180,
                Align.center,
                true
            );
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 1);
            float xOffset = nhanVat.getFlipX() ? 50f : -50f;
            shapeRenderer.rect(nhanVat.getX() + (nhanVat.getRong() - 200) / 2f + xOffset, nhanVat.getY() + nhanVat.getCao() + 80 + offsetY, 200, 36 + layout.height);
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            for (int i = 0; i < 2; i++) {
                shapeRenderer.rect(nhanVat.getX() + (nhanVat.getRong() - 200) / 2f - i + xOffset, nhanVat.getY() + nhanVat.getCao() + 80 - i + offsetY, 200 + i * 2, 36 + layout.height + i * 2);
            }
            shapeRenderer.end();
            batch.begin();
            float duoiX = nhanVat.getFlipX() ? nhanVat.getX() + nhanVat.getRong() + 20+50f : nhanVat.getX() - 20-50f;
            float flipScale = nhanVat.getFlipX() ? -1f : 1f;
            batch.draw(duoichat, duoiX, nhanVat.getY() + nhanVat.getCao() + 65 + offsetY, 16 * flipScale, 16);
            fontchat.draw(batch, layout, nhanVat.getX() + (nhanVat.getRong() - 200) / 2f + xOffset + 10f, nhanVat.getY() + nhanVat.getCao() + 80 + 18f + layout.height + offsetY);
            int index = ((int)(timeHienTinNhanPet * 10f)) % 2;
            float daoDong = (float) Math.sin(nhanVat.thoiGianTichLuy) * 3f ;
            batch.draw(framesPet[index],duoiX,nhanVat.getY() + nhanVat.getCao() + 10f + daoDong + offsetY,framesPet[index].getWidth()*0.5f* flipScale,framesPet[index].getHeight()*0.5f);
            batch.setProjectionMatrix(camManager.uiCamera.combined);
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        }
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
    void PopupHanhTrangDeTu(ShapeRenderer shapeRenderer,SpriteBatch batch, float x, float y , float width , int oHanhTrangDangChon) {
        popupHanhTrang.PopupHanhTrangDeTu(shapeRenderer,batch,x,y,width,oHanhTrangDangChon);
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

    public DeTuCauHinh DoicaitrangDeTu(String TenCaiTrang){
        return DeTuXuLy.xuly_id("caitrang_"+TenCaiTrang);
    }
    public DeTuCauHinh Doi_avt_ao_quan_DeTu(String HanhTinh, String TenAvatar , String ao, String quan){
        return DeTuXuLy.xuly_id("avatar_"+HanhTinh+"+"+TenAvatar+"+"+ao+"+"+quan);
    }

    public void dispose() {
        Texture[] textures = {
            saoden, saoxanh, ochat, ochatclick, thanhhp,
            odauthan, odauthanclick, oskill, oskillclick,
            nutpopup, popupNhanVat, nutX, nutchucnang, nutchucnangclick,
            vang, ngoc, thanhtheluc, thanhtheluc2,
            hanh_trang, hanh_trang_click, hanh_trang_dang_mac, hanh_trang_dang_mac_click,
            o_chi_so_co_ban, o_chi_so_co_ban_click,
            o_noi_tai, o_noi_tai_click, iconnoitai,
            nutvuong, nutvuongclick, anhThongBao,
            nutdn, nutclick, khungchat, duoichat,
            thanhhpnv, thanhkinv,

            // Các texture hiệu ứng hợp thể
            nenflash,
            dautrai, thantrai, chantrai,
            dauphai, thanphai, chanphai,
            dauGotenks,
            dau1, than1, chan1, dau2, than2, chan2, dau3, than3, chan3, dau6, than6, chan6, than7, chan7,
            dau1p, than1p, chan1p, dau2p, than2p, chan2p, dau3p, than3p, chan3p, dau6p, than6p, chan6p, than7p, chan7p
        };

        for (Texture tex : textures) {
            if (tex != null) tex.dispose();
        }

        for (Texture icon : iconchisocoban) {
            if (icon != null) icon.dispose();
        }

        if (texAvt != null) texAvt.dispose();

        for (int i = 1; i < nhacNen.length; i++) {
            if (nhacNen[i] != null) nhacNen[i].dispose();
        }

        BitmapFont[] fonts = {
            font, fontText, fontTenSkill, fontSkilldaco, fontSkillchuaco, fontSkillchuaco1,
            fontDauThan, fontchat, fontChucnang, fontNhiemVu, fontNhiemVu1, fontCapSKill,
            fontMotaNganSkill1, fontNhiemVuChuaLam, fontMotaNhiemVu, fontMotaNoiTai,
            fontMotaSkill, fontvangngoc, fontMotaHanhTrang, fontMotaHanhTrang1,
            fontTiemNang, fontsm, fontMotaNganSkill
        };

        for (BitmapFont f : fonts) {
            if (f != null) f.dispose();
        }

        if (shapeRenderer != null) shapeRenderer.dispose();
        for (int i = 0; i < 6; i++) {
            if (dau[i] != null) dau[i].dispose();
            if (than[i] != null) than[i].dispose();
            if (chan[i] != null) chan[i].dispose();

            if (daup[i] != null) daup[i].dispose();
            if (thanp[i] != null) thanp[i].dispose();
            if (chanp[i] != null) chanp[i].dispose();
        }
    }
    public void veGlow(ShapeRenderer shapeRenderer, float x, float y, float timeGlow) {
        if (timeGlow <= 0) return;

        Gdx.gl.glEnable(GL20.GL_BLEND); // Cho phép alpha
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        int soVong = 10; // số vòng tròn đồng tâm
        float banKinhGoc = 10; // bán kính nhỏ nhất
        float maxThem = 30;    // mức tăng bán kính cho vòng lớn nhất
        float alphaBase = Math.min(timeGlow / 0.5f, 1f); // alpha giảm theo thời gian

        for (int i = 0; i < soVong; i++) {
            float tiLe = (float) i / soVong;
            float radius = banKinhGoc + tiLe * maxThem;
            float alpha = (1 - tiLe) * alphaBase * 0.6f; // vòng ngoài mờ hơn
            shapeRenderer.setColor(1f, 1f, 0f, alpha); // màu vàng, alpha mờ dần
            shapeRenderer.circle(x, y, radius);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    public void capNhatTrangThaiDeTu() {
        if (trangthaide.equals("Đi theo")) {
            duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Ok con theo sư phụ",3f);
        } else if (trangthaide.equals("Bảo vệ")) {
            duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Ok con sẽ bảo vệ sư phụ",3f);
        } else if (trangthaide.equals("Tấn công")) {
            duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Ok sư phụ để con lo cho",3f);
        } else if (trangthaide.equals("Về nhà")) {
            if (!duLieuNguoiChoi.deTu.getTrangthai().equals(trangthaide) && !dangHopThe) {
                duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Ok con về, bibi sư phụ", 1.5f);
                duLieuNguoiChoi.deTu.timeHoatAnhBienMat = 0.3f;
                duLieuNguoiChoi.deTu.chuaLayToaDoBienMat = true;
            }
        }
        duLieuNguoiChoi.deTu.setTrangthai(trangthaide);
    }
    public String formatSoTrongChuoi(String chuoiGoc,DecimalFormat dinhDang) {
        String[] tach = chuoiGoc.split("/",-1);

        if (tach.length == 2) {
            try {
                long so = Long.parseLong(tach[0].trim());
                return dinhDang.format(so) + "/" + tach[1];
            } catch (NumberFormatException e) {
                return chuoiGoc;
            }
        }

        return chuoiGoc;
    }
    public boolean laClickTrenHUD(float x, float y) {
        // === VÙNG Ô SKILL ===
        int oskillW = 50;
        int oskillH = 50;
        float skillBaseX = 30;
        float skillY = 25f;
        for (int i = 0; i < 5; i++) {
            float x_ve = skillBaseX + i * 65f;
            if (x >= x_ve && x <= x_ve + oskillW && y >= skillY && y <= skillY + oskillH) {
                return true;
            }
        }

        // === VÙNG Ô CHAT ===
        int ochatW = 60;
        int ochatH = 60;
        float ochatX = Gdx.graphics.getWidth() - ochatW - 15;
        float ochatY = Gdx.graphics.getHeight() - 10 - ochatH;
        if (x >= ochatX && x <= ochatX + ochatW && y >= ochatY && y <= ochatY + ochatH) {
            return true;
        }

        // === VÙNG Ô ĐẬU THẦN ===
        int odauthanW = 75;
        int odauthanH = 75;
        float odauthanX = Gdx.graphics.getWidth() - odauthanW - 10;
        float odauthanY = 10;
        if (x >= odauthanX && x <= odauthanX + odauthanW && y >= odauthanY && y <= odauthanY + odauthanH) {
            return true;
        }

        // === VÙNG MỞ POPUP ===
        float nutPopupX = 0f;
        float nutPopupY = Gdx.graphics.getHeight() / 4f * 3;
        if (x >= nutPopupX && x <= nutPopupX + 25 && y >= nutPopupY && y <= nutPopupY + 35) {
            return true;
        }

        return false; // không trúng vùng nào
    }
    public void setCamera(QuanLyCamera camManager) {
        this.camManager = camManager;
    }
    void chonDieuUocRongThan(SpriteBatch batch) {
        if (timeHienRongThan<=0) return;
        float timeMax = 300f;
        float step = 0.12f;
        if (timeHienRongThan>300f-2.52f) {
            int tick = (int) (timeHienRongThan * 18);
            if (tick % 2 == 0) {
                veNenFlash = true;
            } else {
                veNenFlash = false;
            }
        } else {
            veNenFlash = false;
        }
        for (int i = 0; i < 21; i++) {
            float start = timeMax - i*step;
            float end = start - step;
            if (timeHienRongThan >= end && timeHienRongThan < start) {
                batch.setProjectionMatrix(camManager.camera.combined);
                batch.draw(hieuUngRongThan[i],nhanVat.getX()+120f-(hieuUngRongThan[i].getWidth()*0.5f/2f),nhanVat.getY()+200-(hieuUngRongThan[i].getHeight()*0.5f/2f),hieuUngRongThan[i].getWidth()*0.5f,hieuUngRongThan[i].getHeight()*0.5f);
                batch.setProjectionMatrix(camManager.uiCamera.combined);
            }
        }
    }
}
