package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import java.text.DecimalFormat;

public class HUDPopupThongTin {
    private final VeHUD veHUD;
    private DuLieuNguoiChoi duLieuNguoiChoi;
    private NhanVat nhanVat;
    private GlyphLayout layout;
    public HUDPopupThongTin(VeHUD veHUD, GlyphLayout layout, DuLieuNguoiChoi duLieuNguoiChoi , NhanVat nhanVat) {
        this.veHUD = veHUD;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
        this.nhanVat = nhanVat;
        this.layout = layout;
    }
    public void PopupThongTin(ShapeRenderer shapeRenderer, SpriteBatch batch, float x, float y , float width, float height , int oChiSoDangChon) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++) {
            shapeRenderer.rect(x - i, y - i, width + i * 2, height + i * 2);
        }
        shapeRenderer.end();
        batch.begin();
        if (oChiSoDangChon<5){
            if (oChiSoDangChon != 4) {
                DecimalFormat dinhDang = new DecimalFormat("#,###");
                String[] tenChiSo = {"HP", "KI", "SĐ", "Giáp"};
                int[] buocTangTheoChiSo = {20, 20, 1, 1}; // mỗi lần tăng bao nhiêu cho từng chỉ số
                int buocTang = buocTangTheoChiSo[oChiSoDangChon];
                //xử lí text thông tin
                String[] textChiSoCoBan2 = {
                    dinhDang.format(duLieuNguoiChoi.getHpGoc()+1000),
                    dinhDang.format(duLieuNguoiChoi.getKiGoc()+1000),
                    dinhDang.format(duLieuNguoiChoi.getSucDanhGoc()*100),
                    dinhDang.format(500000+duLieuNguoiChoi.getGiapGoc()*100000),
                };
                layout.setText(veHUD.fontCapSKill,"Sử dụng "+textChiSoCoBan2[oChiSoDangChon]+" tiềm năng");
                veHUD.fontCapSKill.draw(batch,layout,veHUD.PopupThongTinX+(veHUD.PopupThongTinW- layout.width)/2f,veHUD.PopupThongTinY+53);
                layout.setText(veHUD.fontCapSKill,"để tăng "+buocTang+" "+tenChiSo[oChiSoDangChon]+" gốc");
                veHUD.fontCapSKill.draw(batch,layout,veHUD.PopupThongTinX+(veHUD.PopupThongTinW- layout.width)/2f,veHUD.PopupThongTinY+33);
                //xử lí nút
                float[] chiSoGocArray = {
                    duLieuNguoiChoi.getHpGoc(),
                    duLieuNguoiChoi.getKiGoc(),
                    duLieuNguoiChoi.getSucDanhGoc(),
                    duLieuNguoiChoi.getGiapGoc()
                };
                int chiSoGoc = (int) chiSoGocArray[oChiSoDangChon];
                String ten = tenChiSo[oChiSoDangChon];

                int[] tangGiaTri = new int[3];
                long[] chiPhiTiemNang = new long[3];

                for (int i = 0; i < 3; i++) {
                    int soLanTang = (int) Math.pow(10, i); // x1, x10, x100
                    tangGiaTri[i] = buocTang * soLanTang;
                    chiPhiTiemNang[i] = veHUD.tinhChiPhiTiemNang(oChiSoDangChon, chiSoGoc, soLanTang, buocTang);
                }
                for (int i = 0; i < 3; i++) {
                    float nutX = 1 + i * 120;
                    float nutY = y - 115;
                    if (veHUD.nutduocchon==i) {
                        Texture nutVe = veHUD.nutClickTimer > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                        batch.draw(nutVe , nutX, nutY, 114, 114);
                    }
                    else {
                        batch.draw(veHUD.nutvuong , nutX, nutY, 114, 114);
                    }

                    veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);

                    layout.setText(veHUD.font, "Tăng");
                    veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 30);

                    layout.setText(veHUD.font, tangGiaTri[i] + ten);
                    veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 55);

                    layout.setText(veHUD.font, "-"+veHUD.formatVangNgoc(chiPhiTiemNang[i]));
                    veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 80);
                }

            } else {
                layout.setText(veHUD.fontCapSKill,"Sử dụng "+veHUD.formatVangNgoc(30000000+(duLieuNguoiChoi.getChiMangGoc()-1)*5000000000L)+" tiềm năng");
                veHUD.fontCapSKill.draw(batch,layout,veHUD.PopupThongTinX+(veHUD.PopupThongTinW- layout.width)/2f,veHUD.PopupThongTinY+53);
                layout.setText(veHUD.fontCapSKill,"để tăng "+1+" "+"chí mạng gốc");
                veHUD.fontCapSKill.draw(batch,layout,veHUD.PopupThongTinX+(veHUD.PopupThongTinW- layout.width)/2f,veHUD.PopupThongTinY+33);
                Texture nutVe = veHUD.nutClickTimer > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                batch.draw(nutVe,1,y - 115, 114, 114);
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font, "Tăng");
                veHUD.font.draw(batch, layout, 1 + (114 - layout.width) / 2f, y - 115 + 114 - 30);
                layout.setText(veHUD.font, "1 Crit");
                veHUD.font.draw(batch, layout, 1 + (114 - layout.width) / 2f, y - 115 + 114 - 55);
                layout.setText(veHUD.font, veHUD.formatVangNgoc(30000000 + (duLieuNguoiChoi.getChiMangGoc() - 1) * 5000000000L) + "");
                veHUD.font.draw(batch, layout, 1 + (114 - layout.width) / 2f, y - 115 + 114 - 80);
            }
        } else if (oChiSoDangChon>5 && oChiSoDangChon <= 15) {
            int capskill = duLieuNguoiChoi.getCapSkill(oChiSoDangChon-6);
            if (capskill >= 1){
                // Mo ta skill
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill,duLieuNguoiChoi.getTenSkill(oChiSoDangChon-6));
                veHUD.fontTenSkill.draw(batch,layout,veHUD.PopupThongTinX+(veHUD.PopupThongTinW- layout.width)/2f,veHUD.PopupThongTinY+veHUD.PopupThongTinH-23);

                layout.setText(veHUD.fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[0]);
                veHUD.fontMotaNganSkill.draw(batch,layout,veHUD.PopupThongTinX+(veHUD.PopupThongTinW- layout.width)/2f,veHUD.PopupThongTinY+veHUD.PopupThongTinH-48);
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font,"____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f, veHUD.PopupThongTinY + veHUD.PopupThongTinH - 60 - i*1);
                }

                layout.setText(veHUD.fontSkilldaco,"Cấp độ: "+capskill);
                veHUD.fontSkilldaco.draw(batch,layout,veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f,veHUD.PopupThongTinY + veHUD.PopupThongTinH - 95);

                layout.setText(veHUD.fontMotaNganSkill1,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[1]);
                veHUD.fontMotaNganSkill1.draw(batch,layout,veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f,veHUD.PopupThongTinY + veHUD.PopupThongTinH - 120);

                layout.setText(veHUD.fontMotaNganSkill1,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[2]);
                veHUD.fontMotaNganSkill1.draw(batch,layout,veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f,veHUD.PopupThongTinY + veHUD.PopupThongTinH - 145);

                layout.setText(veHUD.fontMotaNganSkill1,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[3]);
                veHUD.fontMotaNganSkill1.draw(batch,layout,veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f,veHUD.PopupThongTinY + veHUD.PopupThongTinH - 170);

                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font,"____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f, veHUD.PopupThongTinY + veHUD.PopupThongTinH - 182 - i*1);
                }

                layout.setText(veHUD.fontTenSkill,"Đã mở khóa skill");
                veHUD.fontTenSkill.draw(batch,layout,veHUD.PopupThongTinX+(veHUD.PopupThongTinW- layout.width)/2f,veHUD.PopupThongTinY+veHUD.PopupThongTinH-217);

                for (int i = 0; i < 2; i++) {
                    float nutX = 1 + i * 120;
                    float nutY = y - 115;
                    if (veHUD.nutduocchon == i+1) {
                        Texture nutVe = veHUD.nutClickTimer1 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                        batch.draw(nutVe, nutX, nutY, 114, 114);
                    } else {
                        batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
                    }

                    veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                    if (i == 0) {
                        layout.setText(veHUD.font, "Gán ô");
                        veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 40);

                        layout.setText(veHUD.font, "Phím tắt");
                        veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 68);
                    } else {
                        layout.setText(veHUD.font, "Đóng");
                        veHUD.font.draw(batch, layout, nutX + (114 - layout.width) / 2f, nutY + 114 - 52);
                    }
                }
            } else {
                // Mo ta skill
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.fontTenSkill,duLieuNguoiChoi.getTenSkill(oChiSoDangChon-6));
                veHUD.fontTenSkill.draw(batch,layout,veHUD.PopupThongTinX+(veHUD.PopupThongTinW- layout.width)/2f,veHUD.PopupThongTinY+veHUD.PopupThongTinH-23);

                layout.setText(veHUD.fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[0]);
                veHUD.fontMotaNganSkill.draw(batch,layout,veHUD.PopupThongTinX+(veHUD.PopupThongTinW- layout.width)/2f,veHUD.PopupThongTinY+veHUD.PopupThongTinH-48);
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font,"____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f, veHUD.PopupThongTinY + veHUD.PopupThongTinH - 60 - i*1);
                }

                layout.setText(veHUD.fontSkilldaco,"Chưa học");
                veHUD.fontSkilldaco.draw(batch,layout,veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f,veHUD.PopupThongTinY + veHUD.PopupThongTinH - 95);

                layout.setText(veHUD.fontSkillchuaco1,"Để học cần đủ sức mạnh");
                veHUD.fontSkillchuaco1.draw(batch,layout,veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f,veHUD.PopupThongTinY + veHUD.PopupThongTinH - 120);

                layout.setText(veHUD.fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[1]);
                veHUD.fontMotaNganSkill.draw(batch,layout,veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f,veHUD.PopupThongTinY + veHUD.PopupThongTinH - 145);

                layout.setText(veHUD.fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[2]);
                veHUD.fontMotaNganSkill.draw(batch,layout,veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f,veHUD.PopupThongTinY + veHUD.PopupThongTinH - 170);

                layout.setText(veHUD.fontMotaNganSkill,duLieuNguoiChoi.getMotaSKill(oChiSoDangChon-6)[3]);
                veHUD.fontMotaNganSkill.draw(batch,layout,veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f,veHUD.PopupThongTinY + veHUD.PopupThongTinH - 195);
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(veHUD.font,"____________________________________");
                for (int i = 0; i < 2; i++) {
                    veHUD.font.draw(batch, layout, veHUD.PopupThongTinX + (veHUD.PopupThongTinW - layout.width) / 2f, veHUD.PopupThongTinY + veHUD.PopupThongTinH - 207 - i*1);
                }

                Texture nutVe = veHUD.nutClickTimer1 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                batch.draw(nutVe,1,y - 115, 114, 114);
                layout.setText(veHUD.font, "Đóng");
                veHUD.font.draw(batch, layout, 1 + (114 - layout.width) / 2f, y - 115 + 114 - 52);
            }
        }
        batch.end();
    }
}
