package com.xiaopeng.carcontrol.viewmodel.space;

import androidx.lifecycle.Observer;

/* compiled from: lambda */
/* renamed from: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$L5-j_QEbveG9sCZKt4HxS3fA7CQ  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$L5j_QEbveG9sCZKt4HxS3fA7CQ implements Observer {
    public final /* synthetic */ VipSeatControl f$0;

    @Override // androidx.lifecycle.Observer
    public final void onChanged(Object obj) {
        this.f$0.onSeatOccupiedChanged((Boolean) obj);
    }
}
