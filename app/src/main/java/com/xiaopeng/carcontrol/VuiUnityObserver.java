package com.xiaopeng.carcontrol;

import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import com.xiaopeng.speech.vui.VuiEngine;

/* loaded from: classes.dex */
public class VuiUnityObserver implements IServicePublisher {
    private static final String TAG = "VuiUnityObserver";

    public void onEvent(String event, String data) {
        LogUtils.d(TAG, "event==" + event + " data=" + data);
        VuiEngine.getInstance(App.getInstance().getApplicationContext()).dispatchVuiEvent(event, data);
    }

    public String getElementState(String sceneId, String elementId) {
        LogUtils.d(TAG, "getElementState" + sceneId + " elementId=" + elementId);
        String elementState = VuiEngine.getInstance(App.getInstance().getApplicationContext()).getElementState(sceneId, elementId);
        LogUtils.d(TAG, "result == " + elementState);
        return elementState;
    }
}
