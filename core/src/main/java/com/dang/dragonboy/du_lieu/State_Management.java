package com.dang.dragonboy.du_lieu;

import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.network.DTO.UserResponse;
import com.dang.dragonboy.nhan_vat.NhanVat;

public class State_Management {
    private static UserResponse userResponse = null;
    private static String tenTaiKhoan = null;
    private static boolean dangDangNhap = false;
    private static DuLieuNguoiChoi duLieuNguoiChoi = null;
    private static VeHUD veHUD = null;
    private static NhanVat nhanVat = null;
    private static String token = "";
    private static String sessionId = "";
    private static String refresh_token = "";
    private static int auth_id = 0;
    private static String role = "";

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

    public static String getRefresh_token() {
        return refresh_token;
    }

    public static void setRefresh_token(String refresh_tokenn) {
        refresh_token = refresh_tokenn;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String rolee) {
        role = rolee;
    }

    public static int getAuth_id() {
        return auth_id;
    }

    public static void setAuth_id(int auth_idd) {
        auth_id = auth_idd;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionIdd) {
        sessionId = sessionIdd;
    }

    public static void setDuLieuNguoiChoi(DuLieuNguoiChoi duLieuNguoiChoii) {
        duLieuNguoiChoi = duLieuNguoiChoii;
    }
}
