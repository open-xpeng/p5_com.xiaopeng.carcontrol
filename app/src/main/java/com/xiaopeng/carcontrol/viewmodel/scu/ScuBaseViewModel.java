package com.xiaopeng.carcontrol.viewmodel.scu;

import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IScuController;
import com.xiaopeng.carcontrol.carmanager.controller.IXpuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public abstract class ScuBaseViewModel implements IScuViewModel, IScuController.Callback {
    private static final String LSS_STATE_SW = "lcc_ldw_type";
    private static final String TAG = "ScuBaseViewModel";
    IScuController mScuController;
    IXpuController mXpuController;
    private final boolean mIsSupportDomainXpu = CarBaseConfig.getInstance().isSupportXpuDomainCtrl();
    private final Object mDomainBsdLock = new Object();
    private final Runnable mDomainBsdStTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.scu.-$$Lambda$ScuBaseViewModel$Y4DBIdkDeLyvFYy2DYw08_-DXEM
        @Override // java.lang.Runnable
        public final void run() {
            ScuBaseViewModel.this.lambda$new$0$ScuBaseViewModel();
        }
    };
    private final Object mDomainDowLock = new Object();
    private final Runnable mDomainDowStTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.scu.-$$Lambda$ScuBaseViewModel$YtpQ0U3rMUmGg5TpYr8ZHhWMK-Y
        @Override // java.lang.Runnable
        public final void run() {
            ScuBaseViewModel.this.lambda$new$1$ScuBaseViewModel();
        }
    };
    private final Object mDomainRcwLock = new Object();
    private final Runnable mDomainRcwStTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.scu.-$$Lambda$ScuBaseViewModel$d9-nuComQAM6_GTjES8I2CUgrts
        @Override // java.lang.Runnable
        public final void run() {
            ScuBaseViewModel.this.lambda$new$2$ScuBaseViewModel();
        }
    };
    private final Object mDomainRctaLock = new Object();
    private final Runnable mDomainRctaStTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.scu.-$$Lambda$ScuBaseViewModel$Ahxl2ibahpiCrDGphY5iLjBnqTE
        @Override // java.lang.Runnable
        public final void run() {
            ScuBaseViewModel.this.lambda$new$3$ScuBaseViewModel();
        }
    };

    private int convertSpecialSasState(int slif, int islc) {
        if (slif == 1) {
            return islc != 0 ? 0 : 1;
        } else if (slif != 2) {
            return (slif == 6 || slif == 7) ? 4 : 0;
        } else if (islc != 0) {
            if (islc == 1 || islc == 2 || islc == 3 || islc == 4) {
                return 3;
            }
            return (islc == 6 || islc == 7) ? 5 : 0;
        } else {
            return 2;
        }
    }

    abstract void handleAccStateChanged(ScuResponse state);

    abstract void handleAccStateForMirrorChanged(ScuResponse state);

    abstract void handleAlcStateChanged(ScuResponse state);

    abstract void handleAutoParkStateChanged(ScuResponse state);

    abstract void handleBsdStateChanged(ScuResponse state);

    abstract void handleDowStateChanged(ScuResponse state);

    abstract void handleDsmStateChanged(DsmState fromScuState);

    abstract void handleElkStateChanged(ScuResponse state);

    abstract void handleFcwSenChanged(FcwSensitivity state);

    abstract void handleFcwStateChanged(ScuResponse fromScuState);

    /* JADX INFO: Access modifiers changed from: package-private */
    public void handleIhbStateChanged(ScuResponse state) {
    }

    abstract void handleIslaConfirmModeChanged(int confirmMode);

    abstract void handleIslaSpdRangeChanged(ScuIslaSpdRange spdRange);

    abstract void handleIslaStateChanged(ScuIslaMode state);

    abstract void handleIslcStateChanged(ScuResponse state);

    abstract void handleKeyParkSwChanged(ScuResponse state);

    abstract void handleLccStateChanged(ScuResponse state);

    abstract void handleLdwStateChanged(ScuResponse state);

    abstract void handleLkaStateChanged(ScuResponse state);

    abstract void handleLssStateChanged(int state);

    abstract void handleMemParkStateChanged(ApResponse state);

    abstract void handleNgpChangeLaneModeChanged(NgpChangeLaneMode mode);

    abstract void handleNgpQuickLaneChanged(boolean enabled);

    abstract void handleNgpRemindModeChanged(int mode);

    abstract void handleNgpStateChanged(ScuResponse state);

    abstract void handleNgpTipWindowChanged(boolean enabled);

    abstract void handleNgpTruckOffsetChanged(boolean enabled);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: handleNgpVoiceChangeLane */
    public abstract void lambda$setNgpVoiceChangeLane$4$ScuBaseViewModel(boolean enable);

    abstract void handlePhoneParkSwChanged(ScuResponse state);

    abstract void handleRctaStateChanged(ScuResponse state);

    abstract void handleRcwStateChanged(ScuResponse state);

    abstract void handleSpecialSasStateChanged(int state);

    abstract void handleXpuXPilotStateChanged(int state);

    public /* synthetic */ void lambda$new$0$ScuBaseViewModel() {
        ScuResponse bsdState = getBsdState();
        LogUtils.i(TAG, "onDomainBsd Final St: " + bsdState);
        handleBsdStateChanged(bsdState);
    }

    public /* synthetic */ void lambda$new$1$ScuBaseViewModel() {
        ScuResponse dowState = getDowState();
        LogUtils.i(TAG, "onDomainDow Final St: " + dowState);
        handleDowStateChanged(dowState);
    }

    public /* synthetic */ void lambda$new$2$ScuBaseViewModel() {
        ScuResponse rcwState = getRcwState();
        LogUtils.i(TAG, "onDomain Rcw Final St: " + rcwState);
        handleRcwStateChanged(rcwState);
    }

    public /* synthetic */ void lambda$new$3$ScuBaseViewModel() {
        ScuResponse rctaState = getRctaState();
        LogUtils.i(TAG, "onDomainRcta Final St: " + rctaState);
        handleRctaStateChanged(rctaState);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ScuBaseViewModel() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mScuController = (IScuController) carClientWrapper.getController(CarClientWrapper.XP_SCU_SERVICE);
        this.mXpuController = (IXpuController) carClientWrapper.getController(CarClientWrapper.XP_XPU_SERVICE);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setFcwEnable() {
        ScuResponse fcwState = getFcwState();
        LogUtils.i(TAG, "setFcwEnable currentState = " + fcwState, false);
        if (fcwState != null) {
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[fcwState.ordinal()];
            if (i == 1) {
                this.mScuController.setFcwState(false);
                return;
            } else if (i == 2) {
                this.mScuController.setFcwState(true);
                return;
            } else {
                NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
                return;
            }
        }
        this.mScuController.setFcwState(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getFcwState() {
        return ScuResponse.fromScuState(this.mScuController.getFcwState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public FcwSensitivity getFcwSensitivity() {
        return FcwSensitivity.fromXpuState(this.mScuController.getFcwSensitivity());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setFcwSensitivity(FcwSensitivity value) {
        this.mScuController.setFcwSensitivity(FcwSensitivity.toXpuCmd(value));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setRcwEnable(boolean enable) {
        ScuResponse rcwState = getRcwState();
        LogUtils.i(TAG, "setRcwEnable currentState = " + rcwState, false);
        if (rcwState != null) {
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[rcwState.ordinal()];
            if (i == 1) {
                this.mScuController.setRcwState(false);
                return;
            } else if (i == 2) {
                this.mScuController.setRcwState(true);
                return;
            } else {
                NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
                return;
            }
        }
        this.mScuController.setRcwState(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getRcwState() {
        return ScuResponse.fromScuState(this.mScuController.getRcwState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setRctaEnable() {
        ScuResponse rctaState = getRctaState();
        LogUtils.i(TAG, "setRctaEnable currentState = " + rctaState, false);
        if (rctaState != null) {
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[rctaState.ordinal()];
            if (i == 1) {
                this.mScuController.setRctaState(false);
                return;
            } else if (i == 2) {
                this.mScuController.setRctaState(true);
                return;
            } else {
                NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
                return;
            }
        }
        this.mScuController.setRctaState(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getRctaState() {
        return ScuResponse.fromScuState(this.mScuController.getRctaState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setLdwEnable() {
        ScuResponse ldwState = getLdwState();
        LogUtils.i(TAG, "setLdwEnable currentState = " + ldwState, false);
        if (ldwState != null) {
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[ldwState.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    this.mScuController.setLdwState(true);
                    return;
                } else {
                    NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
                    return;
                }
            }
            this.mScuController.setLdwState(false);
            if (CarBaseConfig.getInstance().isSupportXpilotLccBindLdw()) {
                setLccEnable(false);
                return;
            }
            return;
        }
        this.mScuController.setLdwState(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getLdwState() {
        return ScuResponse.fromScuState(this.mScuController.getLdwState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getLkaState() {
        return ScuResponse.fromScuState(this.mScuController.getLkaState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setElkEnable(boolean enable) {
        this.mScuController.setElkState(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getElkState() {
        return ScuResponse.fromScuState(this.mScuController.getElkState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setLssMode(ScuLssMode lssMode) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode[lssMode.ordinal()];
        if (i == 1) {
            this.mScuController.setLssMode(0);
            if (!CarBaseConfig.getInstance().isSupportXpilotLccBindLss() || getLccState() == ScuResponse.OFF) {
                return;
            }
            setLccEnable(false);
        } else if (i == 2) {
            this.mScuController.setLssMode(1);
        } else if (i == 3) {
            this.mScuController.setLssMode(2);
        } else if (i != 4) {
        } else {
            this.mScuController.setLssMode(3);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuLssMode getLssMode() {
        if (BaseFeatureOption.getInstance().isNewLssSignal()) {
            return convertLssState(this.mScuController.getLssMode());
        }
        return convertLssState(getLdwState(), getLkaState());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ScuLssMode convertLssState(int state) {
        Settings.System.putInt(App.getInstance().getContentResolver(), LSS_STATE_SW, state);
        if (state != 1) {
            if (state != 2) {
                if (state != 3) {
                    if (state == 4) {
                        return ScuLssMode.Unavailable;
                    }
                    return ScuLssMode.Off;
                }
                return ScuLssMode.All;
            }
            return ScuLssMode.Lka;
        }
        return ScuLssMode.Ldw;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ScuLssMode convertLssState(ScuResponse ldwState, ScuResponse lkaState) {
        if (!CarBaseConfig.getInstance().isLssCertification()) {
            if (lkaState == ScuResponse.ON && (ldwState == ScuResponse.OFF || ldwState == ScuResponse.FAIL)) {
                return ScuLssMode.Lka;
            }
            if (ldwState == ScuResponse.ON && (lkaState == ScuResponse.OFF || lkaState == ScuResponse.FAIL)) {
                return ScuLssMode.Ldw;
            }
            return ScuLssMode.Off;
        }
        ScuResponse elkState = getElkState();
        if (lkaState == ScuResponse.ON && ldwState == ScuResponse.ON && elkState == ScuResponse.ON) {
            Settings.System.putInt(App.getInstance().getContentResolver(), LSS_STATE_SW, ScuLssMode.All.ordinal());
            return ScuLssMode.All;
        } else if (lkaState == ScuResponse.ON && ldwState == ScuResponse.OFF && elkState == ScuResponse.ON) {
            Settings.System.putInt(App.getInstance().getContentResolver(), LSS_STATE_SW, ScuLssMode.Lka.ordinal());
            return ScuLssMode.Lka;
        } else if (lkaState == ScuResponse.OFF && ldwState == ScuResponse.ON && elkState == ScuResponse.OFF) {
            Settings.System.putInt(App.getInstance().getContentResolver(), LSS_STATE_SW, ScuLssMode.Ldw.ordinal());
            return ScuLssMode.Ldw;
        } else {
            Settings.System.putInt(App.getInstance().getContentResolver(), LSS_STATE_SW, ScuLssMode.Off.ordinal());
            return ScuLssMode.Off;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setBsdEnable(boolean enable) {
        ScuResponse bsdState = getBsdState();
        LogUtils.i(TAG, "setBsdEnable currentState = " + bsdState, false);
        if (bsdState == ScuResponse.FAIL) {
            NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
            return;
        }
        if (bsdState == null) {
            enable = true;
        }
        this.mScuController.setBsdState(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getBsdState() {
        return ScuResponse.fromScuState(this.mScuController.getBsdState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setDowEnable(boolean enable) {
        setDowEnable(enable, true);
    }

    protected boolean setDowEnable(boolean enable, boolean needSp) {
        ScuResponse dowState = getDowState();
        LogUtils.i(TAG, "setDowEnable currentState = " + dowState, false);
        if (dowState == ScuResponse.FAIL && enable) {
            NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
            return false;
        }
        this.mScuController.setDowSw(enable, needSp);
        return true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getDowState() {
        return ScuResponse.fromScuState(this.mScuController.getDowState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setIhbEnable(boolean enable, boolean checkAndToast) {
        if (checkAndToast) {
            if (BaseFeatureOption.getInstance().isIhbDependOnXPilot()) {
                if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
                    NotificationHelper.getInstance().showToast(R.string.xpilot_srr_missed_open_fail);
                    return;
                }
                int xpuXpilotState = getXpuXpilotState();
                if (xpuXpilotState == 0) {
                    NotificationHelper.getInstance().showToast(R.string.ihb_xpilot_st_init);
                    return;
                } else if (xpuXpilotState == 2) {
                    NotificationHelper.getInstance().showToast(R.string.ihb_purchase_help_1);
                    return;
                } else if (xpuXpilotState == 3) {
                    NotificationHelper.getInstance().showToast(R.string.ihb_xpilot_st_not_activated);
                    return;
                }
            } else if (CarBaseConfig.getInstance().isSupportSrrMiss()) {
                NotificationHelper.getInstance().showToast(R.string.xpilot_srr_missed_open_fail2);
                return;
            }
        }
        ScuResponse ihbState = getIhbState();
        LogUtils.i(TAG, "setIhbEnable currentState = " + ihbState, false);
        if (checkAndToast && ihbState == ScuResponse.FAIL) {
            NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
        } else if (!checkAndToast || checkIfXpuAvailable()) {
            if (enable) {
                NotificationHelper.getInstance().showToast(R.string.ihb_switch_open);
                this.mScuController.setIhbState(true);
                return;
            }
            NotificationHelper.getInstance().showToast(R.string.ihb_switch_close);
            this.mScuController.setIhbState(false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getIhbState() {
        return ScuResponse.fromScuState(this.mScuController.getIhbState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setAutoParkSw(boolean enable) {
        this.mScuController.setAutoParkSw(enable);
        if (enable) {
            return;
        }
        this.mScuController.setMemoryParkSw(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getAutoParkSw() {
        return ScuResponse.fromScuState(this.mScuController.getAutoParkSw());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setMemoryParkSw(boolean enable) {
        this.mScuController.setMemoryParkSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ApResponse getMemoryParkSw() {
        return ApResponse.fromScuState(this.mScuController.getMemoryParkSw());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public int getVpaState() {
        return this.mScuController.getMemoryParkSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setPhoneParkSw(boolean enable) {
        this.mScuController.setPhoneParkSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getPhoneParkSw() {
        return ScuResponse.fromScuState(this.mScuController.getPhoneParkSw());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setKeyParkSw(boolean enable) {
        this.mScuController.setKeyParkSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getKeyParkSw() {
        return ScuResponse.fromScuState(this.mScuController.getKeyParkSw());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setXngpEnable(boolean enable) {
        setLccEnable(enable);
        if (enable && this.mScuController.isFirstOpenXngpSw()) {
            this.mXpuController.setLccTrafficLightSw(true);
            this.mScuController.setFirstOpenXngpSw(false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getXngpState() {
        return getLccState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setLccEnable(boolean enable) {
        ScuResponse lccState = getLccState();
        LogUtils.i(TAG, "setLccEnable currentState = " + lccState, false);
        if (lccState != null) {
            if (lccState == ScuResponse.FAIL) {
                NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
                return;
            } else if (lccState == ScuResponse.UNAVAILABLE) {
                NotificationHelper.getInstance().showToast(R.string.xpilot_xpu_unavailable);
                return;
            } else {
                this.mScuController.setLccState(enable);
                if (!enable) {
                    LogUtils.i(TAG, "LCC set to false, also set ALC and NGP to false", false);
                    this.mScuController.setAlcState(false);
                    this.mScuController.setNgpEnable(false);
                    if (CarBaseConfig.getInstance().isSupportCNgp()) {
                        this.mXpuController.setCityNgpSw(false);
                        return;
                    }
                    return;
                } else if (CarBaseConfig.getInstance().isSupportXpilotLccBindLss() && getLssMode() == ScuLssMode.Off) {
                    setLssMode(ScuLssMode.Ldw);
                    return;
                } else {
                    return;
                }
            }
        }
        this.mScuController.setLccState(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getLccState() {
        return ScuResponse.fromScuState(this.mScuController.getLccState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getLccSafeExamResult() {
        if (CarBaseConfig.getInstance().isSupportLidar() && BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
            return this.mScuController.getSuperLccExamResult(null);
        }
        return this.mScuController.getLccSafeExamResult(null) || this.mScuController.getSuperLccExamResult(null);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getApaSafeExamResult() {
        return this.mScuController.getApaSafeExamResult(null);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getAccState() {
        return ScuResponse.fromScuState(this.mScuController.getAccState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getAccStateForMirror() {
        return ScuResponse.fromScuState(this.mScuController.getAccStateForMirror());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getLccWorkSt() {
        return ScuResponse.fromScuState(this.mScuController.getLccWorkSt());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setIslcEnable() {
        ScuResponse islcState = getIslcState();
        LogUtils.i(TAG, "setIslcEnable currentState = " + islcState, false);
        if (islcState != null) {
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[islcState.ordinal()];
            if (i == 1) {
                this.mScuController.setIslcState(false);
                return;
            } else if (i == 2) {
                this.mScuController.setIslcState(true);
                return;
            } else {
                NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
                return;
            }
        }
        this.mScuController.setIslcState(true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getIslcState() {
        return ScuResponse.fromScuState(this.mScuController.getIslcState());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scu.ScuBaseViewModel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse;

        static {
            int[] iArr = new int[ScuIslaMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode = iArr;
            try {
                iArr[ScuIslaMode.OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[ScuIslaMode.SLA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[ScuIslaMode.ISLA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[ScuLssMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode = iArr2;
            try {
                iArr2[ScuLssMode.Off.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode[ScuLssMode.Ldw.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode[ScuLssMode.Lka.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuLssMode[ScuLssMode.All.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr3 = new int[ScuResponse.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse = iArr3;
            try {
                iArr3[ScuResponse.ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuResponse[ScuResponse.OFF.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setIslaMode(ScuIslaMode islaMode) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scu$ScuIslaMode[islaMode.ordinal()];
        if (i == 1) {
            this.mScuController.setIslaSw(0);
        } else if (i == 2) {
            this.mScuController.setIslaSw(1);
        } else if (i != 3) {
        } else {
            this.mScuController.setIslaSw(2);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuIslaMode getIslaMode() {
        if (CarBaseConfig.getInstance().isSupportOldIsla()) {
            ScuResponse slaState = getSlaState();
            LogUtils.i(TAG, "getOldIslaSt: " + slaState, false);
            if (slaState == ScuResponse.ON) {
                int oldIslaSw = this.mScuController.getOldIslaSw();
                if (oldIslaSw == 1) {
                    return ScuIslaMode.SLA;
                }
                if (oldIslaSw == 2) {
                    return ScuIslaMode.ISLA;
                }
            }
            return ScuIslaMode.OFF;
        }
        return convertIslaState(getSlaState(), getIslaState());
    }

    private ScuIslaMode convertIslaState(ScuResponse slaSt, ScuResponse islaSt) {
        LogUtils.i(TAG, "convertIsla slaSt: " + slaSt + ", islaSt: " + islaSt, false);
        if (slaSt == ScuResponse.ON) {
            if (islaSt == ScuResponse.ON) {
                return ScuIslaMode.ISLA;
            }
            return ScuIslaMode.SLA;
        } else if (slaSt == ScuResponse.OFF) {
            if (islaSt == ScuResponse.OFF) {
                return ScuIslaMode.OFF;
            }
            return ScuIslaMode.FAIL;
        } else {
            return ScuIslaMode.FAIL;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getSlaState() {
        return ScuResponse.fromScuState(this.mScuController.getSlaState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getIslaState() {
        return ScuResponse.fromScuState(this.mScuController.getIslaState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setIslaSpdRange(ScuIslaSpdRange spdRange) {
        this.mScuController.setIslaSpdRange(spdRange.toScuCmd());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuIslaSpdRange getIslaSpdRange() {
        return ScuIslaSpdRange.fromScuState(this.mScuController.getIslaSpdRange());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setIslaConfirmMode(boolean enable) {
        this.mScuController.setIslaConfirmMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getIslaConfirmMode() {
        return this.mScuController.getIslaConfirmMode() == 2;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setAlcEnable(boolean enable) {
        ScuResponse alcState = getAlcState();
        LogUtils.i(TAG, "setAlcEnable currentState = " + alcState, false);
        if (enable && getLccState() != ScuResponse.ON) {
            LogUtils.i(TAG, "setAlcEnable: The lcc function isn't prepared", false);
        } else if (alcState != null) {
            if (alcState == ScuResponse.FAIL) {
                NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail);
            } else if (alcState == ScuResponse.UNAVAILABLE) {
                NotificationHelper.getInstance().showToast(R.string.xpilot_xpu_unavailable);
            } else {
                this.mScuController.setAlcState(enable);
                if (enable) {
                    return;
                }
                LogUtils.i(TAG, "ALC set to false, also set NGP to false", false);
                this.mScuController.setNgpEnable(false);
                if (CarBaseConfig.getInstance().isSupportCNgp()) {
                    this.mXpuController.setCityNgpSw(false);
                }
            }
        } else {
            this.mScuController.setAlcState(true);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getAlcState() {
        return ScuResponse.fromScuState(this.mScuController.getAlcState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean isXpuXpilotActivated() {
        return this.mScuController.isXpuXpilotActivated();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public int getXpuXpilotState() {
        return this.mScuController.getXpuXpilotState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getNgpSafeExamResult() {
        return this.mScuController.getNgpSafeExamResult(null);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getMemParkSafeExamResult() {
        if (CarBaseConfig.getInstance().isSupportLidar() && BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
            return this.mScuController.getSuperVpaExamResult(null);
        }
        return this.mScuController.getMemParkExamResult(null) || this.mScuController.getSuperVpaExamResult(null);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setNgpEnable(boolean enable) {
        this.mScuController.setNgpEnable(enable);
        if (!CarBaseConfig.getInstance().isSupportCNgp() || enable) {
            return;
        }
        this.mXpuController.setCityNgpSw(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public ScuResponse getNgpState() {
        return ScuResponse.fromScuState(this.mScuController.getNgpState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getNgpQuickLaneSw() {
        return this.mScuController.getNgpFastLaneSw() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setNgpQuickLaneSw(boolean enable) {
        this.mScuController.setNgpFastLaneSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getNgpTruckOffsetSw() {
        return this.mScuController.getNgpTruckOffsetSw() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setNgpTruckOffsetSw(boolean enable) {
        this.mScuController.setNgpTruckOffsetSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getNgpTipsWindowSw() {
        return this.mScuController.getNgpTipsWinSw() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setNgpTipsWindow(boolean enable) {
        this.mScuController.setNgpTipsWin(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getNgpVoiceChangeLane() {
        return this.mScuController.getNgpVoiceChangeLane() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setNgpVoiceChangeLane(final boolean enable) {
        this.mScuController.setNgpVoiceChangeLane(enable);
        ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.scu.-$$Lambda$ScuBaseViewModel$uJ0KC2toewmKMx9ccSp3TmPLQeU
            @Override // java.lang.Runnable
            public final void run() {
                ScuBaseViewModel.this.lambda$setNgpVoiceChangeLane$4$ScuBaseViewModel(enable);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public NgpChangeLaneMode getNgpChangeLaneMode() {
        return NgpChangeLaneMode.fromScuState(this.mScuController.getNgpChangeLaneMode());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setNgpChangeLaneMode(NgpChangeLaneMode mode) {
        this.mScuController.setNgpChangeLaneMode(mode.toScuCmd());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public int getNgpRemindMode() {
        return this.mScuController.getNgpRemindMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setNgpRemindMode(int mode) {
        this.mScuController.setNgpRemindMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean isNgpRunning() {
        return CarBaseConfig.getInstance().isSupportNewNgpArch() ? this.mXpuController.getCngpModeStatus() == 5 : this.mScuController.getNgpModeStatus() == 5;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean isCngpRunning() {
        return this.mXpuController.getCngpModeStatus() == 8;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setDistractionSwitch(boolean enable) {
        this.mScuController.setDistractionSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean isAutoPilotNeedTts() {
        boolean isAutoPilotNeedTts = this.mScuController.isAutoPilotNeedTts();
        LogUtils.i(TAG, "isAutoPilotNeedTts: " + isAutoPilotNeedTts, false);
        return isAutoPilotNeedTts;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setAutoPilotNeedTts(boolean enable) {
        LogUtils.i(TAG, "setAutoPilotNeedTts: " + enable, false);
        this.mScuController.setAutoPilotNeedTts(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setTtsBroadcastType(int type) {
        if (type == 0 || type == 1) {
            this.mScuController.setTtsBroadcastType(type);
        } else {
            LogUtils.w(TAG, "setTtsBroadcastType with invalid effect: " + type, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public int getTtsBroadcastType() {
        return this.mScuController.getTtsBroadcastType();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public int getSdcLeftRadarDisLevel() {
        return this.mScuController.getSdcLeftRadarDisLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public int getSdcRightRadarDisLevel() {
        return this.mScuController.getSdcRightRadarDisLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean isLccVideoWatched() {
        return this.mScuController.isLccVideoWatched();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setLccVideoWatched(boolean watched) {
        this.mScuController.setLccVideoWatched(watched);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean isMemParkVideoWatched() {
        return this.mScuController.isMemParkVideoWatched();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setMemParkVideoWatched(boolean watched) {
        this.mScuController.setMemParkVideoWatched(watched);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setDsmSw(boolean on) {
        this.mScuController.setDsmSw(on);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setRemoteCameraSw(boolean on) {
        this.mScuController.setRemoteCameraSw(on);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public boolean getRemoteCameraSw() {
        return this.mScuController.getRemoteCameraSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public DsmState getDsmState() {
        return DsmState.fromScuState(this.mScuController.getDsmSwStatus());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void setSpecialSasMode(int mode) {
        this.mScuController.setSpecialSasMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public int getSpecialSasMode() {
        return convertSpecialSasState(this.mScuController.getSlaState(), this.mScuController.getIslaState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void closeMrrByUser(int reason) {
        if (reason == 1) {
            NotificationHelper.getInstance().showToast(R.string.mrr_close_safe_tip, 0);
            this.mXpuController.setMrrEnable(false);
            return;
        }
        LogUtils.w(TAG, "Unknown reason for closing MRR: " + reason, false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel
    public void openMrrByUser(int reason) {
        if (reason == 1) {
            this.mXpuController.setMrrEnable(true);
        } else {
            LogUtils.w(TAG, "Unknown reason for opening MRR: " + reason, false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onFcwSwChanged(int state) {
        handleFcwStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onFcwSenChanged(int sensitivity) {
        handleFcwSenChanged(FcwSensitivity.fromXpuState(sensitivity));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onBsdStateChanged(int state) {
        if (this.mIsSupportDomainXpu) {
            synchronized (this.mDomainBsdLock) {
                ThreadUtils.removeRunnable(this.mDomainBsdStTask);
                ThreadUtils.postDelayed(0, this.mDomainBsdStTask, 100L);
            }
            return;
        }
        handleBsdStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onDowSwChanged(int state) {
        if (this.mIsSupportDomainXpu) {
            synchronized (this.mDomainDowLock) {
                ThreadUtils.removeRunnable(this.mDomainDowStTask);
                ThreadUtils.postDelayed(0, this.mDomainDowStTask, 100L);
            }
            return;
        }
        handleDowStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onLdwSwChanged(int state) {
        handleLdwStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onLkaSwChanged(int state) {
        handleLkaStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onElkSwChanged(int state) {
        handleElkStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onLssSwChanged(int state) {
        handleLssStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onRctaSwChanged(int state) {
        if (this.mIsSupportDomainXpu) {
            synchronized (this.mDomainRctaLock) {
                ThreadUtils.removeRunnable(this.mDomainRctaStTask);
                ThreadUtils.postDelayed(0, this.mDomainRctaStTask, 100L);
            }
            return;
        }
        handleRctaStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onRcwSwChanged(int state) {
        if (CarBaseConfig.getInstance().isSupportXpuDomainCtrl()) {
            synchronized (this.mDomainRcwLock) {
                ThreadUtils.removeRunnable(this.mDomainRcwStTask);
                ThreadUtils.postDelayed(0, this.mDomainRcwStTask, 100L);
            }
            return;
        }
        handleRcwStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onLccStateChanged(int state) {
        handleLccStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onAccStateChanged(int state) {
        handleAccStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onAccStateForMirrorChanged(int state) {
        handleAccStateForMirrorChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onIslcSwChanged(int state) {
        handleIslcStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onSlaStateChanged(int state) {
        if (CarBaseConfig.getInstance().isSupportSpecialSas()) {
            handleSpecialSasStateChanged(convertSpecialSasState(state, this.mScuController.getIslaState()));
        } else {
            handleIslaStateChanged(convertIslaState(ScuResponse.fromScuState(state), getIslaState()));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onIslaStateChanged(int state) {
        if (CarBaseConfig.getInstance().isSupportSpecialSas()) {
            handleSpecialSasStateChanged(convertSpecialSasState(this.mScuController.getSlaState(), state));
        } else {
            handleIslaStateChanged(convertIslaState(getSlaState(), ScuResponse.fromScuState(state)));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onIslaSpdRangeChanged(int spdRange) {
        handleIslaSpdRangeChanged(ScuIslaSpdRange.fromScuState(spdRange));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onIslaConfirmModeChanged(int confirmMode) {
        handleIslaConfirmModeChanged(confirmMode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onLcsStateChanged(int state) {
        handleAlcStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onXpuXPilotStateChanged(int state) {
        handleXpuXPilotStateChanged(state);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onNgpStateChanged(int state) {
        handleNgpStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onNgpQuickLaneChanged(int state) {
        handleNgpQuickLaneChanged(state == 1);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onNgpTruckOffsetChanged(int state) {
        handleNgpTruckOffsetChanged(state == 1);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onNgpTipWindowChanged(int state) {
        handleNgpTipWindowChanged(state == 1);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onNgpChangeLaneModeChanged(int mode) {
        handleNgpChangeLaneModeChanged(NgpChangeLaneMode.fromScuState(mode));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onNgpRemindModeChanged(int mode) {
        handleNgpRemindModeChanged(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onAutoParkSwChanged(int state) {
        handleAutoParkStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onMemParkSwChanged(int state) {
        handleMemParkStateChanged(ApResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onPhoneParkSwChanged(int state) {
        handlePhoneParkSwChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onKeyParkSwChanged(int state) {
        handleKeyParkSwChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onIhbStateChanged(int state) {
        handleIhbStateChanged(ScuResponse.fromScuState(state));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
    public void onDsmSwStateChanged(int state) {
        if (this.mXpuController.getNedcSwitchStatus() != 1) {
            handleDsmStateChanged(DsmState.fromScuState(state));
        }
    }

    private boolean checkIfXpuAvailable() {
        int nedcSwitchStatus = this.mXpuController.getNedcSwitchStatus();
        if (nedcSwitchStatus != 1) {
            if (nedcSwitchStatus == 3) {
                NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail_xpu_turning_on);
                return false;
            } else if (nedcSwitchStatus != 4 && nedcSwitchStatus != 5) {
                return true;
            }
        }
        NotificationHelper.getInstance().showToast(R.string.xpilot_open_fail_xpu_off);
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void registerBusiness(String... keys) {
        this.mScuController.registerBusiness(keys);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void unregisterBusiness(String... keys) {
        this.mScuController.unregisterBusiness(keys);
    }
}
