package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.renderUInpc;

public class admin_haidang extends renderUInpc {
    public boolean dangBatManHinhGacha = false;
    public boolean dangGacha = false;
    public int maxQuay = 0;
    public int chisovong = 0;
    public Texture anhGachaBase;
    public Texture[] anhGacha = new Texture[16];
    public float timeDoiFrame = 0.06f;
    public int[] mangGacha1 = new int[1];
    public int[] mangGacha10 = new int[10];
    public int dangGachaX1hayX10 = 0;
    public int nutDangChon = -1;
    public float timeClickNut = 0f;
    public float timeSauGacha = 0f;
    public int chisovongtruoc = -1;
    public float timeChoTruocGacha = 0f;
    public int nutGachaDangChon = -1;

    private int demKhongVIP = 0;             // đếm số lần quay không ra VIP liên tiếp
    private final int mocPityMem = 75;       // bắt đầu tăng tỉ lệ sau mốc này
    private final int mocPityCung = 90;     // đảm bảo ra VIP ở lần quay thứ 90
    private final float tileCoBanVIP = 0.006f; // 0.6% tỉ lệ cơ bản ra VIP
    private int pity = 0;

    public Texture veQuay, veQuayKhoa;

    public admin_haidang(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        super(npc, veHUD, duLieuNguoiChoi, nhanVat);
        for (int i = 0; i < 16; i++) {
            anhGacha[i] = new Texture("hieuung/hieuunggame/gacha/1 ("+(i+2)+").png");
        }
        anhGachaBase = new Texture("hieuung/hieuunggame/gacha/1 (1).png");
        veQuay = new Texture("vatpham/vatphamgame/ve_quay_npc_haidang/vequay.png");
        veQuayKhoa = new Texture("vatpham/vatphamgame/ve_quay_npc_haidang/vequaykhoa.png");
    }

