package com.xiaopeng.lib.framework.netchannelmodule.messaging.profile;

import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.utils.SharedPreferencesUtils;
import java.util.Set;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/* loaded from: classes2.dex */
public final class TestingChannelProfile extends AbstractChannelProfile {
    private static final String DEFAULT_CLIENT_ID = "ExampleAndroidClient";
    private static final String DEFAULT_SERVER_URI = "tcp://10.192.152.118:1883";
    private static final boolean FUNCTION_PUBLISH = true;
    private static final boolean FUNCTION_SUBSCRIBE = true;
    private static final int MAX_CACHABLE_BUFFER_SIZE = 5000;
    private static final int MAX_INFLIGHT = 120;
    private static final String SYSTEM_PROPERTY_MQTT_CLIENT_ID = "persist.sys.mqtt.test.clientid";
    private static final String SYSTEM_PROPERTY_MQTT_TESTING_URL = "persist.sys.mqtt.test.url";
    private SharedPreferencesUtils mPreference;

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public boolean enableExtremePing() {
        return true;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public boolean enableTrace() {
        return true;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public Set<String> getAcceptedLogList() {
        return null;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public boolean needToCollectData() {
        return false;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public boolean sendOutAllLogs() {
        return true;
    }

    public TestingChannelProfile() {
        super(true, true);
        this.mPreference = SharedPreferencesUtils.getInstance(GlobalConfig.getApplicationContext());
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public IMessaging.CHANNEL channel() {
        return IMessaging.CHANNEL.TESTING;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public String serverUrl() {
        String str = SystemProperties.get(SYSTEM_PROPERTY_MQTT_TESTING_URL);
        if (TextUtils.isEmpty(str)) {
            String string = this.mPreference.getString(SYSTEM_PROPERTY_MQTT_TESTING_URL);
            return !TextUtils.isEmpty(string) ? string : DEFAULT_SERVER_URI;
        }
        return str;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public String clientId() {
        String str = SystemProperties.get(SYSTEM_PROPERTY_MQTT_CLIENT_ID);
        if (TextUtils.isEmpty(str)) {
            String string = this.mPreference.getString(SYSTEM_PROPERTY_MQTT_CLIENT_ID);
            if (!TextUtils.isEmpty(string)) {
                return string + System.currentTimeMillis();
            }
            return DEFAULT_CLIENT_ID + System.currentTimeMillis();
        }
        return str;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public MqttConnectOptions buildConnectOptions() {
        MqttConnectOptions buildConnectOptions = super.buildConnectOptions();
        buildConnectOptions.setMaxInflight(120);
        return buildConnectOptions;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public DisconnectedBufferOptions buildBufferOptions() {
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(5000);
        disconnectedBufferOptions.setDeleteOldestMessages(true);
        disconnectedBufferOptions.setPersistBuffer(true);
        return disconnectedBufferOptions;
    }
}
