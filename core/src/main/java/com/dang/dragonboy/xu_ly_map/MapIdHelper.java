package com.dang.dragonboy.xu_ly_map;

public class MapIdHelper {
    public static int layMapId(String tenMap) {
        switch (tenMap) {
            case "Nhà Gôhan":   return 1;
            case "Làng Aru":    return 2;
            case "Đồi Hoa Cúc": return 3;
            default: return 0;
        }
    }
}
