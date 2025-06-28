package com.dang.dragonboy.nhan_vat;

public class DoLechModular {
    public float lechThanX, lechThanY;
    public float lechDauX, lechDauY;

    public DoLechModular(float ltx, float lty, float ldx, float ldy) {
        this.lechThanX = ltx;
        this.lechThanY = lty;
        this.lechDauX = ldx;
        this.lechDauY = ldy;
    }

    // Hàm cộng dồn 2 offset
    public DoLechModular cong(DoLechModular Khac) {
        return new DoLechModular(
            this.lechThanX + Khac.lechThanX,
            this.lechThanY + Khac.lechThanY,
            this.lechDauX + Khac.lechDauX,
            this.lechDauY + Khac.lechDauY
        );
    }
}
