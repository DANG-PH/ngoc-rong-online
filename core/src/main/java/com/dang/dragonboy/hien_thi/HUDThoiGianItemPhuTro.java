package com.dang.dragonboy.hien_thi;

public class HUDThoiGianItemPhuTro {
    private VeHUD veHUD;

    public HUDThoiGianItemPhuTro(VeHUD veHUD) {
        this.veHUD = veHUD;
    }

    public void capNhatThoiGianItem(float delta) {
        if (veHUD.timeDungBoHuyet>0) {
            veHUD.timeDungBoHuyet-=delta;
            if(veHUD.timeDungBoHuyet<=0) {
                veHUD.timeDungBoHuyet = 0;
                veHUD.dangDungBoHuyet = false;
            }
        }
        if (veHUD.timeDungBoKhi>0) {
            veHUD.timeDungBoKhi-=delta;
            if(veHUD.timeDungBoKhi<=0) {
                veHUD.timeDungBoKhi = 0;
                veHUD.dangDungBoKhi = false;
            }
        }
        if (veHUD.timeDungCuongNo>0) {
            veHUD.timeDungCuongNo-=delta;
            if(veHUD.timeDungCuongNo<=0) {
                veHUD.timeDungCuongNo = 0;
                veHUD.dangDungCuongNo = false;
            }
        }
        if (veHUD.timeDungGiapXen>0) {
            veHUD.timeDungGiapXen-=delta;
            if(veHUD.timeDungGiapXen<=0) {
                veHUD.timeDungGiapXen = 0;
                veHUD.dangDungGiapXen = false;
            }
        }
        if (veHUD.timeDungSinhLuc>0) {
            veHUD.timeDungSinhLuc-=delta;
            if(veHUD.timeDungSinhLuc<=0) {
                veHUD.timeDungSinhLuc = 0;
                veHUD.dangDungSinhLuc = false;
            }
        }
        if (veHUD.timeDungCuongCong>0) {
            veHUD.timeDungCuongCong-=delta;
            if(veHUD.timeDungCuongCong<=0) {
                veHUD.timeDungCuongCong = 0;
                veHUD.dangDungCuongCong = false;
            }
        }
        if (veHUD.timeDungLinhKhi>0) {
            veHUD.timeDungLinhKhi-=delta;
            if(veHUD.timeDungLinhKhi<=0) {
                veHUD.timeDungLinhKhi = 0;
                veHUD.dangDungLinhKhi = false;
            }
        }
    }
}
