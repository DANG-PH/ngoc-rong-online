package com.dang.dragonboy.nhan_vat;

public class DoLechModular {
    public float lechThanX, lechThanY;
    public float lechDauX, lechDauY;
    public float lechChanX, lechChanY;

    public DoLechModular(float ltx, float lty, float ldx, float ldy, float lcx, float lcy) {
        this.lechThanX = ltx;
        this.lechThanY = lty;
        this.lechDauX = ldx;
        this.lechDauY = ldy;
        this.lechChanX = lcx;
        this.lechChanY = lcy;
    }

    // Hàm cộng dồn 2 offset
    public DoLechModular cong(DoLechModular Khac) {
        return new DoLechModular(
            this.lechThanX + Khac.lechThanX,
            this.lechThanY + Khac.lechThanY,
            this.lechDauX + Khac.lechDauX,
            this.lechDauY + Khac.lechDauY,
            this.lechChanX + Khac.lechChanX,
            this.lechChanY + Khac.lechChanY
        );
    }
}
