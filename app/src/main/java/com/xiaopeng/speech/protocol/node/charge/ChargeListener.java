package com.xiaopeng.speech.protocol.node.charge;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface ChargeListener extends INodeListener {
    default void onChangeMileageMode(String str) {
    }

    default void onChargeTrunkPower(boolean z) {
    }

    void onModeFull();

    void onModePercent(int i);

    void onModePercentSupport(int i);

    void onModeSmartClose();

    void onModeSmartCloseSupport();

    void onModeSmartOff();

    void onModeSmartOffSupport();

    void onModeSmartOn();

    void onModeSmartOnSupport();

    void onPortOpen();

    void onPortOpenSupport();

    void onRestart();

    void onRestartSupport();

    void onStart();

    void onStartSupport();

    void onStop();

    void onStopSupport();

    void onUiClose();

    void onUiOpen();

    default void setDischargeLimit(int i) {
    }
}
