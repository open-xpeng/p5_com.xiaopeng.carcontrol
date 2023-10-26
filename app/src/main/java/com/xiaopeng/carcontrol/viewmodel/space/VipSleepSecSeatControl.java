package com.xiaopeng.carcontrol.viewmodel.space;

import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class VipSleepSecSeatControl extends VipCapsuleSeatControl {
    private static final String TAG = "VipSleepSecSeatControl";

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static final VipSleepSecSeatControl INSTANCE = new VipSleepSecSeatControl();

        private SingleHolder() {
        }
    }

    public static VipSleepSecSeatControl getInstance() {
        return SingleHolder.INSTANCE;
    }

    private VipSleepSecSeatControl() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void onVipSeatLayFlatFinish() {
        LogUtils.i(TAG, "onVipSeatLayFlatEnd:" + this.mSeatStatus);
        if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
            if (isLayFlatCorrectly()) {
                this.mSeatViewModel.setRLSeatFold(true);
                this.mSeatViewModel.setRRSeatFold(true);
                callbackStatus(VipSeatStatus.FlatSecFold);
                return;
            }
            callbackStatus(VipSeatStatus.FlatPause);
        }
    }
}
