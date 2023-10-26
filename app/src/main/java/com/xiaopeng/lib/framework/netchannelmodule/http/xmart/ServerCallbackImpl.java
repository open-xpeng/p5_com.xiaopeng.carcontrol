package com.xiaopeng.lib.framework.netchannelmodule.http.xmart;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import okhttp3.Response;

/* loaded from: classes2.dex */
public abstract class ServerCallbackImpl extends AbsCallback<ServerBean> implements Callback<ServerBean> {
    private ServerConverter convert = new ServerConverter();

    @Override // com.lzy.okgo.convert.Converter
    public ServerBean convertResponse(Response response) throws Throwable {
        ServerBean convertResponse = this.convert.convertResponse(response);
        response.close();
        return convertResponse;
    }
}
