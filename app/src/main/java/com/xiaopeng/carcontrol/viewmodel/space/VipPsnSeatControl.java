package com.xiaopeng.carcontrol.viewmodel.space;

import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class VipPsnSeatControl extends VipSeatControl {

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static final VipPsnSeatControl INSTANCE = new VipPsnSeatControl();

        private SingleHolder() {
        }
    }

    public static VipPsnSeatControl getInstance() {
        return SingleHolder.INSTANCE;
    }

    private VipPsnSeatControl() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void init() {
        if (this.mIsInit) {
            return;
        }
        super.init();
        observerPsnSeatMove();
        observerPsnHeadrest();
        if (!BaseFeatureOption.getInstance().isSupportSeatFlatFrontOccupied()) {
            observerPsnSeatOccupied();
        }
        this.mIsInit = true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restore() {
        LogUtils.i(this.TAG, "restore seat:" + this.mSeatStatus);
        if (checkRearSeatOccupied() && this.mSeatStatus == VipSeatStatus.RestoreMoving) {
            this.mSeatViewModel.restorePsnSeatPos();
            this.mHandler.removeMessages(4);
            this.mHandler.sendEmptyMessageDelayed(4, 2000L);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void moveSeatFlat() {
        LogUtils.i(this.TAG, "start move seat flat:" + this.mSeatStatus);
        if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
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
    protected boolean checkSeatMoveAble() {
        return super.checkRearSeatOccupied() && !this.mSeatViewModel.isPsnHeadrestNormal() && (BaseFeatureOption.getInstance().isSupportSeatFlatFrontOccupied() || !this.mSeatViewModel.isPsnSeatOccupied());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkFrontSeatOccupied() {
        return this.mSeatViewModel.isPsnSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean isLayFlatCorrectly() {
        return this.mSeatViewModel.isPsnSeatLieFlat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restoreFinish() {
        LogUtils.i(this.TAG, "restoreFinish:" + this.mSeatStatus);
        callbackStatus(this.mSeatViewModel.isPsnSeatEqualMemory() ? VipSeatStatus.Normal : VipSeatStatus.RestorePause);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    public void stopSeat() {
        this.mSeatViewModel.stopPsnSeatMove();
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
