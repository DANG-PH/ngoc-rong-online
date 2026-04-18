package com.dang.dragonboy.xu_ly_map;
import java.util.*;

public class MapDataCache {
    private static final MapDataCache INSTANCE = new MapDataCache();
    private final Map<Integer, List<NpcServerData>> cache = new HashMap<>();

    public static MapDataCache getInstance() { return INSTANCE; }
    public boolean daCo(int mapId) { return cache.containsKey(mapId); }
    public List<NpcServerData> lay(int mapId) { return cache.get(mapId); }
    public void luu(int mapId, List<NpcServerData> data) { cache.put(mapId, data); }
}
