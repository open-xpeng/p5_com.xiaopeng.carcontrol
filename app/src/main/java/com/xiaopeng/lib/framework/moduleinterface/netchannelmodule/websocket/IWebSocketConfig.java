package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

/* loaded from: classes2.dex */
public interface IWebSocketConfig {
    void apply();

    IWebSocketConfig client(OkHttpClient okHttpClient);

    IWebSocketConfig disableLog();

    IWebSocketConfig pingInterval(long j);

    IWebSocketConfig reconnectInterval(long j);

    IWebSocketConfig showLog();

    IWebSocketConfig showLog(String str);

    IWebSocketConfig sslSocketFactory(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager);
}
