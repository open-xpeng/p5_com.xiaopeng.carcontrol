package com.xiaopeng.carcontrol.viewmodel.hvac;

import com.xiaopeng.carcontrol.IpcRouterService;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class D2HvacSmartControl extends HvacSmartControl {
    private static final int HVAC_DEODORANT_FAN_SPEED = 4;
    private static final int HVAC_RAPID_COOLING_FAN_SPEED = 6;
    private static final String TAG = "D2HvacSmartControl";
    private boolean mIsAirProtectOpen;

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    protected void initAirPurgeMode() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onAirQualityLevelChanged(int level) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onAqsModeChange(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onFrontDefrostChange(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onSmartHvacSwChanged(boolean enabled) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    protected void resetAirPurge() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void smartControlHvac() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacInnerAqChanged(int aqValue) {
        if (CarBaseConfig.getInstance().isSupportInnerPm25()) {
            LogUtils.d(TAG, "onHvacInnerAqChanged:" + aqValue + ",isAuto:" + this.mHvacBaseVm.isHvacAutoModeOn(), false);
            if (aqValue < 5 || aqValue == 1023) {
                return;
            }
            if (aqValue >= 100 && FunctionModel.getInstance().isAirProtectFuncAllowed()) {
                FunctionModel.getInstance().setAirProtectTs(System.nanoTime());
                if (this.mHvacBaseVm.isHvacQualityPurgeEnable() || !this.mWindowBaseVm.isAllDoorsClosed()) {
                    return;
                }
                this.mIsAirProtectOpen = true;
                if (this.mHvacBaseVm.isHvacAutoModeOn()) {
                    doAirProtect(true);
                } else {
                    airProtectRemind();
                }
            } else if (aqValue >= 100 || !this.mIsAirProtectOpen) {
            } else {
                if (this.mHvacBaseVm.isHvacQualityPurgeEnable() && this.mHvacBaseVm.isHvacAutoModeOn()) {
                    this.mHvacBaseVm.setHvacQualityPurgeMode(false);
                }
                this.mIsAirProtectOpen = false;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void doAirProtect(boolean auto) {
        if (CarBaseConfig.getInstance().isSupportInnerPm25()) {
            if (!this.mHvacBaseVm.isHvacQualityPurgeEnable()) {
                this.mHvacBaseVm.setHvacQualityPurgeMode(true);
            }
            NotificationHelper.getInstance().showToast(auto ? R.string.hvac_auto_air_auto_toast : R.string.hvac_auto_air_remind_toast);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onAirQualityOutsideChanged(boolean isPolluted) {
        if (isPolluted && this.mWindowBaseVm.isAllDoorsClosed()) {
            if (this.mHvacBaseVm.isHvacAirIntakeAutoEnable() || this.mHvacBaseVm.getHvacCirculationMode() == 2) {
                if (!this.mHvacBaseVm.isHvacAutoModeOn()) {
                    airAirPollutedProtectRemind();
                } else {
                    doAirPollutedProtect(true);
                }
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    void airAirPollutedProtectRemind() {
        NotificationHelper.getInstance().sendMessageToMessageCenter(NotificationHelper.D21_SCENE_OUTSIDE_AIR_QUALITY, ResUtils.getString(R.string.hvac_air_quality_out_protect_title), ResUtils.getString(R.string.hvac_air_quality_out_protect_sub), ResUtils.getString(R.string.hvac_air_quality_out_protect_tts), ResUtils.getString(R.string.hvac_auto_air_protect_wake_words), ResUtils.getString(R.string.hvac_air_quality_out_protect_manual), ResUtils.getString(R.string.hvac_air_quality_out_btn_title), false, 0L, new IpcRouterService.ICallback() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$D2HvacSmartControl$Bo3giC6G025OLKRANnyHYUj0RyY
            @Override // com.xiaopeng.carcontrol.IpcRouterService.ICallback
            public final void onCallback(String str) {
                D2HvacSmartControl.this.lambda$airAirPollutedProtectRemind$0$D2HvacSmartControl(str);
            }
        });
    }

    public /* synthetic */ void lambda$airAirPollutedProtectRemind$0$D2HvacSmartControl(String content) {
        doAirPollutedProtect(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    void doAirPollutedProtect(boolean auto) {
        this.mHvacBaseVm.setHvacCirculationMode(1);
        SoundHelper.play(this.mHvacBaseVm.getAirAutoProtectSound(), false, false);
        NotificationHelper.getInstance().showToast(auto ? R.string.hvac_air_quality_out_protect_auto : R.string.hvac_air_quality_out_protect_manual);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacRapidCoolingChanged(boolean enable) {
        if (enable) {
            if (this.mHvacBaseVm.isHvacDeodorantEnable()) {
                this.mHvacBaseVm.setHvacDeodorantEnable(false);
            } else {
                memoryHvacSmartStatus();
            }
            controlRapidCoolingOpen();
            startHvacRapidCoolingCountDownDelay(180);
            return;
        }
        if (!this.mHvacBaseVm.isHvacDeodorantEnable() && this.mHvacBaseVm.isHvacPowerModeOn()) {
            recoverHvacRapidCooling();
        }
        stopHvacRapidCoolingCountDownDelay();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    void controlRapidCoolingOpen() {
        this.mRapidCoolingTime = FunctionModel.getInstance().getHvacRapidCoolingTime();
        if (!this.mHvacBaseVm.isHvacPowerModeOn()) {
            this.mNeedMemoryHvacStatus = true;
            this.mHvacBaseVm.setHvacPowerMode(true);
        } else if (this.mHvacBaseVm.isHvacAutoModeOn()) {
            this.mHvacBaseVm.setHvacAutoMode(false);
            this.mNeedMemoryHvacStatus = false;
        } else if (this.mHvacBaseVm.isHvacFrontDefrostOn()) {
            this.mNeedMemoryHvacStatus = true;
            this.mHvacBaseVm.setHvacFrontDefrost(false);
        } else {
            this.mNeedMemoryHvacStatus = false;
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$D2HvacSmartControl$ht1dlVF09AnFBIGbVBYBa7VuapM
            @Override // java.lang.Runnable
            public final void run() {
                D2HvacSmartControl.this.lambda$controlRapidCoolingOpen$1$D2HvacSmartControl();
            }
        }, 200L);
    }

    public /* synthetic */ void lambda$controlRapidCoolingOpen$1$D2HvacSmartControl() {
        String str = TAG;
        LogUtils.i(str, "controlRapidCoolingOpen delay isHvacRapidCoolingEnable:" + this.mFunctionModel.isHvacRapidCoolingEnable(), false);
        if (!this.mFunctionModel.isHvacRapidCoolingEnable()) {
            LogUtils.i(str, "isHvacRapidCoolingEnable is false", false);
            return;
        }
        if (this.mNeedMemoryHvacStatus) {
            memoryHvacSmartStatus();
        }
        this.mHvacBaseVm.setHvacWindSpeedLevel(6);
        this.mHvacBaseVm.setHvacAcMode(true);
        this.mHvacBaseVm.setHvacTempDriver(18.0f);
        this.mHvacBaseVm.setHvacTempPsn(18.0f);
        if (this.mHvacBaseVm.getHvacEconMode() != 0) {
            this.mHvacBaseVm.setHvacEconMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.OFF));
        }
        this.mHvacBaseVm.setHvacWindBlowModeGroup(HvacWindBlowMode.toHvacCmdD21(HvacWindBlowMode.Face));
        this.mHvacBaseVm.setHvacCirculationOut();
        if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
            this.mHvacBaseVm.setSeatVentLevel(3);
        }
        setSeatHeatOff();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void recoverHvacRapidCoolingDelay() {
        FunctionModel functionModel = FunctionModel.getInstance();
        float hvacSmartDrvTemp = functionModel.getHvacSmartDrvTemp();
        if (hvacSmartDrvTemp != this.mHvacBaseVm.getHvacDriverTemp()) {
            this.mHvacBaseVm.setHvacTempDriver(hvacSmartDrvTemp);
        }
        float hvacSmartPsnTemp = functionModel.getHvacSmartPsnTemp();
        if (hvacSmartPsnTemp != this.mHvacBaseVm.getHvacPsnTemp() && !this.mHvacBaseVm.isHvacDriverSyncMode()) {
            this.mHvacBaseVm.setHvacTempPsn(hvacSmartPsnTemp);
        }
        if (functionModel.getHvacSmartEconMode() != this.mHvacBaseVm.getHvacEconMode()) {
            this.mHvacBaseVm.setHvacEconMode(functionModel.getHvacSmartEconMode());
        }
        if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
            this.mHvacBaseVm.setSeatVentLevel(this.mFunctionModel.getHvacSmartDrvSeatVent());
        }
        if (this.mHvacBaseVm.getHvacAqsMode() == HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.ON)) {
            this.mHvacBaseVm.setHvacAqsMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.OFF));
        }
        if (functionModel.isHvacSmartAutoEnable() && this.mHvacBaseVm.getHvacWindSpeedLevel() == 6 && this.mHvacBaseVm.getHvacWindBlowMode() == 1 && this.mHvacBaseVm.isHvacAcModeOn() && !this.mHvacBaseVm.isHvacFrontDefrostOn() && !this.mHvacBaseVm.isHvacQualityPurgeEnable()) {
            if (this.mHvacBaseVm.isHvacAutoModeOn()) {
                return;
            }
            this.mHvacBaseVm.setHvacAutoMode(true);
        } else if (this.mHvacBaseVm.isHvacAutoModeOn() || this.mHvacBaseVm.isHvacFrontDefrostOn()) {
        } else {
            int hvacSmartAcPtcMode = functionModel.getHvacSmartAcPtcMode();
            if (this.mHvacBaseVm.getAcHeatNatureMode() != hvacSmartAcPtcMode) {
                this.mHvacBaseVm.setHvacAcMode(hvacSmartAcPtcMode == 1);
            }
            int hvacSmartFanSpeed = functionModel.getHvacSmartFanSpeed();
            if (hvacSmartFanSpeed != this.mHvacBaseVm.getHvacWindSpeedLevel()) {
                this.mHvacBaseVm.setHvacWindSpeedLevel(hvacSmartFanSpeed);
            }
            int hvacSmartBlowMode = functionModel.getHvacSmartBlowMode();
            if (hvacSmartBlowMode != 14) {
                this.mHvacBaseVm.setHvacWindBlowModeGroup(hvacSmartBlowMode);
            }
            if (this.mHvacBaseVm.isHvacQualityPurgeEnable()) {
                return;
            }
            HvacCirculationMode fromHvacState = HvacCirculationMode.fromHvacState(functionModel.getHvacSmartCirculation());
            HvacCirculationMode fromHvacState2 = HvacCirculationMode.fromHvacState(this.mHvacBaseVm.getHvacCirculationMode());
            if (HvacCirculationMode.Inner == fromHvacState && fromHvacState2 != fromHvacState) {
                this.mHvacBaseVm.setHvacCirculationInner();
            } else if (HvacCirculationMode.Outside != fromHvacState || fromHvacState2 == fromHvacState) {
            } else {
                this.mHvacBaseVm.setHvacCirculationOut();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onHvacDeodorantChanged(boolean enable) {
        if (enable) {
            if (this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
                this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
                if (this.mHvacBaseVm.isHvacPowerModeOn()) {
                    FunctionModel functionModel = FunctionModel.getInstance();
                    if (functionModel.getHvacSmartAcPtcMode() == 0) {
                        this.mHvacBaseVm.setHvacAcMode(false);
                    }
                    this.mHvacBaseVm.setHvacTempDriver(functionModel.getHvacSmartDrvTemp());
                    this.mHvacBaseVm.setHvacTempPsn(functionModel.getHvacSmartPsnTemp());
                    if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
                        this.mHvacBaseVm.setSeatVentLevel(this.mFunctionModel.getHvacSmartDrvSeatVent());
                    }
                }
            } else {
                memoryHvacSmartStatus();
            }
            controlDeodorantOpen();
            startHvacDeodorantCountdownDelay(180);
            return;
        }
        if (!this.mHvacBaseVm.isHvacRapidCoolingEnable() && this.mHvacBaseVm.isHvacPowerModeOn()) {
            recoverHvacDeodorant();
        }
        stopHvacDeodorantCountdownDelay();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    void controlDeodorantOpen() {
        this.mDeodorantTime = FunctionModel.getInstance().getHvacDeodorantTime();
        if (!this.mHvacBaseVm.isHvacPowerModeOn()) {
            this.mNeedMemoryHvacStatus = true;
            this.mHvacBaseVm.setHvacPowerMode(true);
        } else if (this.mHvacBaseVm.isHvacAutoModeOn()) {
            this.mHvacBaseVm.setHvacAutoMode(false);
            this.mNeedMemoryHvacStatus = false;
        } else if (this.mHvacBaseVm.isHvacFrontDefrostOn()) {
            this.mNeedMemoryHvacStatus = true;
            this.mHvacBaseVm.setHvacFrontDefrost(false);
        } else {
            this.mNeedMemoryHvacStatus = false;
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$D2HvacSmartControl$3-XsN9tle5jPOOX6AuwISMOg4FU
            @Override // java.lang.Runnable
            public final void run() {
                D2HvacSmartControl.this.lambda$controlDeodorantOpen$2$D2HvacSmartControl();
            }
        }, 200L);
    }

    public /* synthetic */ void lambda$controlDeodorantOpen$2$D2HvacSmartControl() {
        if (this.mNeedMemoryHvacStatus) {
            memoryHvacSmartStatus();
        }
        this.mHvacBaseVm.setHvacWindSpeedLevel(4);
        if (this.mHvacBaseVm.getHvacEconMode() != 0) {
            this.mHvacBaseVm.setHvacEconMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.OFF));
        }
        this.mHvacBaseVm.setHvacWindBlowModeGroup(HvacWindBlowMode.toHvacCmdD21(HvacWindBlowMode.Face));
        this.mHvacBaseVm.setHvacCirculationOut();
        if (!this.mWindowBaseVm.isWindowLockActive()) {
            this.mWindowBaseVm.controlWindowVent();
            return;
        }
        LogUtils.d(TAG, "WindowLockActive");
        NotificationHelper.getInstance().showToast(R.string.win_lock_is_active);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void recoverHvacDeodorantDelay() {
        FunctionModel functionModel = FunctionModel.getInstance();
        if (functionModel.getHvacSmartEconMode() != this.mHvacBaseVm.getHvacEconMode()) {
            this.mHvacBaseVm.setHvacEconMode(functionModel.getHvacSmartEconMode());
        }
        if (this.mHvacBaseVm.getHvacAqsMode() == HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.ON)) {
            this.mHvacBaseVm.setHvacAqsMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.OFF));
        }
        if (functionModel.isHvacSmartAutoEnable() && this.mHvacBaseVm.getHvacWindSpeedLevel() == 4 && this.mHvacBaseVm.getHvacWindBlowMode() == 1 && this.mHvacBaseVm.getHvacCirculationMode() == 2 && !this.mHvacBaseVm.isHvacFrontDefrostOn() && !this.mHvacBaseVm.isHvacQualityPurgeEnable()) {
            if (this.mHvacBaseVm.isHvacAutoModeOn()) {
                return;
            }
            this.mHvacBaseVm.setHvacAutoMode(true);
        } else if (this.mHvacBaseVm.isHvacAutoModeOn() || this.mHvacBaseVm.isHvacFrontDefrostOn()) {
        } else {
            if (functionModel.getHvacSmartFanSpeed() != this.mHvacBaseVm.getHvacWindSpeedLevel()) {
                this.mHvacBaseVm.setHvacWindSpeedLevel(functionModel.getHvacSmartFanSpeed());
            }
            int hvacSmartBlowMode = functionModel.getHvacSmartBlowMode();
            if (hvacSmartBlowMode != 14) {
                this.mHvacBaseVm.setHvacWindBlowModeGroup(hvacSmartBlowMode);
            }
            if (this.mHvacBaseVm.isHvacQualityPurgeEnable()) {
                return;
            }
            HvacCirculationMode fromHvacState = HvacCirculationMode.fromHvacState(functionModel.getHvacSmartCirculation());
            HvacCirculationMode fromHvacState2 = HvacCirculationMode.fromHvacState(this.mHvacBaseVm.getHvacCirculationMode());
            if (HvacCirculationMode.Inner == fromHvacState && fromHvacState2 != fromHvacState) {
                this.mHvacBaseVm.setHvacCirculationInner();
            }
            if (HvacCirculationMode.Outside != fromHvacState || fromHvacState2 == fromHvacState) {
                return;
            }
            this.mHvacBaseVm.setHvacCirculationOut();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onAirAualityPurgeChanged(boolean enable) {
        if (enable) {
            if (this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
                this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
            }
            if (this.mHvacBaseVm.isHvacDeodorantEnable()) {
                this.mHvacBaseVm.setHvacDeodorantEnable(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void memoryHvacSmartStatus() {
        this.mNeedMemoryHvacStatus = false;
        if (this.mHvacBaseVm.isHvacPowerModeOn()) {
            FunctionModel functionModel = FunctionModel.getInstance();
            functionModel.setHvacSmartAutoEnable(this.mHvacBaseVm.isHvacAutoModeOn());
            functionModel.setHvacSmartAcPtcMode(this.mHvacBaseVm.getAcHeatNatureMode());
            functionModel.setHvacSmartDrvTemp(this.mHvacBaseVm.getHvacDriverTemp());
            functionModel.setHvacSmartPsnTemp(this.mHvacBaseVm.getHvacPsnTemp());
            functionModel.setHvacSmartFanSpeed(this.mHvacBaseVm.getHvacWindSpeedLevel());
            functionModel.setHvacSmartEconMode(this.mHvacBaseVm.getHvacEconMode());
            functionModel.setHvacSmartBlowMode(this.mHvacBaseVm.getHvacWindBlowMode());
            functionModel.setHvacSmartCirculation(this.mHvacBaseVm.getHvacCirculationMode());
            if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
                functionModel.setHvacSmartDrvSeatVent(this.mHvacBaseVm.getSeatVentLevel());
            }
            LogUtils.d(TAG, "AUTO:" + this.mHvacBaseVm.isHvacAutoModeOn() + ",AC:" + this.mHvacBaseVm.getAcHeatNatureMode() + ",DrvTemp:" + this.mHvacBaseVm.getHvacDriverTemp() + ",PsnTemp:" + this.mHvacBaseVm.getHvacPsnTemp() + ",speed:" + this.mHvacBaseVm.getHvacWindSpeedLevel() + ",speed auto" + this.mHvacBaseVm.isHvacFanSpeedAutoEnable() + ",econ:" + this.mHvacBaseVm.getHvacEconMode() + ",blowMode:" + this.mHvacBaseVm.getHvacWindBlowMode() + ",blowMode auto" + SharedPreferenceUtil.isHvacBlowModeAuto() + ",circulation:" + this.mHvacBaseVm.getHvacCirculationMode() + ",circulation auto" + this.mHvacBaseVm.getHvacCirculationMode() + ",qualityPurge:" + this.mHvacBaseVm.isHvacQualityPurgeEnable(), false);
            return;
        }
        LogUtils.d(TAG, "memoryHvacSmartStatus power is off memory on power on", false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onWindBlowModeChanged(int mode) {
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown() && mode != 1) {
            FunctionModel.getInstance().setHvacSmartBlowMode(mode);
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
        }
        if (this.mHvacBaseVm.isHvacDeodorantEnable() && isDeodorantCountdown() && mode != 1) {
            FunctionModel.getInstance().setHvacSmartBlowMode(mode);
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    public void onWindSpeedLevelChange(int level) {
        if (level == 0) {
            resetSmartMode();
            return;
        }
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown() && level != 6) {
            FunctionModel.getInstance().setHvacSmartFanSpeed(level);
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
        }
        if (this.mHvacBaseVm.isHvacDeodorantEnable() && isDeodorantCountdown() && level != 4) {
            FunctionModel.getInstance().setHvacSmartFanSpeed(level);
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    void hvacRapidCoolingStatusCheck() {
        boolean z;
        String str = TAG;
        LogUtils.d(str, "hvacRapidCoolingStatusCheck");
        boolean z2 = true;
        if (this.mHvacBaseVm.isHvacAcModeOn()) {
            z = false;
        } else {
            this.mFunctionModel.setHvacSmartAcPtcMode(this.mHvacBaseVm.getAcHeatNatureMode());
            z = true;
        }
        if (this.mHvacBaseVm.getHvacWindSpeedLevel() != 6) {
            this.mFunctionModel.setHvacSmartFanSpeed(this.mHvacBaseVm.getHvacWindSpeedLevel());
            z = true;
        }
        if (this.mHvacBaseVm.getHvacDriverTemp() != 18.0f) {
            this.mFunctionModel.setHvacSmartDrvTemp(this.mHvacBaseVm.getHvacDriverTemp());
            z = true;
        }
        if (this.mHvacBaseVm.getHvacPsnTemp() != 18.0f) {
            this.mFunctionModel.setHvacSmartPsnTemp(this.mHvacBaseVm.getHvacPsnTemp());
        } else {
            z2 = z;
        }
        if (z2 || isSmartModeStatusError()) {
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
            LogUtils.i(str, "hvacRapidCoolingStatusCheck status error AcModeOn:" + this.mHvacBaseVm.isHvacAcModeOn() + ",WindSpeed:" + this.mHvacBaseVm.getHvacWindSpeedLevel() + ",DrvTemp:" + this.mHvacBaseVm.getHvacDriverTemp() + ",PsnTemp:" + this.mHvacBaseVm.getHvacPsnTemp(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    void hvacDeodorantStatusCheck() {
        boolean z;
        if (this.mHvacBaseVm.getHvacWindSpeedLevel() != 4) {
            this.mFunctionModel.setHvacSmartFanSpeed(this.mHvacBaseVm.getHvacWindSpeedLevel());
            z = true;
        } else {
            z = false;
        }
        if (z || isSmartModeStatusError() || HvacCirculationMode.Outside != HvacCirculationMode.fromHvacState(this.mHvacBaseVm.getHvacCirculationMode())) {
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
            LogUtils.i(TAG, "hvacDeodorantStatusCheck status error WindSpeed:" + this.mHvacBaseVm.getHvacWindSpeedLevel(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl
    boolean isSmartModeStatusError() {
        boolean z;
        LogUtils.d(TAG, "PowerMode:" + this.mHvacBaseVm.isHvacPowerModeOn() + ",AutoMode:" + this.mHvacBaseVm.isHvacAutoModeOn() + ",FrontDefrost:" + this.mHvacBaseVm.isHvacFrontDefrostOn() + ",QualityPurge:" + this.mHvacBaseVm.isHvacQualityPurgeEnable() + ",EconMode:" + this.mHvacBaseVm.getHvacEconMode() + ",WindBlowMode:" + this.mHvacBaseVm.getHvacWindBlowMode() + ",CirculationMode:" + this.mHvacBaseVm.getHvacCirculationMode(), false);
        if (HvacWindBlowMode.Face != HvacWindBlowMode.fromHvacState(this.mHvacBaseVm.getHvacWindBlowMode())) {
            this.mFunctionModel.setHvacSmartBlowMode(this.mHvacBaseVm.getHvacWindBlowMode());
            z = true;
        } else {
            z = false;
        }
        return z || !this.mHvacBaseVm.isHvacPowerModeOn() || this.mHvacBaseVm.isHvacAutoModeOn() || this.mHvacBaseVm.isHvacFrontDefrostOn() || this.mHvacBaseVm.isHvacQualityPurgeEnable() || 1 == this.mHvacBaseVm.getHvacEconMode();
    }
}
