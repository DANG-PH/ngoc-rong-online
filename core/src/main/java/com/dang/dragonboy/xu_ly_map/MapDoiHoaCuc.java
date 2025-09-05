package com.dang.dragonboy.xu_ly_map;

import com.dang.dragonboy.xu_ly_map.npc.LoaiNPC;

public class MapDoiHoaCuc extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // Thêm nền đất chính
        danhSachDat.add(new HitboxDat(0, -38-96, 864, 175+38+96));
        danhSachDat.add(new HitboxDat(864, -38-96, 864, 175-48+38+96));
        danhSachDat.add(new HitboxDat(864*2, -38-96, 1248, 175+48+38+96));
        //npc
        themNpc("admin_dungle", LoaiNPC.NGUOI,250,188);
        themNpc("thay_hieu", LoaiNPC.NGUOI,864*2+300,188+48);
    }
    @Override
    public float getChieuRongMap() {
        return 2976f;
    }
    @Override
    public float getChieuCaoMap() {
        return 1000f;
    }
}
