package org.eclipse.paho.android.service;

import java.util.Iterator;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* loaded from: classes3.dex */
interface MessageStore {

    /* loaded from: classes3.dex */
    public interface StoredMessage {
        String getClientHandle();

        MqttMessage getMessage();

        String getMessageId();

        String getTopic();
    }

    void clearArrivedMessages(String clientHandle);

    void close();

    boolean discardArrived(String clientHandle, String id);

    Iterator<StoredMessage> getAllArrivedMessages(String clientHandle);

    String storeArrived(String clientHandle, String Topic, MqttMessage message);
}
