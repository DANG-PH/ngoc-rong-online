package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.websocket.GameSocket;

import java.util.ArrayList;

public class HUDRongThan {
    private VeHUD veHUD;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    private DuLieuNguoiChoi duLieuNguoiChoi;
    private NhanVat nhanVat;
    public static Texture[] hieuUngRongThan = new Texture[21];
    public static Texture[] luaRongThan = new Texture[2];
    public static Texture[] rongThan = new Texture[9];
    public static Texture[] luaRongThanDen = new Texture[2];
    public static Texture[] rongThanDen = new Texture[9];
    public static Texture[] setRongThanDen = new Texture[5];

    public HUDRongThan(VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        layout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
        this.veHUD = veHUD;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
        this.nhanVat = nhanVat;
        for (int i = 0; i < 21; i++) {
            hieuUngRongThan[i] = new Texture("hieuung/hieuunggame/rong_than/"+(i+1)+".png");
        }
        for (int i = 0; i < 2; i++) {
            luaRongThan[i] = new Texture("hieuung/hieuunggame/rong_than/lua_"+(i+1)+".png");
        }
        for (int i = 0; i < 9; i++) {
            rongThan[i] = new Texture("hieuung/hieuunggame/rong_than/r"+(i+1)+".png");
        }
        for (int i = 0; i < 2; i++) {
            luaRongThanDen[i] = new Texture("hieuung/hieuunggame/rong_than/lua_den_"+(i+1)+".png");
        }
        for (int i = 0; i < 9; i++) {
            rongThanDen[i] = new Texture("hieuung/hieuunggame/rong_than/rd"+(i+1)+".png");
        }
        for (int i = 0; i < 5; i++) {
            setRongThanDen[i] = new Texture("hieuung/hieuunggame/rong_than/set_"+(i+1)+".png");
        }
    }
    public void capNhatThoiGian(float delta) {
        if (veHUD.timeHienRongThan>0) {
            veHUD.timeHienRongThan-=delta;
            if (veHUD.timeHienRongThan<=1f) {
                int tick = (int) (veHUD.timeHienRongThan * 18);
                if (tick % 2 == 0) {
                    veHUD.veNenFlash = true;
                } else {
                    veHUD.veNenFlash = false;
                }
            }
            if (veHUD.timeHienRongThan<=0) {
                veHUD.timeHienRongThan = 0;
                veHUD.veNenFlash = false;
                veHUD.ngocRongUoc = "";
                veHUD.dangHienDieuUocRongThan = false;
                veHUD.daUocRongThan = false;
                GameSocket.guiUocXongRongThan();
            }
        }
    }
    public void chonDieuUocRongThan(SpriteBatch batch) {
        if (veHUD.timeHienRongThan<=0) return;
        float offsetX = 120f;

        if (veHUD.timeHienRongThan<=1) return;
        float timeMax = 300f;
        float step = 0.1f;
        if (veHUD.timeHienRongThan>300f-2.1f) {
            int tick = (int) (veHUD.timeHienRongThan * 18);
            if (tick % 2 == 0) {
                veHUD.veNenFlash = true;
            } else {
                veHUD.veNenFlash = false;
            }
        } else {
            veHUD.veNenFlash = false;
        }
        for (int i = 0; i < 21; i++) {
            float start = timeMax - i*step;
            float end = start - step;
            if (veHUD.timeHienRongThan >= end && veHUD.timeHienRongThan < start) {
                batch.setProjectionMatrix(veHUD.camManager.camera.combined);
                float halfW = hieuUngRongThan[i].getWidth() * 0.5f;
                float halfH = hieuUngRongThan[i].getHeight() * 0.5f;
                batch.draw(hieuUngRongThan[i], nhanVat.getX() + offsetX - halfW / 2f, nhanVat.getY() + 200f - halfH / 2f, halfW,halfH);
                batch.setProjectionMatrix(veHUD.camManager.uiCamera.combined);
            }
        }
        if (veHUD.timeHienRongThan<=300f-2.1f) {
            String[] listDieuUoc = new String[5];
            if (veHUD.ngocRongUoc.equals("3sao")) {
                listDieuUoc = new String[]{"Danh Hiệu Thiếu Nhi","Giàu có \n"+"+50 \n Ngọc","+2 Tr \n Sức mạnh \n và tiềm năng","Giàu có \n +5 Tr \n Vàng","Sức Mạnh \n Đổi Skill 1 \n đệ tử"};
            } else if (veHUD.ngocRongUoc.equals("2sao")) {
                listDieuUoc = new String[]{"Đẹp trai"+"\n"+"nhất"+"\n"+"Vũ trụ","Giàu có \n"+"+300 \n Ngọc","+20 Tr \n Sức mạnh \n và tiềm năng","Giàu có \n +50 Tr \n Vàng","Sức Mạnh \n Đổi Skill 2 và 3 \n đệ tử"};
            } else if (veHUD.ngocRongUoc.equals("1sao")) {
                listDieuUoc = new String[]{"Bông tai Porata \n Đặc Biệt","Giàu có \n"+"+1500 \n Ngọc","+200 Tr \n Sức mạnh \n và tiềm năng","Giàu có \n +500 Tr \n Vàng","x1 Ngọc Rồng Đen Ngẫu Nhiên"};
                if (duLieuNguoiChoi.getSucManh() >= 50_000_000_000L && duLieuNguoiChoi.getCapSkill(3) == 7) {
                    listDieuUoc[2] = "Sức Mạnh \n Nâng cấp Skill 4\n Lên cấp 8";
                }
            } else if (veHUD.ngocRongUoc.equals("1saoden")) {
                listDieuUoc = new String[]{"Đổi cơ thể với Goku","Thú cưỡi phượng hoàng cực VIP","Danh hiệu VIP ngẫu nhiên","Vật phẩm đeo lưng ngẫu nhiên","Sở Hữu Đệ Tử"};
                if (duLieuNguoiChoi.coDeTu()) {
                    if (duLieuNguoiChoi.deTu.getSucManh()<1_500_000_000L) {
                        listDieuUoc[4] = "+150 Tr \n Sức mạnh \n và tiềm năng \n Đệ tử";
                    } else if (duLieuNguoiChoi.deTu.getSucManh()<20_000_000_000L) {
                        listDieuUoc[4] = "+2 Tỷ \n Sức mạnh \n và tiềm năng \n Đệ tử";
                    } else if (duLieuNguoiChoi.deTu.getSucManh()>=20_000_000_000L) {
                        listDieuUoc[4] = "Sức Mạnh \n Đổi Skill 4 \n đệ tử";
                    }
                }
                if (duLieuNguoiChoi.getHanhTrangDangMac().get(5)!=null) {
                    if (duLieuNguoiChoi.getHanhTrangDangMac().get(5).getId().equals("goku_black")) {
                        listDieuUoc[0] = "Khai mở sức mạnh SSJ Rose";
                    }
                }
                String[] danhSachId = {"luoi_hai","canh_doi","canh_ac_quy","canh_thien_than","canh_thien_su","hoa","kiem"};
                boolean duTatCa = true;
                ArrayList<Item> danhSach = duLieuNguoiChoi.getHanhTrang();
                for (String idCanTim : danhSachId) {
                    boolean timThay = false;
                    for (Item item : danhSach) {
                        if (item != null && idCanTim.equals(item.getId())) {
                            timThay = true;
                            break;
                        }
                    }
                    if (!timThay) {
                        duTatCa = false; // thiếu ít nhất 1 id
                        break;
                    }
                }
                if (duTatCa) listDieuUoc[3] = "AURA VIP ngẫu nhiên";
            }
            for (int i = 0; i < 5; i++) {
                if (veHUD.nutduocchon==i) {
                    Texture nutVe = veHUD.nutClickTimer2 > 0 ? veHUD.nutvuongclick : veHUD.nutvuong;
                    batch.draw(nutVe , 210 + i * 120, 5, 114, 114);
                }
                else {
                    batch.draw(veHUD.nutvuong , 210 + i * 120, 5, 114, 114);
                }
                veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                layout.setText(
                    veHUD.font,
                    listDieuUoc[i],
                    veHUD.font.getColor(),
                    100,
                    Align.center,
                    true
                );
                veHUD.font.draw(batch,layout,210 + i * 120+7f,5+(114+layout.height)/2f);
            }
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1f, 1f, 1f, 1f);
            shapeRenderer.rect((Gdx.graphics.getWidth()-594)/2f, 120, 594,80);
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            for (int i = 0; i < 2; i++) {
                shapeRenderer.rect((Gdx.graphics.getWidth()-594)/2f - i , 120 - i, 594 + i * 2, 80 + i * 2);
            }
            shapeRenderer.end();
            batch.begin();
            String TextRongThan ;
            if (!veHUD.ngocRongUoc.equals("1saoden")) {
                TextRongThan = "Ta sẽ ban cho ngươi một điều ước, ngươi có 5 phút, hãy suy nghĩ thật kỹ trước khi quyết định";
            } else {
                TextRongThan = "Ta là Rồng Thần Bóng Tối, sẽ thực hiện cho ngươi 1 điều ước, ngươi có 5 phút, nên hãy chọn điều mà lòng ngươi khao khát nhất";
            }
            layout.setText(
                veHUD.fontchat,
                TextRongThan,
                new Color(0, 0, 0, 1),
                594-30*2f,
                Align.center,
                true
            );
            veHUD.fontchat.draw(batch,layout,(Gdx.graphics.getWidth()-594)/2f+30f,120+(80+layout.height)/2f);
        }
    }
    public void dispose() {
        if (rongThan != null) {
            for (Texture tex : rongThan) if (tex != null) tex.dispose();
        }
        if (rongThanDen != null) {
            for (Texture tex : rongThanDen) if (tex != null) tex.dispose();
        }
        if (luaRongThan != null) {
            for (Texture tex : luaRongThan) if (tex != null) tex.dispose();
        }
        if (luaRongThanDen != null) {
            for (Texture tex : luaRongThanDen) if (tex != null) tex.dispose();
        }
        if (setRongThanDen != null) {
            for (Texture tex : setRongThanDen) if (tex != null) tex.dispose();
        }
        if (hieuUngRongThan != null) {
            for (Texture tex : hieuUngRongThan) if (tex != null) tex.dispose();
        }
    }

    public static void veRongThan(SpriteBatch batch, String ngocRongUoc, int nguoiUocRong, float xRong, float yRong) {
        VeHUD veHUD = State_Management.getVeHUD();
        NhanVat nhanVat = State_Management.getNhanVat();
        float daoDong = (float) Math.sin(nhanVat.thoiGianTichLuy * 2f);
        float offsetX = 120f + daoDong;
        boolean flipX = false;
        float flipscale = 1;
        // ve rong than
        boolean dieuKien;
        if (nguoiUocRong == State_Management.getUserResponse().id) {
            // Người gọi thì đợi vài giây để chạy animation lóe sáng
            dieuKien = veHUD.timeHienRongThan <= 300f - 2.1f;
        } else {
            dieuKien = true;
        }
        if (dieuKien) {
            batch.setProjectionMatrix(veHUD.camManager.camera.combined);
            if (ngocRongUoc.equals("Ngọc Rồng Sao Đen")) {
                Texture luaDuocChon = ((int) (nhanVat.thoiGianTichLuy / 15f * 10f) % 2 == 0 ? luaRongThanDen[0] : luaRongThanDen[1]);
                // k thấy luaDuocChon.getWidth()* 2f/3f mà lấy  luaRongThanDen[0].getWidth() * 2f / 3f cho đồng bộ giữa 2 ảnh tránh nhảy tọa độ x
                batch.draw(rongThanDen[7], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThanDen[7].getWidth() * 2f : 0), yRong, rongThanDen[7].getWidth() * 2f * flipscale, rongThanDen[7].getHeight() * 2f);
                batch.draw(rongThanDen[6], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThanDen[7].getWidth() * 2f + 75 : -75f), yRong + rongThanDen[7].getHeight() * 2f - 10f + daoDong, rongThanDen[6].getWidth() * 2f * flipscale, rongThanDen[6].getHeight() * 2f);
                batch.draw(rongThanDen[5], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThanDen[7].getWidth() * 2f + 78 : -78f), yRong + rongThanDen[7].getHeight() * 2f + rongThanDen[6].getHeight() * 2f - 18f, rongThanDen[5].getWidth() * 2f * flipscale, rongThanDen[5].getHeight() * 2f);
                batch.draw(rongThanDen[8], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThanDen[7].getWidth() * 2f + 5 : -5f), yRong + rongThanDen[7].getHeight() * 2f + rongThanDen[6].getHeight() * 2f - 18f + daoDong * 1.1f, rongThanDen[8].getWidth() * 2f * flipscale, rongThanDen[8].getHeight() * 2f);
                batch.draw(rongThanDen[2], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThanDen[7].getWidth() * 2f + 78 : -78f), yRong + rongThanDen[7].getHeight() * 2f + rongThanDen[6].getHeight() * 2f + rongThanDen[5].getHeight() * 2f - 26f, rongThanDen[2].getWidth() * 2f * flipscale, rongThanDen[2].getHeight() * 2f);
                batch.draw(rongThanDen[3], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThanDen[7].getWidth() * 2f - 25 : 25f), yRong + rongThanDen[7].getHeight() * 2f + rongThanDen[6].getHeight() * 2f + rongThanDen[5].getHeight() * 2f + rongThanDen[2].getHeight() * 2f - 26f, rongThanDen[3].getWidth() * 2f * flipscale, rongThanDen[3].getHeight() * 2f);
                batch.draw(rongThanDen[1], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThanDen[7].getWidth() * 2f + 147 : -147f), yRong + rongThanDen[7].getHeight() * 2f + rongThanDen[6].getHeight() * 2f + rongThanDen[5].getHeight() * 2f - 20f + daoDong * 0.8f, rongThanDen[1].getWidth() * 2f * flipscale, rongThanDen[1].getHeight() * 2f);
                batch.draw(rongThanDen[0], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThanDen[7].getWidth() * 2f + 75 : -75f), yRong + rongThanDen[7].getHeight() * 2f + rongThanDen[6].getHeight() * 2f + rongThanDen[5].getHeight() * 2f + rongThanDen[2].getHeight() * 2f - 50f + daoDong * 1.2f, rongThanDen[0].getWidth() * 2f * flipscale, rongThanDen[0].getHeight() * 2f);
                batch.draw(rongThanDen[4], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThanDen[7].getWidth() * 2f + 5 : -5f), yRong + rongThanDen[7].getHeight() * 2f + rongThanDen[6].getHeight() * 2f - 10f + daoDong * 0.7f, rongThanDen[4].getWidth() * 2f * flipscale, rongThanDen[4].getHeight() * 2f);
                batch.draw(luaDuocChon, xRong + offsetX - luaDuocChon.getWidth() * 2f / 3f, yRong, luaDuocChon.getWidth() * 2f, luaDuocChon.getHeight() * 2f);
            } else {
                Texture luaDuocChon = ((int) (nhanVat.thoiGianTichLuy / 15f * 10f) % 2 == 0 ? luaRongThan[0] : luaRongThan[1]);
                batch.draw(rongThan[7], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThan[7].getWidth() * 2f : 0), yRong, rongThan[7].getWidth() * 2f * flipscale, rongThan[7].getHeight() * 2f);
                batch.draw(rongThan[6], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThan[7].getWidth() * 2f + 75 : -75f), yRong + rongThan[7].getHeight() * 2f - 10f + daoDong, rongThan[6].getWidth() * 2f * flipscale, rongThan[6].getHeight() * 2f);
                batch.draw(rongThan[5], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThan[7].getWidth() * 2f + 78 : -78f), yRong + rongThan[7].getHeight() * 2f + rongThan[6].getHeight() * 2f - 18f, rongThan[5].getWidth() * 2f * flipscale, rongThan[5].getHeight() * 2f);
                batch.draw(rongThan[8], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThan[7].getWidth() * 2f + 5 : -5f), yRong + rongThan[7].getHeight() * 2f + rongThan[6].getHeight() * 2f - 18f + daoDong * 1.1f, rongThan[8].getWidth() * 2f * flipscale, rongThan[8].getHeight() * 2f);
                batch.draw(rongThan[2], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThan[7].getWidth() * 2f + 78 : -78f), yRong + rongThan[7].getHeight() * 2f + rongThan[6].getHeight() * 2f + rongThan[5].getHeight() * 2f - 26f, rongThan[2].getWidth() * 2f * flipscale, rongThan[2].getHeight() * 2f);
                batch.draw(rongThan[3], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThan[7].getWidth() * 2f - 25 : 25f), yRong + rongThan[7].getHeight() * 2f + rongThan[6].getHeight() * 2f + rongThan[5].getHeight() * 2f + rongThan[2].getHeight() * 2f - 26f, rongThan[3].getWidth() * 2f * flipscale, rongThan[3].getHeight() * 2f);
                batch.draw(rongThan[1], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThan[7].getWidth() * 2f + 147 : -147f), yRong + rongThan[7].getHeight() * 2f + rongThan[6].getHeight() * 2f + rongThan[5].getHeight() * 2f - 20f + daoDong * 0.8f, rongThan[1].getWidth() * 2f * flipscale, rongThan[1].getHeight() * 2f);
                batch.draw(rongThan[0], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThan[7].getWidth() * 2f + 75 : -75f), yRong + rongThan[7].getHeight() * 2f + rongThan[6].getHeight() * 2f + rongThan[5].getHeight() * 2f + rongThan[2].getHeight() * 2f - 50f + daoDong * 1.2f, rongThan[0].getWidth() * 2f * flipscale, rongThan[0].getHeight() * 2f);
                batch.draw(rongThan[4], xRong + offsetX - luaRongThanDen[0].getWidth() * 2f / 3f + (flipX ? rongThan[7].getWidth() * 2f + 5 : -5f), yRong + rongThan[7].getHeight() * 2f + rongThan[6].getHeight() * 2f - 10f + daoDong * 0.7f, rongThan[4].getWidth() * 2f * flipscale, rongThan[4].getHeight() * 2f);
                batch.draw(luaDuocChon, xRong + offsetX - luaDuocChon.getWidth() * 2f / 3f, yRong, luaDuocChon.getWidth() * 2f, luaDuocChon.getHeight() * 2f);
            }
        }
    }
}
