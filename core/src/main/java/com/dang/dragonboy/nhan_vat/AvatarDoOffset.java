package com.dang.dragonboy.nhan_vat;

import java.util.HashMap;
import java.util.Map;

public class AvatarDoOffset {

    private static final Map<String, Map<TrangThai, DoLechModular>> OFFSET_AVATAR = new HashMap<>();
    private static final Map<String, Map<TrangThai, DoLechModular>> OFFSET_AO = new HashMap<>();
    private static final Map<String, Map<TrangThai, DoLechModular>> OFFSET_QUAN = new HashMap<>();

    static {
        // --- AVATAR ---
        OFFSET_AVATAR.put("Goku_base", taoLech(
            lechDau(-0.3f, -15.5f),
            lechDau(3.3f, -9f),
            lechDau(3f, -23.5f),
            lechDau(0.2f, -31f),
            lechDau(-0.3f, -15.5f)
        ));
        OFFSET_AVATAR.put("Krillin_base", taoLech(
            lechDau(-0.3f, -15.5f),
            lechDau(3.3f, -9f),
            lechDau(3f, -23.5f),
            lechDau(0.2f, -31f),
            lechDau(-0.3f, -15.5f)
        ));
        OFFSET_AVATAR.put("Yamcha_base", taoLech(
            lechDau(-0.3f, -21f),
            lechDau(3.3f, -14.5f),
            lechDau(3f, -29f),
            lechDau(0.2f, -36.5f),
            lechDau(-0.3f, -21f)
        ));

        OFFSET_AVATAR.put("avt_vip", taoLech(
            lechDau(-2f, -13.5f),
            lechDau( 3.3f, -9f),
            lechDau( 1f, -23.5f),
            lechDau(-3.2f, -31f),
            lechDau(-0.3f, -15.5f)
        ));

        // --- ÁO ---
        OFFSET_AO.put("set_base", taoLech(
            lechThan(0f, -0.4f),
            lechThan(1.5f, 5.2f ),
            lechThan(-4f, 6f),
            lechThan(-6f, 7f ),
            lechThan(0f, 0f)
        ));

        OFFSET_AO.put("set_cam", taoLech(
            lechThan(0f, 0f),
            lechThan(1.5f, 5.2f ),
            lechThan(-4f, 6f),
            lechThan(-5.5f, 6.5f ),
            lechThan(0f, 0f)
        ));

        OFFSET_AO.put("set_huy_diet", taoLech(
            lechThan(-1f, 4f),
            lechThan(1.5f, 7f),
            lechThan(0f, 2f),
            lechThan(-4f, 2f),
            lechThan(-1f, 4f)
        ));

        // --- QUẦN ---
        OFFSET_QUAN.put("set_cam", taoLech(
            lechChan(),
            lechChan(),
            lechChan(),
            lechChan(),
            lechChan()
        ));

        OFFSET_QUAN.put("set_huy_diet", taoLech(
            lechChan(),
            lechChan(),
            lechChan(),
            lechChan(),
            lechChan()
        ));
    }

    // ✅ Lấy offset tổng hợp
    public static Map<TrangThai, DoLechModular> getOffset(String avatar, String ao, String quan) {
        Map<TrangThai, DoLechModular> lechTong = new HashMap<>();
        Map<TrangThai, DoLechModular> lechAvt = OFFSET_AVATAR.getOrDefault(avatar, getMacDinh());
        Map<TrangThai, DoLechModular> lechAo = OFFSET_AO.getOrDefault(ao, getMacDinh());
        Map<TrangThai, DoLechModular> lechQuan = OFFSET_QUAN.getOrDefault(quan, getMacDinh());

        for (TrangThai tt : TrangThai.values()) {
            DoLechModular a = lechAvt.getOrDefault(tt, new DoLechModular(0, 0, 0, 0));
            DoLechModular b = lechAo.getOrDefault(tt, new DoLechModular(0, 0, 0, 0));
            DoLechModular c = lechQuan.getOrDefault(tt, new DoLechModular(0, 0, 0, 0));
            lechTong.put(tt, a.cong(b).cong(c));
        }

        return lechTong;
    }

    // ✅ Hàm tạo offset theo trạng thái
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

    // ✅ Offset mặc định
    private static Map<TrangThai, DoLechModular> getMacDinh() {
        Map<TrangThai, DoLechModular> macDinh = new HashMap<>();
        DoLechModular zero = new DoLechModular(0, 0, 0, 0);
        for (TrangThai tt : TrangThai.values()) {
            macDinh.put(tt, zero);
        }
        return macDinh;
    }

    // Chỉ cần đầu (2 số sau)
    private static DoLechModular lechDau(float dauX, float dauY) {
        return new DoLechModular(0f, 0f, dauX, dauY);
    }

    // Chỉ cần thân (2 số đầu)
    private static DoLechModular lechThan(float thanX, float thanY) {
        return new DoLechModular(thanX, thanY, 0f, 0f);
    }

    // offset full 0
    private static DoLechModular lechChan() {
        return new DoLechModular(0f, 0f, 0f, 0f);
    }
}
