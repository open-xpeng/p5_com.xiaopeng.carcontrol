package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.carcontrol.bean.Seat;
import com.xiaopeng.carcontrol.config.DxCarConfig;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.dialog.dropdownmenu.DropDownMenuManager;

/* loaded from: classes2.dex */
public class D2SeatViewModel extends SeatViewModel {
    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isSupportMSMP() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isSupportMsm() {
        return true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isDrvBeltUnbuckled() {
        return this.mBcmController.getDrvBeltStatus() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setEsbMode(final boolean enable) {
        if (isSupportEsbConfig()) {
            if ("2".equals(this.mFollowedVehicleLostConfig)) {
                LogUtils.i(this.TAG, "Use new config, force set electric belt sw to true, and set SCU OTA tag with: " + (enable ? 2 : 0));
                this.mBcmController.setEsbEnable(true, enable);
                this.mScuController.setScuOtaTagStatus(enable ? 2 : 0);
            } else {
                LogUtils.i(this.TAG, "Use old config, set electric belt sw with param: " + enable + ", and set SCU OTA tag with: " + (enable ? 3 : 1));
                this.mBcmController.setEsbEnable(enable);
                this.mScuController.setScuOtaTagStatus(enable ? 3 : 1);
            }
        } else {
            this.mBcmController.setEsbEnable(enable);
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$D2SeatViewModel$fZffTSbOegp37jftFobCAe0srwo
            @Override // java.lang.Runnable
            public final void run() {
                D2SeatViewModel.this.lambda$setEsbMode$0$D2SeatViewModel(enable);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean getEsbMode() {
        if ("2".equals(this.mFollowedVehicleLostConfig)) {
            return this.mBcmController.getEsbModeSp();
        }
        return this.mBcmController.isEsbEnabled();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    /* renamed from: handleEsbChanged */
    public void lambda$setEsbMode$0$D2SeatViewModel(boolean enabled) {
        if ("2".equals(this.mFollowedVehicleLostConfig)) {
            this.mEsbMode.postValue(Boolean.valueOf(this.mBcmController.getEsbModeSp()));
        } else {
            this.mEsbMode.postValue(Boolean.valueOf(enabled));
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setBackBeltSw(boolean on) {
        this.mBcmController.setRsbWarning(on);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean getBackBeltSw() {
        return this.mBcmController.isRsbWarningEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handleWelcomeModeChanged(boolean enabled) {
        this.mSeatWelcomeMode.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean haveDefaultSeat(int pos) {
        return this.mMsmController.getDrvSeatPos(pos) != null;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    boolean isSupportEsbConfig() {
        return DxCarConfig.getInstance().isSupportLcc() && DxCarConfig.getInstance().isSupportEsb();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void exitMeditationState() {
        if (isDrvDoorOpened()) {
            LogUtils.d(this.TAG, "Drive door opened, do not recovery seat tilt to saved position", false);
            return;
        }
        int[] drvSeatPos = this.mMsmController.getDrvSeatPos(this.mMsmController.getDrvSeatPosIdx());
        if (drvSeatPos == null) {
            LogUtils.d(this.TAG, "Saved seat position is null", false);
        } else if (checkChairMovable(false, true)) {
            this.mMsmController.setDriverAllPositions(drvSeatPos[0], drvSeatPos[1], drvSeatPos[2], drvSeatPos[3], drvSeatPos[4]);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel
    public boolean memoryDrvSeatData() {
        return memoryDrvSeatData(this.mMsmController.getDrvSeatPosIdx());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean memoryDrvSeatData(int index) {
        if (!isSupportMsm()) {
            LogUtils.w(this.TAG, "Current car does not support MSMD feature", false);
            return false;
        } else if (this.mIsDriverSeatManualMoving) {
            LogUtils.w(this.TAG, "The driver seat is under adjusting, can not save seat data");
            return false;
        } else if ((index < 0 && index != -1) || index >= 3) {
            LogUtils.w(this.TAG, "The memory drv seat index is invalid: " + index, false);
            return false;
        } else {
            LogUtils.d(this.TAG, "mDriverSeatData:" + this.mDriverSeatData.toString() + ",index:" + index);
            this.mMsmController.saveDrvSeatPos(index, this.mDriverSeatData.getSeatPosition());
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_DRV_SEAT_POS_INT, Integer.valueOf(index));
            setDriverAllPositionsToMcu();
            this.mDriverSeatSavedPos = this.mDriverSeatData.getSeatPosition();
            return true;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreDrvSeatPos() {
        return restoreDrvSeatPos(this.mMsmController.getDrvSeatPosIdx());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreDrvSeatPos(int index) {
        if (!isSupportMsm()) {
            LogUtils.w(this.TAG, "Current car does not support MSMD feature", false);
            return false;
        } else if (!checkChairMovable()) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_DRV_SEAT_POS_INT, Integer.valueOf(getDrvSeatPosIdx()));
            return false;
        } else if ((index < 0 && index != -1) || index >= 3) {
            LogUtils.w(this.TAG, "restoreDrvSeatPos The memory drv seat index is invalid: " + index, false);
            return false;
        } else {
            int[] drvSeatPos = this.mMsmController.getDrvSeatPos(index);
            if (drvSeatPos == null && !CarStatusUtils.isE28CarType()) {
                LogUtils.d(this.TAG, "restoreDrvSeatPos mDriverSeatSavedPos no save seat data index=" + index);
                return true;
            }
            if (this.mSeatControlHandler != null) {
                this.mSeatControlHandler.removeCallbacks(this.mDropDownRun);
            }
            DropDownMenuManager.getInstance().dismiss("seat_mirror_menu");
            this.mDriverSeatSavedPos = Seat.fromPosition(drvSeatPos).getSeatPosition();
            this.mMsmController.saveDrvSeatPos(index, this.mDriverSeatSavedPos);
            setDriverAllPositionsToMcu();
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_DRV_SEAT_POS_INT, Integer.valueOf(index));
            LogUtils.d(this.TAG, "restoreDrvSeatPos mDriverSeatSavedPos index=" + index + "hor:" + this.mDriverSeatSavedPos[0] + ",ver:" + this.mDriverSeatSavedPos[1] + ",tilt:" + this.mDriverSeatSavedPos[2] + ",leg:" + this.mDriverSeatSavedPos[3]);
            return controlDriverSeatAuto(this.mDriverSeatSavedPos);
        }
    }
}
