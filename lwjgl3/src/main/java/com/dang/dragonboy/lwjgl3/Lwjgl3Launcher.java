package com.dang.dragonboy.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.dang.dragonboy.he_thong.Main;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        taoUngDung();
    }

    private static Lwjgl3Application taoUngDung() {
        return new Lwjgl3Application(new Main(), layCauHinh());
    }

    private static Lwjgl3ApplicationConfiguration layCauHinh() {
        Lwjgl3ApplicationConfiguration cauHinh = new Lwjgl3ApplicationConfiguration();
        cauHinh.setTitle("Github DANG-PH");
        cauHinh.useVsync(true);
        cauHinh.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        cauHinh.setWindowedMode(1020, 610);
        cauHinh.setWindowIcon("hud/giaodienngoai/chung/icon128_1.png", "hud/giaodienngoai/chung/icon32_1.png", "hud/giaodienngoai/chung/icon16_1.png");
        cauHinh.setResizable(false);   // Không cho kéo giãn cửa sổ
        cauHinh.setMaximized(false);   // Không cho phóng to toàn màn hình
        return cauHinh;
    }
}
