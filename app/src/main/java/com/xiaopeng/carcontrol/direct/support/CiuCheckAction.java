package com.xiaopeng.carcontrol.direct.support;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel;

/* loaded from: classes2.dex */
public class CiuCheckAction implements SupportCheckAction {
    private static final String TAG = "CiuCheckAction";

    @Override // com.xiaopeng.carcontrol.direct.support.SupportCheckAction
    public boolean checkSupport() {
        if (CarBaseConfig.getInstance().isSupportCiuConfig()) {
            try {
                return ((ICiuViewModel) ViewModelManager.getInstance().getViewModelImpl(ICiuViewModel.class)).isCiuExist();
            } catch (Exception e) {
                LogUtils.w(TAG, e.getMessage(), false);
            }
        }
        return false;
    }
}
