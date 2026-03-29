package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.thay_hieu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.LoiThoai_ChucNang_Npc;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.a_PHAN_LOAI_NPC.NPC_CUA_HANG;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.a_PHAN_LOAI_NPC.NPC_KHUNG_CHUNG;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.ItemGia;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.LoaiTien;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.TrangThaiChucNang_admin_thanhle;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.renderUInpc;

import java.util.*;

public class thay_hieu extends renderUInpc {
    public TrangThaiChucNang_PHA_LE_thay_hieu trangThaiPhaLe;
    public TrangThaiChucNang_thay_hieu trangThai = TrangThaiChucNang_thay_hieu.NONE;
    public TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu trangThaiNangCap = TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.NONE;

    public int nutChucNangDangChon = -1;
    public float timeClickNut = 0f;

    public int nutDuocChonHanhTrangTrai = -1;
    public float timeChoHanhTrangTrai = 0f;

    public int nutDuocChonHanhTrangPhai = -1;
    public float timeChoHanhTrangPhai = 0f;

    public int indexItemDuocChon = -1;

    public float timeClickNutPhaLeHoaTrangBi = 0f;
    public int nutChucNangDangChon_pha_le_hoa = -1;
    public float timeClickNut_pha_le_hoa = 0f;

    public int[] tiLeNangCapTheoSao = new int[]{30,20,10,5,3,2,1};
    public int[] vangNangCapTheoSao = new int[]{5_000_000,10_000_000,20_000_000,40_000_000,60_000_000,90_000_000,120_000_000};
    public float timeHienManHinhPhaLeHoa = 0f;
    public float timeHienManHinhPhaLeHoaX10 = 0f;
    public String tinNhanPhaLeX10 = "";
    public boolean daRanDom = false;
    public boolean thanhCongNangCap = false;

    public thay_hieu(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        super(npc, veHUD, duLieuNguoiChoi, nhanVat);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderChung(batch);
        renderNut(batch);
        renderChucNangPhaLeTrangBi(batch);
        renderDangNangCap(batch);
    }

    @Override
    public void capNhat() {
        float delta = Gdx.graphics.getDeltaTime();
        capNhatClickNut(delta);
        capNhatTimeHanhTrangTrai(delta);
        capNhatTimeHanhTrangPhai(delta);

        capNhatClickNutPhaLeHoaTrangBi(delta);
        capNhatTimeDangNangCap(delta);
    }

