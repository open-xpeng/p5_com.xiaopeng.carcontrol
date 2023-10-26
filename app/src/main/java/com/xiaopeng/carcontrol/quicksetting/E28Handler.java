package com.xiaopeng.carcontrol.quicksetting;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.ClickHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkState;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkType;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.AsEngineerMode;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampMode;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.vcu.DriveMode;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.NedcState;
import com.xiaopeng.carcontrolmodule.R;
import java.util.List;
import java.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class E28Handler extends AbstractHandler {
    private ICarBodyViewModel mCarBodyVm;
    private ChassisViewModel mChassisViewModel;
    private LampViewModel mLampVm;
    private IScuViewModel mScuVm;
    private IVcuViewModel mVcuVm;
    private WindowDoorViewModel mWinDoorVm;
    private IXpuViewModel mXpuVm;

    private int convertChildLockState(boolean isLocked) {
        return isLocked ? 2 : 1;
    }

    private int convertEasyLoadState(boolean isOn) {
        return isOn ? 2 : 1;
    }

    private int convertTrunkState(int state) {
        return state == 0 ? 1 : 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public E28Handler(IQuickSettingHandler.IQuickSettingCallback callback) {
        super(callback);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initViewModel() {
        super.initViewModel();
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mCarBodyVm = (ICarBodyViewModel) viewModelManager.getViewModelImpl(ICarBodyViewModel.class);
        this.mWinDoorVm = (WindowDoorViewModel) viewModelManager.getViewModelImpl(IWindowDoorViewModel.class);
        this.mVcuVm = (IVcuViewModel) viewModelManager.getViewModelImpl(IVcuViewModel.class);
        this.mScuVm = (IScuViewModel) viewModelManager.getViewModelImpl(IScuViewModel.class);
        this.mXpuVm = (IXpuViewModel) viewModelManager.getViewModelImpl(IXpuViewModel.class);
        this.mChassisViewModel = (ChassisViewModel) viewModelManager.getViewModelImpl(IChassisViewModel.class);
        this.mLampVm = (LampViewModel) viewModelManager.getViewModelImpl(ILampViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initData() {
        super.initData();
        onSignalCallback(QuickSettingConstants.KEY_WIPER_INT, this.mCarBodyVm.getWiperIntervalValue());
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            onSignalCallback(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, this.mWinDoorVm.getRearTrunkStateValue());
        } else {
            onSignalCallback(QuickSettingConstants.KEY_TRUNK_INT, Integer.valueOf(this.mWinDoorVm.getRearTrunkState()));
        }
        onSignalCallback(QuickSettingConstants.KEY_POWER_RECYCLE_INT, Integer.valueOf(this.mVcuVm.getEnergyRecycleGrade()));
        onSignalCallback(QuickSettingConstants.KEY_AC_CHARGE_INT, Integer.valueOf(this.mCarBodyVm.getChargePortUnlock(1)));
        onSignalCallback(QuickSettingConstants.KEY_DC_CHARGE_INT, Integer.valueOf(this.mCarBodyVm.getChargePortUnlock(2)));
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            onSignalCallback(QuickSettingConstants.KEY_IHB_INT, this.mScuVm.getIhbState());
        }
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            onSignalCallback(QuickSettingConstants.KEY_EASY_LOAD, Boolean.valueOf(this.mChassisViewModel.isEasyLoadingEnable()));
        }
        if (CarBaseConfig.getInstance().isSupportChildLock()) {
            onSignalCallback(QuickSettingConstants.KEY_CHILD_lOCK_LEFT, Boolean.valueOf(this.mCarBodyVm.isLeftChildLocked()));
            onSignalCallback(QuickSettingConstants.KEY_CHILD_LOCK_RIGHT, Boolean.valueOf(this.mCarBodyVm.isRightChildLocked()));
        }
        if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
            onSignalCallback(QuickSettingConstants.KEY_HEAD_LAMP_INT, this.mLampVm.getHeadLampMode(LampMode.Auto));
            onSignalCallback(QuickSettingConstants.KEY_REAR_FOG_INT, Boolean.valueOf(this.mLampVm.getRearFogLampState()));
        }
        if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
            onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_FL, this.mChassisViewModel.getTyrePressure(1));
            onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_FR, this.mChassisViewModel.getTyrePressure(2));
            onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_RL, this.mChassisViewModel.getTyrePressure(3));
            onSignalCallback(QuickSettingConstants.KEY_TIRE_VALUE_RR, this.mChassisViewModel.getTyrePressure(4));
        }
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public List<String> getKeyList() {
        List<String> keyList = super.getKeyList();
        keyList.add(QuickSettingConstants.KEY_WIPER_INT);
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            keyList.add(QuickSettingConstants.KEY_CONTROL_TRUNK_INT);
        } else {
            keyList.add(QuickSettingConstants.KEY_TRUNK_INT);
        }
        keyList.add(QuickSettingConstants.KEY_POWER_RECYCLE_INT);
        if (!CarBaseConfig.getInstance().isChargePortSignalErr()) {
            keyList.add(QuickSettingConstants.KEY_DC_CHARGE_INT);
        }
        if (!CarBaseConfig.getInstance().isSingleChargePort() || CarBaseConfig.getInstance().isChargePortSignalErr()) {
            keyList.add(QuickSettingConstants.KEY_AC_CHARGE_INT);
        }
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            keyList.add(QuickSettingConstants.KEY_IHB_INT);
        }
        if (CarBaseConfig.getInstance().isSupportAirSuspension()) {
            keyList.add(QuickSettingConstants.KEY_EASY_LOAD);
        }
        if (CarBaseConfig.getInstance().isSupportChildLock()) {
            keyList.add(QuickSettingConstants.KEY_CHILD_lOCK_LEFT);
            keyList.add(QuickSettingConstants.KEY_CHILD_LOCK_RIGHT);
        }
        if (BaseFeatureOption.getInstance().isSupportInterTransportMode()) {
            keyList.add(QuickSettingConstants.KEY_HEAD_LAMP_INT);
            keyList.add(QuickSettingConstants.KEY_REAR_FOG_INT);
        }
        return keyList;
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler
    public void onHandleCommand(String key, int command) {
        key.hashCode();
        char c = 65535;
        switch (key.hashCode()) {
            case -1635033136:
                if (key.equals(QuickSettingConstants.KEY_IHB_INT)) {
                    c = 0;
                    break;
                }
                break;
            case -1561203865:
                if (key.equals(QuickSettingConstants.KEY_POWER_RECYCLE_INT)) {
                    c = 1;
                    break;
                }
                break;
            case -1436700797:
                if (key.equals(QuickSettingConstants.KEY_CHILD_LOCK_RIGHT)) {
                    c = 2;
                    break;
                }
                break;
            case -980570102:
                if (key.equals(QuickSettingConstants.KEY_DC_CHARGE_INT)) {
                    c = 3;
                    break;
                }
                break;
            case -779549843:
                if (key.equals(QuickSettingConstants.KEY_AC_CHARGE_INT)) {
                    c = 4;
                    break;
                }
                break;
            case -323622464:
                if (key.equals(QuickSettingConstants.KEY_CHILD_lOCK_LEFT)) {
                    c = 5;
                    break;
                }
                break;
            case -236779268:
                if (key.equals(QuickSettingConstants.KEY_TRUNK_INT)) {
                    c = 6;
                    break;
                }
                break;
            case 307837852:
                if (key.equals(QuickSettingConstants.KEY_HEAD_LAMP_INT)) {
                    c = 7;
                    break;
                }
                break;
            case 940987760:
                if (key.equals(QuickSettingConstants.KEY_WIPER_INT)) {
                    c = '\b';
                    break;
                }
                break;
            case 1425245407:
                if (key.equals(QuickSettingConstants.KEY_REAR_FOG_INT)) {
                    c = '\t';
                    break;
                }
                break;
            case 1517230959:
                if (key.equals(QuickSettingConstants.KEY_CONTROL_TRUNK_INT)) {
                    c = '\n';
                    break;
                }
                break;
            case 1923500803:
                if (key.equals(QuickSettingConstants.KEY_EASY_LOAD)) {
                    c = 11;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                setIhbSw(command);
                return;
            case 1:
                setPowerRecycle(command);
                return;
            case 2:
                this.mCarBodyVm.setChildLock(false, command == 2);
                return;
            case 3:
            case 4:
                controlChargePort(key, command);
                return;
            case 5:
                this.mCarBodyVm.setChildLock(true, command == 2);
                return;
            case 6:
                this.mWinDoorVm.openRearTrunk();
                return;
            case 7:
                this.mLampVm.setHeadLampMode(convertToHeadLampMode(command));
                return;
            case '\b':
                setWiperLevel(command);
                return;
            case '\t':
                this.mLampVm.setRearFogLamp(command == 1);
                return;
            case '\n':
                controlTrunk(command);
                return;
            case 11:
                controlEasyLoadSwitch(command == 2);
                return;
            default:
                LogUtils.d(this.TAG, "onHandleCommand: undefined key " + key, false);
                return;
        }
    }

    private void setWiperLevel(int level) {
        WiperInterval wiperInterval = WiperInterval.Medium;
        if (level == 1) {
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
            onSignalCallbackOnSingleThread(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$E28Handler$pxXeZTjAc2T8sa2xby3cUEzET3o
                @Override // java.util.function.Supplier
                public final Object get() {
                    return E28Handler.this.lambda$controlTrunk$0$E28Handler();
                }
            });
        } else if (command == 2 && this.mVcuVm.getCarSpeed() >= 3.0f) {
            NotificationHelper.getInstance().showToast(R.string.trunk_unable_with_running);
            onSignalCallbackOnSingleThread(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$E28Handler$w7MUElMKOwZMS9E2M7UlnkSIzec
                @Override // java.util.function.Supplier
                public final Object get() {
                    return E28Handler.this.lambda$controlTrunk$1$E28Handler();
                }
            });
        } else {
            TrunkState rearTrunkStateValue = this.mWinDoorVm.getRearTrunkStateValue();
            if (command == 1) {
                if (rearTrunkStateValue == TrunkState.Closed) {
                    onSignalCallbackOnSingleThread(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$E28Handler$woCcOX0t9KJNLd7_FCorKNJWtVM
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return E28Handler.this.lambda$controlTrunk$4$E28Handler();
                        }
                    });
                }
                this.mWinDoorVm.controlRearTrunk(TrunkType.Close);
            } else if (command == 2) {
                if (rearTrunkStateValue == TrunkState.Opened) {
                    onSignalCallbackOnSingleThread(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$E28Handler$cDS7EzriFi1BLqxwEnMu3l-WmgI
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return E28Handler.this.lambda$controlTrunk$3$E28Handler();
                        }
                    });
                }
                this.mWinDoorVm.controlRearTrunk(TrunkType.Open);
            } else if (command == 5 || command == 6) {
                if (rearTrunkStateValue == TrunkState.Pause_Closing || rearTrunkStateValue == TrunkState.Pause_Opening) {
                    onSignalCallbackOnSingleThread(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$E28Handler$GAJNTc5bAYiReiG-JgxtukqREIs
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return E28Handler.this.lambda$controlTrunk$2$E28Handler();
                        }
                    });
                }
                this.mWinDoorVm.controlRearTrunk(TrunkType.Pause);
            } else {
                LogUtils.d(this.TAG, "controlTrunk undefined command " + command, false);
            }
        }
    }

    public /* synthetic */ TrunkState lambda$controlTrunk$0$E28Handler() {
        return this.mWinDoorVm.getRearTrunkStateValue();
    }

    public /* synthetic */ TrunkState lambda$controlTrunk$1$E28Handler() {
        return this.mWinDoorVm.getRearTrunkStateValue();
    }

    public /* synthetic */ TrunkState lambda$controlTrunk$2$E28Handler() {
        return this.mWinDoorVm.getRearTrunkStateValue();
    }

    public /* synthetic */ TrunkState lambda$controlTrunk$3$E28Handler() {
        return this.mWinDoorVm.getRearTrunkStateValue();
    }

    public /* synthetic */ TrunkState lambda$controlTrunk$4$E28Handler() {
        return this.mWinDoorVm.getRearTrunkStateValue();
    }

    private void setPowerRecycle(int cmd) {
        if (cmd != 1 && cmd != 2) {
            if (cmd == 4) {
                this.mVcuVm.setDriveModeByUser(DriveMode.EcoPlusOff);
                return;
            } else {
                LogUtils.d(this.TAG, "setPowerRecycle undefined command " + cmd, false);
                return;
            }
        }
        int i = cmd == 1 ? 1 : 3;
        if (this.mVcuVm.isEnergyEnable(true)) {
            this.mVcuVm.setEnergyRecycleGrade(i);
        } else {
            onSignalCallback(QuickSettingConstants.KEY_POWER_RECYCLE_INT, Integer.valueOf(this.mVcuVm.getEnergyRecycleGrade()));
        }
    }

    private void controlChargePort(String side, int command) {
        final boolean equals = QuickSettingConstants.KEY_AC_CHARGE_INT.equals(side);
        boolean z = command == 2;
        if (this.mCarBodyVm.getChargePortUnlock(equals ? 1 : 2) == z) {
            onSignalCallbackOnSingleThread(side, new Supplier() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$E28Handler$q94xvilN1Qhjt4gctGE1mI1n00Y
                @Override // java.util.function.Supplier
                public final Object get() {
                    return E28Handler.this.lambda$controlChargePort$5$E28Handler(equals);
                }
            });
        } else if (ClickHelper.isFastClick(500L)) {
            LogUtils.d(this.TAG, "controlChargePort too fast!!!", false);
            onSignalCallbackOnSingleThread(side, new Supplier() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$E28Handler$8yrryTOjcN-DfBJ7xcqRbvkttEs
                @Override // java.util.function.Supplier
                public final Object get() {
                    return E28Handler.this.lambda$controlChargePort$6$E28Handler(equals);
                }
            });
        } else if (this.mCarBodyVm.isChargePortEnable(equals, true)) {
            this.mCarBodyVm.setChargePortUnlock(equals ? 1 : 2, z);
        } else if (this.mCarBodyVm.isChargePortResetable(equals)) {
            this.mCarBodyVm.resetChargePort(equals);
        } else {
            onSignalCallback(side, Integer.valueOf(this.mCarBodyVm.getChargePortUnlock(equals ? 1 : 2)));
        }
    }

    public /* synthetic */ Integer lambda$controlChargePort$5$E28Handler(final boolean isLeft) {
        return Integer.valueOf(this.mCarBodyVm.getChargePortUnlock(isLeft ? 1 : 2));
    }

    public /* synthetic */ Integer lambda$controlChargePort$6$E28Handler(final boolean isLeft) {
        return Integer.valueOf(this.mCarBodyVm.getChargePortUnlock(isLeft ? 1 : 2));
    }

    private void setIhbSw(int command) {
        ScuResponse ihbState = this.mScuVm.getIhbState();
        boolean z = command == 2;
        if (ihbState == (z ? ScuResponse.ON : ScuResponse.OFF)) {
            onSignalCallbackOnSingleThread(QuickSettingConstants.KEY_IHB_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$E28Handler$ktapES0hKiIBT2mhc2864FRCz2A
                @Override // java.util.function.Supplier
                public final Object get() {
                    return E28Handler.this.lambda$setIhbSw$7$E28Handler();
                }
            });
        } else if (ClickHelper.isFastClick(500L)) {
            LogUtils.d(this.TAG, "setIhbSw too fast!!!", false);
            onSignalCallback(QuickSettingConstants.KEY_IHB_INT, this.mScuVm.getIhbState());
        } else {
            this.mScuVm.setIhbEnable(z, true);
        }
    }

    public /* synthetic */ ScuResponse lambda$setIhbSw$7$E28Handler() {
        return this.mScuVm.getIhbState();
    }

    private void controlEasyLoadSwitch(boolean isOn) {
        if (isOn) {
            if (this.mChassisViewModel.getAsFaultStatus()) {
                NotificationHelper.getInstance().showToast(R.string.esp_fault_prompt);
                return;
            } else if (this.mVcuVm.getGearLevel() != 4) {
                NotificationHelper.getInstance().showToast(R.string.as_repair_open_without_p_gear);
                return;
            } else {
                AsEngineerMode asEngineerMode = this.mChassisViewModel.getAsEngineerMode();
                if (AsEngineerMode.MAINTENANCE == asEngineerMode) {
                    NotificationHelper.getInstance().showToast(R.string.trailer_hook_open_with_as_repair);
                    return;
                } else if (AsEngineerMode.TRAILER == asEngineerMode) {
                    NotificationHelper.getInstance().showToast(R.string.as_handle_failed_for_trailer_hook);
                    return;
                } else if (AsEngineerMode.CAMPING == asEngineerMode) {
                    NotificationHelper.getInstance().showToast(R.string.trailer_hook_open_with_as_height);
                    return;
                } else if (AsEngineerMode.TRANSPORT == asEngineerMode) {
                    NotificationHelper.getInstance().showToast(R.string.easy_load_open_transport_tip);
                    return;
                } else if (this.mChassisViewModel.getAsLockModeStatus() || this.mChassisViewModel.getAsHoistModeSwitchStatus()) {
                    NotificationHelper.getInstance().showToast(R.string.easy_load_open_as_lock_tip);
                    return;
                }
            }
        }
        this.mChassisViewModel.setEasyLoadingSwitch(isOn);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler
    public <T> int onHandleCallback(String key, T value) {
        char c;
        key.hashCode();
        switch (key.hashCode()) {
            case -1635033136:
                if (key.equals(QuickSettingConstants.KEY_IHB_INT)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1561203865:
                if (key.equals(QuickSettingConstants.KEY_POWER_RECYCLE_INT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1436700797:
                if (key.equals(QuickSettingConstants.KEY_CHILD_LOCK_RIGHT)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -980570102:
                if (key.equals(QuickSettingConstants.KEY_DC_CHARGE_INT)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -779549843:
                if (key.equals(QuickSettingConstants.KEY_AC_CHARGE_INT)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -323622464:
                if (key.equals(QuickSettingConstants.KEY_CHILD_lOCK_LEFT)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -236779268:
                if (key.equals(QuickSettingConstants.KEY_TRUNK_INT)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 307837852:
                if (key.equals(QuickSettingConstants.KEY_HEAD_LAMP_INT)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 940987760:
                if (key.equals(QuickSettingConstants.KEY_WIPER_INT)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1425245407:
                if (key.equals(QuickSettingConstants.KEY_REAR_FOG_INT)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1517230959:
                if (key.equals(QuickSettingConstants.KEY_CONTROL_TRUNK_INT)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1923500803:
                if (key.equals(QuickSettingConstants.KEY_EASY_LOAD)) {
                    c = 11;
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
                return convertIhbState((ScuResponse) value);
            case 1:
                return convertPowerRecycle(((Integer) value).intValue());
            case 2:
            case 5:
                return convertChildLockState(((Boolean) value).booleanValue());
            case 3:
            case 4:
                return convertChargePortState(((Integer) value).intValue());
            case 6:
                return convertTrunkState(((Integer) value).intValue());
            case 7:
                return convertFromHeadLampMode((LampMode) value);
            case '\b':
                return convertWiperLevel((WiperInterval) value);
            case '\t':
                return convertRearFogState((Boolean) value);
            case '\n':
                return convertTrunkState((TrunkState) value);
            case 11:
                return convertEasyLoadState(((Boolean) value).booleanValue());
            default:
                LogUtils.d(this.TAG, "onHandleCallback: undefined key " + key, false);
                return -1;
        }
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.AbstractHandler
    protected <T> String onHandleCallbackForString(String key, T value) {
        key.hashCode();
        char c = 65535;
        switch (key.hashCode()) {
            case -968896962:
                if (key.equals(QuickSettingConstants.KEY_TIRE_VALUE_RR)) {
                    c = 0;
                    break;
                }
                break;
            case -754279886:
                if (key.equals(QuickSettingConstants.KEY_TIRE_VALUE_FR)) {
                    c = 1;
                    break;
                }
                break;
            case -48972166:
                if (key.equals(QuickSettingConstants.KEY_TIRE_WARNING)) {
                    c = 2;
                    break;
                }
                break;
            case 1459142916:
                if (key.equals(QuickSettingConstants.KEY_TIRE_VALUE_RL)) {
                    c = 3;
                    break;
                }
                break;
            case 1673759992:
                if (key.equals(QuickSettingConstants.KEY_TIRE_VALUE_FL)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return (String) value;
            default:
                LogUtils.d(this.TAG, "onHandleCallbackForString: undefined key " + key, false);
                return null;
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
            case 7:
                return 8;
            default:
                return 1;
        }
    }

    private int convertPowerRecycle(int grade) {
        if (this.mVcuVm.getDriveMode() == 5) {
            return 3;
        }
        return grade == 3 ? 2 : 1;
    }

    private int convertChargePortState(int bcmState) {
        if (bcmState != -1) {
            if (bcmState != 0) {
                if (bcmState != 1) {
                    if (bcmState != 2) {
                        if (bcmState != 3) {
                            LogUtils.d(this.TAG, "convertChargePortState: undefined state " + bcmState, false);
                            return -1;
                        }
                        return 4;
                    }
                    return 1;
                }
                return 5;
            }
            return 2;
        }
        return 3;
    }

    private int convertIhbState(ScuResponse state) {
        return ((!BaseFeatureOption.getInstance().isIhbDependOnXPilot() || this.mScuVm.isXpuXpilotActivated()) && this.mXpuVm.getNedcState() != NedcState.On && state == ScuResponse.ON) ? 2 : 1;
    }

    private int convertRearFogState(Boolean value) {
        return value.booleanValue() ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.quicksetting.E28Handler$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode;

        static {
            int[] iArr = new int[LampMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode = iArr;
            try {
                iArr[LampMode.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.Position.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.LowBeam.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[LampMode.Auto.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[TrunkState.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState = iArr2;
            try {
                iArr2[TrunkState.Opened.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Closing.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Opening.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Pause_Closing.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Pause_Opening.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Preparing.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$TrunkState[TrunkState.Min_Open_Angle.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr3 = new int[WiperInterval.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval = iArr3;
            try {
                iArr3[WiperInterval.Slow.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Medium.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Fast.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$carsettings$WiperInterval[WiperInterval.Ultra.ordinal()] = 4;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    private int convertFromHeadLampMode(LampMode mode) {
        if (mode == null) {
            return 3;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$lamp$LampMode[mode.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return i != 3 ? 3 : 2;
            }
            return 1;
        }
        return 0;
    }

    private LampMode convertToHeadLampMode(int index) {
        LampMode lampMode = LampMode.Auto;
        if (index != 0) {
            if (index != 1) {
                return index != 2 ? lampMode : LampMode.LowBeam;
            }
            return LampMode.Position;
        }
        return LampMode.Off;
    }
}
