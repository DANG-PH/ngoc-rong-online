package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.dang.dragonboy.du_lieu.DeTu;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.nhan_vat.NhanVatCauHinh;
import com.dang.dragonboy.nhan_vat.NhanVatXuLy;
import com.dang.dragonboy.nhan_vat.DeTuXuLy;
import com.dang.dragonboy.nhan_vat.DeTuCauHinh;
import java.util.ArrayList;

public class HUDXulyitem {
    private final VeHUD veHUD;
    private DuLieuNguoiChoi duLieuNguoiChoi;
    private NhanVat nhanVat;

    public HUDXulyitem(VeHUD veHUD, GlyphLayout layout, DuLieuNguoiChoi duLieuNguoiChoi , NhanVat nhanVat) {
        this.veHUD = veHUD;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
        this.nhanVat = nhanVat;
    }
    public void macDo(int index) {
        if (index <= 7 && index >= 0){
            if (index == 5) {
                goCaiTrang(NhanVatXuLy.getDangMacCaiTrang(),false);
            } else if (index == 0) {
                goAo(false);
            } else if (index == 1) {
                goQuan(false);
            } else if (index == 2) {
                goGang(false);
            } else if (index == 3) {
                goGiay(false);
            } else if (index == 4) {
                goRada(false);
            } else if (index == 6) {
                goGiapLuyenTap(false);
            } else if (index == 7) {
                goVanBay(false);
            }
        }
        if (index >= 8) {
            int indexx = veHUD.hangTrangDangChon - 8;
            ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();

            if (indexx < danhSach.size()) {
                Item item = danhSach.get(indexx);
                veHUD.itemm = item;
                if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {

                    boolean loaiCaiTrangDangMac = NhanVatXuLy.getDangMacCaiTrang(); // cái đang mặc
                    boolean laCaiTrang = item.getLoai() == LoaiItem.CAITRANG;       // cái sắp mặc
                    if (veHUD.iconct == null) {
                        // Gán cải trang mới, không có cái cũ
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,5);
                        veHUD.iconct = item.getLinkTexture();
                        veHUD.avatardangmac = laCaiTrang ? nhanVat.getNhanvat() + "_base" : item.getId();
                        if (laCaiTrang) {
                            NhanVatXuLy.setDangMacCaiTrang(true);
                            NhanVatXuLy.setDangMacAvatar(false);
                        } else {
                            NhanVatXuLy.setDangMacAvatar(true);
                            NhanVatXuLy.setDangMacCaiTrang(false);
                        }
                        if (!veHUD.dangHopThe && !veHUD.dangBienKhi) {
                            NhanVatCauHinh c2 = laCaiTrang ? veHUD.Doicaitrang(item.getId()) : veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), item.getId(), veHUD.aodangmac, veHUD.quandangmac);
                            nhanVat.fixCaiTrang(c2);
                            veHUD.texAvt = new Texture(nhanVat.doiavatar());
                        }
                        tangchiso(item.getChiso());
                        danhSach.remove(indexx);
                    } else {
                        macCaiTrangMoi(item, indexx, danhSach, loaiCaiTrangDangMac, laCaiTrang);
                    }
                } else if (item.getLoai() == LoaiItem.AO) {
                    if (veHUD.ao == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,0);
                        veHUD.ao = item.getLinkTexture();
                        veHUD.aodangmac = item.getId();
                        veHUD.skha = item.getSetkichhoat();
                        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe && !veHUD.dangBienKhi) {
                            NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, item.getId(), veHUD.quandangmac);
                            nhanVat.fixCaiTrang(c2);
                        }
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacAo(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macAoMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.QUAN) {
                    if (veHUD.quan == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,1);
                        veHUD.quan = item.getLinkTexture();
                        veHUD.quandangmac = item.getId();
                        veHUD.skhq = item.getSetkichhoat();
                        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe && !veHUD.dangBienKhi) {
                            NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, veHUD.aodangmac, item.getId());
                            nhanVat.fixCaiTrang(c2);
                        }
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacQuan(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macQuanMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.GANG) {
                    if (veHUD.gang == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,2);
                        veHUD.gang = item.getLinkTexture();
                        veHUD.skhg = item.getSetkichhoat();
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacGang(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macGangMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.GIAY) {
                    if (veHUD.giay == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,3);
                        veHUD.giay = item.getLinkTexture();
                        veHUD.skhj = item.getSetkichhoat();
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacGiay(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macGiayMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.RADA) {
                    if (veHUD.rada == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,4);
                        veHUD.rada = item.getLinkTexture();
                        veHUD.skhrada = item.getSetkichhoat();
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacRada(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macRadaMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                    if (veHUD.giaplt == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,6);
                        veHUD.giaplt = item.getLinkTexture();
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacGlt(true);
                        veHUD.dangMacGiapLuyenTap = true;
                        danhSach.remove(indexx);
                    } else {
                        macGiapLuyenTapMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.VANBAY) {
                    if (veHUD.vanbay == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,7);
                        veHUD.vanbay = item.getLinkTexture();
                        nhanVat.dangMangVanBay = true;
                        nhanVat.doiVanBay(item.getId());
                        danhSach.remove(indexx);
                    } else {
                        macVanBayMoi(item, indexx, danhSach);
                    }
                }
            }
        }
    }

    public void macDoVuaLogin(Item item) {
        if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {

            boolean loaiCaiTrangDangMac = NhanVatXuLy.getDangMacCaiTrang(); // cái đang mặc
            boolean laCaiTrang = item.getLoai() == LoaiItem.CAITRANG;       // cái sắp mặc
            if (veHUD.iconct == null) {
                // Gán cải trang mới, không có cái cũ
                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,5);
                veHUD.iconct = item.getLinkTexture();
                veHUD.avatardangmac = laCaiTrang ? nhanVat.getNhanvat() + "_base" : item.getId();
                if (laCaiTrang) {
                    NhanVatXuLy.setDangMacCaiTrang(true);
                    NhanVatXuLy.setDangMacAvatar(false);
                } else {
                    NhanVatXuLy.setDangMacAvatar(true);
                    NhanVatXuLy.setDangMacCaiTrang(false);
                }
                if (!veHUD.dangHopThe && !veHUD.dangBienKhi) {
                    NhanVatCauHinh c2 = laCaiTrang ? veHUD.Doicaitrang(item.getId()) : veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), item.getId(), veHUD.aodangmac, veHUD.quandangmac);
                    nhanVat.fixCaiTrang(c2);
                    veHUD.texAvt = new Texture(nhanVat.doiavatar());
                }
                tangchiso(item.getChiso());
            }
        } else if (item.getLoai() == LoaiItem.AO) {
            if (veHUD.ao == null){
                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,0);
                veHUD.ao = item.getLinkTexture();
                veHUD.aodangmac = item.getId();
                veHUD.skha = item.getSetkichhoat();
                if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe && !veHUD.dangBienKhi) {
                    NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, item.getId(), veHUD.quandangmac);
                    nhanVat.fixCaiTrang(c2);
                }
                tangchiso(item.getChiso());
                duLieuNguoiChoi.dangMacAo(true);
                kichHoatSetHienTai();
            }
        } else if (item.getLoai() == LoaiItem.QUAN) {
            if (veHUD.quan == null){
                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,1);
                veHUD.quan = item.getLinkTexture();
                veHUD.quandangmac = item.getId();
                veHUD.skhq = item.getSetkichhoat();
                if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe && !veHUD.dangBienKhi) {
                    NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, veHUD.aodangmac, item.getId());
                    nhanVat.fixCaiTrang(c2);
                }
                tangchiso(item.getChiso());
                duLieuNguoiChoi.dangMacQuan(true);
                kichHoatSetHienTai();
            }
        } else if (item.getLoai() == LoaiItem.GANG) {
            if (veHUD.gang == null){
                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,2);
                veHUD.gang = item.getLinkTexture();
                veHUD.skhg = item.getSetkichhoat();
                tangchiso(item.getChiso());
                duLieuNguoiChoi.dangMacGang(true);
                kichHoatSetHienTai();
            }
        } else if (item.getLoai() == LoaiItem.GIAY) {
            if (veHUD.giay == null) {
                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item, 3);
                veHUD.giay = item.getLinkTexture();
                veHUD.skhj = item.getSetkichhoat();
                tangchiso(item.getChiso());
                duLieuNguoiChoi.dangMacGiay(true);
                kichHoatSetHienTai();
            }
        } else if (item.getLoai() == LoaiItem.RADA) {
            if (veHUD.rada == null){
                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,4);
                veHUD.rada = item.getLinkTexture();
                veHUD.skhrada = item.getSetkichhoat();
                tangchiso(item.getChiso());
                duLieuNguoiChoi.dangMacRada(true);
                kichHoatSetHienTai();
            }
        } else if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
            if (veHUD.giaplt == null){
                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,6);
                veHUD.giaplt = item.getLinkTexture();
                tangchiso(item.getChiso());
                duLieuNguoiChoi.dangMacGlt(true);
                veHUD.dangMacGiapLuyenTap = true;
            }
        } else if (item.getLoai() == LoaiItem.VANBAY) {
            if (veHUD.vanbay == null){
                duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,7);
                veHUD.vanbay = item.getLinkTexture();
                nhanVat.dangMangVanBay = true;
                nhanVat.doiVanBay(item.getId());
            }
        }
    }

    public void macDoVuaLoginDeTu(Item item) {
        if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {

            boolean loaiCaiTrangDangMac = DeTuXuLy.getDangMacCaiTrang(); // cái đang mặc
            boolean laCaiTrang = item.getLoai() == LoaiItem.CAITRANG;       // cái sắp mặc
            if (veHUD.iconctDeTu == null) {
                // Gán cải trang mới, không có cái cũ
                duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,5);
                veHUD.iconctDeTu = item.getLinkTexture();
                if (laCaiTrang) {
                    duLieuNguoiChoi.deTu.setAvtDangMac(duLieuNguoiChoi.deTu.getHanhtinh() + "_base");
                } else {
                    duLieuNguoiChoi.deTu.setAvtDangMac(item.getId());
                }
                if (!duLieuNguoiChoi.deTu.dangBienKhi) {
                    DeTuCauHinh c2 = laCaiTrang ? veHUD.DoicaitrangDeTu(item.getId()) : veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), item.getId(), veHUD.aodetudangmac, veHUD.quandetudangmac);
                    duLieuNguoiChoi.deTu.fixCaiTrang(c2);
                }
                tangchisoDeTu(item.getChiso());
            }
        } else if (item.getLoai() == LoaiItem.AO) {
            if (veHUD.aoDeTu == null){
                duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,0);
                veHUD.aoDeTu = item.getLinkTexture();
                veHUD.aodetudangmac = item.getId();
                veHUD.skha_detu = item.getSetkichhoat();
                if (!duLieuNguoiChoi.deTu.dangBienKhi) {
                    if (!DeTuXuLy.getDangMacCaiTrang()) {
                        DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), item.getId(), veHUD.quandetudangmac);
                        duLieuNguoiChoi.deTu.fixCaiTrang(c2);
                    }
                }
                tangchisoDeTu(item.getChiso());
                duLieuNguoiChoi.deTu.dangMacAo(true);
                kichHoatSetHienTaiDeTu();
            }
        } else if (item.getLoai() == LoaiItem.QUAN) {
            if (veHUD.quanDeTu == null){
                duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,1);
                veHUD.quanDeTu = item.getLinkTexture();
                veHUD.quandetudangmac = item.getId();
                veHUD.skhq_detu = item.getSetkichhoat();
                if (!duLieuNguoiChoi.deTu.dangBienKhi) {
                    if (!DeTuXuLy.getDangMacCaiTrang()) {
                        DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), veHUD.aodetudangmac, item.getId());
                        duLieuNguoiChoi.deTu.fixCaiTrang(c2);
                    }
                }
                tangchisoDeTu(item.getChiso());
                duLieuNguoiChoi.deTu.dangMacQuan(true);
                kichHoatSetHienTaiDeTu();
            }
        } else if (item.getLoai() == LoaiItem.GANG) {
            if (veHUD.gangDeTu == null){
                duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,2);
                veHUD.gangDeTu = item.getLinkTexture();
                veHUD.skhg_detu = item.getSetkichhoat();
                tangchisoDeTu(item.getChiso());
                duLieuNguoiChoi.deTu.dangMacGang(true);
                kichHoatSetHienTaiDeTu();
            }
        } else if (item.getLoai() == LoaiItem.GIAY) {
            if (veHUD.giayDeTu == null){
                duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,3);
                veHUD.giayDeTu = item.getLinkTexture();
                veHUD.skhj_detu = item.getSetkichhoat();
                tangchisoDeTu(item.getChiso());
                duLieuNguoiChoi.deTu.dangMacGiay(true);
                kichHoatSetHienTaiDeTu();
            }
        } else if (item.getLoai() == LoaiItem.RADA) {
            if (veHUD.radaDeTu == null){
                duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,4);
                veHUD.radaDeTu = item.getLinkTexture();
                veHUD.skhrada_detu = item.getSetkichhoat();
                tangchisoDeTu(item.getChiso());
                duLieuNguoiChoi.deTu.dangMacRada(true);
                kichHoatSetHienTaiDeTu();
            }
        }
    }

    public void macDoChoDe(int index) {
        int indexx = veHUD.hangTrangDangChon - 8;
        ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();

        if (indexx < danhSach.size()) {
            Item item = danhSach.get(indexx);
            veHUD.itemm = item;
            if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {

                boolean loaiCaiTrangDangMac = DeTuXuLy.getDangMacCaiTrang(); // cái đang mặc
                boolean laCaiTrang = item.getLoai() == LoaiItem.CAITRANG;       // cái sắp mặc
                if (veHUD.iconctDeTu == null) {
                    // Gán cải trang mới, không có cái cũ
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,5);
                    veHUD.iconctDeTu = item.getLinkTexture();
                    if (laCaiTrang) {
                        duLieuNguoiChoi.deTu.setAvtDangMac(duLieuNguoiChoi.deTu.getHanhtinh() + "_base");
                    } else {
                        duLieuNguoiChoi.deTu.setAvtDangMac(item.getId());
                    }
                    if (!duLieuNguoiChoi.deTu.dangBienKhi) {
                        DeTuCauHinh c2 = laCaiTrang ? veHUD.DoicaitrangDeTu(item.getId()) : veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), item.getId(), veHUD.aodetudangmac, veHUD.quandetudangmac);
                        duLieuNguoiChoi.deTu.fixCaiTrang(c2);
                    }
                    tangchisoDeTu(item.getChiso());
                    danhSach.remove(indexx);
                } else {
                    macCaiTrangMoiDeTu(item, indexx, danhSach, loaiCaiTrangDangMac, laCaiTrang);
                }
            } else if (item.getLoai() == LoaiItem.AO) {
                if (veHUD.aoDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,0);
                    veHUD.aoDeTu = item.getLinkTexture();
                    veHUD.aodetudangmac = item.getId();
                    veHUD.skha_detu = item.getSetkichhoat();
                    if (!duLieuNguoiChoi.deTu.dangBienKhi) {
                        if (!DeTuXuLy.getDangMacCaiTrang()) {
                            DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), item.getId(), veHUD.quandetudangmac);
                            duLieuNguoiChoi.deTu.fixCaiTrang(c2);
                        }
                    }
                    tangchisoDeTu(item.getChiso());
                    duLieuNguoiChoi.deTu.dangMacAo(true);
                    kichHoatSetHienTaiDeTu();
                    danhSach.remove(indexx);
                } else {
                    macAoMoiDeTu(item, indexx, danhSach);
                }
            } else if (item.getLoai() == LoaiItem.QUAN) {
                if (veHUD.quanDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,1);
                    veHUD.quanDeTu = item.getLinkTexture();
                    veHUD.quandetudangmac = item.getId();
                    veHUD.skhq_detu = item.getSetkichhoat();
                    if (!duLieuNguoiChoi.deTu.dangBienKhi) {
                        if (!DeTuXuLy.getDangMacCaiTrang()) {
                            DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), veHUD.aodetudangmac, item.getId());
                            duLieuNguoiChoi.deTu.fixCaiTrang(c2);
                        }
                    }
                    tangchisoDeTu(item.getChiso());
                    duLieuNguoiChoi.deTu.dangMacQuan(true);
                    kichHoatSetHienTaiDeTu();
                    danhSach.remove(indexx);
                } else {
                    macQuanMoiDeTu(item, indexx, danhSach);
                }
            } else if (item.getLoai() == LoaiItem.GANG) {
                if (veHUD.gangDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,2);
                    veHUD.gangDeTu = item.getLinkTexture();
                    veHUD.skhg_detu = item.getSetkichhoat();
                    tangchisoDeTu(item.getChiso());
                    duLieuNguoiChoi.deTu.dangMacGang(true);
                    kichHoatSetHienTaiDeTu();
                    danhSach.remove(indexx);
                } else {
                    macGangMoiDeTu(item, indexx, danhSach);
                }
            } else if (item.getLoai() == LoaiItem.GIAY) {
                if (veHUD.giayDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,3);
                    veHUD.giayDeTu = item.getLinkTexture();
                    veHUD.skhj_detu = item.getSetkichhoat();
                    tangchisoDeTu(item.getChiso());
                    duLieuNguoiChoi.deTu.dangMacGiay(true);
                    kichHoatSetHienTaiDeTu();
                    danhSach.remove(indexx);
                } else {
                    macGiayMoiDeTu(item, indexx, danhSach);
                }
            } else if (item.getLoai() == LoaiItem.RADA) {
                if (veHUD.radaDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,4);
                    veHUD.radaDeTu = item.getLinkTexture();
                    veHUD.skhrada_detu = item.getSetkichhoat();
                    tangchisoDeTu(item.getChiso());
                    duLieuNguoiChoi.deTu.dangMacRada(true);
                    kichHoatSetHienTaiDeTu();
                    danhSach.remove(indexx);
                } else {
                    macRadaMoiDeTu(item, indexx, danhSach);
                }
            }
        }
    }

    public void goDoChoDe(int index) {
        if (index <= 5 && index >= 0){
            if (index == 5) {
                goCaiTrangDeTu(DeTuXuLy.getDangMacCaiTrang());
            } else if (index == 0) {
                goAoDeTu();
            } else if (index == 1) {
                goQuanDeTu();
            } else if (index == 2) {
                goGangDeTu();
            } else if (index == 3) {
                goGiayDeTu();
            } else if (index == 4) {
                goRadaDeTu();
            }
        }
    }

    // ==================== AO ====================

    private void macAoMoi(Item item, int indexx, ArrayList<Item> danhSach) {
        Item aoCu = duLieuNguoiChoi.getHanhTrangDangMac().get(0);

        huyHieuUngSet(veHUD.skha);
        duLieuNguoiChoi.dangMacAo(false);
        giamchiso(aoCu != null ? aoCu.getChiso() : null);

        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item, 0);
        veHUD.ao        = item.getLinkTexture();
        veHUD.skha      = item.getSetkichhoat();
        veHUD.aodangmac = item.getId();

        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe && !veHUD.dangBienKhi) {
            nhanVat.fixCaiTrang(veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, item.getId(), veHUD.quandangmac));
        }

        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacAo(true);
        kichHoatSetHienTai();
        danhSach.set(indexx, aoCu);
    }

    public void goAo(boolean vut) {
        if (veHUD.ao == null) return;
        Item aoCu = duLieuNguoiChoi.getHanhTrangDangMac().get(0);
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null, 0);

        huyHieuUngSet(veHUD.skha);
        duLieuNguoiChoi.dangMacAo(false);
        giamchiso(aoCu != null ? aoCu.getChiso() : null);

        veHUD.ao        = null;
        veHUD.skha      = "mac_dinh";
        veHUD.aodangmac = "set_base";

        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe && !veHUD.dangBienKhi) {
            nhanVat.fixCaiTrang(veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, "set_base", veHUD.quandangmac));
        }

        kichHoatSetHienTai();
        if (!vut) duLieuNguoiChoi.themItemVaoHanhTrangNoSave(aoCu);
    }

