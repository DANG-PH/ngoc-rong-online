package com.dang.dragonboy.network.DTO;

import java.util.*;

public class UserResponse {
    public Long id;
    public String username;
    public String gameName;

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

    public boolean coDeTu;

    public DeTuTheoUser deTu;

    public boolean biBan;
    public String role;

    public List<Integer> danhSachVatPhamWeb;
}
