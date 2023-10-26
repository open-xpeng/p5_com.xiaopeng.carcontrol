package com.xiaopeng.speech.apirouter;

import android.util.Log;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.speech.UnitySpeechEngine;

/* loaded from: classes2.dex */
public class ApiRouterUnitySceneService implements IServicePublisher {
    public void onEvent(String str, String str2) {
        Log.i("ApiRouterUnityService", "消息接收 event== " + str + ",data:" + str2);
        UnitySpeechEngine.dispatchVuiEvent(str, str2);
    }

    public String getElementState(String str, String str2) {
        return UnitySpeechEngine.getElementState(str, str2);
    }

    public void onVuiQuery(String str, String str2) {
        Log.i("ApiRouterUnityService", "消息接收 event== " + str + ",data:" + str2);
        UnitySpeechEngine.onVuiQuery(str, str2);
    }
}
