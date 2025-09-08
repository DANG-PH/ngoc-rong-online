package com.dang.dragonboy.item;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;

public class ItemThuongXuLi {
    // Mô tả theo loại
    private static final Map<LoaiItem, String> MOTA_ITEM = Map.of(
        LoaiItem.AO, "Giúp giảm sát thương",
        LoaiItem.QUAN, "Giúp tăng HP",
        LoaiItem.GANG, "Giúp tăng sức đánh",
        LoaiItem.GIAY, "Giúp tăng MP",
        LoaiItem.RADA, "Giúp tăng Chí Mạng"
    );

    // Dữ liệu chỉ số theo hành tinh + id + loại
    private static final Map<String, Map<LoaiItem, int[]>> CHISO_ITEM = new HashMap<>();
    // Dữ liệu sức mạnh yêu cầu theo hành tinh + id + loại
    private static final Map<String, Map<LoaiItem, Long>> SMYC_ITEM = new HashMap<>();

    static {
        // Ví dụ set_cam traidat
        CHISO_ITEM.put("traidat:set_cam", Map.of(
            LoaiItem.AO, new int[]{0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0},
            LoaiItem.QUAN, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 5000, 0, 0, 0},
            LoaiItem.GANG, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0},
            LoaiItem.GIAY, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 200, 0, 0}
        ));

        SMYC_ITEM.put("traidat:set_cam", Map.of(
            LoaiItem.AO, 150_000L,
            LoaiItem.QUAN, 200_000L,
            LoaiItem.GANG, 180_000L,
            LoaiItem.GIAY, 175_000L
        ));
    }

    public static Item taoItemThuong(String id, LoaiItem loaiItem, String hanhTinh) {
        String subStringItem = switch (loaiItem) {
            case AO -> "ao";
            case QUAN -> "quan";
            case GANG -> "gang";
            case GIAY -> "giay";
            default -> "";
        };

        if (!subStringItem.isEmpty()) {
            String key = hanhTinh + ":" + id;
            return new Item(
                id,
                layTenItem(hanhTinh, id, loaiItem),
                loaiItem,
                new Texture("vatpham/do/" + hanhTinh + "/" + id + "/" + subStringItem + ".png"),
                MOTA_ITEM.get(loaiItem),
                1,
                CHISO_ITEM.getOrDefault(key, Map.of()).get(loaiItem),
                hanhTinh,
                SMYC_ITEM.getOrDefault(key, Map.of()).getOrDefault(loaiItem, -1L),
                null, 0, 0, 0, -1
            );
        } else {
            int chiSo = Integer.parseInt(id.substring(4, 5));
            return new Item(
                id,
                "Rada cấp " + chiSo,
                LoaiItem.RADA,
                new Texture("vatpham/do/rada/" + id + ".png"),
                MOTA_ITEM.get(LoaiItem.RADA),
                1,
                new int[]{0, 0, 0, chiSo, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                "all",
                laySucManhYeuCauItemRada(chiSo),
                null, 0, 0, 0, -1
            );
        }
    }

    private static long laySucManhYeuCauItemRada(int chiSo) {
        return switch (chiSo) {
            case 1 -> 1_300L;
            case 2 -> 13_000L;
            case 3 -> 28_000L;
            case 4 -> 86_000L;
            case 5 -> 260_000L;
            case 6 -> 780_000L;
            case 7 -> 2_300_000L;
            case 8 -> 7_000_000L;
            case 9 -> 42_000_000L;
            case 10 -> 200_000_000L;
            case 11 -> 1_000_000_000L;
            case 12 -> 1_500_000_000L;
            default -> -1;
        };
    }

    private static String layTenItem(String hanhTinh, String id, LoaiItem loaiItem) {
        if ("traidat".equals(hanhTinh) && "set_cam".equals(id)) {
            return switch (loaiItem) {
                case AO -> "Áo võ kame";
                case QUAN -> "Quần võ kame";
                case GANG -> "Găng võ kame";
                case GIAY -> "Giày võ kame";
                default -> null;
            };
        }
        return null;
    }
}
