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
        // Nếu vượt mốc pity mềm -> tăng tỉ lệ mỗi lần
        if (npc.demKhongVIP >= npc.mocPityMem) {
            // mỗi lần sau mốc 75 tăng 0.5% (0.005f)
            float congThem = 0.005f * (npc.demKhongVIP - npc.mocPityMem + 1);
            tileHienTai = Math.min(0.10f, npc.tileCoBanVIP + congThem); // trần 10%
        }
        // pity cứng: đảm bảo lần thứ 90 ra VIP
        boolean batBuocRaVIP = (npc.demKhongVIP + 1 >= npc.mocPityCung);

        boolean laVIP = batBuocRaVIP || Math.random() < tileHienTai;

        int ketQua;
        if (laVIP) {
            int[] oVIP = {0, 4, 8, 12};
            ketQua = oVIP[MathUtils.random(oVIP.length - 1)];
            npc.demKhongVIP = 0;
        } else {
            int[] oThuong = {1,3,6,7,9,10,13,14,15};
            int[] oVipLoai2 = {2,5,11};
            boolean chonVipLoai2 = Math.random() < 0.01;
            if (chonVipLoai2) {
                ketQua = oVipLoai2[MathUtils.random(oVipLoai2.length - 1)];
            } else {
                ketQua = oThuong[MathUtils.random(oThuong.length - 1)];
            }
            npc.demKhongVIP++;
        }
        return ketQua;
    }

    public static int randomSoLanQuay(int so) {
        return so+16*2;
    }
}
