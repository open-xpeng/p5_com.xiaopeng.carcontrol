package com.xiaopeng.carcontrol.viewmodel.service;

import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.bean.Mirror;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.ICiuController;
import com.xiaopeng.carcontrol.carmanager.controller.IIcmController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.model.CarControlSyncDataEvent;
import com.xiaopeng.carcontrol.model.D2DataSyncModel;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasSmartControl;
import com.xiaopeng.carcontrol.viewmodel.cabin.CabinSmartControl;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatSmartControl;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodySmartControl;
import com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisSmartControl;
import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class D2ServiceViewModel extends ServiceViewModel {
    private static final String TAG = "D2ServiceViewModel";
    private final CiuCallbackImpl mCiuCallback;
    private ICiuController mCiuController;

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
    protected void checkDoorBossKeySw() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void checkWelcomeModeGoOff() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel, com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void handleEmergencyIgOff() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void handlePGearMenu(int gear) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void handleRearMirrorWhenGoOn() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public D2ServiceViewModel() {
        CiuCallbackImpl ciuCallbackImpl = new CiuCallbackImpl();
        this.mCiuCallback = ciuCallbackImpl;
        ICiuController iCiuController = (ICiuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_CIU_SERVICE);
        this.mCiuController = iCiuController;
        iCiuController.registerCallback(ciuCallbackImpl);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel, com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void onIgLocalOn() {
        super.onIgLocalOn();
        if (this.mCarConfigHelper.isSupportMirrorDown() && this.mDataSync.mIsDataSynced && this.mVcuController.getGearLevel() == 4 && !this.mDataSync.isGuest()) {
            CabinSmartControl.getInstance().syncAccountMirrorPos(this.mDataSync.getMirrorData(), true);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel, com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void onIgRemoteOn() {
        super.onIgRemoteOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel, com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void onIgOff() {
        this.mEspController.setEspSw(false);
        this.mEspController.setHdc(false);
        this.mHvacSmartControl.resetSmartMode();
        this.mBcmController.setMirrorHeat(false);
        this.mBcmController.setBackDefrost(false);
        dismissMicrophoneDialog();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel, com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void resumeSaveSettings() {
        ICiuController iCiuController;
        LogUtils.i(TAG, "resumeSaveSettings mIsDataSyncEmpty: " + this.mIsDataSyncEmpty, false);
        resumeVcuModule();
        this.mBcmController.setHeadLampState(3, true);
        if (this.mDataSync.mIsDataSynced) {
            this.mBcmController.setLightMeHome(this.mDataSync.getLightMeHome(), false);
        }
        this.mBcmController.setRearFogLamp(false);
        if (this.mDataSync.mIsDataSynced) {
            this.mBcmController.setDriveAutoLock(this.mDataSync.getDriveAutoLock());
            this.mBcmController.setParkingAutoUnlock(this.mDataSync.getParkAutoUnlock());
            int unlockResponse = this.mDataSync.getUnlockResponse();
            LogUtils.i(TAG, "resume Unlock Response value: " + unlockResponse, false);
            if (this.mCarConfigHelper.isNewAvasArch()) {
                CarBodySmartControl.getInstance().setUnlockResponse(unlockResponse);
            } else {
                this.mBcmController.setUnlockResponse(this.mDataSync.getUnlockResponse());
            }
            this.mBcmController.setSeatWelcomeMode(this.mDataSync.getWelcomeMode());
            if (this.mCarConfigHelper.isSupportDhc()) {
                this.mBcmController.setAutoDoorHandleEnable(this.mDataSync.getAutoDhc(), false);
            }
        }
        if (this.mMcuController.getCiuState()) {
            this.mCiuController.setCiuWiperLevel(this.mDataSync.getCiuWiperInterval());
        } else {
            this.mBcmController.setWiperInterval(this.mDataSync.getWiperInterval());
        }
        SeatSmartControl.getInstance().onFollowedVehicleLostConfigChanged(true);
        if (this.mCarConfigHelper.isFollowVehicleLostConfigUseNew()) {
            SeatSmartControl.getInstance().setFollowedVehicleLostConfig("2");
        }
        if (this.mDataSync.mIsDataSynced) {
            this.mBcmController.setRsbWarning(this.mDataSync.getRsbWarning());
        }
        if (this.mCarConfigHelper.isSupportCwc() && this.mCarConfigHelper.isSupportCwc() && this.mDataSync.mIsDataSynced) {
            this.mBcmController.setCwcSwEnable(this.mDataSync.getCwcSw());
        }
        if (this.mDataSync.mIsDataSynced) {
            this.mBcmController.setHighSpdCloseWin(this.mDataSync.isWinHighSpdEnabled());
        }
        D2DataSyncModel d2DataSyncModel = (D2DataSyncModel) this.mDataSync;
        this.mScuController.setFcwState(true);
        if (this.mDataSync.mIsDataSynced) {
            if (!this.mCarConfigHelper.isSupportLka()) {
                this.mScuController.setLdwState(this.mDataSync.getLdwSw());
            }
            this.mScuController.setBsdState(this.mDataSync.getBsdSw());
            if (this.mCarConfigHelper.isSupportIslc()) {
                this.mScuController.setIslcState(this.mDataSync.getIslcSw());
            }
            this.mScuController.setRctaState(d2DataSyncModel.getSideReversingWarning());
        }
        this.mScuController.setDowSw(this.mDataSync.getDowSw(), false);
        if (this.mCarConfigHelper.isSupportElk()) {
            if (BaseFeatureOption.getInstance().isForceTurnOnElkWhenIgOn()) {
                this.mScuController.setElkState(true);
            } else {
                this.mScuController.setElkState(this.mDataSync.getElkSw());
            }
        }
        resumeOrSyncLccAlcState(true);
        resumeOrSyncApaState(true);
        resumeChassisModule();
        resumeWheelKeyModule();
        if (this.mCarConfigHelper.isSupportEbw()) {
            if (this.mDataSync.mIsDataSynced) {
                this.mBcmController.setEbwEnable(this.mDataSync.getEbwEnable());
            }
        } else {
            this.mBcmController.setEbwEnable(GlobalConstant.DEFAULT.EMERGENCY_BREAK_WARNING);
        }
        this.mAvasController.setLowSpdSoundEnable(AvasSmartControl.getInstance().isSettingLowSpdEnable());
        if (this.mDataSync.mIsDataSynced) {
            this.mAvasController.setLowSpdSoundType(this.mDataSync.getAvasEffect());
        }
        if (this.mDataSync.mIsDataSynced && this.mCarConfigHelper.isSupportMirrorDown()) {
            this.mBcmController.setMirrorAutoDown(this.mDataSync.getMirrorAutoDownSw());
        }
        if (this.mMcuController.getCiuState() && (iCiuController = this.mCiuController) != null && iCiuController.isCiuValid()) {
            this.mCiuController.controlDmsSwitch(this.mDataSync.getCiuCameraSw());
        }
        if (this.mCarConfigHelper.isSupportDrvSeatVent()) {
            this.mBcmController.restoreSeatVentLevel();
        }
        if (this.mCarConfigHelper.isSupportDrvSeatHeat()) {
            this.mBcmController.restoreSeatHeatLevel();
        }
        if (this.mDataSync.mIsDataSynced) {
            HashMap<Integer, Object> hashMap = new HashMap<>();
            hashMap.put(Integer.valueOf((int) IIcmController.ID_ICM_TEMPERATURE), Boolean.valueOf(d2DataSyncModel.getMeterDefineTemperature()));
            hashMap.put(Integer.valueOf((int) IIcmController.ID_ICM_WIND_POWER), Boolean.valueOf(d2DataSyncModel.getMeterDefineWindPower()));
            hashMap.put(Integer.valueOf((int) IIcmController.ID_ICM_WIND_MODE), Boolean.valueOf(d2DataSyncModel.getMeterDefineWindMode()));
            hashMap.put(Integer.valueOf((int) IIcmController.ID_ICM_MEDIA_SOURCE), Boolean.valueOf(this.mCarConfigHelper.isSupportSwitchMedia() && d2DataSyncModel.getMeterDefineMediaSource()));
            hashMap.put(Integer.valueOf((int) IIcmController.ID_ICM_SCREEN_LIGHT), Boolean.valueOf(d2DataSyncModel.getMeterDefineScreenLight()));
            hashMap.put(Integer.valueOf((int) IIcmController.ID_ICM_SPEED_LIMIT_WARNING_SWITCH), Boolean.valueOf(d2DataSyncModel.getSpeedLimit()));
            if (d2DataSyncModel.getSpeedLimit()) {
                hashMap.put(Integer.valueOf((int) IIcmController.ID_ICM_SPEED_LIMIT_WARNING_VALUE), Integer.valueOf(d2DataSyncModel.getSpeedLimitValue()));
            }
            this.mIcmController.setMeterMultiProperties(hashMap);
        }
        if (this.mDataSync.mIsDataSynced && this.mCarConfigHelper.isSupportOldLka() && this.mVcuController.getGearLevel() == 4) {
            int lssState = d2DataSyncModel.getLssState();
            LogUtils.i(TAG, "Start to resume Lss State:" + lssState);
            this.mScuController.setLssMode(lssState);
        }
        if (this.mCarConfigHelper.isSupportIsla()) {
            int islaSw = d2DataSyncModel.getIslaSw();
            if (islaSw != 1 && islaSw != 2) {
                islaSw = 1;
            }
            LogUtils.i(TAG, "Start to resume ISLA sw:" + islaSw, false);
            this.mScuController.setIslaSw(islaSw);
            int islaSpdRange = d2DataSyncModel.getIslaSpdRange();
            LogUtils.i(TAG, "Start to resume ISLA speed range:" + islaSpdRange, false);
            this.mScuController.setIslaSpdRange(islaSpdRange);
            boolean islaConfirmMode = d2DataSyncModel.getIslaConfirmMode();
            LogUtils.i(TAG, "Start to resume ISLA confirm mode:" + islaConfirmMode, false);
            this.mScuController.setIslaConfirmMode(islaConfirmMode);
        }
        if (this.mCarConfigHelper.isSupportLlu()) {
            if (this.mDataSync.mIsDataSynced) {
                this.mBcmController.setParkLampIncludeFmB(this.mDataSync.getParkLampB());
                boolean lluSw = this.mDataSync.getLluSw();
                this.mLluController.setLluEnable(lluSw);
                if (lluSw) {
                    this.mLluController.setLluWakeWaitSwitch(this.mDataSync.getLluUnlockSw(), true, false);
                    this.mLluController.setLluSleepSwitch(this.mDataSync.getLluLockSw(), true, false);
                    this.mLluController.setLluChargingSwitch(this.mDataSync.getLluChargeSw(), true, false);
                } else {
                    LogUtils.i(TAG, "Set all llu sub effect sw to false, because llu sw is false", false);
                    this.mLluController.setLluWakeWaitSwitch(false, true, true);
                    this.mLluController.setLluSleepSwitch(false, true, true);
                    this.mLluController.setLluChargingSwitch(false, true, true);
                }
            }
        } else {
            LogUtils.d(TAG, "llu not support, force save Park lamp B to false ");
            this.mBcmController.setParkLampIncludeFmB(false, false);
        }
        this.mIsInitComplete = true;
        this.mIsDataSyncEmpty = false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    void resumeVcuModule() {
        int driveMode;
        if (this.mDataSync.mIsDataSynced) {
            if (this.mCarConfigHelper.isSupportDriveEnergyReset()) {
                driveMode = 0;
            } else if (ShowCarControl.getInstance().isShowCarDriveDisable()) {
                driveMode = IVcuController.DRIVE_MODE_SHOW_MODE;
            } else if (this.mDataSync.isXpedal()) {
                driveMode = 5;
            } else {
                driveMode = this.mDataSync.isAntiSicknessEnabled() ? 7 : this.mDataSync.getDriveMode();
            }
            LogUtils.i(TAG, "Resume drive mode: " + driveMode, false);
            this.mVcuController.setDriveMode(driveMode);
            if (driveMode == 0 || driveMode == 1 || driveMode == 2) {
                int recycleGrade = this.mCarConfigHelper.isSupportDriveEnergyReset() ? 3 : this.mDataSync.getRecycleGrade();
                LogUtils.i(TAG, "Resume energy recycle grade: " + recycleGrade, false);
                this.mVcuController.setEnergyRecycleGrade(recycleGrade);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    void resumeChassisModule() {
        this.mEspController.setHdc(false);
        this.mEspController.setEspSw(true);
        ChassisSmartControl.getInstance().checkEspCondition(true);
        if (this.mDataSync.mIsDataSynced) {
            this.mChassisSmartControl.checkAvhCondition();
            this.mEpsController.setSteeringEps(this.mDataSync.getSteerMode());
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
            }
            wheelXKey = 0;
            this.mIcmController.setXKeyForCustomer(wheelXKey);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void handleLccWhenLogin() {
        boolean lccSw = this.mDataSync.getLccSw();
        boolean alcSw = this.mDataSync.getAlcSw();
        boolean shouldUpdateLccSafeExam = this.mDataSync.shouldUpdateLccSafeExam();
        LogUtils.i(TAG, "need to update lcc exam result ?, shouldUpdateLccSafeExam:" + shouldUpdateLccSafeExam + ", lccState: " + lccSw);
        if (shouldUpdateLccSafeExam && lccSw) {
            updateLccSafeExamResult();
        }
        if (lccSw && !this.mDataSync.getLccSafeExamResult()) {
            LogUtils.i(TAG, "Lcc memory value from sp is ON,but no exam passed record in local!");
            lccSw = false;
            alcSw = false;
        }
        this.mScuController.setLccState(lccSw);
        this.mScuController.setAlcState(alcSw);
    }

    /* JADX WARN: Removed duplicated region for block: B:168:0x070f  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0729  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0769  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0783  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x07df  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0845  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x085f  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x089f  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x08b9  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x08f9  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0913  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0965  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0976  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0992  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x09c5  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0a20  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0aac  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0aae  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0ab5  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0ad1  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0b1a  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0b1c  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0b23  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0b3f  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0b72  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0be8  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0c25  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0c3f  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0c70  */
    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void handleSyncSettings(com.xiaopeng.carcontrol.model.CarControlSyncDataEvent r20) {
        /*
            Method dump skipped, instructions count: 3277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.service.D2ServiceViewModel.handleSyncSettings(com.xiaopeng.carcontrol.model.CarControlSyncDataEvent):void");
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void syncMirrorData(CarControlSyncDataEvent event) {
        if (this.mCarConfigHelper.isSupportMirrorDown()) {
            boolean isMirrorAutoDown = this.mBcmController.isMirrorAutoDown();
            boolean z = false;
            if (!event.isMirrorAutoDown()) {
                LogUtils.i(TAG, "Mirror auto down is null, save currentValue: " + isMirrorAutoDown, false);
                this.mDataSync.setMirrorAutoDownSw(isMirrorAutoDown);
            } else {
                boolean mirrorAutoDownSw = this.mDataSync.getMirrorAutoDownSw();
                LogUtils.i(TAG, "Mirror auto down, resume saved value: " + mirrorAutoDownSw + ", currentValue: " + isMirrorAutoDown, false);
                if (mirrorAutoDownSw != isMirrorAutoDown) {
                    this.mBcmController.setMirrorAutoDown(mirrorAutoDownSw);
                }
            }
            if (!event.isMirrorPos()) {
                Mirror mirror = new Mirror();
                mirror.leftHPos = this.mBcmController.getLeftMirrorLRPos(true);
                mirror.leftVPos = this.mBcmController.getLeftMirrorUDPos(true);
                mirror.rightHPos = this.mBcmController.getRightMirrorLRPos(true);
                mirror.rightVPos = this.mBcmController.getRightMirrorUDPos(true);
                String mirror2 = mirror.toString();
                LogUtils.i(TAG, "Mirror saved position is null, save current mirror data: " + mirror2, false);
                this.mDataSync.setMirrorData(mirror2);
            } else {
                this.mDataSync.setMirrorRestoreFinished();
            }
            CabinSmartControl cabinSmartControl = CabinSmartControl.getInstance();
            String mirrorData = this.mDataSync.getMirrorData();
            if (this.mIsLocalIgOn && this.mVcuController.getGearLevel() == 4 && !this.mDataSync.isGuest()) {
                z = true;
            }
            cabinSmartControl.syncAccountMirrorPos(mirrorData, z);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    void handleFactoryReset() {
        LogUtils.i(TAG, "Save current seat position to Temp account", false);
        int[] iArr = {this.mMsmController.getDSeatHorzPos(), this.mMsmController.getDSeatVerPos(), this.mMsmController.getDSeatTiltPos()};
        LogUtils.i(TAG, "Save current seat position: " + Arrays.toString(iArr));
        this.mDataSync.saveDrvSeatPos(iArr);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void checkWelcomeModeGoOn() {
        int[] drvSeatSavedPos;
        LogUtils.d(TAG, "checkEnterWelcomeModeGoOn mode=" + this.mBcmController.getSeatWelcomeMode() + " p=" + this.mVcuController.getGearLevel() + " s=" + this.mVcuController.getCarSpeed() + " occupied=" + this.mBcmController.isDrvSeatOccupied() + " door=" + isDrvDoorClose() + " belt=" + this.mBcmController.getDrvBeltStatus());
        if (this.mBcmController.getSeatWelcomeMode() && this.mVcuController.getGearLevel() == 4 && this.mVcuController.getCarSpeed() < 3.0f && this.mBcmController.isDrvSeatOccupied() && isDrvDoorClose() && (drvSeatSavedPos = this.mDataSync.getDrvSeatSavedPos()) != null) {
            this.mMsmController.setDriverAllPositions(drvSeatSavedPos[0], drvSeatSavedPos[1], drvSeatSavedPos[2], drvSeatSavedPos[3], drvSeatSavedPos[4]);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel
    protected void handleEspFaultChanged(boolean isFault) {
        if (isFault) {
            return;
        }
        this.mChassisSmartControl.checkEspCondition();
    }

    /* loaded from: classes2.dex */
    private static class CiuCallbackImpl implements ICiuController.Callback {
        private CiuCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController.Callback
        public void onCiuWiperIntervalChanged(int level) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_WIPER_INT, WiperInterval.fromCiuState(level));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.ICiuController.Callback
        public void onCiuWiperAutoSwitchChanged(boolean enable) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_WIPER_GEAR_AUTO_EXIST_BOOL, Boolean.valueOf(enable));
        }
    }
}
