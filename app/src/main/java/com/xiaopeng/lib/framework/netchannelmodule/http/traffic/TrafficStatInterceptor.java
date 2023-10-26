package com.xiaopeng.lib.framework.netchannelmodule.http.traffic;

import com.xiaopeng.lib.framework.netchannelmodule.http.statistic.HttpCounter;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes2.dex */
public final class TrafficStatInterceptor implements Interceptor {
    private TrafficStatInterceptor() {
    }

    public static TrafficStatInterceptor getInstance() {
        return Holder.INSTANCE;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        countRequestSize(request);
        String host = request.url().host();
        try {
            Response proceed = chain.proceed(request);
            countResponseSize(host, proceed);
            return proceed;
        } catch (Exception e) {
            HttpCounter.getInstance().increaseFailureWithSize(host, 0L);
            throw e;
        }
    }

    private void countRequestSize(Request request) {
        HttpCounter.getInstance().increaseRequest(request.url().host(), countHeadersSize(request.headers()) + request.url().toString().length() + countRequestBodySize(request));
    }

    private void countResponseSize(String str, Response response) {
        long countHeadersSize = countHeadersSize(response.headers()) + response.message().length();
        if (response.isSuccessful()) {
            HttpCounter.getInstance().increaseSucceedWithSize(str, countHeadersSize);
        } else {
            HttpCounter.getInstance().increaseFailureWithSize(str, countHeadersSize);
        }
    }

    private long countHeadersSize(Headers headers) {
        long j = 0;
        if (headers == null) {
            return 0L;
        }
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            j = j + headers.name(i).length() + headers.value(i).length();
        }
        return j;
    }

    private long countRequestBodySize(Request request) {
        if (request.body() == null) {
            return 0L;
        }
        try {
            return request.body().contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /* loaded from: classes2.dex */
    private static final class Holder {
        private static final TrafficStatInterceptor INSTANCE = new TrafficStatInterceptor();

        private Holder() {
        }
    }
}
