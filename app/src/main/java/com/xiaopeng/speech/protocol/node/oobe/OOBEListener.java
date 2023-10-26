package com.xiaopeng.speech.protocol.node.oobe;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface OOBEListener extends INodeListener {
    void onASRError();

    void onAddressSet(String str);

    void onError(String str);

    void onNetWorkError();

    void onRecordInput(String str);

    void onRecordResult(String str);

    void onSearchError();

    void onSkip();

    void onVolumeChange(String str);
}
