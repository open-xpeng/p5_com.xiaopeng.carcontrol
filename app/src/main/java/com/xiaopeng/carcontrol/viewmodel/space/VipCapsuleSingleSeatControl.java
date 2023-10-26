package com.xiaopeng.carcontrol.viewmodel.space;

import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class VipCapsuleSingleSeatControl extends VipSeatControl {
    private static final String TAG = "VipCapsuleSingleSeatControl";

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void seatFlatSpeak() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static final VipCapsuleSingleSeatControl INSTANCE = new VipCapsuleSingleSeatControl();

        private SingleHolder() {
        }
    }

    public static VipCapsuleSingleSeatControl getInstance() {
        return SingleHolder.INSTANCE;
    }

    private VipCapsuleSingleSeatControl() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void init() {
        if (this.mIsInit) {
            return;
        }
        super.init();
        observerPsnSeatMove();
        observerPsnHeadrest();
        this.mIsInit = true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void layFlatWaitingTTS() {
        if (this.mStatusChangedListener != null) {
            this.mStatusChangedListener.onPlayLayFlatWaitingTTS();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void moveSeatFlat() {
        LogUtils.i(TAG, "start move seat flat:" + this.mSeatStatus);
        if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
            if (this.mStatusChangedListener != null) {
                this.mStatusChangedListener.onSeatStartMove(true);
            }
            if (BaseFeatureOption.getInstance().isEcuDoSeatLayFlat()) {
                this.mSeatViewModel.setPsnSeatLayFlat();
            } else if (this.mSeatViewModel.getPSeatTiltPos() < 55 && this.mSeatViewModel.getPSeatHorzPos() < 95) {
                this.mIsPsnMoveHor = true;
                this.mSeatViewModel.setPSeatHorzPos(100);
            } else {
                this.mSeatViewModel.setPsnSeatAllPositions(100, VIP_SEAT_VER_POS, 0, 0);
            }
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 2000L);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void stopSeat() {
        this.mSeatViewModel.stopPsnSeatMove();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restore() {
        LogUtils.i(TAG, "restore seat:" + this.mSeatStatus);
        if (checkRearSeatOccupied() && this.mSeatStatus == VipSeatStatus.RestoreMoving) {
            if (this.mStatusChangedListener != null) {
                this.mStatusChangedListener.onSeatStartMove(false);
            }
            this.mSeatViewModel.restorePsnSeatPos();
            this.mHandler.removeMessages(4);
            this.mHandler.sendEmptyMessageDelayed(4, 2000L);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkSeatMoveAble() {
        return super.checkRearSeatOccupied() && !this.mSeatViewModel.isPsnHeadrestNormal();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restoreFinish() {
        LogUtils.i(TAG, "restoreFinish:" + this.mSeatStatus);
        callbackStatus(this.mSeatViewModel.isPsnSeatEqualMemory() ? VipSeatStatus.Normal : VipSeatStatus.RestorePause);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean isLayFlatCorrectly() {
        return this.mSeatViewModel.isPsnSeatLieFlat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkFrontSeatOccupied() {
        return this.mSeatViewModel.isPsnSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void onSeatPosChanged() {
        if (this.mSeatStatus == VipSeatStatus.Flat && !isLayFlatCorrectly()) {
            callbackStatus(VipSeatStatus.Normal);
            return;
        }
        if (this.mIsPsnMoveHor && this.mSeatStatus == VipSeatStatus.FlatMoving && this.mSeatViewModel.getPSeatHorzPos() > 95) {
            this.mIsPsnMoveHor = false;
            this.mSeatViewModel.setPsnSeatAllPositions(100, VIP_SEAT_VER_POS, 0, 0);
        }
        super.onSeatPosChanged();
    }
}
