package com.dang.dragonboy.xu_ly_map;

import com.dang.dragonboy.xu_ly_map.npc.LoaiNPC;

public class MapLangAru extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // Thêm nền đất chính
        danhSachDat.add(new HitboxDat(-70, -38, 2540, 175+38));
        //npc
        themNpc("admin_thanhle", LoaiNPC.NGUOI,320,188);
        themNpc("admin_haidang", LoaiNPC.NGUOI,1400,188);
    }
    @Override
    public float getChieuRongMap() {
        return 2400f;
    }
    @Override
    public float getChieuCaoMap() {
        return 1000f;
    }
}
