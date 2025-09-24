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
        LOITHOAI_TRONG_DATA.put("admin_dungle",new String[] {"Nhận quà để tiếp tục hành trình phía trước"});
        LOITHOAI_TRONG_DATA.put("admin_thanhle",new String[] {"Cậu cần trang bị gì cứ đến chỗ tôi nhé"} );
        LOITHOAI_TRONG_DATA.put("thay_hieu", new String[] {"Em tìm thầy có việc gì?","Thầy có thể giúp gì cho em?","Thầy sẽ biến trang bị cấp cao hơn của em\nthành trang bị có cấp độ và sao pha lê của trang bị cũ"});
        LOITHOAI_TRONG_DATA.put("vua_vegeta", new String[] {"1"});
    }

    static {
        // Đặt thoại cho từng NPC
        LOITHOAI_NGOAI_DATA.put("ong_gohan", new String[] {"null"});
        LOITHOAI_NGOAI_DATA.put("admin_haidang", new String[] {"Thiên mệnh tại thân...","Đạo hữu xin dừng bước...","PTIT x UDU"});
        LOITHOAI_NGOAI_DATA.put("admin_dungle",new String[] {"Bạn muốn nhận quà?","Có điều gì đó chờ đợi bạn ở đây...","Chúc một ngày tốt lành..."});
        LOITHOAI_NGOAI_DATA.put("admin_thanhle",new String[] {"Thứ ngươi thiếu, chính là sức mạnh nơi đây...", "Trang bị tốt, hành trình bền...", "Vật trong tay, mệnh trong người...",} );
        LOITHOAI_NGOAI_DATA.put("thay_hieu", new String[] {"Học OOP chưa? Hay lại chơi game tiếp?","Đừng để class thành God Class nhé","Học viện công nghệ bưu chính viễn thông","Kế thừa giúp tái sử dụng" ,"Đa hình giúp mở rộng","Trừu tượng giúp đơn giản hóa vấn đề","Đóng gói giúp bảo vệ dữ liệu"});
        LOITHOAI_NGOAI_DATA.put("admin_huykhoi", new String[] {"Sách quý, kỹ năng hiếm, ngươi dám thử không?","Ngươi muốn biết chiêu hay tuyệt diệu nào?"});
        LOITHOAI_NGOAI_DATA.put("vua_vegeta", new String[] {"null"});
    }

    static {
        // Đặt chuc nang cho từng NPC
        CHUCNANG_DATA.put("ong_gohan", new String[] {"1"});
        CHUCNANG_DATA.put("admin_haidang", new String[] {"Vòng quay\nmay mắn","Quy đổi vé","Hướng dẫn chơi"});
        CHUCNANG_DATA.put("admin_dungle",new String[] {"Nhập Giftcode","Nhận quà","Nhận quà\nNạp từ web","Đóng"});
        CHUCNANG_DATA.put("admin_thanhle",new String[] {"Cửa hàng","Đóng"} );
        CHUCNANG_DATA.put("thay_hieu",new String[] {"Chức năng\nPha lê","Chuyển hóa\nTrang bị"} );
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
