package com.dang.dragonboy.network.DTO;

public class ShopItemServerData {
    public int id;
    public String tenItem;
    public long gia;
    public String loaiTien; // "VANG" | "NGOC"
    public String tab;      // "AO_QUAN" | "PHU_KIEN" | "DAC_BIET"
    public boolean is_active;
}
