package com.xiaopeng.carcontrol.direct.support;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.NedcState;

/* loaded from: classes2.dex */
public class XPilot3CheckAction implements SupportCheckAction {
    private static final String TAG = "XPilot3CheckAction";

    @Override // com.xiaopeng.carcontrol.direct.support.SupportCheckAction
    public boolean checkSupport() {
        if (CarBaseConfig.getInstance().isSupportXpu()) {
            IScuViewModel iScuViewModel = (IScuViewModel) ViewModelManager.getInstance().getViewModelImpl(IScuViewModel.class);
            IXpuViewModel iXpuViewModel = (IXpuViewModel) ViewModelManager.getInstance().getViewModelImpl(IXpuViewModel.class);
            try {
                if (iScuViewModel.isXpuXpilotActivated() && iScuViewModel.getNgpState() == ScuResponse.ON) {
                    return iXpuViewModel.getNedcState() == NedcState.Off;
                }
                return false;
            } catch (Exception e) {
                LogUtils.w(TAG, e.getMessage(), false);
            }
        }
        return false;
    }
}
