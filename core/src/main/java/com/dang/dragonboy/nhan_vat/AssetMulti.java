package com.dang.dragonboy.nhan_vat;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.dang.dragonboy.nhan_vat.van_bay.VanBayCauHinh;

public class AssetMulti {

    // ===== CACHE =====
    private static final Map<String, NhanVatCauHinh> SKIN_CACHE = new HashMap<>();
    private static final Map<String, Texture> TEXTURE_CACHE = new HashMap<>();
    private static final Map<String, VanBayCauHinh> VAN_BAY_CACHE = new HashMap<>();

    // =====================================================
    // ===================== PUBLIC API ====================
    // =====================================================

    /** Lấy cấu hình skin nhân vật */
    public static NhanVatCauHinh getSkin(String skinId) {
        return SKIN_CACHE.computeIfAbsent(skinId, AssetMulti::loadSkin);
    }

    /** Lấy texture (BẮT BUỘC dùng hàm này) */
    public static Texture getTexture(String path) {
        return TEXTURE_CACHE.computeIfAbsent(path, Texture::new);
    }

    /** Lấy cấu hình ván bay */
    public static VanBayCauHinh getVanBay(String ten) {
        return VAN_BAY_CACHE.computeIfAbsent(ten, AssetMulti::loadVanBay);
    }

    // =====================================================
    // ==================== INTERNAL =======================
    // =====================================================

    private static NhanVatCauHinh loadSkin(String skinId) {
        // Tái sử dụng logic cũ
        return NhanVatXuLy.xuly_id(skinId);
    }

    private static VanBayCauHinh loadVanBay(String ten) {
        Texture[] frames;

        if (!"base".equals(ten)) {
            frames = new Texture[]{
                getTexture("vatpham/vanbay/" + ten + "/" + ten + "1.png"),
                getTexture("vatpham/vanbay/" + ten + "/" + ten + "2.png"),
                getTexture("vatpham/vanbay/" + ten + "/" + ten + "3.png"),
                getTexture("vatpham/vanbay/" + ten + "/" + ten + "4.png")
            };
        } else {
            frames = new Texture[]{
                getTexture("hieuung/hieuunggame/aura_bay/1.png"),
                getTexture("hieuung/hieuunggame/aura_bay/2.png"),
                getTexture("hieuung/hieuunggame/aura_bay/3.png"),
                getTexture("hieuung/hieuunggame/aura_bay/4.png")
            };
        }

        switch (ten) {
            case "phuong_hoang_lua":
                return new VanBayCauHinh(frames, 0.5f, false, 0f, -40f);

            case "candauvan":
                return new VanBayCauHinh(frames, 0.1f, false, -1f, -20f);

            case "base":
                return new VanBayCauHinh(frames, 0.5f, false, -50f, -20f);

            default:
                throw new IllegalArgumentException("Không tồn tại ván bay: " + ten);
        }
    }

    // =====================================================
    // ===================== DISPOSE =======================
    // =====================================================

    public static void disposeAll() {
        TEXTURE_CACHE.values().forEach(Texture::dispose);
        TEXTURE_CACHE.clear();
        VAN_BAY_CACHE.clear();
        SKIN_CACHE.clear();
    }
}
