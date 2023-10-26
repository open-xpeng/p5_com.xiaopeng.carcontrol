package org.eclipse.paho.android.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.SparseArray;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/* loaded from: classes3.dex */
public class MqttAndroidClient extends BroadcastReceiver implements IMqttAsyncClient {
    private static final int BIND_SERVICE_FLAG = 0;
    private static final String SERVICE_NAME = "org.eclipse.paho.android.service.MqttService";
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private volatile boolean bindedService;
    private MqttCallback callback;
    private String clientHandle;
    private final String clientId;
    private MqttConnectOptions connectOptions;
    private IMqttToken connectToken;
    private final Ack messageAck;
    private MqttService mqttService;
    private Context myContext;
    private MqttClientPersistence persistence;
    private volatile boolean receiverRegistered;
    private final String serverURI;
    private final MyServiceConnection serviceConnection;
    private final SparseArray<IMqttToken> tokenMap;
    private int tokenNumber;
    private MqttTraceHandler traceCallback;
    private boolean traceEnabled;

    /* loaded from: classes3.dex */
    public enum Ack {
        AUTO_ACK,
        MANUAL_ACK
    }

    public boolean isServiceConnected() {
        return this.bindedService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public final class MyServiceConnection implements ServiceConnection {
        private MyServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder binder) {
            MqttAndroidClient.this.mqttService = ((MqttServiceBinder) binder).getService();
            MqttAndroidClient.this.bindedService = true;
            MqttAndroidClient.pool.execute(new Runnable() { // from class: org.eclipse.paho.android.service.MqttAndroidClient.MyServiceConnection.1
                @Override // java.lang.Runnable
                public void run() {
                    MqttAndroidClient.this.doConnect();
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            MqttAndroidClient.this.mqttService = null;
        }
    }

    public MqttAndroidClient(Context context, String serverURI, String clientId) {
        this(context, serverURI, clientId, null, Ack.AUTO_ACK);
    }

    public MqttAndroidClient(Context ctx, String serverURI, String clientId, Ack ackType) {
        this(ctx, serverURI, clientId, null, ackType);
    }

    public MqttAndroidClient(Context ctx, String serverURI, String clientId, MqttClientPersistence persistence) {
        this(ctx, serverURI, clientId, persistence, Ack.AUTO_ACK);
    }

    public MqttAndroidClient(Context context, String serverURI, String clientId, MqttClientPersistence persistence, Ack ackType) {
        this.serviceConnection = new MyServiceConnection();
        this.tokenMap = new SparseArray<>();
        this.tokenNumber = 0;
        this.persistence = null;
        this.traceEnabled = false;
        this.receiverRegistered = false;
        this.bindedService = false;
        this.myContext = context;
        this.serverURI = serverURI;
        this.clientId = clientId;
        this.persistence = persistence;
        this.messageAck = ackType;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public boolean isConnected() {
        MqttService mqttService;
        String str = this.clientHandle;
        if (str != null && (mqttService = this.mqttService) != null) {
            try {
                return mqttService.isConnected(str);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public String getClientId() {
        return this.clientId;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public String getServerURI() {
        return this.serverURI;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void close() {
        MqttService mqttService = this.mqttService;
        if (mqttService != null) {
            if (this.clientHandle == null) {
                this.clientHandle = mqttService.getClient(this.serverURI, this.clientId, this.myContext.getApplicationInfo().packageName, this.persistence);
            }
            this.mqttService.close(this.clientHandle);
            this.clientHandle = null;
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken connect() throws MqttException {
        return connect(null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken connect(MqttConnectOptions options) throws MqttException {
        return connect(options, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken connect(Object userContext, IMqttActionListener callback) throws MqttException {
        return connect(new MqttConnectOptions(), userContext, callback);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken connect(MqttConnectOptions options, Object userContext, IMqttActionListener callback) throws MqttException {
        IMqttActionListener actionCallback;
        IMqttToken mqttTokenAndroid = new MqttTokenAndroid(this, userContext, callback);
        this.connectOptions = options;
        this.connectToken = mqttTokenAndroid;
        if (this.mqttService == null) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this.myContext.getPackageName(), SERVICE_NAME));
            if (this.myContext.startServiceAsUser(intent, UserHandle.CURRENT_OR_SELF) == null && (actionCallback = mqttTokenAndroid.getActionCallback()) != null) {
                actionCallback.onFailure(mqttTokenAndroid, new RuntimeException("cannot start service org.eclipse.paho.android.service.MqttService"));
            }
            this.myContext.bindService(intent, this.serviceConnection, 1);
            if (!this.receiverRegistered) {
                registerReceiver(this);
            }
        } else {
            pool.execute(new Runnable() { // from class: org.eclipse.paho.android.service.MqttAndroidClient.1
                @Override // java.lang.Runnable
                public void run() {
                    MqttAndroidClient.this.doConnect();
                    if (MqttAndroidClient.this.receiverRegistered) {
                        return;
                    }
                    MqttAndroidClient mqttAndroidClient = MqttAndroidClient.this;
                    mqttAndroidClient.registerReceiver(mqttAndroidClient);
                }
            });
        }
        return mqttTokenAndroid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerReceiver(BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MqttServiceConstants.CALLBACK_TO_ACTIVITY);
        LocalBroadcastManager.getInstance(this.myContext).registerReceiver(receiver, intentFilter);
        this.receiverRegistered = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void doConnect() {
        String str = this.clientHandle;
        if (str != null) {
            try {
                this.mqttService.getConnection(str);
            } catch (Exception unused) {
                this.clientHandle = null;
            }
        }
        if (this.clientHandle == null) {
            this.clientHandle = this.mqttService.getClient(this.serverURI, this.clientId, this.myContext.getApplicationInfo().packageName, this.persistence);
        }
        this.mqttService.setTraceEnabled(this.traceEnabled);
        this.mqttService.setTraceCallbackId(this.clientHandle);
        this.mqttService.connect(this.clientHandle, this.connectOptions, null, storeToken(this.connectToken));
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken disconnect() throws MqttException {
        MqttTokenAndroid mqttTokenAndroid = new MqttTokenAndroid(this, null, null);
        this.mqttService.disconnect(this.clientHandle, null, storeToken(mqttTokenAndroid));
        return mqttTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken disconnect(long quiesceTimeout) throws MqttException {
        MqttTokenAndroid mqttTokenAndroid = new MqttTokenAndroid(this, null, null);
        this.mqttService.disconnect(this.clientHandle, quiesceTimeout, null, storeToken(mqttTokenAndroid));
        return mqttTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken disconnect(Object userContext, IMqttActionListener callback) throws MqttException {
        MqttTokenAndroid mqttTokenAndroid = new MqttTokenAndroid(this, userContext, callback);
        this.mqttService.disconnect(this.clientHandle, null, storeToken(mqttTokenAndroid));
        return mqttTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken disconnect(long quiesceTimeout, Object userContext, IMqttActionListener callback) throws MqttException {
        MqttTokenAndroid mqttTokenAndroid = new MqttTokenAndroid(this, userContext, callback);
        this.mqttService.disconnect(this.clientHandle, quiesceTimeout, null, storeToken(mqttTokenAndroid));
        return mqttTokenAndroid;
    }

    public IMqttToken disconnectConnection(IMqttActionListener callback) throws MqttException {
        MqttTokenAndroid mqttTokenAndroid = new MqttTokenAndroid(this, null, callback);
        this.mqttService.disconnectConnection(this.clientHandle, null, storeToken(mqttTokenAndroid));
        return mqttTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken publish(String topic, byte[] payload, int qos, boolean retained) throws MqttException, MqttPersistenceException {
        return publish(topic, payload, qos, retained, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken publish(String topic, MqttMessage message) throws MqttException, MqttPersistenceException {
        return publish(topic, message, (Object) null, (IMqttActionListener) null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken publish(String topic, byte[] payload, int qos, boolean retained, Object userContext, IMqttActionListener callback) throws MqttException, MqttPersistenceException {
        MqttMessage mqttMessage = new MqttMessage(payload);
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);
        MqttDeliveryTokenAndroid mqttDeliveryTokenAndroid = new MqttDeliveryTokenAndroid(this, userContext, callback, mqttMessage);
        mqttDeliveryTokenAndroid.setDelegate(this.mqttService.publish(this.clientHandle, topic, payload, qos, retained, null, storeToken(mqttDeliveryTokenAndroid)));
        return mqttDeliveryTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken publish(String topic, MqttMessage message, Object userContext, IMqttActionListener callback) throws MqttException, MqttPersistenceException {
        MqttDeliveryTokenAndroid mqttDeliveryTokenAndroid = new MqttDeliveryTokenAndroid(this, userContext, callback, message);
        mqttDeliveryTokenAndroid.setDelegate(this.mqttService.publish(this.clientHandle, topic, message, null, storeToken(mqttDeliveryTokenAndroid)));
        return mqttDeliveryTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String topic, int qos) throws MqttException, MqttSecurityException {
        return subscribe(topic, qos, (Object) null, (IMqttActionListener) null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String[] topic, int[] qos) throws MqttException, MqttSecurityException {
        return subscribe(topic, qos, (Object) null, (IMqttActionListener) null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String topic, int qos, Object userContext, IMqttActionListener callback) throws MqttException {
        MqttTokenAndroid mqttTokenAndroid = new MqttTokenAndroid(this, userContext, callback, new String[]{topic});
        this.mqttService.subscribe(this.clientHandle, topic, qos, (String) null, storeToken(mqttTokenAndroid));
        return mqttTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String[] topic, int[] qos, Object userContext, IMqttActionListener callback) throws MqttException {
        MqttTokenAndroid mqttTokenAndroid = new MqttTokenAndroid(this, userContext, callback, topic);
        this.mqttService.subscribe(this.clientHandle, topic, qos, (String) null, storeToken(mqttTokenAndroid));
        return mqttTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String topicFilter, int qos, Object userContext, IMqttActionListener callback, IMqttMessageListener messageListener) throws MqttException {
        return subscribe(new String[]{topicFilter}, new int[]{qos}, userContext, callback, new IMqttMessageListener[]{messageListener});
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String topicFilter, int qos, IMqttMessageListener messageListener) throws MqttException {
        return subscribe(topicFilter, qos, (Object) null, (IMqttActionListener) null, messageListener);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String[] topicFilters, int[] qos, IMqttMessageListener[] messageListeners) throws MqttException {
        return subscribe(topicFilters, qos, (Object) null, (IMqttActionListener) null, messageListeners);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken subscribe(String[] topicFilters, int[] qos, Object userContext, IMqttActionListener callback, IMqttMessageListener[] messageListeners) throws MqttException {
        this.mqttService.subscribe(this.clientHandle, topicFilters, qos, null, storeToken(new MqttTokenAndroid(this, userContext, callback, topicFilters)), messageListeners);
        return null;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken unsubscribe(String topic) throws MqttException {
        return unsubscribe(topic, (Object) null, (IMqttActionListener) null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken unsubscribe(String[] topic) throws MqttException {
        return unsubscribe(topic, (Object) null, (IMqttActionListener) null);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken unsubscribe(String topic, Object userContext, IMqttActionListener callback) throws MqttException {
        MqttTokenAndroid mqttTokenAndroid = new MqttTokenAndroid(this, userContext, callback);
        this.mqttService.unsubscribe(this.clientHandle, topic, (String) null, storeToken(mqttTokenAndroid));
        return mqttTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttToken unsubscribe(String[] topic, Object userContext, IMqttActionListener callback) throws MqttException {
        MqttTokenAndroid mqttTokenAndroid = new MqttTokenAndroid(this, userContext, callback);
        this.mqttService.unsubscribe(this.clientHandle, topic, (String) null, storeToken(mqttTokenAndroid));
        return mqttTokenAndroid;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public IMqttDeliveryToken[] getPendingDeliveryTokens() {
        return this.mqttService.getPendingDeliveryTokens(this.clientHandle);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void setCallback(MqttCallback callback) {
        this.callback = callback;
    }

    public void setTraceCallback(MqttTraceHandler traceCallback) {
        this.traceCallback = traceCallback;
    }

    public void setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
        MqttService mqttService = this.mqttService;
        if (mqttService != null) {
            mqttService.setTraceEnabled(traceEnabled);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String string = extras.getString(MqttServiceConstants.CALLBACK_CLIENT_HANDLE);
        if (string == null || !string.equals(this.clientHandle)) {
            return;
        }
        String string2 = extras.getString(MqttServiceConstants.CALLBACK_ACTION);
        if (MqttServiceConstants.CONNECT_ACTION.equals(string2)) {
            connectAction(extras);
        } else if (MqttServiceConstants.CONNECT_EXTENDED_ACTION.equals(string2)) {
            connectExtendedAction(extras);
        } else if (MqttServiceConstants.MESSAGE_ARRIVED_ACTION.equals(string2)) {
            messageArrivedAction(extras);
        } else if ("subscribe".equals(string2)) {
            subscribeAction(extras);
        } else if ("unsubscribe".equals(string2)) {
            unSubscribeAction(extras);
        } else if (MqttServiceConstants.SEND_ACTION.equals(string2)) {
            sendAction(extras);
        } else if (MqttServiceConstants.MESSAGE_DELIVERED_ACTION.equals(string2)) {
            messageDeliveredAction(extras);
        } else if (MqttServiceConstants.ON_CONNECTION_LOST_ACTION.equals(string2)) {
            connectionLostAction(extras);
        } else if (MqttServiceConstants.DISCONNECT_ACTION.equals(string2)) {
            disconnected(extras);
        } else if (MqttServiceConstants.TRACE_ACTION.equals(string2)) {
            traceAction(extras);
        } else {
            this.mqttService.traceError(MqttServiceConstants.WAKELOCK_NETWORK_INTENT, "Callback action doesn't exist.");
        }
    }

    public boolean acknowledgeMessage(String messageId) {
        return this.messageAck == Ack.MANUAL_ACK && this.mqttService.acknowledgeMessageArrival(this.clientHandle, messageId) == Status.OK;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void messageArrivedComplete(int messageId, int qos) throws MqttException {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void setManualAcks(boolean manualAcks) {
        throw new UnsupportedOperationException();
    }

    private void connectAction(Bundle data) {
        IMqttToken iMqttToken = this.connectToken;
        removeMqttToken(data);
        simpleAction(iMqttToken, data);
    }

    private void disconnected(Bundle data) {
        this.clientHandle = null;
        IMqttToken removeMqttToken = removeMqttToken(data);
        if (removeMqttToken != null) {
            ((MqttTokenAndroid) removeMqttToken).notifyComplete();
        }
        MqttCallback mqttCallback = this.callback;
        if (mqttCallback != null) {
            mqttCallback.connectionLost(null);
        }
    }

    private void connectionLostAction(Bundle data) {
        if (this.callback != null) {
            this.callback.connectionLost((Exception) data.getSerializable(MqttServiceConstants.CALLBACK_EXCEPTION));
        }
    }

    private void connectExtendedAction(Bundle data) {
        if (this.callback instanceof MqttCallbackExtended) {
            ((MqttCallbackExtended) this.callback).connectComplete(data.getBoolean(MqttServiceConstants.CALLBACK_RECONNECT, false), data.getString(MqttServiceConstants.CALLBACK_SERVER_URI));
        }
    }

    private void simpleAction(IMqttToken token, Bundle data) {
        if (token != null) {
            if (((Status) data.getSerializable(MqttServiceConstants.CALLBACK_STATUS)) == Status.OK) {
                ((MqttTokenAndroid) token).notifyComplete();
                return;
            }
            ((MqttTokenAndroid) token).notifyFailure((Exception) data.getSerializable(MqttServiceConstants.CALLBACK_EXCEPTION));
            return;
        }
        this.mqttService.traceError(MqttServiceConstants.WAKELOCK_NETWORK_INTENT, "simpleAction : token is null");
    }

    private void sendAction(Bundle data) {
        simpleAction(getMqttToken(data), data);
    }

    private void subscribeAction(Bundle data) {
        simpleAction(removeMqttToken(data), data);
    }

    private void unSubscribeAction(Bundle data) {
        simpleAction(removeMqttToken(data), data);
    }

    private void messageDeliveredAction(Bundle data) {
        IMqttToken removeMqttToken = removeMqttToken(data);
        if (removeMqttToken == null || this.callback == null || ((Status) data.getSerializable(MqttServiceConstants.CALLBACK_STATUS)) != Status.OK || !(removeMqttToken instanceof IMqttDeliveryToken)) {
            return;
        }
        this.callback.deliveryComplete((IMqttDeliveryToken) removeMqttToken);
    }

    private void messageArrivedAction(Bundle data) {
        if (this.callback != null) {
            String string = data.getString(MqttServiceConstants.CALLBACK_MESSAGE_ID);
            String string2 = data.getString(MqttServiceConstants.CALLBACK_DESTINATION_NAME);
            ParcelableMqttMessage parcelableMqttMessage = (ParcelableMqttMessage) data.getParcelable(MqttServiceConstants.CALLBACK_MESSAGE_PARCEL);
            try {
                if (this.messageAck == Ack.AUTO_ACK) {
                    this.callback.messageArrived(string2, parcelableMqttMessage);
                    this.mqttService.acknowledgeMessageArrival(this.clientHandle, string);
                } else {
                    parcelableMqttMessage.messageId = string;
                    this.callback.messageArrived(string2, parcelableMqttMessage);
                }
            } catch (Exception unused) {
            }
        }
    }

    private void traceAction(Bundle data) {
        if (this.traceCallback != null) {
            String string = data.getString(MqttServiceConstants.CALLBACK_TRACE_SEVERITY);
            String string2 = data.getString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE);
            String string3 = data.getString(MqttServiceConstants.CALLBACK_TRACE_TAG);
            if (MqttServiceConstants.TRACE_DEBUG.equals(string)) {
                this.traceCallback.traceDebug(string3, string2);
            } else if (MqttServiceConstants.TRACE_ERROR.equals(string)) {
                this.traceCallback.traceError(string3, string2);
            } else {
                this.traceCallback.traceException(string3, string2, (Exception) data.getSerializable(MqttServiceConstants.CALLBACK_EXCEPTION));
            }
        }
    }

    private synchronized String storeToken(IMqttToken token) {
        int i;
        this.tokenMap.put(this.tokenNumber, token);
        i = this.tokenNumber;
        this.tokenNumber = i + 1;
        return Integer.toString(i);
    }

    private synchronized IMqttToken removeMqttToken(Bundle data) {
        String string = data.getString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN);
        if (string != null) {
            int parseInt = Integer.parseInt(string);
            IMqttToken iMqttToken = this.tokenMap.get(parseInt);
            this.tokenMap.delete(parseInt);
            return iMqttToken;
        }
        return null;
    }

    private synchronized IMqttToken getMqttToken(Bundle data) {
        return this.tokenMap.get(Integer.parseInt(data.getString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN)));
    }

    public void setBufferOpts(DisconnectedBufferOptions bufferOpts) {
        this.mqttService.setBufferOpts(this.clientHandle, bufferOpts);
    }

    public int getBufferedMessageCount() {
        return this.mqttService.getBufferedMessageCount(this.clientHandle);
    }

    public MqttMessage getBufferedMessage(int bufferIndex) {
        return this.mqttService.getBufferedMessage(this.clientHandle, bufferIndex);
    }

    public void deleteBufferedMessage(int bufferIndex) {
        this.mqttService.deleteBufferedMessage(this.clientHandle, bufferIndex);
    }

    public SSLSocketFactory getSSLSocketFactory(InputStream keyStore, String password) throws MqttSecurityException {
        try {
            KeyStore keyStore2 = KeyStore.getInstance("BKS");
            keyStore2.load(keyStore, password.toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            trustManagerFactory.init(keyStore2);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            SSLContext sSLContext = SSLContext.getInstance("TLSv1");
            sSLContext.init(null, trustManagers, null);
            return sSLContext.getSocketFactory();
        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new MqttSecurityException(e);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void disconnectForcibly() throws MqttException {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void disconnectForcibly(long disconnectTimeout) throws MqttException {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public void disconnectForcibly(long quiesceTimeout, long disconnectTimeout) throws MqttException {
        throw new UnsupportedOperationException();
    }

    public synchronized void unregisterResources() {
        if (this.myContext != null && this.receiverRegistered) {
            synchronized (this) {
                LocalBroadcastManager.getInstance(this.myContext).unregisterReceiver(this);
                this.receiverRegistered = false;
                if (this.bindedService) {
                    try {
                        this.myContext.unbindService(this.serviceConnection);
                        this.bindedService = false;
                    } catch (IllegalArgumentException unused) {
                    }
                }
            }
        }
    }

    public synchronized void registerResources(Context context) {
        if (context != null) {
            this.myContext = context;
            if (!this.receiverRegistered) {
                registerReceiver(this);
            }
        }
    }

    public void checkPing(IMqttActionListener callback) throws MqttException, MqttPersistenceException {
        this.mqttService.checkPing(this.clientHandle, storeToken(new MqttDeliveryTokenAndroid(this, null, callback, null)));
    }
}
