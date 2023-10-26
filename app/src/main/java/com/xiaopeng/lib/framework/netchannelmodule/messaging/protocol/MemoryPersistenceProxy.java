package com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol;

import android.util.Pair;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.util.Enumeration;
import java.util.Hashtable;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

/* loaded from: classes2.dex */
public class MemoryPersistenceProxy implements MqttClientPersistence {
    private static final int MAX_MESSAGE_NUMBER = 200;
    private static final String TAG = "MemoryPersistenceProxy";
    protected String mClientId;
    protected Hashtable<String, Pair<MqttPersistable, Long>> mHashTable = new Hashtable<>();
    protected String mServerURI;
    protected boolean mTraceEnabled;

    private void log(String str) {
        if (BuildInfoUtils.isEngVersion() && this.mTraceEnabled) {
            LogUtils.d(TAG, str);
        }
    }

    public MemoryPersistenceProxy() {
        log("new MemoryPersistenceProxy");
    }

    private void checkOpen() throws MqttPersistenceException {
        if (this.mHashTable == null) {
            throw new MqttPersistenceException();
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void close() throws MqttPersistenceException {
        log("close()");
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public Enumeration keys() throws MqttPersistenceException {
        checkOpen();
        Enumeration<String> keys = this.mHashTable.keys();
        log("keys():" + keys + " hasMoreElements:" + keys.hasMoreElements());
        return keys;
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public MqttPersistable get(String str) throws MqttPersistenceException {
        checkOpen();
        MqttPersistable mqttPersistable = (MqttPersistable) this.mHashTable.get(str).first;
        log("get key:" + str + " result:" + mqttPersistable);
        return mqttPersistable;
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void open(String str, String str2) throws MqttPersistenceException {
        log("open clientId:" + str + " serverURI:" + str2);
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("clientId or serverURI can't be null");
        }
        if (str.equals(this.mClientId) && str2.equals(this.mServerURI)) {
            log("same config, return!");
            return;
        }
        Hashtable<String, Pair<MqttPersistable, Long>> hashtable = this.mHashTable;
        if (hashtable != null) {
            hashtable.clear();
        }
        this.mClientId = str;
        this.mServerURI = str2;
        this.mHashTable = new Hashtable<>();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void put(String str, MqttPersistable mqttPersistable) throws MqttPersistenceException {
        checkOpen();
        log("put key:" + str + " persistable:" + mqttPersistable + " size:" + this.mHashTable.size());
        if (this.mHashTable.size() >= 200) {
            this.mHashTable.clear();
            log("exceed max persist count");
        }
        this.mHashTable.put(str, new Pair<>(mqttPersistable, Long.valueOf(System.currentTimeMillis())));
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void remove(String str) throws MqttPersistenceException {
        checkOpen();
        log("remove key:" + str);
        this.mHashTable.remove(str);
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void clear() throws MqttPersistenceException {
        checkOpen();
        log("clear");
        this.mHashTable.clear();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public boolean containsKey(String str) throws MqttPersistenceException {
        checkOpen();
        boolean containsKey = this.mHashTable.containsKey(str);
        log("containsKey key:" + str + " result:" + containsKey);
        return containsKey;
    }

    public void setTraceEnabled(boolean z) {
        this.mTraceEnabled = z;
    }
}
