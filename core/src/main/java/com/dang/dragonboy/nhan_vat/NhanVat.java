package com.dang.dragonboy.nhan_vat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Map;
import java.util.HashMap;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.du_lieu.TrangThaiDeTu;
import com.dang.dragonboy.giao_dien_ngoai.ManHinhMenu;
import com.dang.dragonboy.he_thong.Main;
import com.dang.dragonboy.nhan_vat.van_bay.VanBayCauHinh;
import com.dang.dragonboy.hien_thi.VeHUD;
import java.util.List;
import java.util.ArrayList;

import com.dang.dragonboy.websocket.GameSocket;
import com.dang.dragonboy.xu_ly_map.HitboxDat;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class NhanVat {
    private ShapeRenderer shapeRenderer;
    private GlyphLayout layout;
    private SpriteBatch batch;
    private VeHUD veHUD;
    public float x, y;
    private String ten;
    public float vx = 0, vy = 0;
    public float rong, cao, rong_chan;
    public boolean dangDungDat = true;
    public float thoiGianTichLuy = 0f;

    private TrangThai trangThai = TrangThai.DUNG_YEN;
    private Map<TrangThai, List<DoLechModular>> lechTheoTrangThai = new HashMap<>();

    // Ảnh các phần
    private String avt;
    public String ao,quan,gang,giay,rada,iconct,giaplt,vanbay;
    private Texture avtTexture; // ảnh cache
    private Texture dau_dung, dau_chay;
    private Texture than_dung, than_nhay, than_roi;
    private Texture[] than_chay;
    private Texture than_bay,than_thu;
    private Texture chan_dung, chan_nhay, chan_roi,chan_gong;
    private Texture[] chan_chay;
    private Texture chan_bay;
    public String dauPath, chanPath, thanPath;

    public Texture[] ttnl = new Texture[4];
    public Texture[] bom = new Texture[2];
    public Texture[] saoDo = new Texture[3];
    public Texture[] saoXanh = new Texture[3];
    public Texture[] saoVang = new Texture[2];
    private int frameTtnl = 0, frameBom = 0, frameSaoXanh, frameSaoDo, frameSaoVang;

    // Cho animation chạy
    private int frame = 0;
    private float timeChay = 0f;

    private final float trongLuc = -0.5f;
    private float tocDoDiChuyen = 7f;

    private final float tiLe = 0.5f;

    private boolean dangBayNgang = false;
    private boolean daNhay = false;
    private float demThoiGianBay = 0;
    private final float delayRoi = 15f;

    public boolean phimTraiDangGiu = false;
    public boolean phimPhaiDangGiu = false;
    public boolean phimNhayDangGiu = false;

    private boolean flipX = false;

    // Offset để tùy chỉnh vị trí đầu/thân theo trạng thái
    public float lechThanX = 0f, lechThanY = 0f;
    public float lechDauX = 0f, lechDauY = 0f;
    public float lechChanX = 0f, lechChanY = 0f;

    public String tenVanBay = "base";

    private VanBayCauHinh vanBayCauHinh;
    public int frameVanBay = 0;
    private float timeVanBay = 0f;

    public boolean dangMangVanBay = false;

    private float gioiHanXMax,gioiHanXMin;
    private float gioiHanYMax,gioiHanYMin;
    public List<HitboxDat> danhSachDat = new ArrayList<>();

    public void setDanhSachDat(List<HitboxDat> ds) {
        this.danhSachDat = ds;
        if (duLieuNguoiChoi.coDeTu()) {
            duLieuNguoiChoi.deTu.setDanhSachDat(danhSachDat);
        }
    }
    private String hanhtinh;
    private String nhanvat;

    // ==== THUỘC TÍNH GAMEPLAY ==== //
    private long sucManh = 40_999_999_999L;
    private int theLuc = 100;

    private int HpGoc = 500000;
    private int KiGoc = 500000;
    private float HpNhanVat = HpGoc;
    private float KiNhanVat = KiGoc;
    private float HpHienTai = HpNhanVat*0.1f;
    private float KiHienTai = KiNhanVat*0.1f;
    private int SucDanhGoc = 24000;
    private float SucDanhNhanVat = SucDanhGoc;
    private int GiapGoc = 990;
    private int GiapNhanVat = GiapGoc;
    private int ChiMangGoc = 10;
    private int ChiMangNhanVat = ChiMangGoc;
    private int SatThuongChiMang = 150;
    private long TiemNangNhanVat = 99999999999L;
    private int DiemSoiDongNhanVat = 0;
    private int GiamSatThuongNhanVat = 0;

    private int capcaydau;
    private int soDauThan = 99;
    private long vang = 100_000_000_000L;
    private long ngoc = 100;
    private String capBac = "Thần Xayda cấp 9+99.99%";
    private int CapSkill1 = 7;
    private int CapSkill2 = 6;
    private int CapSkill3 = 7;
    private int CapSkill4 = 1;
    private int CapSkill5 = 3;
    private int CapSkill6 = 1;
    private int CapSkill7 = 1;
    private int CapSkill8 = 0;
    private int CapSkill9 = 7;
    private String idCaiTrang = "mac_dinh";
    private String tenCaiTrang = "mac_dinh";
    private String moTaCaiTrang = "mac_dinh";
    private int[] chisoCaiTrang ;
    private float hanSuDungCaiTrang;
    private String hanhTinhCaiTrang;
    private long sucManhYeuCauCaiTrang;
    private String idAo = "mac_dinh";
    private String tenAo = "mac_dinh";
    private String moTaAo = "mac_dinh";
    private int[] chisoAo ;
    private int soSaoAo;
    private int soCapAo;
    private int soSaoCuongHoaAo;
    private String hanhTinhAo;
    private long sucManhYeuCauAo;
    private String idQuan = "mac_dinh";
    private String tenQuan = "mac_dinh";
    private String moTaQuan = "mac_dinh";
    private int[] chisoQuan ;
    private int soSaoQuan;
    private int soCapQuan;
    private int soSaoCuongHoaQuan;
    private String hanhTinhQuan;
    private long sucManhYeuCauQuan;
    private String idGang = "mac_dinh";
    private String tenGang = "mac_dinh";
    private String moTaGang = "mac_dinh";
    private int[] chisoGang ;
    private int soSaoGang;
    private int soCapGang;
    private int soSaoCuongHoaGang;
    private String hanhTinhGang;
    private long sucManhYeuCauGang;
    private String idGiay = "mac_dinh";
    private String tenGiay = "mac_dinh";
    private String moTaGiay = "mac_dinh";
    private int[] chisoGiay ;
    private int soSaoGiay;
    private int soCapGiay;
    private int soSaoCuongHoaGiay;
    private String hanhTinhGiay;
    private long sucManhYeuCauGiay;
    private String idRada = "mac_dinh";
    private String tenRada = "mac_dinh";
    private String moTaRada = "mac_dinh";
    private int[] chisoRada ;
    private int soSaoRada;
    private int soCapRada;
    private int soSaoCuongHoaRada;
    private String hanhTinhRada;
    private long sucManhYeuCauRada;
    private String idGiapLuyenTap;
    private String tenGiapLuyenTap = "mac_dinh";
    private String moTaGiapLuyenTap = "mac_dinh";
    private int[] chisoGiapLuyenTap ;
    private int soSaoGiapLuyenTap;
    private int soSaoCuongHoaGlt;
    private float hanSuDungGiapLuyenTap;
    private String hanhTinhGiapLuyenTap;
    private long sucManhYeuCauGiapLuyenTap;
    private String idVanBay;
    private String tenVanBayy;
    private String moTaVanBay;
    private int[] chisoVanBay;
    private String hanhTinhVanBay;
    private long sucManhYeuCauVanBay;

    private DuLieuNguoiChoi duLieuNguoiChoi;

    public boolean diChuyenDenMucTieu = false;
    private float x_muc_tieu,y_muc_tieu;
    public float x_check_npc, y_check_npc;
    public boolean vuaClick = false;
    boolean daNhayDeLenY = false;
    private Texture[] clickdichuyen = new Texture[4];
    private float thoiGianHienDiemCanDen = 0; // đếm thời gian từ lúc click
    private boolean dangHienDiemCanDen = false;
    public boolean chanDiChuyenToaDo1Lan = false; // chan animation di chuyen toa do

    public float timeChoHienBay;
    private float timeDoiFramesHuyHieu,timeDoiFramesDeoLung,timeDoiFramesAura,timeDoiFramesTtnl,timeDoiFramesQckk,timeHieuUngHuytSao;
    public boolean chuaSetTimeHuytSao = true;
    public final Texture[] auraChan = new Texture[4];

    private float moveTimer = 0f;
    private static final float MOVE_INTERVAL = 0.05f; // 20 lần/giây - mượt
    // Trade-off ở đây là nếu tăng lên thì trải nghiệm người chơi giảm nhưng server tải ít hơn
    // Để mức trung bình 20 lần/s, có thể điều chỉnh sau

    // Thay vì gửi 10 lần/s thì tối ưu thêm là nếu x, y hoặc trạng thái khác lần gần nhất mới gửi
    private float lastSentX = -9999f;
    private float lastSentY = -9999f;
    private TrangThai lastSentTrangThai = null;
    private static final float POSITION_THRESHOLD = 0.5f; // Khoảng cách được coi là đã dịch chuyển ( x_mới - x_last_sent > là gửi ) ( tương tự với y )
    private boolean vuaFixCaiTrang = false;

    public void setToaDoMucTieu(float x, float y) {
        if (chanDiChuyenToaDo1Lan) {
            chanDiChuyenToaDo1Lan = false;
            return;
        }

        this.x_muc_tieu = x;
        this.y_muc_tieu = y;
        this.x_check_npc = x;
        this.y_check_npc = y;
        vuaClick = true;
        dangHienDiemCanDen = true;
        thoiGianHienDiemCanDen = 0f;
    }

    public void veDiemCanDen(SpriteBatch batch) {
        if (this.veHUD.daClickVaoPlayer) return;
        if (!dangHienDiemCanDen) return;

        thoiGianHienDiemCanDen += Gdx.graphics.getDeltaTime();
        if (thoiGianHienDiemCanDen > 0.5f) {
            dangHienDiemCanDen = false;
            return;
        }
        int frameIndex = (int)(thoiGianHienDiemCanDen / (0.18f/4f))%4;
        float flipscale = flipX ? -1f : 1f;
        if (dangDungDat && y_muc_tieu <= y+cao ) {
            y_muc_tieu = y;
        }
        batch.draw(clickdichuyen[frameIndex], x_muc_tieu-clickdichuyen[frameIndex].getWidth()*0.5f*flipscale/2f, y_muc_tieu,clickdichuyen[frameIndex].getWidth()*0.5f*flipscale,clickdichuyen[frameIndex].getHeight()*0.5f);
    }

    // cai trang
    public void setIdCaiTrang(String id) {
        this.idCaiTrang = id;
    }

    public String getIdCaiTrang() {
        return idCaiTrang;
    }
    public void setTenCaiTrang(String ten) {
        this.tenCaiTrang = ten;
    }

    public String getTenCaiTrang() {
        return tenCaiTrang;
    }
    public void setMoTaCaiTrang(String mota) {
        this.moTaCaiTrang = mota;
    }

    public String getMoTaCaiTrang() {
        return moTaCaiTrang;
    }

    public void setChisoCaiTrang(int[] chiso) {
        this.chisoCaiTrang = chiso;
    }

    public int[] getChisoCaiTrang() {
        return chisoCaiTrang;
    }

    public void setHanSuDungCaiTrang(float hanSuDung){
        this.hanSuDungCaiTrang = hanSuDung;
    }

    public float getHanSuDungCaiTrang() {
        return hanSuDungCaiTrang;
    }

    public void setHanhTinhCaiTrang(String ht){
        this.hanhTinhCaiTrang = ht;
    }

    public String getHanhTinhCaiTrang() {
        return hanhTinhCaiTrang;
    }

    public void setSucManhYeuCauCaiTrang(long SucManhYeuCau) {
        this.sucManhYeuCauCaiTrang = SucManhYeuCau;
    }

    public long getSucManhYeuCauCaiTrang() {
        return sucManhYeuCauCaiTrang;
    }

    // ao
    public void setIdAo(String id) {
        this.idAo = id;
    }

    public String getIdAo() {
        return idAo;
    }

    public void setTenAo(String ten) {
        this.tenAo = ten;
    }

    public String getTenAo() {
        return tenAo;
    }

    public void setMoTaAo(String mota) {
        this.moTaAo = mota;
    }

    public String getMoTaAo() {
        return moTaAo;
    }

    public void setChisoAo(int[] chiso) {
        this.chisoAo = chiso;
    }

    public int[] getChisoAo() {
        return chisoAo;
    }

    public void setSoSaoAo(int soSao) {
        this.soSaoAo =  soSao;
    }

    public int getSoSaoAo() {
        return soSaoAo;
    }

    public void setSoCapAo(int soCap) {
        this.soCapAo =  soCap;
    }

    public int getSoCapAo() {
        return soCapAo;
    }

    public void setHanhTinhAo(String ht) {
        this.hanhTinhAo = ht;
    }

    public String getHanhTinhAo() {
        return hanhTinhAo;
    }

    public void setSucManhYeuCauAo(long SucManhYeuCau) {
        this.sucManhYeuCauAo = SucManhYeuCau;
    }

    public long getSucManhYeuCauAo() {
        return sucManhYeuCauAo;
    }

    public void setSoSaoCuongHoaAo(int soSao) {
        this.soSaoCuongHoaAo =  soSao;
    }

    public int getSoSaoCuongHoaAo() {
        return soSaoCuongHoaAo;
    }
    // quan
    public void setIdQuan(String id) {
        this.idQuan = id;
    }

    public String getIdQuan() {
        return idQuan;
    }

    public void setTenQuan(String ten) {
        this.tenQuan = ten;
    }

    public String getTenQuan() {
        return tenQuan;
    }

    public void setMoTaQuan(String mota) {
        this.moTaQuan = mota;
    }

    public String getMoTaQuan() {
        return moTaQuan;
    }

    public void setChisoQuan(int[] chiso) {
        this.chisoQuan = chiso;
    }

    public int[] getChisoQuan() {
        return chisoQuan;
    }

    public void setSoSaoQuan(int soSao) {
        this.soSaoQuan =  soSao;
    }

    public int getSoSaoQuan() {
        return soSaoQuan;
    }

    public void setSoCapQuan(int soCap) {
        this.soCapQuan =  soCap;
    }

    public int getSoCapQuan() {
        return soCapQuan;
    }

    public void setHanhTinhQuan(String ht) {
        this.hanhTinhQuan = ht;
    }

    public String getHanhTinhQuan() {
        return hanhTinhQuan;
    }

    public void setSucManhYeuCauQuan(long SucManhYeuCau) {
        this.sucManhYeuCauQuan = SucManhYeuCau;
    }

    public long getSucManhYeuCauQuan() {
        return sucManhYeuCauQuan;
    }
    public void setSoSaoCuongHoaQuan(int soSao) {
        this.soSaoCuongHoaQuan =  soSao;
    }

    public int getSoSaoCuongHoaQuan() {
        return soSaoCuongHoaQuan;
    }
    // gang
    public void setIdGang(String id) {
        this.idGang = id;
    }

    public String getIdGang() {
        return idGang;
    }
    public void setTenGang(String ten) {
        this.tenGang = ten;
    }

    public String getTenGang() {
        return tenGang;
    }

    public void setMoTaGang(String mota) {
        this.moTaGang = mota;
    }

    public String getMoTaGang() {
        return moTaGang;
    }

    public void setChisoGang(int[] chiso) {
        this.chisoGang = chiso;
    }

    public int[] getChisoGang() {
        return chisoGang;
    }

    public void setSoSaoGang(int soSao) {
        this.soSaoGang =  soSao;
    }

    public int getSoSaoGang() {
        return soSaoGang;
    }

    public void setSoCapGang(int soCap) {
        this.soCapGang =  soCap;
    }

    public int getSoCapGang() {
        return soCapGang;
    }

    public void setHanhTinhGang(String ht) {
        this.hanhTinhGang = ht;
    }

    public String getHanhTinhGang() {
        return hanhTinhGang;
    }

    public void setSucManhYeuCauGang(long SucManhYeuCau) {
        this.sucManhYeuCauGang = SucManhYeuCau;
    }

    public long getSucManhYeuCauGang() {
        return sucManhYeuCauGang;
    }
    public void setSoSaoCuongHoaGang(int soSao) {
        this.soSaoCuongHoaGang =  soSao;
    }

    public int getSoSaoCuongHoaGang() {
        return soSaoCuongHoaGang;
    }
    //giay
    public void setIdGiay(String id) {
        this.idGiay = id;
    }

    public String getIdGiay() {
        return idGiay;
    }
    public void setTenGiay(String ten) {
        this.tenGiay = ten;
    }

    public String getTenGiay() {
        return tenGiay;
    }

    public void setMoTaGiay(String mota) {
        this.moTaGiay = mota;
    }

    public String getMoTaGiay() {
        return moTaGiay;
    }

    public void setChisoGiay(int[] chiso) {
        this.chisoGiay = chiso;
    }

    public int[] getChisoGiay() {
        return chisoGiay;
    }

    public void setSoSaoGiay(int soSao) {
        this.soSaoGiay =  soSao;
    }

    public int getSoSaoGiay() {
        return soSaoGiay;
    }

    public void setSoCapGiay(int soCap) {
        this.soCapGiay =  soCap;
    }

    public int getSoCapGiay() {
        return soCapGiay;
    }

    public void setHanhTinhGiay(String ht) {
        this.hanhTinhGiay = ht;
    }

    public String getHanhTinhGiay() {
        return hanhTinhGiay;
    }

    public void setSucManhYeuCauGiay(long SucManhYeuCau) {
        this.sucManhYeuCauGiay = SucManhYeuCau;
    }

    public long getSucManhYeuCauGiay() {
        return sucManhYeuCauGiay;
    }
    public void setSoSaoCuongHoaGiay(int soSao) {
        this.soSaoCuongHoaGiay =  soSao;
    }

    public int getSoSaoCuongHoaGiay() {
        return soSaoCuongHoaGiay;
    }

    // rada
    public void setIdRada(String id) {
        this.idRada = id;
    }

    public String getIdRada() {
        return idRada;
    }
    public void setTenRada(String ten) {
        this.tenRada = ten;
    }

    public String getTenRada() {
        return tenRada;
    }

    public void setMoTaRada(String mota) {
        this.moTaRada = mota;
    }

    public String getMoTaRada() {
        return moTaRada;
    }

    public void setChisoRada(int[] chiso) {
        this.chisoRada = chiso;
    }

    public int[] getChisoRada() {
        return chisoRada;
    }

    public void setSoSaoRada(int soSao) {
        this.soSaoRada =  soSao;
    }

    public int getSoSaoRada() {
        return soSaoRada;
    }

    public void setSoCapRada(int soCap) {
        this.soCapRada =  soCap;
    }

    public int getSoCapRada() {
        return soCapRada;
    }

    public void setHanhTinhRada(String ht) {
        this.hanhTinhRada = ht;
    }

    public String getHanhTinhRada() {
        return hanhTinhRada;
    }

    public void setSucManhYeuCauRada(long SucManhYeuCau) {
        this.sucManhYeuCauRada = SucManhYeuCau;
    }

    public long getSucManhYeuCauRada() {
        return sucManhYeuCauRada;
    }
    public void setSoSaoCuongHoaRada(int soSao) {
        this.soSaoCuongHoaRada =  soSao;
    }

    public int getSoSaoCuongHoaRada() {
        return soSaoCuongHoaRada;
    }

    // giap lt
    public void setIdGiapLuyenTap(String id) {
        this.idGiapLuyenTap = id;
    }

    public String getIdGiapLuyenTap() {
        return idGiapLuyenTap;
    }

    public void setTenGiapLuyenTap(String ten) {
        this.tenGiapLuyenTap = ten;
    }

    public String getTenGiapLuyenTap() {
        return tenGiapLuyenTap;
    }

    public void setMoTaGiapLuyenTap(String mota) {
        this.moTaGiapLuyenTap = mota;
    }

    public String getMoTaGiapLuyenTap() {
        return moTaGiapLuyenTap;
    }

    public void setChisoGiapLuyenTap(int[] chiso) {
        this.chisoGiapLuyenTap = chiso;
    }

    public int[] getChisoGiapLuyenTap() {
        return chisoGiapLuyenTap;
    }

    public void setSoSaoGiapLuyenTap(int soSao) {
        this.soSaoGiapLuyenTap = soSao;
    }

    public int getSoSaoGiapLuyenTap() {
        return soSaoGiapLuyenTap;
    }

    public void setHanSuDungGiapLuyenTap(float hanSuDung){
        this.hanSuDungGiapLuyenTap = hanSuDung;
    }

    public float getHanSuDungGiapLuyenTap() {
        return hanSuDungGiapLuyenTap;
    }

    public void tangHanSuDungGiapLuyenTap() {
        this.hanSuDungGiapLuyenTap += 1/60f;
        this.hanSuDungGiapLuyenTap = Math.max(0,Math.min(this.hanSuDungGiapLuyenTap,1800f));
    }

    public void giamHanSuDungGiapLuyenTap() {
        this.hanSuDungGiapLuyenTap -= 1/60f;
        this.hanSuDungGiapLuyenTap = Math.max(0,Math.min(this.hanSuDungGiapLuyenTap,1800f));
    }

    public void setHanhTinhGiapLuyenTap(String ht) {
        this.hanhTinhGiapLuyenTap = ht;
    }

    public String getHanhTinhGiapLuyenTap() {
        return hanhTinhGiapLuyenTap;
    }

    public void setSucManhYeuCauGiapLuyenTap(long SucManhYeuCau) {
        this.sucManhYeuCauGiapLuyenTap = SucManhYeuCau;
    }

    public long getSucManhYeuCauGiapLuyenTap() {
        return sucManhYeuCauGiapLuyenTap;
    }

    public void setSoSaoCuongHoaGlt(int soSao) {
        this.soSaoCuongHoaGlt = soSao;
    }

    public int getSoSaoCuongHoaGlt() {
        return soSaoCuongHoaGlt ;
    }

    // Van Bay
    public void setIdVanBay(String id) {
        this.idVanBay = id;
    }

    public String getIdVanBay() {
        return idVanBay;
    }

    public void setTenVanBay(String ten) {
        this.tenVanBayy = ten;
    }

    public String getTenVanBay() {
        return tenVanBayy;
    }

    public void setMoTaVanBay(String mota) {
        this.moTaVanBay = mota;
    }

    public String getMoTaVanBay() {
        return moTaVanBay;
    }

    public void setChisoVanBay(int[] chiso) {
        this.chisoVanBay = chiso;
    }

    public int[] getChisoVanBay() {
        return chisoVanBay;
    }

    public void setHanhTinhVanBay(String ht) {
        this.hanhTinhVanBay = ht;
    }

    public String getHanhTinhVanBay() {
        return hanhTinhVanBay;
    }

    public void setSucManhYeuCauVanBay(long SucManhYeuCau) {
        this.sucManhYeuCauVanBay = SucManhYeuCau;
    }

    public long getSucManhYeuCauVanBay() {
        return sucManhYeuCauVanBay;
    }

    // chi so chinh
    public long getSucManh() {return sucManh;}

    public int getTheLuc() {return theLuc;}

    public float getHpHienTai() {
        return HpHienTai;
    }

    public float getHpToiDa() {
        return HpNhanVat;
    }
    public int getHpGoc(){
        return HpGoc;
    }
    public float getKiHienTai() {
        return KiHienTai;
    }
    public int getKiGoc(){
        return KiGoc;
    }
    public float getKiToiDa() {
        return KiNhanVat;
    }
    public int getSucDanhGoc(){
        return SucDanhGoc;
    }
    public float getSucDanhNhanVat(){
        return SucDanhNhanVat;
    }
    public int getGiapGoc(){
        return GiapGoc;
    }
    public int getGiapNhanVat(){
        return GiapNhanVat;
    }
    public int getChiMangGoc(){
        return ChiMangGoc;
    }
    public int getChiMangNhanVat(){
        return ChiMangNhanVat;
    }
    public int getSatThuongChiMang(){
        return SatThuongChiMang;
    }
    public long getTiemNangNhanVat(){
        return TiemNangNhanVat;
    }
    public int getDiemSoiDongNhanVat(){
        return DiemSoiDongNhanVat;
    }
    public int getGiamSatThuongNhanVat() { return GiamSatThuongNhanVat; }
    public int getCapcaydau() { return capcaydau; }
    public int getSoDauThan() { return soDauThan ;}

    public long getVang() {
        return vang;
    }

    public long getNgoc() {
        return ngoc;
    }

    public String getHanhtinh(){ return hanhtinh;}
    public String getNhanvat(){ return nhanvat;}

    public String getCapBac() {
        return capBac;
    }
    public int getCapSkill(int Skill){
        switch (Skill) {
            case 1: return CapSkill1;
            case 2: return CapSkill2;
            case 3: return CapSkill3;
            case 4: return CapSkill4;
            case 5: return CapSkill5;
            case 6: return CapSkill6;
            case 7: return CapSkill7;
            case 8: return CapSkill8;
            case 9: return CapSkill9;
            default: return 0;
        }
    }
    public String getTenSkill(int skill,String hanhTinh){
        if ("traidat".equals(hanhTinh)){
            switch (skill){
                case 1: return "Chiêu đấm Dragon";
                case 2: return "Chiêu Kamejoko";
                case 3: return "Thái Dương Hạ San";
                case 4: return "Kaioken";
                case 5: return "Quả cầu khinh khi";
                case 6: return "Dịch chuyển tức thời";
                case 7: return "Thôi miên";
                case 8: return "Super Kamehameha";
                case 9: return "Khiên năng lượng";
                default: return "";
            }
        } else if ("xayda".equals(hanhTinh)) {
            switch (skill){
                case 1: return "Chiêu đấm Galick";
                case 2: return "Chiêu Antomic";
                case 3: return "Tái tạo năng lượng";
                case 4: return "Biến hình";
                case 5: return "Tự phát nổ";
                case 6: return "Huýt sáo";
                case 7: return "Trói";
                case 8: return "Cađíc liên hoàn chưởng";
                case 9: return "Khiên năng lượng";
                default: return "";
            }
        } else {
            switch (skill){
                case 1: return "Chiêu đấm Demon";
                case 2: return "Chiêu Masenko";
                case 3: return "Trị thương";
                case 4: return "Makankosappo";
                case 5: return "Đẻ trứng";
                case 6: return "Biến Sôcôla";
                case 7: return "Đấm liên hoàn";
                case 8: return "Ma phong ba";
                case 9: return "Khiên năng lượng";
                default: return "";
            }
        }
    }
    public String[] getMotaSkill(int skill,String hanhTinh){
        if ("traidat".equals(hanhTinh)){
            switch (skill){
                case 1: return new String[]{"Tấn công cận chiến","Tăng sức đánh","KI tiêu hao","Hồi chiêu"};
                case 2: return new String[]{"Bắn xa nhờ năng lượng","Tăng sức đánh","KI tiêu hao","Hồi chiêu"};
                case 3: return new String[]{"Làm kẻ địch bất động","","KI tiêu hao","Hồi chiêu"};
                case 4: return new String[]{"Kaioken","","KI tiêu hao","Hồi chiêu"};
                case 5: return new String[]{"Quả cầu khinh khi","","KI tiêu hao","Hồi chiêu"};
                case 6: return new String[]{"Dịch chuyển tức thời","","KI tiêu hao","Hồi chiêu"};
                case 7: return new String[]{"Thôi miên","","KI tiêu hao","Hồi chiêu"};
                case 8: return new String[]{"Super Kamehameha","","KI tiêu hao","Hồi chiêu"};
                case 9: return new String[]{"Khiên năng lượng","","KI tiêu hao","Hồi chiêu"};
                default: return new String[]{""};
            }
        } else if ("xayda".equals(hanhTinh)) {
            switch (skill){
                case 1: return new String[]{"Tấn công cận chiến","Tăng sức đánh: "+(100+10*CapSkill1)+"%","KI tiêu hao: "+(10*CapSkill1),"Hồi chiêu: "+0.5+"s"};
                case 2: return new String[]{"Bắn xa nhờ năng lượng","Tăng sức đánh :"+(150+20*CapSkill2)+"%","KI tiêu hao: "+(300+100*CapSkill2),"Hồi chiêu: 2.2s"};
                case 3: return new String[]{"Tái tạo lại HP và MP đang có","Tự tái tạo HP MP "+(3+1*CapSkill3)+"%/s","KI tiêu hao: 0%","Hồi chiêu: "+"20s"};
                case 4: return new String[]{"Biến hình thành khỉ","Tăng sức đánh, HP và tốc độ","KI tiêu hao: 10%","Hồi chiêu: "+(500-20*CapSkill4)+"s"};
                case 5: return new String[]{"Tự phát nổ","Hy sinh, gây sát thương lớn cho kẻ thù","KI tiêu hao: 50%","Hồi chiêu: 120s"};
                case 6: return new String[]{"Huýt sáo","Tăng tạm thời "+(30+10*CapSkill6)+"%HP cho mọi người","KI tiêu hao: 20","Hồi chiêu: 180s"};
                case 7: return new String[]{"Trói","Trói kẻ thù","KI tiêu hao: 32000","Hồi chiêu: "+(70-5*CapSkill7)+"s"};
                case 8: return new String[]{"Cađíc liên hoàn chưởng","Tăng sức đánh: "+(550+CapSkill8*50)+"%","KI tiêu hao: 80%","Hồi chiêu: 170s"};
                case 9: return new String[]{"Khiên năng lượng","Vô hiệu các đòn tấn công","KI tiêu hao: 33%","Hồi chiêu: "+(175-10*CapSkill9)+"s"};
                default: return new String[]{""};
            }
        } else {
            switch (skill){
                case 1: return new String[]{"Tấn công cận chiến","Tăng sức đánh","KI tiêu hao","Hồi chiêu"};
                case 2: return new String[]{"Bắn xa nhờ năng lượng","Tăng sức đánh","KI tiêu hao","Hồi chiêu"};
                case 3: return new String[]{"Trị thương cho bạn và đồng minh","","KI tiêu hao","Hồi chiêu"};
                case 4: return new String[]{"Makankosappo","","KI tiêu hao","Hồi chiêu"};
                case 5: return new String[]{"Đẻ trứng","","KI tiêu hao","Hồi chiêu"};
                case 6: return new String[]{"Biến Sôcôla","","KI tiêu hao","Hồi chiêu"};
                case 7: return new String[]{"Đấm liên hoàn","","KI tiêu hao","Hồi chiêu"};
                case 8: return new String[]{"Ma phong ba","","KI tiêu hao","Hồi chiêu"};
                case 9: return new String[]{"Khiên năng lượng","","KI tiêu hao","Hồi chiêu"};
                default: return new String[]{""};
            }
        }
    }

    public NhanVat(float x, float y,
                   Texture dau_dung, Texture dau_chay,
                   Texture than_dung, Texture than_nhay, Texture than_roi, Texture[] than_chay,
                   Texture chan_dung, Texture chan_nhay, Texture chan_roi, Texture[] chan_chay,
                   Texture than_bay,Texture chan_bay,Texture chan_gong, Texture than_thu, Map<TrangThai, List<DoLechModular>> lechTheoTrangThai,String avt,
                   String ao, String quan, String gang, String giay, String rada, String iconct, String giaplt, String vanbay,
                   int capcaydau, String hanhtinh, String nhanvat) {
        this.x = State_Management.getUserResponse().x;
        this.y = State_Management.getUserResponse().y;

        this.avt = avt;
        this.ao = ao;
        this.quan = quan;
        this.gang = gang;
        this.giay = giay;
        this.rada = rada;
        this.iconct = iconct;
        this.giaplt = giaplt;
        this.vanbay = vanbay;

        this.dau_dung = dau_dung;
        this.dau_chay = dau_chay;
        this.than_dung = than_dung;
        this.than_nhay = than_nhay;
        this.than_roi = than_roi;
        this.than_chay = than_chay;

        this.chan_dung = chan_dung;
        this.chan_nhay = chan_nhay;
        this.chan_roi = chan_roi;
        this.chan_chay = chan_chay;

        this.than_bay = than_bay;
        this.chan_bay = chan_bay;

        this.chan_gong = chan_gong;
        this.than_thu = than_thu;

        this.rong = than_dung.getWidth() * tiLe;
        this.rong_chan = chan_dung.getWidth() * tiLe;
        this.cao = chan_dung.getHeight() * tiLe + than_dung.getHeight() * tiLe + dau_dung.getHeight() * 0.15f;

        this.lechTheoTrangThai = lechTheoTrangThai;
        this.capcaydau = capcaydau;
        this.hanhtinh = hanhtinh;
        this.nhanvat = nhanvat;
        taiAnhVanBay("base"); // tùy chọn
        shapeRenderer = new ShapeRenderer();
        layout = new GlyphLayout();
        for (int i = 0; i < 4;i++) {
            clickdichuyen[i] = new Texture("hieuung/hieuunggame/click_di_chuyen/"+(i+1)+".png");
        }
        for (int i = 0; i < 4;i++) {
            auraChan[i] = new Texture("vatpham/vatphamgame/aura/chan/"+(i+1)+".png");
        }
        for (int i = 0; i < 4;i++) {
            ttnl[i] = new Texture("hieuung/hieuunggame/bien_khi/ttnl"+(i+1)+".png");
        }
        for (int i = 0; i < 2;i++) {
            bom[i] = new Texture("hieuung/hieuunggame/bien_khi/bom"+(i+1)+".png");
        }
        for (int i = 0; i < 3;i++) {
            saoDo[i] = new Texture("hieuung/hieuunggame/huyt_sao/do"+(i+1)+".png");
        }
        for (int i = 0; i < 3;i++) {
            saoXanh[i] = new Texture("hieuung/hieuunggame/huyt_sao/xanh"+(i+1)+".png");
        }
        for (int i = 0; i < 2;i++) {
            saoVang[i] = new Texture("hieuung/hieuunggame/huyt_sao/vang"+(i+1)+".png");
        }
        batch = new SpriteBatch();
    }

    public void fixCaiTrang
        (Texture dau_dung, Texture dau_chay,
         Texture than_dung, Texture than_nhay, Texture than_roi, Texture[] than_chay,
         Texture chan_dung, Texture chan_nhay, Texture chan_roi, Texture[] chan_chay,
         Texture than_bay,Texture chan_bay,Texture chan_gong,Texture than_thu,Map<TrangThai, List<DoLechModular>> lechTheoTrangThai,String avt){
        this.avt = avt;

        this.dau_dung = dau_dung;
        this.dau_chay = dau_chay;
        this.than_dung = than_dung;
        this.than_nhay = than_nhay;
        this.than_roi = than_roi;
        this.than_chay = than_chay;

        this.chan_dung = chan_dung;
        this.chan_nhay = chan_nhay;
        this.chan_roi = chan_roi;
        this.chan_chay = chan_chay;

        this.than_bay = than_bay;
        this.chan_bay = chan_bay;

        this.chan_gong = chan_gong;
        this.than_thu = than_thu;

        this.rong = than_dung.getWidth() * tiLe;
        this.rong_chan = chan_dung.getWidth() * tiLe;
        this.cao = chan_dung.getHeight() * tiLe + than_dung.getHeight() * tiLe + dau_dung.getHeight() * 0.15f;
        this.lechTheoTrangThai = lechTheoTrangThai;

        // Đổi ảnh thì sync cho players khác luôn
        vuaFixCaiTrang = true;
    }

    public String doiavatar(){
        return avt;
    }
    public void diTrai() {
        phimTraiDangGiu = true;
        phimPhaiDangGiu = false;
    }

    public void diPhai() {
        phimPhaiDangGiu = true;
        phimTraiDangGiu = false;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTen() {
        return ten;
    }

    public void dungTrai() {
        phimTraiDangGiu = false;
    }

    public void dungPhai() {
        phimPhaiDangGiu = false;
    }

    public void nhanNhay() {
        phimNhayDangGiu = true;
    }

    public void thaNhay() {
        phimNhayDangGiu = false;
    }

    public void setGioiHanToaDo(float chieuRongMap, float chieuCaoMap,float gioiHanXMin,float gioiHanYMin) {
        this.gioiHanXMax = chieuRongMap;
        this.gioiHanYMax = chieuCaoMap;
        this.gioiHanXMin = gioiHanXMin;
        this.gioiHanYMin = gioiHanYMin;
        if (duLieuNguoiChoi.coDeTu()) {
            duLieuNguoiChoi.deTu.setGioiHanToaDo(gioiHanXMax, gioiHanYMax);
        }
    }

    public void capNhat() {
        duLieuNguoiChoi.capNhat();
//        if (1==1) {
//            trangThai = TrangThai.GONG;
//            flipX = false;
//            return;
//        }
        if (duLieuNguoiChoi.getKiHienTai()<=0) {
            if (dangBayNgang) {
                dungTrai();
                dungPhai();
                thaNhay();
                diChuyenDenMucTieu = false;
            }
        }
        if (veHUD.dangHienPopup || veHUD.timeChoBienKhi > 0 || veHUD.dangHienDauThan) {
            dungTrai();
            dungPhai();
            thaNhay();
            diChuyenDenMucTieu = false;
        }
        if (veHUD.timeChoBienKhi > 0) {
            if (veHUD.timeChoBienKhi>1.25f) {
                trangThai = TrangThai.GONG;
            } else if (veHUD.timeChoBienKhi>1f) {
                trangThai = TrangThai.THU;
            } else {
                trangThai = TrangThai.GONG;
            }
            return;
        }
        if (veHUD.timeBienKhi > veHUD.timeBienKhiMAX - 0.3f) {
            trangThai = TrangThai.GONG;
            return;
        }
        if (veHUD.timeBienKhi < 0.15f && veHUD.timeBienKhi > 0) {
            trangThai = TrangThai.THU;
            return;
        }
        if (veHUD.dangBienKhi) {
            tocDoDiChuyen = 9.5f;
        } else {
            tocDoDiChuyen = 7f;
        }
        if (veHUD.timeSauBienKhi < 0.6f && veHUD.timeSauBienKhi > 0) {
            if (veHUD.timeSauBienKhi > 0.35f) {
                trangThai = TrangThai.THU;
            } else {
                trangThai = TrangThai.GONG;
            }
            return;
        }
        if (!dangDungDat && veHUD.timeTtnl <= veHUD.timeTtnlMax-0.4f) {
            if (veHUD.dangTtnl) veHUD.huyTtnl();
        }
        if (veHUD.timeTtnl < 8f) {
            if (phimTraiDangGiu || phimNhayDangGiu || phimPhaiDangGiu) {
                if (veHUD.dangTtnl) veHUD.huyTtnl();
            }
        }
        if (veHUD.dangTtnl) {
            if (veHUD.timeTtnl > veHUD.timeTtnlMax-0.3f) {
                trangThai = TrangThai.THU;
            } else {
                trangThai = TrangThai.GONG;
            }
            return;
        }
        if (!diChuyenDenMucTieu) {
            daNhayDeLenY = false; // reset nếu không di chuyển nữa
        }
        if (dangDungDat && diChuyenDenMucTieu) {
            float x_real;
            if (flipX) {
                x_real = x+rong/2f;
            } else {
                x_real = x+rong/2f;
            }
            boolean ganX = Math.abs(x_real - x_muc_tieu) < 10f;
            boolean chenhLechY = Math.abs(y+cao - y_muc_tieu) > 5f;

            if (!ganX) {
                // Chưa đến gần X → đi ngang
                if (x_real < x_muc_tieu) {
                    setFlipPhai();
                    phimPhaiDangGiu = true;
                    phimTraiDangGiu = false;
                } else {
                    setFlipTrai();
                    phimPhaiDangGiu = false;
                    phimTraiDangGiu = true;
                }
            } else if (chenhLechY && y_muc_tieu > y + cao && !daNhayDeLenY) {
                phimNhayDangGiu = true;
                daNhayDeLenY = true; // chỉ nhảy một lần
            } else {
                // Gần đủ X và Y → dừng
                ketThucDiChuyen();
            }
        }
        if (!dangDungDat && diChuyenDenMucTieu) {
            float x_real;
            if (flipX) {
                x_real = x+rong/2f;
            } else {
                x_real = x+rong/2f;
            }
            boolean daDenX = Math.abs(x_real - x_muc_tieu) < 10f;
            boolean daDenY = Math.abs(y - y_muc_tieu) < 5f;

            // Nếu Y mục tiêu nằm thấp hơn hiện tại → chỉ rơi xuống, không đi ngang
            if (!daDenY && y_muc_tieu < y) {
                phimPhaiDangGiu = false;
                phimTraiDangGiu = false;
                phimNhayDangGiu = false;
            } else {
                // Chỉ xử lý X khi đã gần đúng Y hoặc cần bay lên để tới
                if (!daDenX) {
                    if (x_muc_tieu > x_real) {
                        setFlipPhai();
                        phimPhaiDangGiu = true;
                        phimTraiDangGiu = false;
                    } else {
                        setFlipTrai();
                        phimPhaiDangGiu = false;
                        phimTraiDangGiu = true;
                    }
                } else {
                    phimPhaiDangGiu = false;
                    phimTraiDangGiu = false;
                }

                // Nếu chưa đến Y và mục tiêu Y cao hơn → nhảy lên
                if (!daDenY && y_muc_tieu > y) {
                    phimNhayDangGiu = true;
                } else {
                    phimNhayDangGiu = false;
                }
            }

            if (daDenX && daDenY) {
                ketThucDiChuyen();
            }
        }
        boolean giuPhimNgang = phimTraiDangGiu || phimPhaiDangGiu;
        // Nếu đang đứng trên đất và giữ trái/phải + giữ ↑ thì vào trạng thái bay ngang
        if (dangDungDat && giuPhimNgang && phimNhayDangGiu && duLieuNguoiChoi.getKiHienTai()>0) {
            vy = 5f; // nhảy nhẹ lên
            dangDungDat = false;
            daNhay = true;
            dangBayNgang = true;
            demThoiGianBay = 0;
        }

        // Nếu chỉ giữ ↑ mà không giữ trái/phải thì nhảy cao như bình thường
        if (dangDungDat && !giuPhimNgang && phimNhayDangGiu && !daNhay) {
            vy = 10f; // nhảy cao
            dangDungDat = false;
            daNhay = true;
        }
        if (!dangDungDat) {
            if (giuPhimNgang && !dangBayNgang && vy < 0 && duLieuNguoiChoi.getKiHienTai()>0) {
                dangBayNgang = true;
                vy = 0; // giữ Y hiện tại khi chuyển sang bay ngang
                demThoiGianBay = 0;
            }

            if (dangBayNgang) {
                trangThai = TrangThai.BAY_NGANG;
                if (phimTraiDangGiu) {
                    vx = -tocDoDiChuyen;
                } else if (phimPhaiDangGiu) {
                    vx = tocDoDiChuyen;
                }
                else vx = 0;

                if (phimNhayDangGiu) {
                    vy = 3f; // tạo hiệu ứng bay lên nhẹ, không cộng dồn mãi
                    demThoiGianBay = 0;
                } else {
                    vy = 0; // giữ nguyên Y khi không nhấn ↑
                    if (!giuPhimNgang) {
                        demThoiGianBay++;
                        if (demThoiGianBay > delayRoi) {
                            dangBayNgang = false;
                        }
                    }
                }
            } else {
                // Rơi tự do nếu không bay ngang
                vy += trongLuc;

                if (phimTraiDangGiu) vx = -tocDoDiChuyen;
                else if (phimPhaiDangGiu) vx = tocDoDiChuyen;
                else vx = 0;
            }
        } else {
            daNhay = false;
            dangBayNgang = false;
            demThoiGianBay = 0;

            if (phimTraiDangGiu) vx = -tocDoDiChuyen;
            else if (phimPhaiDangGiu) vx = tocDoDiChuyen;
            else vx = 0;
        }
        // Cập nhật trạng thái chuyển động
        if (dangBayNgang) {
            trangThai = TrangThai.BAY_NGANG;
        } else if (!dangDungDat) {
            if (vy > 0) {
                trangThai = TrangThai.NHAY;
            } else {
                trangThai = TrangThai.ROI;
            }
            timeChoHienBay = 0;
        } else {
            if (vx != 0) {
                trangThai = TrangThai.DI_CHUYEN;
            } else {
                trangThai = TrangThai.DUNG_YEN;
            }
            timeChoHienBay = 0;
        }
        int steps = 10;
        float dx = vx / steps;
        float dy = vy / steps;

        for (int i = 0; i < steps; i++) {
            // Di chuyển nhỏ
            x += dx;
            dangDungDat = false;
            for (HitboxDat dat : danhSachDat) {
                if (dat.vaChamBenTrai(x, y, rong, cao)) {
                    x = dat.x - rong;
                    break;
                } else if (dat.vaChamBenPhai(x, y, rong, cao)) {
                    x = dat.x + dat.width;
                    break;
                }
            }

            y += dy;

            for (HitboxDat dat : danhSachDat) {
                if (dat.vaChamTuTren(x, y, rong, cao, vy)) {
                    y = dat.y + dat.height;
                    vy = 0;
                    dangDungDat = true;
                    daNhay = false;
                    break;
                } else if (dat.vaChamTuDuoi(x, y, rong, cao, vy)) {
                    y = dat.y - cao;
                    vy = 0;
                    break;
                }
            }
        }


        // Giới hạn không cho ra khỏi bản đồ
        x = Math.max(gioiHanXMin, Math.min(x, gioiHanXMax-rong));

        y =Math.max(gioiHanYMin, Math.min(y, gioiHanYMax-cao));
    }
    public void setFlipTrai() {
        flipX = true;
    }
    public void setFlipPhai() {
        flipX = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getCao() {
        return cao;
    }

    public float getRong() {
        return rong;
    }

    public float getGioiHanXMax() {
        return gioiHanXMax;
    }

    public float getGioiHanYMax() {
        return gioiHanYMax;
    }

    public void setHUD(VeHUD hud) {
        this.veHUD = hud;
    }

    private void taiAnhVanBay(String ten) {
        this.tenVanBay = ten;
        Texture[] frames;
        if (!ten.equals("base")) {
            frames = new Texture[]{
                new Texture("vatpham/vanbay/" + ten + "/" + ten + "1.png"),
                new Texture("vatpham/vanbay/" + ten + "/" + ten + "2.png"),
                new Texture("vatpham/vanbay/" + ten + "/" + ten + "3.png"),
                new Texture("vatpham/vanbay/" + ten + "/" + ten + "4.png")
            };
        } else {
            frames = new Texture[] {
                new Texture("hieuung/hieuunggame/aura_bay/" + "1.png"),
                new Texture("hieuung/hieuunggame/aura_bay/" + "2.png"),
                new Texture("hieuung/hieuunggame/aura_bay/" + "3.png"),
                new Texture("hieuung/hieuunggame/aura_bay/" + "4.png")
            };
        }

        switch (ten) {
            case "phuong_hoang_lua":
                vanBayCauHinh = new VanBayCauHinh(frames, 0.5f, false, 0f, -40f);
                break;
            case "candauvan":
                vanBayCauHinh = new VanBayCauHinh(frames, 0.1f, false, -1f, -20f);
                break;
            case "base":
                vanBayCauHinh = new VanBayCauHinh(frames, 0.5f, false, -50f, -20f);
                break;
            default:
                break;
        }
    }

    public void doiVanBay(String tenMoi) {
        taiAnhVanBay(tenMoi);
    }
    public void datToaDo(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void setflip(String huong){
        if ("trai".equals(huong)){
            setFlipTrai();
        }
        else {
            setFlipPhai();
        }
    }

    public void ve(SpriteBatch batch, float thoiGian) {
        boolean duDieuKien = true;
        if (veHUD.dangHopTheThuong) {
            if (veHUD.timeHopTheTHuong < 1.5f ){
                duDieuKien = false;
            } else {
                duDieuKien = true;
            }
        }
        this.thoiGianTichLuy = thoiGian;
        if (duLieuNguoiChoi.coDeTu()) {
            if (!veHUD.renderDeTu && duLieuNguoiChoi.deTu.timeHoatAnhBienMat > 0) {
                if (duLieuNguoiChoi.deTu.chuaLayToaDoBienMat) {
                    duLieuNguoiChoi.deTu.x_bien_mat = duLieuNguoiChoi.deTu.x;
                    duLieuNguoiChoi.deTu.y_bien_mat = duLieuNguoiChoi.deTu.y;
                    duLieuNguoiChoi.deTu.chuaLayToaDoBienMat = false;
                }
                float timeMAX = 0.3f;
                float step = timeMAX / 3;
                for (int i = 0; i < 3; i++) {
                    float start = timeMAX - i * step;
                    float end = timeMAX - (i + 1) * step;
                    if (duLieuNguoiChoi.deTu.timeHoatAnhBienMat >= end && duLieuNguoiChoi.deTu.timeHoatAnhBienMat <= start) {
                        batch.draw(duLieuNguoiChoi.deTu.bien_mat[i], duLieuNguoiChoi.deTu.x_bien_mat + (duLieuNguoiChoi.deTu.rong_de_tu - duLieuNguoiChoi.deTu.bien_mat[i].getWidth() * 0.45f) / 2f, duLieuNguoiChoi.deTu.y_bien_mat + (duLieuNguoiChoi.deTu.cao_de_tu - duLieuNguoiChoi.deTu.bien_mat[i].getHeight() * 0.45f) / 2f, duLieuNguoiChoi.deTu.bien_mat[i].getWidth() * 0.45f, duLieuNguoiChoi.deTu.bien_mat[i].getHeight() * 0.45f);
                    }
                }
            }
        }

        float daoDong;
        if (dangMangVanBay) {
            daoDong = (trangThai == TrangThai.DUNG_YEN || trangThai == TrangThai.BAY_NGANG) ? (float) Math.sin(thoiGian) * 1.08f : 0f;
        } else {
            daoDong = (trangThai == TrangThai.DUNG_YEN) ? (float) Math.sin(thoiGian) * 1.08f
                : (trangThai == TrangThai.BAY_NGANG) ? (float) Math.sin(thoiGian) * 5f
                : 0f;
        }

        if (veHUD.dangDungHuyHieu && veHUD.timeChoHopThe == 0 && duDieuKien && !(veHUD.dangDungAura)){
            if (veHUD.chuaSetUpAnhHuyHieu) {
                veHUD.anhHuyHieu = AssetMulti.getHuyHieu(veHUD.huyHieuDangDung.getId());
                veHUD.chuaSetUpAnhHuyHieu = false;
            }
            timeDoiFramesHuyHieu+=Gdx.graphics.getDeltaTime();
            if (timeDoiFramesHuyHieu>0.03f) {
                veHUD.framesHuyHieu = (veHUD.framesHuyHieu + 1) % veHUD.anhHuyHieu.length;
                timeDoiFramesHuyHieu=0;
            }
            batch.draw(veHUD.anhHuyHieu[veHUD.framesHuyHieu], x - (veHUD.anhHuyHieu[veHUD.framesHuyHieu].getWidth() * 0.55f - rong) / 2f, y + cao + 35f + daoDong, veHUD.anhHuyHieu[veHUD.framesHuyHieu].getWidth() * 0.55f, veHUD.anhHuyHieu[veHUD.framesHuyHieu].getHeight() * 0.55f);
        }
        if (duLieuNguoiChoi.coDeTu()) {
            if (veHUD.renderDeTu) {
                duLieuNguoiChoi.deTu.ve(batch, thoiGian);
            }
        }
        if (veHUD.dangDungAura && veHUD.timeChoHopThe == 0 && duDieuKien && !(trangThai == TrangThai.BAY_NGANG && !dangMangVanBay) && !(trangThai == TrangThai.DI_CHUYEN)){
            float offsetX = 0;
            float offsetY = 0;
            float tiLe = 0;
            if (veHUD.auraDangDung.getId().equals("tan_hon_rong_namek")) {
                offsetX = 12f;
                if (trangThai == TrangThai.ROI) offsetY = 10f;
                tiLe = 0.5f;
            }
            if (veHUD.auraDangDung.getId().equals("tieu_doi_truong")) {
                offsetX = 5f;
                offsetY = 25f;
                if (trangThai == TrangThai.ROI) offsetY = 35f;
                tiLe = 0.5f;
            }
            if (veHUD.chuaSetUpAnhAura) {
                veHUD.anhAura = AssetMulti.getAura(veHUD.auraDangDung.getId());
                veHUD.chuaSetUpAnhAura = false;
            }
            timeDoiFramesAura+=Gdx.graphics.getDeltaTime();
            float timeDoiFrames = 0.1f;
            if (timeDoiFramesAura>timeDoiFrames) {
                veHUD.framesAura = (veHUD.framesAura + 1) % veHUD.anhAura.length;
                timeDoiFramesAura=0;
            }
            float flipScale = flipX ? -1f : 1f;
            float anchorX = flipX ? x + rong  + (veHUD.anhAura[veHUD.framesAura].getWidth() * tiLe)*4.5f/10f-(rong/10f) - offsetX : x  - (veHUD.anhAura[veHUD.framesAura].getWidth() * tiLe)*4.5f/10f+(rong/10f) + offsetX;
            batch.draw(veHUD.anhAura[veHUD.framesAura], anchorX, y+daoDong+(cao/3f)-25f + offsetY, veHUD.anhAura[veHUD.framesAura].getWidth() * tiLe * flipScale, veHUD.anhAura[veHUD.framesAura].getHeight() * tiLe);
        }
        if (veHUD.dangDungDeoLung && veHUD.timeChoHopThe == 0 && duDieuKien && !(trangThai == TrangThai.BAY_NGANG && !dangMangVanBay) && !(trangThai == TrangThai.DI_CHUYEN) && !(trangThai == TrangThai.GONG) && !(trangThai == TrangThai.THU)){
            int soAnh = 2;
            switch (veHUD.deoLungDangDung.getId()) {
                case "dao" : soAnh = 8; break;
                case  "kiem_vip" : soAnh = 7; break;
            }
            if (veHUD.chuaSetUpAnhDeoLung) {
                veHUD.anhDeoLung = AssetMulti.getDeoLung(
                    veHUD.deoLungDangDung.getId(),
                    soAnh
                );
                veHUD.chuaSetUpAnhDeoLung = false;
            }
            timeDoiFramesDeoLung+=Gdx.graphics.getDeltaTime();
            float timeDoiFrames = 0.2f;
            if (timeDoiFramesDeoLung>timeDoiFrames) {
                veHUD.framesDeoLung = (veHUD.framesDeoLung + 1) % soAnh;
                timeDoiFramesDeoLung=0;
            }
            float offsetX = 0;
            float offsetY = 0;
            float flipScale = flipX ? -1f : 1f;
            if (trangThai == TrangThai.ROI) offsetY = 10f;
            float anchorX = flipX ? x + rong  + (veHUD.anhDeoLung[veHUD.framesDeoLung].getWidth() * 0.45f)*4.5f/10f-(rong/10f) - offsetX : x  - (veHUD.anhDeoLung[veHUD.framesDeoLung].getWidth() * 0.45f)*4.5f/10f+(rong/10f) + offsetX;
            batch.draw(veHUD.anhDeoLung[veHUD.framesDeoLung], anchorX, y+daoDong+(cao/3f)-25f + offsetY, veHUD.anhDeoLung[veHUD.framesDeoLung].getWidth() * 0.45f * flipScale, veHUD.anhDeoLung[veHUD.framesDeoLung].getHeight() * 0.45f);
        }
        if (veHUD.timeChoHopThe == 0 && duDieuKien) {
            Texture chanVe = chan_dung;
            Texture thanVe = than_dung;
            Texture dauVe = dau_dung;

            if (trangThai == TrangThai.BAY_NGANG) {
                timeVanBay += Gdx.graphics.getDeltaTime();
                if (dangMangVanBay) {
                    if (timeVanBay > 0.12f) {
                        frameVanBay = (frameVanBay + 1) % vanBayCauHinh.frames.length;
                        timeVanBay = 0;
                    }
                    if (tenVanBay.equals("phuong_hoang_lua")) {
                        duLieuNguoiChoi.tangKiHienTai(duLieuNguoiChoi.getKiToiDa()*0.0001f);
                        duLieuNguoiChoi.tangHpHienTai(duLieuNguoiChoi.getHpToiDa()*0.00005f);
                    }
                } else {
                    timeChoHienBay += Gdx.graphics.getDeltaTime();
                    if (timeVanBay > 0.02f) {
                        frameVanBay = (frameVanBay + 1) % vanBayCauHinh.frames.length;
                        timeVanBay = 0;
                    }
                    duLieuNguoiChoi.giamKiHienTai(duLieuNguoiChoi.getKiToiDa()*0.0003f);
                }
            }

            switch (trangThai) {
                case BAY_NGANG:
                    if (dangMangVanBay) {
                        chanVe = chan_dung;
                        thanVe = than_dung;
                        dauVe = dau_dung;
                    } else {
                        chanVe = chan_bay;
                        thanVe = than_bay;
                        dauVe = dau_dung;
                    }
                    break;
                case DI_CHUYEN:
                    timeChay += Gdx.graphics.getDeltaTime(); // tăng thời gian theo deltaTime
                    float timeDoiFrames = 0.1f;
                    if (veHUD.dangBienKhi) timeDoiFrames = 0.06f;
                    if (timeChay >= timeDoiFrames) {
                        frame = (frame + 1) % chan_chay.length;
                        timeChay = 0;
                    }
                    chanVe = chan_chay[frame];
                    thanVe = than_chay[frame];
                    dauVe = dau_chay;
                    break;
                case NHAY:
                    chanVe = chan_nhay;
                    thanVe = than_nhay;
                    break;
                case ROI:
                    chanVe = chan_roi;
                    thanVe = than_roi;
                    break;
                case THU:
                    chanVe = chan_gong;
                    thanVe = than_thu;
                    break;
                case GONG:
                    chanVe = chan_gong;
                    thanVe = than_nhay;
                    break;
                case DUNG_YEN:
                default:
                    // giữ ảnh mặc định đã gán ban đầu
                    break;
            }
            // đúng kiểu dữ liệu 2 vế và gán class LechModular để có thể truy cập thuộc tính
            DoLechModular lech = layLech(lechTheoTrangThai, trangThai, frame,dangMangVanBay);
            lechThanX = lech.lechThanX;
            lechThanY = lech.lechThanY;
            lechDauX = lech.lechDauX;
            lechDauY = lech.lechDauY;
            lechChanX = lech.lechChanX;
            lechChanY = lech.lechChanY;

            // Tính tọa độ theo hướng flip
            float chanW = chanVe.getWidth() * tiLe;
            float chanH = chanVe.getHeight() * tiLe;
            float thanW = thanVe.getWidth() * tiLe;
            float thanH = thanVe.getHeight() * tiLe;
            float dauW = dauVe.getWidth() * tiLe;
            float dauH = dauVe.getHeight() * tiLe;

            // Flip bằng scale âm nếu cần
            float flipScale = flipX ? -1f : 1f;
            float anchorX = flipX ? x + rong : x;
            float offsetChanX = flipX ? -lechChanX :  lechChanX;
            if (trangThai != TrangThai.BAY_NGANG) {
                batch.draw(chanVe, anchorX + offsetChanX, y + lechChanY, chanW * flipScale, chanH);

                float thanX = anchorX + (chanW / 2f - thanW / 2f) * flipScale;
                float thanY = y + chanH + daoDong;
                float dauX = anchorX + (chanW / 2f - dauW / 2f) * flipScale;
                float dauY = thanY + thanH;
                batch.draw(dauVe, dauX + lechDauX * flipScale, dauY + lechDauY, dauW * flipScale, dauH);
                batch.draw(thanVe, thanX + lechThanX * flipScale, thanY - 10.2f + lechThanY, thanW * flipScale, thanH);
            } else {
                if (!dangMangVanBay) {
                    batch.draw(chanVe, anchorX - (thanW - 30) * flipScale, y + chanH + daoDong - 10, chanW * flipScale, chanH);

                    float thanX = anchorX + (chanW / 2f - thanW / 2f) * flipScale;
                    float thanY = y + chanH + daoDong;
                    float dauX = anchorX + (chanW / 2f - dauW / 2f) * flipScale;
                    float dauY = thanY + thanH;
                    batch.draw(dauVe, dauX + lechDauX * flipScale, dauY + lechDauY, dauW * flipScale, dauH);
                    batch.draw(thanVe, thanX + lechThanX * flipScale, thanY - 10.2f + lechThanY, thanW * flipScale, thanH);
                } else {
                    batch.draw(chanVe, anchorX, y, chanW * flipScale, chanH);

                    float thanX = anchorX + (chanW / 2f - thanW / 2f) * flipScale;
                    float thanY = y + chanH + daoDong;
                    float dauX = anchorX + (chanW / 2f - dauW / 2f) * flipScale;
                    float dauY = thanY + thanH;
                    batch.draw(dauVe, dauX + lechDauX * flipScale, dauY + lechDauY, dauW * flipScale, dauH);
                    batch.draw(thanVe, thanX + lechThanX * flipScale, thanY - 10.2f + lechThanY, thanW * flipScale, thanH);
                }
            }
            if (trangThai == TrangThai.BAY_NGANG) {
                if (dangMangVanBay) {
                    Texture cloud = vanBayCauHinh.frames[frameVanBay];
                    float cloudW = cloud.getWidth() * vanBayCauHinh.tile;
                    float cloudH = cloud.getHeight() * vanBayCauHinh.tile;
                    float flipCloud = (flipX == vanBayCauHinh.flipVanBay) ? 1f : -1f;

                    batch.draw(
                        cloud,
                        anchorX + (chanW / 2f - cloudW / 2f + vanBayCauHinh.offsetX) * flipCloud,
                        y + vanBayCauHinh.offsetY,
                        cloudW * flipCloud,
                        cloudH
                    );
                } else {
                    if (timeChoHienBay>=0.4f) {
                        Texture cloud = vanBayCauHinh.frames[frameVanBay];
                        float tiLe = 0.55f;
                        float cloudW = cloud.getWidth() * tiLe;
                        float cloudH = cloud.getHeight() * tiLe;
                        float flipCloud = !flipX ? 1f : -1f;

                        batch.draw(
                            cloud,
                            anchorX - (thanW - 30 + cloudW - 20) * flipScale,
                            y + daoDong * 2f+chanH-(cloudH)/2f+(chanH)/2f-3f,
                            cloudW * flipCloud,
                            cloudH
                        );
                    }
                }
            }

            // Lấy string path texture đang vẽ hiện tại để gửi cho backend làm multiplayer
            this.dauPath = NhanVatXuLy.getPathFromTexture(dauVe);
            this.chanPath = NhanVatXuLy.getPathFromTexture(chanVe);
            this.thanPath = NhanVatXuLy.getPathFromTexture(thanVe);

            // ⚠️ QUAN TRỌNG: Phải lấy path texture TRƯỚC khi gửi socket
            // vì dauPath/chanPath/thanPath được cập nhật trong ve(), không phải capNhat()
            // Nếu gửi trong capNhat() thì sẽ gửi path của frame trước → client khác render sai animation
            this.moveTimer += Gdx.graphics.getDeltaTime();

            float dxx = Math.abs(x - lastSentX);
            float dyy = Math.abs(y - lastSentY);
            boolean posChanged = dxx > POSITION_THRESHOLD || dyy > POSITION_THRESHOLD;
            boolean stateChanged = trangThai != lastSentTrangThai;

            // Tối ưu băng thông: chỉ gửi khi có thay đổi thực sự
            // Thay vì gửi 10 lần/giây liên tục bất kể đứng yên hay di chuyển
            if (stateChanged || vuaFixCaiTrang) {
                // Gửi NGAY khi đổi trạng thái, không chờ timer
                // Lý do: nếu chờ timer (0.1s), có thể xảy ra tình huống:
                //   - Frame N:   gửi x=100, trangthai=DI_CHUYEN
                //   - Frame N+1: player dừng, trangthai=DUNG_YEN, nhưng timer chưa đủ
                //   - Frame N+2: x,y không đổi nên posChanged=false, stateChanged=false → không gửi
                //   → Client khác mãi thấy animation chạy dù player đã đứng yên
                if (vuaFixCaiTrang) vuaFixCaiTrang = false;
                try {
                    GameSocket.guiPlayerMove(this);
                    lastSentX = x;
                    lastSentY = y;
                    lastSentTrangThai = trangThai;
                    moveTimer = 0f; // reset để tránh gửi 2 lần liên tiếp
                } catch (Exception e) {}
            } else if (moveTimer >= MOVE_INTERVAL && posChanged) {
                try {
                    // gửi
                    GameSocket.guiPlayerMove(this);
                    lastSentX = x;
                    lastSentY = y;
                    lastSentTrangThai = trangThai;
                    moveTimer = 0f;  // ← reset chỉ khi gửi
                } catch (Exception e) {}
            }

            if (veHUD.timeChoBienKhi > 0) {
                int tick = (int)(veHUD.timeChoBienKhi * 10);
                int tick1 = (int)(veHUD.timeChoBienKhi * 15);
                if (tick1 % 2 == 0) {
                    veQckk(batch,x + rong/2f+5f*flipScale, y + cao / 2f);
                }
                if (tick % 2 == 0) {
                    veTaiTaoNangLuong(batch,x + rong/2f+5f*flipScale,y);
                }
            }
            if (veHUD.timeSauBienKhi < 0.6f && veHUD.timeSauBienKhi > 0) {
                if (veHUD.timeSauBienKhi > 0.35f) {
                    veTaiTaoNangLuong(batch,x + rong/2f+5f*flipScale,y);
                }
            }
            if (veHUD.dangTtnl) {
                float offsetX = 5f;
                if (veHUD.dangBienKhi) offsetX = -5f;
                veTaiTaoNangLuong(batch,x + rong/2f+offsetX*flipScale,y);
            }
            if (veHUD.dangHuytSao) {
                veHuytSao(batch);
            }
            if (veHUD.dangHienTinNhanChat) {
                layout.setText(
                    veHUD.fontchat,
                    veHUD.tinNhanChat,
                    new Color(0, 0, 0, 1),
                    180,
                    Align.center,
                    true
                );
                batch.end();
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1, 1, 1, 1);
                shapeRenderer.rect(x + (rong - 200) / 2f, y + cao + 30, 200, 36 + layout.height);
                shapeRenderer.end();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.BLACK);
                for (int i = 0; i < 2; i++) {
                    shapeRenderer.rect(x + (rong - 200) / 2f - i, y + cao + 30 - i, 200 + i * 2, 36 + layout.height + i * 2);
                }
                shapeRenderer.end();
                batch.begin();
                float duoiX = flipX ? x + rong + 20 : x - 20;
                batch.draw(veHUD.duoichat, duoiX, y + cao + 15, 16 * flipScale, 16);
                veHUD.fontchat.draw(batch, layout, x + (rong - 200) / 2f + 10f, y + cao + 30 + 18f + layout.height);
                if (duLieuNguoiChoi.coDeTu()) {
                    if (veHUD.tinNhanChat.equals("bao ve") || veHUD.tinNhanChat.equals("protect")) {
                        veHUD.trangthaide = "Bảo vệ";
                        duLieuNguoiChoi.deTu.capNhatTrangThaiDeTu();
                    } else if (veHUD.tinNhanChat.equals("tan cong") || veHUD.tinNhanChat.equals("attack")) {
                        veHUD.trangthaide = "Tấn công";
                        duLieuNguoiChoi.deTu.capNhatTrangThaiDeTu();
                    } else if (veHUD.tinNhanChat.equals("di theo") || veHUD.tinNhanChat.equals("follow")) {
                        veHUD.trangthaide = "Đi theo";
                        duLieuNguoiChoi.deTu.capNhatTrangThaiDeTu();
                    } else if (veHUD.tinNhanChat.equals("ve nha") || veHUD.tinNhanChat.equals("go home")) {
                        veHUD.trangthaide = "Về nhà";
                        duLieuNguoiChoi.deTu.capNhatTrangThaiDeTu();
                    }
                    if (veHUD.tinNhanChat.equals("bien hinh") || veHUD.tinNhanChat.equals("transformation")) {
                        if (duLieuNguoiChoi.deTu.getTenSkill(3) != null && duLieuNguoiChoi.deTu.getTenSkill(3).equals("Biến hình") && duLieuNguoiChoi.deTu.timeCoolDownBienKhi == 0 && duLieuNguoiChoi.deTu.timeChoBienKhi == 0) {
                            duLieuNguoiChoi.deTu.timeChoBienKhi = 0.6f;
                            duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Dạ sư phụ",3f);
                        }
                    }
                    if (veHUD.tinNhanChat.equals("huy bien hinh")) {
                        if (duLieuNguoiChoi.deTu.getTenSkill(3) != null && duLieuNguoiChoi.deTu.getTenSkill(3).equals("Biến hình") && duLieuNguoiChoi.deTu.dangBienKhi) {
                            duLieuNguoiChoi.deTu.huyBienKhi();
                            duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Dạ sư phụ",3f);
                        }
                    }
                    if (veHUD.tinNhanChat.contains("ten con la:") && !veHUD.daRanDomChatDeTu) {
                        String[] part = veHUD.tinNhanChat.split(":", -1);
                        String tenDeTu = part[1].trim();
                        if (duLieuNguoiChoi.deTu.chuaSetTenDeTu) {
                            if (!tenDeTu.isEmpty()) {
                                duLieuNguoiChoi.deTu.setTenDeTu(tenDeTu);
                                String[] text = {
                                    "Con xin nhận tên " + tenDeTu + " ạ, con sẽ không làm sư phụ thất vọng.",
                                    "Tên " + tenDeTu + " , đệ tử xin ghi nhớ!",
                                    "Từ nay con là " + tenDeTu + ", cám ơn sư phụ."
                                };
                                duLieuNguoiChoi.deTu.setTinNhanDeTuChat(text[MathUtils.random(text.length - 1)], 4f);
                                duLieuNguoiChoi.deTu.chuaSetTenDeTu = false;
                            } else {
                                duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Sư phụ chưa đặt tên rõ ràng cho con...", 4f);
                            }
                        } else {
                            duLieuNguoiChoi.deTu.setTinNhanDeTuChat("Sư phụ, tên con là " + duLieuNguoiChoi.deTu.getTen() + " mà...", 4f);
                        }
                        veHUD.daRanDomChatDeTu = true;
                    }
                }
            }
        } else {
            if (!veHUD.dangHopThe && !veHUD.dangHopTheThuong) {
                if (veHUD.timeChoHopThe > 1.2f) {
                    // Tính phần trăm đã đi
                    float alpha = (1.5f - veHUD.timeChoHopThe) / 0.3f;
                    alpha = Math.min(Math.max(alpha, 0f), 1f); // đảm bảo nằm trong 0→1

                    // Tính vị trí hai bên chạy về x
                    float xTrai = (x - 80) + 80 * alpha;
                    float xPhai = (x + 80) - 80 * alpha;

                    float chantW = veHUD.chantrai.getWidth() * 2;
                    float chantH = veHUD.chantrai.getHeight() * 2;
                    float thantW = veHUD.thantrai.getWidth() * 2;
                    float thantH = veHUD.thantrai.getHeight() * 2;
                    float dautW = veHUD.dautrai.getWidth() * 2;
                    float dautH = veHUD.dautrai.getHeight() * 2;

                    float chanpW = veHUD.chanphai.getWidth() * 2;
                    float chanpH = veHUD.chanphai.getHeight() * 2;
                    float thanpW = veHUD.thanphai.getWidth() * 2;
                    float thanpH = veHUD.thanphai.getHeight() * 2;
                    float daupW = veHUD.dauphai.getWidth() * 2;
                    float daupH = veHUD.dauphai.getHeight() * 2;

                    // Vẽ bên trái
                    batch.draw(veHUD.chantrai, xTrai, y, chantW, chantH);
                    float thantX = xTrai + (chantW / 2f - thantW / 2f);
                    float thantY = y + chantH;
                    float dautX = xTrai + (chantW / 2f - dautW / 2f);
                    float dautY = thantY + thantH;
                    batch.draw(veHUD.thantrai, thantX, thantY - 10.2f, thantW, thantH);
                    batch.draw(veHUD.dautrai, dautX, dautY - 14f, dautW, dautH);

                    // Vẽ bên phải
                    batch.draw(veHUD.chanphai, xPhai, y, chanpW, chanpH);
                    float thanpX = xPhai + (chanpW / 2f - thanpW / 2f);
                    float thanpY = y + chanpH;
                    float daupX = xPhai + (chanpW / 2f - daupW / 2f);
                    float daupY = thanpY + thanpH;
                    batch.draw(veHUD.thanphai, thanpX, thanpY - 10.2f, thanpW, thanpH);
                    batch.draw(veHUD.dauphai, daupX, daupY - 14f, daupW, daupH);
                } else if (veHUD.timeChoHopThe > 0.5f) {
                    float chantW = veHUD.chantrai.getWidth() * 2;
                    float chantH = veHUD.chantrai.getHeight() * 2;
                    float thantW = veHUD.thantrai.getWidth() * 2;
                    float thantH = veHUD.thantrai.getHeight() * 2;
                    float dautW = veHUD.dautrai.getWidth() * 2;
                    float dautH = veHUD.dautrai.getHeight() * 2;

                    float chanpW = veHUD.chanphai.getWidth() * 2;
                    float chanpH = veHUD.chanphai.getHeight() * 2;
                    float thanpW = veHUD.thanphai.getWidth() * 2;
                    float thanpH = veHUD.thanphai.getHeight() * 2;
                    float daupW = veHUD.dauphai.getWidth() * 2;
                    float daupH = veHUD.dauphai.getHeight() * 2;
                    if (!flipX) {
                        // Vẽ bên trái
                        batch.draw(veHUD.chantrai, x, y, chantW, chantH);
                        float thantX = x + (chantW / 2f - thantW / 2f);
                        float thantY = y + chantH;
                        float dautX = x + (chantW / 2f - dautW / 2f);
                        float dautY = thantY + thantH;
                        batch.draw(veHUD.thantrai, thantX, thantY - 10.2f, thantW, thantH);
                        batch.draw(veHUD.dautrai, dautX, dautY - 14f, dautW, dautH);
                    } else {
                        batch.draw(veHUD.chanphai, x, y, chanpW, chanpH);
                        float thanpX = x + (chanpW / 2f - thanpW / 2f);
                        float thanpY = y + chanpH;
                        float daupX = x + (chanpW / 2f - daupW / 2f);
                        float daupY = thanpY + thanpH;
                        batch.draw(veHUD.thanphai, thanpX, thanpY - 10.2f, thanpW, thanpH);
                        batch.draw(veHUD.dauphai, daupX, daupY - 14f, daupW, daupH);
                    }
                }
            } else if (veHUD.dangHopThe) {
                float timeHieuUng;
                if (veHUD.dangHopTheThuong) {
                    timeHieuUng = veHUD.timeHopTheTHuong;
                } else {
                    timeHieuUng = veHUD.timeChoHopThe;
                }
                if (timeHieuUng > 0.5f && timeHieuUng< 1.4f) {
                    int tick = (int)(timeHieuUng * 22);
                    if (tick % 2 == 0) {
                        float chantW = veHUD.chantrai.getWidth() * 2;
                        float chantH = veHUD.chantrai.getHeight() * 2;
                        float thantW = veHUD.thantrai.getWidth() * 2;
                        float thantH = veHUD.thantrai.getHeight() * 2;
                        float dautW = veHUD.dautrai.getWidth() * 2;
                        float dautH = veHUD.dautrai.getHeight() * 2;

                        float chanpW = veHUD.chanphai.getWidth() * 2;
                        float chanpH = veHUD.chanphai.getHeight() * 2;
                        float thanpW = veHUD.thanphai.getWidth() * 2;
                        float thanpH = veHUD.thanphai.getHeight() * 2;
                        float daupW = veHUD.dauphai.getWidth() * 2;
                        float daupH = veHUD.dauphai.getHeight() * 2;
                        if (!flipX) {
                            // Vẽ bên trái
                            batch.draw(veHUD.chantrai, x, y, chantW, chantH);
                            float thantX = x + (chantW / 2f - thantW / 2f);
                            float thantY = y + chantH;
                            float dautX = x + (chantW / 2f - dautW / 2f);
                            float dautY = thantY + thantH;
                            batch.draw(veHUD.thantrai, thantX, thantY - 10.2f, thantW, thantH);
                            batch.draw(veHUD.dautrai, dautX, dautY - 14f, dautW, dautH);
                        } else {
                            batch.draw(veHUD.chanphai, x, y, chanpW, chanpH);
                            float thanpX = x + (chanpW / 2f - thanpW / 2f);
                            float thanpY = y + chanpH;
                            float daupX = x + (chanpW / 2f - daupW / 2f);
                            float daupY = thanpY + thanpH;
                            batch.draw(veHUD.thanphai, thanpX, thanpY - 10.2f, thanpW, thanpH);
                            batch.draw(veHUD.dauphai, daupX, daupY - 14f, daupW, daupH);
                        }
                    }
                } else if (timeHieuUng <= 0.5f) {
                    // Tính phần trăm đã đi
                    float alpha = (0.5f - timeHieuUng) / 0.3f;
                    alpha = Math.min(Math.max(alpha, 0f), 1f); // đảm bảo nằm trong 0→1

                    // Tính vị trí hai bên chạy về x
                    float xTrai = x - 70 * alpha;
                    float xPhai = x + 70 * alpha;

                    float chantW = veHUD.chantrai.getWidth() * 2;
                    float chantH = veHUD.chantrai.getHeight() * 2;
                    float thantW = veHUD.thantrai.getWidth() * 2;
                    float thantH = veHUD.thantrai.getHeight() * 2;
                    float dautW = veHUD.dautrai.getWidth() * 2;
                    float dautH = veHUD.dautrai.getHeight() * 2;

                    float chanpW = veHUD.chanphai.getWidth() * 2;
                    float chanpH = veHUD.chanphai.getHeight() * 2;
                    float thanpW = veHUD.thanphai.getWidth() * 2;
                    float thanpH = veHUD.thanphai.getHeight() * 2;
                    float daupW = veHUD.dauphai.getWidth() * 2;
                    float daupH = veHUD.dauphai.getHeight() * 2;

                    // Vẽ bên trái
                    batch.draw(veHUD.chantrai, xTrai, y, chantW, chantH);
                    float thantX = xTrai + (chantW / 2f - thantW / 2f);
                    float thantY = y + chantH;
                    float dautX = xTrai + (chantW / 2f - dautW / 2f);
                    float dautY = thantY + thantH;
                    batch.draw(veHUD.thantrai, thantX, thantY - 10.2f, thantW, thantH);
                    batch.draw(veHUD.dautrai, dautX, dautY - 14f, dautW, dautH);

                    // Vẽ bên phải
                    batch.draw(veHUD.chanphai, xPhai, y, chanpW, chanpH);
                    float thanpX = xPhai + (chanpW / 2f - thanpW / 2f);
                    float thanpY = y + chanpH;
                    float daupX = xPhai + (chanpW / 2f - daupW / 2f);
                    float daupY = thanpY + thanpH;
                    batch.draw(veHUD.thanphai, thanpX, thanpY - 10.2f, thanpW, thanpH);
                    batch.draw(veHUD.dauphai, daupX, daupY - 14f, daupW, daupH);
                }
            } else {
                float step = 0.2f;
                float timeMax = 2f;
                for (int i = 0; i < 6; i++) {
                    float start = timeMax - i*step;
                    float end = start - step;
                    if (veHUD.timeChoHopThe >= end && veHUD.timeChoHopThe < start) {
                        veNhanVatHopThe(i, batch, x, y);
                        break;
                    }
                }
                if (veHUD.timeChoHopThe >= timeMax-6*step-step*3 && veHUD.timeChoHopThe <= timeMax-6*step) {
                    float chantW = veHUD.chan7.getWidth() * 2.3f;
                    float chantH = veHUD.chan7.getHeight() * 2.3f;
                    float thantW = veHUD.than7.getWidth() * 2.3f;
                    float thantH = veHUD.than7.getHeight() * 2.3f;

                    float chanpW = veHUD.chan7p.getWidth() * 2.3f;
                    float chanpH = veHUD.chan7p.getHeight() * 2.3f;
                    float thanpW = veHUD.than7p.getWidth() * 2.3f;
                    float thanpH = veHUD.than7p.getHeight() * 2.3f;

                    float offsetx = 38.5f;
                    float offsetThanX = -10f;
                    float offsetThanY = 0f;

                    // Vẽ bên trái
                    batch.draw(veHUD.chan7, x - offsetx, y, chantW, chantH);
                    float thantX = x + (chantW / 2f - thantW / 2f);
                    float thantY = y + chantH;
                    batch.draw(veHUD.than7, thantX - offsetx - offsetThanX, thantY - offsetThanY, thantW, thantH);

                    // Vẽ bên phải
                    batch.draw(veHUD.chan7p, x + offsetx, y, chanpW, chanpH);
                    float thanpX = x + (chanpW / 2f - thanpW / 2f);
                    float thanpY = y + chanpH;
                    batch.draw(veHUD.than7p, thanpX + offsetx + offsetThanX, thanpY - offsetThanY, thanpW, thanpH);
                }
            }
        }
        if (veHUD.dangDungAura && veHUD.timeChoHopThe == 0 && duDieuKien && trangThai == TrangThai.DUNG_YEN) {
            float tiLe = 0.35f;
            if (veHUD.dangHopThe) tiLe = 0.4f;
            if (veHUD.dangBienKhi) tiLe = 0.55f;
            float flipScale = flipX ? -1f : 1f;
            float anchorX2 = flipX ? x + rong + (auraChan[veHUD.framesAura].getWidth()*tiLe-rong_chan)/2f : x - (auraChan[veHUD.framesAura].getWidth()*tiLe-rong_chan)/2f;
            batch.draw(auraChan[veHUD.framesAura], anchorX2, y, auraChan[veHUD.framesAura].getWidth() * tiLe * flipScale, auraChan[veHUD.framesAura].getHeight() * tiLe);
        }
    }
    public static DoLechModular layLech(Map<TrangThai, List<DoLechModular>> map, TrangThai trangThai, int frameIndex, boolean dangMangVanBay) {
        List<DoLechModular> ds;
        if (trangThai == TrangThai.BAY_NGANG) {
            if (dangMangVanBay) {
                ds = map.get(TrangThai.DUNG_YEN);
            } else {
                ds = map.get(trangThai);
            }
        } else {
            ds = map.get(trangThai);
        }
        if (ds == null || ds.isEmpty()) {
            return new DoLechModular(0, 0, 0, 0,0,0); // fallback nếu thiếu dữ liệu
        }

        if (trangThai == TrangThai.DI_CHUYEN) {
            return ds.get(frameIndex % ds.size()); // vòng lặp theo frame
        } else {
            return ds.get(0); // chỉ có 1 frame cho trạng thái khác
        }
    }
    void veNhanVatHopThe(int index, SpriteBatch batch, float x, float y) {
        float chantW = veHUD.chan[index].getWidth() * 2;
        float chantH = veHUD.chan[index].getHeight() * 2;
        float thantW = veHUD.than[index].getWidth() * 2;
        float thantH = veHUD.than[index].getHeight() * 2;
        float dautW = veHUD.dau[index].getWidth() * 2;
        float dautH = veHUD.dau[index].getHeight() * 2;

        float chanpW = veHUD.chanp[index].getWidth() * 2;
        float chanpH = veHUD.chanp[index].getHeight() * 2;
        float thanpW = veHUD.thanp[index].getWidth() * 2;
        float thanpH = veHUD.thanp[index].getHeight() * 2;
        float daupW = veHUD.daup[index].getWidth() * 2;
        float daupH = veHUD.daup[index].getHeight() * 2;

        float offsetx = veHUD.offsetX[index];
        float offsetThanX = veHUD.offsetThanX[index];
        float offsetThanY = veHUD.offsetThanY[index];
        float offsetDauX = veHUD.offsetDauX[index];
        float offsetDauY = veHUD.offsetDauY[index];

        // Vẽ bên trái
        batch.draw(veHUD.chan[index], x - offsetx, y, chantW, chantH);
        float thantX = x + (chantW / 2f - thantW / 2f);
        float thantY = y + chantH;
        float dautX = x + (chantW / 2f - dautW / 2f);
        float dautY = thantY + thantH;
        batch.draw(veHUD.than[index], thantX - offsetx - offsetThanX, thantY - offsetThanY, thantW, thantH);
        batch.draw(veHUD.dau[index], dautX - offsetx - offsetDauX, dautY - offsetDauY, dautW, dautH);

        // Vẽ bên phải
        batch.draw(veHUD.chanp[index], x + offsetx, y, chanpW, chanpH);
        float thanpX = x + (chanpW / 2f - thanpW / 2f);
        float thanpY = y + chanpH;
        float daupX = x + (chanpW / 2f - daupW / 2f);
        float daupY = thanpY + thanpH;
        batch.draw(veHUD.thanp[index], thanpX + offsetx + offsetThanX, thanpY - offsetThanY, thanpW, thanpH);
        batch.draw(veHUD.daup[index], daupX + offsetx + offsetDauX, daupY - offsetDauY, daupW, daupH);
    }

    public void setDuLieuNguoiChoi(DuLieuNguoiChoi duLieuNguoiChoi) {
        this.duLieuNguoiChoi = duLieuNguoiChoi;
    }
    public TrangThai getTrangThai() {
        return this.trangThai;
    }

    public boolean getFlipX() {
        return this.flipX;
    }

    public int getFrame() {
        return this.frame;
    }
    private void ketThucDiChuyen() {
        diChuyenDenMucTieu = false;
        phimTraiDangGiu = false;
        phimPhaiDangGiu = false;
        phimNhayDangGiu = false;
        vx = 0;
        vy = 0;
    }

    public void veTaiTaoNangLuong(SpriteBatch batch,float x, float y) {
        timeDoiFramesTtnl += Gdx.graphics.getDeltaTime();
        if (timeDoiFramesTtnl > 0.06f) {
            frameTtnl = (frameTtnl+1)%ttnl.length;
            timeDoiFramesTtnl = 0;
        }
        float tl = 0.5f;
        if (veHUD.dangBienKhi || veHUD.dangHopThe) tl = 0.55f;
        batch.draw(ttnl[frameTtnl],x-ttnl[frameTtnl].getWidth()*tl/2f,y,ttnl[frameTtnl].getWidth()*tl,ttnl[frameTtnl].getHeight()*tl);
    }
    public void veQckk(SpriteBatch batch,float x, float y) {
        timeDoiFramesQckk += Gdx.graphics.getDeltaTime();
        if (timeDoiFramesQckk > 0.1f) {
            frameBom = (frameBom+1)%bom.length;
            timeDoiFramesQckk = 0;
        }
        batch.setColor(1,1,1,0.9f);
        batch.draw(bom[frameBom],x-bom[frameBom].getWidth()*0.5f/2f,y-bom[frameBom].getHeight()*0.5f/2f,bom[frameBom].getWidth()*0.5f,bom[frameBom].getHeight()*0.5f);
        batch.setColor(1,1,1,1f);
    }

    public void veHuytSao(SpriteBatch batch) {
        if (chuaSetTimeHuytSao) {
            timeHieuUngHuytSao = 0.9f;
            chuaSetTimeHuytSao = false;
        }

        float x_sao_base = x + rong / 2f - 10;
        float y_sao_base = y + cao + 10f;
        float tiLe = 0.5f;

        timeHieuUngHuytSao -= Gdx.graphics.getDeltaTime();
        if (timeHieuUngHuytSao < 0) timeHieuUngHuytSao = 0;

        float t = timeHieuUngHuytSao % 0.3f;

        if (t >= 0.25f && t < 0.3f) {
            batch.draw(saoDo[0], x_sao_base, y_sao_base,
                saoDo[0].getWidth()*tiLe, saoDo[0].getHeight()*tiLe);
        }
        else if (t >= 0.2f && t < 0.25f) {
            batch.draw(saoDo[0], x_sao_base, y_sao_base+5f,
                saoDo[0].getWidth()*tiLe, saoDo[0].getHeight()*tiLe);
            batch.draw(saoXanh[0], x_sao_base+25f, y_sao_base-12f,
                saoXanh[0].getWidth()*tiLe, saoXanh[0].getHeight()*tiLe);
        }
        else if (t >= 0.15f && t < 0.2f) {
            batch.draw(saoDo[1], x_sao_base, y_sao_base+5f,
                saoDo[1].getWidth()*tiLe, saoDo[1].getHeight()*tiLe);
            batch.draw(saoXanh[0], x_sao_base+25f, y_sao_base-12f,
                saoXanh[0].getWidth()*tiLe, saoXanh[0].getHeight()*tiLe);
            batch.draw(saoVang[0], x_sao_base+45f, y_sao_base-21f,
                saoVang[0].getWidth()*tiLe, saoVang[0].getHeight()*tiLe);
        }
        else if (t >= 0.1f && t < 0.15f) {
            batch.draw(saoDo[1], x_sao_base, y_sao_base+8f,
                saoDo[1].getWidth()*tiLe, saoDo[1].getHeight()*tiLe);
            batch.draw(saoXanh[1], x_sao_base+20f, y_sao_base-9f,
                saoXanh[1].getWidth()*tiLe, saoXanh[1].getHeight()*tiLe);
            batch.draw(saoVang[0], x_sao_base+50f, y_sao_base-21f,
                saoVang[0].getWidth()*tiLe, saoVang[0].getHeight()*tiLe);
        }
        else if (t >= 0.05f && t < 0.1f) {
            batch.draw(saoDo[2], x_sao_base-5f, y_sao_base+3f,
                saoDo[2].getWidth()*tiLe, saoDo[2].getHeight()*tiLe);
            batch.draw(saoXanh[2], x_sao_base+15f, y_sao_base-14f,
                saoXanh[2].getWidth()*tiLe, saoXanh[2].getHeight()*tiLe);
            batch.draw(saoVang[1], x_sao_base+60f, y_sao_base-19f,
                saoVang[1].getWidth()*tiLe, saoVang[1].getHeight()*tiLe);
        }
        else if (t > 0f && t < 0.05f) {
            batch.draw(saoDo[2], x_sao_base-15f, y_sao_base-13f,
                saoDo[2].getWidth()*tiLe, saoDo[2].getHeight()*tiLe);
            batch.draw(saoXanh[2], x_sao_base+13f, y_sao_base-9f,
                saoXanh[2].getWidth()*tiLe, saoXanh[2].getHeight()*tiLe);
            batch.draw(saoVang[1], x_sao_base+63f, y_sao_base-14f,
                saoVang[1].getWidth()*tiLe, saoVang[1].getHeight()*tiLe);
        }
    }

    public DuLieuNguoiChoi getDuLieuNguoiChoi() {
        return duLieuNguoiChoi;
    }

    public VeHUD getVeHUD() {
        return veHUD;
    }

    public void dispose() {
        if (veHUD != null) veHUD.dispose();
        if (avtTexture != null) {
            avtTexture.dispose();
            avtTexture = null;
        }
        // Các phần thân thể
        dau_dung.dispose();
        dau_chay.dispose();
        than_dung.dispose();
        than_nhay.dispose();
        than_roi.dispose();
        chan_dung.dispose();
        chan_nhay.dispose();
        chan_roi.dispose();
        than_bay.dispose();
        chan_bay.dispose();

        // Mảng chạy
        for (Texture t : than_chay) {
            t.dispose();
        }
        for (Texture t : chan_chay) {
            t.dispose();
        }

        // clickdichuyen
        for (Texture t : clickdichuyen) {
            t.dispose();
        }

        // auraChan
        for (Texture t : auraChan) {
            t.dispose();
        }

        for (Texture t : ttnl) {
            t.dispose();
        }

        for (Texture t : bom) {
            t.dispose();
        }

        for (Texture t : saoDo) {
            t.dispose();
        }

        for (Texture t : saoXanh) {
            t.dispose();
        }

        for (Texture t : saoVang) {
            t.dispose();
        }


        // vanBayCauHinh
        if (vanBayCauHinh != null && vanBayCauHinh.frames != null) {
            for (Texture t : vanBayCauHinh.frames) {
                t.dispose();
            }
        }

        // batch, shapeRenderer
        if (batch != null) batch.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}
