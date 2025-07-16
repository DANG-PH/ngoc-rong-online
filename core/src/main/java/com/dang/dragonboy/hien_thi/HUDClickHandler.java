package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
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

    public void xuLyClick(int x, int y) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // === VÙNG Ô CHAT ===
        int ochatW = 60;
        int ochatH = 60;
        float ochatX = screenWidth - ochatW - 15;
        float ochatY = screenHeight-10-ochatH;
        if (x >= ochatX && x <= ochatX + 60 && y >= ochatY && y <= ochatY + 60) {
            if (!veHUD.dangHienPopup && !veHUD.dangHienKhungChat) {
                veHUD.clickOChat();
            }
        }

        // === VÙNG Ô ĐẬU THẦN ===
        int odauthanW = 75;
        int odauthanH = 75;
        float odauthanX = screenWidth - odauthanW - 10;
        float odauthanY = 10;
        if (x >= odauthanX && x <= odauthanX + 75 && y >= odauthanY && y <= odauthanY + 75) {
            if (!veHUD.dangHienPopup && !veHUD.dangHienKhungChat) {
                veHUD.clickODauThan();
            }
        }
        // Vùng mở popup
        float nutPopupX = 0f;
        float nutPopupY = screenHeight / 4f * 3;
        if (x >= nutPopupX && x <= nutPopupX + 25 && y >= nutPopupY && y <= nutPopupY + 35) {
            if (!veHUD.dangHienKhungChat) {
                veHUD.hienPopupNhanVat();
            }
        }

        if (veHUD.dangHienPopup && !veHUD.dangHienThongBao && !veHUD.dangHienKhungChat) {
            if (veHUD.vuaMoPopup) {
                veHUD.vuaMoPopup = false;
                return;
            }
            // nutX để tắt popup
            float nutXW = nutX.getWidth() * 0.5f;
            float nutXH = nutX.getHeight() * 0.55f;
            float nutXX = 350 - nutXW - 6;
            float nutXY = 610 - nutXH - 2;
            if (x >= nutXX && x <= nutXX + nutXW && y >= nutXY && y <= nutXY + nutXH) {
                if (veHUD.dangHienGioiThieuGame) {
                    veHUD.dangHienGioiThieuGame = false;
                    veHUD.scrollY = 0;
                    veHUD.oChiSoDangChon = -1;
                } else if (veHUD.dangHienThongBaoGame) {
                    veHUD.dangHienThongBaoGame = false;
                    veHUD.scrollY = 0;
                    veHUD.oChiSoDangChon = -1;
                } else {
                    veHUD.tatPopupNhanVat();
                    veHUD.hangTrangDangChon = -1;
                    veHUD.oChiSoDangChon = -1;
                }
            } else if (x > 350 && x <= 1020 && !veHUD.DangHienPopupThongTin && !veHUD.HienPopUpGanSkill && !veHUD.DangHienPopupThongTin1) {
                if (veHUD.dangHienGioiThieuGame) {
                    veHUD.dangHienGioiThieuGame = false;
                    veHUD.scrollY = 0;
                    veHUD.oChiSoDangChon = -1;
                } else if (veHUD.dangHienThongBaoGame) {
                    if (veHUD.dangHienThongBaoLienHeAdmin) {
                        veHUD.dangHienThongBaoLienHeAdmin = false;
                        veHUD.scrollY = 0;
                        veHUD.oChiSoDangChon = -1;
                    } else if (veHUD.dangHienThongBaoCapNhat) {
                        veHUD.dangHienThongBaoCapNhat = false;
                        veHUD.scrollY = 0;
                        veHUD.oChiSoDangChon = -1;
                    } else if (veHUD.dangHienThongBaox2x3) {
                        veHUD.dangHienThongBaox2x3 = false;
                        veHUD.scrollY = 0;
                        veHUD.oChiSoDangChon = -1;
                    } else if (veHUD.dangHienThongBaoGiftCode) {
                        veHUD.dangHienThongBaoGiftCode = false;
                        veHUD.scrollY = 0;
                        veHUD.oChiSoDangChon = -1;
                    } else if (veHUD.dangHienThongBaoEvent) {
                        veHUD.dangHienThongBaoEvent = false;
                        veHUD.scrollY = 0;
                        veHUD.oChiSoDangChon = -1;
                    } else {
                        veHUD.dangHienThongBaoGame = false;
                        veHUD.scrollY = 0;
                        veHUD.oChiSoDangChon = -1;
                    }
                } else {
                    veHUD.tatPopupNhanVat();
                    veHUD.hangTrangDangChon = -1;
                    veHUD.oChiSoDangChon = -1;
                }
            }
            // cac nut chuc nang
            for (int i = 0; i < 5; i++) {
                if (x >= 2+i*68+3 && x <= 2+i*68+3 + 68 && y >= 450 && y <= 450 + 52){
                    veHUD.chucNangDangChon=i;
                    veHUD.scrollY = 0;
                    veHUD.oChiSoDangChon = -1;
                    veHUD.hangTrangDangChon = -1;
                    veHUD.dangHienGioiThieuGame = false;
                    veHUD.dangHienThongBaoGame = false;
                    veHUD.dangHienThongBaoLienHeAdmin = false;
                    veHUD.dangHienThongBaox2x3 = false;
                    veHUD.dangHienThongBaoCapNhat = false;
                    veHUD.dangHienThongBaoGiftCode= false;
                    veHUD.dangHienThongBaoEvent = false;
                }
            }
        }
        if (veHUD.dangHienPopup && veHUD.chucNangDangChon == 1 && !veHUD.DangHienPopupThongTin1 && !veHUD.dangHienThongBao) {
            if (veHUD.vanBayDau){
                veHUD.vanbay = new Texture("vatpham/vanbay/"+"candauvan/candauvan.png");
                nhanVat.setIdVanBay("candauvan");
                nhanVat.setTenVanBay("Cân đẩu vân");
                nhanVat.setMoTaVanBay("Dùng để bay không tốn KI");
                nhanVat.setChisoVanBay(new int[] {0,0,0,0,0,0,0,0,0,0,0,0});
                nhanVat.setHanhTinhVanBay("traidat");
                nhanVat.setSucManhYeuCauVanBay(0);
                Item vanBay = new Item("candauvan", "Cân đẩu vân", LoaiItem.VANBAY, veHUD.vanbay, "Dùng để bay không tốn KI", 1, new int[] {0,0,0,0,0,0,0,0,0,0,0,0},"traidat",0, null,0,0,0,-1);
                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(vanBay,7);
            }
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 8 + 12;

            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {
                float relativeY = y - viewY;
                float realY = veHUD.scrollY + (viewHeight - relativeY);
                int index = (int) (realY / KhoangCachItem);
                veHUD.hangTrangDangChon = index;
                if (veHUD.hangTrangDangChon >= 8) {
                    int indexx = veHUD.hangTrangDangChon - 8;
                    ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();
                    if (indexx < danhSach.toArray().length) {
                        Item item = danhSach.get(indexx);
                        veHUD.itemm = item;
                        if (item.getLoai() == LoaiItem.CAITRANG) {
                            veHUD.itemDangChon = "caitrang";
                        } else if (item.getLoai() == LoaiItem.AVATAR) {
                            veHUD.itemDangChon = "avatar";
                        } else if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                            veHUD.itemDangChon = "giapluyentap";
                        } else if (item.getLoai() == LoaiItem.AO) {
                            veHUD. itemDangChon = "ao";
                        } else if (item.getLoai() == LoaiItem.QUAN) {
                            veHUD.itemDangChon = "quan";
                        } else if (item.getLoai() == LoaiItem.GANG) {
                            veHUD.itemDangChon = "gang";
                        } else if (item.getLoai() == LoaiItem.GIAY) {
                            veHUD.itemDangChon = "giay";
                        } else if (item.getLoai() == LoaiItem.RADA) {
                            veHUD.itemDangChon = "rada";
                        } else if (item.getLoai() == LoaiItem.VANBAY) {
                            veHUD.itemDangChon = "vanbay";
                        }
                    } else {
                        veHUD.itemm = null;
                    }
                    if (veHUD.itemm != null) {
                        veHUD.DangHienPopupThongTin1 = true;
                        veHUD.PopupHanhTrangX = 5;
                        veHUD.PopupHanhTrangW = 360;
                        veHUD.PopupHanhTrangY = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollY;
                        veHUD.PopupHanhTrangH = 0;
                        veHUD.TimeChoHienPopup = 0.3f;
                        veHUD.vuaMoPopupThongTin = true;
                    }
                } else {
                    ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrangDangMac();
                    if (danhSach.get(veHUD.hangTrangDangChon) != null){
                        Item item = danhSach.get(veHUD.hangTrangDangChon);
                        veHUD.itemm = item;
                        if (item.getLoai() == LoaiItem.CAITRANG) {
                            veHUD.itemDangChon = "caitrang";
                        } else if (item.getLoai() == LoaiItem.AVATAR) {
                            veHUD.itemDangChon = "avatar";
                        } else if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                            veHUD.itemDangChon = "giapluyentap";
                        } else if (item.getLoai() == LoaiItem.AO) {
                            veHUD.itemDangChon = "ao";
                        } else if (item.getLoai() == LoaiItem.QUAN) {
                            veHUD.itemDangChon = "quan";
                        } else if (item.getLoai() == LoaiItem.GANG) {
                            veHUD.itemDangChon = "gang";
                        } else if (item.getLoai() == LoaiItem.GIAY) {
                            veHUD.itemDangChon = "giay";
                        } else if (item.getLoai() == LoaiItem.RADA) {
                            veHUD.itemDangChon = "rada";
                        } else if (item.getLoai() == LoaiItem.VANBAY) {
                            veHUD.itemDangChon = "vanbay";
                        }
                    } else {
                        veHUD.itemm = null;
                    }
                    if (veHUD.itemm != null) {
                        veHUD.DangHienPopupThongTin1 = true;
                        veHUD.PopupHanhTrangX = 5;
                        veHUD.PopupHanhTrangW = 360;
                        veHUD.PopupHanhTrangY = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollY;
                        veHUD.PopupHanhTrangH = 0;
                        veHUD.TimeChoHienPopup = 0.3f;
                        veHUD.vuaMoPopupThongTin = true;
                    }
                }
            }
        }
        if (veHUD.dangHienPopup && veHUD.chucNangDangChon == 2 && !veHUD.DangHienPopupThongTin && !veHUD.HienPopUpGanSkill) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 61;
            int tongSoO = 15;

            // Kiểm tra có click vào vùng hành trang không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {

                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = veHUD.scrollY + (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    veHUD.oChiSoDangChon = index;
                    veHUD.DangHienPopupThongTin = true;
                    if (index >= 0 && index < 5) {
                        veHUD.PopupThongTinX = 5;
                        veHUD.PopupThongTinY = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollY;
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
                        veHUD.PopupThongTinY = viewY + viewHeight - (index + 1) * KhoangCachItem + veHUD.scrollY;
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
        if (veHUD.dangHienPopup && veHUD.chucNangDangChon == 4 && !veHUD.dangHienThongBaoGame && !veHUD.dangHienGioiThieuGame) {
            float viewY = 35;
            float viewHeight = 444 - 35;
            int KhoangCachItem = 49;
            int tongSoO = 10;

            // Kiểm tra có click vào vùng không
            if (x >= 3 && x <= 3 + 344 && y >= viewY && y <= viewY + viewHeight) {
                // Tính tọa độ tương đối trong khung scroll
                float relativeY = y - viewY;

                // Tính vị trí click từ đỉnh danh sách cuộn
                float realY = veHUD.scrollY + (viewHeight - relativeY);

                int index = (int)(realY / KhoangCachItem);

                if (index >= 0 && index < tongSoO) {
                    veHUD.oChiSoDangChon = index;
                    veHUD.timeDoTre = 0.3f;
                }
            }
        }
        if (veHUD.dangHienGioiThieuGame) {
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
                float nutY = 444-115-20-20-70 - h + veHUD.scrollY;
                if (y >= nutY && y <= nutY + 50 &&
                    x >= (350-140)/2f && x <= (350-140)/2f + 140) {
                    veHUD.isThongBaoOKPressed = 0.3f;
                }
            }
        }
        if (veHUD.dangHienThongBaoGame) {
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
            if (x > 0 && x <= 360 && (y > veHUD.PopupHanhTrangY + veHUD.PopupHanhTrangH || y < veHUD.PopupHanhTrangY - 130)) {
                veHUD.DangHienPopupThongTin1 = false;
                veHUD.TimeChoHienPopup = 0;
            } else if (x > 360 && x <= 1020) {
                veHUD.DangHienPopupThongTin1 = false;
                veHUD.TimeChoHienPopup = 0;
            }
        }
        if (veHUD.DangHienPopupThongTin1) {
            float yNut = veHUD.PopupHanhTrangY - 115;
            if (veHUD.hangTrangDangChon!=7){
                if (x > 1 && x < 115 && y >= yNut && y <= yNut + veHUD.PopupHanhTrangH){
                    veHUD.nutClickTimer3 = 0.3f;
                    veHUD.nuthanhtrangchon=1;
                } else if (x > 121 && x < 115+120 && y >= yNut && y <= yNut + veHUD.PopupHanhTrangH){
                    veHUD.nutClickTimer3 = 0.3f;
                    veHUD.nuthanhtrangchon=2;
                }
            } else {
                if (x > 1 && x < 115 && y >= yNut && y <= yNut + veHUD.PopupHanhTrangH) {
                    veHUD.nutClickTimer3 = 0.3f;
                    veHUD.nuthanhtrangchon = 3;
                }
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
                int[] gioiHanToiDa = {550000, 550000, 25000, 3000}; // Giới hạn HP, KI, Sức đánh, Giáp
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
                    boolean duTiemNang = duLieuNguoiChoi.getTiemNangNhanVat() >= chiPhi;
                    boolean khongVuotGioiHan = chiSoGoc + giaTriTang <= gioiHanToiDa[veHUD.oChiSoDangChon];

                    if (trongVungNut && duTiemNang && khongVuotGioiHan) {
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
                boolean duTiemNang = duLieuNguoiChoi.getTiemNangNhanVat() >= chiPhi;
                boolean khongVuotGioiHan = duLieuNguoiChoi.getChiMangGoc() + 1 <= gioiHanToiDa;

                if (trongVungNut && duTiemNang && khongVuotGioiHan) {
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
}



