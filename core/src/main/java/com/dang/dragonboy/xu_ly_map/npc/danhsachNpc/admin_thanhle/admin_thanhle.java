package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.ItemThuongXuLi;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.network.ApiService;
import com.dang.dragonboy.network.DTO.ShopItemServerData;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.ShopCache;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.a_PHAN_LOAI_NPC.NPC_CUA_HANG;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.renderUInpc;

import java.util.*;

public class admin_thanhle extends renderUInpc {
    public TrangThaiChucNang_admin_thanhle trangThai = TrangThaiChucNang_admin_thanhle.NONE;
    public int nutChucNangDangChon = -1;
    public float timeClickNut = 0f;
    public TrangThaiChucNang_CUA_HANG_admin_thanhle trangThaiCuaHang = TrangThaiChucNang_CUA_HANG_admin_thanhle.AO_QUAN;
    public ArrayList<Item> danhSachItemAoQuan = new ArrayList<>();
    public ArrayList<Item> danhSachItemPhuKien = new ArrayList<>();
    public ArrayList<Item> danhSachItemDacBiet = new ArrayList<>();
    public float TimeChoHienPopup = 0;
    public boolean dangHienPopupThongTin = false;
    public int[] indexItemDuocChon = new int[3];
    {
        for (int i = 0; i < indexItemDuocChon.length; i++) {
            indexItemDuocChon[i] = -1;
        }
    }
    public Item itemDangChon;

    public int nutDuocChonHanhTrangTrai = -1;
    public float timeChoHanhTrangTrai = 0f;

    public int nutDuocChonHanhTrangPhai = -1;
    public float timeChoHanhTrangPhai = 0f;

    public float[] scrollYTrai = new float[3];

    private static final int NPC_BASE_ID = 3;

    public admin_thanhle(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        super(npc, veHUD, duLieuNguoiChoi, nhanVat);
        ShopCache cache = ShopCache.getInstance();
        if (cache.daCo(NPC_BASE_ID)) {
            apDungDuLieuShop(cache.lay(NPC_BASE_ID));
        } else {
            ApiService.layShopCuaNpc(NPC_BASE_ID, data -> {
                cache.luu(NPC_BASE_ID, data);
                Gdx.app.postRunnable(() -> apDungDuLieuShop(data));
            });
        }
    }

