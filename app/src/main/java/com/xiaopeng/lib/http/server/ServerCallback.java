package com.xiaopeng.lib.http.server;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import okhttp3.Response;

/* loaded from: classes2.dex */
public abstract class ServerCallback extends AbsCallback<ServerBean> implements Callback<ServerBean> {
    public static final int CODE_SUCCESS = 200;
    private ServerConvert convert = new ServerConvert();

    @Override // com.lzy.okgo.convert.Converter
    public ServerBean convertResponse(Response response) throws Throwable {
        ServerBean convertResponse = this.convert.convertResponse(response);
        response.close();
        return convertResponse;
    }
}
