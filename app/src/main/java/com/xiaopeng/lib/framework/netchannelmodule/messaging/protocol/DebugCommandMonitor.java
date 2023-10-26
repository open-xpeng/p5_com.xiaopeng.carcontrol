package com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.utils.LogUtils;

/* loaded from: classes2.dex */
public class DebugCommandMonitor {
    private static final String ACTION_BROADCAST_DEBUG_CLOSE_MQTT = "com.xiaopeng.broadcast.ACTION_DEBUG_CLOSE_MQTT";
    private static final String ACTION_BROADCAST_DEBUG_OPEN_MQTT = "com.xiaopeng.broadcast.ACTION_DEBUG_OPEN_MQTT";
    private static final String TAG = "SystemStatusMonitor";
    private CommandReceiver mReceiver;

    /* loaded from: classes2.dex */
    private static final class Holder {
        private static final DebugCommandMonitor INSTANCE = new DebugCommandMonitor();

        private Holder() {
        }
    }

    public static DebugCommandMonitor getInstance() {
        return Holder.INSTANCE;
    }

    public void registerBroadcastReceiver() {
        if (this.mReceiver == null) {
            this.mReceiver = new CommandReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_BROADCAST_DEBUG_OPEN_MQTT);
            intentFilter.addAction(ACTION_BROADCAST_DEBUG_CLOSE_MQTT);
            GlobalConfig.getApplicationContext().registerReceiver(this.mReceiver, intentFilter);
        }
    }

    /* loaded from: classes2.dex */
    private class CommandReceiver extends BroadcastReceiver {
        private CommandReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.d(DebugCommandMonitor.TAG, "onReceive action:" + action);
            if (TextUtils.isEmpty(action)) {
                return;
            }
            action.hashCode();
            if (action.equals(DebugCommandMonitor.ACTION_BROADCAST_DEBUG_CLOSE_MQTT)) {
                MqttChannel.getInstance().requestToDisconnect();
            } else if (action.equals(DebugCommandMonitor.ACTION_BROADCAST_DEBUG_OPEN_MQTT)) {
                MqttChannel.getInstance().requestToConnect();
            }
        }
    }
}
