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
                    if (item.getLoai() == LoaiItem.CAITRANG ){
                        veHUD.itemDangChon = "caitrang";
                    } else { veHUD.itemDangChon = "avatar"; }

                    boolean loaiCaiTrangDangMac = NhanVatXuLy.getDangMacCaiTrang(); // cái đang mặc
                    boolean laCaiTrang = item.getLoai() == LoaiItem.CAITRANG;       // cái sắp mặc
                    if (veHUD.iconct == null) {
                        // Gán cải trang mới, không có cái cũ
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,5);
                        veHUD.iconct = item.getTexture();
                        nhanVat.setIdCaiTrang(item.getId());
                        nhanVat.setTenCaiTrang(item.getTenItem());
                        nhanVat.setMoTaCaiTrang(item.getMoTa());
                        nhanVat.setChisoCaiTrang(item.getChiso());
                        nhanVat.setHanSuDungCaiTrang(item.getHanSuDung());
                        nhanVat.setHanhTinhCaiTrang(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauCaiTrang(item.getSucManhYeuCau());
                        veHUD.avatardangmac = laCaiTrang ? nhanVat.getNhanvat() + "_base" : item.getId();
                        if (laCaiTrang) {
                            NhanVatXuLy.setDangMacCaiTrang(true);
                            NhanVatXuLy.setDangMacAvatar(false);
                        } else {
                            NhanVatXuLy.setDangMacAvatar(true);
                            NhanVatXuLy.setDangMacCaiTrang(false);
                        }
                        if (!veHUD.dangHopThe) {
                            NhanVatCauHinh c2 = laCaiTrang ? veHUD.Doicaitrang(item.getId()) : veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), item.getId(), veHUD.aodangmac, veHUD.quandangmac);
                            nhanVat.fixCaiTrang(
                                c2.dau_dung, c2.dau_chay,
                                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                                c2.than_bay, c2.chan_bay,
                                c2.lechMap,
                                c2.avt
                            );
                            veHUD.texAvt = new Texture(nhanVat.doiavatar());
                        }
                        tangchiso(item.getChiso());
                        danhSach.remove(indexx);
                    } else {
                        macCaiTrangMoi(item, indexx, danhSach, loaiCaiTrangDangMac, laCaiTrang);
                    }
                } else if (item.getLoai() == LoaiItem.AO) {
                    veHUD.itemDangChon = "ao";
                    if (veHUD.ao == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,0);
                        veHUD.ao = item.getTexture();
                        nhanVat.setIdAo(item.getId());
                        nhanVat.setTenAo(item.getTenItem());
                        nhanVat.setMoTaAo(item.getMoTa());
                        nhanVat.setChisoAo(item.getChiso());
                        veHUD.aodangmac = item.getId();
                        veHUD.skha = item.getSetkichhoat();
                        nhanVat.setSoSaoAo(item.getSoSaoPhaLe());
                        nhanVat.setSoCapAo(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaAo(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhAo(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauAo(item.getSucManhYeuCau());
                        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe) {
                            NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, item.getId(), veHUD.quandangmac);
                            nhanVat.fixCaiTrang(
                                c2.dau_dung, c2.dau_chay,
                                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                                c2.than_bay, c2.chan_bay,
                                c2.lechMap,
                                c2.avt
                            );
                        }
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacAo(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macAoMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.QUAN) {
                    veHUD.itemDangChon = "quan";
                    if (veHUD.quan == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,1);
                        veHUD.quan = item.getTexture();
                        nhanVat.setIdQuan(item.getId());
                        nhanVat.setTenQuan(item.getTenItem());
                        nhanVat.setMoTaQuan(item.getMoTa());
                        nhanVat.setChisoQuan(item.getChiso());
                        veHUD.quandangmac = item.getId();
                        veHUD.skhq = item.getSetkichhoat();
                        nhanVat.setSoSaoQuan(item.getSoSaoPhaLe());
                        nhanVat.setSoCapQuan(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaQuan(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhQuan(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauQuan(item.getSucManhYeuCau());
                        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe) {
                            NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, veHUD.aodangmac, item.getId());
                            nhanVat.fixCaiTrang(
                                c2.dau_dung, c2.dau_chay,
                                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                                c2.than_bay, c2.chan_bay,
                                c2.lechMap,
                                c2.avt
                            );
                        }
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacQuan(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macQuanMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.GANG) {
                    veHUD.itemDangChon = "gang";
                    if (veHUD.gang == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,2);
                        veHUD.gang = item.getTexture();
                        nhanVat.setIdGang(item.getId());
                        nhanVat.setTenGang(item.getTenItem());
                        nhanVat.setMoTaGang(item.getMoTa());
                        nhanVat.setChisoGang(item.getChiso());
                        veHUD.skhg = item.getSetkichhoat();
                        nhanVat.setSoSaoGang(item.getSoSaoPhaLe());
                        nhanVat.setSoCapGang(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaGang(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhGang(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauGang(item.getSucManhYeuCau());
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacGang(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macGangMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.GIAY) {
                    veHUD.itemDangChon = "giay";
                    if (veHUD.giay == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,3);
                        veHUD.giay = item.getTexture();
                        nhanVat.setIdGiay(item.getId());
                        nhanVat.setTenGiay(item.getTenItem());
                        nhanVat.setMoTaGiay(item.getMoTa());
                        nhanVat.setChisoGiay(item.getChiso());
                        veHUD.skhj = item.getSetkichhoat();
                        nhanVat.setSoSaoGiay(item.getSoSaoPhaLe());
                        nhanVat.setSoCapGiay(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaGiay(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhGiay(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauGiay(item.getSucManhYeuCau());
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacGiay(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macGiayMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.RADA) {
                    veHUD.itemDangChon = "rada";
                    if (veHUD.rada == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,4);
                        veHUD.rada = item.getTexture();
                        nhanVat.setIdRada(item.getId());
                        nhanVat.setTenRada(item.getTenItem());
                        nhanVat.setMoTaRada(item.getMoTa());
                        nhanVat.setChisoRada(item.getChiso());
                        veHUD.skhrada = item.getSetkichhoat();
                        nhanVat.setSoSaoRada(item.getSoSaoPhaLe());
                        nhanVat.setSoCapRada(item.getSoCap());
                        nhanVat.setSoSaoCuongHoaRada(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanhTinhRada(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauRada(item.getSucManhYeuCau());
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacRada(true);
                        kichHoatSetHienTai();
                        danhSach.remove(indexx);
                    } else {
                        macRadaMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                    veHUD.itemDangChon = "giapluyentap";
                    if (veHUD.giaplt == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,6);
                        veHUD.giaplt = item.getTexture();
                        nhanVat.setIdGiapLuyenTap(item.getId());
                        nhanVat.setTenGiapLuyenTap(item.getTenItem());
                        nhanVat.setMoTaGiapLuyenTap(item.getMoTa());
                        nhanVat.setChisoGiapLuyenTap(item.getChiso());
                        nhanVat.setSoSaoGiapLuyenTap(item.getSoSaoPhaLe());
                        nhanVat.setSoSaoCuongHoaGlt(item.getSoSaoPhaLeCuongHoa());
                        nhanVat.setHanSuDungGiapLuyenTap(item.getHanSuDung());
                        nhanVat.setHanhTinhGiapLuyenTap(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauGiapLuyenTap(item.getSucManhYeuCau());
                        tangchiso(item.getChiso());
                        duLieuNguoiChoi.dangMacGlt(true);
                        veHUD.dangMacGiapLuyenTap = true;
                        danhSach.remove(indexx);
                    } else {
                        macGiapLuyenTapMoi(item, indexx, danhSach);
                    }
                } else if (item.getLoai() == LoaiItem.VANBAY) {
                    veHUD.itemDangChon = "vanbay";
                    if (veHUD.vanbay == null){
                        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,7);
                        veHUD.vanbay = item.getTexture();
                        nhanVat.setIdVanBay(item.getId());
                        nhanVat.setTenVanBay(item.getTenItem());
                        nhanVat.setMoTaVanBay(item.getMoTa());
                        nhanVat.setChisoVanBay(item.getChiso());
                        nhanVat.setHanhTinhVanBay(item.getHanhtinh());
                        nhanVat.setSucManhYeuCauVanBay(item.getSucManhYeuCau());
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

    public void macDoChoDe(int index) {
        int indexx = veHUD.hangTrangDangChon - 8;
        ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();

        if (indexx < danhSach.size()) {
            Item item = danhSach.get(indexx);
            veHUD.itemm = item;
            if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {
                if (item.getLoai() == LoaiItem.CAITRANG ){
                    veHUD.itemDangChon = "caitrang";
                } else { veHUD.itemDangChon = "avatar"; }

                boolean loaiCaiTrangDangMac = DeTuXuLy.getDangMacCaiTrang(); // cái đang mặc
                boolean laCaiTrang = item.getLoai() == LoaiItem.CAITRANG;       // cái sắp mặc
                if (veHUD.iconctDeTu == null) {
                    // Gán cải trang mới, không có cái cũ
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,5);
                    veHUD.iconctDeTu = item.getTexture();
                    duLieuNguoiChoi.deTu.setIdCaiTrang(item.getId());
                    duLieuNguoiChoi.deTu.setTenCaiTrang(item.getTenItem());
                    duLieuNguoiChoi.deTu.setMoTaCaiTrang(item.getMoTa());
                    duLieuNguoiChoi.deTu.setChisoCaiTrang(item.getChiso());
                    duLieuNguoiChoi.deTu.setHanSuDungCaiTrang(item.getHanSuDung());
                    duLieuNguoiChoi.deTu.setHanhTinhCaiTrang(item.getHanhtinh());
                    duLieuNguoiChoi.deTu.setSucManhYeuCauCaiTrang(item.getSucManhYeuCau());
                    if (laCaiTrang) {
                        duLieuNguoiChoi.deTu.setAvtDangMac(duLieuNguoiChoi.deTu.getHanhtinh() + "_base");
                    } else {
                        duLieuNguoiChoi.deTu.setAvtDangMac(item.getId());
                    }

                    DeTuCauHinh c2 = laCaiTrang ? veHUD.DoicaitrangDeTu(item.getId()) : veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), item.getId(), veHUD.aodetudangmac, veHUD.quandetudangmac);
                    duLieuNguoiChoi.deTu.fixCaiTrang(
                        c2.dau_dung_de_tu, c2.dau_chay_de_tu,
                        c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
                        c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
                        c2.than_bay_de_tu, c2.chan_bay_de_tu,
                        c2.lechMapDeTu
                    );
                    tangchisoDeTu(item.getChiso());
                    danhSach.remove(indexx);
                } else {
                    macCaiTrangMoiDeTu(item, indexx, danhSach, loaiCaiTrangDangMac, laCaiTrang);
                }
            } else if (item.getLoai() == LoaiItem.AO) {
                veHUD.itemDangChon = "ao";
                if (veHUD.aoDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,0);
                    veHUD.aoDeTu = item.getTexture();
                    duLieuNguoiChoi.deTu.setIdAo(item.getId());
                    duLieuNguoiChoi.deTu.setTenAo(item.getTenItem());
                    duLieuNguoiChoi.deTu.setMoTaAo(item.getMoTa());
                    duLieuNguoiChoi.deTu.setChisoAo(item.getChiso());
                    veHUD.aodetudangmac = item.getId();
                    veHUD.skha_detu = item.getSetkichhoat();
                    duLieuNguoiChoi.deTu.setSoSaoAo(item.getSoSaoPhaLe());
                    duLieuNguoiChoi.deTu.setSoCapAo(item.getSoCap());
                    duLieuNguoiChoi.deTu.setSoSaoCuongHoaAo(item.getSoSaoPhaLeCuongHoa());
                    duLieuNguoiChoi.deTu.setHanhTinhAo(item.getHanhtinh());
                    duLieuNguoiChoi.deTu.setSucManhYeuCauAo(item.getSucManhYeuCau());
                    if (!DeTuXuLy.getDangMacCaiTrang()) {
                        DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), item.getId(), veHUD.quandetudangmac);
                        duLieuNguoiChoi.deTu.fixCaiTrang(
                            c2.dau_dung_de_tu, c2.dau_chay_de_tu,
                            c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
                            c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
                            c2.than_bay_de_tu, c2.chan_bay_de_tu,
                            c2.lechMapDeTu
                        );
                    }
                    tangchisoDeTu(item.getChiso());
                    duLieuNguoiChoi.deTu.dangMacAo(true);
                    kichHoatSetHienTaiDeTu();
                    danhSach.remove(indexx);
                } else {
                    macAoMoiDeTu(item, indexx, danhSach);
                }
            } else if (item.getLoai() == LoaiItem.QUAN) {
                veHUD.itemDangChon = "quan";
                if (veHUD.quanDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,1);
                    veHUD.quanDeTu = item.getTexture();
                    duLieuNguoiChoi.deTu.setIdQuan(item.getId());
                    duLieuNguoiChoi.deTu.setTenQuan(item.getTenItem());
                    duLieuNguoiChoi.deTu.setMoTaQuan(item.getMoTa());
                    duLieuNguoiChoi.deTu.setChisoQuan(item.getChiso());
                    veHUD.quandetudangmac = item.getId();
                    veHUD.skhq_detu = item.getSetkichhoat();
                    duLieuNguoiChoi.deTu.setSoSaoQuan(item.getSoSaoPhaLe());
                    duLieuNguoiChoi.deTu.setSoCapQuan(item.getSoCap());
                    duLieuNguoiChoi.deTu.setSoSaoCuongHoaQuan(item.getSoSaoPhaLeCuongHoa());
                    duLieuNguoiChoi.deTu.setHanhTinhQuan(item.getHanhtinh());
                    duLieuNguoiChoi.deTu.setSucManhYeuCauQuan(item.getSucManhYeuCau());
                    if (!DeTuXuLy.getDangMacCaiTrang()) {
                        DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), veHUD.aodetudangmac, item.getId());
                        duLieuNguoiChoi.deTu.fixCaiTrang(
                            c2.dau_dung_de_tu, c2.dau_chay_de_tu,
                            c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
                            c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
                            c2.than_bay_de_tu, c2.chan_bay_de_tu,
                            c2.lechMapDeTu
                        );
                    }
                    tangchisoDeTu(item.getChiso());
                    duLieuNguoiChoi.deTu.dangMacQuan(true);
                    kichHoatSetHienTaiDeTu();
                    danhSach.remove(indexx);
                } else {
                    macQuanMoiDeTu(item, indexx, danhSach);
                }
            } else if (item.getLoai() == LoaiItem.GANG) {
                veHUD.itemDangChon = "gang";
                if (veHUD.gangDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,2);
                    veHUD.gangDeTu = item.getTexture();
                    duLieuNguoiChoi.deTu.setIdGang(item.getId());
                    duLieuNguoiChoi.deTu.setTenGang(item.getTenItem());
                    duLieuNguoiChoi.deTu.setMoTaGang(item.getMoTa());
                    duLieuNguoiChoi.deTu.setChisoGang(item.getChiso());
                    veHUD.skhg_detu = item.getSetkichhoat();
                    duLieuNguoiChoi.deTu.setSoSaoGang(item.getSoSaoPhaLe());
                    duLieuNguoiChoi.deTu.setSoCapGang(item.getSoCap());
                    duLieuNguoiChoi.deTu.setSoSaoCuongHoaGang(item.getSoSaoPhaLeCuongHoa());
                    duLieuNguoiChoi.deTu.setHanhTinhGang(item.getHanhtinh());
                    duLieuNguoiChoi.deTu.setSucManhYeuCauGang(item.getSucManhYeuCau());
                    tangchisoDeTu(item.getChiso());
                    duLieuNguoiChoi.deTu.dangMacGang(true);
                    kichHoatSetHienTaiDeTu();
                    danhSach.remove(indexx);
                } else {
                    macGangMoiDeTu(item, indexx, danhSach);
                }
            } else if (item.getLoai() == LoaiItem.GIAY) {
                veHUD.itemDangChon = "giay";
                if (veHUD.giayDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,3);
                    veHUD.giayDeTu = item.getTexture();
                    duLieuNguoiChoi.deTu.setIdGiay(item.getId());
                    duLieuNguoiChoi.deTu.setTenGiay(item.getTenItem());
                    duLieuNguoiChoi.deTu.setMoTaGiay(item.getMoTa());
                    duLieuNguoiChoi.deTu.setChisoGiay(item.getChiso());
                    veHUD.skhj_detu = item.getSetkichhoat();
                    duLieuNguoiChoi.deTu.setSoSaoGiay(item.getSoSaoPhaLe());
                    duLieuNguoiChoi.deTu.setSoCapGiay(item.getSoCap());
                    duLieuNguoiChoi.deTu.setSoSaoCuongHoaGiay(item.getSoSaoPhaLeCuongHoa());
                    duLieuNguoiChoi.deTu.setHanhTinhGiay(item.getHanhtinh());
                    duLieuNguoiChoi.deTu.setSucManhYeuCauGiay(item.getSucManhYeuCau());
                    tangchisoDeTu(item.getChiso());
                    duLieuNguoiChoi.deTu.dangMacGiay(true);
                    kichHoatSetHienTaiDeTu();
                    danhSach.remove(indexx);
                } else {
                    macGiayMoiDeTu(item, indexx, danhSach);
                }
            } else if (item.getLoai() == LoaiItem.RADA) {
                veHUD.itemDangChon = "rada";
                if (veHUD.radaDeTu == null){
                    duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,4);
                    veHUD.radaDeTu = item.getTexture();
                    duLieuNguoiChoi.deTu.setIdRada(item.getId());
                    duLieuNguoiChoi.deTu.setTenRada(item.getTenItem());
                    duLieuNguoiChoi.deTu.setMoTaRada(item.getMoTa());
                    duLieuNguoiChoi.deTu.setChisoRada(item.getChiso());
                    veHUD.skhrada_detu = item.getSetkichhoat();
                    duLieuNguoiChoi.deTu.setSoSaoRada(item.getSoSaoPhaLe());
                    duLieuNguoiChoi.deTu.setSoCapRada(item.getSoCap());
                    duLieuNguoiChoi.deTu.setSoSaoCuongHoaRada(item.getSoSaoPhaLeCuongHoa());
                    duLieuNguoiChoi.deTu.setHanhTinhRada(item.getHanhtinh());
                    duLieuNguoiChoi.deTu.setSucManhYeuCauRada(item.getSucManhYeuCau());
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

    private void macAoMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu thông tin áo cũ
        String idCu = nhanVat.getIdAo();
        String tenCu = nhanVat.getTenAo();
        String motacu = nhanVat.getMoTaAo();
        int[] chisocu = nhanVat.getChisoAo();
        String skhaCu = veHUD.skha; // lưu lại skha cũ
        int sosaocu = nhanVat.getSoSaoAo();
        int socapcu = nhanVat.getSoCapAo();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaAo();
        String hanhtinhcu = nhanVat.getHanhTinhAo();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauAo();
        LoaiItem loaiCu = LoaiItem.AO;
        Item aoCu = new Item(idCu, tenCu, loaiCu, veHUD.ao, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhaCu,sosaocu ,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ trước
        huyHieuUngSet(skhaCu);
        duLieuNguoiChoi.dangMacAo(false);
        // 3. Giảm chỉ số áo cũ
        giamchiso(chisocu);

        // 4. Cập nhật áo mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,0);
        veHUD.ao = item.getTexture();
        nhanVat.setIdAo(item.getId());
        nhanVat.setTenAo(item.getTenItem());
        nhanVat.setMoTaAo(item.getMoTa());
        nhanVat.setChisoAo(item.getChiso());
        veHUD.skha = item.getSetkichhoat();
        veHUD.aodangmac = item.getId();
        nhanVat.setSoSaoAo(item.getSoSaoPhaLe());
        nhanVat.setSoCapAo(item.getSoCap());
        nhanVat.setSoSaoCuongHoaAo(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhAo(item.getHanhtinh());
        nhanVat.setSucManhYeuCauAo(item.getSucManhYeuCau());

        // 5. Load avatar nếu không cải trang
        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe) {
            NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, item.getId(), veHUD.quandangmac);
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );
        }

        // 6. Tăng chỉ số áo mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacAo(true);
        // 7. Kích hoạt lại set nếu đủ
        kichHoatSetHienTai();

        // 8. Đưa áo cũ vào hành trang
        danhSach.set(indexx, aoCu);
    }

    public void goAo(boolean vut) {
        if (veHUD.ao == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,0);
        // 1. Lưu áo cũ
        String idCu = nhanVat.getIdAo();
        String tenCu = nhanVat.getTenAo();
        String motacu = nhanVat.getMoTaAo();
        int[] chisocu = nhanVat.getChisoAo();
        String skhaCu = veHUD.skha;
        int sosaocu = nhanVat.getSoSaoAo();
        int socapcu = nhanVat.getSoCapAo();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaAo();
        String hanhtinhcu = nhanVat.getHanhTinhAo();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauAo();
        LoaiItem loaiCu = LoaiItem.AO;
        Item aoCu = new Item(idCu, tenCu, loaiCu, veHUD.ao, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhaCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSet(skhaCu);
        duLieuNguoiChoi.dangMacAo(false);
        // 3. Giảm chỉ số áo
        giamchiso(chisocu);

        // 4. Cập nhật trạng thái không mặc
        veHUD.ao = null;
        veHUD.skha = "mac_dinh";
        veHUD.aodangmac = "set_base";

        // 5. Cập nhật giao diện nếu không cải trang
        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe){
            NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, "set_base", veHUD.quandangmac);
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );
        }

        // 6. Kích hoạt lại set nếu đủ 4 món còn lại
        kichHoatSetHienTai();

        // 7. Trả áo cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(aoCu);
        }
    }

    private void macAoMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu thông tin áo cũ
        String idCu = duLieuNguoiChoi.deTu.getIdAo();
        String tenCu = duLieuNguoiChoi.deTu.getTenAo();
        String motacu = duLieuNguoiChoi.deTu.getMoTaAo();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoAo();
        String skhaCu = veHUD.skha_detu; // lưu lại skha cũ
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoAo();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapAo();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaAo();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhAo();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauAo();
        LoaiItem loaiCu = LoaiItem.AO;
        Item aoCu = new Item(idCu, tenCu, loaiCu, veHUD.aoDeTu, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhaCu,sosaocu ,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ trước
        huyHieuUngSetDeTu(skhaCu);
        duLieuNguoiChoi.deTu.dangMacAo(false);
        // 3. Giảm chỉ số áo cũ
        giamchisoDeTu(chisocu);

        // 4. Cập nhật áo mới
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,0);
        veHUD.aoDeTu = item.getTexture();
        duLieuNguoiChoi.deTu.setIdAo(item.getId());
        duLieuNguoiChoi.deTu.setTenAo(item.getTenItem());
        duLieuNguoiChoi.deTu.setMoTaAo(item.getMoTa());
        duLieuNguoiChoi.deTu.setChisoAo(item.getChiso());
        veHUD.skha_detu = item.getSetkichhoat();
        veHUD.aodetudangmac = item.getId();
        duLieuNguoiChoi.deTu.setSoSaoAo(item.getSoSaoPhaLe());
        duLieuNguoiChoi.deTu.setSoCapAo(item.getSoCap());
        duLieuNguoiChoi.deTu.setSoSaoCuongHoaAo(item.getSoSaoPhaLeCuongHoa());
        duLieuNguoiChoi.deTu.setHanhTinhAo(item.getHanhtinh());
        duLieuNguoiChoi.deTu.setSucManhYeuCauAo(item.getSucManhYeuCau());

        // 5. Load avatar nếu không cải trang
        if (!DeTuXuLy.getDangMacCaiTrang()) {
            DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), item.getId(), veHUD.quandetudangmac);
            duLieuNguoiChoi.deTu.fixCaiTrang(
                c2.dau_dung_de_tu, c2.dau_chay_de_tu,
                c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
                c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
                c2.than_bay_de_tu, c2.chan_bay_de_tu,
                c2.lechMapDeTu
            );
        }

        // 6. Tăng chỉ số áo mới
        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacAo(true);
        // 7. Kích hoạt lại set nếu đủ
        kichHoatSetHienTaiDeTu();

        // 8. Đưa áo cũ vào hành trang
        danhSach.set(indexx, aoCu);
    }

    public void goAoDeTu() {
        if (veHUD.aoDeTu == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null,0);
        // 1. Lưu áo cũ
        String idCu = duLieuNguoiChoi.deTu.getIdAo();
        String tenCu = duLieuNguoiChoi.deTu.getTenAo();
        String motacu = duLieuNguoiChoi.deTu.getMoTaAo();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoAo();
        String skhaCu = veHUD.skha_detu;
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoAo();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapAo();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaAo();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhAo();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauAo();
        LoaiItem loaiCu = LoaiItem.AO;
        Item aoCu = new Item(idCu, tenCu, loaiCu, veHUD.aoDeTu, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhaCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSetDeTu(skhaCu);
        duLieuNguoiChoi.deTu.dangMacAo(false);
        // 3. Giảm chỉ số áo
        giamchisoDeTu(chisocu);

        // 4. Cập nhật trạng thái không mặc
        veHUD.aoDeTu = null;
        veHUD.skha_detu = "mac_dinh";
        veHUD.aodetudangmac = "set_base";

        // 5. Cập nhật giao diện nếu không cải trang
        if (!DeTuXuLy.getDangMacCaiTrang()){
            DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), "set_base", veHUD.quandetudangmac);
            duLieuNguoiChoi.deTu.fixCaiTrang(
                c2.dau_dung_de_tu, c2.dau_chay_de_tu,
                c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
                c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
                c2.than_bay_de_tu, c2.chan_bay_de_tu,
                c2.lechMapDeTu
            );
        }

        // 6. Kích hoạt lại set nếu đủ 4 món còn lại
        kichHoatSetHienTaiDeTu();

        // 7. Trả áo cũ vào hành trang
        duLieuNguoiChoi.themItemVaoHanhTrang(aoCu);
    }

    private void macQuanMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu thông tin quần cũ
        String idCu = nhanVat.getIdQuan();
        String tenCu = nhanVat.getTenQuan();
        String motacu = nhanVat.getMoTaQuan();
        int[] chisocu = nhanVat.getChisoQuan();
        String skhqCu = veHUD.skhq; // lưu lại skhq cũ
        int sosaocu = nhanVat.getSoSaoQuan();
        int socapcu = nhanVat.getSoCapQuan();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaQuan();
        String hanhtinhcu = nhanVat.getHanhTinhQuan();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauQuan();
        LoaiItem loaiCu = LoaiItem.QUAN;
        Item quanCu = new Item(idCu, tenCu, loaiCu, veHUD.quan, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhqCu,sosaocu ,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ trước khi gỡ chỉ số
        huyHieuUngSet(skhqCu);
        duLieuNguoiChoi.dangMacQuan(false);
        // 3. Giảm chỉ số quần cũ
        giamchiso(chisocu);

        // 4. Gán quần mới vào
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,1);
        veHUD.quan = item.getTexture();
        nhanVat.setIdQuan(item.getId());
        nhanVat.setTenQuan(item.getTenItem());
        nhanVat.setMoTaQuan(item.getMoTa());
        nhanVat.setChisoQuan(item.getChiso());
        veHUD.skhq = item.getSetkichhoat();
        veHUD.quandangmac = item.getId();
        nhanVat.setSoSaoQuan(item.getSoSaoPhaLe());
        nhanVat.setSoCapQuan(item.getSoCap());
        nhanVat.setSoSaoCuongHoaQuan(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhQuan(item.getHanhtinh());
        nhanVat.setSucManhYeuCauQuan(item.getSucManhYeuCau());

        // 5. Load avatar nếu không cải trang
        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe) {
            NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, veHUD.aodangmac, item.getId());
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );
        }

        // 6. Tăng chỉ số quần mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacQuan(true);
        // 7. Kích hoạt lại set mới nếu đủ
        kichHoatSetHienTai();

        // 8. Đưa quần cũ vào hành trang
        danhSach.set(indexx, quanCu);
    }

    public void goQuan(boolean vut) {
        if (veHUD.quan == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,1);
        // 1. Lưu thông tin quần cũ
        String idCu = nhanVat.getIdQuan();
        String tenCu = nhanVat.getTenQuan();
        String motacu = nhanVat.getMoTaQuan();
        int[] chisocu = nhanVat.getChisoQuan();
        String skhqCu = veHUD.skhq;
        int sosaocu = nhanVat.getSoSaoQuan();
        int socapcu = nhanVat.getSoCapQuan();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaQuan();
        String hanhtinhcu = nhanVat.getHanhTinhQuan();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauQuan();
        LoaiItem loaiCu = LoaiItem.QUAN;
        Item quanCu = new Item(idCu, tenCu, loaiCu, veHUD.quan, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhqCu, sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSet(skhqCu);
        duLieuNguoiChoi.dangMacQuan(false);
        // 3. Giảm chỉ số quần
        giamchiso(chisocu);

        // 4. Cập nhật lại trạng thái không mặc gì
        veHUD.skhq = "mac_dinh";
        veHUD.quan = null;
        veHUD.quandangmac = "set_base";

        // 5. Cập nhật giao diện nếu không cải trang
        if (!NhanVatXuLy.getDangMacCaiTrang() && !veHUD.dangHopThe) {
            NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), veHUD.avatardangmac, veHUD.aodangmac, "set_base");
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );
        }

        // 6. Kiểm tra lại set còn lại có đủ không
        kichHoatSetHienTai();

        // 7. Trả quần cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(quanCu);
        }
    }

    private void macQuanMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu thông tin quần cũ
        String idCu = duLieuNguoiChoi.deTu.getIdQuan();
        String tenCu = duLieuNguoiChoi.deTu.getTenQuan();
        String motacu = duLieuNguoiChoi.deTu.getMoTaQuan();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoQuan();
        String skhqCu = veHUD.skhq_detu; // lưu lại skhq cũ
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoQuan();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapQuan();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaQuan();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhQuan();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauQuan();
        LoaiItem loaiCu = LoaiItem.QUAN;
        Item quanCu = new Item(idCu, tenCu, loaiCu, veHUD.quanDeTu, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhqCu,sosaocu ,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ trước khi gỡ chỉ số
        huyHieuUngSetDeTu(skhqCu);
        duLieuNguoiChoi.deTu.dangMacQuan(false);
        // 3. Giảm chỉ số quần cũ
        giamchisoDeTu(chisocu);

        // 4. Gán quần mới vào
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,1);
        veHUD.quanDeTu = item.getTexture();
        duLieuNguoiChoi.deTu.setIdQuan(item.getId());
        duLieuNguoiChoi.deTu.setTenQuan(item.getTenItem());
        duLieuNguoiChoi.deTu.setMoTaQuan(item.getMoTa());
        duLieuNguoiChoi.deTu.setChisoQuan(item.getChiso());
        veHUD.skhq_detu = item.getSetkichhoat();
        veHUD.quandetudangmac = item.getId();
        duLieuNguoiChoi.deTu.setSoSaoQuan(item.getSoSaoPhaLe());
        duLieuNguoiChoi.deTu.setSoCapQuan(item.getSoCap());
        duLieuNguoiChoi.deTu.setSoSaoCuongHoaQuan(item.getSoSaoPhaLeCuongHoa());
        duLieuNguoiChoi.deTu.setHanhTinhQuan(item.getHanhtinh());
        duLieuNguoiChoi.deTu.setSucManhYeuCauQuan(item.getSucManhYeuCau());

        // 5. Load avatar nếu không cải trang
        if (!DeTuXuLy.getDangMacCaiTrang()) {
            DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(),veHUD.aodetudangmac, item.getId());
            duLieuNguoiChoi.deTu.fixCaiTrang(
                c2.dau_dung_de_tu, c2.dau_chay_de_tu,
                c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
                c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
                c2.than_bay_de_tu, c2.chan_bay_de_tu,
                c2.lechMapDeTu
            );
        }

        // 6. Tăng chỉ số quần mới
        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacQuan(true);
        // 7. Kích hoạt lại set mới nếu đủ
        kichHoatSetHienTaiDeTu();

        // 8. Đưa quần cũ vào hành trang
        danhSach.set(indexx, quanCu);
    }

    public void goQuanDeTu() {
        if (veHUD.quanDeTu == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null, 1);

        // 1. Lưu quần cũ
        String idCu = duLieuNguoiChoi.deTu.getIdQuan();
        String tenCu = duLieuNguoiChoi.deTu.getTenQuan();
        String motacu = duLieuNguoiChoi.deTu.getMoTaQuan();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoQuan();
        String skhqCu = veHUD.skhq_detu;
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoQuan();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapQuan();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaQuan();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhQuan();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauQuan();
        LoaiItem loaiCu = LoaiItem.QUAN;
        Item quanCu = new Item(idCu, tenCu, loaiCu, veHUD.quanDeTu, motacu, 1, chisocu, hanhtinhcu, sucmanhyeucaucu, skhqCu, sosaocu, sosaocuonghoacu, socapcu, -1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSetDeTu(skhqCu);
        duLieuNguoiChoi.deTu.dangMacQuan(false);

        // 3. Giảm chỉ số quần
        giamchisoDeTu(chisocu);

        // 4. Cập nhật trạng thái không mặc
        veHUD.quanDeTu = null;
        veHUD.skhq_detu = "mac_dinh";
        veHUD.quandetudangmac = "set_base";

        // 5. Cập nhật giao diện nếu không cải trang
        if (!DeTuXuLy.getDangMacCaiTrang()) {
            DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getAvtDangMac(), veHUD.aodetudangmac, "set_base");
            duLieuNguoiChoi.deTu.fixCaiTrang(
                c2.dau_dung_de_tu, c2.dau_chay_de_tu,
                c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
                c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
                c2.than_bay_de_tu, c2.chan_bay_de_tu,
                c2.lechMapDeTu
            );
        }

        // 6. Kích hoạt lại set nếu đủ 4 món còn lại
        kichHoatSetHienTaiDeTu();

        // 7. Trả quần cũ vào hành trang
        duLieuNguoiChoi.themItemVaoHanhTrang(quanCu);
    }

    private void macGangMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu găng cũ
        String idCu = nhanVat.getIdGang();
        String tenCu = nhanVat.getTenGang();
        String motacu = nhanVat.getMoTaGang();
        int[] chisocu = nhanVat.getChisoGang();
        String skhgCu = veHUD.skhg;
        int sosaocu = nhanVat.getSoSaoGang();
        int socapcu = nhanVat.getSoCapGang();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGang();
        String hanhtinhcu = nhanVat.getHanhTinhGang();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGang();
        LoaiItem loaiCu = LoaiItem.GANG;
        Item gangCu = new Item(idCu, tenCu, loaiCu, veHUD.gang, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhgCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ
        huyHieuUngSet(skhgCu);
        duLieuNguoiChoi.dangMacGang(false);
        // 3. Giảm chỉ số găng cũ
        giamchiso(chisocu);

        // 4. Gán găng mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,2);
        veHUD.gang = item.getTexture();
        nhanVat.setIdGang(item.getId());
        nhanVat.setTenGang(item.getTenItem());
        nhanVat.setMoTaGang(item.getMoTa());
        nhanVat.setChisoGang(item.getChiso());
        veHUD.skhg = item.getSetkichhoat();
        nhanVat.setSoSaoGang(item.getSoSaoPhaLe());
        nhanVat.setSoCapGang(item.getSoCap());
        nhanVat.setSoSaoCuongHoaGang(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhGang(item.getHanhtinh());
        nhanVat.setSucManhYeuCauGang(item.getSucManhYeuCau());

        // 5. Tăng chỉ số găng mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacGang(true);
        // 6. Kích hoạt lại set nếu đủ
        kichHoatSetHienTai();

        // 7. Đưa găng cũ vào hành trang
        danhSach.set(indexx, gangCu);
    }

    public void goGang(boolean vut) {
        if (veHUD.gang == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,2);
        // 1. Lưu găng cũ
        String idCu = nhanVat.getIdGang();
        String tenCu = nhanVat.getTenGang();
        String motacu = nhanVat.getMoTaGang();
        int[] chisocu = nhanVat.getChisoGang();
        String skhgCu = veHUD.skhg;
        int sosaocu = nhanVat.getSoSaoGang();
        int socapcu = nhanVat.getSoCapGang();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGang();
        String hanhtinhcu = nhanVat.getHanhTinhGang();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGang();
        LoaiItem loaiCu = LoaiItem.GANG;
        Item gangCu = new Item(idCu, tenCu, loaiCu, veHUD.gang, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhgCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSet(skhgCu);
        duLieuNguoiChoi.dangMacGang(false);
        // 3. Giảm chỉ số găng
        giamchiso(chisocu);

        // 4. Cập nhật trạng thái không mặc
        veHUD.gang = null;
        veHUD.skhg = "mac_dinh";

        // 5. Kích hoạt lại set nếu vẫn còn đủ 4 món
        kichHoatSetHienTai();

        // 6. Trả găng cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(gangCu);
        }
    }

    private void macGangMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu găng cũ
        String idCu = duLieuNguoiChoi.deTu.getIdGang();
        String tenCu = duLieuNguoiChoi.deTu.getTenGang();
        String motacu = duLieuNguoiChoi.deTu.getMoTaGang();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoGang();
        String skhgCu = veHUD.skhg_detu;
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoGang();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapGang();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaGang();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhGang();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauGang();
        LoaiItem loaiCu = LoaiItem.GANG;
        Item gangCu = new Item(idCu, tenCu, loaiCu, veHUD.gangDeTu, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhgCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ
        huyHieuUngSetDeTu(skhgCu);
        duLieuNguoiChoi.deTu.dangMacGang(false);
        // 3. Giảm chỉ số găng cũ
        giamchisoDeTu(chisocu);

        // 4. Gán găng mới
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,2);
        veHUD.gangDeTu = item.getTexture();
        duLieuNguoiChoi.deTu.setIdGang(item.getId());
        duLieuNguoiChoi.deTu.setTenGang(item.getTenItem());
        duLieuNguoiChoi.deTu.setMoTaGang(item.getMoTa());
        duLieuNguoiChoi.deTu.setChisoGang(item.getChiso());
        veHUD.skhg_detu = item.getSetkichhoat();
        duLieuNguoiChoi.deTu.setSoSaoGang(item.getSoSaoPhaLe());
        duLieuNguoiChoi.deTu.setSoCapGang(item.getSoCap());
        duLieuNguoiChoi.deTu.setSoSaoCuongHoaGang(item.getSoSaoPhaLeCuongHoa());
        duLieuNguoiChoi.deTu.setHanhTinhGang(item.getHanhtinh());
        duLieuNguoiChoi.deTu.setSucManhYeuCauGang(item.getSucManhYeuCau());

        // 5. Tăng chỉ số găng mới
        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacGang(true);
        // 6. Kích hoạt lại set nếu đủ
        kichHoatSetHienTaiDeTu();

        // 7. Đưa găng cũ vào hành trang
        danhSach.set(indexx, gangCu);
    }

    public void goGangDeTu() {
        if (veHUD.gangDeTu == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null,2);
        // 1. Lưu găng cũ
        String idCu = duLieuNguoiChoi.deTu.getIdGang();
        String tenCu = duLieuNguoiChoi.deTu.getTenGang();
        String motacu = duLieuNguoiChoi.deTu.getMoTaGang();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoGang();
        String skhgCu = veHUD.skhg_detu;
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoGang();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapGang();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaGang();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhGang();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauGang();
        LoaiItem loaiCu = LoaiItem.GANG;
        Item gangCu = new Item(idCu, tenCu, loaiCu, veHUD.gangDeTu, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhgCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSetDeTu(skhgCu);
        duLieuNguoiChoi.deTu.dangMacGang(false);
        // 3. Giảm chỉ số găng
        giamchisoDeTu(chisocu);

        // 4. Cập nhật trạng thái không mặc
        veHUD.gangDeTu = null;
        veHUD.skhg_detu = "mac_dinh";

        // 5. Kích hoạt lại set nếu vẫn còn đủ 4 món
        kichHoatSetHienTaiDeTu();

        // 6. Trả găng cũ vào hành trang
        duLieuNguoiChoi.themItemVaoHanhTrang(gangCu);
    }

    private void macGiayMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu giày cũ
        String idCu = nhanVat.getIdGiay();
        String tenCu = nhanVat.getTenGiay();
        String motacu = nhanVat.getMoTaGiay();
        int[] chisocu = nhanVat.getChisoGiay();
        String skhjCu = veHUD.skhj;
        int sosaocu = nhanVat.getSoSaoGiay();
        int socapcu = nhanVat.getSoCapGiay();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGiay();
        String hanhtinhcu = nhanVat.getHanhTinhGiay();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGiay();
        LoaiItem loaiCu = LoaiItem.GIAY;
        Item giayCu = new Item(idCu, tenCu, loaiCu, veHUD.giay, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhjCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ
        huyHieuUngSet(skhjCu);
        duLieuNguoiChoi.dangMacGiay(false);
        // 3. Giảm chỉ số giày cũ
        giamchiso(chisocu);

        // 4. Gán giày mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,3);
        veHUD.giay = item.getTexture();
        nhanVat.setIdGiay(item.getId());
        nhanVat.setTenGiay(item.getTenItem());
        nhanVat.setMoTaGiay(item.getMoTa());
        nhanVat.setChisoGiay(item.getChiso());
        veHUD.skhj = item.getSetkichhoat();
        nhanVat.setSoSaoGiay(item.getSoSaoPhaLe());
        nhanVat.setSoCapGiay(item.getSoCap());
        nhanVat.setSoSaoCuongHoaGiay(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhGiay(item.getHanhtinh());
        nhanVat.setSucManhYeuCauGiay(item.getSucManhYeuCau());

        // 5. Tăng chỉ số giày mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacGiay(true);
        // 6. Kích hoạt lại set nếu đủ
        kichHoatSetHienTai();

        // 7. Thay giày cũ vào hành trang
        danhSach.set(indexx, giayCu);
    }


    public void goGiay(boolean vut) {
        if (veHUD.giay == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,3);
        // 1. Lưu giày cũ
        String idCu = nhanVat.getIdGiay();
        String tenCu = nhanVat.getTenGiay();
        String motacu = nhanVat.getMoTaGiay();
        int[] chisocu = nhanVat.getChisoGiay();
        String skhjCu = veHUD.skhj;
        int sosaocu = nhanVat.getSoSaoGiay();
        int socapcu = nhanVat.getSoCapGiay();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGiay();
        String hanhtinhcu = nhanVat.getHanhTinhGiay();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGiay();
        LoaiItem loaiCu = LoaiItem.GIAY;
        Item giayCu = new Item(idCu, tenCu, loaiCu, veHUD.giay, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhjCu, sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSet(skhjCu);
        duLieuNguoiChoi.dangMacGiay(false);
        // 3. Giảm chỉ số giày
        giamchiso(chisocu);

        // 4. Cập nhật lại trạng thái
        veHUD.giay = null;
        veHUD.skhj = "mac_dinh";

        // 5. Kích hoạt lại set nếu vẫn còn đủ
        kichHoatSetHienTai();

        // 6. Trả giày cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(giayCu);
        }
    }

    private void macGiayMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu giày cũ
        String idCu = duLieuNguoiChoi.deTu.getIdGiay();
        String tenCu = duLieuNguoiChoi.deTu.getTenGiay();
        String motacu = duLieuNguoiChoi.deTu.getMoTaGiay();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoGiay();
        String skhjCu = veHUD.skhj_detu;
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoGiay();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapGiay();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaGiay();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhGiay();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauGiay();
        LoaiItem loaiCu = LoaiItem.GIAY;
        Item giayCu = new Item(idCu, tenCu, loaiCu, veHUD.giayDeTu, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhjCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ
        huyHieuUngSetDeTu(skhjCu);
        duLieuNguoiChoi.deTu.dangMacGiay(false);
        // 3. Giảm chỉ số giày cũ
        giamchisoDeTu(chisocu);

        // 4. Gán giày mới
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item, 3);
        veHUD.giayDeTu = item.getTexture();
        duLieuNguoiChoi.deTu.setIdGiay(item.getId());
        duLieuNguoiChoi.deTu.setTenGiay(item.getTenItem());
        duLieuNguoiChoi.deTu.setMoTaGiay(item.getMoTa());
        duLieuNguoiChoi.deTu.setChisoGiay(item.getChiso());
        veHUD.skhj_detu = item.getSetkichhoat();
        duLieuNguoiChoi.deTu.setSoSaoGiay(item.getSoSaoPhaLe());
        duLieuNguoiChoi.deTu.setSoCapGiay(item.getSoCap());
        duLieuNguoiChoi.deTu.setSoSaoCuongHoaGiay(item.getSoSaoPhaLeCuongHoa());
        duLieuNguoiChoi.deTu.setHanhTinhGiay(item.getHanhtinh());
        duLieuNguoiChoi.deTu.setSucManhYeuCauGiay(item.getSucManhYeuCau());

        // 5. Tăng chỉ số giày mới
        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacGiay(true);
        // 6. Kích hoạt lại set nếu đủ
        kichHoatSetHienTaiDeTu();

        // 7. Đưa giày cũ vào hành trang
        danhSach.set(indexx, giayCu);
    }

    public void goGiayDeTu() {
        if (veHUD.giayDeTu == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null, 3);

        // 1. Lưu giày cũ
        String idCu = duLieuNguoiChoi.deTu.getIdGiay();
        String tenCu = duLieuNguoiChoi.deTu.getTenGiay();
        String motacu = duLieuNguoiChoi.deTu.getMoTaGiay();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoGiay();
        String skhjCu = veHUD.skhj_detu;
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoGiay();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapGiay();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaGiay();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhGiay();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauGiay();
        LoaiItem loaiCu = LoaiItem.GIAY;

        Item giayCu = new Item(idCu, tenCu, loaiCu, veHUD.giayDeTu, motacu, 1, chisocu, hanhtinhcu, sucmanhyeucaucu, skhjCu, sosaocu, sosaocuonghoacu, socapcu, -1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSetDeTu(skhjCu);
        duLieuNguoiChoi.deTu.dangMacGiay(false);

        // 3. Giảm chỉ số giày
        giamchisoDeTu(chisocu);

        // 4. Cập nhật trạng thái không mặc
        veHUD.giayDeTu = null;
        veHUD.skhj_detu = "mac_dinh";

        // 5. Kích hoạt lại set nếu vẫn còn đủ 4 món
        kichHoatSetHienTaiDeTu();

        // 6. Trả giày cũ vào hành trang
        duLieuNguoiChoi.themItemVaoHanhTrang(giayCu);
    }

    private void macRadaMoi(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu rada cũ
        String idCu = nhanVat.getIdRada();
        String tenCu = nhanVat.getTenRada();
        String motacu = nhanVat.getMoTaRada();
        int[] chisocu = nhanVat.getChisoRada();
        String skhradaCu = veHUD.skhrada;
        int sosaocu = nhanVat.getSoSaoRada();
        int socapcu = nhanVat.getSoCapRada();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaRada();
        String hanhtinhcu = nhanVat.getHanhTinhRada();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauRada();
        LoaiItem loaiCu = LoaiItem.RADA;
        Item radaCu = new Item(idCu, tenCu, loaiCu, veHUD.rada, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhradaCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ (nếu có)
        huyHieuUngSet(skhradaCu);
        duLieuNguoiChoi.dangMacRada(false);
        // 3. Giảm chỉ số rada cũ
        giamchiso(chisocu);

        // 4. Gán rada mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,4);
        veHUD.rada = item.getTexture();
        nhanVat.setIdRada(item.getId());
        nhanVat.setTenRada(item.getTenItem());
        nhanVat.setMoTaRada(item.getMoTa());
        nhanVat.setChisoRada(item.getChiso());
        veHUD.skhrada = item.getSetkichhoat();
        nhanVat.setSoSaoRada(item.getSoSaoPhaLe());
        nhanVat.setSoCapRada(item.getSoCap());
        nhanVat.setSoSaoCuongHoaRada(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanhTinhRada(item.getHanhtinh());
        nhanVat.setSucManhYeuCauRada(item.getSucManhYeuCau());

        // 5. Tăng chỉ số rada mới
        tangchiso(item.getChiso());
        duLieuNguoiChoi.dangMacRada(true);
        // 6. Kích hoạt lại hiệu ứng set nếu đủ
        kichHoatSetHienTai();

        // 7. Thay rada cũ vào hành trang
        danhSach.set(indexx, radaCu);
    }


    public void goRada(boolean vut) {
        if (veHUD.rada == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,4);
        // 1. Lưu rada cũ
        String idCu = nhanVat.getIdRada();
        String tenCu = nhanVat.getTenRada();
        String motacu = nhanVat.getMoTaRada();
        int[] chisocu = nhanVat.getChisoRada();
        String skhradaCu = veHUD.skhrada;
        int sosaocu = nhanVat.getSoSaoRada();
        int socapcu = nhanVat.getSoCapRada();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaRada();
        String hanhtinhcu = nhanVat.getHanhTinhRada();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauRada();
        LoaiItem loaiCu = LoaiItem.RADA;
        Item radaCu = new Item(idCu, tenCu, loaiCu, veHUD.rada, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, skhradaCu,sosaocu,sosaocuonghoacu,socapcu,-1);

        // 2. Gỡ hiệu ứng set cũ
        huyHieuUngSet(skhradaCu);
        duLieuNguoiChoi.dangMacRada(false);
        // 3. Giảm chỉ số rada
        giamchiso(chisocu);

        // 4. Cập nhật trạng thái không mặc
        veHUD.rada = null;
        veHUD.skhrada = "mac_dinh";

        // 5. Kích hoạt lại hiệu ứng set nếu còn đủ món
        kichHoatSetHienTai();

        // 6. Trả rada cũ vào hành trang
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(radaCu);
        }
    }

    private void macRadaMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach){
        // 1. Lưu rada cũ
        String idCu = duLieuNguoiChoi.deTu.getIdRada();
        String tenCu = duLieuNguoiChoi.deTu.getTenRada();
        String motacu = duLieuNguoiChoi.deTu.getMoTaRada();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoRada();
        String skhgCu = veHUD.skhrada_detu;
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoRada();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapRada();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaRada();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhRada();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauRada();
        LoaiItem loaiCu = LoaiItem.RADA;
        Item radaCu = new Item(idCu, tenCu, loaiCu, veHUD.radaDeTu, motacu, 1, chisocu, hanhtinhcu, sucmanhyeucaucu, skhgCu, sosaocu, sosaocuonghoacu, socapcu, -1);

        // 2. Gỡ hiệu ứng set cũ
        huyHieuUngSetDeTu(skhgCu);
        duLieuNguoiChoi.deTu.dangMacRada(false);

        // 3. Giảm chỉ số rada cũ
        giamchisoDeTu(chisocu);

        // 4. Gán rada mới
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item, 4);
        veHUD.radaDeTu = item.getTexture();
        duLieuNguoiChoi.deTu.setIdRada(item.getId());
        duLieuNguoiChoi.deTu.setTenRada(item.getTenItem());
        duLieuNguoiChoi.deTu.setMoTaRada(item.getMoTa());
        duLieuNguoiChoi.deTu.setChisoRada(item.getChiso());
        veHUD.skhrada_detu = item.getSetkichhoat();
        duLieuNguoiChoi.deTu.setSoSaoRada(item.getSoSaoPhaLe());
        duLieuNguoiChoi.deTu.setSoCapRada(item.getSoCap());
        duLieuNguoiChoi.deTu.setSoSaoCuongHoaRada(item.getSoSaoPhaLeCuongHoa());
        duLieuNguoiChoi.deTu.setHanhTinhRada(item.getHanhtinh());
        duLieuNguoiChoi.deTu.setSucManhYeuCauRada(item.getSucManhYeuCau());

        // 5. Tăng chỉ số rada mới
        tangchisoDeTu(item.getChiso());
        duLieuNguoiChoi.deTu.dangMacRada(true);

        // 6. Kích hoạt lại set nếu đủ
        kichHoatSetHienTaiDeTu();

        // 7. Đưa rada cũ vào hành trang
        danhSach.set(indexx, radaCu);
    }

    public void goRadaDeTu() {
        if (veHUD.radaDeTu == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null, 4);

        // 1. Lưu rada cũ
        String idCu = duLieuNguoiChoi.deTu.getIdRada();
        String tenCu = duLieuNguoiChoi.deTu.getTenRada();
        String motacu = duLieuNguoiChoi.deTu.getMoTaRada();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoRada();
        String skhradaCu = veHUD.skhrada_detu;
        int sosaocu = duLieuNguoiChoi.deTu.getSoSaoRada();
        int socapcu = duLieuNguoiChoi.deTu.getSoCapRada();
        int sosaocuonghoacu = duLieuNguoiChoi.deTu.getSoSaoCuongHoaRada();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhRada();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauRada();
        LoaiItem loaiCu = LoaiItem.RADA;

        Item radaCu = new Item(idCu, tenCu, loaiCu, veHUD.radaDeTu, motacu, 1, chisocu, hanhtinhcu, sucmanhyeucaucu, skhradaCu, sosaocu, sosaocuonghoacu, socapcu, -1);

        // 2. Gỡ hiệu ứng set trước
        huyHieuUngSetDeTu(skhradaCu);
        duLieuNguoiChoi.deTu.dangMacRada(false);

        // 3. Giảm chỉ số rada
        giamchisoDeTu(chisocu);

        // 4. Cập nhật trạng thái không mặc
        veHUD.radaDeTu = null;
        veHUD.skhrada_detu = "mac_dinh";

        // 5. Kích hoạt lại set nếu vẫn còn đủ 4 món
        kichHoatSetHienTaiDeTu();

        // 6. Trả rada cũ vào hành trang
        duLieuNguoiChoi.themItemVaoHanhTrang(radaCu);
    }

    private void macCaiTrangMoi(Item item, int indexx, ArrayList<Item> danhSach, boolean caiTrangDangMac, boolean laCaiTrangMoi) {
        // 1. Lưu cải trang cũ
        String idCu = nhanVat.getIdCaiTrang();
        String tenCu = nhanVat.getTenCaiTrang();
        String motacu = nhanVat.getMoTaCaiTrang();
        int[] chisocu = nhanVat.getChisoCaiTrang();
        float hansudung = nhanVat.getHanSuDungCaiTrang();
        String hanhtinhcu = nhanVat.getHanhTinhCaiTrang();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauCaiTrang();
        LoaiItem loaiCu = caiTrangDangMac ? LoaiItem.CAITRANG : LoaiItem.AVATAR;

        Item caiTrangCu = new Item(idCu, tenCu, loaiCu, veHUD.iconct, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,hansudung);
        giamchiso(chisocu);

        // 2. Gán cải trang mới
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,5);
        veHUD.iconct = item.getTexture();
        nhanVat.setIdCaiTrang(item.getId());
        nhanVat.setTenCaiTrang(item.getTenItem());
        nhanVat.setMoTaCaiTrang(item.getMoTa());
        nhanVat.setChisoCaiTrang(item.getChiso());
        nhanVat.setHanSuDungCaiTrang(item.getHanSuDung());
        nhanVat.setHanhTinhCaiTrang(item.getHanhtinh());
        nhanVat.setSucManhYeuCauCaiTrang(item.getSucManhYeuCau());
        if (laCaiTrangMoi) {
            veHUD.avatardangmac = nhanVat.getNhanvat() + "_base";
            NhanVatXuLy.setDangMacCaiTrang(true);
            NhanVatXuLy.setDangMacAvatar(false);
        } else {
            veHUD.avatardangmac = item.getId(); // Đây là avatar, cần lưu ID để load lại đúng sau này
            NhanVatXuLy.setDangMacAvatar(true);
            NhanVatXuLy.setDangMacCaiTrang(false);
        }
        // 3. Load config cải trang mới
        if (!veHUD.dangHopThe) {
            NhanVatCauHinh c2 = laCaiTrangMoi
                ? veHUD.Doicaitrang(item.getId())
                : veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), item.getId(), veHUD.aodangmac, veHUD.quandangmac);
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );

            veHUD.texAvt = new Texture(nhanVat.doiavatar());
        }
        tangchiso(item.getChiso());

        // 4. Thay vào hành trang
        danhSach.set(indexx, caiTrangCu);
    }

    private void macCaiTrangMoiDeTu(Item item, int indexx, ArrayList<Item> danhSach, boolean caiTrangDangMac, boolean laCaiTrangMoi) {
        // 1. Lưu cải trang cũ
        String idCu = duLieuNguoiChoi.deTu.getIdCaiTrang();
        String tenCu = duLieuNguoiChoi.deTu.getTenCaiTrang();
        String motacu = duLieuNguoiChoi.deTu.getMoTaCaiTrang();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoCaiTrang();
        float hansudung = duLieuNguoiChoi.deTu.getHanSuDungCaiTrang();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhCaiTrang();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauCaiTrang();
        LoaiItem loaiCu = caiTrangDangMac ? LoaiItem.CAITRANG : LoaiItem.AVATAR;

        Item caiTrangCu = new Item(idCu, tenCu, loaiCu, veHUD.iconctDeTu, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,hansudung);
        giamchisoDeTu(chisocu);

        // 2. Gán cải trang mới
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(item,5);
        veHUD.iconctDeTu = item.getTexture();
        duLieuNguoiChoi.deTu.setIdCaiTrang(item.getId());
        duLieuNguoiChoi.deTu.setTenCaiTrang(item.getTenItem());
        duLieuNguoiChoi.deTu.setMoTaCaiTrang(item.getMoTa());
        duLieuNguoiChoi.deTu.setChisoCaiTrang(item.getChiso());
        duLieuNguoiChoi.deTu.setHanSuDungCaiTrang(item.getHanSuDung());
        duLieuNguoiChoi.deTu.setHanhTinhCaiTrang(item.getHanhtinh());
        duLieuNguoiChoi.deTu.setSucManhYeuCauCaiTrang(item.getSucManhYeuCau());
        if (laCaiTrangMoi) {
            duLieuNguoiChoi.deTu.setAvtDangMac(duLieuNguoiChoi.deTu.getHanhtinh() + "_base");
        } else {
            duLieuNguoiChoi.deTu.setAvtDangMac(item.getId());
        }
        // 3. Load config cải trang mới
        DeTuCauHinh c2 = laCaiTrangMoi
            ? veHUD.DoicaitrangDeTu(item.getId())
            : veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), item.getId(), veHUD.aodetudangmac, veHUD.quandetudangmac);
        duLieuNguoiChoi.deTu.fixCaiTrang(
            c2.dau_dung_de_tu, c2.dau_chay_de_tu,
            c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
            c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
            c2.than_bay_de_tu, c2.chan_bay_de_tu,
            c2.lechMapDeTu
        );

        tangchisoDeTu(item.getChiso());

        // 4. Thay vào hành trang
        danhSach.set(indexx, caiTrangCu);
    }

    public void goCaiTrang(boolean laCaiTrang,boolean vut) {
        if (veHUD.iconct == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,5);
        veHUD.avatardangmac = nhanVat.getNhanvat()+"_base";
        String idCu = nhanVat.getIdCaiTrang();
        String tenCu = nhanVat.getTenCaiTrang();
        String motacu = nhanVat.getMoTaCaiTrang();
        int[] chisocu = nhanVat.getChisoCaiTrang();
        float hansudung = nhanVat.getHanSuDungCaiTrang();
        String hanhtinhcu = nhanVat.getHanhTinhCaiTrang();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauCaiTrang();
        LoaiItem loai = laCaiTrang ? LoaiItem.CAITRANG : LoaiItem.AVATAR;

        Item caiTrangCu = new Item(idCu, tenCu, loai, veHUD.iconct, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,hansudung);
        giamchiso(chisocu);
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(caiTrangCu);
        }
        NhanVatXuLy.setDangMacAvatar(false);
        NhanVatXuLy.setDangMacCaiTrang(false);
        if (!veHUD.dangHopThe) {
            NhanVatCauHinh c2 = veHUD.Doi_avt_ao_quan(nhanVat.getHanhtinh(), nhanVat.getNhanvat() + "_base", veHUD.aodangmac, veHUD.quandangmac);
            nhanVat.fixCaiTrang(
                c2.dau_dung, c2.dau_chay,
                c2.than_dung, c2.than_nhay, c2.than_roi, c2.than_chay,
                c2.chan_dung, c2.chan_nhay, c2.chan_roi, c2.chan_chay,
                c2.than_bay, c2.chan_bay,
                c2.lechMap,
                c2.avt
            );
            veHUD.texAvt = new Texture(nhanVat.doiavatar());
        }
        veHUD.iconct = null;
    }

    public void goCaiTrangDeTu(boolean laCaiTrang) {
        if (veHUD.iconctDeTu == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.deTu.setItemVaoHanhTrangDangMac(null,5);
        duLieuNguoiChoi.deTu.setAvtDangMac(duLieuNguoiChoi.deTu.getHanhtinh()+"_base");
        String idCu = duLieuNguoiChoi.deTu.getIdCaiTrang();
        String tenCu = duLieuNguoiChoi.deTu.getTenCaiTrang();
        String motacu = duLieuNguoiChoi.deTu.getMoTaCaiTrang();
        int[] chisocu = duLieuNguoiChoi.deTu.getChisoCaiTrang();
        float hansudung = duLieuNguoiChoi.deTu.getHanSuDungCaiTrang();
        String hanhtinhcu = duLieuNguoiChoi.deTu.getHanhTinhCaiTrang();
        long sucmanhyeucaucu = duLieuNguoiChoi.deTu.getSucManhYeuCauCaiTrang();
        LoaiItem loai = laCaiTrang ? LoaiItem.CAITRANG : LoaiItem.AVATAR;

        Item caiTrangCu = new Item(idCu, tenCu, loai, veHUD.iconctDeTu, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,hansudung);
        giamchisoDeTu(chisocu);
        duLieuNguoiChoi.themItemVaoHanhTrang(caiTrangCu);

        DeTuCauHinh c2 = veHUD.Doi_avt_ao_quan_DeTu(duLieuNguoiChoi.deTu.getHanhtinh(), duLieuNguoiChoi.deTu.getHanhtinh()+"_base", veHUD.aodetudangmac, veHUD.quandetudangmac);
        duLieuNguoiChoi.deTu.fixCaiTrang(
            c2.dau_dung_de_tu, c2.dau_chay_de_tu,
            c2.than_dung_de_tu, c2.than_nhay_de_tu, c2.than_roi_de_tu, c2.than_chay_de_tu,
            c2.chan_dung_de_tu, c2.chan_nhay_de_tu, c2.chan_roi_de_tu, c2.chan_chay_de_tu,
            c2.than_bay_de_tu, c2.chan_bay_de_tu,
            c2.lechMapDeTu
        );
        veHUD.iconctDeTu = null;
    }

    private void macGiapLuyenTapMoi(Item item, int indexx, ArrayList<Item> danhSach){
        String idCu = nhanVat.getIdGiapLuyenTap();
        String tenCu = nhanVat.getTenGiapLuyenTap();
        String motacu = nhanVat.getMoTaGiapLuyenTap();
        int[] chisocu = nhanVat.getChisoGiapLuyenTap();
        int sosaocu = nhanVat.getSoSaoGiapLuyenTap();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGlt();
        float hansudung = nhanVat.getHanSuDungGiapLuyenTap();
        String hanhtinhcu = nhanVat.getHanhTinhGiapLuyenTap();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGiapLuyenTap();
        LoaiItem loaiCu = LoaiItem.GIAPLUYENTAP;
        Item giapLuyenTapCu = new Item(idCu, tenCu, loaiCu, veHUD.giaplt, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,sosaocu,sosaocuonghoacu,0,hansudung);
        giamchiso(chisocu);
        veHUD.giaplt = item.getTexture();
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,6);
        nhanVat.setIdGiapLuyenTap(item.getId());
        nhanVat.setTenGiapLuyenTap(item.getTenItem());
        nhanVat.setMoTaGiapLuyenTap(item.getMoTa());
        nhanVat.setChisoGiapLuyenTap(item.getChiso());
        nhanVat.setSoSaoGiapLuyenTap(item.getSoSaoPhaLe());
        nhanVat.setSoSaoCuongHoaGlt(item.getSoSaoPhaLeCuongHoa());
        nhanVat.setHanSuDungGiapLuyenTap(item.getHanSuDung());
        nhanVat.setHanhTinhGiapLuyenTap(item.getHanhtinh());
        nhanVat.setSucManhYeuCauGiapLuyenTap(item.getSucManhYeuCau());
        tangchiso(item.getChiso());
        danhSach.set(indexx, giapLuyenTapCu);
        veHUD.dangMacGiapLuyenTap = true;
        duLieuNguoiChoi.dangMacGlt(true);
    }


    public void goGiapLuyenTap(boolean vut) {
        if (veHUD.giaplt == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,6);
        String idCu = nhanVat.getIdGiapLuyenTap();
        String tenCu = nhanVat.getTenGiapLuyenTap();
        String motacu = nhanVat.getMoTaGiapLuyenTap();
        int[] chisocu = nhanVat.getChisoGiapLuyenTap();
        int sosaocu = nhanVat.getSoSaoGiapLuyenTap();
        int sosaocuonghoacu = nhanVat.getSoSaoCuongHoaGlt();
        float hansudung = nhanVat.getHanSuDungGiapLuyenTap();
        String hanhtinhcu = nhanVat.getHanhTinhGiapLuyenTap();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauGiapLuyenTap();
        LoaiItem loaiCu = LoaiItem.GIAPLUYENTAP;
        Item giapLuyenTapCu = new Item(idCu, tenCu, loaiCu, veHUD.giaplt, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,sosaocu,sosaocuonghoacu,0,hansudung);
        giamchiso(chisocu);
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(giapLuyenTapCu);
        }
        veHUD.giaplt = null;
        veHUD.dangMacGiapLuyenTap = false;
        duLieuNguoiChoi.dangMacGlt(false);
        veHUD.itemGiapLuyenTapVuaCoi = giapLuyenTapCu;
    }

    private void macVanBayMoi(Item item, int indexx, ArrayList<Item> danhSach){
//        if (!veHUD.vanBayDau) {
//            String idCu = nhanVat.getIdVanBay();
//            String tenCu = nhanVat.getTenVanBay();
//            String motacu = nhanVat.getMoTaVanBay();
//            int[] chisocu = nhanVat.getChisoVanBay();
//            String hanhtinhcu = nhanVat.getHanhTinhVanBay();
//            long sucmanhyeucaucu = nhanVat.getSucManhYeuCauVanBay();
//            LoaiItem loaiCu = LoaiItem.VANBAY;
//            Item vanBayCu = new Item(idCu, tenCu, loaiCu, veHUD.vanbay, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,-1);
//            danhSach.set(indexx, vanBayCu);
//        } else {
//            danhSach.set(indexx, duLieuNguoiChoi.getHanhTrangDangMac().get(7));
//            veHUD.vanBayDau = false;
//        }
        String idCu = nhanVat.getIdVanBay();
        String tenCu = nhanVat.getTenVanBay();
        String motacu = nhanVat.getMoTaVanBay();
        int[] chisocu = nhanVat.getChisoVanBay();
        String hanhtinhcu = nhanVat.getHanhTinhVanBay();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauVanBay();
        LoaiItem loaiCu = LoaiItem.VANBAY;
        Item vanBayCu = new Item(idCu, tenCu, loaiCu, veHUD.vanbay, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,-1);
        danhSach.set(indexx, vanBayCu);
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(item,7);
        veHUD.vanbay = item.getTexture();
        nhanVat.setIdVanBay(item.getId());
        nhanVat.setTenVanBay(item.getTenItem());
        nhanVat.setMoTaVanBay(item.getMoTa());
        nhanVat.setChisoVanBay(item.getChiso());
        nhanVat.setHanhTinhVanBay(item.getHanhtinh());
        nhanVat.setSucManhYeuCauVanBay(item.getSucManhYeuCau());
        nhanVat.doiVanBay(item.getId());
    }

    public void goVanBay(boolean vut) {
        if (veHUD.vanbay == null) return; // Không mặc gì thì không gỡ
        duLieuNguoiChoi.setItemVaoHanhTrangDangMac(null,7);
        String idCu = nhanVat.getIdVanBay();
        String tenCu = nhanVat.getTenVanBay();
        String motacu = nhanVat.getMoTaVanBay();
        int[] chisocu = nhanVat.getChisoVanBay();
        String hanhtinhcu = nhanVat.getHanhTinhVanBay();
        long sucmanhyeucaucu = nhanVat.getSucManhYeuCauVanBay();
        LoaiItem loaiCu = LoaiItem.VANBAY;
        Item vanBayCu = new Item(idCu, tenCu, loaiCu, veHUD.vanbay, motacu, 1, chisocu,hanhtinhcu,sucmanhyeucaucu, null,0,0,0,-1);
        giamchiso(chisocu);
        if (!vut) {
            duLieuNguoiChoi.themItemVaoHanhTrang(vanBayCu);
        }
        veHUD.vanbay = null;
        nhanVat.dangMangVanBay = false;
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
