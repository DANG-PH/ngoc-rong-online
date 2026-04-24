package com.dang.dragonboy.item;

public class ItemData {
    public static Item[] danhSachItemDeoLung = new Item[]{
        new Item("luoi_hai", "Lưỡi Hái Thời Không", LoaiItem.DEOLUNG,
            "vatpham/vatphamgame/deo_lung/luoi_hai/icon.png",
            "Lưỡi hái mang sức mạnh xé tan thời không. [Hiệu ứng] Khi trang bị cùng Black Goku Rose: +4% Sức đánh, HP, KI.", 1,
            new int[]{0,0,0,0,0,0,1,1,1,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, -1),

        new Item("canh_doi", "Cánh Dơi Dracula", LoaiItem.DEOLUNG,
            "vatpham/vatphamgame/deo_lung/canh_doi/icon.png",
            "Đôi cánh dơi huyền bí. [Hiệu ứng] Khi HP ≤ 50%: +10% Chí mạng.", 1,
            new int[]{0,0,0,1,0,0,0,0,1,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, -1),

        new Item("canh_ac_quy", "Cánh Ác Quỷ", LoaiItem.DEOLUNG,
            "vatpham/vatphamgame/deo_lung/canh_ac_quy/icon.png",
            "Cánh ác quỷ tỏa ra khí tức u tối. [Hiệu ứng] KI > 80%: +5% Chí mạng. Nếu trang bị cùng Huy Hiệu Trùm Cuối: +5% Chí mạng, +10% Sát thương chí mạng.", 1,
            new int[]{0,0,0,1,0,1,0,0,0,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, -1),

        new Item("kiem", "Kiếm Thánh Z", LoaiItem.DEOLUNG,
            "vatpham/vatphamgame/deo_lung/kiem/icon.png",
            "Thanh kiếm huyền thoại của Future Trunks. [Hiệu ứng] Khi HP ≤ 40%: +15% Sức đánh.", 1,
            new int[]{0,0,0,0,0,1,0,0,1,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, -1),

        new Item("hoa", "Bó Hoa Hồng", LoaiItem.DEOLUNG,
            "vatpham/vatphamgame/deo_lung/hoa/icon.png",
            "Bó hoa chứa nguồn sức mạnh huyền ảo. [Hiệu ứng] Mỗi 3s có 10% cơ hội hồi 2% HP tối đa (x2 tỷ lệ nếu trang bị Huy Hiệu Thiên Tử).", 1,
            new int[]{0,0,0,0,0,0,1,0,1,0,0,0,1},
            "all", 10_000_000L, null, 0, 0, 0, -1),

        new Item("canh_thien_su", "Cánh Thiên Sứ", LoaiItem.DEOLUNG,
            "vatpham/vatphamgame/deo_lung/canh_thien_su/icon.png",
            "Đôi cánh của thiên sứ, thuần khiết và sáng ngời. [Hiệu ứng] Khi bay ngang: giảm sát thương nhận vào 5%. Nếu cưỡi Phượng Hoàng Lửa: giảm thêm 10% sát thương.", 1,
            new int[]{0,0,0,0,0,0,1,0,0,0,0,0,1},
            "all", 10_000_000L, null, 0, 0, 0, -1),

        new Item("canh_thien_than", "Cánh Thiên Thần", LoaiItem.DEOLUNG,
            "vatpham/vatphamgame/deo_lung/canh_thien_than/icon.png",
            "Đôi cánh tỏa sáng rực rỡ của thiên thần. [Hiệu ứng] Khi HP đầy: +10% Sức đánh. Khi KI ≤ 20%: +10% Sát thương chí mạng.", 1,
            new int[]{0,0,0,1,0,0,0,1,1,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, -1)
    };
}
