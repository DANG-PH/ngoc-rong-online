package com.dang.dragonboy.du_lieu;
import java.util.ArrayList;

import com.dang.dragonboy.network.DTO.DeTuTheoUser;
import com.dang.dragonboy.network.DTO.ItemCanLuu;
import com.dang.dragonboy.network.DTO.UserResponse;
import com.dang.dragonboy.websocket.GameSocket;
import com.google.gson.Gson;
import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
import com.dang.dragonboy.nhan_vat.DeTuXuLy;
import com.dang.dragonboy.nhan_vat.DeTuCauHinh;
import com.badlogic.gdx.math.MathUtils;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.dang.dragonboy.network.*;
public class DuLieuNguoiChoi {
    public DeTu deTu;
    public NhanVat nhanVat;
    public VeHUD veHUD;
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
    public long vangNapTuWeb;
    public long ngocNapTuWeb;
    public List<Integer> danhSachVatPhamWeb = new ArrayList<>();
    private String capBac;
    private int[] capSkill = new int[9];  // Mặc định toàn 0
    private String[] tenSkill = new String[9];
    private String[][] motaSkill = new String[9][];
    private int capcaydau;

    private ArrayList<Item> hanhTrang = new ArrayList<>();
    private ArrayList<Item> hanhTrangDangMac = new ArrayList<>(8);
    private ArrayList<Item> hanhTrangRuongDo = new ArrayList<>();
    public ArrayList<Item> hanhTrangGiaoDich = new ArrayList<>();
    public ArrayList<Item> hanhTrangGiaoDichPlayer2 = new ArrayList<>(); // hanh trang mà người kia giao dịch cho
    public ArrayList<Item> danhSachItemCuongHoa = new ArrayList<>();
    public final int MAXRUONGDO = 20;
    public final int MAXHANHTRANG = 50;

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
    private boolean dangmacglt = false;
    private boolean setKichHoatNappa = false;

    private String[] danhSachHanhTinh = {"traidat","xayda","namek"};

    private float HpHopThe = HpNhanVat,KiHopThe = KiNhanVat,SdHopThe,ChiMangSuDung,SatThuongChiMangSuDung,GiamSatThuongSuDung;

    // Logic chỉ cho đậu thần
    public int soDauThanHienTai ;
    public float timeTangMotDauThan = 1/2f * 60f;

    // dữ liệu vé quay khóa ở npc hải đăng
    private int soVeQuayKhoa = 0;

    private List<String> giftCodeTanThu = new ArrayList<>(List.of("HDG01", "HDG02", "HDG03"));
    private List<String> giftCodeTanThuDaDung = new ArrayList<>();

    private boolean daNhanQuaTanThu = false;

    private float timeCapNhatAPISaveDuLieu = 20f;

    // Dùng làm tmpId
    private static final AtomicInteger idCounter = new AtomicInteger(1);

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
        this.ten = State_Management.getUserResponse().gameName;
        this.sucManh = State_Management.getUserResponse().sucManh;
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
//        this.vang = vang;
//        this.ngoc = ngoc;
        this.vang = State_Management.getUserResponse().vang;
        this.ngoc = State_Management.getUserResponse().ngoc;
        this.vangNapTuWeb = State_Management.getUserResponse().vangNapTuWeb;
        this.ngocNapTuWeb = State_Management.getUserResponse().ngocNapTuWeb;
        this.danhSachVatPhamWeb = State_Management.getUserResponse().danhSachVatPhamWeb;
        if (danhSachVatPhamWeb == null) this.danhSachVatPhamWeb = new ArrayList<>();
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

