package com.dang.dragonboy.du_lieu;

import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.network.*;

public class State_Management {
    private static UserResponse userResponse = null;
    private static String tenTaiKhoan = null;
    private static boolean dangDangNhap = false;
    private static DuLieuNguoiChoi duLieuNguoiChoi = null;
    private static VeHUD veHUD = null;
    private static NhanVat nhanVat = null;
    private static String token = "";

    public static void setDuLieuStateManagement(NhanVat nhanvatt, VeHUD veHUDD,DuLieuNguoiChoi duLieuNguoiChoii) {
        duLieuNguoiChoi = duLieuNguoiChoii;
        veHUD = veHUDD;
        nhanVat = nhanvatt;
    }

    public static void setUserResponse(UserResponse userResponsee) {
        userResponse = userResponsee;
    }

    public static DuLieuNguoiChoi getDuLieuNguoiChoi() {
        return duLieuNguoiChoi;
    }

    public static VeHUD getVeHUD() {
        return veHUD;
    }

    public static NhanVat getNhanVat() {
        return nhanVat;
    }

    public static boolean getDangDangNhap() {
        return dangDangNhap;
    }

    public static String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public static UserResponse getUserResponse() {
        return userResponse;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String tokenn) {
        token = tokenn;
    }
}
