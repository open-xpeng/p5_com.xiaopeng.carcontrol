package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;

/* loaded from: classes2.dex */
public interface IEvent {

    /* loaded from: classes2.dex */
    public enum TYPE {
        CONNECTED,
        DISCONNECTED,
        SUBSCRIBED,
        UNSUBSCRIBED,
        RECEIVED_MESSAGE,
        DELIVERY_COMPLETED,
        DELIVERY_FAILURE,
        TRACE
    }

    IMessaging.CHANNEL channel();

    byte[] messageContent();

    long messageId();

    String messageTopic();

    int protocolReasonCode();

    int reasonCode();

    TYPE type();
}
