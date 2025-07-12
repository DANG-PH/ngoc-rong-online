package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HUDPopupRenderer {
    private final VeHUD veHUD;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    private DuLieuNguoiChoi duLieuNguoiChoi;
    public HUDPopupRenderer(VeHUD veHUD,GlyphLayout layout,DuLieuNguoiChoi duLieuNguoiChoi) {
        shapeRenderer = new ShapeRenderer();
        this.veHUD = veHUD;
        this.layout = layout;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
    }

    public void renderPopup(SpriteBatch batch) {
        if (!veHUD.dangHienPopup || veHUD.texAvt == null) return;
        //avt + nut X
        batch.draw(veHUD.popupNhanVat, 0, 0, 350, 610);
        float nutXW = veHUD.nutX.getWidth() * 0.5f;
        float nutXH = veHUD.nutX.getHeight() * 0.55f;
        batch.draw(veHUD.nutX, 350 - nutXW - 6, 610 - nutXH - 2, nutXW, nutXH - 5);

        float texAvtW = veHUD.texAvt.getWidth() * 0.52f;
        float texAvtH = veHUD.texAvt.getHeight() * 0.52f;
        batch.draw(veHUD.texAvt, 0, 505, texAvtW, texAvtH);

        // ===== Vẽ vàng, ngọc =====
        batch.draw(veHUD.vang, 10, 8, 20, 20);
        batch.draw(veHUD.ngoc, 275, 7, 20, 20);

        // → Định dạng rút gọn vàng
        String vangHienThi = veHUD.formatVangNgoc(duLieuNguoiChoi.getVang());
        layout.setText(veHUD.fontvangngoc, vangHienThi);
        veHUD.fontvangngoc.draw(batch, layout, 10 + 20 + 10, 22);

        // → Định dạng rút gọn ngọc
        String ngocHienThi = veHUD.formatVangNgoc(duLieuNguoiChoi.getNgoc());
        layout.setText(veHUD.fontvangngoc, ngocHienThi);
        veHUD.fontvangngoc.draw(batch, layout, 275 + 20 + 10, 22);

        // chuc nang
        String[] TextChucnang1 = {
            "Nhiệm",
            "Hành",
            "Kỹ",
            "Bang",
            "Chức"
        };
        String[] TextChucnang2 = {
            "Vụ",
            "Trang",
            "Năng",
            "Hội",
            "năng"
        };
        for (int i = 0; i < 5; i++) {
            Texture nutcn = veHUD.chucNangDangChon==i ? veHUD.nutchucnangclick : veHUD.nutchucnang;
            batch.draw(nutcn, 2+i*68+3, 450, 68, 52);
            layout.setText(veHUD.fontChucnang,TextChucnang1[i]);
            veHUD.fontChucnang.draw(batch,layout,2+i*68+3+(68- layout.width)/2f,450 + 41);
            layout.setText(veHUD.fontChucnang,TextChucnang2[i]);
            veHUD.fontChucnang.draw(batch,layout,2+i*68+3+(68- layout.width)/2f,450 + 20);
        }
        // noi dung theo chuc nang
        if (veHUD.chucNangDangChon == 0){
            // Tên nhân vật + thể lực + Cấp bậc + Sức mạnh
            veHUD.font.setColor(1,1,1,1);
            layout.setText(veHUD.font, duLieuNguoiChoi.getTen());
            veHUD.font.draw(batch,layout,125,595);

            layout.setText(veHUD.fontsm,"Thể lực");
            veHUD.fontsm.draw(batch,layout,125,570);
            batch.draw(veHUD.thanhtheluc ,125+68,556);
            layout.setText(veHUD.fontsm, duLieuNguoiChoi.getCapBac());
            veHUD.fontsm.draw(batch,layout,125,545);
            // ===== Vẽ sức mạnh =====
            DecimalFormat dinhDang = new DecimalFormat("#,###");

            long sucManh = duLieuNguoiChoi.getSucManh();
            String sucManhHienThi = dinhDang.format(sucManh);
            layout.setText(veHUD.fontsm, "Sức mạnh: " + sucManhHienThi);
            veHUD.fontsm.draw(batch, layout, 125, 520);
            // chỉnh OOP theo nhiệm vụ sau cấu trúc ( mô tả, nhiệm vụ cần làm , mô tả dài )
            //mo ta
            layout.setText(veHUD.fontNhiemVu,"Nhiệm vụ tập luyện"); // cần thay đổi sau
            veHUD.fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);

            // nhiệm vụ có nhiều phần , chỉ hiện thị phần hiện tại và đã làm
            layout.setText(veHUD.fontNhiemVu1,"- Đánh ngã 5 mộc nhân (0/5)"); // cần thay đổi sau
            veHUD.fontNhiemVu1.draw(batch,layout,20,385);
            layout.setText(veHUD.fontNhiemVuChuaLam,"- ...");
            veHUD.fontNhiemVuChuaLam.draw(batch,layout,20,385-25);

            // mo ta dai
            layout.setText(veHUD.fontMotaNhiemVu,
                "Mộc nhân được đặt nhiều tại Làng Aru, " +
                    "ngay trước nhà ông Gohan " +
                    "Hãy đánh ngã 5 mộc nhân, sau đó quay " +
                    "về nhà báo cáo với ông Gohan " +
                    "Để đánh, hãy click đôi vào đối tượng \n" +
                    "Thưởng 3 k sức mạnh \n" +
                    "Thưởng 3 k tiềm năng",
                veHUD.fontMotaNhiemVu.getColor(), // dùng lại màu đã set // cần thay đổi sau
                290,                 // wrapWidth
                Align.left,          // căn trái mặc định
                true);               // bật tự xuống dòng
            veHUD.fontMotaNhiemVu.draw(batch,layout,20,385-25-30);
        }
        if (veHUD.chucNangDangChon == 1){
            // chỉ số nhân vật
            layout.setText(veHUD.fontsm,"HP: "+(int)duLieuNguoiChoi.getHpHienTai()+" / "+(int)duLieuNguoiChoi.getHpToiDa());
            veHUD.fontsm.draw(batch,layout,125,595);
            layout.setText(veHUD.fontsm,"KI: "+(int)duLieuNguoiChoi.getKiHienTai()+" / "+(int)duLieuNguoiChoi.getKiToiDa());
            veHUD.fontsm.draw(batch,layout,125,570);
            layout.setText(veHUD.fontsm,"Sức đánh: "+(int)duLieuNguoiChoi.getSucDanhNhanVat()+", Crit: "+duLieuNguoiChoi.getChiMangNhanVat()+"%");
            veHUD.fontsm.draw(batch,layout,125,545);
            layout.setText(veHUD.fontsm,"Giáp: "+(int)duLieuNguoiChoi.getGiapNhanVat()+", ST Crit: "+(int)duLieuNguoiChoi.getSatThuongChiMang()+"%");
            veHUD.fontsm.draw(batch,layout,125,520);

            // ô hành trang
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;

            int soTrangBi = 8;
            ArrayList<Item> danhSachItem = duLieuNguoiChoi.getHanhTrang();
            int soKhac = 22;
            int tongSoTrangBi = soTrangBi + soKhac;

            float totalHeight = tongSoTrangBi * KhoangCachItem;
            veHUD.maxScroll = Math.max(0, totalHeight - viewHeight);

            batch.flush();
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
            Gdx.gl.glScissor(0, (int)viewY, 350, (int)viewHeight);
            // Vị trí bắt đầu vẽ từ trên xuống
            float startY = viewY + viewHeight - KhoangCachItem + veHUD.scrollY;
            Texture[] itemNhanVat = {
                veHUD.ao,veHUD.quan,veHUD.gang,veHUD.giay,veHUD.rada,veHUD.iconct,veHUD.giaplt,veHUD.vanbay
            };
            ArrayList<Item> danhSachDangMac = duLieuNguoiChoi.getHanhTrangDangMac();
            for (int i = 0; i < soTrangBi; i++) {
                Item item = danhSachDangMac.get(i);
                float y = startY - i * KhoangCachItem;
                Texture tex = (veHUD.hangTrangDangChon == i) ? veHUD.hanh_trang_dang_mac_click : veHUD.hanh_trang_dang_mac;
                batch.draw(tex, 3, y, 344, 50);
                if (itemNhanVat[i]!=null){
                    if (itemNhanVat[i].getHeight()*0.5f < 60) {
                        batch.draw(itemNhanVat[i], 3 + (70 - itemNhanVat[i].getWidth() * 0.5f) / 2f, y + (49 - itemNhanVat[i].getHeight() * 0.5f) / 2f, itemNhanVat[i].getWidth() * 0.5f, itemNhanVat[i].getHeight() * 0.5f);
                    } else {
                        batch.draw(itemNhanVat[i], 3 + (70 - itemNhanVat[i].getWidth() * 0.38f) / 2f, y + (49 - itemNhanVat[i].getHeight() * 0.38f) / 2f, itemNhanVat[i].getWidth() * 0.38f, itemNhanVat[i].getHeight() * 0.38f);
                    }
                }
                String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng","Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
                if (item != null) {
                    int kc1 = 0;
                    layout.setText(veHUD.fontMotaSkill, item.getTenItem());
                    veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);
                    kc1 += layout.width+5;
                    if (item.getSoCap()>0) {
                        layout.setText(veHUD.fontMotaSkill, "[+"+item.getSoCap()+"]");
                        veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12 + kc1 , y + 49 - 10);
                    }
                }
                if (i == 7 && veHUD.vanBayDau){
                    layout.setText(veHUD.fontMotaSkill,"Cân đẩu vân");
                    veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);
                    layout.setText(veHUD.fontCapSKill,"Dùng để bay không tốn KI");
                    veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 7 && !veHUD.vanBayDau){
                    layout.setText(veHUD.fontCapSKill,item.getMoTa());
                    veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 6 && item != null) {
                    layout.setText(veHUD.fontCapSKill,"Hiệu lực trong " + (int) (veHUD.timeMacGiapLuyenTap / 60f) + " phút");
                    veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                }
                if (i == 5 && item != null) {
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
                if (i == 0 && item != null){
                    int kc = 0;
                    layout.setText(veHUD.fontCapSKill,"Giáp+"+item.getChiso()[4]);
                    veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                    kc += layout.width+1;
                    if (item.getSetkichhoat()!=null){
                        layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                        veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                    }
                }
                if (i == 1 && item != null){
                    int kc = 0;
                    layout.setText(veHUD.fontCapSKill,"HP+"+item.getChiso()[9]);
                    veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                    kc += layout.width+1;
                    if (item.getSetkichhoat()!=null){
                        layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                        veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                    }
                }
                if (i == 2 && item != null){
                    int kc = 0;
                    layout.setText(veHUD.fontCapSKill,"Tấn công+"+item.getChiso()[11]);
                    veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                    kc += layout.width+1;
                    if (item.getSetkichhoat()!=null){
                        layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                        veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                    }
                }
                if (i == 3 && item != null){
                    int kc = 0;
                    layout.setText(veHUD.fontCapSKill,"KI+"+item.getChiso()[10]);
                    veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                    kc += layout.width+1;
                    if (item.getSetkichhoat()!=null){
                        layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                        veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                    }
                }
                if (i == 4 && item != null){
                    int kc = 0;
                    layout.setText(veHUD.fontCapSKill,"Chí mạng+"+item.getChiso()[3]+"%");
                    veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                    kc += layout.width+1;
                    if (item.getSetkichhoat()!=null){
                        layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                        veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                    }
                }
            }
            for (int i = 0; i < soKhac; i++) {
                float y = startY - (soTrangBi + i) * KhoangCachItem;
                // Vẽ ô nền
                Texture tex = (veHUD.hangTrangDangChon == soTrangBi + i) ? veHUD.hanh_trang_click : veHUD.hanh_trang;
                batch.draw(tex, 3, y, 344, 50);
                // Nếu có item trong danh sách thì vẽ icon
                if (i < danhSachItem.size()) {
                    Item item = danhSachItem.get(i);
                    if (item != null) {
                        if (item.getTexture().getHeight()*0.5f < 60) {
                            batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.5f) / 2f, y + (49 - item.getTexture().getHeight() * 0.5f) / 2f, item.getTexture().getWidth() * 0.5f, item.getTexture().getHeight() * 0.5f);
                        } else {
                            batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.38f) / 2f, y + (49 - item.getTexture().getHeight() * 0.38f) / 2f, item.getTexture().getWidth() * 0.38f, item.getTexture().getHeight() * 0.38f);
                        }
                        String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng","Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
                        int kc1 = 0;
                        layout.setText(veHUD.fontMotaSkill, item.getTenItem());
                        veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);
                        kc1 += layout.width+5;
                        if (item.getSoCap()>0) {
                            layout.setText(veHUD.fontMotaSkill, "[+"+item.getSoCap()+"]");
                            veHUD.fontMotaSkill.draw(batch, layout, 3 + 70 + 12 + kc1 , y + 49 - 10);
                        }
                        if (item.getLoai() == LoaiItem.VANBAY){
                            layout.setText(veHUD.fontCapSKill,item.getMoTa());
                            veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                        }
                        if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                            layout.setText(veHUD.fontCapSKill,"Hiệu lực trong " + (int) (veHUD.timeMacGiapLuyenTap / 60f) + " phút");
                            veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
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
                        if (item.getLoai() == LoaiItem.AO){
                            int kc = 0;
                            layout.setText(veHUD.fontCapSKill,"Giáp+"+item.getChiso()[4]);
                            veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                            kc += layout.width+1;
                            if (item.getSetkichhoat()!=null){
                                layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                                veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                            }
                        }
                        if (item.getLoai() == LoaiItem.QUAN){
                            int kc = 0;
                            layout.setText(veHUD.fontCapSKill,"HP+"+item.getChiso()[9]);
                            veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                            kc += layout.width+1;
                            if (item.getSetkichhoat()!=null){
                                layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                                veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                            }
                        }
                        if (item.getLoai() == LoaiItem.GANG){
                            int kc = 0;
                            layout.setText(veHUD.fontCapSKill,"Tấn công+"+item.getChiso()[11]);
                            veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                            kc += layout.width+1;
                            if (item.getSetkichhoat()!=null){
                                layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                                veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                            }
                        }
                        if (item.getLoai() == LoaiItem.GIAY){
                            int kc = 0;
                            layout.setText(veHUD.fontCapSKill,"KI+"+item.getChiso()[10]);
                            veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                            kc += layout.width+1;
                            if (item.getSetkichhoat()!=null){
                                layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                                veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                            }
                        }
                        if (item.getLoai() == LoaiItem.RADA){
                            int kc = 0;
                            layout.setText(veHUD.fontCapSKill,"Chí mạng+"+item.getChiso()[3]+"%");
                            veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
                            kc += layout.width+1;
                            if (item.getSetkichhoat()!=null){
                                layout.setText(veHUD.fontCapSKill,",Set "+item.getSetkichhoat());
                                veHUD.fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
                            }
                        }
                    }
                }
            }
            batch.flush();
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
            batch.end();
            if (veHUD.DangHienPopupThongTin1 && veHUD.TimeChoHienPopup <= 0) {
                veHUD.PopupHanhTrang(shapeRenderer,batch, veHUD.PopupHanhTrangX, veHUD.PopupHanhTrangY ,veHUD.PopupHanhTrangW ,veHUD.hangTrangDangChon);
            }
            batch.begin();
        }


        if (veHUD.chucNangDangChon == 2){
            DecimalFormat dinhDang = new DecimalFormat("#,###");
            layout.setText(veHUD.fontTiemNang,"Top 1");
            veHUD.fontTiemNang.draw(batch,layout,105+(225-layout.width)/2f,600);
            layout.setText(veHUD.fontvangngoc,"Điểm tiềm năng");
            veHUD.fontvangngoc.draw(batch,layout,105+(225-layout.width)/2f,575);
            layout.setText(veHUD.fontTiemNang,dinhDang.format(duLieuNguoiChoi.getTiemNangNhanVat()));
            veHUD.fontTiemNang.draw(batch,layout,105+(225-layout.width)/2f,550);
            layout.setText(veHUD.fontvangngoc,"Năng động: "+dinhDang.format(duLieuNguoiChoi.getDiemSoiDongNhanVat()));
            veHUD.fontvangngoc.draw(batch,layout,105+(225-layout.width)/2f,525);
            String[] textChiSoCoBan1 = {
                "HP gốc: " + dinhDang.format(duLieuNguoiChoi.getHpGoc()),
                "KI gốc: " + dinhDang.format(duLieuNguoiChoi.getKiGoc()),
                "Sức đánh gốc: " + dinhDang.format(duLieuNguoiChoi.getSucDanhGoc()),
                "Giáp gốc: " + dinhDang.format(duLieuNguoiChoi.getGiapGoc()),
                "Crit gốc: " + duLieuNguoiChoi.getChiMangGoc() +"%"
            };
            String[] textChiSoCoBan2 = {
                dinhDang.format(duLieuNguoiChoi.getHpGoc()+1000)+" tiềm năng: tăng 20",
                dinhDang.format(duLieuNguoiChoi.getKiGoc()+1000)+" tiềm năng: tăng 20",
                dinhDang.format(duLieuNguoiChoi.getSucDanhGoc()*100)+" tiềm năng: tăng 1",
                dinhDang.format(500000+duLieuNguoiChoi.getGiapGoc()*100000)+" tiềm năng: tăng 1",
                veHUD.formatVangNgoc(30000000+(duLieuNguoiChoi.getChiMangGoc()-1)*5000000000L)+" tiềm năng: tăng 1"
            };
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachO = 61;

            int oChiSoCoBan = 5;
            int oNoiTai = 1;
            int oSKill = 9;
            int tongO = oChiSoCoBan + oNoiTai + oSKill;

            float totalHeight = tongO * KhoangCachO;
            veHUD.maxScroll = Math.max(0, totalHeight - viewHeight);

            batch.flush();
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
            Gdx.gl.glScissor(0, (int)viewY, 350, (int)viewHeight);

            // Vị trí bắt đầu vẽ từ trên xuống
            float startY = viewY + viewHeight - KhoangCachO + veHUD.scrollY;

            for (int i = 0; i < oChiSoCoBan; i++) {
                float y = startY - i * KhoangCachO;
                Texture tex = (veHUD.oChiSoDangChon == i) ? veHUD.o_chi_so_co_ban_click :veHUD.o_chi_so_co_ban;
                batch.draw(tex, 350-288-3, y, 288, 62);
                batch.draw(veHUD.oskill,3,y+2,350-288-3-3,58);
                batch.draw(veHUD.iconchisocoban[i], 3 + 8, y+2 + 8, 350-288-3-3 - 16, 58 - 16);
                layout.setText(veHUD.fontSkilldaco,textChiSoCoBan1[i]);
                veHUD.fontSkilldaco.draw(batch,layout,350-288+8,y+50);
                layout.setText(veHUD.fontMotaSkill,textChiSoCoBan2[i]);
                veHUD.fontMotaSkill.draw(batch,layout,350-288+8,y+22);
            }

            for (int i = 0; i < oNoiTai; i++) {
                float y = startY - (oChiSoCoBan + i) * KhoangCachO;
                Texture tex = (veHUD.oChiSoDangChon == oChiSoCoBan + i) ? veHUD.o_noi_tai_click : veHUD.o_noi_tai;
                batch.draw(tex, 350-288-3, y, 288, 62);
                layout.setText(
                    veHUD.fontMotaNoiTai,
                    "Khiên năng lượng +55% tốc độ hồi phục [15 đến 55]",
                    veHUD.fontMotaNoiTai.getColor(), // dùng lại màu đã set
                    250,                 // wrapWidth
                    Align.left,          // căn trái mặc định
                    true);               // bật tự xuống dòng
                veHUD.fontMotaNoiTai.draw(batch, layout, 350 - 288 + 8, y + 45);
                batch.draw(veHUD.oskill,3,y+2,350-288-3-3,58);
                batch.draw(veHUD.iconnoitai, 3 + 8, y+2 + 8, 350-288-3-3 - 16, 58 - 16);
            }
            for (int i = 0; i < oSKill; i++) {
                float y = startY - (oChiSoCoBan + oNoiTai + i) * KhoangCachO;
                Texture tex = (veHUD.oChiSoDangChon == oChiSoCoBan + oNoiTai + i) ?  veHUD.o_chi_so_co_ban_click :veHUD.o_chi_so_co_ban;
                batch.draw(tex, 350-288-3, y, 288, 62);
                int capskill = duLieuNguoiChoi.getCapSkill(i);
                if (capskill >= 1) {
                    layout.setText(veHUD.fontSkilldaco, duLieuNguoiChoi.getTenSkill(i));
                    veHUD.fontSkilldaco.draw(batch, layout, 350 - 288 + 8, y + 50);
                    layout.setText(veHUD.fontMotaSkill, "Đã mở khóa skill");
                    veHUD.fontMotaSkill.draw(batch, layout, 350 - 288 + 8, y + 22);
                    layout.setText(veHUD.fontCapSKill, "Cấp: "+capskill);
                    veHUD.fontCapSKill.draw(batch, layout, 350 - 60, y + 50);
                } else {
                    layout.setText(veHUD.fontSkillchuaco, duLieuNguoiChoi.getTenSkill(i));
                    veHUD.fontSkillchuaco.draw(batch, layout, 350 - 288 + 8, y + 50);
                    layout.setText(veHUD.fontMotaSkill, "Chưa mở khóa skill");
                    veHUD.fontMotaSkill.draw(batch, layout, 350 - 288 + 8, y + 22);
                }
                batch.draw(veHUD.oskill,3,y+2,350-288-3-3,58);
                if (veHUD.skillIcons != null && veHUD.skillIcons[i] != null) {
                    batch.draw(veHUD.skillIcons[i].icon, 3 + 8, y+2 + 8, 350-288-3-3 - 16, 58 - 16);
                }
            }
            batch.flush();
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
            batch.end();
            if (veHUD.DangHienPopupThongTin && veHUD.TimeChoHienPopup <= 0) {
                veHUD.PopupThongTin(shapeRenderer,batch, veHUD.PopupThongTinX, veHUD.PopupThongTinY ,veHUD.PopupThongTinW , veHUD.PopupThongTinH ,veHUD.oChiSoDangChon);
            }
            batch.begin();
            if (veHUD.HienPopUpGanSkill) {
                for (int i = 0; i < 5; i++) {
                    if (veHUD.nutduocchon==i) {
                        Texture nutVe = veHUD.nutClickTimer2 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                        batch.draw(nutVe , 210 + i * 120, 5, 114, 114);
                    }
                    else {
                        batch.draw(veHUD.nutvuong , 210 + i * 120, 5, 114, 114);
                    }
                    layout.setText(veHUD.font,"Vào");
                    veHUD.font.draw(batch,layout,210 + i * 120+(114- layout.width)/2f,5+114-40);
                    layout.setText(veHUD.font,"phím "+(i+1));
                    veHUD.font.draw(batch,layout,210 + i * 120+(114- layout.width)/2f,5+114-65);
                }
            }
        }
        if (veHUD.chucNangDangChon == 3){
            layout.setText(veHUD.fontNhiemVu,"Bang hội đang phát triển!");
            veHUD.fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);
        }
        if (veHUD.chucNangDangChon == 4){
            layout.setText(veHUD.fontNhiemVu,"Chức năng đang phát triển!");
            veHUD.fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);
        }
    }
}
