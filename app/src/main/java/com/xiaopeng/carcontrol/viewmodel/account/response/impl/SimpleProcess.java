package com.xiaopeng.carcontrol.viewmodel.account.response.impl;

import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;

/* loaded from: classes2.dex */
public class SimpleProcess implements Callback {
    private static final String TAG = "X_EXAM_SimpleProcess";

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
    public void onSuccess(IResponse iResponse) {
        if (iResponse == null) {
            LogUtils.w(TAG, "Request success ,result without response.");
        } else {
            LogUtils.i(TAG, "Request success result: " + iResponse.getRawResponse());
        }
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
    public void onFailure(IResponse iResponse) {
        if (iResponse == null) {
            LogUtils.w(TAG, "Request failure ,result without response.");
        } else {
            LogUtils.e(TAG, "Request success failure, response : " + iResponse.getRawResponse() + "exception: " + iResponse.getException(), false);
        }
    }
}
