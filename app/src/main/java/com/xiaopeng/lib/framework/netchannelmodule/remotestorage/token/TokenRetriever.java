package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.exception.StorageExceptionImpl;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.oss.Bucket;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class TokenRetriever {
    private static final long BLOCKING_TIME_INTERVAL = 3600000;
    private static final String EMPTY_BODY = "{}";
    private static final long RESPONSE_NO_CERTIFICATE = 11001502;
    private static final String TAG = "NetChannel-TokenRetriever";
    private long mBlockingTime;
    private final CallbackList mCallbacks;
    private volatile boolean mRetrieving;
    private volatile Token mToken;

    /* loaded from: classes2.dex */
    public interface IRetrievingCallback {
        void onFailure(StorageException storageException);

        void onSuccess(Token token);
    }

    private TokenRetriever() {
        this.mToken = null;
        this.mCallbacks = new CallbackList();
        this.mBlockingTime = 0L;
    }

    public static TokenRetriever getInstance() {
        return Holder.INSTANCE;
    }

    public void getTokenWithCallback(IRetrievingCallback iRetrievingCallback) {
        if (this.mBlockingTime > 0 && System.currentTimeMillis() < this.mBlockingTime) {
            iRetrievingCallback.onFailure(new StorageExceptionImpl(StorageException.REASON_GET_TOKEN_ERROR, String.valueOf((long) RESPONSE_NO_CERTIFICATE)));
        } else if (this.mToken != null && this.mToken.stillFresh()) {
            iRetrievingCallback.onSuccess(this.mToken);
        } else {
            this.mCallbacks.add(iRetrievingCallback);
            if (this.mRetrieving) {
                return;
            }
            this.mRetrieving = true;
            getNewTokenFromServer(GlobalConfig.getApplicationContext());
        }
    }

    public void clearToken() {
        this.mToken = null;
        LogUtils.d(TAG, "Clear the existing token!");
    }

    private void getNewTokenFromServer(Context context) {
        ((IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class)).bizHelper().post(Bucket.TOKEN_URL_V5, EMPTY_BODY).build().execute(new GetTokenCallback());
    }

    /* loaded from: classes2.dex */
    private static final class Holder {
        public static final TokenRetriever INSTANCE = new TokenRetriever();

        private Holder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class GetTokenCallback implements Callback {
        private GetTokenCallback() {
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onFailure(IResponse iResponse) {
            String message;
            TokenRetriever.this.mRetrieving = false;
            TokenRetriever.this.mBlockingTime = 0L;
            LogUtils.e(TokenRetriever.TAG, "Request token error! the error message---->" + iResponse.message());
            if (iResponse.getException() != null) {
                message = iResponse.getException().getMessage();
            } else {
                message = iResponse.message();
            }
            TokenRetriever.this.callFailureCallbacks(new StorageExceptionImpl(StorageException.REASON_GET_TOKEN_ERROR, message));
        }

        @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
        public void onSuccess(IResponse iResponse) {
            TokenRetriever.this.mRetrieving = false;
            TokenRetriever.this.mBlockingTime = 0L;
            if (TextUtils.isEmpty(iResponse.body())) {
                TokenRetriever.this.callFailureCallbacks(new StorageExceptionImpl(StorageException.REASON_GET_TOKEN_ERROR, "Empty response!"));
                return;
            }
            JsonObject asJsonObject = new JsonParser().parse(iResponse.body()).getAsJsonObject();
            long asLong = asJsonObject.get("code").getAsLong();
            if (asLong == 200) {
                JsonObject asJsonObject2 = asJsonObject.getAsJsonObject("data");
                String asString = asJsonObject2.get("security_token").getAsString();
                String asString2 = asJsonObject2.get("access_key_secret").getAsString();
                String asString3 = asJsonObject2.get("access_key_id").getAsString();
                TokenRetriever.this.mToken = new Token(asString, asString2, asString3);
                TokenRetriever tokenRetriever = TokenRetriever.this;
                tokenRetriever.callSuccessCallbacks(tokenRetriever.mToken);
                return;
            }
            String body = iResponse.body();
            if (asLong == TokenRetriever.RESPONSE_NO_CERTIFICATE) {
                TokenRetriever.this.mBlockingTime = System.currentTimeMillis() + 3600000;
            }
            TokenRetriever.this.callFailureCallbacks(new StorageExceptionImpl(StorageException.REASON_GET_TOKEN_ERROR, body));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callSuccessCallbacks(final Token token) {
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.1
            @Override // java.lang.Runnable
            public void run() {
                TokenRetriever.this.mCallbacks.onSuccess(token);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callFailureCallbacks(final StorageException storageException) {
        ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.token.TokenRetriever.2
            @Override // java.lang.Runnable
            public void run() {
                TokenRetriever.this.mCallbacks.onFailure(storageException);
            }
        });
    }

    /* loaded from: classes2.dex */
    private class CallbackList {
        private List<IRetrievingCallback> mCallbacks = new ArrayList();

        public CallbackList() {
        }

        public synchronized void add(IRetrievingCallback iRetrievingCallback) {
            this.mCallbacks.add(iRetrievingCallback);
        }

        public synchronized void onFailure(StorageException storageException) {
            for (IRetrievingCallback iRetrievingCallback : this.mCallbacks) {
                iRetrievingCallback.onFailure(storageException);
            }
            this.mCallbacks.clear();
        }

        public synchronized void onSuccess(Token token) {
            for (IRetrievingCallback iRetrievingCallback : this.mCallbacks) {
                iRetrievingCallback.onSuccess(token);
            }
            this.mCallbacks.clear();
        }
    }
}
