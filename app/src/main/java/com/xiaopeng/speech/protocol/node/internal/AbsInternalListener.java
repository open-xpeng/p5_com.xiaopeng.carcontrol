package com.xiaopeng.speech.protocol.node.internal;

/* loaded from: classes2.dex */
public abstract class AbsInternalListener implements InternalListener {
    @Override // com.xiaopeng.speech.protocol.node.internal.InternalListener
    public void onDmOutput(String str) {
    }

    @Override // com.xiaopeng.speech.protocol.node.internal.InternalListener
    public void onInputDmData(String str, String str2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.internal.InternalListener
    public void onLocalWakeupResult(String str, String str2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.internal.InternalListener
    public void onLocalWakeupResultWithChannel(String str, String str2, String str3) {
    }

    @Override // com.xiaopeng.speech.protocol.node.internal.InternalListener
    public void onRebootComplete(String str, String str2) {
    }

    @Override // com.xiaopeng.speech.protocol.node.internal.InternalListener
    public void onResourceUpdateFinish(String str, String str2) {
    }
}
