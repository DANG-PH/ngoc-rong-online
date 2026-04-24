package com.dang.dragonboy.item;

import com.dang.dragonboy.du_lieu.State_Management;

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

    public static Item[] danhSachItemHuyHieu = new Item[]{
        new Item(
            "thien_tu", "Huy hiệu Thiên Tử", LoaiItem.HUYHIEU,
            "vatpham/vatphamgame/huy_hieu/thien_tu/icon.png",
            "Thiên mệnh tại thân - Thống ngự càn khôn", 1,
            new int[]{0,0,0,0,0,0,10,10,10,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, -1),
        new Item(
            "trum_cuoi", "Huy hiệu Trùm cuối", LoaiItem.HUYHIEU,
            "vatpham/vatphamgame/huy_hieu/trum_cuoi/icon.png",
            "Vinh quang vô địch - Đỉnh cao sức mạnh", 1,
            new int[]{0,0,0,5,0,5,0,0,10,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, -1),
        new Item(
            "traidat"+"_toi_thuong", "Huy hiệu Tối Thượng", LoaiItem.HUYHIEU,
            "vatpham/vatphamgame/huy_hieu/"+"traidat"+"_toi_thuong/icon.png",
            "Biểu tượng của người thống trị - Sức mạnh tối thượng không ai sánh kịp", 1,
            new int[]{0,0,0,10,0,10,0,0,0,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, -1),
        new Item(
            "xayda"+"_toi_thuong", "Huy hiệu Tối Thượng", LoaiItem.HUYHIEU,
            "vatpham/vatphamgame/huy_hieu/"+"xayda"+"_toi_thuong/icon.png",
            "Biểu tượng của người thống trị - Sức mạnh tối thượng không ai sánh kịp", 1,
            new int[]{0,0,0,0,0,0,20,0,0,0,0,0,10},
            "all", 10_000_000L, null, 0, 0, 0, -1),
    };

    public static Item[] danhSachItemAura = new Item[]{
        new Item(
            "tan_hon_rong_namek", "Aura Long Hồn Thượng Giới", LoaiItem.AURA,
            "vatpham/vatphamgame/aura/tan_hon_rong_namek/icon.png",
            "Di vật tối thượng lưu lạc từ cõi Thượng Giới, kết tinh linh hồn bất diệt của Rồng Thần. [Hiệu ứng] KI > 70%: +10% Sức đánh hoặc +10% HP tùy trang bị. KI < 20%: +10% Chí mạng hoặc +10% Giảm sát thương tùy trang bị. Nếu không có trang bị phù hợp các hiệu ứng được chia đều", 1,
            new int[]{0,0,0,8,0,8,8,0,0,0,0,0,8},
            "all", 10_000_000L, null, 0, 0, 0, -1),

        new Item(
            "tieu_doi_truong", "Aura Tiểu Đội Trưởng", LoaiItem.AURA,
            "vatpham/vatphamgame/aura/tieu_doi_truong/icon.png",
            "Hào quang thủ lĩnh. [Hiệu ứng] Đứng yên: +10% HP tối đa. Di chuyển hoặc tấn công: +10% Sức đánh.", 1,
            new int[]{0,0,0,0,0,0,10,10,10,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, -1)
    };
}
