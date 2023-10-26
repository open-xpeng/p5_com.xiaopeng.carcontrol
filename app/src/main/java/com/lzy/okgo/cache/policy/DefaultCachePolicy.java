package com.lzy.okgo.cache.policy;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.exception.CacheException;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import okhttp3.Call;

/* loaded from: classes.dex */
public class DefaultCachePolicy<T> extends BaseCachePolicy<T> {
    public DefaultCachePolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void onSuccess(final Response<T> response) {
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.DefaultCachePolicy.1
            @Override // java.lang.Runnable
            public void run() {
                DefaultCachePolicy.this.mCallback.onSuccess(response);
                DefaultCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void onError(final Response<T> response) {
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.DefaultCachePolicy.2
            @Override // java.lang.Runnable
            public void run() {
                DefaultCachePolicy.this.mCallback.onError(response);
                DefaultCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    @Override // com.lzy.okgo.cache.policy.BaseCachePolicy, com.lzy.okgo.cache.policy.CachePolicy
    public boolean onAnalysisResponse(Call call, okhttp3.Response response) {
        if (response.code() != 304) {
            return false;
        }
        if (this.cacheEntity == null) {
            final Response error = Response.error(true, call, response, CacheException.NON_AND_304(this.request.getCacheKey()));
            runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.DefaultCachePolicy.3
                @Override // java.lang.Runnable
                public void run() {
                    DefaultCachePolicy.this.mCallback.onError(error);
                    DefaultCachePolicy.this.mCallback.onFinish();
                }
            });
        } else {
            final Response success = Response.success(true, this.cacheEntity.getData(), call, response);
            runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.DefaultCachePolicy.4
                @Override // java.lang.Runnable
                public void run() {
                    DefaultCachePolicy.this.mCallback.onCacheSuccess(success);
                    DefaultCachePolicy.this.mCallback.onFinish();
                }
            });
        }
        return true;
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
            Response<T> requestNetworkSync = requestNetworkSync();
            if (requestNetworkSync.isSuccessful() && requestNetworkSync.code() == 304) {
                if (cacheEntity == null) {
                    return Response.error(true, this.rawCall, requestNetworkSync.getRawResponse(), CacheException.NON_AND_304(this.request.getCacheKey()));
                }
                return Response.success(true, cacheEntity.getData(), this.rawCall, requestNetworkSync.getRawResponse());
            }
            return requestNetworkSync;
        } catch (Throwable th) {
            return Response.error(false, this.rawCall, null, th);
        }
    }

    @Override // com.lzy.okgo.cache.policy.CachePolicy
    public void requestAsync(CacheEntity<T> cacheEntity, Callback<T> callback) {
        this.mCallback = callback;
        runOnUiThread(new Runnable() { // from class: com.lzy.okgo.cache.policy.DefaultCachePolicy.5
            @Override // java.lang.Runnable
            public void run() {
                DefaultCachePolicy.this.mCallback.onStart(DefaultCachePolicy.this.request);
                try {
                    DefaultCachePolicy.this.prepareRawCall();
                    DefaultCachePolicy.this.requestNetworkAsync();
                } catch (Throwable th) {
                    DefaultCachePolicy.this.mCallback.onError(Response.error(false, DefaultCachePolicy.this.rawCall, null, th));
                }
            }
        });
    }
}
