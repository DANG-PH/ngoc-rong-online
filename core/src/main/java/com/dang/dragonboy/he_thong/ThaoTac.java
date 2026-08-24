package com.dang.dragonboy.he_thong;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import com.dang.dragonboy.hien_thi.*;
import com.dang.dragonboy.nhan_vat.NhanVat;
import com.dang.dragonboy.websocket.GameSocket;
import com.dang.dragonboy.websocket.TrangThaiHanhTrangGd;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_dungle.TrangThaiChucNang_admin_dungle;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_haidang.admin_haidang;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_dungle.admin_dungle;
import com.dang.dragonboy.xu_ly_map.npc.danhsachNpc.admin_thanhle.TrangThaiChucNang_admin_thanhle;

public class ThaoTac extends InputAdapter {
    private int yCuKeoPhai = 0;
    private int yCuKeoTrai = 0;
    // Điểm chạm ban đầu + cờ "đã vượt ngưỡng kéo" cho khung hành trang trái/phải — trên màn hình
    // cảm ứng, ngón tay luôn rung nhẹ vài pixel dù chỉ định tap, nếu xử lý mọi touchDragged đều
    // là "đang kéo" (như code gốc) thì tap chọn item gần như không bao giờ thành công vì touchUp
    // sẽ nghĩ là vừa kéo xong nên bỏ qua click. Cần 1 ngưỡng di chuyển tối thiểu trước khi tính là
    // kéo thật sự, giống cách camera.keoCamera() đã làm.
    private static final int NGUONG_KEO_HANH_TRANG = 8;
    private int diemBatDauKeoPhaiY = 0;
    private boolean vuotNguongKeoPhai = false;
    private int diemBatDauKeoTraiY = 0;
    private boolean vuotNguongKeoTrai = false;
    private final NhanVat nhanVat;
    private final VeHUD hud;
    private final QuanLyCamera camera;

    public ThaoTac(NhanVat nhanVat, VeHUD hud,  QuanLyCamera camera) {
        this.nhanVat = nhanVat;
        this.hud = hud;
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (GameSocket.isReconnecting || GameSocket.retryCount > 0) return true;
        if (hud.timeChoHopThe == 0 && !hud.dangHienDauThan && !hud.daClickVaoNpc && !hud.dangHienPopup && !hud.dangHienRuongDo && !hud.dangHienDieuUocRongThan && hud.timeChoBienKhi == 0) {
            switch (keycode) {
                case Input.Keys.LEFT:
                    if (nhanVat.diChuyenDenMucTieu) {
                        nhanVat.diChuyenDenMucTieu = false;
                        nhanVat.phimTraiDangGiu = false;
                        nhanVat.phimPhaiDangGiu = false;
                        nhanVat.phimNhayDangGiu = false;
                    }
                    nhanVat.diTrai();
                    nhanVat.setFlipTrai();
                    break;
                case Input.Keys.RIGHT:
                    if (nhanVat.diChuyenDenMucTieu) {
                        nhanVat.diChuyenDenMucTieu = false;
                        nhanVat.phimTraiDangGiu = false;
                        nhanVat.phimPhaiDangGiu = false;
                        nhanVat.phimNhayDangGiu = false;
                    }
                    nhanVat.diPhai();
                    nhanVat.setFlipPhai();
                    break;
                case Input.Keys.UP:
                    if (nhanVat.diChuyenDenMucTieu) {
                        nhanVat.diChuyenDenMucTieu = false;
                        nhanVat.phimTraiDangGiu = false;
                        nhanVat.phimPhaiDangGiu = false;
                        nhanVat.phimNhayDangGiu = false;
                    }
                    nhanVat.nhanNhay();
                    break;

                // Bấm phím số 1–5 để chọn skill
                case Input.Keys.NUM_1:
                    if (hud.skillDangChon == 0) {
                        hud.dungSkill(0);
                    } else {
                        hud.chonSkill(0);
                    }
                    break;
                case Input.Keys.NUM_2:
                    if (hud.skillDangChon == 1) {
                        hud.dungSkill(1);
                    } else {
                        hud.chonSkill(1);
                    }
                    break;
                case Input.Keys.NUM_3:
                    if (hud.skillDangChon == 2) {
                        hud.dungSkill(2);
                    } else {
                        hud.chonSkill(2);
                    }
                    break;
                case Input.Keys.NUM_4:
                    if (hud.skillDangChon == 3) {
                        hud.dungSkill(3);
                    } else {
                        hud.chonSkill(3);
                    }
                    break;
                case Input.Keys.NUM_5:
                    if (hud.skillDangChon == 4) {
                        hud.dungSkill(4);
                    } else {
                        hud.chonSkill(4);
                    }
                    break;
            }
        }
        return true;
    }