    public void apDungDuLieuShop(List<ShopItemServerData> data) {
        danhSachItemAoQuan.clear();
        danhSachItemPhuKien.clear();
        danhSachItemDacBiet.clear();

        for (ShopItemServerData s : data) {
            if (!s.is_active) continue;
            Item item = ItemThuongXuLi.taoItemTuTen(s.ten_item);
            if (item == null) continue;

            switch (s.tab) {
                case "AO_QUAN"  -> danhSachItemAoQuan.add(item);
                case "PHU_KIEN" -> danhSachItemPhuKien.add(item);
                case "DAC_BIET" -> danhSachItemDacBiet.add(item);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        renderCacChucNang(batch);
        renderChucNangCuaHang(batch);
    }

    @Override
    public void capNhat() {
        float delta = Gdx.graphics.getDeltaTime();
        if (!veHUD.dangHienPopupNhanVatPhai && trangThai == TrangThaiChucNang_admin_thanhle.CUA_HANG) {
            trangThai = TrangThaiChucNang_admin_thanhle.NONE;
            trangThaiCuaHang = TrangThaiChucNang_CUA_HANG_admin_thanhle.AO_QUAN;
        }
        TimeChoHienPopup-=Gdx.graphics.getDeltaTime();
        capNhatClickNut(delta);
        capNhatTimeHanhTrangTrai(delta);
        capNhatTimeHanhTrangPhai(delta);
    }

    public void renderCacChucNang(SpriteBatch batch) {
        if (trangThai != TrangThaiChucNang_admin_thanhle.NONE) return;

        float daoDong = (float) Math.sin(nhanVat.thoiGianTichLuy) * 1.3f;
        float doDaiShaperender = 120 * npc.getChucNang().length;
        if (doDaiShaperender < 500 ) doDaiShaperender = 500;
        batch.draw(npc.taiAnh.avtNpc,(Gdx.graphics.getWidth() - doDaiShaperender) / 2f+30,120+60+daoDong,npc.taiAnh.avtNpc.getWidth()*0.5f,npc.taiAnh.avtNpc.getHeight()*0.5f);
        batch.end();
        veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        veHUD.shapeRenderer.setColor(1, 1, 1, 1);
        veHUD.shapeRenderer.rect((Gdx.graphics.getWidth() - doDaiShaperender) / 2f, 120, doDaiShaperender, 60);
        veHUD.shapeRenderer.end();
        veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        veHUD.shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++) {
            veHUD.shapeRenderer.rect((Gdx.graphics.getWidth() - doDaiShaperender) / 2f - i, 120 - i, doDaiShaperender + i * 2, 60 + i * 2);
        }
        veHUD.shapeRenderer.end();
        batch.begin();
        veHUD.layout.setText(veHUD.fontMotaHanhTrang,npc.getLoiThoaiTrong()[0]);
        veHUD.fontMotaHanhTrang.draw(batch,veHUD.layout,(Gdx.graphics.getWidth() - doDaiShaperender) / 2f + (doDaiShaperender-veHUD.layout.width)/2f,120+35);

        int soNut = npc.getChucNang().length;

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
                npc.getChucNang()[i],
                veHUD.font.getColor(),
                100,
                Align.center,
                true
            );
            veHUD.font.draw(batch,veHUD.layout,nutX+7f,5+(114+veHUD.layout.height)/2f);
        }
    }

    public void renderChucNangCuaHang(SpriteBatch batch) {
        if (trangThai != TrangThaiChucNang_admin_thanhle.CUA_HANG || !veHUD.dangHienPopupNhanVatPhai) return;
        veHUD.renderHUDPopupNhanVatPhai(batch,npc.taiAnh.avtNpc);

        veHUD.fontMotaChucNangNpc.setColor(1.0f, 0.956f, 0.863f, 1f);
        veHUD.layout.setText(veHUD.fontMotaChucNangNpc,"Xin chào!\nCậu muốn mua gì?");
        veHUD.fontMotaChucNangNpc.draw(batch, veHUD.layout, 150,565);

        // chuc nang
        String[] TextChucnang = {
            "Áo\nQuần",
            "Phụ\nKiện",
            "Đặc\nBiệt"
        };
        for (int i = 0; i < TextChucnang.length; i++) {
            Texture nutcn = trangThaiCuaHang.ordinal() == i ? veHUD.nutchucnangclick : veHUD.nutchucnang;
            batch.draw(nutcn, (350-TextChucnang.length*80)/2f + i * 82, 450, 80, 52);
            veHUD.layout.setText(
                veHUD.fontChucnang,
                TextChucnang[i],
                veHUD.fontChucnang.getColor(),
                80,
                Align.center,
                true
            );
            veHUD.fontChucnang.draw(batch, veHUD.layout, (350-TextChucnang.length*80)/2f + i * 82 , 450 + 52/2f + veHUD.layout.height/2f);
        }
        ArrayList<Item> ds = null;
        switch (trangThaiCuaHang) {
            case AO_QUAN -> ds = danhSachItemAoQuan;
            case PHU_KIEN -> ds = danhSachItemPhuKien;
            case DAC_BIET -> ds = danhSachItemDacBiet;
        }

        NPC_CUA_HANG.render_item(true, batch,274,veHUD, ds, indexItemDuocChon[trangThaiCuaHang.ordinal()]);

        veHUD.renderHUDThongBaoPopupNhanVatPhai(batch);
    }

    public void capNhatClickNut(float delta) {
        if (timeClickNut > 0) {
            timeClickNut -= delta;
            if (timeClickNut <= 0) {
                timeClickNut = 0;
                if (trangThai == TrangThaiChucNang_admin_thanhle.NONE) {
                    if (nutChucNangDangChon == 1) {
                        veHUD.daClickVaoNpc = false;
                    }
                    if (nutChucNangDangChon == 0) {
                        trangThai = TrangThaiChucNang_admin_thanhle.CUA_HANG;
                        veHUD.dangHienPopupNhanVatPhai = true;
                    }
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
                    case 1 :
                        break;
                    case 0 :
                        String loi = null;
                        Long giaItem = ItemGia.layGiaItem(veHUD.itemm);
                        LoaiTien loaiTien = ItemGia.layLoaiTien(veHUD.itemm);
                        Long loaiTienCanSoSanh = loaiTien == LoaiTien.VANG ? duLieuNguoiChoi.getVang() : duLieuNguoiChoi.getNgoc();
                        if (!duLieuNguoiChoi.duChoTrongHanhTrang(1)) {
                            loi = "Cần ít nhất 1 ô trống trong hành trang";
                        } else if (loaiTienCanSoSanh < giaItem) {
                            loi = "Không đủ "+(loaiTien == LoaiTien.VANG ? "vàng" : "ngọc");
                        }
                        if (loi == null) {
                            if (loaiTien == LoaiTien.VANG) {
                                duLieuNguoiChoi.giamVang(giaItem);
                            } else {
                                duLieuNguoiChoi.giamNgoc(giaItem);
                            }
                            // Thêm item, rollback nếu thất bại
                            boolean thanhCong = duLieuNguoiChoi.themItemVaoHanhTrang(veHUD.itemm);
                            if (!thanhCong) {
                                if (loaiTien == LoaiTien.VANG) {
                                    duLieuNguoiChoi.tangVang(giaItem); // hoàn tiền
                                } else {
                                    duLieuNguoiChoi.tangNgoc(giaItem);
                                }
                                veHUD.setTinNhanPet("Có lỗi khi thêm vật phẩm, giao dịch bị hủy", 2f);
                            } else {
                                veHUD.setTinNhanPet("Mua thành công "+veHUD.itemm.getTenItem(),2f);
                            }
                        } else {
                            veHUD.setTinNhanPet(loi,2f);
                        }
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
                        if (ItemGia.layGiaItem(veHUD.itemm) > 0) veHUD.dangHienThongBao = true;
                        else veHUD.setTinNhanPet("Bạn không thể bán vật phẩm này",2f);
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

    public void banItemHanhTrang() {
        Item item = veHUD.itemm;
        Long giaItem = ItemGia.layGiaItem(item);
        LoaiTien loaiTien = ItemGia.layLoaiTien(item);
        if (loaiTien == LoaiTien.VANG) {
            duLieuNguoiChoi.tangVang(giaItem/4);
        } else {
            duLieuNguoiChoi.tangNgoc(giaItem/4);
        }
        veHUD.setTinNhanPet("Bán thành công "+item.getTenItem()+" với giá\n"+ veHUD.formatVangNgoc(giaItem/4),2f);
    }

    public void themItemVaoDanhSachAoQuan() {
        danhSachItemAoQuan.add(ItemThuongXuLi.taoItemThuong("set_cam",LoaiItem.AO,"traidat"));
        danhSachItemAoQuan.add(ItemThuongXuLi.taoItemThuong("set_cam",LoaiItem.QUAN,"traidat"));
    }

    public void themItemVaoDanhSachPhuKien() {
        danhSachItemPhuKien.add(ItemThuongXuLi.taoItemThuong("set_cam",LoaiItem.GANG,"traidat"));
        danhSachItemPhuKien.add(ItemThuongXuLi.taoItemThuong("set_cam",LoaiItem.GIAY,"traidat"));
        danhSachItemPhuKien.add(ItemThuongXuLi.taoItemThuong("rada1",LoaiItem.RADA,"all"));
    }

    public void themItemVaoDanhSachDacBiet() {
        danhSachItemDacBiet.add(new Item(
            "bongtaic1", "Bông tai Porata", LoaiItem.BONGTAI,
            "vatpham/vatphamgame/bongtai/bongtaic1.png",
            "Sử dụng để hợp thể với đệ tử", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 1500000L, null, 0, 0, 0, -1
        ));
        danhSachItemDacBiet.add(new Item(
            "glt_c1", "Giáp luyện tập cấp 1", LoaiItem.GIAPLUYENTAP,
            "vatpham/vatphamgame/giapluyentap/gltc1.png",
            "Khi mặc vào sẽ tích lũy thời gian luyện tập, khi cởi ra nếu sẽ tăng sức đánh 10% và Crit 15%, ST Crit 30%", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
            "all", 10_000_000L, null, 0, 0, 0, 0
        ));
    }
}
