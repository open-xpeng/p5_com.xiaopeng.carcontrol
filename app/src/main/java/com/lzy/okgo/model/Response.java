package com.lzy.okgo.model;

import okhttp3.Call;
import okhttp3.Headers;

/* loaded from: classes.dex */
public final class Response<T> {
    private T body;
    private boolean isFromCache;
    private Call rawCall;
    private okhttp3.Response rawResponse;
    private Throwable throwable;

    public static <T> Response<T> success(boolean z, T t, Call call, okhttp3.Response response) {
        Response<T> response2 = new Response<>();
        response2.setFromCache(z);
        response2.setBody(t);
        response2.setRawCall(call);
        response2.setRawResponse(response);
        return response2;
    }

    public static <T> Response<T> error(boolean z, Call call, okhttp3.Response response, Throwable th) {
        Response<T> response2 = new Response<>();
        response2.setFromCache(z);
        response2.setRawCall(call);
        response2.setRawResponse(response);
        response2.setException(th);
        return response2;
    }

    public int code() {
        okhttp3.Response response = this.rawResponse;
        if (response == null) {
            return -1;
        }
        return response.code();
    }

    public String message() {
        okhttp3.Response response = this.rawResponse;
        if (response == null) {
            return null;
        }
        return response.message();
    }

    public Headers headers() {
        okhttp3.Response response = this.rawResponse;
        if (response == null) {
            return null;
        }
        return response.headers();
    }

    public boolean isSuccessful() {
        return this.throwable == null;
    }

    public void setBody(T t) {
        this.body = t;
    }

    public T body() {
        return this.body;
    }

    public Throwable getException() {
        return this.throwable;
    }

    public void setException(Throwable th) {
        this.throwable = th;
    }

    public Call getRawCall() {
        return this.rawCall;
    }

    public void setRawCall(Call call) {
        this.rawCall = call;
    }

    public okhttp3.Response getRawResponse() {
        return this.rawResponse;
    }

    public void setRawResponse(okhttp3.Response response) {
        this.rawResponse = response;
    }

    public boolean isFromCache() {
        return this.isFromCache;
    }

    public void setFromCache(boolean z) {
        this.isFromCache = z;
    }
}
