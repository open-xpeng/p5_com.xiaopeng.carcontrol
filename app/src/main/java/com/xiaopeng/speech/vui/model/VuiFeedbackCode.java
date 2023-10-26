package com.xiaopeng.speech.vui.model;

/* loaded from: classes2.dex */
public enum VuiFeedbackCode {
    SUCCESS(1),
    FAIL(0);
    
    private int code;

    VuiFeedbackCode(int i) {
        this.code = i;
    }

    public int getFeedbackCode() {
        return this.code;
    }
}
