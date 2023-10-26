package com.xiaopeng.lib.framework.netchannelmodule.messaging.events;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;

/* loaded from: classes2.dex */
public class TracingEvent implements IEvent {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private IMessaging.CHANNEL mChannel;
    private byte[] mMessage;
    private IEvent.TYPE mType = IEvent.TYPE.TRACE;

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public long messageId() {
        return 0L;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public String messageTopic() {
        return null;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public int protocolReasonCode() {
        return 0;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public int reasonCode() {
        return 0;
    }

    public TracingEvent message(String str) {
        this.mMessage = str.getBytes();
        return this;
    }

    public TracingEvent message(byte[] bArr) {
        this.mMessage = bArr;
        return this;
    }

    public TracingEvent channel(IMessaging.CHANNEL channel) {
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
    public byte[] messageContent() {
        return this.mMessage;
    }
}
