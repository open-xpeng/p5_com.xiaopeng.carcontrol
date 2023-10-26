package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes3.dex */
public interface DestinationProvider {
    MqttTopic getTopic(String str);
}
