package com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.utils.ThreadUtils;

/* loaded from: classes2.dex */
public class SystemStatusMonitor {
    static final String TAG = "SystemStatusMonitor";
    private static final int TYPE_NONE = -1;
    private BroadcastReceiver mBroadcastReceiver;

    /* loaded from: classes2.dex */
    private static final class Holder {
        private static final SystemStatusMonitor INSTANCE = new SystemStatusMonitor();

        private Holder() {
        }
    }

    public static SystemStatusMonitor getInstance() {
        return Holder.INSTANCE;
    }

    public void registerBroadcastReceiver(Context context) {
        if (this.mBroadcastReceiver == null) {
            this.mBroadcastReceiver = new BroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            context.registerReceiver(this.mBroadcastReceiver, intentFilter);
        }
    }

    /* loaded from: classes2.dex */
    private class BroadcastReceiver extends android.content.BroadcastReceiver {
        private BroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                onNetworkChanged();
            } else if (action.equals("android.intent.action.SCREEN_ON")) {
                MqttChannel.getInstance().disconnectedDueToException(new Exception("awake from sleep"));
            }
        }

        private void onNetworkChanged() {
            ThreadUtils.postBackground(new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.SystemStatusMonitor.BroadcastReceiver.1
                private int mOldNetType = -1;

                @Override // java.lang.Runnable
                public void run() {
                    boolean z = true;
                    PowerManager.WakeLock newWakeLock = ((PowerManager) GlobalConfig.getApplicationContext().getSystemService("power")).newWakeLock(1, "MQTT");
                    newWakeLock.acquire();
                    NetworkInfo activeNetworkInfo = ((ConnectivityManager) GlobalConfig.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        int type = activeNetworkInfo.getType();
                        if (this.mOldNetType != type) {
                            if (!activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
                                z = false;
                            }
                            MqttChannel.getInstance().networkChanged(z);
                        }
                        this.mOldNetType = type;
                    } else {
                        this.mOldNetType = -1;
                        MqttChannel.getInstance().networkChanged(false);
                    }
                    newWakeLock.release();
                }
            });
        }
    }
}
