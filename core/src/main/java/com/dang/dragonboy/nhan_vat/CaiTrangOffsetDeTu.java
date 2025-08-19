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
        OFFSET_CAI_TRANG.put("xiao", taoXiao());
        OFFSET_CAI_TRANG.put("ayaka", taoAyaka());
        OFFSET_CAI_TRANG.put("khi_7", taoKhi7(0,0));
        OFFSET_CAI_TRANG.put("khi_6", taoKhi7(0,0));
        OFFSET_CAI_TRANG.put("khi_5", taoKhi7(0,0));
        OFFSET_CAI_TRANG.put("khi_4", taoKhi7(-7,3));
        OFFSET_CAI_TRANG.put("khi_3", taoKhi7(14,10));
        OFFSET_CAI_TRANG.put("khi_2", taoKhi7(12.9f,6));
        OFFSET_CAI_TRANG.put("khi_1", taoKhi7(12.9f,6));
        OFFSET_CAI_TRANG.put("khi_8", taoKhi7(-2,1));
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
            lech(0f, -1f, 0.1f, -15.4f,0,0),
            lechDiChuyen(new float[][] {
                {2.2f, 5.2f, 3.3f, -10f,0,0},
                {2.2f, 5.2f, 3.3f, -10f,0,0},
                {2.2f, 5.2f, 3.3f, -10f,0,0},
                {2.2f, 5.2f, 3.3f, -10f,0,0},
                {2.2f, 5.2f, 3.3f, -10f,0,0}
            }),
            lech(-4f, 6f, 0.5f, -21.5f,0,0),
            lech(-5.5f, 6.5f, -1.2f, -28f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(8f, 8f, 6f, -15.5f,0,0),
            lech(-1f, 8f, 6f, -22f,0,0)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoGokuBlackRose() {
        return taoLech(
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lechDiChuyen(new float[][] {
                {2.2f, 5.2f, 3.3f, -10f,0,0},
                {2.2f, 5.2f, 3.3f, -10f,0,0},
                {2.2f, 5.2f, 3.3f, -10f,0,0},
                {2.2f, 5.2f, 3.3f, -10f,0,0},
                {2.2f, 5.2f, 3.3f, -10f,0,0}
            }),
            lech(-4f, 6f, 0.5f, -21.5f,0,0),
            lech(-5.5f, 6.5f, -1.2f, -28f,0,0),
            lech(5f, 8f, 7f, -19f,0,0),
            lech(8f, 8f, 6f, -15.5f,0,0),
            lech(-1f, 8f, 6f, -22f,0,0)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoVegitoXeno() {
        return taoLech(
            lech(11.3f, -18.5f, 5f, -63.5f,0,0),
            lechDiChuyen(new float[][] {
                {18f, -4f, 7.5f, -47.5f,0,0},
                {18f, -4f, 7.5f, -47.5f,0,0},
                {18f, -4f, 7.5f, -47.5f,0,0},
                {18f, -4f, 7.5f, -47.5f,0,0},
                {18f, -4f, 7.5f, -47.5f,0,0}
            }),
            lech(6f, 7.5f, 0.5f, -53.5f,0,0),
            lech(-5.5f, 6.5f, -4f, -56.5f,0,0),
            lech(11.3f, -18.5f, 5f, -63.5f,0,0),
            lech(8f, 8f, 6f, -15.5f,0,0),
            lech(-1f, 8f, 6f, -22f,0,0)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoVegitoSsj() {
        return taoLech(
            lech(0f, -2.2f, 2f, -17.3f,0,0),
            lechDiChuyen(new float[][] {
                {2f, 4f, 4.5f, -13.8f,0,0},
                {2f, 4f, 4.5f, -13.8f,0,0},
                {2f, 4f, 4.5f, -13.8f,0,0},
                {2f, 4f, 4.5f, -13.8f,0,0},
                {2f, 4f, 4.5f, -13.8f,0,0}
            }),
            lech(-1f, 7f, 0.5f, -20.8f,0,0),
            lech(-7f, 7.5f, 1f, -31.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(8f, 8f, 6f, -15.5f,0,0),
            lech(-1f, 8f, 6f, -22f,0,0)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoGohanBeast() {
        return taoLech(
            lech(0f, -4f, 2.2f, -20f,0,0),
            lechDiChuyen(new float[][] {
                {4f, 4f, 4.5f, -13.8f,0,0},
                {4f, 4f, 4.5f, -13.8f,0,0},
                {4f, 4f, 4.5f, -13.8f,0,0},
                {4f, 4f, 4.5f, -13.8f,0,0},
                {4f, 4f, 4.5f, -13.8f,0,0}
            }),
            lech(6.5f, 7f, 9f, -25f,0,0),
            lech(-7f, 7.5f, 1f, -34.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(8f, 8f, 6f, -15.5f,0,0),
            lech(-1f, 8f, 6f, -22f,0,0)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoCtDeTuTraiDat() {
        return taoLech(
            lech(0f, 5f, -0.5f, -10f,0,0),
            lechDiChuyen(new float[][] {
                {5f, 2f, 4.5f, -14f,0,0},
                {5f, 2f, 4.5f, -14f,0,0},
                {5f, 2f, 4.5f, -14f,0,0},
                {5f, 2f, 4.5f, -14f,0,0},
                {5f, 2f, 4.5f, -14f,0,0}
            }),
            lech(3.5f, 7f, 6f, -18.5f,0,0),
            lech(-5f, 7.5f, -1f, -29f,0,0),
            lech(3.5f, 7f, 6f, -18.5f,0,0),
            lech(8f, 8f, 6f, -15.5f,0,0),
            lech(-1f, 8f, 6f, -22f,0,0)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoXiao() {
        return taoLech(
            lech(0f, -24f, -1.5f, -55f,0,0),
            lechDiChuyen(new float[][] {
                {2.2f, -23f, 2.8f, -54f,0,0},
                {2.2f, -23f, 2.8f, -54f,0,0},
                {2.2f, -23f, 2.8f, -54f,0,0},
                {2.2f, -23f, 2.8f, -54f,0,0},
                {2.2f, -23f, 2.8f, -54f,0,0}
            }),
            lech(-4f, -12f, 0.5f, -49f,0,0),
            lech(-5.5f, -12f, -1.2f, -54f,0,0),
            lech(-17f, -1f, -13f, -35f,0,0),
            lech(8f, 8f, 6f, -15.5f,0,0),
            lech(-1f, 8f, 6f, -22f,0,0)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoAyaka() {
        return taoLech(
            lech(2f, -3f, -5f, -60f,0,0),
            lechDiChuyen(new float[][] {
                {2.2f, -6f, -7f, -63f,0,0},
                {2.2f, -6f, -7f, -63f,0,0},
                {2.2f, -6f, -7f, -63f,0,0},
                {2.2f, -6f, -7f, -63f,0,0},
                {2.2f, -6f, -7f, -63f,0,0}
            }),
            lech(0, -3f, -5f, -60f,0,0),
            lech(-1.5f, -3f, -8f, -66f,0,0),
            lech(0f, 10f, -4f, -47f,0,0),
            lech(8f, 8f, 6f, -15.5f,0,0),
            lech(-1f, 8f, 6f, -22f,0,0)
        );
    }

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoKhi7(float offsetDauY, float offsetDauX) {
        return taoLech(
            lech(1f, -5f, -6f+offsetDauX, -54f+offsetDauY,0,0),
            lechDiChuyen(new float[][] {
                {3f  , -2f, 10f+offsetDauX, -49.5f+offsetDauY, 0  , 0},
                {-18f-29f, -4f, -19f+offsetDauX, -53.5f+offsetDauY, -20-29f, 0},
                {-1f-39f  , -18f, -29f+offsetDauX, -65.5f+offsetDauY, -20-39f, 0},
                {-33f-33f, -15f, -23f+offsetDauX, -77.5f+offsetDauY, -20-33f, 0},
                {3f-29f  , -5f, -19f+offsetDauX, -53.5f+offsetDauY, -20-29f  , 0},
            }),
            lech(3f, 1f, 1f+offsetDauX, -48.5f+offsetDauY,0,0),
            lech(-3f, 2f, -12f+offsetDauX, -67f+offsetDauY,0,0),
            lech(-26f, 20f, -28f+offsetDauX, -29.5f+offsetDauY,0,0),
            lech(5f, -1f, 0f+offsetDauX, -53f+offsetDauY,0,0),
            lech(0f, -1f, -2f+offsetDauX, -50.5f+offsetDauY,0,0)
        );
    }

    // ===== Hàm tiện ích chung =====

    private static Map<TrangThaiDeTu, List<DoLechModular>> taoLech(
        List<DoLechModular> dungYen,
        List<DoLechModular> diChuyen,
        List<DoLechModular> nhay,
        List<DoLechModular> roi,
        List<DoLechModular> bayNgang,
        List<DoLechModular> thu,
        List<DoLechModular> gong
    ) {
        Map<TrangThaiDeTu, List<DoLechModular>> map = new HashMap<>();
        map.put(TrangThaiDeTu.DUNG_YEN, dungYen);
        map.put(TrangThaiDeTu.DI_CHUYEN, diChuyen);
        map.put(TrangThaiDeTu.NHAY, nhay);
        map.put(TrangThaiDeTu.ROI, roi);
        map.put(TrangThaiDeTu.BAY_NGANG, bayNgang);
        map.put(TrangThaiDeTu.THU, thu);
        map.put(TrangThaiDeTu.GONG, gong);
        return map;
    }

    private static List<DoLechModular> lech(float thanX, float thanY, float dauX, float dauY, float chanX, float chanY) {
        return List.of(new DoLechModular(thanX, thanY, dauX, dauY, chanX, chanY));
    }

    private static List<DoLechModular> lechChan() {
        return List.of(
            new DoLechModular(0, 0, 0, 0,0,0),
            new DoLechModular(0, 0, 0, 0,0,0),
            new DoLechModular(0, 0, 0, 0,0,0),
            new DoLechModular(0, 0, 0, 0,0,0),
            new DoLechModular(0, 0, 0, 0,0,0)
        );
    }
    private static List<DoLechModular> lechDiChuyen(
        float[][] cacFrame
    ) {
        List<DoLechModular> list = new ArrayList<>();
        for (float[] frame : cacFrame) {
            list.add(new DoLechModular(frame[0], frame[1], frame[2], frame[3], frame[4], frame[5]));
        }
        return list;
    }
}
