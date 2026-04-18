package com.dang.dragonboy.xu_ly_map;

import com.dang.dragonboy.network.ApiService;
import com.dang.dragonboy.xu_ly_map.npc.*;
import com.dang.dragonboy.xu_ly_map.MapDataCache;
import com.dang.dragonboy.xu_ly_map.MapServerData;
import com.dang.dragonboy.xu_ly_map.NpcServerData;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public abstract class MapCoBan {
    protected int mapId; // subclass gán trong constructor

    protected List<HitboxDat> danhSachDat = new ArrayList<>();
    protected List<Npc> danhSachNpc = new ArrayList<>();
    protected Map<String, NpcTaiAnh> npcTaiAnhMap = new HashMap<>();
    protected Map<String, NpcOffset> npcOffsetMap = new HashMap<>();

    // Gọi cái này thay vì taiDuLieuMap() trực tiếp
    public void khoiTao(Runnable onHoanThanh) {
        taiDuLieuMap();

        if (mapId == 0) {
            onHoanThanh.run();
            return;
        }

        MapDataCache cache = MapDataCache.getInstance();
        if (cache.daCo(mapId)) {
            apDungDuLieuServer(cache.lay(mapId));
            onHoanThanh.run();
            return;
        }

        ApiService.layNpcCuaMap(mapId, danhSach -> {
            cache.luu(mapId, danhSach);
            apDungDuLieuServer(danhSach);
            onHoanThanh.run();
        });
    }

    private void apDungDuLieuServer(List<NpcServerData> danhSach) {
        for (NpcServerData npc : danhSach) {
            if (!npc.is_active) continue;
            LoaiNPC loai = LoaiNPC.valueOf(npc.loai_npc);
            themNpc(npc.ten_npc, loai, npc.x, npc.y);
        }
    }

    // --- Giữ nguyên toàn bộ phần cũ ---

    public List<HitboxDat> LayDanhSachDat() {
        return danhSachDat;
    }

    public List<Npc> LayDanhSachNpc() {
        return danhSachNpc;
    }

    public void capNhatNpc() {
        danhSachNpc.removeIf(npc -> npc.isDaHuy());
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

    public abstract void taiDuLieuMap();

    public float getChieuRongMap() {
        return 1020f;
    }

    public float getChieuCaoMap() {
        return 610f;
    }
}
