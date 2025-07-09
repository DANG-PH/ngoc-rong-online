package com.dang.dragonboy.nhan_vat;

import java.util.HashMap;
import java.util.Map;

public class CaiTrangOffset {

    private static final Map<String, Map<TrangThai, DoLechModular>> OFFSET_CAI_TRANG = new HashMap<>();

    // Khởi tạo static
    static {
        OFFSET_CAI_TRANG.put("goku_black", taoGokuBlack());
        OFFSET_CAI_TRANG.put("goku_black_rose", taoGokuBlackRose());
        OFFSET_CAI_TRANG.put("vegito_xeno", taoVegitoXeno());
        OFFSET_CAI_TRANG.put("vegito_ssj", taoVegitoSsj());
        OFFSET_CAI_TRANG.put("gohan_beast", taoGohanBeast());
    }

    // Hàm công khai để lấy offset theo tên cải trang
    public static Map<TrangThai, DoLechModular> getOffset(String ten) {
        return OFFSET_CAI_TRANG.getOrDefault(ten, getMacDinh());
    }

    // mặc định
    private static Map<TrangThai, DoLechModular> getMacDinh() {
        Map<TrangThai, DoLechModular> macDinh = new HashMap<>();
        DoLechModular lech = new DoLechModular(0, 0, 0, 0);
        for (TrangThai tt : TrangThai.values()) {
            macDinh.put(tt, lech);
        }
        return macDinh;
    }

    // goku_black
    private static Map<TrangThai, DoLechModular> taoGokuBlack() {
        Map<TrangThai, DoLechModular> map = new HashMap<>();
        map.put(TrangThai.DUNG_YEN, new DoLechModular(0f, -1f, 0.1f, -15.4f));
        map.put(TrangThai.DI_CHUYEN, new DoLechModular(2.2f, 5.2f, 3.3f, -10f));
        map.put(TrangThai.NHAY, new DoLechModular(-4f, 6f, 0.5f, -21.5f));
        map.put(TrangThai.ROI, new DoLechModular(-5.5f, 6.5f, -1.2f, -28f));
        map.put(TrangThai.BAY_NGANG, new DoLechModular(0f, -1f, -0.3f, -15.5f));
        return map;
    }

    // goku_black_rose
    private static Map<TrangThai, DoLechModular> taoGokuBlackRose() {
        Map<TrangThai, DoLechModular> map = new HashMap<>();
        map.put(TrangThai.DUNG_YEN, new DoLechModular(0f, -1f, -0.3f, -15.5f));
        map.put(TrangThai.DI_CHUYEN, new DoLechModular(2.2f, 5.2f, 3.3f, -10f));
        map.put(TrangThai.NHAY, new DoLechModular(-4f, 6f, 0.5f, -21.5f));
        map.put(TrangThai.ROI, new DoLechModular(-5.5f, 6.5f, -1.2f, -28f));
        map.put(TrangThai.BAY_NGANG, new DoLechModular(0f, -1f, -0.3f, -15.5f));
        return map;
    }
    // Vegito Xeno
    private static Map<TrangThai, DoLechModular> taoVegitoXeno() {
        Map<TrangThai, DoLechModular> map = new HashMap<>();
        map.put(TrangThai.DUNG_YEN, new DoLechModular(11.3f, -18.5f, 5f, -63.5f));
        map.put(TrangThai.DI_CHUYEN, new DoLechModular(18f, -4f, 7.5f, -47.5f));
        map.put(TrangThai.NHAY, new DoLechModular(6f, 7.5f, 0.5f, -53.5f));
        map.put(TrangThai.ROI, new DoLechModular(-5.5f, 6.5f, -4f, -56.5f));
        map.put(TrangThai.BAY_NGANG, new DoLechModular(11.3f, -18.5f, 5f, -63.5f));
        return map;
    }
    // Vegito Ssj
    private static Map<TrangThai, DoLechModular> taoVegitoSsj() {
        Map<TrangThai, DoLechModular> map = new HashMap<>();
        map.put(TrangThai.DUNG_YEN, new DoLechModular(0f, -2.2f, 2f, -17.3f));
        map.put(TrangThai.DI_CHUYEN, new DoLechModular(2f, 4f, 4.5f, -13.8f));
        map.put(TrangThai.NHAY, new DoLechModular(-1f, 7f, 0.5f, -20.8f));
        map.put(TrangThai.ROI, new DoLechModular(-7f, 7.5f, 1f, -31.5f));
        map.put(TrangThai.BAY_NGANG, new DoLechModular(0f, -1f, -0.3f, -15.5f));
        return map;
    }
    // Gohan Beast
    private static Map<TrangThai, DoLechModular> taoGohanBeast() {
        Map<TrangThai, DoLechModular> map = new HashMap<>();
        map.put(TrangThai.DUNG_YEN, new DoLechModular(0f, -4f, 2.2f, -20f));
        map.put(TrangThai.DI_CHUYEN, new DoLechModular(4f, 4f, 4.5f, -13.8f));
        map.put(TrangThai.NHAY, new DoLechModular(6.5f, 7f, 9f, -25f));
        map.put(TrangThai.ROI, new DoLechModular(-7f, 7.5f, 1f, -34.5f));
        map.put(TrangThai.BAY_NGANG, new DoLechModular(0f, -1f, -0.3f, -15.5f));
        return map;
    }
}
