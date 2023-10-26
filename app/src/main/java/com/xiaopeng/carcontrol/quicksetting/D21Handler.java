package com.xiaopeng.carcontrol.quicksetting;

import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorFoldState;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkState;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkType;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowState;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.ciu.CiuViewModel;
import com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class D21Handler extends AbstractHandler {
    private ICarBodyViewModel mCarBodyVm;
    private IChassisViewModel mChassisVm;
    private CiuViewModel mCiuVm;
    private IHvacViewModel mHvacVm;
    private LampViewModel mLampVm;
    private IMirrorViewModel mMirrorVm;
    private IVcuViewModel mVcuVm;
    private WindowDoorViewModel mWindowVm;

    private int convertCiuWiperExist(boolean enable) {
        return enable ? 1 : 0;
    }

    private int convertHdc(boolean enable) {
        return enable ? 2 : 1;
    }

    private int convertHvacClean(boolean enable) {
        return enable ? 2 : 1;
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler
    protected <T> String onHandleCallbackForString(String key, T value) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public D21Handler(IQuickSettingHandler.IQuickSettingCallback callback) {
        super(callback);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initViewModel() {
        super.initViewModel();
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mCarBodyVm = (ICarBodyViewModel) viewModelManager.getViewModelImpl(ICarBodyViewModel.class);
        this.mWindowVm = (WindowDoorViewModel) viewModelManager.getViewModelImpl(IWindowDoorViewModel.class);
        this.mVcuVm = (IVcuViewModel) viewModelManager.getViewModelImpl(IVcuViewModel.class);
        this.mMirrorVm = (IMirrorViewModel) viewModelManager.getViewModelImpl(IMirrorViewModel.class);
        this.mHvacVm = (IHvacViewModel) viewModelManager.getViewModelImpl(IHvacViewModel.class);
        this.mChassisVm = (IChassisViewModel) viewModelManager.getViewModelImpl(IChassisViewModel.class);
        this.mLampVm = (LampViewModel) viewModelManager.getViewModelImpl(ILampViewModel.class);
        this.mCiuVm = (CiuViewModel) viewModelManager.getViewModelImpl(ICiuViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initData() {
        super.initData();
        TrunkState rearTrunkStateValue = this.mWindowVm.getRearTrunkStateValue();
        if (rearTrunkStateValue == null) {
            rearTrunkStateValue = TrunkState.Closed;
        }
        onSignalCallback(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, rearTrunkStateValue);
        WindowState windowStateValue = this.mWindowVm.getWindowStateValue();
        if (windowStateValue == null) {
            windowStateValue = WindowState.Closed;
        }
        onSignalCallback(QuickSettingConstants.KEY_OPEN_WINDOW_INT, windowStateValue);
        onSignalCallback(QuickSettingConstants.KEY_CLOSE_WINDOW_INT, windowStateValue);
        onSignalCallback(QuickSettingConstants.KEY_VENT_WINDOW_INT, windowStateValue);
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$D21Handler$-xrgr-eVfLh8sgg7uY6vzJCd0Ao
            @Override // java.lang.Runnable
            public final void run() {
                D21Handler.this.lambda$initData$2$D21Handler();
            }
        });
        MirrorFoldState mirrorFoldState = this.mMirrorVm.getMirrorFoldState();
        onSignalCallback(QuickSettingConstants.KEY_UNFOLD_MIRROR_INT, mirrorFoldState);
        onSignalCallback(QuickSettingConstants.KEY_FOLD_MIRROR_INT, mirrorFoldState);
        onSignalCallback(QuickSettingConstants.KEY_HVAC_CLEAN_BOOL, Boolean.valueOf(this.mHvacVm.isHvacQualityPurgeEnable()));
        boolean z = false;
        onSignalCallback(QuickSettingConstants.KEY_HDC_BOOL, Boolean.valueOf(!this.mChassisVm.getAvhFault() && this.mChassisVm.getHdc()));
        if (this.mCiuVm.isCiuExist() && this.mCarBodyVm.isCiuRainSwEnable()) {
            z = true;
        }
        onSignalCallback(QuickSettingConstants.KEY_WIPER_GEAR_AUTO_EXIST_BOOL, Boolean.valueOf(z));
        onSignalCallback(QuickSettingConstants.KEY_WIPER_INT, this.mCarBodyVm.getWiperIntervalValue());
        if (getTargetCommand(QuickSettingConstants.KEY_LAMP_CONTROL) == 0) {
            controlLampByMode(true);
        }
    }

    public /* synthetic */ void lambda$initData$2$D21Handler() {
        this.mWindowVm.getRearTrunkData().observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$D21Handler$aY3y4dwDEMIrKgOG9bZL_YScrmQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                D21Handler.this.lambda$null$0$D21Handler((TrunkState) obj);
            }
        });
        if (this.mWindowVm.getWindowStateData() != null) {
            this.mWindowVm.getWindowStateData().observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$D21Handler$IuALQ2qyEwPMdVNibWZ0erCYWY8
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    D21Handler.this.lambda$null$1$D21Handler((WindowState) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$0$D21Handler(TrunkState state) {
        if (state == null) {
            state = TrunkState.Closed;
        }
        onSignalCallback(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, state);
    }

    public /* synthetic */ void lambda$null$1$D21Handler(WindowState state) {
        onSignalCallback(QuickSettingConstants.KEY_OPEN_WINDOW_INT, state);
        onSignalCallback(QuickSettingConstants.KEY_CLOSE_WINDOW_INT, state);
        onSignalCallback(QuickSettingConstants.KEY_VENT_WINDOW_INT, state);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public List<String> getKeyList() {
        List<String> keyList = super.getKeyList();
        keyList.add(QuickSettingConstants.KEY_WIPER_INT);
        keyList.add(QuickSettingConstants.KEY_CONTROL_TRUNK_INT);
        keyList.add(QuickSettingConstants.KEY_UNFOLD_MIRROR_INT);
        keyList.add(QuickSettingConstants.KEY_FOLD_MIRROR_INT);
        keyList.add(QuickSettingConstants.KEY_OPEN_WINDOW_INT);
        keyList.add(QuickSettingConstants.KEY_CLOSE_WINDOW_INT);
        keyList.add(QuickSettingConstants.KEY_VENT_WINDOW_INT);
        keyList.add(QuickSettingConstants.KEY_HVAC_CLEAN_BOOL);
        keyList.add(QuickSettingConstants.KEY_HDC_BOOL);
        keyList.add(QuickSettingConstants.KEY_LAMP_CONTROL);
        return keyList;
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler
    public void onHandleCommand(String key, int command) {
        key.hashCode();
        char c = 65535;
        switch (key.hashCode()) {
            case -2136764233:
                if (key.equals(QuickSettingConstants.KEY_HVAC_CLEAN_BOOL)) {
                    c = 0;
                    break;
                }
                break;
            case -2027031142:
                if (key.equals(QuickSettingConstants.KEY_CLOSE_WINDOW_INT)) {
                    c = 1;
                    break;
                }
                break;
            case -2009121622:
                if (key.equals(QuickSettingConstants.KEY_OPEN_WINDOW_INT)) {
                    c = 2;
                    break;
                }
                break;
            case -1674495063:
                if (key.equals(QuickSettingConstants.KEY_HDC_BOOL)) {
                    c = 3;
                    break;
                }
                break;
            case -551990896:
                if (key.equals(QuickSettingConstants.KEY_VENT_WINDOW_INT)) {
                    c = 4;
                    break;
                }
                break;
            case 308941893:
                if (key.equals(QuickSettingConstants.KEY_UNFOLD_MIRROR_INT)) {
                    c = 5;
                    break;
                }
                break;
            case 940987760:
                if (key.equals(QuickSettingConstants.KEY_WIPER_INT)) {
                    c = 6;
                    break;
                }
                break;
            case 1024440051:
                if (key.equals(QuickSettingConstants.KEY_FOLD_MIRROR_INT)) {
                    c = 7;
                    break;
                }
                break;
            case 1343636982:
                if (key.equals(QuickSettingConstants.KEY_LAMP_CONTROL)) {
                    c = '\b';
                    break;
                }
                break;
            case 1517230959:
                if (key.equals(QuickSettingConstants.KEY_CONTROL_TRUNK_INT)) {
                    c = '\t';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                controlHvacClean(command);
                return;
            case 1:
                controlWindow(false);
                return;
            case 2:
                controlWindow(true);
                return;
            case 3:
                this.mChassisVm.setHdc(command == 2);
                return;
            case 4:
                controlWindowVent();
                return;
            case 5:
                this.mMirrorVm.controlMirror(false);
                return;
            case 6:
                setWiperLevel(command);
                return;
            case 7:
                this.mMirrorVm.controlMirror(true);
                return;
            case '\b':
                controlLampByMode(command == 0);
                return;
            case '\t':
                controlTrunk(command);
                return;
            default:
                LogUtils.d(this.TAG, "handleCommand: undefined key " + key, false);
                return;
        }
    }

    private void controlLampByMode(boolean on) {
        LampViewModel lampViewModel = this.mLampVm;
        lampViewModel.setHeadLampGroup(on ? 0 : lampViewModel.getHeadLampGroupSp(), false);
    }

    private void setWiperLevel(int level) {
        WiperInterval wiperInterval = WiperInterval.Medium;
        if (level == 0) {
            wiperInterval = WiperInterval.Auto;
        } else if (level == 1) {
            wiperInterval = WiperInterval.Slow;
        } else if (level == 2) {
            wiperInterval = WiperInterval.Medium;
        } else if (level == 3) {
            wiperInterval = WiperInterval.Fast;
        } else if (level == 4) {
            wiperInterval = WiperInterval.Ultra;
        } else {
            LogUtils.d(this.TAG, "setWiperLevel undefined level " + level, false);
        }
        this.mCarBodyVm.setWiperInterval(wiperInterval);
    }

    private void controlTrunk(int command) {
        if (ClickHelper.isFastClick(1000L)) {
            LogUtils.d(this.TAG, "controlTrunk too fast!!!", false);
            onSignalCallback(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, this.mWindowVm.getRearTrunkStateValue());
        } else if (command == 2 && this.mVcuVm.getCarSpeed() >= 3.0f) {
            NotificationHelper.getInstance().showToast(R.string.trunk_unable_with_running);
            onSignalCallback(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, this.mWindowVm.getRearTrunkStateValue());
        } else {
            TrunkType trunkType = null;
            if (command == 1) {
                trunkType = TrunkType.Close;
            } else if (command == 2) {
                trunkType = TrunkType.Open;
            } else if (command == 5 || command == 6) {
                trunkType = TrunkType.Pause;
            } else {
                LogUtils.d(this.TAG, "controlTrunk undefined command " + command, false);
            }
            if (trunkType != null) {
                this.mWindowVm.controlRearTrunk(trunkType);
            }
        }
    }

    private void controlWindow(boolean open) {
        if (isWindowLockInactive()) {
            if (open) {
                this.mWindowVm.controlWindowOpen();
            } else {
                this.mWindowVm.controlWindowClose();
            }
        }
    }

    private void controlWindowVent() {
        if (isWindowLockInactive()) {
            this.mWindowVm.controlWindowVent();
        }
    }

    private boolean isWindowLockInactive() {
        if (this.mWindowVm.isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active);
            WindowState windowStateValue = this.mWindowVm.getWindowStateValue();
            if (windowStateValue == null) {
                windowStateValue = WindowState.Closed;
            }
            onSignalCallback(QuickSettingConstants.KEY_OPEN_WINDOW_INT, windowStateValue);
            onSignalCallback(QuickSettingConstants.KEY_CLOSE_WINDOW_INT, windowStateValue);
            onSignalCallback(QuickSettingConstants.KEY_VENT_WINDOW_INT, windowStateValue);
            return false;
        }
        return true;
    }

    private void controlHvacClean(int targetCommand) {
        if (!this.mHvacVm.isSmartModeInProtected()) {
            this.mHvacVm.setHvacQualityPurgeMode(targetCommand == 2);
        } else {
            onSignalCallback(QuickSettingConstants.KEY_HVAC_CLEAN_BOOL, Boolean.valueOf(this.mHvacVm.isHvacQualityPurgeEnable()));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler
    public <T> int onHandleCallback(String key, T value) {
        char c;
        key.hashCode();
        switch (key.hashCode()) {
            case -2136764233:
                if (key.equals(QuickSettingConstants.KEY_HVAC_CLEAN_BOOL)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2027031142:
                if (key.equals(QuickSettingConstants.KEY_CLOSE_WINDOW_INT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -2009121622:
                if (key.equals(QuickSettingConstants.KEY_OPEN_WINDOW_INT)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1674495063:
                if (key.equals(QuickSettingConstants.KEY_HDC_BOOL)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -551990896:
                if (key.equals(QuickSettingConstants.KEY_VENT_WINDOW_INT)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 308941893:
                if (key.equals(QuickSettingConstants.KEY_UNFOLD_MIRROR_INT)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 940987760:
                if (key.equals(QuickSettingConstants.KEY_WIPER_INT)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1024440051:
                if (key.equals(QuickSettingConstants.KEY_FOLD_MIRROR_INT)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1517230959:
                if (key.equals(QuickSettingConstants.KEY_CONTROL_TRUNK_INT)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 2147155216:
                if (key.equals(QuickSettingConstants.KEY_WIPER_GEAR_AUTO_EXIST_BOOL)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return convertHvacClean(((Boolean) value).booleanValue());
            case 1:
            case 2:
            case 4:
                return convertWindowState((WindowState) value);
            case 3:
                return convertHdc(((Boolean) value).booleanValue());
            case 5:
            case 7:
                return convertMirrorFoldState((MirrorFoldState) value);
            case 6:
                return convertWiperLevel((WiperInterval) value);
            case '\b':
                return convertTrunkState((TrunkState) value);
            case '\t':
                return convertCiuWiperExist(((Boolean) value).booleanValue());
            default:
                LogUtils.d(this.TAG, "onSignalCallback: undefined key " + key, false);
                return -1;
        }
    }

    private int convertWiperLevel(WiperInterval level) {
        if (level == null) {
            return -1;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[level.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i != 5) {
                            LogUtils.d(this.TAG, "convertWiperLevel: undefined level " + level, false);
                            return -1;
                        }
                        return 4;
                    }
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    private int convertTrunkState(TrunkState state) {
        if (state == null) {
            LogUtils.d(this.TAG, "convertTrunkState: state is null", false);
            return 1;
        }
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[state.ordinal()]) {
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 3;
            case 4:
                return 6;
            case 5:
                return 5;
            case 6:
                return 7;
            default:
                return 1;
        }
    }

    private int convertMirrorFoldState(MirrorFoldState state) {
        if (state == null) {
            LogUtils.d(this.TAG, "convertMirrorFoldState: state is null", false);
            return 2;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorFoldState[state.ordinal()];
        if (i != 1) {
            return i != 2 ? 2 : 3;
        }
        return 1;
    }

    private int convertWindowState(WindowState state) {
        if (state == null) {
            LogUtils.d(this.TAG, "convertWindowState: state is null", false);
            return 1;
        }
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[state.ordinal()]) {
            case 1:
            default:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
            case 5:
            case 6:
                return 3;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.quicksetting.D21Handler$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorFoldState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval;

        static {
            int[] iArr = new int[WindowState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState = iArr;
            try {
                iArr[WindowState.Closed.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Opened.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.HalfOpened.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Closing.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Opening.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$WindowState[WindowState.Ventting.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[MirrorFoldState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorFoldState = iArr2;
            try {
                iArr2[MirrorFoldState.Folded.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorFoldState[MirrorFoldState.Middle.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr3 = new int[TrunkState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState = iArr3;
            try {
                iArr3[TrunkState.Opened.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Closing.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Opening.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Pause_Closing.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Pause_Opening.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Preparing.ordinal()] = 6;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr4 = new int[WiperInterval.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval = iArr4;
            try {
                iArr4[WiperInterval.Auto.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Slow.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Medium.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Fast.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Ultra.ordinal()] = 5;
            } catch (NoSuchFieldError unused19) {
            }
        }
    }
}
