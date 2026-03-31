package com.dang.dragonboy.hien_thi;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.giao_dien_ngoai.ManHinhMenu;
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.*;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.item.ThemItemTest;
import java.text.DecimalFormat;
import com.badlogic.gdx.graphics.GL20;
import com.dang.dragonboy.item.Item;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Music;
import java.util.LinkedList;
import com.dang.dragonboy.he_thong.TrangThaiChu;
import com.dang.dragonboy.websocket.*;
import com.dang.dragonboy.xu_ly_map.MapDoiHoaCuc;
import com.dang.dragonboy.xu_ly_map.MapLangAru;
import com.dang.dragonboy.xu_ly_map.MapNhaGohan;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.MapCoBan;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_haidang.admin_haidang;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.admin_thanhle;

public class VeHUD {
    //Map hien tai
    public MapCoBan mapHienTai;
    // trang thai man hinh
    public TrangThaiChucNangHUD trangThaiChucNangHUD = TrangThaiChucNangHUD.NHIEM_VU;
    public TrangThaiChucNangHUD_ChucNang trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
    public TrangThaiChucNangHUD_ChucNang_ThongBao trangThaiChucNangHUDChucNangThongBao = TrangThaiChucNangHUD_ChucNang_ThongBao.NONE;
    public TrangThaiChucNangHUD_ChucNang_MiniGame trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE;
    //fps
    public boolean dangBatFPS = false;
    private float timeCapNhatFPS = 0f;
    private int fps = 0;

    //cooldown
    public boolean dangHienCoolDownSkill = false;
    //mapHienTai
    public boolean dangHienMapHienTai = false;
    //ngayGioHienTai
    public boolean dangHienNgayGioHienTai = false;
    //ChiSoHPKI
    public boolean dangHienChiSo = false;

    public ThemItemTest themItemTest;
    private HUDThoiGianItemPhuTro thoiGianItemPhuTro;
    private HUDRongThan HUDRongThan;
    private HUDThoiGianMiniGame thoiGianMiniGame;
    public HUDVeGlow veGlow;
    private HUDTinhToanChiSo tinhToanChiSo;
    private HUDClickHandler clickHandler;
    private HUDPopupRenderer popupRenderer;
    public HUDXulyitem xulyitem;
    private HUDPopupThongTin popupThongTin;
    private HUDPopupHanhTrang popupHanhTrang;
    private HUDRuongDo ruongDo;

    private DuLieuNguoiChoi duLieuNguoiChoi;
    public QuanLyCamera camManager;

    public Texture thanhhpnv, thanhkinv;
    public TextureRegion thanhhpnv1, thanhkinv1;
    public Texture saoden, saoxanh;
    public Texture ochat, ochatclick;
    public Texture thanhhp;
    public Texture odauthan, odauthanclick;
    public Texture oskill, oskillclick;
    public Texture nutpopup;

    public BitmapFont fontTen,font,fontNgayGioHienTai, fontChucnang,fontChucnang1, fontDauThan, fontNhiemVu, fontNhiemVu1, fontNhiemVuChuaLam, fontMotaNhiemVu, fontvangngoc, fontsm, fontSkilldaco, fontSkillchuaco, fontMotaSkill, fontCapSKill, fontMotaNoiTai, fontTiemNang, fontTenSkill, fontchat, fontMotaNganSkill, fontMotaNganSkill1, fontSkillchuaco1, fontMotaHanhTrang, fontMotaHanhTrang1, fontText, fontMoTaQuyDoiVe, fontMotaChucNangNpc;
    public GlyphLayout layout;

    public SkillNhanVat[] skillIcons;

    private float thoiGianClickOChat = 0;
    private float thoiGianClickODauThan = 0;
    public int skillDangChon = -1; // -1: chưa chọn

    public ShapeRenderer shapeRenderer;

    public Texture popupNhanVat;
    public Texture nutX;
    public boolean dangHienPopup = false;
    public boolean vuaMoPopup = false;
    public boolean vuaTatPopup = false;
    private NhanVat nhanVat;
    public Texture texAvt = null;
    public Texture nutchucnang, nutchucnangclick;
    public int chucNangDeTuDangChon = 0;
    public Texture vang, ngoc;
    public Texture thanhtheluc, thanhtheluc2;
    public TextureRegion thanhtheluc1, thanhtheluc3;

    public Texture hanh_trang, hanh_trang_click, hanh_trang_dang_mac, hanh_trang_dang_mac_click, nenTrangNga, nenTrangNgaClick ;

    public float scrollYPhai = 0f;
    public float scrollYTrai = 0f;
    public float maxScrollPhai = 0f;
    public float maxScrollTrai = 0f;
    private final float scrollSpeed = 30f; // số pixel cuộn mỗi lần

    public int hangTrangDangChon = -1;
    public int hangTrangDeTuDangChon = -1;
    public int hanhTrangRuongDoDangChon = -1;

    public Texture o_noi_tai, o_noi_tai_click, o_chi_so_co_ban, o_chi_so_co_ban_click;
    public int oChiSoDangChon = -1;
    public Texture[] iconchisocoban = new Texture[5];
    public Texture iconnoitai;

    public Texture nutvuong, nutvuongclick;
    public boolean DangHienPopupThongTin = false;
    public boolean DangHienPopupThongTin1 = false;
    public boolean DangHienPopupThongTin2 = false;
    public boolean DangHienPopupThongTin3 = false;
    public float TimeChoHienPopup = 0;
    public float TimeChoHienPopup1 = 0;
    public boolean vuaMoPopupThongTin = false;
    public float PopupThongTinX = 0;
    public float PopupThongTinY = 0;
    public float PopupThongTinW = 0;
    public float PopupThongTinH = 0;
    public float PopupHanhTrangX_Trai = 0;
    public float PopupHanhTrangY_Trai = 0;
    public float PopupHanhTrangX_Phai = 0;
    public float PopupHanhTrangY_Phai = 0;
    public Item itemm = null;

    public float PopupHanhTrangW_Phai = 0;
    public float PopupHanhTrangH_Phai = 0;
    public float PopupHanhTrangW_Trai = 0;
    public float PopupHanhTrangH_Trai = 0;

    float nutClickTimer = 0;
    float nutClickTimer1 = 0;
    int oChiSoDangChonTamThoi = -1;
    int giaTriTangTamThoi = 0;
    long chiPhiTamThoi = 0;
    public int nutduocchon = -1;

    public boolean HienPopUpGanSkill = false;
    float TimeChoHienPopupGanSkill = 0;
    private int[] oSkills;
    float nutClickTimer2 = 0;

    public String ao, quan, gang, giay, rada, iconct, giaplt, vanbay;
    public String aoDeTu, quanDeTu, gangDeTu, giayDeTu, radaDeTu, iconctDeTu;

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

    public float timeDoTre = 0;
    private boolean chuaNhanQuaLanDau = true;

    public boolean dangChonHanhTrangPhai = false;
    public boolean dangChonHanhTrangTrai = false;

    public Music[] nhacNen = new Music[13];

    public float timeMiniGame = 60f;
    public int ketQuaGiaiTruoc;
    public int soNguoiChoiChon;
    public int soNgocCuoc;
    public int soNgocDuocNhanGanNhat;
    public String soNgocNguoiChoiNhap = "";

    public float timeMiniGameChanLe = 30f;
    public int ketQuaGiaiTruocChanLe;
    public String NguoiChoiChonChanLe = "";
    public long soVangCuocChanLe;
    public long soVangDuocNhanGanNhatChanLe;
    public String soVangNguoiChoiNhapChanLe = "";

    public boolean dangHopThe = false;
    public boolean vuaHopThe = false;
    public float timeChoHopThe = 0;
    public Texture nenflash;
    public boolean veNenFlash;

    public float timeChoBienKhi = 0;
    public boolean dangBienKhi = false;
    public float timeBienKhi = 0f;
    public final float timeBienKhiMAX = 200f;
    public float timeCoolDownBienKhi = 0f;
    public float sucDanhTangBienKhi = 0f;
    public float hpTangBienKhi = 0f;
    public float timeSauBienKhi = 0;
    public boolean vuaBienKhi = false;

    public final float timeTtnlMax = 10f;
    public boolean dangTtnl = false;
    public float timeTtnl = 0f;
    public float timeCoolDownTtnl = 0f;
    public float hpHoiTtnl, KiHoiTtnl;
    public float timeNhayLanTiepTtnl = 1f;
    public float timeThongBaoHoiPhucTtnl = 0f;

    public boolean dangHuytSao = false;
    public float timeHuytSao = 0f;
    public float timeCoolDownHuytSao = 0f;
    public float hpTangHuytSao;

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

    public boolean keoHanhTrangPhai = false;
    public boolean vuaKeoHanhTrangPhai = false;
    public boolean keoHanhTrangTrai = false;
    public boolean vuaKeoHanhTrangTrai = false;

    public String trangthaide;
    public boolean renderDeTu = false;

    LinkedList<TrangThaiChu> lichSuTrangThaiChu = new LinkedList<>();

    public Texture avtPetTheoHanhTinh;
    public Texture[] framesPet = new Texture[2];

    public boolean daRanDomChatDeTu = false;
    public String bongTaiDangDung = "";
    public boolean bongTaiRongThan = false;

    public String ngocRongUoc = "";
    public boolean dangHienDieuUocRongThan = false;
    public float timeHienRongThan = 0f;
    public float timeDelayUocRong = 0f;
    public boolean daUocRongThan = false;

    public boolean dangDungHuyHieu = false;
    public boolean chuaSetUpAnhHuyHieu = true;
    public Texture[] anhHuyHieu = new Texture[6];
    public int framesHuyHieu = 0;
    public Item huyHieuDangDung = null;

    public boolean dangDungDeoLung = false;
    public boolean chuaSetUpAnhDeoLung = true;
    public Texture[] anhDeoLung;
    public int framesDeoLung = 0;
    public Item deoLungDangDung = null;

    public boolean dangDungAura = false;
    public boolean chuaSetUpAnhAura = true;
    public Texture[] anhAura = new Texture[4];
    public int framesAura = 0;
    public Item auraDangDung = null;
    public float timeChoBuffAuraTieuDoiTruong = 0f;

