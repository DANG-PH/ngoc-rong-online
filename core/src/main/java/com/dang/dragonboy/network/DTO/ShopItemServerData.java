package com.dang.dragonboy.network.DTO;

public class ShopItemServerData {
    public int id;
    public int item_base_id;
    public String ten_item;  // ← dùng để mapping TEN_TO_INFO
    public String ma_item;
    public long gia;
    public String loaiTien;
    public String tab;
    public boolean is_active;
}
