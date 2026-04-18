package com.dang.dragonboy.xu_ly_map;

public class MapIdHelper {
    public static int layMapId(String tenMap) {
        switch (tenMap) {
            case "Làng Aru":    return 1;
            case "Nhà Gôhan":   return 2;
            case "Đồi Hoa Cúc": return 3;
            default: return 0;
        }
    }
}
