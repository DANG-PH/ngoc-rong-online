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
    private String hanhtinh;
    private String setkichhoat;
    private int soSaoPhaLe, soCap;
    private int soSaoPhaLeCuongHoa;
    private float HanSuDung;
    private long sucManhYeuCau;

    public Item(String id, String ten, LoaiItem loai, Texture texture, String moTa, int soLuong, int[] chiso, String hanhtinh, long sucManhYeuCau, String setkichhoat, int soSaoPhaLe,int soSaoPhaLeCuongHoa, int soCap, float HanSuDung) {
        this.id = id;
        this.ten = ten;
        this.loai = loai;
        this.texture = texture;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.chiso = chiso;
        this.hanhtinh = hanhtinh;
        this.setkichhoat = setkichhoat;
        this.soSaoPhaLe = soSaoPhaLe;
        this.soCap = soCap;
        this.HanSuDung = HanSuDung;
        this.sucManhYeuCau = sucManhYeuCau;
        this.soSaoPhaLeCuongHoa = soSaoPhaLeCuongHoa;
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

    public void tangSoLuong(int soLuong) {
        this.soLuong += soLuong;
    }

    public void giamSoLuong(int soLuong) {
        this.soLuong -= soLuong;
    }

    public boolean tangSoSaoPhaLe() {
        if (this.soSaoPhaLe<7) {
            this.soSaoPhaLe++;
            return true;
        }
        return false;
    }

    public boolean tangSoSaoPhaLeCuongHoa() {
        if (this.soSaoPhaLeCuongHoa<7) {
            this.soSaoPhaLeCuongHoa++;
            return true;
        }
        return false;
    }

    public int[] getChiso(){
        return chiso;
    }

    public String getHanhtinh() { return hanhtinh;}

    public int getSoSaoPhaLe(){
        return soSaoPhaLe;
    }

    public int getSoSaoPhaLeCuongHoa() { return soSaoPhaLeCuongHoa; }

    public int getSoCap() {
        return soCap;
    }

    public float getHanSuDung() {
        return HanSuDung;
    }

    public long getSucManhYeuCau() { return sucManhYeuCau; }

    public void tangHanSuDung() {
        this.HanSuDung += 1/60f;
        this.HanSuDung = Math.max(0,Math.min(this.HanSuDung,1800f));
    }

    public void giamHanSuDung() {
        this.HanSuDung -= 1/60f;
        this.HanSuDung = Math.max(0,Math.min(this.HanSuDung,1800f));
    }

}
