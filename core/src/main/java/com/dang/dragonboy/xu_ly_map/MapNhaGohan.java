package com.dang.dragonboy.xu_ly_map;

import com.dang.dragonboy.xu_ly_map.npc.DuLieuOffsetNpc;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.NpcTaiAnh;

public class MapNhaGohan extends MapCoBan {

    @Override
    public void taiDuLieuMap() {
        danhSachDat.add(new HitboxDat(0, 0, 1420, 175));

        Npc gohan = new Npc("onggohan", 500, 300);
        danhSachNpc.add(gohan);

        // Lấy offset từ file DuLieuOffsetNpc
        npcOffsetMap.put("ong_gohan", DuLieuOffsetNpc.get("ong_gohan"));

        // Load ảnh
        npcTaiAnhMap.put("ong_gohan", new NpcTaiAnh("ong_gohan"));
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
