package com.lzy.okgo.callback;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.OkLogger;

/* loaded from: classes.dex */
public abstract class AbsCallback<T> implements Callback<T> {
    @Override // com.lzy.okgo.callback.Callback
    public void downloadProgress(Progress progress) {
    }

    @Override // com.lzy.okgo.callback.Callback
    public void onCacheSuccess(Response<T> response) {
    }

    @Override // com.lzy.okgo.callback.Callback
    public void onFinish() {
    }

    @Override // com.lzy.okgo.callback.Callback
    public void onStart(Request<T, ? extends Request> request) {
    }

    @Override // com.lzy.okgo.callback.Callback
    public void uploadProgress(Progress progress) {
    }

    @Override // com.lzy.okgo.callback.Callback
    public void onError(Response<T> response) {
        OkLogger.printStackTrace(response.getException());
    }
}
