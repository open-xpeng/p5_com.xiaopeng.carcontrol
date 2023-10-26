package com.xiaopeng.lib.framework.netchannelmodule.messaging.profile;

import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/* loaded from: classes2.dex */
public final class ReportingChannelProfile extends AbstractChannelProfile {
    private static final boolean FUNCTION_PUBLISH = true;
    private static final boolean FUNCTION_SUBSCRIBE = false;
    private static final int MAX_CACHABLE_BUFFER_SIZE = 1000;

    public ReportingChannelProfile() {
        super(true, false);
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public String serverUrl() {
        String decode = decode(SystemProperties.get(SYSTEM_PROPERTY_MQTT_REPORTING_URL));
        return TextUtils.isEmpty(decode) ? "" : resolveWithDns("ssl://" + decode);
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public String clientId() {
        return super.clientId();
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public IMessaging.CHANNEL channel() {
        return IMessaging.CHANNEL.REPORTING;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public int defaultQoSLevel() {
        return IMessaging.QOS.LEVEL_1.value();
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public MqttConnectOptions buildConnectOptions() {
        MqttConnectOptions buildConnectOptions = super.buildConnectOptions();
        buildConnectOptions.setSocketFactory(GlobalConfig.getSocketFactory());
        buildConnectOptions.setUserName(getMQTTUserName());
        buildConnectOptions.setPassword(getMQTTPassword().toCharArray());
        return buildConnectOptions;
    }

    @Override // com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile
    public DisconnectedBufferOptions buildBufferOptions() {
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(1000);
        disconnectedBufferOptions.setDeleteOldestMessages(true);
        disconnectedBufferOptions.setPersistBuffer(true);
        return disconnectedBufferOptions;
    }

    private String getMQTTUserName() {
        return decode(SystemProperties.get(SYSTEM_PROPERTY_MQTT_USER));
    }

    private String getMQTTPassword() {
        return decode(SystemProperties.get(SYSTEM_PROPERTY_MQTT_PASS));
    }
}
