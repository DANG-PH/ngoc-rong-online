package com.dang.dragonboy.he_thong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.dang.dragonboy.network.ApiService;
import com.dang.dragonboy.network.DTO.MusicServerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class MusicStatus {
    public static final int UNSPECIFIED = 0;
    public static final int PROCESSING = 1;
    public static final int ACTIVE = 2;
    public static final int INACTIVE = 3;
    public static final int FAILED = 4;
}

public class MusicManager {

    // Lấy app ở .exe ưu tiên ng dùng cuối thay vì dev
    private static final String LOCAL_DIR = "app/nhacnen/";

    // Map id (từ server) → Music instance
    public static final Map<Integer, Music> nhacNen = new HashMap<>();

    // Danh sách nhạc active có thứ tự (cho UI hiển thị)
    private static final List<MusicServerData> danhSachNhac = new ArrayList<>();

    // Tên các bài tải/load lỗi ở lần refresh gần nhất — cho VeHUD đọc để báo qua pet, tránh lỗi
    // bị nuốt âm thầm (trước đây chỉ log Gdx.app.error, không có gì hiện trong game để biết).
    private static final List<String> loiTaiNhac = new ArrayList<>();

    private static boolean daLoad = false;

    /**
     * Khởi tạo + load tất cả nhạc nền.
     * Gọi 1 lần khi game khởi động (ví dụ trong loading screen).
     */
    public static void init(Runnable onXong) {
        if (daLoad) {
            if (onXong != null) onXong.run();
            return;
        }

        ApiService.layDanhSachNhac(danhSach -> {
            // Chạy phần download trong thread riêng
            new Thread(() -> taiVaLoad(danhSach, onXong)).start();
        });
    }

    public static void refresh(Runnable onXong) {
        // Không check daLoad — luôn fetch list mới
        ApiService.layDanhSachNhac(danhSach -> {
            new Thread(() -> taiVaLoad(danhSach, onXong)).start();
        });
    }

    private static void taiVaLoad(List<MusicServerData> danhSach, Runnable onXong) {
        FileHandle dir = Gdx.files.local(LOCAL_DIR);
        if (!dir.exists()) dir.mkdirs();

        // Lưu lại danh sách active cho UI
        danhSachNhac.clear();
        loiTaiNhac.clear();
        for (MusicServerData m : danhSach) {
            if (m.status == MusicStatus.ACTIVE) {
                danhSachNhac.add(m);
            }
        }

        // Tập tên file hợp lệ (dùng để cleanup sau)
        Set<String> validFiles = new HashSet<>();

        for (MusicServerData m : danhSachNhac) {
            String fileName = m.hash + ".mp3";
            validFiles.add(fileName);

            FileHandle local = Gdx.files.local(LOCAL_DIR + fileName);

            // Chưa có → download
            if (!local.exists()) {
                boolean ok = ApiService.taiFileNhacVeLocal(m.file_url, local);
                if (!ok) {
                    Gdx.app.error("MusicManager", "Không tải được: " + m.name);
                    loiTaiNhac.add(m.name);
                    continue;
                }
            }

            // Load music phải chạy trên main thread (LibGDX yêu cầu)
            final int id = m.id;
            final String name = m.name;
            Gdx.app.postRunnable(() -> {
                try {
                    Music music = Gdx.audio.newMusic(local);
                    music.setLooping(true);
                    music.setVolume(0.5f);
                    nhacNen.put(id, music);
                } catch (Exception e) {
                    Gdx.app.error("MusicManager", "Load music fail: " + name, e);
                    loiTaiNhac.add(name);
                }
            });
        }

        // Dọn file rác (file local không còn trong danh sách server)
        for (FileHandle f : dir.list()) {
            if (!validFiles.contains(f.name())) {
                f.delete();
            }
        }

        daLoad = true;
        if (onXong != null) Gdx.app.postRunnable(onXong);
    }

    /**
     * Lấy danh sách nhạc đã load (cho UI hiển thị, có thứ tự).
     */
    public static List<MusicServerData> getDanhSach() {
        return danhSachNhac;
    }

    /**
     * Tên các bài tải/load lỗi ở lần refresh gần nhất (rỗng nếu không có lỗi).
     */
    public static List<String> getLoiTaiNhac() {
        return loiTaiNhac;
    }

    /**
     * Lấy Music theo id để play.
     * Trả null nếu chưa load xong hoặc id không tồn tại.
     */
    public static Music get(int id) {
        return nhacNen.get(id);
    }

    /**
     * Play bài theo id, dừng các bài đang phát.
     */
    public static void play(int id) {
        stopAll();
        Music music = nhacNen.get(id);
        if (music != null) music.play();
    }

    /**
     * Dừng tất cả nhạc đang phát.
     */
    public static void stopAll() {
        for (Music m : nhacNen.values()) {
            if (m != null && m.isPlaying()) m.stop();
        }
    }

    public static void setVolume(float volume) {
        for (Music m : nhacNen.values()) {
            if (m != null) m.setVolume(volume);
        }
    }

    public static void dispose() {
        for (Music m : nhacNen.values()) {
            if (m != null) m.dispose();
        }
        nhacNen.clear();
        danhSachNhac.clear();
        daLoad = false;
    }
}
