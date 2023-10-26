package com.xiaopeng.vui.commons;

import com.xiaopeng.libconfig.settings.SettingsUtil;

/* loaded from: classes2.dex */
public enum VuiFeedbackType {
    SOUND(SettingsUtil.PAGE_SOUND),
    TTS("Tts");
    
    private String type;

    VuiFeedbackType(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }
}
