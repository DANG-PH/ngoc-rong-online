package com.dang.dragonboy.network;

// Mỗi nền tảng (desktop, Android...) đăng nhập Google theo 1 luồng khác nhau hoàn toàn
// (desktop: mở browser hệ thống + ServerSocket cục bộ; Android: mở browser + intent-filter
// redirect + PKCE). Interface này để core gọi chung 1 API, còn implementation cụ thể do từng
// launcher (Lwjgl3Launcher, AndroidLauncher) tự đăng ký vào PlatformBridge lúc khởi động app.
public interface GoogleOAuthProvider {
    void login(GoogleLoginCallback callback);
}
