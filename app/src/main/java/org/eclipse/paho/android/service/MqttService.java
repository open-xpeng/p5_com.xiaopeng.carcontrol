package org.eclipse.paho.android.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/* loaded from: classes3.dex */
public class MqttService extends Service implements MqttTraceHandler {
    static final String TAG = "MqttService";
    private BackgroundDataPreferenceReceiver backgroundDataPreferenceMonitor;
    private HandlerThread mHanlerThread;
    private Handler mThreadHandler;
    MessageStore messageStore;
    private MqttServiceBinder mqttServiceBinder;
    private NetworkConnectionIntentReceiver networkConnectionMonitor;
    private String traceCallbackId;
    private boolean traceEnabled = true;
    private boolean XMART_ENABLE_NETWORK_MONITOR = false;
    private volatile boolean backgroundDataEnabled = true;
    private Map<String, MqttConnection> connections = new ConcurrentHashMap();
    private int oldNetType = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void callbackToActivity(String clientHandle, Status status, Bundle dataBundle) {
        Intent intent = new Intent(MqttServiceConstants.CALLBACK_TO_ACTIVITY);
        if (clientHandle != null) {
            intent.putExtra(MqttServiceConstants.CALLBACK_CLIENT_HANDLE, clientHandle);
        }
        intent.putExtra(MqttServiceConstants.CALLBACK_STATUS, status);
        if (dataBundle != null) {
            intent.putExtras(dataBundle);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public String getClient(String serverURI, String clientId, String contextId, MqttClientPersistence persistence) {
        String str = serverURI + QuickSettingConstants.JOINER + clientId + QuickSettingConstants.JOINER + contextId;
        if (!this.connections.containsKey(str)) {
            this.connections.put(str, new MqttConnection(this, serverURI, clientId, persistence, str));
        }
        return str;
    }

    public void connect(String clientHandle, MqttConnectOptions connectOptions, String invocationContext, String activityToken) throws MqttSecurityException, MqttException {
        MqttConnection connection = getConnection(clientHandle);
        if (connection == null) {
            return;
        }
        connection.connect(connectOptions, null, activityToken);
    }

    void reconnect() {
        traceDebug("MqttService", "Reconnect to server, client size=" + this.connections.size());
        for (MqttConnection mqttConnection : this.connections.values()) {
            traceDebug("Reconnect Client:", mqttConnection.getClientId() + '/' + mqttConnection.getServerURI());
            if (isOnline()) {
                mqttConnection.reconnect();
            }
        }
    }

    public void close(String clientHandle) {
        try {
            getConnection(clientHandle).close();
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "close: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "close: " + e2.getMessage());
        }
    }

    public void disconnect(String clientHandle, String invocationContext, String activityToken) {
        try {
            getConnection(clientHandle).disconnect(invocationContext, activityToken);
            close(clientHandle);
            this.connections.remove(clientHandle);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "disconnect: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "disconnect: " + e2.getMessage());
        }
        stopSelf();
    }

    public void disconnect(String clientHandle, long quiesceTimeout, String invocationContext, String activityToken) {
        try {
            getConnection(clientHandle).disconnect(quiesceTimeout, invocationContext, activityToken);
            close(clientHandle);
            this.connections.remove(clientHandle);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "disconnect: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "disconnect: " + e2.getMessage());
        }
        stopSelf();
    }

    public void disconnectConnection(String clientHandle, String invocationContext, String activityToken) {
        try {
            getConnection(clientHandle).disconnectForcibly(invocationContext, activityToken);
            close(clientHandle);
            this.connections.remove(clientHandle);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "disconnectConnection: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "disconnectConnection: " + e2.getMessage());
        }
        stopSelf();
    }

    public boolean isConnected(String clientHandle) {
        try {
            MqttConnection connection = getConnection(clientHandle);
            if (connection != null) {
                return connection.isConnected();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public IMqttDeliveryToken publish(String clientHandle, String topic, byte[] payload, int qos, boolean retained, String invocationContext, String activityToken) throws MqttPersistenceException, MqttException {
        try {
            return getConnection(clientHandle).publish(topic, payload, qos, retained, invocationContext, activityToken);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "publish: " + e.getMessage());
            return null;
        } catch (Exception e2) {
            Log.e("MqttService", "publish: " + e2.getMessage());
            return null;
        }
    }

    public IMqttDeliveryToken publish(String clientHandle, String topic, MqttMessage message, String invocationContext, String activityToken) throws MqttPersistenceException, MqttException {
        try {
            return getConnection(clientHandle).publish(topic, message, invocationContext, activityToken);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "publish: " + e.getMessage());
            return null;
        } catch (Exception e2) {
            Log.e("MqttService", "publish: " + e2.getMessage());
            return null;
        }
    }

    public void subscribe(String clientHandle, String topic, int qos, String invocationContext, String activityToken) {
        try {
            getConnection(clientHandle).subscribe(topic, qos, invocationContext, activityToken);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "subscribe: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "subscribe: " + e2.getMessage());
        }
    }

    public void subscribe(String clientHandle, String[] topic, int[] qos, String invocationContext, String activityToken) {
        try {
            getConnection(clientHandle).subscribe(topic, qos, invocationContext, activityToken);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "subscribe: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "subscribe: " + e2.getMessage());
        }
    }

    public void subscribe(String clientHandle, String[] topicFilters, int[] qos, String invocationContext, String activityToken, IMqttMessageListener[] messageListeners) {
        try {
            getConnection(clientHandle).subscribe(topicFilters, qos, invocationContext, activityToken, messageListeners);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "subscribe: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "subscribe: " + e2.getMessage());
        }
    }

    public void unsubscribe(String clientHandle, final String topic, String invocationContext, String activityToken) {
        try {
            getConnection(clientHandle).unsubscribe(topic, invocationContext, activityToken);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "unsubscribe: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "unsubscribe: " + e2.getMessage());
        }
    }

    public void unsubscribe(String clientHandle, final String[] topic, String invocationContext, String activityToken) {
        try {
            getConnection(clientHandle).unsubscribe(topic, invocationContext, activityToken);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "unsubscribe: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "unsubscribe: " + e2.getMessage());
        }
    }

    public IMqttDeliveryToken[] getPendingDeliveryTokens(String clientHandle) {
        try {
            return getConnection(clientHandle).getPendingDeliveryTokens();
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "getPendingDeliveryTokens: " + e.getMessage());
            return null;
        } catch (Exception e2) {
            Log.e("MqttService", "getPendingDeliveryTokens: " + e2.getMessage());
            return null;
        }
    }

    public MqttConnection getConnection(String clientHandle) {
        Map<String, MqttConnection> map = this.connections;
        if (map == null) {
            return null;
        }
        MqttConnection mqttConnection = map.get(clientHandle);
        if (mqttConnection != null) {
            return mqttConnection;
        }
        throw new IllegalArgumentException("Invalid ClientHandle");
    }

    public Status acknowledgeMessageArrival(String clientHandle, String id) {
        if (this.messageStore.discardArrived(clientHandle, id)) {
            return Status.OK;
        }
        return Status.ERROR;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mqttServiceBinder = new MqttServiceBinder(this);
        this.messageStore = new DatabaseMessageStore(this, this);
    }

    @Override // android.app.Service
    public void onDestroy() {
        traceDebug("MqttService", "onDestroy");
        for (MqttConnection mqttConnection : this.connections.values()) {
            mqttConnection.disconnect(null, null);
        }
        if (this.mqttServiceBinder != null) {
            this.mqttServiceBinder = null;
        }
        if (this.XMART_ENABLE_NETWORK_MONITOR) {
            unregisterBroadcastReceivers();
        }
        MessageStore messageStore = this.messageStore;
        if (messageStore != null) {
            messageStore.close();
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        this.mqttServiceBinder.setActivityToken(intent.getStringExtra(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN));
        return this.mqttServiceBinder;
    }

    @Override // android.app.Service
    public int onStartCommand(final Intent intent, int flags, final int startId) {
        if (this.XMART_ENABLE_NETWORK_MONITOR) {
            registerBroadcastReceivers();
            return 1;
        }
        return 1;
    }

    public void setTraceCallbackId(String traceCallbackId) {
        this.traceCallbackId = traceCallbackId;
    }

    public void setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
    }

    public boolean isTraceEnabled() {
        return this.traceEnabled;
    }

    @Override // org.eclipse.paho.android.service.MqttTraceHandler
    public void traceDebug(String tag, String message) {
        traceCallback(MqttServiceConstants.TRACE_DEBUG, tag, message);
    }

    @Override // org.eclipse.paho.android.service.MqttTraceHandler
    public void traceError(String tag, String message) {
        traceCallback(MqttServiceConstants.TRACE_ERROR, tag, message);
    }

    private void traceCallback(String severity, String tag, String message) {
        if (this.traceCallbackId == null || !this.traceEnabled) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.TRACE_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_TRACE_SEVERITY, severity);
        bundle.putString(MqttServiceConstants.CALLBACK_TRACE_TAG, tag);
        bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, message);
        callbackToActivity(this.traceCallbackId, Status.ERROR, bundle);
    }

