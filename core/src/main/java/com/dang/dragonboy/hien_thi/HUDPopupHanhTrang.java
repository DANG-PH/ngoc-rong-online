package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.nhan_vat.NhanVat;

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
    public void PopupHanhTrang(ShapeRenderer shapeRenderer, SpriteBatch batch, float x, float y , float width , int oHanhTrangDangChon) {
        String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng","Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
        Map<String, String> setkichhoat = new HashMap<>();
        setkichhoat.put("Nappa", "(5 món +80% HP)");
        setkichhoat.put("Songoku", "(5 món +100% Kamejoko)");
        veHUD.PopupHanhTrangH = 0;
        if (veHUD.itemm!=null) {
            if ("giapluyentap".equals(veHUD.itemDangChon)) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.PopupHanhTrangH += layout.height + 15;
                layout.setText(veHUD.fontSkillchuaco, "Hiệu lực trong " + (int) (veHUD.timeMacGiapLuyenTap / 60f) + " phút");
                veHUD.PopupHanhTrangH += layout.height + 15;
                for (int i = 6; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        veHUD.PopupHanhTrangH += layout.height + 15;
                    }
                }
                layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                veHUD.PopupHanhTrangH += layout.height + 10;
                layout.setText(veHUD.font, "____________________________________");
                veHUD.PopupHanhTrangH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.PopupHanhTrangH += layout.height + 14;
                if (veHUD.itemm.getSoSaoPhaLe() > 0) {
                    veHUD.PopupHanhTrangH += 20;
                }
            }
            if ("ao".equals(veHUD.itemDangChon) || "quan".equals(veHUD.itemDangChon) || "giay".equals(veHUD.itemDangChon) || "gang".equals(veHUD.itemDangChon) || "rada".equals(veHUD.itemDangChon)) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.PopupHanhTrangH += layout.height + 15;
                for (int i = 9; i < 12; i++ ){
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco,chisoduoccong[i] + "+"+veHUD.itemm.getChiso()[i]);
                        veHUD.PopupHanhTrangH += layout.height + 15;
                    }
                    if (veHUD.itemm.getChiso()[i-6] > 0){
                        layout.setText(veHUD.fontSkillchuaco,chisoduoccong[i-6] + "+"+veHUD.itemm.getChiso()[i-6]);
                        veHUD.PopupHanhTrangH += layout.height + 15;
                    }
                }

                for (int i = 6; i < 9; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        veHUD.PopupHanhTrangH += layout.height + 15;
                    }
                }
                if (veHUD.itemm.getSetkichhoat() != null){
                    boolean fullSetNappa = "Nappa".equals(veHUD.skha)
                        && "Nappa".equals(veHUD.skhq)
                        && "Nappa".equals(veHUD.skhg)
                        && "Nappa".equals(veHUD.skhj)
                        && "Nappa".equals(veHUD.skhrada);
                    if (fullSetNappa) {
                        layout.setText(veHUD.fontSkillchuaco, "Set " + veHUD.itemm.getSetkichhoat());
                        veHUD.PopupHanhTrangH += layout.height + 12;
                        layout.setText(veHUD.fontSkillchuaco, setkichhoat.get(veHUD.itemm.getSetkichhoat()));
                        veHUD.PopupHanhTrangH += layout.height + 12;
                        layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                        veHUD.PopupHanhTrangH += layout.height + 12;
                    } else {
                        layout.setText(veHUD.fontTenSkill, "Set " + veHUD.itemm.getSetkichhoat());
                        veHUD.PopupHanhTrangH += layout.height + 12;
                        layout.setText(veHUD.fontTenSkill, setkichhoat.get(veHUD.itemm.getSetkichhoat()));
                        veHUD.PopupHanhTrangH += layout.height + 12;
                        layout.setText(veHUD.fontTenSkill, "Không thể giao dịch");
                        veHUD.PopupHanhTrangH += layout.height + 12;
                    }
                }
                layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                veHUD.PopupHanhTrangH += layout.height + 10;
                layout.setText(veHUD.font, "____________________________________");
                veHUD.PopupHanhTrangH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.PopupHanhTrangH += layout.height + 42;
                if (veHUD.itemm.getSoSaoPhaLe() > 0) {
                    veHUD.PopupHanhTrangH += 20;
                }
            }
            if ("caitrang".equals(veHUD.itemDangChon) || "avatar".equals(veHUD.itemDangChon)) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.PopupHanhTrangH += layout.height + 15;
                for (int i = 6; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        veHUD.PopupHanhTrangH += layout.height + 15;
                    }
                }
                layout.setText(veHUD.fontTenSkill, "Không thể giao dịch");
                veHUD.PopupHanhTrangH += layout.height + 12;
                layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                veHUD.PopupHanhTrangH += layout.height + 10;
                layout.setText(veHUD.font, "____________________________________");
                veHUD.PopupHanhTrangH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.PopupHanhTrangH += layout.height + 28;
            }
            if ("vanbay".equals(veHUD.itemDangChon)) {
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.PopupHanhTrangH += layout.height + 15;
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getMoTa());
                veHUD.PopupHanhTrangH += layout.height + 15;
                layout.setText(veHUD.fontTenSkill, "Không thể giao dịch");
                veHUD.PopupHanhTrangH += layout.height + 12;
                layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + veHUD.itemm.getSucManhYeuCau());
                veHUD.PopupHanhTrangH += layout.height + 10;
                layout.setText(veHUD.font, "____________________________________");
                veHUD.PopupHanhTrangH += layout.height + 15;
                layout.setText(
                    veHUD.fontMotaHanhTrang,
                    veHUD.itemm.getMoTa(),
                    veHUD.fontMotaHanhTrang.getColor(),
                    330,
                    Align.left,
                    true
                );
                veHUD.PopupHanhTrangH += layout.height + 28;
            }
            // --- VẼ BACKGROUND BẰNG SHAPERENDERER ---
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.rect(x, y, width, veHUD.PopupHanhTrangH);
            shapeRenderer.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            for (int i = 0; i < 2; i++) {
                shapeRenderer.rect(x - i, y - i, width + i * 2, veHUD.PopupHanhTrangH + i * 2);
            }
            shapeRenderer.end();

            // --- GIỚI HẠN VỊ TRÍ POPUP ---
            if ((veHUD.PopupHanhTrangY - 120) < 0) {
                veHUD.PopupHanhTrangY = 125;
            }
            if ((veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH) > 590) {
                veHUD.PopupHanhTrangY = 590 - veHUD.PopupHanhTrangH;
            }

            batch.begin();
            // --- VẼ THÔNG TIN ITEM ---
            DecimalFormat dinhDang = new DecimalFormat("#,###");
            if ("giapluyentap".equals(veHUD.itemDangChon)) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), veHUD.PopupHanhTrangX + 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;

                layout.setText(veHUD.fontSkillchuaco, "Hiệu lực trong " + (int) (veHUD.timeMacGiapLuyenTap / 60f) + " phút");
                veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;

                for (int i = 6; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0 && i != 8) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                veHUD.fontMotaHanhTrang.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height;
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - layout.width) / 2f, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY - i * 1);
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
                veHUD.fontMotaHanhTrang.draw(batch, layout, veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - layout.width) / 2f, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 30;

                if (veHUD.itemm.getSoSaoPhaLe() > 0) {
                    float saoxanhW = veHUD.saoxanh.getWidth() * 0.5f;
                    float saoxanhH = veHUD.saoxanh.getHeight() * 0.5f;
                    float spacing = 40f;
                    int soSao = veHUD.itemm.getSoSaoPhaLe();

                    float totalW = (soSao - 1) * spacing + saoxanhW;

                    float startX = veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - totalW) / 2f;

                    for (int i = 0; i < soSao; i++) {
                        float drawX = startX + i * spacing;
                        if (i < veHUD.itemm.getSoSaoPhaLeCuongHoa()) {
                            batch.draw(veHUD.saoxanh, drawX, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY, saoxanhW, saoxanhH);
                        } else {
                            batch.draw(veHUD.saoden, drawX, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY, saoxanhW, saoxanhH);
                        }
                    }
                }
            } else if ("ao".equals(veHUD.itemDangChon) || "quan".equals(veHUD.itemDangChon) || "giay".equals(veHUD.itemDangChon) || "gang".equals(veHUD.itemDangChon) || "rada".equals(veHUD.itemDangChon)) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), veHUD.PopupHanhTrangX + 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                if (veHUD.itemm.getSoCap() > 0){
                    veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                    veHUD.fontTenSkill.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 50, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                    layout.setText(veHUD.fontTenSkill, "[+"+veHUD.itemm.getSoCap()+"]");
                    veHUD.fontTenSkill.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                    offsetY += layout.height + 12;
                } else {
                    veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                    veHUD.fontTenSkill.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                    offsetY += layout.height + 12;
                }
                for (int i = 9; i < 12; i++ ){
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco,chisoduoccong[i] + "+"+veHUD.itemm.getChiso()[i]);
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                    if (veHUD.itemm.getChiso()[i-6] > 0){
                        layout.setText(veHUD.fontSkillchuaco,chisoduoccong[i-6] + "+"+veHUD.itemm.getChiso()[i-6]);
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                if (veHUD.itemm.getSetkichhoat() != null){
                    boolean fullSetNappa = "Nappa".equals(veHUD.skha)
                        && "Nappa".equals(veHUD.skhq)
                        && "Nappa".equals(veHUD.skhg)
                        && "Nappa".equals(veHUD.skhj)
                        && "Nappa".equals(veHUD.skhrada);
                    if (fullSetNappa) {
                        layout.setText(veHUD.fontSkillchuaco, "Set " + veHUD.itemm.getSetkichhoat());
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                        layout.setText(veHUD.fontSkillchuaco, setkichhoat.get(veHUD.itemm.getSetkichhoat()));
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                        layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    } else {
                        veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                        layout.setText(veHUD.fontSkillchuaco, "Set " + veHUD.itemm.getSetkichhoat());
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                        layout.setText(veHUD.fontTenSkill, setkichhoat.get(veHUD.itemm.getSetkichhoat()));
                        veHUD.fontTenSkill.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                        layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                for (int i = 6; i < 9; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i]+"%");
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                veHUD.fontMotaHanhTrang.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height;
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - layout.width) / 2f, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY - i * 1);
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
                veHUD.fontMotaHanhTrang.draw(batch, layout, veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - layout.width) / 2f, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 32;

                if (veHUD.itemm.getSoSaoPhaLe() > 0) {
                    float saoxanhW = veHUD.saoxanh.getWidth() * 0.5f;
                    float saoxanhH = veHUD.saoxanh.getHeight() * 0.5f;
                    float spacing = 40f;
                    int soSao = veHUD.itemm.getSoSaoPhaLe();

                    float totalW = (soSao - 1) * spacing + saoxanhW;

                    float startX = veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - totalW) / 2f;

                    for (int i = 0; i < soSao; i++) {
                        float drawX = startX + i * spacing;
                        if (i < veHUD.itemm.getSoSaoPhaLeCuongHoa()) {
                            batch.draw(veHUD.saoxanh, drawX, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY, saoxanhW, saoxanhH);
                        } else {
                            batch.draw(veHUD.saoden, drawX, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY, saoxanhW, saoxanhH);
                        }
                    }
                }
            } else if ("caitrang".equals(veHUD.itemDangChon) || "avatar".equals(veHUD.itemDangChon)) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    if (veHUD.itemm.getTexture().getHeight()*0.5f < 60) {
                        batch.draw(veHUD.itemm.getTexture(), veHUD.PopupHanhTrangX + 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    } else {
                        batch.draw(veHUD.itemm.getTexture(), veHUD.PopupHanhTrangX + 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - veHUD.itemm.getTexture().getHeight() * 0.38f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.38f, veHUD.itemm.getTexture().getHeight() * 0.38f);
                    }
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;

                for (int i = 6; i <= 12; i++) {
                    if (veHUD.itemm.getChiso()[i] > 0) {
                        layout.setText(veHUD.fontSkillchuaco, chisoduoccong[i] + "+" + veHUD.itemm.getChiso()[i] + "%");
                        veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                        offsetY += layout.height + 12;
                    }
                }
                layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;
                layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                veHUD.fontMotaHanhTrang.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height;
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - layout.width) / 2f, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY - i * 1);
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
                veHUD.fontMotaHanhTrang.draw(batch, layout, veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - layout.width) / 2f, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 30;
            } else if ("vanbay".equals(veHUD.itemDangChon)) {
                float offsetY = 10;
                if (veHUD.itemm.getTexture() != null) {
                    batch.draw(veHUD.itemm.getTexture(), veHUD.PopupHanhTrangX + 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - veHUD.itemm.getTexture().getHeight() * 0.5f - offsetY, veHUD.itemm.getTexture().getWidth() * 0.5f, veHUD.itemm.getTexture().getHeight() * 0.5f);
                    offsetY += 10;
                }
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill, veHUD.itemm.getTenItem());
                veHUD.fontTenSkill.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;

                layout.setText(veHUD.fontSkillchuaco, veHUD.itemm.getMoTa());
                veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;
                layout.setText(veHUD.fontSkillchuaco, "Không thể giao dịch");
                veHUD.fontSkillchuaco.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 12;
                layout.setText(veHUD.fontMotaHanhTrang, "Sức mạnh yêu cầu: " + dinhDang.format(veHUD.itemm.getSucManhYeuCau()));
                veHUD.fontMotaHanhTrang.draw(batch, layout, veHUD.PopupHanhTrangW + veHUD.PopupHanhTrangX - layout.width - 15, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height;
                layout.setText(veHUD.font, "____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - layout.width) / 2f, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY - i * 1);
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
                veHUD.fontMotaHanhTrang.draw(batch, layout, veHUD.PopupHanhTrangX + (veHUD.PopupHanhTrangW - layout.width) / 2f, veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH - offsetY);
                offsetY += layout.height + 30;
            }
        }
        if (veHUD.itemm!=null) {
            if (oHanhTrangDangChon != 7) {
                for (int i = 0; i < 2; i++) {
                    float nutX = 1 + i * 120;
                    float nutY = y - 115;
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
            } else {
                float nutX = 1 ;
                float nutY = y - 115;
                if (veHUD.nuthanhtrangchon == 3) {
                    Texture nutVe = veHUD.nutClickTimer3 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                    batch.draw(nutVe, nutX, nutY, 114, 114);
                } else {
                    batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
                }
                layout.setText(veHUD.font, "Đóng");
                veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
            }
        }
        batch.end();
    }
}
