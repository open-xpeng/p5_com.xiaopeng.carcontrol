package org.eclipse.paho.android.service;

import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.paho.android.service.MessageStore;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingReq;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class MqttConnection implements MqttCallbackExtended {
    private static final long DISCONNECT_TIMEOUT = 5000;
    private static final String NOT_CONNECTED = "not connected";
    private static final boolean NO_DISCONNECT_PACKET = false;
    private static final long QUIESCE_TIMEOUT = 10000;
    private static final String TAG = "MqttConnection";
    private static final boolean USE_EXTERNAL_STORAGE = false;
    private String clientHandle;
    private String clientId;
    private MqttConnectOptions connectOptions;
    private MqttClientPersistence persistence;
    private String serverURI;
    private MqttService service;
    private String wakeLockTag;
    private String reconnectActivityToken = null;
    private MqttAsyncClient myClient = null;
    private AlarmPingSender alarmPingSender = null;
    private volatile boolean disconnected = true;
    private boolean cleanSession = true;
    private volatile boolean isConnecting = false;
    private Map<IMqttDeliveryToken, String> savedTopics = new HashMap();
    private Map<IMqttDeliveryToken, MqttMessage> savedSentMessages = new HashMap();
    private Map<IMqttDeliveryToken, String> savedActivityTokens = new HashMap();
    private Map<IMqttDeliveryToken, String> savedInvocationContexts = new HashMap();
    private PowerManager.WakeLock wakelock = null;
    private DisconnectedBufferOptions bufferOpts = null;

    public String getServerURI() {
        return this.serverURI;
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MqttConnectOptions getConnectOptions() {
        return this.connectOptions;
    }

    public void setConnectOptions(MqttConnectOptions connectOptions) {
        this.connectOptions = connectOptions;
    }

    public String getClientHandle() {
        return this.clientHandle;
    }

    public void setClientHandle(String clientHandle) {
        this.clientHandle = clientHandle;
    }

    /* loaded from: classes3.dex */
    private class CustomMqttAsyncClient extends MqttAsyncClient {
        private MqttPingReq mPingMessage;

        public CustomMqttAsyncClient(String serverURI, String clientId, MqttClientPersistence persistence, MqttPingSender pingSender) throws MqttException {
            super(serverURI, clientId, persistence, pingSender, null);
            this.mPingMessage = new MqttPingReq();
        }

        public void checkPing(IMqttActionListener listener) throws MqttException {
            if (this.comms == null || !this.comms.isConnected()) {
                return;
            }
            MqttToken mqttToken = new MqttToken(getClientId());
            mqttToken.setActionCallback(listener);
            mqttToken.setUserContext(null);
            this.comms.sendNoWait(this.mPingMessage, mqttToken);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MqttConnection(MqttService service, String serverURI, String clientId, MqttClientPersistence persistence, String clientHandle) {
        this.persistence = null;
        this.service = null;
        this.wakeLockTag = null;
        this.serverURI = serverURI;
        this.service = service;
        this.clientId = clientId;
        this.persistence = persistence;
        this.clientHandle = clientHandle;
        this.wakeLockTag = getClass().getCanonicalName() + " " + clientId + " on host " + serverURI;
    }

    public void connect(MqttConnectOptions options, String invocationContext, String activityToken) {
        this.connectOptions = options;
        this.reconnectActivityToken = activityToken;
        if (options != null) {
            this.cleanSession = options.isCleanSession();
        }
        if (this.connectOptions.isCleanSession()) {
            this.service.messageStore.clearArrivedMessages(this.clientHandle);
        }
        this.service.traceDebug(TAG, "Connecting {" + this.serverURI + "} as {" + this.clientId + "}");
        final Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.CONNECT_ACTION);
        try {
            if (this.persistence == null) {
                File dir = this.service.getDir(TAG, 0);
                if (dir == null) {
                    bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, "Error! No external and internal storage available");
                    bundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, new MqttPersistenceException());
                    this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
                    return;
                }
                this.persistence = new MqttDefaultFilePersistence(dir.getAbsolutePath());
            }
            MqttConnectionListener mqttConnectionListener = new MqttConnectionListener(bundle) { // from class: org.eclipse.paho.android.service.MqttConnection.1
                @Override // org.eclipse.paho.android.service.MqttConnection.MqttConnectionListener, org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onSuccess(IMqttToken asyncActionToken) {
                    MqttConnection.this.doAfterConnectSuccess(bundle);
                    MqttConnection.this.service.traceDebug(MqttConnection.TAG, "connect success!");
                }

                @Override // org.eclipse.paho.android.service.MqttConnection.MqttConnectionListener, org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, exception.getLocalizedMessage());
                    bundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, exception);
                    MqttConnection.this.service.traceError(MqttConnection.TAG, "connect fail, call connect to reconnect.reason:" + exception.getMessage());
                    MqttConnection.this.doAfterConnectFail(bundle);
                }
            };
            if (this.myClient != null) {
                if (this.isConnecting) {
                    this.service.traceDebug(TAG, "myClient != null and the client is connecting. Connect return directly.");
                    this.service.traceDebug(TAG, "Connect return:isConnecting:" + this.isConnecting + ".disconnected:" + this.disconnected);
                    return;
                } else if (!this.disconnected) {
                    this.service.traceDebug(TAG, "myClient != null and the client is connected and notify!");
                    doAfterConnectSuccess(bundle);
                    return;
                } else {
                    this.service.traceDebug(TAG, "myClient != null and the client is not connected");
                    this.service.traceDebug(TAG, "Do Real connect!");
                    setConnectingState(true);
                    this.myClient.connect(this.connectOptions, invocationContext, mqttConnectionListener);
                    return;
                }
            }
            this.alarmPingSender = new AlarmPingSender(this.service, this);
            CustomMqttAsyncClient customMqttAsyncClient = new CustomMqttAsyncClient(this.serverURI, this.clientId, this.persistence, this.alarmPingSender);
            this.myClient = customMqttAsyncClient;
            customMqttAsyncClient.setCallback(this);
            this.service.traceDebug(TAG, "Do Real connect!");
            setConnectingState(true);
            this.myClient.connect(this.connectOptions, invocationContext, mqttConnectionListener);
        } catch (Exception e) {
            this.service.traceError(TAG, "Exception occurred attempting to connect: " + e.getMessage());
            setConnectingState(false);
            handleException(bundle, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAfterConnectSuccess(final Bundle resultBundle) {
        acquireWakeLock();
        this.service.callbackToActivity(this.clientHandle, Status.OK, resultBundle);
        deliverBacklog();
        setConnectingState(false);
        this.disconnected = false;
        releaseWakeLock();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallbackExtended
    public void connectComplete(boolean reconnect, String serverURI) {
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.CONNECT_EXTENDED_ACTION);
        bundle.putBoolean(MqttServiceConstants.CALLBACK_RECONNECT, reconnect);
        bundle.putString(MqttServiceConstants.CALLBACK_SERVER_URI, serverURI);
        this.service.callbackToActivity(this.clientHandle, Status.OK, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAfterConnectFail(final Bundle resultBundle) {
        acquireWakeLock();
        this.disconnected = true;
        setConnectingState(false);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, resultBundle);
        releaseWakeLock();
    }

    private void handleException(final Bundle resultBundle, Exception e) {
        resultBundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, e.getLocalizedMessage());
        resultBundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, e);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, resultBundle);
    }

    private void deliverBacklog() {
        Iterator<MessageStore.StoredMessage> allArrivedMessages = this.service.messageStore.getAllArrivedMessages(this.clientHandle);
        while (allArrivedMessages.hasNext()) {
            MessageStore.StoredMessage next = allArrivedMessages.next();
            Bundle messageToBundle = messageToBundle(next.getMessageId(), next.getTopic(), next.getMessage());
            messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.MESSAGE_ARRIVED_ACTION);
            this.service.callbackToActivity(this.clientHandle, Status.OK, messageToBundle);
        }
    }

    private Bundle messageToBundle(String messageId, String topic, MqttMessage message) {
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_MESSAGE_ID, messageId);
        bundle.putString(MqttServiceConstants.CALLBACK_DESTINATION_NAME, topic);
        bundle.putParcelable(MqttServiceConstants.CALLBACK_MESSAGE_PARCEL, new ParcelableMqttMessage(message));
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void close() {
        this.service.traceDebug(TAG, "close()");
        try {
            MqttAsyncClient mqttAsyncClient = this.myClient;
            if (mqttAsyncClient != null) {
                mqttAsyncClient.close(true);
                this.myClient = null;
            }
        } catch (MqttException e) {
            handleException(new Bundle(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void disconnect(long quiesceTimeout, String invocationContext, String activityToken) {
        this.service.traceDebug(TAG, "disconnect()");
        this.disconnected = true;
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.DISCONNECT_ACTION);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                this.myClient.disconnect(quiesceTimeout, invocationContext, new MqttConnectionListener(bundle));
                this.alarmPingSender.stop();
            } catch (Exception e) {
                handleException(bundle, e);
            }
        } else {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.DISCONNECT_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
        }
        MqttConnectOptions mqttConnectOptions = this.connectOptions;
        if (mqttConnectOptions != null && mqttConnectOptions.isCleanSession()) {
            this.service.messageStore.clearArrivedMessages(this.clientHandle);
        }
        releaseWakeLock();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void disconnect(String invocationContext, String activityToken) {
        this.service.traceDebug(TAG, "disconnect()");
        this.disconnected = true;
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.DISCONNECT_ACTION);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                this.myClient.disconnect(invocationContext, new MqttConnectionListener(bundle));
                this.alarmPingSender.stop();
            } catch (Exception e) {
                handleException(bundle, e);
            }
        } else {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.DISCONNECT_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
        }
        MqttConnectOptions mqttConnectOptions = this.connectOptions;
        if (mqttConnectOptions != null && mqttConnectOptions.isCleanSession()) {
            this.service.messageStore.clearArrivedMessages(this.clientHandle);
        }
        releaseWakeLock();
    }

    public void disconnectForcibly(String invocationContext, String activityToken) {
        this.service.traceDebug(TAG, "disconnect()");
        this.disconnected = true;
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.DISCONNECT_ACTION);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            new MqttConnectionListener(bundle);
            try {
                this.myClient.disconnectForcibly(QUIESCE_TIMEOUT, 5000L, false);
                this.alarmPingSender.stop();
            } catch (Exception e) {
                handleException(bundle, e);
            }
        } else {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.DISCONNECT_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
        }
        MqttConnectOptions mqttConnectOptions = this.connectOptions;
        if (mqttConnectOptions != null && mqttConnectOptions.isCleanSession()) {
            this.service.messageStore.clearArrivedMessages(this.clientHandle);
        }
        releaseWakeLock();
    }

    public void checkPing(String activityToken) {
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SEND_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, null);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            MqttConnectionListener mqttConnectionListener = new MqttConnectionListener(bundle);
            try {
                MqttAsyncClient mqttAsyncClient2 = this.myClient;
                if (mqttAsyncClient2 instanceof CustomMqttAsyncClient) {
                    ((CustomMqttAsyncClient) mqttAsyncClient2).checkPing(mqttConnectionListener);
                    return;
                }
                return;
            } catch (Exception e) {
                handleException(bundle, e);
                return;
            }
        }
        bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
        this.service.traceError(MqttServiceConstants.SEND_ACTION, NOT_CONNECTED);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
    }

    public boolean isConnected() {
        MqttAsyncClient mqttAsyncClient = this.myClient;
        return mqttAsyncClient != null && mqttAsyncClient.isConnected();
    }

    public IMqttDeliveryToken publish(String topic, byte[] payload, int qos, boolean retained, String invocationContext, String activityToken) {
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SEND_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        IMqttDeliveryToken iMqttDeliveryToken = null;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            MqttConnectionListener mqttConnectionListener = new MqttConnectionListener(bundle);
            try {
                MqttMessage mqttMessage = new MqttMessage(payload);
                mqttMessage.setQos(qos);
                mqttMessage.setRetained(retained);
                IMqttDeliveryToken publish = this.myClient.publish(topic, payload, qos, retained, invocationContext, mqttConnectionListener);
                try {
                    storeSendDetails(topic, mqttMessage, publish, invocationContext, activityToken);
                    return publish;
                } catch (Exception e) {
                    e = e;
                    iMqttDeliveryToken = publish;
                    handleException(bundle, e);
                    return iMqttDeliveryToken;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } else {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.SEND_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
            return null;
        }
    }

    public IMqttDeliveryToken publish(String topic, MqttMessage message, String invocationContext, String activityToken) {
        DisconnectedBufferOptions disconnectedBufferOptions;
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SEND_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        IMqttDeliveryToken iMqttDeliveryToken = null;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                iMqttDeliveryToken = this.myClient.publish(topic, message, invocationContext, new MqttConnectionListener(bundle));
                storeSendDetails(topic, message, iMqttDeliveryToken, invocationContext, activityToken);
                return iMqttDeliveryToken;
            } catch (Exception e) {
                handleException(bundle, e);
                return iMqttDeliveryToken;
            }
        } else if (this.myClient != null && (disconnectedBufferOptions = this.bufferOpts) != null && disconnectedBufferOptions.isBufferEnabled()) {
            try {
                iMqttDeliveryToken = this.myClient.publish(topic, message, invocationContext, new MqttConnectionListener(bundle));
                storeSendDetails(topic, message, iMqttDeliveryToken, invocationContext, activityToken);
                return iMqttDeliveryToken;
            } catch (Exception e2) {
                handleException(bundle, e2);
                return iMqttDeliveryToken;
            }
        } else {
            Log.i(TAG, "Client is not connected, so not sending message");
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.SEND_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
            return null;
        }
    }

    public void subscribe(final String topic, final int qos, String invocationContext, String activityToken) {
        this.service.traceDebug(TAG, "subscribe({" + topic + "}," + qos + ",{" + invocationContext + "}, {" + activityToken + "}");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, "subscribe");
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                this.myClient.subscribe(topic, qos, invocationContext, new MqttConnectionListener(bundle));
                return;
            } catch (Exception e) {
                handleException(bundle, e);
                return;
            }
        }
        bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
        this.service.traceError("subscribe", NOT_CONNECTED);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
    }

    public void subscribe(final String[] topic, final int[] qos, String invocationContext, String activityToken) {
        this.service.traceDebug(TAG, "subscribe({" + Arrays.toString(topic) + "}," + Arrays.toString(qos) + ",{" + invocationContext + "}, {" + activityToken + "}");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, "subscribe");
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                this.myClient.subscribe(topic, qos, invocationContext, new MqttConnectionListener(bundle));
                return;
            } catch (Exception e) {
                handleException(bundle, e);
                return;
            }
        }
        bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
        this.service.traceError("subscribe", NOT_CONNECTED);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
    }

    public void subscribe(String[] topicFilters, int[] qos, String invocationContext, String activityToken, IMqttMessageListener[] messageListeners) {
        this.service.traceDebug(TAG, "subscribe({" + Arrays.toString(topicFilters) + "}," + Arrays.toString(qos) + ",{" + invocationContext + "}, {" + activityToken + "}");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, "subscribe");
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            new MqttConnectionListener(bundle);
            try {
                this.myClient.subscribe(topicFilters, qos, messageListeners);
                return;
            } catch (Exception e) {
                handleException(bundle, e);
                return;
            }
        }
        bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
        this.service.traceError("subscribe", NOT_CONNECTED);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void unsubscribe(final String topic, String invocationContext, String activityToken) {
        this.service.traceDebug(TAG, "unsubscribe({" + topic + "},{" + invocationContext + "}, {" + activityToken + "})");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, "unsubscribe");
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                this.myClient.unsubscribe(topic, invocationContext, new MqttConnectionListener(bundle));
                return;
            } catch (Exception e) {
                handleException(bundle, e);
                return;
            }
        }
        bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
        this.service.traceError("subscribe", NOT_CONNECTED);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void unsubscribe(final String[] topic, String invocationContext, String activityToken) {
        this.service.traceDebug(TAG, "unsubscribe({" + Arrays.toString(topic) + "},{" + invocationContext + "}, {" + activityToken + "})");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, "unsubscribe");
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, activityToken);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, invocationContext);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                this.myClient.unsubscribe(topic, invocationContext, new MqttConnectionListener(bundle));
                return;
            } catch (Exception e) {
                handleException(bundle, e);
                return;
            }
        }
        bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
        this.service.traceError("subscribe", NOT_CONNECTED);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
    }

    public IMqttDeliveryToken[] getPendingDeliveryTokens() {
        return this.myClient.getPendingDeliveryTokens();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void connectionLost(Throwable why) {
        this.service.traceDebug(TAG, "connectionLost(" + (why == null ? "" : why.getMessage()) + ")");
        this.disconnected = true;
        try {
            if (!this.connectOptions.isAutomaticReconnect()) {
                this.myClient.disconnect(null, new IMqttActionListener() { // from class: org.eclipse.paho.android.service.MqttConnection.2
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken asyncActionToken) {
                        MqttConnection.this.service.traceDebug(MqttConnection.TAG, "disconnect onSuccess");
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        MqttConnection.this.service.traceDebug(MqttConnection.TAG, "disconnect onFailure, exception:" + exception);
                    }
                });
            } else {
                this.alarmPingSender.schedule(100L);
            }
        } catch (Exception e) {
            this.service.traceDebug(TAG, "disconnect fail, exception:" + e);
        }
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.ON_CONNECTION_LOST_ACTION);
        if (why != null) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, why.getMessage());
            if (why instanceof MqttException) {
                bundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, why);
            }
            bundle.putString(MqttServiceConstants.CALLBACK_EXCEPTION_STACK, Log.getStackTraceString(why));
        }
        this.service.callbackToActivity(this.clientHandle, Status.OK, bundle);
        releaseWakeLock();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void deliveryComplete(IMqttDeliveryToken messageToken) {
        this.service.traceDebug(TAG, "deliveryComplete(" + messageToken + ")");
        MqttMessage remove = this.savedSentMessages.remove(messageToken);
        if (remove != null) {
            String remove2 = this.savedActivityTokens.remove(messageToken);
            String remove3 = this.savedInvocationContexts.remove(messageToken);
            Bundle messageToBundle = messageToBundle(null, this.savedTopics.remove(messageToken), remove);
            if (remove2 != null) {
                messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SEND_ACTION);
                messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, remove2);
                messageToBundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, remove3);
                this.service.callbackToActivity(this.clientHandle, Status.OK, messageToBundle);
            }
            messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.MESSAGE_DELIVERED_ACTION);
            this.service.callbackToActivity(this.clientHandle, Status.OK, messageToBundle);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        this.service.traceDebug(TAG, "messageArrived(" + topic + ",{" + message.toString() + "})");
        String storeArrived = this.service.messageStore.storeArrived(this.clientHandle, topic, message);
        Bundle messageToBundle = messageToBundle(storeArrived, topic, message);
        messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.MESSAGE_ARRIVED_ACTION);
        messageToBundle.putString(MqttServiceConstants.CALLBACK_MESSAGE_ID, storeArrived);
        this.service.callbackToActivity(this.clientHandle, Status.OK, messageToBundle);
    }

    private void storeSendDetails(final String topic, final MqttMessage msg, final IMqttDeliveryToken messageToken, final String invocationContext, final String activityToken) {
        this.savedTopics.put(messageToken, topic);
        this.savedSentMessages.put(messageToken, msg);
        this.savedActivityTokens.put(messageToken, activityToken);
        this.savedInvocationContexts.put(messageToken, invocationContext);
    }

    private void acquireWakeLock() {
        if (this.wakelock == null) {
            this.wakelock = ((PowerManager) this.service.getSystemService("power")).newWakeLock(1, this.wakeLockTag);
        }
        this.wakelock.acquire();
    }

    private void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.wakelock;
        if (wakeLock == null || !wakeLock.isHeld()) {
            return;
        }
        this.wakelock.release();
    }

    /* loaded from: classes3.dex */
    private class MqttConnectionListener implements IMqttActionListener {
        private final Bundle resultBundle;

        private MqttConnectionListener(Bundle resultBundle) {
            this.resultBundle = resultBundle;
        }

        @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
        public void onSuccess(IMqttToken asyncActionToken) {
            MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.OK, this.resultBundle);
        }

        @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            this.resultBundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, exception.getLocalizedMessage());
            this.resultBundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, exception);
            MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.ERROR, this.resultBundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void offline() {
        this.service.traceDebug(TAG, "start offline. disconnected:" + this.disconnected + " cleanSession:" + this.cleanSession);
        if (this.disconnected || this.cleanSession) {
            return;
        }
        connectionLost(new Exception("Android offline"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void reconnect() {
        this.service.traceDebug(TAG, "start reconnect, disconnected:" + this.disconnected + " cleanSession:" + this.cleanSession);
        if (this.myClient == null) {
            this.service.traceError(TAG, "Reconnect myClient = null. Will not do reconnect");
        } else if (this.isConnecting) {
            this.service.traceDebug(TAG, "The client is connecting. Reconnect return directly.");
        } else if (!this.service.isOnline()) {
            this.service.traceDebug(TAG, "The network is not reachable. Will not do reconnect");
        } else {
            if (this.connectOptions.isAutomaticReconnect()) {
                this.service.traceDebug(TAG, "Requesting Automatic reconnect using New Java AC");
                Bundle bundle = new Bundle();
                bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, this.reconnectActivityToken);
                bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, null);
                bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.CONNECT_ACTION);
                try {
                    this.myClient.reconnect();
                } catch (MqttException e) {
                    Log.e(TAG, "Exception occurred attempting to reconnect: " + e.getMessage());
                    setConnectingState(false);
                    handleException(bundle, e);
                }
                return;
            }
            if (this.disconnected && !this.cleanSession) {
                this.service.traceDebug(TAG, "Do Real Reconnect!");
                final Bundle bundle2 = new Bundle();
                bundle2.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, this.reconnectActivityToken);
                bundle2.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, null);
                bundle2.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.CONNECT_ACTION);
                try {
                    try {
                        this.myClient.connect(this.connectOptions, null, new MqttConnectionListener(bundle2) { // from class: org.eclipse.paho.android.service.MqttConnection.3
                            @Override // org.eclipse.paho.android.service.MqttConnection.MqttConnectionListener, org.eclipse.paho.client.mqttv3.IMqttActionListener
                            public void onSuccess(IMqttToken asyncActionToken) {
                                MqttConnection.this.service.traceDebug(MqttConnection.TAG, "Reconnect Success!");
                                MqttConnection.this.service.traceDebug(MqttConnection.TAG, "DeliverBacklog when reconnect.");
                                MqttConnection.this.doAfterConnectSuccess(bundle2);
                            }

                            @Override // org.eclipse.paho.android.service.MqttConnection.MqttConnectionListener, org.eclipse.paho.client.mqttv3.IMqttActionListener
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                bundle2.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, exception.getLocalizedMessage());
                                bundle2.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, exception);
                                MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.ERROR, bundle2);
                                MqttConnection.this.doAfterConnectFail(bundle2);
                            }
                        });
                        setConnectingState(true);
                    } catch (Exception e2) {
                        this.service.traceError(TAG, "Cannot reconnect to remote server." + e2.getMessage());
                        setConnectingState(false);
                        handleException(bundle2, new MqttException(6, e2.getCause()));
                    }
                } catch (MqttException e3) {
                    this.service.traceError(TAG, "Cannot reconnect to remote server." + e3.getMessage());
                    setConnectingState(false);
                    handleException(bundle2, e3);
                }
            }
            return;
        }
    }

    private synchronized void setConnectingState(boolean isConnecting) {
        this.isConnecting = isConnecting;
    }

    public void setBufferOpts(DisconnectedBufferOptions bufferOpts) {
        this.bufferOpts = bufferOpts;
        this.myClient.setBufferOpts(bufferOpts);
    }

    public int getBufferedMessageCount() {
        return this.myClient.getBufferedMessageCount();
    }

    public MqttMessage getBufferedMessage(int bufferIndex) {
        return this.myClient.getBufferedMessage(bufferIndex);
    }

    public void deleteBufferedMessage(int bufferIndex) {
        this.myClient.deleteBufferedMessage(bufferIndex);
    }
}
