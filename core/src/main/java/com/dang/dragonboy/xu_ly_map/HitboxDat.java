package com.dang.dragonboy.xu_ly_map;

public class HitboxDat {
    public float x, y, width, height;

    public HitboxDat(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Phương thức kiểm tra va chạm (đây là cái bạn cần)
    public boolean kiemTraVaCham(float nx, float ny, float nrong, float ncao) {
        return nx + nrong > x && nx < x + width && ny <= y + height && ny + ncao >= y;
    }
}
