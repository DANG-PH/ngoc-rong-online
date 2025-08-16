package com.dang.dragonboy.nhan_vat;

import com.dang.dragonboy.du_lieu.TrangThaiDeTu;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
public class AvatarDoOffsetDeTu {

    private static final Map<String, Map<TrangThaiDeTu, List<DoLechModular>>> OFFSET_AVATAR = new HashMap<>();
    private static final Map<String, Map<TrangThaiDeTu, List<DoLechModular>>> OFFSET_AO = new HashMap<>();
    private static final Map<String, Map<TrangThaiDeTu, List<DoLechModular>>> OFFSET_QUAN = new HashMap<>();

    static {
        // --- AVATAR ---
        OFFSET_AVATAR.put("Goku_base", taoLech(
            lechDau(-0.3f, -15.5f),
            lechDau(3.3f, -9f,3.3f, -9f,3.3f, -9f,3.3f, -9f,3.3f, -9f),
            lechDau(3f, -23.5f),
            lechDau(0.2f, -31f),
            lechDau(-0.3f, -15.5f)
        ));
        OFFSET_AVATAR.put("Krillin_base", taoLech(
            lechDau(-0.3f, -15.5f),
            lechDau(3.3f, -9f,3.3f, -9f,3.3f, -9f,3.3f, -9f,3.3f, -9f),
            lechDau(3f, -23.5f),
            lechDau(0.2f, -31f),
            lechDau(-0.3f, -15.5f)
        ));
        OFFSET_AVATAR.put("Yamcha_base", taoLech(
            lechDau(-0.3f, -21f),
            lechDau(3.3f, -14.5f,3.3f, -14.5f,3.3f, -14.5f,3.3f, -14.5f,3.3f, -14.5f),
            lechDau(3f, -29f),
            lechDau(0.2f, -36.5f),
            lechDau(-0.3f, -21f)
        ));

        OFFSET_AVATAR.put("avt_vip", taoLech(
            lechDau(-2f, -13.5f),
            lechDau( 3.3f, -9f,3.3f, -9f,3.3f, -9f,3.3f, -9f,3.3f, -9f),
            lechDau( 1f, -23.5f),
            lechDau(-3.2f, -31f),
            lechDau(-0.3f, -15.5f)
        ));

        OFFSET_AVATAR.put("traidat_base", taoLech(
            lechDau(-4.3f, -15f),
            lechDau(3.3f, -10f,3.3f, -10f,3.3f, -10f,3.3f, -10f,3.3f, -10f),
            lechDau(-2f, -23.5f),
            lechDau(-3.8f, -31f),
            lechDau(5f, -19f)
        ));

        // --- ÁO ---
        OFFSET_AO.put("set_base", taoLech(
            lechThan(0f, -0.4f),
            lechThan(1.5f, 5.2f,1.5f, 5.2f,1.5f, 5.2f,1.5f, 5.2f,1.5f, 5.2f ),
            lechThan(-4f, 6f),
            lechThan(-6f, 7f ),
            lechThan(5f, 8f)
        ));

        OFFSET_AO.put("set_cam", taoLech(
            lechThan(0f, 0f),
            lechThan(1.5f, 5.2f,1.5f, 5.2f,1.5f, 5.2f,1.5f, 5.2f,1.5f, 5.2f ),
            lechThan(-4f, 6f),
            lechThan(-5.5f, 6.5f ),
            lechThan(0f, 0f)
        ));

        OFFSET_AO.put("set_huy_diet", taoLech(
            lechThan(-1f, 3.5f),
            lechThan(1.5f, 7f,1.5f, 7f,1.5f, 7f,1.5f, 7f,1.5f, 7f),
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
    public static Map<TrangThaiDeTu, List<DoLechModular>> getOffset(String avatar, String ao, String quan) {
        Map<TrangThaiDeTu, List<DoLechModular>> result = new HashMap<>();

        Map<TrangThaiDeTu, List<DoLechModular>> lechAvt = OFFSET_AVATAR.getOrDefault(avatar, getMacDinh());
        Map<TrangThaiDeTu, List<DoLechModular>> lechAo = OFFSET_AO.getOrDefault(ao, getMacDinh());
        Map<TrangThaiDeTu, List<DoLechModular>> lechQuan = OFFSET_QUAN.getOrDefault(quan, getMacDinh());

        for (TrangThaiDeTu tt : TrangThaiDeTu.values()) {
            List<DoLechModular> la = lechAvt.get(tt);
            List<DoLechModular> lb = lechAo.get(tt);
            List<DoLechModular> lc = lechQuan.get(tt);

            int size = Math.min(Math.min(la.size(), lb.size()), lc.size());

            List<DoLechModular> resultList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                resultList.add(la.get(i).cong(lb.get(i)).cong(lc.get(i)));
            }

            result.put(tt, resultList);
        }

        return result;
    }


    // ✅ Hàm tạo offset theo trạng thái
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

    // ✅ Offset mặc định
    private static Map<TrangThaiDeTu, List<DoLechModular>> getMacDinh() {
        Map<TrangThaiDeTu, List<DoLechModular>> macDinh = new HashMap<>();
        List<DoLechModular> zeroList = lechChan(); // 5 phần tử 0
        for (TrangThaiDeTu tt : TrangThaiDeTu.values()) {
            macDinh.put(tt, zeroList);
        }
        return macDinh;
    }

    // Chỉ cần đầu (2 số sau)
    private static List<DoLechModular> lechDau(float... values) {
        List<DoLechModular> list = new ArrayList<>();
        for (int i = 0; i < values.length; i += 2) {
            list.add(new DoLechModular(0f, 0f, values[i], values[i + 1],0,0));
        }
        return list;
    }

    private static List<DoLechModular> lechThan(float... values) {
        List<DoLechModular> list = new ArrayList<>();
        for (int i = 0; i < values.length; i += 2) {
            list.add(new DoLechModular(values[i], values[i + 1], 0f, 0f,0,0));
        }
        return list;
    }

    private static List<DoLechModular> lechChan() {
        return List.of(
            new DoLechModular(0f, 0f, 0f, 0f,0,0),
            new DoLechModular(0f, 0f, 0f, 0f,0,0),
            new DoLechModular(0f, 0f, 0f, 0f,0,0),
            new DoLechModular(0f, 0f, 0f, 0f,0,0),
            new DoLechModular(0f, 0f, 0f, 0f,0,0)
        );
    }
}
