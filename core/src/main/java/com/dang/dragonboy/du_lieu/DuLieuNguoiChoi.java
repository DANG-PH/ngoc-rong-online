package com.dang.dragonboy.du_lieu;
import java.util.ArrayList;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
import com.dang.dragonboy.nhan_vat.DeTuXuLy;
import com.dang.dragonboy.nhan_vat.DeTuCauHinh;
import com.badlogic.gdx.math.MathUtils;
public class DuLieuNguoiChoi {
    public DeTu deTu;
    private NhanVat nhanVat;
    private String ten;
    private long sucManh;
    private int theLuc;
    private float HpNhanVat,KiNhanVat,SucDanhNhanVat;
    private int GiapNhanVat,ChiMangNhanVat;
    private int HpGoc;
    private int KiGoc;
    private float HpHienTai;
    private float KiHienTai;
    private int SucDanhGoc;
    private int GiapGoc;
    private int ChiMangGoc;
    private int SatThuongChiMang;
    private long TiemNangNhanVat;
    private int DiemSoiDongNhanVat;
    private int GiamSatThuongNhanVat;

    private int soDauThan;
    private long vang;
    private long ngoc;
    private String capBac;
    private int[] capSkill = new int[9];  // Mặc định toàn 0
    private String[] tenSkill = new String[9];
    private String[][] motaSkill = new String[9][];
    private int capcaydau;

    private ArrayList<Item> hanhTrang = new ArrayList<>();
    private ArrayList<Item> hanhTrangDangMac = new ArrayList<>(8);
    {
        for (int i = 0; i < 8; i++) {
            hanhTrangDangMac.add(null);
        }
    }
    public void setNhanVat(NhanVat nv) {
        this.nhanVat = nv;
    }
    private boolean dangmacao = false;
    private boolean dangmacquan = false;
    private boolean dangmacgang = false;
    private boolean dangmacgiay = false;
    private boolean dangmacrada = false;
    private boolean setKichHoatNappa = false;
    private boolean checkgiapluyentap = true;
    private int chiSoGlt;

    private String[] danhSachHanhTinh = {"traidat","xayda","namek"};

    private float HpHopThe,KiHopThe,SdHopThe;

    // Constructor
    public DuLieuNguoiChoi(String ten, long sucManh, int theLuc,
                           float HpHienTai, float HpNhanVat, int HpGoc,
                           float KiHienTai,float KiNhanVat, int KiGoc,
                           int SucDanhGoc,int GiapGoc,
                           float SucDanhNhanVat,int GiapNhanVat,
                           int ChiMangGoc, int ChiMangNhanVat,
                           int SatThuongChiMang,
                           long TiemNangNhanVat,int DiemSoiDongNhanVat,
                           int soDauThan, long vang, long ngoc,
                           String capBac,int[] capSkill,String[] tenSkill,String[][] motaSkill,int capcaydau,
                           int GiamSatThuongNhanVat) {
        this.ten = ten;
        this.sucManh = sucManh;
        this.theLuc = theLuc;
        this.HpHienTai = HpHienTai;
        this.HpNhanVat = HpNhanVat;
        this.HpGoc = HpGoc;
        this.KiHienTai = KiHienTai;
        this.KiNhanVat = KiNhanVat;
        this.KiGoc = KiGoc;
        this.SucDanhGoc = SucDanhGoc;
        this.SucDanhNhanVat = SucDanhNhanVat;
        this.GiapGoc = GiapGoc;
        this.GiapNhanVat = GiapNhanVat;
        this.ChiMangGoc = ChiMangGoc;
        this.ChiMangNhanVat = ChiMangNhanVat;
        this.SatThuongChiMang = SatThuongChiMang;
        this.TiemNangNhanVat = TiemNangNhanVat;
        this.DiemSoiDongNhanVat = DiemSoiDongNhanVat;
        this.GiamSatThuongNhanVat = GiamSatThuongNhanVat;
        this.capcaydau = capcaydau;
        this.soDauThan = soDauThan;
        this.vang = vang;
        this.ngoc = ngoc;
        this.capBac = capBac;
        if (capSkill != null && capSkill.length == 9) {
            System.arraycopy(capSkill, 0, this.capSkill, 0, 9);
        }
        if (tenSkill != null && tenSkill.length == 9) {
            System.arraycopy(tenSkill, 0, this.tenSkill, 0, 9);
        }
        if (motaSkill != null && motaSkill.length == 9) {
            System.arraycopy(motaSkill, 0, this.motaSkill, 0, 9);
        }
    }

