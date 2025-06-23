package com.dang.dragonboy.nhan_vat;

import java.util.HashMap;
import java.util.Map;

public class AvatarDoOffset {

    // Cấu trúc lưu offset: avatar -> doSet -> trạng thái -> độ lệch
    private static final Map<String, Map<String, Map<TrangThai, DoLechModular>>> OFFSET_MAP = new HashMap<>();

    static {
        // GOKU_BASE + SET_CAM
        themOffset("goku_base", "set_cam", taoLech(
            new DoLechModular(0f, 0f, -0.3f, -15.5f),   // DUNG_YEN
            new DoLechModular(1.5f, 5.2f, 3.3f, -9f),    // DI_CHUYEN
            new DoLechModular(-4f, 6f, 3f, -23.5f),      // NHAY
            new DoLechModular(-5.5f, 6.5f, 0.2f, -31f),  // ROI
            new DoLechModular(0f, 0f, -0.3f, -15.5f)     // BAY_NGANG
        ));

        // GOKU_BASE + SET_HUY_DIET
        themOffset("goku_base", "set_huy_diet", taoLech(
            new DoLechModular(5f, 0f, -2f, -13.5f),
            new DoLechModular(1.5f, 5.2f, 3.3f, -9f),
            new DoLechModular(-4f, 6f, 1f, -23.5f),
            new DoLechModular(-5.5f, 6.5f, -1.2f, -31f),
            new DoLechModular(0f, 0f, -0.3f, -15.5f)
        ));

        // AVATAR ĐẸP TRAI + SET_CAM
        themOffset("avt_vip", "set_cam", taoLech(
            new DoLechModular(0f, 0f, -2f, -13.5f),
            new DoLechModular(1.5f, 5.2f, 3.3f, -9f),
            new DoLechModular(-4f, 6f, 1f, -23.5f),
            new DoLechModular(-5.5f, 6.5f, -1.2f, -31f),
            new DoLechModular(0f, 0f, -0.3f, -15.5f)
        ));

        // AVATAR ĐẸP TRAI + SET_HUY_DIET
        themOffset("avt_vip", "set_huy_diet", taoLech(
            new DoLechModular(3f, 0f, -0.3f, -15.5f),
            new DoLechModular(1.5f, 5.2f, 3.3f, -9f),
            new DoLechModular(-4f, 6f, 3f, -23.5f),
            new DoLechModular(-5.5f, 6.5f, 0.2f, -31f),
            new DoLechModular(0f, 0f, -0.3f, -15.5f)
        ));

        // đoạn sau thêm đồ + avt vào đây
    }

    // Lấy offset theo avatar + set đồ
    public static Map<TrangThai, DoLechModular> getOffset(String avatar, String doSet) {
        return OFFSET_MAP
            .getOrDefault(avatar, new HashMap<>())
            .getOrDefault(doSet, getMacDinh());
    }

    // Hàm tạo Map offset cho từng trạng thái
    private static Map<TrangThai, DoLechModular> taoLech(
        DoLechModular dungYen,
        DoLechModular diChuyen,
        DoLechModular nhay,
        DoLechModular roi,
        DoLechModular bayNgang
    ) {
        Map<TrangThai, DoLechModular> map = new HashMap<>();
        map.put(TrangThai.DUNG_YEN, dungYen);
        map.put(TrangThai.DI_CHUYEN, diChuyen);
        map.put(TrangThai.NHAY, nhay);
        map.put(TrangThai.ROI, roi);
        map.put(TrangThai.BAY_NGANG, bayNgang);
        return map;
    }

    // Thêm offset vào OFFSET_MAP
    private static void themOffset(String avatar, String doSet, Map<TrangThai, DoLechModular> offset) {
        if (!OFFSET_MAP.containsKey(avatar)) {
            OFFSET_MAP.put(avatar, new HashMap<>());
        }
        OFFSET_MAP.get(avatar).put(doSet, offset);
    }

    // Offset mặc định nếu không khớp avatar + đồ
    private static Map<TrangThai, DoLechModular> getMacDinh() {
        Map<TrangThai, DoLechModular> macDinh = new HashMap<>();
        DoLechModular lech = new DoLechModular(0, 0, 0, 0);
        for (TrangThai tt : TrangThai.values()) {
            macDinh.put(tt, lech);
        }
        return macDinh;
    }
}
