package com.xiaopeng.carcontrol.viewmodel.lamp;

/* loaded from: classes2.dex */
public enum LluPreviewState {
    Idle,
    Switching,
    Starting,
    Previewing,
    Stopping;
    
    private LluEffect mEffect;

    public void setEffect(LluEffect effect) {
        this.mEffect = effect;
    }

    public LluEffect getEffect() {
        return this.mEffect;
    }
}
