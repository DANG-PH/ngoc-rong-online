package com.dang.dragonboy.nhan_vat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Map;
import java.util.HashMap;
import com.dang.dragonboy.nhan_vat.van_bay.VanBayCauHinh;

import java.util.List;
import java.util.ArrayList;
import com.dang.dragonboy.xu_ly_map.HitboxDat;

enum TrangThai {
    DUNG_YEN,
    DI_CHUYEN,
    NHAY,
    ROI,
    BAY_NGANG
}
public class NhanVat {
    public float x, y;
    private String ten;
    public float vx = 0, vy = 0;
    public float rong, cao;
    public boolean dangDungDat = true;

    private TrangThai trangThai = TrangThai.DUNG_YEN;
    private Map<TrangThai, DoLechModular> lechTheoTrangThai = new HashMap<>();

    // Ảnh các phần
    private String avt;
    private Texture avtTexture; // ảnh cache
    private Texture dau_dung, dau_chay;
    private Texture than_dung, than_nhay, than_roi;
    private Texture[] than_chay;
    private Texture than_bay;
    private Texture chan_dung, chan_nhay, chan_roi;
    private Texture[] chan_chay;
    private Texture chan_bay;

    // Cho animation chạy
    private int frame = 0;
    private float timeChay = 0f;

    private final float trongLuc = -0.5f;
    private final float tocDoDiChuyen = 7f;
    private final float doCaoDat = 175f;

    private final float tiLe = 0.5f;

    private boolean dangBayNgang = false;
    private boolean daNhay = false;
    private float demThoiGianBay = 0;
    private final float delayRoi = 15f;

    private boolean phimTraiDangGiu = false;
    private boolean phimPhaiDangGiu = false;
    private boolean phimNhayDangGiu = false;

    private boolean flipX = false;

    // Offset để tùy chỉnh vị trí đầu/thân theo trạng thái
    private float lechThanX = 0f, lechThanY = 0f;
    private float lechDauX = 0f, lechDauY = 0f;

    private String tenVanBay = "candauvan";

    private VanBayCauHinh vanBayCauHinh;
    private int frameVanBay = 0;
    private float timeVanBay = 0f;

    private float gioiHanXMax;
    private float gioiHanYMax;
    private List<HitboxDat> danhSachDat = new ArrayList<>();

    public void setDanhSachDat(List<HitboxDat> ds) {
        this.danhSachDat = ds;
    }

    // ==== THUỘC TÍNH GAMEPLAY ==== //
    private long sucManh = 99_999_999_999L;
    private int theLuc = 100;
    private int hpHienTai = 100;
    private int hpToiDa = 100;
    private int kiHienTai = 100;
    private int kiToiDa = 100;
    private int soDauThan = 1;
    private int vang = 1000000000;
    private int ngoc = 1000000000;
    private String capBac = "Thần Xayda cấp 9+99.99%";

    public long getSucManh() {
        return sucManh;
    }

    public int getTheLuc() {
        return theLuc;
    }

    public int getHpHienTai() {
        return hpHienTai;
    }

    public int getHpToiDa() {
        return hpToiDa;
    }

    public int getKiHienTai() {
        return kiHienTai;
    }

    public int getKiToiDa() {
        return kiToiDa;
    }

    public int getSoDauThan() {
        return soDauThan;
    }

    public int getVang() {
        return vang;
    }

    public int getNgoc() {
        return ngoc;
    }

    public String getCapBac() {
        return capBac;
    }

    public void setHpHienTai(int hp) {
        this.hpHienTai = Math.max(0, Math.min(hp, hpToiDa));
    }

    public void setKiHienTai(int ki) {
        this.kiHienTai = Math.max(0, Math.min(ki, kiToiDa));
    }

    public void tangVang(int soLuong) {
        this.vang += soLuong;
    }

    public void tangNgoc(int soLuong) {
        this.ngoc += soLuong;
    }
    public void giamVang(int soLuong) {
        this.vang -= soLuong;
    }

    public void giamNgoc(int soLuong) {
        this.ngoc -= soLuong;
    }

    public void tangDau(int soluong){
        this.soDauThan += soluong;
    }