// ==================== AO DE TU ====================

    private void macAoMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach) {
        Item aoCu = duLieuNguoiChoi.getHanhTrangDangMac().get(0);

        huyHieuUngSetDeTu(veHUD.skha_detu);
        duLieuNguoiChoi.deTu.dangMacAo(false);
        giamchisoDeTu(aoCu != null ? aoCu.getChiso() : null);

        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item, 0);
        veHUD.aoDeTu        = item.getLinkTexture();
        veHUD.skha_detu     = item.getSetkichhoat();
        veHUD.aodetudangmac = item.getId();

        if (!duLieuNguoiChoi.deTu.dangBienKhi && !DeTuXuLy.getDangMacCaiTrang()) {
            duLieuNguoiChoi.deTu.fixCaiTrang(veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), item.getId(), veHUD.quandetudangmac));
        }

        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacAo(true);
        kichHoatSetHienTaiDeTu();
        danhSach.set(indexx, aoCu);
    }

    public void goAoDeTu() {
        if (veHUD.aoDeTu == null) return;
        Item aoCu = duLieuNguoiChoi.getHanhTrangDangMac().get(0);
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null, 0);

        huyHieuUngSetDeTu(veHUD.skha_detu);
        duLieuNguoiChoi.deTu.dangMacAo(false);
        giamchisoDeTu(aoCu != null ? aoCu.getChiso() : null);

        veHUD.aoDeTu        = null;
        veHUD.skha_detu     = "mac_dinh";
        veHUD.aodetudangmac = "set_base";

        if (!duLieuNguoiChoi.deTu.dangBienKhi && !DeTuXuLy.getDangMacCaiTrang()) {
            duLieuNguoiChoi.deTu.fixCaiTrang(veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), "set_base", veHUD.quandetudangmac));
        }

        kichHoatSetHienTaiDeTu();
        duLieuNguoiChoi.themItemVaoHanhTrangNoSave(aoCu);
    }

