package com.dang.dragonboy.nhan_vat;

import java.util.*;

public class CaiTrangOffset {

    private static final Map<String, Map<TrangThai, List<DoLechModular>>> OFFSET_CAI_TRANG = new HashMap<>();

    static {
        OFFSET_CAI_TRANG.put("goku_black", taoGokuBlack());
        OFFSET_CAI_TRANG.put("goku_black_rose", taoGokuBlackRose());
        OFFSET_CAI_TRANG.put("bong_tai_3_xayda", taoVegitoXeno());
        OFFSET_CAI_TRANG.put("bong_tai_3_traidat", taoVegito());
        OFFSET_CAI_TRANG.put("bong_tai_2_xayda", taoVegitoSsj());
        OFFSET_CAI_TRANG.put("bong_tai_2_traidat", taoVegitoSsj());
        OFFSET_CAI_TRANG.put("gohan_beast", taoGohanBeast());
        OFFSET_CAI_TRANG.put("bong_tai_1_traidat", taoVegito());
        OFFSET_CAI_TRANG.put("bong_tai_1_xayda", taoVegito());
        OFFSET_CAI_TRANG.put("hop_the_thuong_traidat", taoGotenks());
        OFFSET_CAI_TRANG.put("xiao", taoXiao());
        OFFSET_CAI_TRANG.put("ayaka", taoAyaka());
        OFFSET_CAI_TRANG.put("broly_lssj", taoBrolyLssj());
        OFFSET_CAI_TRANG.put("khi_7", taoKhi7());
    }

    public static Map<TrangThai, List<DoLechModular>> getOffset(String ten) {
        return OFFSET_CAI_TRANG.getOrDefault(ten, getMacDinh());
    }

    private static Map<TrangThai, List<DoLechModular>> getMacDinh() {
        Map<TrangThai, List<DoLechModular>> macDinh = new HashMap<>();
        List<DoLechModular> zero = lechChan();
        for (TrangThai tt : TrangThai.values()) {
            macDinh.put(tt, zero);
        }
        return macDinh;
    }

    // ===== Các bộ cải trang =====

    private static Map<TrangThai, List<DoLechModular>> taoGokuBlack() {
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
            lech(5f, 8f, 7f, -19f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoGokuBlackRose() {
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
            lech(5f, 8f, 10f, -19f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoVegitoXeno() {
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
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoVegitoSsj() {
        return taoLech(
            lech(0f, -2.2f, 2f, -17.3f,0,0),
            lechDiChuyen(new float[][] {
                {2f, 8f, 4.5f, -10f,0,0},
                {2f, 8f, 4.5f, -10f,0,0},
                {2f, 8f, 4.5f, -10f,0,0},
                {2f, 8f, 4.5f, -10f,0,0},
                {2f, 8f, 4.5f, -10f,0,0}
            }),
            lech(-1f, 7f, 0.5f, -20.8f,0,0),
            lech(-7f, 7.5f, 1f, -31.5f,0,0),
            lech(-23f, 20f, -18f, -15f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoGohanBeast() {
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
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoVegito() {
        return taoLech(
            lech(0f, -5f, 1f, -21.8f,0,0),
            lechDiChuyen(new float[][] {
                {3f, 5f, 4.5f, -18f,0,0},
                {3f, 5f, 4.5f, -18f,0,0},
                {3f, 5f, 4.5f, -18f,0,0},
                {3f, 5f, 4.5f, -18f,0,0},
                {3f, 5f, 4.5f, -18f,0,0}
            }),
            lech(-1f, 7f, 1f, -29.5f,0,0),
            lech(-7f, 7.5f, 1f, -32f,0,0),
            lech(-23f, 20f, -20f, -18f,0,0),
            lech(5f, 3f, 2f, -15.5f,0,0),
            lech(1f, 3f, 4f, -35f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoGotenks() {
        return taoLech(
            lech(0f, -7f, 1f, -27f,0,0),
            lechDiChuyen(new float[][]{
                {3f, 5f, 3f, -16f,0,0},
                {3f, 5f, 3f, -16f,0,0},
                {3f, 5f, 3f, -16f,0,0},
                {3f, 5f, 3f, -16f,0,0},
                {3f, 5f, 3f, -16f,0,0}
            }),
            lech(-1f, 7f, 0f, -29.5f,0,0),
            lech(-7f, 7.5f, 0f, -32.5f,0,0),
            lech(0f, -7f, 1f, -26.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoXiao() {
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
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoAyaka() {
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
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoBrolyLssj() {
        return taoLech(
            lech(0f, -5f, 1f, -21.8f,0,0),
            lechDiChuyen(new float[][] {
                {3f, 5f, 4.5f, -18f,0,0},
                {3f, 5f, 4.5f, -18f,0,0},
                {3f, 5f, 4.5f, -18f,0,0},
                {3f, 5f, 4.5f, -18f,0,0},
                {3f, 5f, 4.5f, -18f,0,0}
            }),
            lech(-1f, 7f, 1f, -29.5f,0,0),
            lech(-7f, 7.5f, 1f, -32f,0,0),
            lech(-23f, 20f, -20f, -19f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0),
            lech(0f, -1f, -0.3f, -15.5f,0,0)
        );
    }

    private static Map<TrangThai, List<DoLechModular>> taoKhi7() {
        return taoLech(
            lech(1f, -5f, -6f, -54f,0,0),
            lechDiChuyen(new float[][] {
                {3f  , -2f, 10f, -49.5f, 0  , 0},
                {-18f-29f, -4f, -19f, -53.5f, -20-29f, 0},
                {-1f-39f  , -18f, -29f, -65.5f, -20-39f, 0},
                {-33f-33f, -15f, -23f, -77.5f, -20-33f, 0},
                {3f-29f  , -5f, -19f, -53.5f, -20-29f  , 0},
            }),
            lech(3f, 1f, 1f, -48.5f,0,0),
            lech(-3f, 2f, -12f, -67f,0,0),
            lech(-26f, 20f, -28f, -29.5f,0,0),
            lech(5f, -1f, 0f, -53f,0,0),
            lech(0f, -1f, -2f, -50.5f,0,0)
        );
    }

    // ===== Hàm tiện ích chung =====

    private static Map<TrangThai, List<DoLechModular>> taoLech(
        List<DoLechModular> dungYen,
        List<DoLechModular> diChuyen,
        List<DoLechModular> nhay,
        List<DoLechModular> roi,
        List<DoLechModular> bayNgang,
        List<DoLechModular> thu,
        List<DoLechModular> gong
    ) {
        Map<TrangThai, List<DoLechModular>> map = new HashMap<>();
        map.put(TrangThai.DUNG_YEN, dungYen);
        map.put(TrangThai.DI_CHUYEN, diChuyen);
        map.put(TrangThai.NHAY, nhay);
        map.put(TrangThai.ROI, roi);
        map.put(TrangThai.BAY_NGANG, bayNgang);
        map.put(TrangThai.THU, thu);
        map.put(TrangThai.GONG, gong);
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
