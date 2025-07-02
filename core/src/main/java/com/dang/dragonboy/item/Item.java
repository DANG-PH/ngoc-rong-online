package com.dang.dragonboy.item;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private String id;
    private String ten;
    private LoaiItem loai;
    private Texture texture;
    private String moTa;
    private int soLuong;
    private int[] chiso ;
    private String setkichhoat;
    private int soSaoPhaLe, soCap;
    private float HanSuDung;

    public Item(String id, String ten, LoaiItem loai, Texture texture, String moTa, int soLuong, int[] chiso, String setkichhoat, int soSaoPhaLe, int soCap, float HanSuDung) {
        this.id = id;
        this.ten = ten;
        this.loai = loai;
        this.texture = texture;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.chiso = chiso;
        this.setkichhoat = setkichhoat;
        this.soSaoPhaLe = soSaoPhaLe;
        this.soCap = soCap;
        this.HanSuDung = HanSuDung;
    }

    public String getId(){
        return id;
    }

    public String getSetkichhoat(){
        return setkichhoat;
    }

    public String getTenItem() {
        return ten;
    }

    public LoaiItem getLoai() {
        return loai;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getMoTa() {
        return moTa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int[] getChiso(){
        return chiso;
    }

    public int getSoSaoPhaLe(){
        return soSaoPhaLe;
    }

    public int getSoCap() {
        return soCap;
    }

    public float getHanSuDung() {
        return HanSuDung;
    }

    public void suDung() {
        switch (loai) {
            case HOI_MAU:
                System.out.println("Hồi máu cho nhân vật...");
                break;
            case HOI_KI:
                System.out.println("Hồi KI cho nhân vật...");
                break;
            case CAITRANG:

        }
    }
}