// ==================== QUAN ====================

    private void macQuanMoi(Item item, int indexx, ArrayList<Item> danhSach) {
        Item quanCu = duLieuNguoiChoi.getHanhTrangDangMac().get(1);

        huyHieuUngSet(veHUD.skhq);
        duLieuNguoiChoi.dangMacQuan(false);
        giamchiso(quanCu != null ? quanCu.getChiso() : null);

        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item, 1);
        veHUD.quan        = item.getLinkTexture();
        veHUD.skhq        = item.getSetkichhoat();
        veHUD.quandangmac = item.getId();

        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe && !veHUD.dangBienKhi) {
            nhanVat.fixCaiTrang(veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, veHUD.aodangmac, item.getId()));
        }

        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacQuan(true);
        kichHoatSetHienTai();
        danhSach.set(indexx, quanCu);
    }

    public void goQuan(boolean vut) {
        if (veHUD.quan == null) return;
        Item quanCu = duLieuNguoiChoi.getHanhTrangDangMac().get(1);
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null, 1);

        huyHieuUngSet(veHUD.skhq);
        duLieuNguoiChoi.dangMacQuan(false);
        giamchiso(quanCu != null ? quanCu.getChiso() : null);

        veHUD.quan        = null;
        veHUD.skhq        = "mac_dinh";
        veHUD.quandangmac = "set_base";

        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe && !veHUD.dangBienKhi) {
            nhanVat.fixCaiTrang(veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, veHUD.aodangmac, "set_base"));
        }

        kichHoatSetHienTai();
        if (!vut) duLieuNguoiChoi.themItemVaoHanhTrangNoSave(quanCu);
    }

