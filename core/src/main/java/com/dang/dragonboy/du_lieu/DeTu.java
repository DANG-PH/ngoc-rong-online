package com.dang.dragonboy.du_lieu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

import java.util.List;
import java.util.ArrayList;

import com.dang.dragonboy.xu_ly_map.HitboxDat;

public class DeTu {
    private GlyphLayout layout;
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

    public Texture ao_de_tu,quan_de_tu,gang_de_tu,giay_de_tu,rada_de_tu,iconct_de_tu;
    public Texture dau_dung, dau_chay;
    public Texture than_dung, than_nhay, than_roi;
    public Texture[] than_chay;
    public Texture than_bay;
    public Texture chan_dung, chan_nhay, chan_roi;
    public Texture[] chan_chay;
    public Texture chan_bay;
    public float rong,cao;
    private boolean chuaFixAvtAoQuan = true;

    private VeHUD veHUD;

    public DeTu(float x, float y,String ten, String hanhtinh, Texture dau_dung, Texture dau_chay,
                Texture than_dung, Texture than_nhay, Texture than_roi, Texture[] than_chay,
                Texture chan_dung, Texture chan_nhay, Texture chan_roi, Texture[] chan_chay,
                Texture than_bay, Texture chan_bay, Map<TrangThaiDeTu, List<DoLechModular>> lechTheoTrangThai,
                Texture ao, Texture quan, Texture gang, Texture giay, Texture rada, Texture iconct) {
        layout = new GlyphLayout();
        this.ten = ten;
//        this.hanhtinh = danhSachHanhTinh[MathUtils.random(danhSachHanhTinh.length - 1)];
        this.hanhtinh = hanhtinh;
        this.sucManh = 1_499_999L;
        this.theLuc = 70;
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

    }

    public Texture getAvtDeTu() {
        String Avt_lon_hay_sosinh = sucManh >= 1500000 ? "lon" : "sosinh";
        return new Texture("nhanvat/detu/"+hanhtinh+"/avt"+Avt_lon_hay_sosinh+".png");
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
        }
        if (sucManh >= 1_500_000_000 && tenSkill[2]==null) {
            tenSkill[2] = danhSachSkill3[MathUtils.random(danhSachSkill3.length - 1)];
        }
        if (sucManh >= 20_000_000_000L && tenSkill[3]==null) {
            tenSkill[3] = danhSachSkill4[MathUtils.random(danhSachSkill4.length - 1)];
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
    public void capNhat(float delta, LinkedList<TrangThaiChu> lichSuChu, float delayThoiGian) {
        int indexDelay = (int)(delayThoiGian / 0.05f);
        if (lichSuChu.size() <= indexDelay) return;

        TrangThaiChu mucTieu = lichSuChu.get(indexDelay);
        float dx = mucTieu.x - this.x;
        float dy = mucTieu.y - this.y;
        float tocDo = 350f;

        // Giữ khoảng cách X tối thiểu
        float absDx = Math.abs(dx);
        float absDy = Math.abs(dy);
        boolean quaGanX = absDx < 70f;
        boolean quaGanY = absDy < 0f;

        float targetX = this.x;
        float targetY = mucTieu.y;

        if (!quaGanX) {
            float huongX = Math.signum(dx);
            targetX = mucTieu.x - huongX * 70f;
        }

        dx = targetX - this.x;
        dy = targetY - this.y;
        float khoangCach = (float)Math.sqrt(dx * dx + dy * dy);

        if (khoangCach > 0.01f) {
            this.x += (dx / khoangCach) * tocDo * delta;
            this.y += (dy / khoangCach) * tocDo * delta * 0.75f;
        }

        // Luôn giữ theo trạng thái mục tiêu – KHÔNG ép về DUNG_YEN
        this.flipX = (mucTieu.x - this.x) <= 0;
        if (mucTieu.trangThai == TrangThai.DUNG_YEN && khoangCach < 72f && Math.abs(dy)<2f) {
            this.trangThai = TrangThaiDeTu.DUNG_YEN;
            this.y = mucTieu.y;
        } else {
            this.trangThai = TrangThaiDeTu.valueOf(mucTieu.trangThai.name());
        }
        if (trangThai == TrangThaiDeTu.BAY_NGANG) {
            timeVanBay += Gdx.graphics.getDeltaTime();
            if (timeVanBay > 0.12f) {
                frameVanBay = (frameVanBay + 1) % vanBayCauHinh.length;
                timeVanBay = 0;
            }
        }
    }

    public void ve(SpriteBatch batch, float thoiGian) {
        boolean duDieuKien = true;
        if (veHUD.dangHopTheThuong) {
            if (veHUD.timeHopTheTHuong < 1.5f) {
                duDieuKien = false;
            } else {
                duDieuKien = true;
            }
        }
        if (veHUD.timeChoHopThe == 0 && duDieuKien) {
            float daoDong = (trangThai == TrangThaiDeTu.DUNG_YEN || trangThai == TrangThaiDeTu.BAY_NGANG) ? (float) Math.sin(thoiGian) * 1.08f : 0f;

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
                    if (timeChay >= 0.1f) {
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

                Texture cloud = vanBayCauHinh[frameVanBay];
                float cloudW = cloud.getWidth() * 0.5f;
                float cloudH = cloud.getHeight() * 0.5f;
                float flipCloud = !flipX ? 1f : -1f;

                batch.draw(
                    cloud,
                    anchorX - (thanW-30 + cloudW - 15)*flipScale,
                    y + daoDong - 5f,
                    cloudW * flipCloud,
                    cloudH
                );
            }
            fontTenDeTu.setColor(16f / 255f, 237f / 255f, 227f / 255f, 1f);
            layout.setText(fontTenDeTu,getTen());
            fontTenDeTu.draw(batch,layout,x+(rong_de_tu- layout.width)/2f,y+cao_de_tu+30);
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
}
