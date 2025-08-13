package com.dang.dragonboy.xu_ly_map;

import com.dang.dragonboy.xu_ly_map.npc.DuLieuOffsetNpc;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.NpcTaiAnh;

public class MapNhaGohan extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // them dat chinh
        danhSachDat.add(new HitboxDat(0, 0, 1420, 175));
        // them npc
        themNpc("ong_gohan",400,188);
        themNpc("admin_haidang",900,188);
    }

    @Override
    public float getChieuRongMap() {
        return 1420f;
    }
    @Override
    public float getChieuCaoMap() {
        return 760f;
    }
}
