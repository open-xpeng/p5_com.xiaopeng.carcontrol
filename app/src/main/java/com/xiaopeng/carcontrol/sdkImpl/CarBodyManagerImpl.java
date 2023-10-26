package com.xiaopeng.carcontrol.sdkImpl;

import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkState;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkType;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ChargePort;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ChargePortState;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval;
import com.xiaopeng.smartcontrol.sdk.server.CarControlSDKImpl;
import com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer;

/* loaded from: classes2.dex */
public class CarBodyManagerImpl extends AbstractManagerImpl implements CarBodyManagerServer.Implementation {
    private CarBodyManagerServer.Callback mCallback;
    private CarBodyViewModel mCarBodyVm;
    private WindowDoorViewModel mWindowDoorVm;

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public boolean getCwcChargeErrorSt() {
        return false;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getCwcChargeSt() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public boolean getCwcFRChargeErrorSt() {
        return false;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getCwcFRChargeSt() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public boolean getCwcFRSwEnable() {
        return false;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public boolean getCwcRLChargeErrorSt() {
        return false;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getCwcRLChargeSt() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public boolean getCwcRLSwEnable() {
        return false;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public boolean getCwcRRChargeErrorSt() {
        return false;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getCwcRRChargeSt() {
        return 0;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public boolean getCwcRRSwEnable() {
        return false;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public boolean getCwcSwEnable() {
        return false;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void setCwcSwEnable(boolean b) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void setCwcSwFREnable(boolean b) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void setCwcSwRLEnable(boolean b) {
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void setCwcSwRREnable(boolean b) {
    }

    @Override // com.xiaopeng.carcontrol.sdkImpl.AbstractManagerImpl
    protected void initInternal() {
        this.mWindowDoorVm = (WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
        this.mCarBodyVm = (CarBodyViewModel) ViewModelManager.getInstance().getViewModelImpl(ICarBodyViewModel.class);
        this.mCallback = CarControlSDKImpl.getInstance().getCarBodyCallback();
    }

    @Override // com.xiaopeng.carcontrol.sdkImpl.AbstractManagerImpl
    protected void observeData() {
        setLiveDataObserver(this.mWindowDoorVm.getDoorStateData(0), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$RLd39MzNAGoBR7bx5TH3S4Zhd0k
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarBodyManagerImpl.this.lambda$observeData$0$CarBodyManagerImpl((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mWindowDoorVm.getDoorStateData(1), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$Q4ah5ruZio6vz43qviXevN-un6U
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarBodyManagerImpl.this.lambda$observeData$1$CarBodyManagerImpl((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mWindowDoorVm.getDoorStateData(2), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$E_TFuX1utRjfeCe6BkezAiT3r6I
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarBodyManagerImpl.this.lambda$observeData$2$CarBodyManagerImpl((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mWindowDoorVm.getDoorStateData(3), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$MRmev_gp0FiirR4fzdmRlpJKoTE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarBodyManagerImpl.this.lambda$observeData$3$CarBodyManagerImpl((Boolean) obj);
            }
        });
        setLiveDataObserver(this.mWindowDoorVm.getRearTrunkData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$bp6AJ-v6YhFvHKVlDUXkqvbVCnc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarBodyManagerImpl.this.lambda$observeData$4$CarBodyManagerImpl((TrunkState) obj);
            }
        });
        setLiveDataObserver(this.mCarBodyVm.getRightChargePortStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$dHW6CTCzB0Ws7zMWeKJ02WrA5dA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarBodyManagerImpl.this.lambda$observeData$5$CarBodyManagerImpl((ChargePortState) obj);
            }
        });
        setLiveDataObserver(this.mCarBodyVm.getLeftChargePortStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$LDpRnA3paepclGhW2ss4yRnXFMc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarBodyManagerImpl.this.lambda$observeData$6$CarBodyManagerImpl((ChargePortState) obj);
            }
        });
        setLiveDataObserver(this.mCarBodyVm.getWiperIntervalData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$hgwFwGSGsKTpjqgiPX9RWn4jMaM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarBodyManagerImpl.this.lambda$observeData$7$CarBodyManagerImpl((WiperInterval) obj);
            }
        });
        if (CarBaseConfig.getInstance().isSupportSlideDoor()) {
            setLiveDataObserver(this.mWindowDoorVm.getLeftSlideDoorModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$MKTFUSNX0Emyx5YbALRHElCumCw
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CarBodyManagerImpl.this.lambda$observeData$8$CarBodyManagerImpl((Integer) obj);
                }
            });
            setLiveDataObserver(this.mWindowDoorVm.getRightSlideDoorModeData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$z7DjSAg1um13w7kXaAEoEL-wu-k
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CarBodyManagerImpl.this.lambda$observeData$9$CarBodyManagerImpl((Integer) obj);
                }
            });
            setLiveDataObserver(this.mWindowDoorVm.getLeftSlideDoorStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$YkyLOLmIgAsI-HI7n0YD4BOUBD8
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CarBodyManagerImpl.this.lambda$observeData$10$CarBodyManagerImpl((Integer) obj);
                }
            });
            setLiveDataObserver(this.mWindowDoorVm.getRightSlideDoorStateData(), new Observer() { // from class: com.xiaopeng.carcontrol.sdkImpl.-$$Lambda$CarBodyManagerImpl$epxF7Isj9CMzoza5cTrAzJ8iTGg
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CarBodyManagerImpl.this.lambda$observeData$11$CarBodyManagerImpl((Integer) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$observeData$0$CarBodyManagerImpl(Boolean isClosed) {
        CarBodyManagerServer.Callback callback;
        if (isClosed == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onDoorChanged(0, isClosed.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$1$CarBodyManagerImpl(Boolean isClosed) {
        CarBodyManagerServer.Callback callback;
        if (isClosed == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onDoorChanged(1, isClosed.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$2$CarBodyManagerImpl(Boolean isClosed) {
        CarBodyManagerServer.Callback callback;
        if (isClosed == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onDoorChanged(2, isClosed.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$3$CarBodyManagerImpl(Boolean isClosed) {
        CarBodyManagerServer.Callback callback;
        if (isClosed == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onDoorChanged(3, isClosed.booleanValue());
    }

    public /* synthetic */ void lambda$observeData$4$CarBodyManagerImpl(TrunkState state) {
        CarBodyManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onTrunkStateChanged(convertTrunkState(state));
    }

    public /* synthetic */ void lambda$observeData$5$CarBodyManagerImpl(ChargePortState state) {
        CarBodyManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onChargePortChanged(false, convertChargePortState(state));
    }

    public /* synthetic */ void lambda$observeData$6$CarBodyManagerImpl(ChargePortState state) {
        CarBodyManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onChargePortChanged(true, convertChargePortState(state));
    }

    public /* synthetic */ void lambda$observeData$7$CarBodyManagerImpl(WiperInterval interval) {
        CarBodyManagerServer.Callback callback;
        if (interval == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onWiperSensitivityChanged(interval.toSensitivityBcmCmd());
    }

    public /* synthetic */ void lambda$observeData$8$CarBodyManagerImpl(Integer mode) {
        CarBodyManagerServer.Callback callback;
        if (mode == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onLeftSlideDoorModeChanged(mode.intValue());
    }

    public /* synthetic */ void lambda$observeData$9$CarBodyManagerImpl(Integer mode) {
        CarBodyManagerServer.Callback callback;
        if (mode == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onRightSlideDoorModeChanged(mode.intValue());
    }

    public /* synthetic */ void lambda$observeData$10$CarBodyManagerImpl(Integer state) {
        CarBodyManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onLeftSlideDoorStateChanged(state.intValue());
    }

    public /* synthetic */ void lambda$observeData$11$CarBodyManagerImpl(Integer state) {
        CarBodyManagerServer.Callback callback;
        if (state == null || (callback = this.mCallback) == null) {
            return;
        }
        callback.onRightSlideDoorStateChanged(state.intValue());
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public boolean isDoorClosed(int index) {
        return this.mWindowDoorVm.isDoorClosed(index);
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getTrunkState() {
        return convertTrunkState(this.mWindowDoorVm.getRearTrunkStateValue());
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void setTrunk(int controlType) {
        this.mWindowDoorVm.getRearTrunkStateValue();
        if (controlType == 1) {
            this.mWindowDoorVm.controlRearTrunk(TrunkType.Open);
        } else if (controlType == 2) {
            this.mWindowDoorVm.controlRearTrunk(TrunkType.Pause);
        } else if (controlType != 3) {
        } else {
            this.mWindowDoorVm.controlRearTrunk(TrunkType.Close);
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getChargePort(boolean isLeft) {
        return convertChargePortState(this.mCarBodyVm.getChargePortState(isLeft ? ChargePort.Left : ChargePort.Right));
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void setChargePort(boolean isLeft, boolean open) {
        if (this.mCarBodyVm.isChargePortEnable(isLeft, true)) {
            this.mCarBodyVm.controlChargePort(isLeft ? ChargePort.Left : ChargePort.Right, open);
        }
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getWiperSensitivity() {
        int sensitivityBcmCmd = this.mCarBodyVm.getWiperIntervalValue().toSensitivityBcmCmd();
        LogUtils.d(this.TAG, "getWiperSensitivity: " + sensitivityBcmCmd, false);
        return sensitivityBcmCmd;
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void setWiperSensitivity(int value) {
        LogUtils.d(this.TAG, "setWiperSensitivity: " + value, false);
        this.mCarBodyVm.setWiperInterval(WiperInterval.fromSensitivityBcmState(value));
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getLeftSlideDoorMode() {
        return this.mWindowDoorVm.getLeftSlideDoorMode();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void setLeftSlideDoorMode(int mode) {
        this.mWindowDoorVm.setLeftSlideDoorMode(mode);
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getRightSlideDoorMode() {
        return this.mWindowDoorVm.getRightSlideDoorMode();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void setRightSlideDoorMode(int mode) {
        this.mWindowDoorVm.setRightSlideDoorMode(mode);
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getLeftSlideDoorState() {
        return this.mWindowDoorVm.getLeftSlideDoorState();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public int getRightSlideDoorState() {
        return this.mWindowDoorVm.getRightSlideDoorState();
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void controlLeftSlideDoor(int cmd) {
        this.mWindowDoorVm.controlLeftSlideDoor(cmd);
    }

    @Override // com.xiaopeng.smartcontrol.sdk.server.vehicle.CarBodyManagerServer.Implementation
    public void controlRightSlideDoor(int cmd) {
        this.mWindowDoorVm.controlRightSlideDoor(cmd);
    }

    private int convertTrunkState(TrunkState state) {
        if (state == null) {
            state = TrunkState.Closed;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[state.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        i2 = 5;
                        if (i != 5) {
                            return 0;
                        }
                    }
                }
            }
        }
        return i2;
    }

    private int convertChargePortState(ChargePortState state) {
        if (state == null) {
            state = ChargePortState.UNKNOWN;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePortState[state.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return i != 4 ? 4 : 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.sdkImpl.CarBodyManagerImpl$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePortState;

        static {
            int[] iArr = new int[ChargePortState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePortState = iArr;
            try {
                iArr[ChargePortState.OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePortState[ChargePortState.MIDDLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePortState[ChargePortState.CLOSE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePortState[ChargePortState.FAULT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$ChargePortState[ChargePortState.UNKNOWN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[TrunkState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState = iArr2;
            try {
                iArr2[TrunkState.Opening.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Closing.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Pause_Opening.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Pause_Closing.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Opened.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Closed.ordinal()] = 6;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }
}