    public void renderChung(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_thay_hieu.NONE || trangThai == TrangThaiChucNang_thay_hieu.CHUC_NANG_PHA_LE || trangThai == TrangThaiChucNang_thay_hieu.CHUC_NANG_CHUYEN_HOA)) return;
        if (veHUD.dangHienPopupNhanVatPhai) return;

        String text = "";
        BitmapFont fontVeKhung = veHUD.fontMotaHanhTrang;
        fontVeKhung.getData().markupEnabled = true;
        switch (trangThai) {
            case NONE -> text = "[#000000]"+npc.getLoiThoaiTrong()[0];
            case CHUC_NANG_PHA_LE -> text = "[#000000]"+npc.getLoiThoaiTrong()[1];
            case CHUC_NANG_CHUYEN_HOA -> text = "[#000000]"+npc.getLoiThoaiTrong()[2];
            default -> text = null;
        }

        NPC_KHUNG_CHUNG.renderKhungNpc(npc,veHUD,nhanVat,batch,text,fontVeKhung);
    }

    public void renderNut(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_thay_hieu.NONE || trangThai == TrangThaiChucNang_thay_hieu.CHUC_NANG_PHA_LE || trangThai == TrangThaiChucNang_thay_hieu.CHUC_NANG_CHUYEN_HOA)) return;
        if (veHUD.dangHienPopupNhanVatPhai) return;

        String[] textDung = new String[0];

        switch (trangThai) {
            case NONE -> textDung = npc.getChucNang();
            case CHUC_NANG_PHA_LE -> textDung = new String[]{
                "Ép sao\ntrang bị", "Pha lê\nhóa\ntrang bị", "Đóng"
            };
            case CHUC_NANG_CHUYEN_HOA -> textDung = new String[]{
                "Chuyển hóa\nDùng vàng", "Chuyển hóa\nDùng ngọc", "Đóng"
            };
        }

        NPC_KHUNG_CHUNG.renderKhungNut(npc,veHUD,batch,textDung,nutChucNangDangChon,timeClickNut);
    }

    public void capNhatClickNut(float delta) {
        if (timeClickNut > 0) {
            timeClickNut -= delta;
            if (timeClickNut <= 0) {
                timeClickNut = 0;
                switch (trangThai) {
                    case NONE :
                        switch (nutChucNangDangChon) {
                            case 0 -> trangThai = TrangThaiChucNang_thay_hieu.CHUC_NANG_PHA_LE;
                            case 1 -> trangThai = TrangThaiChucNang_thay_hieu.CHUC_NANG_CHUYEN_HOA;
                        }
                        break;
                    case CHUC_NANG_PHA_LE:
                        switch (nutChucNangDangChon) {
                            case 0 -> {
                                trangThaiPhaLe = TrangThaiChucNang_PHA_LE_thay_hieu.EP_SAO_TRANG_BI;
                                veHUD.dangHienPopupNhanVatPhai = true;
                            }
                            case 1 -> {
                                trangThaiPhaLe = TrangThaiChucNang_PHA_LE_thay_hieu.PHA_LE_HOA_TRANG_BI;
                                veHUD.dangHienPopupNhanVatPhai = true;
                            }
                            case 2 -> trangThai = TrangThaiChucNang_thay_hieu.NONE;
                        }
                        break;
                    case CHUC_NANG_CHUYEN_HOA:
                        switch (nutChucNangDangChon) {
                            case 2 -> trangThai = TrangThaiChucNang_thay_hieu.NONE;
                        }
                        break;
                    default:break;
                }
            }
        }
    }

    public void capNhatTimeHanhTrangTrai(float delta) {
        if (timeChoHanhTrangTrai > 0) {
            timeChoHanhTrangTrai -= delta;
            if (timeChoHanhTrangTrai <= 0) {
                timeChoHanhTrangTrai = 0;
                switch (nutDuocChonHanhTrangTrai) {
                    case 0 :
                        duLieuNguoiChoi.danhSachItemCuongHoa.remove(veHUD.itemm);
                        duLieuNguoiChoi.themItemVaoHanhTrangNoSave(veHUD.itemm);
                        veHUD.hangTrangDangChon = -1;
                        indexItemDuocChon = -1;
                        break;
                }
                veHUD.DangHienPopupThongTin3 = false;
                veHUD.TimeChoHienPopup = 0;
                veHUD.dangChonHanhTrangPhai = false;
                veHUD.dangChonHanhTrangTrai = true;
            }
        }
    }

    public void capNhatTimeHanhTrangPhai(float delta) {
        if (timeChoHanhTrangPhai > 0) {
            timeChoHanhTrangPhai -= delta;
            if (timeChoHanhTrangPhai <= 0) {
                timeChoHanhTrangPhai = 0;
                switch (nutDuocChonHanhTrangPhai) {
                    case 1 :
                        String loi = null;
                        if (!duLieuNguoiChoi.danhSachItemCuongHoa.isEmpty()) loi = "Chỉ có thể nâng cấp 1 vật phẩm cùng lúc";
                        if (veHUD.itemm.getLoai() != LoaiItem.AO &&
                            veHUD.itemm.getLoai() != LoaiItem.QUAN &&
                            veHUD.itemm.getLoai() != LoaiItem.GANG &&
                            veHUD.itemm.getLoai() != LoaiItem.GIAY &&
                            veHUD.itemm.getLoai() != LoaiItem.RADA &&
                            veHUD.itemm.getLoai() != LoaiItem.GIAPLUYENTAP) {
                            loi = "Vật phẩm không thể nâng cấp";
                        }
                        if (!duLieuNguoiChoi.getHanhTrang().contains(veHUD.itemm)) loi = "Bạn cần gỡ đồ ra để nâng cấp";
                        if (loi == null) {
                            duLieuNguoiChoi.getHanhTrang().remove(veHUD.itemm);
                            duLieuNguoiChoi.danhSachItemCuongHoa.add(veHUD.itemm);
                            veHUD.hangTrangDangChon = -1;
                            indexItemDuocChon = -1;
                        } else {
                            veHUD.setTinNhanPet(loi,2f);
                        }
                        break;
                    case 0 :
                        veHUD.xuLyDungItem();
                }
                veHUD.DangHienPopupThongTin1 = false;
                veHUD.TimeChoHienPopup = 0;
                veHUD.dangChonHanhTrangPhai = false;
                veHUD.dangChonHanhTrangTrai = true;
            }
        }
    }

    public void capNhatClickNutPhaLeHoaTrangBi(float delta) {
        if (timeClickNutPhaLeHoaTrangBi > 0) {
            timeClickNutPhaLeHoaTrangBi -= delta;
            if (timeClickNutPhaLeHoaTrangBi<=0) {
                timeClickNutPhaLeHoaTrangBi = 0;
                String loi = null;
                if (duLieuNguoiChoi.danhSachItemCuongHoa.isEmpty()) loi = "Vui lòng chọn vật phẩm";
                if (duLieuNguoiChoi.danhSachItemCuongHoa.get(0).getSoSaoPhaLe() >= 7) loi = "Số sao pha lê đã đạt cấp tối đa";
                if (loi == null) {
                    trangThaiNangCap = TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.POPUP_XAC_NHAN;
                } else {
                    veHUD.setTinNhanPet(loi,2f);
                }
            }
        }
        if (timeClickNut_pha_le_hoa > 0) {
            timeClickNut_pha_le_hoa -= delta;
            if (timeClickNut_pha_le_hoa <= 0) {
                int soLanCuongHoa = 0;
                if (nutChucNangDangChon_pha_le_hoa == 1) soLanCuongHoa = 10;
                else if (nutChucNangDangChon_pha_le_hoa == 2) soLanCuongHoa = 100;
                if (nutChucNangDangChon_pha_le_hoa == 0) {
                    Item item = duLieuNguoiChoi.danhSachItemCuongHoa.get(0);
                    String loi = null;
                    if (duLieuNguoiChoi.getVang() < vangNangCapTheoSao[item.getSoSaoPhaLe()])
                        loi = "Bạn không đủ vàng";
                    if (duLieuNguoiChoi.getNgoc() < item.getSoSaoPhaLe() + 1) loi = "Bạn không đủ ngọc";
                    if (loi == null) {
                        timeHienManHinhPhaLeHoa = 3.5f;
                        trangThaiNangCap = TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.DANG_NANG_CAP;
                        duLieuNguoiChoi.giamVang(vangNangCapTheoSao[item.getSoSaoPhaLe()]);
                        duLieuNguoiChoi.giamNgoc(item.getSoSaoPhaLe() + 1);
                    } else {
                        veHUD.setTinNhanPet(loi, 2f);
                    }
                }
                if (nutChucNangDangChon_pha_le_hoa == 1 || nutChucNangDangChon_pha_le_hoa == 2) {
                    Item item = duLieuNguoiChoi.danhSachItemCuongHoa.get(0);
                    String loi = null;
                    if (duLieuNguoiChoi.getVang() < vangNangCapTheoSao[item.getSoSaoPhaLe()])
                        loi = "Bạn không đủ vàng";
                    if (duLieuNguoiChoi.getNgoc() < item.getSoSaoPhaLe() + 1) loi = "Bạn không đủ ngọc";
                    if (loi == null) {
                        boolean thoatNangCap = false;
                        for (int i = 0; i < soLanCuongHoa; i++) {
                            if (duLieuNguoiChoi.getVang() < vangNangCapTheoSao[item.getSoSaoPhaLe()]) {
                                veHUD.setTinNhanPet("Bạn không đủ vàng", 2f);
                                break;
                            }
                            if (duLieuNguoiChoi.getNgoc() < item.getSoSaoPhaLe() + 1) {
                                veHUD.setTinNhanPet("Bạn không đủ ngọc", 2f);
                                break;
                            }

                            if (ranDomTangSaoPhaLeTrucTiep(item)) {
                                thoatNangCap = true;
                                item.tangSoSaoPhaLeCuongHoa();
                                item.tangChiSo(6, 5);
                                break;
                            }
                        }
                        timeHienManHinhPhaLeHoaX10 = 3.5f;
                        tinNhanPhaLeX10 = thoatNangCap ? "Chúc mừng em nhé" : "Chúc em may mắn lần sau";
                        trangThaiNangCap = TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.DANG_NANG_CAP;
                    } else {
                        veHUD.setTinNhanPet(loi, 2f);
                    }
                }
                if (nutChucNangDangChon_pha_le_hoa == 3) {
                    trangThaiNangCap = TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.NONE;
                }
            }
        }
    }

    public void capNhatTimeDangNangCap(float delta) {
        if (timeHienManHinhPhaLeHoa > 0) {
            timeHienManHinhPhaLeHoa -= delta;
            if (timeHienManHinhPhaLeHoa <= 2) {
                if (!daRanDom) {
                    Item item = duLieuNguoiChoi.danhSachItemCuongHoa.get(0);
                    int so = MathUtils.random(1, 100);
                    boolean nangCapThanhCong = so <= tiLeNangCapTheoSao[item.getSoSaoPhaLe()];
                    npc.dangHienTinNhanChat = true;
                    npc.timeHienTinNhan = 2f;
                    npc.tinNhanChat = nangCapThanhCong ? "Chúc mừng em nhé" : "Chúc em may mắn lần sau";
                    daRanDom = true;
                    thanhCongNangCap = nangCapThanhCong;
                }
                if (timeHienManHinhPhaLeHoa <= 0) {
                    timeHienManHinhPhaLeHoa = 0;
                    trangThaiNangCap = TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.NONE;
                    if (thanhCongNangCap) {
                        duLieuNguoiChoi.danhSachItemCuongHoa.get(0).tangSoSaoPhaLe();
                    }
                    daRanDom = false;
                }
            }
        }
        if (timeHienManHinhPhaLeHoaX10 > 0) {
            timeHienManHinhPhaLeHoaX10 -= delta;
            if (timeHienManHinhPhaLeHoaX10 <= 2) {
                if (!daRanDom) {
                    npc.dangHienTinNhanChat = true;
                    npc.timeHienTinNhan = 2f;
                    npc.tinNhanChat = tinNhanPhaLeX10;
                    daRanDom = true;
                }
                if (timeHienManHinhPhaLeHoaX10 <= 0) {
                    timeHienManHinhPhaLeHoaX10 = 0;
                    trangThaiNangCap = TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.NONE;
                    daRanDom = false;
                }
            }
        }
    }

    public void renderChucNangPhaLeTrangBi(SpriteBatch batch) {
        if (trangThaiPhaLe != TrangThaiChucNang_PHA_LE_thay_hieu.PHA_LE_HOA_TRANG_BI || !veHUD.dangHienPopupNhanVatPhai) return;
        if (trangThaiNangCap == TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.DANG_NANG_CAP) return;

        veHUD.renderHUDPopupNhanVatPhai(batch,npc.taiAnh.avtNpc);

        veHUD.layout.setText(
            veHUD.fontMotaChucNangNpc,
            "Thầy sẽ nâng cấp\ncho trang bị của em\ntrở thành trang bị pha lê",
            new Color(1.0f, 0.956f, 0.863f, 1f),
            200,
            Align.center,
            true
        );
        veHUD.fontMotaChucNangNpc.draw(batch, veHUD.layout, 120,504+(610-504)/2f+veHUD.layout.height/2f);

        veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.layout.setText(veHUD.fontTenSkill, "Nâng cấp");
        veHUD.fontTenSkill.draw(batch, veHUD.layout, (350 - veHUD.layout.width) / 2f, 444 + 35);

        if (!duLieuNguoiChoi.danhSachItemCuongHoa.isEmpty()) {
            batch.draw(timeClickNutPhaLeHoaTrangBi > 0 ? veHUD.nutclick : veHUD.nutdn, (350 - 140) / 2f, 444-duLieuNguoiChoi.danhSachItemCuongHoa.size()*50-50-2f, 140, 50);
            veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            veHUD.layout.setText(veHUD.fontTenSkill, "Nâng cấp");
            veHUD.fontTenSkill.draw(batch, veHUD.layout, (350 - 140) / 2f + (140 - veHUD.layout.width) / 2f, 444-duLieuNguoiChoi.danhSachItemCuongHoa.size()*50-50-2f+50/2f+veHUD.layout.height/2f);

            NPC_CUA_HANG.render_item(true, batch, 344, veHUD, duLieuNguoiChoi.danhSachItemCuongHoa, indexItemDuocChon);
        } else {
            veHUD.layout.setText(
                veHUD.fontTenSkill,
                "Vào hành trang\n\nChọn trang bị\n\n(Áo, quần, găng, giày hoặc rađa)\n\nSau đó chọn 'Nâng cấp'",
                new Color(83 / 255f, 41 / 255f, 5 / 255f, 1),
                300,
                Align.center,
                true
            );
            veHUD.fontTenSkill.draw(batch, veHUD.layout, 25,35+(444-35)/2f+veHUD.layout.height/2f);

        }
        veHUD.renderHUDThongBaoPopupNhanVatPhai(batch);

        if (trangThaiNangCap == TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.POPUP_XAC_NHAN) {
            Item item = duLieuNguoiChoi.danhSachItemCuongHoa.get(0);
            BitmapFont fontVeKhung = veHUD.fontTenSkill;
            fontVeKhung.getData().markupEnabled = true;
            String textPhu = item.getMoTa();
            String maMau = duLieuNguoiChoi.getVang() >= vangNangCapTheoSao[item.getSoSaoPhaLe()] ?"[#6975E9]":"[#C21D11]";
            if (item.getLoai() == LoaiItem.GIAPLUYENTAP) textPhu = "Hiệu lực trong " + (item.getHanSuDung() > 60f ? (int) (item.getHanSuDung() / 60f) + " phút" : (int) item.getHanSuDung() + " giây");
            String text = "[#532905]"+item.getTenItem()+"[]\n[#17BF01]"+textPhu+"[]\n[#17BF01]"+(item.getSoSaoPhaLe()+1)+" Sao Pha Lê"+"[]\n[#6975E9]Tỉ lệ thành công: "+tiLeNangCapTheoSao[item.getSoSaoPhaLe()]+"%[]\n"+maMau+"Cần "+vangNangCapTheoSao[item.getSoSaoPhaLe()]/1000000+" Tr vàng[]";
            NPC_KHUNG_CHUNG.renderKhungNpc(npc,veHUD,nhanVat,batch,text,fontVeKhung);
            String textNut = duLieuNguoiChoi.getVang() >= vangNangCapTheoSao[item.getSoSaoPhaLe()] ?"Nâng cấp\n"+(item.getSoSaoPhaLe()+1)+" ngọc":"Còn thiếu\n"+veHUD.formatVangNgoc(vangNangCapTheoSao[item.getSoSaoPhaLe()]-duLieuNguoiChoi.getVang())+" vàng";
            String[] textDung = new String[] {textNut,"Nâng cấp\nx10 lần","Nâng cấp\nx100 lần","Từ chối"};
            NPC_KHUNG_CHUNG.renderKhungNut(npc,veHUD,batch,textDung,nutChucNangDangChon_pha_le_hoa,timeClickNut_pha_le_hoa);
        }
    }

    public void renderChucNangEpSaoTrangBi(SpriteBatch batch) {
        if (trangThaiPhaLe != TrangThaiChucNang_PHA_LE_thay_hieu.EP_SAO_TRANG_BI || !veHUD.dangHienPopupNhanVatPhai) return;
        if (trangThaiNangCap == TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.DANG_NANG_CAP) return;

        veHUD.renderHUDPopupNhanVatPhai(batch,npc.taiAnh.avtNpc);

        veHUD.renderHUDThongBaoPopupNhanVatPhai(batch);
    }

    public void renderDangNangCap(SpriteBatch batch) {
        if (trangThaiPhaLe != TrangThaiChucNang_PHA_LE_thay_hieu.PHA_LE_HOA_TRANG_BI || trangThaiNangCap != TrangThaiChucNang_PHA_LE_HOA_TRANG_BI_thay_hieu.DANG_NANG_CAP) return;
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        veHUD.shapeRenderer.setColor(0f, 0f, 0f, 0.6f);
        veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        veHUD.shapeRenderer.rect(0,0,1020,610);
        veHUD.shapeRenderer.end();
        batch.setProjectionMatrix(veHUD.camManager.camera.combined);
        batch.begin();
        npc.ve(batch,nhanVat.thoiGianTichLuy);
        npc.timeLauLauChat = 0;
        float flipScale = !npc.flipX ? -1f : 1f;
        float rong = npc.taiAnh.chan.getWidth()*0.5f;
        float xVe = !npc.flipX ? npc.getX() + rong - (rong-duLieuNguoiChoi.danhSachItemCuongHoa.get(0).getTexture().getWidth()/2f)/2f + 3f : npc.getX()+(rong-duLieuNguoiChoi.danhSachItemCuongHoa.get(0).getTexture().getWidth()/2f)/2f - 3f;
        if (timeHienManHinhPhaLeHoa > 2) {
            int tick = (int)(timeHienManHinhPhaLeHoa * 3);
            if (tick % 2 == 0) {
                veHUD.timeGlow = 0.06f;
                veHUD.clickX = npc.getX()+rong/2f;
                veHUD.clickY = npc.getY()+duLieuNguoiChoi.danhSachItemCuongHoa.get(0).getTexture().getHeight()/4f;
            }
        }
        if (timeHienManHinhPhaLeHoaX10 > 2) {
            int tick = (int)(timeHienManHinhPhaLeHoaX10 * 3);
            if (tick % 2 == 0) {
                veHUD.timeGlow = 0.06f;
                veHUD.clickX = npc.getX()+rong/2f;
                veHUD.clickY = npc.getY()+duLieuNguoiChoi.danhSachItemCuongHoa.get(0).getTexture().getHeight()/4f;
            }
        }
        batch.end();
        if (veHUD.timeGlow>0) {
            veHUD.shapeRenderer.setProjectionMatrix(veHUD.camManager.camera.combined);
            veHUD.veGlow.veGlow(veHUD.shapeRenderer,veHUD.clickX,veHUD.clickY,veHUD.timeGlow);
            veHUD.shapeRenderer.setProjectionMatrix(veHUD.camManager.uiCamera.combined);
        }
        batch.begin();
        batch.draw(duLieuNguoiChoi.danhSachItemCuongHoa.get(0).getTexture(),xVe,npc.getY(),duLieuNguoiChoi.danhSachItemCuongHoa.get(0).getTexture().getWidth()/2f * flipScale,duLieuNguoiChoi.danhSachItemCuongHoa.get(0).getTexture().getHeight()/2f);
        batch.setProjectionMatrix(veHUD.camManager.uiCamera.combined);
    }

    public boolean ranDomTangSaoPhaLeTrucTiep(Item item) {
        duLieuNguoiChoi.giamVang(vangNangCapTheoSao[item.getSoSaoPhaLe()]);
        duLieuNguoiChoi.giamNgoc(item.getSoSaoPhaLe() + 1);
        int so = MathUtils.random(1, 100);
        boolean nangCapThanhCong = so <= tiLeNangCapTheoSao[item.getSoSaoPhaLe()];
        if (nangCapThanhCong) {
            duLieuNguoiChoi.danhSachItemCuongHoa.get(0).tangSoSaoPhaLe();
        }
        return nangCapThanhCong;
    }
}
