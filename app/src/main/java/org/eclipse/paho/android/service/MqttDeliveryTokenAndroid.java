package org.eclipse.paho.android.service;

import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class MqttDeliveryTokenAndroid extends MqttTokenAndroid implements IMqttDeliveryToken {
    private MqttMessage message;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MqttDeliveryTokenAndroid(MqttAndroidClient client, Object userContext, IMqttActionListener listener, MqttMessage message) {
        super(client, userContext, listener);
        this.message = message;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
    public MqttMessage getMessage() throws MqttException {
        return this.message;
    }

    void setMessage(MqttMessage message) {
        this.message = message;
    }

    void notifyDelivery(MqttMessage delivered) {
        this.message = delivered;
        super.notifyComplete();
    }

    @Override // org.eclipse.paho.android.service.MqttTokenAndroid
    public String toString() {
        return "MqttDeliveryTokenAndroid{topics=" + Arrays.toString(getTopics()) + '}';
    }
}
