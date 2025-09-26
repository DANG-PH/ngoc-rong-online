package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_haidang;
import com.badlogic.gdx.graphics.GL20;
import com.dang.dragonboy.item.ThemItemTest;
import com.dang.dragonboy.he_thong.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.LoaiItem;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.Npc;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.renderUInpc;
import com.dang.dragonboy.item.Item;

public class admin_haidang extends renderUInpc {
    public TrangThaiChucNang_admin_haidang trangThai = TrangThaiChucNang_admin_haidang.NONE;
    public TrangThaiChucNang_admin_haidang trangThaiTruoc = TrangThaiChucNang_admin_haidang.NONE;

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
    public float timeClickNut = 0f;
    public float timeSauGacha = 0f;
    public int chisovongtruoc = -1;
    public float timeChoTruocGacha = 0f;
    public int nutGachaDangChon = -1;

    public int demKhongVIP = 0;             // đếm số lần quay không ra VIP liên tiếp
    public final int mocPityMem = 75;       // bắt đầu tăng tỉ lệ sau mốc này
    public final int mocPityCung = 90;     // đảm bảo ra VIP ở lần quay thứ 90
    public final float tileCoBanVIP = 0.006f; // 0.6% tỉ lệ cơ bản ra VIP
    private int pity = 0;

    public Texture veQuay, veQuayKhoa, anhItemThuong;
    public Texture[] randomGenShin = new Texture[2],randomHuyHieu = new Texture[4],randomDeoLung = new Texture[7],randomBongTai = new Texture[2], randomNgocRong = new Texture[7], randomNgocRongDen = new Texture[7];
    public float timeDoiFrameVip = 0f, alphaGenshin = 0f;
    public int frameGenshin = 0,frameHuyHieu = 0,frameDeoLung = 0;

    public float timeBamNutOk = 0f;

    public Texture[] vatPhamGachaKrandom = new Texture[16];
    public Texture formQuyDoiVe,nutsv,nutclicksv,shopThuong,shopVip;
    public int chucNangQuyDoiVeDangChon = 0;

    public float timeChoTatChucNang = 0f;

    public boolean dangHienChucNang = false;

    public boolean dangHienChatDoiVeQuay = false;
    public float timeChoHienChatDoiVeQuay = 0f;
    public int nutDuocChonKhiChat = -1;
    public String tinNhanChat = "";

    public long tongVang;
    public int tongNgoc,tongVeKhoa,tongVeVip;

    public float timeChoThemItemVeQuay = 0f;
    public int nutThemDangChon = -1;

    public float timeChoMuaItemThuong = 0f, timeChoMuaItemVip = 0f;

    public int nutChucNangDangChon= -1;

    public float timeBamNutOkHuongDan = 0f;

    public int soVeKhoaTamThoi = 0;


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
        renderHuongDanCachChoi(batch);

