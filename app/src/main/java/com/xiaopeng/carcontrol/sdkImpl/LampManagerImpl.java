package com.xiaopeng.carcontrol.sdkImpl;

import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.TurnLampState;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.NedcState;
import com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel;
import com.xiaopeng.smartcontrol.sdk.server.CarControlSDKImpl;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer;

/* loaded from: classes2.dex */
public class LampManagerImpl extends AbstractManagerImpl implements LampManagerServer.Implementation {
    private LampManagerServer.Callback mCallback;
    private LampViewModel mLampVm;
    private ScuViewModel mScuVm;
    private VcuViewModel mVcuVm;
    private XpuViewModel mXpuVm;

    @Override // com.xiaopeng.carcontrol.sdkImpl.AbstractManagerImpl
    protected void initInternal() {
        this.mLampVm = (LampViewModel) ViewModelManager.getInstance().getViewModelImpl(ILampViewModel.class);
        this.mVcuVm = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        this.mScuVm = (ScuViewModel) ViewModelManager.getInstance().getViewModelImpl(IScuViewModel.class);
        this.mXpuVm = (XpuViewModel) ViewModelManager.getInstance().getViewModelImpl(IXpuViewModel.class);
        this.mCallback = CarControlSDKImpl.getInstance().getLampCallback();
    }

