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
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.renderUInpc;

import java.util.*;

public class thay_hieu extends renderUInpc {
    public TrangThaiChucNang_thay_hieu trangThai = TrangThaiChucNang_thay_hieu.NONE;
    public int nutChucNangDangChon = -1;
    public float timeClickNut = 0f;

    public thay_hieu(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        super(npc, veHUD, duLieuNguoiChoi, nhanVat);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderChung(batch);
        renderNut(batch);
    }

    @Override
    public void capNhat() {
        float delta = Gdx.graphics.getDeltaTime();
        capNhatClickNut(delta);
    }

    public void renderChung(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_thay_hieu.NONE || trangThai == TrangThaiChucNang_thay_hieu.CHUC_NANG_PHA_LE || trangThai == TrangThaiChucNang_thay_hieu.CHUC_NANG_CHUYEN_HOA)) return;

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
}
