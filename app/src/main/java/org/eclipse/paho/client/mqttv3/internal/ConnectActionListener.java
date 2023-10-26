package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttToken;

/* loaded from: classes3.dex */
public class ConnectActionListener implements IMqttActionListener {
    private MqttAsyncClient client;
    private ClientComms comms;
    private MqttCallbackExtended mqttCallbackExtended;
    private MqttConnectOptions options;
    private int originalMqttVersion;
    private MqttClientPersistence persistence;
    private boolean reconnect;
    private IMqttActionListener userCallback;
    private Object userContext;
    private MqttToken userToken;

    public ConnectActionListener(MqttAsyncClient mqttAsyncClient, MqttClientPersistence mqttClientPersistence, ClientComms clientComms, MqttConnectOptions mqttConnectOptions, MqttToken mqttToken, Object obj, IMqttActionListener iMqttActionListener, boolean z) {
        this.persistence = mqttClientPersistence;
        this.client = mqttAsyncClient;
        this.comms = clientComms;
        this.options = mqttConnectOptions;
        this.userToken = mqttToken;
        this.userContext = obj;
        this.userCallback = iMqttActionListener;
        this.originalMqttVersion = mqttConnectOptions.getMqttVersion();
        this.reconnect = z;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onSuccess(IMqttToken iMqttToken) {
        if (this.originalMqttVersion == 0) {
            this.options.setMqttVersion(0);
        }
        this.userToken.internalTok.markComplete(iMqttToken.getResponse(), null);
        this.userToken.internalTok.notifyComplete();
        this.userToken.internalTok.setClient(this.client);
        this.comms.notifyConnect();
        if (this.userCallback != null) {
            this.userToken.setUserContext(this.userContext);
            this.userCallback.onSuccess(this.userToken);
        }
        if (this.mqttCallbackExtended != null) {
            this.mqttCallbackExtended.connectComplete(this.reconnect, this.comms.getNetworkModules()[this.comms.getNetworkModuleIndex()].getServerURI());
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onFailure(IMqttToken iMqttToken, Throwable th) {
        MqttException mqttException;
        int length = this.comms.getNetworkModules().length;
        int networkModuleIndex = this.comms.getNetworkModuleIndex() + 1;
        if (networkModuleIndex < length || (this.originalMqttVersion == 0 && this.options.getMqttVersion() == 4)) {
            if (this.originalMqttVersion == 0) {
                if (this.options.getMqttVersion() == 4) {
                    this.options.setMqttVersion(3);
                } else {
                    this.options.setMqttVersion(4);
                    this.comms.setNetworkModuleIndex(networkModuleIndex);
                }
            } else {
                this.comms.setNetworkModuleIndex(networkModuleIndex);
            }
            try {
                connect();
                return;
            } catch (MqttPersistenceException e) {
                onFailure(iMqttToken, e);
                return;
            }
        }
        if (this.originalMqttVersion == 0) {
            this.options.setMqttVersion(0);
        }
        if (th instanceof MqttException) {
            mqttException = (MqttException) th;
        } else {
            mqttException = new MqttException(th);
        }
        this.userToken.internalTok.markComplete(null, mqttException);
        this.userToken.internalTok.notifyComplete();
        this.userToken.internalTok.setClient(this.client);
        if (this.userCallback != null) {
            this.userToken.setUserContext(this.userContext);
            this.userCallback.onFailure(this.userToken, th);
        }
    }

    public void connect() throws MqttPersistenceException {
        MqttToken mqttToken = new MqttToken(this.client.getClientId());
        mqttToken.setActionCallback(this);
        mqttToken.setUserContext(this);
        this.persistence.open(this.client.getClientId(), this.client.getServerURI());
        if (this.options.isCleanSession()) {
            this.persistence.clear();
        }
        if (this.options.getMqttVersion() == 0) {
            this.options.setMqttVersion(4);
        }
        try {
            this.comms.connect(this.options, mqttToken);
        } catch (MqttException e) {
            onFailure(mqttToken, e);
        }
    }

    public void setMqttCallbackExtended(MqttCallbackExtended mqttCallbackExtended) {
        this.mqttCallbackExtended = mqttCallbackExtended;
    }
}
