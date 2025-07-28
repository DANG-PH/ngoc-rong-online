package com.dang.dragonboy.du_lieu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.item.Item;
import com.badlogic.gdx.graphics.Texture;
import com.dang.dragonboy.hien_thi.VeHUD;
import java.util.*;

import com.badlogic.gdx.math.MathUtils;
import com.dang.dragonboy.nhan_vat.DeTuCauHinh;
import com.dang.dragonboy.nhan_vat.DeTuXuLy;
import com.dang.dragonboy.nhan_vat.DoLechModular;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.he_thong.TrangThaiChu;
import com.dang.dragonboy.nhan_vat.TrangThai;
import java.util.LinkedList;
import com.dang.dragonboy.nhan_vat.NhanVat;

import java.util.List;
import java.util.ArrayList;

import com.dang.dragonboy.xu_ly_map.HitboxDat;

public class DeTu {
    private NhanVat nhanVat;
    private Texture texAvtDeTu;
    private final float delayRoi = 15f;
    private final float trongLuc = -0.5f;
    private final float tocDoDiChuyen = 6f;
    private float gioiHanXMax;
    private float gioiHanYMax;
    private List<HitboxDat> danhSachDat = new ArrayList<>();
    float vx, vy;
    boolean dangDungDat, dangBayNgang, daNhay;
    int demThoiGianBay;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    public float x, y;
    public float rong_de_tu, cao_de_tu;
    private final float tiLe = 0.5f;
    private Map<TrangThaiDeTu, List<DoLechModular>> lechTheoTrangThai = new HashMap<>();
    public Map<String,Texture> iconSkill = new HashMap<>();
    private TrangThaiDeTu trangThai = TrangThaiDeTu.DUNG_YEN;
    private String ten;
    private String[] danhSachHanhTinh = {"traidat","xayda","namek"};
    private String hanhtinh;
    private long sucManh;
    private int theLuc;
    private float HpDeTu,KiDeTu,SucDanhDeTu;
    private int GiapDeTu,ChiMangDeTu;
    private int HpGoc;
    private int KiGoc;
    private float HpHienTai;
    private float KiHienTai;
    private int SucDanhGoc;
    private int GiapGoc;
    private int ChiMangGoc;
    private int SatThuongChiMang;
    private long TiemNangDeTu;
    private int GiamSatThuongDeTu;
    private Texture avtDeTu;
    private String capBac;

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

    private String trangthai = "Đi theo";

    private int[] capSkill = new int[4];  // Mặc định toàn 0
    private String[] tenSkill = new String[4];
    private String[] danhSachSkill1 = {"Chiêu đấm Galick","Chiêu đấm Dragon","Chiêu đấm Demon"};
    private String[] danhSachSkill2 = {"Chiêu Kamejoko","Chiêu Kamejoko","Chiêu Kamejoko"};
    private String[] danhSachSkill3 = {"Thái Dương Hạ San","Tái tạo năng lượng","Kaioken"};
    private String[] danhSachSkill4 = {"Biến hình","Khiên năng lượng","Đẻ trứng"};

    private boolean flipX;
    private int frame;
    private float lechThanX = 0f, lechThanY = 0f;
    private float lechDauX = 0f, lechDauY = 0f;
    private float timeChay = 0f;

    private BitmapFont fontTenDeTu;
    private int frameVanBay = 0;
    private float timeVanBay = 0f;
    private Texture[] vanBayCauHinh;

    private ArrayList<Item> hanhTrangDangMac = new ArrayList<>(6);
    {
        for (int i = 0; i < 6; i++) {
            hanhTrangDangMac.add(null);
        }
    }
    private boolean dangmacao = false;
    private boolean dangmacquan = false;
    private boolean dangmacgang = false;
    private boolean dangmacgiay = false;
    private boolean dangmacrada = false;
    private boolean setKichHoatNappa = false;

    private String avtdangmac;

    public Texture dau_dung, dau_chay;
    public Texture than_dung, than_nhay, than_roi;
    public Texture[] than_chay;
    public Texture than_bay;
    public Texture chan_dung, chan_nhay, chan_roi;
    public Texture[] chan_chay;
    public Texture chan_bay;
    private boolean chuaFixAvtAoQuan = true;

    private VeHUD veHUD;

    private String tinNhanDeTuChat;
    private float timeHienChat;

    private float timeChoHienBay;
    // Dùng cho hành vi di chuyển quanh sư phụ
    private boolean diChuyenXungQuanh = false;
    private float timeDungYen = 0f;
    private float timeChuyenHuong = 0f;
    private boolean diQuaPhai = true; // ban đầu chạy qua phải (theo flip ban đầu)
    boolean dangDoiFlip = false;
    boolean vuaspawn = true;
    float timeCooldownDash = 0f;
    final float DASH_COOLDOWN = 2.0f; // 2 giây cooldown sau mỗi lần dash
    float x_truoc_dash;
    float y_truoc_dash;
    boolean flip_truoc_dash = false;
    private Texture dau_tele,than_tele,chan_tele;
    public Texture[] bien_mat = new Texture[3];
    public boolean hoatAnhBienMat = false;
    public float timeHoatAnhBienMat = 0f;
    public float x_bien_mat,y_bien_mat;
    public boolean chuaLayToaDoBienMat = true;

    float timeBayKoTienLaiGan = 0;
    float khoangCachCu = -1;
    private int soLanBiKet = 0;
    private boolean camDiChuyenXungQuanh = false;

    public boolean chuaSetTenDeTu = true;

