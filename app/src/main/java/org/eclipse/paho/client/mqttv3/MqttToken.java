package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.Token;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* loaded from: classes3.dex */
public class MqttToken implements IMqttToken {
    public Token internalTok;

    public MqttToken() {
        this.internalTok = null;
    }

    public MqttToken(String str) {
        this.internalTok = null;
        this.internalTok = new Token(str);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public MqttException getException() {
        return this.internalTok.getException();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public boolean isComplete() {
        return this.internalTok.isComplete();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public void setActionCallback(IMqttActionListener iMqttActionListener) {
        this.internalTok.setActionCallback(iMqttActionListener);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public IMqttActionListener getActionCallback() {
        return this.internalTok.getActionCallback();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public void waitForCompletion() throws MqttException {
        this.internalTok.waitForCompletion(-1L);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public void waitForCompletion(long j) throws MqttException {
        this.internalTok.waitForCompletion(j);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public IMqttAsyncClient getClient() {
        return this.internalTok.getClient();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public String[] getTopics() {
        return this.internalTok.getTopics();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public Object getUserContext() {
        return this.internalTok.getUserContext();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public void setUserContext(Object obj) {
        this.internalTok.setUserContext(obj);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public int getMessageId() {
        return this.internalTok.getMessageID();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public int[] getGrantedQos() {
        return this.internalTok.getGrantedQos();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public boolean getSessionPresent() {
        return this.internalTok.getSessionPresent();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public MqttWireMessage getResponse() {
        return this.internalTok.getResponse();
    }
}
