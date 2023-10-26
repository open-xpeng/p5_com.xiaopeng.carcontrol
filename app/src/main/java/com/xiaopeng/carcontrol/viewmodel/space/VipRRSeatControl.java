package com.xiaopeng.carcontrol.viewmodel.space;

import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class VipRRSeatControl extends VipSeatControl {
    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkFrontSeatOccupied() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restore() {
    }

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static final VipRRSeatControl instance = new VipRRSeatControl();

        private SingleHolder() {
        }
    }

    public static VipRRSeatControl getInstance() {
        return SingleHolder.instance;
    }

    private VipRRSeatControl() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void init() {
        if (this.mIsInit) {
            return;
        }
        super.init();
        observerRRSeatMove();
        this.mIsInit = true;
    }

    private void observerRRSeatMove() {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipRRSeatControl$ataV_bAe1yGP66TXqIfKka1T39M
            @Override // java.lang.Runnable
            public final void run() {
                VipRRSeatControl.this.lambda$observerRRSeatMove$1$VipRRSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$null$0$VipRRSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    public /* synthetic */ void lambda$observerRRSeatMove$1$VipRRSeatControl() {
        this.mSeatViewModel.getRRSeatData().observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipRRSeatControl$rjez6wWFDJwgYZ5zdbmoyT9_87k
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipRRSeatControl.this.lambda$null$0$VipRRSeatControl((Integer) obj);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void onSeatPosChanged() {
        if (this.mSeatStatus == VipSeatStatus.Flat && !isLayFlatCorrectly()) {
            callbackStatus(VipSeatStatus.Normal);
        } else {
            super.onSeatPosChanged();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    public void laySeatFlat() {
        init();
        LogUtils.i(this.TAG, "RRSeat lay flat rlOccupied:" + this.mSeatViewModel.isRlSeatOccupied() + ",rrOccupied:" + this.mSeatViewModel.isRrSeatOccupied() + ",rcOccupied:" + this.mSeatViewModel.isRmSeatOccupied());
        if (isLayFlatCorrectly()) {
            seatFlatSpeak();
            callbackStatus(VipSeatStatus.Flat);
            if (this.mStatusChangedListener != null) {
                this.mStatusChangedListener.onSeatHasFlat();
            }
        } else if (checkSeatMoveAble()) {
            moveSeatFlat();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void moveSeatFlat() {
        callbackStatus(VipSeatStatus.FlatMoving);
        this.mSeatViewModel.setRRSeatFold(true);
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, 2000L);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    public void restoreSeat() {
        init();
        LogUtils.i(this.TAG, "restoreSeat mStatus:" + this.mSeatStatus);
        callbackStatus(VipSeatStatus.RestoreMoving);
        this.mSeatViewModel.setRRSeatFold(false);
        this.mHandler.removeMessages(4);
        this.mHandler.sendEmptyMessageDelayed(4, 2000L);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkSeatMoveAble() {
        if (this.mVcuViewModel.getGearLevelValue() != GearLevel.P) {
            NotificationHelper.getInstance().showToast(R.string.rear_seat_operate_gear_p);
            return false;
        } else if (this.mSeatViewModel.isRrSeatOccupied() || this.mSeatViewModel.isRmSeatOccupied()) {
            NotificationHelper.getInstance().showToast(R.string.rear_seat_occupied_notice);
            return false;
        } else {
            return true;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restoreFinish() {
        LogUtils.i(this.TAG, "restoreFinish:" + this.mSeatStatus);
        callbackStatus(VipSeatStatus.Normal);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean isLayFlatCorrectly() {
        return this.mSeatViewModel.isRRSeatLieFlat();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    public void stopSeat() {
        this.mSeatViewModel.setRRSeatStopMove();
    }
}
