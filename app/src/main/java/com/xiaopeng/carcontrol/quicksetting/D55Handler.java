package com.xiaopeng.carcontrol.quicksetting;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.NedcState;
import com.xiaopeng.carcontrolmodule.R;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class D55Handler extends D21Handler {
    private CarBodyViewModel mCarBodyVm;
    private IScuViewModel mScuVm;
    private ISeatViewModel mSeatVm;
    private IWindowDoorViewModel mWindowVm;
    private IXpuViewModel mXpuVm;

    private int convertTrunkState(int state) {
        return state == 0 ? 1 : 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public D55Handler(IQuickSettingHandler.IQuickSettingCallback callback) {
        super(callback);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initViewModel() {
        super.initViewModel();
        ViewModelManager viewModelManager = ViewModelManager.getInstance();
        this.mSeatVm = (ISeatViewModel) viewModelManager.getViewModelImpl(ISeatViewModel.class);
        this.mWindowVm = (IWindowDoorViewModel) viewModelManager.getViewModelImpl(IWindowDoorViewModel.class);
        this.mCarBodyVm = (CarBodyViewModel) viewModelManager.getViewModelImpl(ICarBodyViewModel.class);
        this.mScuVm = (IScuViewModel) viewModelManager.getViewModelImpl(IScuViewModel.class);
        this.mXpuVm = (IXpuViewModel) viewModelManager.getViewModelImpl(IXpuViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initData() {
        super.initData();
        onSignalCallback(QuickSettingConstants.KEY_DRV_SEAT_POS_INT, Integer.valueOf(this.mSeatVm.getDrvSeatPosIdx()));
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            onSignalCallback(QuickSettingConstants.KEY_IHB_INT, this.mScuVm.getIhbState());
        }
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public List<String> getKeyList() {
        List<String> keyList = super.getKeyList();
        keyList.add(QuickSettingConstants.KEY_DRV_SEAT_POS_INT);
        keyList.add(QuickSettingConstants.KEY_TRUNK_INT);
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            keyList.add(QuickSettingConstants.KEY_IHB_INT);
        }
        return keyList;
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler
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
            case -893159540:
                if (key.equals(QuickSettingConstants.KEY_DRV_SEAT_POS_INT)) {
                    c = 1;
                    break;
                }
                break;
            case -236779268:
                if (key.equals(QuickSettingConstants.KEY_TRUNK_INT)) {
                    c = 2;
                    break;
                }
                break;
            case 1343636982:
                if (key.equals(QuickSettingConstants.KEY_LAMP_CONTROL)) {
                    c = 3;
                    break;
                }
                break;
            case 1517230959:
                if (key.equals(QuickSettingConstants.KEY_CONTROL_TRUNK_INT)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mScuVm.setIhbEnable(command == 2, true);
                return;
            case 1:
                setDrvSeatIndex(command - 1);
                return;
            case 2:
                this.mWindowVm.openRearTrunk();
                return;
            case 3:
            case 4:
                return;
            default:
                super.onHandleCommand(key, command);
                return;
        }
    }

    private void setDrvSeatIndex(int index) {
        if (this.mSeatVm.haveDefaultSeat(index)) {
            this.mSeatVm.restoreDrvSeatPos(index);
            return;
        }
        NotificationHelper.getInstance().showToast(ResUtils.getString(R.string.drv_seat_is_empty_prompt, Integer.valueOf(index + 1)));
        onSignalCallback(QuickSettingConstants.KEY_DRV_SEAT_POS_INT, Integer.valueOf(this.mSeatVm.getDrvSeatPosIdx()));
    }

    private void setWiperSensitivity(int level) {
        int i = 4;
        if (level != 1) {
            if (level != 2) {
                if (level == 3) {
                    i = 3;
                } else if (level != 4) {
                    LogUtils.d(this.TAG, "setWiperSensitivity undefined level " + level, false);
                }
            }
            i = 2;
        } else {
            i = 1;
        }
        this.mCarBodyVm.setWiperInterval(WiperInterval.fromSensitivityBcmState(i));
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler
    public <T> int onHandleCallback(String key, T value) {
        key.hashCode();
        char c = 65535;
        switch (key.hashCode()) {
            case -1635033136:
                if (key.equals(QuickSettingConstants.KEY_IHB_INT)) {
                    c = 0;
                    break;
                }
                break;
            case -893159540:
                if (key.equals(QuickSettingConstants.KEY_DRV_SEAT_POS_INT)) {
                    c = 1;
                    break;
                }
                break;
            case -236779268:
                if (key.equals(QuickSettingConstants.KEY_TRUNK_INT)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return convertIhbState((ScuResponse) value);
            case 1:
                return ((Integer) value).intValue() + 1;
            case 2:
                return convertTrunkState(((Integer) value).intValue());
            default:
                return super.onHandleCallback(key, value);
        }
    }

    private int convertIhbState(ScuResponse state) {
        if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
            return 1;
        }
        return ((!BaseFeatureOption.getInstance().isIhbDependOnXPilot() || this.mScuVm.isXpuXpilotActivated()) && this.mXpuVm.getNedcState() != NedcState.On && state == ScuResponse.ON) ? 2 : 1;
    }
}
