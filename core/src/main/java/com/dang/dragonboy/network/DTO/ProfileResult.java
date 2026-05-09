package com.dang.dragonboy.network.DTO;

public class ProfileResult {
    public enum Status { OK, TOKEN_INVALID, BANNED, SERVER_ERROR }

    public final Status status;
    public final UserResponse user;
    public final String banMessage; // chỉ có khi BANNED

    private ProfileResult(Status status, UserResponse user, String banMessage) {
        this.status = status;
        this.user = user;
        this.banMessage = banMessage;
    }

    public static ProfileResult ok(UserResponse user) {
        return new ProfileResult(Status.OK, user, null);
    }
    public static ProfileResult tokenInvalid() {
        return new ProfileResult(Status.TOKEN_INVALID, null, null);
    }
    public static ProfileResult banned(String message) {
        return new ProfileResult(Status.BANNED, null, message);
    }
    public static ProfileResult serverError() {
        return new ProfileResult(Status.SERVER_ERROR, null, null);
    }
}
