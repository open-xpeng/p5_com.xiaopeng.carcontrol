package com.xiaopeng.spacecapsule.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class ScreenOnUtil {
    public static final String SCREEN_ON_ACTION = "com.xiaopeng.broadcast.ACTION_SCREEN_STATUS_CHANGE";
    private static final String TAG = "ScreenOnUtil";
    private static final String device_DriverScreen = "xp_mt_ivi";
    private static PowerManager powerManager = (PowerManager) App.getInstance().getApplicationContext().getSystemService(PowerManager.class);

    public static void setXpIcmScreenOnOrOff(boolean on) {
        LogUtils.d(TAG, "setXpIcmScreenOnOrOff " + on);
        if (on) {
            powerManager.setXpIcmScreenOn(SystemClock.uptimeMillis());
        } else {
            powerManager.setXpIcmScreenOff(SystemClock.uptimeMillis());
        }
    }

    public static void setDriverScreenOnOrOff(boolean on) {
        LogUtils.d(TAG, "setDriverScreenOnOrOff " + on);
        if (on) {
            powerManager.setXpScreenOn(device_DriverScreen, SystemClock.uptimeMillis());
        } else {
            powerManager.setXpScreenOff(device_DriverScreen, SystemClock.uptimeMillis());
        }
    }

    /* loaded from: classes2.dex */
    public static class ScreenEventBroadCastReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            LogUtils.d(ScreenOnUtil.TAG, "onReceive - intent:" + intent.getAction());
            boolean booleanExtra = intent.getBooleanExtra("status", false);
            if (ScreenOnUtil.device_DriverScreen.equals(intent.getStringExtra(GlobalConstant.ACTION.ACTION_SCREEN_STATUS_CHANGE_DEVICE_EXTRA)) && booleanExtra) {
                ScreenOnUtil.setXpIcmScreenOnOrOff(true);
            }
        }
    }
}
