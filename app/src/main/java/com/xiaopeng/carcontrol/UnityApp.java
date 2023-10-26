package com.xiaopeng.carcontrol;

import android.content.Context;

/* loaded from: classes.dex */
public class UnityApp extends App {
    @Override // com.xiaopeng.carcontrol.App
    void onAppCreate() {
    }

    @Override // com.xiaopeng.carcontrol.App
    void preloadControlPanel() {
    }

    @Override // android.content.ContextWrapper
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sAppInstance = this;
        sIsMainProcess = true;
    }
}
