package com.xiaopeng.carcontrol.viewmodel.service;

import com.xiaopeng.carcontrol.model.CarControlSyncDataEvent;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodySmartControl;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class D55ServiceViewModel extends ServiceViewModel {
    private static final String TAG = "D55ServiceViewModel";

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void checkBonnetOpened(int state) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void checkBrakeFluid(float speed) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void checkBrkPedalStatus(int status) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void handlePGearMenu(int gear) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    void resumeOtherVcuFunction() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    void resumeChassisModule() {
        if (this.mDataSync.mIsDataSynced) {
            this.mEpsController.setSteeringEps(this.mDataSync.getSteerMode());
        }
        this.mEspController.setEspSw(true);
        this.mEspController.setHdc(false);
        this.mChassisSmartControl.checkEspCondition(true);
        if (this.mDataSync.mIsDataSynced) {
            this.mChassisSmartControl.checkAvhCondition();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    void resumeWheelKeyModule() {
        if (this.mDataSync.mIsDataSynced) {
            int wheelXKey = this.mDataSync.getWheelXKey();
            if (wheelXKey == 4 && !this.mCarConfigHelper.isSupportAutoPark()) {
                LogUtils.i(TAG, "Not support auto park, do not resume xkey for: " + wheelXKey, false);
            } else if (wheelXKey == 9 && !this.mCarConfigHelper.isSupportXSport()) {
                LogUtils.i(TAG, "Not support xsport, do not resume xkey for: " + wheelXKey, false);
            } else {
                if (wheelXKey == 10 && !this.mCarConfigHelper.isSupportNra()) {
                    LogUtils.i(TAG, "Not support nra, do not resume xkey for: " + wheelXKey, false);
                }
                this.mIcmController.setXKeyForCustomer(wheelXKey);
                this.mIcmController.setDoorKeyForCustomer(this.mDataSync.getDoorBossKey());
            }
            wheelXKey = 0;
            this.mIcmController.setXKeyForCustomer(wheelXKey);
            this.mIcmController.setDoorKeyForCustomer(this.mDataSync.getDoorBossKey());
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void syncAtlData(CarControlSyncDataEvent event) {
        if (this.mCarConfigHelper.isSupportAtl()) {
            int atlSingleColor = this.mAtlController.getAtlSingleColor(null);
            if (!event.isAtlSingleColor()) {
                LogUtils.d(TAG, "ATL single color, save currentValue: " + atlSingleColor);
                this.mDataSync.setAtlSingleColor(atlSingleColor);
            } else {
                int atlSingleColor2 = this.mDataSync.getAtlSingleColor();
                LogUtils.d(TAG, "ATL single color, resume saved value: " + atlSingleColor2 + ", currentValue: " + atlSingleColor);
                if (atlSingleColor2 != atlSingleColor) {
                    this.mAtlController.setAtlSingleColor(null, atlSingleColor2, false);
                }
            }
            boolean isAtlSwEnabled = this.mAtlController.isAtlSwEnabled();
            if (!event.isAtlSw()) {
                LogUtils.d(TAG, "ATL, save currentValue: " + isAtlSwEnabled);
                this.mDataSync.setAtlSw(isAtlSwEnabled);
                return;
            }
            boolean atlSw = this.mDataSync.getAtlSw();
            LogUtils.d(TAG, "ATL, resume saved value: " + atlSw + ", currentValue: " + isAtlSwEnabled);
            if (atlSw != isAtlSwEnabled) {
                this.mAtlController.setAtlSwEnable(atlSw);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void syncLockData(CarControlSyncDataEvent event) {
        boolean driveAutoLock = this.mBcmController.getDriveAutoLock();
        if (!event.isDriveAutoLock()) {
            LogUtils.d(TAG, "Drive auto lock, save currentValue: " + driveAutoLock);
            this.mDataSync.setDriveAutoLock(driveAutoLock);
        } else {
            boolean driveAutoLock2 = this.mDataSync.getDriveAutoLock();
            LogUtils.d(TAG, "Drive auto lock, resume saved value: " + driveAutoLock2 + ", currentValue: " + driveAutoLock);
            if (driveAutoLock2 != driveAutoLock) {
                this.mBcmController.setDriveAutoLock(driveAutoLock2);
            }
        }
        boolean parkingAutoUnlock = this.mBcmController.getParkingAutoUnlock();
        if (!event.isParkAutoUnlock()) {
            LogUtils.d(TAG, "Park auto unlock, save currentValue: " + parkingAutoUnlock);
            this.mDataSync.setParkAutoUnlock(parkingAutoUnlock);
        } else {
            boolean parkAutoUnlock = this.mDataSync.getParkAutoUnlock();
            LogUtils.d(TAG, "Park auto unlock, resume saved value: " + parkAutoUnlock + ", currentValue: " + parkingAutoUnlock);
            if (parkAutoUnlock != parkingAutoUnlock) {
                this.mBcmController.setParkingAutoUnlock(parkAutoUnlock);
            }
        }
        int unlockResponse = this.mBcmController.getUnlockResponse();
        if (!event.isUnlockResponse()) {
            LogUtils.d(TAG, "Unlock response, save currentValue: " + unlockResponse);
            this.mDataSync.setUnlockResponse(unlockResponse);
        } else {
            int unlockResponse2 = this.mDataSync.getUnlockResponse();
            LogUtils.d(TAG, "Unlock response, resume saved value: " + unlockResponse2 + ", currentValue: " + unlockResponse);
            CarBodySmartControl.getInstance().setUnlockResponse(unlockResponse2);
        }
        boolean isAutoDoorHandleEnabled = this.mBcmController.isAutoDoorHandleEnabled();
        if (!event.isAutoDhc()) {
            LogUtils.d(TAG, "Auto door handle, save currentValue: " + isAutoDoorHandleEnabled);
            this.mDataSync.setAutoDhc(isAutoDoorHandleEnabled);
            return;
        }
        boolean autoDhc = this.mDataSync.getAutoDhc();
        LogUtils.d(TAG, "Auto door handle, resume saved value: " + autoDhc + ", currentValue: " + isAutoDoorHandleEnabled);
        if (autoDhc != isAutoDoorHandleEnabled) {
            this.mBcmController.setAutoDoorHandleEnable(autoDhc, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void syncHighSpdCloseWin(CarControlSyncDataEvent event) {
        if (!event.isHighSpdCloseWin()) {
            boolean highSpdCloseWinSw = SharedPreferenceUtil.getHighSpdCloseWinSw();
            LogUtils.i(TAG, "High speed close win is null, save currentValue: " + highSpdCloseWinSw, false);
            this.mDataSync.setHighSpdCloseWin(highSpdCloseWinSw);
            this.mBcmController.setHighSpdCloseWin(highSpdCloseWinSw);
            return;
        }
        boolean isWinHighSpdEnabled = this.mDataSync.isWinHighSpdEnabled();
        LogUtils.i(TAG, "High speed close win, resume saved value: " + isWinHighSpdEnabled, false);
        this.mBcmController.setHighSpdCloseWin(isWinHighSpdEnabled);
    }
}
