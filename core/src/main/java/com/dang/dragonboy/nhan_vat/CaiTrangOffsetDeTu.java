package com.dang.dragonboy.nhan_vat;

import com.dang.dragonboy.du_lieu.TrangThaiDeTu;

import java.util.*;

public class CaiTrangOffsetDeTu {

    private static final Map<String, Map<TrangThaiDeTu, List<DoLechModular>>> OFFSET_CAI_TRANG = new HashMap<>();

    static {
        OFFSET_CAI_TRANG.put("goku_black", taoGokuBlack());
        OFFSET_CAI_TRANG.put("goku_black_rose", taoGokuBlackRose());
        OFFSET_CAI_TRANG.put("vegito_xeno", taoVegitoXeno());
        OFFSET_CAI_TRANG.put("vegito_ssj", taoVegitoSsj());
        OFFSET_CAI_TRANG.put("gohan_beast", taoGohanBeast());
        OFFSET_CAI_TRANG.put("set_base_traidat", taoCtDeTuTraiDat());
    }

    public static Map<TrangThaiDeTu, List<DoLechModular>> getOffset(String ten) {
        return OFFSET_CAI_TRANG.getOrDefault(ten, getMacDinh());
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> getMacDinh() {
        Map<TrangThaiDeTu, List<DoLechModular>> macDinh = new HashMap<>();
        List<DoLechModular> zero = lechChan();
        for (TrangThaiDeTu tt : TrangThaiDeTu.values()) {
            macDinh.put(tt, zero);
        }
        return macDinh;
    }

    // ===== Các bộ cải trang =====

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoGokuBlack() {
        return taoLech(
            lech(0f, -1f, 0.1f, -15.4f),
            lechDiChuyen(new float[][] {
                {2.2f, 5.2f, 3.3f, -10f},
                {2.2f, 5.2f, 3.3f, -10f},
                {2.2f, 5.2f, 3.3f, -10f},
                {2.2f, 5.2f, 3.3f, -10f},
                {2.2f, 5.2f, 3.3f, -10f}
            }),
            lech(-4f, 6f, 0.5f, -21.5f),
            lech(-5.5f, 6.5f, -1.2f, -28f),
            lech(0f, -1f, -0.3f, -15.5f)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoGokuBlackRose() {
        return taoLech(
            lech(0f, -1f, -0.3f, -15.5f),
            lechDiChuyen(new float[][] {
                {2.2f, 5.2f, 3.3f, -10f},
                {2.2f, 5.2f, 3.3f, -10f},
                {2.2f, 5.2f, 3.3f, -10f},
                {2.2f, 5.2f, 3.3f, -10f},
                {2.2f, 5.2f, 3.3f, -10f}
            }),
            lech(-4f, 6f, 0.5f, -21.5f),
            lech(-5.5f, 6.5f, -1.2f, -28f),
            lech(0f, -1f, -0.3f, -15.5f)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoVegitoXeno() {
        return taoLech(
            lech(11.3f, -18.5f, 5f, -63.5f),
            lechDiChuyen(new float[][] {
                {18f, -4f, 7.5f, -47.5f},
                {18f, -4f, 7.5f, -47.5f},
                {18f, -4f, 7.5f, -47.5f},
                {18f, -4f, 7.5f, -47.5f},
                {18f, -4f, 7.5f, -47.5f}
            }),
            lech(6f, 7.5f, 0.5f, -53.5f),
            lech(-5.5f, 6.5f, -4f, -56.5f),
            lech(11.3f, -18.5f, 5f, -63.5f)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoVegitoSsj() {
        return taoLech(
            lech(0f, -2.2f, 2f, -17.3f),
            lechDiChuyen(new float[][] {
                {2f, 4f, 4.5f, -13.8f},
                {2f, 4f, 4.5f, -13.8f},
                {2f, 4f, 4.5f, -13.8f},
                {2f, 4f, 4.5f, -13.8f},
                {2f, 4f, 4.5f, -13.8f}
            }),
            lech(-1f, 7f, 0.5f, -20.8f),
            lech(-7f, 7.5f, 1f, -31.5f),
            lech(0f, -1f, -0.3f, -15.5f)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoGohanBeast() {
        return taoLech(
            lech(0f, -4f, 2.2f, -20f),
            lechDiChuyen(new float[][] {
                {4f, 4f, 4.5f, -13.8f},
                {4f, 4f, 4.5f, -13.8f},
                {4f, 4f, 4.5f, -13.8f},
                {4f, 4f, 4.5f, -13.8f},
                {4f, 4f, 4.5f, -13.8f}
            }),
            lech(6.5f, 7f, 9f, -25f),
            lech(-7f, 7.5f, 1f, -34.5f),
            lech(0f, -1f, -0.3f, -15.5f)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoCtDeTuTraiDat() {
        return taoLech(
            lech(0f, 5f, -0.5f, -10f),
            lechDiChuyen(new float[][] {
                {5f, 2f, 4.5f, -14f},
                {5f, 2f, 4.5f, -14f},
                {5f, 2f, 4.5f, -14f},
                {5f, 2f, 4.5f, -14f},
                {5f, 2f, 4.5f, -14f}
            }),
            lech(3.5f, 7f, 6f, -18.5f),
            lech(-5f, 7.5f, -1f, -29f),
            lech(3.5f, 7f, 6f, -18.5f)
        );
    }

    // ===== Hàm tiện ích chung =====

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoLech(
        List<DoLechModular> dungYen,
        List<DoLechModular> diChuyen,
        List<DoLechModular> nhay,
        List<DoLechModular> roi,
        List<DoLechModular> bayNgang
    ) {
        Map<TrangThaiDeTu, List<DoLechModular>> map = new HashMap<>();
        map.put(TrangThaiDeTu.DUNG_YEN, dungYen);
        map.put(TrangThaiDeTu.DI_CHUYEN, diChuyen);
        map.put(TrangThaiDeTu.NHAY, nhay);
        map.put(TrangThaiDeTu.ROI, roi);
        map.put(TrangThaiDeTu.BAY_NGANG, bayNgang);
        return map;
    }

    private static List<DoLechModular> lech(float thanX, float thanY, float dauX, float dauY) {
        return List.of(new DoLechModular(thanX, thanY, dauX, dauY));
    }

    private static List<DoLechModular> lechChan() {
        return List.of(
            new DoLechModular(0, 0, 0, 0),
            new DoLechModular(0, 0, 0, 0),
            new DoLechModular(0, 0, 0, 0),
            new DoLechModular(0, 0, 0, 0),
            new DoLechModular(0, 0, 0, 0)
        );
    }
    private static List<DoLechModular> lechDiChuyen(
        float[][] cacFrame
    ) {
        List<DoLechModular> list = new ArrayList<>();
        for (float[] frame : cacFrame) {
            list.add(new DoLechModular(frame[0], frame[1], frame[2], frame[3]));
        }
        return list;
    }
}