    public DeTu(float x, float y,boolean flipX,boolean diQuaPhai,String ten, String hanhtinh, Texture dau_dung, Texture dau_chay,
                Texture than_dung, Texture than_nhay, Texture than_roi, Texture[] than_chay,
                Texture chan_dung, Texture chan_nhay, Texture chan_roi, Texture[] chan_chay,
                Texture than_bay, Texture chan_bay, Map<TrangThaiDeTu, List<DoLechModular>> lechTheoTrangThai,
                Texture ao, Texture quan, Texture gang, Texture giay, Texture rada, Texture iconct,NhanVat nhanVat) {
        shapeRenderer = new ShapeRenderer();
        layout = new GlyphLayout();
        this.ten = ten;
        this.nhanVat = nhanVat;
        this.hanhtinh = hanhtinh;
        this.sucManh = 1_499_999L;
        this.theLuc = 50;
        this.HpGoc = 550000;
        this.KiGoc = 550000;
        this.HpDeTu = HpGoc;
        this.KiDeTu = KiGoc;
        this.HpHienTai = HpDeTu*0.9f;
        this.KiHienTai = HpDeTu*0.8f;
        this.SucDanhGoc = 25000;
        this.SucDanhDeTu = SucDanhGoc;
        this.GiapGoc = 200;
        this.GiapDeTu = GiapGoc;
        this.ChiMangGoc = 10;
        this.ChiMangDeTu = ChiMangGoc;
        this.SatThuongChiMang = 150;
        this.TiemNangDeTu = 99999999999L;
        iconSkill.put("Chiêu đấm Galick",new Texture("kynang/iconkynang/xayda/skill1_xayda.png"));
        iconSkill.put("Chiêu đấm Dragon",new Texture("kynang/iconkynang/xayda/skill1_xayda.png"));
        iconSkill.put("Chiêu đấm Demon",new Texture("kynang/iconkynang/xayda/skill1_xayda.png"));
        iconSkill.put("Chiêu Kamejoko",new Texture("kynang/iconkynang/xayda/skill2_xayda.png"));
        iconSkill.put("Chiêu Antomic",new Texture("kynang/iconkynang/xayda/skill2_xayda.png"));
        iconSkill.put("Chiêu Masenko",new Texture("kynang/iconkynang/xayda/skill2_xayda.png"));
        iconSkill.put("Thái Dương Hạ San",new Texture("kynang/iconkynang/traidat/skill3_traidat.png"));
        iconSkill.put("Tái tạo năng lượng",new Texture("kynang/iconkynang/xayda/skill3_xayda.png"));
        iconSkill.put("Kaioken",new Texture("kynang/iconkynang/traidat/skill4_traidat.png"));
        iconSkill.put("Biến hình",new Texture("kynang/iconkynang/xayda/skill4_xayda.png"));
        iconSkill.put("Khiên năng lượng",new Texture("kynang/iconkynang/xayda/skill9_xayda.png"));
        iconSkill.put("Đẻ trứng",new Texture("kynang/iconkynang/namek/skill5_namek.png"));
        for (int i = 0; i < 4; i++) {
            capSkill[i]=1;
        }
        if (sucManh >= 0 && tenSkill[0]==null) {
            tenSkill[0] = danhSachSkill1[MathUtils.random(danhSachSkill1.length - 1)];
        }
        if (sucManh >= 150_000_000 && tenSkill[1]==null) {
            tenSkill[1] = danhSachSkill2[MathUtils.random(danhSachSkill2.length - 1)];
        }
        if (sucManh >= 1_500_000_000 && tenSkill[2]==null) {
            tenSkill[2] = danhSachSkill3[MathUtils.random(danhSachSkill3.length - 1)];
        }
        if (sucManh >= 20_000_000_000L && tenSkill[3]==null) {
            tenSkill[3] = danhSachSkill4[MathUtils.random(danhSachSkill4.length - 1)];
        }
        this.avtdangmac = this.hanhtinh+"_base";
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

        this.rong_de_tu = chan_dung.getWidth() * tiLe;
        this.cao_de_tu = chan_dung.getHeight() * tiLe + than_dung.getHeight() * tiLe + dau_dung.getHeight() * 0.15f;
        this.x = x;
        this.y = y;

        this.lechTheoTrangThai = lechTheoTrangThai;

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchinh.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param2.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể ộ ứ ỹ ệ ợ ặ ề ở ự ỷ ị ổ ế ờ ử ắ ỉ ẩ , ỡ ẫ Đ";
        param2.size = 19;
        fontTenDeTu = generator2.generateFont(param2);
        generator2.dispose();

        vanBayCauHinh = new Texture[] {
            new Texture("hieuung/hieuunggame/aura_bay/" + "1.png"),
            new Texture("hieuung/hieuunggame/aura_bay/" + "2.png"),
            new Texture("hieuung/hieuunggame/aura_bay/" + "3.png"),
            new Texture("hieuung/hieuunggame/aura_bay/" + "4.png")
        };
        tinNhanDeTuChat = "Xin hãy thu nhận con làm đệ tử";
        timeHienChat = 3f;

        this.flipX = flipX;
        this.diQuaPhai = diQuaPhai;

        dau_tele = new Texture("hieuung/hieuunggame/tele/dau.png");
        than_tele = new Texture("hieuung/hieuunggame/tele/than.png");
        chan_tele = new Texture("hieuung/hieuunggame/tele/chan.png");

        for (int i = 0; i < 3; i++) {
            bien_mat[i] = new Texture("hieuung/hieuunggame/bien_mat/"+(i+1)+".png");
        }
    }

    public Texture getAvtDeTu() {
        if (texAvtDeTu == null) {
            String Avt_lon_hay_sosinh = sucManh >= 1500000 ? "lon" : "sosinh";
            texAvtDeTu = new Texture("nhanvat/detu/" + hanhtinh + "/avt" + Avt_lon_hay_sosinh + ".png");
        }
        return texAvtDeTu;
    }

    public String getAvtDangMac() {
        return avtdangmac;
    }

    public void setAvtDangMac(String avtdangmac) {
        this.avtdangmac = avtdangmac ;
    }

    public String getHanhtinh() {
        return hanhtinh;
    }

    public String getCapBac() {
        if (hanhtinh.equals("xayda")) {
            capBac = "Thần Xayda cấp 10+99.99%";
        } else if (hanhtinh.equals("traidat")) {
            capBac = "Thần Trái Đất cấp 10+99.99%";
        } else {
            capBac = "Thần Namếc cấp 10+99.99%";
        }
        return capBac;
    }

    public ArrayList<Item> getHanhTrangDangMac() {
        return hanhTrangDangMac;
    }

