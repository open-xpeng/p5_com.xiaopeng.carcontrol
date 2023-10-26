package com.xiaopeng.lib;

/* loaded from: classes2.dex */
public class InitEventHolder implements HttpInitEventListener {
    private static volatile InitEventHolder sHttpInitEventListener = new InitEventHolder();
    private HttpInitEventListener mProxy;

    @Override // com.xiaopeng.lib.HttpInitEventListener
    public void onInitException(int i, String str) {
        HttpInitEventListener httpInitEventListener = this.mProxy;
        if (httpInitEventListener != null) {
            httpInitEventListener.onInitException(i, str);
        }
    }

    public static HttpInitEventListener get() {
        return sHttpInitEventListener;
    }

    public static void setProxy(HttpInitEventListener httpInitEventListener) {
        sHttpInitEventListener.mProxy = httpInitEventListener;
    }
}
