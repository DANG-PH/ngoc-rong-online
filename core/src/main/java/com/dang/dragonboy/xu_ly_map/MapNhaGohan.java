package com.dang.dragonboy.xu_ly_map;

import com.dang.dragonboy.xu_ly_map.npc.DuLieuOffsetNpc;
import com.dang.dragonboy.xu_ly_map.npc.LoaiNPC;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.NpcTaiAnh;

public class MapNhaGohan extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        // them dat chinh
        danhSachDat.add(new HitboxDat(0, 0, 1420, 175));
        // them npc
        themNpc("ong_gohan", LoaiNPC.NGUOI,400,188);
        themNpc("dau_traidat_7", LoaiNPC.CAYDAU, 600, 192);
        themNpc("ruong_do", LoaiNPC.RUONGDO, 120, 190);
        themNpc("dui_ga", LoaiNPC.DUIGA, 1178, 205);
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
