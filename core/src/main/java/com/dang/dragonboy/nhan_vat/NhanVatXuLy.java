package com.dang.dragonboy.nhan_vat;

import com.badlogic.gdx.graphics.Texture;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class NhanVatXuLy {
    public static boolean dangMacCaiTrang = false;
    public static boolean dangMacAvatar = false;
    // ==== CAITRANG (dạng cố định) ====
    public static NhanVatCauHinh loadCaiTrang(String tenCaiTrang) {
        String path = "nhanvat/caitrang/" + tenCaiTrang + "/";
        String avt = path + "avt.png";
        String itemdo = "vatpham/do/";
        String itemvanbay = "vatpham/vanbay/";
        String itemglt = "vatpham/vatphamgame/giapluyentap/";

//        Texture itemao = new Texture(itemdo+"aoquan/traidat/set_cam/ao.png");
//        Texture itemquan = new Texture(itemdo+"aoquan/traidat/set_cam/quan.png");
//        Texture gang = new Texture(itemdo+"gang/traidat/gang1.png");
//        Texture giay = new Texture(itemdo+"giay/traidat/giay1.png");
//        Texture rada = new Texture(itemdo+"rada/rada1.png");
//        Texture iconct = new Texture(path + "daudung.png");
//        Texture giaplt = new Texture(itemglt+"gltc1.png");
        Texture vanbay = new Texture(itemvanbay+"candauvan/candauvan.png");
        Texture dau_dung = new Texture(path + "daudung.png");
        Texture dau_chay = new Texture(path + "dauchay.png");

        Texture than_dung = new Texture(path + "thandung.png");
        Texture than_nhay = new Texture(path + "thannhay.png");
        Texture than_roi = new Texture(path + "thanroi.png");
        Texture[] than_chay = {
            new Texture(path + "thanchay (1).png"),
            new Texture(path + "thanchay (2).png"),
            new Texture(path + "thanchay (3).png"),
            new Texture(path + "thanchay (4).png"),
            new Texture(path + "thanchay (5).png")
        };
        Texture than_bay = new Texture(path + "thanbay.png");

        Texture chan_dung = new Texture(path + "chandung.png");
        Texture chan_nhay = new Texture(path + "channhay.png");
        Texture chan_roi = new Texture(path + "chanroi.png");
        Texture[] chan_chay = {
            new Texture(path + "chanchay (1).png"),
            new Texture(path + "chanchay (2).png"),
            new Texture(path + "chanchay (3).png"),
            new Texture(path + "chanchay (4).png"),
            new Texture(path + "chanchay (5).png")
        };
        Texture chan_bay = new Texture(path + "chanbay.png");

        Map<TrangThai, List<DoLechModular>> lech = CaiTrangOffset.getOffset(tenCaiTrang);
        if (!tenCaiTrang.equals("vegito")) {
            dangMacCaiTrang = true;
            dangMacAvatar = false;
        }
        return new NhanVatCauHinh(
            dau_dung, dau_chay,
            than_dung, than_nhay, than_roi, than_chay,
            chan_dung, chan_nhay, chan_roi, chan_chay,
            than_bay, chan_bay,
            lech,
            avt,
            null,null,null,null,null,null,null,vanbay
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

//        Texture itemao = new Texture(itemdo+"aoquan/traidat/set_cam/ao.png");
//        Texture itemquan = new Texture(itemdo+"aoquan/traidat/set_cam/quan.png");
//        Texture gang = new Texture(itemdo+"gang/traidat/gang1.png");
//        Texture giay = new Texture(itemdo+"giay/traidat/giay1.png");
//        Texture rada = new Texture(itemdo+"rada/rada1.png");
//        Texture iconct = new Texture(avatarPath + "daudung.png");
//        Texture giaplt = new Texture(itemglt+"gltc1.png");
        Texture vanbay = new Texture(itemvanbay+"candauvan/candauvan.png");

        Texture dau_dung = new Texture(avatarPath + "daudung.png");
        Texture dau_chay = new Texture(avatarPath + "dauchay.png");

        Texture than_dung = new Texture(aoPath + "thandung.png");
        Texture than_nhay = new Texture(aoPath + "thannhay.png");
        Texture than_roi = new Texture(aoPath + "thanroi.png");
        Texture[] than_chay = {
            new Texture(aoPath + "thanchay (1).png"),
            new Texture(aoPath + "thanchay (2).png"),
            new Texture(aoPath + "thanchay (3).png"),
            new Texture(aoPath + "thanchay (4).png"),
            new Texture(aoPath + "thanchay (5).png")
        };
        Texture than_bay = new Texture(aoPath + "thanbay.png");

        Texture chan_dung = new Texture(quanPath + "chandung.png");
        Texture chan_nhay = new Texture(quanPath + "channhay.png");
        Texture chan_roi = new Texture(quanPath + "chanroi.png");
        Texture[] chan_chay = {
            new Texture(quanPath + "chanchay (1).png"),
            new Texture(quanPath + "chanchay (2).png"),
            new Texture(quanPath + "chanchay (3).png"),
            new Texture(quanPath + "chanchay (4).png"),
            new Texture(quanPath + "chanchay (5).png")
        };
        Texture chan_bay = new Texture(quanPath + "chanbay.png");

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
            lech,
            avt,
            null,null,null,null,null,null,null,vanbay
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
}
