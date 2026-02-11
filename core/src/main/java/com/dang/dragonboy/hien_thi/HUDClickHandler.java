package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.nhan_vat.MultiplayerRenderer;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_haidang.admin_haidang;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.TrangThaiChucNang_CUA_HANG_admin_thanhle;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.*;

import java.util.ArrayList;

public class HUDClickHandler {
    private final VeHUD veHUD;
    private GlyphLayout layout;
    private final DuLieuNguoiChoi duLieuNguoiChoi;
    private final NhanVat nhanVat;
    public Texture nutX;
    public HUDClickHandler(VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat ) {
        this.veHUD = veHUD;
        layout = new GlyphLayout();
        this.duLieuNguoiChoi = duLieuNguoiChoi;
        this.nhanVat = nhanVat;
        nutX = new Texture("hud/giaodientrong/nutX.png");
    }

    public void xuLyClick(int x, int y, float worldX, float worldY) {
        // check click NPC đặt ở đây để cùng thời điểm với xử lí click của hud
        if (veHUD.daClickVaoNpc) {
            veHUD.npcHienTai.xuLyClick(x, y);
        }

        MultiplayerRenderer.checkClick(worldX, worldY, veHUD);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // === VÙNG Ô SKILL ===
        int oskillW = 50;
        int oskillH = 50;
        float skillBaseX = 30;
        float skillY = 25f;

        for (int i = 0; i < 5; i++) {
            float x_ve = skillBaseX + i * (65f);
            if (x >= x_ve && x <= x_ve+oskillW && y >= skillY && y <= skillY+oskillH) {
                if (!veHUD.dangHienPopup && !veHUD.dangHienDauThan && !veHUD.daClickVaoNpc) {
                    if (veHUD.skillDangChon == i) {
                        veHUD.dungSkill(i);
                    } else {
                        veHUD.chonSkill(i);
                    }
                    if (!veHUD.dangHienPopup) {
                        veHUD.clickY = skillY + oskillH / 2f;
                        veHUD.clickX = x_ve + oskillW / 2f;
                        veHUD.timeGlow = 0.2f;
                    }
                }
            }
        }

        // === VÙNG Ô CHAT ===
        int ochatW = 60;
        int ochatH = 60;
        float ochatX = screenWidth - ochatW - 15;
        float ochatY = screenHeight-10-ochatH;
        if (x >= ochatX && x <= ochatX + 60 && y >= ochatY && y <= ochatY + 60) {
            if (!veHUD.dangHienPopup && !veHUD.dangHienDauThan && !veHUD.daClickVaoNpc && !veHUD.dangHienKhungChat && !(veHUD.timeHienRongThan<=300-2.1f && veHUD.timeHienRongThan>0)) {
                veHUD.clickOChat();
            }
        }

        // === VÙNG Ô ĐẬU THẦN ===
        int odauthanW = 75;
        int odauthanH = 75;
        float odauthanX = screenWidth - odauthanW - 10;
        float odauthanY = 10;
        if (x >= odauthanX && x <= odauthanX + 75 && y >= odauthanY && y <= odauthanY + 75) {
            if (!veHUD.dangHienPopup && !veHUD.dangHienDauThan && !veHUD.daClickVaoNpc && !veHUD.dangHienKhungChat && !(veHUD.timeHienRongThan<=300-2.1f && veHUD.timeHienRongThan>0)) {
                veHUD.clickODauThan();
            }
        }
        // Vùng mở popup
        float nutPopupX = 0f;
        float nutPopupY = screenHeight / 4f * 3;
        if (x >= nutPopupX && x <= nutPopupX + 25 && y >= nutPopupY && y <= nutPopupY + 35) {
            if (!veHUD.dangHienKhungChat && !veHUD.dangHienDauThan && !veHUD.daClickVaoNpc && !(veHUD.timeHienRongThan<=300-2.1f && veHUD.timeHienRongThan>0)) {
                veHUD.vuaClickMoPopup = true;
                veHUD.clickY =  nutPopupY + 19f;
                veHUD.clickX =  nutPopupX + 20;
                veHUD.timeGlow = 0.2f;
            }
        }

        // Vùng điều ước rồng thần
        if (veHUD.timeHienRongThan <= 300f-2.1f && veHUD.timeHienRongThan>0 && !veHUD.daUocRongThan){
            for (int i = 0; i < 5; i++){
                if (x>=210 + i * 120 && x <= 210 + i * 120+114 && y>=5 && y<=5+114){
                    veHUD.nutClickTimer2 = 0.3f;
                    veHUD.nutduocchon = i;
                }
            }
        }

        // Vùng lựa chọn khi bật npc cây đậu
        if (veHUD.dangHienDauThan){
            for (int i = 0; i < 2; i++){
                if (x>=393 + i * 120 && x <= 393 + i * 120+114 && y>=5 && y<=5+114){
                    veHUD.nutClickTimer2 = 0.3f;
                    veHUD.nutduocchon = i;
                }
            }
            if (x<393 || x> 393 + 120+114 || y > 119) {
                veHUD.dangHienDauThan = false;
            }
        }

        if (veHUD.dangHienPopup && !veHUD.dangHienThongBao) {
//            if (veHUD.vuaMoPopup) {
//                veHUD.vuaMoPopup = false;
//                return;
//            }
            // nutX để tắt popup
            float nutXW = nutX.getWidth() * 0.5f;
            float nutXH = nutX.getHeight() * 0.55f;
            float nutXX = 350 - nutXW - 6;
            float nutXY = 610 - nutXH - 2;
            if (x >= nutXX && x <= nutXX + nutXW && y >= nutXY && y <= nutXY + nutXH) {
                if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.THONG_BAO || veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.GIOI_THIEU_GAME) {
                    veHUD.trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
                    veHUD.scrollYPhai = 0;
                    veHUD.oChiSoDangChon = -1;
                    veHUD.clickY = y;
                    veHUD.clickX = x;
                    veHUD.timeGlow = 0.3f;
                } else if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) {
                    veHUD.trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
                    veHUD.scrollYPhai = 0;
                    veHUD.oChiSoDangChon = -1;
                    veHUD.chucNangDeTuDangChon = 0;
                    veHUD.clickY = y;
                    veHUD.clickX = x;
                    veHUD.timeGlow = 0.3f;
                }
            } else if (x > 350 && x <= 1020 && !veHUD.DangHienPopupThongTin && !veHUD.HienPopUpGanSkill && !veHUD.DangHienPopupThongTin1 && (veHUD.trangThaiChucNangHUDChucNang != TrangThaiChucNangHUD_ChucNang.MINIGAME) && (veHUD.trangThaiChucNangHUDChucNang != TrangThaiChucNangHUD_ChucNang.DE_TU)) {
                if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.THONG_BAO) {
                    if (veHUD.trangThaiChucNangHUDChucNangThongBao == TrangThaiChucNangHUD_ChucNang_ThongBao.NONE) {
                        veHUD.trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
                        veHUD.scrollYPhai = 0;
                        veHUD.oChiSoDangChon = -1;
                    }
                    else {
                        veHUD.trangThaiChucNangHUDChucNangThongBao = TrangThaiChucNangHUD_ChucNang_ThongBao.NONE;
                        veHUD.scrollYPhai = 0;
                        veHUD.oChiSoDangChon = -1;
                    }
                } else {
                    if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.NONE) {
                        veHUD.tatPopupNhanVat();
                        veHUD.hangTrangDangChon = -1;
                        veHUD.oChiSoDangChon = -1;
                        veHUD.scrollYPhai = 0;
                        veHUD.scrollYTrai = 0;
                    }
                    else {
                        veHUD.trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
                        veHUD.scrollYPhai = 0;
                        veHUD.oChiSoDangChon = -1;
                    }
                }
            }
            // cac nut chuc nang
            for (int i = 0; i < 5; i++) {
                if (x >= 2+i*68+3 && x <= 2+i*68+3 + 68 && y >= 450 && y <= 450 + 52){
                    if (!(veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) && !(veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME)) {
                        switch (i) {
                            case 0 -> veHUD.trangThaiChucNangHUD = TrangThaiChucNangHUD.NHIEM_VU;
                            case 1 -> veHUD.trangThaiChucNangHUD = TrangThaiChucNangHUD.HANH_TRANG;
                            case 2 -> veHUD.trangThaiChucNangHUD = TrangThaiChucNangHUD.KY_NANG;
                            case 3 -> veHUD.trangThaiChucNangHUD = TrangThaiChucNangHUD.BANG_HOI;
                            case 4 -> veHUD.trangThaiChucNangHUD = TrangThaiChucNangHUD.CHUC_NANG;
                        }
                        veHUD.scrollYPhai = 0;
                        veHUD.oChiSoDangChon = -1;
                        veHUD.DangHienPopupThongTin1 = false;
                        veHUD.hangTrangDangChon = -1;
                        veHUD.trangThaiChucNangHUDChucNangThongBao = TrangThaiChucNangHUD_ChucNang_ThongBao.NONE;
                        veHUD.trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
                        veHUD.clickX = x;
                        veHUD.clickY = y;
                        veHUD.timeGlow = 0.3f;
                    }
                }
            }
        }
        boolean duDieuKienn = false;
        if (veHUD.dangHienPopup && (veHUD.trangThaiChucNangHUD == TrangThaiChucNangHUD.HANH_TRANG || veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) && !veHUD.DangHienPopupThongTin1 && !veHUD.DangHienPopupThongTin2 && !veHUD.dangHienThongBao) {
            duDieuKienn = true;
        }
        if ((veHUD.dangHienRuongDo || veHUD.dangHienPopupNhanVatPhai) && !veHUD.DangHienPopupThongTin1 && !veHUD.DangHienPopupThongTin3 && !veHUD.dangHienThongBao) {
            duDieuKienn = true;
        }
        if (duDieuKienn) {
//            if (veHUD.vanBayDau){
//                veHUD.vanbay = new Texture("vatpham/vanbay/"+"candauvan/candauvan.png");
//                nhanVat.setIdVanBay("candauvan");
//                nhanVat.setTenVanBay("Cân đẩu vân");
//                nhanVat.setMoTaVanBay("Dùng để bay không tốn KI");
//                nhanVat.setChisoVanBay(new int[] {0,0,0,0,0,0,0,0,0,0,0,0});
//                nhanVat.setHanhTinhVanBay("traidat");
//                nhanVat.setSucManhYeuCauVanBay(0);
//                Item vanBay = new Item("candauvan", "Cân đẩu vân", LoaiItem.VANBAY, veHUD.vanbay, "Dùng để bay không tốn KI", 1, new int[] {0,0,0,0,0,0,0,0,0,0,0,0},"traidat",0, null,0,0,0,-1);
//                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(vanBay,7);
//            }
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 8 + 12;
            boolean duDieuKien = false;
            boolean vePhai = false;
            if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU || veHUD.dangHienRuongDo || veHUD.dangHienPopupNhanVatPhai) {
                duDieuKien = x >= 3 + 1020-350 && x <= 3 + 344 + 1020-350 && y >= viewY && y <= viewY + viewHeight;
                vePhai = true;
            }
            if (veHUD.trangThaiChucNangHUD == TrangThaiChucNangHUD.HANH_TRANG && veHUD.dangHienPopup) {
                duDieuKien = x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight;
            }
            if (duDieuKien) {
                float relativeY = y - viewY;
                float realY = veHUD.scrollYPhai + (viewHeight - relativeY);
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
                            veHUD.PopupHanhTrangY_Trai = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYPhai;
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
                            veHUD.PopupHanhTrangY_Trai = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYPhai;
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
        if (veHUD.dangHienPopup && veHUD.trangThaiChucNangHUD == TrangThaiChucNangHUD.KY_NANG && !veHUD.DangHienPopupThongTin && !veHUD.HienPopUpGanSkill) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 61;
            int tongSoO = 15;

            // Kiểm tra có click vào vùng hành trang không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {

                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = veHUD.scrollYPhai + (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    veHUD.oChiSoDangChon = index;
                    veHUD.DangHienPopupThongTin = true;
                    if (index >= 0 && index < 5) {
                        veHUD.PopupThongTinX = 5;
                        veHUD.PopupThongTinY = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYPhai;
                        veHUD.PopupThongTinW = 360;
                        veHUD.PopupThongTinH = 80;
                        veHUD.TimeChoHienPopup = 0.3f;
                        if ((veHUD.PopupThongTinY-120)<0){
                            veHUD.PopupThongTinY = 125;
                        }
                        if ((veHUD.PopupThongTinY+veHUD.PopupThongTinH)>590){
                            veHUD.PopupThongTinY = 590-veHUD.PopupThongTinH;
                        }
                        veHUD.vuaMoPopupThongTin = true;
                    } else if (index == 5) {
                        veHUD.PopupThongTinW = 600;
                        veHUD.PopupThongTinX = (1020-veHUD.PopupThongTinW)/2f;
                        veHUD.PopupThongTinY = 125;
                        veHUD.PopupThongTinH = 90;
                        veHUD.TimeChoHienPopup = 0.3f;
                        veHUD.vuaMoPopupThongTin = true;
                    } else {
                        veHUD.PopupThongTinX = 5;
                        veHUD.PopupThongTinY = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYPhai;
                        veHUD.PopupThongTinW = 360;
                        veHUD.PopupThongTinH = 250;
                        veHUD.TimeChoHienPopup = 0.3f;
                        if ((veHUD.PopupThongTinY-120)<0){
                            veHUD.PopupThongTinY = 125;
                        }
                        if ((veHUD.PopupThongTinY+veHUD.PopupThongTinH)>590){
                            veHUD.PopupThongTinY = 590-veHUD.PopupThongTinH;
                        }
                        veHUD.vuaMoPopupThongTin = true;
                    }
                }
            }
        }
        if (veHUD.dangHienPopup && veHUD.trangThaiChucNangHUD == TrangThaiChucNangHUD.CHUC_NANG && veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.NONE) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 0;
            if (duLieuNguoiChoi.coDeTu()) {
                tongSoO = 10;
            } else {
                tongSoO = 9;
            }

            // Kiểm tra có click vào vùng không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {
                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = veHUD.scrollYPhai + (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    veHUD.oChiSoDangChon = index;
                    veHUD.timeDoTre = 0.3f;
                }
            }
        }
        // chức năng giới thiệu game
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.GIOI_THIEU_GAME) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            if (x >= (350-140)/2f && x <= (350-140)/2f + 140 && y >= viewY && y <= viewY + viewHeight) {
                layout.setText(
                    veHUD.font,
                    "Xin chào mọi người!\n" +
                        "Chúng mình là Phạm Hải Đăng và Lê Đình Thành, hai sinh viên sắp bước sang năm 2 tại Học viện Công nghệ Bưu chính Viễn thông (PTIT).\n" +
                        "\n" +
                        "Trong thời gian rảnh, tụi mình đã cùng nhau thực hiện một dự án nhỏ — game clone Ngọc Rồng Online (Dragon Boy) — nhằm ôn lại tuổi thơ và thử sức với lập trình game từ con số 0.\n" +
                        "\n" +
                        "Game được phát triển bằng Java & LibGDX, với mong muốn tái hiện lại không khí săn đệ, đập sao pha lê, đi doanh trại, và những ngày vui chơi đầy kỷ niệm mà ai từng chơi Ngọc Rồng Online chắc hẳn sẽ nhớ mãi.\n" +
                        "\n" +
                        "Dự án vẫn đang được hoàn thiện và tiếp tục bổ sung thêm nhiều nội dung mới.\n" +
                        "Rất mong nhận được sự ủng hộ và góp ý từ mọi người!\n" +
                        "\n" +
                        "Liên hệ:\n" +
                        " Phạm Hải Đăng — dangph.ptit@gmail.com\n" +
                        " Lê Đình Thành — thanhld.ptit@gmail.com",
                    new Color(83 / 255f, 41 / 255f, 5 / 255f, 1),
                    320,
                    Align.left,
                    true
                );
                float h = layout.height;
                float nutY = 444-115-20-20-70 - h + veHUD.scrollYPhai;
                if (y >= nutY && y <= nutY + 50 &&
                    x >= (350-140)/2f && x <= (350-140)/2f + 140) {
                    veHUD.isThongBaoOKPressed = 0.3f;
                }
            }
        }
        // chuc nang mini game
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME && veHUD.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.NONE) {
            for (int i = 0; i < 2; i++) {
                if (x >= (Gdx.graphics.getWidth()-240)/2f + i * 120 && x <= (Gdx.graphics.getWidth()-240)/2f + i * 120 + 115 && y >= 120 - 115 && y <= 120) {
                    veHUD.nutClickTimer3 = 0.3f;
                    veHUD.nuthanhtrangchon = i;
                }
            }
            if (x < (Gdx.graphics.getWidth()-240)/2f || x > (Gdx.graphics.getWidth()-240)/2f + 240) {
                veHUD.trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
            } else {
                if (y > 120) {
                    veHUD.trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
                }
            }
        }
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME && veHUD.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM) {
            for (int i = 0; i < 3; i++) {
                if (x >= (Gdx.graphics.getWidth()-360)/2f + i * 120 && x <= (Gdx.graphics.getWidth()-360)/2f + i * 120 + 115 && y >= 120 - 115 && y <= 120) {
                    veHUD.nutClickTimer3 = 0.3f;
                    veHUD.nuthanhtrangchon = i;
                }
            }
            if (x < (Gdx.graphics.getWidth()-360)/2f || x > (Gdx.graphics.getWidth()-360)/2f + 360) {
                veHUD.trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE;
            } else {
                if (y > 120) {
                    veHUD.trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE;
                }
            }
        }
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME && veHUD.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.HUONG_DAN_THEM_CSMM) {
            float i = 1;
            if (x >= (Gdx.graphics.getWidth()-360)/2f + i * 120 && x <= (Gdx.graphics.getWidth()-360)/2f + i * 120 + 115 && y >= 120 - 115 && y <= 120) {
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon = i;
            }
            if (x < (Gdx.graphics.getWidth()-360)/2f || x > (Gdx.graphics.getWidth()-360)/2f + 360) {
                veHUD.trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM;
            } else {
                if (y > 120) {
                    veHUD.trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CSMM;
                }
            }
        }
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME && veHUD.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CSMM) {
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 12;
            if (x >= nutX-81 && x <= nutX-81 + 140 && y >= nutY && y <= nutY + 50 ) {
                veHUD.isThongBaoOKPressed=0.3f;
                veHUD.nutduocchon = 1;
            }
            if (x >= nutX+81 && x <= nutX+81 + 140 && y >= nutY && y <= nutY + 50) {
                veHUD.isThongBaoOKPressed=0.3f;
                veHUD.nutduocchon = 2;
            }
        }
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME && veHUD.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE) {
            for (int i = 0; i < 3; i++) {
                if (x >= (Gdx.graphics.getWidth()-360)/2f + i * 120 && x <= (Gdx.graphics.getWidth()-360)/2f + i * 120 + 115 && y >= 120 - 115 && y <= 120) {
                    veHUD.nutClickTimer3 = 0.3f;
                    veHUD.nuthanhtrangchon = i;
                }
            }
            if (x < (Gdx.graphics.getWidth()-360)/2f || x > (Gdx.graphics.getWidth()-360)/2f + 360) {
                veHUD.trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE;
            } else {
                if (y > 120) {
                    veHUD.trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE;
                }
            }
        }
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME && veHUD.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.HUONG_DAN_THEM_CHAN_LE) {
            float i = 1;
            if (x >= (Gdx.graphics.getWidth()-360)/2f + i * 120 && x <= (Gdx.graphics.getWidth()-360)/2f + i * 120 + 115 && y >= 120 - 115 && y <= 120) {
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon = i;
            }
            if (x < (Gdx.graphics.getWidth()-360)/2f || x > (Gdx.graphics.getWidth()-360)/2f + 360) {
                veHUD.trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE;
            } else {
                if (y > 120) {
                    veHUD.trangThaiChucNangHUDChucNangMiniGame = TrangThaiChucNangHUD_ChucNang_MiniGame.NONE_CHAN_LE;
                }
            }
        }
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME && veHUD.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CHAN_LE) {
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 12;
            if (x >= nutX-81 && x <= nutX-81 + 140 && y >= nutY && y <= nutY + 50 ) {
                veHUD.isThongBaoOKPressed=0.3f;
                veHUD.nutduocchon = 1;
            }
            if (x >= nutX+81 && x <= nutX+81 + 140 && y >= nutY && y <= nutY + 50) {
                veHUD.isThongBaoOKPressed=0.3f;
                veHUD.nutduocchon = 2;
            }
        }
        // chức năng thông báo game
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.THONG_BAO && veHUD.trangThaiChucNangHUDChucNangThongBao == TrangThaiChucNangHUD_ChucNang_ThongBao.NONE) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 5;

            // Kiểm tra có click vào vùng không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {
                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    veHUD.oChiSoDangChon = index;
                    veHUD.timeDoTre = 0.3f;
                }
            }
        }
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) {
            if (x > 1020-350 && x <= 1020) {
                veHUD.dangChonHanhTrangPhai = true;
            } else {
                veHUD.dangChonHanhTrangPhai = false;
            }
            if (x > 0 && x<= 350) {
                veHUD.dangChonHanhTrangTrai = true;
            } else {
                veHUD.dangChonHanhTrangTrai = false;
            }
            if (!veHUD.DangHienPopupThongTin1 && !veHUD.DangHienPopupThongTin2 && !veHUD.dangHienThongBao && duLieuNguoiChoi.coDeTu() && veHUD.chucNangDeTuDangChon == 0) {
                float viewY = 35;
                float viewHeight = 444 - 35;
                int KhoangCachItem = 61;
                boolean duDieuKien = false;
                duDieuKien = x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight;
                if (duDieuKien) {
                    float relativeY = y - viewY;
                    float realY = veHUD.scrollYTrai + (viewHeight - relativeY);
                    int index = (int) (realY / KhoangCachItem);
                    if (index >= 0 && index <= 5) {
                        veHUD.hangTrangDeTuDangChon = index;
                        ArrayList<Item> danhSach = duLieuNguoiChoi.deTu.getHanhTrangDangMac();
                        if (danhSach.get(veHUD.hangTrangDeTuDangChon) != null) {
                            Item item = danhSach.get(veHUD.hangTrangDeTuDangChon);
                            veHUD.itemm = item;
                        } else {
                            veHUD.itemm = null;
                        }
                        if (veHUD.itemm != null) {
                            veHUD.DangHienPopupThongTin2 = true;
                            veHUD.PopupHanhTrangX_Trai = 5;
                            veHUD.PopupHanhTrangW_Trai = 360;
                            veHUD.PopupHanhTrangY_Trai = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollYTrai;
                            veHUD.PopupHanhTrangH_Trai = 0;
                            veHUD.TimeChoHienPopup1 = 0.3f;
                            veHUD.vuaMoPopupThongTin = true;
                        }
                    } else if (index > 5 && index <= 9 ) {
                        veHUD.hangTrangDeTuDangChon = index;
                    }
                }
            }
        }
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU && !veHUD.DangHienPopupThongTin1 && !veHUD.DangHienPopupThongTin2 && !veHUD.dangHienThongBao) {
            if (x > 350 && x <= 1020-350) {
                veHUD.trangThaiChucNangHUDChucNang = TrangThaiChucNangHUD_ChucNang.NONE;
                veHUD.scrollYPhai = 0;
                veHUD.oChiSoDangChon = -1;
                veHUD.chucNangDeTuDangChon = 0;
            }
            for (int i = 0; i < 2; i++) {
                if (x >= (350-80)/2f-3-40-1.5f+i*80+3 && x <= (350-80)/2f-3-40-1.5f+i*80+3 + 80 && y >= 450 && y <= 450 + 52){
                    veHUD.chucNangDeTuDangChon = i;
                    veHUD.hangTrangDeTuDangChon = -1;
                    veHUD.scrollYTrai = 0;
                    veHUD.clickY = y;
                    veHUD.clickX = x;
                    veHUD.timeGlow = 0.3f;
                }
            }
        }
        // tắt rương đồ -  để gần logic popup đệ tử
        if (veHUD.dangHienRuongDo) {
            if (x > 1020-350 && x <= 1020) {
                veHUD.dangChonHanhTrangPhai = true;
            } else {
                veHUD.dangChonHanhTrangPhai = false;
            }
            if (x > 0 && x<= 350) {
                veHUD.dangChonHanhTrangTrai = true;
            } else {
                veHUD.dangChonHanhTrangTrai = false;
            }
            if (!veHUD.DangHienPopupThongTin1 && !veHUD.DangHienPopupThongTin3 && !veHUD.dangHienThongBao) {
                float viewY = 35;
                float viewHeight = 444 - 35;
                int KhoangCachItem = 49;
                boolean duDieuKien = false;
                duDieuKien = x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight;
                if (duDieuKien) {
                    float relativeY = y - viewY;
                    float realY = veHUD.scrollYTrai + (viewHeight - relativeY);
                    int index = (int) (realY / KhoangCachItem);
                    if (index >= 0 && index <= 19) {
                        veHUD.hanhTrangRuongDoDangChon = index;
                        ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrangRuongDo();
                        if (index < danhSach.toArray().length) {
                            Item item = danhSach.get(veHUD.hanhTrangRuongDoDangChon);
                            veHUD.itemm = item;
                        } else {
                            veHUD.itemm = null;
                        }
                        if (veHUD.itemm != null) {
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
            }
        }
        if (veHUD.dangHienRuongDo && !veHUD.DangHienPopupThongTin1 && !veHUD.DangHienPopupThongTin3 && !veHUD.dangHienThongBao) {
            if (x > 350 && x <= 1020-350) {
                veHUD.dangHienRuongDo = false;
                veHUD.scrollYPhai = 0;
                veHUD.scrollYTrai = 0;
                veHUD.hanhTrangRuongDoDangChon = -1;
                veHUD.vuaTatRuongDo = true;
            }
        }

        // chức năng đệ tử game
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU && veHUD.chucNangDeTuDangChon == 1) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 5;

            // Kiểm tra có click vào vùng không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {
                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    veHUD.oChiSoDangChon = index;
                    veHUD.timeDoTre = 0.3f;
                }
            }
        }
        // chức năng chọn nhạc nền
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.NHAC_NEN) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = veHUD.nhacNen.length;

            // Kiểm tra có click vào vùng không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {
                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = veHUD.scrollYPhai + (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    veHUD.oChiSoDangChon = index;
                    veHUD.timeDoTre = 0.3f;
                }
            }
        }
        // Chuc nang tai khoan
        if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.TAI_KHOAN) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 5;

            // Kiểm tra có click vào vùng không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {
                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = veHUD.scrollYPhai + (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    veHUD.oChiSoDangChon = index;
                    veHUD.timeDoTre = 0.3f;
                }
            }
        }

        // Popup bỏ vật phẩm
        if (veHUD.dangHienThongBao) {
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 50;
            if (x >= nutX-81 && x <= nutX-81 + 140 && y >= nutY && y <= nutY + 50 ) {
                veHUD.isThongBaoOKPressed=0.3f;
                veHUD.nutduocchon = 1;
            }
            if (x >= nutX+81 && x <= nutX+81 + 140 && y >= nutY && y <= nutY + 50) {
                veHUD.isThongBaoOKPressed=0.3f;
                veHUD.nutduocchon = 2;
            }
        }

        // popup hanh trang
        if (veHUD.DangHienPopupThongTin1) {
            if (veHUD.vuaMoPopupThongTin) {
                veHUD.vuaMoPopupThongTin = false;
                return;
            }
            if (x > 0 && x <= 360 && (y > veHUD.PopupHanhTrangY_Trai + veHUD.PopupHanhTrangH_Trai || y < veHUD.PopupHanhTrangY_Trai - 130)) {
                if (!(veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) && !veHUD.dangHienRuongDo && !veHUD.dangHienPopupNhanVatPhai) {
                    veHUD.DangHienPopupThongTin1 = false;
                    veHUD.TimeChoHienPopup = 0;
                }
            } if (x > 360 && x <= 1020) {
                if (!(veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) && !veHUD.dangHienRuongDo && !veHUD.dangHienPopupNhanVatPhai) {
                    veHUD.DangHienPopupThongTin1 = false;
                    veHUD.TimeChoHienPopup = 0;
                }
            } if (x > 1020 - 360 && x <= 1020 && (y > veHUD.PopupHanhTrangY_Phai + veHUD.PopupHanhTrangH_Phai || y < veHUD.PopupHanhTrangY_Phai - 130)) {
                if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU || veHUD.dangHienRuongDo && veHUD.dangHienPopupNhanVatPhai) {
                    veHUD.DangHienPopupThongTin1 = false;
                    veHUD.TimeChoHienPopup = 0;
                }
            } if (x > 0 && x <= 1020-360) {
                if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) {
                    veHUD.DangHienPopupThongTin1 = false;
                    veHUD.TimeChoHienPopup = 0;
                    veHUD.dangChonHanhTrangPhai = true;
                    veHUD.dangChonHanhTrangTrai = false;
                }
                if (veHUD.dangHienRuongDo) {
                    veHUD.DangHienPopupThongTin1 = false;
                    veHUD.TimeChoHienPopup = 0;
                    veHUD.dangChonHanhTrangPhai = true;
                    veHUD.dangChonHanhTrangTrai = false;
                }
                if (veHUD.dangHienPopupNhanVatPhai) {
                    veHUD.DangHienPopupThongTin1 = false;
                    veHUD.TimeChoHienPopup = 0;
                    veHUD.dangChonHanhTrangPhai = true;
                }
            }
        }
        if (veHUD.DangHienPopupThongTin1) {
            float xCongThem = 0,yNut;
            // npc rương đồ và đệ ( popup nhân vật phải ở npc thì có logic nút 2 bên riêng ở trong ui của npc )
            if (veHUD.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU || veHUD.dangHienRuongDo) {
                xCongThem = 1020 - 360 - 10;
                yNut = veHUD.PopupHanhTrangY_Phai - 115;
            } else {
                xCongThem = 0;
                yNut = veHUD.PopupHanhTrangY_Trai - 115;
            }

            if (x > 1 + xCongThem && x < 115 + xCongThem && y >= yNut && y <= yNut + 115){
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon=1;
            } else if (x > 121 + xCongThem && x < 115+120 + xCongThem && y >= yNut && y <= yNut + 115){
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon=2;
            } else if (x > 241 + xCongThem && x < 115 + 120*2 + xCongThem && y >= yNut && y <= yNut + 115 &&
                (veHUD.itemm.getLoai() == LoaiItem.AO ||
                veHUD.itemm.getLoai() == LoaiItem.QUAN ||
                veHUD.itemm.getLoai() == LoaiItem.GIAY ||
                veHUD.itemm.getLoai() == LoaiItem.GANG ||
                veHUD.itemm.getLoai() == LoaiItem.CAITRANG ||
                veHUD.itemm.getLoai() == LoaiItem.AVATAR ||
                veHUD.itemm.getLoai() == LoaiItem.RADA) && duLieuNguoiChoi.coDeTu()
                && !veHUD.dangHienRuongDo) {
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon = 3;
            } else if (x > 241 + xCongThem && x < 115 + 120*2 + xCongThem && y >= yNut && y <= yNut + 115 && veHUD.dangHienRuongDo) {
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon = 3;
            }
        }
        if (veHUD.DangHienPopupThongTin2) {
            float yNut = veHUD.PopupHanhTrangY_Trai - 115;
            if (x > 1 && x < 115 && y >= yNut && y <= yNut + 115){
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon=4;
            } else if (x > 121 && x < 115+120 && y >= yNut && y <= yNut + 115){
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon=5;
            }
        }

        if (veHUD.DangHienPopupThongTin2) {
            if (veHUD.vuaMoPopupThongTin) {
                veHUD.vuaMoPopupThongTin = false;
                return;
            }
            if (x > 0 && x <= 360 && (y > veHUD.PopupHanhTrangH_Trai + veHUD.PopupHanhTrangH_Trai || y < veHUD.PopupHanhTrangY_Trai - 130)) {
                veHUD.DangHienPopupThongTin2 = false;
                veHUD.TimeChoHienPopup = 0;
            }
            if (x > 360 && x <= 1020) {
                veHUD.DangHienPopupThongTin2 = false;
                veHUD.TimeChoHienPopup = 0;
                veHUD.dangChonHanhTrangPhai = false;
                veHUD.dangChonHanhTrangTrai = true;
            }
        }

        if (veHUD.DangHienPopupThongTin3) {
            if (veHUD.vuaMoPopupThongTin) {
                veHUD.vuaMoPopupThongTin = false;
                return;
            }
            if (x > 0 && x <= 360 && (y > veHUD.PopupHanhTrangY_Trai + veHUD.PopupHanhTrangH_Trai || y < veHUD.PopupHanhTrangY_Trai - 130)) {
                veHUD.DangHienPopupThongTin3 = false;
                veHUD.TimeChoHienPopup = 0;
                veHUD.dangChonHanhTrangPhai = false;
                veHUD.dangChonHanhTrangTrai = true;
            } if (x > 360 && x <= 1020) {
                veHUD.DangHienPopupThongTin3 = false;
                veHUD.TimeChoHienPopup = 0;
                veHUD.dangChonHanhTrangPhai = false;
                veHUD.dangChonHanhTrangTrai = true;
            }
        }
        // Nút của riêng rương đồ, npc làm trong phần click của NPC
        if (veHUD.DangHienPopupThongTin3 && veHUD.dangHienRuongDo) {
            float yNut = veHUD.PopupHanhTrangY_Trai - 115;
            if (x > 1 && x < 115 && y >= yNut && y <= yNut + 115){
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon=4;
            } else if (x > 121 && x < 115+120 && y >= yNut && y <= yNut + 115){
                veHUD.nutClickTimer3 = 0.3f;
                veHUD.nuthanhtrangchon=5;
            }
        }
        // Tắt popup thông tin
        if (veHUD.DangHienPopupThongTin) {
            if (veHUD.vuaMoPopupThongTin) {
                veHUD.vuaMoPopupThongTin = false;
                return;
            }
            if (veHUD.oChiSoDangChon != 5) {
                if (x > 0 && x <= 360 && (y > veHUD.PopupThongTinY + veHUD.PopupThongTinH || y < veHUD.PopupThongTinY - 130)) {
                    veHUD.DangHienPopupThongTin = false;
                    veHUD.TimeChoHienPopup = 0;
                } else if (x > 360 && x <= 1020) {
                    veHUD.DangHienPopupThongTin = false;
                    veHUD.TimeChoHienPopup = 0;
                }
            } else {
                if (y > veHUD.PopupThongTinY + veHUD.PopupThongTinH) {
                    veHUD.DangHienPopupThongTin = false;
                    veHUD.TimeChoHienPopup = 0;
                } else if (x < veHUD.PopupThongTinX || x > veHUD.PopupThongTinX + veHUD.PopupThongTinW) {
                    veHUD.DangHienPopupThongTin = false;
                    veHUD.TimeChoHienPopup = 0;
                }
            }
        }
        if (veHUD.DangHienPopupThongTin){
            if (veHUD.oChiSoDangChon >= 0 && veHUD.oChiSoDangChon < 4) {
                int[] buocTangTheoChiSo = {20, 20, 1, 1};
                int buocTang = buocTangTheoChiSo[veHUD.oChiSoDangChon];

                int chiSoGoc = switch (veHUD.oChiSoDangChon) {
                    case 0 -> duLieuNguoiChoi.getHpGoc();
                    case 1 -> duLieuNguoiChoi.getKiGoc();
                    case 2 -> duLieuNguoiChoi.getSucDanhGoc();
                    case 3 -> duLieuNguoiChoi.getGiapGoc();
                    default -> 0;
                };

                float yNut = veHUD.PopupThongTinY - 115;
                for (int i = 0; i < 3; i++) {
                    int soLanTang = (int) Math.pow(10, i);
                    int giaTriTang = buocTang * soLanTang;
                    long chiPhi = veHUD.tinhChiPhiTiemNang(veHUD.oChiSoDangChon, chiSoGoc, soLanTang, buocTang);

                    boolean trongVungNut = (x >= 1 + i * 120 && x <= 1 + i * 120 + 114 && y >= yNut && y <= yNut + 114);

                    if (trongVungNut) {
                        veHUD.nutClickTimer = 0.3f;
                        veHUD.oChiSoDangChonTamThoi = veHUD.oChiSoDangChon;
                        veHUD.giaTriTangTamThoi = giaTriTang;
                        veHUD.chiPhiTamThoi = chiPhi;
                        veHUD.nutduocchon=i;
                    }
                }
            } else if (veHUD.oChiSoDangChon==4){
                int gioiHanToiDa = 10; // Crit tối đa 10%
                float yNut = veHUD.PopupThongTinY - 115;
                long chiPhi = veHUD.tinhChiPhiTiemNang(4, duLieuNguoiChoi.getChiMangGoc(), 1, 1);
                if (chiPhi<=0){chiPhi=10000000;}
                boolean trongVungNut = (x >= 1  && x <= 1 + 114 && y >= yNut && y <= yNut + 114);

                if (trongVungNut) {
                    veHUD.nutClickTimer = 0.3f;
                    veHUD.oChiSoDangChonTamThoi = veHUD.oChiSoDangChon;
                    veHUD.giaTriTangTamThoi = 1;
                    veHUD.chiPhiTamThoi = chiPhi;
                }
            } else if (veHUD.oChiSoDangChon > 5 && veHUD.oChiSoDangChon <= 15){
                float yNut = veHUD.PopupThongTinY - 115;
                int capskill = duLieuNguoiChoi.getCapSkill(veHUD.oChiSoDangChon-6);
                if (capskill >= 1){
                    if (x > 1 && x < 115 && y >= yNut && y <= yNut + veHUD.PopupThongTinH){
                        veHUD.nutClickTimer1 = 0.3f;
                        veHUD.nutduocchon=1;
                    } else if (x > 121 && x < 115+120 && y >= yNut && y <= yNut + veHUD.PopupThongTinH){
                        veHUD.nutClickTimer1 = 0.3f;
                        veHUD.nutduocchon=2;
                    }
                }
                else {
                    if (x > 1 && x < 115 && y >= yNut && y <= yNut + veHUD.PopupThongTinH){
                        veHUD.nutClickTimer1 = 0.3f;
                        veHUD.nutduocchon=2;
                    }
                }
            }
        }
        if (veHUD.HienPopUpGanSkill){
            if (x < 210 || x > 210 + 4*120 + 114 ){
                veHUD.HienPopUpGanSkill = false;
            } else if ( x >= 210 && x<=210 + 4*120 && y>120){
                veHUD.HienPopUpGanSkill = false;
            } else {
                for (int i = 0; i < 5; i++){
                    if (x>=210 + i * 120 && x <= 210 + i * 120+114 && y>=5 && y<=5+114){
                        veHUD.nutClickTimer2 = 0.3f;
                        veHUD.nutduocchon = i;
                    }
                }
            }
        }
        if (veHUD.dangHienKhungChat) {
            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 12;
            if (x >= nutX-81 && x <= nutX-81 + 140 && y >= nutY && y <= nutY + 50 ) {
                veHUD.isThongBaoOKPressed=0.3f;
                veHUD.nutduocchon = 1;
            }
            if (x >= nutX+81 && x <= nutX+81 + 140 && y >= nutY && y <= nutY + 50) {
                veHUD.isThongBaoOKPressed=0.3f;
                veHUD.nutduocchon = 2;
            }
        }
    }

    public boolean checkChuotTrongNut(float chuotX, float chuotY,float nutX, float nutY, float nutW ,float nutH) {
        if (chuotX >= nutX && chuotX <= nutX + nutW && chuotY >= nutY && chuotY <= nutY + nutH) {
            return true;
        }
        return false;
    }
}



