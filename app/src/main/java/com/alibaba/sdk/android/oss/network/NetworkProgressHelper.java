package com.alibaba.sdk.android.oss.network;

import java.io.IOException;
import java.io.InputStream;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/* loaded from: classes.dex */
public class NetworkProgressHelper {
    public static OkHttpClient addProgressResponseListener(OkHttpClient okHttpClient, final ExecutionContext executionContext) {
        return okHttpClient.newBuilder().addNetworkInterceptor(new Interceptor() { // from class: com.alibaba.sdk.android.oss.network.NetworkProgressHelper.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Response proceed = chain.proceed(chain.request());
                return proceed.newBuilder().body(new ProgressTouchableResponseBody(proceed.body(), ExecutionContext.this)).build();
            }
        }).build();
    }

    public static ProgressTouchableRequestBody addProgressRequestBody(InputStream inputStream, long j, String str, ExecutionContext executionContext) {
        return new ProgressTouchableRequestBody(inputStream, j, str, executionContext);
    }
}
