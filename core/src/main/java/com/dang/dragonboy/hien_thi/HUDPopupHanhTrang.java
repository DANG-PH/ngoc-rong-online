package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class HUDPopupHanhTrang {
    private final VeHUD veHUD;
    private DuLieuNguoiChoi duLieuNguoiChoi;
    private NhanVat nhanVat;
    private GlyphLayout layout;
    public HUDPopupHanhTrang(VeHUD veHUD, GlyphLayout layout, DuLieuNguoiChoi duLieuNguoiChoi , NhanVat nhanVat) {
        this.veHUD = veHUD;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
        this.nhanVat = nhanVat;
        this.layout = layout;
    }
    public void PopupHanhTrang(ShapeRenderer shapeRenderer, SpriteBatch batch, int oHanhTrangDangChon, boolean benPhai) {
        String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng","Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
        Map<String, String> setkichhoat = new HashMap<>();
        setkichhoat.put("Nappa", "(5 món +80% HP)");
        setkichhoat.put("Sôngôku", "(5 món +100% sát thương Kamejoko)");
        setkichhoat.put("Dũng Sĩ Trong Băng Giá", "(5 món +40% Chí Mạng)");
        float PopupH,PopupW,PopupX,PopupY;

        float xCongThem = 0;
        if (benPhai) {
            PopupX = veHUD.PopupHanhTrangX_Phai;
            PopupY = veHUD.PopupHanhTrangY_Phai;
            PopupW = veHUD.PopupHanhTrangW_Phai;
            PopupH = veHUD.PopupHanhTrangH_Phai;
            xCongThem = 1020 - PopupW - 10;
        } else {
            xCongThem = 0;
            PopupX = veHUD.PopupHanhTrangX_Trai;
            PopupY = veHUD.PopupHanhTrangY_Trai;
            PopupW = veHUD.PopupHanhTrangW_Trai;
            PopupH = veHUD.PopupHanhTrangH_Trai;
        }

        if (veHUD.itemm!=null) {
            LoaiItem loaiItem = veHUD.itemm.getLoai();
            if (loaiItem == LoaiItem.GIAPLUYENTAP) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                layout.setText(veHUD.fontSkillchuaco, "Hiệu lực trong " + (veHUD.itemm.getHanSuDung() > 60f ? (int) (veHUD.itemm.getHanSuDung() / 60f) + " phút" : (int)(veHUD.itemm.getHanSuDung())+" giây"));
                PopupH += layout.height + 15;
                for (int i = 6; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        PopupH += layout.height + 15;
                    }
                }
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh của bạn: " + duLieuNguoiChoi.getSucManh());
                        PopupH += layout.height + 10;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 42;
                if (veHUD.itemm.getSoSaoPhaLe() > 0) {
                    PopupH += 20;
                }
            }
            if (loaiItem == LoaiItem.AO || loaiItem == LoaiItem.QUAN || loaiItem == LoaiItem.GANG || loaiItem == LoaiItem.GIAY || loaiItem == LoaiItem.RADA) {
                if (!nhanVat.getHanhtinh().equals(veHUD.itemm.getHanhtinh())){
                    String ht = veHUD.itemm.getHanhtinh();
                    if (ht.equals("traidat")){ht = "Trái đất";}
                    if (ht.equals("xayda")){ht = "Sayda";}
                    if (ht.equals("namek")){ht = "Namếc";}
                    layout.setText(veHUD.fontTenSkill,"Dành cho "+ht);
                    PopupH += layout.height + 15;
                }
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                for (int i = 9; i < 12; i++ ){
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco,chisoduoccong[i] + "+"+veHUD.itemm.getChiso()[i]);
                        PopupH += layout.height + 15;
                    }
                    if (veHUD.itemm.getChiso()[i-6] > 0){
                        layout.setText(veHUD.fontSkillchuaco,chisoduoccong[i-6] + "+"+veHUD.itemm.getChiso()[i-6]);
                        PopupH += layout.height + 15;
                    }
                }

                for (int i = 6; i < 9; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        PopupH += layout.height + 15;
                    }
                }
                if (veHUD.itemm.getSetkichhoat() != null){
                    boolean fullSetNappa = "Nappa".equals(veHUD.skha)
                        && "Nappa".equals(veHUD.skhq)
                        && "Nappa".equals(veHUD.skhg)
                        && "Nappa".equals(veHUD.skhj)
                        && "Nappa".equals(veHUD.skhrada);
                    boolean fullSetSongoku = "Sôngôku".equals(veHUD.skha)
                        && "Sôngôku".equals(veHUD.skhq)
                        && "Sôngôku".equals(veHUD.skhg)
                        && "Sôngôku".equals(veHUD.skhj)
                        && "Sôngôku".equals(veHUD.skhrada);
                    boolean fullSetAyaka = "Dũng Sĩ Trong Băng Giá".equals(veHUD.skha)
                        && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhq)
                        && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhg)
                        && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhj)
                        && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhrada);
                    if (fullSetNappa || fullSetSongoku || fullSetAyaka) {
                        layout.setText(veHUD.fontSkillchuaco, "Set " + veHUD.itemm.getSetkichhoat());
                        PopupH += layout.height + 12;
                        layout.setText(veHUD.fontSkillchuaco, setkichhoat.get(veHUD.itemm.getSetkichhoat()));
                        PopupH += layout.height + 12;
                        layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                        PopupH += layout.height + 12;
                    } else {
                        layout.setText(veHUD.fontTenSkill, "Set " + veHUD.itemm.getSetkichhoat());
                        PopupH += layout.height + 12;
                        layout.setText(veHUD.fontTenSkill, setkichhoat.get(veHUD.itemm.getSetkichhoat()));
                        PopupH += layout.height + 12;
                        layout.setText(veHUD.fontTenSkill, "Không thể giao dịch");
                        PopupH += layout.height + 12;
                    }
                }
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh của bạn: " + duLieuNguoiChoi.getSucManh());
                        PopupH += layout.height + 10;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 42;
                if (veHUD.itemm.getSoSaoPhaLe() > 0) {
                    PopupH += 20;
                }
            }
            if (loaiItem == LoaiItem.CAITRANG || loaiItem == LoaiItem.AVATAR) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                for (int i = 0; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        PopupH += layout.height + 15;
                    }
                }
                layout.setText(veHUD.fontTenSkill, "Không thể giao dịch");
                PopupH += layout.height + 12;
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh của bạn: " + duLieuNguoiChoi.getSucManh());
                        PopupH += layout.height + 10;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 28;
            }
            if (loaiItem == LoaiItem.VANBAY) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getMoTa());
                PopupH += layout.height + 15;
                layout.setText(veHUD.fontTenSkill, "Không thể giao dịch");
                PopupH += layout.height + 12;
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh của bạn: " + duLieuNguoiChoi.getSucManh());
                        PopupH += layout.height + 10;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 40;
            }
            if (loaiItem == LoaiItem.BONGTAI) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                if (veHUD.itemm.getMoTa().contains("Rồng Thần")) {
                    layout.setText(veHUD.fontTenSkill, "Tăng 5% chỉ số");
                    PopupH += layout.height + 12;
                }
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh của bạn: " + duLieuNguoiChoi.getSucManh());
                        PopupH += layout.height + 10;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 40;
            }
            if (loaiItem == LoaiItem.NGOCRONG) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 50;
            }
            if (loaiItem == LoaiItem.HUYHIEU) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                for (int i = 0; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        PopupH += layout.height + 15;
                    }
                }
                layout.setText(veHUD.fontTenSkill, "Không thể giao dịch");
                PopupH += layout.height + 12;
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh của bạn: " + duLieuNguoiChoi.getSucManh());
                        PopupH += layout.height + 10;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 28;
            }
            if (loaiItem == LoaiItem.HOPQUA) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh của bạn: " + duLieuNguoiChoi.getSucManh());
                        PopupH += layout.height + 10;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 50;
            }
            if (loaiItem == LoaiItem.PHUTRO|| loaiItem == LoaiItem.VE_QUAY_NPC_HAIDANG) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 50;
            }
            if (loaiItem == LoaiItem.DEOLUNG || loaiItem == LoaiItem.AURA) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                for (int i = 0; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        PopupH += layout.height + 15;
                    }
                }
                layout.setText(veHUD.fontTenSkill, "Không thể giao dịch");
                PopupH += layout.height + 12;
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh của bạn: " + duLieuNguoiChoi.getSucManh());
                        PopupH += layout.height + 10;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 28;
            }
            if (loaiItem == LoaiItem.NANGSKILL) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                PopupH += layout.height + 15;
                layout.setText(veHUD.fontTenSkill, "Không thể giao dịch");
                PopupH += layout.height + 12;
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                        PopupH += layout.height + 10;
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh của bạn: " + duLieuNguoiChoi.getSucManh());
                        PopupH += layout.height + 10;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                PopupH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                PopupH += layout.height + 40;
            }
            // --- VẼ BACKGROUND BẰNG SHAPERENDERER ---

            // --- GIỚI HẠN VỊ TRÍ POPUP ---
            if ((PopupY - 120) < 0) {
                PopupY = 125;
            }
            if ((PopupY + PopupH) > 590) {
                PopupY = 590 - PopupH;
            }

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.rect(PopupX + xCongThem, PopupY, PopupW, PopupH);
            shapeRenderer.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            for (int i = 0; i < 2; i++) {
                shapeRenderer.rect(PopupX - i + xCongThem, PopupY - i, PopupW + i * 2, PopupH + i * 2);
            }
            shapeRenderer.end();

            batch.begin();
            // --- VẼ THÔNG TIN ITEM ---
            DecimalFormat dinhDang = new DecimalFormat("#,###");
            if (loaiItem == LoaiItem.GIAPLUYENTAP) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                layout.setText(veHUD.fontSkillchuaco, "Hiệu lực trong " + (veHUD.itemm.getHanSuDung() > 60f ? (int) (veHUD.itemm.getHanSuDung() / 60f) + " phút" : (int)(veHUD.itemm.getHanSuDung())+" giây"));
                veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                for (int i = 6; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height+5;
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh của bạn: " +  dinhDang.format(duLieuNguoiChoi.getSucManh()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;

                if (veHUD.itemm.getSoSaoPhaLe() > 0) {
                    float saoxanhW = veHUD.saoxanh.getWidth() * 0.5f;
                    float saoxanhH = veHUD.saoxanh.getHeight() * 0.5f;
                    float spacing = 40f;
                    int soSao = veHUD.itemm.getSoSaoPhaLe();

                    float totalW = (soSao - 1) * spacing + saoxanhW;

                    float startX = PopupX + (PopupW - totalW) / 2f;

                    for (int i = 0; i < soSao; i++) {
                        float drawX = startX + i * spacing;
                        if (i < veHUD.itemm.getSoSaoPhaLeCuongHoa()) {
                            batch.draw(veHUD.saoxanh, drawX + xCongThem, PopupY + PopupH - offsetY, saoxanhW, saoxanhH);
                        } else {
                            batch.draw(veHUD.saoden, drawX + xCongThem, PopupY + PopupH - offsetY, saoxanhW, saoxanhH);
                        }
                    }
                }
            } else if (loaiItem == LoaiItem.AO || loaiItem == LoaiItem.QUAN || loaiItem == LoaiItem.GANG || loaiItem == LoaiItem.GIAY || loaiItem == LoaiItem.RADA) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                if (!nhanVat.getHanhtinh().equals(veHUD.itemm.getHanhtinh())){
                    String ht = veHUD.itemm.getHanhtinh();
                    if (ht.equals("traidat")){ht = "Trái đất";}
                    if (ht.equals("xayda")){ht = "Sayda";}
                    if (ht.equals("namek")){ht = "Namếc";}
                    veHUD.fontTenSkill.setColor(203 / 255f, 0 / 255f, 26 / 255f, 1f);
                    layout.setText(veHUD.fontTenSkill,"Dành cho "+ht);
                    veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                    offsetY += layout.height + 12;
                }
                if (veHUD.itemm.getSoCap() > 0){
                    veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                    veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 50 + xCongThem, PopupY + PopupH - offsetY);
                    layout.setText(veHUD.fontTenSkill, "[+"+veHUD.itemm.getSoCap()+"]");
                    veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                    offsetY += layout.height + 12;
                } else {
                    veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                    veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                    offsetY += layout.height + 12;
                }
                for (int i = 9; i < 12; i++ ){
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco,chisoduoccong[i] + "+"+veHUD.itemm.getChiso()[i]);
                        veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height + 12;
                    }
                    if (veHUD.itemm.getChiso()[i-6] > 0){
                        if (i-6==4) {
                            layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i - 6] + "+" + veHUD.itemm.getChiso()[i - 6]);
                        } else {
                            layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i - 6] + "+" + veHUD.itemm.getChiso()[i - 6]+"%");
                        }
                        veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                if (veHUD.itemm.getSetkichhoat() != null) {
                    String set = veHUD.itemm.getSetkichhoat();
                    boolean isFullSet = false;

                    if ("Nappa".equals(set)) {
                        isFullSet = "Nappa".equals(veHUD.skha)
                            && "Nappa".equals(veHUD.skhq)
                            && "Nappa".equals(veHUD.skhg)
                            && "Nappa".equals(veHUD.skhj)
                            && "Nappa".equals(veHUD.skhrada);
                    } else if ("Sôngôku".equals(set)) {
                        isFullSet = "Sôngôku".equals(veHUD.skha)
                            && "Sôngôku".equals(veHUD.skhq)
                            && "Sôngôku".equals(veHUD.skhg)
                            && "Sôngôku".equals(veHUD.skhj)
                            && "Sôngôku".equals(veHUD.skhrada);
                    } else if ("Dũng Sĩ Trong Băng Giá".equals(set)) {
                        isFullSet = "Dũng Sĩ Trong Băng Giá".equals(veHUD.skha)
                            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhq)
                            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhg)
                            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhj)
                            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhrada);
                    }
                    // Hiển thị
                    layout.setText(veHUD.fontSkillchuaco, "Set " + set);
                    veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                    offsetY += layout.height + 12;

                    layout.setText(isFullSet ? veHUD.fontSkillchuaco : veHUD.fontTenSkill, setkichhoat.get(set));
                    (isFullSet ? veHUD.fontSkillchuaco : veHUD.fontTenSkill).draw(batch, layout,
                        PopupW + PopupX - layout.width - 15 + xCongThem,
                        PopupY + PopupH - offsetY);
                    offsetY += layout.height + 12;

                    layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                    veHUD.fontSkillchuaco.draw(batch, layout,
                        PopupW + PopupX - layout.width - 15 + xCongThem,
                        PopupY + PopupH - offsetY);
                    offsetY += layout.height + 12;
                }
                for (int i = 6; i < 9; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i]+"%");
                        veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height+5;
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh của bạn: " +  dinhDang.format(duLieuNguoiChoi.getSucManh()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 32;

                if (veHUD.itemm.getSoSaoPhaLe() > 0) {
                    float saoxanhW = veHUD.saoxanh.getWidth() * 0.5f;
                    float saoxanhH = veHUD.saoxanh.getHeight() * 0.5f;
                    float spacing = 40f;
                    int soSao = veHUD.itemm.getSoSaoPhaLe();

                    float totalW = (soSao - 1) * spacing + saoxanhW;

                    float startX = PopupX + (PopupW - totalW) / 2f;

                    for (int i = 0; i < soSao; i++) {
                        float drawX = startX + i * spacing;
                        if (i < veHUD.itemm.getSoSaoPhaLeCuongHoa()) {
                            batch.draw(veHUD.saoxanh, drawX + xCongThem, PopupY + PopupH - offsetY, saoxanhW, saoxanhH);
                        } else {
                            batch.draw(veHUD.saoden, drawX + xCongThem, PopupY + PopupH - offsetY, saoxanhW, saoxanhH);
                        }
                    }
                }
            } else if (loaiItem == LoaiItem.CAITRANG || loaiItem == LoaiItem.AVATAR) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    if (veHUD.itemm.getTexture().getHeight()*0.5f < 60) {
                        batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    } else {
                        batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.38f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.38f, veHUD.itemm.getTexture().getHeight() * 0.38f);
                    }
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                for (int i = 0; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        if (!chisoduoccong[i].equals("Giáp")) {
                            layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        } else {
                            layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i]);
                        }
                        veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height+5;
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh của bạn: " +  dinhDang.format(duLieuNguoiChoi.getSucManh()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;
            } else if (loaiItem == LoaiItem.VANBAY) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                layout.setText(veHUD.fontSkillchuaco, veHUD.itemm.getMoTa());
                veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;
                layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height+5;
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh của bạn: " +  dinhDang.format(duLieuNguoiChoi.getSucManh()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;
            } else if (loaiItem == LoaiItem.BONGTAI) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;
                if (veHUD.itemm.getMoTa().contains("Rồng Thần")) {
                    layout.setText(veHUD.fontSkillchuaco, "Tăng 5% chỉ số");
                    veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                    offsetY += layout.height + 12;
                }
                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height+5;
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh của bạn: " +  dinhDang.format(duLieuNguoiChoi.getSucManh()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    }
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;
            } else if (loaiItem == LoaiItem.NGOCRONG) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;
            } else if (loaiItem == LoaiItem.HUYHIEU) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                for (int i = 0; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        if (!chisoduoccong[i].equals("Giáp")) {
                            layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        } else {
                            layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i]);
                        }
                        veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height+5;
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh của bạn: " +  dinhDang.format(duLieuNguoiChoi.getSucManh()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    }
                }

                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;
            } else if (loaiItem == LoaiItem.HOPQUA) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height+5;
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh của bạn: " +  dinhDang.format(duLieuNguoiChoi.getSucManh()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    }
                }

                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;
            } else if (loaiItem == LoaiItem.PHUTRO|| loaiItem == LoaiItem.VE_QUAY_NPC_HAIDANG) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;
            } else if (loaiItem == LoaiItem.DEOLUNG || loaiItem == LoaiItem.AURA) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                for (int i = 0; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        if (!chisoduoccong[i].equals("Giáp")) {
                            layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        } else {
                            layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i]);
                        }
                        veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height+5;
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh của bạn: " +  dinhDang.format(duLieuNguoiChoi.getSucManh()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    }
                }

                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;
            } else if (loaiItem == LoaiItem.NANGSKILL) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), PopupX + 15 + xCongThem, PopupY + PopupH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                veHUD.fontSkillchuaco.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 12;

                if (veHUD.itemm.getSucManhYeuCau()>0) {
                    if (duLieuNguoiChoi.getSucManh() >= veHUD.itemm.getSucManhYeuCau()) {
                        layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    } else {
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height+5;
                        layout.setText(veHUD.fontMotaHanhTrang1, "Sức mạnh của bạn: " +  dinhDang.format(duLieuNguoiChoi.getSucManh()));
                        veHUD.fontMotaHanhTrang1.draw(batch, layout, PopupW + PopupX - layout.width - 15 + xCongThem, PopupY + PopupH - offsetY);
                        offsetY += layout.height;
                    }
                }

                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY - i * 1);
                }
                offsetY += layout.height + 25;

                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.fontMotaHanhTrang.draw(batch, layout, PopupX + (PopupW - layout.width) / 2f + xCongThem, PopupY + PopupH - offsetY);
                offsetY += layout.height + 30;
            }
        }
        nutTheoChucNang(batch, oHanhTrangDangChon, PopupX, PopupY, PopupW, PopupH, xCongThem);
    }

    public void nutTheoChucNang(SpriteBatch batch, int oHanhTrangDangChon, float PopupX, float PopupY, float PopupW, float PopupH, float xCongThem) {

        if (veHUD.trangThaiChucNangHUD == TrangThaiChucNangHUD.HANH_TRANG && veHUD.dangHienPopup ||
            veHUD.trangThaiChucNangHUD == TrangThaiChucNangHUD.CHUC_NANG && veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU && veHUD.dangChonHanhTrangPhai ||
            veHUD.daClickVaoNpc && veHUD.dangChonHanhTrangPhai && veHUD.dangHienPopupNhanVatPhai
        ) {
            if (veHUD.itemm!=null) {
                for (int i = 0; i < 2; i++) {
                    float nutX = 1 + i * 120 + xCongThem;
                    float nutY = PopupY - 115;
                    if (veHUD.nuthanhtrangchon == i + 1) {
                        Texture nutVe = veHUD.nutClickTimer3 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                        batch.draw(nutVe, nutX, nutY, 114, 114);
                    } else {
                        batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
                    }

                    veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    if (i == 0) {
                        if (oHanhTrangDangChon < 8) {
                            layout.setText(veHUD.font, "Lấy ra");
                            veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                        } else {
                            layout.setText(veHUD.font, "Sử dụng");
                            veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                        }
                    } else {
                        layout.setText(veHUD.font, "Bỏ ra");
                        veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                    }
                }
                if (oHanhTrangDangChon >= 8 &&
                    (veHUD.itemm.getLoai() == LoaiItem.AO ||
                        veHUD.itemm.getLoai() == LoaiItem.QUAN ||
                        veHUD.itemm.getLoai() == LoaiItem.GIAY ||
                        veHUD.itemm.getLoai() == LoaiItem.GANG ||
                        veHUD.itemm.getLoai() == LoaiItem.CAITRANG ||
                        veHUD.itemm.getLoai() == LoaiItem.AVATAR ||
                        veHUD.itemm.getLoai() == LoaiItem.RADA) && duLieuNguoiChoi.coDeTu()) {
                    float nutX = 1 + 2 * 120 + xCongThem;
                    float nutY = PopupY - 115;
                    if (veHUD.nuthanhtrangchon == 3) {
                        Texture nutVe = veHUD.nutClickTimer3 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                        batch.draw(nutVe, nutX, nutY, 114, 114);
                    } else {
                        batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
                    }
                    layout.setText(veHUD.font, "Cho đệ tử");
                    veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                }
                batch.end();
            }
        }
        if (veHUD.dangHienRuongDo && veHUD.dangChonHanhTrangPhai) {
            if (veHUD.itemm!=null) {
                for (int i = 0; i < 2; i++) {
                    float nutX = 1 + i * 120 + xCongThem;
                    float nutY = PopupY - 115;
                    if (veHUD.nuthanhtrangchon == i + 1) {
                        Texture nutVe = veHUD.nutClickTimer3 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                        batch.draw(nutVe, nutX, nutY, 114, 114);
                    } else {
                        batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
                    }

                    veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    if (i == 0) {
                        if (oHanhTrangDangChon < 8) {
                            layout.setText(veHUD.font, "Lấy ra");
                            veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                        } else {
                            layout.setText(veHUD.font, "Sử dụng");
                            veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                        }
                    } else {
                        layout.setText(veHUD.font, "Bỏ ra");
                        veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                    }
                }
                if (oHanhTrangDangChon >= 8) {
                    float nutX = 1 + 2 * 120 + xCongThem;
                    float nutY = PopupY - 115;
                    if (veHUD.nuthanhtrangchon == 3) {
                        Texture nutVe = veHUD.nutClickTimer3 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                        batch.draw(nutVe, nutX, nutY, 114, 114);
                    } else {
                        batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
                    }
                    layout.setText(veHUD.font, "Cất vào \n rương");
                    veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114/2f + layout.height/2f);
                }
                batch.end();
            }
        }
        if (veHUD.dangChonHanhTrangTrai && veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU ||
            veHUD.dangChonHanhTrangTrai && veHUD.dangHienRuongDo
        ) {
            if (veHUD.itemm!=null) {
                for (int i = 0; i < 2; i++) {
                    float nutX = 1 + i * 120 + xCongThem;
                    float nutY = PopupY - 115;
                    if (veHUD.nuthanhtrangchon == i+4) {
                        Texture nutVe = veHUD.nutClickTimer3 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                        batch.draw(nutVe, nutX, nutY, 114, 114);
                    } else {
                        batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
                    }

                    veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    if (i == 0) {
                        layout.setText(veHUD.font, "Lấy ra");
                        veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                    } else {
                        layout.setText(veHUD.font, "Đóng");
                        veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                    }
                }
            }
            batch.end();
        }
        if (veHUD.dangChonHanhTrangTrai && veHUD.daClickVaoNpc) {
            if (veHUD.npcHienTai.npcHUDrender.ui_npc instanceof admin_thanhle) {
                admin_thanhle ui = (admin_thanhle) veHUD.npcHienTai.npcHUDrender.ui_npc;
                if (ui.trangThai == TrangThaiChucNang_admin_thanhle.CUA_HANG) {
                    if (veHUD.itemm != null) {
                        for (int i = 0; i < 2; i++) {
                            float nutX = 1 + i * 120 + xCongThem;
                            float nutY = PopupY - 115;
                            if (ui.nutDuocChonClickMuaDo == i) {
                                Texture nutVe = ui.timeChoMuaDo > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                                batch.draw(nutVe, nutX, nutY, 114, 114);
                            } else {
                                batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
                            }

                            veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                            if (i == 0) {
                                layout.setText(veHUD.font, "Mua");
                                veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                            } else {
                                layout.setText(veHUD.font, "Đóng");
                                veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                            }
                        }
                    }
                    batch.end();
                }
            }
        }
    }
}
