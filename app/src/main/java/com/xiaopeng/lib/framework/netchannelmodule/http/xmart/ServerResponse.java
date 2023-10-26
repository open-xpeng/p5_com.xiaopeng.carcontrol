package com.xiaopeng.lib.framework.netchannelmodule.http.xmart;

import com.lzy.okgo.model.Response;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IXmartResponse;

/* loaded from: classes2.dex */
public class ServerResponse implements IXmartResponse {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    Response<ServerBean> mInternalResponse;

    public ServerResponse(Response<ServerBean> response) {
        this.mInternalResponse = response;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IXmartResponse
    public int code() {
        return this.mInternalResponse.code();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IXmartResponse
    public ServerBean body() {
        return this.mInternalResponse.body();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IXmartResponse
    public String message() {
        return this.mInternalResponse.message();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IXmartResponse
    public Throwable getException() {
        return this.mInternalResponse.getException();
    }
}
