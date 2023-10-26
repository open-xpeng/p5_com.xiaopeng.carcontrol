package com.lzy.okgo.callback;

import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/* loaded from: classes.dex */
public interface Callback<T> extends Converter<T> {
    void downloadProgress(Progress progress);

    void onCacheSuccess(Response<T> response);

    void onError(Response<T> response);

    void onFinish();

    void onStart(Request<T, ? extends Request> request);

    void onSuccess(Response<T> response);

    void uploadProgress(Progress progress);
}
