package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.NhanVat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HUDRuongDo {
    private VeHUD veHUD;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    private DuLieuNguoiChoi duLieuNguoiChoi;
    private NhanVat nhanVat;

    private Texture avtRuong;

    public HUDRuongDo(VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        layout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
        this.veHUD = veHUD;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
        this.nhanVat = nhanVat;
        avtRuong = new Texture("nhanvat/npc/ruong_do/avt_ruong.png");
    }

    public void renderRuongDo(SpriteBatch batch) {
        if (!veHUD.dangHienRuongDo) return;
        //avt
        float texAvtW = veHUD.texAvt.getWidth() * 0.52f;
        float texAvtH = veHUD.texAvt.getHeight() * 0.52f;
        batch.draw(veHUD.popupNhanVat, 1020 - 350, 0, 350, 610);
        batch.draw(veHUD.texAvt, 1020 - 350, 505, texAvtW, texAvtH);

        // ===== Vẽ vàng, ngọc =====
        batch.draw(veHUD.vang, 10 + 1020 - 350, 8, 20, 20);
        batch.draw(veHUD.ngoc, 275 + 1020 - 350, 7, 20, 20);

        // → Định dạng rút gọn vàng
        layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(duLieuNguoiChoi.getVang()));
        veHUD.fontvangngoc.draw(batch, layout, 10 + 20 + 10 + 1020 - 350, 22);

        // → Định dạng rút gọn ngọc
        layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(duLieuNguoiChoi.getNgoc()));
        veHUD.fontvangngoc.draw(batch, layout, 275 + 20 + 10 + 1020 - 350, 22);

        veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        layout.setText(veHUD.fontTenSkill, "Trang bị");
        veHUD.fontTenSkill.draw(batch, layout, 1020 - 350 + (350 - layout.width) / 2f, 444 + 35);
        veHUD.font.setColor(1, 1, 1, 1);
        layout.setText(veHUD.font, duLieuNguoiChoi.getTen());
        veHUD.font.draw(batch, layout, 125 + 1020 - 350, 595);
        layout.setText(veHUD.fontsm, "Thể lực");
        veHUD.fontsm.draw(batch, layout, 125 + 1020 - 350, 570);
        batch.draw(veHUD.thanhtheluc, 125 + 68 + 1020 - 350, 558);
        batch.draw(veHUD.thanhtheluc3 ,125 + 68 + 1020 - 350, 558);
        layout.setText(veHUD.fontsm, duLieuNguoiChoi.getCapBac());
        veHUD.fontsm.draw(batch, layout, 125 + 1020 - 350, 545);
        DecimalFormat dinhDang = new DecimalFormat("#,###");
        long sucManh = duLieuNguoiChoi.getSucManh();
        String sucManhHienThi = dinhDang.format(sucManh);
        layout.setText(veHUD.fontsm, "Sức mạnh: " + sucManhHienThi);
        veHUD.fontsm.draw(batch, layout, 125 + 1020 - 350, 520);

        float viewY = 35;
        float viewHeight = 444 - 35;
        int KhoangCachItem = 49;

        int soTrangBi = 8;
        ArrayList<Item> danhSachItem = duLieuNguoiChoi.getHanhTrang();
        int soKhac = 50;
        int tongSoTrangBi = soTrangBi + soKhac;

        float totalHeight = tongSoTrangBi * KhoangCachItem;
        veHUD.maxScrollPhai = Math.max(0, totalHeight - viewHeight);

        batch.flush();
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(1020 - 350, (int) viewY, 350, (int) viewHeight);
        // Vị trí bắt đầu vẽ từ trên xuống
        float startY = viewY + viewHeight - KhoangCachItem + veHUD.scrollYPhai;

        ArrayList<Item> danhSachDangMac = duLieuNguoiChoi.getHanhTrangDangMac();
        for (int i = 0; i < soTrangBi; i++) {
            Item item = danhSachDangMac.get(i);
            float y = startY - i * KhoangCachItem;
            Texture tex = (veHUD.hangTrangDangChon == i) ? veHUD.hanh_trang_dang_mac_click : veHUD.hanh_trang_dang_mac;
            batch.draw(tex, 3 + 1020 - 350, y, 344, 50);
            if (item != null) {
                if (item.getTexture().getHeight() * 0.5f < 60 && item.getTexture().getWidth()*0.5f < 100) {
                    batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.5f) / 2f + 1020 - 350, y + (49 - item.getTexture().getHeight() * 0.5f) / 2f, item.getTexture().getWidth() * 0.5f, item.getTexture().getHeight() * 0.5f);
                } else {
                    batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.38f) / 2f + 1020 - 350, y + (49 - item.getTexture().getHeight() * 0.38f) / 2f, item.getTexture().getWidth() * 0.38f, item.getTexture().getHeight() * 0.38f);
                }
            }
            String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng", "Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
            if (item != null) {
                int kc1 = 0;
                layout.setText(veHUD.fontMotaSkill, item.getTenItem());
                veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 10);
                kc1 += layout.width + 5;
                if (item.getSoCap() > 0) {
                    layout.setText(veHUD.fontMotaSkill, "[+" + item.getSoCap() + "]");
                    veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12 + kc1 + 1020 - 350, y + 49 - 10);
                }
            }
            if (i == 7 && item != null) {
                layout.setText(veHUD.fontCapSKill, item.getMoTa());
                veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
            }
            if (i == 6 && item != null) {
                layout.setText(veHUD.fontCapSKill, "Hiệu lực trong " + (item.getHanSuDung() > 60f ? (int) (item.getHanSuDung() / 60f) + " phút" : (int)(item.getHanSuDung())+" giây"));
                veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
            }
            if (i == 5 && item != null) {
                int kc = 0;
                int soChiso = 0;
                for (int j = 6; j <= 12; j++) {
                    if (item.getChiso()[j] > 0) {
                        String prefix = (soChiso == 0) ? "" : ",";
                        layout.setText(veHUD.fontCapSKill, prefix + chisoduoccong[j] + "+" + item.getChiso()[j] + "%");
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                        kc += layout.width + 1;
                        soChiso++;
                    }
                }
            }
            if (i == 0 && item != null) {
                int kc = 0;
                layout.setText(veHUD.fontCapSKill, "Giáp+" + item.getChiso()[4]);
                veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                kc += layout.width + 1;
                if (item.getSetkichhoat() != null) {
                    layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                    veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                }
            }
            if (i == 1 && item != null) {
                int kc = 0;
                layout.setText(veHUD.fontCapSKill, "HP+" + item.getChiso()[9]);
                veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                kc += layout.width + 1;
                if (item.getSetkichhoat() != null) {
                    layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                    veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                }
            }
            if (i == 2 && item != null) {
                int kc = 0;
                layout.setText(veHUD.fontCapSKill, "Tấn công+" + item.getChiso()[11]);
                veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                kc += layout.width + 1;
                if (item.getSetkichhoat() != null) {
                    layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                    veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                }
            }
            if (i == 3 && item != null) {
                int kc = 0;
                layout.setText(veHUD.fontCapSKill, "KI+" + item.getChiso()[10]);
                veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                kc += layout.width + 1;
                if (item.getSetkichhoat() != null) {
                    layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                    veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                }
            }
            if (i == 4 && item != null) {
                int kc = 0;
                layout.setText(veHUD.fontCapSKill, "Chí mạng+" + item.getChiso()[3] + "%");
                veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                kc += layout.width + 1;
                if (item.getSetkichhoat() != null) {
                    layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                    veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                }
            }
        }
        for (int i = 0; i < soKhac; i++) {
            float y = startY - (soTrangBi + i) * KhoangCachItem;
            // Vẽ ô nền
            Texture tex = (veHUD.hangTrangDangChon == soTrangBi + i) ? veHUD.hanh_trang_click : veHUD.hanh_trang;
            batch.draw(tex, 3 + 1020 - 350, y, 344, 50);
            // Nếu có item trong danh sách thì vẽ icon
            if (i < danhSachItem.size()) {
                Item item = danhSachItem.get(i);
                if (item != null) {
                    if (item.getTexture().getHeight() * 0.5f < 60 && item.getTexture().getWidth()*0.5f < 100f) {
                        batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.5f) / 2f + 1020 - 350, y + (49 - item.getTexture().getHeight() * 0.5f) / 2f, item.getTexture().getWidth() * 0.5f, item.getTexture().getHeight() * 0.5f);
                    } else {
                        batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.38f) / 2f + 1020 - 350, y + (49 - item.getTexture().getHeight() * 0.38f) / 2f, item.getTexture().getWidth() * 0.38f, item.getTexture().getHeight() * 0.38f);
                    }
                    String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng", "Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
                    int kc1 = 0;
                    layout.setText(veHUD.fontMotaSkill, item.getTenItem());
                    veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 10);
                    kc1 += layout.width + 5;
                    if (item.getSoCap() > 0) {
                        layout.setText(veHUD.fontMotaSkill, "[+" + item.getSoCap() + "]");
                        veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12 + kc1 + 1020 - 350, y + 49 - 10);
                    }
                    if (item.getSoLuong() > 1) {
                        layout.setText(veHUD.fontsm, item.getSoLuong()+"");
                        veHUD.fontsm.draw(batch, layout, 3 + (70- layout.width)-5f + 1020 - 350, y + 15f);
                    }
                    if (item.getLoai() == LoaiItem.VANBAY) {
                        layout.setText(veHUD.fontCapSKill, item.getMoTa());
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                    }
                    if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                        layout.setText(veHUD.fontCapSKill, "Hiệu lực trong " + (item.getHanSuDung() > 60f ? (int) (item.getHanSuDung() / 60f) + " phút" : (int)(item.getHanSuDung())+" giây"));
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                    }
                    if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {
                        int kc = 0;
                        int soChiso = 0;
                        for (int j = 6; j <= 12; j++) {
                            if (item.getChiso()[j] > 0) {
                                String prefix = (soChiso == 0) ? "" : ",";
                                layout.setText(veHUD.fontCapSKill, prefix + chisoduoccong[j] + "+" + item.getChiso()[j] + "%");
                                veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                                kc += layout.width + 1;
                                soChiso++;
                            }
                        }
                    }
                    if (item.getLoai() == LoaiItem.AO) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "Giáp+" + item.getChiso()[4]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.QUAN) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "HP+" + item.getChiso()[9]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.GANG) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "Tấn công+" + item.getChiso()[11]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.GIAY) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "KI+" + item.getChiso()[10]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.RADA) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "Chí mạng+" + item.getChiso()[3] + "%");
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc + 1020 - 350, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.DEOLUNG ||
                        item.getLoai() == LoaiItem.AURA ||
                        item.getLoai() == LoaiItem.HUYHIEU ||
                        item.getLoai() == LoaiItem.BONGTAI ||
                        item.getLoai() == LoaiItem.NANGSKILL ||
                        item.getLoai() == LoaiItem.VE_QUAY_NPC_HAIDANG
                    ) {
                        layout.setText(veHUD.fontCapSKill,item.getMoTa());
                        veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 + 1020 - 350, y + 49 - 30);
                    }
                }
            }
        }
        batch.flush();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        batch.end();
        if (veHUD.DangHienPopupThongTin1 && veHUD.TimeChoHienPopup <= 0) {
            veHUD.PopupHanhTrang(shapeRenderer, batch, veHUD.hangTrangDangChon,true);
        }
        batch.begin();

        //hanh trang rương đồ
        batch.draw(veHUD.popupNhanVat, 0, 0, 350, 610);
        batch.draw(avtRuong,0,505,avtRuong.getWidth()*0.52f,avtRuong.getHeight()*0.52f);

        // ===== Vẽ vàng, ngọc =====
        batch.draw(veHUD.vang, 10, 8, 20, 20);
        batch.draw(veHUD.ngoc, 275, 7, 20, 20);

        // → Định dạng rút gọn vàng
        layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(duLieuNguoiChoi.getVang()));
        veHUD.fontvangngoc.draw(batch, layout, 10 + 20 + 10, 22);

        // → Định dạng rút gọn ngọc
        layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(duLieuNguoiChoi.getNgoc()));
        veHUD.fontvangngoc.draw(batch, layout, 275 + 20 + 10, 22);

        layout.setText(veHUD.fontTenSkill, "Rương đồ");
        veHUD.fontTenSkill.draw(batch, layout, (350 - layout.width) / 2f, 444 + 35);
        veHUD.font.setColor(1, 1, 1, 1);
        layout.setText(veHUD.font, "Rương đồ");
        veHUD.font.draw(batch, layout, 125, 595);
        layout.setText(veHUD.fontsm, "Đã dùng :" + duLieuNguoiChoi.getHanhTrangRuongDo().size()+"/" + duLieuNguoiChoi.MAXRUONGDO + " ô");
        veHUD.fontsm.draw(batch, layout, 125, 570);

        ArrayList<Item> danhSachItemRuongDo = duLieuNguoiChoi.getHanhTrangRuongDo();
        int tongSoRuongDo = duLieuNguoiChoi.MAXRUONGDO;

        float totalHeightRuongDo = tongSoRuongDo * KhoangCachItem;
        veHUD.maxScrollTrai = Math.max(0, totalHeightRuongDo - viewHeight);

        batch.flush();
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(0, (int) viewY, 350, (int) viewHeight);
        // Vị trí bắt đầu vẽ từ trên xuống
        float startYRuongDo = viewY + viewHeight - KhoangCachItem + veHUD.scrollYTrai;

        for (int i = 0; i < tongSoRuongDo; i++) {
            float y = startYRuongDo - i * KhoangCachItem;
            // Vẽ ô nền
            Texture tex = (veHUD.hanhTrangRuongDoDangChon == i) ? veHUD.hanh_trang_dang_mac_click : veHUD.hanh_trang_dang_mac;
            batch.draw(tex, 3, y, 344, 50);
            // Nếu có item trong danh sách thì vẽ icon
            if (i < danhSachItemRuongDo.size()) {
                Item item = danhSachItemRuongDo.get(i);
                if (item != null) {
                    if (item.getTexture().getHeight() * 0.5f < 60 && item.getTexture().getWidth()*0.5f < 100f) {
                        batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.5f) / 2f, y + (49 - item.getTexture().getHeight() * 0.5f) / 2f, item.getTexture().getWidth() * 0.5f, item.getTexture().getHeight() * 0.5f);
                    } else {
                        batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.38f) / 2f, y + (49 - item.getTexture().getHeight() * 0.38f) / 2f, item.getTexture().getWidth() * 0.38f, item.getTexture().getHeight() * 0.38f);
                    }
                    String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng", "Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
                    int kc1 = 0;
                    layout.setText(veHUD.fontMotaSkill, item.getTenItem());
                    veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);
                    kc1 += layout.width + 5;
                    if (item.getSoCap() > 0) {
                        layout.setText(veHUD.fontMotaSkill, "[+" + item.getSoCap() + "]");
                        veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12 + kc1, y + 49 - 10);
                    }
                    if (item.getSoLuong() > 1) {
                        layout.setText(veHUD.fontsm, item.getSoLuong()+"");
                        veHUD.fontsm.draw(batch, layout, 3 + (70- layout.width)-5f, y + 15f);
                    }
                    if (item.getLoai() == LoaiItem.VANBAY) {
                        layout.setText(veHUD.fontCapSKill, item.getMoTa());
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12, y + 49 - 30);
                    }
                    if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                        layout.setText(veHUD.fontCapSKill, "Hiệu lực trong " + (item.getHanSuDung() > 60f ? (int) (item.getHanSuDung() / 60f) + " phút" : (int)(item.getHanSuDung())+" giây"));
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12, y + 49 - 30);
                    }
                    if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {
                        int kc = 0;
                        int soChiso = 0;
                        for (int j = 6; j <= 12; j++) {
                            if (item.getChiso()[j] > 0) {
                                String prefix = (soChiso == 0) ? "" : ",";
                                layout.setText(veHUD.fontCapSKill, prefix + chisoduoccong[j] + "+" + item.getChiso()[j] + "%");
                                veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
                                kc += layout.width + 1;
                                soChiso++;
                            }
                        }
                    }
                    if (item.getLoai() == LoaiItem.AO) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "Giáp+" + item.getChiso()[4]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.QUAN) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "HP+" + item.getChiso()[9]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.GANG) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "Tấn công+" + item.getChiso()[11]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.GIAY) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "KI+" + item.getChiso()[10]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.RADA) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "Chí mạng+" + item.getChiso()[3] + "%");
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.DEOLUNG ||
                        item.getLoai() == LoaiItem.AURA ||
                        item.getLoai() == LoaiItem.HUYHIEU ||
                        item.getLoai() == LoaiItem.BONGTAI ||
                        item.getLoai() == LoaiItem.NANGSKILL ||
                        item.getLoai() == LoaiItem.VE_QUAY_NPC_HAIDANG
                    ) {
                        layout.setText(veHUD.fontCapSKill,item.getMoTa());
                        veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                    }
                }
            }
        }
        batch.flush();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        batch.end();
        if (veHUD.DangHienPopupThongTin3 && veHUD.TimeChoHienPopup <= 0) {
            veHUD.PopupHanhTrang(shapeRenderer, batch, veHUD.hanhTrangRuongDoDangChon,false);
        }
        batch.begin();
        if (veHUD.dangHienThongBao){
            batch.draw(veHUD.anhThongBao, (Gdx.graphics.getWidth() - 720) / 2f, 65, 720, 175);
            layout.setText(veHUD.fontTenSkill, "Bạn có chắc muốn hủy bỏ (mất luôn)");
            veHUD.fontTenSkill.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, 175);
            layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem()+" ?");
            veHUD.fontTenSkill.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, 150);
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 50;
            batch.draw(veHUD.isThongBaoOKPressed>0 && veHUD.nutduocchon==1? veHUD.nutclick : veHUD.nutdn, nutX-81, nutY, 140, 50);
            layout.setText(veHUD.fontTenSkill, "Có");
            veHUD.fontTenSkill.draw(batch, layout, nutX-81 + (140 - layout.width) / 2f, nutY + 30);
            batch.draw(veHUD.isThongBaoOKPressed>0 && veHUD.nutduocchon==2? veHUD.nutclick : veHUD.nutdn, nutX+81, nutY, 140, 50);
            layout.setText(veHUD.fontTenSkill, "Không");
            veHUD.fontTenSkill.draw(batch, layout, nutX+81 + (140 - layout.width) / 2f, nutY + 30);
        }
    }
}