    public ArrayList<Item> getHanhTrang() {
        return hanhTrang;
    }

    public void themItemVaoHanhTrang(Item item) {
        if (hanhTrang.size() < 50) {
            hanhTrang.add(item);
        }
    }

    public ArrayList<Item> getHanhTrangDangMac() {
        return hanhTrangDangMac;
    }

    public void setItemVaoHanhTrangDangMac(Item item, int index) {
        hanhTrangDangMac.set(index,item);
    }
    public void xoaItemKhoiHanhTrang(int index) {
        hanhTrang.remove(index);
    }

    public void xoaItemTheoIndex(int index) {
        if (index >= 0 && index < hanhTrang.size()) {
            hanhTrang.remove(index);
        }
    }

    public Item getItemTheoIndex(int index) {
        if (index >= 0 && index < hanhTrang.size()) {
            return hanhTrang.get(index);
        }
        return null;
    }

    // Getter
    public String getTen() { return ten; }
    public long getSucManh() { return sucManh; }
    public int getTheLuc() { return theLuc; }
    public float getHpHienTai() { return HpHienTai; }
    public float getHpToiDa() { return HpNhanVat; }
    public int getHpGoc() { return HpGoc; }
    public float getKiHienTai() { return KiHienTai; }
    public int getKiGoc() { return KiGoc; }
    public float getKiToiDa() { return KiNhanVat; }
    public int getSucDanhGoc() { return SucDanhGoc; }
    public float getSucDanhNhanVat() { return SucDanhNhanVat; }
    public int getGiapGoc() { return GiapGoc; }
    public int getGiapNhanVat() { return GiapNhanVat; }
    public int getChiMangGoc() { return ChiMangGoc; }
    public int getChiMangNhanVat() { return ChiMangNhanVat; }
    public int getSatThuongChiMang() { return SatThuongChiMang; }
    public long getTiemNangNhanVat() { return TiemNangNhanVat; }
    public int getDiemSoiDongNhanVat() { return DiemSoiDongNhanVat; }
    public int getGiamSatThuongNhanVat() { return GiamSatThuongNhanVat; }
    public int getCapCayDau() { return capcaydau ;}
    public int getSoDauThan() { return soDauThan; }
    public int getDauHoiHPKI() {
        switch (capcaydau){
            case 0: return 500;
            case 1: return 1000;
            case 2: return 3000;
            case 3: return 5000;
            case 4: return 12000;
            case 5: return 30000;
            case 6: return 50000;
            case 7: return 90000;
            case 8: return 150000;
            case 9: return 250000;
            default: return 0;
        }
    }
    public long getVang() { return vang; }
    public long getNgoc() { return ngoc; }
    public String getCapBac() { return capBac; }
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
    public String[] getMotaSKill(int index){
        if (index >= 0 && index < motaSkill.length) {
            return motaSkill[index];
        }
        return null;
    }

    public void tangSucManh(long SucManhCongThem){
        this.sucManh += SucManhCongThem;
    }

