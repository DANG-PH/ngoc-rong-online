package com.dang.dragonboy.nhan_vat;

import com.badlogic.gdx.graphics.Texture;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.dang.dragonboy.du_lieu.DeTu;
import com.dang.dragonboy.du_lieu.TrangThaiDeTu;

public class DeTuCauHinh {
    // Đầu
    public Texture dau_dung_de_tu;
    public Texture dau_chay_de_tu;

    // Thân
    public Texture than_dung_de_tu;
    public Texture than_nhay_de_tu;
    public Texture than_roi_de_tu;
    public Texture[] than_chay_de_tu;
    public Texture than_bay_de_tu;

    // Chân
    public Texture chan_dung_de_tu;
    public Texture chan_nhay_de_tu;
    public Texture chan_roi_de_tu;
    public Texture[] chan_chay_de_tu;
    public Texture chan_bay_de_tu;

    // Offset
    public Map<TrangThaiDeTu, List<DoLechModular>> lechMapDeTu;

    // item
    public Texture ao_de_tu,quan_de_tu,gang_de_tu,giay_de_tu,rada_de_tu,iconct_de_tu;

    public DeTuCauHinh(
        Texture dau_dung_de_tu, Texture dau_chay_de_tu,
        Texture than_dung_de_tu, Texture than_nhay_de_tu, Texture than_roi_de_tu, Texture[] than_chay_de_tu,
        Texture chan_dung_de_tu, Texture chan_nhay_de_tu, Texture chan_roi_de_tu, Texture[] chan_chay_de_tu,
        Texture than_bay_de_tu, Texture chan_bay_de_tu,
        Map<TrangThaiDeTu, List<DoLechModular>> lechMapDeTu,
        Texture ao_de_tu, Texture quan_de_tu, Texture gang_de_tu, Texture giay_de_tu, Texture rada_de_tu, Texture iconct_de_tu
    ) {
        this.dau_dung_de_tu = dau_dung_de_tu;
        this.dau_chay_de_tu = dau_chay_de_tu;
        this.than_dung_de_tu = than_dung_de_tu;
        this.than_nhay_de_tu = than_nhay_de_tu;
        this.than_roi_de_tu = than_roi_de_tu;
        this.than_chay_de_tu = than_chay_de_tu;
        this.than_bay_de_tu = than_bay_de_tu;
        this.chan_dung_de_tu = chan_dung_de_tu;
        this.chan_nhay_de_tu = chan_nhay_de_tu;
        this.chan_roi_de_tu = chan_roi_de_tu;
        this.chan_chay_de_tu = chan_chay_de_tu;
        this.chan_bay_de_tu = chan_bay_de_tu;
        this.lechMapDeTu = lechMapDeTu;
        this.ao_de_tu = ao_de_tu;
        this.quan_de_tu = quan_de_tu;
        this.gang_de_tu = gang_de_tu;
        this.giay_de_tu = giay_de_tu;
        this.rada_de_tu = rada_de_tu;
        this.iconct_de_tu = iconct_de_tu;
    }
}
