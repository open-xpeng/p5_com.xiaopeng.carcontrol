package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket;

import okio.ByteString;

/* loaded from: classes2.dex */
public interface IWebSocketInfo {
    public static final int CODE_NORMAL = 0;

    /* loaded from: classes2.dex */
    public enum STATE {
        OPEN,
        CLOSED
    }

    ByteString byteStringMessage();

    String closedReason();

    int closedReasonCode();

    boolean isClosed();

    boolean isOnOpen();

    boolean isOnReconnect();

    String stringMessage();
}
