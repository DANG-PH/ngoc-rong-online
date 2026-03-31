package com.dang.dragonboy.websocket;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.dang.dragonboy.du_lieu.DuLieuNguoiChoi;
import com.dang.dragonboy.du_lieu.State_Management;
import com.dang.dragonboy.hien_thi.VeHUD;
import com.dang.dragonboy.item.Item;
import com.dang.dragonboy.nhan_vat.*;
import com.dang.dragonboy.xu_ly_map.npc.LoaiNPC;

import java.util.ArrayList;

public class PlayerState {
    public float serverX, serverY; // lerp
    public int userId;
    public float x;
    public float y;
    public float rong;
    public float cao;
    public String trangthai;
    public int dir;
    public String dau;
    public String than;
    public String chan;
    public String avatar;
    public float timeChoHienBay;
    public float lechThanX;
    public float lechDauX;
    public float lechDauY;
    public float lechThanY;
    public float lechChanX;
    public float lechChanY;
    public int frameVanBay;
    public boolean dangMangVanBay;
    public String tenVanBay;
    public String gameName;

    public boolean dangHienTinNhan = false;
    public float MAX_TIME_TIN_NHAN = 3f;
    public float timeHienTinNhan = 0f;
    public String tinNhanHien = "";

    public boolean dangClickPlayer = false;
    public boolean dangClickPlayer2 = false;

    public float timeDoiFrameMuiTen = 0f;
    public int frameMuiTen = 0;
    public int soLanChay = 0;

    public boolean dangGanPlayer1 = false;
    public float timeClickOChat = 0f;

    public ChucNangPlayer chucNangPlayer = ChucNangPlayer.NONE;
    public float timeChoThucHienChucNang = 0f;

    // Skill
    public float timeChoBienKhi = 0;
    public boolean dangBienKhi = false;
    public float timeSauBienKhi = 0;

    public boolean dangTtnl = false;

    public boolean dangHuytSao = false;
//    public float timeHuytSao = 0f; // Mở ra khi cần time để tính time tăng hp

    private float timeDoiFramesTtnl,timeDoiFramesQckk,timeHieuUngHuytSao;
    public boolean chuaSetTimeHuytSao = true;
    private int frameTtnl = 0, frameBom = 0;

