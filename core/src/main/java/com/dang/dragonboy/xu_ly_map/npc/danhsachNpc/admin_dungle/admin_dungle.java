package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_dungle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.a_PHAN_LOAI_NPC.NPC_KHUNG_CHUNG;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.renderUInpc;
import com.dang.dragonboy.du_lieu.*;
import com.dang.dragonboy.network.*;
import java.util.*;

public class admin_dungle extends renderUInpc {
    public TrangThaiChucNang_admin_dungle trangThai = TrangThaiChucNang_admin_dungle.NONE;
    public int nutChucNangDangChon = -1;
    public float timeClickNut = 0f;
    public String tinNhanChat = "";
    public int nutDuocChonKhiChat = -1;
    public float timeChoDoiGiftCode = 0f;
    public List<String> danhSachPhanThuong = new ArrayList<>();
    public String footerQuaTanThu = "";

    public int nutChucNangDangChon_nhan_qua_web = -1;
    public float timeClickNut_nhan_qua_web = 0f;

    public admin_dungle(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        super(npc, veHUD, duLieuNguoiChoi, nhanVat);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderCacChucNang(batch);
        renderChucNangDoiGiftCode(batch);
        renderChucNanNhanQuaTuWeb(batch);
        renderChucNangNhanQuaThanhCong(batch);
    }

    @Override
    public void capNhat() {
        float delta = Gdx.graphics.getDeltaTime();
        capNhatClickNut(delta);
        capNhatTimeChoDoiGiftCode(delta);
        capNhatTimeNhanQuaWeb(delta);
    }