    private Texture boHuyet, boKhi, cuongNo, giapXen;
    public boolean dangDungBoHuyet = false;
    public float timeDungBoHuyet = 0f;
    public boolean dangDungBoKhi = false;
    public float timeDungBoKhi = 0f;
    public boolean dangDungCuongNo = false;
    public float timeDungCuongNo = 0f;
    public boolean dangDungGiapXen = false;
    public float timeDungGiapXen = 0f;
    public boolean dangDungSinhLuc = false;
    public float timeDungSinhLuc = 0f;
    public boolean dangDungCuongCong = false;
    public float timeDungCuongCong = 0f;
    public boolean dangDungLinhKhi = false;
    public float timeDungLinhKhi = 0f;

    public boolean dangHienDauThan = false, vuaClickNangCapDau = false, vuaClickThuHoachDau = false;
    public boolean dangHienRuongDo = false;
    public boolean vuaTatRuongDo = false;

    public boolean daClickVaoNpc = false;
    public Npc npcHienTai;
    public boolean vuaThoatNpc = false;

    public boolean dangHienPopupNhanVatPhai = false;
    public float timeChoTatPopupNpc = 0f;

    // Phần logic cho việc click vào Người chơi khác
    public PlayerState playerDuocChon;
    public boolean daClickVaoPlayer = false;
    public boolean vuaThoatPlayer = false;
    public Texture muiTen;
    public Texture[] clickMuiTen = new Texture[4];

    public boolean dangHienKhungChung = false;

    // giao dich 2 nguoi choi
    public boolean dangCoYeuCauGiaoDich = false;
    public float timeChapNhanGiaoDich = 0f;
    public PlayerState playerGiaoDich;
    public float scrollX_trade = 0f;
    public boolean tradeTextDone = false;

    public boolean dangGiaoDich = false;
    public TrangThaiHanhTrangGd trangThaiHanhTrangGd = TrangThaiHanhTrangGd.ITEM_CHO;
    public int indexItemGiaoDich = -1;
    public int indexItemGiaoDichPlayer2 = -1;

    public void setDuLieuNguoiChoi(DuLieuNguoiChoi data) {
        this.duLieuNguoiChoi = data;
        duLieuNguoiChoi.setNhanVat(nhanVat);
        duLieuNguoiChoi.setVeHUD(this);
        nhanVat.setDuLieuNguoiChoi(duLieuNguoiChoi);
        // Gọi thêm item từ file ngoài
        if (State_Management.getUserResponse().coDeTu) {
            duLieuNguoiChoi.taoDeTu("Đệ tử", false);
            duLieuNguoiChoi.deTu.setVeHUD(this);
        }
        xulyitem = new HUDXulyitem(this, layout, duLieuNguoiChoi, nhanVat);
        themItemTest = new ThemItemTest(duLieuNguoiChoi,nhanVat,this);
        themItemTest.themItemTest();
        thoiGianItemPhuTro = new HUDThoiGianItemPhuTro(this);
        veGlow = new HUDVeGlow(this);
        HUDRongThan = new HUDRongThan(this,duLieuNguoiChoi,nhanVat);
        thoiGianMiniGame = new HUDThoiGianMiniGame(this,duLieuNguoiChoi);
        tinhToanChiSo = new HUDTinhToanChiSo(this,duLieuNguoiChoi,nhanVat);
        clickHandler = new HUDClickHandler(this, duLieuNguoiChoi, nhanVat);
        popupRenderer = new HUDPopupRenderer(this, layout, duLieuNguoiChoi,nhanVat);
        popupThongTin = new HUDPopupThongTin(this, layout, duLieuNguoiChoi, nhanVat);
        popupHanhTrang = new HUDPopupHanhTrang(this, layout, duLieuNguoiChoi, nhanVat);
        ruongDo = new HUDRuongDo(this,duLieuNguoiChoi,nhanVat);

        State_Management.setDuLieuStateManagement(nhanVat,this, duLieuNguoiChoi);
    }

    public void setCamera(QuanLyCamera camManager) {
        this.camManager = camManager;
    }

    public void setSkillIcons(SkillNhanVat[] skillIcons) {
        this.skillIcons = skillIcons;
        oSkills = new int[5];
        for (int i = 0; i < oSkills.length; i++) {
            if (nhanVat.getCapSkill(i+1) > 0) {
                oSkills[i] = i;
            } else {
                oSkills[i] = -1;
            }
        }
    }

    public void scrollPhai(int amount) {
        // amount âm là cuộn lên, dương là cuộn xuống
        scrollYPhai += amount * scrollSpeed;

        // Giới hạn scroll
        scrollYPhai = Math.max(0, Math.min(scrollYPhai, maxScrollPhai));
    }

    public void scrollTrai(int amount) {
        // amount âm là cuộn lên, dương là cuộn xuống
        scrollYTrai += amount * scrollSpeed;

        // Giới hạn scroll
        scrollYTrai = Math.max(0, Math.min(scrollYTrai, maxScrollTrai));
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
        boHuyet = new Texture("vatpham/vatphamgame/phu_tro/bo_huyet.png");
        boKhi = new Texture("vatpham/vatphamgame/phu_tro/bo_khi.png");
        cuongNo = new Texture("vatpham/vatphamgame/phu_tro/cuong_no.png");
        giapXen = new Texture("vatpham/vatphamgame/phu_tro/giap_xen.png");
        nenTrangNga = new Texture("hud/giaodientrong/ochisosc.png");
        nenTrangNgaClick = new Texture("hud/giaodientrong/ochisoscclick.png");
        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ ễ ằ ừ — ẳ ữ ỗ ằ ễ ỗ ừ ẵ ê : ĩ ≤";
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
        param.color = new Color(Color.WHITE);
        param.size = 19;
        param.borderWidth = 2f;
        param.borderColor = new Color(0.4f, 0.4f, 0.4f, 1f);
        fontNgayGioHienTai = generator.generateFont(param);
        generator.dispose();
        // Font có viền đen dành riêng cho dòng chữ "Đậu thần cấp ..."
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchinh.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param2.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ ễ ằ ừ — ẳ ữ ỗ ằ ễ ỗ ừ ẵ ê : ĩ ≤";
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
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ ễ ằ ừ — ẳ ữ ỗ ằ ễ ỗ ừ ẵ ê : ĩ ≤";
        param3.size = 14;
        fontMoTaQuyDoiVe = generator3.generateFont(param3);
        param3.size = 15;
        fontMotaChucNangNpc = generator3.generateFont(param3);
        param3.size = 14;
        fontchat = generator3.generateFont(param3);
        param3.color = new Color(94 / 255f, 86 / 255f, 74 / 255f, 1f);
        fontChucnang = generator3.generateFont(param3);
        param3.size = 11;
        fontChucnang1 = generator3.generateFont(param3);
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

        FreeTypeFontGenerator generator4 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchinh.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param4.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ Đ";
        param4.size = 21;
        fontTen = generator4.generateFont(param4);
        generator4.dispose();

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
            "suthatdaboquen.mp3",
            "khonglayduocvo.mp3",
            "seasons.mp3",
            "vokichcuaem.mp3"
        };

        for (int i = 1; i < tenFile.length; i++) {
            nhacNen[i] = Gdx.audio.newMusic(Gdx.files.internal("nhacnen/" + tenFile[i]));
            nhacNen[i].setLooping(true);
            nhacNen[i].setVolume(0.5f);
        }

        muiTen = new Texture("hud/giaodientrong/clicknpc.png");

