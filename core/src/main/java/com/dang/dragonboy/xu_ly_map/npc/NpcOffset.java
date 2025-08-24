package com.dang.dragonboy.xu_ly_map.npc;

public class NpcOffset {
    public float dauX, dauY;
    public float thanX, thanY;
    public float chanX, chanY;

    public NpcOffset(float thanX, float thanY, float dauX, float dauY, float chanX, float chanY) {
        this.dauX = dauX;
        this.dauY = dauY;
        this.thanX = thanX;
        this.thanY = thanY;
        this.chanX = chanX;
        this.chanY = chanY;
    }
    public float getOffsetX() { return chanX; }
    public float getOffsetY() { return chanY; }
    public float getDauXOffset() { return dauX; }
    public float getDauYOffset() { return dauY; }
    public float getThanXOffset() { return thanX; }
    public float getThanYOffset() { return thanY; }
}
