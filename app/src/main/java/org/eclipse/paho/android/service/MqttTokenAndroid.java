package org.eclipse.paho.android.service;

import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class MqttTokenAndroid implements IMqttToken {
    private MqttAndroidClient client;
    private IMqttToken delegate;
    private volatile boolean isComplete;
    private volatile MqttException lastException;
    private IMqttActionListener listener;
    private MqttException pendingException;
    private String[] topics;
    private Object userContext;
    private Object waitObject;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MqttTokenAndroid(MqttAndroidClient client, Object userContext, IMqttActionListener listener) {
        this(client, userContext, listener, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MqttTokenAndroid(MqttAndroidClient client, Object userContext, IMqttActionListener listener, String[] topics) {
        this.waitObject = new Object();
        this.client = client;
        this.userContext = userContext;
        this.listener = listener;
        this.topics = topics;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public synchronized void waitForCompletion() throws MqttException, MqttSecurityException {
        synchronized (this.waitObject) {
            try {
                this.waitObject.wait();
            } catch (InterruptedException unused) {
            }
        }
        MqttException mqttException = this.pendingException;
        if (mqttException != null) {
            throw mqttException;
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public void waitForCompletion(long timeout) throws MqttException, MqttSecurityException {
        synchronized (this.waitObject) {
            try {
                this.waitObject.wait(timeout);
            } catch (InterruptedException unused) {
            }
            if (!this.isComplete) {
                throw new MqttException(32000);
            }
            MqttException mqttException = this.pendingException;
            if (mqttException != null) {
                throw mqttException;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyComplete() {
        synchronized (this.waitObject) {
            this.isComplete = true;
            this.waitObject.notifyAll();
            IMqttActionListener iMqttActionListener = this.listener;
            if (iMqttActionListener != null) {
                iMqttActionListener.onSuccess(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void notifyFailure(Throwable exception) {
        synchronized (this.waitObject) {
            this.isComplete = true;
            if (exception instanceof MqttException) {
                this.pendingException = (MqttException) exception;
            } else {
                this.pendingException = new MqttException(exception);
            }
            this.waitObject.notifyAll();
            if (exception instanceof MqttException) {
                this.lastException = (MqttException) exception;
            }
            IMqttActionListener iMqttActionListener = this.listener;
            if (iMqttActionListener != null) {
                iMqttActionListener.onFailure(this, exception);
            }
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public boolean isComplete() {
        return this.isComplete;
    }

    void setComplete(boolean complete) {
        this.isComplete = complete;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public MqttException getException() {
        return this.lastException;
    }

    void setException(MqttException exception) {
        this.lastException = exception;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public IMqttAsyncClient getClient() {
        return this.client;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public void setActionCallback(IMqttActionListener listener) {
        this.listener = listener;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public IMqttActionListener getActionCallback() {
        return this.listener;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public String[] getTopics() {
        IMqttToken iMqttToken = this.delegate;
        if (iMqttToken != null && iMqttToken.getTopics() != null) {
            return this.delegate.getTopics();
        }
        return this.topics;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public void setUserContext(Object userContext) {
        this.userContext = userContext;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public Object getUserContext() {
        return this.userContext;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDelegate(IMqttToken delegate) {
        this.delegate = delegate;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public int getMessageId() {
        IMqttToken iMqttToken = this.delegate;
        if (iMqttToken != null) {
            return iMqttToken.getMessageId();
        }
        return 0;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public MqttWireMessage getResponse() {
        return this.delegate.getResponse();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public boolean getSessionPresent() {
        return this.delegate.getSessionPresent();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttToken
    public int[] getGrantedQos() {
        return this.delegate.getGrantedQos();
    }

    public String toString() {
        return "MqttTokenAndroid{topics=" + Arrays.toString(this.topics) + '}';
    }
}
