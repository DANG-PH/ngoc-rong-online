package com.dang.dragonboy.nhan_vat;

import com.badlogic.gdx.graphics.Texture;

import java.util.Map;

public class NhanVatXuLy {

    // ==== CAITRANG (dạng cố định) ====
    public static NhanVatCauHinh loadCaiTrang(String tenCaiTrang) {
        String path = "nhanvat/caitrang/" + tenCaiTrang + "/";

        String avt = path + "avt.png";

        Texture dau_dung = new Texture(path + "dung.png");
        Texture dau_chay = new Texture(path + "chay.png");

        Texture than_dung = new Texture(path + "thandung.png");
        Texture than_nhay = new Texture(path + "thannhay.png");
        Texture than_roi = new Texture(path + "thanroi.png");
        Texture[] than_chay = {
            new Texture(path + "thanchay0.png"),
            new Texture(path + "thanchay1.png"),
            new Texture(path + "thanchay2.png"),
            new Texture(path + "thanchay3.png"),
            new Texture(path + "thanchay4.png")
        };
        Texture than_bay = new Texture(path + "thanbay.png");

        Texture chan_dung = new Texture(path + "chandung.png");
        Texture chan_nhay = new Texture(path + "channhay.png");
        Texture chan_roi = new Texture(path + "chanroi.png");
        Texture[] chan_chay = {
            new Texture(path + "chanchay0.png"),
            new Texture(path + "chanchay1.png"),
            new Texture(path + "chanchay2.png"),
            new Texture(path + "chanchay3.png"),
            new Texture(path + "chanchay4.png")
        };
        Texture chan_bay = new Texture(path + "chanbay.png");

        Map<TrangThai, DoLechModular> lech = CaiTrangOffset.getOffset(tenCaiTrang);

        return new NhanVatCauHinh(
            dau_dung, dau_chay,
            than_dung, than_nhay, than_roi, than_chay,
            chan_dung, chan_nhay, chan_roi, chan_chay,
            than_bay, chan_bay,
            lech,
            avt
        );
    }

    // ==== AVATAR + SET (dạng modular linh hoạt) ====
    public static NhanVatCauHinh loadAvatarSet(String hanhtinh ,String avatar, String set) {
        String basePath = "nhanvat/" + hanhtinh + "/";
        String setPath = basePath + "do/" + set + "/";
        String avatarPath = basePath + "avatar/" + avatar + "/";

        String avt = avatarPath + "avt.png";

        Texture dau_dung = new Texture(avatarPath + "dung.png");
        Texture dau_chay = new Texture(avatarPath + "chay.png");

        Texture than_dung = new Texture(setPath + "thandung.png");
        Texture than_nhay = new Texture(setPath + "thannhay.png");
        Texture than_roi = new Texture(setPath + "thanroi.png");
        Texture[] than_chay = {
            new Texture(setPath + "thanchay0.png"),
            new Texture(setPath + "thanchay1.png"),
            new Texture(setPath + "thanchay2.png"),
            new Texture(setPath + "thanchay3.png"),
            new Texture(setPath + "thanchay4.png")
        };
        Texture than_bay = new Texture(setPath + "thanbay.png");

        Texture chan_dung = new Texture(setPath + "chandung.png");
        Texture chan_nhay = new Texture(setPath + "channhay.png");
        Texture chan_roi = new Texture(setPath + "chanroi.png");
        Texture[] chan_chay = {
            new Texture(setPath + "chanchay0.png"),
            new Texture(setPath + "chanchay1.png"),
            new Texture(setPath + "chanchay2.png"),
            new Texture(setPath + "chanchay3.png"),
            new Texture(setPath + "chanchay4.png")
        };
        Texture chan_bay = new Texture(setPath + "chanbay.png");

        Map<TrangThai, DoLechModular> lech = AvatarDoOffset.getOffset(avatar, set);

        return new NhanVatCauHinh(
            dau_dung, dau_chay,
            than_dung, than_nhay, than_roi, than_chay,
            chan_dung, chan_nhay, chan_roi, chan_chay,
            than_bay, chan_bay,
            lech,
            avt
        );
    }

    // ==== GỌI TỰ ĐỘNG BẰNG ID CHUẨN HÓA ====
    public static NhanVatCauHinh xuly_id(String id) {
        if (id.startsWith("caitrang_")) {
            return loadCaiTrang(id.replace("caitrang_", ""));
        } else if (id.startsWith("avatar_")) {
            // Ví dụ: avatar_traidat+set_cam
            String[] parts = id.substring(7).split("\\+");
            if (parts.length == 3) {
                return loadAvatarSet(parts[0], parts[1] , parts[2]);
            }
        }
        return null;
    }
}
