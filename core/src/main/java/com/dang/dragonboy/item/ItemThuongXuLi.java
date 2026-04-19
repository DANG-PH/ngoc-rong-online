package com.dang.dragonboy.item;

import java.util.*;
import com.badlogic.gdx.Gdx;

public class ItemThuongXuLi {

    private static final Map<LoaiItem, String> MOTA_ITEM = Map.of(
        LoaiItem.AO,   "Giúp giảm sát thương",
        LoaiItem.QUAN, "Giúp tăng HP",
        LoaiItem.GANG, "Giúp tăng sức đánh",
        LoaiItem.GIAY, "Giúp tăng MP",
        LoaiItem.RADA, "Giúp tăng Chí Mạng"
    );

    private static final Map<String, Map<LoaiItem, int[]>> CHISO_ITEM = new HashMap<>();
    private static final Map<String, Map<LoaiItem, Long>> SMYC_ITEM = new HashMap<>();

    private record ItemInfo(String id, LoaiItem loai, String hanhTinh) {}
    private static final Map<String, ItemInfo> TEN_TO_INFO = new HashMap<>();

    static {
        // ===== CHISO & SMYC =====
        CHISO_ITEM.put("traidat:set_cam", Map.of(
            LoaiItem.AO,   new int[]{0,0,0,0,10,0,0,0,0,0,0,0,0},
            LoaiItem.QUAN, new int[]{0,0,0,0,0,0,0,0,0,5000,0,0,0},
            LoaiItem.GANG, new int[]{0,0,0,0,0,0,0,0,0,0,0,10,0},
            LoaiItem.GIAY, new int[]{0,0,0,0,0,0,0,0,0,0,200,0,0}
        ));
        SMYC_ITEM.put("traidat:set_cam", Map.of(
            LoaiItem.AO,   150_000L,
            LoaiItem.QUAN, 200_000L,
            LoaiItem.GANG, 180_000L,
            LoaiItem.GIAY, 175_000L
        ));

        // ===== TEN_TO_INFO =====

        // Đồ thường
        TEN_TO_INFO.put("Áo võ kame",   new ItemInfo("set_cam", LoaiItem.AO,   "traidat"));
        TEN_TO_INFO.put("Quần võ kame", new ItemInfo("set_cam", LoaiItem.QUAN, "traidat"));
        TEN_TO_INFO.put("Găng võ kame", new ItemInfo("set_cam", LoaiItem.GANG, "traidat"));
        TEN_TO_INFO.put("Giày võ kame", new ItemInfo("set_cam", LoaiItem.GIAY, "traidat"));
        TEN_TO_INFO.put("Rada cấp 1",   new ItemInfo("rada1",   LoaiItem.RADA, "all"));

        // Bông tai
        TEN_TO_INFO.put("Bông tai Porata",       new ItemInfo("bongtaic1", LoaiItem.BONGTAI, "all"));
        TEN_TO_INFO.put("Bông tai Porata Cấp 2", new ItemInfo("bongtaic2", LoaiItem.BONGTAI, "all"));
        TEN_TO_INFO.put("Bông tai Porata Cấp 3", new ItemInfo("bongtaic3", LoaiItem.BONGTAI, "all"));

        // Giáp luyện tập
        TEN_TO_INFO.put("Giáp luyện tập cấp 1", new ItemInfo("gltc1", LoaiItem.GIAPLUYENTAP, "all"));
        TEN_TO_INFO.put("Giáp luyện tập cấp 3", new ItemInfo("gltc3", LoaiItem.GIAPLUYENTAP, "all"));

        // Đeo lưng
        TEN_TO_INFO.put("Lưỡi Hái Thời Không", new ItemInfo("luoi_hai",        LoaiItem.DEOLUNG, "all"));
        TEN_TO_INFO.put("Cánh Dơi Dracula",     new ItemInfo("canh_doi",        LoaiItem.DEOLUNG, "all"));
        TEN_TO_INFO.put("Cánh Ác Quỷ",          new ItemInfo("canh_ac_quy",     LoaiItem.DEOLUNG, "all"));
        TEN_TO_INFO.put("Kiếm Thánh Z",         new ItemInfo("kiem",            LoaiItem.DEOLUNG, "all"));
        TEN_TO_INFO.put("Bó Hoa Hồng",          new ItemInfo("hoa",             LoaiItem.DEOLUNG, "all"));
        TEN_TO_INFO.put("Cánh Thiên Sứ",        new ItemInfo("canh_thien_su",   LoaiItem.DEOLUNG, "all"));
        TEN_TO_INFO.put("Cánh Thiên Thần",      new ItemInfo("canh_thien_than", LoaiItem.DEOLUNG, "all"));
        TEN_TO_INFO.put("Gậy Như Ý",            new ItemInfo("gay_nhu_y",       LoaiItem.DEOLUNG, "all"));
        TEN_TO_INFO.put("Lưỡi Hái VIP",         new ItemInfo("kiem_vip",        LoaiItem.DEOLUNG, "all"));
        TEN_TO_INFO.put("Dao Thời Không",        new ItemInfo("dao",             LoaiItem.DEOLUNG, "all"));

        // Huy hiệu
        TEN_TO_INFO.put("Huy hiệu Thiên Tử",             new ItemInfo("thien_tu",           LoaiItem.HUYHIEU, "all"));
        TEN_TO_INFO.put("Huy hiệu Trùm cuối",            new ItemInfo("trum_cuoi",          LoaiItem.HUYHIEU, "all"));
        TEN_TO_INFO.put("Huy hiệu Tối Thượng Trái Đất",  new ItemInfo("traidat_toi_thuong", LoaiItem.HUYHIEU, "all"));
        TEN_TO_INFO.put("Huy hiệu Tối Thượng Saiyan",    new ItemInfo("xayda_toi_thuong",   LoaiItem.HUYHIEU, "all"));

        // Aura
        TEN_TO_INFO.put("Aura Long Hồn Thượng Giới", new ItemInfo("tan_hon_rong_namek", LoaiItem.AURA, "all"));
        TEN_TO_INFO.put("Aura Tiểu Đội Trưởng",      new ItemInfo("tieu_doi_truong",    LoaiItem.AURA, "all"));

        // Cải trang
        TEN_TO_INFO.put("Cải trang Black Goku",        new ItemInfo("goku_black",      LoaiItem.CAITRANG, "all"));
        TEN_TO_INFO.put("Cải trang Super Black Goku",  new ItemInfo("goku_black_rose", LoaiItem.CAITRANG, "all"));
        TEN_TO_INFO.put("Cải trang Gohan Beast",       new ItemInfo("gohan_beast",     LoaiItem.CAITRANG, "all"));
        TEN_TO_INFO.put("Cải trang Broly",             new ItemInfo("broly_lssj",      LoaiItem.CAITRANG, "all"));
        TEN_TO_INFO.put("Cải trang Hộ Pháp Dạ Xoa",   new ItemInfo("xiao",            LoaiItem.CAITRANG, "all"));
        TEN_TO_INFO.put("Cải trang tiểu thư Kamisato", new ItemInfo("ayaka",           LoaiItem.CAITRANG, "all"));

        // Van bay
        TEN_TO_INFO.put("Thú cưỡi cực VIP", new ItemInfo("phuong_hoang_lua", LoaiItem.VANBAY, "all"));

        // Vé quay
        TEN_TO_INFO.put("Vé quay Rồng Thần", new ItemInfo("ve_quay_npc_haidang", LoaiItem.VE_QUAY_NPC_HAIDANG, "all"));

        // Phụ trợ
        TEN_TO_INFO.put("Sinh lực",    new ItemInfo("hp",          LoaiItem.PHUTRO, "all"));
        TEN_TO_INFO.put("Cường công",  new ItemInfo("dame",        LoaiItem.PHUTRO, "all"));
        TEN_TO_INFO.put("Linh khí",    new ItemInfo("ki",          LoaiItem.PHUTRO, "all"));
        TEN_TO_INFO.put("Bổ huyết",    new ItemInfo("bo_huyet",    LoaiItem.PHUTRO, "all"));
        TEN_TO_INFO.put("Bổ khí",      new ItemInfo("bo_khi",      LoaiItem.PHUTRO, "all"));
        TEN_TO_INFO.put("Cuồng nộ",    new ItemInfo("cuong_no",    LoaiItem.PHUTRO, "all"));
        TEN_TO_INFO.put("Giáp xên bọ hung", new ItemInfo("giap_xen", LoaiItem.PHUTRO, "all"));
        TEN_TO_INFO.put("Trứng đệ tử", new ItemInfo("trung_de_tu", LoaiItem.PHUTRO, "all"));

        // Nâng skill
        TEN_TO_INFO.put("Nâng Cấp Hóa Khỉ Khổng lồ",  new ItemInfo("khi",           LoaiItem.NANGSKILL, "all"));
        TEN_TO_INFO.put("Nâng Cấp Kỹ Năng Huýt Sáo",   new ItemInfo("huytsao",       LoaiItem.NANGSKILL, "all"));
        TEN_TO_INFO.put("Nâng Cấp Tái Tạo Năng Lượng",  new ItemInfo("ttnl",          LoaiItem.NANGSKILL, "all"));
        TEN_TO_INFO.put("Nâng cấp Biến Hình Đệ Tử",     new ItemInfo("skill4_de_khi", LoaiItem.NANGSKILL, "all"));
        TEN_TO_INFO.put("Nâng Cấp Kỹ Năng 1 Đệ Tử",    new ItemInfo("skill1_de",     LoaiItem.NANGSKILL, "all"));
        TEN_TO_INFO.put("Nâng Cấp Kỹ Năng 2 Đệ Tử",    new ItemInfo("skill2_de",     LoaiItem.NANGSKILL, "all"));
        TEN_TO_INFO.put("Nâng Cấp Kỹ Năng 3 Đệ Tử",    new ItemInfo("skill3_de",     LoaiItem.NANGSKILL, "all"));
        TEN_TO_INFO.put("Nâng Cấp Kỹ Năng 4 Đệ Tử",    new ItemInfo("skill4_de",     LoaiItem.NANGSKILL, "all"));

        // Ngọc rồng thường
        for (int i = 1; i <= 7; i++) {
            TEN_TO_INFO.put("Ngọc rồng " + i + " sao", new ItemInfo("nr"+i+"s", LoaiItem.NGOCRONG, "all"));
        }
        // Ngọc rồng đen
        for (int i = 1; i <= 7; i++) {
            TEN_TO_INFO.put("Ngọc rồng đen " + i + " sao", new ItemInfo("nr"+i+"sd", LoaiItem.NGOCRONG, "all"));
        }
    }

