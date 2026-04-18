package com.dang.dragonboy.xu_ly_map.npc;

import com.dang.dragonboy.network.DTO.ShopItemServerData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopCache {
    private static final ShopCache INSTANCE = new ShopCache();
    private final Map<Integer, List<ShopItemServerData>> cache = new HashMap<>();

    public static ShopCache getInstance() { return INSTANCE; }
    public boolean daCo(int npcBaseId) { return cache.containsKey(npcBaseId); }
    public List<ShopItemServerData> lay(int npcBaseId) { return cache.get(npcBaseId); }
    public void luu(int npcBaseId, List<ShopItemServerData> data) { cache.put(npcBaseId, data); }
}
