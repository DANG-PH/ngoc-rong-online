package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc;
import com.dang.dragonboy.du_lieu.ThemItemTest;
import com.dang.dragonboy.he_thong.Main;
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
    public Texture[] mangAnhGacha1 = new Texture[1];
    public Texture[] mangAnhGacha10 = new Texture[10];
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

    public Texture veQuay, veQuayKhoa, anhItemThuong;
    public Texture[] randomGenShin = new Texture[2],randomHuyHieu = new Texture[4],randomDeoLung = new Texture[7],randomBongTai = new Texture[2], randomNgocRong = new Texture[7], randomNgocRongDen = new Texture[7];
    public float timeDoiFrameVip = 0f, alphaGenshin = 0f;
    public int frameGenshin = 0,frameHuyHieu = 0,frameDeoLung = 0;

    public boolean dangThongBaoSauGacha = false;

    public float timeBamNutOk = 0f;

    public Texture[] vatPhamGachaKrandom = new Texture[16];

    public admin_haidang(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        super(npc, veHUD, duLieuNguoiChoi, nhanVat);

        anhItemThuong = new Texture("hieuung/hieuunggame/gacha/gacha1.png");
        for (int i = 0; i < 16; i++) {
            anhGacha[i] = Main.assetManager.get("hieuung/hieuunggame/gacha/1 (" + (i + 2) + ").png", Texture.class);
        }
        anhGachaBase = Main.assetManager.get("hieuung/hieuunggame/gacha/1 (1).png", Texture.class);
        veQuay = Main.assetManager.get("vatpham/vatphamgame/ve_quay_npc_haidang/vequay.png", Texture.class);
        veQuayKhoa = Main.assetManager.get("vatpham/vatphamgame/ve_quay_npc_haidang/vequaykhoa.png", Texture.class);

        for (int i = 0; i < 2; i++) {
            randomGenShin[i] = new Texture("hieuung/hieuunggame/gacha/randomgenshin/"+(i+1)+".png");
            randomBongTai[i] = new Texture("hieuung/hieuunggame/gacha/randombongtai/"+(i+1)+".png");
        }
        for (int i = 0; i < 4; i++) {
            randomHuyHieu[i] = new Texture("hieuung/hieuunggame/gacha/randomhuyhieu/"+(i+1)+".png");
        }
        for (int i = 0; i < 7; i++) {
            randomDeoLung[i] = new Texture("hieuung/hieuunggame/gacha/randomdeolung/"+(i+1)+".png");
            randomNgocRongDen[i] = new Texture("hieuung/hieuunggame/gacha/randomngocrongden/nr"+(i+1)+"sd.png");
            randomNgocRong[i] = new Texture("hieuung/hieuunggame/gacha/randomngocrong/nr"+(i+1)+"s.png");
        }
        for (int i = 1; i < 16; i++) {
            if (i!=4 && i!=6 && i!=8 && i!=11 && i!=12) {
                vatPhamGachaKrandom[i] = new Texture("hieuung/hieuunggame/gacha/vatphamgacha/"+i+".png");
            }
        }
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
            batch.draw(anhItemThuong,333,126);
            batch.setColor(1f, 1f, 1f, alphaGenshin);
            batch.draw(randomGenShin[frameGenshin],frameGenshin == 0 ? 635 : 648,frameGenshin == 0 ? 130 : 140);
            batch.draw(randomBongTai[frameGenshin],339,443);
            batch.draw(randomHuyHieu[frameHuyHieu],360-randomHuyHieu[frameHuyHieu].getWidth()/2f,157-randomHuyHieu[frameHuyHieu].getHeight()/2f);
            batch.draw(randomDeoLung[frameDeoLung],663-randomDeoLung[frameDeoLung].getWidth()/2f,463-randomDeoLung[frameDeoLung].getHeight()/2f);
            batch.setColor(1f, 1f, 1f, 1f);
            String[] text = new String[] {"Quay x1","Quay x10","Thoát"};
            for (int i = 0; i < 3; i++) {
                float nutWidth = 140;
                float nutHeight = 50;
                float nutX = xVe + (anhGachaBase.getWidth() - nutWidth) / 2f - 3f;
                float nutY = offsetY + 300 - i*60;
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
                if (i!=2) {
                    float iconX = textX + veHUD.layout.width + 5f;
                    float iconY = nutY + (nutHeight - iconH) / 2f + 2f;
                    batch.draw(veQuayKhoa, iconX, iconY, iconW, iconH);
                }
            }

            veHUD.layout.setText(veHUD.fontsm, "Vé quay: "+duLieuNguoiChoi.getSoVeQuayKhoa() +" | " + "Pity: "+pity+"/90");
            veHUD.fontsm.draw(batch, veHUD.layout, 510-veHUD.layout.width/2f+3f, 220);
        }
        if (dangThongBaoSauGacha) {
            batch.draw(veHUD.anhThongBao, (Gdx.graphics.getWidth() - 720) / 2f, 65, 720, 175);

            String text = dangGachaX1hayX10 == 1 ? "Chúc mừng bạn đã nhận được phần quà sau" : "Chúc mừng bạn đã nhận được những phần quà sau";
            veHUD.layout.setText(veHUD.fontTenSkill, text);
            veHUD.fontTenSkill.draw(batch, veHUD.layout, (Gdx.graphics.getWidth() - veHUD.layout.width) / 2, 210);

            if (dangGachaX1hayX10 == 1) {
                batch.draw(mangAnhGacha1[0],(Gdx.graphics.getWidth() - 720) / 2f+(720-mangAnhGacha1[0].getWidth())/2f,65+(175-mangAnhGacha1[0].getHeight())/2f);
            } else {
                for (int i = 0; i < 10; i++) {
                    batch.draw(mangAnhGacha10[i],(Gdx.graphics.getWidth() - 720) / 2f+50f+65*i,65+(175-mangAnhGacha10[i].getHeight())/2f-4f);
                }
            }

            float nutX = (Gdx.graphics.getWidth() - 140) / 2f;
            float nutY = 50;
            batch.draw(timeBamNutOk > 0 ? veHUD.nutclick : veHUD.nutdn, nutX, nutY, 140, 50);
            veHUD.layout.setText(veHUD.fontTenSkill,"OK");
            veHUD.fontTenSkill.draw(batch, veHUD.layout, nutX + (140 - veHUD.layout.width) / 2f, nutY + 30);
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
                int[] mang = dangGachaX1hayX10 == 1 ? mangGacha1 : mangGacha10;
                ThemItemTest themItemTest = new ThemItemTest(duLieuNguoiChoi,nhanVat,veHUD);
                Texture[] mangDuocTruyen = dangGachaX1hayX10 == 1 ? mangAnhGacha1 : mangAnhGacha10;
                for (int i = 0; i < mang.length; i++) {
                    themItemTest.themQuaGacha(mang[i],i, mangDuocTruyen);
                }
                dangThongBaoSauGacha = true;
            }
        }
        if (timeChoTruocGacha > 0) {
            timeChoTruocGacha -= Gdx.graphics.getDeltaTime();
            if (timeChoTruocGacha <= 0) {
                timeChoTruocGacha = 0;
                if (nutGachaDangChon != 2) {
                    if (duLieuNguoiChoi.getSoVeQuayKhoa() > (nutGachaDangChon == 0 ? 1 : 10)) {
                        if (!dangGacha) {
                            boolean dieuKien = nutGachaDangChon == 0 ? duLieuNguoiChoi.getHanhTrang().size() <= 49 : duLieuNguoiChoi.getHanhTrang().size() <= 40;
                            if (dieuKien) {
                                if (nutGachaDangChon == 0) {
                                    gacha(1);
                                    duLieuNguoiChoi.giamVeQuayKhoa(1);
                                } else {
                                    gacha(10);
                                    duLieuNguoiChoi.giamVeQuayKhoa(10);
                                }
                            } else {
                                veHUD.setTinNhanPet("Hành trang đã đầy", 2f);
                            }
                        } else {
                            veHUD.setTinNhanPet("Vui lòng kiên nhẫn", 2f);
                        }
                    } else {
                        veHUD.setTinNhanPet("Vui lòng quy đổi vé quay", 2f);
                    }
                } else {
                    dangBatManHinhGacha = false;
                }
            }
        }
        timeDoiFrameVip += Gdx.graphics.getDeltaTime();
        if (timeDoiFrameVip >= 0 && timeDoiFrameVip < 0.5f) {
            alphaGenshin = 1-(0.5f-timeDoiFrameVip)/0.5f;
        }
        if (timeDoiFrameVip >= 2.5f && timeDoiFrameVip < 3f) {
            alphaGenshin = (3f-timeDoiFrameVip)/0.5f;
        }
        if (timeDoiFrameVip >= 3f) {
            timeDoiFrameVip = 0;
            frameGenshin = (frameGenshin+1)%2;
            frameHuyHieu = (frameHuyHieu+1)%4;
            frameDeoLung = (frameDeoLung+1)%7;
        }
        if (timeBamNutOk > 0) {
            timeBamNutOk -= Gdx.graphics.getDeltaTime();
            if (timeBamNutOk <= 0) {
                dangThongBaoSauGacha = false;
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
            int[] oThuong = {1,3,6,7,9,10,13,14,15};
            int[] oVipLoai2 = {2,5,11};
            boolean chonVipLoai2 = Math.random() < 0.01;
            if (chonVipLoai2) {
                ketQua = oVipLoai2[MathUtils.random(oVipLoai2.length - 1)];
            } else {
                ketQua = oThuong[MathUtils.random(oThuong.length - 1)];
            }
            demKhongVIP++;
        }
        return ketQua;
    }

    public int randomSoLanQuay(int so) {
        return so+16*3;
    }
}
