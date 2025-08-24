package com.dang.dragonboy.xu_ly_map;

import com.dang.dragonboy.xu_ly_map.npc.*;

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

    public void themNpc(String tenNpc, LoaiNPC loainpc, float x, float y) {
        Npc npc = new Npc(tenNpc, loainpc, x, y);
        danhSachNpc.add(npc);
        if (loainpc == LoaiNPC.NGUOI) {
            npcOffsetMap.put(tenNpc, DuLieuOffsetNpc.get(tenNpc));
        }
        npcTaiAnhMap.put(tenNpc, new NpcTaiAnh(tenNpc, loainpc));
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