    public void setItemVaoHanhTrangDangMac(Item item, int index) {
        hanhTrangDangMac.set(index,item);
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getTrangthai() {
        return trangthai;
    }

    // Getter
    public String getTen() { return ten; }
    public long getSucManh() { return sucManh; }
    public int getTheLuc() { return theLuc; }
    public void tangTheLuc(int theLuc) { this.theLuc += theLuc; this.theLuc = Math.min(this.theLuc,100); }
    public float getHpHienTai() { return HpHienTai; }
    public float getHpToiDa() { return HpDeTu; }
    public int getHpGoc() { return HpGoc; }
    public float getKiHienTai() { return KiHienTai; }
    public int getKiGoc() { return KiGoc; }
    public float getKiToiDa() { return KiDeTu; }
    public int getSucDanhGoc() { return SucDanhGoc; }
    public float getSucDanhDeTu() { return SucDanhDeTu; }
    public int getGiapGoc() { return GiapGoc; }
    public int getGiapDeTu() { return GiapDeTu; }
    public int getChiMangGoc() { return ChiMangGoc; }
    public int getChiMangDeTu() { return ChiMangDeTu; }
    public int getSatThuongChiMang() { return SatThuongChiMang; }
    public long getTiemNangDeTu() { return TiemNangDeTu; }
    public int getGiamSatThuongDeTu() { return GiamSatThuongDeTu; }
    public int getCapSkill(int index) {
        if (index >= 0 && index < capSkill.length) {
            return capSkill[index];
        }
        return -1;
    }
    public String getTenSkill(int index) {
        if (index >= 0 && index < tenSkill.length) {
            return tenSkill[index];
        }
        return null;
    }

    public void tangSucManh(long SucManhCongThem){
        this.sucManh += SucManhCongThem;
        if (sucManh >= 0 && tenSkill[0]==null) {
            tenSkill[0] = danhSachSkill1[MathUtils.random(danhSachSkill1.length - 1)];
        }
        if (sucManh >= 150_000_000 && tenSkill[1]==null) {
            tenSkill[1] = danhSachSkill2[MathUtils.random(danhSachSkill2.length - 1)];
            setTinNhanDeTuChat("Sư phụ ơi con lên cấp rồi",3f);
        }
        if (sucManh >= 1_500_000_000 && tenSkill[2]==null) {
            tenSkill[2] = danhSachSkill3[MathUtils.random(danhSachSkill3.length - 1)];
            setTinNhanDeTuChat("Sư phụ ơi con lên cấp rồi",3f);
        }
        if (sucManh >= 20_000_000_000L && tenSkill[3]==null) {
            tenSkill[3] = danhSachSkill4[MathUtils.random(danhSachSkill4.length - 1)];
            setTinNhanDeTuChat("Sư phụ ơi con lên cấp rồi",3f);
        }
        if (sucManh >= 1_500_000 && chuaFixAvtAoQuan) {
            DeTuCauHinh c2 = Doi_avt_ao_quan_DeTu(hanhtinh,hanhtinh+"_base","set_base","set_base");
            fixCaiTrang(
                c2.dau_dung_de_tu, c2.dau_chay_de_tu,
                c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
                c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
                c2.than_bay_de_tu, c2.chan_bay_de_tu,
                c2.lechMapDeTu
            );
            texAvtDeTu = new Texture("nhanvat/detu/" + hanhtinh + "/avt" + "lon" + ".png");
            setTinNhanDeTuChat("Sư phụ ơi con lên cấp rồi",3f);
            chuaFixAvtAoQuan = false;
        }
    }

    public void tangHpGoc(int HpCongThem,boolean choPhepHienThi){
        int[] chisoCaiTrang = getChisoCaiTrang(); // chỉ số cải tranga
        int[] chisoAo = getChisoAo();
        int[] chisoQuan = getChisoQuan();
        int[] chisoGang = getChisoGang();
        int[] chisoGiay = getChisoGiay();
        int[] chisoRada = getChisoRada();
        float tilePhanTramHPctrang = 0;
        float tilePhanTramHPao = 0;
        float tilePhanTramHPquan = 0;
        float tilePhanTramHPgang = 0;
        float tilePhanTramHPgiay = 0;
        float tilePhanTramHPrada = 0;
        float tilePhanTramHPsetkh = 0;

        // Hàm phụ cộng %HP nếu mảng hợp lệ và có phần tử thứ 6
        if (DeTuXuLy.getDangMacCaiTrang() || DeTuXuLy.getDangMacAvatar()) {
            tilePhanTramHPctrang += (chisoCaiTrang != null && chisoCaiTrang.length >= 7) ? chisoCaiTrang[6] : 0;
        }
        if (dangmacao) {
            tilePhanTramHPao += chisoAo[6];
        }
        if (dangmacquan) {
            tilePhanTramHPquan += chisoQuan[6];
        }
        if (dangmacgang) {
            tilePhanTramHPgang += chisoGang[6];
        }
        if (dangmacgiay) {
            tilePhanTramHPgiay += chisoGiay[6];
        }
        if (dangmacrada) {
            tilePhanTramHPrada += chisoRada[6];
        }
        if (setKichHoatNappa){
            tilePhanTramHPsetkh += 80;
        }
        // Tính HP cộng thêm thực tế
        int HpCongThemThucTeCt = Math.round(HpCongThem * (1 + tilePhanTramHPctrang/ 100f));
        int HpCongThemThucTeAo = Math.round(HpCongThemThucTeCt* (1 + tilePhanTramHPao/ 100f));
        int HpCongThemThucTeQuan = Math.round(HpCongThemThucTeAo* (1 + tilePhanTramHPquan/ 100f));
        int HpCongThemThucTeGang = Math.round(HpCongThemThucTeQuan* (1 + tilePhanTramHPgang/ 100f));
        int HpCongThemThucTeGiay = Math.round(HpCongThemThucTeGang* (1 + tilePhanTramHPgiay/ 100f));
        int HpCongThemThucTeRada = Math.round(HpCongThemThucTeGiay* (1 + tilePhanTramHPrada/ 100f));
        int HpCongThemThucTeNappa = Math.round(HpCongThemThucTeRada * (1 + tilePhanTramHPsetkh/ 100f));
        // Cộng vào HpGoc (tối đa 550000)
        if (choPhepHienThi){
            this.HpGoc += HpCongThem;
            if (this.HpGoc >= 550000){
                this.HpGoc = 550000;
            }
        }
        // Cộng vào HP tổng
        this.HpDeTu += HpCongThemThucTeNappa;
    }
    public void giamHpGoc(int HpCongThem){
        int[] chisoCaiTrang = getChisoCaiTrang(); // chỉ số cải trang
        int[] chisoAo = getChisoAo();
        int[] chisoQuan = getChisoQuan();
        int[] chisoGang = getChisoGang();
        int[] chisoGiay = getChisoGiay();
        int[] chisoRada = getChisoRada();
        float tilePhanTramHPctrang = 0;
        float tilePhanTramHPao = 0;
        float tilePhanTramHPquan = 0;
        float tilePhanTramHPgang = 0;
        float tilePhanTramHPgiay = 0;
        float tilePhanTramHPrada = 0;
        float tilePhanTramHPsetkh = 0;
        float tilePhanTramHPglt = 0;
        // Hàm phụ cộng %HP nếu mảng hợp lệ và có phần tử thứ 6
        if (DeTuXuLy.getDangMacCaiTrang() || DeTuXuLy.getDangMacAvatar()) {
            tilePhanTramHPctrang  += (chisoCaiTrang != null && chisoCaiTrang.length >= 7) ? chisoCaiTrang[6] : 0;
        }
        if (dangmacao) {
            tilePhanTramHPao += chisoAo[6];
        }
        if (dangmacquan) {
            tilePhanTramHPquan += chisoQuan[6];
        }
        if (dangmacgang) {
            tilePhanTramHPgang += chisoGang[6];
        }
        if (dangmacgiay) {
            tilePhanTramHPgiay += chisoGiay[6];
        }
        if (dangmacrada) {
            tilePhanTramHPrada += chisoRada[6];
        }
        if (setKichHoatNappa){
            tilePhanTramHPsetkh += 80;
        }
        // Tính HP cộng thêm thực tế
        int HpCongThemThucTeCt = Math.round(HpCongThem * (1 + tilePhanTramHPctrang/ 100f));
        int HpCongThemThucTeAo = Math.round(HpCongThemThucTeCt* (1 + tilePhanTramHPao/ 100f));
        int HpCongThemThucTeQuan = Math.round(HpCongThemThucTeAo* (1 + tilePhanTramHPquan/ 100f));
        int HpCongThemThucTeGang = Math.round(HpCongThemThucTeQuan* (1 + tilePhanTramHPgang/ 100f));
        int HpCongThemThucTeGiay = Math.round(HpCongThemThucTeGang* (1 + tilePhanTramHPgiay/ 100f));
        int HpCongThemThucTeRada = Math.round(HpCongThemThucTeGiay* (1 + tilePhanTramHPrada/ 100f));
        int HpCongThemThucTeNappa = Math.round(HpCongThemThucTeRada * (1 + tilePhanTramHPsetkh/ 100f));
        // Cộng vào HP tổng
        this.HpDeTu -= HpCongThemThucTeNappa;
    }

    public void tangKiGoc(int KiCongThem, boolean choPhepHienThi) {
        int[] chisoCaiTrang = getChisoCaiTrang();
        int[] chisoAo = getChisoAo();
        int[] chisoQuan = getChisoQuan();
        int[] chisoGang = getChisoGang();
        int[] chisoGiay = getChisoGiay();
        int[] chisoRada = getChisoRada();

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0;

        if (DeTuXuLy.getDangMacCaiTrang() || DeTuXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 8) ? chisoCaiTrang[7] : 0;
        }
        if (dangmacao) tileAo += chisoAo[7];
        if (dangmacquan) tileQuan += chisoQuan[7];
        if (dangmacgang) tileGang += chisoGang[7];
        if (dangmacgiay) tileGiay += chisoGiay[7];
        if (dangmacrada) tileRada += chisoRada[7];

        int k1 = Math.round(KiCongThem * (1 + tileCT / 100f));
        int k2 = Math.round(k1 * (1 + tileAo / 100f));
        int k3 = Math.round(k2 * (1 + tileQuan / 100f));
        int k4 = Math.round(k3 * (1 + tileGang / 100f));
        int k5 = Math.round(k4 * (1 + tileGiay / 100f));
        int k6 = Math.round(k5 * (1 + tileRada / 100f));

        if (choPhepHienThi) {
            this.KiGoc += KiCongThem;
            if (this.KiGoc >= 550000) this.KiGoc = 550000;
        }
        this.KiDeTu += k6;
    }
    public void giamKiGoc(int KiCongThem) {
        int[] chisoCaiTrang = getChisoCaiTrang();
        int[] chisoAo = getChisoAo();
        int[] chisoQuan = getChisoQuan();
        int[] chisoGang = getChisoGang();
        int[] chisoGiay = getChisoGiay();
        int[] chisoRada = getChisoRada();

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0;

        if (DeTuXuLy.getDangMacCaiTrang() || DeTuXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 8) ? chisoCaiTrang[7] : 0;
        }
        if (dangmacao) tileAo += chisoAo[7];
        if (dangmacquan) tileQuan += chisoQuan[7];
        if (dangmacgang) tileGang += chisoGang[7];
        if (dangmacgiay) tileGiay += chisoGiay[7];
        if (dangmacrada) tileRada += chisoRada[7];

