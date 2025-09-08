package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.a_PHAN_LOAI_NPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.ItemGia;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.LoaiTien;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.TrangThaiChucNang_admin_thanhle;

import java.util.ArrayList;

public class NPC_CUA_HANG {
    public static void render_item(SpriteBatch batch, VeHUD veHUD, ArrayList<Item> danhSachItem, int oChiSoCanXet) {
        float viewY = 35, viewHeight = 444 - 35;
        int khoangCachItem = 49;
        int tongSoRuongDo = danhSachItem.size();
        veHUD.maxScrollTrai = Math.max(0, tongSoRuongDo * khoangCachItem - viewHeight);

        batch.flush();
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(0, (int) viewY, 350, (int) viewHeight);

        float startY = viewY + viewHeight - khoangCachItem + veHUD.scrollYTrai;

        for (int i = 0; i < tongSoRuongDo; i++) {
            float y = startY - i * khoangCachItem;

            // Vẽ ô nền
            Texture tex = (oChiSoCanXet == i) ? veHUD.hanh_trang_dang_mac_click : veHUD.hanh_trang_dang_mac;
            Texture texNen = (oChiSoCanXet == i) ? veHUD.nenTrangNgaClick : veHUD.nenTrangNga;
            batch.draw(tex, 3, y, 274, 50);
            batch.draw(texNen, 60, y, 287, 50);

            // Vẽ item
            if (i < danhSachItem.size()) {
                Item item = danhSachItem.get(i);
                if (item != null) {
                    veIconItem(batch,veHUD, item, y);
                    veTenSoLuong(batch,veHUD, item, y);
                    veChiTietItem(batch,veHUD, item, y);
                    veGiaItem(batch,veHUD, item, y);
                }
            }
        }

        batch.flush();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        batch.end();

        if (veHUD.DangHienPopupThongTin3 && veHUD.TimeChoHienPopup <= 0) {
            veHUD.PopupHanhTrang(veHUD.shapeRenderer, batch, oChiSoCanXet, false);
        }
        batch.begin();
    }

    // Vẽ icon item với scale tự động
    private static void veIconItem(SpriteBatch batch,VeHUD veHUD, Item item, float y) {
        Texture tex = item.getTexture();
        float scale = (tex.getHeight() * 0.5f < 60 && tex.getWidth() * 0.5f < 100f) ? 0.5f : 0.38f;
        float x = 3 + (56 - tex.getWidth() * scale) / 2f;
        float yy = y + (49 - tex.getHeight() * scale) / 2f;
        batch.draw(tex, x, yy, tex.getWidth() * scale, tex.getHeight() * scale);
    }

    // Vẽ tên + số cấp + số lượng
    private static void veTenSoLuong(SpriteBatch batch,VeHUD veHUD, Item item, float y) {
        GlyphLayout layout = veHUD.layout;

        // Tên item
        layout.setText(veHUD.fontMotaSkill, item.getTenItem());
        veHUD.fontMotaSkill.draw(batch, layout, 71, y + 39);

        float kc = layout.width + 5;

        // Cấp
        if (item.getSoCap() > 0) {
            layout.setText(veHUD.fontMotaSkill, "[+" + item.getSoCap() + "]");
            veHUD.fontMotaSkill.draw(batch, layout, 71 + kc, y + 39);
        }

        // Số lượng
        if (item.getSoLuong() > 1) {
            layout.setText(veHUD.fontsm, String.valueOf(item.getSoLuong()));
            veHUD.fontsm.draw(batch, layout, 65 - layout.width, y + 15);
        }
    }

    // Vẽ mô tả/thuộc tính item
    private static void veChiTietItem(SpriteBatch batch,VeHUD veHUD, Item item, float y) {
        GlyphLayout layout = veHUD.layout;
        float baseX = 71, baseY = y + 19;

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

    // Vẽ giá và loại tiền
    private static void veGiaItem(SpriteBatch batch,VeHUD veHUD, Item item, float y) {
        GlyphLayout layout = veHUD.layout;
        veHUD.fontTenSkill.setColor(Color.valueOf("FEA900"));

        String textGia = ItemGia.layGiaItem(item) >= 1_000_000L ?
            veHUD.formatVangNgoc(ItemGia.layGiaItem(item)) : String.valueOf(ItemGia.layGiaItem(item));

        layout.setText(veHUD.fontTenSkill, textGia);
        veHUD.fontTenSkill.draw(batch, layout, 350 - layout.width - 35, y + 42);

        Texture loaiTien = (ItemGia.layLoaiTien(item) == LoaiTien.VANG) ? veHUD.vang : veHUD.ngoc;
        batch.draw(loaiTien, 320, y + 42 - layout.height, 20, 20);
    }
}
