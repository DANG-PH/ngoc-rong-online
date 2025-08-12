package com.dang.dragonboy.xu_ly_map;

import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.NpcOffset;
import com.dang.dragonboy.xu_ly_map.npc.NpcTaiAnh;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public abstract class MapCoBan {
    protected List<HitboxDat> danhSachDat = new ArrayList<>();
    protected List<Npc> danhSachNpc = new ArrayList<>();
    protected Map<String, NpcTaiAnh> npcTaiAnhMap = new HashMap<>();
    protected Map<String, NpcOffset> npcOffsetMap = new HashMap<>();

    public List<HitboxDat> LayDanhSachDat() {
        return danhSachDat;
    }

    public List<Npc> LayDanhSachNpc() {
        return danhSachNpc;
    }

    public NpcTaiAnh getNpcTaiAnh(String ten) {
        return npcTaiAnhMap.get(ten);
    }

    public Map<String, NpcTaiAnh> getNpcTaiAnhMap() {
        return npcTaiAnhMap;
    }

    public NpcOffset getNpcOffset(String ten) {
        return npcOffsetMap.get(ten);
    }

    // Map nào cũng phải có hàm này để load dữ liệu
    public abstract void taiDuLieuMap();

    public float getChieuRongMap() {
        return 1020f;
    }

    public float getChieuCaoMap() {
        return 610f;
    }
}