    public void ve(SpriteBatch batch, float thoiGian, VeHUD veHUD) {
        TrangThai trangThai = TrangThai.valueOf(this.trangthai);

        float daoDong;
        daoDong = (trangThai == TrangThai.DUNG_YEN) ? (float) Math.sin(thoiGian) * 1.08f
            : (trangThai == TrangThai.BAY_NGANG) ? (float) Math.sin(thoiGian) * 5f
            : 0f;


        Texture chanVe = AssetMulti.getTexture(this.chan);
        Texture thanVe = AssetMulti.getTexture(this.than);
        Texture dauVe = AssetMulti.getTexture(this.dau);

        float x = this.x;
        float y = this.y;

        // Tính tọa độ theo hướng flip
        float chanW = chanVe.getWidth() * 0.5f;
        float chanH = chanVe.getHeight() * 0.5f;
        float thanW = thanVe.getWidth() * 0.5f;
        float thanH = thanVe.getHeight() * 0.5f;
        float dauW = dauVe.getWidth() * 0.5f;
        float dauH = dauVe.getHeight() * 0.5f;

        float cao = this.cao;
        float rong = this.rong;

        // Flip bằng scale âm nếu cần
        float flipScale = this.dir;
        float anchorX = this.dir == -1 ? x + rong : x;
        float offsetChanX = this.dir == -1 ? -this.lechChanX :  this.lechChanX;
        if (trangThai != TrangThai.BAY_NGANG) {
            batch.draw(chanVe, anchorX + offsetChanX, y + this.lechChanY, chanW * flipScale, chanH);

            float thanX = anchorX + (chanW / 2f - thanW / 2f) * flipScale;
            float thanY = y + chanH + daoDong;
            float dauX = anchorX + (chanW / 2f - dauW / 2f) * flipScale;
            float dauY = thanY + thanH;
            batch.draw(dauVe, dauX + this.lechDauX * flipScale, dauY + this.lechDauY, dauW * flipScale, dauH);
            batch.draw(thanVe, thanX + this.lechThanX * flipScale, thanY - 10.2f + this.lechThanY, thanW * flipScale, thanH);
        } else {
            if (!dangMangVanBay) {
                batch.draw(chanVe, anchorX - (thanW - 30) * flipScale, y + chanH + daoDong - 10, chanW * flipScale, chanH);

                float thanX = anchorX + (chanW / 2f - thanW / 2f) * flipScale;
                float thanY = y + chanH + daoDong;
                float dauX = anchorX + (chanW / 2f - dauW / 2f) * flipScale;
                float dauY = thanY + thanH;
                batch.draw(dauVe, dauX + lechDauX * flipScale, dauY + lechDauY, dauW * flipScale, dauH);
                batch.draw(thanVe, thanX + lechThanX * flipScale, thanY - 10.2f + lechThanY, thanW * flipScale, thanH);
            } else {
                batch.draw(chanVe, anchorX, y, chanW * flipScale, chanH);

                float thanX = anchorX + (chanW / 2f - thanW / 2f) * flipScale;
                float thanY = y + chanH + daoDong;
                float dauX = anchorX + (chanW / 2f - dauW / 2f) * flipScale;
                float dauY = thanY + thanH;
                batch.draw(dauVe, dauX + lechDauX * flipScale, dauY + lechDauY, dauW * flipScale, dauH);
                batch.draw(thanVe, thanX + lechThanX * flipScale, thanY - 10.2f + lechThanY, thanW * flipScale, thanH);
            }
        }
        if (trangThai == TrangThai.BAY_NGANG) {
            if (dangMangVanBay) {
                Texture cloud = AssetMulti.getVanBay(tenVanBay).frames[frameVanBay];
                float cloudW = cloud.getWidth() * AssetMulti.getVanBay(tenVanBay).tile;
                float cloudH = cloud.getHeight() * AssetMulti.getVanBay(tenVanBay).tile;
                float flipCloud = ((dir == -1) == AssetMulti.getVanBay(tenVanBay).flipVanBay) ? 1f : -1f;

                batch.draw(
                    cloud,
                    anchorX + (chanW / 2f - cloudW / 2f + AssetMulti.getVanBay(tenVanBay).offsetX) * flipCloud,
                    y + AssetMulti.getVanBay(tenVanBay).offsetY,
                    cloudW * flipCloud,
                    cloudH
                );
            } else {
                if (timeChoHienBay>=0.4f) {
                    Texture cloud = AssetMulti.getVanBay(tenVanBay).frames[frameVanBay];
                    float tiLe = 0.55f;
                    float cloudW = cloud.getWidth() * tiLe;
                    float cloudH = cloud.getHeight() * tiLe;
                    float flipCloud = this.dir;

                    batch.draw(
                        cloud,
                        anchorX - (thanW - 30 + cloudW - 20) * flipScale,
                        y + daoDong * 2f+chanH-(cloudH)/2f+(chanH)/2f-3f,
                        cloudW * flipCloud,
                        cloudH
                    );
                }
            }
        }

//        veHUD.fontTen.setColor(16f / 255f, 237f / 255f, 227f / 255f, 1f);
        veHUD.fontTen.setColor(1f, 1f, 1f, 1f);
        veHUD.layout.setText(veHUD.fontTen, this.gameName);
        veHUD.fontTen.draw(batch,veHUD.layout,x+(rong - veHUD.layout.width)/2f,y+cao+30);

        // Ve mui ten click Player
        float tenY = y + cao + 30;
        if (dangClickPlayer) {
            batch.draw(veHUD.muiTen, x + (rong - veHUD.muiTen.getWidth() * 0.5f) / 2f, tenY, veHUD.muiTen.getWidth() * 0.5f, veHUD.muiTen.getHeight() * 0.5f);
        }

        // Nếu click nhiều lần
        if (dangClickPlayer2) {
            batch.draw(veHUD.clickMuiTen[frameMuiTen], x + (rong - veHUD.clickMuiTen[frameMuiTen].getWidth() * 0.5f) / 2f, tenY, veHUD.clickMuiTen[frameMuiTen].getWidth() * 0.5f, veHUD.clickMuiTen[frameMuiTen].getHeight() * 0.5f);
        }

        // Nếu khoảng cách player 1 và player 2 < 150 pixel thì hiển thị bảng trên đầu
        if (dangGanPlayer1) {
            Texture oVe = timeClickOChat > 0f ? veHUD.ochatclick : veHUD.ochat;
            batch.draw(oVe, x + (rong-oVe.getWidth()*0.35f)/2f, y + cao + 50, oVe.getWidth()*0.35f, oVe.getHeight()*0.35f);
        }

        if (timeChoBienKhi > 0) {
            int tick = (int)(timeChoBienKhi * 10);
            int tick1 = (int)(timeChoBienKhi * 15);
            if (tick1 % 2 == 0) {
                veQckk(batch,x + rong/2f+5f*flipScale, y + cao / 2f);
            }
            if (tick % 2 == 0) {
                veTaiTaoNangLuong(batch,x + rong/2f+5f*flipScale,y);
            }
        }
        if (timeSauBienKhi < 0.6f && timeSauBienKhi > 0) {
            if (timeSauBienKhi > 0.35f) {
                veTaiTaoNangLuong(batch,x + rong/2f+5f*flipScale,y);
            }
        }
        if (dangTtnl) {
            float offsetX = 5f;
            if (dangBienKhi) offsetX = -5f;
            veTaiTaoNangLuong(batch,x + rong/2f+offsetX*flipScale,y);
        }
        if (dangHuytSao) {
            veHuytSao(batch);
        }

        if (this.dangHienTinNhan) {
            veHUD.layout.setText(
                veHUD.fontchat,
                tinNhanHien,
                new Color(0, 0, 0, 1),
                180,
                Align.center,
                true
            );
            batch.end();
            veHUD.shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            veHUD.shapeRenderer.setColor(1, 1, 1, 1);
            veHUD.shapeRenderer.rect(x + (rong - 200) / 2f, y + cao + 30, 200, 36 + veHUD.layout.height);
            veHUD.shapeRenderer.end();
            veHUD.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            veHUD.shapeRenderer.setColor(Color.BLACK);
            for (int i = 0; i < 2; i++) {
                veHUD.shapeRenderer.rect(x + (rong - 200) / 2f - i, y + cao + 30 - i, 200 + i * 2, 36 + veHUD.layout.height + i * 2);
            }
            veHUD.shapeRenderer.end();
            batch.begin();
            float duoiX = this.dir == -1 ? x + rong + 20 : x - 20;
            batch.draw(veHUD.duoichat, duoiX, y + cao + 15, 16 * flipScale, 16);
            veHUD.fontchat.draw(batch, veHUD.layout, x + (rong - 200) / 2f + 10f, y + cao + 30 + 18f + veHUD.layout.height);
        }
    }