    // ===== METHODS =====

    public static Item taoItemThuong(String id, LoaiItem loaiItem, String hanhTinh) {
        String subStringItem = switch (loaiItem) {
            case AO   -> "ao";
            case QUAN -> "quan";
            case GANG -> "gang";
            case GIAY -> "giay";
            default   -> "";
        };

        if (!subStringItem.isEmpty()) {
            String key = hanhTinh + ":" + id;
            return new Item(
                id, layTenItem(hanhTinh, id, loaiItem), loaiItem,
                "vatpham/do/" + hanhTinh + "/" + id + "/" + subStringItem + ".png",
                MOTA_ITEM.get(loaiItem), 1,
                CHISO_ITEM.getOrDefault(key, Map.of()).get(loaiItem),
                hanhTinh,
                SMYC_ITEM.getOrDefault(key, Map.of()).getOrDefault(loaiItem, -1L),
                null, 0, 0, 0, -1
            );
        } else {
            int chiSo = Integer.parseInt(id.substring(4, 5));
            return new Item(
                id, "Rada cấp " + chiSo, LoaiItem.RADA,
                "vatpham/do/rada/" + id + ".png",
                MOTA_ITEM.get(LoaiItem.RADA), 1,
                new int[]{0,0,0,chiSo,0,0,0,0,0,0,0,0,0},
                "all", laySucManhYeuCauItemRada(chiSo),
                null, 0, 0, 0, -1
            );
        }
    }

