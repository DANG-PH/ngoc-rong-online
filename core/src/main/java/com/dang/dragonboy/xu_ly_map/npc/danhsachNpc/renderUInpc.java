package com.dang.dragonboy.xu_ly_map.npc.danhsachNpc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.xu_ly_map.npc.Npc;

public abstract class renderUInpc {
    protected NhanVat nhanVat;
    protected DuLieuNguoiChoi duLieuNguoiChoi;
    protected Npc npc;
    protected VeHUD veHUD;

    public renderUInpc(Npc npc, VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi, NhanVat nhanVat) {
        this.nhanVat = nhanVat;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
        this.npc = npc;
        this.veHUD = veHUD;
    }

    public abstract void render(SpriteBatch batch);

    public abstract void capNhat();
}
