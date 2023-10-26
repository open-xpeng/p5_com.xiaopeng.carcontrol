package com.xiaopeng.speech.asr;

import android.os.RemoteException;
import com.xiaopeng.speech.asr.IRecognizeListener;

/* loaded from: classes2.dex */
class RecognizeListenerImpl extends IRecognizeListener.Stub {
    private final RecognizeListener listener;

    public RecognizeListenerImpl(RecognizeListener recognizeListener) {
        this.listener = recognizeListener;
    }

    @Override // com.xiaopeng.speech.asr.IRecognizeListener
    public void onResult(String str, boolean z) throws RemoteException {
        this.listener.onResult(str, z);
    }

    @Override // com.xiaopeng.speech.asr.IRecognizeListener
    public void onError(int i, String str) throws RemoteException {
        this.listener.onError(i, str);
    }

    @Override // com.xiaopeng.speech.asr.IRecognizeListener
    public void onState(int i, int i2) throws RemoteException {
        this.listener.onState(i, i2);
    }

    @Override // com.xiaopeng.speech.asr.IRecognizeListener
    public void onExtra(int i, int i2, int i3, String str, byte[] bArr) throws RemoteException {
        this.listener.onExtra(i, i2, i3, str, bArr);
    }
}
