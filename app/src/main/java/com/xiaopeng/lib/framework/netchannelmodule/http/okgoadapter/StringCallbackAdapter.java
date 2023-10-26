package com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;

/* loaded from: classes2.dex */
public class StringCallbackAdapter extends StringCallback {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Callback mOuterCallback;

    public StringCallbackAdapter(Callback callback) {
        this.mOuterCallback = callback;
    }

    @Override // com.lzy.okgo.callback.AbsCallback, com.lzy.okgo.callback.Callback
    public void onError(Response<String> response) {
        super.onError(response);
        this.mOuterCallback.onFailure(new ResponseAdapter(response));
    }

    @Override // com.lzy.okgo.callback.Callback
    public void onSuccess(Response<String> response) {
        this.mOuterCallback.onSuccess(new ResponseAdapter(response));
    }
}
