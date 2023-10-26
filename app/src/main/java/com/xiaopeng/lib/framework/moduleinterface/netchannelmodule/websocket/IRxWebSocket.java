package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket;

import io.reactivex.Observable;
import okio.ByteString;

/* loaded from: classes2.dex */
public interface IRxWebSocket {
    void close(String str);

    IWebSocketConfig config();

    Observable<IWebSocketInfo> get(String str);

    Observable<IWebSocketInfo> get(String str, long j);

    IRxWebSocket header(String str, String str2);

    void send(String str, String str2) throws Exception;

    void send(String str, ByteString byteString) throws Exception;
}
