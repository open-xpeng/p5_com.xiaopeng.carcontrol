package com.xiaopeng.speech.vui.model;

/* loaded from: classes2.dex */
public enum VuiSceneState {
    INIT(0),
    ACTIVE(1),
    UNACTIVE(2),
    IDLE(3);
    
    private int state;

    VuiSceneState(int i) {
        this.state = i;
    }

    public int getState() {
        return this.state;
    }
}
