package com.xiaopeng.lib.framework.netchannelmodule.http.xmart;

import com.lzy.okgo.model.Response;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IServerCallback;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.BasePostRequestAdapter;

/* loaded from: classes2.dex */
public class ServerRequest extends BasePostRequestAdapter<ServerBean> {
    public ServerRequest(String str) {
        super(str);
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.BasePostRequestAdapter, com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest
    public void execute(IServerCallback iServerCallback) {
        super.execute(new ServerCallbackImplAdapter(iServerCallback));
    }

    /* loaded from: classes2.dex */
    public class ServerCallbackImplAdapter extends ServerCallbackImpl {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private IServerCallback mOuterCallback;

        public ServerCallbackImplAdapter(IServerCallback iServerCallback) {
            this.mOuterCallback = iServerCallback;
        }

        @Override // com.lzy.okgo.callback.AbsCallback, com.lzy.okgo.callback.Callback
        public void onError(Response<ServerBean> response) {
            super.onError(response);
            this.mOuterCallback.onFailure(new ServerResponse(response));
        }

        @Override // com.lzy.okgo.callback.Callback
        public void onSuccess(Response<ServerBean> response) {
            this.mOuterCallback.onSuccess(new ServerResponse(response));
        }
    }
}
