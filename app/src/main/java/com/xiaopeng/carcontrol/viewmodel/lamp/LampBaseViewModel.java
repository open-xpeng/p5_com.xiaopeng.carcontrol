package com.xiaopeng.carcontrol.viewmodel.lamp;

import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;

/* loaded from: classes2.dex */
public abstract class LampBaseViewModel implements ILampViewModel {
    private static final String TAG = "LampBaseViewModel";
    private boolean mSetFogLaterFlag;
    private final Object mDomeLightStLock = new Object();
    private final Runnable mDomeLightSwTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LampBaseViewModel$rz0NFy7LTSVKgkuJGFfFIB0L9-o
        @Override // java.lang.Runnable
        public final void run() {
            LampBaseViewModel.this.lambda$new$0$LampBaseViewModel();
        }
    };
    final IBcmController.Callback mLampCallBack = new IBcmController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel.1
        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onHeadLampModeChanged(int state) {
            LampBaseViewModel.this.handleHeadLampModeChanged(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLampHeightLevelChanged(int level) {
            LampBaseViewModel.this.handleLampHeightLevelChanged(level);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAutoLampHeightChanged(boolean auto) {
            LampBaseViewModel.this.handleAutoLampHeightChanged(auto);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLightMeHomeChanged(boolean enabled) {
            LampBaseViewModel.this.handleLightMeHomeChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLightMeHomeTimeChanged(int timeCfg) {
            LampBaseViewModel.this.handleLightMeHomeTime(timeCfg);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRearFogLampChanged(boolean enabled) {
            LampBaseViewModel.this.handleRearFogLampChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLowBeamLampStateChanged(boolean isOn) {
            LampBaseViewModel.this.handleLowBeamLampChanged(isOn);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLowBeamOffConfirmStateChanged(boolean isActivated) {
            LampBaseViewModel.this.handleLowBeamOffConfirmStateChanged(isActivated);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onPositionLampStateChanged(boolean isOn) {
            LampBaseViewModel.this.handlePositionLampChanged(isOn);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onHighBeamLampStateChanged(boolean isOn) {
            LampBaseViewModel.this.handleHighBeamLampChanged(isOn);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onTurnLampStateChanged(int[] state) {
            LampBaseViewModel.this.handleTurnLampChanged(TurnLampState.fromBcmStatus(state));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDomeLightStateChanged(int state) {
            LampBaseViewModel.this.handleDomeLightState(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onParkLightFmbChanged(int cfg) {
            LampBaseViewModel.this.handleParkLightRelatedFMBLightState(cfg);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onParkingLampStatesChanged(int[] states) {
            LampBaseViewModel.this.handleParkingLampStatesChanged(states);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDomeLightStChanged(int lightType, int status) {
            synchronized (LampBaseViewModel.this.mDomeLightStLock) {
                LogUtils.i(LampBaseViewModel.TAG, String.format("onDomeLightStChanged pos: %1d, status: %2d", Integer.valueOf(lightType), Integer.valueOf(status)));
                ThreadUtils.removeRunnable(LampBaseViewModel.this.mDomeLightSwTask);
                if (status == 0) {
                    ThreadUtils.postDelayed(0, LampBaseViewModel.this.mDomeLightSwTask, 100L);
                } else {
                    LampBaseViewModel.this.handleDomeLightState(true);
                }
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDomeLightBrightChanged(int brightness) {
            LampBaseViewModel lampBaseViewModel = LampBaseViewModel.this;
            lampBaseViewModel.handleDomeLightBrightness(lampBaseViewModel.parseDomeLightBrightness(brightness));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDaytimeRunningLightChanged(int state) {
            LampBaseViewModel.this.handleDaytimeRunningLightChanged(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRearLogoLightSwChanged(boolean enable) {
            LampBaseViewModel.this.handleRearLogoLightSwChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCarpetLightWelcomeChanged(boolean enable) {
            LampBaseViewModel.this.handleCarpetLightWelcomeStateChange(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onPollingLightWelcomeChanged(boolean enable) {
            LampBaseViewModel.this.handlePollingWelcomeStateChange(enable);
        }
    };
    IBcmController mBcmController = (IBcmController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_BCM_SERVICE);

    abstract void handleAutoLampHeightChanged(boolean auto);

    protected abstract void handleCarpetLightWelcomeStateChange(boolean enable);

    protected abstract void handleDaytimeRunningLightChanged(int state);

    abstract void handleDomeLightBrightness(int brightness);

    abstract void handleDomeLightState(int state);

    abstract void handleDomeLightState(boolean lightOnOff);

    abstract void handleHeadLampModeChanged(int state);

    abstract void handleHighBeamLampChanged(boolean isOn);

    abstract void handleLampHeightLevelChanged(int level);

    abstract void handleLightMeHomeChanged(boolean enabled);

    abstract void handleLightMeHomeTime(int timeCfg);

    abstract void handleLowBeamOffConfirmStateChanged(boolean isActivated);

    abstract void handleParkLightRelatedFMBLightState(int state);

    abstract void handleParkingLampStatesChanged(int[] states);

    protected abstract void handlePollingWelcomeStateChange(boolean enable);

    abstract void handlePositionLampChanged(boolean isOn);

    abstract void handleRearFogLampChanged(boolean enabled);

    protected abstract void handleRearLogoLightSwChanged(boolean enable);

    abstract void handleTurnLampChanged(TurnLampState state);

    int parseDomeLightBrightness(int brightness) {
        char c = 3;
        if (brightness >= 5) {
            c = 5;
        } else if (brightness < 3) {
            c = 1;
        }
        if (c != 1) {
            return c != 5 ? 1 : 2;
        }
        return 0;
    }

    public /* synthetic */ void lambda$new$0$LampBaseViewModel() {
        boolean domeLightSw = getDomeLightSw(4);
        LogUtils.i(TAG, "onFullDomeLightStChanged convertSt: " + String.valueOf(domeLightSw));
        handleDomeLightState(domeLightSw);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean getParkLampState() {
        return this.mBcmController.getParkLamp();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean isParkLampIncludeFmB() {
        return this.mBcmController.isParkLampIncludeFmB();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setParkLampIncludeFmB(boolean enable) {
        this.mBcmController.setParkLampIncludeFmB(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public int[] getParkingLampStates() {
        return this.mBcmController.getParkingLampStates();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean getLowBeamState() {
        return this.mBcmController.getLowBeamLamp();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean getHighBeamState() {
        return this.mBcmController.getHighBeamLamp();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public int getHeadLampGroup() {
        return this.mBcmController.getHeadLampState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public int getHeadLampGroupSp() {
        return this.mBcmController.getHeadLampStateSp();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setHeadLampGroup(int groupId) {
        this.mBcmController.setHeadLampState(groupId, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setHeadLampGroup(int groupId, boolean needSave) {
        this.mBcmController.setHeadLampState(groupId, needSave);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setLowBeamOffConfirmSt(boolean confirmSt) {
        this.mBcmController.setLowBeamOffConfirmSt(confirmSt ? 1 : 2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public int getLampHeightLevel() {
        return this.mBcmController.getLampHeightLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setLampHeightLevel(int height) {
        this.mBcmController.setLampHeightLevel(height);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setLampHeightLevel(int height, boolean needSave) {
        this.mBcmController.setLampHeightLevel(height, needSave);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean isAutoLampHeight() {
        return this.mBcmController.isAutoLampHeight();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setAutoLampHeight(boolean auto) {
        this.mBcmController.setAutoLampHeight(auto);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setAutoLampHeight(boolean auto, boolean needSave) {
        this.mBcmController.setAutoLampHeight(auto, needSave);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setRearFogLamp(boolean enable) {
        if (App.isMainProcess()) {
            try {
                LogUtils.i(TAG, "setRearFogLamp: " + enable, false);
                if (enable && !this.mBcmController.getLowBeamLamp()) {
                    this.mSetFogLaterFlag = true;
                    setHeadLampGroup(2);
                    return;
                }
                this.mBcmController.setRearFogLamp(enable);
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, (String) null, e);
                return;
            }
        }
        CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.REAR_FOG_SW, enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean getRearFogLampState() {
        return this.mBcmController.getRearFogLamp();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public TurnLampState getTurnLampState() {
        return TurnLampState.fromBcmStatus(this.mBcmController.getTurnLampState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean isTurnLampOn() {
        return getTurnLampState() != TurnLampState.Off;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setLightMeHome(boolean enable) {
        this.mBcmController.setLightMeHome(enable, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean isLightMeHomeEnable() {
        return this.mBcmController.getLightMeHome();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setLightMeHomeTime(int timeCfg) {
        this.mBcmController.setLightMeHomeTime(timeCfg, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public int getLightMeHomeTime() {
        return this.mBcmController.getLightMeHomeTime();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean isLedDrlEnabled() {
        return this.mBcmController.isLedDrlEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setLedDrlEnable(boolean enable) {
        this.mBcmController.setLedDrlEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public int getDomeLightState() {
        return this.mBcmController.getDomeLightState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setDomeLight(int value) {
        this.mBcmController.setDomeLight(value);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0048, code lost:
        if (r7.mBcmController.getDomeLightStatus(8) == 0) goto L19;
     */
    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getDomeLightSw(int r8) {
        /*
            r7 = this;
            r0 = 8
            r1 = 7
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 0
            r6 = 4
            if (r8 != r6) goto L4b
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r5)
            if (r8 != 0) goto L2d
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r4)
            if (r8 != 0) goto L2d
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r3)
            if (r8 != 0) goto L2d
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r2)
            if (r8 == 0) goto L2b
            goto L2d
        L2b:
            r8 = r5
            goto L2e
        L2d:
            r8 = r4
        L2e:
            com.xiaopeng.carcontrol.config.CarBaseConfig r2 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r2 = r2.isSupportDomeLightThirdRow()
            if (r2 == 0) goto L9e
            if (r8 != 0) goto L60
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r1)
            if (r8 != 0) goto L60
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r0)
            if (r8 == 0) goto L5f
            goto L60
        L4b:
            r6 = 5
            if (r8 != r6) goto L62
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r5)
            if (r8 != 0) goto L60
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r4)
            if (r8 == 0) goto L5f
            goto L60
        L5f:
            r4 = r5
        L60:
            r8 = r4
            goto L9e
        L62:
            r6 = 6
            if (r8 != r6) goto L76
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r3)
            if (r8 != 0) goto L60
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r2)
            if (r8 == 0) goto L5f
            goto L60
        L76:
            com.xiaopeng.carcontrol.config.CarBaseConfig r2 = com.xiaopeng.carcontrol.config.CarBaseConfig.getInstance()
            boolean r2 = r2.isSupportDomeLightThirdRow()
            if (r2 == 0) goto L95
            r2 = 9
            if (r8 != r2) goto L95
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r1)
            if (r8 != 0) goto L60
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r8 = r7.mBcmController
            int r8 = r8.getDomeLightStatus(r0)
            if (r8 == 0) goto L5f
            goto L60
        L95:
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r0 = r7.mBcmController
            int r8 = r0.getDomeLightStatus(r8)
            if (r8 == 0) goto L5f
            goto L60
        L9e:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel.getDomeLightSw(int):boolean");
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setDomeLightSw(int lightType, boolean enable) {
        if (lightType == 4) {
            this.mBcmController.setDomeLightSw(0, enable);
            this.mBcmController.setDomeLightSw(1, enable);
            this.mBcmController.setDomeLightSw(2, enable);
            this.mBcmController.setDomeLightSw(3, enable);
            if (CarBaseConfig.getInstance().isSupportDomeLightThirdRow()) {
                this.mBcmController.setDomeLightSw(7, enable);
                this.mBcmController.setDomeLightSw(8, enable);
            }
        } else if (lightType == 5) {
            this.mBcmController.setDomeLightSw(0, enable);
            this.mBcmController.setDomeLightSw(1, enable);
        } else if (lightType == 6) {
            this.mBcmController.setDomeLightSw(2, enable);
            this.mBcmController.setDomeLightSw(3, enable);
        } else if (CarBaseConfig.getInstance().isSupportDomeLightThirdRow() && lightType == 9) {
            this.mBcmController.setDomeLightSw(7, enable);
            this.mBcmController.setDomeLightSw(8, enable);
        } else {
            this.mBcmController.setDomeLightSw(lightType, enable);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public int getDomeLightBrightness() {
        return parseDomeLightBrightness(this.mBcmController.getDomeLightBright());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setDomeLightBrightness(int brightness) {
        setDomeLightBrightness(brightness, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setDomeLightBrightness(int brightness, boolean needSave) {
        if (brightness == 1) {
            this.mBcmController.setDomeLightBright(1, needSave);
        } else if (brightness == 5) {
            this.mBcmController.setDomeLightBright(5, needSave);
        } else {
            this.mBcmController.setDomeLightBright(3, needSave);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public int getDaytimeRunningLightsOutputStatus() {
        return this.mBcmController.getDaytimeRunningLightsOutputStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setRearLogoLightSw(boolean enable) {
        this.mBcmController.setRearLogoLightSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean getRearLogoLightSw() {
        return this.mBcmController.getRearLogoLightSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setCarpetLightWelcomeMode(boolean enable) {
        this.mBcmController.setCarpetLightWelcomeMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean getCarpetLightWelcomeMode() {
        return this.mBcmController.getCarpetLightWelcomeMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void setPollingLightWelcomeMode(boolean enable) {
        this.mBcmController.setPollingLightWelcomeMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public boolean getPollingLightWelcomeMode() {
        return this.mBcmController.getPollingLightWelcomeMode();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void handleLowBeamLampChanged(boolean isOn) {
        if (App.isMainProcess()) {
            if (isOn) {
                if (this.mSetFogLaterFlag) {
                    setRearFogLamp(true);
                    this.mSetFogLaterFlag = false;
                    return;
                }
                return;
            }
            setRearFogLamp(false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void registerBusiness(String... keys) {
        this.mBcmController.registerBusiness(keys);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void unregisterBusiness(String... keys) {
        this.mBcmController.unregisterBusiness(keys);
    }
}
