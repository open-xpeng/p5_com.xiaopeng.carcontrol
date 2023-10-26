package com.xiaopeng.speech.protocol.node.tts;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface TtsListener extends INodeListener {
    default void onTtsTimbreSetting(int i) {
    }

    void ttsEnd(String str);

    void ttsStart(String str);
}
