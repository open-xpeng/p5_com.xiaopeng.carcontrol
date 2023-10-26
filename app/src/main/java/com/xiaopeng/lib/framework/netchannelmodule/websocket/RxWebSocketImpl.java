package com.xiaopeng.lib.framework.netchannelmodule.websocket;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IRxWebSocket;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IWebSocketConfig;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IWebSocketInfo;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import okio.ByteString;

/* loaded from: classes2.dex */
public final class RxWebSocketImpl implements IRxWebSocket {
    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IRxWebSocket
    public IWebSocketConfig config() {
        return new WebSocketConfig();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IRxWebSocket
    public RxWebSocketImpl header(String str, String str2) {
        RxWebSocketInternal.getInstance().header(str, str2);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IRxWebSocket
    public Observable<IWebSocketInfo> get(String str) {
        return RxWebSocketInternal.getInstance().getWebSocketInfo(str);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IRxWebSocket
    public Observable<IWebSocketInfo> get(String str, long j) {
        return RxWebSocketInternal.getInstance().getWebSocketInfo(str, j, TimeUnit.MILLISECONDS);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IRxWebSocket
    public void send(String str, String str2) {
        RxWebSocketInternal.getInstance().send(str, str2);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IRxWebSocket
    public void send(String str, ByteString byteString) {
        RxWebSocketInternal.getInstance().send(str, byteString);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.IRxWebSocket
    public void close(String str) {
        RxWebSocketInternal.getInstance().close(str);
    }
}
