package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import okhttp3.Response;

/* loaded from: classes2.dex */
public interface IResponse {
    String body();

    InputStream byteStream();

    int code();

    Throwable getException();

    Response getRawResponse();

    String header(String str);

    Map<String, List<String>> headers();

    String message();
}
