package com.lzy.okgo.cache.policy;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/* loaded from: classes.dex */
public class FirstCacheRequestPolicy<T> extends BaseCachePolicy<T> {
    public FirstCacheRequestPolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void onSuccess(final Response<T> response) {
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.FirstCacheRequestPolicy.1
            @Override // java.lang.Runnable
            public void run() {
                FirstCacheRequestPolicy.this.mCallback.onSuccess(response);
                FirstCacheRequestPolicy.this.mCallback.onFinish();
            }
        });
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void onError(final Response<T> response) {
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.FirstCacheRequestPolicy.2
            @Override // java.lang.Runnable
            public void run() {
                FirstCacheRequestPolicy.this.mCallback.onError(response);
                FirstCacheRequestPolicy.this.mCallback.onFinish();
            }
        });
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
            if (cacheEntity != null) {
                Response.success(true, cacheEntity.getData(), this.rawCall, null);
            }
            Response<T> requestNetworkSync = requestNetworkSync();
            return (requestNetworkSync.isSuccessful() || cacheEntity == null) ? requestNetworkSync : Response.success(true, cacheEntity.getData(), this.rawCall, requestNetworkSync.getRawResponse());
        } catch (Throwable th) {
            return Response.error(false, this.rawCall, null, th);
        }
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void requestAsync(final CacheEntity<T> cacheEntity, Callback<T> callback) {
        this.mCallback = callback;
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.FirstCacheRequestPolicy.3
            @Override // java.lang.Runnable
            public void run() {
                FirstCacheRequestPolicy.this.mCallback.onStart(FirstCacheRequestPolicy.this.request);
                try {
                    FirstCacheRequestPolicy.this.prepareRawCall();
                    CacheEntity cacheEntity2 = cacheEntity;
                    if (cacheEntity2 != null) {
                        FirstCacheRequestPolicy.this.mCallback.onCacheSuccess(Response.success(true, cacheEntity2.getData(), FirstCacheRequestPolicy.this.rawCall, null));
                    }
                    FirstCacheRequestPolicy.this.requestNetworkAsync();
                } catch (Throwable th) {
                    FirstCacheRequestPolicy.this.mCallback.onError(Response.error(false, FirstCacheRequestPolicy.this.rawCall, null, th));
                }
            }
        });
    }
}
