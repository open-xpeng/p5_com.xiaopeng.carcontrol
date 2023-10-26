package com.xiaopeng.lib.framework.netchannelmodule.messaging.exception;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.MessagingException;
import org.eclipse.paho.client.mqttv3.MqttException;

/* loaded from: classes2.dex */
public class MessagingExceptionImpl extends MessagingException {
    private static final long serialVersionUID = 100;
    private int mProtocolReasonCode;
    private int mReasonCode;

    public MessagingExceptionImpl(int i) {
        super("");
        this.mReasonCode = i;
        this.mProtocolReasonCode = 0;
    }

    public MessagingExceptionImpl(MqttException mqttException) {
        super(mqttException.getMessage());
        this.mReasonCode = 1;
        this.mProtocolReasonCode = mqttException.getReasonCode();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.MessagingException
    public int getReasonCode() {
        return this.mReasonCode;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.MessagingException
    public int getProtocolReasonCode() {
        return this.mProtocolReasonCode;
    }
}
