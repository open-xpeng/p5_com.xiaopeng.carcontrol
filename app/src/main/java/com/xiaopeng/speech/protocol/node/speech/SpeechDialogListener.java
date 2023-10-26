package com.xiaopeng.speech.protocol.node.speech;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface SpeechDialogListener extends INodeListener {
    void onCloseSceneGuideWindow(String str);

    void onCloseSpeechSceneSetting();

    default void onCloseSuperDialogue() {
    }

    void onCloseWindow(String str);

    void onOpenSceneGuideWindow(String str);

    void onOpenSpeechSceneSetting();

    default void onOpenSuperDialogue() {
    }

    void onOpenWindow(String str);

    default void onRefreshUi(int i, boolean z) {
    }
}
