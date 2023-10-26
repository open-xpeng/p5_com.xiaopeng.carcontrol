package com.xiaopeng.carcontrol.quicksetting;

import android.text.TextUtils;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.DriveMode;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
class CommonHandler implements IQuickSettingHandler {
    private static final String TAG = "CommonHandler";
    private final IQuickSettingHandler.IQuickSettingCallback mCallback;
    private IChassisViewModel mChassisVm;
    private IHvacViewModel mHvacVm;
    private IVcuViewModel mVcuVm;

    private int convertAntiSickDriveMode(int mode) {
        return 7 == mode ? 2 : 1;
    }

    private int convertAvh(boolean enable) {
        return enable ? 2 : 1;
    }

    private int convertHvacDeodorant(boolean enable) {
        return enable ? 2 : 1;
    }

    private int convertHvacDriveTemp(float temp) {
        return (int) (temp * 10.0f);
    }

    private int convertHvacRapidCooling(boolean enable) {
        return enable ? 2 : 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CommonHandler(IQuickSettingHandler.IQuickSettingCallback callback) {
        this.mCallback = callback;
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public List<String> getKeyList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(QuickSettingConstants.KEY_DRIVE_MODE_INT);
        arrayList.add(QuickSettingConstants.KEY_AVH_BOOL);
        if (!BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            arrayList.add(QuickSettingConstants.KEY_HVAC_WIND_SPEED_INT);
            arrayList.add(QuickSettingConstants.KEY_HVAC_DRIVER_TEMP_FLOAT);
            arrayList.add(QuickSettingConstants.KEY_QUICK_COOL_BOOL);
            arrayList.add(QuickSettingConstants.KEY_DEODORANT_BOOL);
            arrayList.add(QuickSettingConstants.KEY_HVAC_AUTO_DEFOG_WORK_ST);
            arrayList.add(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_HEAT_LEVEL_INT);
            arrayList.add(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_VENT_LEVEL_INT);
            arrayList.add(QuickSettingConstants.KEY_HVAC_PSN_SEAT_HEAT_LEVEL_INT);
            arrayList.add(QuickSettingConstants.KEY_HVAC_PSN_SEAT_VENT_LEVEL_INT);
            arrayList.add(QuickSettingConstants.KEY_HVAC_MODE_STATUS);
        }
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initViewModel() {
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mVcuVm = (IVcuViewModel) viewModelManager.getViewModelImpl(IVcuViewModel.class);
        this.mChassisVm = (IChassisViewModel) viewModelManager.getViewModelImpl(IChassisViewModel.class);
        this.mHvacVm = (IHvacViewModel) viewModelManager.getViewModelImpl(IHvacViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initData() {
        onSignalCallback(QuickSettingConstants.KEY_DRIVE_MODE_INT, Integer.valueOf(this.mVcuVm.getDriveMode()));
        onSignalCallback(QuickSettingConstants.KEY_ANTI_SICK_DRIVE_MODE_SW_INT, Integer.valueOf(this.mVcuVm.getDriveMode()));
        onSignalCallback(QuickSettingConstants.KEY_AVH_BOOL, Boolean.valueOf(this.mChassisVm.getAvhForUI()));
        if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            return;
        }
        onSignalCallback(QuickSettingConstants.KEY_HVAC_WIND_SPEED_INT, Integer.valueOf(this.mHvacVm.getHvacWindSpeedLevel()));
        onSignalCallback(QuickSettingConstants.KEY_HVAC_DRIVER_TEMP_FLOAT, Float.valueOf(this.mHvacVm.getHvacDriverTemp()));
        onSignalCallback(QuickSettingConstants.KEY_QUICK_COOL_BOOL, Boolean.valueOf(this.mHvacVm.isHvacRapidCoolingEnable()));
        onSignalCallback(QuickSettingConstants.KEY_DEODORANT_BOOL, Boolean.valueOf(this.mHvacVm.isHvacDeodorantEnable()));
        onSignalCallback(QuickSettingConstants.KEY_HVAC_AUTO_DEFOG_WORK_ST, Boolean.valueOf(this.mHvacVm.isAutoDefogWorkSt()));
        onSignalCallback(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_HEAT_LEVEL_INT, Integer.valueOf(this.mHvacVm.getSeatHeatLevel()));
        onSignalCallback(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_VENT_LEVEL_INT, Integer.valueOf(this.mHvacVm.getSeatVentLevel()));
        onSignalCallback(QuickSettingConstants.KEY_HVAC_PSN_SEAT_HEAT_LEVEL_INT, Integer.valueOf(this.mHvacVm.getPsnSeatHeatLevel()));
        onSignalCallback(QuickSettingConstants.KEY_HVAC_PSN_SEAT_VENT_LEVEL_INT, Integer.valueOf(this.mHvacVm.getPsnSeatVentLevel()));
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public boolean handleCommand(String key, int command) {
        key.hashCode();
        char c = 65535;
        switch (key.hashCode()) {
            case -2078090512:
                if (key.equals(QuickSettingConstants.KEY_DEODORANT_BOOL)) {
                    c = 0;
                    break;
                }
                break;
            case -1868200807:
                if (key.equals(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_HEAT_LEVEL_INT)) {
                    c = 1;
                    break;
                }
                break;
            case -1756583756:
                if (key.equals(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_VENT_LEVEL_INT)) {
                    c = 2;
                    break;
                }
                break;
            case -1654800892:
                if (key.equals(QuickSettingConstants.KEY_HVAC_WIND_SPEED_INT)) {
                    c = 3;
                    break;
                }
                break;
            case -1473685539:
                if (key.equals(QuickSettingConstants.KEY_QUICK_COOL_BOOL)) {
                    c = 4;
                    break;
                }
                break;
            case -595740345:
                if (key.equals(QuickSettingConstants.KEY_HVAC_MODE_STATUS)) {
                    c = 5;
                    break;
                }
                break;
            case 499705892:
                if (key.equals(QuickSettingConstants.KEY_AVH_BOOL)) {
                    c = 6;
                    break;
                }
                break;
            case 788168150:
                if (key.equals(QuickSettingConstants.KEY_HVAC_PSN_SEAT_HEAT_LEVEL_INT)) {
                    c = 7;
                    break;
                }
                break;
            case 899785201:
                if (key.equals(QuickSettingConstants.KEY_HVAC_PSN_SEAT_VENT_LEVEL_INT)) {
                    c = '\b';
                    break;
                }
                break;
            case 1655014668:
                if (key.equals(QuickSettingConstants.KEY_HVAC_DRIVER_TEMP_FLOAT)) {
                    c = '\t';
                    break;
                }
                break;
            case 1989404601:
                if (key.equals(QuickSettingConstants.KEY_DRIVE_MODE_INT)) {
                    c = '\n';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                setHvacDeodorant(command == 2);
                break;
            case 1:
                this.mHvacVm.setSeatHeatLevel(command);
                break;
            case 2:
                this.mHvacVm.setSeatVentLevel(command);
                break;
            case 3:
                this.mHvacVm.setHvacWindSpeedLevel(command);
                break;
            case 4:
                setHvacRapidCooling(command == 2);
                break;
            case 5:
                this.mHvacVm.setHvacModeData(command == 2);
                break;
            case 6:
                this.mChassisVm.setAvhSw(command == 2);
                break;
            case 7:
                this.mHvacVm.setPsnSeatHeatLevel(command);
                break;
            case '\b':
                this.mHvacVm.setPsnSeatVentLevel(command);
                break;
            case '\t':
                setHvacPsnTemp(command);
                break;
            case '\n':
                setDriveMode(command);
                break;
            default:
                return false;
        }
        return true;
    }

    private void setDriveMode(int mode) {
        int i = 0;
        if (mode == 1) {
            i = 1;
        } else if (mode == 2) {
            i = 2;
        } else if (mode != 3) {
            LogUtils.d(TAG, "setDriveMode: undefined mode " + mode, false);
            i = 3;
        }
        this.mVcuVm.setDriveModeByUser(DriveMode.fromVcuDriveMode(i));
    }

    private void setHvacPsnTemp(int temp) {
        if (!this.mHvacVm.isHvacDriverSyncMode()) {
            this.mHvacVm.setHvacDriverSyncMode(true);
        }
        this.mHvacVm.setHvacTempDriver(temp / 10.0f);
    }

    private void setHvacRapidCooling(boolean enable) {
        if (!this.mHvacVm.isSmartModeInProtected()) {
            this.mHvacVm.setHvacRapidCoolingEnable(enable);
        } else {
            onSignalCallback(QuickSettingConstants.KEY_QUICK_COOL_BOOL, Boolean.valueOf(this.mHvacVm.isHvacRapidCoolingEnable()));
        }
    }

    private void setHvacDeodorant(boolean enable) {
        if (!this.mHvacVm.isSmartModeInProtected()) {
            this.mHvacVm.setHvacDeodorantEnable(enable);
        } else {
            onSignalCallback(QuickSettingConstants.KEY_DEODORANT_BOOL, Boolean.valueOf(this.mHvacVm.isHvacDeodorantEnable()));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public <T> boolean onSignalCallback(String key, T value) {
        char c;
        int convertHvacDeodorant;
        key.hashCode();
        switch (key.hashCode()) {
            case -2078090512:
                if (key.equals(QuickSettingConstants.KEY_DEODORANT_BOOL)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1868200807:
                if (key.equals(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_HEAT_LEVEL_INT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1756583756:
                if (key.equals(QuickSettingConstants.KEY_HVAC_DRIVER_SEAT_VENT_LEVEL_INT)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1654800892:
                if (key.equals(QuickSettingConstants.KEY_HVAC_WIND_SPEED_INT)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1473685539:
                if (key.equals(QuickSettingConstants.KEY_QUICK_COOL_BOOL)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 499705892:
                if (key.equals(QuickSettingConstants.KEY_AVH_BOOL)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 788168150:
                if (key.equals(QuickSettingConstants.KEY_HVAC_PSN_SEAT_HEAT_LEVEL_INT)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 899785201:
                if (key.equals(QuickSettingConstants.KEY_HVAC_PSN_SEAT_VENT_LEVEL_INT)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1487861277:
                if (key.equals(QuickSettingConstants.KEY_HVAC_AUTO_DEFOG_WORK_ST)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1655014668:
                if (key.equals(QuickSettingConstants.KEY_HVAC_DRIVER_TEMP_FLOAT)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1822514813:
                if (key.equals(QuickSettingConstants.KEY_ANTI_SICK_DRIVE_MODE_SW_INT)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1989404601:
                if (key.equals(QuickSettingConstants.KEY_DRIVE_MODE_INT)) {
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
                convertHvacDeodorant = convertHvacDeodorant(((Boolean) value).booleanValue());
                break;
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
                convertHvacDeodorant = ((Integer) value).intValue();
                break;
            case 4:
                convertHvacDeodorant = convertHvacRapidCooling(((Boolean) value).booleanValue());
                break;
            case 5:
                convertHvacDeodorant = convertAvh(((Boolean) value).booleanValue());
                break;
            case '\b':
                convertHvacDeodorant = ((Boolean) value).booleanValue() ? 2 : 1;
                break;
            case '\t':
                convertHvacDeodorant = convertHvacDriveTemp(((Float) value).floatValue());
                break;
            case '\n':
                convertHvacDeodorant = convertAntiSickDriveMode(((Integer) value).intValue());
                break;
            case 11:
                convertHvacDeodorant = convertDriveMode(((Integer) value).intValue());
                break;
            default:
                convertHvacDeodorant = -1;
                break;
        }
        if (TextUtils.isEmpty(key) || convertHvacDeodorant == -1) {
            return false;
        }
        this.mCallback.onHandleCallback(key, convertHvacDeodorant);
        return true;
    }

    private int convertDriveMode(int mode) {
        int i = 2;
        if (mode == 0) {
            i = 3;
        } else if (mode == 1) {
            i = 1;
        } else if (mode != 2) {
            LogUtils.d(TAG, "convertDriveMode: undefined mode " + mode, false);
            i = mode;
        }
        LogUtils.d(TAG, "convertDriveMode, mode: " + mode + ", qsDriveMode: " + i, false);
        return i;
    }
}
