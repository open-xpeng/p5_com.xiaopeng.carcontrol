package com.xiaopeng.carcontrol.viewmodel.space;

import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class VipCapsuleSeatControl extends VipSeatControl {
    private static final String TAG = "VipCapsuleSeatControl";

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void seatFlatSpeak() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static final VipCapsuleSeatControl INSTANCE = new VipCapsuleSeatControl();

        private SingleHolder() {
        }
    }

    public static VipCapsuleSeatControl getInstance() {
        return SingleHolder.INSTANCE;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void init() {
        if (this.mIsInit) {
            return;
        }
        super.init();
        observerDrvSeatMove();
        observerPsnSeatMove();
        observerDrvHeadrest();
        observerPsnHeadrest();
        if (!BaseFeatureOption.getInstance().isSupportSeatFlatFrontOccupied()) {
            observerDrvSeatOccupied();
            observerPsnSeatOccupied();
        }
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
        if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
            if (this.mStatusChangedListener != null) {
                this.mStatusChangedListener.onSeatStartMove(true);
            }
            if (BaseFeatureOption.getInstance().isEcuDoSeatLayFlat()) {
                this.mSeatViewModel.setDrvSeatLayFlat();
                this.mSeatViewModel.setPsnSeatLayFlat();
            } else {
                if (this.mSeatViewModel.getDSeatTiltPos() < 55 && this.mSeatViewModel.getDSeatHorzPos() < 95) {
                    this.mIsDrvMoveHor = true;
                    this.mSeatViewModel.setDSeatHorzPos(100);
                } else {
                    this.mSeatViewModel.setDrvSeatAllPositions(100, VIP_SEAT_VER_POS, 0, this.mSeatViewModel.getDSeatLegPos(), this.mSeatViewModel.getDSeatCushionPos());
                }
                if (this.mSeatViewModel.getPSeatTiltPos() < 55 && this.mSeatViewModel.getPSeatHorzPos() < 95) {
                    this.mIsPsnMoveHor = true;
                    this.mSeatViewModel.setPSeatHorzPos(100);
                } else {
                    this.mSeatViewModel.setPsnSeatAllPositions(100, VIP_SEAT_VER_POS, 0, 0);
                }
            }
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 2000L);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void stopSeat() {
        this.mSeatViewModel.stopDrvSeatMove();
        this.mSeatViewModel.stopPsnSeatMove();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restore() {
        LogUtils.i(TAG, "restore seat:" + this.mSeatStatus);
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
        return super.checkRearSeatOccupied() && !this.mSeatViewModel.isDrvHeadrestNormal() && !this.mSeatViewModel.isPsnHeadrestNormal() && (BaseFeatureOption.getInstance().isSupportSeatFlatFrontOccupied() || !(this.mSeatViewModel.isDrvSeatOccupied() || this.mSeatViewModel.isPsnSeatOccupied()));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void restoreFinish() {
        LogUtils.i(TAG, "restoreFinish:" + this.mSeatStatus);
        callbackStatus((this.mSeatViewModel.isDrvSeatEqualMemory() && this.mSeatViewModel.isPsnSeatEqualMemory()) ? VipSeatStatus.Normal : VipSeatStatus.RestorePause);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    public boolean isLayFlatCorrectly() {
        return this.mSeatViewModel.isDrvSeatLieFlat() && this.mSeatViewModel.isPsnSeatLieFlat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected boolean checkFrontSeatOccupied() {
        return this.mSeatViewModel.isDrvSeatOccupied() || this.mSeatViewModel.isPsnSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl
    protected void onSeatPosChanged() {
        if (this.mIsDrvMoveHor && this.mSeatStatus == VipSeatStatus.FlatMoving && this.mSeatViewModel.getDSeatHorzPos() > 95) {
            this.mIsDrvMoveHor = false;
            this.mSeatViewModel.setDrvSeatAllPositions(100, VIP_SEAT_VER_POS, 0, this.mSeatViewModel.getDSeatLegPos(), this.mSeatViewModel.getDSeatCushionPos());
        }
        if (this.mIsPsnMoveHor && this.mSeatStatus == VipSeatStatus.FlatMoving && this.mSeatViewModel.getPSeatHorzPos() > 95) {
            this.mIsPsnMoveHor = false;
            this.mSeatViewModel.setPsnSeatAllPositions(100, VIP_SEAT_VER_POS, 0, 0);
        }
        super.onSeatPosChanged();
    }
}
