package com.xiaopeng.lib.framework.netchannelmodule.common;

import android.util.Log;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class TrafficeStaFlagInterceptor implements Interceptor {
    private static final String TAG = "TrafficFlag";

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Log.i(TAG, "setTraficInfo:\t" + Thread.currentThread().getId());
        TrafficStatsEntry.setTraficInfo();
        return chain.proceed(chain.request());
    }
}