    @Override
    public void render(SpriteBatch batch) {
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
            if (nutDangChon == i) {
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

        float offsetX = 8;
        float offsetY = 55;
        float xVe = (Gdx.graphics.getWidth()-anhGachaBase.getWidth())/2f + offsetX;
        if (dangBatManHinhGacha && !dangGacha) {
            batch.draw(anhGachaBase,xVe,offsetY);
        }

        if (dangBatManHinhGacha && dangGacha) {
            if (timeSauGacha <= 0) {
                batch.draw(anhGacha[chisovong % 16], xVe, offsetY);
            }  else {
                batch.draw(anhGacha[chisovongtruoc % 16], xVe, offsetY);
            }
        }

        if (dangBatManHinhGacha) {
            for (int i = 0; i < 2; i++) {
                String[] text = new String[] {"Quay x1","Quay x10"};
                float nutWidth = 140;
                float nutHeight = 50;
                float nutX = xVe + (anhGachaBase.getWidth() - nutWidth) / 2f - 3f;
                float nutY = offsetY + 280 - i*100;
                // kích thước icon
                float iconW = veQuayKhoa.getWidth() / 4f;
                float iconH = veQuayKhoa.getHeight() / 4f;
                // vẽ nút
                batch.draw(timeChoTruocGacha > 0 && nutGachaDangChon == i  ? veHUD.nutclick : veHUD.nutdn, nutX, nutY, nutWidth, nutHeight);
                // vẽ chữ
                veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
                veHUD.layout.setText(veHUD.fontTenSkill, text[i]);
                float textX = nutX + (nutWidth - veHUD.layout.width) / 2f - 5f;
                float textY = nutY + nutHeight / 2f + veHUD.layout.height / 2f;
                veHUD.fontTenSkill.draw(batch, veHUD.layout, textX, textY);
                // vẽ icon ngay bên phải chữ
                float iconX = textX + veHUD.layout.width + 5f;
                float iconY = nutY + (nutHeight - iconH) / 2f + 2f;
                batch.draw(veQuayKhoa, iconX, iconY, iconW, iconH);
            }

            veHUD.layout.setText(veHUD.fontsm, "Vé quay: "+duLieuNguoiChoi.getSoVeQuayKhoa() +" | " + "Pity: "+pity+"/90");
            veHUD.fontsm.draw(batch, veHUD.layout, 510-veHUD.layout.width/2f+3f, 220);
        }
    }

    @Override
    public void capNhat() {
        if (timeClickNut > 0) {
            timeClickNut -= Gdx.graphics.getDeltaTime();
            if (timeClickNut <= 0) {
                timeClickNut = 0;
                if (nutDangChon == 0) {
                    dangBatManHinhGacha = true;
                }
            }
        }
        if (dangGacha && timeSauGacha<=0) {
            float timeDoi = 0.1f;
            if (chisovong >= maxQuay - 4) timeDoi = 0.3f;
            if (chisovong >= maxQuay - 1) timeDoi = 0.7f;
            timeDoiFrame += Gdx.graphics.getDeltaTime();
            if (timeDoiFrame >= timeDoi) {
                timeDoiFrame = 0;

                if (chisovong < maxQuay) {
                    chisovong++;
                }

                if (chisovong == maxQuay) {
                    timeSauGacha = 0.5f;
                    chisovongtruoc = chisovong;
                }
            }
        }
        if (timeSauGacha > 0) {
            timeSauGacha -= Gdx.graphics.getDeltaTime();
            if (timeSauGacha <= 0) {
                timeSauGacha = 0;
                dangGacha = false;
                pity = demKhongVIP;
            }
        }
        if (timeChoTruocGacha > 0) {
            timeChoTruocGacha -= Gdx.graphics.getDeltaTime();
            if (timeChoTruocGacha <= 0) {
                timeChoTruocGacha = 0;
                if (!dangGacha) {
                    boolean dieuKien = nutGachaDangChon == 0 ? duLieuNguoiChoi.getHanhTrang().size() <= 49 : duLieuNguoiChoi.getHanhTrang().size() <= 40;
                    if (dieuKien) {
                        if (nutGachaDangChon == 0) gacha(1);
                        else gacha(10);
                    } else {
                        veHUD.setTinNhanPet("Hành trang đã đầy",2f);
                    }
                } else {
                    veHUD.setTinNhanPet("Vui lòng kiên nhẫn",2f);
                }
            }
        }
    }

    public void gacha(int soLanGacha) {
        int[] mangGacha = new int[soLanGacha];
        for (int i = 0; i < soLanGacha; i++) {
            mangGacha[i] = randomSo();
        }
        dangGachaX1hayX10 = soLanGacha;
        if (dangGachaX1hayX10 == 1) {
            mangGacha1 = mangGacha;
        } else if (dangGachaX1hayX10 == 10) {
            mangGacha10 = mangGacha;
        }
        chisovong = 0;
        maxQuay = randomSoLanQuay(mangGacha[mangGacha.length-1]);
        dangGacha = true;
    }

    public int randomSo() {
        float tileHienTai = tileCoBanVIP;
        // Nếu vượt mốc pity mềm -> tăng tỉ lệ mỗi lần
        if (demKhongVIP >= mocPityMem) {
            // mỗi lần sau mốc 75 tăng 0.5% (0.005f)
            float congThem = 0.005f * (demKhongVIP - mocPityMem + 1);
            tileHienTai = Math.min(0.10f, tileCoBanVIP + congThem); // trần 10%
        }
        // pity cứng: đảm bảo lần thứ 90 ra VIP
        boolean batBuocRaVIP = (demKhongVIP + 1 >= mocPityCung);

        boolean laVIP = batBuocRaVIP || Math.random() < tileHienTai;

        int ketQua;
        if (laVIP) {
            int[] oVIP = {0, 4, 8, 12};
            ketQua = oVIP[MathUtils.random(oVIP.length - 1)];
            demKhongVIP = 0;
        } else {
            int[] oThuong = {1,2,3,5,6,7,9,10,11,13,14,15};
            ketQua = oThuong[MathUtils.random(oThuong.length - 1)];
            demKhongVIP++;
        }
        return ketQua;
    }

    public int randomSoLanQuay(int so) {
        return so+16*3;
    }
}
