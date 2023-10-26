package org.eclipse.paho.client.mqttv3;

/* loaded from: classes3.dex */
public class MqttPersistenceException extends MqttException {
    public static final short REASON_CODE_PERSISTENCE_IN_USE = 32200;
    private static final long serialVersionUID = 300;

    public MqttPersistenceException() {
        super(0);
    }

    public MqttPersistenceException(int i) {
        super(i);
    }

    public MqttPersistenceException(Throwable th) {
        super(th);
    }

    public MqttPersistenceException(int i, Throwable th) {
        super(i, th);
    }
}
