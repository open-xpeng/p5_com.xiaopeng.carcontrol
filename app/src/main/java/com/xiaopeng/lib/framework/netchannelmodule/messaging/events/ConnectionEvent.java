package com.xiaopeng.lib.framework.netchannelmodule.messaging.events;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;

/* loaded from: classes2.dex */
public class ConnectionEvent implements IEvent {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private IMessaging.CHANNEL mChannel;
    private int mProtocolReasonCode = 0;
    private int mReasonCode;
    private byte[] mServerUri;
    private IEvent.TYPE mType;

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public long messageId() {
        return 0L;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public String messageTopic() {
        return null;
    }

    public ConnectionEvent(IEvent.TYPE type) {
        this.mType = type;
    }

    public ConnectionEvent reasonCode(int i) {
        this.mReasonCode = i;
        return this;
    }

    public ConnectionEvent protocolReasonCode(int i) {
        this.mProtocolReasonCode = i;
        return this;
    }

    public ConnectionEvent channel(IMessaging.CHANNEL channel) {
        this.mChannel = channel;
        return this;
    }

    public ConnectionEvent serverUri(String str) {
        this.mServerUri = str.getBytes();
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
    public int reasonCode() {
        return this.mReasonCode;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public byte[] messageContent() {
        return this.mServerUri;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IEvent
    public int protocolReasonCode() {
        return this.mProtocolReasonCode;
    }
}
