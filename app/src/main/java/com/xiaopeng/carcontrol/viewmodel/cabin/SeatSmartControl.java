package com.xiaopeng.carcontrol.viewmodel.cabin;

import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;

/* loaded from: classes2.dex */
public final class SeatSmartControl {
    private static final String TAG = "SeatSmartControl";
    private final ISeatViewModel mSeatVm;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static SeatSmartControl sInstance = new SeatSmartControl();

        private SingleHolder() {
        }
    }

    public static SeatSmartControl getInstance() {
        return SingleHolder.sInstance;
    }

    private SeatSmartControl() {
        this.mSeatVm = (ISeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
    }

    public void handleEmergencyIgOff() {
        LogUtils.d(TAG, "Emergency ig off, stop pending seat control task", false);
        this.mSeatVm.stopSeatControl();
    }

    public void controlPsnSeatMoveForwardStart() {
        LogUtils.i(TAG, "controlPsnSeatMoveForwardStart", false);
        this.mSeatVm.controlPsnSeatStart(1, 1, 2);
    }

    public void controlPsnSeatMoveForwardEnd() {
        LogUtils.i(TAG, "controlPsnSeatMoveForwardEnd", false);
        this.mSeatVm.controlPsnSeatEnd(1);
    }

    public void onFollowedVehicleLostConfigChanged(boolean forceUpdate) {
        this.mSeatVm.onFollowedVehicleLostConfigChanged(forceUpdate);
    }

    public boolean isDrvSeatEqualMemory() {
        return this.mSeatVm.isDrvSeatEqualMemory();
    }

    public boolean isPsnSeatEqualMemory() {
        return this.mSeatVm.isPsnSeatEqualMemory();
    }

    public void removeSeatDropdownMenu() {
        LogUtils.d(TAG, "Remove and dismiss seat dropdown menu, and not allow to show any more", false);
        this.mSeatVm.removeDropDownRun();
        this.mSeatVm.setSeatUiCtrlResume(true);
    }

    public void resumeSeatDropdownMenu() {
        LogUtils.d(TAG, "Resume show seat dropdown menu", false);
        this.mSeatVm.setSeatUiCtrlResume(false);
    }

    public void setFollowedVehicleLostConfig(String config) {
        this.mSeatVm.setFollowedVehicleLostConfig(config);
    }

    public void restoreSeat(int[] pos) {
        LogUtils.d(TAG, "restore Drv Seat", false);
        this.mSeatVm.restoreDrvSeatAllPositions(pos);
    }

    public void restorePsnSeat() {
        LogUtils.d(TAG, "restore Psn Seat", false);
        this.mSeatVm.restorePsnSeatPos();
    }
}
