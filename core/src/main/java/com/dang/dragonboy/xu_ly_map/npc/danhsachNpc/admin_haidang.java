package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc;
import com.badlogic.gdx.graphics.GL20;
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
import com.dang.dragonboy.item.Item;

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
    public Texture formQuyDoiVe,nutsv,nutclicksv,shopThuong,shopVip;
    public boolean dangQuyDoiVe = false;
    public int chucNangQuyDoiVeDangChon = 0;

    public float timeChoTatChucNang = 0f;
    public int chucNangCanTat = -1;

    public boolean dangHienChucNang = false;

    public boolean dangHienChatDoiVeQuay = false;
    public float timeChoHienChatDoiVeQuay = 0f;
    public int nutDuocChonKhiChat = -1;
    public String tinNhanChat = "";

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

        nutsv = new Texture("hud/giaodienngoai/chung/maychubutton.png");
        nutclicksv = new Texture("hud/giaodienngoai/chung/maychuclickbutton.png");
        formQuyDoiVe = new Texture("hud/giaodienngoai/chung/formmaychu.jpg");
        shopThuong = Main.assetManager.get("hieuung/hieuunggame/gacha/shopthuong.png", Texture.class);
        shopVip = Main.assetManager.get("hieuung/hieuunggame/gacha/shopvip.png", Texture.class);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderCacChucNang(batch);
        renderChucNangGacha(batch);
        renderChucNangQuyDoiVe(batch);
        renderKhungChatQuyDoiVe(batch);
        renderThongBaoSauGacha(batch);
    }

    @Override
    public void capNhat() {
        if (timeClickNut > 0) {
            timeClickNut -= Gdx.graphics.getDeltaTime();
            if (timeClickNut <= 0) {
                timeClickNut = 0;
                if (nutDangChon == 0) {
                    dangBatManHinhGacha = true;
                    if (veHUD.isDangPhatNhac()) {
                        // Tắt nhạc cũ
                        for (int i = 1; i < veHUD.nhacNen.length; i++) {
                            if (veHUD.nhacNen[i].isPlaying() && i != 11) {
                                veHUD.nhacNen[i].stop();
                                // Phát nhạc mới
                                veHUD.nhacNen[11].play();
                                veHUD.setTinNhanPet("Đang phát bài Seasons",2f);
                            }
                        }
                    }
                } else if (nutDangChon == 1) {
                    dangQuyDoiVe = true;
                    chucNangQuyDoiVeDangChon = 0;
                }
                dangHienChucNang = true;
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
                int soVeCan = (nutGachaDangChon == 0 ? 1 : 10);
                int gioiHanHanhTrang = (nutGachaDangChon == 0 ? 49 : 40);

                String loi = null;
                if (duLieuNguoiChoi.getSoVeQuayKhoa() < soVeCan) {
                    loi = "Vui lòng quy đổi vé quay";
                } else if (dangGacha) {
                    loi = "Vui lòng kiên nhẫn";
                } else if (duLieuNguoiChoi.getHanhTrang().size() > gioiHanHanhTrang) {
                    loi = "Hành trang đã đầy";
                }

                if (loi != null) {
                    veHUD.setTinNhanPet(loi, 2f);
                } else {
                    gacha(soVeCan);
                    duLieuNguoiChoi.giamVeQuayKhoa(soVeCan);
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
        if (timeChoTatChucNang > 0) {
            timeChoTatChucNang -= Gdx.graphics.getDeltaTime();
            if (timeChoTatChucNang <= 0) {
                switch (chucNangCanTat) {
                    case 0: dangBatManHinhGacha = false;break;
                    case 1: dangQuyDoiVe = false;break;
                }
                dangHienChucNang = false;
            }
        }
        if (timeChoHienChatDoiVeQuay > 0) {
            timeChoHienChatDoiVeQuay -= Gdx.graphics.getDeltaTime();
            if (timeChoHienChatDoiVeQuay <= 0) {
                if (!dangHienChatDoiVeQuay) {
                    timeChoHienChatDoiVeQuay = 0;
                    dangHienChatDoiVeQuay = true;
                    nutDuocChonKhiChat = -1;
                } else {
                    if (nutDuocChonKhiChat == 0) {
                        String loi = null;
                        if (tinNhanChat.isEmpty() || !chuoiToanSo(tinNhanChat)) {
                            loi = "Vui lòng nhập số vé cần quy đổi";
                        } else if (!duLieuNguoiChoi.checkCoItemTrongHanhTrang("ve_quay_npc_haidang")) {
                            loi = "Không có item trong hành trang";
                        } else if (duLieuNguoiChoi.layItemTrongHanhTrang("ve_quay_npc_haidang").getSoLuong() < Integer.parseInt(tinNhanChat)) {
                            loi = "Số lượng quy đổi không hợp lệ";
                        }
                        if (loi != null) {
                            veHUD.setTinNhanPet(loi,2f);
                        } else {
                            duLieuNguoiChoi.layItemTrongHanhTrang("ve_quay_npc_haidang").giamSoLuong(Integer.parseInt(tinNhanChat));
                            duLieuNguoiChoi.tangVeQuayKhoa(Integer.parseInt(tinNhanChat));
                            veHUD.setTinNhanPet("Quy đổi thành công",2f);
                        }
                        dangHienChatDoiVeQuay = false;
                        tinNhanChat = "";
                        nutDuocChonKhiChat = -1;
                    } else {
                        dangHienChatDoiVeQuay = false;
                        tinNhanChat = "";
                        nutDuocChonKhiChat = -1;
                    }
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

    public void renderCacChucNang(SpriteBatch batch) {
        if (dangHienChucNang && nutDangChon == 2) return;

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
    }

    public void renderChucNangGacha(SpriteBatch batch) {
        if (!dangBatManHinhGacha) return;

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
                boolean dieuKienSangNut;
                if (i!=2) {
                    dieuKienSangNut = timeChoTruocGacha > 0 && nutGachaDangChon == i;
                } else {
                    dieuKienSangNut = timeChoTatChucNang > 0 && chucNangCanTat == 0;
                }
                batch.draw(dieuKienSangNut ? veHUD.nutclick : veHUD.nutdn, nutX, nutY, nutWidth, nutHeight);
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
    }

    public void renderChucNangQuyDoiVe(SpriteBatch batch) {
        if (!dangQuyDoiVe) return;

        //chung
        batch.draw(formQuyDoiVe,(Gdx.graphics.getWidth()-680)/2f,65,680,400);
        veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.layout.setText(veHUD.font, "© 2025 Chiến Binh Rồng Thiêng | HDG");
        veHUD.font.draw(batch, veHUD.layout, (Gdx.graphics.getWidth()-680)/2f + 5, 65 + 20);

        String[] text = new String[] {"Shop Vé Thường","Shop Vé VIP"};
        for (int i = 0; i < 2; i++) {
            batch.draw(chucNangQuyDoiVeDangChon == i ? nutclicksv : nutsv,(Gdx.graphics.getWidth()-680)/2f + 12.5f,416-i*46,200,36);
            veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
            veHUD.layout.setText(veHUD.font, text[i]);
            veHUD.font.draw(batch, veHUD.layout, (Gdx.graphics.getWidth()-680)/2f + 12.5f + (200-veHUD.layout.width)/2f, 416-i*46 + 23);
        }

        batch.draw(timeChoHienChatDoiVeQuay > 0 && nutDuocChonKhiChat == -1 ? nutclicksv : nutsv,(Gdx.graphics.getWidth()-680)/2f + 12.5f,65 + 30 + 38,200,36);
        veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.layout.setText(veHUD.font, "Quy đổi vé");
        veHUD.font.draw(batch, veHUD.layout, (Gdx.graphics.getWidth()-680)/2f + 12.5f + (200-veHUD.layout.width)/2f,65 + 53 + 38);

        String[] textMoTa = new String[] {
            "1,Shop Vé Thường (vé khóa) để quay gacha.\n" +
            "2,Chỉ sử dụng trong tài khoản hiện tại.\n" +
            "3,phù hợp cho F2P, tích lũy dần."
            ,
            "1,Shop Vé VIP (vé giao dịch) để đổi sang Vé Thường.\n" +
            "2,Vé VIP có thể mua bán, trao đổi giữa các nhân vật.\n" +
            "3,Khi đổi sang Vé Thường mới quay gacha được.\n" +
            "4,cơ hội sở hữu item độc quyền, giá trị."
        };
        veHUD.fontMoTaQuyDoiVe.setColor(1.0f, 0.956f, 0.863f, 1f);
        veHUD.layout.setText(veHUD.fontMoTaQuyDoiVe,
            chucNangQuyDoiVeDangChon == 0 ? textMoTa[0] : textMoTa[1],
            veHUD.fontMoTaQuyDoiVe.getColor(), // dùng lại màu đã set
            190,                 // wrapWidth
            Align.left,          // căn trái mặc định
            true);               // bật tự xuống dòng
        veHUD.fontMoTaQuyDoiVe.draw(batch, veHUD.layout, (Gdx.graphics.getWidth()-680)/2 + 20f, 330 + 20);

        batch.draw(timeChoTatChucNang > 0 && chucNangCanTat == 1 ? nutclicksv : nutsv,(Gdx.graphics.getWidth()-680)/2f + 12.5f,65 + 30,200,36);
        veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.layout.setText(veHUD.font, "Thoát");
        veHUD.font.draw(batch, veHUD.layout, (Gdx.graphics.getWidth()-680)/2f + 12.5f + (200-veHUD.layout.width)/2f,65 + 53);

        veHUD.font.setColor(1.0f, 0.956f, 0.863f, 1f);
        veHUD.layout.setText(veHUD.font, chucNangQuyDoiVeDangChon == 0 ? text[0] : text[1]);
        veHUD.font.draw(batch, veHUD.layout, 396+(442-veHUD.layout.width)/2f,440);

        //riêng
        if (chucNangQuyDoiVeDangChon == 0) {
            batch.draw(shopThuong,396f-0.4f,260,shopThuong.getWidth(),shopThuong.getHeight());
            for (int i = 0; i < 3; i++) {
                float nutW = 68 * 0.7f,nutH = 52 * 0.7f;
                float nutX = 396f - 0.4f + (140 - nutW)/2f + 151*i, nutY = 260 + 3f;
                batch.draw(veHUD.nutchucnang, nutX, nutY, nutW, nutH);
            }
        }
        if (chucNangQuyDoiVeDangChon == 1) {
            batch.draw(shopVip,396f-0.4f,259,shopVip.getWidth(),shopVip.getHeight());
            for (int i = 0; i < 3; i++) {
                float nutW = 68 * 0.7f,nutH = 52 * 0.7f;
                float nutX = 396f - 0.4f + (140 - nutW)/2f + 151*i, nutY = 260 + 3f;
                batch.draw(veHUD.nutchucnang, nutX, nutY, nutW, nutH);
            }
        }
        for (int i = 0; i < 3; i++) {
            float nutW = 68 * 0.7f,nutH = 52 * 0.7f;
            float nutX = 396f - 0.4f + (140 - nutW)/2f + 151*i, nutY = 260 + 3f;
            veHUD.layout.setText(veHUD.fontChucnang1,"Thêm");
            veHUD.fontChucnang1.draw(batch,veHUD.layout,nutX+(nutW-veHUD.layout.width)/2f,nutY+(nutH+veHUD.layout.height)/2f);
        }
    }
    public void renderKhungChatQuyDoiVe(SpriteBatch batch) {
        if (dangHienChatDoiVeQuay) {
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
            batch.draw(timeChoHienChatDoiVeQuay>0 && nutDuocChonKhiChat==0? veHUD.nutclick : veHUD.nutdn, nX-81, nutY, 140, 48);
            veHUD.layout.setText(veHUD.fontTenSkill, "OK");
            veHUD.fontTenSkill.draw(batch, veHUD.layout, nX-81 + (140 - veHUD.layout.width) / 2f, nutY + 29);
            batch.draw(timeChoHienChatDoiVeQuay>0 && nutDuocChonKhiChat==1? veHUD.nutclick : veHUD.nutdn, nX+81, nutY, 140, 48);
            veHUD.layout.setText(veHUD.fontTenSkill, "Đóng");
            veHUD.fontTenSkill.draw(batch, veHUD.layout, nX+81 + (140 - veHUD.layout.width) / 2f, nutY + 29);

            veHUD.fontTenSkill.setColor(0f / 255f, 85f / 255f, 38f / 255f, 1f);
            veHUD.layout.setText(veHUD.fontTenSkill, "Quy đổi vé quay VIP - Thường");
            veHUD.fontTenSkill.draw(batch, veHUD.layout, (Gdx.graphics.getWidth() - 528) / 2f + 15, 35 + 115);

            // Các thông số
            float khungX = (Gdx.graphics.getWidth() - 528) / 2f + 25;
            float khungY = 35;
            float khungWidth = 465;
            float khungHeight = 68;

            if (tinNhanChat.isEmpty()) {
                veHUD.fontText.setColor(1.0f, 0.956f, 0.863f, 1f);
                veHUD.layout.setText(veHUD.fontText, "Nhập số vé cần quy đổi từ VIP sang Thường (tỉ lệ 1 - 1)");
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
    }
    public void renderThongBaoSauGacha(SpriteBatch batch) {
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
    public boolean chuoiToanSo(String chuoi) {
        try {
            int so = Integer.parseInt(chuoi);
            return so > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