    @Override // org.eclipse.paho.android.service.MqttTraceHandler
    public void traceException(String tag, String message, Exception e) {
        if (this.traceCallbackId != null) {
            Bundle bundle = new Bundle();
            bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.TRACE_ACTION);
            bundle.putString(MqttServiceConstants.CALLBACK_TRACE_SEVERITY, "exception");
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, message);
            bundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, e);
            bundle.putString(MqttServiceConstants.CALLBACK_TRACE_TAG, tag);
            callbackToActivity(this.traceCallbackId, Status.ERROR, bundle);
        }
    }

    private void registerBroadcastReceivers() {
        if (this.networkConnectionMonitor == null) {
            NetworkConnectionIntentReceiver networkConnectionIntentReceiver = new NetworkConnectionIntentReceiver();
            this.networkConnectionMonitor = networkConnectionIntentReceiver;
            registerReceiver(networkConnectionIntentReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
        if (Build.VERSION.SDK_INT < 14) {
            this.backgroundDataEnabled = ((ConnectivityManager) getSystemService("connectivity")).getBackgroundDataSetting();
            if (this.backgroundDataPreferenceMonitor == null) {
                BackgroundDataPreferenceReceiver backgroundDataPreferenceReceiver = new BackgroundDataPreferenceReceiver();
                this.backgroundDataPreferenceMonitor = backgroundDataPreferenceReceiver;
                registerReceiver(backgroundDataPreferenceReceiver, new IntentFilter("android.net.conn.BACKGROUND_DATA_SETTING_CHANGED"));
            }
        }
    }

    private void unregisterBroadcastReceivers() {
        BackgroundDataPreferenceReceiver backgroundDataPreferenceReceiver;
        NetworkConnectionIntentReceiver networkConnectionIntentReceiver = this.networkConnectionMonitor;
        if (networkConnectionIntentReceiver != null) {
            unregisterReceiver(networkConnectionIntentReceiver);
            this.networkConnectionMonitor = null;
        }
        if (Build.VERSION.SDK_INT >= 14 || (backgroundDataPreferenceReceiver = this.backgroundDataPreferenceMonitor) == null) {
            return;
        }
        unregisterReceiver(backgroundDataPreferenceReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class NetworkConnectionIntentReceiver extends BroadcastReceiver {
        private NetworkConnectionIntentReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(final Context context, Intent intent) {
            MqttService.this.traceDebug("MqttService", "Internal network status receive.");
            if (MqttService.this.mHanlerThread == null) {
                MqttService.this.mHanlerThread = new HandlerThread("handlerThread", 10);
                MqttService.this.mHanlerThread.start();
            }
            if (MqttService.this.mThreadHandler == null) {
                MqttService.this.mThreadHandler = new Handler(MqttService.this.mHanlerThread.getLooper());
            }
            MqttService.this.mThreadHandler.post(new Runnable() { // from class: org.eclipse.paho.android.service.MqttService.NetworkConnectionIntentReceiver.1
                @Override // java.lang.Runnable
                public void run() {
                    PowerManager.WakeLock newWakeLock = ((PowerManager) MqttService.this.getSystemService("power")).newWakeLock(1, "MQTT");
                    newWakeLock.acquire();
                    MqttService.this.traceDebug("MqttService", "Reconnect for Network recovery.");
                    if (!MqttService.this.isOnline()) {
                        MqttService.this.oldNetType = -1;
                        MqttService.this.notifyClientsOffline();
                    } else {
                        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                        if (activeNetworkInfo != null) {
                            int type = activeNetworkInfo.getType();
                            if (MqttService.this.oldNetType != type && MqttService.this.oldNetType != 0) {
                                MqttService.this.traceDebug("MqttService", "Online,reconnect.");
                                new Timer().schedule(new TimerTask() { // from class: org.eclipse.paho.android.service.MqttService.NetworkConnectionIntentReceiver.1.1
                                    @Override // java.util.TimerTask, java.lang.Runnable
                                    public void run() {
                                        MqttService.this.reconnect();
                                    }
                                }, 1000L);
                            }
                            MqttService.this.oldNetType = type;
                        }
                    }
                    newWakeLock.release();
                }
            });
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected() && this.backgroundDataEnabled;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyClientsOffline() {
        for (MqttConnection mqttConnection : this.connections.values()) {
            mqttConnection.offline();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class BackgroundDataPreferenceReceiver extends BroadcastReceiver {
        private BackgroundDataPreferenceReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            MqttService.this.traceDebug("MqttService", "Reconnect since BroadcastReceiver.");
            if (((ConnectivityManager) MqttService.this.getSystemService("connectivity")).getBackgroundDataSetting()) {
                if (MqttService.this.backgroundDataEnabled) {
                    return;
                }
                MqttService.this.backgroundDataEnabled = true;
                MqttService.this.reconnect();
                return;
            }
            MqttService.this.backgroundDataEnabled = false;
            MqttService.this.notifyClientsOffline();
        }
    }

    public void setBufferOpts(String clientHandle, DisconnectedBufferOptions bufferOpts) {
        try {
            getConnection(clientHandle).setBufferOpts(bufferOpts);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "setBufferOpts: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "setBufferOpts: " + e2.getMessage());
        }
    }

    public int getBufferedMessageCount(String clientHandle) {
        try {
            return getConnection(clientHandle).getBufferedMessageCount();
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "getBufferedMessageCount: " + e.getMessage());
            return 0;
        } catch (Exception e2) {
            Log.e("MqttService", "getBufferedMessageCount: " + e2.getMessage());
            return 0;
        }
    }

    public MqttMessage getBufferedMessage(String clientHandle, int bufferIndex) {
        try {
            return getConnection(clientHandle).getBufferedMessage(bufferIndex);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "getBufferedMessage: " + e.getMessage());
            return null;
        } catch (Exception e2) {
            Log.e("MqttService", "getBufferedMessage: " + e2.getMessage());
            return null;
        }
    }

    public void deleteBufferedMessage(String clientHandle, int bufferIndex) {
        try {
            getConnection(clientHandle).deleteBufferedMessage(bufferIndex);
        } catch (IllegalArgumentException e) {
            Log.w("MqttService", "deleteBufferedMessage: " + e.getMessage());
        } catch (Exception e2) {
            Log.e("MqttService", "deleteBufferedMessage: " + e2.getMessage());
        }
    }

    public void removeConnection(MqttConnection connection) {
        traceDebug("MqttService", "remove connection:" + connection);
        if (connection == null) {
            traceDebug("MqttService", "connection is null, return!");
            return;
        }
        for (Map.Entry<String, MqttConnection> entry : this.connections.entrySet()) {
            if (connection.equals(entry.getValue())) {
                traceDebug("MqttService", "connection's key is '" + entry.getKey() + "', disconnect, close and remove it");
                connection.disconnect(null, null);
                connection.close();
                this.connections.remove(entry.getKey());
                return;
            }
        }
    }

    public void checkPing(String clientHandle, String activityToken) {
        MqttConnection mqttConnection;
        try {
            mqttConnection = getConnection(clientHandle);
        } catch (Exception unused) {
            mqttConnection = null;
        }
        if (mqttConnection != null) {
            mqttConnection.checkPing(activityToken);
        }
    }
}
