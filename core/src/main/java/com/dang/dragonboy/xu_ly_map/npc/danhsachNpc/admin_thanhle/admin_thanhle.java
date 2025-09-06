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
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.renderUInpc;

import java.util.*;

public class admin_thanhle extends renderUInpc {
    public TrangThaiChucNang_admin_thanhle trangThai = TrangThaiChucNang_admin_thanhle.NONE;
    public int nutChucNangDangChon = -1;
    public float timeClickNut = 0f;
    public TrangThaiChucNang_CUA_HANG_admin_thanhle trangThaiCuaHang = TrangThaiChucNang_CUA_HANG_admin_thanhle.AO_QUAN;
    public ArrayList<Item> danhSachItemAoQuan = new ArrayList<>();
    public float TimeChoHienPopup = 0;
    public boolean dangHienPopupThongTin = false;
    public int indexItemDuocChon = -1;
    private Texture nenTrangNga, nenTrangNgaClick ;
    public Item itemDangChon;
    public int nutDuocChonClickMuaDo = -1;
    public float timeChoMuaDo = 0f;

    public admin_thanhle(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        super(npc, veHUD, duLieuNguoiChoi, nhanVat);
        themItemVaoDanhSach();
        nenTrangNga = new Texture("hud/giaodientrong/ochiso.png");
        nenTrangNgaClick = new Texture("hud/giaodientrong/ochisoclick.png");
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
        capNhatTimeChoMuaDo(delta);
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
        if (trangThai != TrangThaiChucNang_admin_thanhle.CUA_HANG) return;
        veHUD.renderHUDPopupNhanVatPhai(batch,npc.taiAnh.avtNpc);

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

        renderAoQuan(batch);
    }

