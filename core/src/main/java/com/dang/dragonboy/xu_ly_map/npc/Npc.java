package com.dang.dragonboy.xu_ly_map.npc;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.Color;

public class Npc {
    String ten;
    String tenTiengVietNpc;
    float x, y;
    private NpcTaiAnh taiAnh;
    private NpcOffset offset;
    private GlyphLayout layout;
    private BitmapFont font;

    public Npc(String ten, float x, float y) {
        this.ten = ten;
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
    }
    public String getTen() {
        return ten;
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
    public void ve(SpriteBatch batch, float thoiGianTichLuy) {
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
        float thanY = baseY + chanH + doDaoDong + offset.getThanYOffset();
        batch.draw(than, thanX, thanY , thanW, thanH);

        // Vẽ đầu
        float dauX = baseX + chanW / 2f - dauW / 2f + offset.getDauXOffset();
        float dauY = thanY + thanH + offset.getDauYOffset();
        batch.draw(dau, dauX, dauY, dauW, dauH);

        // ve Ten Npc
        setTenTiengVietNpc();
        font.setColor(Color.YELLOW);
        layout.setText(font, tenTiengVietNpc);
        float tenX = baseX + (chanW - layout.width) / 2f;
        float tenY = baseY + chanH + dauH + 30;
        font.draw(batch, layout, tenX, tenY);
        font.setColor(Color.WHITE);
    }

    public void setTenTiengVietNpc() {
        switch (ten) {
            case "ong_gohan" -> tenTiengVietNpc = "Ông Gôhan";
            case "admin_haidang" -> tenTiengVietNpc = "Admin Hải Đăng";
            default -> tenTiengVietNpc = "";
        }
    }
}
