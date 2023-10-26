package com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor;

/* loaded from: classes2.dex */
public interface IContextSensor {
    void getSensorValue(String str, String str2, ISensorCallback iSensorCallback);

    void subscribe(String str, ISensorListener iSensorListener);

    void unSubscribe(String str, ISensorListener iSensorListener);
}
