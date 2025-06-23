package com.dang.dragonboy.nhan_vat.van_bay;

import com.badlogic.gdx.graphics.Texture;

public class VanBayCauHinh {
    public Texture[] frames;
    public float tile;
    public boolean flipVanBay;
    public float offsetX;
    public float offsetY;

    public VanBayCauHinh(Texture[] frames, float tile, boolean flipVanBay, float offsetX, float offsetY) {
        this.frames = frames;
        this.tile = tile;
        this.flipVanBay = flipVanBay;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