        int k1 = Math.round(KiCongThem * (1 + tileCT / 100f));
        int k2 = Math.round(k1 * (1 + tileAo / 100f));
        int k3 = Math.round(k2 * (1 + tileQuan / 100f));
        int k4 = Math.round(k3 * (1 + tileGang / 100f));
        int k5 = Math.round(k4 * (1 + tileGiay / 100f));
        int k6 = Math.round(k5 * (1 + tileRada / 100f));

        this.KiDeTu -= k6;
    }
    public void tangSucDanhGoc(int SucDanhCongThem, boolean choPhepHienThi) {
        int[] chisoCaiTrang = getChisoCaiTrang();
        int[] chisoAo = getChisoAo();
        int[] chisoQuan = getChisoQuan();
        int[] chisoGang = getChisoGang();
        int[] chisoGiay = getChisoGiay();
        int[] chisoRada = getChisoRada();

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0;

        if (DeTuXuLy.getDangMacCaiTrang() || DeTuXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 9) ? chisoCaiTrang[8] : 0;
        }
        if (dangmacao) tileAo += chisoAo[8];
        if (dangmacquan) tileQuan += chisoQuan[8];
        if (dangmacgang) tileGang += chisoGang[8];
        if (dangmacgiay) tileGiay += chisoGiay[8];
        if (dangmacrada) tileRada += chisoRada[8];

        int s1 = Math.round(SucDanhCongThem * (1 + tileCT / 100f));
        int s2 = Math.round(s1 * (1 + tileAo / 100f));
        int s3 = Math.round(s2 * (1 + tileQuan / 100f));
        int s4 = Math.round(s3 * (1 + tileGang / 100f));
        int s5 = Math.round(s4 * (1 + tileGiay / 100f));
        int s6 = Math.round(s5 * (1 + tileRada / 100f));

        if (choPhepHienThi) {
            this.SucDanhGoc += SucDanhCongThem;
            if (this.SucDanhGoc >= 25000) this.SucDanhGoc = 25000;
        }
        this.SucDanhDeTu += s6;
    }
    public void giamSucDanhGoc(int SucDanhCongThem) {
        int[] chisoCaiTrang = getChisoCaiTrang();
        int[] chisoAo = getChisoAo();
        int[] chisoQuan = getChisoQuan();
        int[] chisoGang = getChisoGang();
        int[] chisoGiay = getChisoGiay();
        int[] chisoRada = getChisoRada();

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0;

        if (DeTuXuLy.getDangMacCaiTrang() || DeTuXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 9) ? chisoCaiTrang[8] : 0;
        }
        if (dangmacao) tileAo += chisoAo[8];
        if (dangmacquan) tileQuan += chisoQuan[8];
        if (dangmacgang) tileGang += chisoGang[8];
        if (dangmacgiay) tileGiay += chisoGiay[8];
        if (dangmacrada) tileRada += chisoRada[8];

        int s1 = Math.round(SucDanhCongThem * (1 + tileCT / 100f));
        int s2 = Math.round(s1 * (1 + tileAo / 100f));
        int s3 = Math.round(s2 * (1 + tileQuan / 100f));
        int s4 = Math.round(s3 * (1 + tileGang / 100f));
        int s5 = Math.round(s4 * (1 + tileGiay / 100f));
        int s6 = Math.round(s5 * (1 + tileRada / 100f));

        this.SucDanhDeTu -= s6;
    }
    public void tangGiapGoc(int GiapCongThem){
        this.GiapGoc += GiapCongThem;
        this.GiapDeTu += GiapCongThem;
    }
    public void tangChiMangGoc(int ChiMangThem){
        this.ChiMangGoc += ChiMangThem;
        this.ChiMangDeTu += ChiMangThem;
    }

    public void tangHp(int HpCongThem){
        this.HpDeTu += HpCongThem;
    }
    public void tangHpPt(int HpCongThem){ this.HpDeTu *= (1f+HpCongThem/100f); }
    public void tangHpHienTai(int HpCongThem){
        this.HpHienTai += HpCongThem;
        this.HpHienTai = Math.min(HpHienTai,HpDeTu);
    }
    public void tangHpPtHienTai(int HpCongThem){
        this.HpHienTai *= (1f+HpCongThem/100f);
        this.HpHienTai = Math.min(HpHienTai,HpDeTu);
    }
    public void tangKi(int KiCongThem){
        this.KiDeTu += KiCongThem;
    }
    public void tangKiPt(int KiCongThem){ this.KiDeTu *= (1f+KiCongThem/100f); }
    public void tangKiHienTai(int KiCongThem){
        this.KiHienTai += KiCongThem;
        this.KiHienTai = Math.min(KiHienTai,KiDeTu);
    }
    public void tangKiPtHienTai(int KiCongThem){
        this.KiHienTai *= (1f+KiCongThem/100f);
        this.KiHienTai = Math.min(KiHienTai,KiDeTu);
    }
    public void tangSucDanh(int SucDanhCongThem){
        this.SucDanhDeTu += SucDanhCongThem;
    }
    public void tangSucDanhPt(int SucDanhCongThem){
        this.SucDanhDeTu *= (1f+SucDanhCongThem/100f);
    }
    public void tangGiap(int GiapCongThem){
        this.GiapDeTu += GiapCongThem;
    }
    public void tangChiMang(int ChiMangThem){
        this.ChiMangDeTu += ChiMangThem;
    }

    public void tangSatThuongChiMang(int SatThuongChiMangThem){this.SatThuongChiMang += SatThuongChiMangThem;}
    public void tangTiemNang(int TiemNangCongThem){
        this.TiemNangDeTu += TiemNangCongThem;
    }
    public void tangGiamSatThuongDeTu(int PtGiamSatThuong) {
        this.GiamSatThuongDeTu += PtGiamSatThuong;
        this.GiamSatThuongDeTu = Math.min(this.GiamSatThuongDeTu,85);
    }

    public void tangCapSkill(int i) {
        if (i >= 1 && i <= 4 && capSkill[i - 1] < 7) {
            capSkill[i - 1]++;
        }
    }

    public void giamHp(int Hp){
        this.HpDeTu -= Hp;
    }
    public void giamHpPt(int Hp){
        this.HpDeTu /= (1f+Hp/100f);
    }
    public void giamKi(int Ki){
        this.KiDeTu -= Ki;
    }
    public void giamKiPt(int Ki){
        this.KiDeTu /= (1f+Ki/100f);
    }
    public void giamSucDanh(int SucDanh){
        this.SucDanhDeTu -= SucDanh;
    }
    public void giamSucDanhPt(int SucDanh){
        this.SucDanhDeTu /= (1f+SucDanh/100f);
    }
    public void giamGiap(int Giap){
        this.GiapDeTu -= Giap;
    }
    public void giamChiMang(int ChiMang){
        this.ChiMangDeTu -= ChiMang;
    }
    public void giamSatThuongChiMang(int STChiMang){
        this.SatThuongChiMang -= STChiMang;
    }
    public void giamTiemNang(long TiemNang){
        this.TiemNangDeTu -= TiemNang;
    }
    public void giamGiamSatThuongDeTu(int PtGiamSatThuong) {
        this.GiamSatThuongDeTu -= PtGiamSatThuong;
        this.GiamSatThuongDeTu = Math.min(this.GiamSatThuongDeTu,85);
    }

    public void dangMacAo(boolean dangmacAo){
        if (dangmacAo){
            dangmacao = true;
        } else {
            dangmacao = false;
        }
    }
    public void dangMacQuan(boolean dangmacQuan){
        if (dangmacQuan){
            dangmacquan = true;
        } else {
            dangmacquan = false;
        }
    }
    public void dangMacGang(boolean dangmacGang){
        if (dangmacGang){
            dangmacgang = true;
        } else {
            dangmacgang = false;
        }
    }
    public void dangMacGiay(boolean dangmacGiay){
        if (dangmacGiay){
            dangmacgiay = true;
        } else {
            dangmacgiay = false;
        }
    }
    public void dangMacRada(boolean dangmacRada){
        if (dangmacRada){
            dangmacrada = true;
        } else {
            dangmacrada = false;
        }
    }

    public void setNappa(boolean duDieuKien){
        if (duDieuKien){
            setKichHoatNappa = true;
        } else {
            setKichHoatNappa = false;
        }
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


    public void fixCaiTrang
        (Texture dau_dung, Texture dau_chay,
         Texture than_dung, Texture than_nhay, Texture than_roi, Texture[] than_chay,
         Texture chan_dung, Texture chan_nhay, Texture chan_roi, Texture[] chan_chay,
         Texture than_bay, Texture chan_bay, Map<TrangThaiDeTu, List<DoLechModular>> lechTheoTrangThai){

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

        this.rong_de_tu = chan_dung.getWidth() * tiLe;
        this.cao_de_tu = chan_dung.getHeight() * tiLe + than_dung.getHeight() * tiLe + dau_dung.getHeight() * 0.15f;

        this.lechTheoTrangThai = lechTheoTrangThai;
    }
    private DeTuCauHinh Doi_avt_ao_quan_DeTu(String HanhTinh, String TenAvatar , String ao, String quan){
        return DeTuXuLy.xuly_id("avatar_"+HanhTinh+"+"+TenAvatar+"+"+ao+"+"+quan);
    }

    public void capNhatAI(float delta, LinkedList<TrangThaiChu> lichSu, float delayGiay) {
        timeCooldownDash -= delta;
        // Tính frame delay tương ứng
        int frameDelay = (int)(delayGiay / Gdx.graphics.getDeltaTime());

        if (lichSu.size() <= frameDelay) return; // Chưa đủ dữ liệu, skip
        // Lấy trạng thái sư phụ ở thời điểm "delay" trước
        TrangThaiChu trangThaiDelay = lichSu.get(frameDelay);

        float xSuPhu = trangThaiDelay.x;
        float ySuPhu = trangThaiDelay.y;
        float dx = xSuPhu - this.x;
        float dy = ySuPhu - this.y;
        float khoangCach = (float) Math.sqrt(dx*dx+dy*dy);
        boolean phimTraiDangGiu = dx < -80 || (khoangCach > 120 && dx < -5);
        boolean phimPhaiDangGiu = dx > 80 || (khoangCach > 120 && dx > 5);
        if (vuaspawn) {
            if (!phimPhaiDangGiu && !phimTraiDangGiu) {
                if (dx<0) {
                    phimTraiDangGiu = true;
                    phimPhaiDangGiu = false;
                } else {
                    phimPhaiDangGiu = true;
                    phimTraiDangGiu = false;
                }
                vuaspawn = false;
            }
        }
        boolean phimNhayDangGiu = dy > 10f && khoangCach > 30f;

        // Xử lý bay ngang nếu cần
        boolean giuPhimNgang = phimTraiDangGiu || phimPhaiDangGiu;
        // Tắt chạy vòng nếu đang làm hành vi chủ động khác
        if (giuPhimNgang || phimNhayDangGiu || Math.abs(dx) > 100f) {
            diChuyenXungQuanh = false;
            timeDungYen = 0;
            timeChuyenHuong = 0;
        }
        if (dangDungDat && giuPhimNgang && phimNhayDangGiu) {
            vy = 5f;
            dangDungDat = false;
            daNhay = true;
            dangBayNgang = true;
            demThoiGianBay = 0;
        }

        if (dangDungDat && !giuPhimNgang && phimNhayDangGiu && !daNhay  && Math.abs(dy) > 92f) {
            vy = 10f;
            dangDungDat = false;
            daNhay = true;
        }

        if (!dangDungDat) {
            if (!dangBayNgang && giuPhimNgang && phimNhayDangGiu && Math.abs(dy) > 20f) {
                // Cho phép bay lại giữa không trung nếu sư phụ đang bay
                dangBayNgang = true;
                vy = Math.signum(dy) * 4f; // đẩy nhẹ lên hoặc xuống
                demThoiGianBay = 0;
            }
            if (dangBayNgang) {
                if (Math.abs(x - khoangCachCu) < 5f) {
                    timeBayKoTienLaiGan += delta;
                } else {
                    timeBayKoTienLaiGan = 0;
                }
                trangThai = TrangThaiDeTu.BAY_NGANG;
                timeChoHienBay += delta;
                // Di chuyển ngang
                if (phimTraiDangGiu) vx = -tocDoDiChuyen;
                else if (phimPhaiDangGiu) vx = tocDoDiChuyen;
                else if (!diChuyenXungQuanh) vx = 0;

                // Bay lên hoặc xuống để tiệm cận sư phụ
                if (Math.abs(dy) > 10) {
                    vy = Math.signum(dy) * 6f;  // 3.5f là tốc độ bay lên/xuống
                    demThoiGianBay = 0;
                } else {
                    vy = 0;
                    if (!giuPhimNgang && !nhanVat.phimTraiDangGiu && !nhanVat.phimPhaiDangGiu && !nhanVat.phimNhayDangGiu) {
                        demThoiGianBay++;
                    } else {
                        demThoiGianBay = 0; // reset nếu tiếp tục bay
                    }

                    if (demThoiGianBay > delayRoi) {
                        dangBayNgang = false;
                    }
                }
            } else {
                // Chưa bay ngang → bị rơi tự do
                vy += trongLuc;
                timeBayKoTienLaiGan = 0;
                if (!diChuyenXungQuanh) {
                    if (phimTraiDangGiu) vx = -tocDoDiChuyen;
                    else if (phimPhaiDangGiu) vx = tocDoDiChuyen;
                    else vx = 0;
                }
            }
        } else {
            daNhay = false;
            dangBayNgang = false;
            demThoiGianBay = 0;
            timeBayKoTienLaiGan = 0;
            if (!diChuyenXungQuanh) {
                if (phimTraiDangGiu) vx = -tocDoDiChuyen;
                else if (phimPhaiDangGiu) vx = tocDoDiChuyen;
                else vx = 0;
            }
        }
        khoangCachCu = x;
        // Xử lý va chạm từng bước nhỏ
        int steps = 10;
        float dxStep = vx / steps;
        float dyStep = vy / steps;
        dangDungDat = false;
        for (int i = 0; i < steps; i++) {
            x += dxStep;
            for (HitboxDat dat : danhSachDat) {
                if (dat.vaChamBenTrai(x, y, rong_de_tu, cao_de_tu)) {
                    x = dat.x - rong_de_tu;
                    break;
                } else if (dat.vaChamBenPhai(x, y, rong_de_tu, cao_de_tu)) {
                    x = dat.x + dat.width;
                    break;
                }
            }

            y += dyStep;

            for (HitboxDat dat : danhSachDat) {
                if (dat.vaChamTuTren(x, y, rong_de_tu, cao_de_tu, vy)) {
                    y = dat.y + dat.height;
                    vy = 0;
                    dangDungDat = true;
                    daNhay = false;
                    break;
                } else if (dat.vaChamTuDuoi(x, y, rong_de_tu, cao_de_tu, vy)) {
                    y = dat.y - cao_de_tu;
                    vy = 0;
                    break;
                }
            }
        }

        // Cập nhật trạng thái
        if (dangBayNgang) {
            trangThai = TrangThaiDeTu.BAY_NGANG;
        } else if (!dangDungDat) {
            if (vy > 0) trangThai = TrangThaiDeTu.NHAY;
            else {
                trangThai = TrangThaiDeTu.ROI;
            }
            timeChoHienBay = 0;
        }  else {
            if (vx != 0 || diChuyenXungQuanh) {
                trangThai = TrangThaiDeTu.DI_CHUYEN;
            } else {
                trangThai = TrangThaiDeTu.DUNG_YEN;
            }
            timeChoHienBay = 0;
        }
        if (!diChuyenXungQuanh) {
            if (vx > 0) {
                flipX = false;
            } else if (vx < 0) {
                flipX = true;
            }
        }
        if (trangThai == TrangThaiDeTu.BAY_NGANG) {
//            float tocDoHienTai = (float)Math.sqrt(vx * vx + vy * vy); // tốc độ tổng
//            float tocDoVay = Math.max(0.06f, 0.2f - tocDoHienTai * 0.02f);

            timeVanBay += delta;
            if (timeVanBay > 0.06f) {
                frameVanBay = (frameVanBay + 1) % vanBayCauHinh.length;
                timeVanBay = 0;
            }
        }
        // Giới hạn map
        x = Math.max(-1f, Math.min(x, gioiHanXMax+1f));
        y = Math.max(0, Math.min(y, gioiHanYMax));

        if (timeHienChat > 0) {
            timeHienChat -= delta;
            if (timeHienChat <= 0) {
                tinNhanDeTuChat = "";
            }
        }

        // === 3.1 Theo dõi thời gian đứng yên ===
        if (!diChuyenXungQuanh && dangDungDat && vx == 0 && vy == 0 && !dangBayNgang) {
            timeDungYen += delta;
        } else if (!diChuyenXungQuanh) {
            timeDungYen = 0f;
            timeChuyenHuong = 0f;
        }
        // === 3.2 Kích hoạt AI chạy quanh sư phụ nếu đứng yên quá lâu ===
        if (camDiChuyenXungQuanh) {
            if (nhanVat.phimTraiDangGiu || nhanVat.phimPhaiDangGiu || nhanVat.phimNhayDangGiu) {
                camDiChuyenXungQuanh = false;
            }
        }
        if (timeDungYen > 3f && !diChuyenXungQuanh && !camDiChuyenXungQuanh) {
            diChuyenXungQuanh = true;
            if (!this.getTrangthai().equals("Về nhà")) {
                String[] text = {
                    "Sư phụ con sẽ bảo vệ người",
                    "Con sẽ không để ai làm hại sư phụ",
                    "Đi tuần quanh đây một chút...",
                    "Hình như có nguy hiểm gần đây..."
                };
                setTinNhanDeTuChat(text[MathUtils.random(text.length-1)], 2f);
            }
            diQuaPhai = (x < xSuPhu); // đúng hướng vị trí hiện tại
            timeChuyenHuong = 0f;
        }

        if (diChuyenXungQuanh) {
            float huongDi = Math.signum(xSuPhu + (diQuaPhai ? 70f : -70f));

            if (dangDoiFlip) {
                vx = 0;
                timeChuyenHuong += delta;
                this.trangThai = TrangThaiDeTu.DUNG_YEN;
                if (timeChuyenHuong > 2f) {
                    diQuaPhai = x < xSuPhu;
                    flipX = !diQuaPhai;
                    this.trangThai = TrangThaiDeTu.DI_CHUYEN;
                    dangDoiFlip = false;
                    timeChuyenHuong = 0;
                }
            } else {
                if (Math.abs(xSuPhu + (diQuaPhai ? 70f : -70f)- x) <= 8f) {
                    dangDoiFlip = true;
                    vx = 0;
                    timeChuyenHuong = 0;
                } else {
                    // === Kiểm tra có đất phía trước và không đâm tường ===
                    float xKiemTra = x + (diQuaPhai ? rong_de_tu + 4 : -4);
                    float yKiemTra = y - 1; // ngay dưới chân
                    boolean coDatPhiaTruoc = false;
                    boolean biChanPhiaTruoc = false;

                    for (HitboxDat dat : danhSachDat) {
                        if (dat.vaChamTuTren(xKiemTra, yKiemTra, 1, 1, 0)) {
                            coDatPhiaTruoc = true;
                        }
                        if (diQuaPhai && dat.vaChamBenPhai(x, y, rong_de_tu, cao_de_tu)) {
                            biChanPhiaTruoc = true;
                        } else if (!diQuaPhai && dat.vaChamBenTrai(x, y, rong_de_tu, cao_de_tu)) {
                            biChanPhiaTruoc = true;
                        }
                    }
                    if (!coDatPhiaTruoc && ySuPhu < y) {
                        // Nếu đi được thì reset đếm kẹt
                        soLanBiKet = 0;

                        // Cho đi bình thường
                        boolean matQuaySangPhai = !flipX;
                        if ((huongDi > 0 && matQuaySangPhai) || (huongDi < 0 && !matQuaySangPhai)) {
                            vx = huongDi * tocDoDiChuyen * 0.65f;
                        } else {
                            vx = -huongDi * tocDoDiChuyen * 0.65f;
                        }
                    } else if (!coDatPhiaTruoc || biChanPhiaTruoc) {
                        // Nếu bị kẹt liên tục thì reset vòng chạy
                        soLanBiKet++;

                        if (soLanBiKet >= 2) {
                            diChuyenXungQuanh = false;
                            soLanBiKet = 0;
                            camDiChuyenXungQuanh = true;
                            String[] text = {
                                "Con không qua được!",
                                "Chỗ này bị chắn mất rồi...",
                                "Hình như không đi tiếp được...",
                                "Sư phụ, chỗ này bí lắm!"
                            };
                            setTinNhanDeTuChat(text[MathUtils.random(text.length-1)], 1.5f);
                        } else {
                            dangDoiFlip = true;
                            vx = 0;
                            timeChuyenHuong = 0;
                        }
                    } else {
                        // Nếu đi được thì reset đếm kẹt
                        soLanBiKet = 0;

                        // Cho đi bình thường
                        boolean matQuaySangPhai = !flipX;
                        if ((huongDi > 0 && matQuaySangPhai) || (huongDi < 0 && !matQuaySangPhai)) {
                            vx = huongDi * tocDoDiChuyen * 0.65f;
                        } else {
                            vx = -huongDi * tocDoDiChuyen * 0.65f;
                        }
                    }
                }
            }
        }
        // Dash tức thời nếu cách quá xa và cooldown đã hồi
        if ((khoangCach > 150f || (timeBayKoTienLaiGan > 0.2f && nhanVat.getTrangThai() == TrangThai.DUNG_YEN)) && timeCooldownDash <= 0) {
            if (trangThai == TrangThaiDeTu.BAY_NGANG) {
                trangThai = TrangThaiDeTu.ROI;
                dangBayNgang = false;
            }
            timeBayKoTienLaiGan = 0;
            float huong = Math.signum(dx); // hướng về phía sư phụ
            x_truoc_dash = x;
            y_truoc_dash = y;
            flip_truoc_dash = flipX;
            x += huong * 120f; // dịch chuyển 1 phát
            y = ySuPhu+10;
            flipX = huong <= 0;
            if (!this.getTrangthai().equals("Về nhà")) {
                String[] text = {"Con tới liền","Đợi con với"};
                setTinNhanDeTuChat(text[MathUtils.random(text.length-1)], 1f);
            }

            // Kích hoạt lại cooldown
            timeCooldownDash = DASH_COOLDOWN;
        }
        if (!veHUD.renderDeTu) {
            if (timeHoatAnhBienMat > 0) {
                timeHoatAnhBienMat -= delta;
                if (timeHoatAnhBienMat <= 0) {
                    timeHoatAnhBienMat = 0;
                }
            }
        }
    }


    public void ve(SpriteBatch batch, float thoiGian) {
        if (timeCooldownDash > 1.8f && timeCooldownDash <= 2.0f) {
            float alpha;
            if (timeCooldownDash > 1.9f) {
                // Fade in: 2.0 → 1.9
                alpha = (2.0f - timeCooldownDash) / 0.1f;  // 0 → 1
            } else {
                // Fade out: 1.9 → 1.8
                alpha = (timeCooldownDash - 1.8f) / 0.1f;  // 1 → 0
            }
            // Áp dụng alpha
            batch.setColor(1f, 1f, 1f, alpha);

            float chanW = chan_tele.getWidth() * tiLe;
            float chanH = chan_tele.getHeight() * tiLe;
            float thanW = than_tele.getWidth() * tiLe;
            float thanH = than_tele.getHeight() * tiLe;
            float dauW = dau_tele.getWidth() * tiLe;
            float dauH = dau_tele.getHeight() * tiLe;

            float flipScale = flip_truoc_dash ? -1f : 1f;
            float anchorX = flip_truoc_dash ? x_truoc_dash + chanW : x_truoc_dash;

            batch.draw(chan_tele, anchorX, y_truoc_dash + 5, chanW * flipScale, chanH);

            float thanX = anchorX + (chanW / 2f - thanW / 2f - 7f) * flipScale;
            float thanY = y_truoc_dash + chanH - 3f;
            batch.draw(than_tele, thanX, thanY, thanW * flipScale, thanH);

            float dauX = anchorX + (chanW / 2f - dauW / 2f - 1f) * flipScale;
            float dauY = thanY + thanH - 20;
            batch.draw(dau_tele, dauX, dauY, dauW * flipScale, dauH);

            // Reset lại màu
            batch.setColor(1f, 1f, 1f, 1f);
        }
        boolean duDieuKien = true;
        if (veHUD.dangHopTheThuong) {
            if (veHUD.timeHopTheTHuong < 1.5f) {
                duDieuKien = false;
            } else {
                duDieuKien = true;
            }
        }
        if (veHUD.timeChoHopThe == 0 && duDieuKien) {
            float daoDong = (trangThai == TrangThaiDeTu.DUNG_YEN) ? (float) Math.sin(thoiGian) * 1.08f
                : (trangThai == TrangThaiDeTu.BAY_NGANG) ? (float) Math.sin(thoiGian) * 5f
                : 0f;

            Texture chanVe = chan_dung;
            Texture thanVe = than_dung;
            Texture dauVe = dau_dung;

            switch (trangThai) {
                case BAY_NGANG:
                    chanVe = chan_bay;
                    thanVe = than_bay;
                    dauVe = dau_dung;
                    break;
                case DI_CHUYEN:
                    timeChay += Gdx.graphics.getDeltaTime(); // tăng thời gian theo deltaTime
                    if (timeChay >= 0.08f) {
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
                case DUNG_YEN:
                default:
                    // giữ ảnh mặc định đã gán ban đầu
                    break;
            }
            // đúng kiểu dữ liệu 2 vế và gán class LechModular để có thể truy cập thuộc tính
            DoLechModular lech = layLech(lechTheoTrangThai, trangThai, frame);
            lechThanX = lech.lechThanX;
            lechThanY = lech.lechThanY;
            lechDauX = lech.lechDauX;
            lechDauY = lech.lechDauY;
            // Tính tọa độ theo hướng flip
            float chanW = chanVe.getWidth() * tiLe;
            float chanH = chanVe.getHeight() * tiLe;
            float thanW = thanVe.getWidth() * tiLe;
            float thanH = thanVe.getHeight() * tiLe;
            float dauW = dauVe.getWidth() * tiLe;
            float dauH = dauVe.getHeight() * tiLe;

            // Flip bằng scale âm nếu cần
            float flipScale = flipX ? -1f : 1f;
            float anchorX = flipX ? x + chanW : x;
            if (trangThai != TrangThaiDeTu.BAY_NGANG) {
                batch.draw(chanVe, anchorX, y, chanW * flipScale, chanH);

                float thanX = anchorX + (chanW / 2f - thanW / 2f) * flipScale;
                float thanY = y + chanH + daoDong;
                float dauX = anchorX + (chanW / 2f - dauW / 2f) * flipScale;
                float dauY = thanY + thanH;
                batch.draw(dauVe, dauX + lechDauX * flipScale, dauY + lechDauY, dauW * flipScale, dauH);
                batch.draw(thanVe, thanX + lechThanX * flipScale, thanY - 10.2f + lechThanY, thanW * flipScale, thanH);
            } else {
                batch.draw(chanVe, anchorX - (thanW-30)*flipScale, y + chanH + daoDong - 10, chanW * flipScale, chanH);

                float thanX = anchorX + (chanW / 2f - thanW / 2f) * flipScale;
                float thanY = y + chanH + daoDong;
                float dauX = anchorX + (chanW / 2f - dauW / 2f) * flipScale;
                float dauY = thanY + thanH;
                batch.draw(dauVe, dauX + lechDauX * flipScale, dauY + lechDauY, dauW * flipScale, dauH);
                batch.draw(thanVe, thanX + lechThanX * flipScale, thanY - 10.2f + lechThanY, thanW * flipScale, thanH);
                if (timeChoHienBay>=0.5f) {
                    Texture cloud = vanBayCauHinh[frameVanBay];
                    float cloudW = cloud.getWidth() * 0.5f;
                    float cloudH = cloud.getHeight() * 0.5f;
                    float flipCloud = !flipX ? 1f : -1f;

                    batch.draw(
                        cloud,
                        anchorX - (thanW - 30 + cloudW - 15) * flipScale,
                        y + daoDong*1.5f - 5f,
                        cloudW * flipCloud,
                        cloudH
                    );
                }
            }
            fontTenDeTu.setColor(16f / 255f, 237f / 255f, 227f / 255f, 1f);
            layout.setText(fontTenDeTu,getTen());
            fontTenDeTu.draw(batch,layout,x+(rong_de_tu- layout.width)/2f,y+cao_de_tu+30);
        }
        if (!tinNhanDeTuChat.isEmpty() && veHUD.timeChoHopThe == 0) {
            float flipScale = flipX ? -1f : 1f;
            layout.setText(
                veHUD.fontchat,
                tinNhanDeTuChat,
                new Color(0, 0, 0, 1),
                180,
                Align.center,
                true
            );
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.rect(x + (rong_de_tu - 200) / 2f, y +cao_de_tu + 30, 200, 36 + layout.height);
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            for (int i = 0; i < 2; i++) {
                shapeRenderer.rect(x + (rong_de_tu - 200) / 2f - i, y +cao_de_tu + 30 - i, 200 + i * 2, 36 + layout.height + i * 2);
            }
            shapeRenderer.end();
            batch.begin();
            float duoiX = flipX ? x + rong_de_tu + 20 : x - 20;
            batch.draw(veHUD.duoichat, duoiX, y + cao_de_tu + 15, 16 * flipScale, 16);
            veHUD.fontchat.draw(batch, layout, x + (rong_de_tu - 200) / 2f + 10f, y + cao_de_tu + 30 + 18f + layout.height);
        }
    }
    public static DoLechModular layLech(Map<TrangThaiDeTu, List<DoLechModular>> map, TrangThaiDeTu trangThai, int frameIndex) {
        List<DoLechModular> ds = map.get(trangThai);
        if (ds == null || ds.isEmpty()) {
            return new DoLechModular(0, 0, 0, 0); // fallback nếu thiếu dữ liệu
        }

        if (trangThai == TrangThaiDeTu.DI_CHUYEN) {
            return ds.get(frameIndex % ds.size()); // vòng lặp theo frame
        } else {
            return ds.get(0); // chỉ có 1 frame cho trạng thái khác
        }
    }
    public void setVeHUD(VeHUD veHUD) {
        this.veHUD = veHUD;
    }
    public void setTinNhanDeTuChat(String tinnhan, float timeHienChat) {
        this.tinNhanDeTuChat = tinnhan;
        this.timeHienChat = timeHienChat;
    }
    public float getTimeHienChat() {
        return timeHienChat;
    }
    public void setDanhSachDat(List<HitboxDat> ds) {
        this.danhSachDat = ds;
    }
    public void setGioiHanToaDo(float ghX, float ghy) {
        this.gioiHanXMax = ghX;
        this.gioiHanYMax = ghy;
    }
    public void setTenDeTu(String ten) {
        this.ten = ten;
    }
    public void dispose() {
        // Giải phóng texture modular
        if (dau_dung != null) dau_dung.dispose();
        if (dau_chay != null) dau_chay.dispose();
        if (than_dung != null) than_dung.dispose();
        if (than_nhay != null) than_nhay.dispose();
        if (than_roi != null) than_roi.dispose();
        if (than_chay != null) {
            for (Texture tex : than_chay) {
                if (tex != null) tex.dispose();
            }
        }

        if (chan_dung != null) chan_dung.dispose();
        if (chan_nhay != null) chan_nhay.dispose();
        if (chan_roi != null) chan_roi.dispose();
        if (chan_chay != null) {
            for (Texture tex : chan_chay) {
                if (tex != null) tex.dispose();
            }
        }

        if (than_bay != null) than_bay.dispose();
        if (chan_bay != null) chan_bay.dispose();

        // Giải phóng texture teleport
        if (dau_tele != null) dau_tele.dispose();
        if (than_tele != null) than_tele.dispose();
        if (chan_tele != null) chan_tele.dispose();

        // Biến mất
        for (Texture tex : bien_mat) {
            if (tex != null) tex.dispose();
        }

        // Van bay
        if (vanBayCauHinh != null) {
            for (Texture tex : vanBayCauHinh) {
                if (tex != null) tex.dispose();
            }
        }

        // Giải phóng icon kỹ năng
        for (Texture tex : iconSkill.values()) {
            if (tex != null) tex.dispose();
        }
        iconSkill.clear();

        // ShapeRenderer và font
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (fontTenDeTu != null) fontTenDeTu.dispose();
        if (texAvtDeTu != null) texAvtDeTu.dispose();
    }
}
