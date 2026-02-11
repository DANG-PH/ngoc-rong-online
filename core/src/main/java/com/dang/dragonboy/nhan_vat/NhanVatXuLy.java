package com.dang.dragonboy.nhan_vat;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class NhanVatXuLy {
    public static boolean dangMacCaiTrang = false;
    public static boolean dangMacAvatar = false;
    private static final Map<Texture, String> PathTexture = new HashMap<>();

    // ==== CAITRANG (dạng cố định) ====
    public static NhanVatCauHinh loadCaiTrang(String tenCaiTrang) {
        String path = "nhanvat/caitrang/" + tenCaiTrang + "/";
        String avt = path + "avt.png";
        String itemdo = "vatpham/do/";
        String itemvanbay = "vatpham/vanbay/";
        String itemglt = "vatpham/vatphamgame/giapluyentap/";

        Texture dau_dung = loadTexture(path + "daudung.png");
        Texture dau_chay = loadTexture(path + "dauchay.png");

        Texture than_dung = loadTexture(path + "thandung.png");
        Texture than_nhay = loadTexture(path + "thannhay.png");
        Texture than_roi = loadTexture(path + "thanroi.png");
        Texture[] than_chay = {
            loadTexture(path + "thanchay (1).png"),
            loadTexture(path + "thanchay (2).png"),
            loadTexture(path + "thanchay (3).png"),
            loadTexture(path + "thanchay (4).png"),
            loadTexture(path + "thanchay (5).png")
        };
        Texture than_bay = loadTexture(path + "thannhay.png");

        Texture chan_dung = loadTexture(path + "chandung.png");
        Texture chan_nhay = loadTexture(path + "channhay.png");
        Texture chan_roi = loadTexture(path + "chanroi.png");
        Texture[] chan_chay = {
            loadTexture(path + "chanchay (1).png"),
            loadTexture(path + "chanchay (2).png"),
            loadTexture(path + "chanchay (3).png"),
            loadTexture(path + "chanchay (4).png"),
            loadTexture(path + "chanchay (5).png")
        };
        Texture chan_bay = loadTexture(path + "chanbay.png");

        Texture chan_gong = loadTexture(path + "changong.png");
        Texture than_thu = loadTexture(path + "thanthu.png");

        Map<TrangThai, List<DoLechModular>> lech = CaiTrangOffset.getOffset(tenCaiTrang);
        if (
            !tenCaiTrang.equals("hop_the_thuong_traidat") &&
                !tenCaiTrang.equals("bong_tai_1_traidat") &&
                !tenCaiTrang.equals("bong_tai_2_traidat") &&
                !tenCaiTrang.equals("bong_tai_3_traidat") &&
                !tenCaiTrang.equals("hop_the_thuong_xayda") &&
                !tenCaiTrang.equals("bong_tai_1_xayda") &&
                !tenCaiTrang.equals("bong_tai_2_xayda") &&
                !tenCaiTrang.equals("bong_tai_3_xayda") &&
                !tenCaiTrang.equals("hop_the_thuong_namek") &&
                !tenCaiTrang.equals("bong_tai_1_namek") &&
                !tenCaiTrang.equals("bong_tai_2_namek") &&
                !tenCaiTrang.equals("bong_tai_3_namek") &&
                !tenCaiTrang.equals("khi_1") &&
                !tenCaiTrang.equals("khi_2") &&
                !tenCaiTrang.equals("khi_3") &&
                !tenCaiTrang.equals("khi_4") &&
                !tenCaiTrang.equals("khi_5") &&
                !tenCaiTrang.equals("khi_6") &&
                !tenCaiTrang.equals("khi_7") &&
                !tenCaiTrang.equals("khi_8")
        ) {
            dangMacCaiTrang = true;
            dangMacAvatar = false;
        }
        return new NhanVatCauHinh(
            dau_dung, dau_chay,
            than_dung, than_nhay, than_roi, than_chay,
            chan_dung, chan_nhay, chan_roi, chan_chay,
            than_bay, chan_bay,
            chan_gong,than_thu,
            lech,
            avt,
            null,null,null,null,null,null,null,null
        );
    }

    // ==== AVATAR + SET (dạng modular linh hoạt) ====
    public static NhanVatCauHinh loadAvatarSet(String hanhtinh ,String avatar, String ao, String quan) {
        String basePath = "nhanvat/" + hanhtinh + "/";
        String aoPath = basePath + "do/" + ao + "/";
        String quanPath = basePath + "do/" + quan + "/";
        String avatarPath = basePath + "avatar/" + avatar + "/";
        String checkDau = avatar;
        String avt = avatarPath + "avt.png";
        String itemdo = "vatpham/do/";
        String itemvanbay = "vatpham/vanbay/";
        String itemglt = "vatpham/vatphamgame/giapluyentap/";

        Texture dau_dung = loadTexture(avatarPath + "daudung.png");
        Texture dau_chay = loadTexture(avatarPath + "dauchay.png");

        Texture than_dung = loadTexture(aoPath + "thandung.png");
        Texture than_nhay = loadTexture(aoPath + "thannhay.png");
        Texture than_roi = loadTexture(aoPath + "thanroi.png");
        Texture[] than_chay = {
            loadTexture(aoPath + "thanchay (1).png"),
            loadTexture(aoPath + "thanchay (2).png"),
            loadTexture(aoPath + "thanchay (3).png"),
            loadTexture(aoPath + "thanchay (4).png"),
            loadTexture(aoPath + "thanchay (5).png")
        };
        Texture than_bay = loadTexture(aoPath + "thannhay.png");

        Texture chan_dung = loadTexture(quanPath + "chandung.png");
        Texture chan_nhay = loadTexture(quanPath + "channhay.png");
        Texture chan_roi = loadTexture(quanPath + "chanroi.png");
        Texture[] chan_chay = {
            loadTexture(quanPath + "chanchay (1).png"),
            loadTexture(quanPath + "chanchay (2).png"),
            loadTexture(quanPath + "chanchay (3).png"),
            loadTexture(quanPath + "chanchay (4).png"),
            loadTexture(quanPath + "chanchay (5).png")
        };
        Texture chan_bay = loadTexture(quanPath + "chanbay.png");

        Texture chan_gong = loadTexture(quanPath + "changong.png");
        Texture than_thu = loadTexture(aoPath + "thanthu.png");

        Map<TrangThai, List<DoLechModular>> lech = AvatarDoOffset.getOffset(avatar, ao, quan);
        dangMacCaiTrang = false;
        if (!checkDau.contains("_base")) {
            dangMacAvatar = true;
        } else {
            dangMacAvatar = false;
        }
        return new NhanVatCauHinh(
            dau_dung, dau_chay,
            than_dung, than_nhay, than_roi, than_chay,
            chan_dung, chan_nhay, chan_roi, chan_chay,
            than_bay, chan_bay,
            chan_gong,than_thu,
            lech,
            avt,
            null,null,null,null,null,null,null,null
        );
    }

    // ==== GỌI TỰ ĐỘNG BẰNG ID CHUẨN HÓA ====
    public static NhanVatCauHinh xuly_id(String id) {
        if (id.startsWith("caitrang_")) {
            return loadCaiTrang(id.replace("caitrang_", ""));
        } else if (id.startsWith("avatar_")) {
            // Ví dụ: avatar_traidat+goku_base+set_cam+set_huy_diet
            String[] parts = id.substring(7).split("\\+");
            if (parts.length == 4) {
                return loadAvatarSet(parts[0], parts[1] , parts[2], parts[3]);
            }
        }
        return null;
    }

    public static boolean getDangMacCaiTrang(){
        return dangMacCaiTrang;
    }
    public static boolean getDangMacAvatar(){
        return dangMacAvatar;
    }
    public static void setDangMacAvatar(boolean dangmacavt){
        dangMacAvatar = dangmacavt;
    }
    public static void setDangMacCaiTrang(boolean dangmacct){
        dangMacCaiTrang = dangmacct;
    }

    private static Texture loadTexture(String path) {
        Texture tex = new Texture(path);
        PathTexture.put(tex, path);
        return tex;
    }

    public static String getPathFromTexture(Texture texture) {
        return PathTexture.get(texture);
    }
}