// ==================== QUAN DE TU ====================

    private void macQuanMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach) {
        Item quanCu = duLieuNguoiChoi.getHanhTrangDangMac().get(1);

        huyHieuUngSetDeTu(veHUD.skhq_detu);
        duLieuNguoiChoi.deTu.dangMacQuan(false);
        giamchisoDeTu(quanCu != null ? quanCu.getChiso() : null);

        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item, 1);
        veHUD.quanDeTu        = item.getLinkTexture();
        veHUD.skhq_detu       = item.getSetkichhoat();
        veHUD.quandetudangmac = item.getId();

        if (!duLieuNguoiChoi.deTu.dangBienKhi && !DeTuXuLy.getDangMacCaiTrang()) {
            duLieuNguoiChoi.deTu.fixCaiTrang(veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), veHUD.aodetudangmac, item.getId()));
        }

        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacQuan(true);
        kichHoatSetHienTaiDeTu();
        danhSach.set(indexx, quanCu);
    }

    public void goQuanDeTu() {
        if (veHUD.quanDeTu == null) return;
        Item quanCu = duLieuNguoiChoi.getHanhTrangDangMac().get(1);
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null, 1);

        huyHieuUngSetDeTu(veHUD.skhq_detu);
        duLieuNguoiChoi.deTu.dangMacQuan(false);
        giamchisoDeTu(quanCu != null ? quanCu.getChiso() : null);

        veHUD.quanDeTu        = null;
        veHUD.skhq_detu       = "mac_dinh";
        veHUD.quandetudangmac = "set_base";

        if (!duLieuNguoiChoi.deTu.dangBienKhi && !DeTuXuLy.getDangMacCaiTrang()) {
            duLieuNguoiChoi.deTu.fixCaiTrang(veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), "set_base", veHUD.quandetudangmac));
        }

        kichHoatSetHienTaiDeTu();
        duLieuNguoiChoi.themItemVaoHanhTrangNoSave(quanCu);
    }

