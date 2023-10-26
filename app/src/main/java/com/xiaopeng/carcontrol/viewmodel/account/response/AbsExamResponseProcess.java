package com.xiaopeng.carcontrol.viewmodel.account.response;

import com.google.gson.Gson;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.SafeExamUtils;
import com.xiaopeng.carcontrol.viewmodel.account.response.ResponseCallback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.ServerBean;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/* loaded from: classes2.dex */
public abstract class AbsExamResponseProcess<T extends ResponseCallback> implements Callback {
    private static final int HTTP_OK = 200;
    private static final String TAG = "X_EXAM_ExamResponseProcess";
    protected T mExamCallBack;
    protected Gson mGson = new Gson();

    @NotNull
    protected abstract void processExamSuccess(String data);

    /* JADX INFO: Access modifiers changed from: protected */
    public AbsExamResponseProcess(T callback) {
        Objects.requireNonNull(callback, "Please check the callback!");
        this.mExamCallBack = callback;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
    public void onSuccess(IResponse iResponse) {
        if (iResponse == null || iResponse.getRawResponse() == null) {
            this.mExamCallBack.onExamFailure(-1, "Response is null.");
            return;
        }
        LogUtils.i(TAG, "Get exam raw response:" + iResponse.getRawResponse());
        ServerBean parseResponse = SafeExamUtils.parseResponse(iResponse);
        if (parseResponse.code() == 200) {
            String data = parseResponse.data();
            if (data == null) {
                this.mExamCallBack.onExamFailure(-2, "Response data is null.");
                return;
            }
            LogUtils.i(TAG, "process exam response data: " + data);
            processExamSuccess(data);
            return;
        }
        this.mExamCallBack.onExamFailure(parseResponse.code(), "Http response is not okplease check the code:" + parseResponse.code());
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
    public void onFailure(IResponse iResponse) {
        this.mExamCallBack.onExamFailure(iResponse.code(), iResponse.message());
    }
}
