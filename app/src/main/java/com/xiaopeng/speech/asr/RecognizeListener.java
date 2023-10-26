package com.xiaopeng.speech.asr;

/* loaded from: classes2.dex */
public interface RecognizeListener {
    void onError(int i, String str);

    void onExtra(int i, int i2, int i3, String str, byte[] bArr);

    void onResult(String str, boolean z);

    void onState(int i, int i2);
}
