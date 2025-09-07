package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle;

import com.dang.dragonboy.item.Item;
import java.util.HashMap;
import java.util.Map;

public class ItemGia {
    private static final Map<String, Map<LoaiTien, Long>> bangGia = new HashMap<>();

    static {
        themGia("Áo võ kame",LoaiTien.VANG, 500_000L);
        themGia("Quần võ kame",LoaiTien.VANG, 450_000L);
        themGia("Giày võ kame", LoaiTien.VANG,350_000L);
        themGia("Rada cấp 1", LoaiTien.VANG,10_000L);
        themGia("Găng thần linh",LoaiTien.VANG, 1_000_000_000L);
    }

    // Hàm tiện ích để thêm giá
    private static void themGia(String tenItem, LoaiTien loaiTien, long giaTien) {
        Map<LoaiTien, Long> gia = bangGia.get(tenItem);
        if (gia == null) {
            gia = new HashMap<>();
            bangGia.put(tenItem, gia);
        }
        gia.put(loaiTien, giaTien);
    }

    // Lấy giá theo loại tiền
    public static long layGiaItem(Item item) {
        Map<LoaiTien, Long> gia = bangGia.get(item.getTenItem());
        if (gia == null) return 0L;

        LoaiTien loai = layLoaiTien(item);
        return gia.getOrDefault(loai, 0L);
    }

    public static LoaiTien layLoaiTien(Item item) {
        Map<LoaiTien, Long> gia = bangGia.get(item.getTenItem());
        for (LoaiTien loai : gia.keySet()) {
            return loai; // vì chỉ có 1 phần tử
        }
        return null; // nếu không có;
    }
}
