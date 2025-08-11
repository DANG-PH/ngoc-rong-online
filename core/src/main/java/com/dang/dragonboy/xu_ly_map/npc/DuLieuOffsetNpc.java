package com.dang.dragonboy.xu_ly_map.npc;

import java.util.HashMap;
import java.util.Map;

public class DuLieuOffsetNpc {
    private static final Map<String, NpcOffset> OFFSET_DATA = new HashMap<>();

    static {
        // Đặt offset cho từng NPC
        OFFSET_DATA.put("ong_gohan", new NpcOffset(0, 20, 0, 5, 0, -3));
        OFFSET_DATA.put("bulma",    new NpcOffset(1, 18, 0, 4, 0, -2));
        OFFSET_DATA.put("vua_vegeta", new NpcOffset(2, 22, 0, 6, 0, -4));
    }

    public static NpcOffset get(String npcName) {
        return OFFSET_DATA.getOrDefault(npcName, new NpcOffset(0, 0, 0, 0, 0, 0));
    }
}
