package com.dang.dragonboy.xu_ly_map.npc;

import java.util.HashMap;
import java.util.Map;

public class DuLieuOffsetNpc {
    private static final Map<String, NpcOffset> OFFSET_DATA = new HashMap<>();

    static {
        // Đặt offset cho từng NPC
        OFFSET_DATA.put("ong_gohan", new NpcOffset(0, 8.5f, 0f, -20f, 0f, 0));
        OFFSET_DATA.put("admin_haidang", new NpcOffset(0f, -1.5f, -0.3f, -16f,0,0));
        OFFSET_DATA.put("admin_thanhle", new NpcOffset(0f, -1f, -1.2f, -16f,0,0));
        OFFSET_DATA.put("admin_dungle", new NpcOffset(0f, -0.5f, 1f, -19f,0,0));
        OFFSET_DATA.put("thay_hieu", new NpcOffset(0f, -11f, 1f, -24.2f,0,0));
        OFFSET_DATA.put("vua_vegeta", new NpcOffset(2, 22, 0, 6, 0, -4));
    }

    public static NpcOffset get(String npcName) {
        return OFFSET_DATA.getOrDefault(npcName, new NpcOffset(0, 0, 0, 0, 0, 0));
    }
}
