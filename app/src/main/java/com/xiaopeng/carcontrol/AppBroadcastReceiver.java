package com.xiaopeng.carcontrol;

import android.content.Context;
import android.content.Intent;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes.dex */
public class AppBroadcastReceiver extends BaseBroadcastReceiver {
    private static final String TAG = "AppBroadcastReceiver";

    private int getBossKeyBIResult(int keyFunc) {
        return keyFunc == 3 ? 3 : 4;
    }

    @Override // com.xiaopeng.carcontrol.BaseBroadcastReceiver, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        String action = intent.getAction();
        LogUtils.d(TAG, "onReceive : " + action, false);
        action.hashCode();
        if (action.equals(GlobalConstant.ACTION.ACTION_BOSSKEY_USER)) {
            Intent intent2 = new Intent(context, CarControlService.class);
            intent2.setAction(GlobalConstant.ACTION.ACTION_SHOW_CUSTOM_DOOR_KEY_PANEL);
            App.getInstance().startService(intent2);
        } else if (action.equals(GlobalConstant.ACTION.ACTION_BOSSKEY)) {
            String stringExtra = intent.getStringExtra(GlobalConstant.EXTRA.KEY_KEY_TYPE);
            int intExtra = intent.getIntExtra(GlobalConstant.EXTRA.KEY_KEY_FUNC, 0);
            LogUtils.d(TAG, "keyType: " + stringExtra + ", keyFunc: " + intExtra);
            if (stringExtra.equals(GlobalConstant.EXTRA.VALUE_X_SHORT) && intExtra == 0) {
                NotificationHelper.getInstance().showToast(R.string.custom_door_key_no_setting);
            }
        } else {
            super.onReceive(context, intent);
        }
    }
}
