package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/* loaded from: classes3.dex */
public class MqttPingReq extends MqttWireMessage {
    public static final String KEY = "Ping";

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public String getKey() {
        return "Ping";
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte getMessageInfo() {
        return (byte) 0;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    protected byte[] getVariableHeader() throws MqttException {
        return new byte[0];
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
    public boolean isMessageIdRequired() {
        return false;
    }

    public MqttPingReq() {
        super(MqttWireMessage.MESSAGE_TYPE_PINGREQ);
    }

    public MqttPingReq(byte b, byte[] bArr) throws IOException {
        super(MqttWireMessage.MESSAGE_TYPE_PINGREQ);
    }
}