// ==================== GANG ====================

    private void macGangMoi(Item item, int indexx, ArrayList<Item> danhSach) {
        Item gangCu = duLieuNguoiChoi.getHanhTrangDangMac().get(2);

        huyHieuUngSet(veHUD.skhg);
        duLieuNguoiChoi.dangMacGang(false);
        giamchiso(gangCu != null ? gangCu.getChiso() : null);

        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item, 2);
        veHUD.gang = item.getLinkTexture();
        veHUD.skhg = item.getSetkichhoat();

        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacGang(true);
        kichHoatSetHienTai();
        danhSach.set(indexx, gangCu);
    }

    public void goGang(boolean vut) {
        if (veHUD.gang == null) return;
        Item gangCu = duLieuNguoiChoi.getHanhTrangDangMac().get(2);
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null, 2);

        huyHieuUngSet(veHUD.skhg);
        duLieuNguoiChoi.dangMacGang(false);
        giamchiso(gangCu != null ? gangCu.getChiso() : null);

        veHUD.gang = null;
        veHUD.skhg = "mac_dinh";

        kichHoatSetHienTai();
        if (!vut) duLieuNguoiChoi.themItemVaoHanhTrangNoSave(gangCu);
    }

// ==================== GANG DE TU ====================

    private void macGangMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach) {
        Item gangCu = duLieuNguoiChoi.getHanhTrangDangMac().get(2);

        huyHieuUngSetDeTu(veHUD.skhg_detu);
        duLieuNguoiChoi.deTu.dangMacGang(false);
        giamchisoDeTu(gangCu != null ? gangCu.getChiso() : null);

        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item, 2);
        veHUD.gangDeTu  = item.getLinkTexture();
        veHUD.skhg_detu = item.getSetkichhoat();

        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacGang(true);
        kichHoatSetHienTaiDeTu();
        danhSach.set(indexx, gangCu);
    }

    public void goGangDeTu() {
        if (veHUD.gangDeTu == null) return;
        Item gangCu = duLieuNguoiChoi.getHanhTrangDangMac().get(2);
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null, 2);

        huyHieuUngSetDeTu(veHUD.skhg_detu);
        duLieuNguoiChoi.deTu.dangMacGang(false);
        giamchisoDeTu(gangCu != null ? gangCu.getChiso() : null);

        veHUD.gangDeTu  = null;
        veHUD.skhg_detu = "mac_dinh";

        kichHoatSetHienTaiDeTu();
        duLieuNguoiChoi.themItemVaoHanhTrangNoSave(gangCu);
    }

// ==================== GIAY ====================

    private void macGiayMoi(Item item, int indexx, ArrayList<Item> danhSach) {
        Item giayCu = duLieuNguoiChoi.getHanhTrangDangMac().get(3);

        huyHieuUngSet(veHUD.skhj);
        duLieuNguoiChoi.dangMacGiay(false);
        giamchiso(giayCu != null ? giayCu.getChiso() : null);

        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item, 3);
        veHUD.giay = item.getLinkTexture();
        veHUD.skhj = item.getSetkichhoat();

        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacGiay(true);
        kichHoatSetHienTai();
        danhSach.set(indexx, giayCu);
    }

    public void goGiay(boolean vut) {
        if (veHUD.giay == null) return;
        Item giayCu = duLieuNguoiChoi.getHanhTrangDangMac().get(3);
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null, 3);

        huyHieuUngSet(veHUD.skhj);
        duLieuNguoiChoi.dangMacGiay(false);
        giamchiso(giayCu != null ? giayCu.getChiso() : null);

        veHUD.giay = null;
        veHUD.skhj = "mac_dinh";

        kichHoatSetHienTai();
        if (!vut) duLieuNguoiChoi.themItemVaoHanhTrangNoSave(giayCu);
    }

