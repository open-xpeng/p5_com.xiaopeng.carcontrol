package com.xiaopeng.carcontrol.viewmodel.chassis;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.ICdcController;
import com.xiaopeng.carcontrol.carmanager.controller.IEpsController;
import com.xiaopeng.carcontrol.carmanager.controller.IEspController;
import com.xiaopeng.carcontrol.carmanager.controller.IMsmController;
import com.xiaopeng.carcontrol.carmanager.controller.ITpmsController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IXpuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.lib.apirouter.ApiRouter;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class ChassisBaseViewModel implements IChassisViewModel, IEspController.Callback, IEpsController.Callback, ITpmsController.Callback {
    protected static final String TAG = "ChassisViewModel";
    IBcmController mBcmController;
    ICdcController mCdcController;
    IEpsController mEpsController;
    IEspController mEspController;
    protected boolean mInTtmRunning;
    private final IMsmController mMsmController;
    ITpmsController mTpmsController;
    protected int mTtmHook;
    IVcuController mVcuController;
    IXpuController mXpuController;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    final IBcmController.Callback mBcmCallBack = new IBcmController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel.1
        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onEbwChanged(boolean enabled) {
            ChassisBaseViewModel.this.handleEbwChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAsHeightModeChanged(int mode) {
            ChassisBaseViewModel.this.handleAsHeightModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAsSoftModeChanged(int mode) {
            ChassisBaseViewModel.this.handleAsSoftModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAsWelcomeModeChanged(boolean mode) {
            ChassisBaseViewModel.this.handleAsWelcomeModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAsEasyLoadingModeChanged(boolean mode) {
            ChassisBaseViewModel.this.handleAsEasyLoadingModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAsMaintainModeChanged(int mode) {
            ChassisBaseViewModel.this.handleAsRepairModeChanged(mode == 1);
            ChassisBaseViewModel.this.handleAsEngineerModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAsCampingModeChanged(boolean mode) {
            ChassisBaseViewModel.this.handleAsCampingModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAsLevlingModeChanged(int mode) {
            ChassisBaseViewModel.this.handleAsLevlingModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onTrailerHitchStatusChanged(int status) {
            if (ChassisBaseViewModel.this.mInTtmRunning && status == 2) {
                LogUtils.d(ChassisBaseViewModel.TAG, "In TtmRunning, Do not report Middle error");
            } else {
                ChassisBaseViewModel.this.handleTtmSwitchStatusChanged(status);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onTrailerModeStatusChanged(boolean enable) {
            ChassisBaseViewModel.this.handleTrailerModeChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onTtmHookMotorStatusChanged(int status) {
            if (status == 2) {
                ChassisBaseViewModel.this.handleTtmSwitchStatusChanged(4);
            } else if (status == 1) {
                ChassisBaseViewModel.this.handleTtmSwitchStatusChanged(5);
            }
            ChassisBaseViewModel.this.handleTtmHookMotorStatusChanged(status);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onTtmSysErrStatusChanged(boolean isErr) {
            ChassisBaseViewModel.this.handleTtmSysErrStatusChanged(isErr);
        }
    };
    final ICdcController.Callback mCdcCallback = new ICdcController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel.2
        @Override // com.xiaopeng.carcontrol.carmanager.controller.ICdcController.Callback
        public void onCdcModeChanged(int cdc) {
            ChassisBaseViewModel.this.handleCdcModeChanged(cdc);
        }
    };
    private final Runnable mTtmControlOpenRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$4vWmnf6aWLDlB-xSpgi6FTbIR04
        @Override // java.lang.Runnable
        public final void run() {
            ChassisBaseViewModel.this.lambda$new$0$ChassisBaseViewModel();
        }
    };
    private final Runnable mTtmControlCloseRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$k64y3Yr8ofaynFa0uqaNO7VxeCk
        @Override // java.lang.Runnable
        public final void run() {
            ChassisBaseViewModel.this.lambda$new$1$ChassisBaseViewModel();
        }
    };
    private final Runnable mTtmResetOpenRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$OTzm27myScamURTAXC_f6VtmsWE
        @Override // java.lang.Runnable
        public final void run() {
            ChassisBaseViewModel.this.lambda$new$3$ChassisBaseViewModel();
        }
    };
    private final Runnable mTtmResetResultRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$AZA6h6KfUiD9EBXVap9C36RVIXI
        @Override // java.lang.Runnable
        public final void run() {
            ChassisBaseViewModel.this.lambda$new$6$ChassisBaseViewModel();
        }
    };
    private ContentObserver mContentObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel.4
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            String lastPathSegment = uri.getLastPathSegment();
            if (CarControl.Quick.TRAILER_HOOK_SW.equals(lastPathSegment)) {
                ChassisBaseViewModel.this.handleTtmSwitchStatusChanged(CarControl.Quick.getInt(App.getInstance().getContentResolver(), lastPathSegment, 0));
            } else if (CarControl.Quick.TRAILER_MODE_SW.equals(lastPathSegment)) {
                ChassisBaseViewModel.this.handleTrailerModeChanged(CarControl.Quick.getInt(App.getInstance().getContentResolver(), lastPathSegment, 0) == 1);
            }
        }
    };
    final CarBaseConfig mCarConfig = CarBaseConfig.getInstance();

    protected abstract void handleApbSystemStatusChanged(int status);

    protected abstract void handleAsCampingModeChanged(boolean mode);

    protected abstract void handleAsEasyLoadingModeChanged(boolean mode);

    protected abstract void handleAsEngineerModeChanged(int status);

    protected abstract void handleAsHeightModeChanged(int mode);

    protected abstract void handleAsLevlingModeChanged(int mode);

    protected abstract void handleAsRepairModeChanged(boolean on);

    protected abstract void handleAsSoftModeChanged(int mode);

    protected abstract void handleAsWelcomeModeChanged(boolean mode);

    abstract void handleAvhFaultChanged(boolean isFault);

    abstract void handleAvhSwChanged(boolean enabled);

    abstract void handleCdcModeChanged(int cdc);

    abstract void handleEbwChanged(boolean enabled);

    abstract void handleEspFaultChanged(boolean isFault);

    abstract void handleEspSwChanged(boolean enabled);

    abstract void handleHdcChanged(boolean enabled);

    abstract void handleSteeringEpsChanged(int eps);

    protected abstract void handleTrailerModeChanged(boolean mode);

    protected abstract void handleTtmHookMotorStatusChanged(int status);

    protected abstract void handleTtmSysErrStatusChanged(boolean isErr);

    public /* synthetic */ void lambda$new$0$ChassisBaseViewModel() {
        int trailerHitchSwitchStatus = this.mBcmController.getTrailerHitchSwitchStatus();
        LogUtils.d(TAG, "Ttm OpenRunnable ttmStatus: " + trailerHitchSwitchStatus, false);
        if (trailerHitchSwitchStatus == 1) {
            handleTtmSwitchStatusChanged(1);
        } else {
            handleTtmSwitchStatusChanged(2);
        }
        this.mInTtmRunning = false;
    }

    public /* synthetic */ void lambda$new$1$ChassisBaseViewModel() {
        int trailerHitchSwitchStatus = this.mBcmController.getTrailerHitchSwitchStatus();
        LogUtils.d(TAG, "Ttm CloseRunnable ttmStatus: " + trailerHitchSwitchStatus, false);
        if (trailerHitchSwitchStatus == 0) {
            handleTtmSwitchStatusChanged(0);
        } else {
            handleTtmSwitchStatusChanged(2);
        }
        this.mInTtmRunning = false;
    }

    public /* synthetic */ void lambda$new$3$ChassisBaseViewModel() {
        this.mBcmController.setTrailerHitchSwitchStatus(true);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$oGNANFDMVJrZfSiJXvsV-S9UAl8
            @Override // java.lang.Runnable
            public final void run() {
                ChassisBaseViewModel.this.lambda$null$2$ChassisBaseViewModel();
            }
        });
        this.mInTtmRunning = true;
    }

    public /* synthetic */ void lambda$null$2$ChassisBaseViewModel() {
        handleTtmSwitchStatusChanged(5);
    }

    public /* synthetic */ void lambda$new$6$ChassisBaseViewModel() {
        int trailerHitchSwitchStatus = this.mBcmController.getTrailerHitchSwitchStatus();
        LogUtils.d(TAG, "Ttm ResetResult ttmStatus: " + trailerHitchSwitchStatus, false);
        if (trailerHitchSwitchStatus == 1) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$bupefRezxSIIDX4g2N4EPfNXPrs
                @Override // java.lang.Runnable
                public final void run() {
                    ChassisBaseViewModel.this.lambda$null$4$ChassisBaseViewModel();
                }
            });
        } else {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$4Zwj3ss2P7IZrzpKJiDUqnIxgcQ
                @Override // java.lang.Runnable
                public final void run() {
                    ChassisBaseViewModel.this.lambda$null$5$ChassisBaseViewModel();
                }
            });
        }
        this.mInTtmRunning = false;
    }

    public /* synthetic */ void lambda$null$4$ChassisBaseViewModel() {
        handleTtmSwitchStatusChanged(1);
    }

    public /* synthetic */ void lambda$null$5$ChassisBaseViewModel() {
        handleTtmSwitchStatusChanged(2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ChassisBaseViewModel() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mEspController = (IEspController) carClientWrapper.getController(CarClientWrapper.XP_ESP_SERVICE);
        this.mEpsController = (IEpsController) carClientWrapper.getController(CarClientWrapper.XP_EPS_SERVICE);
        this.mTpmsController = (ITpmsController) carClientWrapper.getController(CarClientWrapper.XP_TPMS_SERVICE);
        this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mMsmController = (IMsmController) carClientWrapper.getController(CarClientWrapper.XP_MSM_SERVICE);
        this.mXpuController = (IXpuController) carClientWrapper.getController(CarClientWrapper.XP_XPU_SERVICE);
        this.mCdcController = (ICdcController) carClientWrapper.getController(CarClientWrapper.XP_CDC_SERVICE);
        this.mInTtmRunning = false;
        this.mVcuController.registerCallback(new IVcuController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.ChassisBaseViewModel.3
            @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
            public void onXSportDriveModeChanged(int mode) {
                if (mode == 0) {
                    ChassisBaseViewModel chassisBaseViewModel = ChassisBaseViewModel.this;
                    chassisBaseViewModel.onSteeringEpsChanged(chassisBaseViewModel.mEpsController.getSteeringEps());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void initTrailerMode() {
        if (App.isMainProcess()) {
            return;
        }
        App.getInstance().getContentResolver().registerContentObserver(CarControl.Quick.getUriFor(CarControl.Quick.TRAILER_HOOK_SW), false, this.mContentObserver);
        App.getInstance().getContentResolver().registerContentObserver(CarControl.Quick.getUriFor(CarControl.Quick.TRAILER_MODE_SW), false, this.mContentObserver);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setEsp(boolean enable) {
        this.mEspController.setEsp(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getEspFault() {
        return this.mEspController.getEspFault();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setEspSw() {
        boolean espFault = getEspFault();
        LogUtils.d(TAG, "setEspSw espFault=" + espFault, false);
        boolean espSw = this.mEspController.getEspSw();
        if (espFault) {
            handleEspSwChanged(false);
            return;
        }
        boolean isEvSysReady = this.mVcuController.isEvSysReady();
        LogUtils.d(TAG, "setEspSw isEspPrepared=" + isEvSysReady + ", currentEspSw=" + espSw, false);
        this.mEspController.setEspSw(!espSw);
        if (isEvSysReady) {
            this.mEspController.setEsp(!espSw);
        } else {
            handleEspSwChanged(!espSw);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getEspForUI() {
        boolean espFault = this.mEspController.getEspFault();
        boolean isEvSysReady = this.mVcuController.isEvSysReady();
        int esp = this.mEspController.getEsp();
        boolean espSw = this.mEspController.getEspSw();
        LogUtils.d(TAG, "getEspForUI: esp_fault=" + espFault + ", system_ready=" + isEvSysReady + ", esp=" + esp + ", espsw=" + espSw);
        if (espFault) {
            return false;
        }
        return isEvSysReady ? this.mCarConfig.isNewEspArch() ? esp == 1 || esp == 2 : esp == 4 : espSw;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getEspSw() {
        return this.mEspController.getEspSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAvhSw(boolean enable) {
        boolean avhFault = getAvhFault();
        LogUtils.i(TAG, "setAvhSw avhFault=" + avhFault);
        if (avhFault) {
            handleAvhSwChanged(false);
            if (App.isMainProcess()) {
                QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_AVH_BOOL, false);
                return;
            }
            return;
        }
        this.mEspController.setAvhSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAvhSw() {
        return this.mEspController.getAvhSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAvhForUI() {
        boolean avhFault = getAvhFault();
        boolean isAvhPrepared = isAvhPrepared();
        LogUtils.d(TAG, "getAvhForUI, avhFault: " + avhFault + ", avhPrepared: " + isAvhPrepared, false);
        if (avhFault) {
            return false;
        }
        if (isAvhPrepared) {
            return this.mEspController.getAvh() == 1 || this.mEspController.getAvh() == 2;
        }
        return this.mEspController.getAvhSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAvhFault() {
        return this.mEspController.getAvhFault();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setHdc(boolean enable) {
        if (this.mCarConfig.isSupportHdc()) {
            boolean hdcFault = getHdcFault();
            float carSpeed = this.mVcuController.getCarSpeed();
            boolean hdc = this.mEspController.getHdc();
            LogUtils.i(TAG, "setHdc hdcFault=" + hdcFault + ", carSpd=" + carSpeed + ", currentHdc=" + hdc);
            if (hdcFault || (carSpeed > 40.0f && !hdc)) {
                NotificationHelper.getInstance().showToast(R.string.hdc_open_fail);
            } else {
                this.mEspController.setHdc(!hdc);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getHdc() {
        return this.mEspController.getHdc();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getHdcFault() {
        return this.mEspController.getHdcFault();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setEbw(boolean enable) {
        this.mBcmController.setEbwEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getEbw() {
        return this.mBcmController.isEbwEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setSteeringEps(int eps, boolean storeEnable) {
        this.mEpsController.setSteeringEps(eps, storeEnable);
        if (this.mCarConfig.isNewEspArch()) {
            return;
        }
        handleSteeringEpsChanged(eps);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getSteeringEps() {
        return this.mEpsController.getSteeringEps();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getEpsWarningState() {
        return this.mEspController.getEpsWarningState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public float getSteeringAngle() {
        return this.mEpsController.getSteeringAngle();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setCdcMode(int cdc) {
        this.mCdcController.setCdcMode(cdc, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getCdcMode() {
        return this.mCdcController.getCdcMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getTpmsCalibrateState() {
        return this.mTpmsController.getTpmsCalibrateState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void calibrateTyrePressure() {
        try {
            this.mTpmsController.calibrateTyrePressure();
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean[] isTpmsTempWarning() {
        return this.mTpmsController.isTpmsTempWarning();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getTpmsTemperature(int position) {
        int[] tpmsTemperature = this.mTpmsController.getTpmsTemperature();
        if (tpmsTemperature != null) {
            return tpmsTemperature[position - 1];
        }
        return 0;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean isTpmsPressureNormal() {
        int[] tpmsWarningState = this.mTpmsController.getTpmsWarningState();
        if (tpmsWarningState == null || tpmsWarningState.length < 4) {
            return false;
        }
        boolean z = true;
        for (int i : tpmsWarningState) {
            z &= i == 0;
        }
        return z;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public float getTorsionBarTorque() {
        if (this.mCarConfig.isSupportEpsTorque()) {
            return this.mEpsController.getTorsionBarTorque();
        }
        return 0.0f;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onEspChanged(boolean enabled) {
        if (this.mEspController.getEspFault() || !this.mVcuController.isEvSysReady()) {
            return;
        }
        handleEspSwChanged(enabled);
        if (App.isMainProcess() && enabled && BaseFeatureOption.getInstance().isSupportEspFeedback()) {
            LogUtils.i(TAG, "ESP changed to true, re-send true to ESP");
            this.mEspController.setEsp(true);
            this.mEspController.setEspSw(true);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onEspSwChanged(boolean enabled) {
        handleEspSwChanged(enabled);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onEspFaultChanged(boolean isFault) {
        handleEspFaultChanged(isFault);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onAvhStatusChanged(int status) {
        if (isAvhPrepared()) {
            handleAvhSwChanged(ChassisSmartControl.parseAvhStatus(status));
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onAvhFaultChanged(boolean isFault) {
        handleAvhFaultChanged(isFault);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onAvhSwChanged(boolean enabled) {
        handleAvhSwChanged(enabled);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
    public void onHdcChanged(boolean enabled) {
        handleHdcChanged(enabled);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IEpsController.Callback
    public void onSteeringEpsChanged(int eps) {
        handleSteeringEpsChanged(eps);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleTtmSwitchStatusChanged(int status) {
        if (App.isMainProcess()) {
            this.mTtmHook = status;
            CarControl.Quick.putInt(App.getInstance().getContentResolver(), CarControl.Quick.TRAILER_HOOK_SW, status);
        }
    }

    private boolean isAvhPrepared() {
        boolean isDrvDoorClosed = isDrvDoorClosed();
        boolean isEvSysReady = this.mVcuController.isEvSysReady();
        boolean isDrvBeltBuckled = isDrvBeltBuckled();
        LogUtils.d(TAG, "isDoorClose:" + isDrvDoorClosed + ", isEvSysReady:" + isEvSysReady + ", isBeltBuckled:" + isDrvBeltBuckled, false);
        return isDrvDoorClosed && isEvSysReady && isDrvBeltBuckled;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean isDrvBeltBuckled() {
        return this.mCarConfig.isSupportSrs() ? this.mMsmController.getDrvBeltStatus() == 0 : this.mBcmController.getDrvBeltStatus() == 0;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean isDrvDoorClosed() {
        int[] doorsState = this.mBcmController.getDoorsState();
        return doorsState != null && doorsState.length >= 4 && doorsState[0] == 0;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAirSuspensionHeightMode(int height, boolean storeEnable) {
        this.mBcmController.setAirSuspensionHeight(height, 6 != height && storeEnable);
        this.mBcmController.setCustomerModeFlag(true, storeEnable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getAirSuspensionHeightMode() {
        return this.mBcmController.getAirSuspensionHeight();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void saveAsLocationInfo(final int asHeight) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$ZEktejMfddCl_gKAO6F2FwkfKi8
            @Override // java.lang.Runnable
            public final void run() {
                ChassisBaseViewModel.lambda$saveAsLocationInfo$7(asHeight);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$saveAsLocationInfo$7(final int asHeight) {
        Uri.Builder builder = new Uri.Builder();
        String str = "com.xiaopeng.napa.SuspensionService";
        builder.authority(BaseFeatureOption.getInstance().isSupportNapa() ? "com.xiaopeng.napa.SuspensionService" : "com.xiaopeng.montecarlo.SuspensionService").path("getCurrentLocation");
        try {
            String str2 = (String) ApiRouter.route(builder.build());
            LogUtils.i(TAG, "getCurrentLocation, location: " + str2, false);
            if (str2 != null) {
                try {
                    JSONObject jSONObject = new JSONObject(str2);
                    double optDouble = jSONObject.optDouble("lon");
                    double optDouble2 = jSONObject.optDouble("lat");
                    Uri.Builder builder2 = new Uri.Builder();
                    if (!BaseFeatureOption.getInstance().isSupportNapa()) {
                        str = "com.xiaopeng.montecarlo.SuspensionService";
                    }
                    builder2.authority(str).path("saveSuspensionUserInfo").appendQueryParameter("lon", String.valueOf(optDouble)).appendQueryParameter("lat", String.valueOf(optDouble2)).appendQueryParameter("level", String.valueOf(asHeight));
                    ApiRouter.route(builder2.build());
                } catch (Exception e) {
                    LogUtils.e(TAG, "saveSuspensionUserInfo: " + e.getMessage(), false);
                }
            }
        } catch (Exception e2) {
            LogUtils.e(TAG, "getCurrentLocation: " + e2.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAsLocationSw(boolean on) {
        setAsLocationSw(on, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAsLocationSw(boolean on, boolean storeEnable) {
        this.mBcmController.setAsLocationSw(on, storeEnable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAsLocationSw() {
        return this.mBcmController.getAsLocationSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAsLocationControlSw(boolean on) {
        this.mBcmController.setAsLocationControlSw(on);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAsLocationControlSw() {
        return this.mBcmController.getAsLocationControlSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAirSuspensionSoftMode(int soft) {
        this.mBcmController.setAirSuspensionSoft(soft);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getAirSuspensionSoftMode() {
        return this.mBcmController.getAirSuspensionSoft();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAirSuspensionWelcome(boolean enable) {
        this.mBcmController.setAsWelcomeMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean isAirSuspensionWelcomeEnable() {
        return this.mBcmController.getAsWelcomeMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setEasyLoadingSwitch(boolean enable) {
        this.mBcmController.setEasyLoadingSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean isEasyLoadingEnable() {
        return this.mBcmController.getEasyLoadingSwitch();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAirSuspensionRepairMode(boolean enable) {
        this.mBcmController.setAirSuspensionRepairMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAirSuspensionRepairMode() {
        return this.mBcmController.getAirSuspensionRepairMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setEngineeringModeStatus(int status) {
        this.mBcmController.setEngineeringModeStatus(status);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getEngineeringModeStatus() {
        return this.mBcmController.getEngineeringModeStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAsCampingMode(boolean enable) {
        this.mBcmController.setAsCampingModeSwitch(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAsCampingMode() {
        return this.mBcmController.getAsCampingModeSwitch();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getAsAutoLevelingResult() {
        return this.mBcmController.getAsAutoLevelingResult();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAsHeightChangingStatus() {
        return this.mBcmController.getAsHeightChangingStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAsLockModeStatus() {
        return this.mBcmController.getAsLockModeStatus() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAsHoistModeSwitchStatus() {
        return this.mBcmController.getAsHoistModeSwitchStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setEpbSystemStatus(boolean enable) {
        this.mEspController.setEpbSystemStatus(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getApbSystemStatus() {
        return this.mEspController.getApbSystemStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setAsCustomerModeFlagSwitchStatus(boolean enable) {
        this.mBcmController.setCustomerModeFlag(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAsCustomerModeFlagSwitchStatus() {
        return this.mBcmController.getCustomerModeFlag();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getAsRequestEspState() {
        return this.mBcmController.getAsRequestEspState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getTsmFaultStatus() {
        return this.mEspController.getTsmFaultStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getTtmSystemError() {
        return this.mBcmController.getTtmSystemError();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getAsFaultStatus() {
        return this.mBcmController.getAsYellowLampRequest() || this.mBcmController.getAsRedLampRequest();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setTtmSwitch(boolean enable) {
        this.mBcmController.setTrailerHitchSwitchStatus(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getTtmSwitch() {
        return this.mBcmController.getTrailerHitchSwitchStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setTtmSwitchStatusForUI(final boolean enable) {
        this.mInTtmRunning = true;
        this.mHandler.removeCallbacks(this.mTtmControlOpenRunnable);
        this.mHandler.removeCallbacks(this.mTtmControlCloseRunnable);
        this.mHandler.removeCallbacks(this.mTtmResetOpenRunnable);
        this.mHandler.removeCallbacks(this.mTtmResetResultRunnable);
        this.mBcmController.setTrailerHitchSwitchStatus(enable);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$AfjKujImnLgmOzb8dWHncmJ6CRY
            @Override // java.lang.Runnable
            public final void run() {
                ChassisBaseViewModel.this.lambda$setTtmSwitchStatusForUI$8$ChassisBaseViewModel(enable);
            }
        });
        this.mHandler.postDelayed(enable ? this.mTtmControlOpenRunnable : this.mTtmControlCloseRunnable, 10000L);
    }

    public /* synthetic */ void lambda$setTtmSwitchStatusForUI$8$ChassisBaseViewModel(final boolean enable) {
        handleTtmSwitchStatusChanged(enable ? 5 : 4);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getTtmSwitchStatusForUI() {
        LogUtils.d(TAG, "getTtmSwitchStatusForUI, mTtmHook: " + this.mTtmHook, false);
        if (this.mBcmController.getTtmSystemError()) {
            return 0;
        }
        int ttmHookMotorStatus = this.mBcmController.getTtmHookMotorStatus();
        if (ttmHookMotorStatus == 1) {
            return 5;
        }
        if (ttmHookMotorStatus == 2) {
            return 4;
        }
        return this.mBcmController.getTrailerHitchSwitchStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void resetTtmSwitchStatus() {
        this.mInTtmRunning = true;
        this.mHandler.removeCallbacks(this.mTtmControlOpenRunnable);
        this.mHandler.removeCallbacks(this.mTtmControlCloseRunnable);
        this.mHandler.removeCallbacks(this.mTtmResetOpenRunnable);
        this.mHandler.removeCallbacks(this.mTtmResetResultRunnable);
        this.mBcmController.setTrailerHitchSwitchStatus(false);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.chassis.-$$Lambda$ChassisBaseViewModel$d8jExGaJURdlTExGeZyOeWkI-n0
            @Override // java.lang.Runnable
            public final void run() {
                ChassisBaseViewModel.this.lambda$resetTtmSwitchStatus$9$ChassisBaseViewModel();
            }
        });
        this.mHandler.postDelayed(this.mTtmResetOpenRunnable, 10000L);
        this.mHandler.postDelayed(this.mTtmResetResultRunnable, 20000L);
    }

    public /* synthetic */ void lambda$resetTtmSwitchStatus$9$ChassisBaseViewModel() {
        handleTtmSwitchStatusChanged(4);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setTrailerModeStatus(boolean enable) {
        this.mBcmController.setTrailerModeStatus(enable);
        this.mEspController.setEspTsmSwitchStatus(enable);
        this.mBcmController.setAsTrailerModeSwitchStatus(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getTrailerModeStatus() {
        return this.mBcmController.getEngineeringModeStatus() == 3;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getCdcuTrailerModeStatus() {
        return this.mBcmController.getTrailerModeStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getDtcFaultStatus() {
        return this.mEspController.getDtcFaultStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getTtmLampConnectStatus() {
        return this.mBcmController.getTtmLampConnectStatus() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getTtmDenormalizeStatus() {
        return this.mBcmController.getTtmDenormalizeStatus() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getTtmHookMotorStatus() {
        return this.mBcmController.getTtmHookMotorStatus();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setEspCstSw(boolean enable) {
        this.mEspController.setEspCstSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public boolean getEspCstSw() {
        return this.mEspController.getEspCstSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public void setEspBpfMode(int mode) {
        this.mEspController.setEspBpfMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel
    public int getEspBpfMode() {
        return this.mEspController.getEspBpfMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void registerBusiness(String... keys) {
        this.mTpmsController.registerBusiness(keys);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void unregisterBusiness(String... keys) {
        this.mTpmsController.unregisterBusiness(keys);
    }
}
