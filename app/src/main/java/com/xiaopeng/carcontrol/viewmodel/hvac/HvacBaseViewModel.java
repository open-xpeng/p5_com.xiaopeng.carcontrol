package com.xiaopeng.carcontrol.viewmodel.hvac;

import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.IHvacController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public abstract class HvacBaseViewModel implements IHvacViewModel, IHvacController.Callback {
    private static final long SMART_MODE_PROTECT_TIME = 1200;
    static final String TAG = "HvacViewModel";
    final IBcmController.Callback mBcmCallback = new IBcmController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel.1
        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onMirrorHeatModeChanged(boolean enabled) {
            HvacBaseViewModel.this.handleMirrorHeatChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onFrontMirrorHeatModeChanged(boolean enabled) {
            HvacBaseViewModel.this.handleFrontMirrorHeatChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDriverSeatHeatLevelChanged(int level) {
            HvacBaseViewModel.this.handleSeatHeatChanged(level);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_HEAT_LEVEL_INT, Integer.valueOf(level));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDriverSeatBlowLevelChanged(int level) {
            HvacBaseViewModel.this.handleSeatVentChanged(level);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_VENT_LEVEL_INT, Integer.valueOf(level));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onPsnSeatHeatLevelChanged(int level) {
            HvacBaseViewModel.this.handlePsnSeatHeatChanged(level);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HVAC_PSN_SEAT_HEAT_LEVEL_INT, Integer.valueOf(level));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRRSeatHeatLevelChanged(int level) {
            HvacBaseViewModel.this.handleRRSeatHeatChanged(level);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRLSeatHeatLevelChanged(int level) {
            HvacBaseViewModel.this.handleRLSeatHeatChanged(level);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onPsnSeatVentLevelChanged(int level) {
            HvacBaseViewModel.this.handlePsnSeatVentChanged(level);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HVAC_PSN_SEAT_VENT_LEVEL_INT, Integer.valueOf(level));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onSteerHeatLevelChanged(int level) {
            HvacBaseViewModel.this.handleSteerHeatChanged(level);
        }
    };
    IBcmController mBcmController;
    IHvacController mHvacController;
    IMcuController mMcuController;

    private float matchTemp(float temp) {
        if (temp < 18.0f) {
            temp = 18.0f;
        }
        if (temp > 32.0f) {
            return 32.0f;
        }
        return temp;
    }

    protected abstract void handleAirProtectModeChanged(int mode);

    protected abstract void handleFrontMirrorHeatChanged(boolean enabled);

    protected abstract void handleMirrorHeatChanged(boolean enabled);

    protected abstract void handlePsnSeatHeatChanged(int level);

    protected abstract void handlePsnSeatVentChanged(int level);

    protected abstract void handleRLSeatHeatChanged(int level);

    protected abstract void handleRRSeatHeatChanged(int level);

    protected abstract void handleSeatHeatChanged(int level);

    protected abstract void handleSeatVentChanged(int level);

    protected abstract void handleSmartHvacModeChanged(boolean enabled);

    protected abstract void handleSteerHeatChanged(int level);

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAirDistributionAutoChanged(boolean enable) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HvacBaseViewModel() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mHvacController = (IHvacController) carClientWrapper.getController("hvac");
        this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mMcuController = (IMcuController) carClientWrapper.getController(CarClientWrapper.XP_MCU_SERVICE);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacPowerMode(boolean enable) {
        this.mHvacController.setHvacPowerMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacPowerModeOn() {
        return this.mHvacController.isHvacPowerModeOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacAutoMode(boolean enable) {
        this.mHvacController.setHvacAutoMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacAutoModeOn() {
        return this.mHvacController.isHvacAutoModeOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setAcHeatNatureMode(int mode) {
        this.mHvacController.setAcHeatNatureMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getAcHeatNatureMode() {
        return this.mHvacController.getAcHeatNatureMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacAcMode(boolean enable) {
        this.mHvacController.setHvacAcMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacAcModeOn() {
        return this.mHvacController.isHvacAcModeOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacHeatMode(boolean enable) {
        int acHeatNatureMode = getAcHeatNatureMode();
        if (enable) {
            if (3 != acHeatNatureMode) {
                setAcHeatNatureMode(2);
            }
        } else if (3 == acHeatNatureMode) {
            setAcHeatNatureMode(3);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacNatureMode(boolean enable) {
        int acHeatNatureMode = getAcHeatNatureMode();
        if (enable) {
            if (5 != acHeatNatureMode) {
                setAcHeatNatureMode(3);
            }
        } else if (5 == acHeatNatureMode && isHvacPowerModeOn()) {
            setHvacPowerMode(false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacTempDriver(float temperature) {
        if (temperature < 18.0f) {
            temperature = 18.0f;
        }
        if (temperature > 32.0f) {
            temperature = 32.0f;
        }
        this.mHvacController.setHvacTempDriver(temperature);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public float getHvacDriverTemp() {
        return matchTemp(this.mHvacController.getHvacTempDriver());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacTempDriverStep(boolean isUp) {
        this.mHvacController.setHvacTempDriverStep(isUp);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacDriverSyncMode(boolean enable) {
        this.mHvacController.setHvacDriverSyncMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacDriverSyncMode() {
        return this.mHvacController.getHvacDriverSyncMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacTempPsn(float temperature) {
        if (temperature < 18.0f) {
            temperature = 18.0f;
        }
        if (temperature > 32.0f) {
            temperature = 32.0f;
        }
        this.mHvacController.setHvacTempPsn(temperature);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public float getHvacPsnTemp() {
        return matchTemp(this.mHvacController.getHvacTempPsn());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacTempPsnStep(boolean isUp) {
        this.mHvacController.setHvacTempPsnStep(isUp);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacPsnSyncMode(boolean enable) {
        this.mHvacController.setHvacPsnSyncMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacPsnSyncMode() {
        return this.mHvacController.getHvacPsnSyncMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public float getHvacInnerTemp() {
        return this.mHvacController.getHvacInnerTemp();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowMode(int mode) {
        this.mHvacController.setHvacWindBlowMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearWindBlowMode(int mode) {
        this.mHvacController.setHvacRearWindBlowMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRearVoiceWindBlowMode(int mode) {
        this.mHvacController.setHvacRearVoiceWindBlowMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacRearWindBlowMode() {
        return this.mHvacController.getHvacRearWindBlowMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowModeGroup(int mode) {
        this.mHvacController.setHvacWindBlowModeGroup(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacWindBlowMode() {
        return this.mHvacController.getHvacWindBlowMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindSpeedLevel(int level) {
        if (level < 1) {
            level = 1;
        } else if (level > getFanMaxLevel()) {
            level = getFanMaxLevel();
        }
        this.mHvacController.setHvacWindSpeedLevel(level);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindSpeedMin() {
        setHvacWindSpeedLevel(1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindSpeedUp() {
        this.mHvacController.setHvacWindSpeedStep(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindSpeedDown() {
        this.mHvacController.setHvacWindSpeedStep(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacWindSpeedLevel() {
        return this.mHvacController.getHvacWindSpeedLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacCirculationMode(int mode) {
        this.mHvacController.setHvacCirculationMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacCirculationMode() {
        return this.mHvacController.getHvacCirculationMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacFrontDefrost(boolean enable) {
        this.mHvacController.setHvacFrontDefrost(enable);
        this.mBcmController.setFrontMirrorHeat(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacFrontDefrostOnly(boolean enable) {
        this.mHvacController.setHvacFrontDefrost(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacFrontDefrostOn() {
        return this.mHvacController.isHvacFrontDefrostOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setMirrorHeatEnable(boolean enable) {
        this.mBcmController.setMirrorHeat(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isMirrorHeatEnabled() {
        return this.mBcmController.isMirrorHeatEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setFrontMirrorHeatEnable(boolean enable) {
        this.mBcmController.setFrontMirrorHeat(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isFrontMirrorHeatEnabled() {
        return this.mBcmController.getFrontMirrorHeat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setSeatHeatLevel(int level) {
        this.mBcmController.setSeatHeatLevel(level, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setSeatHeatLevel(int level, boolean storeEnable) {
        this.mBcmController.setSeatHeatLevel(level, storeEnable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getSeatHeatLevel() {
        return this.mBcmController.getSeatHeatLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setSeatVentLevel(int level) {
        this.mBcmController.setSeatVentLevel(level, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setSeatVentLevel(int level, boolean storeEnable) {
        this.mBcmController.setSeatVentLevel(level, storeEnable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getPsnSeatHeatLevel() {
        return this.mBcmController.getPsnSeatHeatLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setPsnSeatHeatLevel(int level) {
        this.mBcmController.setPsnSeatHeatLevel(level);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getPsnSeatVentLevel() {
        return this.mBcmController.getPsnSeatVentLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setPsnSeatVentLevel(int level) {
        this.mBcmController.setPsnSeatVentLevel(level);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getSteerHeatLevel() {
        return this.mBcmController.getSteerHeatLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setSteerHeatLevel(int level) {
        this.mBcmController.setSteerHeatLevel(level, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setSteerHeatLevel(int level, boolean storeEnable) {
        this.mBcmController.setSteerHeatLevel(level, storeEnable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getRLSeatHeatLevel() {
        return this.mBcmController.getRLSeatHeatLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setRLSeatHeatLevel(int level) {
        this.mBcmController.setRLSeatHeatLevel(level);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getRRSeatHeatLevel() {
        return this.mBcmController.getRRSeatHeatLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setRRSeatHeatLevel(int level) {
        this.mBcmController.setRRSeatHeatLevel(level);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getSeatVentLevel() {
        return this.mBcmController.getSeatVentLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacInnerPM25() {
        return this.mHvacController.getHvacInnerPm25();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public float getHvacExternalTemp() {
        return this.mHvacController.getHvacExternalTemp();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEconMode(int status) {
        this.mHvacController.setHvacEconMode(status);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEconMode() {
        return this.mHvacController.getHvacEconMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacAqsMode(int status) {
        this.mHvacController.setHvacAqsMode(status);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacAqsMode() {
        return this.mHvacController.getHvacAqsMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacAqsLevel(int level) {
        this.mHvacController.setHvacAqsLevel(level);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacAqsLevel() {
        return this.mHvacController.getHvacAqsLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEavDriverWindMode(int mode) {
        this.mHvacController.setHvacEavDrvWindMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEavDriverWindMode() {
        return this.mHvacController.getHvacEavDrvWindMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEavPsnWindMode(int mode) {
        this.mHvacController.setHvacEavPsnWindMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEavPsnWindMode() {
        return this.mHvacController.getHvacEavPsnWindMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVDriverLeftHPos(int pos) {
        if (isHvacVentOpen(0)) {
            this.mHvacController.setHvacEavDrvLeftHPos(pos);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVDriverLeftHPosDirect(int pos) {
        this.mHvacController.setHvacEavDrvLeftHPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEAVDriverLeftHPos() {
        return this.mHvacController.getHvacEavDrvLeftHPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVDriverLeftVPos(int pos) {
        LogUtils.d(TAG, "setHvacEAVDriverLeftVPos " + pos);
        if (isHvacVentOpen(0)) {
            this.mHvacController.setHvacEavDrvLeftVPos(pos);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVDriverLeftVPosDirect(int pos) {
        this.mHvacController.setHvacEavDrvLeftVPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEAVDriverLeftVPos() {
        return this.mHvacController.getHvacEavDrvLeftVPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVDriverRightHPos(int pos) {
        if (isHvacVentOpen(1)) {
            this.mHvacController.setHvacEavDrvRightHPos(pos);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVDriverRightHPosDirect(int pos) {
        this.mHvacController.setHvacEavDrvRightHPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEAVDriverRightHPos() {
        return this.mHvacController.getHvacEavDrvRightHPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVDriverRightVPos(int pos) {
        if (isHvacVentOpen(1)) {
            this.mHvacController.setHvacEavDrvRightVPos(pos);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVDriverRightVPosDirect(int pos) {
        this.mHvacController.setHvacEavDrvRightVPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEAVDriverRightVPos() {
        return this.mHvacController.getHvacEavDrvRightVPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVPsnLeftHPos(int pos) {
        if (isHvacVentOpen(2)) {
            this.mHvacController.setHvacEavPsnLeftHPos(pos);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVPsnLeftHPosDirect(int pos) {
        this.mHvacController.setHvacEavPsnLeftHPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEAVPsnLeftHPos() {
        return this.mHvacController.getHvacEavPsnLeftHPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVPsnLeftVPos(int pos) {
        if (isHvacVentOpen(2)) {
            this.mHvacController.setHvacEavPsnLeftVPos(pos);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVPsnLeftVPosDirect(int pos) {
        HvacSmartControl.HVAC_SINGLE_MODE_ENABLE = false;
        this.mHvacController.setHvacEavPsnLeftVPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEAVPsnLeftVPos() {
        return this.mHvacController.getHvacEavPsnLeftVPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVPsnRightHPos(int pos) {
        if (isHvacVentOpen(3)) {
            this.mHvacController.setHvacEavPsnRightHPos(pos);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVPsnRightHPosDirect(int pos) {
        this.mHvacController.setHvacEavPsnRightHPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEAVPsnRightHPos() {
        return this.mHvacController.getHvacEavPsnRightHPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVPsnRightVPos(int pos) {
        if (isHvacVentOpen(3)) {
            this.mHvacController.setHvacEavPsnRightVPos(pos);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEAVPsnRightVPosDirect(int pos) {
        HvacSmartControl.HVAC_SINGLE_MODE_ENABLE = false;
        this.mHvacController.setHvacEavPsnRightVPos(pos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEAVPsnRightVPos() {
        return this.mHvacController.getHvacEavPsnRightVPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacCirculationTime(int time) {
        this.mHvacController.setHvacCirculationTime(time);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacCirculationTime() {
        return this.mHvacController.getHvacCirculationTime();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacEavSweepMode(int status) {
        this.mHvacController.setHvacEavSweepMode(status);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacEavSweepMode() {
        return this.mHvacController.getHvacEavSweepMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacSweepEnable() {
        return getHvacEavSweepMode() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacIonizerMode() {
        return this.mHvacController.getHvacIonizerMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacSelfDryEnable(boolean enable) {
        this.mHvacController.setHvacSelfDryEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacSelfDryOn() {
        return this.mHvacController.isHvacSelfDryOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isAutoDefogWorkSt() {
        return this.mHvacController.isAutoDefogWorkSt();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setAutoDefogSwitch(boolean enable) {
        this.mHvacController.setAutoDefogSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isAutoDefogSwitch() {
        return this.mHvacController.isAutoDefogSwitch();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public HvacAirAutoProtect getAirAutoProtectMode() {
        return HvacAirAutoProtect.fromValue(this.mHvacController.getAirAutoProtectMode());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setAirAutoProtectedMode(HvacAirAutoProtect mode) {
        this.mHvacController.setAirAutoProtectedMode(mode.ordinal());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public String getAirAutoProtectSound() {
        return this.mHvacController.getAirAutoProtectSound();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setAirAutoProtectSound(String soundType) {
        this.mHvacController.setAirAutoProtectSound(soundType);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSmartHvacEnabled() {
        return this.mHvacController.isSmartHvacEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setSmartHvacEnable(boolean enable) {
        this.mHvacController.setSmartHvacEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacAirProtectModeChanged(int mode) {
        handleAirProtectModeChanged(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
    public void onHvacSmartSwChanged(boolean enabled) {
        handleSmartHvacModeChanged(enabled);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacRapidCoolingEnable() {
        return this.mHvacController.isHvacRapidCoolingEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRapidCoolingEnable(boolean enable) {
        LogUtils.d(TAG, "setHvacRapidCoolingEnable:" + enable);
        this.mHvacController.setHvacRapidCoolingEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRapidCoolingCountdownTime(int time) {
        this.mHvacController.setHvacRapidCoolingCountDownTimer(time);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacRapidCoolingCountdownTime() {
        return this.mHvacController.getHvacRapidCoolingCountDownTimer();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacDeodorantEnable() {
        return this.mHvacController.isHvacDeodorantEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacDeodorantEnable(boolean enable) {
        this.mHvacController.setHvacDeodorantEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacDeodorantCountdownTime(int time) {
        this.mHvacController.setHvacDeodorantCountDownTimer(time);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacDeodorantCountdownTime() {
        return this.mHvacController.getHvacDeodorantCountDownTimer();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacRapidHeatEnable() {
        return this.mHvacController.isHvacRapidHeatEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRapidHeatEnable(boolean enable) {
        this.mHvacController.setHvacRapidHeatEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacRapidHeatCountdownTime() {
        return this.mHvacController.getHvacRapidHeatCountDownTimer();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacRapidHeatCountdownTime(int time) {
        this.mHvacController.setHvacRapidHeatCountDownTimer(time);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacVentOpen(int position) {
        boolean z = true;
        if (position == 0 ? getHvacEAVDriverLeftVPos() == 14 || getHvacEAVDriverLeftVPos() == 15 : position == 1 ? getHvacEAVDriverRightVPos() == 14 || getHvacEAVDriverRightVPos() == 15 : position == 2 ? getHvacEAVPsnLeftVPos() == 14 || getHvacEAVPsnLeftVPos() == 15 : position == 3 && (getHvacEAVPsnRightVPos() == 14 || getHvacEAVPsnRightVPos() == 15)) {
            z = false;
        }
        LogUtils.i(TAG, "isHvacVentOpen position:" + position + " isOpen:" + z, false);
        return z;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getHvacVentClosedCount() {
        int i = isHvacVentOpen(0) ? 0 : 1;
        if (!isHvacVentOpen(1)) {
            i++;
        }
        if (!isHvacVentOpen(2)) {
            i++;
        }
        return !isHvacVentOpen(3) ? i + 1 : i;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacVentStatus(int position, boolean status, boolean isManual) {
        if (status) {
            if (isHvacVentOpen(position)) {
                LogUtils.i(TAG, "setHvacVentStatus isHvacVentOpen", false);
                return;
            } else if (position == 0) {
                this.mHvacController.setHvacEavDrvLeftVPos(13);
            } else if (position == 1) {
                this.mHvacController.setHvacEavDrvRightVPos(13);
            } else if (position == 2) {
                if (isManual) {
                    HvacSmartControl.HVAC_SINGLE_MODE_ENABLE = false;
                }
                this.mHvacController.setHvacEavPsnLeftVPos(13);
            } else if (position == 3) {
                if (isManual) {
                    HvacSmartControl.HVAC_SINGLE_MODE_ENABLE = false;
                }
                this.mHvacController.setHvacEavPsnRightVPos(13);
            }
        } else if (getHvacVentClosedCount() >= 3) {
            NotificationHelper.getInstance().showToast(R.string.hvac_vent_close_notice, true, "hvac");
            return;
        } else if (position == 0) {
            this.mHvacController.setHvacEavDrvLeftVPos(14);
        } else if (position == 1) {
            this.mHvacController.setHvacEavDrvRightVPos(14);
        } else if (position == 2) {
            if (isManual) {
                HvacSmartControl.HVAC_SINGLE_MODE_ENABLE = false;
            }
            this.mHvacController.setHvacEavPsnLeftVPos(14);
        } else if (position == 3) {
            if (isManual) {
                HvacSmartControl.HVAC_SINGLE_MODE_ENABLE = false;
            }
            this.mHvacController.setHvacEavPsnRightVPos(14);
        }
        LogUtils.i(TAG, "setHvacVentStatus end", false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacQualityPurgeEnable() {
        return this.mHvacController.isHvacQualityPurgeEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacQualityPurgeMode(boolean enable) {
        this.mHvacController.setHvacQualityPurgeMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getWindModeColor() {
        return this.mHvacController.getHvacWindModEconLour();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getOutsidePm25() {
        return this.mHvacController.getOutsidePm25();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacAirDistributionAutoEnable() {
        return this.mHvacController.isHvacAirDistributionAutoEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacFanSpeedAutoEnable() {
        return this.mHvacController.isHvacFanSpeedAutoEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacAirIntakeAutoEnable() {
        return this.mHvacController.isHvacAirIntakeAutoEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacSingleMode(boolean enable) {
        this.mHvacController.setHvacSingleMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isHvacSingleMode() {
        return this.mHvacController.isHvacSingleModeEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSmartModeInProtected() {
        FunctionModel functionModel = FunctionModel.getInstance();
        long currentTimeMillis = System.currentTimeMillis() - functionModel.getHvacSmartModeClickTime();
        return (currentTimeMillis <= SMART_MODE_PROTECT_TIME && currentTimeMillis >= 0) || (System.currentTimeMillis() - functionModel.getHvacPurgeClickTime() <= SMART_MODE_PROTECT_TIME && System.currentTimeMillis() - functionModel.getHvacPurgeClickTime() >= 0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public int getFanMaxLevel() {
        return this.mHvacController.getWindMaxLevel();
    }

    public boolean isLocalIgOn() {
        return this.mMcuController.getIgStatusFromMcu() == 1;
    }
}