    @Override // com.xiaopeng.carcontrol.sdkImpl.AbstractManagerImpl
    protected void observeData() {
        setLiveDataObserver(this.mLampVm.getParkLightRelatedFMBLightState(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$a3vybn-TeJ25F8qLJHWx05yref0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LampManagerImpl.this.lambda$observeData$0$LampManagerImpl((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mLampVm.getTurnLampData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$5FUGnPhPk8q_Em5yDRA6ksFIrEY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LampManagerImpl.this.lambda$observeData$1$LampManagerImpl((TurnLampState) obj);
            }
        });
        setLiveDataObserver(this.mLampVm.getParkLampData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$8hHxhrWXz6KNsttXrAQL-5-m9nw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LampManagerImpl.this.lambda$observeData$2$LampManagerImpl((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mLampVm.getLowBeamLampState(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$aNzyqXRhxDVcccXjmN2FY9eYDDE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LampManagerImpl.this.lambda$observeData$3$LampManagerImpl((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mLampVm.getHighBeamLampData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$o1JzxdiDUr62lHqkTPf6fN68bnI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LampManagerImpl.this.lambda$observeData$4$LampManagerImpl((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mLampVm.getRearFogLampData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$z9MzB4iglrzt2YZLCqwjmpnU0U0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LampManagerImpl.this.lambda$observeData$5$LampManagerImpl((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mVcuVm.getGearLevelData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$W0KGQHrPwXCnucVy--rT6PCyMWc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LampManagerImpl.this.lambda$observeData$6$LampManagerImpl((GearLevel) obj);
            }
        });
        if (CarBaseConfig.getInstance().isSupportBrakeLight()) {
            setLiveDataObserver(this.mVcuVm.getBrakeLightData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$IP1HXNIMjOe_U-yb9OA02QemInE
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LampManagerImpl.this.lambda$observeData$7$LampManagerImpl((Boolean) obj);
                }
            });
        }
        if (CarBaseConfig.getInstance().isSupportDaytimeRunningLight()) {
            setLiveDataObserver(this.mLampVm.getDaytimeRunningLightData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$FoGvodvwRRXrZUePJploqt2bfeU
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LampManagerImpl.this.lambda$observeData$8$LampManagerImpl((Integer) obj);
                }
            });
        }
        if (CarBaseConfig.getInstance().isSupportIhb()) {
            setLiveDataObserver(this.mScuVm.getIhbData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$QFPHpFqHfLXVO_Os6G-UkQWyrZ0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LampManagerImpl.this.lambda$observeData$9$LampManagerImpl((ScuResponse) obj);
                }
            });
            setLiveDataObserver(this.mXpuVm.getNedcStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$LampManagerImpl$FxpoSDby_8dVQ4O0Og_57kAzFgg
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    LampManagerImpl.this.lambda$observeData$10$LampManagerImpl((NedcState) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$observeData$0$LampManagerImpl(Boolean isOn) {
        LampManagerServer.Callback callback;
        if (isOn == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onDoubleFlashBeamChanged(convertDoubleFlashState(this.mLampVm.getTurnLampState()));
        this.mCallback.onPositionBeamChanged(this.mLampVm.getParkLampState());
        this.mCallback.onSaberModeChanged(!isOn.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$1$LampManagerImpl(TurnLampState state) {
        LampManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onDoubleFlashBeamChanged(convertDoubleFlashState(state));
        this.mCallback.onPositionBeamChanged(this.mLampVm.getParkLampState());
    }

    public /* synthetic */ void lambda$observeData$2$LampManagerImpl(Boolean isOn) {
        LampManagerServer.Callback callback;
        if (isOn == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onDoubleFlashBeamChanged(convertDoubleFlashState(this.mLampVm.getTurnLampState()));
        this.mCallback.onPositionBeamChanged(isOn.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$3$LampManagerImpl(Boolean isOn) {
        LampManagerServer.Callback callback;
        if (isOn == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onLowBeamChanged(isOn.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$4$LampManagerImpl(Boolean isOn) {
        LampManagerServer.Callback callback;
        if (isOn == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onHighBeamChanged(isOn.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$5$LampManagerImpl(Boolean isOn) {
        LampManagerServer.Callback callback;
        if (isOn == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onRearFogLampChanged(isOn.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$6$LampManagerImpl(GearLevel level) {
        LampManagerServer.Callback callback;
        if (level == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onReverseLampChanged(level == GearLevel.R);
    }

    public /* synthetic */ void lambda$observeData$7$LampManagerImpl(Boolean on) {
        LampManagerServer.Callback callback;
        if (on == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onBrakeLightChanged(on.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$8$LampManagerImpl(Integer state) {
        LampManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onDaytimeRunningLightChanged(state.intValue());
    }

    public /* synthetic */ void lambda$observeData$9$LampManagerImpl(ScuResponse state) {
        LampManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onIhbStateChanged(getIhbState());
    }

    public /* synthetic */ void lambda$observeData$10$LampManagerImpl(NedcState state) {
        LampManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onIhbStateChanged(getIhbState());
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public int getDoubleFlashBeamState() {
        return convertDoubleFlashState(this.mLampVm.getTurnLampState());
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public boolean isPositionBeamOn() {
        return this.mLampVm.getParkLampState();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public boolean isLowBeamOn() {
        return this.mLampVm.getLowBeamState();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public boolean isHighBeamOn() {
        return this.mLampVm.getHighBeamState();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public int getSaberMode() {
        return !this.mLampVm.isParkLampIncludeFmB();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public boolean isRearFogLampOn() {
        return this.mLampVm.getRearFogLampState();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public boolean isReverseLampOn() {
        return this.mVcuVm.getGearLevelValue() == GearLevel.R;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public boolean getBrakeLightOnOff() {
        return this.mVcuVm.getBrakeLightOnOff();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public int getDaytimeRunningLightsOutputStatus() {
        return this.mLampVm.getDaytimeRunningLightsOutputStatus();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public int getIhbState() {
        int i = 1;
        if (!this.mXpuVm.isXpuNedcActivated()) {
            int i2 = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[this.mScuVm.getIhbState().ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    i = 3;
                }
            }
            LogUtils.d(this.TAG, "getIhbState: " + i);
            return i;
        }
        i = 0;
        LogUtils.d(this.TAG, "getIhbState: " + i);
        return i;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.LampManagerServer.Implementation
    public void setIhbSw(boolean on) {
        this.mScuVm.setIhbEnable(on, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.sdkImpl.LampManagerImpl$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$TurnLampState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse;

        static {
            int[] iArr = new int[TurnLampState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$TurnLampState = iArr;
            try {
                iArr[TurnLampState.Left.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$TurnLampState[TurnLampState.Right.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$TurnLampState[TurnLampState.Both.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[ScuResponse.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse = iArr2;
            try {
                iArr2[ScuResponse.ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[ScuResponse.FAIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[ScuResponse.OFF.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private int convertDoubleFlashState(TurnLampState state) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$TurnLampState[state.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return 0;
                }
            }
        }
        return i2;
    }
}
