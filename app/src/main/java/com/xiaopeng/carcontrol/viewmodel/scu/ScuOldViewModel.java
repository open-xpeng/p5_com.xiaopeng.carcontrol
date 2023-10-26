package com.xiaopeng.carcontrol.viewmodel.scu;

import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class ScuOldViewModel extends ScuViewModel {
    private static final String TAG = "D2ScuViewModel";

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModel, com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    public void handleIhbStateChanged(ScuResponse state) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModel, com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    void handleRcwStateChanged(ScuResponse state) {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel, com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setBsdEnable(boolean enable) {
        ScuResponse bsdState = getBsdState();
        LogUtils.i(TAG, "setBsdEnable currentState = " + bsdState, false);
        if (bsdState == ScuResponse.FAIL) {
            NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
            return;
        }
        this.mScuController.setBsdState(bsdState == null ? true : enable);
        handleBsdStateChanged(enable ? ScuResponse.ON : ScuResponse.OFF);
        if (enable) {
            return;
        }
        LogUtils.i(TAG, "BSD set to false, also set DOW to false", false);
        setDowEnable(false, false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel
    protected boolean setDowEnable(boolean enable, boolean needSp) {
        if (enable && !confirmBsdStatus()) {
            LogUtils.i(TAG, "setDow: The bsd function isn't prepared", false);
            return false;
        }
        ScuResponse dowState = getDowState();
        LogUtils.i(TAG, "setDowEnable currentState = " + dowState, false);
        if (dowState == ScuResponse.FAIL && enable) {
            NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
            return false;
        }
        this.mScuController.setDowSw(enable, needSp);
        handleDowStateChanged(enable ? ScuResponse.ON : ScuResponse.OFF);
        return true;
    }

    private boolean confirmBsdStatus() {
        ScuResponse bsdState = getBsdState();
        LogUtils.i(TAG, "confirmBsdStatus currentState = " + bsdState, false);
        if (bsdState != null) {
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[bsdState.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    setBsdEnable(true);
                } else {
                    NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
                    return false;
                }
            }
            return true;
        }
        setBsdEnable(true);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scu.ScuOldViewModel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse;

        static {
            int[] iArr = new int[ScuResponse.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse = iArr;
            try {
                iArr[ScuResponse.ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[ScuResponse.OFF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel, com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setLccEnable(boolean enable) {
        LogUtils.i(TAG, "setLaaEnable currentState = " + getLccState(), false);
        this.mScuController.setLccState(enable);
        if (enable) {
            return;
        }
        LogUtils.i(TAG, "LCC set to false, also set LCS to false");
        this.mScuController.setAlcState(false);
        handleAlcStateChanged(ScuResponse.OFF);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel, com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setAlcEnable(boolean enable) {
        LogUtils.i(TAG, "setLcaEnable currentState = " + getAlcState(), false);
        this.mScuController.setAlcState(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel, com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onBsdStateChanged(int state) {
        ScuResponse fromScuState = ScuResponse.fromScuState(state);
        handleBsdStateChanged(fromScuState);
        if (fromScuState == ScuResponse.ON) {
            boolean z = this.mScuController.getDowState() == 1;
            LogUtils.i(TAG, "onBsdStateChanged to ON, set dow to previous saved mode: " + z, false);
            setDowEnable(z, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel, com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onDowSwChanged(int state) {
        if (state == 1) {
            handleDowStateChanged(ScuResponse.fromScuState(this.mScuController.getDowState()));
        } else {
            handleDowStateChanged(ScuResponse.fromScuState(state));
        }
    }
}
