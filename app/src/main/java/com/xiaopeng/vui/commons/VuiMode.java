package com.xiaopeng.vui.commons;

/* loaded from: classes2.dex */
public enum VuiMode {
    DISABLED("Disabled"),
    SILENT("Silent"),
    UNDERSTOOD("Understood"),
    NORMAL("Normal");
    
    String name;

    VuiMode(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }
}