    public NhanVat(float x, float y,
                   Texture dau_dung, Texture dau_chay,
                   Texture than_dung, Texture than_nhay, Texture than_roi, Texture[] than_chay,
                   Texture chan_dung, Texture chan_nhay, Texture chan_roi, Texture[] chan_chay,
                   Texture than_bay,Texture chan_bay,Map<TrangThai, DoLechModular> lechTheoTrangThai,String avt) {
        this.x = x;
        this.y = y;

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

        this.rong = chan_dung.getWidth() * tiLe;
        this.cao = chan_dung.getHeight() * tiLe + than_dung.getHeight() * tiLe + dau_dung.getHeight() * 0.15f;

        this.lechTheoTrangThai = lechTheoTrangThai; //  Dòng này cực kỳ quan trọng
        taiAnhVanBay("candauvan"); // tùy chọn
    }
    public String doiavatar(){
        return avt;
    }
    public void dispose() {
        if (avtTexture != null) {
            avtTexture.dispose();
            avtTexture = null;
        }
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

    public void setGioiHanToaDo(float chieuRongMap, float chieuCaoMap) {
        this.gioiHanXMax = chieuRongMap - rong;
        this.gioiHanYMax = chieuCaoMap - cao;
    }

    public void capNhat() {
        boolean giuPhimNgang = phimTraiDangGiu || phimPhaiDangGiu;
        // Nếu đang đứng trên đất và giữ trái/phải + giữ ↑ thì vào trạng thái bay ngang
        if (dangDungDat && giuPhimNgang && phimNhayDangGiu) {
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
            if (giuPhimNgang && !dangBayNgang && vy < 0) {
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
        } else {
            if (vx != 0) {
                trangThai = TrangThai.DI_CHUYEN;
            } else {
                trangThai = TrangThai.DUNG_YEN;
            }
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
        float gioiHanXMin = 0;
        x = Math.max(gioiHanXMin, Math.min(x, gioiHanXMax));

        float gioiHanYMin=0;
        y =Math.max(gioiHanYMin, Math.min(y, gioiHanYMax));
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

    private void taiAnhVanBay(String ten) {
        this.tenVanBay = ten;
        Texture[] frames = new Texture[] {
            new Texture("vatpham/vanbay/" + ten + "/" + ten + "1.png"),
            new Texture("vatpham/vanbay/" + ten + "/" + ten + "2.png"),
            new Texture("vatpham/vanbay/" + ten + "/" + ten + "3.png"),
            new Texture("vatpham/vanbay/" + ten + "/" + ten + "4.png")
        };

        switch (ten) {
            case "phuong_hoang_lua":
                vanBayCauHinh = new VanBayCauHinh(frames, 0.5f, false, -0f, -40f);
                break;
            case "candauvan":
            default:
                vanBayCauHinh = new VanBayCauHinh(frames, 0.1f, true, -32f, -20f);
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

        float daoDong = (trangThai == TrangThai.DUNG_YEN || trangThai == TrangThai.BAY_NGANG) ? (float) Math.sin(thoiGian) * 1.08f : 0f;

        Texture chanVe = chan_dung;
        Texture thanVe = than_dung;
        Texture dauVe = dau_dung;

        if (trangThai == TrangThai.BAY_NGANG) {
            timeVanBay += Gdx.graphics.getDeltaTime();
            if (timeVanBay > 0.12f) {
                frameVanBay = (frameVanBay + 1) % vanBayCauHinh.frames.length;
                timeVanBay = 0;
            }
        }

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
        DoLechModular lech = lechTheoTrangThai.get(trangThai);
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

        batch.draw(chanVe, anchorX, y, chanW * flipScale, chanH);

        float thanX = anchorX + (chanW / 2f - thanW / 2f) * flipScale;
        float thanY = y + chanH + daoDong;
        float dauX = anchorX + (chanW / 2f - dauW / 2f) * flipScale;
        float dauY = thanY + thanH;
        batch.draw(dauVe, dauX + lechDauX * flipScale, dauY + lechDauY, dauW * flipScale, dauH);
        batch.draw(thanVe, thanX + lechThanX * flipScale, thanY - 10.2f + lechThanY, thanW * flipScale, thanH);
        if (trangThai == TrangThai.BAY_NGANG) {
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
        }
    }
}
