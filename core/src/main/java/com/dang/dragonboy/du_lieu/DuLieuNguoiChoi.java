package com.dang.dragonboy.du_lieu;

public class DuLieuNguoiChoi {
    private String ten;
    private long sucManh;
    private int theLuc;

    private int hpHienTai;
    private int hpToiDa;
    private int kiHienTai;
    private int kiToiDa;

    private int soDauThan;
    private long vang;
    private long ngoc;
    private String capBac;

    // Constructor
    public DuLieuNguoiChoi(String ten, long sucManh, int theLuc,
                           int hpHienTai, int hpToiDa,
                           int kiHienTai, int kiToiDa,
                           int soDauThan, long vang, long ngoc,
                           String capBac) {
        this.ten = ten;
        this.sucManh = sucManh;
        this.theLuc = theLuc;
        this.hpHienTai = hpHienTai;
        this.hpToiDa = hpToiDa;
        this.kiHienTai = kiHienTai;
        this.kiToiDa = kiToiDa;
        this.soDauThan = soDauThan;
        this.vang = vang;
        this.ngoc = ngoc;
        this.capBac = capBac;
    }

    // Getter
    public String getTen() { return ten; }
    public long getSucManh() { return sucManh; }
    public int getTheLuc() { return theLuc; }
    public int getHpHienTai() { return hpHienTai; }
    public int getHpToiDa() { return hpToiDa; }
    public int getKiHienTai() { return kiHienTai; }
    public int getKiToiDa() { return kiToiDa; }
    public int getSoDauThan() { return soDauThan; }
    public long getVang() { return vang; }
    public long getNgoc() { return ngoc; }
    public String getCapBac() { return capBac; }

    // Setter (khi chơi game sẽ cần thay đổi)
    public void setHpHienTai(int hpHienTai) { this.hpHienTai = hpHienTai; }
    public void setKiHienTai(int kiHienTai) { this.kiHienTai = kiHienTai; }
    public void setTheLuc(int theLuc) { this.theLuc = theLuc; }
    public void setSoDauThan(int soDauThan) { this.soDauThan = soDauThan; }
    public void setVang(long vang) { this.vang = vang; }
    public void setNgoc(long ngoc) { this.ngoc = ngoc; }
}
