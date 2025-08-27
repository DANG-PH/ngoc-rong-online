package com.dang.dragonboy.hien_thi;

import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.nhan_vat.*;


public class HUDTinhToanChiSo {

    private VeHUD veHUD;
    private DuLieuNguoiChoi duLieuNguoiChoi;
    private NhanVat nhanVat;

    public HUDTinhToanChiSo(VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        this.veHUD = veHUD;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
        this.nhanVat = nhanVat;
    }

    public void capNhatChiSo(float delta) {
        // Lấy chỉ số gốc
        float hp = duLieuNguoiChoi.getHpToiDa();
        float ki = duLieuNguoiChoi.getKiToiDa();
        float sd;
        if (!veHUD.dangDungCuongNo) {
            sd = duLieuNguoiChoi.getSucDanhNhanVat();
        } else {
            sd = duLieuNguoiChoi.getSucDanhNhanVat()*2;
        }
        int cm = duLieuNguoiChoi.getChiMangNhanVat();
        int stcm = duLieuNguoiChoi.getSatThuongChiMang();
        int giamSatThuong = duLieuNguoiChoi.getGiamSatThuongNhanVat();

        float hp_de = 0,ki_de = 0,sd_de = 0;
        int cm_de = 0,stcm_de = 0,giamSatThuong_de = 0;
        if (duLieuNguoiChoi.coDeTu()) {
            hp_de = duLieuNguoiChoi.deTu.getHpToiDa();
            ki_de = duLieuNguoiChoi.deTu.getKiToiDa();
            sd_de = duLieuNguoiChoi.deTu.getSucDanhDeTu();
            cm_de = duLieuNguoiChoi.deTu.getChiMangDeTu();
            stcm_de = duLieuNguoiChoi.deTu.getSatThuongChiMang();
            giamSatThuong_de = duLieuNguoiChoi.deTu.getGiamSatThuongDeTu();
        }

        // ===== BÔNG TAI =====
        if (veHUD.dangHopThe) {
            hp += hp_de;
            ki += ki_de;
            sd += sd_de;

            switch (veHUD.bongTaiDangDung) {
                case "bongtaic1":
                    if (veHUD.bongTaiRongThan) {
                        hp *= 1.05f;
                        ki *= 1.05f;
                        sd *= 1.05f;
                    }
                    break;
                case "bongtaic2":
                    hp *= 1.1f;
                    ki *= 1.1f;
                    sd *= 1.1f;
                    break;
                case "bongtaic3":
                    hp *= 1.2f;
                    ki *= 1.2f;
                    sd *= 1.2f;
                    break;
            }
        }
        if (veHUD.dangBienKhi) {
            hp *= 1+veHUD.hpTangBienKhi/100f;
            sd *= 1+veHUD.sucDanhTangBienKhi/100f;
        }

        if (duLieuNguoiChoi.coDeTu() && duLieuNguoiChoi.deTu.dangBienKhi) {
            hp_de *= 1+duLieuNguoiChoi.deTu.hpTangBienKhi/100f;
            sd_de *= 1+duLieuNguoiChoi.deTu.sucDanhTangBienKhi/100f;
        }

        // ===== Bổ Huyết =====
        if (veHUD.dangDungBoHuyet) {
            hp *= 2;
        }

        // ===== Bổ Khí =====
        if (veHUD.dangDungBoKhi) {
            ki *= 2;
        }

        // ===== HUY HIỆU =====
        if (veHUD.dangDungHuyHieu) {
            cm += veHUD.huyHieuDangDung.getChiso()[3];
            stcm += veHUD.huyHieuDangDung.getChiso()[5];
            giamSatThuong += veHUD.huyHieuDangDung.getChiso()[12];
            hp *= (veHUD.huyHieuDangDung.getChiso()[6] / 100f + 1);
            ki *= (veHUD.huyHieuDangDung.getChiso()[7] / 100f + 1);
            sd *= (veHUD.huyHieuDangDung.getChiso()[8] / 100f + 1);
        }

        // ===== ĐEO LƯNG =====
        if (veHUD.dangDungDeoLung) {
            cm += veHUD.deoLungDangDung.getChiso()[3];
            stcm += veHUD.deoLungDangDung.getChiso()[5];
            giamSatThuong += veHUD.deoLungDangDung.getChiso()[12];
            hp *= (veHUD.deoLungDangDung.getChiso()[6] / 100f + 1);
            ki *= (veHUD.deoLungDangDung.getChiso()[7] / 100f + 1);
            sd *= (veHUD.deoLungDangDung.getChiso()[8] / 100f + 1);
            if (veHUD.deoLungDangDung.getId().equals("luoi_hai") && duLieuNguoiChoi.getHanhTrangDangMac().get(5) != null && duLieuNguoiChoi.getHanhTrangDangMac().get(5).getId().equals("goku_black_rose")) {
                hp*=1.04f;
                ki*=1.04f;
                sd*=1.04f;
            }
            if (veHUD.deoLungDangDung.getId().equals("canh_doi") && duLieuNguoiChoi.getHpHienTai()<=duLieuNguoiChoi.getHpHopThe()*0.5f) {
                cm += 10;
            }
            if (veHUD.deoLungDangDung.getId().equals("canh_ac_quy")) {
                if (duLieuNguoiChoi.getKiHienTai()>duLieuNguoiChoi.getKiHopThe()*0.8f) {
                    cm += 5;
                }
                if (veHUD.dangDungHuyHieu && veHUD.huyHieuDangDung.getId().equals("trum_cuoi")) {
                    cm += 5;
                    stcm += 10;
                }
            }
            if (veHUD.deoLungDangDung.getId().equals("kiem") && duLieuNguoiChoi.getHpHienTai()<=duLieuNguoiChoi.getHpHopThe()*0.4f) {
                sd*=1.15f;
            }
            if (veHUD.deoLungDangDung.getId().equals("hoa")) {
                float xacSuat = 0.000586f;
                if (veHUD.dangDungHuyHieu && veHUD.huyHieuDangDung.getId().equals("thien_tu")) {
                    xacSuat*=2;
                }
                if (Math.random()<xacSuat) {
                    duLieuNguoiChoi.tangHpHienTai(duLieuNguoiChoi.getHpHopThe()*0.02f);
                }
            }
            if (veHUD.deoLungDangDung.getId().equals("canh_thien_su")) {
                if (nhanVat.getTrangThai() == TrangThai.BAY_NGANG) {
                    giamSatThuong+=5;
                }
                if (duLieuNguoiChoi.getHanhTrangDangMac().get(7) != null && duLieuNguoiChoi.getHanhTrangDangMac().get(7).getId().equals("phuong_hoang_lua")) {
                    giamSatThuong+=10;
                }
            }
            if (veHUD.deoLungDangDung.getId().equals("canh_thien_than")) {
                if (duLieuNguoiChoi.getHpHienTai() == duLieuNguoiChoi.getHpHopThe()) {
                    sd*=1.1f;
                }
                if (duLieuNguoiChoi.getKiHienTai()<=duLieuNguoiChoi.getKiHopThe()*0.2f) {
                    stcm+=10;
                }
            }
        }

        // ===== AURA =====
        if (veHUD.dangDungAura) {
            cm += veHUD.auraDangDung.getChiso()[3];
            stcm += veHUD.auraDangDung.getChiso()[5];
            giamSatThuong += veHUD.auraDangDung.getChiso()[12];
            hp *= (veHUD.auraDangDung.getChiso()[6] / 100f + 1);
            ki *= (veHUD.auraDangDung.getChiso()[7] / 100f + 1);
            sd *= (veHUD.auraDangDung.getChiso()[8] / 100f + 1);
            if (veHUD.auraDangDung.getId().equals("tan_hon_rong_namek")) {
                int tongHpDangMac = 0;
                int tongSucDanhDangMac = 0;
                for (int i = 0; i < 5; i++) {
                    if (duLieuNguoiChoi.getHanhTrangDangMac().get(i) != null) {
                        tongHpDangMac += duLieuNguoiChoi.getHanhTrangDangMac().get(i).getChiso()[6];
                        tongSucDanhDangMac += duLieuNguoiChoi.getHanhTrangDangMac().get(i).getChiso()[8];
                    }
                }
                if (tongSucDanhDangMac*5f/3f > tongHpDangMac) {
                    if (duLieuNguoiChoi.getKiHienTai()>duLieuNguoiChoi.getKiHopThe()*0.7f) {
                        sd *= 1.1f;
                    }
                    if (duLieuNguoiChoi.getKiHienTai()<duLieuNguoiChoi.getKiHopThe()*0.2f) {
                        cm += 10;
                    }
                } else if (tongSucDanhDangMac*5f/3f < tongHpDangMac) {
                    if (duLieuNguoiChoi.getKiHienTai()>duLieuNguoiChoi.getKiHopThe()*0.7f) {
                        hp *= 1.1f;
                    }
                    if (duLieuNguoiChoi.getKiHienTai()<duLieuNguoiChoi.getKiHopThe()*0.2f) {
                        giamSatThuong += 10;
                    }
                } else {
                    if (duLieuNguoiChoi.getKiHienTai()>duLieuNguoiChoi.getKiHopThe()*0.7f) {
                        sd *= 1.05f;
                        hp *= 1.05f;
                    }
                    if (duLieuNguoiChoi.getKiHienTai()<duLieuNguoiChoi.getKiHopThe()*0.2f) {
                        cm += 5;
                        giamSatThuong += 5;
                    }
                }
            }
            if (veHUD.auraDangDung.getId().equals("tieu_doi_truong")) {
                if (nhanVat.getTrangThai() == TrangThai.DUNG_YEN) {
                    veHUD.timeChoBuffAuraTieuDoiTruong += delta;
                    if (veHUD.timeChoBuffAuraTieuDoiTruong > 3f) {
                        hp*=1.1f;
                    }
                } else {
                    veHUD.timeChoBuffAuraTieuDoiTruong = 0;
                    sd*=1.1f;
                }
            }
        }
        // ===== GIÁP LUYỆN TẬP =====
        if (veHUD.dangMacGiapLuyenTap) {
            var giap = duLieuNguoiChoi.getHanhTrangDangMac().get(6);

            // Giảm thời gian sử dụng
            giap.tangHanSuDung();
            nhanVat.setHanSuDungGiapLuyenTap(giap.getHanSuDung());

            float sdConLai;
            switch (giap.getId()) {
                case "glt_c3": sdConLai = 0.7f; break;
                case "glt_c2": sdConLai = 0.8f; break;
                case "glt_c1": sdConLai = 0.9f; break;
                default:       sdConLai = 1f;   break;
            }
            sd *= sdConLai;

        } else if (veHUD.itemGiapLuyenTapVuaCoi != null) {
            // Giảm thời gian còn lại
            if (veHUD.itemGiapLuyenTapVuaCoi.getHanSuDung() > 0f) {
                veHUD.itemGiapLuyenTapVuaCoi.giamHanSuDung();
                nhanVat.setHanSuDungGiapLuyenTap(veHUD.itemGiapLuyenTapVuaCoi.getHanSuDung());

                float sdCongThem;
                switch (veHUD.itemGiapLuyenTapVuaCoi.getId()) {
                    case "glt_c3": sdCongThem = 1.3f; break;
                    case "glt_c2": sdCongThem = 1.2f; break;
                    case "glt_c1": sdCongThem = 1.1f; break;
                    default:       sdCongThem = 1f;   break;
                }
                sd *= sdCongThem;
            }

            // tăng chí mạng
            if (veHUD.itemGiapLuyenTapVuaCoi.getHanSuDung() > 60f) {
                cm += 15;
                stcm += 30;
            }
        }

        // ===== DÙNG SKILL =====
        if (veHUD.dangHuytSao) {
            hp *= 1 + veHUD.hpTangHuytSao/100f;
        }

        // ===== CẬP NHẬT CHỈ SỐ =====
        //suphu
        duLieuNguoiChoi.setHpHopThe(hp);
        duLieuNguoiChoi.setKiHopThe(ki);
        duLieuNguoiChoi.setSdHopThe(sd);
        //detu
        duLieuNguoiChoi.deTu.setHpHopThe(hp_de);
        duLieuNguoiChoi.deTu.setKiHopThe(ki_de);
        duLieuNguoiChoi.deTu.setSdHopThe(sd_de);
        // skill
        if (veHUD.dangBienKhi) {
            cm = 110;
        }
        if (duLieuNguoiChoi.coDeTu()) {
            if (duLieuNguoiChoi.deTu.dangBienKhi) {
                cm_de = 110;
            }
        }
        if (veHUD.dangTtnl) {
            veHUD.timeNhayLanTiepTtnl += delta;
            veHUD.timeThongBaoHoiPhucTtnl += delta;
            if (veHUD.timeNhayLanTiepTtnl >= 1f) {
                veHUD.timeNhayLanTiepTtnl = 0;
                duLieuNguoiChoi.tangHpHienTai(duLieuNguoiChoi.getHpHopThe()*veHUD.hpHoiTtnl/100f);
                duLieuNguoiChoi.tangKiHienTai(duLieuNguoiChoi.getKiHopThe()*veHUD.KiHoiTtnl/100f);
            }
            if (duLieuNguoiChoi.getKiHienTai() == duLieuNguoiChoi.getKiHopThe() && duLieuNguoiChoi.getHpHienTai() == duLieuNguoiChoi.getHpHopThe() && veHUD.timeTtnl <= veHUD.timeTtnlMax-2f) {
                veHUD.huyTtnl();
                veHUD.dangHienTinNhanChat = true;
                veHUD.tinNhanChat = "Phục hồi năng lượng\n" + "100%";
            }
            if (veHUD.timeThongBaoHoiPhucTtnl >= 4.5f) {
                veHUD.timeThongBaoHoiPhucTtnl = 0f;
                veHUD.dangHienTinNhanChat = true;
                float ptHpHt = duLieuNguoiChoi.getHpHienTai()/duLieuNguoiChoi.getHpHopThe()*100f;
                float ptKiHt = duLieuNguoiChoi.getKiHienTai()/duLieuNguoiChoi.getKiHopThe()*100f;
                if (ptHpHt < ptKiHt) {
                    veHUD.tinNhanChat = "Phục hồi năng lượng\n" + (int)ptHpHt +"% HP";
                } else if (ptHpHt > ptKiHt) {
                    veHUD.tinNhanChat = "Phục hồi năng lượng\n" + (int)ptKiHt +"% KI";
                } else {
                    veHUD.tinNhanChat = "Phục hồi năng lượng\n" + (int)ptKiHt +"%";
                }
            }
        }
        // su phu
        duLieuNguoiChoi.setChiMangSuDung(cm);
        duLieuNguoiChoi.setSatThuongChiMangSuDung(stcm);
        duLieuNguoiChoi.setGiamSatThuongSuDung(giamSatThuong);
        // de tu
        duLieuNguoiChoi.deTu.setChiMangSuDung(cm_de);
        duLieuNguoiChoi.deTu.setSatThuongChiMangSuDung(stcm_de);
        duLieuNguoiChoi.deTu.setGiamSatThuongSuDung(giamSatThuong_de);

        if (veHUD.vuaHopThe) {
            duLieuNguoiChoi.setHpHienTai(duLieuNguoiChoi.getHpHopThe());
            duLieuNguoiChoi.setKiHienTai(duLieuNguoiChoi.getKiHopThe());
            veHUD.vuaHopThe = false;
        }
        if (veHUD.vuaBienKhi) {
            duLieuNguoiChoi.tangHpHienTai(duLieuNguoiChoi.getHpHopThe()/(1+veHUD.hpTangBienKhi/100f));
            veHUD.vuaBienKhi = false;
        }
        // Giới hạn HP/KI hiện tại
        if (duLieuNguoiChoi.getHpHienTai() > hp) duLieuNguoiChoi.setHpHienTai(hp);
        if (duLieuNguoiChoi.getKiHienTai() > ki) duLieuNguoiChoi.setKiHienTai(ki);

        if (duLieuNguoiChoi.deTu.getHpHienTai() > hp_de) duLieuNguoiChoi.deTu.setHpHienTai(hp_de);
        if (duLieuNguoiChoi.deTu.getKiHienTai() > ki_de) duLieuNguoiChoi.deTu.setKiHienTai(ki_de);
    }
}
