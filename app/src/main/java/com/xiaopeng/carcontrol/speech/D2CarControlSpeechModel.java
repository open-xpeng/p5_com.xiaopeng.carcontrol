package com.xiaopeng.carcontrol.speech;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.UserBookHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.vcu.EnergyRecycleGrade;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.protocol.bean.base.CommandValue;
import com.xiaopeng.speech.protocol.node.carcontrol.bean.UserBookValue;

/* loaded from: classes2.dex */
class D2CarControlSpeechModel extends CarControlSpeechModel {
    private static final String TAG = "D2CarControlSpeechModel";

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected boolean checkDriverSeatOccupied() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected boolean checkPsnSeatOccupied() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getDoorKeyValue() {
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected boolean isSupportDrivingMode() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected boolean isSupportEnergyRecycle() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected boolean seatCheckDoorClose() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected boolean isSupportCloseMirror() {
        return CarBaseConfig.getInstance().isSupportMirrorFold() && this.mVcuVm.getCarSpeed() < 3.0f;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onCheckUserBook(UserBookValue userBookValue) {
        String keyword = userBookValue.getKeyword();
        LogUtils.d(TAG, "onCheckUserBook: " + keyword, false);
        UserBookHelper.openUserBook(keyword, true);
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onOpenUserBook() {
        LogUtils.d(TAG, "onOpenUserBook", false);
        UserBookHelper.openUserBook((String) null, true);
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onCloseUserBook() {
        LogUtils.d(TAG, "onCloseUserBook", false);
        UserBookHelper.closeUserBook();
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onModesDrivingSport(int source) {
        LogUtils.d(TAG, "onModesDrivingSport", false);
        super.onModesDrivingSport(source);
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onModesDrivingConservation(int source) {
        LogUtils.d(TAG, "onModesDrivingConservation", false);
        super.onModesDrivingConservation(source);
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onModesDrivingNormal(int source) {
        LogUtils.d(TAG, "onModesDrivingNormal", false);
        super.onModesDrivingNormal(source);
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onEnergyRecycleLow(int source) {
        LogUtils.d(TAG, "onEnergyRecycleLow", false);
        super.onEnergyRecycleLow(source);
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onEnergyRecycleMedium(int source) {
        LogUtils.d(TAG, "onEnergyRecycleMedium", false);
        this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.Middle, source != 1);
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onEnergyRecycleHigh(int source) {
        LogUtils.d(TAG, "onEnergyRecycleHigh", false);
        super.onEnergyRecycleHigh(source);
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onEnergyRecycleUp() {
        int energyRecycleGradeByUser;
        if (isAntiSickOn() || isXPedalOn()) {
            energyRecycleGradeByUser = this.mVcuVm.getEnergyRecycleGradeByUser();
        } else {
            energyRecycleGradeByUser = this.mVcuVm.getEnergyRecycleGrade();
        }
        LogUtils.d(TAG, "onEnergyRecycleUp: current level=" + energyRecycleGradeByUser, false);
        if (energyRecycleGradeByUser == 1) {
            this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.Middle);
        } else if (energyRecycleGradeByUser != 2) {
        } else {
            this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.High);
        }
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onEnergyRecycleDown() {
        int energyRecycleGradeByUser;
        if (isAntiSickOn() || isXPedalOn()) {
            energyRecycleGradeByUser = this.mVcuVm.getEnergyRecycleGradeByUser();
        } else {
            energyRecycleGradeByUser = this.mVcuVm.getEnergyRecycleGrade();
        }
        LogUtils.d(TAG, "onEnergyRecycleDown: current level=" + energyRecycleGradeByUser, false);
        if (energyRecycleGradeByUser == 2) {
            this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.Low);
        } else if (energyRecycleGradeByUser != 3) {
        } else {
            this.mVcuVm.setEnergyRecycleGrade(EnergyRecycleGrade.Middle);
        }
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onWindowsVentilateOn() {
        LogUtils.d(TAG, "onWindowsVentilateOn", false);
        this.mWinDoorVm.controlWindowVent();
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onWindowsVentilateOff() {
        LogUtils.d(TAG, "onWindowsVentilateOff", false);
        this.mWinDoorVm.controlWindowAuto(false);
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onWindowsControl(int pos, int mode, int percent) {
        LogUtils.d(TAG, "onWindowsControl: pos=" + pos + ", mode=" + mode + ", percent=" + percent, false);
        if (this.mWinDoorVm.isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active);
            return;
        }
        boolean z = mode == 0;
        switch (pos) {
            case 0:
                this.mWinDoorVm.controlFLWin(z);
                return;
            case 1:
                this.mWinDoorVm.controlFRWin(z);
                return;
            case 2:
                this.mWinDoorVm.controlRLWin(z);
                return;
            case 3:
                this.mWinDoorVm.controlRRWin(z);
                return;
            case 4:
                this.mWinDoorVm.controlFLWin(z);
                this.mWinDoorVm.controlRLWin(z);
                return;
            case 5:
                this.mWinDoorVm.controlFRWin(z);
                this.mWinDoorVm.controlRRWin(z);
                return;
            case 6:
                this.mWinDoorVm.controlWindowAuto(z);
                return;
            case 7:
                this.mWinDoorVm.controlFLWin(z);
                this.mWinDoorVm.controlFRWin(z);
                return;
            case 8:
                this.mWinDoorVm.controlRLWin(z);
                this.mWinDoorVm.controlRRWin(z);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onMirrorRearSet() {
        LogUtils.d(TAG, "onMirrorRearSet not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onLegUp() {
        LogUtils.d(TAG, "onLegUp not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onLegDown() {
        LogUtils.d(TAG, "onLegDown not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onLegHighest() {
        LogUtils.d(TAG, "onLegHighest not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onLegLowest() {
        LogUtils.d(TAG, "onLegLowest not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getLegHeight() {
        LogUtils.d(TAG, "getLegHeight not support", false);
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected boolean isSupportControlChargePort(int chargePort, int mode) {
        LogUtils.d(TAG, "isSupportControlChargePort not support", false);
        return false;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getStatusChargePortControl(int chargePort, int controlType) {
        LogUtils.d(TAG, "getStatusChargePortControl not support", false);
        return 3;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onChargePortControl(int chargePort, int mode) {
        LogUtils.d(TAG, "onChargePortControl not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onTirePressureShow() {
        LogUtils.d(TAG, "onTirePressureShow not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onLightAtmosphereBrightnessSet(CommandValue commandValue) {
        LogUtils.d(TAG, "onLightAtmosphereBrightnessSet not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onLightAtmosphereBrightnessUp() {
        LogUtils.d(TAG, "onLightAtmosphereBrightnessUp not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onLightAtmosphereBrightnessDown() {
        LogUtils.d(TAG, "onLightAtmosphereBrightnessDown not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onLightAtmosphereBrightnessMax() {
        LogUtils.d(TAG, "onLightAtmosphereBrightnessMax not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onLightAtmosphereBrightnessMin() {
        LogUtils.d(TAG, "onLightAtmosphereBrightnessMin not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getAtmosphereBrightnessStatus() {
        LogUtils.d(TAG, "getAtmosphereBrightnessStatus not support", false);
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onControlScissorLeftDoorOn() {
        LogUtils.d(TAG, "onControlScissorLeftDoorOn not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onControlScissorRightDoorOn() {
        LogUtils.d(TAG, "onControlScissorRightDoorOn not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onControlScissorLeftDoorOff() {
        LogUtils.d(TAG, "onControlScissorLeftDoorOff not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onControlScissorRightDoorOff() {
        LogUtils.d(TAG, "onControlScissorRightDoorOff not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onControlScissorRightDoorPause() {
        LogUtils.d(TAG, "onControlScissorRightDoorPause not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onControlScissorLeftDoorPause() {
        LogUtils.d(TAG, "onControlScissorLeftDoorPause not support");
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getControlScissorDoorLeftOpenSupport() {
        LogUtils.d(TAG, "getControlScissorDoorLeftOpenSupport not support", false);
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getControlScissorDoorRightOpenSupport() {
        LogUtils.d(TAG, "getControlScissorDoorRightOpenSupport not support", false);
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getControlScissorDoorLeftCloseSupport() {
        LogUtils.d(TAG, "getControlScissorDoorLeftCloseSupport not support", false);
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getControlScissorDoorRightCloseSupport() {
        LogUtils.d(TAG, "getControlScissorDoorRightCloseSupport not support", false);
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getControlScissorDoorLeftRunningSupport() {
        LogUtils.d(TAG, "getControlScissorDoorLeftRunningSupport not support", false);
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected int getControlScissorDoorRightRunningSupport() {
        LogUtils.d(TAG, "getControlScissorDoorRightRunningSupport not support", false);
        return -1;
    }

    private boolean isAntiSickOn() {
        return this.mVcuVm.getDriveMode() == 7;
    }

    private boolean isXPedalOn() {
        return this.mVcuVm.getDriveMode() == 5;
    }
}
