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
        ShopItemServerData data = tim(item);
        return data != null ? data.gia : 0L;
    }

    public static LoaiTien layLoaiTien(Item item) {
        ShopItemServerData data = tim(item);
        if (data == null) return LoaiTien.VANG;
        return "NGOC".equals(data.loaiTien) ? LoaiTien.NGOC : LoaiTien.VANG;
    }

    private static ShopItemServerData tim(Item item) {
        ShopCache cache = ShopCache.getInstance();
        if (!cache.daCo(NPC_BASE_ID)) return null;

        // Case 1: item từ shop UI → match chính xác theo tmpId (id server)
        // Có 2 th sẽ vào đây
        // 1 là item của shop npc ( vì id item npc ở game-data-service sẽ đc gán tạm vào đây )
        // 2 là item mà user mua nhưng chưa có uuid trigger về sau khi call event ws
        // Chỉ đc match th1 nên item.uuid phải == null
        // Nếu để th2 lọt vào thì sẽ bị loạn giá vì tmpId của th2 k tồn tại trong ShopItemServerData
        if (item.tmpId != -1 && item.uuid == null) {
            for (ShopItemServerData s : cache.lay(NPC_BASE_ID)) {
                if (s.id == item.tmpId) return s;
            }
        }

        // Case 2: item từ inventory (bán) → ưu tiên dòng vĩnh viễn
        ShopItemServerData vinhVien = null;
        ShopItemServerData flashSale = null;
        for (ShopItemServerData s : cache.lay(NPC_BASE_ID)) {
            if (!s.ten_item.equals(item.getTenItem())) continue;
            if (s.start_at == null && s.end_at == null) {
                vinhVien = s;
            } else {
                flashSale = s;
            }
        }
        return vinhVien != null ? vinhVien : flashSale;
    }
}