// ==================== GIAY DE TU ====================

    private void macGiayMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach) {
        Item giayCu = duLieuNguoiChoi.getHanhTrangDangMac().get(3);

        huyHieuUngSetDeTu(veHUD.skhj_detu);
        duLieuNguoiChoi.deTu.dangMacGiay(false);
        giamchisoDeTu(giayCu != null ? giayCu.getChiso() : null);

        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item, 3);
        veHUD.giayDeTu  = item.getLinkTexture();
        veHUD.skhj_detu = item.getSetkichhoat();

        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacGiay(true);
        kichHoatSetHienTaiDeTu();
        danhSach.set(indexx, giayCu);
    }

    public void goGiayDeTu() {
        if (veHUD.giayDeTu == null) return;
        Item giayCu = duLieuNguoiChoi.getHanhTrangDangMac().get(3);
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null, 3);

        huyHieuUngSetDeTu(veHUD.skhj_detu);
        duLieuNguoiChoi.deTu.dangMacGiay(false);
        giamchisoDeTu(giayCu != null ? giayCu.getChiso() : null);

        veHUD.giayDeTu  = null;
        veHUD.skhj_detu = "mac_dinh";

        kichHoatSetHienTaiDeTu();
        duLieuNguoiChoi.themItemVaoHanhTrangNoSave(giayCu);
    }

// ==================== RADA ====================

    private void macRadaMoi(Item item, int indexx, ArrayList<Item> danhSach) {
        Item radaCu = duLieuNguoiChoi.getHanhTrangDangMac().get(4);

        huyHieuUngSet(veHUD.skhrada);
        duLieuNguoiChoi.dangMacRada(false);
        giamchiso(radaCu != null ? radaCu.getChiso() : null);

        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item, 4);
        veHUD.rada    = item.getLinkTexture();
        veHUD.skhrada = item.getSetkichhoat();

        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacRada(true);
        kichHoatSetHienTai();
        danhSach.set(indexx, radaCu);
    }

    public void goRada(boolean vut) {
        if (veHUD.rada == null) return;
        Item radaCu = duLieuNguoiChoi.getHanhTrangDangMac().get(4);
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null, 4);

        huyHieuUngSet(veHUD.skhrada);
        duLieuNguoiChoi.dangMacRada(false);
        giamchiso(radaCu != null ? radaCu.getChiso() : null);

        veHUD.rada    = null;
        veHUD.skhrada = "mac_dinh";

        kichHoatSetHienTai();
        if (!vut) duLieuNguoiChoi.themItemVaoHanhTrangNoSave(radaCu);
    }

// ==================== RADA DE TU ====================

    private void macRadaMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach) {
        Item radaCu = duLieuNguoiChoi.getHanhTrangDangMac().get(4);

        huyHieuUngSetDeTu(veHUD.skhrada_detu);
        duLieuNguoiChoi.deTu.dangMacRada(false);
        giamchisoDeTu(radaCu != null ? radaCu.getChiso() : null);

        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item, 4);
        veHUD.radaDeTu      = item.getLinkTexture();
        veHUD.skhrada_detu  = item.getSetkichhoat();

        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacRada(true);
        kichHoatSetHienTaiDeTu();
        danhSach.set(indexx, radaCu);
    }

    public void goRadaDeTu() {
        if (veHUD.radaDeTu == null) return;
        Item radaCu = duLieuNguoiChoi.getHanhTrangDangMac().get(4);
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null, 4);

        huyHieuUngSetDeTu(veHUD.skhrada_detu);
        duLieuNguoiChoi.deTu.dangMacRada(false);
        giamchisoDeTu(radaCu != null ? radaCu.getChiso() : null);

        veHUD.radaDeTu     = null;
        veHUD.skhrada_detu = "mac_dinh";

        kichHoatSetHienTaiDeTu();
        duLieuNguoiChoi.themItemVaoHanhTrangNoSave(radaCu);
    }

// ==================== CAI TRANG ====================

    private void macCaiTrangMoi(Item item, int indexx, ArrayList<Item> danhSach, boolean caiTrangDangMac, boolean laCaiTrangMoi) {
        Item caiTrangCu = duLieuNguoiChoi.getHanhTrangDangMac().get(5);
        giamchiso(caiTrangCu != null ? caiTrangCu.getChiso() : null);

        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item, 5);
        veHUD.iconct = item.getLinkTexture();

        if (laCaiTrangMoi) {
            veHUD.avatardangmac = nhanVat.getNhanvat() + "_base";
            NhanVatXuLy.setDangMacCaiTrang(true);
            NhanVatXuLy.setDangMacAvatar(false);
        } else {
            veHUD.avatardangmac = item.getId();
            NhanVatXuLy.setDangMacAvatar(true);
            NhanVatXuLy.setDangMacCaiTrang(false);
        }

        if (!veHUD.dangHopThe && !veHUD.dangBienKhi) {
            NhanVatCauHinh c2 = laCaiTrangMoi
                ? veHUD.Doicaitrang(item.getId())
                : veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), item.getId(), veHUD.aodangmac, veHUD.quandangmac);
            nhanVat.fixCaiTrang(c2);
            veHUD.texAvt = new Texture(nhanVat.doiavatar());
        }

        tangchiso(item.getChiso());
        danhSach.set(indexx, caiTrangCu);
    }

    private void macCaiTrangMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach, boolean caiTrangDangMac, boolean laCaiTrangMoi) {
        Item caiTrangCu = duLieuNguoiChoi.getHanhTrangDangMac().get(5);
        giamchisoDeTu(caiTrangCu != null ? caiTrangCu.getChiso() : null);

        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item, 5);
        veHUD.iconctDeTu = item.getLinkTexture();
        duLieuNguoiChoi.deTu.setAvtDangMac(laCaiTrangMoi
            ? duLieuNguoiChoi.deTu.getHanhtinh() + "_base"
            : item.getId());

        if (!duLieuNguoiChoi.deTu.dangBienKhi) {
            DeTuCauHinh c2 = laCaiTrangMoi
                ? veHUD.DoicaitrangDeTu(item.getId())
                : veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), item.getId(), veHUD.aodetudangmac, veHUD.quandetudangmac);
            duLieuNguoiChoi.deTu.fixCaiTrang(c2);
        }

        tangchisoDeTu(item.getChiso());
        danhSach.set(indexx, caiTrangCu);
    }

    public void goCaiTrang(boolean laCaiTrang, boolean vut) {
        if (veHUD.iconct == null) return;
        Item caiTrangCu = duLieuNguoiChoi.getHanhTrangDangMac().get(5);
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null, 5);

        giamchiso(caiTrangCu != null ? caiTrangCu.getChiso() : null);
        if (!vut) duLieuNguoiChoi.themItemVaoHanhTrangNoSave(caiTrangCu);

        veHUD.avatardangmac = nhanVat.getNhanvat() + "_base";
        NhanVatXuLy.setDangMacAvatar(false);
        NhanVatXuLy.setDangMacCaiTrang(false);

        if (!veHUD.dangHopThe && !veHUD.dangBienKhi) {
            nhanVat.fixCaiTrang(veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), nhanVat.getNhanvat() + "_base", veHUD.aodangmac, veHUD.quandangmac));
            veHUD.texAvt = new Texture(nhanVat.doiavatar());
        }

        veHUD.iconct = null;
    }

    public void goCaiTrangDeTu(boolean laCaiTrang) {
        if (veHUD.iconctDeTu == null) return;
        Item caiTrangCu = duLieuNguoiChoi.getHanhTrangDangMac().get(5);
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null, 5);

        giamchisoDeTu(caiTrangCu != null ? caiTrangCu.getChiso() : null);
        duLieuNguoiChoi.themItemVaoHanhTrangNoSave(caiTrangCu);
        duLieuNguoiChoi.deTu.setAvtDangMac(duLieuNguoiChoi.deTu.getHanhtinh() + "_base");

        if (!duLieuNguoiChoi.deTu.dangBienKhi) {
            duLieuNguoiChoi.deTu.fixCaiTrang(veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getHanhtinh() + "_base", veHUD.aodetudangmac, veHUD.quandetudangmac));
        }

        veHUD.iconctDeTu = null;
    }

