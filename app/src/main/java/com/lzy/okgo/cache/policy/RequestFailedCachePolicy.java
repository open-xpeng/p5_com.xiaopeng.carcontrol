package com.lzy.okgo.cache.policy;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/* loaded from: classes.dex */
public class RequestFailedCachePolicy<T> extends BaseCachePolicy<T> {
    public RequestFailedCachePolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void onSuccess(final Response<T> response) {
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.RequestFailedCachePolicy.1
            @Override // java.lang.Runnable
            public void run() {
                RequestFailedCachePolicy.this.mCallback.onSuccess(response);
                RequestFailedCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void onError(final Response<T> response) {
        if (this.cacheEntity != null) {
            final Response success = Response.success(true, this.cacheEntity.getData(), response.getRawCall(), response.getRawResponse());
            runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.RequestFailedCachePolicy.2
                @Override // java.lang.Runnable
                public void run() {
                    RequestFailedCachePolicy.this.mCallback.onCacheSuccess(success);
                    RequestFailedCachePolicy.this.mCallback.onFinish();
                }
            });
            return;
        }
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.RequestFailedCachePolicy.3
            @Override // java.lang.Runnable
            public void run() {
                RequestFailedCachePolicy.this.mCallback.onError(response);
                RequestFailedCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
            Response<T> requestNetworkSync = requestNetworkSync();
            return (requestNetworkSync.isSuccessful() || cacheEntity == null) ? requestNetworkSync : Response.success(true, cacheEntity.getData(), this.rawCall, requestNetworkSync.getRawResponse());
        } catch (Throwable th) {
            return Response.error(false, this.rawCall, null, th);
        }
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void requestAsync(CacheEntity<T> cacheEntity, Callback<T> callback) {
        this.mCallback = callback;
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.RequestFailedCachePolicy.4
            @Override // java.lang.Runnable
            public void run() {
                RequestFailedCachePolicy.this.mCallback.onStart(RequestFailedCachePolicy.this.request);
                try {
                    RequestFailedCachePolicy.this.prepareRawCall();
                    RequestFailedCachePolicy.this.requestNetworkAsync();
                } catch (Throwable th) {
                    RequestFailedCachePolicy.this.mCallback.onError(Response.error(false, RequestFailedCachePolicy.this.rawCall, null, th));
                }
            }
        });
    }
}
