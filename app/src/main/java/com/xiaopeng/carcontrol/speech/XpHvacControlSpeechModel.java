package com.xiaopeng.carcontrol.speech;

import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.protocol.node.carac.bean.ChangeValue;

/* loaded from: classes2.dex */
class XpHvacControlSpeechModel extends HvacControlSpeechModel {
    private static final String TAG = "XpHvacControlSpeechModel";

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
    protected void onHeadWindowOn() {
        LogUtils.d(TAG, "onHeadWindowOn");
        this.mHvacViewModel.setHvacWindBlowModeGroup(5);
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected void onHeadWindowFootOn() {
        LogUtils.d(TAG, "onHeadWindowFootOn");
        this.mHvacViewModel.setHvacWindBlowModeGroup(7);
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected void onHeadWindowOff() {
        LogUtils.d(TAG, "onHeadWindowOff");
        this.mHvacViewModel.setHvacWindBlowModeGroup(3);
    }

    @Override // com.xiaopeng.carcontrol.speech.HvacControlSpeechModel
    protected int getTempDualMode() {
        LogUtils.i(TAG, "getTempDualMode");
        return (this.mHvacViewModel.getHvacEconMode() != 1 || this.mSeatViewModel.isPsnSeatOccupied()) ? 1 : 0;
    }
}
