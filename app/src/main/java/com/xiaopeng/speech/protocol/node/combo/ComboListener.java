package com.xiaopeng.speech.protocol.node.combo;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface ComboListener extends INodeListener {
    default void enterUserMode(String str) {
    }

    default void exitUserModel(String str) {
    }

    void onDataModeBioTts();

    void onDataModeFridgeTts();

    void onDataModeInvisibleTts();

    void onDataModeMeditationTts();

    void onDataModeVentilateTts();

    void onDataModeWaitTts();

    void onFastCloseModeInvisible();

    void onModeBio();

    void onModeBioOff();

    void onModeFridge();

    void onModeFridgeOff();

    void onModeInvisible();

    void onModeInvisibleOff();

    void onModeVentilate();

    void onModeVentilateOff();

    void onModeWait();

    void onModeWaitOff();
}
