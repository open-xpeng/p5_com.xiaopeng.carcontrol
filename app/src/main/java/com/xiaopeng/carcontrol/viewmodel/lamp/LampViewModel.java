package com.xiaopeng.carcontrol.viewmodel.lamp;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.function.IntFunction;

/* loaded from: classes2.dex */
public class LampViewModel extends LampBaseViewModel {
    private static final String TAG = "LampViewModel";
    private WeakReference<XDialog> mLowBeamOffDialogRef;
    private final MutableLiveData<LampMode> mHeadLamp = new MutableLiveData<>();
    private final MutableLiveData<LampHeightLevel> mHeightLevel = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAutoLampHeight = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLightMeHome = new MutableLiveData<>();
    private final MutableLiveData<LightMeHomeTime> mLightMeHomeTime = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRearFogLamp = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLedDrl = new MutableLiveData<>();
    private final MutableLiveData<DomeLightState> mDomeLightState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDomeLightSw = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDomeLightBright = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLowBeamLampState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mParkLampState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHighBeamLampState = new MutableLiveData<>();
    private final MutableLiveData<Integer> mParkLampMode = new MutableLiveData<>();
    private final MutableLiveData<TurnLampState> mLeftRightTurnLampState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mParkLightRelatedFMBLightState = new MutableLiveData<>();
    private final MutableLiveData<Integer[]> mParkLightStates = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDaytimeRunningLightData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRearLogoLightSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCarpetLightWelcomeSwData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mPollingLightWelcomeSwData = new MutableLiveData<>();

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleHeadLampModeChanged(int state) {
        LampMode fromBcmState = LampMode.fromBcmState(state);
        if (fromBcmState != null) {
            this.mHeadLamp.postValue(fromBcmState);
            if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
                QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HEAD_LAMP_INT, fromBcmState);
                return;
            }
            return;
        }
        LogUtils.e(TAG, "handle Error Head Lamp Mode: " + state, false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    protected void handleLampHeightLevelChanged(int level) {
        if (LampHeightLevel.fromBcmValue(level) != null) {
            this.mHeightLevel.postValue(LampHeightLevel.fromBcmValue(level));
        } else {
            LogUtils.e(TAG, "handle Error Lamp Height Level: " + level, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleLowBeamOffConfirmStateChanged(boolean isActivated) {
        showLowBeamOffConfirmDialog(isActivated);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleAutoLampHeightChanged(boolean auto) {
        this.mAutoLampHeight.postValue(Boolean.valueOf(auto));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleLightMeHomeChanged(boolean enabled) {
        this.mLightMeHome.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleLightMeHomeTime(int timeCfg) {
        try {
            this.mLightMeHomeTime.postValue(LightMeHomeTime.fromBcmValue(timeCfg));
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleRearFogLampChanged(boolean enabled) {
        this.mRearFogLamp.postValue(Boolean.valueOf(enabled));
        if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_REAR_FOG_INT, Boolean.valueOf(enabled));
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleLowBeamLampChanged(boolean isOn) {
        super.handleLowBeamLampChanged(isOn);
        this.mLowBeamLampState.postValue(Boolean.valueOf(isOn));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handlePositionLampChanged(boolean isOn) {
        this.mParkLampState.postValue(Boolean.valueOf(isOn));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleHighBeamLampChanged(boolean isOn) {
        this.mHighBeamLampState.postValue(Boolean.valueOf(isOn));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleTurnLampChanged(TurnLampState state) {
        this.mLeftRightTurnLampState.postValue(state);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    protected void handleDomeLightState(int state) {
        DomeLightState fromBcmDomeStatus = DomeLightState.fromBcmDomeStatus(state);
        if (fromBcmDomeStatus != null) {
            this.mDomeLightState.postValue(fromBcmDomeStatus);
        } else {
            LogUtils.e(TAG, "handle Error Dome Light State: " + state);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    protected void handleParkLightRelatedFMBLightState(int state) {
        try {
            MutableLiveData<Boolean> mutableLiveData = this.mParkLightRelatedFMBLightState;
            boolean z = true;
            if (state != 1) {
                z = false;
            }
            mutableLiveData.postValue(Boolean.valueOf(z));
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Integer[] lambda$handleParkingLampStatesChanged$0(int x$0) {
        return new Integer[x$0];
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleParkingLampStatesChanged(int[] states) {
        try {
            this.mParkLightStates.postValue(Arrays.stream(states).boxed().toArray(new IntFunction() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LampViewModel$1WilvpjiTjANPGqHFCbWgAW8TEo
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return LampViewModel.lambda$handleParkingLampStatesChanged$0(i);
                }
            }));
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleDomeLightState(boolean lightOnOff) {
        this.mDomeLightSw.postValue(Boolean.valueOf(lightOnOff));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    void handleDomeLightBrightness(int brightness) {
        this.mDomeLightBright.postValue(Integer.valueOf(brightness));
    }

    public void setHeadLampMode(LampMode state) {
        if (state != null) {
            setHeadLampGroup(state.toBcmLampCmd());
        }
    }

    public LampMode getHeadLampMode(LampMode defaultMode) {
        try {
            return LampMode.fromBcmState(getHeadLampGroup());
        } catch (Exception e) {
            e.printStackTrace();
            return defaultMode;
        }
    }

    public LiveData<LampMode> getHeadLampData() {
        return this.mHeadLamp;
    }

    public LampHeightLevel getLampHeightLevelValue() {
        return LampHeightLevel.fromBcmValue(getLampHeightLevel());
    }

    public LiveData<LampHeightLevel> getLampHeightLevelData() {
        return this.mHeightLevel;
    }

    public LiveData<Boolean> getAutoLampHeightData() {
        return this.mAutoLampHeight;
    }

    public LiveData<Boolean> getLightMeHomeData() {
        return this.mLightMeHome;
    }

    public void setLightMeHomeTime(LightMeHomeTime time) {
        if (time != null) {
            setLightMeHomeTime(time.toBcmCmd());
            if (CarBaseConfig.getInstance().isSupportLightMeHomeNew() || isLightMeHomeEnable()) {
                return;
            }
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LampViewModel$6lpXdzQAFWmPz8TiG-wZlgPyNkM
                @Override // java.lang.Runnable
                public final void run() {
                    LampViewModel.this.lambda$setLightMeHomeTime$1$LampViewModel();
                }
            }, 100L);
            return;
        }
        LogUtils.e(TAG, "setLightMeHomeTime: param is null", false);
    }

    public /* synthetic */ void lambda$setLightMeHomeTime$1$LampViewModel() {
        setLightMeHome(true);
    }

    public LightMeHomeTime getLightMeTime() {
        if (this.mLightMeHomeTime.getValue() != null) {
            return this.mLightMeHomeTime.getValue();
        }
        return LightMeHomeTime.fromBcmValue(getLightMeHomeTime());
    }

    public LiveData<LightMeHomeTime> getLightMeHomeTimeData() {
        return this.mLightMeHomeTime;
    }

    public LiveData<Boolean> getRearFogLampData() {
        return this.mRearFogLamp;
    }

    public LiveData<Boolean> getLedDayLightData() {
        return this.mLedDrl;
    }

    public void setDomeLight(DomeLightState state) {
        if (state != null) {
            setDomeLight(state.toBcmDomeCmd());
        }
    }

    public DomeLightState getDomeLightState(DomeLightState defaultState) {
        try {
            return DomeLightState.fromBcmDomeStatus(getDomeLightState());
        } catch (Exception e) {
            e.printStackTrace();
            return defaultState;
        }
    }

    public LiveData<DomeLightState> getDomeLightData() {
        return this.mDomeLightState;
    }

    public LiveData<Boolean> getLowBeamLampState() {
        return this.mLowBeamLampState;
    }

    public LiveData<Boolean> getParkLampData() {
        return this.mParkLampState;
    }

    public LiveData<Boolean> getHighBeamLampData() {
        return this.mHighBeamLampState;
    }

    public LiveData<Integer> getParkLampModeData() {
        return this.mParkLampMode;
    }

    public LiveData<TurnLampState> getTurnLampData() {
        return this.mLeftRightTurnLampState;
    }

    public LiveData<Boolean> getParkLightRelatedFMBLightState() {
        return this.mParkLightRelatedFMBLightState;
    }

    public LiveData<Integer[]> getParkingLampStatesData() {
        return this.mParkLightStates;
    }

    public LiveData<Boolean> getDomeLightSwData() {
        return this.mDomeLightSw;
    }

    public LiveData<Integer> getDomeLightBrightData() {
        return this.mDomeLightBright;
    }

    public LiveData<Boolean> getRearLogoLightSwData() {
        return this.mRearLogoLightSwData;
    }

    public MutableLiveData<Boolean> getCarpetLightWelcomeSwData() {
        return this.mCarpetLightWelcomeSwData;
    }

    public MutableLiveData<Boolean> getPollingLightWelcomeSwData() {
        return this.mPollingLightWelcomeSwData;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    protected void handleDaytimeRunningLightChanged(int state) {
        this.mDaytimeRunningLightData.postValue(Integer.valueOf(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    protected void handleRearLogoLightSwChanged(boolean enable) {
        this.mRearLogoLightSwData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    protected void handleCarpetLightWelcomeStateChange(boolean enable) {
        this.mCarpetLightWelcomeSwData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.LampBaseViewModel
    protected void handlePollingWelcomeStateChange(boolean enable) {
        this.mPollingLightWelcomeSwData.postValue(Boolean.valueOf(enable));
    }

    public LiveData<Integer> getDaytimeRunningLightData() {
        return this.mDaytimeRunningLightData;
    }

    public LampViewModel() {
        this.mBcmController.registerCallback(this.mLampCallBack);
        initLampState();
    }

    private void initLampState() {
        LampMode fromBcmState = LampMode.fromBcmState(this.mBcmController.getHeadLampState());
        this.mHeadLamp.postValue(fromBcmState);
        boolean rearFogLamp = this.mBcmController.getRearFogLamp();
        this.mRearFogLamp.postValue(Boolean.valueOf(rearFogLamp));
        if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HEAD_LAMP_INT, fromBcmState);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_REAR_FOG_INT, Boolean.valueOf(rearFogLamp));
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel
    public void showRearLogoLightConfirmDialog() {
        XDialog negativeButton = new XDialog(App.getInstance(), R.style.XDialogView_Large).setCloseVisibility(true).setTitle(R.string.title_rear_logo_light).setPositiveButton(R.string.rear_logo_light_confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LampViewModel$Ld3ACH9szaSzjZZ0HqeIjAA9f8Q
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                LampViewModel.this.lambda$showRearLogoLightConfirmDialog$2$LampViewModel(xDialog, i);
            }
        }).setNegativeButton(R.string.rear_logo_light_cancel, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LampViewModel$AUHbCmWp1wXBq-GcA3vxL-cqdzw
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                LampViewModel.this.lambda$showRearLogoLightConfirmDialog$3$LampViewModel(xDialog, i);
            }
        });
        negativeButton.setCustomView(LayoutInflater.from(App.getInstance()).inflate(R.layout.layout_rear_logo_view, (ViewGroup) null));
        negativeButton.setSystemDialog(2008);
        negativeButton.show();
    }

    public /* synthetic */ void lambda$showRearLogoLightConfirmDialog$2$LampViewModel(XDialog xDialog, int i) {
        LogUtils.d(TAG, "showRearLogoLightConfirmPanel: setRearLogoLightSw true");
        setRearLogoLightSw(true);
    }

    public /* synthetic */ void lambda$showRearLogoLightConfirmDialog$3$LampViewModel(XDialog xDialog, int i) {
        LogUtils.d(TAG, "showRearLogoLightConfirmPanel: setRearLogoLightSw false");
        setRearLogoLightSw(false);
    }

    public void showLowBeamOffConfirmDialog(final boolean isActivated) {
        if (App.isMainProcess()) {
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LampViewModel$uOmaAaPBJRGHmAasa0O0Vm_sPXM
                @Override // java.lang.Runnable
                public final void run() {
                    LampViewModel.this.lambda$showLowBeamOffConfirmDialog$7$LampViewModel(isActivated);
                }
            });
        }
    }

    public /* synthetic */ void lambda$showLowBeamOffConfirmDialog$7$LampViewModel(final boolean isActivated) {
        LogUtils.d(TAG, "handleLowBeamOffConfirmStateChanged isActivated = " + isActivated + ", mLowBeamOffDialogRef = " + this.mLowBeamOffDialogRef);
        if (isActivated) {
            WeakReference<XDialog> weakReference = this.mLowBeamOffDialogRef;
            if (weakReference == null || weakReference.get() == null) {
                XDialog onDismissListener = new XDialog(App.getInstance(), R.style.XDialogView).setCloseVisibility(false).setTitle(R.string.low_beam_off_confirm_title).setMessage(R.string.low_beam_off_confirm_content).setPositiveButton(R.string.btn_confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LampViewModel$UsJ6TKB8fYqvrHAMnQFBHUxD2w0
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        LampViewModel.this.lambda$null$4$LampViewModel(xDialog, i);
                    }
                }).setNegativeButton(R.string.btn_cancel, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LampViewModel$PlrOSRS-BD-Ptz-Ae2sWO3HgoAM
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        LampViewModel.this.lambda$null$5$LampViewModel(xDialog, i);
                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.viewmodel.lamp.-$$Lambda$LampViewModel$s5lPg-f4ahYvv90H5pxMf_7OP88
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        LampViewModel.this.lambda$null$6$LampViewModel(dialogInterface);
                    }
                });
                this.mLowBeamOffDialogRef = new WeakReference<>(onDismissListener);
                onDismissListener.setSystemDialog(2008);
                onDismissListener.show();
            }
        }
    }

    public /* synthetic */ void lambda$null$4$LampViewModel(XDialog xDialog, int i) {
        LogUtils.i(TAG, "Low beam off confirm: true", false);
        setLowBeamOffConfirmSt(true);
        this.mLowBeamOffDialogRef = null;
    }

    public /* synthetic */ void lambda$null$5$LampViewModel(XDialog xDialog, int i) {
        LogUtils.i(TAG, "Low beam off confirm: false", false);
        setLowBeamOffConfirmSt(false);
        this.mLowBeamOffDialogRef = null;
    }

    public /* synthetic */ void lambda$null$6$LampViewModel(DialogInterface dialog1) {
        LogUtils.i(TAG, "Low beam off confirm dialog dismiss", false);
        this.mLowBeamOffDialogRef = null;
    }
}
