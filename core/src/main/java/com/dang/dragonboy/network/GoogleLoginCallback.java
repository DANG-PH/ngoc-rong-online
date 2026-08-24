package com.dang.dragonboy.network;

public interface GoogleLoginCallback {
    void onSuccess(String idToken);
    void onFailure(String error);
}
