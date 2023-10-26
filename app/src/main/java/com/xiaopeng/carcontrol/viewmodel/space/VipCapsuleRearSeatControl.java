package com.xiaopeng.carcontrol.viewmodel.space;

import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class VipCapsuleRearSeatControl extends VipSeatControl {
    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkFrontSeatOccupied() {
        return false;
    }

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static final VipCapsuleRearSeatControl instance = new VipCapsuleRearSeatControl();

        private SingleHolder() {
        }
    }

    public static VipCapsuleRearSeatControl getInstance() {
        return SingleHolder.instance;
    }

    private VipCapsuleRearSeatControl() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void init() {
        if (this.mIsInit) {
            return;
        }
        super.init();
        observerDrvSeatMove();
        observerPsnSeatMove();
        this.mIsInit = true;
    }

    private void observerRearSeatMove() {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipCapsuleRearSeatControl$Y-AalXtllNCh6HqKVLkDAlLYGRY
            @Override // java.lang.Runnable
            public final void run() {
                VipCapsuleRearSeatControl.this.lambda$observerRearSeatMove$2$VipCapsuleRearSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$null$0$VipCapsuleRearSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    public /* synthetic */ void lambda$observerRearSeatMove$2$VipCapsuleRearSeatControl() {
        this.mSeatViewModel.getRLSeatData().observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipCapsuleRearSeatControl$HJGZDA5474mVQGEkn1j84LQNkNI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipCapsuleRearSeatControl.this.lambda$null$0$VipCapsuleRearSeatControl((Integer) obj);
            }
        });
        this.mSeatViewModel.getRRSeatData().observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipCapsuleRearSeatControl$B3598TU5a6uGK7rJ4jOwclbvkWU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipCapsuleRearSeatControl.this.lambda$null$1$VipCapsuleRearSeatControl((Integer) obj);
            }
        });
    }

    public /* synthetic */ void lambda$null$1$VipCapsuleRearSeatControl(Integer integer) {
        onSeatPosChanged();
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
                return;
            }
            return;
        }
        boolean z = this.mSeatStatus != VipSeatStatus.FlatWaiting;
        if (checkSeatMoveAble()) {
            moveSeatDelay();
        } else if (z) {
            callbackStatus(VipSeatStatus.FlatWaiting);
            layFlatWaitingTTS();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void moveSeatFlat() {
        callbackStatus(VipSeatStatus.FlatMoving);
        this.mSeatViewModel.moveDrvSeatToSafeForRearSleep();
        this.mSeatViewModel.movePsnSeatToSafeForRearSleep();
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, 2000L);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void startLayFlatMoveNotice() {
        if (this.mSeatStatus == VipSeatStatus.Normal || this.mSeatStatus == VipSeatStatus.FlatWaiting) {
            SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.vip_seat_front_moving_toast), true);
        }
        callbackStatus(VipSeatStatus.FlatMoving);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void layFlatWaitingTTS() {
        LogUtils.d(this.TAG, "layFlatWaitingTTS For RSeatOccupied", false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    public void restoreSeat() {
        super.restoreSeat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restore() {
        LogUtils.i(this.TAG, "restore seat:" + this.mSeatStatus);
        if (checkRearSeatOccupied() && this.mSeatStatus == VipSeatStatus.RestoreMoving) {
            if (this.mStatusChangedListener != null) {
                this.mStatusChangedListener.onSeatStartMove(false);
            }
            this.mSeatViewModel.restoreDrvSeatPos(true, true);
            this.mSeatViewModel.restorePsnSeatPos();
            this.mHandler.removeMessages(4);
            this.mHandler.sendEmptyMessageDelayed(4, 2000L);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkSeatMoveAble() {
        return checkRearSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restoreFinish() {
        LogUtils.i(this.TAG, "restoreFinish:" + this.mSeatStatus);
        callbackStatus(VipSeatStatus.Normal);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean isLayFlatCorrectly() {
        return this.mSeatViewModel.isDrvSeatCorrectForRearFold() && this.mSeatViewModel.isPsnSeatCorrectForRearFold();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    public void stopSeat() {
        this.mSeatViewModel.stopDrvSeatMove();
        this.mSeatViewModel.stopPsnSeatMove();
    }
}
