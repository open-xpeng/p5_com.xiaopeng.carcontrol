package com.xiaopeng.carcontrol.viewmodel.lamp;

import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;

/* loaded from: classes2.dex */
public class LampSmartControl {
    private static final String TAG = "LampSmartControl";
    private ILampViewModel mLampVm;

    /* loaded from: classes2.dex */
    private static class Holder {
        private static LampSmartControl sInstance = new LampSmartControl();

        private Holder() {
        }
    }

    private LampSmartControl() {
        this.mLampVm = (ILampViewModel) ViewModelManager.getInstance().getViewModelImpl(ILampViewModel.class);
    }

    public static LampSmartControl getInstance() {
        return Holder.sInstance;
    }

    public void setRearFogSw(boolean on) {
        this.mLampVm.setRearFogLamp(on);
    }

    public void controlXpWaitMode(int mode) {
        LogUtils.i(TAG, "controlXpWaitMode: mode=" + mode);
        if (mode == 0) {
            ILampViewModel iLampViewModel = this.mLampVm;
            iLampViewModel.setHeadLampGroup(iLampViewModel.getHeadLampGroupSp(), false);
        } else if (mode != 1) {
        } else {
            this.mLampVm.setHeadLampGroup(0, false);
        }
    }
}
