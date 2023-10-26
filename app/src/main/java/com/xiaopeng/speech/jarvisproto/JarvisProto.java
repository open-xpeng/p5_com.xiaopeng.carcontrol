package com.xiaopeng.speech.jarvisproto;

import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;

/* loaded from: classes2.dex */
public abstract class JarvisProto {
    public abstract String getEvent();

    public abstract String getJsonData();

    public String toString() {
        return getClass().getSimpleName() + QuickSettingConstants.JOINER + getJsonData();
    }
}
