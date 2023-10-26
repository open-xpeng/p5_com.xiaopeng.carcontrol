package com.xiaopeng.carcontrol.speech;

import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModel;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
class D55CarcontrolSpeechModel extends D2CarControlSpeechModel {
    private static final String TAG = "D55CarcontrolSpeechMode";

    @Override // com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void initExtViewModel(ViewModelManager manager) {
        this.mXpuVm = (XpuViewModel) manager.getViewModelImpl(IXpuViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.speech.D2CarControlSpeechModel, com.xiaopeng.carcontrol.speech.CarControlSpeechModel
    protected void onWindowsControl(int pos, int mode, int percent) {
        LogUtils.d(TAG, "onWindowsControl: pos=" + pos + ", mode=" + mode + ", percent=" + percent, false);
        if (percent < 0 || percent > 100) {
            return;
        }
        if (this.mWinDoorVm.isWindowLockActive()) {
            NotificationHelper.getInstance().showToast(R.string.win_lock_is_active);
            return;
        }
        if (mode == 0) {
            percent = 100 - percent;
        }
        switch (pos) {
            case 0:
                this.mWinDoorVm.setDrvWinMovePos(percent);
                return;
            case 1:
                this.mWinDoorVm.setFRWinPos(percent);
                return;
            case 2:
                this.mWinDoorVm.setRLWinPos(percent);
                return;
            case 3:
                this.mWinDoorVm.setRRWinPos(percent);
                return;
            case 4:
                this.mWinDoorVm.setLeftWinPos(percent);
                return;
            case 5:
                this.mWinDoorVm.setRightWinPos(percent);
                return;
            case 6:
                this.mWinDoorVm.setAllWinPos(percent);
                return;
            case 7:
                this.mWinDoorVm.setFrontWinPos(percent);
                return;
            case 8:
                this.mWinDoorVm.setRearWinPos(percent);
                return;
            default:
                return;
        }
    }
}
