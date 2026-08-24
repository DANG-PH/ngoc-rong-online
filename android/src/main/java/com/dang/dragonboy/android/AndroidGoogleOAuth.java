package com.dang.dragonboy.android;

import android.app.Activity;
import android.os.CancellationSignal;

import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.badlogic.gdx.Gdx;
import com.dang.dragonboy.he_thong.AppConfig;
import com.dang.dragonboy.network.GoogleLoginCallback;
import com.dang.dragonboy.network.GoogleOAuthProvider;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Đăng nhập Google trên Android — BẮT BUỘC dùng Credential Manager (Google Identity Services),
 * KHÔNG dùng được kiểu redirect qua trình duyệt như GoogleOAuth2Desktop.
 *
 * Lý do: đã thử luồng mở browser + redirect qua intent-filter (giống cách "Desktop app"/"iOS" làm)
 * nhưng Google trả về "Lỗi 400: invalid_request — không tuân thủ chính sách OAuth 2.0 giữ an toàn
 * cho app". OAuth Client loại "Android" trên Google Cloud Console không được thiết kế để dùng qua
 * endpoint /o/oauth2/v2/auth như Desktop — nó chỉ xác thực được thông qua chính SDK của Google
 * (Credential Manager), SDK này tự xác minh app qua package name + chữ ký APK ở tầng hệ thống,
 * không cần redirect URI, không cần mở trình duyệt.
 *
 * Lưu ý quan trọng: tham số "server client ID" truyền cho Credential Manager PHẢI là Client ID loại
 * "Web application" (không phải loại "Android") — đây là quy định của Google, vì id_token trả về
 * có "audience" là client Web đó, để backend xác thực. Dùng key config "google.client.id.web".
 */
public class AndroidGoogleOAuth implements GoogleOAuthProvider {

    private final Activity activity;
    private final CredentialManager credentialManager;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public AndroidGoogleOAuth(Activity activity) {
        this.activity = activity;
        this.credentialManager = CredentialManager.create(activity);
    }

    @Override
    public void login(GoogleLoginCallback callback) {
        String webClientId;
        try {
            webClientId = AppConfig.get("google.client.id.web");
        } catch (Exception e) {
            callback.onFailure(e.getMessage());
            return;
        }

        String nonce = UUID.randomUUID().toString().replace("-", "");
        GetSignInWithGoogleOption option = new GetSignInWithGoogleOption.Builder(webClientId)
            .setNonce(nonce)
            .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build();

        credentialManager.getCredentialAsync(
            activity,
            request,
            new CancellationSignal(),
            executor,
            new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                @Override
                public void onResult(GetCredentialResponse response) {
                    try {
                        String idToken = trichIdToken(response);
                        Gdx.app.postRunnable(() -> callback.onSuccess(idToken));
                    } catch (Exception e) {
                        Gdx.app.postRunnable(() -> callback.onFailure(e.getMessage()));
                    }
                }

                @Override
                public void onError(GetCredentialException e) {
                    Gdx.app.postRunnable(() -> callback.onFailure(e.getMessage()));
                }
            }
        );
    }

    private String trichIdToken(GetCredentialResponse response) throws GoogleIdTokenParsingException {
        Credential credential = response.getCredential();
        if (credential instanceof CustomCredential) {
            CustomCredential customCredential = (CustomCredential) credential;
            if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(customCredential.getType())) {
                GoogleIdTokenCredential googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(customCredential.getData());
                return googleIdTokenCredential.getIdToken();
            }
        }
        throw new IllegalStateException("Không nhận được Google ID token hợp lệ");
    }
}
