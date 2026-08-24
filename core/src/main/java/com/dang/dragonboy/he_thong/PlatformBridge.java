package com.dang.dragonboy.he_thong;

import com.dang.dragonboy.network.GoogleOAuthProvider;

// Nơi mỗi launcher (Lwjgl3Launcher, AndroidLauncher) đăng ký các hook đặc thù nền tảng trước khi
// tạo Main, để code trong core gọi được các chức năng chỉ nền tảng đó mới làm được (vd đăng nhập
// Google) mà không cần core phụ thuộc trực tiếp vào API của từng nền tảng.
public class PlatformBridge {
    public static GoogleOAuthProvider googleOAuth;
}
