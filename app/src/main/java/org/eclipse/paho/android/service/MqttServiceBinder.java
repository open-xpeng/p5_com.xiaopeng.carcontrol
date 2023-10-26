package org.eclipse.paho.android.service;

import android.os.Binder;

/* loaded from: classes3.dex */
class MqttServiceBinder extends Binder {
    private String activityToken;
    private MqttService mqttService;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MqttServiceBinder(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    public MqttService getService() {
        return this.mqttService;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setActivityToken(String activityToken) {
        this.activityToken = activityToken;
    }

    public String getActivityToken() {
        return this.activityToken;
    }
}
