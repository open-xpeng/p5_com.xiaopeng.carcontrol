package com.lzy.okgo.cache.policy;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.callback.Callback;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes.dex */
public interface CachePolicy<T> {
    void cancel();

    boolean isCanceled();

    boolean isExecuted();

    boolean onAnalysisResponse(Call call, Response response);

    void onError(com.lzy.okgo.model.Response<T> response);

    void onSuccess(com.lzy.okgo.model.Response<T> response);

    CacheEntity<T> prepareCache();

    Call prepareRawCall() throws Throwable;

    void requestAsync(CacheEntity<T> cacheEntity, Callback<T> callback);

    com.lzy.okgo.model.Response<T> requestSync(CacheEntity<T> cacheEntity);
}
