package com.dang.dragonboy.xu_ly_map;

public class HitboxDat {
    public float x, y, width, height;

    public HitboxDat(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Va chạm từ TRÊN (để xử lý tiếp đất)
    public boolean vaChamTuTren(float nx, float ny, float nrong, float ncao, float vy) {
        return vy <= 0 &&
            nx + nrong > x &&
            nx < x + width &&
            ny >= y + height - 5 &&
            ny <= y + height;
    }

    // Va chạm từ DƯỚI (đập đầu vào nền phía trên)
    public boolean vaChamTuDuoi(float nx, float ny, float nrong, float ncao, float vy) {
        return vy > 0 &&
            nx + nrong > x &&
            nx < x + width &&
            ny + ncao >= y &&
            ny + ncao <= y + 5;
    }

    public boolean vaChamBenPhai(float nx, float ny, float nrong, float ncao) {
        return nx <= x + width && nx + nrong > x + width - 1.5 &&
            ny + ncao > y && ny < y + height;
    }

    public boolean vaChamBenTrai(float nx, float ny, float nrong, float ncao) {
        return nx + nrong >= x && nx < x + 1.5 &&
            ny + ncao > y && ny < y + height;
    }
}
