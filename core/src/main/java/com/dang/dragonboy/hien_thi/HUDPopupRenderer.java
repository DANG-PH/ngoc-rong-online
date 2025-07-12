//package com.dang.dragonboy.hien_thi;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.Align;
//import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
//import com.dang.dragonboy.item.Item;
//import com.dang.dragonboy.item.LoaiItem;
//import com.dang.dragonboy.nhan_vat.NhanVat;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//
//public class HUDPopupRenderer {
//    private final VeHUD veHUD;
//    public HUDPopupRenderer(VeHUD veHUD) {
//        this.veHUD = veHUD;
//    }
//
//    public void renderPopup(SpriteBatch batch) {
//        if (!veHUD.dangHienPopup || veHUD.texAvt == null) return;
//        //avt + nut X
//        batch.draw(veHUD.popupNhanVat, 0, 0, 350, 610);
//        float nutXW = veHUD.nutX.getWidth() * 0.5f;
//        float nutXH = veHUD.nutX.getHeight() * 0.55f;
//        batch.draw(veHUD.nutX, 350 - nutXW - 6, 610 - nutXH - 2, nutXW, nutXH - 5);
//
//        float texAvtW = veHUD.texAvt.getWidth() * 0.52f;
//        float texAvtH = veHUD.texAvt.getHeight() * 0.52f;
//        batch.draw(veHUD.texAvt, 0, 505, texAvtW, texAvtH);
//
//        // ===== Vẽ vàng, ngọc =====
//        batch.draw(veHUD.vang, 10, 8, 20, 20);
//        batch.draw(veHUD.ngoc, 275, 7, 20, 20);
//
//        // → Định dạng rút gọn vàng
//        String vangHienThi = veHUD.formatVangNgoc(duLieuNguoiChoi.getVang());
//        layout.setText(fontvangngoc, vangHienThi);
//        fontvangngoc.draw(batch, layout, 10 + 20 + 10, 22);
//
//        // → Định dạng rút gọn ngọc
//        String ngocHienThi = formatVangNgoc(duLieuNguoiChoi.getNgoc());
//        layout.setText(fontvangngoc, ngocHienThi);
//        fontvangngoc.draw(batch, layout, 275 + 20 + 10, 22);
//
//        // chuc nang
//        String[] TextChucnang1 = {
//            "Nhiệm",
//            "Hành",
//            "Kỹ",
//            "Bang",
//            "Chức"
//        };
//        String[] TextChucnang2 = {
//            "Vụ",
//            "Trang",
//            "Năng",
//            "Hội",
//            "năng"
//        };
//        for (int i = 0; i < 5; i++) {
//            Texture nutcn = chucNangDangChon==i ? nutchucnangclick : nutchucnang;
//            batch.draw(nutcn, 2+i*68+3, 450, 68, 52);
//            layout.setText(fontChucnang,TextChucnang1[i]);
//            fontChucnang.draw(batch,layout,2+i*68+3+(68- layout.width)/2f,450 + 41);
//            layout.setText(fontChucnang,TextChucnang2[i]);
//            fontChucnang.draw(batch,layout,2+i*68+3+(68- layout.width)/2f,450 + 20);
//        }
//        // noi dung theo chuc nang
//        if (chucNangDangChon == 0){
//            // Tên nhân vật + thể lực + Cấp bậc + Sức mạnh
//            font.setColor(1,1,1,1);
//            layout.setText(font, duLieuNguoiChoi.getTen());
//            font.draw(batch,layout,125,595);
//
//            layout.setText(fontsm,"Thể lực");
//            fontsm.draw(batch,layout,125,570);
//            batch.draw(thanhtheluc ,125+68,556);
//            layout.setText(fontsm, duLieuNguoiChoi.getCapBac());
//            fontsm.draw(batch,layout,125,545);
//            // ===== Vẽ sức mạnh =====
//            DecimalFormat dinhDang = new DecimalFormat("#,###");
//
//            long sucManh = duLieuNguoiChoi.getSucManh();
//            String sucManhHienThi = dinhDang.format(sucManh);
//            layout.setText(fontsm, "Sức mạnh: " + sucManhHienThi);
//            fontsm.draw(batch, layout, 125, 520);
//            // chỉnh OOP theo nhiệm vụ sau cấu trúc ( mô tả, nhiệm vụ cần làm , mô tả dài )
//            //mo ta
//            layout.setText(fontNhiemVu,"Nhiệm vụ tập luyện"); // cần thay đổi sau
//            fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);
//
//            // nhiệm vụ có nhiều phần , chỉ hiện thị phần hiện tại và đã làm
//            layout.setText(fontNhiemVu1,"- Đánh ngã 5 mộc nhân (0/5)"); // cần thay đổi sau
//            fontNhiemVu1.draw(batch,layout,20,385);
//            layout.setText(fontNhiemVuChuaLam,"- ...");
//            fontNhiemVuChuaLam.draw(batch,layout,20,385-25);
//
//            // mo ta dai
//            layout.setText(fontMotaNhiemVu,
//                "Mộc nhân được đặt nhiều tại Làng Aru, " +
//                    "ngay trước nhà ông Gohan " +
//                    "Hãy đánh ngã 5 mộc nhân, sau đó quay " +
//                    "về nhà báo cáo với ông Gohan " +
//                    "Để đánh, hãy click đôi vào đối tượng \n" +
//                    "Thưởng 3 k sức mạnh \n" +
//                    "Thưởng 3 k tiềm năng",
//                fontMotaNhiemVu.getColor(), // dùng lại màu đã set // cần thay đổi sau
//                290,                 // wrapWidth
//                Align.left,          // căn trái mặc định
//                true);               // bật tự xuống dòng
//            fontMotaNhiemVu.draw(batch,layout,20,385-25-30);
//        }
//        if (chucNangDangChon == 1){
//            // chỉ số nhân vật
//            layout.setText(fontsm,"HP: "+(int)duLieuNguoiChoi.getHpHienTai()+" / "+(int)duLieuNguoiChoi.getHpToiDa());
//            fontsm.draw(batch,layout,125,595);
//            layout.setText(fontsm,"KI: "+(int)duLieuNguoiChoi.getKiHienTai()+" / "+(int)duLieuNguoiChoi.getKiToiDa());
//            fontsm.draw(batch,layout,125,570);
//            layout.setText(fontsm,"Sức đánh: "+(int)duLieuNguoiChoi.getSucDanhNhanVat()+", Crit: "+duLieuNguoiChoi.getChiMangNhanVat()+"%");
//            fontsm.draw(batch,layout,125,545);
//            layout.setText(fontsm,"Giáp: "+(int)duLieuNguoiChoi.getGiapNhanVat()+", ST Crit: "+(int)duLieuNguoiChoi.getSatThuongChiMang()+"%");
//            fontsm.draw(batch,layout,125,520);
//
//            // ô hành trang
//            float viewY = 35;
//            float viewHeight = 444 - 35;
//            int KhoangCachItem = 49;
//
//            int soTrangBi = 8;
//            ArrayList<Item> danhSachItem = duLieuNguoiChoi.getHanhTrang();
//            int soKhac = 22;
//            int tongSoTrangBi = soTrangBi + soKhac;
//
//            float totalHeight = tongSoTrangBi * KhoangCachItem;
//            maxScroll = Math.max(0, totalHeight - viewHeight);
//
//            batch.flush();
//            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
//            Gdx.gl.glScissor(0, (int)viewY, 350, (int)viewHeight);
//            // Vị trí bắt đầu vẽ từ trên xuống
//            float startY = viewY + viewHeight - KhoangCachItem + scrollY;
//            Texture[] itemNhanVat = {
//                ao,quan,gang,giay,rada,iconct,giaplt,vanbay
//            };
//            ArrayList<Item> danhSachDangMac = duLieuNguoiChoi.getHanhTrangDangMac();
//            for (int i = 0; i < soTrangBi; i++) {
//                Item item = danhSachDangMac.get(i);
//                float y = startY - i * KhoangCachItem;
//                Texture tex = (hangTrangDangChon == i) ? hanh_trang_dang_mac_click : hanh_trang_dang_mac;
//                batch.draw(tex, 3, y, 344, 50);
//                if (itemNhanVat[i]!=null){
//                    if (itemNhanVat[i].getHeight()*0.5f < 60) {
//                        batch.draw(itemNhanVat[i], 3 + (70 - itemNhanVat[i].getWidth() * 0.5f) / 2f, y + (49 - itemNhanVat[i].getHeight() * 0.5f) / 2f, itemNhanVat[i].getWidth() * 0.5f, itemNhanVat[i].getHeight() * 0.5f);
//                    } else {
//                        batch.draw(itemNhanVat[i], 3 + (70 - itemNhanVat[i].getWidth() * 0.38f) / 2f, y + (49 - itemNhanVat[i].getHeight() * 0.38f) / 2f, itemNhanVat[i].getWidth() * 0.38f, itemNhanVat[i].getHeight() * 0.38f);
//                    }
//                }
//                String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng","Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
//                if (item != null) {
//                    int kc1 = 0;
//                    layout.setText(fontMotaSkill, item.getTenItem());
//                    fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);
//                    kc1 += layout.width+5;
//                    if (item.getSoCap()>0) {
//                        layout.setText(fontMotaSkill, "[+"+item.getSoCap()+"]");
//                        fontMotaSkill.draw(batch, layout, 3 + 70 + 12 + kc1 , y + 49 - 10);
//                    }
//                }
//                if (i == 7 && vanBayDau){
//                    layout.setText(fontMotaSkill,"Cân đẩu vân");
//                    fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);
//                    layout.setText(fontCapSKill,"Dùng để bay không tốn KI");
//                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                }
//                if (i == 7 && !vanBayDau){
//                    layout.setText(fontCapSKill,item.getMoTa());
//                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                }
//                if (i == 6 && item != null) {
//                    layout.setText(fontCapSKill,"Hiệu lực trong " + (int) (timeMacGiapLuyenTap / 60f) + " phút");
//                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                }
//                if (i == 5 && item != null) {
//                    int kc = 0;
//                    int soChiso = 0;
//                    for (int j = 6; j <= 12; j++) {
//                        if (item.getChiso()[j] > 0) {
//                            String prefix = (soChiso == 0) ? "" : ",";
//                            layout.setText(fontCapSKill, prefix + chisoduoccong[j] + "+" + item.getChiso()[j] + "%");
//                            fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
//                            kc += layout.width + 1;
//                            soChiso++;
//                        }
//                    }
//                }
//                if (i == 0 && item != null){
//                    int kc = 0;
//                    layout.setText(fontCapSKill,"Giáp+"+item.getChiso()[4]);
//                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                    kc += layout.width+1;
//                    if (item.getSetkichhoat()!=null){
//                        layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                        fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                    }
//                }
//                if (i == 1 && item != null){
//                    int kc = 0;
//                    layout.setText(fontCapSKill,"HP+"+item.getChiso()[9]);
//                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                    kc += layout.width+1;
//                    if (item.getSetkichhoat()!=null){
//                        layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                        fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                    }
//                }
//                if (i == 2 && item != null){
//                    int kc = 0;
//                    layout.setText(fontCapSKill,"Tấn công+"+item.getChiso()[11]);
//                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                    kc += layout.width+1;
//                    if (item.getSetkichhoat()!=null){
//                        layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                        fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                    }
//                }
//                if (i == 3 && item != null){
//                    int kc = 0;
//                    layout.setText(fontCapSKill,"KI+"+item.getChiso()[10]);
//                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                    kc += layout.width+1;
//                    if (item.getSetkichhoat()!=null){
//                        layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                        fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                    }
//                }
//                if (i == 4 && item != null){
//                    int kc = 0;
//                    layout.setText(fontCapSKill,"Chí mạng+"+item.getChiso()[3]+"%");
//                    fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                    kc += layout.width+1;
//                    if (item.getSetkichhoat()!=null){
//                        layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                        fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                    }
//                }
//            }
//            for (int i = 0; i < soKhac; i++) {
//                float y = startY - (soTrangBi + i) * KhoangCachItem;
//                // Vẽ ô nền
//                Texture tex = (hangTrangDangChon == soTrangBi + i) ? hanh_trang_click : hanh_trang;
//                batch.draw(tex, 3, y, 344, 50);
//                // Nếu có item trong danh sách thì vẽ icon
//                if (i < danhSachItem.size()) {
//                    Item item = danhSachItem.get(i);
//                    if (item != null) {
//                        if (item.getTexture().getHeight()*0.5f < 60) {
//                            batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.5f) / 2f, y + (49 - item.getTexture().getHeight() * 0.5f) / 2f, item.getTexture().getWidth() * 0.5f, item.getTexture().getHeight() * 0.5f);
//                        } else {
//                            batch.draw(item.getTexture(), 3 + (70 - item.getTexture().getWidth() * 0.38f) / 2f, y + (49 - item.getTexture().getHeight() * 0.38f) / 2f, item.getTexture().getWidth() * 0.38f, item.getTexture().getHeight() * 0.38f);
//                        }
//                        String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng","Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
//                        int kc1 = 0;
//                        layout.setText(fontMotaSkill, item.getTenItem());
//                        fontMotaSkill.draw(batch, layout, 3 + 70 + 12, y + 49 - 10);
//                        kc1 += layout.width+5;
//                        if (item.getSoCap()>0) {
//                            layout.setText(fontMotaSkill, "[+"+item.getSoCap()+"]");
//                            fontMotaSkill.draw(batch, layout, 3 + 70 + 12 + kc1 , y + 49 - 10);
//                        }
//                        if (item.getLoai() == LoaiItem.VANBAY){
//                            layout.setText(fontCapSKill,item.getMoTa());
//                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                        }
//                        if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
//                            layout.setText(fontCapSKill,"Hiệu lực trong " + (int) (timeMacGiapLuyenTap / 60f) + " phút");
//                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                        }
//                        if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {
//                            int kc = 0;
//                            int soChiso = 0;
//                            for (int j = 6; j <= 12; j++) {
//                                if (item.getChiso()[j] > 0) {
//                                    String prefix = (soChiso == 0) ? "" : ",";
//                                    layout.setText(fontCapSKill, prefix + chisoduoccong[j] + "+" + item.getChiso()[j] + "%");
//                                    fontCapSKill.draw(batch, layout, 3 + 70 + 12 + kc, y + 49 - 30);
//                                    kc += layout.width + 1;
//                                    soChiso++;
//                                }
//                            }
//                        }
//                        if (item.getLoai() == LoaiItem.AO){
//                            int kc = 0;
//                            layout.setText(fontCapSKill,"Giáp+"+item.getChiso()[4]);
//                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                            kc += layout.width+1;
//                            if (item.getSetkichhoat()!=null){
//                                layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                                fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                            }
//                        }
//                        if (item.getLoai() == LoaiItem.QUAN){
//                            int kc = 0;
//                            layout.setText(fontCapSKill,"HP+"+item.getChiso()[9]);
//                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                            kc += layout.width+1;
//                            if (item.getSetkichhoat()!=null){
//                                layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                                fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                            }
//                        }
//                        if (item.getLoai() == LoaiItem.GANG){
//                            int kc = 0;
//                            layout.setText(fontCapSKill,"Tấn công+"+item.getChiso()[11]);
//                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                            kc += layout.width+1;
//                            if (item.getSetkichhoat()!=null){
//                                layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                                fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                            }
//                        }
//                        if (item.getLoai() == LoaiItem.GIAY){
//                            int kc = 0;
//                            layout.setText(fontCapSKill,"KI+"+item.getChiso()[10]);
//                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                            kc += layout.width+1;
//                            if (item.getSetkichhoat()!=null){
//                                layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                                fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                            }
//                        }
//                        if (item.getLoai() == LoaiItem.RADA){
//                            int kc = 0;
//                            layout.setText(fontCapSKill,"Chí mạng+"+item.getChiso()[3]+"%");
//                            fontCapSKill.draw(batch,layout,3 + 70 + 12, y + 49 - 30);
//                            kc += layout.width+1;
//                            if (item.getSetkichhoat()!=null){
//                                layout.setText(fontCapSKill,",Set "+item.getSetkichhoat());
//                                fontCapSKill.draw(batch,layout,3 + 70 + 12 +kc, y + 49 - 30);
//                            }
//                        }
//                    }
//                }
//            }
//            batch.flush();
//            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
//            batch.end();
//            if (DangHienPopupThongTin1 && TimeChoHienPopup <= 0) {
//                PopupHanhTrang(shapeRenderer,batch, PopupHanhTrangX, PopupHanhTrangY ,PopupHanhTrangW ,hangTrangDangChon);
//            }
//            batch.begin();
//        }
//
//
//        if (chucNangDangChon == 2){
//            DecimalFormat dinhDang = new DecimalFormat("#,###");
//            layout.setText(fontTiemNang,"Top 1");
//            fontTiemNang.draw(batch,layout,105+(225-layout.width)/2f,600);
//            layout.setText(fontvangngoc,"Điểm tiềm năng");
//            fontvangngoc.draw(batch,layout,105+(225-layout.width)/2f,575);
//            layout.setText(fontTiemNang,dinhDang.format(duLieuNguoiChoi.getTiemNangNhanVat()));
//            fontTiemNang.draw(batch,layout,105+(225-layout.width)/2f,550);
//            layout.setText(fontvangngoc,"Năng động: "+dinhDang.format(duLieuNguoiChoi.getDiemSoiDongNhanVat()));
//            fontvangngoc.draw(batch,layout,105+(225-layout.width)/2f,525);
//            String[] textChiSoCoBan1 = {
//                "HP gốc: " + dinhDang.format(duLieuNguoiChoi.getHpGoc()),
//                "KI gốc: " + dinhDang.format(duLieuNguoiChoi.getKiGoc()),
//                "Sức đánh gốc: " + dinhDang.format(duLieuNguoiChoi.getSucDanhGoc()),
//                "Giáp gốc: " + dinhDang.format(duLieuNguoiChoi.getGiapGoc()),
//                "Crit gốc: " + duLieuNguoiChoi.getChiMangGoc() +"%"
//            };
//            String[] textChiSoCoBan2 = {
//                dinhDang.format(duLieuNguoiChoi.getHpGoc()+1000)+" tiềm năng: tăng 20",
//                dinhDang.format(duLieuNguoiChoi.getKiGoc()+1000)+" tiềm năng: tăng 20",
//                dinhDang.format(duLieuNguoiChoi.getSucDanhGoc()*100)+" tiềm năng: tăng 1",
//                dinhDang.format(500000+duLieuNguoiChoi.getGiapGoc()*100000)+" tiềm năng: tăng 1",
//                formatVangNgoc(30000000+(duLieuNguoiChoi.getChiMangGoc()-1)*5000000000L)+" tiềm năng: tăng 1"
//            };
//            float viewY = 35;
//            float viewHeight = 444 - 35;
//            int KhoangCachO = 61;
//
//            int oChiSoCoBan = 5;
//            int oNoiTai = 1;
//            int oSKill = 9;
//            int tongO = oChiSoCoBan + oNoiTai + oSKill;
//
//            float totalHeight = tongO * KhoangCachO;
//            maxScroll = Math.max(0, totalHeight - viewHeight);
//
//            batch.flush();
//            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
//            Gdx.gl.glScissor(0, (int)viewY, 350, (int)viewHeight);
//
//            // Vị trí bắt đầu vẽ từ trên xuống
//            float startY = viewY + viewHeight - KhoangCachO + scrollY;
//
//            for (int i = 0; i < oChiSoCoBan; i++) {
//                float y = startY - i * KhoangCachO;
//                Texture tex = (oChiSoDangChon == i) ? o_chi_so_co_ban_click :o_chi_so_co_ban;
//                batch.draw(tex, 350-288-3, y, 288, 62);
//                batch.draw(oskill,3,y+2,350-288-3-3,58);
//                batch.draw(iconchisocoban[i], 3 + 8, y+2 + 8, 350-288-3-3 - 16, 58 - 16);
//                layout.setText(fontSkilldaco,textChiSoCoBan1[i]);
//                fontSkilldaco.draw(batch,layout,350-288+8,y+50);
//                layout.setText(fontMotaSkill,textChiSoCoBan2[i]);
//                fontMotaSkill.draw(batch,layout,350-288+8,y+22);
//            }
//
//            for (int i = 0; i < oNoiTai; i++) {
//                float y = startY - (oChiSoCoBan + i) * KhoangCachO;
//                Texture tex = (oChiSoDangChon == oChiSoCoBan + i) ? o_noi_tai_click : o_noi_tai;
//                batch.draw(tex, 350-288-3, y, 288, 62);
//                layout.setText(
//                    fontMotaNoiTai,
//                    "Khiên năng lượng +55% tốc độ hồi phục [15 đến 55]",
//                    fontMotaNoiTai.getColor(), // dùng lại màu đã set
//                    250,                 // wrapWidth
//                    Align.left,          // căn trái mặc định
//                    true);               // bật tự xuống dòng
//                fontMotaNoiTai.draw(batch, layout, 350 - 288 + 8, y + 45);
//                batch.draw(oskill,3,y+2,350-288-3-3,58);
//                batch.draw(iconnoitai, 3 + 8, y+2 + 8, 350-288-3-3 - 16, 58 - 16);
//            }
//            for (int i = 0; i < oSKill; i++) {
//                float y = startY - (oChiSoCoBan + oNoiTai + i) * KhoangCachO;
//                Texture tex = (oChiSoDangChon == oChiSoCoBan + oNoiTai + i) ?  o_chi_so_co_ban_click :o_chi_so_co_ban;
//                batch.draw(tex, 350-288-3, y, 288, 62);
//                int capskill = duLieuNguoiChoi.getCapSkill(i);
//                if (capskill >= 1) {
//                    layout.setText(fontSkilldaco, duLieuNguoiChoi.getTenSkill(i));
//                    fontSkilldaco.draw(batch, layout, 350 - 288 + 8, y + 50);
//                    layout.setText(fontMotaSkill, "Đã mở khóa skill");
//                    fontMotaSkill.draw(batch, layout, 350 - 288 + 8, y + 22);
//                    layout.setText(fontCapSKill, "Cấp: "+capskill);
//                    fontCapSKill.draw(batch, layout, 350 - 60, y + 50);
//                } else {
//                    layout.setText(fontSkillchuaco, duLieuNguoiChoi.getTenSkill(i));
//                    fontSkillchuaco.draw(batch, layout, 350 - 288 + 8, y + 50);
//                    layout.setText(fontMotaSkill, "Chưa mở khóa skill");
//                    fontMotaSkill.draw(batch, layout, 350 - 288 + 8, y + 22);
//                }
//                batch.draw(oskill,3,y+2,350-288-3-3,58);
//                if (skillIcons != null && skillIcons[i] != null) {
//                    batch.draw(skillIcons[i].icon, 3 + 8, y+2 + 8, 350-288-3-3 - 16, 58 - 16);
//                }
//            }
//            batch.flush();
//            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
//            batch.end();
//            if (DangHienPopupThongTin && TimeChoHienPopup <= 0) {
//                PopupThongTin(shapeRenderer,batch, PopupThongTinX, PopupThongTinY ,PopupThongTinW , PopupThongTinH ,oChiSoDangChon);
//            }
//            batch.begin();
//            if (HienPopUpGanSkill) {
//                for (int i = 0; i < 5; i++) {
//                    if (nutduocchon==i) {
//                        Texture nutVe = nutClickTimer2 > 0 ? nutvuongclick : nutvuong;
//                        batch.draw(nutVe , 210 + i * 120, 5, 114, 114);
//                    }
//                    else {
//                        batch.draw(nutvuong , 210 + i * 120, 5, 114, 114);
//                    }
//                    layout.setText(font,"Vào");
//                    font.draw(batch,layout,210 + i * 120+(114- layout.width)/2f,5+114-40);
//                    layout.setText(font,"phím "+(i+1));
//                    font.draw(batch,layout,210 + i * 120+(114- layout.width)/2f,5+114-65);
//                }
//            }
//        }
//        if (chucNangDangChon == 3){
//            layout.setText(fontNhiemVu,"Bang hội đang phát triển!");
//            fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);
//        }
//        if (chucNangDangChon == 4){
//            layout.setText(fontNhiemVu,"Chức năng đang phát triển!");
//            fontNhiemVu.draw(batch,layout,0+(350- layout.width)/2f,420);
//        }
//    }
//}