    public void renderAoQuan(SpriteBatch batch) {
        if (trangThaiCuaHang != TrangThaiChucNang_CUA_HANG_admin_thanhle.AO_QUAN || trangThai != TrangThaiChucNang_admin_thanhle.CUA_HANG) return;
        // ô hành trang
        float viewY = 35;
        float viewHeight = 444 - 35;
        int KhoangCachItem = 49;
        GlyphLayout layout = veHUD.layout;

        ArrayList<Item> danhSachItemRuongDo = danhSachItemAoQuan;
        int tongSoRuongDo = danhSachItemAoQuan.size();

        float totalHeightRuongDo = tongSoRuongDo * KhoangCachItem;
        veHUD.maxScrollTrai = Math.max(0, totalHeightRuongDo - viewHeight);

        batch.flush();
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(0, (int) viewY, 350, (int) viewHeight);
        // Vị trí bắt đầu vẽ từ trên xuống
        float startYRuongDo = viewY + viewHeight - KhoangCachItem + veHUD.scrollYTrai;

        for (int i = 0; i < tongSoRuongDo; i++) {
            float y = startYRuongDo - i * KhoangCachItem;
            // Vẽ ô nền
            Texture tex = (indexItemDuocChon == i) ? veHUD.hanh_trang_dang_mac_click : veHUD.hanh_trang_dang_mac;
            Texture texNen = (indexItemDuocChon == i) ? nenTrangNgaClick : nenTrangNga;
            batch.draw(tex, 3, y, 274, 50);
            batch.draw(texNen, 60, y, 287, 50);
            // Nếu có item trong danh sách thì vẽ icon
            if (i < danhSachItemRuongDo.size()) {
                Item item = danhSachItemRuongDo.get(i);
                if (item != null) {
                    if (item.getTexture().getHeight() * 0.5f < 60 && item.getTexture().getWidth()*0.5f < 100f) {
                        batch.draw(item.getTexture(), 3 + (56 - item.getTexture().getWidth() * 0.5f) / 2f, y + (49 - item.getTexture().getHeight() * 0.5f) / 2f, item.getTexture().getWidth() * 0.5f, item.getTexture().getHeight() * 0.5f);
                    } else {
                        batch.draw(item.getTexture(), 3 + (56 - item.getTexture().getWidth() * 0.38f) / 2f, y + (49 - item.getTexture().getHeight() * 0.38f) / 2f, item.getTexture().getWidth() * 0.38f, item.getTexture().getHeight() * 0.38f);
                    }
                    String[] chisoduoccong = {"HP", "KI", "SD", "Chí mạng", "Giáp", "ST Crit", "HP", "KI", "Sức đánh", "HP", "KI", "Sức đánh", "Giảm sát thương"};
                    int kc1 = 0;
                    layout.setText(veHUD.fontMotaSkill, item.getTenItem());
                    veHUD.fontMotaSkill.draw(batch, layout, 3 + 56 + 12, y + 49 - 10);
                    kc1 += layout.width + 5;
                    if (item.getSoCap() > 0) {
                        layout.setText(veHUD.fontMotaSkill, "[+" + item.getSoCap() + "]");
                        veHUD.fontMotaSkill.draw(batch, layout, 3 + 56 + 12 + kc1, y + 49 - 10);
                    }
                    if (item.getSoLuong() > 1) {
                        layout.setText(veHUD.fontsm, item.getSoLuong()+"");
                        veHUD.fontsm.draw(batch, layout, 3 + (70- layout.width)-5f, y + 15f);
                    }
                    if (item.getLoai() == LoaiItem.VANBAY) {
                        layout.setText(veHUD.fontCapSKill, item.getMoTa());
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12, y + 49 - 30);
                    }
                    if (item.getLoai() == LoaiItem.GIAPLUYENTAP) {
                        layout.setText(veHUD.fontCapSKill, "Hiệu lực trong " + (item.getHanSuDung() > 60f ? (int) (item.getHanSuDung() / 60f) + " phút" : (int)(item.getHanSuDung())+" giây"));
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12, y + 49 - 30);
                    }
                    if (item.getLoai() == LoaiItem.CAITRANG || item.getLoai() == LoaiItem.AVATAR) {
                        int kc = 0;
                        int soChiso = 0;
                        for (int j = 6; j <= 12; j++) {
                            if (item.getChiso()[j] > 0) {
                                String prefix = (soChiso == 0) ? "" : ",";
                                layout.setText(veHUD.fontCapSKill, prefix + chisoduoccong[j] + "+" + item.getChiso()[j] + "%");
                                veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12 + kc, y + 49 - 30);
                                kc += layout.width + 1;
                                soChiso++;
                            }
                        }
                    }
                    if (item.getLoai() == LoaiItem.AO) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "Giáp+" + item.getChiso()[4]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.QUAN) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "HP+" + item.getChiso()[9]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.GANG) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "Tấn công+" + item.getChiso()[11]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.GIAY) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "KI+" + item.getChiso()[10]);
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.RADA) {
                        int kc = 0;
                        layout.setText(veHUD.fontCapSKill, "Chí mạng+" + item.getChiso()[3] + "%");
                        veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12, y + 49 - 30);
                        kc += layout.width + 1;
                        if (item.getSetkichhoat() != null) {
                            layout.setText(veHUD.fontCapSKill, ",Set " + item.getSetkichhoat());
                            veHUD.fontCapSKill.draw(batch, layout, 3 + 56 + 12 + kc, y + 49 - 30);
                        }
                    }
                    if (item.getLoai() == LoaiItem.DEOLUNG ||
                        item.getLoai() == LoaiItem.AURA ||
                        item.getLoai() == LoaiItem.HUYHIEU ||
                        item.getLoai() == LoaiItem.BONGTAI ||
                        item.getLoai() == LoaiItem.NANGSKILL ||
                        item.getLoai() == LoaiItem.VE_QUAY_NPC_HAIDANG
                    ) {
                        layout.setText(veHUD.fontCapSKill,item.getMoTa());
                        veHUD.fontCapSKill.draw(batch,layout,3 + 56 + 12, y + 49 - 30);
                    }
                }
            }
        }
        batch.flush();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        batch.end();
        if (veHUD.DangHienPopupThongTin3 && veHUD.TimeChoHienPopup <= 0) {
            veHUD.PopupHanhTrang(veHUD.shapeRenderer, batch, indexItemDuocChon,false);
        }
        batch.begin();
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

    public void capNhatTimeChoMuaDo(float delta) {
        if (timeChoMuaDo > 0) {
            timeChoMuaDo -= delta;
            if (timeChoMuaDo <= 0) {
                timeChoMuaDo = 0;
                switch (nutDuocChonClickMuaDo) {
                    case 1 :
                        veHUD.DangHienPopupThongTin3 = false;
                        veHUD.TimeChoHienPopup = 0;
                        veHUD.dangChonHanhTrangPhai = false;
                        veHUD.dangChonHanhTrangTrai = true;
                        break;
                }
            }
        }
    }

    public void themItemVaoDanhSach() {
        danhSachItemAoQuan.add(new Item(
            "set_cam", "Áo võ kame", LoaiItem.AO,
            new Texture("vatpham/do/traidat/set_cam/ao.png"),
            "Giúp giảm sát thương", 1,
            new int[]{0,0,0,0,10,0,0,0,0,0,0,0,0},
            "traidat", 150000L, null, 0,0, 0, -1
        ));

        danhSachItemAoQuan.add(new Item(
            "set_cam", "Quần võ kame", LoaiItem.QUAN,
            new Texture("vatpham/do/traidat/set_cam/quan.png"),
            "Giúp tăng HP", 1,
            new int[]{0,0,0,0,0,0,0,0,0,5000,0,0,0},
            "traidat", 150000L, null, 0,0, 0, -1
        ));

        danhSachItemAoQuan.add(new Item(
            "set_than_linh", "Găng thần linh", LoaiItem.GANG,
            new Texture("vatpham/do/traidat/set_than_linh/gang.png"),
            "Giúp tăng sức đánh", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,9289,0},
            "traidat", 20_000_000_000L, null, 0,0, 0, -1
        ));

        danhSachItemAoQuan.add(new Item(
            "set_cam", "Giày võ kame", LoaiItem.GIAY,
            new Texture("vatpham/do/traidat/set_cam/giay.png"),
            "Giúp tăng MP", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,200,0,0},
            "traidat", 150000L, null, 0,0, 0, -1
        ));

        danhSachItemAoQuan.add(new Item(
            "rada1", "Rada cấp 1", LoaiItem.RADA,
            new Texture("vatpham/do/rada/rada1.png"),
            "Giúp tăng Chí Mạng", 1,
            new int[]{0,0,0,1,0,0,0,0,0,0,0,0,0},
            "traidat", 15000L, null, 0,0, 0, -1
        ));
        danhSachItemAoQuan.add(new Item(
            "set_cam", "Áo võ kame", LoaiItem.AO,
            new Texture("vatpham/do/traidat/set_cam/ao.png"),
            "Giúp giảm sát thương", 1,
            new int[]{0,0,0,0,10,0,0,0,0,0,0,0,0},
            "traidat", 150000L, null, 0,0, 0, -1
        ));

        danhSachItemAoQuan.add(new Item(
            "set_cam", "Quần võ kame", LoaiItem.QUAN,
            new Texture("vatpham/do/traidat/set_cam/quan.png"),
            "Giúp tăng HP", 1,
            new int[]{0,0,0,0,0,0,0,0,0,5000,0,0,0},
            "traidat", 150000L, null, 0,0, 0, -1
        ));

        danhSachItemAoQuan.add(new Item(
            "set_than_linh", "Găng thần linh", LoaiItem.GANG,
            new Texture("vatpham/do/traidat/set_than_linh/gang.png"),
            "Giúp tăng sức đánh", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,0,9289,0},
            "traidat", 20_000_000_000L, null, 0,0, 0, -1
        ));

        danhSachItemAoQuan.add(new Item(
            "set_cam", "Giày võ kame", LoaiItem.GIAY,
            new Texture("vatpham/do/traidat/set_cam/giay.png"),
            "Giúp tăng MP", 1,
            new int[]{0,0,0,0,0,0,0,0,0,0,200,0,0},
            "traidat", 150000L, null, 0,0, 0, -1
        ));

        danhSachItemAoQuan.add(new Item(
            "rada1", "Rada cấp 1", LoaiItem.RADA,
            new Texture("vatpham/do/rada/rada1.png"),
            "Giúp tăng Chí Mạng", 1,
            new int[]{0,0,0,1,0,0,0,0,0,0,0,0,0},
            "traidat", 15000L, null, 0,0, 0, -1
        ));
    }
}
