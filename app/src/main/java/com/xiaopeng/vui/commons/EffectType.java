package com.xiaopeng.vui.commons;

/* loaded from: classes2.dex */
public enum EffectType {
    WATERRIPPLE("waterRipple");
    
    String effect;

    EffectType(String str) {
        this.effect = str;
    }

    public String getEffectType() {
        return this.effect;
    }
}
