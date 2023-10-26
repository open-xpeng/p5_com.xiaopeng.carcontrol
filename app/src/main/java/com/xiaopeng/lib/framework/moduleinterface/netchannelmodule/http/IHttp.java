package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http;

/* loaded from: classes2.dex */
public interface IHttp {
    IBizHelper bizHelper();

    void cancelTag(Object obj);

    IConfig config();

    IRequest get(String str);

    IRequest head(String str);

    IRequest post(String str);

    IRequest requestXmartBiz(String str);
}
