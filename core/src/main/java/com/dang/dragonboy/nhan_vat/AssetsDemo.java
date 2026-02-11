package com.dang.dragonboy.nhan_vat;
import com.badlogic.gdx.graphics.Texture;

public class AssetsDemo {
    public static Texture dau;
    public static Texture than;
    public static Texture chan;

    public static void loadOnce() {
        if (dau != null) return;

        dau  = new Texture("nhanvat/traidat/avatar/Goku_base/daudung.png");
        than = new Texture("nhanvat/traidat/do/set_cam/thandung.png");
        chan = new Texture("nhanvat/traidat/do/set_cam/chandung.png");
    }
}
