package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http;

import android.app.Application;
import okhttp3.Interceptor;

/* loaded from: classes2.dex */
public interface IConfig {
    IConfig addInterceptor(Interceptor interceptor);

    IConfig applicationContext(Application application);

    void apply();

    int connectTimeout();

    IConfig connectTimeout(int i);

    int dnsTimeout();

    IConfig dnsTimeout(int i);

    IConfig enableLogging();

    IConfig enableTracing();

    IConfig enableTrafficStats();

    int readTimeout();

    IConfig readTimeout(int i);

    int retryCount();

    IConfig retryCount(int i);

    int writeTimeout();

    IConfig writeTimeout(int i);
}