        for (int i = 0; i < 4; i++) {
            clickMuiTen[i] = new Texture("hieuung/hieuunggame/click_npc/"+(i+1)+".png");
        }
    }

    public void render(SpriteBatch batch) {
        if (State_Management.isForceLogout()) {
            Gdx.input.setInputProcessor(null);
            // 1. Ngắt WS TRƯỚC TIÊN — trước khi làm bất cứ điều gì khác
            GameSocket.disconnect();

            // 2. Reset state
            State_Management.setToken("");
            State_Management.setSessionId("");
            State_Management.setRefresh_token("");
            State_Management.setAuth_id(0);
            State_Management.setRole("");
            State_Management.setForceLogout(false);
            State_Management.resetAll(); // gọi cuối vì có thể null nhiều thứ

            // 3. Chuyển màn hình — sau khi state đã sạch
            Main game = State_Management.game;
            State_Management.setForceLogoutMessage("Tài khoản được đăng nhập tại nơi khác!");
            game.setScreen(new ManHinhMenu(game, null, true));
        }
        if (GameSocket.isReconnecting || GameSocket.retryCount > 0) {
            batch.draw(anhThongBao, (Gdx.graphics.getWidth() - 740) / 2f, 85, 740, 168);
            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            layout.setText(font, "Kết nối thất bại, đang thử lại lần " + GameSocket.retryCount + "/" + GameSocket.MAX_RETRY);
            font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, 180);
        }
        if (GameSocket.retryCount == GameSocket.MAX_RETRY && !State_Management.isForceLogout()) {
            GameSocket.disconnect();
            State_Management.setToken("");
            State_Management.setSessionId("");
            State_Management.setRefresh_token("");
            State_Management.setAuth_id(0);
            State_Management.setRole("");
            State_Management.setForceLogout(false);
            State_Management.resetAll(); // gọi cuối vì có thể null nhiều thứ

            Main game = State_Management.game;
            State_Management.setForceLogoutMessage("Kết nối bị gián đoạn, về màn hình chính.");
            game.setScreen(new ManHinhMenu(game, null, true));
        }
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (duLieuNguoiChoi.coDeTu()) {
            // Ghi nhận trạng thái hiện tại của nhân vật
            TrangThaiChu trangThaiMoi = new TrangThaiChu(
                nhanVat.getX(),
                nhanVat.getY()
            );

            // Thêm vào danh sách
            lichSuTrangThaiChu.addFirst(trangThaiMoi);

            // Giữ lại tối đa 100 phần tử
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
        HUDRongThan.chonDieuUocRongThan(batch);
        batch.end();
        if (duLieuNguoiChoi.coDeTu()) {
            int widthDeTu = (int) (thanhtheluc2.getWidth() * (duLieuNguoiChoi.deTu.getTheLuc() / 100f));
            thanhtheluc1 = new TextureRegion(thanhtheluc2, 0, 0, widthDeTu, thanhtheluc2.getHeight());
        }
        int widthSuPhu = (int)(thanhtheluc2.getWidth() * (duLieuNguoiChoi.getTheLuc()/100f));
        thanhtheluc3 = new TextureRegion(thanhtheluc2, 0, 0, widthSuPhu, thanhtheluc2.getHeight());
        int widthHP = (int) (thanhhpnv.getWidth() *
            (duLieuNguoiChoi.getHpHienTai() / duLieuNguoiChoi.getHpHopThe()));
        thanhhpnv1 = new TextureRegion(thanhhpnv, 0, 0, widthHP, thanhhpnv.getHeight());

        int widthKI = (int) (thanhkinv.getWidth() *
            (duLieuNguoiChoi.getKiHienTai() / duLieuNguoiChoi.getKiHopThe()));
        thanhkinv1 = new TextureRegion(thanhkinv, 0, 0, widthKI, thanhkinv.getHeight());
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (!dangHienKhungChat && !daClickVaoNpc && !dangHienDauThan && !(timeHienRongThan<=300-2.1f && timeHienRongThan>0)) {
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
        if (dangHienChiSo) {
            layout.setText(fontsm,(int)duLieuNguoiChoi.getHpHienTai()+"");
            fontsm.draw(batch,layout,165+(thanhhpnv.getWidth()-layout.width)/2f,screenHeight - 80 - 5 + 55+(thanhhpnv.getHeight()+layout.height)/2f);
            layout.setText(fontsm,(int)duLieuNguoiChoi.getKiHienTai()+"");
            fontsm.draw(batch,layout,165+(thanhkinv.getWidth()-layout.width)/2f,screenHeight - 80 - 5 + 55-20+(thanhkinv.getHeight()+layout.height)/2f);
        }

        //FPS
        if (dangBatFPS) {
            fontTenSkill.setColor(0.761f, 0.114f, 0.067f, 1f);
            layout.setText(fontTenSkill, "FPS: " + fps);
            fontTenSkill.draw(batch, layout, 166, screenHeight - 80 - 5 + 55 - 20 - 15);
        }
        //Ngày giờ hiện tại
        if (dangHienNgayGioHienTai) {
            ZonedDateTime vnTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            layout.setText(fontNgayGioHienTai, vnTime.format(fmt)+"");
            fontNgayGioHienTai.draw(batch,layout,685,40);
        }
        //Map hien tai
        if (dangHienMapHienTai) {
            layout.setText(fontNgayGioHienTai,"Map: "+layTenMap());
            fontNgayGioHienTai.draw(batch,layout,40,420);
        }

        if (!dangHienKhungChat && !daClickVaoNpc && !dangHienDauThan && !(timeHienRongThan<=300-2.1f && timeHienRongThan>0)) {
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
                if (oSkills[i] != -1) {
                    batch.draw(skillIcons[oSkills[i]].icon, x + 6.9f, skillY + 6.9f, oskillW - 13.8f, oskillH - 13.8f);
                    if (oSkills[i] == 3 && timeCoolDownBienKhi>0) {
                        batch.end();
                        Gdx.gl.glEnable(GL20.GL_BLEND);
                        shapeRenderer.setColor(0f, 0f, 0f, 0.4f);
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                        shapeRenderer.rect(x + 6.9f,skillY + 6.9f,oskillW - 13.8f,(oskillH - 13.8f)*timeCoolDownBienKhi/(500-20*duLieuNguoiChoi.getCapSkill(3)));
                        shapeRenderer.end();
                        batch.begin();
                        if (dangHienCoolDownSkill) {
                            layout.setText(fontsm,(int)(timeCoolDownBienKhi)+"");
                            fontsm.draw(batch,layout,x + 6.9f+(oskillW - 13.8f-layout.width)/2f,skillY + 6.9f+(oskillH - 13.8f + layout.height)/2f);
                        }
                    }
                    if (oSkills[i] == 2 && timeCoolDownTtnl>0) {
                        batch.end();
                        Gdx.gl.glEnable(GL20.GL_BLEND);
                        shapeRenderer.setColor(0f, 0f, 0f, 0.4f);
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                        shapeRenderer.rect(x + 6.9f,skillY + 6.9f,oskillW - 13.8f,(oskillH - 13.8f)*timeCoolDownTtnl/20);
                        shapeRenderer.end();
                        batch.begin();
                        if (dangHienCoolDownSkill) {
                            layout.setText(fontsm,(int)(timeCoolDownTtnl)+"");
                            fontsm.draw(batch,layout,x + 6.9f+(oskillW - 13.8f-layout.width)/2f,skillY + 6.9f+(oskillH - 13.8f + layout.height)/2f);
                        }
                    }
                    if (oSkills[i] == 5 && timeCoolDownHuytSao>0) {
                        batch.end();
                        Gdx.gl.glEnable(GL20.GL_BLEND);
                        shapeRenderer.setColor(0f, 0f, 0f, 0.4f);
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                        shapeRenderer.rect(x + 6.9f,skillY + 6.9f,oskillW - 13.8f,(oskillH - 13.8f)*timeCoolDownHuytSao/180f);
                        shapeRenderer.end();
                        batch.begin();
                        if (dangHienCoolDownSkill) {
                            layout.setText(fontsm,(int)(timeCoolDownHuytSao)+"");
                            fontsm.draw(batch,layout,x + 6.9f+(oskillW - 13.8f-layout.width)/2f,skillY + 6.9f+(oskillH - 13.8f + layout.height)/2f);
                        }
                    }
                    if (oSkills[i] == 3 && timeChoBienKhi > 0 ) {
                        batch.end();
                        veGlow.veGlow(shapeRenderer,x + 6.9f+(oskillW - 13.8f)/2f,skillY + 6.9f+(oskillH - 13.8f)/2f,2f);
                        batch.begin();
                    }
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
        ArrayList<Texture> itemCanVe = new ArrayList<>();
        ArrayList<Float> timeTungItem = new ArrayList<>();
        if (dangBienKhi) {
            itemCanVe.add(skillIcons[3].icon);
            timeTungItem.add(timeBienKhi);
        }
        if (dangHuytSao) {
            itemCanVe.add(skillIcons[5].icon);
            timeTungItem.add(timeHuytSao);
        }
        if (dangDungBoHuyet) {
            itemCanVe.add(boHuyet);
            timeTungItem.add(timeDungBoHuyet);
        }
        if (dangDungBoKhi) {
            itemCanVe.add(boKhi);
            timeTungItem.add(timeDungBoKhi);
        }
        if (dangDungCuongNo) {
            itemCanVe.add(cuongNo);
            timeTungItem.add(timeDungCuongNo);
        }
        if (dangDungGiapXen) {
            itemCanVe.add(giapXen);
            timeTungItem.add(timeDungGiapXen);
        }
        float xVeItem = 40;
        if (timeHopTheTHuong > 0) {
            batch.draw(dauGotenks,40,screenHeight / 4f * 3+17.5f,dauGotenks.getWidth()*0.52f,dauGotenks.getHeight()*0.52f);
            font.setColor(1,1,1,1);
            layout.setText(font,(int)(timeHopTheTHuong/60f)+"'");
            font.draw(batch,layout,58,screenHeight / 4f * 3+17.5f-7);
            xVeItem += dauGotenks.getWidth()*0.52f+10;
        }
        for (int i = 0; i < itemCanVe.size(); i++) {
            batch.draw(itemCanVe.get(i),xVeItem,screenHeight / 4f * 3+17.5f,itemCanVe.get(i).getWidth()*0.52f,itemCanVe.get(i).getHeight()*0.52f);
            font.setColor(1,1,1,1);
            layout.setText(font,(float) timeTungItem.get(i) >= 60f ? (int)(float)(timeTungItem.get(i)/60f) +"'" : (int)(float)(timeTungItem.get(i))+"s");
            font.draw(batch,layout,xVeItem+(itemCanVe.get(i).getWidth()*0.52f-layout.width)/2f,screenHeight / 4f * 3+17.5f-7);
            xVeItem += 50;
        }
        if (veNenFlash) {
            if (ngocRongUoc.equals("1saoden")) {
                batch.setColor(0, 0, 0, 0.45f);
            } else {
                batch.setColor(1, 1, 1, 0.7f);
            }
            batch.draw(nenflash,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            batch.setColor(1, 1, 1, 1);
        }
        // yc gd
        if (dangCoYeuCauGiaoDich) {
          HUDTradeItem.render(batch, this);
        }

        // gd
        if (dangGiaoDich) {
            KhungGiaoDich.render(batch);
        }

        //
        ruongDo.renderRuongDo(batch);
        renderLuaChonCayDau(batch);
        if (daClickVaoNpc) {
            npcHienTai.renderHUDnpc(batch);
        }
        renderPopup(batch);
        renderPetChat(batch);

        if (dangHienKhungChung) {
            if (playerDuocChon != null) {
                KhungChung.renderKhungTrai(AssetMulti.getTexture(playerDuocChon.avatar), this, batch, "Thần Trái Đất\nSức mạnh: 10 tỷ", playerDuocChon.gameName, "Bạn muốn làm gì", 1, new String[]{"Kết bạn","Giao dịch","Thách đấu"});
            }
        }

        batch.end();
        if (timeGlow>0) {
            veGlow.veGlow(shapeRenderer,clickX,clickY,timeGlow);
        }
        batch.begin();
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
            if (duLieuNguoiChoi.coDeTu()) {
                duLieuNguoiChoi.deTu.tangHpHienTai(duLieuNguoiChoi.getDauHoiHPKI());
                duLieuNguoiChoi.deTu.tangKiHienTai(duLieuNguoiChoi.getDauHoiHPKI());
                if (!duLieuNguoiChoi.deTu.getTrangthai().equals("Về nhà")) {
                    duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Cám ơn sư phụ", 1f);
                }
                duLieuNguoiChoi.deTu.tangTheLuc(30);
            }
            dauThanRenderH = 0f;
        }
    }
    public void chonSkill(int index) {
        if (index >= 0 && index < 5 && (!dangHienKhungChat && !(timeHienRongThan<=300-2.1f && timeHienRongThan>0))) {
            skillDangChon = index;
        }
    }
    public void dungSkill(int index) {
        if (index >= 0 && index < 5 && (!dangHienKhungChat && !(timeHienRongThan<=300-2.1f && timeHienRongThan>0))) {
            if (oSkills[index] == 3 && timeCoolDownBienKhi == 0 && duLieuNguoiChoi.getKiHienTai()>=duLieuNguoiChoi.getKiHopThe()*0.1f && !dangBienKhi && timeChoBienKhi==0) {
                timeChoBienKhi = 2f;
                duLieuNguoiChoi.giamKiHienTai(duLieuNguoiChoi.getKiHopThe()*0.1f);
                try {
                    GameSocket.useSkill(nhanVat.getTenSkill(oSkills[index] + 1, "xayda"), (int) (timeChoBienKhi + timeBienKhiMAX));
                } catch (Exception e) {

                }
            }
            if (oSkills[index] == 2 && timeCoolDownTtnl == 0) {
                timeTtnl = timeTtnlMax;
                timeCoolDownTtnl = 20f;
                dangTtnl = true;
                hpHoiTtnl = 3+1*duLieuNguoiChoi.getCapSkill(2);
                KiHoiTtnl = 3+1*duLieuNguoiChoi.getCapSkill(2);
                try {
                    GameSocket.useSkill(nhanVat.getTenSkill(oSkills[index] + 1, "xayda"), (int)timeTtnlMax);
                } catch (Exception e) {

                }
            }
            if (oSkills[index] == 5 && timeCoolDownHuytSao == 0) {
                timeHuytSao = 10+5*duLieuNguoiChoi.getCapSkill(5);
                timeCoolDownHuytSao = 180f;
                dangHuytSao = true;
                nhanVat.chuaSetTimeHuytSao = true;
                hpTangHuytSao = (30+10*duLieuNguoiChoi.getCapSkill(5));
                try {
                    GameSocket.useSkill(nhanVat.getTenSkill(oSkills[index] + 1, "xayda"), (int)timeHuytSao);
                } catch (Exception e) {

                }
            }
        }
    }
    public void update(float delta) {
        //Gd 2 nguoi choi
        if (timeChapNhanGiaoDich > 0f) {
            timeChapNhanGiaoDich -= delta;
            if (timeChapNhanGiaoDich <= 0) {
                timeChapNhanGiaoDich = 0;
                dangCoYeuCauGiaoDich = false;
            }
        }

        //nut X tat popup NPC
        capNhatTimeChoTatPopupNpc(delta);
        //fps
        if (dangBatFPS) {
            timeCapNhatFPS += delta;
            if (timeCapNhatFPS >= 1) {
                timeCapNhatFPS = 0;
                fps = Gdx.graphics.getFramesPerSecond();
            }
        }

        thoiGianItemPhuTro.capNhatThoiGianItem(delta);

        HUDRongThan.capNhatThoiGian(delta);

        thoiGianMiniGame.capNhatThoiGian(delta);

        veGlow.capNhatThoiGian(delta);

        if (timeDoTre >0) {
            timeDoTre -= delta;
            if (timeDoTre <=0){
                if (trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.THONG_BAO) {
                    switch (oChiSoDangChon) {
                        case 0 -> trangThaiChucNangHUDChucNangThongBao = TrangThaiChucNangHUD_ChucNang_ThongBao.LIEN_HE_ADMIN;
                        case 1 -> trangThaiChucNangHUDChucNangThongBao = TrangThaiChucNangHUD_ChucNang_ThongBao.THONG_BAO_CAP_NHAT;
                        case 2 -> trangThaiChucNangHUDChucNangThongBao = TrangThaiChucNangHUD_ChucNang_ThongBao.THONG_BAO_X2X3;
                        case 3 -> trangThaiChucNangHUDChucNangThongBao = TrangThaiChucNangHUD_ChucNang_ThongBao.GIFTCODE_TAN_THU;
                        case 4 -> trangThaiChucNangHUDChucNangThongBao = TrangThaiChucNangHUD_ChucNang_ThongBao.EVENT_SAP_TOI;
                    }
                } else if (trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) {
                    if (oChiSoDangChon!=4) {
                        switch (oChiSoDangChon) {
                            case 0 -> trangthaide = "Đi theo";
                            case 1 -> trangthaide = "Bảo vệ";
                            case 2 -> trangthaide = "Tấn công";
                            case 3 -> trangthaide = "Về nhà";
                        }
                        duLieuNguoiChoi.deTu.capNhatTrangThaiDeTu();
                    }
                    if (oChiSoDangChon == 4 && !dangHopTheThuong && delayHopTheThuong == 0 && !dangHopThe) {
                        timeChoHopThe = 2f;
                        dangHienPopup = false;
                        scrollYPhai = 0;
                        hangTrangDangChon = -1;
                        dangHopTheThuong = true;
                    } else if (dangHopThe) {
                        setTinNhanPet("Bạn đang hợp thể rồi",2);
                    } else if (delayHopTheThuong>0) {
                        setTinNhanPet("Cần chờ "+(int)(delayHopTheThuong/60f)+" phút nữa để hợp thể Fushion Dance",2);
                    }
                } else if (trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.NHAC_NEN) {
                    String[] chucNang = {"Tắt nhạc","Khẩu thị tâm phi","Đếm ngày xa em","Kẻ theo đuổi ánh sáng","Tháp rơi tự do","Điều anh biết","DanDan Kokoro Hikareteku","Sao mình chưa nắm tay nhau","Thời gian sẽ trả lời","Sự thật đã bỏ quên", "Không lấy được vợ","Seasons","Vở kịch của em"};
                    if (oChiSoDangChon == 0) {
                        for (int i = 1; i < nhacNen.length; i++) {
                            if (nhacNen[i].isPlaying()) nhacNen[i].stop();
                            setTinNhanPet("Bạn vừa tắt nhạc",2f);
                        }
                    } else if (oChiSoDangChon >= 1 && oChiSoDangChon < nhacNen.length) {
                        // Tắt nhạc cũ nếu có
                        for (int i = 1; i < nhacNen.length; i++) {
                            if (nhacNen[i].isPlaying()) nhacNen[i].stop();
                        }
                        // Phát nhạc mới
                        nhacNen[oChiSoDangChon].play();
                        setTinNhanPet("Đang phát bài "+chucNang[oChiSoDangChon],2f);
                    }
                    trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
                } else if (trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.TAI_KHOAN) {
                    String thongBao = "";
                    switch (oChiSoDangChon) {
                        case 0:
                            dangBatFPS = !dangBatFPS;
                            if (dangBatFPS) thongBao = "Bạn vừa bật FPS";
                            else thongBao = "Bạn vừa tắt FPS";
                            setTinNhanPet(thongBao,2f);
                            break;
                        case 1:
                            dangHienCoolDownSkill = !dangHienCoolDownSkill;
                            if (dangHienCoolDownSkill) thongBao = "Bạn vừa bật Cooldown Skill";
                            else thongBao = "Bạn vừa tắt Cooldown Skill";
                            setTinNhanPet(thongBao,2f);
                            break;
                        case 2:
                            dangHienMapHienTai = !dangHienMapHienTai;
                            if (dangHienMapHienTai) thongBao = "Đang hiện Map";
                            else thongBao = "Đã ẩn Map";
                            setTinNhanPet(thongBao,2f);
                            break;
                        case 3:
                            dangHienNgayGioHienTai = !dangHienNgayGioHienTai;
                            if (dangHienNgayGioHienTai) thongBao = "Đã hiện ngày giờ";
                            else thongBao = "Đã ẩn ngày giờ";
                            setTinNhanPet(thongBao,2f);
                            break;
                        case 4:
                            dangHienChiSo = !dangHienChiSo;
                            if (dangHienChiSo) thongBao = "Đang hiện chỉ số HP/KI";
                            else thongBao = "Đã ẩn chỉ số";
                            setTinNhanPet(thongBao,2f);
                            break;
                    }
                } else {
                    if (duLieuNguoiChoi.coDeTu()) {
                        if (oChiSoDangChon == 0) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.GIOI_THIEU_GAME;
                        } else if (oChiSoDangChon == 1) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.MINIGAME;
                        } else if (oChiSoDangChon == 2) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.THONG_BAO;
                        } else if (oChiSoDangChon == 3) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.DE_TU;
                            scrollYTrai = 0;
                            hangTrangDangChon = -1;
                            hangTrangDeTuDangChon = -1;
                        } else if (oChiSoDangChon == 7) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NHAC_NEN;
                        } else if (oChiSoDangChon == 8) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.TAI_KHOAN;
                        }
                    } else {
                        if (oChiSoDangChon == 0) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.GIOI_THIEU_GAME;
                        } else if (oChiSoDangChon == 1) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.MINIGAME;
                        } else if (oChiSoDangChon == 2) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.THONG_BAO;
                        } else if (oChiSoDangChon == 6) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NHAC_NEN;
                        } else if (oChiSoDangChon == 7) {
                            trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.TAI_KHOAN;
                        }
                    }
                }
                oChiSoDangChon = -1;
                scrollYPhai = 0;
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
                    setTinNhanPet("Cần thêm "+formatVangNgoc(chiPhiTamThoi-duLieuNguoiChoi.getTiemNangNhanVat())+" tiềm năng nữa",2f);
                } else {
                    setTinNhanPet("Bạn đã đạt giới hạn "+chiSoCong[oChiSoDangChon],2f);
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
                if (HienPopUpGanSkill) {
                    // Xóa các ô đang chứa cùng icon
                    for (int i = 0; i < 5; i++) {
                        if (oSkills[i] == oChiSoDangChon - 6) {
                            oSkills[i] = -1;
                        }
                    }
                    // Gán vào ô được chọn
                    oSkills[nutduocchon] = oChiSoDangChon - 6;
                    HienPopUpGanSkill = false;
                } else if (dangHienDieuUocRongThan) {
                    if (!daUocRongThan) {
                        themItemTest.themQuaTuDieuUocRongThan();
                        daUocRongThan = true;
                    }
                } else if (dangHienDauThan) {
                    if (nutduocchon == 0) {
                        vuaClickThuHoachDau = true;
                    } else {
                        vuaClickNangCapDau = true;
                    }
                }
            }
        }
        if (nutClickTimer3 > 0){
            nutClickTimer3 -= Gdx.graphics.getDeltaTime();
            if (nutClickTimer3 <= 0) {
                if (dangHienPopup || dangHienRuongDo) {
                    if (trangThaiChucNangHUD == TrangThaiChucNangHUD.HANH_TRANG || trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU || dangHienRuongDo || dangHienPopupNhanVatPhai) {
                        // mac do
                        if (nuthanhtrangchon == 1) {
                            // MẶC ITEM
                            xuLyDungItem();
                        } else if (nuthanhtrangchon == 2) {
                            dangHienThongBao = true;
                            DangHienPopupThongTin1 = false;
                            TimeChoHienPopup = 0;
                        } else if (nuthanhtrangchon == 3) {
                            if (!dangHienRuongDo) {
                                if (duLieuNguoiChoi.deTu.getSucManh() >= 1_500_000) {
                                    if (duLieuNguoiChoi.deTu.getSucManh() >= itemm.getSucManhYeuCau()) {
                                        boolean duDieuKien = false;
                                        if (duLieuNguoiChoi.deTu.getHanhtinh().equals(itemm.getHanhtinh()) || itemm.getHanhtinh().equals("all")) {
                                            duDieuKien = true;
                                        }
                                        if (duDieuKien) {
                                            xulyitem.macDoChoDe(hangTrangDangChon);
                                            if (!(trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU)) {
                                                trangThaiChucNangHUD = TrangThaiChucNangHUD.CHUC_NANG;
                                                trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.DE_TU;
                                                scrollYPhai = 0;
                                                scrollYTrai = 0;
                                            }
                                            if (!duLieuNguoiChoi.deTu.getTrangthai().equals("Về nhà")) {
                                                duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Cám ơn sư phụ", 3f);
                                            }
                                        } else {
                                            if (!duLieuNguoiChoi.deTu.getHanhtinh().equals(itemm.getHanhtinh())) {
                                                String ht;
                                                switch (itemm.getHanhtinh()) {
                                                    case "traidat":
                                                        ht = "Trái Đất";
                                                        break;
                                                    case "xayda":
                                                        ht = "Sayda";
                                                        break;
                                                    case "namek":
                                                        ht = "Namếc";
                                                        break;
                                                    default:
                                                        ht = "";
                                                }
                                                setTinNhanPet("Đệ tử không thể mặc đồ " + ht, 2f);
                                            }
                                        }
                                    } else {
                                        setTinNhanPet("Đệ tử cần thêm " + formatVangNgoc(itemm.getSucManhYeuCau() - duLieuNguoiChoi.deTu.getSucManh()) + " sức mạnh nữa", 2f);
                                    }
                                } else {
                                    setTinNhanPet("Đệ tử cần lên 1tr5 sức mạnh", 2f);
                                }
                                hangTrangDangChon = -1;
                                hangTrangDeTuDangChon = -1;
                                DangHienPopupThongTin1 = false;
                                TimeChoHienPopup = 0;
                            } else if (dangHienRuongDo) {
                                if (duLieuNguoiChoi.getHanhTrangRuongDo().size() < duLieuNguoiChoi.MAXRUONGDO) {
                                    duLieuNguoiChoi.getHanhTrang().remove(itemm);
                                }
                                duLieuNguoiChoi.themItemVaoHanhTrangRuongDo(itemm);
                                hangTrangDangChon = -1;
                                hanhTrangRuongDoDangChon = -1;
                                DangHienPopupThongTin1 = false;
                                TimeChoHienPopup = 0;
                            }
                        } else if (nuthanhtrangchon == 4) {
                            if (trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) {
                                xulyitem.goDoChoDe(hangTrangDeTuDangChon);
                                DangHienPopupThongTin2 = false;
                                TimeChoHienPopup = 0;
                            } else if (dangHienRuongDo) {
                                if (duLieuNguoiChoi.getHanhTrang().size() < 50) {
                                    duLieuNguoiChoi.getHanhTrangRuongDo().remove(itemm);
                                }
                                duLieuNguoiChoi.themItemVaoHanhTrang(itemm);
                                DangHienPopupThongTin3 = false;
                                TimeChoHienPopup = 0;
                            }
                        } else if (nuthanhtrangchon == 5) {
                            if (trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) {
                                DangHienPopupThongTin2 = false;
                                TimeChoHienPopup = 0;
                            } else if (dangHienRuongDo) {
                                DangHienPopupThongTin3 = false;
                                TimeChoHienPopup = 0;
                            }
                        }
                    } else if (trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM) {
                        if (nuthanhtrangchon == 2) {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE;
                        } else if (nuthanhtrangchon == 1) {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.HUONG_DAN_THEM_CSMM;
                        } else {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CSMM;
                        }
                    } else if (trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.HUONG_DAN_THEM_CSMM) {
                        if (nuthanhtrangchon == 1) {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM;
                        }
                    } else if (trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE) {
                        if (nuthanhtrangchon == 2) {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE;
                        } else if (nuthanhtrangchon == 1) {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.HUONG_DAN_THEM_CHAN_LE;
                        } else {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CHAN_LE;
                        }
                    } else if (trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.HUONG_DAN_THEM_CHAN_LE) {
                        if (nuthanhtrangchon == 1) {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE;
                        }
                    } else if (trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.NONE) {
                        if (nuthanhtrangchon == 0) {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM;
                        } else if (nuthanhtrangchon == 1) {
                            trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE;
                        }
                    }
                } else if (dangGiaoDich) {
                    if (trangThaiHanhTrangGd == TrangThaiHanhTrangGd.HANH_TRANG && dangChonHanhTrangTrai) {
                        boolean duDieuKien = hangTrangDangChon >= 8;

                        if (duDieuKien) {
                            switch ((int) nuthanhtrangchon) {
                                case 1 -> {
                                    // Call event websocket push item vào
                                    try {
                                        GameSocket.tradeOfferAdd(playerGiaoDich.userId, itemm.uuid);
                                    } catch(Exception e) {

                                    }
                                    this.duLieuNguoiChoi.getHanhTrang().remove(itemm);
                                    this.duLieuNguoiChoi.hanhTrangGiaoDich.add(itemm);
                                    hangTrangDangChon = -1;
                                    DangHienPopupThongTin1 = false;
                                    TimeChoHienPopup = 0;
                                }
                                case 2 -> {
                                    hangTrangDangChon = -1;
                                    DangHienPopupThongTin1 = false;
                                    TimeChoHienPopup = 0;
                                }
                            }
                        } else {
                            switch ((int) nuthanhtrangchon) {
                                case 1 -> {
                                    hangTrangDangChon = -1;
                                    DangHienPopupThongTin1 = false;
                                    TimeChoHienPopup = 0;
                                }
                            }
                        }
                    }

                    if (trangThaiHanhTrangGd == TrangThaiHanhTrangGd.ITEM_CHO && dangChonHanhTrangTrai) {
                        switch ((int) nuthanhtrangchon) {
                            case 1 -> {
                                try {
                                    GameSocket.tradeOfferRemove(playerGiaoDich.userId, itemm.uuid);
                                } catch(Exception e) {

                                }
                                this.duLieuNguoiChoi.getHanhTrang().add(itemm);
                                this.duLieuNguoiChoi.hanhTrangGiaoDich.remove(itemm);
                                hangTrangDangChon = -1;
                                DangHienPopupThongTin3 = false;
                                TimeChoHienPopup = 0;
                            }
                        }
                    }

                    if (dangChonHanhTrangPhai) {
                        switch ((int) nuthanhtrangchon) {
                            case 1 -> {
                                hangTrangDangChon = -1;
                                DangHienPopupThongTin3 = false;
                                TimeChoHienPopup = 0;
                            }
                        }
                    }
                }
            }
        }

        if (timeChoBienKhi > 0) {
            timeChoBienKhi -= delta;
            if (timeChoBienKhi <= 0) {
                timeChoBienKhi = 0;
                dangBienKhi = true;
                vuaBienKhi = true;
                timeBienKhi = timeBienKhiMAX;
                timeCoolDownBienKhi = (500-20*duLieuNguoiChoi.getCapSkill(3));
                sucDanhTangBienKhi = 30+10*duLieuNguoiChoi.getCapSkill(3);
                hpTangBienKhi = 30+10*duLieuNguoiChoi.getCapSkill(3);
                NhanVatCauHinh c2 = Doicaitrang("khi_"+duLieuNguoiChoi.getCapSkill(3));
                nhanVat.fixCaiTrang(
                    c2.dau_dung, c2.dau_chay,
                    c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                    c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                    c2.than_bay, c2.chan_bay,
                    c2.chan_gong,c2.than_thu,
                    c2.lechMap,
                    c2.avt
                );
                texAvt = new Texture(nhanVat.doiavatar());
            }
        }

        if (timeBienKhi > 0) {
            timeBienKhi -= delta;
            if (timeBienKhi <= 0) {
                huyBienKhi();
            }
        }

        if (timeCoolDownBienKhi>0) {
            timeCoolDownBienKhi -= delta;
            if (timeCoolDownBienKhi <= 0) {
                timeCoolDownBienKhi = 0;
            }
        }

        if (timeSauBienKhi>0) {
            timeSauBienKhi -= delta;
            if (timeSauBienKhi <= 0) {
                timeSauBienKhi = 0;
            }
        }

        if (timeTtnl>0) {
            timeTtnl -= delta;
            if (timeTtnl <= 0) {
                if (dangTtnl) huyTtnl();
            }
        }

        if (timeCoolDownTtnl>0) {
            timeCoolDownTtnl -= delta;
            if (timeCoolDownTtnl <= 0) {
                timeCoolDownTtnl = 0;
            }
        }

        if (timeHuytSao>0) {
            timeHuytSao -= delta;
            if (timeHuytSao <= 0) {
                huyHuytSao();
            }
        }

        if (timeCoolDownHuytSao>0) {
            timeCoolDownHuytSao -= delta;
            if (timeCoolDownHuytSao <= 0) {
                timeCoolDownHuytSao = 0;
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
                    vuaHopThe = true;
                    if (!dangBienKhi) {
                        String hopTheDuocChon = dangHopTheThuong ? "hop_the_thuong_" + nhanVat.getHanhtinh() : (bongTaiDangDung.equals("bongtaic1") ? "bong_tai_1_" + nhanVat.getHanhtinh() : bongTaiDangDung.equals("bongtaic2") ? "bong_tai_2_" + nhanVat.getHanhtinh() : "bong_tai_3_" + nhanVat.getHanhtinh());
                        NhanVatCauHinh c2 = Doicaitrang(hopTheDuocChon);
                        nhanVat.fixCaiTrang(
                            c2.dau_dung, c2.dau_chay,
                            c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                            c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                            c2.than_bay, c2.chan_bay,
                            c2.chan_gong,c2.than_thu,
                            c2.lechMap,
                            c2.avt
                        );
                        texAvt = new Texture(nhanVat.doiavatar());
                    }
                    if (dangHopTheThuong) {
                        timeHopTheTHuong = 600f;
                        trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
                        trangThaiChucNangHUD = TrangThaiChucNangHUD.HANH_TRANG;
                    }
                } else {
                    dangHopThe = false;
                    trangthaide = "Bảo vệ";
                    duLieuNguoiChoi.deTu.capNhatTrangThaiDeTu();
                    delayHopTheBongTai = 10f;
                    delayHopTheThuong = 600f;
                    if (!dangBienKhi) {
                        ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrangDangMac();
                        if (NhanVatXuLy.getDangMacCaiTrang() && !NhanVatXuLy.getDangMacAvatar() && danhSach.get(5) != null) {
                            NhanVatCauHinh c2 = Doicaitrang(danhSach.get(5).getId());
                            nhanVat.fixCaiTrang(
                                c2.dau_dung, c2.dau_chay,
                                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                                c2.than_bay, c2.chan_bay,
                                c2.chan_gong,c2.than_thu,
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
                                c2.chan_gong,c2.than_thu,
                                c2.lechMap,
                                c2.avt
                            );
                            texAvt = new Texture(nhanVat.doiavatar());
                        }
                    }
                }
            }
        }

        tinhToanChiSo.capNhatChiSo(delta);

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
                duLieuNguoiChoi.deTu.capNhatTrangThaiDeTu();
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
                        c2.chan_gong,c2.than_thu,
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
                        c2.chan_gong,c2.than_thu,
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
                            //xét trước khi xóa
                            if (dangDungAura && auraDangDung == danhSach.get(hangTrangDangChon-8)) {
                                dangDungAura = false;
                                timeChoBuffAuraTieuDoiTruong = 0f;
                                chuaSetUpAnhAura = true;
                            }
                            if (dangDungHuyHieu && huyHieuDangDung == danhSach.get(hangTrangDangChon-8)) {
                                dangDungHuyHieu = false;
                                chuaSetUpAnhHuyHieu = true;
                            }
                            if (dangDungDeoLung && deoLungDangDung == danhSach.get(hangTrangDangChon-8)) {
                                dangDungDeoLung = false;
                                chuaSetUpAnhDeoLung = true;
                            }
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
                        if (daClickVaoNpc && npcHienTai.npcHUDrender.ui_npc instanceof admin_thanhle) {
                            admin_thanhle ui = (admin_thanhle) npcHienTai.npcHUDrender.ui_npc;
                            ui.banItemHanhTrang();
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

                            try {
                                GameSocket.guiChat(tinNhanChat);
                            } catch (Exception e) {

                            }
                        }
                    }
                } else if (trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.GIOI_THIEU_GAME) {
                    if (chuaNhanQuaLanDau) {
                        duLieuNguoiChoi.tangNgoc(1_000_000);
                        duLieuNguoiChoi.tangVang(10_000_000_000L);
                        duLieuNguoiChoi.tangSucManh(10_000_000_000L);
                        if (duLieuNguoiChoi.coDeTu()) {
                            duLieuNguoiChoi.deTu.tangSucManh(50_000_000_000L);
                        }
                        setTinNhanPet("Bạn vừa nhận quà VIP từ admin HAIDANG",2f);
                        chuaNhanQuaLanDau = false;
                    } else {
                        setTinNhanPet("Bạn đã nhận quà này rồi",2f);
                    }
                    nutduocchon = -1;
                } else if (trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CSMM) {
                    if (nutduocchon == 2){
                        trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM;
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
                                    trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM;
                                    soNgocNguoiChoiNhap = "";
                                    nutduocchon = -1;
                                } catch (NumberFormatException e) {
                                    trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM;
                                    soNgocNguoiChoiNhap = "";
                                    nutduocchon = -1;
                                }
                            } else {
                                trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM;
                                soNgocNguoiChoiNhap = "";
                                nutduocchon = -1;
                            }
                        }
                    }
                } else if (trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CHAN_LE) {
                    if (nutduocchon == 2){
                        trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE;
                        nutduocchon = -1;
                        soVangNguoiChoiNhapChanLe = "";
                    } else if (nutduocchon == 1) {
                        if (!soVangNguoiChoiNhapChanLe.isEmpty()){
                            if (soVangNguoiChoiNhapChanLe.contains("/") && soVangNguoiChoiNhapChanLe.split("/").length == 2) {
                                try {
                                    String[] parts = soVangNguoiChoiNhapChanLe.split("/");
                                    long soVang = Long.parseLong(parts[0].trim());
                                    String duDoan = parts[1].trim();
                                    if (soVang >= 1 && (duDoan.equals("chan") || duDoan.equals("le")) && soVangCuocChanLe == 0 && "".equals(NguoiChoiChonChanLe) && duLieuNguoiChoi.getVang()>=soVang) {
                                        soVangCuocChanLe = soVang;
                                        duLieuNguoiChoi.giamVang(soVang);
                                        NguoiChoiChonChanLe = duDoan;
                                    }
                                    trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE;
                                    soVangNguoiChoiNhapChanLe = "";
                                    nutduocchon = -1;
                                } catch (NumberFormatException e) {
                                    trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE;
                                    soVangNguoiChoiNhapChanLe = "";
                                    nutduocchon = -1;
                                }
                            } else {
                                trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE;
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
        if (DangHienPopupThongTin || DangHienPopupThongTin1 || DangHienPopupThongTin3){
            TimeChoHienPopup-=Gdx.graphics.getDeltaTime();
        }
        if (DangHienPopupThongTin2){
            TimeChoHienPopup1-=Gdx.graphics.getDeltaTime();
        }
        if (trangThaiChucNangHUD != TrangThaiChucNangHUD.KY_NANG){
            DangHienPopupThongTin = false;
        }
        if (dauThanRenderH < 53f){
            dauThanRenderH+=10.6f*Gdx.graphics.getDeltaTime();
            dauThanRenderH = Math.min(dauThanRenderH, 53f);
        }
    }
    public void xuLyClick(int x, int y, float worldX, float worldY) {
        clickHandler.xuLyClick(x, y, worldX, worldY);
    }

    public void hienPopupNhanVat() {
        dangHienPopup = true;
//        vuaMoPopup = true;
    }

    public void tatPopupNhanVat() {
        dangHienPopup = false;
        vuaTatPopup = true;
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

    void PopupThongTin(ShapeRenderer shapeRenderer, SpriteBatch batch, float x, float y , float width, float height , int oChiSoDangChon) {
        popupThongTin.PopupThongTin(shapeRenderer,batch,x,y,width,height,oChiSoDangChon);
    }
    public void PopupHanhTrang(ShapeRenderer shapeRenderer,SpriteBatch batch, int oHanhTrangDangChon,boolean benPhai) {
        popupHanhTrang.PopupHanhTrang(shapeRenderer,batch,oHanhTrangDangChon,benPhai);
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

    public void setTinNhanPet(String tinNhan,float timeTinNhan) {
        tinNhanPet = tinNhan;
        dangHienTinNhanPet = true;
        timeHienTinNhanPet = timeTinNhan;
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

    public void xuLyDungItem() {
        if ((nhanVat.getHanhtinh().equals(itemm.getHanhtinh()) || itemm.getHanhtinh().equals("all")) && duLieuNguoiChoi.getSucManh() >= itemm.getSucManhYeuCau()) {
            if (itemm.getLoai() != LoaiItem.BONGTAI &&
                itemm.getLoai() != LoaiItem.NGOCRONG &&
                itemm.getLoai() != LoaiItem.HUYHIEU &&
                itemm.getLoai() != LoaiItem.HOPQUA &&
                itemm.getLoai() != LoaiItem.PHUTRO &&
                itemm.getLoai() != LoaiItem.DEOLUNG) {
                xulyitem.macDo(hangTrangDangChon);
            }
            if (itemm.getLoai() == LoaiItem.BONGTAI) {
                if (timeHopTheTHuong == 0 && delayHopTheBongTai == 0 && duLieuNguoiChoi.coDeTu()) {
                    timeChoHopThe = 1.5f;
                    dangHienPopup = false;
                    scrollYPhai = 0;
                    hangTrangDangChon = -1;
                    dangHopTheThuong = false;
                    if (!dangHopThe) {
                        bongTaiDangDung = itemm.getId();
                        if (itemm.getMoTa().contains("Rồng Thần")) {
                            bongTaiRongThan = true;
                        } else {
                            bongTaiRongThan = false;
                        }
                    }
                } else {
                    if (delayHopTheBongTai > 0) {
                        setTinNhanPet("Vui lòng đợi " + (int) delayHopTheBongTai + " giây nữa",2f);
                    }
                    if (timeHopTheTHuong > 0) {
                        setTinNhanPet("Vui lòng chờ tách hợp thể Fusion dance",2f);
                    }
                    if (!duLieuNguoiChoi.coDeTu()) {
                        setTinNhanPet("Bạn chưa có đệ tử",2f);
                    }
                }
            }
            if (itemm.getLoai() == LoaiItem.NGOCRONG) {
                if (timeDelayUocRong == 0 || duLieuNguoiChoi.getTen().equals("admin")) {
                    if (!itemm.getTenItem().contains("Ngọc rồng đen")) {
                        if (itemm.getId().equals("nr3s") || itemm.getId().equals("nr2s") || itemm.getId().equals("nr1s")) {
                            String[] idsCanTim = new String[0];
                            if (itemm.getId().equals("nr3s")) {
                                idsCanTim = new String[]{"nr3s", "nr4s", "nr5s", "nr6s", "nr7s"};
                                ngocRongUoc = "3sao";
                                tinNhanPet = "Bạn vừa gọi rồng thần shenron 3 sao";
                            } else if (itemm.getId().equals("nr2s")) {
                                idsCanTim = new String[]{"nr2s", "nr3s", "nr4s", "nr5s", "nr6s", "nr7s"};
                                ngocRongUoc = "2sao";
                                tinNhanPet = "Bạn vừa gọi rồng thần shenron 2 sao";
                            } else if (itemm.getId().equals("nr1s")) {
                                idsCanTim = new String[]{"nr1s", "nr2s", "nr3s", "nr4s", "nr5s", "nr6s", "nr7s"};
                                ngocRongUoc = "1sao";
                                tinNhanPet = "Bạn vừa gọi rồng thần shenron 1 sao";
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
                                setTinNhanPet("Không đủ ngọc rồng",2f);
                            }
                        } else {
                            setTinNhanPet("Chỉ được gọi rồng bằng ngọc rồng 1, 2, 3 sao",2f);
                        }
                    } else {
                        if (itemm.getId().equals("nr1sd")) {
                            String[] idsCanTim = new String[0];
                            idsCanTim = new String[]{"nr1sd", "nr2sd", "nr3sd", "nr4sd", "nr5sd", "nr6sd", "nr7sd"};
                            ngocRongUoc = "1saoden";
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
                                setTinNhanPet("Bạn vừa gọi rồng thần bóng tối",2f);
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
                                setTinNhanPet("Không đủ ngọc rồng",2f);
                            }
                        } else {
                            setTinNhanPet("Chỉ được gọi rồng bóng tối bằng ngọc rồng 1 sao đen",2f);
                        }
                    }
                } else {
                    setTinNhanPet("Ngọc rồng cần khôi phục trong "+(int)(timeDelayUocRong/60f)+" phút nữa",2f);
                }
            }
            if (itemm.getLoai() == LoaiItem.HUYHIEU) {
                if (dangDungHuyHieu) {
                    dangDungHuyHieu = false;
                    chuaSetUpAnhHuyHieu = true;
                } else {
                    dangDungHuyHieu = true;
                    huyHieuDangDung = itemm;
                }
            }
            if (itemm.getLoai() == LoaiItem.HOPQUA) {
                if (itemm.getId().equals("adminHD")) {
                    themItemTest.themQuaAdHaiDang();
                    setTinNhanPet("Bạn vừa nhận 01 set kích hoạt",2f);
                    duLieuNguoiChoi.getHanhTrang().remove(itemm);
                }
                if (itemm.getId().equals("adminTL")) {
                    themItemTest.themQuaAdThanhLe();
                    setTinNhanPet("Bạn vừa nhận x99 bộ Ngọc Rồng",2f);
                    duLieuNguoiChoi.getHanhTrang().remove(itemm);
                }
                if (itemm.getId().equals("adminDL")) {
                    duLieuNguoiChoi.tangNgoc(1_000_000);
                    duLieuNguoiChoi.tangVang(1_000_000_000);
                    setTinNhanPet("Bạn vừa nhận 1 tỷ vàng và 1 triệu ngọc xanh",2f);
                    duLieuNguoiChoi.getHanhTrang().remove(itemm);
                }
            }
            if (itemm.getLoai() == LoaiItem.PHUTRO) {
                if (itemm.getId().equals("bo_huyet")) {
                    dangDungBoHuyet = true;
                    timeDungBoHuyet = 600f;
                    itemm.giamSoLuong(1);
                }
                if (itemm.getId().equals("bo_khi")) {
                    dangDungBoKhi = true;
                    timeDungBoKhi = 600f;
                    itemm.giamSoLuong(1);
                }
                if (itemm.getId().equals("cuong_no")) {
                    dangDungCuongNo = true;
                    timeDungCuongNo = 600f;
                    itemm.giamSoLuong(1);
                }
                if (itemm.getId().equals("giap_xen")) {
                    dangDungGiapXen = true;
                    timeDungGiapXen = 600f;
                    itemm.giamSoLuong(1);
                }
                if (itemm.getId().equals("dame")) {
                    dangDungCuongCong = true;
                    timeDungCuongCong = 120f;
                    itemm.giamSoLuong(1);
                }
                if (itemm.getId().equals("hp")) {
                    dangDungSinhLuc = true;
                    timeDungSinhLuc = 120f;
                    itemm.giamSoLuong(1);
                }
                if (itemm.getId().equals("ki")) {
                    dangDungLinhKhi = true;
                    timeDungLinhKhi = 120f;
                    itemm.giamSoLuong(1);
                }
                // item khác ngoài tăng chỉ số
                if (itemm.getId().equals("trung_de_tu")) {
                    if (!duLieuNguoiChoi.coDeTu()) {
                        duLieuNguoiChoi.taoDeTu("Đệ tử", true);
                        duLieuNguoiChoi.deTu.setVeHUD(this);
                        itemm.giamSoLuong(1);
                    }
                }
                if (itemm.getSoLuong() == 0) {
                    duLieuNguoiChoi.getHanhTrang().remove(itemm);
                }
            }
            if (itemm.getLoai() == LoaiItem.DEOLUNG) {
                if (dangDungDeoLung) {
                    dangDungDeoLung = false;
                    framesDeoLung = 0;
                    chuaSetUpAnhDeoLung = true;
                } else {
                    dangDungDeoLung = true;
                    deoLungDangDung = itemm;
                }
            }
            if (itemm.getLoai() == LoaiItem.AURA) {
                if (dangDungAura) {
                    dangDungAura = false;
                    timeChoBuffAuraTieuDoiTruong = 0f;
                    chuaSetUpAnhAura = true;
                } else {
                    dangDungAura = true;
                    auraDangDung = itemm;
                }
            }
            if (itemm.getLoai() == LoaiItem.NANGSKILL) {
                switch (itemm.getId()) {
                    case "khi":
                        if (duLieuNguoiChoi.getCapSkill(3) < 7) {
                            duLieuNguoiChoi.tangCapSkill(3);
                            duLieuNguoiChoi.capNhatMotaSkill(3);
                            itemm.giamSoLuong(1);
                            if (itemm.getSoLuong() == 0) {
                                duLieuNguoiChoi.getHanhTrang().remove(itemm);
                            }
                            huyBienKhi();
                            timeCoolDownBienKhi = 0;
                            setTinNhanPet("Kỹ năng Biến Hình đạt cấp " + duLieuNguoiChoi.getCapSkill(3), 2f);
                        } else {
                            setTinNhanPet("Bạn cần ước rồng thần để tiếp tục nâng kỹ năng", 2f);
                        }
                        break;
                    case "huytsao":
                        if (duLieuNguoiChoi.getCapSkill(5) < 7) {
                            duLieuNguoiChoi.tangCapSkill(5);
                            duLieuNguoiChoi.capNhatMotaSkill(5);
                            itemm.giamSoLuong(1);
                            if (itemm.getSoLuong() == 0) {
                                duLieuNguoiChoi.getHanhTrang().remove(itemm);
                            }
                            huyHuytSao();
                            timeCoolDownHuytSao = 0;
                            setTinNhanPet("Kỹ năng Huýt Sáo đạt cấp " + duLieuNguoiChoi.getCapSkill(5), 2f);
                        } else {
                            setTinNhanPet("Kỹ năng đã đạt cấp tối đa", 2f);
                        }
                        break;
                    case "ttnl":
                        if (duLieuNguoiChoi.getCapSkill(2) < 7) {
                            duLieuNguoiChoi.tangCapSkill(2);
                            duLieuNguoiChoi.capNhatMotaSkill(2);
                            itemm.giamSoLuong(1);
                            if (itemm.getSoLuong() == 0) {
                                duLieuNguoiChoi.getHanhTrang().remove(itemm);
                            }
                            if (dangTtnl) huyTtnl();
                            timeCoolDownTtnl = 0;
                            setTinNhanPet("Tái tạo năng lượng đạt cấp " + duLieuNguoiChoi.getCapSkill(2), 2f);
                        } else {
                            setTinNhanPet("Kỹ năng đã đạt cấp tối đa", 2f);
                        }
                        break;
                    case "skill1_de":
                    case "skill2_de":
                    case "skill3_de":
                    case "skill4_de":
                        if (duLieuNguoiChoi.coDeTu()) {
                            int skillIndex = Integer.parseInt(itemm.getId().substring(5, 6)) - 1;
                            long[] sucManhYeuCau = {0, 150_000_000L, 1_500_000_000, 20_000_000_000L};
                            if (duLieuNguoiChoi.deTu.getCapSkill(skillIndex) < 7 && duLieuNguoiChoi.deTu.getSucManh() >= sucManhYeuCau[skillIndex]) {
                                duLieuNguoiChoi.deTu.tangCapSkill(skillIndex);
                                itemm.giamSoLuong(1);
                                if (itemm.getSoLuong() == 0) {
                                    duLieuNguoiChoi.getHanhTrang().remove(itemm);
                                }
                                setTinNhanPet("Kỹ năng " + (skillIndex + 1) + " đạt cấp "
                                    + duLieuNguoiChoi.deTu.getCapSkill(skillIndex), 2f);
                                duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Cám ơn sư phụ", 2f);
                                if (skillIndex == 3 && duLieuNguoiChoi.deTu.getTenSkill(3).equals("Biến hình")) duLieuNguoiChoi.deTu.timeCoolDownBienKhi = 0;
                            } else {
                                if (duLieuNguoiChoi.deTu.getCapSkill(skillIndex) >= 7) {
                                    setTinNhanPet("Kỹ năng đệ tử " + (skillIndex + 1) + " đã đạt cấp tối đa", 2f);
                                } else {
                                    setTinNhanPet("Đệ tử cần "+formatVangNgoc(sucManhYeuCau[skillIndex] - duLieuNguoiChoi.deTu.getSucManh())+" sức mạnh nữa",2f);
                                }
                            }
                        } else {
                            setTinNhanPet("Bạn chưa có đệ tử", 2f);
                        }
                        break;
                    case "skill4_de_khi":
                        if (!duLieuNguoiChoi.coDeTu()) {
                            setTinNhanPet("Bạn chưa có đệ tử", 2f);
                            DangHienPopupThongTin1 = false;
                            TimeChoHienPopup = 0;
                            return;
                        }

                        long sucManhYeuCau = 20_000_000_000L;
                        if (duLieuNguoiChoi.deTu.getSucManh() < sucManhYeuCau) {
                            setTinNhanPet("Đệ tử chưa đủ sức mạnh", 2f);
                            DangHienPopupThongTin1 = false;
                            TimeChoHienPopup = 0;
                            return;
                        }

                        String skill = duLieuNguoiChoi.deTu.getTenSkill(3);
                        int capSkill = duLieuNguoiChoi.deTu.getCapSkill(3);

                        if (!"Biến hình".equals(skill)) {
                            duLieuNguoiChoi.deTu.setSkillDeTu(4, "Biến hình");
                            itemm.giamSoLuong(1);
                            if (itemm.getSoLuong() == 0) {
                                duLieuNguoiChoi.getHanhTrang().remove(itemm);
                            }
                            DangHienPopupThongTin1 = false;
                            TimeChoHienPopup = 0;
                            return;
                        }

                        if (capSkill < 7) {
                            setTinNhanPet("Kỹ năng biến hình đệ tử cần đạt cấp 7", 2f);
                            DangHienPopupThongTin1 = false;
                            TimeChoHienPopup = 0;
                            return;
                        }

                        if (capSkill == 7) {
                            duLieuNguoiChoi.deTu.tangCapSkill(3);
                            itemm.giamSoLuong(1);
                            if (itemm.getSoLuong() == 0) {
                                duLieuNguoiChoi.getHanhTrang().remove(itemm);
                            }
                            setTinNhanPet("Kỹ năng đệ tử đã đạt cấp tối đa", 2f);
                            DangHienPopupThongTin1 = false;
                            TimeChoHienPopup = 0;
                            return;
                        }
// Nếu > 7
                        setTinNhanPet("Kỹ năng biến hình đệ đạt cấp tối đa", 2f);
                    break;
                }
            }
            if (itemm.getLoai() == LoaiItem.VE_QUAY_NPC_HAIDANG) {
                setTinNhanPet("Cần gặp NPC Hải Đăng để sử dụng", 2f);
            }
        } else if (duLieuNguoiChoi.getSucManh() < itemm.getSucManhYeuCau()) {
            setTinNhanPet("Bạn cần thêm "+formatVangNgoc(itemm.getSucManhYeuCau()-duLieuNguoiChoi.getSucManh())+" sức mạnh nữa",2f);
        } else if (!nhanVat.getHanhtinh().equals(itemm.getHanhtinh())) {
            String ht;
            switch (itemm.getHanhtinh()) {
                case "traidat": ht = "Trái Đất"; break;
                case "xayda" : ht = "Sayda"; break;
                case "namek" : ht = "Namếc"; break;
                default: ht = "";
            }
            setTinNhanPet("Đồ này dành cho hành tinh "+ht,2f);
        }
        DangHienPopupThongTin1 = false;
        TimeChoHienPopup = 0;
    }
    public DuLieuNguoiChoi getDuLieuNguoiChoi() {
        return duLieuNguoiChoi;
    }
    public void huyTtnl() {
        timeTtnl = 0;
        dangTtnl = false;
        timeNhayLanTiepTtnl = 1f;
        timeThongBaoHoiPhucTtnl = 0f;
        try {
            GameSocket.cancelSkill(nhanVat.getTenSkill(3, "xayda"));
        } catch (Exception e) {

        }
    }
    public void huyHuytSao() {
        timeHuytSao = 0;
        dangHuytSao = false;
        try {
            GameSocket.cancelSkill(nhanVat.getTenSkill(6, "xayda"));
        } catch (Exception e) {

        }
    }
    public void huyBienKhi() {
        dangBienKhi = false;
        timeSauBienKhi = 0.6f;
        try {
            GameSocket.cancelSkill(nhanVat.getTenSkill(4, "xayda"));
        } catch (Exception e) {

        }
        if (dangHopThe) {
            String hopTheDuocChon = dangHopTheThuong ? "hop_the_thuong_"+nhanVat.getHanhtinh() : (bongTaiDangDung.equals("bongtaic1") ? "bong_tai_1_"+nhanVat.getHanhtinh() : bongTaiDangDung.equals("bongtaic2") ? "bong_tai_2_"+nhanVat.getHanhtinh() : "bong_tai_3_"+nhanVat.getHanhtinh() );
            NhanVatCauHinh c2 = Doicaitrang(hopTheDuocChon);
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.chan_gong,c2.than_thu,
                c2.lechMap,
                c2.avt
            );
            texAvt = new Texture(nhanVat.doiavatar());
        } else {
            ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrangDangMac();
            if (NhanVatXuLy.getDangMacCaiTrang() && !NhanVatXuLy.getDangMacAvatar() && danhSach.get(5) != null) {
                NhanVatCauHinh c2 = Doicaitrang(danhSach.get(5).getId());
                nhanVat.fixCaiTrang(
                    c2.dau_dung, c2.dau_chay,
                    c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                    c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                    c2.than_bay, c2.chan_bay,
                    c2.chan_gong,c2.than_thu,
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
                    c2.chan_gong,c2.than_thu,
                    c2.lechMap,
                    c2.avt
                );
                texAvt = new Texture(nhanVat.doiavatar());
            }
        }
    }

    public void renderLuaChonCayDau(SpriteBatch batch) {
        if (!dangHienDauThan) return;
        String[] listLuaChon = new String[] {"Thu hoạch","Nâng cấp "+ formatVangNgoc(duLieuNguoiChoi.getCapCayDau()*duLieuNguoiChoi.getCapCayDau()*5_000_000L) + " vàng"};
        for (int i = 0; i < 2; i++) {
            if (nutduocchon==i) {
                Texture nutVe = nutClickTimer2 > 0 ? nutvuongclick : nutvuong;
                batch.draw(nutVe , 393 + i * 120, 5, 114, 114);
            }
            else {
                batch.draw(nutvuong , 393 + i * 120, 5, 114, 114);
            }
            font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            layout.setText(
                font,
                listLuaChon[i],
                font.getColor(),
                100,
                Align.center,
                true
            );
            font.draw(batch,layout,393 + i * 120+7f,5+(114+layout.height)/2f);
        }
    }

    public boolean isDangPhatNhac() {
        for (int i = 1; i < nhacNen.length; i++) {
            if (nhacNen[i].isPlaying()) return true;
        }
        return false;
    }

    public String layTenMap() {
        if (mapHienTai instanceof MapNhaGohan) {
            return "Nhà Gôhan";
        } else if (mapHienTai instanceof MapLangAru) {
            return "Làng Aru";
        } else if (mapHienTai instanceof MapDoiHoaCuc) {
            return "Đồi Hoa Cúc";
        }
        return null;
    }

    public void renderHUDPopupNhanVatPhai(SpriteBatch batch, Texture avtNPC){
        HUDFormPopupNhanVatPhai.render(batch,this,duLieuNguoiChoi,nhanVat,avtNPC);
    }

    public void renderHUDThongBaoPopupNhanVatPhai(SpriteBatch batch){
        HUDFormPopupNhanVatPhai.renderThongBao(batch,this);
    }

    public void capNhatTimeChoTatPopupNpc(float delta) {
        if (timeChoTatPopupNpc > 0) {
            timeChoTatPopupNpc -= delta;
            if (timeChoTatPopupNpc <= 0) {
                timeChoTatPopupNpc = 0;
                dangHienPopupNhanVatPhai = false;
                scrollYPhai = 0;
                scrollYTrai = 0;
            }
        }
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

        if (nenTrangNga!= null) nenTrangNga.dispose();
        if (nenTrangNgaClick!= null) nenTrangNgaClick.dispose();

        for (int i = 1; i < nhacNen.length; i++) {
            if (nhacNen[i] != null) nhacNen[i].dispose();
        }
        if (framesPet != null) {
            for (Texture tex : framesPet) if (tex != null) tex.dispose();
        }

        if (npcHienTai.npcHUDrender.ui_npc instanceof admin_haidang) {
            admin_haidang ui = (admin_haidang) npcHienTai.npcHUDrender.ui_npc;
            if (ui.anhGachaBase != null) ui.anhGachaBase.dispose();
            for (int i = 0; i < 16; i++) {
                if (ui.anhGacha[i] != null) ui.anhGacha[i].dispose();
            }
            if (ui.veQuayKhoa != null) ui.veQuayKhoa.dispose();
            if (ui.veQuay != null) ui.veQuay.dispose();
            if (ui.nutsv != null) ui.nutsv.dispose();
            if (ui.nutclicksv != null) ui.nutclicksv.dispose();
            if (ui.formQuyDoiVe != null) ui.formQuyDoiVe.dispose();
            for (Texture tex : ui.randomGenShin) if (tex != null) tex.dispose();
            for (Texture tex : ui.randomBongTai) if (tex != null) tex.dispose();
            for (Texture tex : ui.randomHuyHieu) if (tex != null) tex.dispose();
            for (Texture tex : ui.randomDeoLung) if (tex != null) tex.dispose();
            for (Texture tex : ui.randomNgocRong) if (tex != null) tex.dispose();
            for (Texture tex : ui.randomNgocRongDen) if (tex != null) tex.dispose();
            for (Texture tex : ui.vatPhamGachaKrandom) if (tex != null) tex.dispose();
        }
        BitmapFont[] fonts = {
            font, fontText, fontTenSkill, fontSkilldaco, fontSkillchuaco, fontSkillchuaco1,
            fontDauThan, fontchat, fontChucnang, fontNhiemVu, fontNhiemVu1, fontCapSKill,
            fontMotaNganSkill1, fontNhiemVuChuaLam, fontMotaNhiemVu, fontMotaNoiTai,
            fontMotaSkill, fontvangngoc, fontMotaHanhTrang, fontMotaHanhTrang1,
            fontTiemNang, fontsm, fontMotaNganSkill, fontMoTaQuyDoiVe
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
        if (anhAura != null) {
            for (Texture tex : anhAura) {
                if (tex != null) tex.dispose();
            }
            anhAura = null;
        }

        Texture[] itemTextures = {
            boHuyet, boKhi, cuongNo, giapXen,
            avtPetTheoHanhTinh
        };

        for (Texture tex : itemTextures) {
            if (tex != null) tex.dispose();
        }

        // Dispose các hiệu ứng rồng và pet
        HUDRongThan.dispose();
    }
}
