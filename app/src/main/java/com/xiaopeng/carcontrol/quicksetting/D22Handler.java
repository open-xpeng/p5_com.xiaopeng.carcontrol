package com.xiaopeng.carcontrol.quicksetting;

import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrolmodule.R;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class D22Handler extends D21Handler {
    private ISeatViewModel mSeatVm;

    /* JADX INFO: Access modifiers changed from: package-private */
    public D22Handler(IQuickSettingHandler.IQuickSettingCallback callback) {
        super(callback);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initViewModel() {
        super.initViewModel();
        this.mSeatVm = (ISeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initData() {
        super.initData();
        onSignalCallback(QuickSettingConstants.KEY_DRV_SEAT_POS_INT, Integer.valueOf(this.mSeatVm.getDrvSeatPosIdx()));
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler, com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public List<String> getKeyList() {
        List<String> keyList = super.getKeyList();
        keyList.add(QuickSettingConstants.KEY_DRV_SEAT_POS_INT);
        return keyList;
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler
    public void onHandleCommand(String key, int command) {
        if (QuickSettingConstants.KEY_DRV_SEAT_POS_INT.equals(key)) {
            setDrvSeatIndex(command - 1);
        } else {
            super.onHandleCommand(key, command);
        }
    }

    private void setDrvSeatIndex(int index) {
        if (this.mSeatVm.haveDefaultSeat(index)) {
            this.mSeatVm.restoreDrvSeatPos(index);
            return;
        }
        NotificationHelper.getInstance().showToast(ResUtils.getString(R.string.drv_seat_is_empty_prompt, Integer.valueOf(index + 1)));
        onSignalCallback(QuickSettingConstants.KEY_DRV_SEAT_POS_INT, Integer.valueOf(this.mSeatVm.getDrvSeatPosIdx()));
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.D21Handler, com.xiaopeng.carcontrol.quicksetting.AbstractHandler
    public <T> int onHandleCallback(String key, T value) {
        if (QuickSettingConstants.KEY_DRV_SEAT_POS_INT.equals(key)) {
            return ((Integer) value).intValue() + 1;
        }
        return super.onHandleCallback(key, value);
    }
}
