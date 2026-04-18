package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle;

import com.badlogic.gdx.graphics.Texture;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.network.DTO.ShopItemServerData;
import com.dang.dragonboy.xu_ly_map.npc.ShopCache;

import java.util.HashMap;
import java.util.Map;

public class ItemGia {
    private static final int NPC_BASE_ID = 3;

    public static long layGiaItem(Item item) {
        ShopItemServerData data = timTrongCache(item.getTenItem());
        return data != null ? data.gia : 0L;
    }

    public static LoaiTien layLoaiTien(Item item) {
        ShopItemServerData data = timTrongCache(item.getTenItem());
        if (data == null) return LoaiTien.VANG;
        return data.loaiTien.equals("NGOC") ? LoaiTien.NGOC : LoaiTien.VANG;
    }

    private static ShopItemServerData timTrongCache(String tenItem) {
        ShopCache cache = ShopCache.getInstance();
        if (!cache.daCo(NPC_BASE_ID)) return null;
        for (ShopItemServerData s : cache.lay(NPC_BASE_ID)) {
            if (s.tenItem.equals(tenItem)) return s;
        }
        return null;
    }
}