    public void renderCacChucNang(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_admin_dungle.NONE)) return;

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

    public void renderChucNangDoiGiftCode(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_admin_dungle.DOI_GIFT_CODE)) return;
        batch.end();
        veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        veHUD.shapeRenderer.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.shapeRenderer.rect((Gdx.graphics.getWidth() - 528) / 2f - 2f, 35 -1f, 528 +4f, 149 +3f);
        veHUD.shapeRenderer.end();
        batch.begin();
        batch.draw(veHUD.khungchat,(Gdx.graphics.getWidth() - 528) / 2f,35 , 528, 149);
        float nX = (Gdx.graphics.getWidth() - 140) / 2f;
        float nutY = 12;
        veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        batch.draw(timeChoDoiGiftCode>0 && nutDuocChonKhiChat==0? veHUD.nutclick : veHUD.nutdn, nX-81, nutY, 140, 48);
        veHUD.layout.setText(veHUD.fontTenSkill, "OK");
        veHUD.fontTenSkill.draw(batch, veHUD.layout, nX-81 + (140 - veHUD.layout.width) / 2f, nutY + 29);
        batch.draw(timeChoDoiGiftCode>0 && nutDuocChonKhiChat==1? veHUD.nutclick : veHUD.nutdn, nX+81, nutY, 140, 48);
        veHUD.layout.setText(veHUD.fontTenSkill, "Đóng");
        veHUD.fontTenSkill.draw(batch, veHUD.layout, nX+81 + (140 - veHUD.layout.width) / 2f, nutY + 29);

        veHUD.fontTenSkill.setColor(0f / 255f, 85f / 255f, 38f / 255f, 1f);
        veHUD.layout.setText(veHUD.fontTenSkill, "Đổi Giftcode");
        veHUD.fontTenSkill.draw(batch, veHUD.layout, (Gdx.graphics.getWidth() - 528) / 2f + 15, 35 + 115);

        // Các thông số
        float khungX = (Gdx.graphics.getWidth() - 528) / 2f + 25;
        float khungY = 35;
        float khungWidth = 465;
        float khungHeight = 68;

        if (tinNhanChat.isEmpty()) {
            veHUD.fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
            veHUD.layout.setText(veHUD.fontText, "Nhập Giftcode");
        } else {
            veHUD.fontText.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1f);
            veHUD.layout.setText(veHUD.fontText, tinNhanChat);
        }
        float textWidth = veHUD.layout.width;
        float offsetX = 0;

        if (textWidth > khungWidth) {
            offsetX = textWidth - khungWidth;
        }
        batch.flush();
        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor((int) khungX, (int) khungY, (int) khungWidth, (int) khungHeight);
        veHUD.fontText.draw(batch, veHUD.layout, khungX - offsetX, khungY + khungHeight );
        batch.flush();
        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
    }

    public void renderChucNangNhanQuaThanhCong(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_admin_dungle.NHAN_QUA_THANH_CONG)) return;
        veHUD.fontTenSkill.getData().markupEnabled = true;
        String text = danhSachPhanThuong.size() == 1 ?"[#C21D11]Chúc mừng bạn đã nhận được phần quà[]\n\n" : "[#C21D11]Chúc mừng bạn đã nhận được những phần quà[]\n\n";
        for (String qua : danhSachPhanThuong) {
            text += "[#6975E9]" + qua + "[]\n";
        }
        text += footerQuaTanThu.isEmpty() ? "[#17BF01]\nHẹn gặp lại![]" : "[#17BF01]\n"+footerQuaTanThu+"[]";
        veHUD.layout.setText(
            veHUD.fontTenSkill,
            text,
            veHUD.fontTenSkill.getColor(),
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
        veHUD.fontTenSkill.draw(batch,veHUD.layout,(Gdx.graphics.getWidth() - 600) / 2f+25,120+ veHUD.layout.height+35);

        float nutX = (Gdx.graphics.getWidth()-114)/2f;
        float nutY = 120 - 115;
        batch.draw(timeClickNut > 0 ? veHUD.nutvuongclick : veHUD.nutvuong, nutX, nutY, 114, 114);

        veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.layout.setText(veHUD.font, "OK");
        veHUD.font.draw(batch, veHUD.layout, nutX + (114 - veHUD.layout.width) / 2f, nutY + 114 - 52);
    }

    public void capNhatClickNut(float delta) {
        if (timeClickNut > 0) {
            timeClickNut -= delta;
            if (timeClickNut <= 0) {
                timeClickNut = 0;
                if (trangThai == TrangThaiChucNang_admin_dungle.NONE) {
                    if (nutChucNangDangChon == 0) {
                        trangThai = TrangThaiChucNang_admin_dungle.DOI_GIFT_CODE;
                    } else if (nutChucNangDangChon == 1) {
                        String loi = null;
                        if (duLieuNguoiChoi.isDaNhanQuaTanThu()) {
                            loi = "Đã nhận quà tân thủ rồi";
                        } else if (!duLieuNguoiChoi.duChoTrongHanhTrang(3)) {
                            loi = "Cần ít nhất 3 chỗ trống";
                        }
                        if (loi == null) {
                            veHUD.themItemTest.nhanQuaTanThu();
                            duLieuNguoiChoi.daNhanQuaTanThu();
                            trangThai = TrangThaiChucNang_admin_dungle.NHAN_QUA_THANH_CONG;
                            danhSachPhanThuong.add("x1 Hộp quà Admin Hải Đăng");
                            danhSachPhanThuong.add("x1 Hộp quà Admin Thành Lê");
                            danhSachPhanThuong.add("x1 Hộp quà Admin Dũng Lê");
                            footerQuaTanThu = "Khởi đầu mới - Hành trình phiêu lưu đang chờ bạn!";
                            veHUD.setTinNhanPet("Bạn vừa nhận x3 quà tân thủ từ admin",2f);
                        } else {
                            veHUD.setTinNhanPet(loi,2f);
                        }
                    } else if (nutChucNangDangChon == 2) {
                        trangThai = TrangThaiChucNang_admin_dungle.NHAN_VAT_PHAM_NAP_WEB;
                    } else if (nutChucNangDangChon == 3) {
                        veHUD.daClickVaoNpc = false;
                    }
                }
                if (trangThai == TrangThaiChucNang_admin_dungle.NHAN_QUA_THANH_CONG) {
                    if (nutChucNangDangChon == 0) {
                        trangThai = TrangThaiChucNang_admin_dungle.NONE;
                        footerQuaTanThu = "";
                        danhSachPhanThuong.clear();
                    }
                }
            }
        }
    }
    public void capNhatTimeChoDoiGiftCode(float delta) {
        if (timeChoDoiGiftCode > 0) {
            timeChoDoiGiftCode -= delta;
            if (timeChoDoiGiftCode <= 0) {
                timeChoDoiGiftCode = 0;
                switch (nutDuocChonKhiChat) {
                    case 0:
                        duLieuNguoiChoi.suDungGiftCode(tinNhanChat,danhSachPhanThuong);
                        if (!danhSachPhanThuong.isEmpty()) {
                            trangThai = TrangThaiChucNang_admin_dungle.NHAN_QUA_THANH_CONG;
                        } else {
                            trangThai = TrangThaiChucNang_admin_dungle.NONE;
                        }
                        break;
                    case 1:
                        trangThai = TrangThaiChucNang_admin_dungle.NONE;
                        break;
                }
                tinNhanChat = "";
                nutDuocChonKhiChat = -1;
            }
        }
    }

    public void renderChucNanNhanQuaTuWeb(SpriteBatch batch) {
        if (trangThai != TrangThaiChucNang_admin_dungle.NHAN_VAT_PHAM_NAP_WEB) return;

        BitmapFont fontVeKhung = veHUD.fontMotaHanhTrang;
        fontVeKhung.getData().markupEnabled = true;
        String text = "Chào bạn, cảm ơn bạn đã ủng hộ chúng tôi";

        NPC_KHUNG_CHUNG.renderKhungNpc(npc,veHUD,nhanVat,batch,text,fontVeKhung);

        String[] textDung = new String[] {"Hiện có\n"+veHUD.formatVangNgoc(duLieuNguoiChoi.getVangNapTuWeb())+"\nvàng","Hiện có\n"+veHUD.formatVangNgoc(duLieuNguoiChoi.getNgocNapTuWeb())+"\nngọc","Hiện có\n"+duLieuNguoiChoi.danhSachVatPhamWeb.size()+"\nvật phẩm","Quay lại"};
        NPC_KHUNG_CHUNG.renderKhungNut(npc,veHUD,batch,textDung,nutChucNangDangChon_nhan_qua_web,timeClickNut_nhan_qua_web);
    }

    public void capNhatTimeNhanQuaWeb(float delta) {
        if (timeClickNut_nhan_qua_web > 0) {
            timeClickNut_nhan_qua_web -= delta;
            if (timeClickNut_nhan_qua_web <= 0) {
                timeClickNut_nhan_qua_web = 0;
                switch (nutChucNangDangChon_nhan_qua_web) {
                    case 0: { // vàng
                        new Thread(() -> {
                            boolean used = ApiService.useVangNapTuWeb(duLieuNguoiChoi.getVangNapTuWeb());
                            long soVangDung = duLieuNguoiChoi.getVangNapTuWeb();
                            Gdx.app.postRunnable(() -> {
                                if (used) {
                                    duLieuNguoiChoi.tangVang(soVangDung); // cộng trực tiếp vào vàng thường
                                    veHUD.setTinNhanPet("Bạn vừa nhận " + veHUD.formatVangNgoc(soVangDung) + " vàng", 2f);
                                    duLieuNguoiChoi.vangNapTuWeb -= soVangDung;
                                }
                                if (!used) {
                                    veHUD.setTinNhanPet("Vui lòng nạp tiền", 2f);
                                }
                            });
                        }).start();
                        break;
                    }
                    case 1: { // ngọc
                        new Thread(() -> {
                            boolean used = ApiService.useNgocNapTuWeb(duLieuNguoiChoi.getNgocNapTuWeb());
                            long soNgocDung = duLieuNguoiChoi.getNgocNapTuWeb();
                            Gdx.app.postRunnable(() -> {
                                if (used) {
                                    duLieuNguoiChoi.tangNgoc(soNgocDung); // cộng trực tiếp vào ngọc thường
                                    veHUD.setTinNhanPet("Bạn vừa nhận " + veHUD.formatVangNgoc(soNgocDung) + " ngọc", 2f);
                                    duLieuNguoiChoi.ngocNapTuWeb -= soNgocDung;
                                }
                                if (!used) {
                                    veHUD.setTinNhanPet("Vui lòng nạp tiền", 2f);
                                }
                            });
                        }).start();
                        break;
                    }
                    case 2:
                        if (duLieuNguoiChoi.danhSachVatPhamWeb.isEmpty()) {
                            veHUD.setTinNhanPet("Không có vật phẩm", 2f);
                            break;
                        }

                        // Lọc chỉ lấy những item mà hành trang còn đủ chỗ
                        List<Integer> dsGuiBatch = new ArrayList<>();
                        for (int itemId : duLieuNguoiChoi.danhSachVatPhamWeb) {
                            if (!duLieuNguoiChoi.duChoTrongHanhTrang(dsGuiBatch.size() + 1)) break;
                            dsGuiBatch.add(itemId);
                        }

                        if (dsGuiBatch.isEmpty()) {
                            veHUD.setTinNhanPet("Hành trang đã đầy", 2f);
                            break;
                        }

                        new Thread(() -> {
                            // Gọi 1 lần duy nhất với toàn bộ list
                            List<Integer> successIds = ApiService.useItemWeb(
                                State_Management.getUserResponse().username,
                                dsGuiBatch
                            );

                            if (successIds != null && !successIds.isEmpty()) {
                                Gdx.app.postRunnable(() -> {
                                    for (int itemId : successIds) {
                                        duLieuNguoiChoi.themItemVaoHanhTrang(
                                            veHUD.themItemTest.themVatPhamWebTheoId(itemId)
                                        );
                                        duLieuNguoiChoi.danhSachVatPhamWeb.remove((Integer) itemId);
                                    }
                                    veHUD.setTinNhanPet("Bạn vừa nhận " + successIds.size() + " quà từ web", 2f);
                                });
                            }
                        }).start();
                        break;
                    case 3:
                        break;
                }
                trangThai = TrangThaiChucNang_admin_dungle.NONE;
            }
        }
    }
}
