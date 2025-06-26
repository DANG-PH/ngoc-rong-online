package com.dang.dragonboy.du_lieu;

public class DuLieuNguoiChoi {
    private String ten;
    private long sucManh;
    private int theLuc;

    private int ThanhhpToiDa;
    private int ThanhkiToiDa;
    private int HpGoc;
    private int KiGoc;
    private int HpHienTai;
    private int KiHienTai;
    private int SucDanhGoc;
    private int GiapGoc;
    private int ChiMangGoc;
    private int SatThuongChiMang;
    private int TiemNangNhanVat;
    private int DiemSoiDongNhanVat;

    private int soDauThan;
    private long vang;
    private long ngoc;
    private String capBac;
    private int[] capSkill = new int[9];  // Mặc định toàn 0

    // Constructor
    public DuLieuNguoiChoi(String ten, long sucManh, int theLuc,
                           int HpHienTai, int HpGoc,
                           int KiHienTai, int KiGoc,
                           int SucDanhGoc,int GiapGoc,
                           int ChiMangGoc,int SatThuongChiMang,
                           int TiemNangNhanVat,int DiemSoiDongNhanVat,
                           int soDauThan, long vang, long ngoc,
                           String capBac,int[] capSkill) {
        this.ten = ten;
        this.sucManh = sucManh;
        this.theLuc = theLuc;
        this.HpHienTai = HpHienTai;
        this.HpGoc = HpGoc;
        this.KiHienTai = KiHienTai;
        this.KiGoc = KiGoc;
        this.SucDanhGoc = SucDanhGoc;
        this.GiapGoc = GiapGoc;
        this.ChiMangGoc = ChiMangGoc;
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
    }

    // Getter
    public String getTen() { return ten; }
    public long getSucManh() { return sucManh; }
    public int getTheLuc() { return theLuc; }
    public int getHpHienTai() { return HpHienTai; }
    public int getHpToiDa() { return HpGoc; }
    public int getKiHienTai() { return KiHienTai; }
    public int getKiToiDa() { return KiGoc; }
    public int getSucDanhGoc() { return SucDanhGoc; }
    public int getGiapGoc() { return GiapGoc; }
    public int getChiMangGoc() { return ChiMangGoc; }
    public int getSatThuongChiMang() { return SatThuongChiMang; }
    public int getTiemNangNhanVat() { return TiemNangNhanVat; }
    public int getDiemSoiDongNhanVat() { return DiemSoiDongNhanVat; }
    public int getSoDauThan() { return soDauThan; }
    public long getVang() { return vang; }
    public long getNgoc() { return ngoc; }
    public String getCapBac() { return capBac; }


    public void tangSucManh(int SucManhCongThem){
        this.sucManh += SucManhCongThem;
    }
    public void tangHp(int HpCongThem){
        this.HpGoc += HpCongThem;
    }
    public void tangKi(int KiCongThem){
        this.KiGoc += KiCongThem;
    }
    public void tangSucDanh(int SucDanhCongThem){
        this.SucDanhGoc += SucDanhCongThem;
    }
    public void tangGiap(int GiapCongThem){
        this.GiapGoc += GiapCongThem;
    }
    public void tangChiMang(int ChiMangThem){
        this.ChiMangGoc += ChiMangThem;
    }
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
    public void giamTiemNang(int TiemNang){
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
