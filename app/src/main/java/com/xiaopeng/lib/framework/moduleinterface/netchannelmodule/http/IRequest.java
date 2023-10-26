package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IServerCallback;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface IRequest {
    IResponse execute() throws IOException;

    void execute(Callback callback);

    void execute(IServerCallback iServerCallback);

    String getUrl();

    String header(String str);

    IRequest headers(String str, String str2);

    Map<String, List<String>> headers();

    IRequest isMultipart(boolean z);

    IRequest params(String str, float f);

    IRequest params(String str, int i);

    IRequest params(String str, File file);

    IRequest params(String str, String str2);

    IRequest params(String str, boolean z);

    IRequest params(Map<String, String> map, boolean... zArr);

    IRequest removeAllHeaders();

    IRequest removeAllParams();

    IRequest removeHeader(String str);

    IRequest removeParam(String str);

    IRequest tag(Object obj);

    IRequest uploadFile(String str) throws IOException;

    IRequest uploadJson(String str);
}
