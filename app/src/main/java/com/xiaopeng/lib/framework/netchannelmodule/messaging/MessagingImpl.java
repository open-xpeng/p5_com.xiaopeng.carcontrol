package com.xiaopeng.lib.framework.netchannelmodule.messaging;

import android.app.Application;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.exception.MessagingExceptionImpl;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.AbstractChannelProfile;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.profile.ProfileFactory;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel;

/* loaded from: classes2.dex */
public class MessagingImpl implements IMessaging {
    public static final String TAG = "MessagingImpl";
    private AbstractChannelProfile mChannelProfile;

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging
    public void initChannelWithContext(IMessaging.CHANNEL channel, Application application) throws Exception {
        GlobalConfig.setApplicationContext(application);
        Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(application));
        this.mChannelProfile = ProfileFactory.channelProfile(channel);
        MqttChannel.getInstance().init(application, this.mChannelProfile);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging
    public boolean available() {
        if (GlobalConfig.getApplicationContext() == null) {
            return false;
        }
        return MqttChannel.getInstance().isConnected();
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging
    public void subscribe(String str) throws Exception {
        if (GlobalConfig.getApplicationContext() == null) {
            throw new MessagingExceptionImpl(17);
        }
        if (!this.mChannelProfile.canSubscribe()) {
            throw new MessagingExceptionImpl(33);
        }
        MqttChannel.getInstance().subscribe(str);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging
    public long publish(String str, String str2) throws Exception {
        if (GlobalConfig.getApplicationContext() == null) {
            throw new MessagingExceptionImpl(17);
        }
        if (!this.mChannelProfile.canPublish()) {
            throw new MessagingExceptionImpl(32);
        }
        return MqttChannel.getInstance().publishMessage(str, str2);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging
    public long publish(String str, byte[] bArr) throws Exception {
        if (GlobalConfig.getApplicationContext() == null) {
            throw new MessagingExceptionImpl(17);
        }
        if (!this.mChannelProfile.canPublish()) {
            throw new MessagingExceptionImpl(32);
        }
        return MqttChannel.getInstance().publishMessage(str, bArr);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging
    public long publish(String str, String str2, IMessaging.QOS qos) throws Exception {
        if (GlobalConfig.getApplicationContext() == null) {
            throw new MessagingExceptionImpl(17);
        }
        if (!this.mChannelProfile.canPublish()) {
            throw new MessagingExceptionImpl(32);
        }
        return MqttChannel.getInstance().publishMessage(str, str2.getBytes(), qos.value());
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.messaging.IMessaging
    public long publish(String str, byte[] bArr, IMessaging.QOS qos) throws Exception {
        if (GlobalConfig.getApplicationContext() == null) {
            throw new MessagingExceptionImpl(17);
        }
        if (!this.mChannelProfile.canPublish()) {
            throw new MessagingExceptionImpl(32);
        }
        return MqttChannel.getInstance().publishMessage(str, bArr, qos.value());
    }
}
