package com.dang.dragonboy.hien_thi;

import com.badlogic.gdx.math.MathUtils;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;

public class HUDThoiGianMiniGame {
    private VeHUD veHUD;
    private DuLieuNguoiChoi duLieuNguoiChoi;

    public HUDThoiGianMiniGame(VeHUD veHUD, DuLieuNguoiChoi duLieuNguoiChoi) {
        this.veHUD = veHUD;
        this.duLieuNguoiChoi = duLieuNguoiChoi;
    }
    public void capNhatThoiGian(float delta) {
        veHUD.timeMiniGame -= delta;
        veHUD.timeMiniGameChanLe -= delta;
        if (veHUD.timeMiniGame<=0) {
            int conSoMayMan = MathUtils.random(1, 99);
            if (veHUD.soNguoiChoiChon == conSoMayMan) {
                duLieuNguoiChoi.tangNgoc(veHUD.soNgocCuoc*90);
                veHUD.soNgocDuocNhanGanNhat = veHUD.soNgocCuoc*90;
                veHUD.dangHienTinNhanPet = true;
                veHUD.timeHienTinNhanPet = 2f;
                veHUD.tinNhanPet = "Chúc mừng người chơi "+duLieuNguoiChoi.getTen()+" đã may mắn nhận được "+veHUD.formatVangNgoc(veHUD.soNgocCuoc*90)+" ngọc xanh từ tính năng Mini Game";
            }
            veHUD.soNguoiChoiChon = 0;
            veHUD.soNgocCuoc = 0;
            veHUD.ketQuaGiaiTruoc = conSoMayMan;
            veHUD.timeMiniGame = 60f;
        }
        if (veHUD.timeMiniGameChanLe<=0) {
            int conSoMayMan = MathUtils.random(1, 99);
            if ((veHUD.NguoiChoiChonChanLe.equals("chan") && conSoMayMan%2==0) || (veHUD.NguoiChoiChonChanLe.equals("le") && conSoMayMan%2!=0)) {
                duLieuNguoiChoi.tangVang((int)(veHUD.soVangCuocChanLe*1.9f));
                veHUD.soVangDuocNhanGanNhatChanLe = (int)(veHUD.soVangCuocChanLe*1.9f);
                veHUD.dangHienTinNhanPet = true;
                veHUD.timeHienTinNhanPet = 2f;
                veHUD.tinNhanPet = "Chúc mừng người chơi "+duLieuNguoiChoi.getTen()+" đã may mắn nhận được "+veHUD.formatVangNgoc((int)(veHUD.soVangCuocChanLe*1.9f))+" vàng từ tính năng Mini Game";
            }
            veHUD.NguoiChoiChonChanLe = "";
            veHUD.soVangCuocChanLe = 0;
            veHUD.ketQuaGiaiTruocChanLe = conSoMayMan;
            veHUD.timeMiniGameChanLe = 30f;
        }
    }
}
