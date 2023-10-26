package com.xiaopeng.carcontrol.viewmodel.space;

import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class VipDrvSeatControl extends VipSeatControl {

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static final VipDrvSeatControl INSTANCE = new VipDrvSeatControl();

        private SingleHolder() {
        }
    }

    public static VipDrvSeatControl getInstance() {
        return SingleHolder.INSTANCE;
    }

    private VipDrvSeatControl() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void init() {
        if (this.mIsInit) {
            return;
        }
        super.init();
        observerDrvSeatMove();
        observerDrvHeadrest();
        if (!BaseFeatureOption.getInstance().isSupportSeatFlatFrontOccupied()) {
            observerDrvSeatOccupied();
        }
        this.mIsInit = true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkSeatMoveAble() {
        return super.checkRearSeatOccupied() && !this.mSeatViewModel.isDrvHeadrestNormal() && (BaseFeatureOption.getInstance().isSupportSeatFlatFrontOccupied() || !this.mSeatViewModel.isDrvSeatOccupied());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkFrontSeatOccupied() {
        return this.mSeatViewModel.isDrvSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void moveSeatFlat() {
        LogUtils.d(this.TAG, "moveSeatFlat:" + this.mSeatStatus);
        if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
            if (BaseFeatureOption.getInstance().isEcuDoSeatLayFlat()) {
                this.mSeatViewModel.setDrvSeatLayFlat();
            } else if (this.mSeatViewModel.getDSeatTiltPos() < 55 && this.mSeatViewModel.getDSeatHorzPos() < 95) {
                this.mIsDrvMoveHor = true;
                this.mSeatViewModel.setDSeatHorzPos(100);
            } else {
                this.mSeatViewModel.setDrvSeatAllPositions(100, VIP_SEAT_VER_POS, 0, this.mSeatViewModel.getDSeatLegPos(), this.mSeatViewModel.getDSeatCushionPos());
            }
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 2000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    public void stopSeat() {
        this.mSeatViewModel.stopDrvSeatMove();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restore() {
        LogUtils.i(this.TAG, "restore seat:" + this.mSeatStatus);
        if (checkRearSeatOccupied() && this.mSeatStatus == VipSeatStatus.RestoreMoving) {
            this.mSeatViewModel.restoreDrvSeatPos(true, true);
            this.mHandler.removeMessages(4);
            this.mHandler.sendEmptyMessageDelayed(4, 2000L);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean isLayFlatCorrectly() {
        return this.mSeatViewModel.isDrvSeatLieFlat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restoreFinish() {
        LogUtils.i(this.TAG, "restoreFinish:" + this.mSeatStatus);
        callbackStatus(this.mSeatViewModel.isDrvSeatEqualMemory() ? VipSeatStatus.Normal : VipSeatStatus.RestorePause);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void onSeatPosChanged() {
        if (this.mSeatStatus == VipSeatStatus.Flat && !isLayFlatCorrectly()) {
            callbackStatus(VipSeatStatus.Normal);
            return;
        }
        if (this.mIsDrvMoveHor && this.mSeatStatus == VipSeatStatus.FlatMoving && this.mSeatViewModel.getDSeatHorzPos() > 95) {
            this.mIsDrvMoveHor = false;
            this.mSeatViewModel.setDrvSeatAllPositions(100, VIP_SEAT_VER_POS, 0, this.mSeatViewModel.getDSeatLegPos(), this.mSeatViewModel.getDSeatCushionPos());
        }
        super.onSeatPosChanged();
    }
}
