package com.dang.dragonboy.du_lieu;

import com.dang.dragonboy.item.Item;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import com.badlogic.gdx.math.MathUtils;
import com.dang.dragonboy.nhan_vat.DeTuXuLy;

public class DeTu {
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

    public DeTu(String ten) {
        this.ten = ten;
//        this.hanhtinh = danhSachHanhTinh[MathUtils.random(danhSachHanhTinh.length - 1)];
        this.hanhtinh = "xayda";
        this.sucManh = 50_000_000_000L;
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
    }

    public Texture getAvtDeTu() {
        String Avt_lon_hay_sosinh = sucManh >= 1500000 ? "lon" : "sosinh";
        return new Texture("nhanvat/detu/"+hanhtinh+"/avt"+Avt_lon_hay_sosinh+".png");
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
}
