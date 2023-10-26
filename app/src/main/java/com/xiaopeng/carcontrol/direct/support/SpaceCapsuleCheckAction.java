package com.xiaopeng.carcontrol.direct.support;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel;

/* loaded from: classes2.dex */
public class SpaceCapsuleCheckAction implements SupportCheckAction {
    @Override // com.xiaopeng.carcontrol.direct.support.SupportCheckAction
    public boolean checkSupport() {
        if (CarBaseConfig.getInstance().isSupportVipSeat()) {
            SpaceCapsuleViewModel spaceCapsuleViewModel = (SpaceCapsuleViewModel) ViewModelManager.getInstance().getViewModelImpl(ISpaceCapsuleViewModel.class);
            LogUtils.i("SpaceCapsuleCheckAction", "getCurrentSubMode:" + spaceCapsuleViewModel.getCurrentSubMode());
            return spaceCapsuleViewModel.getCurrentSubMode() != -1;
        }
        return false;
    }
}
