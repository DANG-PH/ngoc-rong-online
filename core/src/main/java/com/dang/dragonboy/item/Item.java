package com.dang.dragonboy.item;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private String maItem;
    private String ten;
    private transient LoaiItem loaiItem;
    private String loai;
    private transient Texture texture;
    private String linkTexture;
    private String moTa;
    private int soLuong;
    private int[] chiso ;
    private String hanhtinh;
    private String setkichhoat;
    private int soSaoPhaLe, soCap;
    private int soSaoPhaLeCuongHoa;
    private float HanSuDung;
    private long sucManhYeuCau;
    private String viTri;

    public Item(String maItem, String ten, LoaiItem loai, String linkTexture, String moTa, int soLuong, int[] chiso, String hanhtinh, long sucManhYeuCau, String setkichhoat, int soSaoPhaLe,int soSaoPhaLeCuongHoa, int soCap, float HanSuDung) {
        this.maItem = maItem;
        this.ten = ten;
        this.loaiItem = loai;
        this.linkTexture = linkTexture;
        this.texture = new Texture(linkTexture);
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

        this.loai = loaiItem.name();
    }

    public String getId(){
        return maItem;
    }

    public String getSetkichhoat(){
        return setkichhoat;
    }

    public String getTenItem() {
        return ten;
    }

    public LoaiItem getLoai() {
        return loaiItem;
    }

    public String getLoaiDB() {
        return loai;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getLinkTexture() {
        return linkTexture;
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
    public void tangChiSo(int indexChiSoCanTang, int chiSoTang) {
        this.chiso[indexChiSoCanTang] += chiSoTang;
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

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }
    public String getViTri() {
        return viTri;
    }
}
