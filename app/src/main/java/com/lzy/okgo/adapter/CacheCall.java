package com.lzy.okgo.adapter;

import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cache.policy.CachePolicy;
import com.lzy.okgo.cache.policy.DefaultCachePolicy;
import com.lzy.okgo.cache.policy.FirstCacheRequestPolicy;
import com.lzy.okgo.cache.policy.NoCachePolicy;
import com.lzy.okgo.cache.policy.NoneCacheRequestPolicy;
import com.lzy.okgo.cache.policy.RequestFailedCachePolicy;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.HttpUtils;

/* loaded from: classes.dex */
public class CacheCall<T> implements Call<T> {
    private CachePolicy<T> policy;
    private Request<T, ? extends Request> request;

    public CacheCall(Request<T, ? extends Request> request) {
        this.policy = null;
        this.request = request;
        this.policy = preparePolicy();
    }

    @Override // com.lzy.okgo.adapter.Call
    public Response<T> execute() {
        return this.policy.requestSync(this.policy.prepareCache());
    }

    @Override // com.lzy.okgo.adapter.Call
    public void execute(Callback<T> callback) {
        HttpUtils.checkNotNull(callback, "callback == null");
        this.policy.requestAsync(this.policy.prepareCache(), callback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.lzy.okgo.adapter.CacheCall$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$lzy$okgo$cache$CacheMode;

        static {
            int[] iArr = new int[CacheMode.values().length];
            $SwitchMap$com$lzy$okgo$cache$CacheMode = iArr;
            try {
                iArr[CacheMode.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lzy$okgo$cache$CacheMode[CacheMode.NO_CACHE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lzy$okgo$cache$CacheMode[CacheMode.IF_NONE_CACHE_REQUEST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lzy$okgo$cache$CacheMode[CacheMode.FIRST_CACHE_THEN_REQUEST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$lzy$okgo$cache$CacheMode[CacheMode.REQUEST_FAILED_READ_CACHE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private CachePolicy<T> preparePolicy() {
        int i = AnonymousClass1.$SwitchMap$com$lzy$okgo$cache$CacheMode[this.request.getCacheMode().ordinal()];
        if (i == 1) {
            this.policy = new DefaultCachePolicy(this.request);
        } else if (i == 2) {
            this.policy = new NoCachePolicy(this.request);
        } else if (i == 3) {
            this.policy = new NoneCacheRequestPolicy(this.request);
        } else if (i == 4) {
            this.policy = new FirstCacheRequestPolicy(this.request);
        } else if (i == 5) {
            this.policy = new RequestFailedCachePolicy(this.request);
        }
        if (this.request.getCachePolicy() != null) {
            this.policy = this.request.getCachePolicy();
        }
        HttpUtils.checkNotNull(this.policy, "policy == null");
        return this.policy;
    }

    @Override // com.lzy.okgo.adapter.Call
    public boolean isExecuted() {
        return this.policy.isExecuted();
    }

    @Override // com.lzy.okgo.adapter.Call
    public void cancel() {
        this.policy.cancel();
    }

    @Override // com.lzy.okgo.adapter.Call
    public boolean isCanceled() {
        return this.policy.isCanceled();
    }

    @Override // com.lzy.okgo.adapter.Call
    /* renamed from: clone */
    public Call<T> m69clone() {
        return new CacheCall(this.request);
    }

    @Override // com.lzy.okgo.adapter.Call
    public Request getRequest() {
        return this.request;
    }
}
