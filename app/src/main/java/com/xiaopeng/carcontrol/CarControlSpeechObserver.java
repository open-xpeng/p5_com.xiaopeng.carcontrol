package com.xiaopeng.carcontrol;

import android.text.TextUtils;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.speech.ISpeechModel;
import com.xiaopeng.carcontrol.speech.SpeechModelFactory;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.lib.apirouter.server.IServicePublisher;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class CarControlSpeechObserver implements IServicePublisher {
    private static final String TAG = "CarControlSpeechObserver";
    private final boolean isMainProcess;
    private ISpeechModel mCarcontrolSm;
    private ISpeechModel mHvacSm;

    public CarControlSpeechObserver() {
        boolean isMainProcess = App.isMainProcess();
        this.isMainProcess = isMainProcess;
        if (isMainProcess) {
            if (!BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
                this.mHvacSm = SpeechModelFactory.getSpeechModel("hvac");
            }
            this.mCarcontrolSm = SpeechModelFactory.getSpeechModel("carcontrol");
        }
    }

    public void onEvent(final String event, final String data) {
        if (!this.isMainProcess || TextUtils.isEmpty(event)) {
            return;
        }
        LogUtils.d(TAG, "onEvent=====event: " + event + ", data: " + data, false);
        ThreadUtils.postDelayed(5, new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CarControlSpeechObserver$tNceYS3sOZ0tsuOsZKRB_1qNnco
            @Override // java.lang.Runnable
            public final void run() {
                CarControlSpeechObserver.this.lambda$onEvent$0$CarControlSpeechObserver(data, event);
            }
        });
    }

    public /* synthetic */ void lambda$onEvent$0$CarControlSpeechObserver(final String data, final String event) {
        int source = getSource(data);
        if (event.contains("ac.")) {
            if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
                return;
            }
            this.mHvacSm.onEvent(event, data, source);
            return;
        }
        this.mCarcontrolSm.onEvent(event, data, source);
    }

    public void onQuery(final String event, final String data, final String callback) {
        if (!this.isMainProcess || TextUtils.isEmpty(event)) {
            return;
        }
        LogUtils.d(TAG, "onQuery=====event: " + event + ", data: " + data + ", callback: " + callback, false);
        ThreadUtils.postDelayed(5, new Runnable() { // from class: com.xiaopeng.carcontrol.-$$Lambda$CarControlSpeechObserver$JGj_eOLLPTFXB5xu8LN3ft12EV8
            @Override // java.lang.Runnable
            public final void run() {
                CarControlSpeechObserver.this.lambda$onQuery$1$CarControlSpeechObserver(event, data, callback);
            }
        });
    }

    public /* synthetic */ void lambda$onQuery$1$CarControlSpeechObserver(final String event, final String data, final String callback) {
        if (event.contains("ac.")) {
            if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
                return;
            }
            this.mHvacSm.onQuery(event, data, callback);
            return;
        }
        this.mCarcontrolSm.onQuery(event, data, callback);
    }

    private int getSource(String data) {
        try {
            if (TextUtils.isEmpty(data)) {
                return 0;
            }
            return Integer.parseInt(new JSONObject(data).optString("source"));
        } catch (Exception e) {
            LogUtils.e(TAG, "source: " + e.getMessage());
            return 0;
        }
    }
}
