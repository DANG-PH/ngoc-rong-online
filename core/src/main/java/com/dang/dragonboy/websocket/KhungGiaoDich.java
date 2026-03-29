package com.dang.dragonboy.websocket;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.hien_thi.*;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.AssetMulti;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.a_PHAN_LOAI_NPC.NPC_CUA_HANG;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class KhungGiaoDich {
    public static String textNutGiaoDich = "Khóa";
    public static float timeClickNutGiaoDich = 0f;

    public static void render(SpriteBatch batch) {
        VeHUD veHUD = State_Management.getVeHUD();
        capNhat(veHUD);
        GlyphLayout layout = veHUD.layout;
        DuLieuNguoiChoi duLieuNguoiChoi = veHUD.getDuLieuNguoiChoi();

        if (!veHUD.dangGiaoDich) return;

        float texAvtW = veHUD.texAvt.getWidth() * 0.52f;
        float texAvtH = veHUD.texAvt.getHeight() * 0.52f;
        batch.draw(veHUD.popupNhanVat, 0.1f, 0, 350, 610);
        batch.draw(veHUD.texAvt, 0.1f, 505, texAvtW, texAvtH);

        veHUD.layout.setText(veHUD.fontsm, "Chọn vật phẩm\nKhóa giao dịch\nChờ đối thủ khóa\nNhấn nút 'Xong'");
        veHUD.fontsm.draw(batch, veHUD.layout, 125, 590);

        // nutX
        float nutXW = veHUD.nutX.getWidth() * 0.5f;
        float nutXH = veHUD.nutX.getHeight() * 0.55f;
        batch.draw(veHUD.nutX, 350 - nutXW - 6, 610 - nutXH - 2, nutXW, nutXH - 5);

        // ===== Vẽ vàng, ngọc =====
        batch.draw(veHUD.vang, 10 + 0.1f, 8, 20, 20);
        batch.draw(veHUD.ngoc, 275 + 0.1f, 7, 20, 20);

        // → Định dạng rút gọn vàng
        veHUD.layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(duLieuNguoiChoi.getVang()));
        veHUD.fontvangngoc.draw(batch, veHUD.layout, 10 + 20 + 10 + 0.1f, 22);

        // → Định dạng rút gọn ngọc
        veHUD.layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(duLieuNguoiChoi.getNgoc()));
        veHUD.fontvangngoc.draw(batch, veHUD.layout, 275 + 20 + 10 + 0.1f, 22);

        // player 2
        batch.draw(veHUD.popupNhanVat, 1020-350, 0, 350, 610);
        batch.draw(AssetMulti.getTexture(veHUD.playerGiaoDich.avatar),1020-350,505,AssetMulti.getTexture(veHUD.playerGiaoDich.avatar).getWidth()*0.52f,AssetMulti.getTexture(veHUD.playerGiaoDich.avatar).getHeight()*0.52f);

        veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.layout.setText(veHUD.fontTenSkill, "Item nhận");
        veHUD.fontTenSkill.draw(batch, veHUD.layout, 1020 - 350 + (350 - veHUD.layout.width) / 2f, 444 + 35);
        veHUD.font.setColor(1, 1, 1, 1);
        veHUD.layout.setText(veHUD.font, veHUD.playerGiaoDich.gameName);
        veHUD.font.draw(batch, veHUD.layout, 125 + 1020 - 350, 595);
        veHUD.layout.setText(veHUD.fontsm, "Thần Xayda cấp 9+99.99%");
        veHUD.fontsm.draw(batch, veHUD.layout, 125 + 1020 - 350, 545);
        DecimalFormat dinhDang = new DecimalFormat("#,###");
        long sucManh = 10000000000L;
        String sucManhHienThi = dinhDang.format(sucManh);
        veHUD.layout.setText(veHUD.fontsm, "Sức mạnh: " + sucManhHienThi);
        veHUD.fontsm.draw(batch, veHUD.layout, 125 + 1020 - 350, 520);

        // ===== Vẽ vàng, ngọc =====
        batch.draw(veHUD.vang, 10 + 1020-350, 8, 20, 20);
        batch.draw(veHUD.ngoc, 275 + 1020-350, 7, 20, 20);

        // → Định dạng rút gọn vàng
        veHUD.layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(duLieuNguoiChoi.getVang()));
        veHUD.fontvangngoc.draw(batch, veHUD.layout, 10 + 20 + 10 + 1020-350, 22);

        // → Định dạng rút gọn ngọc
        veHUD.layout.setText(veHUD.fontvangngoc, veHUD.formatVangNgoc(duLieuNguoiChoi.getNgoc()));
        veHUD.fontvangngoc.draw(batch, veHUD.layout, 275 + 20 + 10 + 1020-350, 22);

        TrangThaiHanhTrangGd trangThaiHanhTrangGd = veHUD.trangThaiHanhTrangGd;

        String[] TextChucnangDeTu1 = {
            "Hành",
            "Item",
        };
        String[] TextChucnangDeTu2 = {
            "Trang",
            "Cho",
        };

        for (int i = 0; i < 2; i++) {
            Texture nutcn = veHUD.trangThaiHanhTrangGd.ordinal() == i ? veHUD.nutchucnangclick : veHUD.nutchucnang;
            batch.draw(nutcn, (350 - 80) / 2f - 3 - 40 - 1.5f + i * 80 + 3, 450, 80, 52);
            veHUD.layout.setText(veHUD.fontChucnang, TextChucnangDeTu1[i]);
            veHUD.fontChucnang.draw(batch, veHUD.layout, (350 - 80) / 2f - 3 - 40 - 1.5f + i * 80 + 3 + (80 - veHUD.layout.width) / 2f, 450 + 41);
            veHUD.layout.setText(veHUD.fontChucnang, TextChucnangDeTu2[i]);
            veHUD.fontChucnang.draw(batch, veHUD.layout, (350 - 80) / 2f - 3 - 40 - 1.5f + i * 80 + 3 + (80 - veHUD.layout.width) / 2f, 450 + 20);
        }

        switch (trangThaiHanhTrangGd) {
            case HANH_TRANG -> {
                HUDPopupRenderer.veHanhTrangNhanVat(batch, true);
            }
            case ITEM_CHO -> {
                NPC_CUA_HANG.render_item(true, batch, 344, veHUD, veHUD.getDuLieuNguoiChoi().hanhTrangGiaoDich, veHUD.indexItemGiaoDich);
                int soLuongItem = veHUD.getDuLieuNguoiChoi().hanhTrangGiaoDich.size();
                Texture nutVe = timeClickNutGiaoDich > 0f ? veHUD.nutclick : veHUD.nutdn;
                batch.draw(nutVe, 260, 400 - 50 * soLuongItem, 80, 40);
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, textNutGiaoDich);
                float textX = 260 + (80 - layout.width) / 2f;
                float textY = 400 - 50 * soLuongItem + (40 - layout.height) / 2f + layout.height;
                veHUD.font.draw(batch, layout, textX, textY);
            }
        }

        if (duLieuNguoiChoi.hanhTrangGiaoDichPlayer2.size() > 0) {
            NPC_CUA_HANG.render_item(false, batch, 344, veHUD, veHUD.getDuLieuNguoiChoi().hanhTrangGiaoDichPlayer2, veHUD.indexItemGiaoDich);
        }
    }

    public static void checkClick(float x, float y, float worldX, float worldY) {
        VeHUD veHUD = State_Management.getVeHUD();
        DuLieuNguoiChoi duLieuNguoiChoi = veHUD.getDuLieuNguoiChoi();

        if (!veHUD.DangHienPopupThongTin1 && !veHUD.DangHienPopupThongTin3) {
            // Hủy Giao Dịch
            if (x > 350 && x <= 1020 - 350) {
                try {
                    GameSocket.tradeCancel(veHUD.playerGiaoDich.userId);
                } catch (Exception e) {

                }
            }

            // Chuyển đổi giữa 2 trạng thái
            for (TrangThaiHanhTrangGd trangThai : TrangThaiHanhTrangGd.values()) {
                int i = trangThai.ordinal();

                if (HUDClickHandler.checkChuotTrongNut(x, y,
                    (350 - 80) / 2f - 3 - 40 - 1.5f + i * 80 + 3,
                    450, 80, 52)) {

                    veHUD.trangThaiHanhTrangGd = trangThai;
                    veHUD.scrollYTrai = 0;
                    veHUD.scrollYPhai = 0;
                }
            }
        }

        if (!veHUD.DangHienPopupThongTin1 && veHUD.dangChonHanhTrangTrai) {
            // Click item
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 8 + 12;
            boolean duDieuKien = false;
            boolean vePhai = false;
            if (veHUD.dangGiaoDich && veHUD.trangThaiHanhTrangGd == TrangThaiHanhTrangGd.HANH_TRANG) {
                duDieuKien = x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight;
            }
            if (duDieuKien) {
                float relativeY = y - viewY;
                float realY = veHUD.scrollYTrai + (viewHeight - relativeY);
                int index = (int) (realY / KhoangCachItem);
                veHUD.hangTrangDangChon = index;
                if (veHUD.hangTrangDangChon >= 8) {
                    int indexx = veHUD.hangTrangDangChon - 8;
                    ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();
                    if (indexx < danhSach.toArray().length) {
                        Item item = danhSach.get(indexx);
                        veHUD.itemm = item;
                    } else {
                        veHUD.itemm = null;
                    }
                    if (veHUD.itemm != null) {
                        veHUD.DangHienPopupThongTin1 = true;
                        if (!vePhai) {
                            veHUD.PopupHanhTrangX_Trai = 5;
                            veHUD.PopupHanhTrangW_Trai = 360;
                            veHUD.PopupHanhTrangY_Trai = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYTrai;
                            veHUD.PopupHanhTrangH_Trai = 0;
                        } else {
                            veHUD.PopupHanhTrangX_Phai = 5;
                            veHUD.PopupHanhTrangW_Phai = 360;
                            veHUD.PopupHanhTrangY_Phai = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYPhai;
                            veHUD.PopupHanhTrangH_Phai = 0;
                        }
                        veHUD.TimeChoHienPopup = 0.3f;
                        veHUD.vuaMoPopupThongTin = true;
                    }
                } else {
                    ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrangDangMac();
                    if (danhSach.get(veHUD.hangTrangDangChon) != null){
                        Item item = danhSach.get(veHUD.hangTrangDangChon);
                        veHUD.itemm = item;
                    } else {
                        veHUD.itemm = null;
                    }
                    if (veHUD.itemm != null) {
                        veHUD.DangHienPopupThongTin1 = true;
                        if (!vePhai) {
                            veHUD.PopupHanhTrangX_Trai = 5;
                            veHUD.PopupHanhTrangW_Trai = 360;
                            veHUD.PopupHanhTrangY_Trai = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYTrai;
                            veHUD.PopupHanhTrangH_Trai = 0;
                        } else {
                            veHUD.PopupHanhTrangX_Phai = 5;
                            veHUD.PopupHanhTrangW_Phai = 360;
                            veHUD.PopupHanhTrangY_Phai = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYPhai;
                            veHUD.PopupHanhTrangH_Phai = 0;
                        }
                        veHUD.TimeChoHienPopup = 0.3f;
                        veHUD.vuaMoPopupThongTin = true;
                    }
                }
            }
        }

        if (veHUD.DangHienPopupThongTin1) {
            if (veHUD.vuaMoPopupThongTin) {
                veHUD.vuaMoPopupThongTin = false;
                return;
            }
            if (x > 0 && x <= 360 && (y > veHUD.PopupHanhTrangY_Trai + veHUD.PopupHanhTrangH_Trai || y < veHUD.PopupHanhTrangY_Trai - 130)) {
                veHUD.DangHienPopupThongTin1 = false;
                veHUD.TimeChoHienPopup = 0;
            }
            if (x > 360 && x <= 1020) {
                veHUD.DangHienPopupThongTin1 = false;
                veHUD.TimeChoHienPopup = 0;
            }

            // click nut
            float yNut = veHUD.PopupHanhTrangY_Trai - 115;
            if (x > 1 && x < 115 && y >= yNut && y <= yNut + 115) {
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon = 1;
            } else if (x > 121 && x < 115 + 120 && y >= yNut && y <= yNut + 115) {
                boolean duDieuKien =
                    (veHUD.itemm.getLoai() == LoaiItem.AO  ||
                        veHUD.itemm.getLoai() == LoaiItem.QUAN ||
                        veHUD.itemm.getLoai() == LoaiItem.GIAY ||
                        veHUD.itemm.getLoai() == LoaiItem.GANG ||
                        veHUD.itemm.getLoai() == LoaiItem.RADA) && veHUD.hangTrangDangChon >= 8;
                if (duDieuKien) {
                    veHUD.nutClickTimer3 = 0.3f;
                    veHUD.nuthanhtrangchon = 2;
                }
            }
        }

        if (veHUD.trangThaiHanhTrangGd == TrangThaiHanhTrangGd.ITEM_CHO && veHUD.dangChonHanhTrangTrai) {
            // Click item
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            boolean duDieuKien = HUDClickHandler.checkChuotTrongNut(x, y, 3, 35, 344, 444 - 35) && !veHUD.DangHienPopupThongTin1 && !veHUD.DangHienPopupThongTin3 && !veHUD.dangHienThongBao;
            if (duDieuKien) {
                float relativeY = y - viewY;
                float realY = veHUD.scrollYTrai + (viewHeight - relativeY);
                int index = (int) (realY / KhoangCachItem);
                veHUD.indexItemGiaoDich = index;
                ArrayList<Item> danhSach = veHUD.getDuLieuNguoiChoi().hanhTrangGiaoDich;
                if (index < danhSach.toArray().length) {
                    Item item = danhSach.get(index);
                    veHUD.itemm = item;
                    if (veHUD.itemm != null) {
                        veHUD.dangChonHanhTrangTrai = true;
                        veHUD.dangChonHanhTrangPhai = false;
                        veHUD.DangHienPopupThongTin3 = true;
                        veHUD.PopupHanhTrangX_Trai = 5;
                        veHUD.PopupHanhTrangW_Trai = 360;
                        veHUD.PopupHanhTrangY_Trai = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYTrai;
                        veHUD.PopupHanhTrangH_Trai = 0;
                        veHUD.TimeChoHienPopup = 0.3f;
                        veHUD.vuaMoPopupThongTin = true;
                    }
                }
            }

            // Click nut Giao dich
            int soLuongItem = veHUD.getDuLieuNguoiChoi().hanhTrangGiaoDich.size();
            if (HUDClickHandler.checkChuotTrongNut(x, y, 260, 400 - 50 * soLuongItem, 80, 40)) {
                timeClickNutGiaoDich = 0.3f;
            }
        }

        // Click item player2 gửi
        if (!veHUD.DangHienPopupThongTin1 && !veHUD.DangHienPopupThongTin3) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            boolean duDieuKien = HUDClickHandler.checkChuotTrongNut(x, y, 3 + 1020 - 350, 35, 344, 444 - 35) && !veHUD.dangHienThongBao;
            if (duDieuKien) {
                float relativeY = y - viewY;
                float realY = veHUD.scrollYPhai + (viewHeight - relativeY);
                int index = (int) (realY / KhoangCachItem);
                veHUD.indexItemGiaoDich = index;
                ArrayList<Item> danhSach = veHUD.getDuLieuNguoiChoi().hanhTrangGiaoDichPlayer2;
                if (index < danhSach.toArray().length) {
                    Item item = danhSach.get(index);
                    veHUD.itemm = item;
                    if (veHUD.itemm != null) {
                        veHUD.dangChonHanhTrangTrai = false;
                        veHUD.dangChonHanhTrangPhai = true;
                        veHUD.DangHienPopupThongTin3 = true;
                        veHUD.PopupHanhTrangX_Phai = 5;
                        veHUD.PopupHanhTrangW_Phai = 360;
                        veHUD.PopupHanhTrangY_Phai = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYPhai;
                        veHUD.PopupHanhTrangH_Phai = 0;
                        veHUD.TimeChoHienPopup = 0.3f;
                        veHUD.vuaMoPopupThongTin = true;
                    }
                }
            }
        }

        if (veHUD.DangHienPopupThongTin3) {
            if (veHUD.vuaMoPopupThongTin) {
                veHUD.vuaMoPopupThongTin = false;
                return;
            }
            if (veHUD.dangChonHanhTrangTrai) {
                if (x > 0 && x <= 360 && (y > veHUD.PopupHanhTrangY_Trai + veHUD.PopupHanhTrangH_Trai || y < veHUD.PopupHanhTrangY_Trai - 130)) {
                    veHUD.DangHienPopupThongTin3 = false;
                    veHUD.TimeChoHienPopup = 0;
                }
                if (x > 360 && x <= 1020) {
                    veHUD.DangHienPopupThongTin3 = false;
                    veHUD.TimeChoHienPopup = 0;
                }

                // click nut
                float yNut = veHUD.PopupHanhTrangY_Trai - 115;
                if (x > 1 && x < 115 && y >= yNut && y <= yNut + 115) {
                    veHUD.nutClickTimer3 = 0.3f;
                    veHUD.nuthanhtrangchon = 1;
                }
            } else {
                if (x > 1020 - 350 && x <= 360 + 1020 - 350 && (y > veHUD.PopupHanhTrangY_Phai + veHUD.PopupHanhTrangH_Phai || y < veHUD.PopupHanhTrangY_Phai - 130)) {
                    veHUD.DangHienPopupThongTin3 = false;
                    veHUD.TimeChoHienPopup = 0;
                }
                if (x > 0 && x <= 360) {
                    veHUD.DangHienPopupThongTin3 = false;
                    veHUD.TimeChoHienPopup = 0;
                }

                // click nut
                float yNut = veHUD.PopupHanhTrangY_Phai - 115;
                if (x > 1 + 1020 - 350 && x < 115 + 1020 - 350 && y >= yNut && y <= yNut + 115) {
                    veHUD.nutClickTimer3 = 0.3f;
                    veHUD.nuthanhtrangchon = 1;
                }
            }
        }
    }

    public static void capNhat(VeHUD veHUD) {
        if (timeClickNutGiaoDich > 0) {
            timeClickNutGiaoDich -= Gdx.graphics.getDeltaTime();
            if (timeClickNutGiaoDich <= 0) {
                timeClickNutGiaoDich = 0;
                if (textNutGiaoDich.equals("Khóa")) {
                    try {
                        GameSocket.tradeLock(veHUD.playerGiaoDich.userId);
                    } catch (Exception e) {

                    }
                } else if (textNutGiaoDich.equals("Gửi")) {
                    try {
                        GameSocket.tradeConfirm(veHUD.playerGiaoDich.userId);
                    } catch (Exception e) {

                    }
                }
            }
        }
    }
}
