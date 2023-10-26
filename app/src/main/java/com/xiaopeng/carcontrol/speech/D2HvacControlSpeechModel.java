package com.xiaopeng.carcontrol.speech;

import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.protocol.node.carac.bean.ChangeValue;

/* loaded from: classes2.dex */
class D2HvacControlSpeechModel extends HvacControlSpeechModel {
    private static final String TAG = "D2HvacControlSpeechModel";

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected void closeIntelligentPsn() {
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected int getIntelligentPassengerStatus() {
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected void openIntelligentPsn() {
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected void tempWithAutoOrAc() {
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected void onWindDown(ChangeValue changeValue) {
        LogUtils.d(TAG, "onWindDown:" + changeValue.getValue(), false);
        this.mHvacViewModel.setHvacWindSpeedLevel(this.mHvacViewModel.getHvacWindSpeedLevel() - changeValue.getValue());
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected void onWindUp(ChangeValue changeValue) {
        LogUtils.d(TAG, "onWindUp:" + changeValue.getValue(), false);
        this.mHvacViewModel.setHvacWindSpeedLevel(this.mHvacViewModel.getHvacWindSpeedLevel() + changeValue.getValue());
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected boolean isSupportAutoOff() {
        LogUtils.d(TAG, "isSupportAutoOff", false);
        return false;
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected int isSupportMirrorHeat() {
        LogUtils.d(TAG, "isSupportMirrorHeat", false);
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected void onModesEcoOn() {
        LogUtils.d(TAG, "onModesEcoOn", false);
        if (!this.mHvacViewModel.isHvacPowerModeOn() || this.mHvacViewModel.getHvacEconMode() == 0) {
            this.mHvacViewModel.setHvacEconMode(1);
        }
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected void onModesEcoOff() {
        LogUtils.d(TAG, "onModesEcoOff", false);
        if (this.mHvacViewModel.isHvacPowerModeOn() && this.mHvacViewModel.getHvacEconMode() == 1) {
            this.mHvacViewModel.setHvacEconMode(0);
        }
    }
}
