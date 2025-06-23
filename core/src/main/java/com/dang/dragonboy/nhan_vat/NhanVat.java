package com.dang.dragonboy.nhan_vat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Map;
import java.util.HashMap;
enum TrangThai {
    DUNG_YEN,
    DI_CHUYEN,
    NHAY,
    ROI,
    BAY_NGANG
}
public class NhanVat {
    public float x, y;
    public float vx = 0, vy = 0;
    public float rong, cao;
    public boolean dangDungDat = true;

    private TrangThai trangThai = TrangThai.DUNG_YEN;
    private Map<TrangThai, DoLechModular> lechTheoTrangThai = new HashMap<>();

    // Ảnh các phần
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
    private final float tocDoDiChuyen = 4f;
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

    private Texture[] canDauVanFrames;
    private int frameCanDauVan = 0;
    private float timeCanDauVan = 0f;

    public NhanVat(float x, float y,
                   Texture dau_dung, Texture dau_chay,
                   Texture than_dung, Texture than_nhay, Texture than_roi, Texture[] than_chay,
                   Texture chan_dung, Texture chan_nhay, Texture chan_roi, Texture[] chan_chay,
                   Texture than_bay,Texture chan_bay,Map<TrangThai, DoLechModular> lechTheoTrangThai) {
        this.x = x;
        this.y = y;

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
        this.cao = chan_dung.getHeight() * tiLe + than_dung.getHeight() * tiLe + dau_dung.getHeight() * tiLe;

        this.lechTheoTrangThai = lechTheoTrangThai; //  Dòng này cực kỳ quan trọng
        canDauVanFrames = new Texture[] {
            new Texture("vatpham/vanbay/candauvan/candauvan1.png"),
            new Texture("vatpham/vanbay/candauvan/candauvan2.png"),
            new Texture("vatpham/vanbay/candauvan/candauvan3.png")
        };
    }



    public void diTrai() {
        phimTraiDangGiu = true;
        phimPhaiDangGiu = false;
    }

    public void diPhai() {
        phimPhaiDangGiu = true;
        phimTraiDangGiu = false;
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
                    vy = 1.5f; // tạo hiệu ứng bay lên nhẹ, không cộng dồn mãi
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
        x += vx;
        y += vy;

        if (y <= doCaoDat) {
            y = doCaoDat;
            vy = 0;
            dangDungDat = true;
            daNhay = false; // reset ngay khi chạm đất
        } else {
            dangDungDat = false;
        }
        // Giới hạn không cho ra khỏi bản đồ
        float gioiHanXMin = 0;
        float gioiHanXMax = 1420 - rong; // chiều rộng nhân vật đã được tính sẵn

        x = Math.max(gioiHanXMin, Math.min(x, gioiHanXMax));

        float gioiHanYMin=0;
        float gioiHanYMax = 760 - cao; // trần trên bản đồ, có thể chỉnh
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

    public void ve(SpriteBatch batch, float thoiGian) {

        float daoDong = (trangThai == TrangThai.DUNG_YEN || trangThai == TrangThai.BAY_NGANG) ? (float) Math.sin(thoiGian) * 1.08f : 0f;

        Texture chanVe = chan_dung;
        Texture thanVe = than_dung;
        Texture dauVe = dau_dung;

        if (trangThai == TrangThai.BAY_NGANG) {
            timeCanDauVan += Gdx.graphics.getDeltaTime();
            if (timeCanDauVan > 0.12f) {
                frameCanDauVan = (frameCanDauVan + 1) % canDauVanFrames.length;
                timeCanDauVan = 0;
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
                    timeChay = 0; // reset lại nhưng không về 0 để mượt hơn
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
            Texture cloud = canDauVanFrames[frameCanDauVan];
            float cloudW = cloud.getWidth() * 0.1f;
            float cloudH = cloud.getHeight() * 0.1f;
            float flipCloud = flipX ? 1f : -1f;
            batch.draw(
                cloud,
                anchorX + (chanW / 2f - cloudW / 2f-32) * flipCloud,
                y - 20f,
                cloudW * flipCloud,
                cloudH
            );
        }
    }
}