        renderThongBaoSauGacha(batch);
    }

    @Override
    public void capNhat() {
        capNhatClickNut();
        capNhatTatChucNang();

        capNhatTruocGacha();
        capNhatGacha();
        capNhatFrameVip();
        capNhatSauGacha();
        capNhatNutOkSauGacha();

        capNhatChatDoiVeQuay();
        capNhatThemItemVeQuay();
        capNhatMuaItemThuong();
        capNhatMuaItemVip();

        capNhatNutOkHuongDan();
    }

    public void renderCacChucNang(SpriteBatch batch) {
        if (dangHienChucNang && trangThai == TrangThaiChucNang_admin_haidang.HUONG_DAN) return;

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

    public void renderChucNangGacha(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_admin_haidang.GACHA || trangThai == TrangThaiChucNang_admin_haidang.GACHA_THONG_BAO && trangThaiTruoc == TrangThaiChucNang_admin_haidang.GACHA)) return;

        float offsetX = 8;
        float offsetY = 55;
        float xVe = (Gdx.graphics.getWidth()-anhGachaBase.getWidth())/2f + offsetX;
        if (!dangGacha) {
            batch.draw(anhGachaBase,xVe,offsetY);
        }

        if (dangGacha) {
            if (timeSauGacha <= 0) {
                batch.draw(anhGacha[chisovong % 16], xVe, offsetY);
            }  else {
                batch.draw(anhGacha[chisovongtruoc % 16], xVe, offsetY);
            }
        }

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
                dieuKienSangNut = timeChoTatChucNang > 0 && trangThai == TrangThaiChucNang_admin_haidang.GACHA;
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

        veHUD.layout.setText(veHUD.fontsm, "Pity: "+pity+"/90 "+" | " + " Vé quay: "+(dangGacha ? soVeKhoaTamThoi : duLieuNguoiChoi.getSoVeQuayKhoa()));
        veHUD.fontsm.draw(batch, veHUD.layout, 510-veHUD.layout.width/2f+3f - 6f, 220);
        batch.draw(veQuayKhoa,510-veHUD.layout.width/2f+3f+3f - 6f + veHUD.layout.width,220-veHUD.layout.height/2f-(veQuayKhoa.getHeight()/5f)/2f,veQuayKhoa.getWidth()/5f,veQuayKhoa.getHeight()/5f);
    }

    public void renderChucNangQuyDoiVe(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_admin_haidang.QUY_DOI || trangThai == TrangThaiChucNang_admin_haidang.GACHA_THONG_BAO && trangThaiTruoc == TrangThaiChucNang_admin_haidang.QUY_DOI)) return;

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
        veHUD.fontMoTaQuyDoiVe.draw(batch, veHUD.layout, (Gdx.graphics.getWidth()-680)/2f + 20f, 330 + 20);

        batch.draw(timeChoTatChucNang > 0 && trangThai == TrangThaiChucNang_admin_haidang.QUY_DOI ? nutclicksv : nutsv,(Gdx.graphics.getWidth()-680)/2f + 12.5f,65 + 30,200,36);
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
                batch.draw(timeChoThemItemVeQuay > 0 && nutThemDangChon == i ? veHUD.nutchucnangclick : veHUD.nutchucnang, nutX, nutY, nutW, nutH);
            }
            batch.draw(timeChoMuaItemThuong > 0 ? veHUD.nutclick : veHUD.nutdn,396+(435-140*0.7f),60+40,140*0.7f,48*0.7f);

            veHUD.fontMoTaQuyDoiVe.setColor(1.0f, 0.956f, 0.863f, 1f);
            veHUD.layout.setText(veHUD.fontMoTaQuyDoiVe, "Số vé khóa: "+ (dangGacha ? soVeKhoaTamThoi : duLieuNguoiChoi.getSoVeQuayKhoa()));
            veHUD.fontMoTaQuyDoiVe.draw(batch, veHUD.layout, 401,60+55);
            batch.draw(veQuayKhoa,401+veHUD.layout.width+3f,60+55 - veHUD.layout.height/2f - (veQuayKhoa.getHeight()/5f)/2f,veQuayKhoa.getWidth()/5f,veQuayKhoa.getHeight()/5f);

            veHUD.fontMoTaQuyDoiVe.setColor(1.0f, 0.956f, 0.863f, 1f);
            veHUD.layout.setText(veHUD.fontMoTaQuyDoiVe, "Tổng vé khóa nhận: "+tongVeKhoa);
            veHUD.fontMoTaQuyDoiVe.draw(batch, veHUD.layout, 401,60+75);
            batch.draw(veQuayKhoa,401+veHUD.layout.width+3f,60+75 - veHUD.layout.height/2f - (veQuayKhoa.getHeight()/5f)/2f,veQuayKhoa.getWidth()/5f,veQuayKhoa.getHeight()/5f);

            veHUD.fontMoTaQuyDoiVe.setColor(1.0f, 0.956f, 0.863f, 1f);
            veHUD.layout.setText(veHUD.fontMoTaQuyDoiVe, "Tổng vàng cần: "+veHUD.formatVangNgoc(tongVang));
            veHUD.fontMoTaQuyDoiVe.draw(batch, veHUD.layout, 401,60+95);
            batch.draw(vatPhamGachaKrandom[13],401+veHUD.layout.width+3f,60+95 - veHUD.layout.height/2f - (vatPhamGachaKrandom[13].getHeight()/2f)/2f,vatPhamGachaKrandom[13].getWidth()/2f,vatPhamGachaKrandom[13].getHeight()/2f);
        }

        if (chucNangQuyDoiVeDangChon == 1) {
            batch.draw(shopVip,396f-0.4f,259,shopVip.getWidth(),shopVip.getHeight());
            for (int i = 0; i < 3; i++) {
                float nutW = 68 * 0.7f,nutH = 52 * 0.7f;
                float nutX = 396f - 0.4f + (140 - nutW)/2f + 151*i, nutY = 260 + 3f;
                batch.draw(timeChoThemItemVeQuay > 0 && nutThemDangChon == i ? veHUD.nutchucnangclick : veHUD.nutchucnang, nutX, nutY, nutW, nutH);
            }
            batch.draw(timeChoMuaItemVip > 0 ? veHUD.nutclick : veHUD.nutdn,396+(435-140*0.7f),60+40,140*0.7f,48*0.7f);

            veHUD.fontMoTaQuyDoiVe.setColor(1.0f, 0.956f, 0.863f, 1f);
            veHUD.layout.setText(veHUD.fontMoTaQuyDoiVe, "Số vé VIP: "+ (duLieuNguoiChoi.checkCoItemTrongHanhTrang("ve_quay_npc_haidang") ? duLieuNguoiChoi.layItemTrongHanhTrang("ve_quay_npc_haidang").getSoLuong() : "0"));
            veHUD.fontMoTaQuyDoiVe.draw(batch, veHUD.layout, 401,60+55);
            batch.draw(veQuay,401+veHUD.layout.width+3f,60+55 - veHUD.layout.height/2f - (veQuay.getHeight()/5f)/2f,veQuay.getWidth()/5f,veQuay.getHeight()/5f);

            veHUD.fontMoTaQuyDoiVe.setColor(1.0f, 0.956f, 0.863f, 1f);
            veHUD.layout.setText(veHUD.fontMoTaQuyDoiVe, "Tổng vé VIP nhận: "+tongVeVip);
            veHUD.fontMoTaQuyDoiVe.draw(batch, veHUD.layout, 401,60+75);
            batch.draw(veQuay,401+veHUD.layout.width+3f,60+75 - veHUD.layout.height/2f - (veQuay.getHeight()/5f)/2f,veQuay.getWidth()/5f,veQuay.getHeight()/5f);


            veHUD.fontMoTaQuyDoiVe.setColor(1.0f, 0.956f, 0.863f, 1f);
            veHUD.layout.setText(veHUD.fontMoTaQuyDoiVe, "Tổng ngọc cần: "+tongNgoc);
            veHUD.fontMoTaQuyDoiVe.draw(batch, veHUD.layout, 401,60+95);
            batch.draw(vatPhamGachaKrandom[3],401+veHUD.layout.width+3f,60+95 - veHUD.layout.height/2f - (vatPhamGachaKrandom[3].getHeight()/2f)/2f,vatPhamGachaKrandom[3].getWidth()/2f,vatPhamGachaKrandom[3].getHeight()/2f);
        }

        for (int i = 0; i < 3; i++) {
            float nutW = 68 * 0.7f,nutH = 52 * 0.7f;
            float nutX = 396f - 0.4f + (140 - nutW)/2f + 151*i, nutY = 260 + 3f;
            veHUD.layout.setText(veHUD.fontChucnang1,"Thêm");
            veHUD.fontChucnang1.draw(batch,veHUD.layout,nutX+(nutW-veHUD.layout.width)/2f,nutY+(nutH+veHUD.layout.height)/2f);
        }

        veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.layout.setText(veHUD.font, "Mua");
        veHUD.font.draw(batch, veHUD.layout, 396+(435-140*0.7f) + (140*0.7f - veHUD.layout.width) / 2f, 60+40 + (48*0.7f+veHUD.layout.height)/2f);

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
        if (trangThai == TrangThaiChucNang_admin_haidang.GACHA_THONG_BAO) {
            batch.draw(veHUD.anhThongBao, (Gdx.graphics.getWidth() - 720) / 2f, 65, 720, 175);

            String text = dangGachaX1hayX10 == 1 ? "Chúc mừng bạn đã nhận được phần quà sau" : "Chúc mừng bạn đã nhận được những phần quà sau";
            veHUD.fontTenSkill.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
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
    public void renderHuongDanCachChoi(SpriteBatch batch) {
        if (!(trangThai == TrangThaiChucNang_admin_haidang.HUONG_DAN || trangThai == TrangThaiChucNang_admin_haidang.GACHA_THONG_BAO && trangThaiTruoc == TrangThaiChucNang_admin_haidang.HUONG_DAN)) return;
        veHUD.layout.setText(
            veHUD.fontMotaHanhTrang,
            "Hướng dẫn Mini Game Gacha" + "\n" +
                "- Người chơi cần có Vé Thường hoặc Vé VIP để quay." + "\n" +
                "- Chọn quay x1 (1 vé) hoặc quay x10 (10 vé liên tiếp)." + "\n" +
                "- Mỗi lượt quay sẽ nhận được vật phẩm ngẫu nhiên:" + "\n" +
                "   + Vật phẩm thường: nguyên liệu, item cơ bản." + "\n" +
                "   + Vật phẩm VIP: cải trang, phụ kiện hiếm." + "\n" +
                "   + Vật phẩm siêu hiếm: giới hạn, cực quý." + "\n" +
                "- Tỉ lệ xuất hiện vật phẩm siêu hiếm: 0.06%." + "\n" +
                "- Soft Pity: từ lượt quay thứ 76, tỉ lệ ra đồ siêu hiếm tăng dần." + "\n" +
                "- Hard Pity: đảm bảo ra đồ siêu hiếm ở lượt quay thứ 90." + "\n" +
                "- Trước khi quay hãy đảm bảo hành trang còn trống." + "\n" +
                "- Vé Gacha có thể mua hoặc đổi trong Shop." + "\n" +
                "Bạn đã sẵn sàng thử vận may chưa?",
            new Color(0, 0, 0, 1),
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

        float nutX = (Gdx.graphics.getWidth()-114)/2f;
        float nutY = 120 - 115;
        batch.draw(timeBamNutOkHuongDan > 0 ? veHUD.nutvuongclick : veHUD.nutvuong, nutX, nutY, 114, 114);

        veHUD.font.setColor(83 / 255f, 41 / 255f, 5 / 255f, 1);
        veHUD.layout.setText(veHUD.font, "OK");
        veHUD.font.draw(batch, veHUD.layout, nutX + (114 - veHUD.layout.width) / 2f, nutY + 114 - 52);
    }

    private void capNhatClickNut() {
        if (timeClickNut <= 0) return;

        timeClickNut -= Gdx.graphics.getDeltaTime();
        if (timeClickNut > 0) return;

        timeClickNut = 0;
        switch (nutChucNangDangChon) {
            case 0: trangThai = TrangThaiChucNang_admin_haidang.GACHA; break;
            case 1: trangThai = TrangThaiChucNang_admin_haidang.QUY_DOI; break;
            case 2: trangThai = TrangThaiChucNang_admin_haidang.HUONG_DAN; break;
        }

        switch (trangThai) {
            case GACHA:
                if (veHUD.isDangPhatNhac()) {
                    for (int i = 1; i < veHUD.nhacNen.length; i++) {
                        if (veHUD.nhacNen[i].isPlaying() && i != 11) {
                            veHUD.nhacNen[i].stop();
                            veHUD.nhacNen[11].play();
                            veHUD.setTinNhanPet("Đang phát bài Seasons", 2f);
                        }
                    }
                }
                break;
            case QUY_DOI:
                chucNangQuyDoiVeDangChon = 0;
                break;
            case HUONG_DAN:
                break;
        }
        dangHienChucNang = true;
    }

    private void capNhatGacha() {
        if (!dangGacha || timeSauGacha > 0) return;

        float timeDoi = 0.1f;
        if (chisovong >= maxQuay - 4) timeDoi = 0.3f;
        if (chisovong >= maxQuay - 1) timeDoi = 0.7f;

        timeDoiFrame += Gdx.graphics.getDeltaTime();
        if (timeDoiFrame >= timeDoi) {
            timeDoiFrame = 0;
            if (chisovong < maxQuay) chisovong++;
            if (chisovong == maxQuay) {
                timeSauGacha = 0.5f;
                chisovongtruoc = chisovong;
            }
        }
    }

    private void capNhatSauGacha() {
        if (timeSauGacha <= 0) return;

        timeSauGacha -= Gdx.graphics.getDeltaTime();
        if (timeSauGacha > 0) return;

        timeSauGacha = 0;
        dangGacha = false;
        pity = demKhongVIP;

        duLieuNguoiChoi.giamVeQuayKhoa(dangGachaX1hayX10);
        int[] mang = (dangGachaX1hayX10 == 1) ? mangGacha1 : mangGacha10;
        Texture[] mangDuocTruyen = (dangGachaX1hayX10 == 1) ? mangAnhGacha1 : mangAnhGacha10;

        ThemItemTest themItemTest = new ThemItemTest(duLieuNguoiChoi, nhanVat, veHUD);
        for (int i = 0; i < mang.length; i++) {
            themItemTest.themQuaGacha(mang[i], i, mangDuocTruyen);
        }

        trangThaiTruoc = trangThai;
        trangThai = TrangThaiChucNang_admin_haidang.GACHA_THONG_BAO;
    }

    private void capNhatTruocGacha() {
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
                    ham_tien_ich.gacha(soVeCan,this);
                    soVeKhoaTamThoi = duLieuNguoiChoi.getSoVeQuayKhoa() - soVeCan;
                }
            }
        }
    }

    private void capNhatFrameVip() {
        timeDoiFrameVip += Gdx.graphics.getDeltaTime();
        if (timeDoiFrameVip >= 0 && timeDoiFrameVip < 0.5f) {
            alphaGenshin = 1 - (0.5f - timeDoiFrameVip) / 0.5f;
        }
        if (timeDoiFrameVip >= 2.5f && timeDoiFrameVip < 3f) {
            alphaGenshin = (3f - timeDoiFrameVip) / 0.5f;
        }
        if (timeDoiFrameVip >= 3f) {
            timeDoiFrameVip = 0;
            frameGenshin = (frameGenshin + 1) % 2;
            frameHuyHieu = (frameHuyHieu + 1) % 4;
            frameDeoLung = (frameDeoLung + 1) % 7;
        }
    }

    private void capNhatNutOkSauGacha() {
        if (timeBamNutOk > 0) {
            timeBamNutOk -= Gdx.graphics.getDeltaTime();
            if (timeBamNutOk <= 0) {
                trangThai = trangThaiTruoc;
            }
        }
    }

    private void capNhatTatChucNang() {
        if (timeChoTatChucNang > 0) {
            timeChoTatChucNang -= Gdx.graphics.getDeltaTime();
            if (timeChoTatChucNang <= 0) {
                trangThai = TrangThaiChucNang_admin_haidang.NONE;
                dangHienChucNang = false;
            }
        }
    }

    private void capNhatChatDoiVeQuay() {
        if (timeChoHienChatDoiVeQuay > 0) {
            timeChoHienChatDoiVeQuay -= Gdx.graphics.getDeltaTime();
            if (timeChoHienChatDoiVeQuay <= 0) {
                timeChoHienChatDoiVeQuay = 0;
                if (!dangHienChatDoiVeQuay) {
                    dangHienChatDoiVeQuay = true;
                    nutDuocChonKhiChat = -1;
                } else {
                    xuLyChatDoiVeQuay();
                }
            }
        }
    }

    private void xuLyChatDoiVeQuay() {
        if (nutDuocChonKhiChat == 0) {
            String loi = null;
            if (tinNhanChat.isEmpty() || !ham_tien_ich.chuoiToanSo(tinNhanChat)) {
                loi = "Vui lòng nhập số vé cần quy đổi";
            } else if (!duLieuNguoiChoi.checkCoItemTrongHanhTrang("ve_quay_npc_haidang")) {
                loi = "Không có item trong hành trang";
            } else if (duLieuNguoiChoi.layItemTrongHanhTrang("ve_quay_npc_haidang").getSoLuong() < Integer.parseInt(tinNhanChat)) {
                loi = "Số lượng quy đổi không hợp lệ";
            } else if (duLieuNguoiChoi.getSoVeQuayKhoa() + Integer.parseInt(tinNhanChat) >= 1000) {
                loi = "Số vé quay khóa đã đạt tối đa";
            }

            if (loi != null) {
                veHUD.setTinNhanPet(loi,2f);
            } else {
                duLieuNguoiChoi.layItemTrongHanhTrang("ve_quay_npc_haidang").giamSoLuong(Integer.parseInt(tinNhanChat));
                if (duLieuNguoiChoi.layItemTrongHanhTrang("ve_quay_npc_haidang").getSoLuong() == 0) {
                    duLieuNguoiChoi.getHanhTrang().remove(duLieuNguoiChoi.layItemTrongHanhTrang("ve_quay_npc_haidang"));
                }
                duLieuNguoiChoi.tangVeQuayKhoa(Integer.parseInt(tinNhanChat));
                veHUD.setTinNhanPet("Quy đổi thành công",2f);
            }
        }
        dangHienChatDoiVeQuay = false;
        tinNhanChat = "";
        nutDuocChonKhiChat = -1;
    }

    private void capNhatThemItemVeQuay() {
        if (timeChoThemItemVeQuay > 0) {
            timeChoThemItemVeQuay -= Gdx.graphics.getDeltaTime();
            if (timeChoThemItemVeQuay <= 0) {
                timeChoThemItemVeQuay = 0;
                if (chucNangQuyDoiVeDangChon == 0) {
                    xuLyThemVeBangVang();
                } else if (chucNangQuyDoiVeDangChon == 1) {
                    xuLyThemVeBangNgoc();
                }
            }
        }
    }

    private void xuLyThemVeBangVang() {
        long tongVangCong = 0;
        int tongVeNhan = 0;
        switch (nutThemDangChon) {
            case 0 -> { tongVangCong = 100_000_000L; tongVeNhan = 1; }
            case 1 -> { tongVangCong = 1_000_000_000L; tongVeNhan = 10; }
            case 2 -> { tongVangCong = 10_000_000_000L; tongVeNhan = 100; }
        }

        String loi = null;
        if (duLieuNguoiChoi.getSoVeQuayKhoa() + tongVeKhoa + tongVeNhan >= 1000) {
            loi = "Vé quay khóa đã đầy";
        } else if (duLieuNguoiChoi.getVang() < tongVang + tongVangCong) {
            loi = "Không đủ vàng";
        }

        if (loi != null) {
            veHUD.setTinNhanPet(loi,2f);
        } else {
            tongVang += tongVangCong;
            tongVeKhoa += tongVeNhan;
        }
    }

    private void xuLyThemVeBangNgoc() {
        int tongNgocCong = 0;
        int tongVeNhan = 0;
        switch (nutThemDangChon) {
            case 0 -> { tongNgocCong = 100; tongVeNhan = 1; }
            case 1 -> { tongNgocCong = 1000; tongVeNhan = 10; }
            case 2 -> { tongNgocCong = 10000; tongVeNhan = 100; }
        }

        String loi = null;
        if (duLieuNguoiChoi.checkCoItemTrongHanhTrang("ve_quay_npc_haidang") &&
            duLieuNguoiChoi.layItemTrongHanhTrang("ve_quay_npc_haidang").getSoLuong() + tongVeVip + tongVeNhan >= 1000) {
            loi = "Vé quay VIP đã đầy";
        } else if (duLieuNguoiChoi.getNgoc() < tongNgoc + tongNgocCong) {
            loi = "Không đủ ngọc";
        }

        if (loi != null) {
            veHUD.setTinNhanPet(loi,2f);
        } else {
            tongNgoc += tongNgocCong;
            tongVeVip += tongVeNhan;
        }
    }

    private void capNhatMuaItemThuong() {
        if (timeChoMuaItemThuong > 0) {
            timeChoMuaItemThuong -= Gdx.graphics.getDeltaTime();
            if (timeChoMuaItemThuong <= 0 && tongVeKhoa > 0) {
                duLieuNguoiChoi.tangVeQuayKhoa(tongVeKhoa);
                duLieuNguoiChoi.giamVang(tongVang);
                veHUD.setTinNhanPet("Nhận thành công " + tongVeKhoa + " vé quay khóa", 2f);
                tongVang = 0;
                tongVeKhoa = 0;
            }
        }
    }

    private void capNhatMuaItemVip() {
        if (timeChoMuaItemVip > 0) {
            timeChoMuaItemVip -= Gdx.graphics.getDeltaTime();
            if (timeChoMuaItemVip <= 0 && tongVeVip > 0) {
                duLieuNguoiChoi.themItemVaoHanhTrang(new Item(
                    "ve_quay_npc_haidang", "Vé quay Rồng Thần", LoaiItem.VE_QUAY_NPC_HAIDANG,
                   "vatpham/vatphamgame/ve_quay_npc_haidang/vequay.png",
                    "Vé quay ẩn chứa tiềm năng vô hạn. Cần gặp NPC Admin Hải Đăng để sử dụng và có cơ hội nhận những phần thưởng hiếm và nhiều phần quà giá trị khác",
                    tongVeVip,
                    new int[13],
                    "all", 0, null, 0, 0, 0, -1
                ));
                veHUD.setTinNhanPet("Nhận thành công " + tongVeVip + " vé quay VIP", 2f);
                duLieuNguoiChoi.giamNgoc(tongNgoc);
                tongNgoc = 0;
                tongVeVip = 0;
            }
        }
    }

    public void capNhatNutOkHuongDan() {
        if (timeBamNutOkHuongDan > 0) {
            timeBamNutOkHuongDan -= Gdx.graphics.getDeltaTime();
            if (timeBamNutOkHuongDan <= 0) {
                timeBamNutOkHuongDan = 0;
                trangThai = TrangThaiChucNang_admin_haidang.NONE;
            }
        }
    }
}