// ==================== GIAP LUYEN TAP ====================

    private void macGiapLuyenTapMoi(Item item, int indexx, ArrayList<Item> danhSach) {
        Item giapCu = duLieuNguoiChoi.getHanhTrangDangMac().get(6);
        giamchiso(giapCu != null ? giapCu.getChiso() : null);

        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item, 6);
        veHUD.giaplt = item.getLinkTexture();

        tangchiso(item.getChiso());
        veHUD.dangMacGiapLuyenTap = true;
        duLieuNguoiChoi.dangMacGlt(true);
        danhSach.set(indexx, giapCu);
    }

    public void goGiapLuyenTap(boolean vut) {
        if (veHUD.giaplt == null) return;
        Item giapCu = duLieuNguoiChoi.getHanhTrangDangMac().get(6);
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null, 6);

        giamchiso(giapCu != null ? giapCu.getChiso() : null);
        if (!vut) duLieuNguoiChoi.themItemVaoHanhTrangNoSave(giapCu);

        veHUD.giaplt              = null;
        veHUD.dangMacGiapLuyenTap = false;
        duLieuNguoiChoi.dangMacGlt(false);
        veHUD.itemGiapLuyenTapVuaCoi = giapCu;
    }

// ==================== VAN BAY ====================

    private void macVanBayMoi(Item item, int indexx, ArrayList<Item> danhSach) {
        Item vanBayCu = duLieuNguoiChoi.getHanhTrangDangMac().get(7);
        danhSach.set(indexx, vanBayCu);

        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item, 7);
        veHUD.vanbay = item.getLinkTexture();
        nhanVat.doiVanBay(item.getId());
    }

    public void goVanBay(boolean vut) {
        if (veHUD.vanbay == null) return;
        Item vanBayCu = duLieuNguoiChoi.getHanhTrangDangMac().get(7);
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null, 7);

        giamchiso(vanBayCu != null ? vanBayCu.getChiso() : null);
        if (!vut) duLieuNguoiChoi.themItemVaoHanhTrangNoSave(vanBayCu);

        veHUD.vanbay             = null;
        nhanVat.dangMangVanBay   = false;
        nhanVat.doiVanBay("base");
    }

    private void tangchiso(int[] chiso){
        duLieuNguoiChoi.tangHp(chiso[0]); // hp thường
        duLieuNguoiChoi.tangKi(chiso[1]); // ki thường
        duLieuNguoiChoi.tangSucDanh(chiso[2]); // sức đánh thường
        duLieuNguoiChoi.tangChiMang(chiso[3]); // crit
        duLieuNguoiChoi.tangGiap(chiso[4]); // giáp
        duLieuNguoiChoi.tangSatThuongChiMang(chiso[5]); // st crit
        duLieuNguoiChoi.tangHpGoc(chiso[9],false);
        duLieuNguoiChoi.tangKiGoc(chiso[10],false);
        duLieuNguoiChoi.tangSucDanhGoc(chiso[11],false);
        duLieuNguoiChoi.tangHpPt(chiso[6]); // HP %
        duLieuNguoiChoi.tangKiPt(chiso[7]); // KI %
        duLieuNguoiChoi.tangSucDanhPt(chiso[8]); // Sức đánh %
        duLieuNguoiChoi.tangGiamSatThuongNhanVat(chiso[12]);
    }

    private void giamchiso(int[] chiso){
        duLieuNguoiChoi.giamHp(chiso[0]);
        duLieuNguoiChoi.giamKi(chiso[1]);
        duLieuNguoiChoi.giamSucDanh(chiso[2]);
        duLieuNguoiChoi.giamChiMang(chiso[3]);
        duLieuNguoiChoi.giamGiap(chiso[4]);
        duLieuNguoiChoi.giamSatThuongChiMang(chiso[5]);
        duLieuNguoiChoi.giamHpPt(chiso[6]);
        duLieuNguoiChoi.giamKiPt(chiso[7]);
        duLieuNguoiChoi.giamSucDanhPt(chiso[8]);
        duLieuNguoiChoi.giamHpGoc(chiso[9]);
        duLieuNguoiChoi.giamKiGoc(chiso[10]);
        duLieuNguoiChoi.giamSucDanhGoc(chiso[11]);
        duLieuNguoiChoi.giamGiamSatThuongNhanVat(chiso[12]);
    }

    private void tangchisoDeTu(int[] chiso){
        duLieuNguoiChoi.deTu.tangHp(chiso[0]); // hp thường
        duLieuNguoiChoi.deTu.tangKi(chiso[1]); // ki thường
        duLieuNguoiChoi.deTu.tangSucDanh(chiso[2]); // sức đánh thường
        duLieuNguoiChoi.deTu.tangChiMang(chiso[3]); // crit
        duLieuNguoiChoi.deTu.tangGiap(chiso[4]); // giáp
        duLieuNguoiChoi.deTu.tangSatThuongChiMang(chiso[5]); // st crit
        duLieuNguoiChoi.deTu.tangHpGoc(chiso[9],false);
        duLieuNguoiChoi.deTu.tangKiGoc(chiso[10],false);
        duLieuNguoiChoi.deTu.tangSucDanhGoc(chiso[11],false);
        duLieuNguoiChoi.deTu.tangHpPt(chiso[6]); // HP %
        duLieuNguoiChoi.deTu.tangKiPt(chiso[7]); // KI %
        duLieuNguoiChoi.deTu.tangSucDanhPt(chiso[8]); // Sức đánh %
        duLieuNguoiChoi.deTu.tangGiamSatThuongDeTu(chiso[12]);
    }

    private void giamchisoDeTu(int[] chiso){
        duLieuNguoiChoi.deTu.giamHp(chiso[0]);
        duLieuNguoiChoi.deTu.giamKi(chiso[1]);
        duLieuNguoiChoi.deTu.giamSucDanh(chiso[2]);
        duLieuNguoiChoi.deTu.giamChiMang(chiso[3]);
        duLieuNguoiChoi.deTu.giamGiap(chiso[4]);
        duLieuNguoiChoi.deTu.giamSatThuongChiMang(chiso[5]);
        duLieuNguoiChoi.deTu.giamHpPt(chiso[6]);
        duLieuNguoiChoi.deTu.giamKiPt(chiso[7]);
        duLieuNguoiChoi.deTu.giamSucDanhPt(chiso[8]);
        duLieuNguoiChoi.deTu.giamHpGoc(chiso[9]);
        duLieuNguoiChoi.deTu.giamKiGoc(chiso[10]);
        duLieuNguoiChoi.deTu.giamSucDanhGoc(chiso[11]);
        duLieuNguoiChoi.deTu.giamGiamSatThuongDeTu(chiso[12]);
    }

    private void huyHieuUngSet(String setDangMac) {
        if ("Nappa".equals(setDangMac) && veHUD.vuaMoNappa) {
            duLieuNguoiChoi.setNappa(false);
            duLieuNguoiChoi.giamHpPt(80); // giảm đúng 80%
            veHUD.vuaMoNappa = false;
        }
        if ("Dũng Sĩ Trong Băng Giá".equals(setDangMac) && veHUD.vuaMoNappa) {
            duLieuNguoiChoi.giamChiMang(40);
            veHUD.vuaMoNappa = false;
        }
    }
    private void huyHieuUngSetDeTu(String setDangMac) {
        if ("Nappa".equals(setDangMac) && veHUD.vuaMoDeTuNappa) {
            duLieuNguoiChoi.deTu.setNappa(false);
            duLieuNguoiChoi.deTu.giamHpPt(80); // giảm đúng 80%
            veHUD.vuaMoDeTuNappa = false;
        }
        if ("Dũng Sĩ Trong Băng Giá".equals(setDangMac) && veHUD.vuaMoDeTuNappa) {
            duLieuNguoiChoi.deTu.giamChiMang(40);
            veHUD.vuaMoDeTuNappa = false;
        }
    }
    private void kichHoatSetHienTai() {
        boolean fullSetNappa = "Nappa".equals(veHUD.skha)
            && "Nappa".equals(veHUD.skhq)
            && "Nappa".equals(veHUD.skhg)
            && "Nappa".equals(veHUD.skhj)
            && "Nappa".equals(veHUD.skhrada);

        boolean fullSetAyaka = "Dũng Sĩ Trong Băng Giá".equals(veHUD.skha)
            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhq)
            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhg)
            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhj)
            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhrada);


        if (fullSetNappa && !veHUD.vuaMoNappa) {
            duLieuNguoiChoi.setNappa(true);
            duLieuNguoiChoi.tangHpPt(80); // tăng đúng 80%
            veHUD.vuaMoNappa = true;
        }
        if (fullSetAyaka && !veHUD.vuaMoNappa) {
            duLieuNguoiChoi.tangChiMang(40);
            veHUD.vuaMoNappa = true;
        }
    }
    private void kichHoatSetHienTaiDeTu() {
        boolean fullSetNappa = "Nappa".equals(veHUD.skha_detu)
            && "Nappa".equals(veHUD.skhq_detu)
            && "Nappa".equals(veHUD.skhg_detu)
            && "Nappa".equals(veHUD.skhj_detu)
            && "Nappa".equals(veHUD.skhrada_detu);

        boolean fullSetAyaka = "Dũng Sĩ Trong Băng Giá".equals(veHUD.skha_detu)
            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhq_detu)
            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhg_detu)
            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhj_detu)
            && "Dũng Sĩ Trong Băng Giá".equals(veHUD.skhrada_detu);

        if (fullSetNappa && !veHUD.vuaMoDeTuNappa) {
            duLieuNguoiChoi.deTu.setNappa(true);
            duLieuNguoiChoi.deTu.tangHpPt(80); // tăng đúng 80%
            veHUD.vuaMoDeTuNappa = true;
        }

        if (fullSetAyaka && !veHUD.vuaMoDeTuNappa) {
            duLieuNguoiChoi.deTu.tangChiMang(40);
            veHUD.vuaMoDeTuNappa = true;
        }
    }
}
