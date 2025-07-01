package com.dang.dragonboy.du_lieu;
import java.util.ArrayList;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.nhan_vat.NhanVat;

public class DuLieuNguoiChoi {
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

    private int soDauThan;
    private long vang;
    private long ngoc;
    private String capBac;
    private int[] capSkill = new int[9];  // Mặc định toàn 0
    private String[] tenSkill = new String[9];
    private String[][] motaSkill = new String[9][];
    private int capcaydau;

    private ArrayList<Item> hanhTrang = new ArrayList<>();
    public void setNhanVat(NhanVat nv) {
        this.nhanVat = nv;
    }
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
                           String capBac,int[] capSkill,String[] tenSkill,String[][] motaSkill,int capcaydau) {
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
        if (hanhTrang.size() < 21) {
            hanhTrang.add(item);
        }
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

    public void tangSucManh(int SucManhCongThem){
        this.sucManh += SucManhCongThem;
    }

    public void tangHpGoc(int HpCongThem){
        int[] chiso = nhanVat.getChisoCaiTrang(); // lấy chỉ số cải trang đang mặc
        float tilePhanTramHP = chiso != null && chiso.length >= 7 ? chiso[6] : 0; // chiso[6] là %HP
        int HpCongThemThucTe = Math.round(HpCongThem * (1 + tilePhanTramHP / 100f));

        this.HpGoc += HpCongThem;
        this.HpNhanVat += HpCongThemThucTe;
    }
    public void tangKiGoc(int KiCongThem) {
        int[] chiso = nhanVat.getChisoCaiTrang(); // lấy chỉ số cải trang đang mặc
        float tilePhanTramKi = chiso != null && chiso.length >= 8 ? chiso[7] : 0; // chiso[7] là %KI
        int KiCongThemThucTe = Math.round(KiCongThem * (1 + tilePhanTramKi / 100f));

        this.KiGoc += KiCongThem;
        this.KiNhanVat += KiCongThemThucTe;
    }
    public void tangSucDanhGoc(int SucDanhCongThem) {
        int[] chiso = nhanVat.getChisoCaiTrang(); // lấy chỉ số cải trang đang mặc
        float tilePhanTramSucDanh = chiso != null && chiso.length >= 9 ? chiso[8] : 0; // chiso[8] là %Sức đánh
        int SucDanhCongThemThucTe = Math.round(SucDanhCongThem * (1 + tilePhanTramSucDanh / 100f));

        this.SucDanhGoc += SucDanhCongThem;
        this.SucDanhNhanVat += SucDanhCongThemThucTe;
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
    public void tangHpPt(int HpCongThem){ this.HpNhanVat *= (1f+HpCongThem/100f); }
    public void tangHpHienTai(int HpCongThem){
        this.HpHienTai += HpCongThem;
        this.HpHienTai = Math.min(HpHienTai,HpNhanVat);
    }
    public void tangHpPtHienTai(int HpCongThem){
        this.HpHienTai *= (1f+HpCongThem/100f);
        this.HpHienTai = Math.min(HpHienTai,HpNhanVat);
    }
    public void tangKi(int KiCongThem){
        this.KiNhanVat += KiCongThem;
    }
    public void tangKiPt(int KiCongThem){ this.KiNhanVat *= (1f+KiCongThem/100f); }
    public void tangKiHienTai(int KiCongThem){
        this.KiHienTai += KiCongThem;
        this.KiHienTai = Math.min(KiHienTai,KiNhanVat);
    }
    public void tangKiPtHienTai(int KiCongThem){
        this.KiHienTai *= (1f+KiCongThem/100f);
        this.KiHienTai = Math.min(KiHienTai,KiNhanVat);
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

    public void giamHp(int Hp){
        this.HpNhanVat -= Hp;
    }
    public void giamHpPt(int Hp){
        this.HpNhanVat /= (1f+Hp/100f);
    }
    public void giamKi(int Ki){
        this.KiNhanVat -= Ki;
    }
    public void giamKiPt(int Ki){
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
