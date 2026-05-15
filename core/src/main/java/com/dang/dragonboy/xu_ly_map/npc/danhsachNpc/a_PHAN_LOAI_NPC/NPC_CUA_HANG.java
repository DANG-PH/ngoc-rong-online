package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.a_PHAN_LOAI_NPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.websocket.GameSocket;
import com.dang.dragonboy.websocket.GameSocketGo;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.*;

import java.util.ArrayList;
import java.util.Iterator;

public class NPC_CUA_HANG {
    // ==========================================================
    // Cấu hình fade
    // ==========================================================
    private static final float CHU_KY_FADE = 4.0f;     // 3 giây/chu kỳ
    private static final float THOI_GIAN_HIEN = 1.2f;  // 1.2s mỗi thông tin được hiện rõ
    private static final float THOI_GIAN_FADE = 0.4f;  // 0.3s chuyển đổi

    public static void render_item(boolean benTrai, SpriteBatch batch,float width, VeHUD veHUD, ArrayList<Item> danhSachItem, int oChiSoCanXet) {
        // ====== XÓA ITEM HẾT HẠN KHỎI LIST TRƯỚC KHI RENDER ======
        long serverNow = System.currentTimeMillis() + GameSocketGo.clockOffset;
        Iterator<Item> it = danhSachItem.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item == null) {
                it.remove();
                continue;
            }
            ShopItemTime st = admin_thanhle.ShopItemTime.get(item.tmpId);
            if (st != null && st.endAt != null && st.endAt <= serverNow) {
                admin_thanhle.ShopItemTime.remove(item.tmpId);
                it.remove();
            }
        }

        float viewY = 35, viewHeight = 444 - 35;
        float viewX = benTrai ? 0 : 1020-350;
        int khoangCachItem = 49;
        int tongSoRuongDo = danhSachItem.size();
        if (benTrai) {
            veHUD.maxScrollTrai = Math.max(0, tongSoRuongDo * khoangCachItem - viewHeight);
        } else {
            veHUD.maxScrollPhai = Math.max(0, tongSoRuongDo * khoangCachItem - viewHeight);
        }

        float scrollMax = benTrai ? veHUD.maxScrollTrai : veHUD.maxScrollPhai;

        batch.flush();
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor((int) viewX, (int) viewY, 350, (int) viewHeight);
        float startY = viewY + viewHeight - khoangCachItem + (benTrai ? veHUD.scrollYTrai : veHUD.scrollYPhai);

        for (int i = 0; i < tongSoRuongDo; i++) {
            float y = startY - i * khoangCachItem;

            // Vẽ ô nền
            Texture tex = (oChiSoCanXet == i) ? veHUD.hanh_trang_dang_mac_click : veHUD.hanh_trang_dang_mac;
            Texture texNen = (oChiSoCanXet == i) ? veHUD.nenTrangNgaClick : veHUD.nenTrangNga;
            batch.draw(tex, 3 + viewX, y, width, 50);
            batch.draw(texNen, (width/4.91f)+10f + viewX, y, 347-(width/4.91f)-12f, 50);

            // Vẽ item
            if (i < danhSachItem.size()) {
                Item item = danhSachItem.get(i);
                if (item != null) {
                    veIconItem(batch,width,veHUD, item,viewX, y);
                    veTenSoLuong(batch,width,veHUD, item,viewX, y);
                    veChiTietItem(batch,width,veHUD, item,viewX, y);
                    if (veHUD.npcHienTai != null && veHUD.daClickVaoNpc && veHUD.npcHienTai.npcHUDrender.ui_npc instanceof admin_thanhle) {
                        veGiaVaTime(batch, veHUD, item, y);
                    }
                }
            }
        }

        batch.flush();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        batch.end();

        if (veHUD.DangHienPopupThongTin3 && veHUD.TimeChoHienPopup <= 0) {
            boolean benPhai = !benTrai;
            if ((benPhai && veHUD.dangChonHanhTrangPhai) || (benTrai && veHUD.dangChonHanhTrangTrai)) {
                veHUD.PopupHanhTrang(veHUD.shapeRenderer, batch, oChiSoCanXet, !benTrai);
            }
        }
        batch.begin();
    }

    // Vẽ icon item với scale tự động
    private static void veIconItem(SpriteBatch batch,float width,VeHUD veHUD, Item item,float x, float y) {
        Texture tex = item.getTexture();
        float scale = (tex.getHeight() * 0.5f < 60 && tex.getWidth() * 0.5f < 100f) ? 0.5f : 0.38f;
        float xx = 3 + ((width/4.91f) - tex.getWidth() * scale) / 2f + x;
        float yy = y + (49 - tex.getHeight() * scale) / 2f;
        batch.draw(tex, xx, yy, tex.getWidth() * scale, tex.getHeight() * scale);
    }

    // Vẽ tên + số cấp + số lượng
    private static void veTenSoLuong(SpriteBatch batch,float width,VeHUD veHUD, Item item,float x, float y) {
        GlyphLayout layout = veHUD.layout;
        float xBase = (width/4.91f)+15 + x;
        // Tên item
        layout.setText(veHUD.fontMotaSkill, item.getTenItem());
        veHUD.fontMotaSkill.draw(batch, layout, xBase, y + 39);

        float kc = layout.width + 5;

        // Cấp
        if (item.getSoCap() > 0) {
            layout.setText(veHUD.fontMotaSkill, "[+" + item.getSoCap() + "]");
            veHUD.fontMotaSkill.draw(batch, layout, xBase + kc, y + 39);
        }

        // Số lượng
        if (item.getSoLuong() > 1) {
            layout.setText(veHUD.fontsm, String.valueOf(item.getSoLuong()));
            veHUD.fontsm.draw(batch, layout, (width/4.91f)- 5 - layout.width + x, y + 15);
        }
    }

    // Vẽ mô tả/thuộc tính item
    private static void veChiTietItem(SpriteBatch batch,float width,VeHUD veHUD, Item item,float x, float y) {
        GlyphLayout layout = veHUD.layout;
        float baseX = (width/4.91f)+15 + x, baseY = y + 19;

        switch (item.getLoai()) {
            case VANBAY, DEOLUNG, AURA, HUYHIEU, BONGTAI, NANGSKILL, VE_QUAY_NPC_HAIDANG -> {
                layout.setText(veHUD.fontCapSKill, item.getMoTa());
                veHUD.fontCapSKill.draw(batch, layout, baseX, baseY);
            }
            case GIAPLUYENTAP -> {
                String text = "Hiệu lực trong " +
                    (item.getHanSuDung() > 60f ? (int) (item.getHanSuDung() / 60f) + " phút" : (int) item.getHanSuDung() + " giây");
                layout.setText(veHUD.fontCapSKill, text);
                veHUD.fontCapSKill.draw(batch, layout, baseX, baseY);
            }
            case CAITRANG, AVATAR -> veChiSoCaiTrang(batch,veHUD, item, baseX, baseY);
            case AO -> veChiSoDon(batch,veHUD, "Giáp+" + item.getChiso()[4], item, baseX, baseY);
            case QUAN -> veChiSoDon(batch,veHUD, "HP+" + item.getChiso()[9], item, baseX, baseY);
            case GANG -> veChiSoDon(batch,veHUD, "Tấn công+" + item.getChiso()[11], item, baseX, baseY);
            case GIAY -> veChiSoDon(batch,veHUD, "KI+" + item.getChiso()[10], item, baseX, baseY);
            case RADA -> veChiSoDon(batch,veHUD, "Chí mạng+" + item.getChiso()[3] + "%", item, baseX, baseY);
        }
    }

    // Vẽ nhiều chỉ số cho Cải trang/Avatar
    private static void veChiSoCaiTrang(SpriteBatch batch,VeHUD veHUD, Item item, float x, float y) {
        GlyphLayout layout = veHUD.layout;
        String[] chiSoCong = {"HP", "KI", "SD", "Chí mạng", "Giáp", "ST Crit",
            "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};

        int kc = 0, soChiSo = 0;
        for (int j = 6; j <= 12; j++) {
            if (item.getChiso()[j] > 0) {
                String prefix = (soChiSo == 0) ? "" : ",";
                layout.setText(veHUD.fontCapSKill, prefix + chiSoCong[j] + "+" + item.getChiso()[j] + "%");
                veHUD.fontCapSKill.draw(batch, layout, x + kc, y);
                kc += layout.width + 1;
                soChiSo++;
            }
        }
    }

    // Vẽ chỉ số 1 dòng (Áo, Quần, Găng, Giày, Rada)
    private static void veChiSoDon(SpriteBatch batch,VeHUD veHUD, String text, Item item, float x, float y) {
        GlyphLayout layout = veHUD.layout;
        layout.setText(veHUD.fontCapSKill, text);
        veHUD.fontCapSKill.draw(batch, layout, x, y);

        float kc = layout.width + 1;
        if (item.getSetkichhoat() != null) {
            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
            veHUD.fontCapSKill.draw(batch, layout, x + kc, y);
        }
    }

    // ==========================================================
    // Vẽ giá + thời gian: fade in/out nếu item có hạn,
    // chỉ vẽ giá nếu không có hạn.
    // ==========================================================
    private static void veGiaVaTime(SpriteBatch batch, VeHUD veHUD, Item item, float y) {
        ShopItemTime shopTime = admin_thanhle.ShopItemTime.get(item.tmpId);
        boolean coHan = shopTime != null && shopTime.endAt != null;

        if (!coHan) {
            // Không có hạn → vẽ giá bình thường (alpha = 1)
            veGiaItem(batch, veHUD, item, y, 1f);
            return;
        }

        // Có hạn → fade giữa giá và time
        long serverNow = System.currentTimeMillis() + GameSocketGo.clockOffset;
        long conLai = shopTime.thoiGianConLai(serverNow);

        // Tính alpha dựa trên chu kỳ thời gian thực
        // Dùng currentTimeMillis (không cần offset, chỉ cần đều đặn)
        float t = (System.currentTimeMillis() % (long)(CHU_KY_FADE * 1000)) / 1000f;
        float alphaGia = tinhAlpha(t, 0f);
        float alphaTime = tinhAlpha(t, CHU_KY_FADE / 2f);

        // Vẽ giá với alphaGia
        veGiaItem(batch, veHUD, item, y, alphaGia);
        // Vẽ time với alphaTime (cùng vị trí với giá)
        veTimeConLai(batch, veHUD, item, y, conLai, alphaTime);
    }

    /**
     * Tính alpha cho 1 thông tin trong chu kỳ fade.
     * Chu kỳ chia 2 nửa: nửa đầu hiện thông tin 1, nửa sau hiện thông tin 2.
     * Mỗi nửa lại có: fade in - giữ - fade out.
     *
     * @param t         thời gian hiện tại trong chu kỳ (0 → CHU_KY_FADE)
     * @param offset    offset thời điểm bắt đầu (0 cho giá, CHU_KY_FADE/2 cho time)
     */
    private static float tinhAlpha(float t, float offset) {
        // Dịch theo offset, wrap về [0, CHU_KY_FADE)
        float local = (t - offset + CHU_KY_FADE) % CHU_KY_FADE;
        float halfCycle = CHU_KY_FADE / 2f;

        // Nửa thứ 2 → ẩn hoàn toàn
        if (local >= halfCycle) return 0f;

        // Nửa thứ nhất: fade in - giữ - fade out
        if (local < THOI_GIAN_FADE) {
            return local / THOI_GIAN_FADE;  // fade in 0 → 1
        }
        if (local < THOI_GIAN_FADE + THOI_GIAN_HIEN) {
            return 1f;  // giữ
        }
        if (local < halfCycle) {
            float fadeOutStart = THOI_GIAN_FADE + THOI_GIAN_HIEN;
            return 1f - (local - fadeOutStart) / THOI_GIAN_FADE;  // fade out 1 → 0
        }
        return 0f;
    }

    // Vẽ giá tiền với alpha tùy chỉnh
    private static void veGiaItem(SpriteBatch batch, VeHUD veHUD, Item item, float y, float alpha) {
        if (alpha <= 0.01f) return;  // skip nếu invisible

        GlyphLayout layout = veHUD.layout;
        Color giaColor = new Color(Color.valueOf("FEA900"));
        giaColor.a = alpha;
        veHUD.fontTenSkill.setColor(giaColor);

        String textGia = ItemGia.layGiaItem(item) >= 1_000_000L ?
            veHUD.formatVangNgoc(ItemGia.layGiaItem(item)) : String.valueOf(ItemGia.layGiaItem(item));

        layout.setText(veHUD.fontTenSkill, textGia);
        veHUD.fontTenSkill.draw(batch, layout, 350 - layout.width - 35, y + 42);

        // Icon tiền fade theo alpha
        batch.setColor(1f, 1f, 1f, alpha);
        Texture loaiTien = (ItemGia.layLoaiTien(item) == LoaiTien.VANG) ? veHUD.vang : veHUD.ngoc;
        batch.draw(loaiTien, 320, y + 42 - layout.height, 20, 20);
        batch.setColor(1f, 1f, 1f, 1f);  // reset

        veHUD.fontTenSkill.setColor(Color.WHITE);
    }

    // ==========================================================
    // Vẽ thời gian còn lại của item với alpha tùy chỉnh
    // (Pattern 1 - Lazy filter)
    // ==========================================================
    private static void veTimeConLai(SpriteBatch batch, VeHUD veHUD, Item item, float y, long conLai, float alpha) {
        if (alpha <= 0.01f) return;  // skip nếu invisible

        String text;
        Color mau;
        if (conLai <= 0) {
            text = "Hết hạn";
            mau = new Color(Color.valueOf("FF4444"));
        } else {
            text = formatTimeConLai(conLai);
            if (conLai < 60_000L) {           // < 1 phút
                mau = new Color(Color.valueOf("FF4444"));
            } else if (conLai < 3_600_000L) { // < 1 giờ
                mau = new Color(Color.valueOf("FFA500"));
            } else {
                mau = new Color(0.0902f, 0.7490f, 0.0039f, 1f);
            }
        }
        mau.a = alpha;

        GlyphLayout layout = veHUD.layout;
        veHUD.fontTenSkill.setColor(mau);
        layout.setText(veHUD.fontTenSkill, text);
        // CÙNG vị trí với giá (y + 42), không phải y + 22 nữa
        veHUD.fontTenSkill.draw(batch, layout, 350 - layout.width - 8, y + 42);

        veHUD.fontTenSkill.setColor(Color.WHITE);
    }

    // Format thời gian thành chuỗi dễ đọc
    private static String formatTimeConLai(long ms) {
        long giay = ms / 1000L;
        long phut = giay / 60L;
        long gio = phut / 60L;
        long ngay = gio / 24L;

        if (ngay > 0) {
            // > 1 ngày: hiện "Còn 3 ngày" hoặc "Còn 2n 5h"
            long gioLe = gio % 24L;
            if (gioLe > 0 && ngay < 3) {
                return "Còn " + ngay + "n " + gioLe + "h";
            }
            return "Còn " + ngay + " ngày";
        } else if (gio > 0) {
            // 1-23 giờ: hiện "Còn 5h 20p" hoặc "Còn 12h"
            long phutLe = phut % 60L;
            if (phutLe > 0 && gio < 6) {
                return "Còn " + gio + "h " + phutLe + "p";
            }
            return "Còn " + gio + "h";
        } else if (phut > 0) {
            // 1-59 phút
            long giayLe = giay % 60L;
            if (phut < 10 && giayLe > 0) {
                return "Còn " + phut + "p " + giayLe + "s";
            }
            return "Còn " + phut + "p";
        } else {
            // < 1 phút
            return "Còn " + giay + "s";
        }
    }
}
