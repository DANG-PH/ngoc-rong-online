package com.dang.dragonboy.network;

public class ItemCanLuu {
    public Long id;              // id trong DB
    public String maItem;        // mã định danh item (map sang client)
    public String ten;           // tên hiển thị
    public String loai;          // loại item (AO, QUAN, GIAY…)
    public String moTa;          // mô tả dài

    public int soLuong;          // số lượng stack
    public String hanhTinh;      // hành tinh đặc trưng (nếu có)
    public String setKichHoat;   // tên set để kích hoạt (nếu có)

    public int soSaoPhaLe;       // số sao pha lê
    public int soSaoPhaLeCuongHoa; // số sao pha lê cường hóa
    public int soCap;            // cấp item

    public float hanSuDung;      // hạn sử dụng
    public long sucManhYeuCau;   // yêu cầu sức mạnh tối thiểu

    public String linkTexture;   // link tới texture để hiển thị
    public String chiso;         // JSON string chỉ số {atk, def, hp...}
    public String viTri;         // vị trí hiện tại (hanhtrang, ruongdo…)
}