    public void capNhat(VeHUD veHUD) {
        float delta = Gdx.graphics.getDeltaTime();

        float lerpSpeed = 12f;
        x += (serverX - x) * lerpSpeed * delta;
        y += (serverY - y) * lerpSpeed * delta;

        // Snap nếu quá xa (ví dụ teleport map)
        float dist = (float) Math.sqrt((serverX - x) * (serverX - x) + (serverY - y) * (serverY - y));
        if (dist > 200f) {
            x = serverX;
            y = serverY;
        }
        // Cho time tin nhắn 3s
        if (this.dangHienTinNhan) {
            timeHienTinNhan += Gdx.graphics.getDeltaTime();
            if (timeHienTinNhan >= MAX_TIME_TIN_NHAN) {
                timeHienTinNhan = 0f;
                dangHienTinNhan = false;
            }
        }

        // Click nhieu lan vao Player
        if (dangClickPlayer2) {
            timeDoiFrameMuiTen += Gdx.graphics.getDeltaTime();
            if (timeDoiFrameMuiTen > 0.06f) {
                frameMuiTen=(frameMuiTen+1)%4;
                soLanChay++;
                timeDoiFrameMuiTen = 0f;
            }
            if (soLanChay >= 4*2) {
                dangClickPlayer2 = false;
                soLanChay = 0;
                frameMuiTen = 0;
            }
        }

        if (dangClickPlayer) {
            float x = this.x;
            float y = this.y;
            float x1 = veHUD.getDuLieuNguoiChoi().nhanVat.getX();
            float y1 = veHUD.getDuLieuNguoiChoi().nhanVat.getY();
            float khoangCach2NguoiChoi = (float) Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
            if (khoangCach2NguoiChoi <= 150) {
                dangGanPlayer1 = true;
            } else {
                dangGanPlayer1 = false;
            }
        }

        if (timeClickOChat > 0f) {
            timeClickOChat -= Gdx.graphics.getDeltaTime();
            if (timeClickOChat <= 0) {
                timeClickOChat = 0;
                // Set mở popup bằng true ở đây, sau co the dung enum
                veHUD.dangHienKhungChung = true;
            }
        }

        // Nếu trạng thái bị thay đổi
        if (chucNangPlayer != ChucNangPlayer.NONE && timeChoThucHienChucNang <= 0f) {
            timeChoThucHienChucNang = 0.3f;
        }

        if (timeChoThucHienChucNang > 0f) {
            timeChoThucHienChucNang -= Gdx.graphics.getDeltaTime();
            if (timeChoThucHienChucNang <= 0f) {
                // Switch case chức năng đã chọn để thực hiện
                // call api, gửi event websocket, ...
                switch (chucNangPlayer) {
                    case GIAO_DICH -> {
                        try {
                            GameSocket.guiReqTradeItem(this.userId);
                            veHUD.playerGiaoDich = this;
                        } catch (Exception e) {

                        }
                    }
                }

                // Bước cuối là reset về trạng thái ban đầu
                timeChoThucHienChucNang = 0f;
                chucNangPlayer = ChucNangPlayer.NONE;
            }
        }

        if (timeChoBienKhi > 0) {
            timeChoBienKhi -= delta;
            if (timeChoBienKhi <= 0) {
                timeChoBienKhi = 0;
                dangBienKhi = true;
            }
        }
    }

