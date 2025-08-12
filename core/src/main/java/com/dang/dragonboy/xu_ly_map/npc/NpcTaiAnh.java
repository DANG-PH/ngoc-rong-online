package com.dang.dragonboy.xu_ly_map.npc;

import com.badlogic.gdx.graphics.Texture;

public class NpcTaiAnh {
    public Texture chan;
    public Texture than;
    public Texture dau;

    public NpcTaiAnh(String npcName) {
        chan = new Texture("nhanvat/npc/" + npcName + "/chan.png");
        than = new Texture("nhanvat/npc/" + npcName + "/than.png");
        dau  = new Texture("nhanvat/npc/" + npcName + "/dau.png");
    }

    public Texture getDau() { return dau; }
    public Texture getThan() { return than; }
    public Texture getChan() { return chan; }

    public void dispose() {
        chan.dispose();
        than.dispose();
        dau.dispose();
    }
}
