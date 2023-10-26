package com.xiaopeng.xui.utils;

import android.view.View;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes2.dex */
public class XInputUtils {
    public static void ignoreHiddenInput(View view) {
        view.setTag(ClientDefaults.MAX_MSG_SIZE, 1001);
    }
}
