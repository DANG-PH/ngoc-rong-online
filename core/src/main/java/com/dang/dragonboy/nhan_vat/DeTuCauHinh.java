package com.dang.dragonboy.nhan_vat;

import com.badlogic.gdx.graphics.Texture;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
public class DeTuCauHinh {
    // Đầu
    public Texture dau_dung;
    public Texture dau_chay;

    // Thân
    public Texture than_dung;
    public Texture than_nhay;
    public Texture than_roi;
    public Texture[] than_chay;
    public Texture than_bay;

    // Chân
    public Texture chan_dung;
    public Texture chan_nhay;
    public Texture chan_roi;
    public Texture[] chan_chay;
    public Texture chan_bay;

    // Offset
    public Map<TrangThai, List<DoLechModular>> lechMap;

    // item
    public Texture ao,quan,gang,giay,rada,iconct;

    public DeTuCauHinh(
        Texture dau_dung, Texture dau_chay,
        Texture than_dung, Texture than_nhay, Texture than_roi, Texture[] than_chay,
        Texture chan_dung, Texture chan_nhay, Texture chan_roi, Texture[] chan_chay,
        Texture than_bay, Texture chan_bay,
        Map<TrangThai, List<DoLechModular>> lechMap,
        Texture ao, Texture quan, Texture gang, Texture giay, Texture rada, Texture iconct
    ) {
        this.dau_dung = dau_dung;
        this.dau_chay = dau_chay;
        this.than_dung = than_dung;
        this.than_nhay = than_nhay;
        this.than_roi = than_roi;
        this.than_chay = than_chay;
        this.than_bay = than_bay;
        this.chan_dung = chan_dung;
        this.chan_nhay = chan_nhay;
        this.chan_roi = chan_roi;
        this.chan_chay = chan_chay;
        this.chan_bay = chan_bay;
        this.lechMap = lechMap;
        this.ao = ao;
        this.quan = quan;
        this.gang = gang;
        this.giay = giay;
        this.rada = rada;
        this.iconct = iconct;
    }
}