    @Override
    public boolean keyUp(int keycode) {
        if (GameSocket.isReconnecting || GameSocket.retryCount > 0) return true;
        switch (keycode) {
            case Input.Keys.LEFT:
                nhanVat.dungTrai();
                break;
            case Input.Keys.RIGHT:
                nhanVat.dungPhai();
                break;
            case Input.Keys.UP:
                nhanVat.thaNhay();
                break;
        }
        return true;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (GameSocket.isReconnecting || GameSocket.retryCount > 0) return true;
        // Toạ độ chạm/chuột đến từ libGDX ở hệ pixel THẬT của thiết bị, còn toàn bộ hit-test bên
        // dưới được viết theo hệ toạ độ ảo cố định 1020x610 (khớp cửa sổ PC cũ). Phải unproject
        // qua uiViewport trước, nếu không mọi vùng bấm sẽ sai lệch trên màn hình có độ phân giải khác.
        Vector2 diem = camera.uiViewport.unproject(new Vector2(screenX, screenY));
        int vx = (int) diem.x;
        int vy = (int) diem.y;
        if (button == Input.Buttons.LEFT && !hud.dangGiaoDich && !hud.dangHienKhungChung && !hud.dangHienPopup && !hud.dangHienRuongDo && !hud.daClickVaoNpc) {
            camera.batDauKeoCamera(vx, vy);
        }

        if (hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU ||
            hud.dangHienRuongDo ||
            hud.dangHienPopupNhanVatPhai ||
            hud.dangGiaoDich
        )
        {
            if (!hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin2 && !hud.DangHienPopupThongTin3) {
                if (vx >= 1020 - 360 && vx <= 1020 && vy > 0 && vy <= 444) {
                    hud.dangChonHanhTrangTrai = false;
                    hud.dangChonHanhTrangPhai = true;
                }
                if (vx > 0 && vx <= 350 && vy > 0 && vy <= 444) {
                    hud.dangChonHanhTrangTrai = true;
                    hud.dangChonHanhTrangPhai = false;
                }
            }
        }
        // Kéo hành trang sư phụ bên trái (chỉ khi không mở đệ tử)
        if (button == Input.Buttons.LEFT &&
            (hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.HANH_TRANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.KY_NANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.CHUC_NANG) &&
            !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU) &&
            !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME) &&
            !hud.dangHienRuongDo &&
            !hud.daClickVaoNpc &&
            vx > 0 && vx <= 350 &&
            vy > 0 && vy <= 444) {

            hud.keoHanhTrangPhai = true;
            hud.keoHanhTrangTrai = false;
            yCuKeoPhai = vy;
            diemBatDauKeoPhaiY = vy;
            vuotNguongKeoPhai = false;
        }

        // Kéo hành trang đệ tử bên trái
        if (button == Input.Buttons.LEFT &&
            hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU &&
            hud.dangChonHanhTrangTrai &&
            vx > 0 && vx <= 350 &&
            vy > 0 && vy <= 444) {

            hud.keoHanhTrangTrai = true;
            yCuKeoTrai = vy;
            diemBatDauKeoTraiY = vy;
            vuotNguongKeoTrai = false;
        }

        // Kéo hành trang ruong do bên trái
        if (button == Input.Buttons.LEFT &&
            hud.dangHienRuongDo &&
            hud.dangChonHanhTrangTrai &&
            vx > 0 && vx <= 350 &&
            vy > 0 && vy <= 444) {

            hud.keoHanhTrangTrai = true;
            yCuKeoTrai = vy;
            diemBatDauKeoTraiY = vy;
            vuotNguongKeoTrai = false;
        }

        // Kéo hành trang sư phụ bên phải (chỉ khi mở popup đệ tử hoac ruong do, và đang chọn sư phụ)
        if (button == Input.Buttons.LEFT &&
            (hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU || hud.dangHienRuongDo || hud.dangHienPopupNhanVatPhai) &&
            hud.dangChonHanhTrangPhai &&
            !hud.DangHienPopupThongTin1 &&
            vx >= 1020 - 360 && vx <= 1020 &&
            vy > 0 && vy <= 444) {

            hud.keoHanhTrangPhai = true;
            hud.keoHanhTrangTrai = false;
            yCuKeoPhai = vy;
            diemBatDauKeoPhaiY = vy;
            vuotNguongKeoPhai = false;
        }

        // Kéo hành trang player1 bên trái
        if (button == Input.Buttons.LEFT &&
            (hud.trangThaiHanhTrangGd == TrangThaiHanhTrangGd.HANH_TRANG && hud.dangGiaoDich) &&
            hud.dangChonHanhTrangTrai &&
            vx > 0 && vx <= 350 &&
            vy > 0 && vy <= 444) {

            hud.keoHanhTrangTrai = true;
            yCuKeoTrai = vy;
            diemBatDauKeoTraiY = vy;
            vuotNguongKeoTrai = false;
        }

        // Kéo hành trang player2 bên phải
        if (button == Input.Buttons.LEFT &&
            hud.dangGiaoDich &&
            hud.dangChonHanhTrangPhai &&
            !hud.DangHienPopupThongTin1 &&
            vx >= 1020 - 360 && vx <= 1020 &&
            vy > 0 && vy <= 444) {

            hud.keoHanhTrangPhai = true;
            hud.keoHanhTrangTrai = false;
            yCuKeoPhai = vy;
            diemBatDauKeoPhaiY = vy;
            vuotNguongKeoPhai = false;
        }

        //npc keo duoc
        if (button == Input.Buttons.LEFT &&
            hud.daClickVaoNpc &&
            hud.dangHienPopupNhanVatPhai &&
            vx > 0 && vx <= 350 &&
            vy > 0 && vy <= 444) {

            hud.keoHanhTrangTrai = true;
            yCuKeoTrai = vy;
            diemBatDauKeoTraiY = vy;
            vuotNguongKeoTrai = false;
        }

        return true;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (GameSocket.isReconnecting || GameSocket.retryCount > 0) return true;
        Vector2 diem = camera.uiViewport.unproject(new Vector2(screenX, screenY));
        int vx = (int) diem.x;
        int vy = (int) diem.y;
        camera.keoCamera(vx, vy);
        if (!hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin2 && !hud.DangHienPopupThongTin3) {
            if (hud.keoHanhTrangPhai) {
                if (!vuotNguongKeoPhai && Math.abs(vy - diemBatDauKeoPhaiY) > NGUONG_KEO_HANH_TRANG) {
                    vuotNguongKeoPhai = true;
                }
                if (vuotNguongKeoPhai) {
                    // yCuKeoPhai lưu toạ độ ảo (gốc dưới-trái) nên delta bị đảo dấu so với screenY gốc
                    int deltaY = yCuKeoPhai - vy;
                    hud.scrollYPhai -= deltaY * 1.5f;
                    // giới hạn scrollY
                    if (hud.scrollYPhai < 0) hud.scrollYPhai = 0;
                    if (hud.scrollYPhai > hud.maxScrollPhai) hud.scrollYPhai = hud.maxScrollPhai;
                    hud.vuaKeoHanhTrangPhai = true;
                }
                yCuKeoPhai = vy;
            }
            if (hud.keoHanhTrangTrai && vx > 0 && vx <= 350 && vy > 0 && vy <= 444) {
                if (!vuotNguongKeoTrai && Math.abs(vy - diemBatDauKeoTraiY) > NGUONG_KEO_HANH_TRANG) {
                    vuotNguongKeoTrai = true;
                }
                if (vuotNguongKeoTrai) {
                    int deltaY = yCuKeoTrai - vy;
                    hud.scrollYTrai -= deltaY * 1.5f;
                    // giới hạn scrollYTrai
                    if (hud.scrollYTrai < 0) hud.scrollYTrai = 0;
                    if (hud.scrollYTrai > hud.maxScrollTrai) hud.scrollYTrai = hud.maxScrollTrai;
                    hud.vuaKeoHanhTrangTrai = true;
                }
                yCuKeoTrai = vy;
            } else {
                hud.keoHanhTrangTrai = false;
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (GameSocket.isReconnecting || GameSocket.retryCount > 0) return true;
        Vector2 diem = camera.uiViewport.unproject(new Vector2(screenX, screenY));
        int vx = (int) diem.x;
        int vy = (int) diem.y;
        // Toạ độ thế giới (world) phải unproject qua camera.viewport (viewport của map), KHÔNG phải
        // qua uiViewport — 2 viewport này có thể khác kích thước ảo (map dùng ExtendViewport để
        // lấp đầy màn hình rộng, UI vẫn giữ FitViewport cố định 1020x610), nên không thể suy world
        // từ vx/vy của UI như công thức thủ công cũ.
        if (!hud.vuaKeoHanhTrangPhai && !hud.vuaKeoHanhTrangTrai) {
            Vector2 diemTheGioi = camera.viewport.unproject(new Vector2(screenX, screenY));
            hud.xuLyClick(vx, vy, diemTheGioi.x, diemTheGioi.y);
        }
//        if (hud.daClickVaoNpc) {
//            hud.npcHienTai.xuLyClick(screenX, y);
//        }
        if (button == Input.Buttons.LEFT) {
            camera.ketThucKeoCamera();
            hud.keoHanhTrangPhai = false;
            hud.vuaKeoHanhTrangPhai = false;
            hud.keoHanhTrangTrai = false;
            hud.vuaKeoHanhTrangTrai = false;
        }
        if (!hud.dangGiaoDich && !hud.dangHienKhungChung && !hud.dangHienPopup && !hud.dangHienRuongDo && !hud.daClickVaoNpc && !hud.dangHienDauThan && hud.timeChoBienKhi == 0 && !laClickTrenHUD(vx, vy) && !camera.vuaKeoCamera &&!hud.vuaThoatNpc && !hud.vuaTatPopup && !hud.vuaTatRuongDo && !hud.dangHienKhungChat && hud.timeChoHopThe == 0 && !hud.dangHienDieuUocRongThan ) {
            Vector2 diemTheGioi = camera.viewport.unproject(new Vector2(screenX, screenY));
            nhanVat.diChuyenDenMucTieu = true;
            nhanVat.setToaDoMucTieu(diemTheGioi.x, diemTheGioi.y);
        }
        hud.vuaTatPopup = false;
        hud.vuaTatRuongDo = false;
        hud.vuaThoatNpc = false;
        return true;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (GameSocket.isReconnecting || GameSocket.retryCount > 0) return true;
        // chỉ xử lý khi đang mở popup và chọn mục hành trang
        if (hud.dangHienPopup && (hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.HANH_TRANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.KY_NANG || hud.trangThaiChucNangHUD == TrangThaiChucNangHUD.CHUC_NANG) && !hud.dangHienThongBao && !hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin && !hud.DangHienPopupThongTin2 && !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME)) {
            if (!(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.DE_TU)) {
                hud.scrollPhai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            } else {
                if (hud.dangChonHanhTrangPhai) {
                    hud.scrollPhai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                    return true;
                }
                if (hud.dangChonHanhTrangTrai) {
                    hud.scrollTrai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                    return true;
                }
            }
        }
        if (hud.dangHienRuongDo && !hud.dangHienThongBao && !hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin && !hud.DangHienPopupThongTin3 && !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME)) {
            if (hud.dangChonHanhTrangPhai) {
                hud.scrollPhai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            }
            if (hud.dangChonHanhTrangTrai) {
                hud.scrollTrai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            }
        }
        if (hud.dangHienPopupNhanVatPhai && !hud.dangHienThongBao && !hud.DangHienPopupThongTin1 && !hud.DangHienPopupThongTin && !hud.DangHienPopupThongTin3 && !(hud.trangThaiChucNangHUDChucNang == TrangThaiChucNangHUD_ChucNang.MINIGAME)) {
            if (hud.dangChonHanhTrangPhai) {
                hud.scrollPhai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            }
            if (hud.dangChonHanhTrangTrai) {
                hud.scrollTrai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
                return true;
            }
        }
        if (hud.dangHienKhungChung) {
            hud.scrollPhai((int) amountY); // amountY là số lần lăn bánh (thường là ±1)
            return true;
        }
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        if (GameSocket.isReconnecting || GameSocket.retryCount > 0) return true;
        if (hud.dangHienKhungChat) {
            if (character == '\b') {
                if (!hud.tinNhanChat.isEmpty()) {
                    hud.tinNhanChat = hud.tinNhanChat.substring(0, hud.tinNhanChat.length() - 1);
                }
            } else if (character == '\n' || character == '\r') {
                // Bàn phím ảo Android gửi ký tự '\n' khi bấm nút Enter/Done — coi như bấm nút Gửi.
                hud.guiTinNhanChat();
            } else if (Character.toString(character).matches("[a-zA-Z0-9 :]")) {
                if (hud.tinNhanChat.length() < 100) {
                    hud.tinNhanChat += character;
                }
            }
        }
        if (hud.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CSMM) {
            if (character == '\b') {
                if (!hud.soNgocNguoiChoiNhap.isEmpty()) {
                    hud.soNgocNguoiChoiNhap = hud.soNgocNguoiChoiNhap.substring(0, hud.soNgocNguoiChoiNhap.length() - 1);
                }
            } else if (character == '\n' || character == '\r') {
                hud.xuLyGuiCSMM();
            } else if (Character.toString(character).matches("[a-zA-Z0-9 /]")) {
                if (hud.soNgocNguoiChoiNhap.length() < 100) {
                    hud.soNgocNguoiChoiNhap += character;
                }
            }
        }
        if (hud.trangThaiChucNangHUDChucNangMiniGame == TrangThaiChucNangHUD_ChucNang_MiniGame.THAM_GIA_CHAN_LE) {
            if (character == '\b') {
                if (!hud.soVangNguoiChoiNhapChanLe.isEmpty()) {
                    hud.soVangNguoiChoiNhapChanLe = hud.soVangNguoiChoiNhapChanLe.substring(0, hud.soVangNguoiChoiNhapChanLe.length() - 1);
                }
            } else if (character == '\n' || character == '\r') {
                hud.xuLyGuiChanLe();
            } else if (Character.toString(character).matches("[a-zA-Z0-9 /]")) {
                if (hud.soVangNguoiChoiNhapChanLe.length() < 100) {
                    hud.soVangNguoiChoiNhapChanLe += character;
                }
            }
        }
        if (hud.npcHienTai != null) {
            if (hud.npcHienTai.npcHUDrender.ui_npc instanceof admin_haidang) {
                admin_haidang ui = (admin_haidang) hud.npcHienTai.npcHUDrender.ui_npc;
                if (ui.dangHienChatDoiVeQuay) {
                    if (character == '\b') {
                        if (!ui.tinNhanChat.isEmpty()) {
                            ui.tinNhanChat = ui.tinNhanChat.substring(0, ui.tinNhanChat.length() - 1);
                        }
                    } else if (character == '\n' || character == '\r') {
                        ui.xuLyEnterChatDoiVeQuay();
                    } else if (Character.toString(character).matches("[a-zA-Z0-9]")) {
                        if (ui.tinNhanChat.length() < 100) {
                            ui.tinNhanChat += character;
                        }
                    }
                }
            }
            if (hud.npcHienTai.npcHUDrender.ui_npc instanceof admin_dungle) {
                admin_dungle ui = (admin_dungle) hud.npcHienTai.npcHUDrender.ui_npc;
                if (ui.trangThai == TrangThaiChucNang_admin_dungle.DOI_GIFT_CODE) {
                    if (character == '\b') {
                        if (!ui.tinNhanChat.isEmpty()) {
                            ui.tinNhanChat = ui.tinNhanChat.substring(0, ui.tinNhanChat.length() - 1);
                        }
                    } else if (character == '\n' || character == '\r') {
                        ui.xuLyEnterGiftCode();
                    } else if (Character.toString(character).matches("[a-zA-Z0-9]")) {
                        if (ui.tinNhanChat.length() < 100) {
                            ui.tinNhanChat += character;
                        }
                    }
                }
            }
        }
        return true;
    }
    public boolean laClickTrenHUD(float x, float y) {
        // === VÙNG Ô SKILL ===
        int oskillW = 50;
        int oskillH = 50;
        float skillBaseX = 30;
        float skillY = 25f;
        for (int i = 0; i < 5; i++) {
            float x_ve = skillBaseX + i * 65f;
            if (x >= x_ve && x <= x_ve + oskillW && y >= skillY && y <= skillY + oskillH) {
                return true;
            }
        }

        // === VÙNG Ô CHAT ===
        int ochatW = 60;
        int ochatH = 60;
        float ochatX = QuanLyCamera.VIRTUAL_WIDTH - ochatW - 15;
        float ochatY = QuanLyCamera.VIRTUAL_HEIGHT - 10 - ochatH;
        if (x >= ochatX && x <= ochatX + ochatW && y >= ochatY && y <= ochatY + ochatH) {
            return true;
        }

        // === VÙNG Ô ĐẬU THẦN ===
        int odauthanW = 75;
        int odauthanH = 75;
        float odauthanX = QuanLyCamera.VIRTUAL_WIDTH - odauthanW - 10;
        float odauthanY = 10;
        if (x >= odauthanX && x <= odauthanX + odauthanW && y >= odauthanY && y <= odauthanY + odauthanH) {
            return true;
        }

        // === VÙNG MỞ POPUP ===
        float nutPopupX = 0f;
        float nutPopupY = QuanLyCamera.VIRTUAL_HEIGHT / 4f * 3;
        if (x >= nutPopupX && x <= nutPopupX + 25 && y >= nutPopupY && y <= nutPopupY + 35) {
            return true;
        }

        return false; // không trúng vùng nào
    }
}
