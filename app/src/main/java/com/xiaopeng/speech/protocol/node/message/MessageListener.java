package com.xiaopeng.speech.protocol.node.message;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface MessageListener extends INodeListener {
    void onAIMessage(int i);

    void onAIMessageDisable();

    void onAIMessageDisableSevenDays();

    void onCancel();

    void onCommonAIMessage(String str);

    void onCommonCancel();

    void onCommonSubmit();

    void onHotWordEngineOK(boolean z);

    void onParkingSelected(int i);

    void onPathChanged();
}
