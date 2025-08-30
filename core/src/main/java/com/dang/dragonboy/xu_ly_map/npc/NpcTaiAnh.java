package com.dang.dragonboy.xu_ly_map.npc;

import com.badlogic.gdx.graphics.Texture;

public class NpcTaiAnh {
    public Texture chan;
    public Texture than;
    public Texture dau;
    public Texture avtNpc;

    public Texture anhNpc;

    public NpcTaiAnh(String npcName, LoaiNPC loaiNPC) {
        if (loaiNPC == LoaiNPC.NGUOI) {
            chan = new Texture("nhanvat/npc/" + npcName + "/chan.png");
            than = new Texture("nhanvat/npc/" + npcName + "/than.png");
            dau = new Texture("nhanvat/npc/" + npcName + "/dau.png");
            avtNpc = new Texture("nhanvat/npc/" + npcName + "/avt.png");
        } else if (loaiNPC == LoaiNPC.CAYDAU) {
            String[] parts = npcName.split("_");
            int capCayDau = Integer.parseInt(parts[2]);
            anhNpc = new Texture("nhanvat/npc/"+parts[0]+"_"+parts[1]+"/caydau"+capCayDau+".png");
        } else {
            anhNpc = new Texture("nhanvat/npc/"+npcName+"/1.png");
        }
    }

    public Texture getDau() { return dau; }
    public Texture getThan() { return than; }
    public Texture getChan() { return chan; }
    public Texture getAvtNpc() { return avtNpc; }
    public Texture getAnhNpc() { return anhNpc; }

    public void dispose() {
        if (chan != null) chan.dispose();
        if (than != null) than.dispose();
        if (dau != null) dau.dispose();
        if (anhNpc != null) anhNpc.dispose();
    }
}
