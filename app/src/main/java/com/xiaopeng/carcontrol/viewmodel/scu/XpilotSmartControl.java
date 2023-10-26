package com.xiaopeng.carcontrol.viewmodel.scu;

import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;

/* loaded from: classes2.dex */
public class XpilotSmartControl {
    private static final String TAG = "XpilotSmartControl";
    private IMcuController mMcuController;
    private ScuViewModel mScuVm;
    private IVcuController mVcuController;

    /* loaded from: classes2.dex */
    private static class Holder {
        private static final XpilotSmartControl sInstance = new XpilotSmartControl();

        private Holder() {
        }
    }

    public static XpilotSmartControl getInstance() {
        return Holder.sInstance;
    }

    XpilotSmartControl() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        if (carClientWrapper.isCarServiceConnected()) {
            this.mScuVm = (ScuViewModel) ViewModelManager.getInstance().getViewModelImpl(IScuViewModel.class);
            this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
            this.mMcuController = (IMcuController) carClientWrapper.getController(CarClientWrapper.XP_MCU_SERVICE);
        }
    }

    public void enterTelescopeRange() {
        LogUtils.i(TAG, "enterTelescopeRange", false);
        IMcuController iMcuController = this.mMcuController;
        if (iMcuController != null) {
            iMcuController.setGeoFenceStatus(true);
        }
        if (this.mVcuController.getGearLevel() == 4 && FunctionModel.getInstance().isTelescopeRemindAllowed()) {
            this.mScuVm.showCloseMrrDialog();
            FunctionModel.getInstance().setTelescopeRemindTs(System.nanoTime());
        }
    }

    public void exitTelescopeRange() {
        LogUtils.i(TAG, "exitTelescopeRange", false);
        IMcuController iMcuController = this.mMcuController;
        if (iMcuController != null) {
            iMcuController.setGeoFenceStatus(false);
        }
        dismissCloseMrrDialog();
        this.mScuVm.openMrrByUser(1);
    }

    public void dismissCloseMrrDialog() {
        this.mScuVm.dismissCloseMrrDialog();
    }
}
