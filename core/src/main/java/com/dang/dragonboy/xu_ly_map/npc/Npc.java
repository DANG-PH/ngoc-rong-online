package com.dang.dragonboy.xu_ly_map.npc;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dang.dragonboy.nhan_vat.NhanVat;

public class Npc {
    static NhanVat nhanVat;
    String ten;
    String tenTiengVietNpc;
    float x, y;
    private NpcTaiAnh taiAnh;
    private NpcOffset offset;
    private GlyphLayout layout;
    private BitmapFont font, fontDauThan;
    private Texture muiTenNpc;
    private Texture[] clickNpc = new Texture[4];
    private float timeDoiFrame = 0f;
    private int frame = 0;
    private int soLanChay = 0;
    private boolean dangClickNpc2 = false;
    public boolean dangClickNpc = false;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    LoaiNPC loainpc;

    public Npc(String ten, LoaiNPC loainpc, float x, float y) {
        this.ten = ten;
        this.loainpc = loainpc;
        this.x = x;
        this.y = y;
        layout = new GlyphLayout();
        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/fontt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ";
        param.size = 18;
        font = generator.generateFont(param);
        generator.dispose();
        // Font có viền đen dành riêng cho dòng chữ "Đậu thần cấp ..."
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontchinh.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param2.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            "ăậâấốỐđêôơưáàảãạéèẻẽẹíìịóòỏõọúùủũụĂÂĐÊÔƠƯÁÀẢÃẠÉÈẺẼẸÍÌỊÓÒỎÕỌÚÙỦŨỤ ớ ồ ầ ể";
        param2.size = 22;
        param2.color = Color.WHITE;
        param2.borderWidth = 1f;
        param2.borderColor = new Color(0.4f, 0.4f, 0.4f, 1f);
        fontDauThan = generator2.generateFont(param2);
        generator2.dispose();

        muiTenNpc = new Texture("hud/giaodientrong/clicknpc.png");
        for (int i = 0; i < 4; i++) {
            clickNpc[i] = new Texture("hieuung/hieuunggame/click_npc/"+(i+1)+".png");
        }
    }
    public String getTen() {
        return ten;
    }
    public LoaiNPC getLoai() {
        return loainpc;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setNpcTaiAnh(NpcTaiAnh anhNpc) {
        this.taiAnh = anhNpc;
    }
    public void setNpcOffset(NpcOffset offsetNpc) {
        this.offset = offsetNpc;
    }
    public void setNhanVat(NhanVat nv) {
        nhanVat = nv;
    }

    public void ve(SpriteBatch batch, float thoiGianTichLuy) {
        capNhat();
        if (loainpc == LoaiNPC.NGUOI) {
            if (taiAnh == null || offset == null) return;
            float doDaoDong = (float) Math.sin(thoiGianTichLuy) * 1.08f;
            float scale = 0.5f;

            Texture chan = taiAnh.getChan();
            Texture than = taiAnh.getThan();
            Texture dau = taiAnh.getDau();

            float chanW = chan.getWidth() * scale;
            float chanH = chan.getHeight() * scale;
            float thanW = than.getWidth() * scale;
            float thanH = than.getHeight() * scale;
            float dauW = dau.getWidth() * scale;
            float dauH = dau.getHeight() * scale;

            // Tính vị trí vẽ theo tọa độ NPC cộng với offset và dao động
            float baseX = x + offset.getOffsetX();
            float baseY = y + offset.getOffsetY();

            // Vẽ chân
            batch.draw(chan, baseX, baseY, chanW, chanH);

            // Vẽ thân
            float thanX = baseX + chanW / 2f - thanW / 2f + offset.getThanXOffset();
            float thanY = baseY + chanH + doDaoDong;
            batch.draw(than, thanX, thanY - 10.2f + offset.getThanYOffset(), thanW, thanH);

            // Vẽ đầu
            float dauX = baseX + chanW / 2f - dauW / 2f + offset.getDauXOffset();
            float dauY = thanY + thanH;
            batch.draw(dau, dauX, dauY + offset.getDauYOffset(), dauW, dauH);

            //Vẽ mũi tên Npc nếu đang click
            float tenY = baseY + chanH + dauH + 30;
            if (dangClickNpc) {
                batch.draw(muiTenNpc, baseX + (chanW - muiTenNpc.getWidth() * 0.5f) / 2f, tenY, muiTenNpc.getWidth() * 0.5f, muiTenNpc.getHeight() * 0.5f);
            }

            // ve Ten Npc
            setTenTiengVietNpc();
            font.setColor(Color.YELLOW);
            layout.setText(font, tenTiengVietNpc);
            float tenX = baseX + (chanW - layout.width) / 2f;
            font.draw(batch, layout, tenX, dangClickNpc ? tenY + 40f : tenY);
            font.setColor(Color.WHITE);

            // Nếu click nhiều lần
            if (dangClickNpc2) {
                batch.draw(clickNpc[frame], baseX + (chanW - clickNpc[frame].getWidth() * 0.5f) / 2f, tenY, clickNpc[frame].getWidth() * 0.5f, clickNpc[frame].getHeight() * 0.5f);
            }
        } else {
            if (taiAnh == null) return;

            float scale = 0.5f;

            Texture anh = taiAnh.getAnhNpc();

            float anhW = anh.getWidth() * scale;
            float anhH = anh.getHeight() * scale;

            // Vẽ chân
            batch.draw(anh, x, y, anhW, anhH);

            //Vẽ mũi tên Npc nếu đang click
            float tenY = y + anhH + 30;
            if (dangClickNpc) {
                batch.draw(muiTenNpc, x + (anhW - muiTenNpc.getWidth() * 0.5f) / 2f, tenY, muiTenNpc.getWidth() * 0.5f, muiTenNpc.getHeight() * 0.5f);
            }

            // ve Ten Npc
            setTenTiengVietNpc();
            if (loainpc == LoaiNPC.CAYDAU) {
                layout.setText(fontDauThan, tenTiengVietNpc);
                fontDauThan.draw(batch, layout,
                    x + (anhW - layout.width) / 2f,
                    y + anhH + 15
                );
                if (nhanVat.getX() >= 410 && nhanVat.getX() <= 850) {
                    batch.end();
                    Gdx.gl.glEnable(GL20.GL_BLEND);
                    shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                    shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.rect(
                        600 + (anhW) / 2f - 90,
                        192 + anhH + 35,
                        180,
                        80);
                    shapeRenderer.end();
                    batch.begin();
                    layout.setText(fontDauThan, "Có thể thu hoạch");
                    fontDauThan.draw(batch, layout,
                        600 + (anhW - layout.width) / 2f,
                        192 + anhH + 90
                    );
                    String[] parts = ten.split("_");
                    int capCayDau = Integer.parseInt(parts[2]);
                    layout.setText(fontDauThan, (2 * capCayDau + 3) + "/" + (2 * capCayDau + 3));
                    fontDauThan.draw(batch, layout,
                        600 + (anhW - layout.width) / 2f,
                        192 + anhH + 65
                    );
                }
            } else {
                setTenTiengVietNpc();
                font.setColor(Color.YELLOW);
                layout.setText(font, tenTiengVietNpc);
                float tenX = x + (anhW - layout.width) / 2f;
                font.draw(batch, layout, tenX, dangClickNpc ? tenY + 40f : tenY);
                font.setColor(Color.WHITE);
            }
            // Nếu click nhiều lần
            if (dangClickNpc2) {
                batch.draw(clickNpc[frame], x + (anhW - clickNpc[frame].getWidth() * 0.5f) / 2f, tenY, clickNpc[frame].getWidth() * 0.5f, clickNpc[frame].getHeight() * 0.5f);
            }
        }
    }

    public void checkClick(float x_check, float y_check) {
        if (loainpc == LoaiNPC.NGUOI) {
            if (x_check >= x && x_check <= x + taiAnh.getChan().getWidth() * 0.5f &&
                y_check >= y && y_check <= y + taiAnh.getChan().getHeight() * 0.5f
                + taiAnh.getThan().getHeight() * 0.5f + taiAnh.getDau().getHeight() * 0.5f) {
                if (!dangClickNpc) {
                    dangClickNpc = true;
                    nhanVat.vuaClick = false;
                } else {
                    if (nhanVat.vuaClick) {
                        dangClickNpc2 = true;
                        nhanVat.vuaClick = false;
                    }
                }
            } else {
                dangClickNpc = false;
                dangClickNpc2 = false;
            }
        } else {
            // npc chỉ có 1 ảnh
            if (x_check >= x && x_check <= x + taiAnh.getAnhNpc().getWidth() * 0.5f &&
                y_check >= y && y_check <= y + taiAnh.getAnhNpc().getHeight() * 0.5f) {
                if (!dangClickNpc) {
                    dangClickNpc = true;
                    nhanVat.vuaClick = false;
                } else {
                    if (nhanVat.vuaClick) {
                        dangClickNpc2 = true;
                        nhanVat.vuaClick = false;
                    }
                }
            } else {
                dangClickNpc = false;
                dangClickNpc2 = false;
            }
        }
    }

    public void setTenTiengVietNpc() {
        switch (ten) {
            case "ong_gohan" -> tenTiengVietNpc = "Ông Gôhan";
            case "admin_haidang" -> tenTiengVietNpc = "Admin Hải Đăng";
            case "admin_thanhle" -> tenTiengVietNpc = "Admin Thành Lê";
            case "admin_dungle" -> tenTiengVietNpc = "Admin Dũng Lê";
            case "dau_traidat_1" -> tenTiengVietNpc = "Đậu thần cấp 1";
            case "dau_traidat_2" -> tenTiengVietNpc = "Đậu thần cấp 2";
            case "dau_traidat_3" -> tenTiengVietNpc = "Đậu thần cấp 3";
            case "dau_traidat_4" -> tenTiengVietNpc = "Đậu thần cấp 4";
            case "dau_traidat_5" -> tenTiengVietNpc = "Đậu thần cấp 5";
            case "dau_traidat_6" -> tenTiengVietNpc = "Đậu thần cấp 6";
            case "dau_traidat_7" -> tenTiengVietNpc = "Đậu thần cấp 7";
            case "dau_traidat_8" -> tenTiengVietNpc = "Đậu thần cấp 8";
            case "dau_traidat_9" -> tenTiengVietNpc = "Đậu thần cấp 9";
            case "dau_traidat_10" -> tenTiengVietNpc = "Đậu thần cấp 10";
            case "ruong_do" -> tenTiengVietNpc = "Rương đồ";
            default -> tenTiengVietNpc = "";
        }
    }

    public void capNhat() {
        if (dangClickNpc2) {
            timeDoiFrame += Gdx.graphics.getDeltaTime();
            if (timeDoiFrame > 0.06f) {
                frame=(frame+1)%clickNpc.length;
                soLanChay++;
                timeDoiFrame = 0f;
            }
            if (soLanChay >= clickNpc.length*2) {
                dangClickNpc2 = false;
                soLanChay = 0;
                frame = 0;
            }
        }
    }
}
