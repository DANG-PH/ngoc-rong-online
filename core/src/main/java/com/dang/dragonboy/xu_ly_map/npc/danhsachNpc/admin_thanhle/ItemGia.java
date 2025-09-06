package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;

import java.util.HashMap;
import java.util.Map;

public class ItemGia {
    private static final Map<String, Long> bangGia = new HashMap<>();

    static {
        bangGia.put("Áo võ kame", 500_000L);
        bangGia.put("Quần võ kame", 450_000L);
        bangGia.put("Giày võ kame", 350_000L);
        bangGia.put("Rada cấp 1", 10_000L);
        bangGia.put("Găng thần linh", 1_000_000_000L);
    }

    public static long layGiaItem(Item item) {
        return bangGia.getOrDefault(item.getTenItem(), 0L);
    }
}
