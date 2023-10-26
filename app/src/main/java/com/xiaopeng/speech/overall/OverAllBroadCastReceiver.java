package com.xiaopeng.speech.overall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.speech.vui.utils.LogUtils;

/* loaded from: classes2.dex */
public class OverAllBroadCastReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.i("OverAllBroadCastReceiver", "onReceive:" + action);
        if ("carspeechservice.SpeechServer.Start".contains(action)) {
            OverallManager.instance().subscribe();
        }
    }
}
