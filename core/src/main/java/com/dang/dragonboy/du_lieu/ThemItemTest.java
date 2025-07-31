package com.dang.dragonboy.du_lieu;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.badlogic.gdx.graphics.Texture;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;

public class ThemItemTest {

    public static void themItemTest(DuLieuNguoiChoi duLieu, NhanVat nhanVat) {

        duLieu.themItemVaoHanhTrang(new Item(
            "bongtaic1", "Bông tai Porata", LoaiItem.BONGTAI,
            new Texture("vatpham/vatphamgame/bongtai/bongtaic1.png"),
            "Sử dụng để hợp thể với đệ tử", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 1500000L, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "bongtaic2", "Bông tai Porata Cấp 2", LoaiItem.BONGTAI,
            new Texture("vatpham/vatphamgame/bongtai/bongtaic2.png"),
            "Sử dụng để hợp thể với đệ tử và tăng tổng 10% chỉ số", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 150000000L, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "bongtaic3", "Bông tai Porata Cấp 3", LoaiItem.BONGTAI,
            new Texture("vatpham/vatphamgame/bongtai/bongtaic3.png"),
            "Sử dụng để hợp thể với đệ tử và tăng tổng 20% chỉ số", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 1500000000L, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "phuong_hoang_lua", "Thú cưỡi cực VIP", LoaiItem.VANBAY,
            new Texture("vatpham/vanbay/phuong_hoang_lua/phuonghoanglua.png"),
            "Dùng để bay và hồi phục HP, KI", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 1500000L, null, 0, 0, 0, -1
        ));
        duLieu.themItemVaoHanhTrang(new Item(
            "glt_c3", "Giáp luyện tập cấp 3", LoaiItem.GIAPLUYENTAP,
            new Texture("vatpham/vatphamgame/giapluyentap/gltc3.png"),
            "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra sẽ tăng sức đánh 30% và Crit 15%, ST Crit 30%", 1,
            new int[]{0,0,0,0,0,0,35,0,0,0,0,0,0},
            "all", 1_000_000_000L, null, 7, 7, 0, 0
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "goku_black_rose", "Cải trang Super Black Goku", LoaiItem.CAITRANG,
            new Texture("nhanvat/caitrang/goku_black_rose/daudung.png"),
            "Cải trang thành Super Black Goku", 1,
            new int[]{0,0,0,0,0,0,45,45,45,0,0,0,0},
            "all", 50_000_000_000L, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "goku_black", "Cải trang Black Goku", LoaiItem.CAITRANG,
            new Texture("nhanvat/caitrang/goku_black/daudung.png"),
            "Cải trang thành Super Black Goku", 1,
            new int[]{0,0,0,0,0,0,35,35,35,0,0,0,0},
            "all", 50_000_000_000L, null, 0, 0, 0, -1
        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "vegito_xeno", "Cải trang Vegito Xeno", LoaiItem.CAITRANG,
//            new Texture("nhanvat/caitrang/vegito_xeno/dauchay.png"),
//            "Cải trang thành Vegito Xeno SSJ3", 1,
//            new int[]{0,0,0,20,1010,0,70,70,70,0,0,0,0},
//            nhanVat.getHanhtinh(), 1500000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "gohan_beast", "Cải trang Gohan Beast", LoaiItem.CAITRANG,
//            new Texture("nhanvat/caitrang/gohan_beast/daudung.png"),
//            "Cải trang thành Gohan Beast", 1,
//            new int[]{0,0,0,50,0,100,100,0,100,0,0,0,0},
//            nhanVat.getHanhtinh(), 10_000_000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "xiao", "Cải trang Hộ Pháp Dạ Xoa", LoaiItem.CAITRANG,
//            new Texture("nhanvat/caitrang/xiao/icon.png"),
//            "Cải trang thành Xiao - Genshin impact", 1,
//            new int[]{0,0,0,50,0,100,100,0,100,0,0,0,0},
//            nhanVat.getHanhtinh(), 9_999_999_999L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "ayaka", "Cải trang tiểu thư Kamisato", LoaiItem.CAITRANG,
//            new Texture("nhanvat/caitrang/ayaka/icon.png"),
//            "Cải trang thành Ayaka - Genshin impact", 1,
//            new int[]{0,0,0,0,0,0,40,50,60,0,0,0,0},
//            nhanVat.getHanhtinh(), 9_999_999_999L, null, 0, 0, 0, -1
//        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "avt_vip", "Avatar VIP", LoaiItem.AVATAR,
            new Texture("nhanvat/" + nhanVat.getHanhtinh() + "/avatar/avt_vip/daudung.png"),
            "Dùng để thay đổi khuôn mặt", 1,
            new int[]{0,0,0,0,10,0,15,0,15,0,0,0,0},
            nhanVat.getHanhtinh(), 1500000L, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_cam", "Áo võ kame", LoaiItem.AO,
            new Texture("vatpham/do/traidat/set_cam/ao.png"),
            "Giúp giảm sát thương", 1,
            new int[]{0,0,0,0,10,0,0,0,21,0,0,0,0},
            "traidat", 150000L, "Sôngôku", 7, 7, 3, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_cam", "Quần võ kame", LoaiItem.QUAN,
            new Texture("vatpham/do/traidat/set_cam/quan.png"),
            "Giúp tăng HP", 1,
            new int[]{0,0,0,0,0,0,0,0,21,5000,0,0,0},
            "traidat", 150000L, "Sôngôku", 7, 7, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_cam", "Găng thần linh", LoaiItem.GANG,
            new Texture("vatpham/do/traidat/set_than_linh/gang.png"),
            "Giúp tăng sức đánh", 1,
            new int[]{0,0,0,0,0,0,0,0,21,0,0,9289,0},
            "traidat", 20_000_000_000L, "Sôngôku", 7, 7, 7, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_cam", "Giày võ kame", LoaiItem.GIAY,
            new Texture("vatpham/do/traidat/set_cam/giay.png"),
            "Giúp tăng MP", 1,
            new int[]{0,0,0,0,0,0,0,0,21,0,200,0,0},
            "traidat", 150000L, "Sôngôku", 7, 7, 2, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "rada1", "Rada cấp 1", LoaiItem.RADA,
            new Texture("vatpham/do/rada/rada1.png"),
            "Giúp tăng Chí Mạng", 1,
            new int[]{0,0,0,1,0,0,0,0,21,0,0,0,0},
            "traidat", 15000L, "Sôngôku", 7, 7, 1, -1
        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Nón Lý Trí", LoaiItem.AO,
//            new Texture("vatpham/do/traidat/set_ayaka/ao.png"),
//            "Giúp giảm sát thương", 1,
//            new int[]{0,0,0,0,289,0,0,0,21,0,0,0,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Ly Không Gian", LoaiItem.QUAN,
//            new Texture("vatpham/do/traidat/set_ayaka/quan.png"),
//            "Giúp tăng HP", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,5000,0,0,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Cát Thời Gian", LoaiItem.GANG,
//            new Texture("vatpham/do/traidat/set_ayaka/gang.png"),
//            "Giúp tăng sức đánh", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,0,0,8289,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Lông Vũ Tử Vong", LoaiItem.GIAY,
//            new Texture("vatpham/do/traidat/set_ayaka/giay.png"),
//            "Giúp tăng MP", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,0,10000,0,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Hoa Sự Sống", LoaiItem.RADA,
//            new Texture("vatpham/do/traidat/set_ayaka/rada.png"),
//            "Giúp tăng Chí Mạng", 1,
//            new int[]{0,0,0,10,0,0,0,0,21,0,0,0,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));


//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Áo vải thô", LoaiItem.AO,
//            new Texture("vatpham/do/xayda/set_vai_tho/ao.png"),
//            "Giúp giảm sát thương", 1,
//            new int[]{0,0,0,0,3,0,35,0,0,0,0,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_than_linh", "Quần thần linh", LoaiItem.QUAN,
//            new Texture("vatpham/do/xayda/set_than_linh/quan.png"),
//            "Giúp tăng HP", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,110000,0,0,0},
//            "xayda", 20_000_000_000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Găng vải thô", LoaiItem.GANG,
//            new Texture("vatpham/do/xayda/set_vai_tho/gang.png"),
//            "Giúp tăng sức đánh", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,0,0,8,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Giày vải thô", LoaiItem.GIAY,
//            new Texture("vatpham/do/xayda/set_vai_tho/giay.png"),
//            "Giúp tăng MP", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,0,10,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_than_linh", "Nhẫn thần linh", LoaiItem.RADA,
//            new Texture("vatpham/do/xayda/set_than_linh/rada.png"),
//            "Giúp tăng Chí Mạng", 1,
//            new int[]{0,0,0,18,0,0,35,0,0,0,0,0,0},
//            "xayda", 40_000_000_000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Áo vải thô", LoaiItem.AO,
//            new Texture("vatpham/do/xayda/set_vai_tho/ao.png"),
//            "Giúp giảm sát thương", 1,
//            new int[]{0,0,0,0,8,0,35,0,0,0,0,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_than_linh", "Quần thần linh", LoaiItem.QUAN,
//            new Texture("vatpham/do/xayda/set_than_linh/quan.png"),
//            "Giúp tăng HP", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,123000,0,0,0},
//            "xayda", 20_000_000_000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Găng vải thô", LoaiItem.GANG,
//            new Texture("vatpham/do/xayda/set_vai_tho/gang.png"),
//            "Giúp tăng sức đánh", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,0,0,5,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Giày vải thô", LoaiItem.GIAY,
//            new Texture("vatpham/do/xayda/set_vai_tho/giay.png"),
//            "Giúp tăng MP", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,0,12,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "rada1", "Rada cấp 1", LoaiItem.RADA,
//            new Texture("vatpham/do/rada/rada1.png"),
//            "Giúp tăng Chí Mạng", 1,
//            new int[]{0,0,0,1,0,0,35,0,0,0,0,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr1s", "Ngọc rồng 1 sao", LoaiItem.NGOCRONG,
            new Texture("vatpham/vatphamgame/ngocrong/nr1s.png"),
            "Thu thập để ước rồng thần", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr1s", "Ngọc rồng 1 sao", LoaiItem.NGOCRONG,
            new Texture("vatpham/vatphamgame/ngocrong/nr1s.png"),
            "Thu thập để ước rồng thần", 98,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr2s", "Ngọc rồng 2 sao", LoaiItem.NGOCRONG,
            new Texture("vatpham/vatphamgame/ngocrong/nr2s.png"),
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr3s", "Ngọc rồng 3 sao", LoaiItem.NGOCRONG,
            new Texture("vatpham/vatphamgame/ngocrong/nr3s.png"),
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr4s", "Ngọc rồng 4 sao", LoaiItem.NGOCRONG,
            new Texture("vatpham/vatphamgame/ngocrong/nr4s.png"),
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr5s", "Ngọc rồng 5 sao", LoaiItem.NGOCRONG,
            new Texture("vatpham/vatphamgame/ngocrong/nr5s.png"),
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr6s", "Ngọc rồng 6 sao", LoaiItem.NGOCRONG,
            new Texture("vatpham/vatphamgame/ngocrong/nr6s.png"),
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr7s", "Ngọc rồng 7 sao", LoaiItem.NGOCRONG,
            new Texture("vatpham/vatphamgame/ngocrong/nr7s.png"),
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_huy_diet", "Áo hủy diệt", LoaiItem.AO,
            new Texture("vatpham/do/traidat/set_huy_diet/ao.png"),
            "Giúp giảm sát thương", 1,
            new int[]{0,0,0,0,2718,0,0,0,21,0,0,0,0},
            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_huy_diet", "Quần hủy diệt", LoaiItem.QUAN,
            new Texture("vatpham/do/traidat/set_huy_diet/quan.png"),
            "Giúp tăng HP", 1,
            new int[]{0,0,0,0,0,0,0,0,21,195000,0,0,0},
            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_huy_diet", "Găng hủy diệt", LoaiItem.GANG,
            new Texture("vatpham/do/traidat/set_huy_diet/gang.png"),
            "Giúp tăng sức đánh", 1,
            new int[]{0,0,0,0,0,0,0,0,21,0,0,19500,0},
            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_huy_diet", "Giày hủy diệt", LoaiItem.GIAY,
            new Texture("vatpham/do/traidat/set_huy_diet/giay.png"),
            "Giúp tăng MP", 1,
            new int[]{0,0,0,0,0,0,0,0,21,0,182000,0,0},
            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_huy_diet", "Nhẫn hủy diệt", LoaiItem.RADA,
            new Texture("vatpham/do/traidat/set_huy_diet/rada.png"),
            "Giúp tăng Chí Mạng", 1,
            new int[]{0,0,0,20,0,0,0,0,21,0,0,0,0},
            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "glt_c1", "Giáp luyện tập cấp 1", LoaiItem.GIAPLUYENTAP,
            new Texture("vatpham/vatphamgame/giapluyentap/gltc1.png"),
            "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra nếu sẽ tăng sức đánh 10% và Crit 15%, ST Crit 30%", 1,
            new int[]{0,0,0,0,0,0,25,0,0,0,0,0,0},
            "traidat", 10_000_000L, null, 7, 5, 0, 0
        ));
    }
}
