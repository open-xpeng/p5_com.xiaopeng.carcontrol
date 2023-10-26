package com.xiaopeng.lib.framework.netchannelmodule.messaging.events;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;

/* loaded from: classes2.dex */
public class SubscribeEvent implements IEvent {
    private IMessaging.CHANNEL mChannel;
    private String mTopic;
    private IEvent.TYPE mType;

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public byte[] messageContent() {
        return null;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public long messageId() {
        return -1L;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public int protocolReasonCode() {
        return 0;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public int reasonCode() {
        return 0;
    }

    public SubscribeEvent(IEvent.TYPE type) {
        this.mType = type;
    }

    public SubscribeEvent topic(String str) {
        this.mTopic = str;
        return this;
    }

    public SubscribeEvent channel(IMessaging.CHANNEL channel) {
        this.mChannel = channel;
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public IMessaging.CHANNEL channel() {
        return this.mChannel;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public IEvent.TYPE type() {
        return this.mType;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public String messageTopic() {
        return this.mTopic;
    }
}