    public static Item taoItemTuTen(String tenItem) {
        ItemInfo info = TEN_TO_INFO.get(tenItem);
        if (info == null) {
            Gdx.app.error("ItemThuongXuLi", "Không tìm thấy item: " + tenItem);
            return null;
        }

        switch (info.loai()) {
            case BONGTAI: {
                String path = "vatpham/vatphamgame/bongtai/" + info.id() + ".png";
                String mota = info.id().equals("bongtaic1")
                    ? "Sử dụng để hợp thể với đệ tử"
                    : info.id().equals("bongtaic2")
                    ? "Sử dụng để hợp thể với đệ tử và tăng tổng 10% chỉ số"
                    : "Sử dụng để hợp thể với đệ tử và tăng tổng 20% chỉ số";
                long gia = info.id().equals("bongtaic1") ? 1_500_000L
                    : info.id().equals("bongtaic2") ? 150_000_000L : 1_500_000_000L;
                return new Item(info.id(), tenItem, LoaiItem.BONGTAI, path, mota, 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0}, "all", gia, null, 0, 0, 0, -1);
            }
            case GIAPLUYENTAP: {
                boolean c1 = info.id().equals("glt_c1");
                return new Item(info.id(), tenItem, LoaiItem.GIAPLUYENTAP,
                    "vatpham/vatphamgame/giapluyentap/" + info.id() + ".png",
                    c1 ? "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra nếu sẽ tăng sức đánh 10% và Crit 15%, ST Crit 30%"
                        : "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra sẽ tăng sức đánh 30% và Crit 15%, ST Crit 30%",
                    1, new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0}, "all",
                    c1 ? 10_000_000L : 1_000_000_000L, null, c1 ? 0 : 3, 0, 0, 0);
            }
            case DEOLUNG: {
                Map<String, Object[]> deolungData = new HashMap<>();
                deolungData.put("luoi_hai",        new Object[]{"Lưỡi hái mang sức mạnh xé tan thời không. [Hiệu ứng] Khi trang bị cùng Black Goku Rose: +4% Sức đánh, HP, KI.", new int[]{0,0,0,0,0,0,1,1,1,0,0,0,0}});
                deolungData.put("canh_doi",         new Object[]{"Đôi cánh dơi huyền bí. [Hiệu ứng] Khi HP ≤ 50%: +10% Chí mạng.", new int[]{0,0,0,1,0,0,0,0,1,0,0,0,0}});
                deolungData.put("canh_ac_quy",      new Object[]{"Cánh ác quỷ tỏa ra khí tức u tối. [Hiệu ứng] KI > 80%: +5% Chí mạng.", new int[]{0,0,0,1,0,1,0,0,0,0,0,0,0}});
                deolungData.put("kiem",             new Object[]{"Thanh kiếm huyền thoại của Future Trunks. [Hiệu ứng] Khi HP ≤ 40%: +15% Sức đánh.", new int[]{0,0,0,0,0,1,0,0,1,0,0,0,0}});
                deolungData.put("hoa",              new Object[]{"Bó hoa chứa nguồn sức mạnh huyền ảo.", new int[]{0,0,0,0,0,0,1,0,1,0,0,0,1}});
                deolungData.put("canh_thien_su",    new Object[]{"Đôi cánh của thiên sứ, thuần khiết và sáng ngời.", new int[]{0,0,0,0,0,0,1,0,0,0,0,0,1}});
                deolungData.put("canh_thien_than",  new Object[]{"Đôi cánh tỏa sáng rực rỡ của thiên thần.", new int[]{0,0,0,1,0,0,0,1,1,0,0,0,0}});
                deolungData.put("gay_nhu_y",        new Object[]{"Gậy Như Ý của Son Goku.", new int[]{0,0,0,0,0,0,10,10,10,0,0,0,0}});
                deolungData.put("kiem_vip",         new Object[]{"Lưỡi hái mang sức mạnh xé tan thời không.", new int[]{0,0,0,0,0,0,20,0,0,0,0,0,10}});
                deolungData.put("dao",              new Object[]{"Lưỡi hái mang sức mạnh xé tan thời không.", new int[]{0,0,0,0,0,0,20,0,0,0,0,0,10}});

                Object[] d = deolungData.get(info.id());
                if (d == null) return null;
                return new Item(info.id(), tenItem, LoaiItem.DEOLUNG,
                    "vatpham/vatphamgame/deo_lung/" + info.id() + "/icon.png",
                    (String) d[0], 1, (int[]) d[1], "all", 10_000_000L, null, 0, 0, 0, -1);
            }
            case HUYHIEU: {
                Map<String, int[]> hhData = new HashMap<>();
                hhData.put("thien_tu",           new int[]{0,0,0,0,0,0,10,10,10,0,0,0,0});
                hhData.put("trum_cuoi",          new int[]{0,0,0,5,0,5,0,0,10,0,0,0,0});
                hhData.put("traidat_toi_thuong", new int[]{0,0,0,10,0,10,0,0,0,0,0,0,0});
                hhData.put("xayda_toi_thuong",   new int[]{0,0,0,0,0,0,20,0,0,0,0,0,10});

                int[] chiso = hhData.get(info.id());
                if (chiso == null) return null;
                return new Item(info.id(), tenItem, LoaiItem.HUYHIEU,
                    "vatpham/vatphamgame/huy_hieu/" + info.id() + "/icon.png",
                    "Huy hiệu đặc biệt", 1, chiso, "all", 10_000_000L, null, 0, 0, 0, -1);
            }
            case AURA: {
                Map<String, int[]> auraData = new HashMap<>();
                auraData.put("tan_hon_rong_namek", new int[]{0,0,0,8,0,8,8,0,0,0,0,0,8});
                auraData.put("tieu_doi_truong",    new int[]{0,0,0,0,0,0,10,10,10,0,0,0,0});

                int[] chiso = auraData.get(info.id());
                if (chiso == null) return null;
                return new Item(info.id(), tenItem, LoaiItem.AURA,
                    "vatpham/vatphamgame/aura/" + info.id() + "/icon.png",
                    "Aura đặc biệt", 1, chiso, "all", 10_000_000L, null, 0, 0, 0, -1);
            }
            case CAITRANG: {
                Map<String, int[]> ctData = new HashMap<>();
                ctData.put("goku_black",      new int[]{0,0,0,0,0,0,35,35,35,0,0,0,0});
                ctData.put("goku_black_rose", new int[]{0,0,0,0,0,0,45,45,45,0,0,0,0});
                ctData.put("gohan_beast",     new int[]{0,0,0,10,0,0,30,0,40,0,0,0,0});
                ctData.put("broly_lssj",      new int[]{0,0,0,50,0,100,100,0,100,0,0,0,0});
                ctData.put("xiao",            new int[]{0,0,0,50,0,100,100,0,100,0,0,0,0});
                ctData.put("ayaka",           new int[]{0,0,0,0,0,0,40,50,60,0,0,0,0});

                Map<String, Long> ctGia = new HashMap<>();
                ctGia.put("goku_black",      40_000_000_000L);
                ctGia.put("goku_black_rose", 40_000_000_000L);
                ctGia.put("gohan_beast",     10_000_000L);
                ctGia.put("broly_lssj",      9_999_999_999L);
                ctGia.put("xiao",            9_999_999_999L);
                ctGia.put("ayaka",           9_999_999_999L);

                int[] chiso = ctData.get(info.id());
                if (chiso == null) return null;
                return new Item(info.id(), tenItem, LoaiItem.CAITRANG,
                    "nhanvat/caitrang/" + info.id() + "/daudung.png",
                    "Cải trang đặc biệt", 1, chiso,
                    "all", ctGia.getOrDefault(info.id(), 10_000_000L), null, 0, 0, 0, -1);
            }
            case VANBAY:
                return new Item("phuong_hoang_lua", tenItem, LoaiItem.VANBAY,
                    "vatpham/vanbay/phuong_hoang_lua/phuonghoanglua.png",
                    "Dùng để bay và hồi phục HP, KI", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0}, "all", 1_500_000L, null, 0, 0, 0, -1);

            case VE_QUAY_NPC_HAIDANG:
                return new Item("ve_quay_npc_haidang", tenItem, LoaiItem.VE_QUAY_NPC_HAIDANG,
                    "vatpham/vatphamgame/ve_quay_npc_haidang/vequay.png",
                    "Vé quay ẩn chứa tiềm năng vô hạn.", 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0}, "all", 0, null, 0, 0, 0, -1);

            case PHUTRO: {
                Map<String, String[]> ptData = new HashMap<>();
                ptData.put("hp",          new String[]{"vatpham/vatphamgame/phu_tro/hp.png",       "Trong vòng tối đa 2 phút +10% HP"});
                ptData.put("dame",        new String[]{"vatpham/vatphamgame/phu_tro/dame.png",     "Trong vòng tối đa 2 phút +10% Sức đánh gốc"});
                ptData.put("ki",          new String[]{"vatpham/vatphamgame/phu_tro/ki.png",       "Trong vòng tối đa 2 phút +10% KI"});
                ptData.put("bo_huyet",    new String[]{"vatpham/vatphamgame/phu_tro/bo_huyet.png", "Trong vòng tối đa 10 phút +100% HP"});
                ptData.put("bo_khi",      new String[]{"vatpham/vatphamgame/phu_tro/bo_khi.png",   "Trong vòng tối đa 10 phút +100% KI"});
                ptData.put("cuong_no",    new String[]{"vatpham/vatphamgame/phu_tro/cuong_no.png", "Trong vòng tối đa 10 phút +100% Sức đánh gốc"});
                ptData.put("giap_xen",    new String[]{"vatpham/vatphamgame/phu_tro/giap_xen.png", "Trong vòng tối đa 10 phút giảm 50% sát thương"});
                ptData.put("trung_de_tu", new String[]{"vatpham/vatphamgame/phu_tro/trung_de_tu.png", "Sử dụng vật phẩm có thể giúp người chơi sở hữu đệ tử."});

                String[] d = ptData.get(info.id());
                if (d == null) return null;
                return new Item(info.id(), tenItem, LoaiItem.PHUTRO, d[0], d[1], 1,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0}, "all", 0, null, 0, 0, 0, -1);
            }
            case NANGSKILL: {
                Map<String, String> nsPath = new HashMap<>();
                nsPath.put("khi",           "vatpham/vatphamgame/nang_skill/khi.png");
                nsPath.put("huytsao",       "vatpham/vatphamgame/nang_skill/huytsao.png");
                nsPath.put("ttnl",          "vatpham/vatphamgame/nang_skill/ttnl.png");
                nsPath.put("skill4_de_khi", "kynang/iconkynang/xayda/skill4_xayda.png");
                nsPath.put("skill1_de",     "vatpham/vatphamgame/nang_skill_de_tu/skill1.png");
                nsPath.put("skill2_de",     "vatpham/vatphamgame/nang_skill_de_tu/skill2.png");
                nsPath.put("skill3_de",     "vatpham/vatphamgame/nang_skill_de_tu/skill3.png");
                nsPath.put("skill4_de",     "vatpham/vatphamgame/nang_skill_de_tu/skill4.png");

                String path = nsPath.get(info.id());
                if (path == null) return null;
                return new Item(info.id(), tenItem, LoaiItem.NANGSKILL, path,
                    "Sử dụng để nâng cấp kỹ năng.", 8,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0}, "all", 1_500_000L, null, 0, 0, 0, -1);
            }
            case NGOCRONG: {
                // id dạng "nr1s" hoặc "nr1sd"
                boolean den = info.id().contains("sd");
                String soSao = den ? info.id().substring(2, 3) : info.id().substring(2, 3);
                String path = den
                    ? "vatpham/vatphamgame/ngocrongden/" + info.id() + ".png"
                    : "vatpham/vatphamgame/ngocrong/" + info.id() + ".png";
                String mota = den
                    ? "Thu thập đủ 7 viên để triệu hồi Rồng Thần Hắc Ám."
                    : "Thu thập để ước rồng thần";
                return new Item(info.id(), tenItem, LoaiItem.NGOCRONG, path, mota, 99,
                    new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0}, "all", 0, null, 0, 0, 0, -1);
            }
            default:
                return taoItemThuong(info.id(), info.loai(), info.hanhTinh());
        }
    }

    private static long laySucManhYeuCauItemRada(int chiSo) {
        return switch (chiSo) {
            case 1  -> 1_300L;
            case 2  -> 13_000L;
            case 3  -> 28_000L;
            case 4  -> 86_000L;
            case 5  -> 260_000L;
            case 6  -> 780_000L;
            case 7  -> 2_300_000L;
            case 8  -> 7_000_000L;
            case 9  -> 42_000_000L;
            case 10 -> 200_000_000L;
            case 11 -> 1_000_000_000L;
            case 12 -> 1_500_000_000L;
            default -> -1;
        };
    }

    private static String layTenItem(String hanhTinh, String id, LoaiItem loaiItem) {
        if ("traidat".equals(hanhTinh) && "set_cam".equals(id)) {
            return switch (loaiItem) {
                case AO   -> "Áo võ kame";
                case QUAN -> "Quần võ kame";
                case GANG -> "Găng võ kame";
                case GIAY -> "Giày võ kame";
                default   -> null;
            };
        }
        return null;
    }
}
