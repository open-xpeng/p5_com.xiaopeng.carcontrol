package com.lzy.okgo.cache.policy;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/* loaded from: classes.dex */
public class NoneCacheRequestPolicy<T> extends BaseCachePolicy<T> {
    public NoneCacheRequestPolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void onSuccess(final Response<T> response) {
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.NoneCacheRequestPolicy.1
            @Override // java.lang.Runnable
            public void run() {
                NoneCacheRequestPolicy.this.mCallback.onSuccess(response);
                NoneCacheRequestPolicy.this.mCallback.onFinish();
            }
        });
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void onError(final Response<T> response) {
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.NoneCacheRequestPolicy.2
            @Override // java.lang.Runnable
            public void run() {
                NoneCacheRequestPolicy.this.mCallback.onError(response);
                NoneCacheRequestPolicy.this.mCallback.onFinish();
            }
        });
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
            Response<T> success = cacheEntity != null ? Response.success(true, cacheEntity.getData(), this.rawCall, null) : null;
            return success == null ? requestNetworkSync() : success;
        } catch (Throwable th) {
            return Response.error(false, this.rawCall, null, th);
        }
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void requestAsync(final CacheEntity<T> cacheEntity, Callback<T> callback) {
        this.mCallback = callback;
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.NoneCacheRequestPolicy.3
            @Override // java.lang.Runnable
            public void run() {
                NoneCacheRequestPolicy.this.mCallback.onStart(NoneCacheRequestPolicy.this.request);
                try {
                    NoneCacheRequestPolicy.this.prepareRawCall();
                    CacheEntity cacheEntity2 = cacheEntity;
                    if (cacheEntity2 != null) {
                        NoneCacheRequestPolicy.this.mCallback.onCacheSuccess(Response.success(true, cacheEntity2.getData(), NoneCacheRequestPolicy.this.rawCall, null));
                        NoneCacheRequestPolicy.this.mCallback.onFinish();
                        return;
                    }
                    NoneCacheRequestPolicy.this.requestNetworkAsync();
                } catch (Throwable th) {
                    NoneCacheRequestPolicy.this.mCallback.onError(Response.error(false, NoneCacheRequestPolicy.this.rawCall, null, th));
                }
            }
        });
    }
}
