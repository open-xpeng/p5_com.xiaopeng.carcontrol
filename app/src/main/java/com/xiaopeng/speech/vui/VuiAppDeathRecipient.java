package com.xiaopeng.speech.vui;

import android.os.IBinder;

/* loaded from: classes2.dex */
public class VuiAppDeathRecipient implements IBinder.DeathRecipient {
    private IBinder binder;

    public VuiAppDeathRecipient(IBinder iBinder) {
        this.binder = iBinder;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        VuiSceneManager.instance().insertVuiProviderWhenDeath();
        IBinder iBinder = this.binder;
        if (iBinder != null) {
            iBinder.unlinkToDeath(this, 0);
            this.binder = null;
        }
    }
}
