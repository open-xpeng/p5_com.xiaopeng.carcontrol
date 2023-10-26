package com.xiaopeng.xpmeditation.moreless;

import android.content.Context;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;

/* loaded from: classes2.dex */
public class MorelessHelper {
    private static final String BASE_URL = "https://tide-api.moreless.io/v2/oauth2/";
    private static final String CLIENT_ID_KEY = "client_id";
    private static final String CLIENT_ID_VALUE = "62ac1e31ff065a0001c3a485";
    private static final String CLIENT_SECRET_KEY = "client_secret";
    private static final String CLIENT_SECRET_VALUE = "c35f4d5cd6801dd339feabf8";
    private static final String GRANT_TYPE_KEY = "grant_type";
    private static final String GRANT_TYPE_VALUE = "client_credentials";
    private static final String SCOPE_KEY = "scope";
    private static final String SCOPE_VALUE = "scene.xiaopeng";
    private static final String SOUND_URL = "https://tide-api.moreless.io/v2/oauth2/scenes";
    private static final String TAG = "MorelessHelper";
    private static final String TOKEN_URL = "https://tide-api.moreless.io/v2/oauth2/token";
    private OkHttpClient okHttpClient;

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        static MorelessHelper sInstance = new MorelessHelper();

        private SingletonHolder() {
        }
    }

    public static MorelessHelper getInstance() {
        return SingletonHolder.sInstance;
    }

    private OkHttpClient getHttpClient(Context context) {
        if (this.okHttpClient == null) {
            this.okHttpClient = new OkHttpClient().newBuilder().build();
        }
        return this.okHttpClient;
    }

    public void getToken(Context context, IMorelessCallback callback) {
        getHttpClient(context).newCall(new Request.Builder().post(new FormBody.Builder().add(GRANT_TYPE_KEY, GRANT_TYPE_VALUE).add(SCOPE_KEY, SCOPE_VALUE).add(CLIENT_ID_KEY, CLIENT_ID_VALUE).add(CLIENT_SECRET_KEY, CLIENT_SECRET_VALUE).build()).url(TOKEN_URL).header("Content-Type", "application/x-www-form-urlencoded").build()).enqueue(callback);
    }

    public void getSoundList(Context context, String token, IMorelessCallback callback) {
        getHttpClient(context).newCall(new Request.Builder().url(SOUND_URL).header("Authorization", "Bearer " + token).get().build()).enqueue(callback);
    }

    private SSLSocketFactory createSSLSocketFactory() {
        try {
            SSLContext sSLContext = SSLContext.getInstance(SSLSocketFactoryFactory.DEFAULT_PROTOCOL);
            sSLContext.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
            return sSLContext.getSocketFactory();
        } catch (Exception unused) {
            return null;
        }
    }

    /* loaded from: classes2.dex */
    private class TrustAllManager implements X509TrustManager {
        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        private TrustAllManager() {
        }
    }

    /* loaded from: classes2.dex */
    private class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }

        private TrustAllHostnameVerifier() {
        }
    }
}