        State_Management.setDuLieuNguoiChoi(this);
    }

    public ArrayList<Item> getHanhTrang() {
        return hanhTrang;
    }

    public boolean themItemVaoHanhTrang(Item item) {
        boolean ketQua = themItemVaoHanhTrangNoSave(item);
        if (ketQua) {
            int tmpId = idCounter.incrementAndGet();
            item.tmpId = tmpId;
            luuDuLieuItem(item, tmpId, "hanhtrang");
        }
        return ketQua;
    }

    public boolean themItemVaoHanhTrangNoSave(Item item) {
        if (hanhTrang.size() < MAXHANHTRANG) {
            if (item.getLoai() != LoaiItem.NGOCRONG &&
                item.getLoai() != LoaiItem.PHUTRO &&
                item.getLoai() != LoaiItem.NANGSKILL &&
                item.getLoai() != LoaiItem.VE_QUAY_NPC_HAIDANG) {
                hanhTrang.add(item);
            } else {
                boolean daSoHuuItem = false;
                for (Item itemm : hanhTrang) {
                    if ((itemm.getLoai() == LoaiItem.NGOCRONG ||
                        item.getLoai() == LoaiItem.PHUTRO ||
                        item.getLoai() == LoaiItem.NANGSKILL ||
                        item.getLoai() == LoaiItem.VE_QUAY_NPC_HAIDANG)
                        && itemm.getId().equals(item.getId())) {
                        itemm.tangSoLuong(item.getSoLuong());
                        daSoHuuItem = true;
                    }
                }
                if (!daSoHuuItem) {
                    hanhTrang.add(item);
                }
            }
            return true;
        } else {
            veHUD.setTinNhanPet("Cần ít nhất 1 ô trống", 2f);
            return false;
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

    public ArrayList<Item> getHanhTrangRuongDo() {
        return hanhTrangRuongDo;
    }

    public void themItemVaoHanhTrangRuongDo(Item item) {
        if (hanhTrangRuongDo.size() < MAXRUONGDO) {
            if (item.getLoai() != LoaiItem.NGOCRONG &&
                item.getLoai() != LoaiItem.PHUTRO &&
                item.getLoai() != LoaiItem.NANGSKILL &&
                item.getLoai() != LoaiItem.VE_QUAY_NPC_HAIDANG) {
                hanhTrangRuongDo.add(item);
            } else {
                boolean daSoHuuItem = false;
                for (Item itemm : hanhTrangRuongDo) {
                    if ((itemm.getLoai() == LoaiItem.NGOCRONG ||
                        item.getLoai() == LoaiItem.PHUTRO ||
                        item.getLoai() == LoaiItem.NANGSKILL ||
                        item.getLoai() == LoaiItem.VE_QUAY_NPC_HAIDANG
                        )
                        && itemm.getId() == item.getId()) {
                        itemm.tangSoLuong(item.getSoLuong());
                        daSoHuuItem = true;
                    }
                }
                if (!daSoHuuItem) {
                    hanhTrangRuongDo.add(item);
                }
            }
        } else {
            veHUD.setTinNhanPet("Rương đồ đã đầy",2f);
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
    public int getGiamSatThuongNhanVat() { return GiamSatThuongNhanVat; }
    public int getCapCayDau() { return capcaydau ;}
    public int getSoDauThan() { return soDauThan; }
    public int getDauHoiHPKI() {
        switch (capcaydau-1){
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
    public long getVangNapTuWeb() { return vangNapTuWeb; }
    public long getNgocNapTuWeb() { return ngocNapTuWeb; }
    public void setVangNapTuWeb(long sl) { this.vangNapTuWeb = sl; }
    public void setNgocNapTuWeb(long sl) { this.ngocNapTuWeb = sl; }
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

    public void capNhatMotaSkill(int index) {
        if (index >= 0 && index < motaSkill.length) {
            if (nhanVat.getHanhtinh().equals("xayda") || nhanVat.getTen().equals("admin")) {
                switch (index) {
                    case 3:
                        motaSkill[3] = new String[]{"Biến hình thành khỉ","Tăng sức đánh, HP và tốc độ","KI tiêu hao: 10%","Hồi chiêu: "+(500-20*this.getCapSkill(3))+"s"};
                        break;
                    case 2:
                        motaSkill[2] = new String[]{"Tái tạo lại HP và MP đang có","Tự tái tạo HP MP "+(3+1*this.getCapSkill(2))+"%/s","KI tiêu hao: 0%","Hồi chiêu: "+"20s"};
                        break;
                    case 5:
                        motaSkill[5] = new String[]{"Huýt sáo","Tăng tạm thời "+(30+10*this.getCapSkill(5))+"%HP cho mọi người","KI tiêu hao: 20","Hồi chiêu: 180s"};
                        break;
                }
            }
        }
    }

    public void tangSucManh(long SucManhCongThem){
        this.sucManh += SucManhCongThem;
    }

    public void tangHpGoc(int HpCongThem,boolean choPhepHienThi){
        int[] chisoCaiTrang = getChisoTuSlot(5);
        int[] chisoAo       = getChisoTuSlot(0);
        int[] chisoQuan     = getChisoTuSlot(1);
        int[] chisoGang     = getChisoTuSlot(2);
        int[] chisoGiay     = getChisoTuSlot(3);
        int[] chisoRada     = getChisoTuSlot(4);
        int[] chisoGlt      = getChisoTuSlot(6);
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
        if (dangmacglt) {
            tilePhanTramHPglt += chisoGlt[6];
        }
        if (setKichHoatNappa){
            tilePhanTramHPsetkh += 80;
        }
        // Tính HP cộng thêm thực tế
        float HpCongThemThucTeCt = HpCongThem * (1 + tilePhanTramHPctrang/ 100f);
        float HpCongThemThucTeAo = HpCongThemThucTeCt* (1 + tilePhanTramHPao/ 100f);
        float HpCongThemThucTeQuan = HpCongThemThucTeAo* (1 + tilePhanTramHPquan/ 100f);
        float HpCongThemThucTeGang = HpCongThemThucTeQuan* (1 + tilePhanTramHPgang/ 100f);
        float HpCongThemThucTeGiay = HpCongThemThucTeGang* (1 + tilePhanTramHPgiay/ 100f);
        float HpCongThemThucTeRada = HpCongThemThucTeGiay* (1 + tilePhanTramHPrada/ 100f);
        float HpCongThemThucTeGlt = HpCongThemThucTeRada* (1 + tilePhanTramHPglt/ 100f);
        float HpCongThemThucTeNappa = HpCongThemThucTeGlt * (1 + tilePhanTramHPsetkh/ 100f);
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
        int[] chisoCaiTrang = getChisoTuSlot(5);
        int[] chisoAo       = getChisoTuSlot(0);
        int[] chisoQuan     = getChisoTuSlot(1);
        int[] chisoGang     = getChisoTuSlot(2);
        int[] chisoGiay     = getChisoTuSlot(3);
        int[] chisoRada     = getChisoTuSlot(4);
        int[] chisoGlt      = getChisoTuSlot(6);
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
        if (dangmacglt) {
            tilePhanTramHPglt += chisoGlt[6];
        }
        if (setKichHoatNappa){
            tilePhanTramHPsetkh += 80;
        }
        // Tính HP cộng thêm thực tế
        float HpCongThemThucTeCt = HpCongThem * (1 + tilePhanTramHPctrang/ 100f);
        float HpCongThemThucTeAo = HpCongThemThucTeCt* (1 + tilePhanTramHPao/ 100f);
        float HpCongThemThucTeQuan = HpCongThemThucTeAo* (1 + tilePhanTramHPquan/ 100f);
        float HpCongThemThucTeGang = HpCongThemThucTeQuan* (1 + tilePhanTramHPgang/ 100f);
        float HpCongThemThucTeGiay = HpCongThemThucTeGang* (1 + tilePhanTramHPgiay/ 100f);
        float HpCongThemThucTeRada = HpCongThemThucTeGiay* (1 + tilePhanTramHPrada/ 100f);
        float HpCongThemThucTeGlt = HpCongThemThucTeRada* (1 + tilePhanTramHPglt/ 100f);
        float HpCongThemThucTeNappa = HpCongThemThucTeGlt * (1 + tilePhanTramHPsetkh/ 100f);
        // Cộng vào HP tổng
        this.HpNhanVat -= HpCongThemThucTeNappa;
    }

    public void tangKiGoc(int KiCongThem, boolean choPhepHienThi) {
        int[] chisoCaiTrang = getChisoTuSlot(5);
        int[] chisoAo       = getChisoTuSlot(0);
        int[] chisoQuan     = getChisoTuSlot(1);
        int[] chisoGang     = getChisoTuSlot(2);
        int[] chisoGiay     = getChisoTuSlot(3);
        int[] chisoRada     = getChisoTuSlot(4);
        int[] chisoGlt      = getChisoTuSlot(6);

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0, tileGlt = 0;

        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 8) ? chisoCaiTrang[7] : 0;
        }
        if (dangmacao) tileAo += chisoAo[7];
        if (dangmacquan) tileQuan += chisoQuan[7];
        if (dangmacgang) tileGang += chisoGang[7];
        if (dangmacgiay) tileGiay += chisoGiay[7];
        if (dangmacrada) tileRada += chisoRada[7];
        if (dangmacglt) tileGlt += chisoGlt[7];

        float k1 = KiCongThem * (1 + tileCT / 100f);
        float k2 = k1 * (1 + tileAo / 100f);
        float k3 = k2 * (1 + tileQuan / 100f);
        float k4 = k3 * (1 + tileGang / 100f);
        float k5 = k4 * (1 + tileGiay / 100f);
        float k6 = k5 * (1 + tileRada / 100f);
        float k7 = k6 * (1 + tileGlt / 100f);

        if (choPhepHienThi) {
            this.KiGoc += KiCongThem;
            if (this.KiGoc >= 550000) this.KiGoc = 550000;
        }
        this.KiNhanVat += k7;
    }
    public void giamKiGoc(int KiCongThem) {
        int[] chisoCaiTrang = getChisoTuSlot(5);
        int[] chisoAo       = getChisoTuSlot(0);
        int[] chisoQuan     = getChisoTuSlot(1);
        int[] chisoGang     = getChisoTuSlot(2);
        int[] chisoGiay     = getChisoTuSlot(3);
        int[] chisoRada     = getChisoTuSlot(4);
        int[] chisoGlt      = getChisoTuSlot(6);

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0, tileGlt = 0;

        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 8) ? chisoCaiTrang[7] : 0;
        }
        if (dangmacao) tileAo += chisoAo[7];
        if (dangmacquan) tileQuan += chisoQuan[7];
        if (dangmacgang) tileGang += chisoGang[7];
        if (dangmacgiay) tileGiay += chisoGiay[7];
        if (dangmacrada) tileRada += chisoRada[7];
        if (dangmacglt) tileGlt += chisoGlt[7];

        float k1 = KiCongThem * (1 + tileCT / 100f);
        float k2 = k1 * (1 + tileAo / 100f);
        float k3 = k2 * (1 + tileQuan / 100f);
        float k4 = k3 * (1 + tileGang / 100f);
        float k5 = k4 * (1 + tileGiay / 100f);
        float k6 = k5 * (1 + tileRada / 100f);
        float k7 = k6 * (1 + tileGlt / 100f);

        this.KiNhanVat -= k7;
    }
    public void tangSucDanhGoc(int SucDanhCongThem, boolean choPhepHienThi) {
        int[] chisoCaiTrang = getChisoTuSlot(5);
        int[] chisoAo       = getChisoTuSlot(0);
        int[] chisoQuan     = getChisoTuSlot(1);
        int[] chisoGang     = getChisoTuSlot(2);
        int[] chisoGiay     = getChisoTuSlot(3);
        int[] chisoRada     = getChisoTuSlot(4);
        int[] chisoGlt      = getChisoTuSlot(6);

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0, tileGlt = 0;

        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 9) ? chisoCaiTrang[8] : 0;
        }
        if (dangmacao) tileAo += chisoAo[8];
        if (dangmacquan) tileQuan += chisoQuan[8];
        if (dangmacgang) tileGang += chisoGang[8];
        if (dangmacgiay) tileGiay += chisoGiay[8];
        if (dangmacrada) tileRada += chisoRada[8];
        if (dangmacglt) tileGlt += chisoGlt[8];

        float s1 = SucDanhCongThem * (1 + tileCT / 100f);
        float s2 = s1 * (1 + tileAo / 100f);
        float s3 = s2 * (1 + tileQuan / 100f);
        float s4 = s3 * (1 + tileGang / 100f);
        float s5 = s4 * (1 + tileGiay / 100f);
        float s6 = s5 * (1 + tileRada / 100f);
        float s7 = s6 * (1 + tileGlt / 100f);

        if (choPhepHienThi) {
            this.SucDanhGoc += SucDanhCongThem;
            if (this.SucDanhGoc >= 25000) this.SucDanhGoc = 25000;
        }
        this.SucDanhNhanVat += s7;
    }
    public void giamSucDanhGoc(int SucDanhCongThem) {
        int[] chisoCaiTrang = getChisoTuSlot(5);
        int[] chisoAo       = getChisoTuSlot(0);
        int[] chisoQuan     = getChisoTuSlot(1);
        int[] chisoGang     = getChisoTuSlot(2);
        int[] chisoGiay     = getChisoTuSlot(3);
        int[] chisoRada     = getChisoTuSlot(4);
        int[] chisoGlt      = getChisoTuSlot(6);

        float tileCT = 0, tileAo = 0, tileQuan = 0, tileGang = 0, tileGiay = 0, tileRada = 0, tileGlt = 0;

        if (NhanVatXuLy.getDangMacCaiTrang() || NhanVatXuLy.getDangMacAvatar()) {
            tileCT += (chisoCaiTrang != null && chisoCaiTrang.length >= 9) ? chisoCaiTrang[8] : 0;
        }
        if (dangmacao) tileAo += chisoAo[8];
        if (dangmacquan) tileQuan += chisoQuan[8];
        if (dangmacgang) tileGang += chisoGang[8];
        if (dangmacgiay) tileGiay += chisoGiay[8];
        if (dangmacrada) tileRada += chisoRada[8];
        if (dangmacglt) tileGlt += chisoGlt[8];

        float s1 = SucDanhCongThem * (1 + tileCT / 100f);
        float s2 = s1 * (1 + tileAo / 100f);
        float s3 = s2 * (1 + tileQuan / 100f);
        float s4 = s3 * (1 + tileGang / 100f);
        float s5 = s4 * (1 + tileGiay / 100f);
        float s6 = s5 * (1 + tileRada / 100f);
        float s7 = s6 * (1 + tileGlt / 100f);

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
    public void tangHpPtHienTai(float HpCongThem){
        this.HpHienTai *= (1f+HpCongThem/100f);
        this.HpHienTai = Math.min(HpHienTai,HpNhanVat);
    }
    public void tangHpHienTai(float HpCongThem){
        this.HpHienTai += HpCongThem;
        this.HpHienTai = Math.min(HpHienTai,HpHopThe);
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
        this.KiHienTai = Math.min(KiHienTai,KiHopThe);
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
    public void tangVang(long soLuong) {
        this.vang += soLuong;
    }
    public void tangNgoc(long soLuong) {
        this.ngoc += soLuong;
    }

    public void tangCapCayDau(){
        this.capcaydau += 1;
    }
    public void tangDau(int soluong){
        this.soDauThan += soluong;
    }

    public void tangCapSkill(int i) {
        if (i >= 0 && i < 9 && (capSkill[i] < 7 || capSkill[i] < 8 && i == 3)) {
            capSkill[i]++;
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
    public void giamVang(long soLuong) {
        this.vang -= soLuong;
    }

    public void giamNgoc(long soLuong) {
        this.ngoc -= soLuong;
    }
    public void giamDau(){
        this.soDauThan -= 1;
    }

    private int[] getChisoTuSlot(int slot) {
        Item item = hanhTrangDangMac.get(slot);
        return item != null ? item.getChiso() : null;
    }

    //setter hop the

//    public synchronized void setHpHopThe(float Hp) {
//        this.HpHopThe = Hp;
//    }
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
    }

    public void setKiHienTai(float Ki) {
        this.KiHienTai = Ki;
    }

    public void setChiMangSuDung(int chimang) {
        this.ChiMangSuDung = chimang;
    }

    public void setSatThuongChiMangSuDung(int satThuongChiMang) {
        this.SatThuongChiMangSuDung = satThuongChiMang;
    }

    public void setGiamSatThuongSuDung(int giamSatThuongSuDung) {
        this.GiamSatThuongSuDung = giamSatThuongSuDung;
    }

    public float getChiMangSuDung() {
        return ChiMangSuDung;
    }

    public float getSatThuongChiMangSuDung() {
        return SatThuongChiMangSuDung;
    }

    public float getGiamSatThuongSuDung() {
        return GiamSatThuongSuDung;
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
    public void dangMacGlt(boolean dangMacGlt){
        if (dangMacGlt){
            dangmacglt = true;
        } else {
            dangmacglt = false;
        }
    }

    public void setNappa(boolean duDieuKien){
        if (duDieuKien){
            setKichHoatNappa = true;
        } else {
            setKichHoatNappa = false;
        }
    }
    public void taoDeTu(String ten, boolean taoDeLanDau) {
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
                config.chan_gong_de_tu,config.than_thu_de_tu,
                config.lechMapDeTu,
                null,null,null,null,null,null,nhanVat,this, taoDeLanDau
            );
            deTu.setDanhSachDat(nhanVat.danhSachDat);
            deTu.setGioiHanToaDo(nhanVat.getGioiHanXMax(), nhanVat.getGioiHanYMax());
            if (taoDeLanDau) ApiService.taoDeTu();
        }
    }

    public void capNhat() {
        // đậu thần
        if (soDauThanHienTai < 2 * this.getCapCayDau() + 3) {
            timeTangMotDauThan -= Gdx.graphics.getDeltaTime();
            if (timeTangMotDauThan <= 0) {
                soDauThanHienTai += 1;
                timeTangMotDauThan = this.getCapCayDau()/2f * 60f;
            }
        }

        //api du lieu
        if (timeCapNhatAPISaveDuLieu > 0) {
            timeCapNhatAPISaveDuLieu -= Gdx.graphics.getDeltaTime();
            if (timeCapNhatAPISaveDuLieu <= 0) {
                timeCapNhatAPISaveDuLieu = 20f; // sync data 20s/lần
                UserResponse currentUser = State_Management.getUserResponse();
                currentUser.vang = vang;
                currentUser.ngoc = ngoc;
                currentUser.sucManh = sucManh;
                currentUser.mapHienTai = veHUD.layTenMap();
                currentUser.x = nhanVat.getX();
                currentUser.y = nhanVat.getY();
                if (this.coDeTu()) {
                    currentUser.coDeTu = true;

                    DeTuTheoUser deTuTheoUser = new DeTuTheoUser();

                    deTuTheoUser.sucManh = this.deTu.getSucManh();
                    currentUser.deTu = deTuTheoUser;
                }
                if (currentUser != null) {
                    ApiService.saveGameAsync(currentUser);
                }
            }
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
    public void setVeHUD(VeHUD veHUD) {
        this.veHUD = veHUD;
    }

    public void tangVeQuayKhoa(int soluong) {
        this.soVeQuayKhoa += soluong;
    }

    public void giamVeQuayKhoa(int soluong) {
        this.soVeQuayKhoa -= soluong;
    }

    public int getSoVeQuayKhoa() {
        return soVeQuayKhoa;
    }

    public boolean checkCoItemTrongHanhTrang(String id) {
        for (Item item : this.getHanhTrang()) {
            if (item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Item layItemTrongHanhTrang(String id) {
        for (Item item : this.getHanhTrang()) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public boolean duChoTrongHanhTrang(int choTrong) {
        if (hanhTrang.size()+choTrong > MAXHANHTRANG) return false;
        return true;
    }

    public boolean isDaNhanQuaTanThu() {
        return daNhanQuaTanThu;
    }

    public void daNhanQuaTanThu() {
        this.daNhanQuaTanThu = true;
    }

    public void suDungGiftCode(String giftcode, List<String> danhSachPhanThuong) {
        String loi = null;
        if (!checkCoCode(giftcode)) {
            loi = "Mã code không tồn tại";
        } else if (checkSuDungCode(giftcode)) {
            loi = "Mã code đã được sử dụng";
        } else {
            loi = duDieuKienDungCode(giftcode);
        }

        if (loi == null) {
            giftCodeTanThuDaDung.add(giftcode);
            veHUD.themItemTest.suDungGiftCode(giftcode, danhSachPhanThuong);
        } else {
            veHUD.setTinNhanPet(loi,2f);
        }
    }

    public boolean checkCoCode(String giftcode) {
        return giftCodeTanThu.contains(giftcode);
    }

    public boolean checkSuDungCode(String giftcode) {
        return giftCodeTanThuDaDung.contains(giftcode);
    }

    public String duDieuKienDungCode(String giftcode) {
        String loi = null;
        switch (giftcode) {
            case "HDG01":
                if (checkCoItemTrongHanhTrang("ve_quay_npc_haidang")) {
                    if (layItemTrongHanhTrang("ve_quay_npc_haidang").getSoLuong() >= 999) {
                        loi = "Đã đạt giới hạn vé quay, vui lòng thử lại sau";
                    }
                } else {
                    if (!duChoTrongHanhTrang(1)) {
                        loi = "Cần ít nhất 1 ô trống hành trang";
                    }
                }
                break;
            case "HDG02":
                boolean coBoHuyet = checkCoItemTrongHanhTrang("bo_huyet");
                boolean coCuongNo = checkCoItemTrongHanhTrang("cuong_no");
                int soLuongOtrong = 0;
                if (!coCuongNo) soLuongOtrong++;
                if (!coBoHuyet) soLuongOtrong++;
                if (!duChoTrongHanhTrang(soLuongOtrong)) {
                    loi = "Cần ít nhất "+soLuongOtrong+" ô trống hành trang";
                }
                break;
            case "HDG03":
                if (!duChoTrongHanhTrang(1)) {
                    loi = "Cần ít nhất "+1+" ô trống hành trang";
                }
                break;
        }
        return loi;
    }


    // Helper: chuyển từ Item trong game -> ItemCanLuu để gửi lên backend
    public ItemCanLuu convertItem(Item item, String viTri) {
        if (item == null) return null;

        ItemCanLuu itemDB = new ItemCanLuu();
        itemDB.maItem = item.getId() == null ? "" : item.getId();
        itemDB.ten = item.getTenItem() == null ? "" : item.getTenItem();
        itemDB.moTa = item.getMoTa() == null ? "" : item.getMoTa();
        itemDB.loai = item.getLoaiDB() == null ? "" : item.getLoaiDB();

        itemDB.soLuong = item.getSoLuong();
        itemDB.hanhTinh = item.getHanhtinh() == null ? "" : item.getHanhtinh();
        itemDB.setKichHoat = item.getSetkichhoat() == null ? null : item.getSetkichhoat();

        itemDB.soSaoPhaLe = item.getSoSaoPhaLe();
        itemDB.soSaoPhaLeCuongHoa = item.getSoSaoPhaLeCuongHoa();
        itemDB.soCap = item.getSoCap();

        itemDB.hanSuDung = (int) item.getHanSuDung();
        itemDB.sucManhYeuCau = String.valueOf(item.getSucManhYeuCau());

        itemDB.linkTexture = item.getLinkTexture() == null ? "" : item.getLinkTexture();
        Gson gson = new Gson();
        itemDB.chiso = gson.toJson(item.getChiso());

        itemDB.viTri = viTri;

        itemDB.uuid = item.uuid;
        return itemDB;
    }

    public void luuDuLieuItem(Item item,int tmpId, String viTri) {
        try {
            GameSocket.addItem(tmpId, item, viTri);
        } catch(Exception e) {

        }
    }

    public void setLaiHanhTrangTuDatabase(List<ItemCanLuu> listItem) {
        if (listItem != null) {
            Gson gson = new Gson();
            hanhTrang.clear();
            hanhTrangRuongDo.clear();
            hanhTrangDangMac.clear();
            {
                for (int i = 0; i < 8; i++) {
                    hanhTrangDangMac.add(null);
                }
            }
            if (this.coDeTu()) {
                this.deTu.getHanhTrangDangMac().clear();
                {
                    for (int i = 0; i < 6; i++) {
                        this.deTu.getHanhTrangDangMac().add(null);
                    }
                }
            }
            for (ItemCanLuu item : listItem) {
                Item itemCLient = new Item(
                    item.maItem, item.ten, LoaiItem.valueOf(item.loai),
                    item.linkTexture,
                    item.moTa, item.soLuong,
                    gson.fromJson(item.chiso, int[].class),
                    item.hanhTinh, Long.parseLong(item.sucManhYeuCau), item.setKichHoat, item.soSaoPhaLe, item.soSaoPhaLeCuongHoa, item.soCap, item.hanSuDung
                );
                itemCLient.uuid = item.uuid;
                switch (item.viTri) {
                    case "hanhtrang":
                        this.themItemVaoHanhTrangNoSave(itemCLient);
                        break;
                    case "ruongdo":
                        this.themItemVaoHanhTrangRuongDo(itemCLient);
                        break;
                    case "hanhtrangdangmac":
                        veHUD.xulyitem.macDoVuaLogin(itemCLient);
                        break;
                    case "hanhtrangdetu":
                        veHUD.xulyitem.macDoVuaLoginDeTu(itemCLient);
                        break;
                }
            }
        }
    }
}