    public void tangHpGoc(int HpCongThem,boolean choPhepHienThi){
        int[] chisoCaiTrang = nhanVat.getChisoCaiTrang(); // chỉ số cải tranga
        int[] chisoAo = nhanVat.getChisoAo();
        int[] chisoQuan = nhanVat.getChisoQuan();
        int[] chisoGang = nhanVat.getChisoGang();
        int[] chisoGiay = nhanVat.getChisoGiay();
        int[] chisoRada = nhanVat.getChisoRada();
        int[] chisoGlt = nhanVat.getChisoGiapLuyenTap();
        float tilePhanTramHPctrang = 0;
        float tilePhanTramHPao = 0;
        float tilePhanTramHPquan = 0;
        float tilePhanTramHPgang = 0;
        float tilePhanTramHPgiay = 0;
        float tilePhanTramHPrada = 0;
        float tilePhanTramHPsetkh = 0;
        float tilePhanTramHPglt = 0;

        // Hàm phụ cộng %HP nếu mảng hợp lệ và có phần tử thứ 6
        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
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
        if (checkgiapluyentap && chisoGlt != null) {
            tilePhanTramHPglt += chisoGlt[6];
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
        int HpCongThemThucTeGlt = Math.round(HpCongThemThucTeRada* (1 + tilePhanTramHPglt/ 100f));
        int HpCongThemThucTeNappa = Math.round(HpCongThemThucTeGlt * (1 + tilePhanTramHPsetkh/ 100f));
        // Cộng vào HpGoc (tối đa 550000)
        if (choPhepHienThi){
            this.HpGoc += HpCongThem;
            if (this.HpGoc >= 550000){
                this.HpGoc = 550000;
            }
        }
        // Cộng vào HP tổng
        this.HpNhanVat += HpCongThemThucTeNappa;
    }
    public void giamHpGoc(int HpCongThem){
        int[] chisoCaiTrang = nhanVat.getChisoCaiTrang(); // chỉ số cải trang
        int[] chisoAo = nhanVat.getChisoAo();
        int[] chisoQuan = nhanVat.getChisoQuan();
        int[] chisoGang = nhanVat.getChisoGang();
        int[] chisoGiay = nhanVat.getChisoGiay();
        int[] chisoRada = nhanVat.getChisoRada();
        int[] chisoGlt = nhanVat.getChisoGiapLuyenTap();
        float tilePhanTramHPctrang = 0;
        float tilePhanTramHPao = 0;
        float tilePhanTramHPquan = 0;
        float tilePhanTramHPgang = 0;
        float tilePhanTramHPgiay = 0;
        float tilePhanTramHPrada = 0;
        float tilePhanTramHPsetkh = 0;
        float tilePhanTramHPglt = 0;
        // Hàm phụ cộng %HP nếu mảng hợp lệ và có phần tử thứ 6
        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
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
        if (checkgiapluyentap && chisoGlt != null ) {
            tilePhanTramHPglt += chisoGlt[6];
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
        int HpCongThemThucTeGlt = Math.round(HpCongThemThucTeRada* (1 + tilePhanTramHPglt/ 100f));
        int HpCongThemThucTeNappa = Math.round(HpCongThemThucTeGlt * (1 + tilePhanTramHPsetkh/ 100f));
        // Cộng vào HP tổng
        this.HpNhanVat -= HpCongThemThucTeNappa;
    }

    public void tangKiGoc(int KiCongThem, boolean choPhepHienThi) {
        int[] chisoCaiTrang = nhanVat.getChisoCaiTrang();
        int[] chisoAo = nhanVat.getChisoAo();
        int[] chisoQuan = nhanVat.getChisoQuan();
        int[] chisoGang = nhanVat.getChisoGang();
        int[] chisoGiay = nhanVat.getChisoGiay();
        int[] chisoRada = nhanVat.getChisoRada();
        int[] chisoGlt = nhanVat.getChisoGiapLuyenTap();

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0, tileGlt = 0;

        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 8) ? chisoCaiTrang[7] : 0;
        }
        if (dangmacao) tileAo += chisoAo[7];
        if (dangmacquan) tileQuan += chisoQuan[7];
        if (dangmacgang) tileGang += chisoGang[7];
        if (dangmacgiay) tileGiay += chisoGiay[7];
        if (dangmacrada) tileRada += chisoRada[7];
        if (checkgiapluyentap && chisoGlt != null) tileGlt += chisoGlt[7];

        int k1 = Math.round(KiCongThem * (1 + tileCT / 100f));
        int k2 = Math.round(k1 * (1 + tileAo / 100f));
        int k3 = Math.round(k2 * (1 + tileQuan / 100f));
        int k4 = Math.round(k3 * (1 + tileGang / 100f));
        int k5 = Math.round(k4 * (1 + tileGiay / 100f));
        int k6 = Math.round(k5 * (1 + tileRada / 100f));
        int k7 = Math.round(k6 * (1 + tileGlt / 100f));

        if (choPhepHienThi) {
            this.KiGoc += KiCongThem;
            if (this.KiGoc >= 550000) this.KiGoc = 550000;
        }
        this.KiNhanVat += k7;
    }
    public void giamKiGoc(int KiCongThem) {
        int[] chisoCaiTrang = nhanVat.getChisoCaiTrang();
        int[] chisoAo = nhanVat.getChisoAo();
        int[] chisoQuan = nhanVat.getChisoQuan();
        int[] chisoGang = nhanVat.getChisoGang();
        int[] chisoGiay = nhanVat.getChisoGiay();
        int[] chisoRada = nhanVat.getChisoRada();
        int[] chisoGlt = nhanVat.getChisoGiapLuyenTap();

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0, tileGlt = 0;

        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 8) ? chisoCaiTrang[7] : 0;
        }
        if (dangmacao) tileAo += chisoAo[7];
        if (dangmacquan) tileQuan += chisoQuan[7];
        if (dangmacgang) tileGang += chisoGang[7];
        if (dangmacgiay) tileGiay += chisoGiay[7];
        if (dangmacrada) tileRada += chisoRada[7];
        if (checkgiapluyentap && chisoGlt != null) tileGlt += chisoGlt[7];

        int k1 = Math.round(KiCongThem * (1 + tileCT / 100f));
        int k2 = Math.round(k1 * (1 + tileAo / 100f));
        int k3 = Math.round(k2 * (1 + tileQuan / 100f));
        int k4 = Math.round(k3 * (1 + tileGang / 100f));
        int k5 = Math.round(k4 * (1 + tileGiay / 100f));
        int k6 = Math.round(k5 * (1 + tileRada / 100f));
        int k7 = Math.round(k6 * (1 + tileGlt / 100f));

        this.KiNhanVat -= k7;
    }
    public void tangSucDanhGoc(int SucDanhCongThem, boolean choPhepHienThi) {
        int[] chisoCaiTrang = nhanVat.getChisoCaiTrang();
        int[] chisoAo = nhanVat.getChisoAo();
        int[] chisoQuan = nhanVat.getChisoQuan();
        int[] chisoGang = nhanVat.getChisoGang();
        int[] chisoGiay = nhanVat.getChisoGiay();
        int[] chisoRada = nhanVat.getChisoRada();
        int[] chisoGlt = nhanVat.getChisoGiapLuyenTap();

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0, tileGlt = 0;

        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 9) ? chisoCaiTrang[8] : 0;
        }
        if (dangmacao) tileAo += chisoAo[8];
        if (dangmacquan) tileQuan += chisoQuan[8];
        if (dangmacgang) tileGang += chisoGang[8];
        if (dangmacgiay) tileGiay += chisoGiay[8];
        if (dangmacrada) tileRada += chisoRada[8];
        if (!checkgiapluyentap) tileGlt += chiSoGlt;

        int s1 = Math.round(SucDanhCongThem * (1 + tileCT / 100f));
        int s2 = Math.round(s1 * (1 + tileAo / 100f));
        int s3 = Math.round(s2 * (1 + tileQuan / 100f));
        int s4 = Math.round(s3 * (1 + tileGang / 100f));
        int s5 = Math.round(s4 * (1 + tileGiay / 100f));
        int s6 = Math.round(s5 * (1 + tileRada / 100f));
        int s7 = Math.round(s6 * (1 + tileGlt / 100f));

        if (choPhepHienThi) {
            this.SucDanhGoc += SucDanhCongThem;
            if (this.SucDanhGoc >= 25000) this.SucDanhGoc = 25000;
        }
        this.SucDanhNhanVat += s7;
    }
    public void giamSucDanhGoc(int SucDanhCongThem) {
        int[] chisoCaiTrang = nhanVat.getChisoCaiTrang();
        int[] chisoAo = nhanVat.getChisoAo();
        int[] chisoQuan = nhanVat.getChisoQuan();
        int[] chisoGang = nhanVat.getChisoGang();
        int[] chisoGiay = nhanVat.getChisoGiay();
        int[] chisoRada = nhanVat.getChisoRada();
        int[] chisoGlt = nhanVat.getChisoGiapLuyenTap();

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0, tileGlt = 0;

        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 9) ? chisoCaiTrang[8] : 0;
        }
        if (dangmacao) tileAo += chisoAo[8];
        if (dangmacquan) tileQuan += chisoQuan[8];
        if (dangmacgang) tileGang += chisoGang[8];
        if (dangmacgiay) tileGiay += chisoGiay[8];
        if (dangmacrada) tileRada += chisoRada[8];
        if (!checkgiapluyentap) tileGlt += chiSoGlt;

        int s1 = Math.round(SucDanhCongThem * (1 + tileCT / 100f));
        int s2 = Math.round(s1 * (1 + tileAo / 100f));
        int s3 = Math.round(s2 * (1 + tileQuan / 100f));
        int s4 = Math.round(s3 * (1 + tileGang / 100f));
        int s5 = Math.round(s4 * (1 + tileGiay / 100f));
        int s6 = Math.round(s5 * (1 + tileRada / 100f));
        int s7 = Math.round(s6 * (1 + tileGlt / 100f));

        this.SucDanhNhanVat -= s7;
    }
    public void tangGiapGoc(int GiapCongThem){
        this.GiapGoc += GiapCongThem;
        this.GiapNhanVat += GiapCongThem;
    }
    public void tangChiMangGoc(int ChiMangThem){
        this.ChiMangGoc += ChiMangThem;
        this.ChiMangNhanVat += ChiMangThem;
    }

    public void tangHp(float HpCongThem){
        this.HpNhanVat += HpCongThem;
    }
    public void tangHpPt(int HpCongThem){ this.HpNhanVat *= (1f+HpCongThem/100f); }
    public void tangHpHienTai(int HpCongThem){
        this.HpHienTai += HpCongThem;
        this.HpHienTai = Math.min(HpHienTai, HpHopThe);
    }
    public void tangHpPtHienTai(float HpCongThem){
        this.HpHienTai *= (1f+HpCongThem/100f);
        this.HpHienTai = Math.min(HpHienTai,HpNhanVat);
    }
    public void tangHpHienTai(float HpCongThem){
        this.HpHienTai += HpCongThem;
        this.HpHienTai = Math.min(HpHienTai,HpNhanVat);
    }
    public void giamHpPtHienTai(float HpCongThem){
        this.HpHienTai /= (1f+HpCongThem/100f);
        this.HpHienTai = Math.max(HpHienTai,0);
    }
    public void giamHpHienTai(float Hp){
        this.HpHienTai -= Hp;
        this.HpHienTai = Math.max(HpHienTai,0);
    }
    public void tangKi(int KiCongThem){
        this.KiNhanVat += KiCongThem;
    }
    public void tangKiPt(float KiCongThem){ this.KiNhanVat *= (1f+KiCongThem/100f); }
    public void tangKiHienTai(int KiCongThem){
        this.KiHienTai += KiCongThem;
        this.KiHienTai = Math.min(KiHienTai,KiHopThe);
    }
    public void tangKiPtHienTai(float KiCongThem){
        this.KiHienTai *= (1f+KiCongThem/100f);
        this.KiHienTai = Math.min(KiHienTai,KiNhanVat);
    }
    public void giamKiPtHienTai(float KiCongThem){
        this.KiHienTai /= (1f+KiCongThem/100f);
        this.KiHienTai = Math.max(KiHienTai,0);
    }
    public void tangKiHienTai(float KiCongThem){
        this.KiHienTai += KiCongThem;
        this.KiHienTai = Math.min(KiHienTai,KiNhanVat);
    }
    public void giamKiHienTai(float Ki){
        this.KiHienTai -= Ki;
        this.KiHienTai = Math.max(KiHienTai,0);
    }
    public void tangSucDanh(int SucDanhCongThem){
        this.SucDanhNhanVat += SucDanhCongThem;
    }
    public void tangSucDanhPt(int SucDanhCongThem){
        this.SucDanhNhanVat *= (1f+SucDanhCongThem/100f);
    }
    public void tangGiap(int GiapCongThem){
        this.GiapNhanVat += GiapCongThem;
    }
    public void tangChiMang(int ChiMangThem){
        this.ChiMangNhanVat += ChiMangThem;
    }

    public void tangSatThuongChiMang(int SatThuongChiMangThem){this.SatThuongChiMang += SatThuongChiMangThem;}
    public void tangTiemNang(int TiemNangCongThem){
        this.TiemNangNhanVat += TiemNangCongThem;
    }
    public void tangDiemSoiDong(int DiemSoiDongCongThem){
        this.DiemSoiDongNhanVat += DiemSoiDongCongThem;
    }
    public void tangGiamSatThuongNhanVat(int PtGiamSatThuong) {
        this.GiamSatThuongNhanVat += PtGiamSatThuong;
        this.GiamSatThuongNhanVat = Math.min(this.GiamSatThuongNhanVat,85);
    }
    public void tangVang(int soLuong) {
        this.vang += soLuong;
    }
    public void tangNgoc(int soLuong) {
        this.ngoc += soLuong;
    }

    public void tangCapCayDau(){
        this.capcaydau += 1;
    }
    public void tangDau(int soluong){
        this.soDauThan += soluong;
    }

    public void tangCapSkill(int i) {
        if (i >= 1 && i <= 9 && capSkill[i - 1] < 7) {
            capSkill[i - 1]++;
        }
    }

    public void giamHp(float Hp){
        this.HpNhanVat -= Hp;
    }
    public void giamHpPt(int Hp){
        this.HpNhanVat /= (1f+Hp/100f);
    }
    public void giamKi(int Ki){
        this.KiNhanVat -= Ki;
    }
    public void giamKiPt(float Ki){
        this.KiNhanVat /= (1f+Ki/100f);
    }
    public void giamSucDanh(int SucDanh){
        this.SucDanhNhanVat -= SucDanh;
    }
    public void giamSucDanhPt(int SucDanh){
        this.SucDanhNhanVat /= (1f+SucDanh/100f);
    }
    public void giamGiap(int Giap){
        this.GiapNhanVat -= Giap;
    }
    public void giamChiMang(int ChiMang){
        this.ChiMangNhanVat -= ChiMang;
    }
    public void giamSatThuongChiMang(int STChiMang){
        this.SatThuongChiMang -= STChiMang;
    }
    public void giamTiemNang(long TiemNang){
        this.TiemNangNhanVat -= TiemNang;
    }
    public void giamDiemSoiDong(int DiemSoiDong){
        this.DiemSoiDongNhanVat -= DiemSoiDong;
    }
    public void giamGiamSatThuongNhanVat(int PtGiamSatThuong) {
        this.GiamSatThuongNhanVat -= PtGiamSatThuong;
        this.GiamSatThuongNhanVat = Math.min(this.GiamSatThuongNhanVat,85);
    }
    public void giamVang(int soLuong) {
        this.vang -= soLuong;
    }

    public void giamNgoc(int soLuong) {
        this.ngoc -= soLuong;
    }
    public void giamDau(){
        this.soDauThan -= 1;
    }

    //setter hop the

    public void setHpHopThe(float Hp) {
        this.HpHopThe = Hp;
    }

    public float getHpHopThe() {
        return HpHopThe;
    }

    public void setKiHopThe(float Ki) {
        this.KiHopThe = Ki;
    }

    public float getKiHopThe() {
        return KiHopThe;
    }

    public void setSdHopThe(float Sd) {
        this.SdHopThe = Sd;
    }

    public float getSdHopThe() {
        return SdHopThe;
    }

    public void setHpHienTai(float Hp) {
        this.HpHienTai = Hp;
        this.HpHienTai = Math.max(0,Math.min(HpHienTai,HpHopThe));
    }

    public void setKiHienTai(float Ki) {
        this.KiHienTai = Ki;
        this.KiHienTai = Math.max(0,Math.min(KiHienTai,KiHopThe));
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
    public void checkGiapLuyenTap(boolean duDieuKien,int chiso){
        if (duDieuKien){
            checkgiapluyentap = true;
        } else {
            checkgiapluyentap = false;
            chiSoGlt = chiso;
        }
    }
    public void taoDeTu(String ten) {
//        String hanhtinh = danhSachHanhTinh[MathUtils.random(danhSachHanhTinh.length - 1)];
        String hanhtinh = nhanVat.getHanhtinh();
        DeTuCauHinh config = DoicaitrangDeTu("set_base_"+hanhtinh);
        if (this.deTu == null) {
            int kc = MathUtils.random(30, 80) * (MathUtils.randomBoolean() ? 1 : -1);
            if (nhanVat.getX()+kc <= 0 || nhanVat.getX()+kc>=nhanVat.getGioiHanXMax()) {
                kc = -kc;
            }
            boolean flipX = kc > 0;
            boolean diQuaPhai = kc < 0;
            this.deTu = new DeTu(
                nhanVat.getX()+kc,nhanVat.getY(),
                flipX,diQuaPhai,
                ten,hanhtinh,
                config.dau_dung_de_tu, config.dau_chay_de_tu,
                config.than_dung_de_tu, config.than_nhay_de_tu, config.than_roi_de_tu, config.than_chay_de_tu,
                config.chan_dung_de_tu, config.chan_nhay_de_tu, config.chan_roi_de_tu, config.chan_chay_de_tu,
                config.than_bay_de_tu, config.chan_bay_de_tu,
                config.lechMapDeTu,
                null,null,null,null,null,null,nhanVat
            );
        }
    }

    public DeTu getDeTu() {
        return deTu;
    }

    public boolean coDeTu() {
        return deTu != null;
    }

    public DeTuCauHinh DoicaitrangDeTu(String TenCaiTrang){
        return DeTuXuLy.xuly_id("caitrang_"+TenCaiTrang);
    }
    public DeTuCauHinh Doi_avt_ao_quan_DeTu(String HanhTinh, String TenAvatar , String ao, String quan){
        return DeTuXuLy.xuly_id("avatar_"+HanhTinh+"+"+TenAvatar+"+"+ao+"+"+quan);
    }
}
