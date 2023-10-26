package com.xiaopeng.carcontrol.viewmodel.vcu;

/* loaded from: classes2.dex */
class D2VcuSmartControl extends VcuSmartControl {
    private static final int MILEAGE_THRESHOLD_HIGH = 60;

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuSmartControl
    int getMileageHighThreshold() {
        return 60;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuSmartControl
    void handleTooLow() {
        toEcoMode(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.vcu.VcuSmartControl
    void handleExitLowMode() {
        toPreviousMode(true);
    }
}
