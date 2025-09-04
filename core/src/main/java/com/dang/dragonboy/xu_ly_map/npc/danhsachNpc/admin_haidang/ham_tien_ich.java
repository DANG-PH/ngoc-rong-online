package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_haidang;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.renderUInpc;

import com.badlogic.gdx.math.MathUtils;

public class ham_tien_ich {
    public static boolean chuoiToanSo(String chuoi) {
        try {
            int so = Integer.parseInt(chuoi);
            return so > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void gacha(int soLanGacha, admin_haidang npc) {
        int[] mangGacha = new int[soLanGacha];
        for (int i = 0; i < soLanGacha; i++) {
            mangGacha[i] = randomSo(npc);
        }
        npc.dangGachaX1hayX10 = soLanGacha;
        if (npc.dangGachaX1hayX10 == 1) {
            npc.mangGacha1 = mangGacha;
        } else if (npc.dangGachaX1hayX10 == 10) {
            npc.mangGacha10 = mangGacha;
        }
        npc.chisovong = 0;
        npc.maxQuay = randomSoLanQuay(mangGacha[mangGacha.length-1]);
        npc.dangGacha = true;
    }

    public static int randomSo(admin_haidang npc) {
        float tileHienTai = npc.tileCoBanVIP;
        if (npc.demKhongVIP >= npc.mocPityMem) {
            float congThem = 0.005f * (npc.demKhongVIP - npc.mocPityMem + 1);
            tileHienTai = Math.min(0.10f, npc.tileCoBanVIP + congThem);
        }
        boolean batBuocRaVIP = (npc.demKhongVIP + 1 >= npc.mocPityCung);

        float P = batBuocRaVIP ? 1f : tileHienTai;  // VIP chính
        float rand = (float)Math.random();

        int ketQua;
        if (rand < P) {
            // VIP chính
            int[] oVIP = {0, 4, 8, 12};
            ketQua = oVIP[MathUtils.random(oVIP.length - 1)];
            npc.demKhongVIP = 0;
        } else {
            // Phần còn lại (1 - P)
            float trongSo = rand - P;
            float phanConLai = 1 - P;
            // nhóm 2,5 (1%)
            if (trongSo < 0.01f * phanConLai) {
                int[] nhom015 = {2, 5};
                ketQua = nhom015[MathUtils.random(nhom015.length - 1)];
            }
            // nhóm 15,11,10 (9%)
            else if (trongSo < (0.01f + 0.09f) * phanConLai) {
                int[] nhom15 = {15, 11, 10};
                ketQua = nhom15[MathUtils.random(nhom15.length - 1)];
            }
            // nhóm 6,7 (20%)
            else if (trongSo < (0.01f + 0.09f + 0.20f) * phanConLai) {
                int[] nhom30 = {6, 7};
                ketQua = nhom30[MathUtils.random(nhom30.length - 1)];
            }
            // còn lại (70%)
            else {
                int[] nhomConLai = {1, 9, 13, 14, 3};
                ketQua = nhomConLai[MathUtils.random(nhomConLai.length - 1)];
            }
            npc.demKhongVIP++;
        }
        return ketQua;
    }

    public static int randomSoLanQuay(int so) {
        return so+16*2;
    }
}
