package com.xiaopeng.speech.protocol.node.floater;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.bean.WindowAnimState;

/* loaded from: classes2.dex */
public interface FloaterSpeechListener extends INodeListener {
    void onCloseWindow(int i);

    void onSetAnimState(WindowAnimState windowAnimState);

    void onXiaopExpression(String str);
}
