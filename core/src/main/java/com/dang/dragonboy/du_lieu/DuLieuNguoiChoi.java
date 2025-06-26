package com.dang.dragonboy.du_lieu;

public class DuLieuNguoiChoi {
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

    private int soDauThan;
    private long vang;
    private long ngoc;
    private String capBac;
    private int[] capSkill = new int[9];  // Mặc định toàn 0
    private String[] tenSkill = new String[9];

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
                           String capBac,int[] capSkill,String[] tenSkill) {
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
    public int getSoDauThan() { return soDauThan; }
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


    public void tangSucManh(int SucManhCongThem){
        this.sucManh += SucManhCongThem;
    }

    public void tangHpGoc(int HpCongThem){
        this.HpGoc += HpCongThem;
        this.HpNhanVat += HpCongThem;
    }
    public void tangKiGoc(int KiCongThem){
        this.KiGoc += KiCongThem;
        this.KiNhanVat += KiCongThem;
    }
    public void tangSucDanhGoc(int SucDanhCongThem){
        this.SucDanhGoc += SucDanhCongThem;
        this.SucDanhNhanVat += SucDanhCongThem;
    }
    public void tangGiapGoc(int GiapCongThem){
        this.GiapGoc += GiapCongThem;
        this.GiapNhanVat += GiapCongThem;
    }
    public void tangChiMangGoc(int ChiMangThem){
        this.ChiMangGoc += ChiMangThem;
        this.ChiMangNhanVat += ChiMangThem;
    }

    public void tangHp(int HpCongThem){
        this.HpNhanVat += HpCongThem;
    }
    public void tangKi(int KiCongThem){
        this.KiNhanVat += KiCongThem;
    }
    public void tangSucDanh(int SucDanhCongThem){
        this.SucDanhNhanVat += SucDanhCongThem;
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
    public void tangVang(int soLuong) {
        this.vang += soLuong;
    }

    public void tangNgoc(int soLuong) {
        this.ngoc += soLuong;
    }
    public void tangDau(int soluong){
        this.soDauThan += soluong;
    }

    public void tangCapSkill(int i) {
        if (i >= 1 && i <= 9 && capSkill[i - 1] < 7) {
            capSkill[i - 1]++;
        }
    }

    public void giamHp(int Hp){
        this.HpGoc -= Hp;
    }
    public void giamKi(int Ki){
        this.KiGoc -= Ki;
    }
    public void giamSucDanh(int SucDanh){
        this.SucDanhGoc -= SucDanh;
    }
    public void giamGiap(int Giap){
        this.GiapGoc -= Giap;
    }
    public void giamChiMang(int ChiMang){
        this.ChiMangGoc -= ChiMang;
    }
    public void giamTiemNang(long TiemNang){
        this.TiemNangNhanVat -= TiemNang;
    }
    public void giamDiemSoiDong(int DiemSoiDong){
        this.DiemSoiDongNhanVat -= DiemSoiDong;
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
    // Setter (khi chơi game sẽ cần thay đổi)
    public void setHpHienTai(int HpHienTai) { this.HpHienTai = HpHienTai; }
    public void setKiHienTai(int KiHienTai) { this.KiHienTai = KiHienTai; }
    public void setTheLuc(int theLuc) { this.theLuc = theLuc; }
    public void setSoDauThan(int soDauThan) { this.soDauThan = soDauThan; }
    public void setVang(long vang) { this.vang = vang; }
    public void setNgoc(long ngoc) { this.ngoc = ngoc; }

}
