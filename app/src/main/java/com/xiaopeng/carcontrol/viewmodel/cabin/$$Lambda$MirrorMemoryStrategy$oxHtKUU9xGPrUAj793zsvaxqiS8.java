package com.xiaopeng.carcontrol.viewmodel.cabin;

/* compiled from: lambda */
/* renamed from: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$MirrorMemoryStrategy$oxHtKUU9xGPrUAj793zsvaxqiS8  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$MirrorMemoryStrategy$oxHtKUU9xGPrUAj793zsvaxqiS8 implements Runnable {
    public final /* synthetic */ MirrorMemoryStrategy f$0;

    public /* synthetic */ $$Lambda$MirrorMemoryStrategy$oxHtKUU9xGPrUAj793zsvaxqiS8(MirrorMemoryStrategy mirrorMemoryStrategy) {
        this.f$0 = mirrorMemoryStrategy;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.saveReversePos();
    }
}
