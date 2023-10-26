package org.eclipse.paho.client.mqttv3.internal;

import com.xiaopeng.speech.protocol.event.OOBEEvent;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes3.dex */
public class Token {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.Token";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, Token.class.getName());
    private String key;
    private volatile boolean completed = false;
    private boolean pendingComplete = false;
    private boolean sent = false;
    private Object responseLock = new Object();
    private Object sentLock = new Object();
    protected MqttMessage message = null;
    private MqttWireMessage response = null;
    private MqttException exception = null;
    private String[] topics = null;
    private IMqttAsyncClient client = null;
    private IMqttActionListener callback = null;
    private Object userContext = null;
    private int messageID = 0;
    private boolean notified = false;

    public Token(String str) {
        log.setResourceName(str);
    }

    public int getMessageID() {
        return this.messageID;
    }

    public void setMessageID(int i) {
        this.messageID = i;
    }

    public boolean checkResult() throws MqttException {
        if (getException() == null) {
            return true;
        }
        throw getException();
    }

    public MqttException getException() {
        return this.exception;
    }

    public boolean isComplete() {
        return this.completed;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCompletePending() {
        return this.pendingComplete;
    }

    protected boolean isInUse() {
        return (getClient() == null || isComplete()) ? false : true;
    }

    public void setActionCallback(IMqttActionListener iMqttActionListener) {
        this.callback = iMqttActionListener;
    }

    public IMqttActionListener getActionCallback() {
        return this.callback;
    }

    public void waitForCompletion() throws MqttException {
        waitForCompletion(-1L);
    }

    public void waitForCompletion(long j) throws MqttException {
        Logger logger = log;
        String str = CLASS_NAME;
        logger.fine(str, "waitForCompletion", "407", new Object[]{getKey(), new Long(j), this});
        if (waitForResponse(j) == null && !this.completed) {
            logger.fine(str, "waitForCompletion", "406", new Object[]{getKey(), this});
            MqttException mqttException = new MqttException(32000);
            this.exception = mqttException;
            throw mqttException;
        }
        checkResult();
    }

    protected MqttWireMessage waitForResponse() throws MqttException {
        return waitForResponse(-1L);
    }

    protected MqttWireMessage waitForResponse(long j) throws MqttException {
        synchronized (this.responseLock) {
            Logger logger = log;
            String str = CLASS_NAME;
            Object[] objArr = new Object[7];
            objArr[0] = getKey();
            objArr[1] = new Long(j);
            objArr[2] = new Boolean(this.sent);
            objArr[3] = new Boolean(this.completed);
            MqttException mqttException = this.exception;
            objArr[4] = mqttException == null ? OOBEEvent.STRING_FALSE : OOBEEvent.STRING_TRUE;
            objArr[5] = this.response;
            objArr[6] = this;
            logger.fine(str, "waitForResponse", "400", objArr, mqttException);
            while (!this.completed) {
                if (this.exception == null) {
                    try {
                        log.fine(CLASS_NAME, "waitForResponse", "408", new Object[]{getKey(), new Long(j)});
                        if (j <= 0) {
                            this.responseLock.wait();
                        } else {
                            this.responseLock.wait(j);
                        }
                    } catch (InterruptedException e) {
                        this.exception = new MqttException(e);
                    }
                }
                if (!this.completed) {
                    MqttException mqttException2 = this.exception;
                    if (mqttException2 != null) {
                        log.fine(CLASS_NAME, "waitForResponse", "401", null, mqttException2);
                        throw this.exception;
                    } else if (j > 0) {
                        break;
                    }
                }
            }
        }
        log.fine(CLASS_NAME, "waitForResponse", "402", new Object[]{getKey(), this.response});
        return this.response;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void markComplete(MqttWireMessage mqttWireMessage, MqttException mqttException) {
        log.fine(CLASS_NAME, "markComplete", "404", new Object[]{getKey(), mqttWireMessage, mqttException});
        synchronized (this.responseLock) {
            if (mqttWireMessage instanceof MqttAck) {
                this.message = null;
            }
            this.pendingComplete = true;
            this.response = mqttWireMessage;
            this.exception = mqttException;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void notifyComplete() {
        log.fine(CLASS_NAME, "notifyComplete", "404", new Object[]{getKey(), this.response, this.exception});
        synchronized (this.responseLock) {
            if (this.exception == null && this.pendingComplete) {
                this.completed = true;
                this.pendingComplete = false;
            } else {
                this.pendingComplete = false;
            }
            this.responseLock.notifyAll();
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    public void waitUntilSent() throws MqttException {
        boolean z;
        synchronized (this.sentLock) {
            synchronized (this.responseLock) {
                MqttException mqttException = this.exception;
                if (mqttException != null) {
                    throw mqttException;
                }
            }
            while (true) {
                z = this.sent;
                if (z) {
                    break;
                }
                try {
                    log.fine(CLASS_NAME, "waitUntilSent", "409", new Object[]{getKey()});
                    this.sentLock.wait();
                } catch (InterruptedException unused) {
                }
            }
            if (!z) {
                MqttException mqttException2 = this.exception;
                if (mqttException2 == null) {
                    throw ExceptionHelper.createMqttException(6);
                }
                throw mqttException2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void notifySent() {
        log.fine(CLASS_NAME, "notifySent", "403", new Object[]{getKey()});
        synchronized (this.responseLock) {
            this.response = null;
            this.completed = false;
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setClient(IMqttAsyncClient iMqttAsyncClient) {
        this.client = iMqttAsyncClient;
    }

    public void reset() throws MqttException {
        if (isInUse()) {
            throw new MqttException(32201);
        }
        log.fine(CLASS_NAME, "reset", "410", new Object[]{getKey()});
        this.client = null;
        this.completed = false;
        this.response = null;
        this.sent = false;
        this.exception = null;
        this.userContext = null;
    }

    public MqttMessage getMessage() {
        return this.message;
    }

    public MqttWireMessage getWireMessage() {
        return this.response;
    }

    public void setMessage(MqttMessage mqttMessage) {
        this.message = mqttMessage;
    }

    public String[] getTopics() {
        return this.topics;
    }

    public void setTopics(String[] strArr) {
        this.topics = strArr;
    }

    public Object getUserContext() {
        return this.userContext;
    }

    public void setUserContext(Object obj) {
        this.userContext = obj;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getKey() {
        return this.key;
    }

    public void setException(MqttException mqttException) {
        synchronized (this.responseLock) {
            this.exception = mqttException;
        }
    }

    public boolean isNotified() {
        return this.notified;
    }

    public void setNotified(boolean z) {
        this.notified = z;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=").append(getKey());
        stringBuffer.append(" ,topics=");
        if (getTopics() != null) {
            for (int i = 0; i < getTopics().length; i++) {
                stringBuffer.append(getTopics()[i]).append(", ");
            }
        }
        stringBuffer.append(" ,usercontext=").append(getUserContext());
        stringBuffer.append(" ,isComplete=").append(isComplete());
        stringBuffer.append(" ,isNotified=").append(isNotified());
        stringBuffer.append(" ,exception=").append(getException());
        stringBuffer.append(" ,actioncallback=").append(getActionCallback());
        return stringBuffer.toString();
    }

    public int[] getGrantedQos() {
        int[] iArr = new int[0];
        MqttWireMessage mqttWireMessage = this.response;
        return mqttWireMessage instanceof MqttSuback ? ((MqttSuback) mqttWireMessage).getGrantedQos() : iArr;
    }

    public boolean getSessionPresent() {
        MqttWireMessage mqttWireMessage = this.response;
        if (mqttWireMessage instanceof MqttConnack) {
            return ((MqttConnack) mqttWireMessage).getSessionPresent();
        }
        return false;
    }

    public MqttWireMessage getResponse() {
        return this.response;
    }
}
