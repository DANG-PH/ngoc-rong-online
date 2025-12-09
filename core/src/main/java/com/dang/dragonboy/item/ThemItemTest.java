package com.dang.dragonboy.item;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.network.ApiItemService;
import com.dang.dragonboy.network.DTO.ItemCanLuu;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_haidang.admin_haidang;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThemItemTest {
    private VeHUD veHUD;
    private NhanVat nhanVat;
    private DuLieuNguoiChoi duLieu;

    public ThemItemTest(DuLieuNguoiChoi duLieu, NhanVat nhanVat, VeHUD veHUD) {
        this.veHUD = veHUD;
        this.nhanVat = nhanVat;
        this.duLieu = duLieu;
    }
    public void themItemTest() {
        Gson gson = new Gson();
        List<ItemCanLuu> itemTuDatabase = ApiItemService.getItems();
        for (ItemCanLuu item : itemTuDatabase) {
            switch (item.viTri) {
                case "hanhtrang":
                    duLieu.themItemVaoHanhTrang(new Item(
                        item.maItem, item.ten, LoaiItem.valueOf(item.loai),
                        item.linkTexture,
                        item.moTa, item.soLuong,
                        gson.fromJson(item.chiso, int[].class),
                        item.hanhTinh, Long.parseLong(item.sucManhYeuCau), item.setKichHoat, item.soSaoPhaLe, item.soSaoPhaLeCuongHoa, item.soCap, item.hanSuDung
                    ));
                    break;
                case "ruongdo":
                    duLieu.themItemVaoHanhTrangRuongDo(new Item(
                        item.maItem, item.ten, LoaiItem.valueOf(item.loai),
                        item.linkTexture,
                        item.moTa, item.soLuong,
                        gson.fromJson(item.chiso, int[].class),
                        item.hanhTinh, Long.parseLong(item.sucManhYeuCau), item.setKichHoat, item.soSaoPhaLe, item.soSaoPhaLeCuongHoa, item.soCap, item.hanSuDung
                    ));
                    break;
                case "hanhtrangdangmac":
                    veHUD.xulyitem.macDoVuaLogin(
                        new Item(
                            item.maItem, item.ten, LoaiItem.valueOf(item.loai),
                            item.linkTexture,
                            item.moTa, item.soLuong,
                            gson.fromJson(item.chiso, int[].class),
                            item.hanhTinh, Long.parseLong(item.sucManhYeuCau), item.setKichHoat, item.soSaoPhaLe, item.soSaoPhaLeCuongHoa, item.soCap, item.hanSuDung
                        )
                    );
                    break;
                case "hanhtrangdetu":
                    veHUD.xulyitem.macDoVuaLoginDeTu(
                        new Item(
                            item.maItem, item.ten, LoaiItem.valueOf(item.loai),
                            item.linkTexture,
                            item.moTa, item.soLuong,
                            gson.fromJson(item.chiso, int[].class),
                            item.hanhTinh, Long.parseLong(item.sucManhYeuCau), item.setKichHoat, item.soSaoPhaLe, item.soSaoPhaLeCuongHoa, item.soCap, item.hanSuDung
                        )
                    );
                    break;
            }
        }


//        duLieu.themItemVaoHanhTrang(new Item(
//            "bongtaic1", "Bông tai Porata", LoaiItem.BONGTAI,
//            "vatpham/vatphamgame/bongtai/bongtaic1.png",
//            "Sử dụng để hợp thể với đệ tử", 1,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 1500000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "bongtaic1", "Bông tai Porata", LoaiItem.BONGTAI,
//            "vatpham/vatphamgame/bongtai/bongtaic1.png",
//            "Sử dụng để hợp thể với đệ tử", 1,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 1500000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "tan_hon_rong_namek", "Aura Long Hồn Thượng Giới", LoaiItem.AURA,
//            "vatpham/vatphamgame/aura/tan_hon_rong_namek/icon.png",
//            "Di vật tối thượng lưu lạc từ cõi Thượng Giới, kết tinh linh hồn bất diệt của Rồng Thần. [Hiệu ứng] KI > 70%: +10% Sức đánh hoặc +10% HP tùy trang bị. KI < 20%: +10% Chí mạng hoặc +10% Giảm sát thương tùy trang bị. Nếu không có trang bị phù hợp các hiệu ứng được chia đều", 1,
//            new int[]{0,0,0,8,0,8,8,0,0,0,0,0,8},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "tieu_doi_truong", "Aura Tiểu Đội Trưởng", LoaiItem.AURA,
//            "vatpham/vatphamgame/aura/tieu_doi_truong/icon.png",
//            "Hào quang thủ lĩnh. [Hiệu ứng] Đứng yên: +10% HP tối đa. Di chuyển hoặc tấn công: +10% Sức đánh.", 1,
//            new int[]{0,0,0,0,0,0,10,10,10,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "gay_nhu_y", "Gậy Như Ý", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/gay_nhu_y/icon.png",
//            "Gậy Như Ý của Son Goku.", 1,
//            new int[]{0,0,0,0,0,0,10,10,10,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "khi", "Nâng Cấp Hóa Khỉ Khổng lồ", LoaiItem.NANGSKILL,
//            "vatpham/vatphamgame/nang_skill/khi.png",
//            "Sử dụng để nâng cấp kỹ năng Biến Hình.", 8,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "huytsao", "Nâng Cấp Kỹ Năng Huýt Sáo", LoaiItem.NANGSKILL,
//            "vatpham/vatphamgame/nang_skill/huytsao.png",
//            "Sử dụng để nâng cấp kỹ năng huýt sáo.", 8,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 200_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "ttnl", "Nâng Cấp Tái Tạo Năng Lượng", LoaiItem.NANGSKILL,
//            "vatpham/vatphamgame/nang_skill/ttnl.png",
//            "Sử dụng để nâng cấp kỹ năng tái tạo năng lượng.", 8,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 1_500_000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "skill4_de", "Nâng Cấp Kỹ Năng 4 Đệ Tử", LoaiItem.NANGSKILL,
//            "vatpham/vatphamgame/nang_skill_de_tu/skill4.png",
//            "Sử dụng để nâng cấp kỹ năng 4 đệ tử.", 8,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 1500000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "skill3_de", "Nâng Cấp Kỹ Năng 3 Đệ Tử", LoaiItem.NANGSKILL,
//            "vatpham/vatphamgame/nang_skill_de_tu/skill3.png",
//            "Sử dụng để nâng cấp kỹ năng 3 đệ tử.", 8,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 1500000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "skill2_de", "Nâng Cấp Kỹ Năng 2 Đệ Tử", LoaiItem.NANGSKILL,
//            "vatpham/vatphamgame/nang_skill_de_tu/skill2.png",
//            "Sử dụng để nâng cấp kỹ năng 2 đệ tử.", 8,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 1500000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "skill1_de", "Nâng Cấp Kỹ Năng 1 Đệ Tử", LoaiItem.NANGSKILL,
//            "vatpham/vatphamgame/nang_skill_de_tu/skill1.png",
//            "Sử dụng để nâng cấp kỹ năng 1 đệ tử.", 8,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 1500000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "dao", "Lưỡi Hái Thời Không", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/dao/icon.png",
//            "Lưỡi hái mang sức mạnh xé tan thời không. [Hiệu ứng] Khi trang bị cùng Black Goku Rose: +4% Sức đánh, HP, KI.", 1,
//            new int[]{0,0,0,0,0,0,20,0,0,0,0,0,10},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "kiem_vip", "Lưỡi Hái Thời Không", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/kiem_vip/icon.png",
//            "Lưỡi hái mang sức mạnh xé tan thời không. [Hiệu ứng] Khi trang bị cùng Black Goku Rose: +4% Sức đánh, HP, KI.", 1,
//            new int[]{0,0,0,0,0,0,20,0,0,0,0,0,10},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "luoi_hai", "Lưỡi Hái Thời Không", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/luoi_hai/icon.png",
//            "Lưỡi hái mang sức mạnh xé tan thời không. [Hiệu ứng] Khi trang bị cùng Black Goku Rose: +4% Sức đánh, HP, KI.", 1,
//            new int[]{0,0,0,0,0,0,20,0,0,0,0,0,10},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "canh_doi", "Cánh Dơi Dracula", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/canh_doi/icon.png",
//            "Đôi cánh dơi huyền bí.", 1,
//            new int[]{0,0,0,0,0,0,5,15,5,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "canh_ac_quy", "Cánh Ác Quỷ", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/canh_ac_quy/icon.png",
//            "Cánh ác quỷ tỏa ra khí tức u tối.", 1,
//            new int[]{0,0,0,5,0,10,0,0,0,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "kiem", "Kiếm Thánh Z", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/kiem/icon.png",
//            "Thanh kiếm huyền thoại của Future Trunks.", 1,
//            new int[]{0,0,0,0,0,15,0,0,15,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "hoa", "Bó Hoa Hồng", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/hoa/icon.png",
//            "Bó hoa chứa nguồn sức mạnh huyền ảo.", 1,
//            new int[]{0,0,0,0,0,0,5,5,15,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "canh_thien_su", "Cánh Thiên Sứ", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/canh_thien_su/icon.png",
//            "Đôi cánh của thiên sứ, thuần khiết và sáng ngời.", 1,
//            new int[]{0,0,0,5,0,0,0,0,5,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "canh_thien_than", "Cánh Thiên Thần", LoaiItem.DEOLUNG,
//            "vatpham/vatphamgame/deo_lung/canh_thien_than/icon.png",
//            "Đôi cánh tỏa sáng rực rỡ của thiên thần.", 1,
//            new int[]{0,0,0,0,0,0,8,8,0,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "bo_huyet", "Bổ huyết", LoaiItem.PHUTRO,
//            "vatpham/vatphamgame/phu_tro/bo_huyet.png",
//            "Trong vòng tối đa 10 phút +100% HP", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "hp", "Sinh lực", LoaiItem.PHUTRO,
//            "vatpham/vatphamgame/phu_tro/hp.png"),
//            "Trong vòng tối đa 2 phút +10% HP", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "dame", "Cường công", LoaiItem.PHUTRO,
//            "vatpham/vatphamgame/phu_tro/dame.png"),
//            "Trong vòng tối đa 2 phút +10% Sức đánh gốc", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "ki", "Linh khí", LoaiItem.PHUTRO,
//            "vatpham/vatphamgame/phu_tro/ki.png"),
//            "Trong vòng tối đa 2 phút +10% KI", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "bo_khi", "Bổ khí", LoaiItem.PHUTRO,
//            "vatpham/vatphamgame/phu_tro/bo_khi.png"),
//            "Trong vòng tối đa 10 phút +100% KI", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "cuong_no", "Cuồng nộ", LoaiItem.PHUTRO,
//            "vatpham/vatphamgame/phu_tro/cuong_no.png",
//            "Trong vòng tối đa 10 phút +100% Sức đánh gốc", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "giap_xen", "Giáp xên bọ hung", LoaiItem.PHUTRO,
//            "vatpham/vatphamgame/phu_tro/giap_xen.png"),
//            "Trong vòng tối đa 10 phút giảm 50% sát thương", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "thien_tu", "Huy hiệu Thiên Tử", LoaiItem.HUYHIEU,
//            "vatpham/vatphamgame/huy_hieu/thien_tu/icon.png",
//            "Thiên mệnh tại thân - Thống ngự càn khôn", 1,
//            new int[]{0,0,0,0,0,0,10,10,10,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "trum_cuoi", "Huy hiệu Trùm cuối", LoaiItem.HUYHIEU,
//            "vatpham/vatphamgame/huy_hieu/trum_cuoi/icon.png",
//            "Vinh quang vô địch - Đỉnh cao sức mạnh", 1,
//            new int[]{0,0,0,5,0,5,0,0,10,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            nhanVat.getHanhtinh()+"_toi_thuong", "Huy hiệu Tối Thượng", LoaiItem.HUYHIEU,
//            "vatpham/vatphamgame/huy_hieu/"+nhanVat.getHanhtinh()+"_toi_thuong/icon.png",
//            "Biểu tượng của người thống trị - Sức mạnh tối thượng không ai sánh kịp", 1,
//            new int[]{0,0,0,10,0,10,0,0,0,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "xayda"+"_toi_thuong", "Huy hiệu Tối Thượng", LoaiItem.HUYHIEU,
//            "vatpham/vatphamgame/huy_hieu/"+"xayda"+"_toi_thuong/icon.png",
//            "Biểu tượng của người thống trị - Sức mạnh tối thượng không ai sánh kịp", 1,
//            new int[]{0,0,0,0,0,0,20,0,0,0,0,0,10},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "bongtaic2", "Bông tai Porata Cấp 2", LoaiItem.BONGTAI,
//            "vatpham/vatphamgame/bongtai/bongtaic2.png",
//            "Sử dụng để hợp thể với đệ tử và tăng tổng 10% chỉ số", 1,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 150000000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "bongtaic3", "Bông tai Porata Cấp 3", LoaiItem.BONGTAI,
//            "vatpham/vatphamgame/bongtai/bongtaic3.png",
//            "Sử dụng để hợp thể với đệ tử và tăng tổng 20% chỉ số", 1,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 1500000000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "phuong_hoang_lua", "Thú cưỡi cực VIP", LoaiItem.VANBAY,
//            "vatpham/vanbay/phuong_hoang_lua/phuonghoanglua.png",
//            "Dùng để bay và hồi phục HP, KI", 1,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 1500000L, null, 0, 0, 0, -1
//        ));
//        duLieu.themItemVaoHanhTrang(new Item(
//            "glt_c3", "Giáp luyện tập cấp 3", LoaiItem.GIAPLUYENTAP,
//            "vatpham/vatphamgame/giapluyentap/gltc3.png",
//            "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra sẽ tăng sức đánh 30% và Crit 15%, ST Crit 30%", 1,
//            new int[]{0,0,0,0,0,0,15,0,0,0,0,0,0},
//            "all", 1_000_000_000L, null, 3, 3, 0, 0
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "goku_black_rose", "Cải trang Super Black Goku", LoaiItem.CAITRANG,
//            "nhanvat/caitrang/goku_black_rose/daudung.png",
//            "Cải trang thành Super Black Goku", 1,
//            new int[]{0,0,0,0,0,0,45,45,45,0,0,0,0},
//            "all", 40_000_000_000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "goku_black_rose", "Cải trang Super Black Goku", LoaiItem.CAITRANG,
//            "nhanvat/caitrang/goku_black_rose/daudung.png",
//            "Cải trang thành Super Black Goku", 1,
//            new int[]{0,0,0,0,0,0,45,45,45,0,0,0,0},
//            "all", 40_000_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "goku_black", "Cải trang Black Goku", LoaiItem.CAITRANG,
//            "nhanvat/caitrang/goku_black/daudung.png",
//            "Cải trang thành Super Black Goku", 1,
//            new int[]{0,0,0,0,0,0,35,35,35,0,0,0,0},
//            "all", 50_000_000_000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "vegito_xeno", "Cải trang Vegito Xeno", LoaiItem.CAITRANG,
//            "nhanvat/caitrang/vegito_xeno/dauchay.png",
//            "Cải trang thành Vegito Xeno SSJ3", 1,
//            new int[]{0,0,0,20,1010,0,70,70,70,0,0,0,0},
//            nhanVat.getHanhtinh(), 1500000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "gohan_beast", "Cải trang Gohan Beast", LoaiItem.CAITRANG,
//            "nhanvat/caitrang/gohan_beast/daudung.png",
//            "Cải trang thành Gohan Beast", 1,
//            new int[]{0,0,0,50,0,100,100,0,100,0,0,0,0},
//            nhanVat.getHanhtinh(), 10_000_000L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "broly_lssj", "Cải trang Broly", LoaiItem.CAITRANG,
//            "nhanvat/caitrang/broly_lssj/daudung.png",
//            "Cải trang thành Broly", 1,
//            new int[]{0,0,0,50,0,100,100,0,100,0,0,0,0},
//            nhanVat.getHanhtinh(), 9_999_999_999L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "xiao", "Cải trang Hộ Pháp Dạ Xoa", LoaiItem.CAITRANG,
//            "nhanvat/caitrang/xiao/icon.png",
//            "Cải trang thành Xiao - Genshin impact", 1,
//            new int[]{0,0,0,50,0,100,100,0,100,0,0,0,0},
//            nhanVat.getHanhtinh(), 9_999_999_999L, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "ayaka", "Cải trang tiểu thư Kamisato", LoaiItem.CAITRANG,
//            "nhanvat/caitrang/ayaka/icon.png",
//            "Cải trang thành Ayaka - Genshin impact", 1,
//            new int[]{0,0,0,0,0,0,40,50,60,0,0,0,0},
//            nhanVat.getHanhtinh(), 9_999_999_999L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "avt_vip", "Avatar VIP", LoaiItem.AVATAR,
//            "nhanvat/" + nhanVat.getHanhtinh() + "/avatar/avt_vip/daudung.png",
//            "Dùng để thay đổi khuôn mặt", 1,
//            new int[]{0,0,0,0,10,0,15,0,15,0,0,0,0},
//            nhanVat.getHanhtinh(), 1500000L, null, 0, 0, 0, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_cam", "Áo võ kame", LoaiItem.AO,
//            "vatpham/do/traidat/set_cam/ao.png",
//            "Giúp giảm sát thương", 1,
//            new int[]{0,0,0,0,10,0,0,0,21,0,0,0,0},
//            "traidat", 150000L, "Sôngôku", 7, 7, 3, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_cam", "Quần võ kame", LoaiItem.QUAN,
//            "vatpham/do/traidat/set_cam/quan.png",
//            "Giúp tăng HP", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,5000,0,0,0},
//            "traidat", 150000L, "Sôngôku", 7, 7, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_than_linh", "Găng thần linh", LoaiItem.GANG,
//            "vatpham/do/traidat/set_than_linh/gang.png",
//            "Giúp tăng sức đánh", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,0,0,9289,0},
//            "traidat", 20_000_000_000L, "Sôngôku", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_cam", "Giày võ kame", LoaiItem.GIAY,
//            "vatpham/do/traidat/set_cam/giay.png",
//            "Giúp tăng MP", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,0,200,0,0},
//            "traidat", 150000L, "Sôngôku", 7, 7, 2, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "rada1", "Rada cấp 1", LoaiItem.RADA,
//            "vatpham/do/rada/rada1.png",
//            "Giúp tăng Chí Mạng", 1,
//            new int[]{0,0,0,1,0,0,0,0,21,0,0,0,0},
//            "traidat", 15000L, "Sôngôku", 7, 7, 1, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Nón Lý Trí", LoaiItem.AO,
//            "vatpham/do/traidat/set_ayaka/ao.png",
//            "Giúp giảm sát thương", 1,
//            new int[]{0,0,0,0,289,0,0,0,21,0,0,0,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Ly Không Gian", LoaiItem.QUAN,
//            "vatpham/do/traidat/set_ayaka/quan.png",
//            "Giúp tăng HP", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,5000,0,0,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Cát Thời Gian", LoaiItem.GANG,
//            "vatpham/do/traidat/set_ayaka/gang.png",
//            "Giúp tăng sức đánh", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,0,0,8289,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Lông Vũ Tử Vong", LoaiItem.GIAY,
//            "vatpham/do/traidat/set_ayaka/giay.png",
//            "Giúp tăng MP", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,0,10000,0,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_ayaka", "Hoa Sự Sống", LoaiItem.RADA,
//            "vatpham/do/traidat/set_ayaka/rada.png",
//            "Giúp tăng Chí Mạng", 1,
//            new int[]{0,0,0,10,0,0,0,0,21,0,0,0,0},
//            "traidat", 20_000_000_000L, "Dũng Sĩ Trong Băng Giá", 7, 7, 7, -1
//        ));
//
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Áo vải thô", LoaiItem.AO,
//            "vatpham/do/xayda/set_vai_tho/ao.png",
//            "Giúp giảm sát thương", 1,
//            new int[]{0,0,0,0,3,0,35,0,0,0,0,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));

//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_than_linh", "Quần thần linh", LoaiItem.QUAN,
//            "vatpham/do/xayda/set_than_linh/quan.png",
//            "Giúp tăng HP", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,110000,0,0,0},
//            "xayda", 20_000_000_000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Găng vải thô", LoaiItem.GANG,
//            "vatpham/do/xayda/set_vai_tho/gang.png",
//            "Giúp tăng sức đánh", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,0,0,8,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Giày vải thô", LoaiItem.GIAY,
//            "vatpham/do/xayda/set_vai_tho/giay.png",
//            "Giúp tăng MP", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,0,10,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_than_linh", "Nhẫn thần linh", LoaiItem.RADA,
//            "vatpham/do/xayda/set_than_linh/rada.png",
//            "Giúp tăng Chí Mạng", 1,
//            new int[]{0,0,0,18,0,0,35,0,0,0,0,0,0},
//            "xayda", 40_000_000_000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Áo vải thô", LoaiItem.AO,
//            "vatpham/do/xayda/set_vai_tho/ao.png",
//            "Giúp giảm sát thương", 1,
//            new int[]{0,0,0,0,8,0,35,0,0,0,0,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_than_linh", "Quần thần linh", LoaiItem.QUAN,
//            "vatpham/do/xayda/set_than_linh/quan.png",
//            "Giúp tăng HP", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,123000,0,0,0},
//            "xayda", 20_000_000_000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Găng vải thô", LoaiItem.GANG,
//            "vatpham/do/xayda/set_vai_tho/gang.png",
//            "Giúp tăng sức đánh", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,0,0,5,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "set_vai_tho", "Giày vải thô", LoaiItem.GIAY,
//            "vatpham/do/xayda/set_vai_tho/giay.png",
//            "Giúp tăng MP", 1,
//            new int[]{0,0,0,0,0,0,35,0,0,0,12,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "rada1", "Rada cấp 1", LoaiItem.RADA,
//            "vatpham/do/rada/rada1.png",
//            "Giúp tăng Chí Mạng", 1,
//            new int[]{0,0,0,1,0,0,35,0,0,0,0,0,0},
//            "xayda", 1000L, "Nappa", 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "ve_quay_npc_haidang", "Vé quay Rồng Thần", LoaiItem.VE_QUAY_NPC_HAIDANG,
//            "vatpham/vatphamgame/ve_quay_npc_haidang/vequay.png",
//            "Vé quay ẩn chứa tiềm năng vô hạn. Cần gặp NPC Admin Hải Đăng để sử dụng và có cơ hội nhận những phần thưởng hiếm và nhiều phần quà giá trị khác", 900,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "ve_quay_npc_haidang", "Vé quay Rồng Thần", LoaiItem.VE_QUAY_NPC_HAIDANG,
//            "vatpham/vatphamgame/ve_quay_npc_haidang/vequay.png",
//            "Vé quay ẩn chứa tiềm năng vô hạn. Cần gặp NPC Admin Hải Đăng để sử dụng và có cơ hội nhận những phần thưởng hiếm và nhiều phần quà giá trị khác", 1,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "nr1sd", "Ngọc rồng đen 1 sao", LoaiItem.NGOCRONG,
//            "vatpham/vatphamgame/ngocrongden/nr1sd.png",
//            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "nr2sd", "Ngọc rồng đen 2 sao", LoaiItem.NGOCRONG,
//            "vatpham/vatphamgame/ngocrongden/nr2sd.png",
//            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "nr3sd", "Ngọc rồng đen 3 sao", LoaiItem.NGOCRONG,
//            "vatpham/vatphamgame/ngocrongden/nr3sd.png",
//            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "nr4sd", "Ngọc rồng đen 4 sao", LoaiItem.NGOCRONG,
//            "vatpham/vatphamgame/ngocrongden/nr4sd.png",
//            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "nr5sd", "Ngọc rồng đen 5 sao", LoaiItem.NGOCRONG,
//            "vatpham/vatphamgame/ngocrongden/nr5sd.png",
//            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "nr6sd", "Ngọc rồng đen 6 sao", LoaiItem.NGOCRONG,
//            "vatpham/vatphamgame/ngocrongden/nr6sd.png",
//            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "nr7sd", "Ngọc rồng đen 7 sao", LoaiItem.NGOCRONG,
//            "vatpham/vatphamgame/ngocrongden/nr7sd.png",
//            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
//            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
//            "all", 0, null, 0, 0, 0, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "set_huy_diet", "Áo hủy diệt", LoaiItem.AO,
//            "vatpham/do/traidat/set_huy_diet/ao.png",
//            "Giúp giảm sát thương", 1,
//            new int[]{0,0,0,0,2718,0,0,0,21,0,0,0,0},
//            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "set_huy_diet", "Quần hủy diệt", LoaiItem.QUAN,
//            "vatpham/do/traidat/set_huy_diet/quan.png",
//            "Giúp tăng HP", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,195000,0,0,0},
//            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "set_huy_diet", "Găng hủy diệt", LoaiItem.GANG,
//            "vatpham/do/traidat/set_huy_diet/gang.png",
//            "Giúp tăng sức đánh", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,0,0,19500,0},
//            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "set_huy_diet", "Giày hủy diệt", LoaiItem.GIAY,
//            "vatpham/do/traidat/set_huy_diet/giay.png",
//            "Giúp tăng MP", 1,
//            new int[]{0,0,0,0,0,0,0,0,21,0,182000,0,0},
//            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrangRuongDo(new Item(
//            "set_huy_diet", "Nhẫn hủy diệt", LoaiItem.RADA,
//            "vatpham/do/traidat/set_huy_diet/rada.png",
//            "Giúp tăng Chí Mạng", 1,
//            new int[]{0,0,0,20,0,0,0,0,21,0,0,0,0},
//            "traidat", 40_000_000_000L, null, 7, 7, 7, -1
//        ));
//
//        duLieu.themItemVaoHanhTrang(new Item(
//            "glt_c1", "Giáp luyện tập cấp 1", LoaiItem.GIAPLUYENTAP,
//            "vatpham/vatphamgame/giapluyentap/gltc1.png",
//            "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra nếu sẽ tăng sức đánh 10% và Crit 15%, ST Crit 30%", 1,
//            new int[]{0,0,0,0,0,0,25,0,0,0,0,0,0},
//            "traidat", 10_000_000L, null, 7, 5, 0, 0
//        ));
    }

    // Hàm lấy vật phẩm để thêm vào hành trang
    public Item randomDeoLung() {
        // Danh sách item các vật phẩm đeo lưng
        Item[] danhSachItem = new Item[]{
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
        // Random chọn item
        Item itemDuocChon = danhSachItem[MathUtils.random(danhSachItem.length - 1)];
        switch (itemDuocChon.getId()) {
            case "luoi_hai": veHUD.setTinNhanPet("Bạn đã nhận thành công Lưỡi Hái Thời Không.",2f);break;
            case "canh_doi": veHUD.setTinNhanPet("Bạn đã nhận thành công Cánh Dơi Dracula.",2f);break;
            case "canh_ac_quy": veHUD.setTinNhanPet("Bạn đã nhận thành công Cánh Ác Quỷ.",2f);break;
            case "kiem": veHUD.setTinNhanPet("Bạn đã nhận thành công Kiếm Thánh Z.",2f);break;
            case "hoa": veHUD.setTinNhanPet("Bạn đã nhận thành công Bó Hoa Hồng.",2f);break;
            case "canh_thien_su": veHUD.setTinNhanPet("Bạn đã nhận thành công Cánh Thiên Sứ.",2f);break;
            case "canh_thien_than": veHUD.setTinNhanPet("Bạn đã nhận thành công Cánh Thiên Thần.",2f);break;
            default: veHUD.setTinNhanPet("Bạn đã nhận thành công vật phẩm đeo lưng.",2f);
        }
        // random lại các ô > 0
        int[] stats = itemDuocChon.getChiso();
        int soChiSoCanCong = 0;
        int chiSoRandomMax = 1;
        for (int i = 0; i < stats.length; i++) {
            if (stats[i] > 0) {
                soChiSoCanCong += 1;
            }
        }
        switch (soChiSoCanCong) {
            case 1: chiSoRandomMax = 15;break;
            case 2: chiSoRandomMax = 10;break;
            case 3: chiSoRandomMax = 5;break;
            default: chiSoRandomMax = 1;break;
        }
        for (int i = 0; i < stats.length; i++) {
            if (stats[i] > 0) {
                stats[i] = MathUtils.random(1,chiSoRandomMax);
            }
        }
        // Trả về item mới với chỉ số random
        return itemDuocChon;
    }
    public Item randomHuyHieu() {
        // Danh sách item các vật phẩm đeo lưng
        Item[] danhSachItem = new Item[]{
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
                nhanVat.getHanhtinh()+"_toi_thuong", "Huy hiệu Tối Thượng", LoaiItem.HUYHIEU,
                "vatpham/vatphamgame/huy_hieu/"+nhanVat.getHanhtinh()+"_toi_thuong/icon.png",
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

        // Random chọn item
        Item itemDuocChon = danhSachItem[MathUtils.random(danhSachItem.length - 1)];
        switch (itemDuocChon.getId()) {
            case "thien_tu": veHUD.setTinNhanPet("Bạn đã nhận thành công Danh Hiệu Thiên Tử.",2f);break;
            case "trum_cuoi": veHUD.setTinNhanPet("Bạn đã nhận thành công Danh Hiệu Trùm Cuối.",2f);break;
            case "traidat_toi_thuong": veHUD.setTinNhanPet("Bạn đã nhận thành công Danh Hiệu Trái Đất Tối Thượng.",2f);break;
            case "xayda_toi_thuong": veHUD.setTinNhanPet("Bạn đã nhận thành công Danh Hiệu Saiyan Tối Thượng.",2f);break;
            default: veHUD.setTinNhanPet("Bạn đã nhận thành công Danh Hiệu.",2f);
        }
        // random lại các ô > 0
        int[] stats = itemDuocChon.getChiso();
        int soChiSoCanCong = 0;
        int chiSoRandomMax = 1;
        for (int i = 0; i < stats.length; i++) {
            if (stats[i] > 0) {
                soChiSoCanCong += 1;
            }
        }
        switch (soChiSoCanCong) {
            case 1: chiSoRandomMax = 15;break;
            case 2: chiSoRandomMax = 10;break;
            case 3: chiSoRandomMax = 5;break;
            default: chiSoRandomMax = 1;break;
        }
        for (int i = 0; i < stats.length; i++) {
            if (stats[i] > 0) {
                stats[i] = MathUtils.random(1,chiSoRandomMax);
            }
        }
        // Trả về item mới với chỉ số random
        return itemDuocChon;
    }
    public Item randomAura() {
        // Danh sách item các vật phẩm đeo lưng
        Item[] danhSachItem = new Item[]{
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

        // Random chọn item
        Item itemDuocChon = danhSachItem[MathUtils.random(danhSachItem.length - 1)];
        switch (itemDuocChon.getId()) {
            case "thien_tu": veHUD.setTinNhanPet("Bạn vừa nhận Aura Long Hồn Thượng Giới (VIP).",2f);break;
            case "trum_cuoi": veHUD.setTinNhanPet("Bạn vừa nhận Aura Tiểu Đội Trưởng (VIP).",2f);break;
            default: veHUD.setTinNhanPet("Bạn đã nhận thành công Aura.",2f);
        }
        // random lại các ô > 0
        int[] stats = itemDuocChon.getChiso();
        int soChiSoCanCong = 0;
        int chiSoRandomMax = 1;
        for (int i = 0; i < stats.length; i++) {
            if (stats[i] > 0) {
                soChiSoCanCong += 1;
            }
        }
        switch (soChiSoCanCong) {
            case 1: chiSoRandomMax = 25;break;
            case 2: chiSoRandomMax = 20;break;
            case 3: chiSoRandomMax = 15;break;
            case 4: chiSoRandomMax = 10;break;
            default: chiSoRandomMax = 1;break;
        }
        for (int i = 0; i < stats.length; i++) {
            if (stats[i] > 0) {
                stats[i] = MathUtils.random(1,chiSoRandomMax);
            }
        }
        // Trả về item mới với chỉ số random
        return itemDuocChon;
    }
    public Item randomBongTai() {
        // Danh sách item các vật phẩm đeo lưng
        Item[] danhSachItem = new Item[]{
            new Item(
                "bongtaic2", "Bông tai Porata Cấp 2", LoaiItem.BONGTAI,
                "vatpham/vatphamgame/bongtai/bongtaic2.png",
                "Sử dụng để hợp thể với đệ tử và tăng tổng 10% chỉ số", 1,
                new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                "all", 150000000L, null, 0, 0, 0, -1
            ),
            new Item(
                "bongtaic3", "Bông tai Porata Cấp 3", LoaiItem.BONGTAI,
                "vatpham/vatphamgame/bongtai/bongtaic3.png",
                "Sử dụng để hợp thể với đệ tử và tăng tổng 20% chỉ số", 1,
                new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                "all", 1500000000L, null, 0, 0, 0, -1
            )
        };

        // Random chọn item
        Item itemDuocChon = danhSachItem[MathUtils.random(danhSachItem.length - 1)];
        switch (itemDuocChon.getId()) {
            case "bongtaic2": veHUD.setTinNhanPet("Bạn đã nhận thành công Bông Tai Porata Cấp 2.",2f);break;
            case "bongtaic3": veHUD.setTinNhanPet("Bạn đã nhận thành công Bông Tai Porata Cấp 3.",2f);break;
            default: veHUD.setTinNhanPet("Bạn đã nhận thành công Bông Tai Porata.",2f);
        }

        return itemDuocChon;
    }
    public Item randomGenshin() {
        // Danh sách item các vật phẩm đeo lưng
        Item[] danhSachItem = new Item[]{
            new Item(
                "xiao", "Cải trang Hộ Pháp Dạ Xoa", LoaiItem.CAITRANG,
                "nhanvat/caitrang/xiao/icon.png",
                "Cải trang thành Xiao - Genshin impact", 1,
                new int[]{0,0,0,50,0,100,100,0,100,0,0,0,0},
                nhanVat.getHanhtinh(), 9_999_999_999L, null, 0, 0, 0, -1
            ),
            new Item(
                "ayaka", "Cải trang tiểu thư Kamisato", LoaiItem.CAITRANG,
                "nhanvat/caitrang/ayaka/icon.png",
                "Cải trang thành Ayaka - Genshin impact", 1,
                new int[]{0,0,0,0,0,0,40,50,60,0,0,0,0},
                nhanVat.getHanhtinh(), 9_999_999_999L, null, 0, 0, 0, -1
            )
        };

        // Random chọn item
        Item itemDuocChon = danhSachItem[MathUtils.random(danhSachItem.length - 1)];
        switch (itemDuocChon.getId()) {
            case "xiao": veHUD.setTinNhanPet("Bạn đã nhận thành công Cải Trang Chiến Thần Xiao.",2f);break;
            case "ayaka": veHUD.setTinNhanPet("Bạn đã nhận thành công Cải Trang Tiểu Thư Ayaka.",2f);break;
            default: veHUD.setTinNhanPet("Bạn đã nhận thành công item.",2f);
        }

        return itemDuocChon;
    }
    public Item randomNgocRong() {
        int soSao = MathUtils.random(1,7);
        return new Item(
            "nr"+soSao+"s", "Ngọc rồng " +soSao+" sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrong/"+"nr"+soSao+"s"+".png",
            "Thu thập để ước rồng thần", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        );
    }
    public Item randomNgocRongDen() {
        int soSao = MathUtils.random(1,7);
        return new Item(
            "nr"+soSao+"sd", "Ngọc rồng đen "+soSao+" sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrongden/"+"nr"+soSao+"sd"+".png",
            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        );
    }
    public void themQuaAdHaiDang() {
//        duLieu.themItemVaoHanhTrang(new Item(
//            "thien_tu", "Huy hiệu Thiên Tử", LoaiItem.HUYHIEU,
//            "vatpham/vatphamgame/huy_hieu/thien_tu/icon.png",
//            "Thiên mệnh tại thân - Thống ngự càn khôn", 1,
//            new int[]{0, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
//        duLieu.themItemVaoHanhTrang(new Item(
//            nhanVat.getHanhtinh()+"_toi_thuong", "Huy hiệu Tối Thượng", LoaiItem.HUYHIEU,
//            "vatpham/vatphamgame/huy_hieu/"+nhanVat.getHanhtinh()+"_toi_thuong/icon.png",
//            "Biểu tượng của người thống trị - Sức mạnh tối thượng không ai sánh kịp", 1,
//            new int[]{0,0,0,10,0,10,0,0,0,0,0,0,0},
//            "all", 10_000_000L, null, 0, 0, 0, -1
//        ));
        duLieu.themItemVaoHanhTrang(new Item(
            "set_cam", "Áo võ kame", LoaiItem.AO,
            "vatpham/do/traidat/set_cam/ao.png",
            "Giúp giảm sát thương", 1,
            new int[]{0,0,0,0,10,0,0,0,21,0,0,0,0},
            "traidat", 150000L, "Sôngôku", 7, 7, 3, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_cam", "Quần võ kame", LoaiItem.QUAN,
            "vatpham/do/traidat/set_cam/quan.png",
            "Giúp tăng HP", 1,
            new int[]{0,0,0,0,0,0,0,0,21,5000,0,0,0},
            "traidat", 150000L, "Sôngôku", 7, 7, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_cam", "Găng thần linh", LoaiItem.GANG,
            "vatpham/do/traidat/set_than_linh/gang.png",
            "Giúp tăng sức đánh", 1,
            new int[]{0,0,0,0,0,0,0,0,21,0,0,9289,0},
            "traidat", 20_000_000_000L, "Sôngôku", 7, 7, 7, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "set_cam", "Giày võ kame", LoaiItem.GIAY,
            "vatpham/do/traidat/set_cam/giay.png",
            "Giúp tăng MP", 1,
            new int[]{0,0,0,0,0,0,0,0,21,0,200,0,0},
            "traidat", 150000L, "Sôngôku", 7, 7, 2, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "rada1", "Rada cấp 1", LoaiItem.RADA,
            "vatpham/do/rada/rada1.png",
            "Giúp tăng Chí Mạng", 1,
            new int[]{0,0,0,1,0,0,0,0,21,0,0,0,0},
            "traidat", 15000L, "Sôngôku", 7, 7, 1, -1
        ));
    }
    public void themQuaAdThanhLe() {
        duLieu.themItemVaoHanhTrang(new Item(
            "nr1s", "Ngọc rồng 1 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrong/nr1s.png",
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr2s", "Ngọc rồng 2 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrong/nr2s.png",
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr3s", "Ngọc rồng 3 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrong/nr3s.png",
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr4s", "Ngọc rồng 4 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrong/nr4s.png",
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr5s", "Ngọc rồng 5 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrong/nr5s.png",
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr6s", "Ngọc rồng 6 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrong/nr6s.png",
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr7s", "Ngọc rồng 7 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrong/nr7s.png",
            "Thu thập để ước rồng thần", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr1sd", "Ngọc rồng đen 1 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrongden/nr1sd.png",
            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr2sd", "Ngọc rồng đen 2 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrongden/nr2sd.png",
            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr3sd", "Ngọc rồng đen 3 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrongden/nr3sd.png",
            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr4sd", "Ngọc rồng đen 4 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrongden/nr4sd.png",
            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr5sd", "Ngọc rồng đen 5 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrongden/nr5sd.png",
            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr6sd", "Ngọc rồng đen 6 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrongden/nr6sd.png",
            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "nr7sd", "Ngọc rồng đen 7 sao", LoaiItem.NGOCRONG,
            "vatpham/vatphamgame/ngocrongden/nr7sd.png",
            "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 99,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 0, null, 0, 0, 0, -1
        ));
    }
    public void themQuaTuDieuUocRongThan() {
        if (veHUD.ngocRongUoc.equals("1sao")) {
            switch (veHUD.nutduocchon) {
                case 0:
                    duLieu.themItemVaoHanhTrang(new Item(
                        "bongtaic1", "Bông tai Porata - VIP", LoaiItem.BONGTAI,
                        "vatpham/vatphamgame/bongtai/bongtaic1RT.png",
                        "Sử dụng để hợp thể với đệ tử - Vật phẩm nhận từ Rồng Thần ( tăng 5% chỉ số )", 1,
                        new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                        "all", 1000, null, 0, 0, 0, -1
                    ));
                    if (duLieu.coDeTu() && !veHUD.dangHopThe && veHUD.timeHopTheTHuong == 0 && veHUD.delayHopTheBongTai == 0) {
                        veHUD.timeChoHopThe = 1.5f;
                        veHUD.dangHopTheThuong = false;
                        if (!veHUD.dangHopThe) {
                            veHUD.bongTaiDangDung = "bongtaic1";
                            veHUD.bongTaiRongThan = true;
                        }
                        veHUD.hangTrangDangChon = -1;
                        veHUD.scrollYPhai = 0;
                    }
                    veHUD.setTinNhanPet("Chúc mừng bạn đã ước thành công Bông Tai Porata Đặc Biệt",2f);
                    break;
                case 1:
                    duLieu.tangNgoc(1500);
                    veHUD.setTinNhanPet("Điều ước thành hiện thực - nhận thành công 1500 ngọc",2f);
                    break;
                case 2:
                    if (duLieu.getSucManh() < 50_000_000_000L || duLieu.getCapSkill(3) != 7) {
                        duLieu.tangSucManh(200_000_000);
                        duLieu.tangTiemNang(200_000_000);
                        veHUD.setTinNhanPet("Sức mạnh của bạn đã tăng thêm 200 triệu!", 2f);
                    } else {
                        if (duLieu.getCapSkill(3) == 7) {
                            duLieu.tangCapSkill(3);
                            veHUD.setTinNhanPet("Điều ước thành hiện thực - nâng thành công cấp skill!", 2f);
                            if (nhanVat.getHanhtinh().equals("xayda") || nhanVat.getTen().equals("admin")) {
                                if (veHUD.dangBienKhi) {
                                    veHUD.huyBienKhi();
                                    veHUD.timeChoBienKhi = 2f;
                                } else {
                                    veHUD.timeChoBienKhi = 2f;
                                }
                            }
                            duLieu.capNhatMotaSkill(3);
                        }
                    }
                    break;
                case 3:
                    duLieu.tangVang(500_000_000);
                    veHUD.setTinNhanPet("Bạn nhận được 500 triệu vàng từ Rồng Thần",2f);
                    break;
                case 4:
                    Map<String, String[]> tenNgocRong = new HashMap<>();
                    String[] idNgocRong = {"nr1sd","nr2sd","nr3sd","nr4sd","nr5sd","nr6sd","nr7sd"};
                    for (int i = 0; i < 7; i++) {
                        tenNgocRong.put(idNgocRong[i],new String[] { "Ngọc rồng đen " + (i + 1) + " sao", (i+1)+""});
                    }
                    String ngocRongDuocChon = idNgocRong[MathUtils.random(idNgocRong.length - 1)];
                    duLieu.themItemVaoHanhTrang(new Item(
                        ngocRongDuocChon, tenNgocRong.get(ngocRongDuocChon)[0], LoaiItem.NGOCRONG,
                        "vatpham/vatphamgame/ngocrongden/nr1sd.png",
                        "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám.", 1,
                        new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                        "all", 0, null, 0, 0, 0, -1
                    ));
                    veHUD.setTinNhanPet("Bạn đã nhận được x1 Ngọc Rồng Đen "+tenNgocRong.get(ngocRongDuocChon)[1]+" Sao",2f);
                    break;
            }
        } else if (veHUD.ngocRongUoc.equals("2sao")) {
            switch (veHUD.nutduocchon) {
                case 0:
                    duLieu.themItemVaoHanhTrang(new Item(
                        "avt_vip", "Avatar VIP", LoaiItem.AVATAR,
                        "nhanvat/" + nhanVat.getHanhtinh() + "/avatar/avt_vip/daudung.png",
                        "Dùng để thay đổi khuôn mặt", 1,
                        new int[]{0,0,0,0,10,0,15,0,15,0,0,0,0},
                        nhanVat.getHanhtinh(), 1500000L, null, 0, 0, 0, -1
                    ));
                    if (duLieu.getSucManh() >= 1500000L) {
                        int viTriMoi = duLieu.getHanhTrang().size() - 1;
                        veHUD.hangTrangDangChon = viTriMoi + 8;
                        veHUD.xulyitem.macDo(veHUD.hangTrangDangChon);
                        veHUD.hangTrangDangChon = -1;
                        veHUD.scrollYPhai = 0;
                    }
                    veHUD.setTinNhanPet("Bạn đã trở thành người đẹp trai nhất vũ trụ!",2f);
                    break;
                case 1:
                    duLieu.tangNgoc(300);
                    veHUD.setTinNhanPet("Điều ước thành hiện thực - nhận thành công 300 ngọc",2f);
                    break;
                case 2:
                    duLieu.tangSucManh(20_000_000);
                    duLieu.tangTiemNang(20_000_000);
                    veHUD.setTinNhanPet("Sức mạnh của bạn đã tăng thêm 20 triệu!",2f);
                    break;
                case 3:
                    duLieu.tangVang(50_000_000);
                    veHUD.setTinNhanPet("Bạn nhận được 50 triệu vàng từ Rồng Thần",2f);
                    break;
                case 4:
                    if (duLieu.coDeTu()) {
                        duLieu.deTu.doiSkillDeTu(2);
                        duLieu.deTu.doiSkillDeTu(3);
                        duLieu.deTu.setCapSkillDeTu(1,1);
                        duLieu.deTu.setCapSkillDeTu(2,1);
                    } else {
                        veHUD.setTinNhanPet("Bạn chưa có đệ tử - rồng thần đã hết kiên nhẫn",2f);
                    }
                    break;
            }
        } else if (veHUD.ngocRongUoc.equals("3sao")) {
            switch (veHUD.nutduocchon) {
                case 0:
                    duLieu.themItemVaoHanhTrang(new Item(
                        "thien_tu", "Huy hiệu Thiên Tử", LoaiItem.HUYHIEU,
                        "vatpham/vatphamgame/huy_hieu/thien_tu/icon.png",
                        "Thiên mệnh tại thân - Thống ngự càn khôn", 1,
                        new int[]{0,0,0,0,0,0,10,10,10,0,0,0,0},
                        "all", 10_000_000L, null, 0, 0, 0, -1
                    ));
                    veHUD.tinNhanPet = "Bạn đã nhận được Huy hiệu Thiên Tử";
                    if (duLieu.getSucManh() >= 10_000_000L && duLieu.getHanhTrang().get(duLieu.getHanhTrang().size() - 1).getLoai() == LoaiItem.HUYHIEU) {
                        if (veHUD.dangDungHuyHieu) {
                            veHUD.dangDungHuyHieu = false;
                            veHUD.chuaSetUpAnhHuyHieu = true;
                        }
                        veHUD.dangDungHuyHieu = true;
                        veHUD.huyHieuDangDung = duLieu.getHanhTrang().get(duLieu.getHanhTrang().size() - 1);
                    }
                    break;
                case 1:
                    duLieu.tangNgoc(50);
                    veHUD.setTinNhanPet("Điều ước thành hiện thực - nhận thành công 50 ngọc",2f);
                    break;
                case 2:
                    duLieu.tangSucManh(2_000_000);
                    duLieu.tangTiemNang(2_000_000);
                    veHUD.setTinNhanPet("Sức mạnh của bạn đã tăng thêm 2 triệu!",2f);
                    break;
                case 3:
                    duLieu.tangVang(5_000_000);
                    veHUD.setTinNhanPet("Bạn nhận được 5 triệu vàng từ Rồng Thần",2f);
                    break;
                case 4:
                    if (duLieu.coDeTu()) {
                        duLieu.deTu.doiSkillDeTu(1);
                        duLieu.deTu.setCapSkillDeTu(0,1);
                    } else {
                        veHUD.setTinNhanPet("Bạn chưa có đệ tử - rồng thần đã hết kiên nhẫn",2f);
                    }
                    break;
            }
        } else if (veHUD.ngocRongUoc.equals("1saoden")) {
            switch (veHUD.nutduocchon) {
                case 0:
                    Item itemMoi;
                    if (duLieu.getHanhTrangDangMac().get(5) != null &&
                        duLieu.getHanhTrangDangMac().get(5).getId().equals("goku_black")) {
                        // Đang mặc ct Goku Black thì nâng cấp thành SSJ Rose
                        veHUD.xulyitem.goCaiTrang(NhanVatXuLy.getDangMacCaiTrang(), true);
                        itemMoi = new Item(
                            "goku_black_rose", "Cải trang Super Black Goku", LoaiItem.CAITRANG,
                            "nhanvat/caitrang/goku_black_rose/daudung.png",
                            "Cải trang thành Super Black Goku", 1,
                            new int[]{0, 0, 0, 0, 0, 0, 45, 45, 45, 0, 0, 0, 0},
                            "all", 40_000_000_000L, null, 0, 0, 0, -1
                        );
                        veHUD.setTinNhanPet("Bạn vừa khai phá SSJ ROSE",2f);
                    } else {
                        // Chưa có hoặc không mặc ct Goku Black thì nhận ct Black Goku
                        itemMoi = new Item(
                            "goku_black", "Cải trang Black Goku", LoaiItem.CAITRANG,
                            "nhanvat/caitrang/goku_black/daudung.png",
                            "Cải trang thành Black Goku", 1,
                            new int[]{0, 0, 0, 0, 0, 0, 35, 35, 35, 0, 0, 0, 0},
                            "all", 40_000_000_000L, null, 0, 0, 0, -1
                        );
                        veHUD.setTinNhanPet("Bạn vừa trở thành black goku",2f);
                    }
                    duLieu.themItemVaoHanhTrang(itemMoi);
                    if (duLieu.getSucManh() >= 40_000_000_000L) {
                        int viTriMoi = duLieu.getHanhTrang().size() - 1;
                        veHUD.hangTrangDangChon = viTriMoi + 8;
                        veHUD.xulyitem.macDo(veHUD.hangTrangDangChon);
                        veHUD.hangTrangDangChon = -1;
                        veHUD.scrollYPhai = 0;
                    }
                    break;
                case 1:
                    duLieu.themItemVaoHanhTrang(new Item(
                        "phuong_hoang_lua", "Thú cưỡi cực VIP", LoaiItem.VANBAY,
                        "vatpham/vanbay/phuong_hoang_lua/phuonghoanglua.png",
                        "Dùng để bay và hồi phục HP, KI", 1,
                        new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                        "all", 1500000L, null, 0, 0, 0, -1
                    ));
                    if (duLieu.getSucManh() >= 1500000L) {
                        int viTriMoi = duLieu.getHanhTrang().size() - 1;
                        veHUD.hangTrangDangChon = viTriMoi + 8;
                        veHUD.xulyitem.macDo(veHUD.hangTrangDangChon);
                        veHUD.hangTrangDangChon = -1;
                        veHUD.scrollYPhai = 0;
                    }
                    veHUD.setTinNhanPet("Bạn đã nhận được Thú cưỡi cực VIP - Phượng Hoàng Lửa",2f);
                    break;
                case 2:
                    duLieu.themItemVaoHanhTrang(veHUD.themItemTest.randomHuyHieu());
                    if (duLieu.getSucManh() >= 10_000_000L && duLieu.getHanhTrang().get(duLieu.getHanhTrang().size() - 1).getLoai() == LoaiItem.HUYHIEU) {
                        if (veHUD.dangDungHuyHieu) {
                            veHUD.dangDungHuyHieu = false;
                            veHUD.chuaSetUpAnhHuyHieu = true;
                        }
                        veHUD.dangDungHuyHieu = true;
                        veHUD.huyHieuDangDung = duLieu.getHanhTrang().get(duLieu.getHanhTrang().size() - 1);
                    }
                    break;
                case 3:
                    String[] danhSachId = {"luoi_hai","canh_doi","canh_ac_quy","canh_thien_than","canh_thien_su","hoa","kiem"};
                    boolean duTatCa = true;
                    ArrayList<Item> danhSach = duLieu.getHanhTrang();
                    for (String idCanTim : danhSachId) {
                        boolean timThay = false;
                        for (Item item : danhSach) {
                            if (item != null && idCanTim.equals(item.getId())) {
                                timThay = true;
                                break;
                            }
                        }
                        if (!timThay) {
                            duTatCa = false; // thiếu ít nhất 1 id
                            break;
                        }
                    }
                    if (duTatCa) {
                        duLieu.themItemVaoHanhTrang(veHUD.themItemTest.randomAura());
                        if (duLieu.getSucManh() >= 10_000_000L && duLieu.getHanhTrang().get(duLieu.getHanhTrang().size() - 1).getLoai() == LoaiItem.AURA) {
                            if (veHUD.dangDungAura) {
                                veHUD.dangDungAura = false;
                                veHUD.chuaSetUpAnhAura = true;
                            }
                            veHUD.dangDungAura = true;
                            veHUD.auraDangDung = duLieu.getHanhTrang().get(duLieu.getHanhTrang().size() - 1);
                        }
                    } else {
                        duLieu.themItemVaoHanhTrang(veHUD.themItemTest.randomDeoLung());
                        if (duLieu.getSucManh() >= 10_000_000L && duLieu.getHanhTrang().get(duLieu.getHanhTrang().size() - 1).getLoai() == LoaiItem.DEOLUNG) {
                            if (veHUD.dangDungDeoLung) {
                                veHUD.dangDungDeoLung = false;
                                veHUD.chuaSetUpAnhDeoLung = true;
                            }
                            veHUD.dangDungDeoLung = true;
                            veHUD.deoLungDangDung = duLieu.getHanhTrang().get(duLieu.getHanhTrang().size() - 1);
                        }
                    }
                    break;
                case 4:
                    if (!duLieu.coDeTu()) {
                        duLieu.taoDeTu("Đệ tử", true);
                        duLieu.deTu.setVeHUD(veHUD);
                        veHUD.setTinNhanPet("Bạn vừa tạo đệ tử",2f);
                    } else {
                        if (duLieu.deTu.getSucManh()<1_500_000_000L) {
                            duLieu.deTu.tangSucManh(150_000_000);
                            duLieu.deTu.tangTiemNang(150_000_000);
                            veHUD.setTinNhanPet("Sức mạnh đệ tử vừa tăng 150 Tr",2f);
                        } else if (duLieu.deTu.getSucManh()<20_000_000_000L) {
                            duLieu.deTu.tangSucManh(2_000_000_000);
                            duLieu.deTu.tangTiemNang(2_000_000_000);
                            veHUD.setTinNhanPet("Sức mạnh đệ tử vừa tăng 2 Tỷ",2f);
                        } else if (duLieu.deTu.getSucManh()>=20_000_000_000L) {
                            duLieu.deTu.doiSkillDeTu(4);
                        }
                    }
                    break;
            }
        }
        veHUD.timeHienRongThan = 1f;
    }
    public void themQuaGacha(int index, int chiso, Texture[] mangAnh) {
        admin_haidang ui = (admin_haidang) veHUD.npcHienTai.npcHUDrender.ui_npc;
        switch (index) {
            case 0:
                Item item = this.randomBongTai();
                mangAnh[chiso] = item.getId().contains("c2") ? ui.randomBongTai[0] : ui.randomBongTai[1];
                duLieu.themItemVaoHanhTrang(item);
                break;
            case 1:
                duLieu.themItemVaoHanhTrang(new Item(
                    "hp", "Sinh lực", LoaiItem.PHUTRO,
                    "vatpham/vatphamgame/phu_tro/hp.png",
                    "Trong vòng tối đa 2 phút +10% HP", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                    "all", 0, null, 0, 0, 0, -1
                ));
                mangAnh[chiso] = ui.vatPhamGachaKrandom[1];
                break;
            case 2:
                duLieu.themItemVaoHanhTrang(new Item(
                    "gohan_beast", "Cải trang Gohan Beast", LoaiItem.CAITRANG,
                    "nhanvat/caitrang/gohan_beast/daudung.png",
                    "Cải trang thành Gohan Beast", 1,
                    new int[]{0,0,0,10,0,0,30,0,40,0,0,0,0},
                    nhanVat.getHanhtinh(), 10_000_000L, null, 0, 0, 0, -1
                ));
                mangAnh[chiso] = ui.vatPhamGachaKrandom[2];
                break;
            case 3:
                int soNgocCong = MathUtils.random(10,70);
                duLieu.tangNgoc(soNgocCong);
                mangAnh[chiso] = ui.vatPhamGachaKrandom[3];
                break;
            case 4:
                Item itemDeoLung = this.randomDeoLung();
                switch (itemDeoLung.getId()) {
                    case "canh_ac_quy" : mangAnh[chiso] = ui.randomDeoLung[0];break;
                    case "canh_doi" : mangAnh[chiso] = ui.randomDeoLung[1];break;
                    case "canh_thien_su" : mangAnh[chiso] = ui.randomDeoLung[2];break;
                    case "canh_thien_than" : mangAnh[chiso] = ui.randomDeoLung[3];break;
                    case "hoa" : mangAnh[chiso] = ui.randomDeoLung[4];break;
                    case "kiem" : mangAnh[chiso] = ui.randomDeoLung[5];break;
                    case "luoi_hai" : mangAnh[chiso] = ui.randomDeoLung[6];break;
                }
                duLieu.themItemVaoHanhTrang(itemDeoLung);
                break;
            case 5:
                duLieu.themItemVaoHanhTrang(new Item(
                    "goku_black_rose", "Cải trang Super Black Goku", LoaiItem.CAITRANG,
                    "nhanvat/caitrang/goku_black_rose/daudung.png",
                    "Cải trang thành Super Black Goku", 1,
                    new int[]{0,0,0,0,0,0,45,45,45,0,0,0,0},
                    "all", 40_000_000_000L, null, 0, 0, 0, -1
                ));
                mangAnh[chiso] = ui.vatPhamGachaKrandom[5];
                break;
            case 6:
                Item itemNr = randomNgocRong();
                int indexNr = Integer.parseInt(itemNr.getId().substring(2,3));
                mangAnh[chiso] = ui.randomNgocRong[indexNr-1];
                duLieu.themItemVaoHanhTrang(randomNgocRong());
                break;
            case 7:
                long soVangCong = MathUtils.random(50_000_000L,250_000_000L);
                duLieu.tangVang(soVangCong);
                mangAnh[chiso] = ui.vatPhamGachaKrandom[7];
                break;
            case 8:
                Item itemGenshin = this.randomGenshin();
                mangAnh[chiso] = itemGenshin.getId().equals("xiao") ? ui.randomGenShin[0] : ui.randomGenShin[1];
                duLieu.themItemVaoHanhTrang(itemGenshin);
                break;
            case 9:
                duLieu.themItemVaoHanhTrang(new Item(
                    "dame", "Cường công", LoaiItem.PHUTRO,
                    "vatpham/vatphamgame/phu_tro/dame.png",
                    "Trong vòng tối đa 2 phút +10% Sức đánh gốc", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                    "all", 0, null, 0, 0, 0, -1
                ));
                mangAnh[chiso] = ui.vatPhamGachaKrandom[9];
                break;
            case 10:
                duLieu.themItemVaoHanhTrang(new Item(
                    "skill4_de_khi", "Nâng cấp Biến Hình Đệ Tử", LoaiItem.NANGSKILL,
                    "kynang/iconkynang/xayda/skill4_xayda.png",
                    "Sử dụng để nâng cấp kỹ năng Biến Hình Đệ Tử lên cấp 8. Hoặc đổi skill 4 Đệ Tử thành Biến Hình ", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                    "all", 1500000L, null, 0, 0, 0, -1
                ));
                mangAnh[chiso] = ui.vatPhamGachaKrandom[10];
                break;
            case 11:
                Item itemNrDen = randomNgocRongDen();
                int indexNrDen = Integer.parseInt(itemNrDen.getId().substring(2,3));
                mangAnh[chiso] = ui.randomNgocRongDen[indexNrDen-1];
                duLieu.themItemVaoHanhTrang(itemNrDen);
                break;
            case 12:
                Item itemHuyHieu = this.randomHuyHieu();
                switch (itemHuyHieu.getId()) {
                    case "trum_cuoi" : mangAnh[chiso] = ui.randomHuyHieu[0];break;
                    case "thien_tu" : mangAnh[chiso] = ui.randomHuyHieu[1];break;
                    case "traidat_toi_thuong" : mangAnh[chiso] = ui.randomHuyHieu[2];break;
                    case "xayda_toi_thuong" : mangAnh[chiso] = ui.randomHuyHieu[3];break;
                }
                duLieu.themItemVaoHanhTrang(itemHuyHieu);
                break;
            case 13:
                int soVangCongg = MathUtils.random(5_000_000,50_000_000);
                duLieu.tangVang(soVangCongg);
                mangAnh[chiso] = ui.vatPhamGachaKrandom[13];
                break;
            case 14:
                duLieu.themItemVaoHanhTrang(new Item(
                    "ki", "Linh khí", LoaiItem.PHUTRO,
                    "vatpham/vatphamgame/phu_tro/ki.png",
                    "Trong vòng tối đa 2 phút +10% KI", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                    "all", 0, null, 0, 0, 0, -1
                ));
                mangAnh[chiso] = ui.vatPhamGachaKrandom[14];
                break;
            case 15:
                duLieu.themItemVaoHanhTrang(new Item(
                    "trung_de_tu", "Trứng đệ tử", LoaiItem.PHUTRO,
                    "vatpham/vatphamgame/phu_tro/trung_de_tu.png",
                    "Sử dụng vật phẩm có thể giúp người chơi sở hữu đệ tử.", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                    "all", 0, null, 0, 0, 0, -1
                ));
                mangAnh[chiso] = ui.vatPhamGachaKrandom[15];
                break;
        }
    }
    public void nhanQuaTanThu() {
        duLieu.themItemVaoHanhTrang(new Item(
            "adminHD", "Hộp quà từ Admin Hải Đăng", LoaiItem.HOPQUA,
            "vatpham/vatphamgame/hop_qua/adminHD.png",
            "Tân binh à, chào mừng bạn đến với thế giới Ngọc Rồng Online! Món quà này là lời cảm ơn từ Admin Hải Đăng - mong nó sẽ giúp bạn khởi đầu thuận lợi và có thật nhiều kỷ niệm đáng nhớ.", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 1000L, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "adminTL", "Hộp quà từ Admin Thành Lê", LoaiItem.HOPQUA,
            "vatpham/vatphamgame/hop_qua/adminTL.png",
            "Hoan nghênh bạn, tân binh! Admin Thành Lê gửi đến bạn món quà tân thủ như một lời chào mừng - hãy dùng nó để khởi đầu mạnh mẽ và ghi dấu hành trình của mình.", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 1000L, null, 0, 0, 0, -1
        ));

        duLieu.themItemVaoHanhTrang(new Item(
            "adminDL", "Hộp quà từ Admin Dũng Lê", LoaiItem.HOPQUA,
            "vatpham/vatphamgame/hop_qua/adminDL.png",
            "Xin chào tân binh! Admin Dũng Lê gửi tặng bạn món quà đặc biệt - chúc bạn sớm khám phá trọn vẹn thế giới Ngọc Rồng Online.", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 1000L, null, 0, 0, 0, -1
        ));
    }

    public void suDungGiftCode(String giftcode, List<String> danhSachPhanThuong) {
        switch (giftcode) {
            case "HDG01":
                duLieu.themItemVaoHanhTrang(new Item(
                    "ve_quay_npc_haidang", "Vé quay Rồng Thần", LoaiItem.VE_QUAY_NPC_HAIDANG,
                    "vatpham/vatphamgame/ve_quay_npc_haidang/vequay.png",
                    "Vé quay ẩn chứa tiềm năng vô hạn. Cần gặp NPC Admin Hải Đăng để sử dụng và có cơ hội nhận những phần thưởng hiếm và nhiều phần quà giá trị khác", 99,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                    "all", 0, null, 0, 0, 0, -1
                ));
                veHUD.setTinNhanPet("Bạn vừa nhận x99 Vé quay VIP",2f);
                danhSachPhanThuong.add("x99 Vé quay VIP");
                break;
            case "HDG02":
                duLieu.themItemVaoHanhTrang(new Item(
                    "bo_huyet", "Bổ huyết", LoaiItem.PHUTRO,
                    "vatpham/vatphamgame/phu_tro/bo_huyet.png",
                    "Trong vòng tối đa 10 phút +100% HP", 99,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                    "all", 0, null, 0, 0, 0, -1
                ));

                duLieu.themItemVaoHanhTrang(new Item(
                    "cuong_no", "Cuồng nộ", LoaiItem.PHUTRO,
                    "vatpham/vatphamgame/phu_tro/cuong_no.png",
                    "Trong vòng tối đa 10 phút +100% Sức đánh gốc", 99,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                    "all", 0, null, 0, 0, 0, -1
                ));

                veHUD.setTinNhanPet("Bạn vừa nhận x99 Bổ Huyết, Cuồng Nộ",2f);
                danhSachPhanThuong.add("x99 Bổ Huyết");
                danhSachPhanThuong.add("x99 Cuồng Nộ");
                break;
            case "HDG03":
                duLieu.themItemVaoHanhTrang(new Item(
                    "glt_c3", "Giáp luyện tập cấp 3", LoaiItem.GIAPLUYENTAP,
                    "vatpham/vatphamgame/giapluyentap/gltc3.png",
                    "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra sẽ tăng sức đánh 30% và Crit 15%, ST Crit 30%", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                    "all", 1_000_000_000L, null, 3, 0, 0, 0
                ));
                veHUD.setTinNhanPet("Bạn vừa nhận x1 Giáp Luyện Tập 3 sao",2f);
                danhSachPhanThuong.add("x1 Giáp Luyện Tập 3 sao");
                break;
        }
    }

    public Item themVatPhamWebTheoId(int id) {
        switch (id) {
            case 1: return new Item(
                "goku_black_rose", "Cải trang Super Black Goku", LoaiItem.CAITRANG,
                "nhanvat/caitrang/goku_black_rose/daudung.png",
                "Cải trang thành Super Black Goku", 1,
                new int[]{0,0,0,0,0,0,45,45,45,0,0,0,0},
                "all", 40_000_000_000L, null, 0, 0, 0, -1
                );
            case 2: return new Item(
                "trung_de_tu", "Trứng đệ tử", LoaiItem.PHUTRO,
                "vatpham/vatphamgame/phu_tro/trung_de_tu.png",
                "Sử dụng vật phẩm có thể giúp người chơi sở hữu đệ tử.", 1,
                new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
                "all", 0, null, 0, 0, 0, -1
                );
            case 3:
                return new Item(
                        "set_vai_tho", "Áo vải thô", LoaiItem.AO,
                        "vatpham/do/xayda/set_vai_tho/ao.png",
                    "Giúp giảm sát thương", 1,
                    new int[]{0,0,0,0,3,0,0,0,0,0,0,0,0},
                    "xayda", 1000L, "Nappa", 0, 0, 0, -1
                );
            case 4:
                return new Item(
                        "set_than_linh", "Quần thần linh", LoaiItem.QUAN,
                        "vatpham/do/xayda/set_than_linh/quan.png",
                    "Giúp tăng HP", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,110000,0,0,0},
                    "xayda", 20_000_000_000L, "Nappa", 0, 0, 0, -1
                );
            case 5:
                return new Item(
                        "set_vai_tho", "Găng vải thô", LoaiItem.GANG,
                        "vatpham/do/xayda/set_vai_tho/gang.png",
                    "Giúp tăng sức đánh", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,8,0},
                    "xayda", 1000L, "Nappa", 0,0,0, -1
                );
            case 6:
                return new Item(
                        "set_vai_tho", "Giày vải thô", LoaiItem.GIAY,
                        "vatpham/do/xayda/set_vai_tho/giay.png",
                    "Giúp tăng MP", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,10,0,0},
                    "xayda", 1000L, "Nappa", 0,0,0, -1
                );
            case 7:
                return new Item(
                        "set_than_linh", "Nhẫn thần linh", LoaiItem.RADA,
                        "vatpham/do/xayda/set_than_linh/rada.png",
                    "Giúp tăng Chí Mạng", 1,
                    new int[]{0,0,0,18,0,0,0,0,0,0,0,0,0},
                    "xayda", 40_000_000_000L, "Nappa", 0,0,0, -1
                );
        }
        return null;
    }
}