    public void checkClick(float x_check, float y_check, NhanVat nhanVat) {
        if (dangGanPlayer1) {
            Texture oVe = nhanVat.getVeHUD().ochat;
            float bienDoXMin = x + (rong-oVe.getWidth()*0.35f)/2f;
            float bienDoXMax = bienDoXMin + oVe.getWidth()*0.35f;
            float bienDoYMin = y + cao + 50;
            float bienDoYMax = bienDoYMin + oVe.getHeight()*0.35f;
            if (x_check >= bienDoXMin && x_check <= bienDoXMax && y_check >= bienDoYMin && y_check <= bienDoYMax) {
                timeClickOChat = 0.3f;
                nhanVat.chanDiChuyenToaDo1Lan = true;
            }
        }

        if (timeClickOChat <= 0) {
            if (x_check >= x && x_check <= x + rong &&
                y_check >= y && y_check <= y + cao) {
                if (!dangClickPlayer) {
                    dangClickPlayer = true;
                    nhanVat.vuaClick = false;
                    thucHienHanhDongPlayer(nhanVat.getVeHUD());
                } else {
                    if (nhanVat.vuaClick) {
                        dangClickPlayer2 = true;
                        nhanVat.vuaClick = false;
                    }
                }
            } else {
                dangClickPlayer = false;
                dangClickPlayer2 = false;
                nhanVat.getVeHUD().daClickVaoPlayer = false;
                dangGanPlayer1 = false;
            }
        }
    }

    public void thucHienHanhDongPlayer(VeHUD veHUD) {
        veHUD.daClickVaoPlayer = true;
        veHUD.playerDuocChon = this;
    }

    public void huyTtnl() {
        dangTtnl = false;
    }
    public void huyHuytSao() {
//        timeHuytSao = 0;
        dangHuytSao = false;
    }
    public void huyBienKhi() {
        dangBienKhi = false;
        timeSauBienKhi = 0.6f;
    }

    public void veTaiTaoNangLuong(SpriteBatch batch,float x, float y) {
        VeHUD veHUD = State_Management.getVeHUD();
        timeDoiFramesTtnl += Gdx.graphics.getDeltaTime();
        if (timeDoiFramesTtnl > 0.06f) {
            frameTtnl = (frameTtnl+1)%veHUD.getDuLieuNguoiChoi().nhanVat.ttnl.length;
            timeDoiFramesTtnl = 0;
        }
        float tl = 0.5f;
        if (this.dangBienKhi) tl = 0.55f;
        batch.draw(veHUD.getDuLieuNguoiChoi().nhanVat.ttnl[frameTtnl],x-veHUD.getDuLieuNguoiChoi().nhanVat.ttnl[frameTtnl].getWidth()*tl/2f,y,veHUD.getDuLieuNguoiChoi().nhanVat.ttnl[frameTtnl].getWidth()*tl,veHUD.getDuLieuNguoiChoi().nhanVat.ttnl[frameTtnl].getHeight()*tl);
    }

    public void veQckk(SpriteBatch batch,float x, float y) {
        VeHUD veHUD = State_Management.getVeHUD();
        timeDoiFramesQckk += Gdx.graphics.getDeltaTime();
        if (timeDoiFramesQckk > 0.1f) {
            frameBom = (frameBom+1)%veHUD.getDuLieuNguoiChoi().nhanVat.bom.length;
            timeDoiFramesQckk = 0;
        }
        batch.setColor(1,1,1,0.9f);
        batch.draw(veHUD.getDuLieuNguoiChoi().nhanVat.bom[frameBom],x-veHUD.getDuLieuNguoiChoi().nhanVat.bom[frameBom].getWidth()*0.5f/2f,y-veHUD.getDuLieuNguoiChoi().nhanVat.bom[frameBom].getHeight()*0.5f/2f,veHUD.getDuLieuNguoiChoi().nhanVat.bom[frameBom].getWidth()*0.5f,veHUD.getDuLieuNguoiChoi().nhanVat.bom[frameBom].getHeight()*0.5f);
        batch.setColor(1,1,1,1f);
    }

