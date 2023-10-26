package com.xiaopeng.speech.protocol.node.asr;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.jarvisproto.AsrEvent;

/* loaded from: classes2.dex */
public interface AsrListener extends INodeListener {
    void onAsrEvent(int i);

    default void onAsrEvent(AsrEvent asrEvent) {
    }
}
