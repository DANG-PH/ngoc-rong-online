package com.dang.dragonboy.xu_ly_map.npc;
import java.util.HashMap;
import java.util.Map;

public class LoiThoai_ChucNang_Npc {
    private final static Map<String, String[]> LOITHOAI_TRONG_DATA = new HashMap<>();
    private final static Map<String, String[]> LOITHOAI_NGOAI_DATA = new HashMap<>();
    private final static Map<String, String[]> CHUCNANG_DATA = new HashMap<>();

    static {
        // Đặt thoại cho từng NPC
        LOITHOAI_TRONG_DATA.put("ong_gohan", new String[] {"1"});
        LOITHOAI_TRONG_DATA.put("admin_haidang", new String[] {"Ta ở đây để ban cho ngươi cơ duyên lớn"});
        LOITHOAI_TRONG_DATA.put("admin_dungle",new String[] {"1"});
        LOITHOAI_TRONG_DATA.put("admin_thanhle",new String[] {"1"} );
        LOITHOAI_TRONG_DATA.put("vua_vegeta", new String[] {"1"});
    }

    static {
        // Đặt thoại cho từng NPC
        LOITHOAI_NGOAI_DATA.put("ong_gohan", new String[] {"1"});
        LOITHOAI_NGOAI_DATA.put("admin_haidang", new String[] {"Đạo hữu xin dừng bước!"});
        LOITHOAI_NGOAI_DATA.put("admin_dungle",new String[] {"1"});
        LOITHOAI_NGOAI_DATA.put("admin_thanhle",new String[] {"1"} );
        LOITHOAI_NGOAI_DATA.put("vua_vegeta", new String[] {"1"});
    }

    static {
        // Đặt chuc nang cho từng NPC
        CHUCNANG_DATA.put("ong_gohan", new String[] {"1"});
        CHUCNANG_DATA.put("admin_haidang", new String[] {"Vòng quay\nmay mắn","Quy đổi vé","Hướng dẫn chơi"});
        CHUCNANG_DATA.put("admin_dungle",new String[] {"1"});
        CHUCNANG_DATA.put("admin_thanhle",new String[] {"1"} );
        CHUCNANG_DATA.put("vua_vegeta", new String[] {"1"});
    }

    public static String[] getLoiThoaiTrong(String npcName) {
        return LOITHOAI_TRONG_DATA.getOrDefault(npcName, new String[] {"null"});
    }

    public static String[] getLoiThoaiNgoai(String npcName) {
        return LOITHOAI_NGOAI_DATA.getOrDefault(npcName, new String[] {"null"});
    }
    public static String[] getChucNang(String npcName) {
        return CHUCNANG_DATA.getOrDefault(npcName, new String[] {"null"});
    }
}