    public void veHuytSao(SpriteBatch batch) {
        VeHUD veHUD = State_Management.getVeHUD();
        Texture[] saoDo = veHUD.getDuLieuNguoiChoi().nhanVat.saoDo;
        Texture[] saoVang = veHUD.getDuLieuNguoiChoi().nhanVat.saoVang;
        Texture[] saoXanh = veHUD.getDuLieuNguoiChoi().nhanVat.saoXanh;

        if (chuaSetTimeHuytSao) {
            timeHieuUngHuytSao = 0.9f;
            chuaSetTimeHuytSao = false;
        }

        float x_sao_base = x + rong / 2f - 10;
        float y_sao_base = y + cao + 10f;
        float tiLe = 0.5f;

        timeHieuUngHuytSao -= Gdx.graphics.getDeltaTime();
        if (timeHieuUngHuytSao < 0) timeHieuUngHuytSao = 0;

        float t = timeHieuUngHuytSao % 0.3f;

        if (t >= 0.25f && t < 0.3f) {
            batch.draw(saoDo[0], x_sao_base, y_sao_base,
                saoDo[0].getWidth()*tiLe, saoDo[0].getHeight()*tiLe);
        }
        else if (t >= 0.2f && t < 0.25f) {
            batch.draw(saoDo[0], x_sao_base, y_sao_base+5f,
                saoDo[0].getWidth()*tiLe, saoDo[0].getHeight()*tiLe);
            batch.draw(saoXanh[0], x_sao_base+25f, y_sao_base-12f,
                saoXanh[0].getWidth()*tiLe, saoXanh[0].getHeight()*tiLe);
        }
        else if (t >= 0.15f && t < 0.2f) {
            batch.draw(saoDo[1], x_sao_base, y_sao_base+5f,
                saoDo[1].getWidth()*tiLe, saoDo[1].getHeight()*tiLe);
            batch.draw(saoXanh[0], x_sao_base+25f, y_sao_base-12f,
                saoXanh[0].getWidth()*tiLe, saoXanh[0].getHeight()*tiLe);
            batch.draw(saoVang[0], x_sao_base+45f, y_sao_base-21f,
                saoVang[0].getWidth()*tiLe, saoVang[0].getHeight()*tiLe);
        }
        else if (t >= 0.1f && t < 0.15f) {
            batch.draw(saoDo[1], x_sao_base, y_sao_base+8f,
                saoDo[1].getWidth()*tiLe, saoDo[1].getHeight()*tiLe);
            batch.draw(saoXanh[1], x_sao_base+20f, y_sao_base-9f,
                saoXanh[1].getWidth()*tiLe, saoXanh[1].getHeight()*tiLe);
            batch.draw(saoVang[0], x_sao_base+50f, y_sao_base-21f,
                saoVang[0].getWidth()*tiLe, saoVang[0].getHeight()*tiLe);
        }
        else if (t >= 0.05f && t < 0.1f) {
            batch.draw(saoDo[2], x_sao_base-5f, y_sao_base+3f,
                saoDo[2].getWidth()*tiLe, saoDo[2].getHeight()*tiLe);
            batch.draw(saoXanh[2], x_sao_base+15f, y_sao_base-14f,
                saoXanh[2].getWidth()*tiLe, saoXanh[2].getHeight()*tiLe);
            batch.draw(saoVang[1], x_sao_base+60f, y_sao_base-19f,
                saoVang[1].getWidth()*tiLe, saoVang[1].getHeight()*tiLe);
        }
        else if (t > 0f && t < 0.05f) {
            batch.draw(saoDo[2], x_sao_base-15f, y_sao_base-13f,
                saoDo[2].getWidth()*tiLe, saoDo[2].getHeight()*tiLe);
            batch.draw(saoXanh[2], x_sao_base+13f, y_sao_base-9f,
                saoXanh[2].getWidth()*tiLe, saoXanh[2].getHeight()*tiLe);
            batch.draw(saoVang[1], x_sao_base+63f, y_sao_base-14f,
                saoVang[1].getWidth()*tiLe, saoVang[1].getHeight()*tiLe);
        }
    }
}
