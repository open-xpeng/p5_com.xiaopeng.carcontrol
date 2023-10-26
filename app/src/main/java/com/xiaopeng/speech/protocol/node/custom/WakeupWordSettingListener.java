package com.xiaopeng.speech.protocol.node.custom;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface WakeupWordSettingListener extends INodeListener {
    default void onManualInput(String str) {
    }

    default void onSettingDone(String str) {
    }
}
