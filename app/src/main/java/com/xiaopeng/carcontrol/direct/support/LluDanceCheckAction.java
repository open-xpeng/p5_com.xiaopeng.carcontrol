package com.xiaopeng.carcontrol.direct.support;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;

/* loaded from: classes2.dex */
public class LluDanceCheckAction implements SupportCheckAction {
    private static final String TAG = "LluDanceCheckAction";

    @Override // com.xiaopeng.carcontrol.direct.support.SupportCheckAction
    public boolean checkSupport() {
        if (CarBaseConfig.getInstance().isSupportLlu()) {
            try {
                return ((ILluViewModel) ViewModelManager.getInstance().getViewModelImpl(ILluViewModel.class)).isLLuSwEnabled();
            } catch (Exception e) {
                LogUtils.w(TAG, e.getMessage(), false);
            }
        }
        return false;
    }
}
