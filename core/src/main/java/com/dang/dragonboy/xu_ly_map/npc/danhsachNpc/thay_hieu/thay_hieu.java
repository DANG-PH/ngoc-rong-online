package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.thay_hieu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.a_PHAN_LOAI_NPC.NPC_CUA_HANG;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.ItemGia;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.LoaiTien;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.TrangThaiChucNang_admin_thanhle;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.renderUInpc;

import java.util.*;

public class thay_hieu extends renderUInpc {
    public TrangThaiChucNang_PHA_LE_thay_hieu trangThaiPhaLe;
    public TrangThaiChucNang_thay_hieu trangThai = TrangThaiChucNang_thay_hieu.NONE;
    public ArrayList<Item> danhSachItemCuongHoa = new ArrayList<>();
    public int nutChucNangDangChon = -1;
    public float timeClickNut = 0f;

    public int nutDuocChonHanhTrangTrai = -1;
    public float timeChoHanhTrangTrai = 0f;

    public int nutDuocChonHanhTrangPhai = -1;
    public float timeChoHanhTrangPhai = 0f;

    public int indexItemDuocChon = -1;

    public float timeClickNutPhaLeHoaTrangBi = 0f;

    public thay_hieu(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        super(npc, veHUD, duLieuNguoiChoi, nhanVat);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderChung(batch);
        renderNut(batch);
        renderChucNangPhaLeTrangBi(batch);
    }

    @Override
    public void capNhat() {
        float delta = Gdx.graphics.getDeltaTime();
        capNhatClickNut(delta);
        capNhatTimeHanhTrangTrai(delta);
        capNhatTimeHanhTrangPhai(delta);

        capNhatClickNutPhaLeHoaTrangBi(delta);
    }

    public void renderChung(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_thay_hieu.NONE || trangThai == TrangThaiChucNang_thay_hieu.CHUC_NANG_PHA_LE || trangThai == TrangThaiChucNang_thay_hieu.CHUC_NANG_CHUYEN_HOA)) return;
        if (veHUD.dangHienPopupNhanVatPhai) return;

        String text = "";
        switch (trangThai) {
            case NONE -> text = npc.getLoiThoaiTrong()[0];
            case CHUC_NANG_PHA_LE -> text = npc.getLoiThoaiTrong()[1];
            case CHUC_NANG_CHUYEN_HOA -> text = npc.getLoiThoaiTrong()[2];
            default -> text = null;
        }

        veHUD.layout.setText(
            veHUD.fontMotaHanhTrang,
            text,
            veHUD.fontMotaHanhTrang.getColor(),
            550,
            Align.center,
            true
        );

        float daoDong = (float) Math.sin(nhanVat.thoiGianTichLuy) * 1.3f;
        batch.draw(npc.taiAnh.avtNpc,(Gdx.graphics.getWidth() - 600) / 2f+30,120+veHUD.layout.height+35*2+daoDong,npc.taiAnh.avtNpc.getWidth()*0.5f,npc.taiAnh.avtNpc.getHeight()*0.5f);
        batch.end();
        veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        veHUD.shapeRenderer.setColor(1, 1, 1, 1);
        veHUD.shapeRenderer.rect((Gdx.graphics.getWidth() - 600) / 2f, 120, 600, veHUD.layout.height+35*2);
        veHUD.shapeRenderer.end();
        veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        veHUD.shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++) {
            veHUD.shapeRenderer.rect((Gdx.graphics.getWidth() - 600) / 2f - i, 120 - i, 600 + i * 2, veHUD.layout.height+35*2 + i * 2);
        }
        veHUD.shapeRenderer.end();
        batch.begin();
        veHUD.fontMotaHanhTrang.draw(batch,veHUD.layout,(Gdx.graphics.getWidth() - 600) / 2f+25,120+ veHUD.layout.height+35);
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

        int soNut = textDung.length;

        for (int i = 0; i < soNut; i++) {
            float nutX = (Gdx.graphics.getWidth()-(soNut-1)*120-114)/2f + i * 120;
            float nutY = 120 - 115;
            if (nutChucNangDangChon == i) {
                Texture nutVe = timeClickNut > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                batch.draw(nutVe, nutX, nutY, 114, 114);
            } else {
                batch.draw(veHUD.nutvuong, nutX, nutY, 114, 114);
            }

            veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            veHUD.layout.setText(
                veHUD.font,
                textDung[i],
                veHUD.font.getColor(),
                100,
                Align.center,
                true
            );
            veHUD.font.draw(batch,veHUD.layout,nutX+7f,5+(114+veHUD.layout.height)/2f);
        }
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
                        danhSachItemCuongHoa.remove(veHUD.itemm);
                        duLieuNguoiChoi.themItemVaoHanhTrang(veHUD.itemm);
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
                        if (!danhSachItemCuongHoa.isEmpty()) loi = "Chỉ có thể nâng cấp 1 vật phẩm cùng lúc";
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
                            danhSachItemCuongHoa.add(veHUD.itemm);
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
                if (danhSachItemCuongHoa.isEmpty()) loi = "Vui lòng chọn vật phẩm";
                if (danhSachItemCuongHoa.get(0).getSoSaoPhaLe() >= 7) loi = "Số sao pha lê đã đạt cấp tối đa";
                if (loi == null) {
                } else {
                    veHUD.setTinNhanPet(loi,2f);
                }
            }
        }
    }

    public void renderChucNangPhaLeTrangBi(SpriteBatch batch) {
        if (trangThaiPhaLe != TrangThaiChucNang_PHA_LE_thay_hieu.PHA_LE_HOA_TRANG_BI || !veHUD.dangHienPopupNhanVatPhai) return;
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

        if (!danhSachItemCuongHoa.isEmpty()) {
            batch.draw(timeClickNutPhaLeHoaTrangBi > 0 ? veHUD.nutclick : veHUD.nutdn, (350 - 140) / 2f, 444-danhSachItemCuongHoa.size()*50-50-2f, 140, 50);
            veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            veHUD.layout.setText(veHUD.fontTenSkill, "Nâng cấp");
            veHUD.fontTenSkill.draw(batch, veHUD.layout, (350 - 140) / 2f + (140 - veHUD.layout.width) / 2f, 444-danhSachItemCuongHoa.size()*50-50-2f+50/2f+veHUD.layout.height/2f);

            NPC_CUA_HANG.render_item(batch, 344, veHUD, danhSachItemCuongHoa, indexItemDuocChon);
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
    }
}
