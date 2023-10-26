package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.carcontrol.bean.Seat;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.dialog.dropdownmenu.DropDownMenuManager;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class D55SeatViewModel extends SeatViewModel {
    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected boolean checkPsnChairMovable() {
        return true;
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
        } else if (!isDrvHeadrestNormal()) {
            LogUtils.w(this.TAG, "The driver seat Headrest is moved, can not save seat data");
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

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean checkChairMovable() {
        if (getCarSpeed() >= 3.0f) {
            NotificationHelper.getInstance().showToast(R.string.smart_chair_speed_error);
            return false;
        }
        return true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreDrvSeatPos() {
        return restoreDrvSeatPos(this.mMsmController.getDrvSeatPosIdx());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreDrvSeatPos(boolean ignoreDrvDoorState, boolean ignoreDrvState) {
        return restoreDrvSeatPos(this.mMsmController.getDrvSeatPosIdx(), ignoreDrvDoorState, ignoreDrvState);
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
            int min = Math.min(50, this.mDriverSeatSavedPos[2]);
            if (CarBaseConfig.getInstance().isSupportVipSeat() && getDSeatTiltPos() < 50 && Math.abs(min - getDSeatTiltPos()) >= 2) {
                this.mIsDrvRestoreSafeChairBack = true;
                setDSeatTiltPos(min);
                return true;
            }
            return controlDriverSeatAuto(this.mDriverSeatSavedPos);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel, com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreDrvSeatPos(int index, boolean ignoreDrvDoorState, boolean ignoreDrvState) {
        return restoreDrvSeatPos(index);
    }
}
