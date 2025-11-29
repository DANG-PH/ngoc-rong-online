package com.dang.dragonboy.network.DTO;

import java.util.*;

public class UserResponse {
    public Long id;
    public String username;
    public String password;

    // dữ liệu luu từ server
    public long vang;
    public long ngoc;
    public long sucManh;
    public long vangNapTuWeb;
    public long ngocNapTuWeb;

    public float x;
    public float y;
    public String mapHienTai;

    public boolean daVaoTaiKhoanLanDau;

    public String hanhTinh = "traidat";
    public String nhanVat = "Goku";
    public String tenNhanVat = "admin";

    public boolean coDeTu;

    public DeTuTheoUser deTu;

    public boolean biBan;
    public String role;

    public List<Integer> danhSachVatPhamWeb;
}
