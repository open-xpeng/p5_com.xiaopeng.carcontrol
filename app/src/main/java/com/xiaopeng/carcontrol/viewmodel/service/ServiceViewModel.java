package com.xiaopeng.carcontrol.viewmodel.service;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.hardware.input.InputManager;
import android.media.AudioConfig.AudioConfig;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.IXPKeyListener;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.IpcRouterService;
import com.xiaopeng.carcontrol.bean.Mirror;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IAtlController;
import com.xiaopeng.carcontrol.carmanager.controller.IAvasController;
import com.xiaopeng.carcontrol.carmanager.controller.IAvmController;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.ICarInfoController;
import com.xiaopeng.carcontrol.carmanager.controller.ICdcController;
import com.xiaopeng.carcontrol.carmanager.controller.IDiagnosticController;
import com.xiaopeng.carcontrol.carmanager.controller.IEpsController;
import com.xiaopeng.carcontrol.carmanager.controller.IEspController;
import com.xiaopeng.carcontrol.carmanager.controller.IHvacController;
import com.xiaopeng.carcontrol.carmanager.controller.IIcmController;
import com.xiaopeng.carcontrol.carmanager.controller.ILluController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IMsmController;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.carcontrol.carmanager.controller.IScuController;
import com.xiaopeng.carcontrol.carmanager.controller.ITboxController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IXpuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.CarControlSyncDataEvent;
import com.xiaopeng.carcontrol.model.DataSyncModel;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.statistic.BtnEnum;
import com.xiaopeng.carcontrol.statistic.CarStatusUploadHelper;
import com.xiaopeng.carcontrol.statistic.PageEnum;
import com.xiaopeng.carcontrol.statistic.StatisticUtils;
import com.xiaopeng.carcontrol.util.GsonUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.dialog.panel.ControlPanelManager;
import com.xiaopeng.carcontrol.view.dialog.poppanel.PopPanelManager;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.account.AccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasSmartControl;
import com.xiaopeng.carcontrol.viewmodel.cabin.CabinSmartControl;
import com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatSmartControl;
import com.xiaopeng.carcontrol.viewmodel.cabin.TrunkState;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorBaseViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodySmartControl;
import com.xiaopeng.carcontrol.viewmodel.carsettings.WiperInterval;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisSmartControl;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacAirAutoProtect;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.SeatVentLevel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.scu.XpilotSmartControl;
import com.xiaopeng.carcontrol.viewmodel.selfcheck.SelfCheckUtil;
import com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuSmartControl;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xuimanager.soundresource.SoundResourceManager;
import com.xiaopeng.xuimanager.soundresource.data.SoundEffectResource;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class ServiceViewModel implements IServiceViewModel, DataSyncModel.DataSyncChangeListener {
    private static final long BCM_READY_DISABLE_TIMEOUT = 30000;
    private static final long CHECK_BONNET_TIME = 30000;
    private static final long CHECK_BRK_PEDAL_TIME_BT_NFC = 1000;
    private static final long CHECK_BRK_PEDAL_TIME_KEY = 5000;
    private static final long GEAR_CHANGED_WAIT_TIME = 500;
    private static final long HIGH_SPEED_WAIT_TIME = 10000;
    private static final int SEAT_MAX_ERROR = 2;
    private static final long SEAT_SAVE_INTERVAL = 500;
    private static final String TAG = "ServiceViewModel";
    private static final int UNLOCK_CAR_FAILED_WITH_BLE = 2;
    private static final int UNLOCK_CAR_FAILED_WITH_NFC = 1;
    private static final int UNLOCK_CAR_FAILED_WITH_OTHER = 3;
    private static final int UNLOCK_CAR_FAILED_WITH_WATCH = 4;
    private static final int UNLOCK_CAR_SUCCESS = 0;
    static final float WELCOME_MODE_SPD_THRESHOLD = 3.0f;
    private boolean isSunShadeInitialized;
    private Account mAccountInfo;
    IAtlController mAtlController;
    private final Runnable mAutoDismissDialogTask;
    IAvasController mAvasController;
    private IAvmController mAvmController;
    IBcmController mBcmController;
    private Handler mBgHandler;
    private final DoorBossKeyListener mBossKeyListener;
    private final Runnable mBossKeyUpTask;
    final CarBaseConfig mCarConfigHelper;
    private final ICarInfoController mCarInfoController;
    ICdcController mCdcController;
    ChassisSmartControl mChassisSmartControl;
    private final Runnable mCheckBonnetTask;
    private volatile long mCheckBrkPedalStartTime;
    private final Runnable mCheckReadyDisableTask;
    private final Runnable mCheckTelescopeTask;
    private volatile int mCurrUnlockReqRes;
    private boolean mCurrentBonnetClosed;
    DataSyncModel mDataSync;
    private final Runnable mDelayShowPGearMenuTask;
    private boolean mDeodorantNotToast;
    private final IDiagnosticController mDiagnosticController;
    private final Runnable mEpbRunnable;
    IEpsController mEpsController;
    IEspController mEspController;
    private final FunctionModel mFunctionModel;
    private final Runnable mGearChangedRunnable;
    private volatile boolean mHasCheckReadyDisableTask;
    boolean mHasPendingUpdate;
    private final Runnable mHighSpdRunnable;
    final IHvacController mHvacController;
    protected HvacSmartControl mHvacSmartControl;
    IIcmController mIcmController;
    private volatile boolean mIsBcmReadyEnabled;
    private volatile boolean mIsBossKeyPressed;
    boolean mIsDataSyncEmpty;
    protected boolean mIsDrvDoorOpened;
    protected boolean mIsDrvSrsUnbelted;
    private boolean mIsEpbDialogShow;
    private boolean mIsFindingKey;
    private boolean mIsFindingKeyForPsn;
    private boolean mIsFindingKeyForRL;
    private boolean mIsFindingKeyForRR;
    private boolean mIsHighSpdRunnablePending;
    boolean mIsInitComplete;
    protected volatile boolean mIsLocalIgOn;
    protected boolean mIsPsnDoorOpened;
    protected boolean mIsPsnSrsUnbelted;
    private boolean mIsReadyDisableDialogShow;
    private int mLastMirrorCtrl;
    private float mLastSpd;
    protected final ILluController mLluController;
    private final Runnable mLocalIgOnTask;
    protected IMcuController mMcuController;
    IMsmController mMsmController;
    private XDialog mNarrowSpaceWarnDialog;
    private boolean mNeedUpdateDrvSeat;
    private final MutableLiveData<Boolean> mPGearDropDownShowData;
    private volatile int mPreBrkPedalStatus;
    private int mPreIgState;
    private int mPreviousAsMode;
    private int mPreviousGear;
    private boolean mPreviousXPedalMode;
    private int mPreviousXsportMode;
    private XDialog mReadyDisableDialog;
    private String mReadyDisableScene;
    private final Runnable mRestoreDrvSeatTask;
    private CountDownLatch mResumeSaveSettingLock;
    private final IScenarioController mScenarioController;
    IScuController mScuController;
    private final Runnable mScuMirrorTask;
    private XDialog mSdcObstacleDialog;
    private ISSDoorCallBack mSsDoorStateCallback;
    private final ITboxController mTboxController;
    private final MutableLiveData<String> mUnityDismissDialogData;
    private final MutableLiveData<String> mUnityShowDialogData;
    IVcuController mVcuController;
    private VcuSmartControl mVcuSmartControl;
    private final IXpuController mXpuController;
    private final BcmCallbackImpl mBcmCallback = new BcmCallbackImpl();
    private final VcuCallbackImpl mVcuCallback = new VcuCallbackImpl();
    private final EspCallbackImpl mEspCallback = new EspCallbackImpl();
    private final ScuCallbackImpl mScuCallback = new ScuCallbackImpl();
    private final MsmCallbackImpl mMsmCallback = new MsmCallbackImpl();
    private final HvacCallbackImpl mHvacCallback = new HvacCallbackImpl();
    private final AvasCallbackImpl mAvasCallback = new AvasCallbackImpl();
    private LluCallbackImpl mLluCallback = new LluCallbackImpl();
    private final McuCallbackImpl mMcuCallback = new McuCallbackImpl();
    private final TboxCallbackImpl mTboxCallback = new TboxCallbackImpl();
    private final DiagnosticCallbackImpl mDiagnosticCallback = new DiagnosticCallbackImpl();
    private final XpuCallbackImpl mXpuCallback = new XpuCallbackImpl();

    private int convertSayhiAvasToLlu(int avasSayHiEffect) {
        if (avasSayHiEffect != 2) {
            return avasSayHiEffect != 3 ? 1 : 3;
        }
        return 2;
    }

    private static float matchHvacTemp(float temp) {
        if (temp < 18.0f) {
            temp = 18.0f;
        }
        if (temp > 32.0f) {
            return 32.0f;
        }
        return temp;
    }

    protected void checkPsnWelcomeModeGoOn() {
    }

    protected void handleEspFaultChanged(boolean isFault) {
    }

    public /* synthetic */ void lambda$new$0$ServiceViewModel() {
        if (this.mVcuController.getCarSpeed() >= this.mCarConfigHelper.getHighSpdFuncThreshold()) {
            CabinSmartControl.getInstance().handleHighSpeed();
        }
        this.mIsHighSpdRunnablePending = false;
    }

    public /* synthetic */ void lambda$new$1$ServiceViewModel() {
        if (this.mBcmController.getBonnetState() != 0) {
            LogUtils.i(TAG, "Bonnet opened, play tts warning", false);
            SpeechHelper.getInstance().speak(ResUtils.getString(R.string.bonnet_opened_warning));
            Handler handler = this.mBgHandler;
            if (handler != null) {
                handler.postDelayed(this.mCheckBonnetTask, 30000L);
            }
        }
    }

    public /* synthetic */ void lambda$new$2$ServiceViewModel() {
        LogUtils.i(TAG, "Delay time arrived, start to check EvReady and iBcm_ReadyEnable", false);
        checkReadyDisableStatus();
    }

    public /* synthetic */ void lambda$new$3$ServiceViewModel() {
        this.mIsBossKeyPressed = false;
        LogUtils.i(TAG, "handleBossKeyUp", false);
        SeatSmartControl.getInstance().controlPsnSeatMoveForwardEnd();
    }

    public /* synthetic */ void lambda$new$4$ServiceViewModel() {
        SeatSmartControl.getInstance().restoreSeat(this.mDataSync.getDrvSeatSavedPos());
    }

    public /* synthetic */ void lambda$new$5$ServiceViewModel() {
        checkDoorBossKeySw();
        tryRestoreEcuIfFactoryReset();
        resumeSaveSettings();
        this.mIsInitComplete = true;
        this.mResumeSaveSettingLock.countDown();
        QuickSettingManager.getInstance().initData();
        CabinSmartControl.getInstance().initTrunkState();
        this.mHvacSmartControl.executeHvacDrvTempSync();
        this.mHvacSmartControl.invokeHvacSingleMode(isLocalIgOn());
        SelfCheckUtil.checkSetting();
        if (this.mCarConfigHelper.isSupportSdc()) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ISSDoorCallBack iSSDoorCallBack = this.mSsDoorStateCallback;
            if (iSSDoorCallBack != null) {
                iSSDoorCallBack.onInitSsDoor();
            }
        }
    }

    public LiveData<String> getUnityShowDialogData() {
        return this.mUnityShowDialogData;
    }

    public LiveData<String> getUnityDismissDialogData() {
        return this.mUnityDismissDialogData;
    }

    public LiveData<Boolean> getShowParkDropDownData() {
        return this.mPGearDropDownShowData;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ServiceViewModel() {
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        this.mCarConfigHelper = carBaseConfig;
        this.mPreIgState = -1;
        this.mResumeSaveSettingLock = new CountDownLatch(1);
        this.mHasPendingUpdate = false;
        this.mIsHighSpdRunnablePending = false;
        this.mHighSpdRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$0BVvgtwyMRZ4-Zyw8HJYeU7UF2c
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$new$0$ServiceViewModel();
            }
        };
        this.mPreviousGear = 0;
        this.mGearChangedRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$QFBlo7FzXHKdlMjRbADFy-oJdd8
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.handleGearChanged();
            }
        };
        this.mPreviousXPedalMode = false;
        this.mPreviousAsMode = -1;
        this.mIsFindingKey = false;
        this.mIsFindingKeyForPsn = false;
        this.mIsFindingKeyForRL = false;
        this.mIsFindingKeyForRR = false;
        this.mDeodorantNotToast = true;
        this.mEpbRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel.1
            @Override // java.lang.Runnable
            public void run() {
                if (ServiceViewModel.this.mVcuController.getGearLevel() == 4) {
                    boolean z = ServiceViewModel.this.mMcuController.getIgStatusFromMcu() == 1;
                    boolean z2 = ServiceViewModel.this.mVcuController.getCarSpeed() <= 3.0f;
                    boolean z3 = ServiceViewModel.this.mEspController.getApbSystemStatus() == 1;
                    LogUtils.i(ServiceViewModel.TAG, "onGearChange to park, handleEpb: isIgOn=" + z + ", isCarStop=" + z2 + ", isEpbReleased=" + z3, false);
                    if (z && z2 && z3) {
                        NotificationHelper.showOsd(App.getInstance(), R.string.epb_title, R.drawable.ic_mid_osdparking, R.string.pull_epb_desc);
                    }
                }
            }
        };
        this.mLastMirrorCtrl = 0;
        this.mScuMirrorTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel.2
            @Override // java.lang.Runnable
            public void run() {
                int scuRearMirrorControlState = ServiceViewModel.this.mScuController.getScuRearMirrorControlState();
                LogUtils.i(ServiceViewModel.TAG, "mScuMirrorTask: lastMirrorCtrl=" + ServiceViewModel.this.mLastMirrorCtrl + ", currentMirrorCtrl=" + scuRearMirrorControlState, false);
                if (scuRearMirrorControlState == ServiceViewModel.this.mLastMirrorCtrl) {
                    ServiceViewModel.this.mBcmController.controlMirror(scuRearMirrorControlState == 1);
                }
                ServiceViewModel.this.mLastMirrorCtrl = 0;
            }
        };
        this.mCheckBonnetTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$pdEAyaVnp5WjYpyiGSZAcRvvqFY
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$new$1$ServiceViewModel();
            }
        };
        this.mIsLocalIgOn = false;
        this.mPreBrkPedalStatus = 0;
        this.mHasCheckReadyDisableTask = false;
        this.mCheckBrkPedalStartTime = 0L;
        this.mIsBcmReadyEnabled = false;
        this.mCheckReadyDisableTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$rIQ5Ei9ajDFxBnJU0FT6Hb9qMV8
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$new$2$ServiceViewModel();
            }
        };
        this.mAutoDismissDialogTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$ve_mhVK7iinZsTrR-Nil9kJ8aPo
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.dismissReadyDisableDialog();
            }
        };
        this.mPreviousXsportMode = 0;
        this.mBossKeyListener = new DoorBossKeyListener();
        this.mIsBossKeyPressed = false;
        this.mBossKeyUpTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$TIXUS99U0DvPDl_W8PjiAu5dNx8
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$new$3$ServiceViewModel();
            }
        };
        this.mRestoreDrvSeatTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$PCwEGZqk6oXunINtV9HQioDzi8Y
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$new$4$ServiceViewModel();
            }
        };
        this.mLocalIgOnTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$yw3oFjuCiPTENci7T05LB4nD-Co
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$new$5$ServiceViewModel();
            }
        };
        this.isSunShadeInitialized = true;
        this.mUnityShowDialogData = new MutableLiveData<>();
        this.mUnityDismissDialogData = new MutableLiveData<>();
        this.mPGearDropDownShowData = new MutableLiveData<>();
        this.mDelayShowPGearMenuTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$SJB5QK7n-kA9SVJWBWN6ve1XhqA
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.lambda$new$16();
            }
        };
        this.mCheckTelescopeTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$EtKPrpzwjnF4aoY1RIHPOiZFKo8
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$new$17$ServiceViewModel();
            }
        };
        this.mIsInitComplete = false;
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        this.mEspController = (IEspController) carClientWrapper.getController(CarClientWrapper.XP_ESP_SERVICE);
        this.mEpsController = (IEpsController) carClientWrapper.getController(CarClientWrapper.XP_EPS_SERVICE);
        this.mMsmController = (IMsmController) carClientWrapper.getController(CarClientWrapper.XP_MSM_SERVICE);
        this.mHvacController = (IHvacController) carClientWrapper.getController("hvac");
        this.mScuController = (IScuController) carClientWrapper.getController(CarClientWrapper.XP_SCU_SERVICE);
        this.mAvasController = (IAvasController) carClientWrapper.getController(CarClientWrapper.XP_AVAS_SERVICE);
        this.mLluController = (ILluController) carClientWrapper.getController(CarClientWrapper.XP_LLU_SERVICE);
        this.mAtlController = (IAtlController) carClientWrapper.getController(CarClientWrapper.XP_ATL_SERVICE);
        this.mIcmController = (IIcmController) carClientWrapper.getController(CarClientWrapper.XP_ICM_SERVICE);
        this.mMcuController = (IMcuController) carClientWrapper.getController(CarClientWrapper.XP_MCU_SERVICE);
        this.mTboxController = (ITboxController) carClientWrapper.getController(CarClientWrapper.XP_TBOX_SERVICE);
        this.mDiagnosticController = (IDiagnosticController) carClientWrapper.getController(CarClientWrapper.XP_DIAGNOSTIC_SERVICE);
        this.mCarInfoController = (ICarInfoController) carClientWrapper.getController(CarClientWrapper.XP_CAR_INFO_SERVICE);
        this.mXpuController = (IXpuController) carClientWrapper.getController(CarClientWrapper.XP_XPU_SERVICE);
        if (carBaseConfig.isSupportSdc()) {
            IAvmController iAvmController = (IAvmController) carClientWrapper.getController(CarClientWrapper.XP_AVM_SERVICE);
            this.mAvmController = iAvmController;
            iAvmController.registerCallback(new AvmCallbackImpl());
        }
        this.mScenarioController = (IScenarioController) carClientWrapper.getController(CarClientWrapper.XP_USER_SCENARIO_SERVICE);
        if (carBaseConfig.isSupportCdcControl()) {
            this.mCdcController = (ICdcController) carClientWrapper.getController(CarClientWrapper.XP_CDC_SERVICE);
        }
        this.mIsDrvDoorOpened = isDrvDoorOpened();
        this.mIsPsnDoorOpened = isPsnDoorOpened();
        this.mIsDrvSrsUnbelted = this.mMsmController.getDrvBeltStatus() == 1;
        if (carBaseConfig.isSupportPsnWelcomeMode()) {
            this.mIsPsnSrsUnbelted = this.mMsmController.getPsnBeltStatus() == 1;
        }
        ThreadUtils.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$2hmHe7zPR3WNepeUCndXIkgTIPQ
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$new$6$ServiceViewModel();
            }
        }, (long) HIGH_SPEED_WAIT_TIME);
        DataSyncModel dataSyncModel = DataSyncModel.getInstance();
        this.mDataSync = dataSyncModel;
        dataSyncModel.setDataSyncChangeListener(this);
        if (this.mVcuSmartControl == null) {
            this.mVcuSmartControl = VcuSmartControl.getInstance();
        }
        if (this.mChassisSmartControl == null) {
            this.mChassisSmartControl = ChassisSmartControl.getInstance();
        }
        if (this.mHvacSmartControl == null) {
            this.mHvacSmartControl = HvacSmartControl.getInstance();
        }
        HandlerThread handlerThread = new HandlerThread("ServiceVm-ThreadHandler");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.mBgHandler = handler;
        handler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$uy5LsfpZw2YSUVq6ds_L4Rx3RJ0
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$new$7$ServiceViewModel();
            }
        });
        FunctionModel functionModel = FunctionModel.getInstance();
        this.mFunctionModel = functionModel;
        this.mCurrUnlockReqRes = functionModel.getUnlockReqSrc();
        LogUtils.i(TAG, "Constructor mCurrUnlockReqRes: " + this.mCurrUnlockReqRes, false);
        this.mCurrentBonnetClosed = this.mBcmController.getBonnetState() == 0;
        requestCarLicensePlate();
    }

    public /* synthetic */ void lambda$new$6$ServiceViewModel() {
        this.mIsDrvDoorOpened = isDrvDoorOpened();
        this.mIsPsnDoorOpened = isPsnDoorOpened();
        this.mIsDrvSrsUnbelted = this.mMsmController.getDrvBeltStatus() == 1;
        if (this.mCarConfigHelper.isSupportPsnWelcomeMode()) {
            this.mIsPsnSrsUnbelted = this.mMsmController.getPsnBeltStatus() == 1;
        }
    }

    public /* synthetic */ void lambda$new$7$ServiceViewModel() {
        this.mMcuController.registerCallback(this.mMcuCallback);
        this.mScuController.registerCallback(this.mScuCallback);
        this.mXpuController.registerCallback(this.mXpuCallback);
        this.mBcmController.registerCallback(this.mBcmCallback);
        this.mEspController.registerCallback(this.mEspCallback);
        this.mVcuController.registerCallback(this.mVcuCallback);
        this.mMsmController.registerCallback(this.mMsmCallback);
        this.mHvacController.registerCallback(this.mHvacCallback);
        this.mHvacSmartControl.initHvacSmartMode();
        this.mAvasController.registerCallback(this.mAvasCallback);
        this.mTboxController.registerCallback(this.mTboxCallback);
        this.mDiagnosticController.registerCallback(this.mDiagnosticCallback);
        if (this.mCarConfigHelper.isSupportLlu()) {
            this.mLluController.registerCallback(this.mLluCallback);
        }
        if (this.mMcuController.getIgStatusFromMcu() == 1) {
            handleIgStatusChanged(1);
        }
        updateHvacStatus();
        updateCarStatus();
        updateIcmStatus();
    }

    private void updateCarStatus() {
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        CarControl.System.putInt(contentResolver, CarControl.System.CHARGE_STATUS, this.mVcuController.getChargeStatus());
        CarControl.System.putInt(contentResolver, CarControl.System.AVAILABLE_DISTANCE, this.mVcuController.getAvailableMileage());
        int elecPercent = this.mVcuController.getElecPercent();
        if (elecPercent >= 0 && elecPercent <= 100) {
            CarControl.System.putInt(contentResolver, CarControl.System.BATTERY_PERCENT, elecPercent);
        }
        CarControl.System.putInt(contentResolver, CarControl.System.DRIVE_MODE, this.mVcuController.getDriveModeByUser());
        CarControl.System.putBool(contentResolver, CarControl.System.CENTRAL_LOCK, isCentralLocked());
        CarControl.System.putBool(contentResolver, CarControl.System.DRV_OCCUPIED, isDrvSeatOccupied());
        CarControl.System.putBool(contentResolver, CarControl.System.PSN_OCCUPIED, isPsnSeatOccupied());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHvacStatus() {
        if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            return;
        }
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        CarControl.System.putBool(contentResolver, CarControl.System.HVAC_POWER, isHvacPowerModeOn());
        CarControl.System.putBool(contentResolver, CarControl.System.HVAC_AUTO, isHvacAutoModeOn());
        CarControl.System.putFloat(contentResolver, CarControl.System.HVAC_DRV_TEMP, getHvacDriverTemp());
        CarControl.System.putBool(contentResolver, CarControl.System.HVAC_DRV_SYNC, isHvacDriverSyncMode());
        CarControl.System.putFloat(contentResolver, CarControl.System.HVAC_PSN_TEMP, getHvacPsnTemp());
        CarControl.System.putBool(contentResolver, CarControl.System.HVAC_PSN_SYNC, isHvacPsnSyncMode());
        CarControl.System.putInt(contentResolver, CarControl.System.HVAC_WIND_LEVEL, getHvacWindSpeedLevel());
        CarControl.System.putInt(contentResolver, CarControl.System.HVAC_PM25, getHvacInnerPM25());
        CarControl.System.putFloat(contentResolver, CarControl.System.HVAC_OUT_TEMP, getHvacExternalTemp());
        CarControl.System.putBool(contentResolver, CarControl.System.HVAC_FRONT_DEFROST, isHvacFrontDefrostOn());
        CarControl.System.putBool(contentResolver, CarControl.System.HVAC_BACK_DEFROST, this.mBcmController.isMirrorHeatEnabled());
        CarControl.System.putInt(contentResolver, CarControl.System.HVAC_WIND_MODE_COLOR, this.mHvacController.getHvacWindModEconLour());
        QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HVAC_DRIVER_TEMP_FLOAT, Float.valueOf(this.mHvacController.getHvacTempDriver()));
    }

    private void updateIcmStatus() {
        this.mIcmController.setIcmDrvTemp(this.mHvacController.getHvacTempDriver());
        this.mIcmController.setIcmWindBlowMode(this.mHvacController.getHvacWindBlowMode());
        this.mIcmController.setIcmWindLevel(this.mHvacController.getHvacWindSpeedLevel());
    }

    protected void checkDoorBossKeySw() {
        if (this.mCarConfigHelper.isSupportBossKey()) {
            LogUtils.i(TAG, "checkDoorBossKeySw current sw: " + this.mIcmController.getDoorBossKeySw(), false);
            InputManager.getInstance().registerListener(this.mBossKeyListener, App.getInstance().getPackageName(), true);
        }
    }

    private void executeLocalIgTask() {
        Handler handler = this.mBgHandler;
        if (handler != null) {
            handler.postDelayed(this.mLocalIgOnTask, 500L);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void resumeSaveSettings() {
        boolean isMirrorAutoFoldEnable;
        boolean mirrorAutoFoldEnable;
        boolean z;
        LogUtils.i(TAG, "resumeSaveSettings start mIsDataSynced: " + this.mDataSync.mIsDataSynced, false);
        resumeVcuModule();
        resumeOtherVcuFunction();
        resumeChassisModule();
        resumeWheelKeyModule();
        this.mBcmController.setRearFogLamp(false);
        if (this.mDataSync.isHeadLampStateExisted()) {
            this.mDataSync.setHeadLampState(3);
        } else {
            this.mBcmController.setHeadLampState(3, true);
        }
        this.mBcmController.setLedDrlEnable(true);
        this.mBcmController.setEbwEnable(GlobalConstant.DEFAULT.EMERGENCY_BREAK_WARNING);
        if (this.mDataSync.mIsDataSynced) {
            boolean lightMeHome = this.mDataSync.getLightMeHome();
            this.mBcmController.setLightMeHome(lightMeHome, false);
            if (lightMeHome) {
                this.mBcmController.setLightMeHomeTime(this.mDataSync.getLightMeHomeTime(), false);
            }
        }
        this.mBcmController.setDomeLight(1);
        if (this.mCarConfigHelper.isSupportDomeLightBrightnessCtrl() && this.mDataSync.mIsDataSynced) {
            this.mBcmController.setDomeLightBright(this.mDataSync.getDomeLightBright());
        }
        if (CarBaseConfig.getInstance().isSupportRearLogoLight()) {
            this.mBcmController.setRearLogoLightSw(this.mDataSync.getRearLogoLightSw());
        }
        if (this.mCarConfigHelper.isSupportLlu()) {
            if (this.mDataSync.mIsDataSynced) {
                this.mBcmController.setParkLampIncludeFmB(this.mDataSync.getParkLampB());
                boolean lluSw = this.mDataSync.getLluSw();
                this.mLluController.setLluEnable(lluSw);
                if (lluSw) {
                    this.mLluController.setLluWakeWaitSwitch(this.mDataSync.getLluUnlockSw(), true, false);
                    this.mLluController.setLluSleepSwitch(this.mDataSync.getLluLockSw(), true, false);
                    this.mLluController.setLluChargingSwitch(this.mDataSync.getLluChargeSw(), true, false);
                } else {
                    LogUtils.i(TAG, "Set all llu sub effect sw to false, because llu sw is false", false);
                    this.mLluController.setLluWakeWaitSwitch(false, true, true);
                    this.mLluController.setLluSleepSwitch(false, true, true);
                    this.mLluController.setLluChargingSwitch(false, true, true);
                }
            }
        } else {
            LogUtils.d(TAG, "llu not support, force set Park lamp B to false ");
            this.mBcmController.setParkLampIncludeFmB(false, false);
        }
        if (this.mCarConfigHelper.isSupportAtl() && this.mDataSync.mIsDataSynced) {
            this.mAtlController.setAtlSwEnable(this.mDataSync.getAtlSw());
            String atlEffect = this.mDataSync.getAtlEffect();
            if (this.mCarConfigHelper.isSupportFullAtl()) {
                this.mAtlController.setAtlBrightness(this.mDataSync.getAtlBright(), false);
                this.mAtlController.setAtlEffect(atlEffect, false);
                if (this.mAtlController.isAtlDualColor(atlEffect)) {
                    this.mAtlController.setAtlDualColor(atlEffect, this.mDataSync.getAtlDualColorSw(), false);
                }
                int[] atlDualColor = this.mDataSync.getAtlDualColor();
                if (atlDualColor != null && atlDualColor.length >= 2 && atlDualColor[0] != 0 && atlDualColor[1] != 0) {
                    this.mAtlController.setAtlDualColor(atlEffect, atlDualColor[0], atlDualColor[1], false);
                } else {
                    LogUtils.w(TAG, "dualColor data abnormal!");
                }
            }
            this.mAtlController.setAtlSingleColor(atlEffect, this.mDataSync.getAtlSingleColor(), false);
        }
        if (this.mDataSync.mIsDataSynced) {
            this.mBcmController.setDriveAutoLock(this.mDataSync.getDriveAutoLock());
            this.mBcmController.setParkingAutoUnlock(this.mDataSync.getParkAutoUnlock());
            int unlockResponse = this.mDataSync.getUnlockResponse();
            LogUtils.i(TAG, "resume Unlock Response value: " + unlockResponse, false);
            CarBodySmartControl.getInstance().setUnlockResponse(unlockResponse);
        }
        if (this.mDataSync.mIsDataSynced) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$7IVcSrLpib7swelKA_EMZ4YPpx4
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceViewModel.this.lambda$resumeSaveSettings$8$ServiceViewModel();
                }
            });
            this.mBcmController.setAutoDoorHandleEnable(this.mDataSync.getAutoDhc(), true);
        }
        if (this.mCarConfigHelper.isSupportSdc()) {
            this.mBcmController.setSdcKeyOpenCtrlCfg(this.mDataSync.getSdcKeyCfg());
            this.mBcmController.setSdcKeyCloseCtrlCfg(1);
            this.mBcmController.setSdcWindowsAutoDownSwitch(this.mDataSync.getSdcWinAutoDown());
            this.mBcmController.setSdcMaxAutoDoorOpeningAngle(this.mDataSync.getSdcMaxAutoDoorOpeningAngle());
            if (this.mDataSync.mIsDataSynced) {
                this.mBcmController.setSdcBrakeCloseDoorCfg(this.mDataSync.getSdcBrakeCloseCfg());
            }
        }
        if (this.mCarConfigHelper.isSupportSlideDoor()) {
            this.mBcmController.setLeftSlideDoorMode(this.mDataSync.getLeftSlideDoorMode());
            this.mBcmController.setRightSlideDoorMode(this.mDataSync.getRightSlideDoorMode());
        }
        ISeatViewModel iSeatViewModel = (ISeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        if (this.mDataSync.mIsDataSynced) {
            if (this.mCarConfigHelper.isSupportWelcomeMode()) {
                if (!this.mVcuController.isExhibitionModeOn()) {
                    boolean welcomeMode = this.mDataSync.getWelcomeMode();
                    iSeatViewModel.setWelcomeMode(welcomeMode);
                    if (welcomeMode) {
                        if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                            iSeatViewModel.setDriverAllPositionsToDomain();
                        } else {
                            iSeatViewModel.setDriverAllPositionsToMcu();
                        }
                    }
                } else {
                    iSeatViewModel.setWelcomeMode(false);
                }
            }
            if (this.mCarConfigHelper.isSupportRearSeatWelcomeMode()) {
                this.mBcmController.setRearSeatWelcomeMode(this.mDataSync.getRearSeatWelcomeMode(), false);
            }
        }
        if (this.mCarConfigHelper.isSupportPsnWelcomeMode() && !this.mVcuController.isExhibitionModeOn()) {
            boolean psnWelcomeMode = this.mDataSync.getPsnWelcomeMode();
            iSeatViewModel.setPsnWelcomeMode(psnWelcomeMode);
            if (psnWelcomeMode) {
                if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                    iSeatViewModel.setPsnAllPositionsToDomain();
                } else {
                    iSeatViewModel.setPsnSeatAllPositionsToMcu();
                }
            } else {
                iSeatViewModel.setPsnWelcomeMode(false);
            }
        }
        if (this.mCarConfigHelper.isSupportCarpetLightWelcomeMode()) {
            this.mBcmController.setCarpetLightWelcomeMode(this.mDataSync.getCarpetLightWelcomeMode());
        }
        if (this.mCarConfigHelper.isSupportPollingLightWelcomeMode()) {
            this.mBcmController.setPollingLightWelcomeMode(this.mDataSync.getPollingLightWelcomeMode());
        }
        if (BaseFeatureOption.getInstance().isSupportWelcomeSwitchOff()) {
            iSeatViewModel.setWelcomeMode(false);
        }
        if (this.mCarConfigHelper.isSupportSeatMassage()) {
            this.mMsmController.setDriverSeatLumbControlSwitchEnable(this.mDataSync.getDrvLumbControlSw());
            this.mMsmController.setPassengerSeatLumbControlSwitchEnable(this.mDataSync.getPsnLumbControlSw());
        }
        if (!CarBaseConfig.getInstance().isSupportRearBeltWarningSwitch() || BaseFeatureOption.getInstance().isSupportRsbWarningReset()) {
            this.mMsmController.setBackBeltSw(true);
        } else if (this.mDataSync.mIsDataSynced) {
            this.mMsmController.setBackBeltSw(this.mDataSync.getRsbWarning());
        }
        if (this.mCarConfigHelper.isSupportEsb()) {
            SeatSmartControl.getInstance().onFollowedVehicleLostConfigChanged(true);
            if (this.mCarConfigHelper.isFollowVehicleLostConfigUseNew()) {
                SeatSmartControl.getInstance().setFollowedVehicleLostConfig("2");
            }
        }
        if (this.mCarConfigHelper.isSupportMsmP()) {
            if (this.mDataSync.getPsnSeatPos() == null) {
                LogUtils.d(TAG, "save current seat pos on resume save" + this.mMsmController.isPsnTiltMovingSafe());
                if (this.mMsmController.isPsnTiltMovingSafe()) {
                    IMsmController iMsmController = this.mMsmController;
                    iMsmController.savePsnSeatPos(new int[]{iMsmController.getPSeatHorzPos(), this.mMsmController.getPSeatVerPos(), this.mMsmController.getPSeatTiltPos(), this.mMsmController.getPSeatLegPos()});
                } else {
                    IMsmController iMsmController2 = this.mMsmController;
                    iMsmController2.savePsnSeatPos(iMsmController2.getPsnSeatDefaultPos());
                }
                if (BaseFeatureOption.getInstance().isSupportPsnThreeSavedPos()) {
                    setPsnSeatPosToMultipleSaved();
                }
            }
            if (BaseFeatureOption.getInstance().isSupportPsnThreeSavedPos()) {
                setPsnSeatPosToMultipleSaved();
            }
        }
        if (this.mCarConfigHelper.isSupportMirrorMemory()) {
            if (this.mHasPendingUpdate && this.mDataSync.mIsDataSynced) {
                this.mHasPendingUpdate = false;
                if (TextUtils.isEmpty(this.mDataSync.getMirrorData())) {
                    Mirror mirror = new Mirror();
                    mirror.leftHPos = this.mBcmController.getLeftMirrorLRPos(true);
                    mirror.leftVPos = this.mBcmController.getLeftMirrorUDPos(true);
                    mirror.rightHPos = this.mBcmController.getRightMirrorLRPos(true);
                    mirror.rightVPos = this.mBcmController.getRightMirrorUDPos(true);
                    String mirror2 = mirror.toString();
                    LogUtils.i(TAG, "Mirror position empty, save currentValue: " + mirror2, false);
                    this.mDataSync.setMirrorData(mirror2);
                    z = false;
                } else {
                    LogUtils.i(TAG, "Has pending update: resume mirror data: " + this.mDataSync.getMirrorData(), false);
                    this.mDataSync.setMirrorRestoreFinished();
                    z = true;
                }
                this.mBcmController.setReverseMirrorMode(this.mDataSync.getMirrorReverseMode(), false);
                CabinSmartControl.getInstance().syncAccountMirrorPos(this.mDataSync.getMirrorData(), z);
            }
            CabinSmartControl.getInstance().resetMirrorFoldState();
        }
        if (this.mCarConfigHelper.isSupportCms() && this.mDataSync.mIsDataSynced) {
            boolean cmsAutoBrightSw = this.mDataSync.getCmsAutoBrightSw();
            boolean cmsAutoBrightSw2 = this.mBcmController.getCmsAutoBrightSw();
            if (cmsAutoBrightSw != cmsAutoBrightSw2) {
                this.mBcmController.setCmsAutoBrightSw(cmsAutoBrightSw);
                LogUtils.i(TAG, "Cms AutoBrightSw, resume saved value: " + cmsAutoBrightSw + ", currentValue: " + cmsAutoBrightSw2, false);
            }
            float[] savedCmsLocation = this.mBcmController.getSavedCmsLocation();
            float[] cmsLocation = this.mBcmController.getCmsLocation();
            if (savedCmsLocation != null && !Mirror.isEquals(savedCmsLocation, cmsLocation)) {
                this.mBcmController.setCmsLocation(savedCmsLocation);
                LogUtils.i(TAG, "Cms Location/Pos, resume saved value: " + GsonUtil.toJson(savedCmsLocation) + ", currentValue: " + GsonUtil.toJson(cmsLocation), false);
            }
            boolean cmsHighSpdSw = this.mDataSync.getCmsHighSpdSw();
            boolean cmsHighSpdAssistSw = this.mBcmController.getCmsHighSpdAssistSw();
            if (cmsHighSpdSw != cmsHighSpdAssistSw) {
                this.mBcmController.setCmsHighSpdAssistSw(cmsHighSpdSw);
                LogUtils.i(TAG, "Cms HighSpdAssistSw, resume saved value: " + cmsHighSpdSw + ", currentValue: " + cmsHighSpdAssistSw, false);
            }
            boolean cmsTurnSw = this.mDataSync.getCmsTurnSw();
            boolean cmsTurnAssistSw = this.mBcmController.getCmsTurnAssistSw();
            if (cmsTurnSw != cmsTurnAssistSw) {
                this.mBcmController.setCmsTurnAssistSw(cmsTurnSw);
                LogUtils.i(TAG, "Cms CmsTurnAssistSw, resume saved value: " + cmsTurnSw + ", currentValue: " + cmsTurnAssistSw, false);
            }
            int cmsViewAngle = this.mDataSync.getCmsViewAngle();
            int cmsViewAngle2 = this.mBcmController.getCmsViewAngle();
            if (cmsViewAngle != 0 && cmsViewAngle != cmsViewAngle2) {
                this.mBcmController.setCmsViewAngle(cmsViewAngle);
                LogUtils.i(TAG, "Cms ViewAngle, resume saved value: " + cmsViewAngle + ", currentValue: " + cmsViewAngle2, false);
            }
            boolean cmsObjectRecognizeSw = this.mDataSync.getCmsObjectRecognizeSw();
            boolean cmsObjectRecognizeSw2 = this.mBcmController.getCmsObjectRecognizeSw();
            if (cmsObjectRecognizeSw != cmsObjectRecognizeSw2) {
                this.mBcmController.setCmsObjectRecognizeSw(cmsObjectRecognizeSw);
                LogUtils.i(TAG, "Cms ObjectRecognizeSw, resume saved value: " + cmsObjectRecognizeSw + ", currentValue: " + cmsObjectRecognizeSw2, false);
            }
        }
        if (this.mCarConfigHelper.isSupportIms()) {
            int imsMode = this.mDataSync.getImsMode();
            int imsMode2 = this.mBcmController.getImsMode();
            if (imsMode != imsMode2) {
                this.mBcmController.setImsMode(imsMode);
                LogUtils.i(TAG, "Ims Mode, resume saved value: " + imsMode + ", currentValue: " + imsMode2, false);
            }
            int imsAutoVisionSw = this.mDataSync.getImsAutoVisionSw();
            int imsAutoVisionSw2 = this.mBcmController.getImsAutoVisionSw();
            if (imsAutoVisionSw != imsAutoVisionSw2) {
                this.mBcmController.setImsAutoVisionSw(imsAutoVisionSw);
                LogUtils.i(TAG, "Ims AutoVisionSw, resume saved value: " + imsAutoVisionSw + ", currentValue: " + imsAutoVisionSw2, false);
            }
        }
        if (BaseFeatureOption.getInstance().isSupportMirrorAutoFoldOff() && this.mDataSync.mIsDataSynced && (mirrorAutoFoldEnable = this.mDataSync.getMirrorAutoFoldEnable()) != (isMirrorAutoFoldEnable = this.mBcmController.isMirrorAutoFoldEnable())) {
            this.mBcmController.setMirrorAutoFoldEnable(mirrorAutoFoldEnable);
            LogUtils.i(TAG, "Mirror autofold sw, resume saved value: " + mirrorAutoFoldEnable + ", currentValue: " + isMirrorAutoFoldEnable, false);
        }
        resumeTrunkSettings();
        if (this.mDataSync.mIsDataSynced) {
            this.mBcmController.setHighSpdCloseWin(this.mDataSync.isWinHighSpdEnabled());
            this.mBcmController.setAutoWindowLockSw(this.mDataSync.getLockCloseWin());
            this.mBcmController.setWiperSensitivity(this.mDataSync.getWiperSensitivity(), true, true);
        }
        if (this.mCarConfigHelper.isSupportWindowLock()) {
            this.mBcmController.setWindowLockState(this.mDataSync.getWindowLockState());
        }
        if (this.mCarConfigHelper.isSupportCwc() && this.mDataSync.mIsDataSynced) {
            this.mBcmController.setCwcSwEnable(this.mDataSync.getCwcSw());
        }
        if (this.mCarConfigHelper.isSupportCwcFR()) {
            this.mBcmController.setCwcFRSwEnable(this.mDataSync.getCwcFRSw());
        }
        if (this.mCarConfigHelper.isSupportCwcRL()) {
            this.mBcmController.setCwcRLSwEnable(this.mDataSync.getCwcRLSw());
        }
        if (this.mCarConfigHelper.isSupportCwcRR()) {
            this.mBcmController.setCwcRRSwEnable(this.mDataSync.getCwcRRSw());
        }
        resumeHvacModule();
        if (AvasSmartControl.getInstance().isSettingLowSpdEnable()) {
            this.mAvasController.setLowSpdSoundEnable(true);
            if (this.mDataSync.mIsDataSynced) {
                this.mAvasController.setLowSpdSoundType(this.mDataSync.getAvasEffect());
            }
        } else {
            this.mAvasController.setLowSpdSoundEnable(false);
        }
        if (this.mDataSync.mIsDataSynced) {
            this.mAvasController.setBootSoundEffect(this.mDataSync.getBootEffect());
        }
        resumeXPilotModule();
        this.mFunctionModel.setChargePortRetryTime(0);
        if (this.mCarConfigHelper.isSupportNfc()) {
            LogUtils.d(TAG, "resume nfc key");
            this.mBcmController.setStopNfcCardSw(this.mDataSync.getNfcKeyEnable());
        }
        if (this.mScuController.getRemoteCameraSw()) {
            this.mScuController.setRemoteCameraSw(false);
        }
        if (!CarBaseConfig.getInstance().isSupportDsmCamera() && this.mScuController.getDsmSwStatus() == 1) {
            this.mScuController.setDsmSw(false);
        }
        this.mIsDataSyncEmpty = false;
        LogUtils.i(TAG, "resumeSaveSettings end", false);
    }

    public /* synthetic */ void lambda$resumeSaveSettings$8$ServiceViewModel() {
        if (this.mCarConfigHelper.isSupportChildMode()) {
            IBcmController iBcmController = this.mBcmController;
            iBcmController.setChildModeEnable(iBcmController.isChildModeEnable(), false);
        } else if (this.mCarConfigHelper.isSupportChildLock()) {
            this.mBcmController.setChildLock(true, this.mDataSync.getChildLeftLock());
            if (!this.mCarConfigHelper.isSupportNewChildLock()) {
                try {
                    Thread.sleep(650L);
                } catch (InterruptedException unused) {
                    return;
                }
            }
            this.mBcmController.setChildLock(false, this.mDataSync.getChildRightLock());
        }
    }

    private void setPsnSeatPosToMultipleSaved() {
        if (this.mDataSync.getPsnSelectSeatPos(0) == null) {
            IMsmController iMsmController = this.mMsmController;
            iMsmController.savePsnSeatPos(new int[]{iMsmController.getPSeatHorzPos(), this.mMsmController.getPSeatVerPos(), this.mMsmController.getPSeatTiltPos(), this.mMsmController.getPSeatLegPos()}, 0);
        }
        if (this.mDataSync.getPsnSelectSeatPos(1) == null) {
            IMsmController iMsmController2 = this.mMsmController;
            iMsmController2.savePsnSeatPos(new int[]{iMsmController2.getPSeatHorzPos(), this.mMsmController.getPSeatVerPos(), this.mMsmController.getPSeatTiltPos(), this.mMsmController.getPSeatLegPos()}, 1);
        }
        if (this.mDataSync.getPsnSelectSeatPos(2) == null) {
            IMsmController iMsmController3 = this.mMsmController;
            iMsmController3.savePsnSeatPos(new int[]{iMsmController3.getPSeatHorzPos(), this.mMsmController.getPSeatVerPos(), this.mMsmController.getPSeatTiltPos(), this.mMsmController.getPSeatLegPos()}, 2);
        }
    }

    private void setRLSeatPosToMultipleSaved() {
        int[] rLSeatNowPos = this.mMsmController.getRLSeatNowPos();
        if (this.mDataSync.getRLSelectSeatPos(0) == null) {
            this.mMsmController.saveRLSeatPos(rLSeatNowPos, 0);
        }
        if (this.mDataSync.getRLSelectSeatPos(1) == null) {
            this.mMsmController.saveRLSeatPos(rLSeatNowPos, 1);
        }
        if (this.mDataSync.getRLSelectSeatPos(2) == null) {
            this.mMsmController.saveRLSeatPos(rLSeatNowPos, 2);
        }
    }

    private void setRRSeatPosToMultipleSaved() {
        int[] rRSeatNowPos = this.mMsmController.getRRSeatNowPos();
        if (this.mDataSync.getRRSelectSeatPos(0) == null) {
            this.mMsmController.saveRRSeatPos(rRSeatNowPos, 0);
        }
        if (this.mDataSync.getRRSelectSeatPos(1) == null) {
            this.mMsmController.saveRRSeatPos(rRSeatNowPos, 1);
        }
        if (this.mDataSync.getRRSelectSeatPos(2) == null) {
            this.mMsmController.saveRRSeatPos(rRSeatNowPos, 2);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void onIgLocalOn() {
        LogUtils.d(TAG, "Local ig on", false);
        Handler handler = this.mBgHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mLocalIgOnTask);
        }
        HvacSmartControl.HVAC_SINGLE_MODE_ENABLE = true;
        LogUtils.i(TAG, "mCurrUnlockReqRes: " + this.mCurrUnlockReqRes, false);
        FunctionModel.getInstance().setAirProtectTs(System.nanoTime());
        FunctionModel.getInstance().setIgonTime(System.currentTimeMillis());
        FunctionModel.getInstance().setIgonNanoTime(System.nanoTime());
        this.mIsLocalIgOn = true;
        checkTelescopeMrr();
        executeLocalIgTask();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void onIgRemoteOn() {
        LogUtils.d(TAG, "Remote ig on", false);
        this.mIsLocalIgOn = false;
        if (!BaseFeatureOption.getInstance().isSupportFollowMeHomeWhenRemoteInOn()) {
            this.mBcmController.setLightMeHome(false, false);
        }
        this.mHvacSmartControl.removeSingleModeRunnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void onIgOff() {
        LogUtils.d(TAG, "Ig off", false);
        dismissReadyDisableDialog();
        if (this.mIsLocalIgOn) {
            LogUtils.i(TAG, "Ig off, reset unlock req src", false);
            this.mCurrUnlockReqRes = 0;
            this.mFunctionModel.setUnlockReqSrc(this.mCurrUnlockReqRes);
            this.mFunctionModel.setRequestCarPlateTs(0L);
            this.mIsLocalIgOn = false;
            this.mIsBcmReadyEnabled = false;
        }
        this.mFunctionModel.setRemindWarningTs(0L);
        this.mFunctionModel.setTelescopeRemindTs(0L);
        dismissTelescopeMrrDialog();
        CountDownLatch countDownLatch = this.mResumeSaveSettingLock;
        if (countDownLatch == null || countDownLatch.getCount() == 0) {
            this.mResumeSaveSettingLock = new CountDownLatch(1);
        }
        this.mIsInitComplete = false;
        this.mHvacSmartControl.removeSingleModeRunnable();
        dismissMicrophoneDialog();
        AccountViewModel accountViewModel = (AccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class);
        if (accountViewModel.checkLogin()) {
            this.mAccountInfo = accountViewModel.getCurrentAccountInfo();
            LogUtils.d(TAG, "onIgOff account info: " + this.mAccountInfo, false);
            return;
        }
        LogUtils.d(TAG, "onIgOff do not login", false);
        this.mAccountInfo = null;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void handleEmergencyIgOff() {
        SeatSmartControl.getInstance().handleEmergencyIgOff();
    }

    void resumeVcuModule() {
        int driveMode;
        if (this.mDataSync.mIsDataSynced) {
            if (!this.mCarConfigHelper.isSupportDriveModeNewArch()) {
                if (this.mCarConfigHelper.isSupportDriveEnergyReset()) {
                    driveMode = 0;
                } else if (this.mDataSync.isXpedal()) {
                    driveMode = 5;
                } else {
                    driveMode = this.mDataSync.isAntiSicknessEnabled() ? 7 : this.mDataSync.getDriveMode();
                }
                LogUtils.i(TAG, "Resume drive mode: " + driveMode, false);
                if (this.mCarConfigHelper.isSupportXSport() && driveMode == 1001) {
                    this.mVcuSmartControl.resumeDriveMode(1001);
                    return;
                }
                if (driveMode == 1 && !BaseFeatureOption.getInstance().isSupportEcoDriveMode()) {
                    LogUtils.i(TAG, "Eco drive mode not support, resume Normal drive mode");
                    driveMode = GlobalConstant.DEFAULT.DRIVER_MODE;
                }
                this.mVcuController.setDriveMode(driveMode);
                if (BaseFeatureOption.getInstance().isSupportXSportRacerMode() && 4 == this.mVcuController.getXSportDrivingMode()) {
                    this.mVcuController.exitXSportDriveMode();
                }
                FunctionModel.getInstance().setDriveModeChangedByUser(true);
                if (driveMode == 0 || driveMode == 1 || driveMode == 2) {
                    if (driveMode != 1) {
                        FunctionModel.getInstance().setLastDriveMode(-1);
                    }
                    if (this.mCarConfigHelper.isSupportSnowMode()) {
                        this.mVcuController.setSnowMode(false);
                        if (this.mCarConfigHelper.isSupportDriveEnergyReset()) {
                            FunctionModel.getInstance().setSnowModeEnergyCache(-1);
                            this.mVcuController.setEnergyRecycleGrade(3);
                            return;
                        }
                        int snowModeEnergyCache = FunctionModel.getInstance().getSnowModeEnergyCache();
                        if (snowModeEnergyCache != -1) {
                            this.mVcuController.setEnergyRecycleGrade(snowModeEnergyCache);
                            FunctionModel.getInstance().setSnowModeEnergyCache(-1);
                            return;
                        }
                        this.mVcuController.setEnergyRecycleGrade(this.mDataSync.getRecycleGrade());
                        return;
                    }
                    this.mVcuController.setEnergyRecycleGrade(this.mCarConfigHelper.isSupportDriveEnergyReset() ? 3 : this.mDataSync.getRecycleGrade());
                    return;
                }
                return;
            }
            this.mVcuSmartControl.resumeDriveMode(this.mDataSync.getDriveMode(), true);
        }
    }

    void resumeOtherVcuFunction() {
        if (this.mCarConfigHelper.isSupportNeutralGearProtect() && this.mDataSync.mIsDataSynced) {
            this.mVcuController.setNGearWarningSwitch(this.mDataSync.getNGearWarningSwitch());
        }
        if (CarBaseConfig.getInstance().isSupportAwdSetting()) {
            this.mVcuController.setAwdSetting(this.mDataSync.getAwdSetting());
        }
    }

    void resumeChassisModule() {
        if ((!this.mCarConfigHelper.isSupportXSport() || this.mDataSync.getDriveMode() != 1001) && this.mDataSync.mIsDataSynced) {
            if (!this.mCarConfigHelper.isSupportEpsTorque() || Math.abs(this.mEpsController.getTorsionBarTorque()) <= this.mCarConfigHelper.getEpsTorsionBarThreshold()) {
                this.mEpsController.setSteeringEps(this.mDataSync.getSteerMode());
            } else {
                LogUtils.w(TAG, "TorsionBarTorque > threshold, not allowed to resume EPS setting", false);
            }
        }
        this.mEspController.setEspSw(true);
        if (this.mCarConfigHelper.isSupportHdc()) {
            this.mEspController.setHdc(false);
        }
        if (this.mDataSync.mIsDataSynced) {
            this.mChassisSmartControl.checkAvhCondition();
        }
        this.mChassisSmartControl.checkEspCondition();
        if (this.mCarConfigHelper.isSupportAirSuspension()) {
            this.mBcmController.setAsWelcomeMode(this.mDataSync.getAsWelcomeMode());
            this.mBcmController.setAirSuspensionRepairMode(false);
            this.mBcmController.setEasyLoadingSwitch(false);
            this.mVcuController.setAutoEasyLoadMode(this.mDataSync.getAutoEasyLoadStatus());
            this.mBcmController.setAsLocationControlSw(this.mDataSync.getAsLocationControlSw());
        }
        if (this.mCarConfigHelper.isSupportTrailerMode()) {
            this.mVcuController.setTrailerMode(false);
            this.mBcmController.setTransportMode(false);
        }
        if (this.mCarConfigHelper.isSupportTrailerRv()) {
            boolean trailerHitchStatus = this.mDataSync.getTrailerHitchStatus();
            this.mBcmController.setTrailerModeStatus(trailerHitchStatus);
            this.mEspController.setEspTsmSwitchStatus(trailerHitchStatus);
            this.mBcmController.setAsTrailerModeSwitchStatus(trailerHitchStatus);
        }
        if (this.mCarConfigHelper.isSupportCdcControl() && this.mDataSync.mIsDataSynced) {
            this.mCdcController.setCdcMode(this.mDataSync.getCdcMode(), false);
        }
        if (this.mCarConfigHelper.isSupportEspCst()) {
            this.mEspController.setEspCstSw(false);
        }
        if (this.mCarConfigHelper.isSupportEspBpf()) {
            this.mEspController.setEspBpfMode(this.mDataSync.getEspBpfMode());
        }
    }

    void resumeWheelKeyModule() {
        this.mIcmController.setTouchRotationDirection(this.mDataSync.getWheelTouchDirection());
        this.mIcmController.setTouchRotationSpeed(this.mDataSync.getWheelTouchSpeed(), true);
        if (this.mDataSync.mIsDataSynced) {
            int wheelXKey = this.mDataSync.getWheelXKey();
            if (wheelXKey == 4 && !this.mCarConfigHelper.isSupportAutoPark()) {
                LogUtils.i(TAG, "Not support auto park, do not resume xkey for: " + wheelXKey, false);
                this.mIcmController.setXKeyForCustomer(0);
            } else if (wheelXKey == 9) {
                LogUtils.i(TAG, "Not support xsport, do not resume xkey for: " + wheelXKey, false);
                this.mIcmController.setXKeyForCustomer(0);
            } else if (wheelXKey == 10 && !this.mCarConfigHelper.isSupportNra()) {
                LogUtils.i(TAG, "Not support nra, do not resume xkey for: " + wheelXKey, false);
                this.mIcmController.setXKeyForCustomer(0);
            } else {
                this.mIcmController.setXKeyForCustomer(wheelXKey);
            }
            int doorBossKey = this.mDataSync.getDoorBossKey();
            if (doorBossKey == 1 && !BaseFeatureOption.getInstance().isShowDoorKeySpeech()) {
                LogUtils.i(TAG, "Not support Speech, do not resume doorKey for: " + doorBossKey, false);
                this.mIcmController.setDoorKeyForCustomer(0);
            } else {
                this.mIcmController.setDoorKeyForCustomer(doorBossKey);
            }
            if (this.mCarConfigHelper.isSupportWheelKeyProtect()) {
                this.mIcmController.setWheelKeyProtectSw(this.mDataSync.getWheelKeyProtect());
            }
        }
    }

    void resumeTrunkSettings() {
        if (this.mDataSync.mIsDataSynced && this.mCarConfigHelper.isSupportSensorTrunk()) {
            this.mBcmController.setTrunkSensorEnable(this.mDataSync.getSensorTrunkSw());
        }
    }

    void resumeHvacModule() {
        if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            return;
        }
        openDefaultAutoDefog();
        if (!BaseFeatureOption.getInstance().isHvacDataMemoryFromRDCU()) {
            this.mHvacController.setHvacCirculationTime(this.mDataSync.getHvacCircle());
            if (this.mCarConfigHelper.isSupportAqs()) {
                this.mHvacController.setHvacAqsLevel(this.mDataSync.getHvacAqsLevel());
            }
            this.mHvacController.setHvacSelfDryEnable(this.mDataSync.getHvacSelfDry());
        }
        if (this.mCarConfigHelper.isSupportSmartHvac()) {
            this.mHvacController.setAirAutoProtectedMode(this.mDataSync.getHvacAirProtectMode());
            this.mHvacController.setSmartHvacEnable(this.mDataSync.getHvacSmart());
        }
        if (this.mCarConfigHelper.isSupportDrvSeatHeat()) {
            this.mBcmController.restoreSeatHeatLevel();
        }
        if (this.mCarConfigHelper.isSupportDrvSeatVent()) {
            this.mBcmController.restoreSeatVentLevel();
        }
        if (BaseFeatureOption.getInstance().isSupportHvacPsnSeatRecovery()) {
            igOnClosePsnSeatHeatVentState();
        }
        if (this.mCarConfigHelper.isSupportSteerHeat()) {
            this.mBcmController.restoreSteerHeatLevel();
        }
        checkStoredHvacModeData();
    }

    private void checkStoredHvacModeData() {
        ((IHvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class)).setHvacModeData(false);
    }

    void igOnClosePsnSeatHeatVentState() {
        LogUtils.i(TAG, "igOnClosePsnSeatHeatVentState...", false);
        if (this.mCarConfigHelper.isSupportPsnSeatHeat()) {
            int psnSeatHeatLevel = this.mBcmController.getPsnSeatHeatLevel();
            LogUtils.i(TAG, "setPsnSeatHeatLevel... level:" + psnSeatHeatLevel, false);
            if (SeatVentLevel.Off != SeatVentLevel.fromBcmState(psnSeatHeatLevel)) {
                this.mBcmController.setPsnSeatHeatLevel(SeatVentLevel.Off.ordinal());
            }
        }
        if (this.mCarConfigHelper.isSupportPsnSeatVent()) {
            int psnSeatVentLevel = this.mBcmController.getPsnSeatVentLevel();
            LogUtils.i(TAG, "setPsnSeatVentLevel... level:" + psnSeatVentLevel, false);
            if (SeatVentLevel.Off != SeatVentLevel.fromBcmState(psnSeatVentLevel)) {
                this.mBcmController.setPsnSeatVentLevel(SeatVentLevel.Off.ordinal());
            }
        }
    }

    void resumeMicrophoneModule() {
        if (this.mDataSync.mIsDataSynced) {
            ((IAudioViewModel) ViewModelManager.getInstance().getViewModelImplSync(IAudioViewModel.class)).setMicrophoneMute(this.mDataSync.getMicrophoneMute());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void resumeOrSyncLccAlcState(boolean isResume) {
        LogUtils.i(TAG, "resumeOrSyncLccAlcState...");
        if (!this.mCarConfigHelper.isSupportLcc()) {
            this.mScuController.setLccState(false);
            this.mScuController.setAlcState(false);
            LogUtils.i(TAG, "Not support lcc.");
        } else if (!this.mCarConfigHelper.isSupportXPilotSafeExam()) {
            this.mScuController.setLccState(this.mDataSync.getLccSw());
            if (isResume && BaseFeatureOption.getInstance().isForceCloseAlcWhenIgOn()) {
                LogUtils.i(TAG, "force close alc.");
                this.mScuController.setAlcState(false);
            } else {
                this.mScuController.setAlcState(this.mDataSync.getAlcSw());
            }
            LogUtils.i(TAG, "Not support lcc exam.");
        } else if (!this.mDataSync.mIsDataSynced) {
            LogUtils.i(TAG, "Data sync is not complete,ignore the follow step.");
        } else if (this.mDataSync.isGuest()) {
            this.mScuController.setLccState(false);
            this.mScuController.setAlcState(false);
            LogUtils.i(TAG, "User  not login.");
        } else {
            handleLccWhenLogin();
        }
    }

    protected void handleLccWhenLogin() {
        boolean z;
        boolean lccSw = this.mDataSync.getLccSw();
        boolean alcSw = this.mDataSync.getAlcSw();
        boolean shouldUpdateXngpSafeExam = this.mCarConfigHelper.isSupportXNgp() ? this.mDataSync.shouldUpdateXngpSafeExam() : this.mDataSync.shouldUpdateLccSafeExam();
        boolean z2 = this.mScuController.getLccState() == 1;
        LogUtils.i(TAG, "Need to update lcc exam result ?, shouldUpdateLccSafeExam: " + shouldUpdateXngpSafeExam + ", lccStateFromScu: " + z2 + ", lccState: " + lccSw);
        if (shouldUpdateXngpSafeExam && (lccSw || z2)) {
            if (this.mCarConfigHelper.isSupportXNgp()) {
                updateXngpSafeExamResult();
            } else {
                updateLccSafeExamResult();
            }
            this.mDataSync.setLccSw(true);
            lccSw = true;
        }
        if (lccSw) {
            if (this.mCarConfigHelper.isSupportXNgp()) {
                z = this.mDataSync.getXngpSafeExamResult();
            } else if (this.mCarConfigHelper.isSupportLidar() && BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
                z = this.mDataSync.getSuperLccSafeExamResult();
            } else {
                z = this.mDataSync.getSuperLccSafeExamResult() || this.mDataSync.getLccSafeExamResult();
            }
            if (!z) {
                LogUtils.i(TAG, "Lcc memory from account is ON,but no exam passed record in local!");
                this.mDataSync.setLccSw(false);
                this.mDataSync.setAlcSw(false);
                lccSw = false;
                alcSw = false;
            } else {
                LogUtils.i(TAG, "Already pass the exam,keep lcc on.");
            }
        } else {
            LogUtils.i(TAG, "Lcc value from account is off,keep lcc off.");
            this.mDataSync.setAlcSw(false);
            alcSw = false;
        }
        if (lccSw != z2) {
            this.mScuController.setLccState(lccSw);
        }
        if (alcSw != (this.mScuController.getAlcState() == 1)) {
            this.mScuController.setAlcState(alcSw);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void resumeOrSyncApaState(boolean r8) {
        /*
            r7 = this;
            com.xiaopeng.carcontrol.config.CarBaseConfig r8 = r7.mCarConfigHelper
            boolean r8 = r8.isSupportAutoPark()
            r0 = 0
            java.lang.String r1 = "ServiceViewModel"
            if (r8 != 0) goto L11
            java.lang.String r8 = "Not support Apa."
            com.xiaopeng.carcontrol.util.LogUtils.i(r1, r8, r0)
            return
        L11:
            com.xiaopeng.carcontrol.model.DataSyncModel r8 = r7.mDataSync
            boolean r8 = r8.mIsDataSynced
            if (r8 != 0) goto L1d
            java.lang.String r8 = "User account data has not synced."
            com.xiaopeng.carcontrol.util.LogUtils.i(r1, r8, r0)
            return
        L1d:
            com.xiaopeng.carcontrol.model.DataSyncModel r8 = r7.mDataSync
            boolean r8 = r8.isGuest()
            if (r8 == 0) goto L30
            com.xiaopeng.carcontrol.carmanager.controller.IScuController r8 = r7.mScuController
            r8.setAutoParkSw(r0)
            java.lang.String r8 = "Not login,keep apa off."
            com.xiaopeng.carcontrol.util.LogUtils.i(r1, r8, r0)
            return
        L30:
            com.xiaopeng.carcontrol.config.CarBaseConfig r8 = r7.mCarConfigHelper
            boolean r8 = r8.isSupportXPilotSafeExam()
            if (r8 != 0) goto L49
            com.xiaopeng.carcontrol.carmanager.controller.IScuController r8 = r7.mScuController
            com.xiaopeng.carcontrol.model.DataSyncModel r0 = r7.mDataSync
            boolean r0 = r0.getAutoParkSw()
            r8.setAutoParkSw(r0)
            java.lang.String r8 = "Not support apa exam."
            com.xiaopeng.carcontrol.util.LogUtils.i(r1, r8)
            return
        L49:
            com.xiaopeng.carcontrol.carmanager.controller.IScuController r8 = r7.mScuController
            int r8 = r8.getAutoParkSw()
            r2 = 1
            if (r8 != r2) goto L54
            r8 = r2
            goto L55
        L54:
            r8 = r0
        L55:
            com.xiaopeng.carcontrol.model.DataSyncModel r3 = r7.mDataSync
            boolean r3 = r3.shouldUpdateApaSafeExam()
            com.xiaopeng.carcontrol.model.DataSyncModel r4 = r7.mDataSync
            boolean r4 = r4.getAutoParkSw()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Start to resume Auto Park From Scu: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r6 = " shouldUpdateApaSafeExam: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.String r5 = r5.toString()
            com.xiaopeng.carcontrol.util.LogUtils.i(r1, r5, r0)
            if (r3 == 0) goto L90
            if (r8 != 0) goto L87
            if (r4 == 0) goto L90
        L87:
            r7.updateApaSafeExamResult()
            com.xiaopeng.carcontrol.model.DataSyncModel r3 = r7.mDataSync
            r3.setAutoParkSw(r2)
            goto L91
        L90:
            r2 = r4
        L91:
            if (r2 == 0) goto Lac
            com.xiaopeng.carcontrol.model.DataSyncModel r3 = r7.mDataSync
            boolean r3 = r3.getApaSafeExamResult()
            if (r3 != 0) goto La6
            java.lang.String r2 = "Apa memory from account is ON,but no exam passed record in local!"
            com.xiaopeng.carcontrol.util.LogUtils.i(r1, r2)
            com.xiaopeng.carcontrol.model.DataSyncModel r1 = r7.mDataSync
            r1.setAutoParkSw(r0)
            goto Lb2
        La6:
            java.lang.String r0 = "Already pass the exam,keep autoPark on."
            com.xiaopeng.carcontrol.util.LogUtils.i(r1, r0)
            goto Lb1
        Lac:
            java.lang.String r0 = "AutoPark value from account is off,keep apa off."
            com.xiaopeng.carcontrol.util.LogUtils.i(r1, r0)
        Lb1:
            r0 = r2
        Lb2:
            if (r0 == r8) goto Lb9
            com.xiaopeng.carcontrol.carmanager.controller.IScuController r8 = r7.mScuController
            r8.setAutoParkSw(r0)
        Lb9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel.resumeOrSyncApaState(boolean):void");
    }

    protected void resumeOrDataSyncMemExamResult(boolean isResume) {
        LogUtils.i(TAG, "resumeOrDataSyncMemExamResult: " + isResume, false);
        if (!BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
            LogUtils.i(TAG, "Not support Lidar safe exam, no resume super vpa", false);
        } else if (!this.mCarConfigHelper.isSupportLidar()) {
            LogUtils.i(TAG, "Not support Lidar, no resume super vpa", false);
        } else if (!this.mCarConfigHelper.isSupportMemPark()) {
            LogUtils.i(TAG, "Not support Mem park,no resume super vpa", false);
        } else if (!this.mCarConfigHelper.isSupportXPilotSafeExam()) {
            LogUtils.i(TAG, "Not support mem exam,no resume super vpa.");
        } else if (!this.mDataSync.mIsDataSynced) {
            LogUtils.i(TAG, "User account data has not synced,no resume super vpa", false);
        } else if (this.mDataSync.isGuest()) {
            LogUtils.i(TAG, "Not login,no resume super vpa.", false);
        } else {
            boolean memParkSafeExamResult = this.mDataSync.getMemParkSafeExamResult();
            boolean shouldUpdateSuperVpaSafeExam = this.mDataSync.shouldUpdateSuperVpaSafeExam();
            LogUtils.i(TAG, "shouldUpdateSuperVpaSafeExam:" + shouldUpdateSuperVpaSafeExam + "memParkExamResult: " + memParkSafeExamResult);
            if (memParkSafeExamResult && shouldUpdateSuperVpaSafeExam) {
                updateSuperApaSafeExamResult();
            }
        }
    }

    protected void resumeOrDataSyncLccExamResult(boolean isResume) {
        LogUtils.i(TAG, "resumeOrDataSyncMemExamResult: " + isResume, false);
        if (!BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
            LogUtils.i(TAG, "Not support Lidar safe exam, no resume super vpa", false);
        } else if (!this.mCarConfigHelper.isSupportLidar()) {
            LogUtils.i(TAG, "Not support Lidar, no resume super lcc", false);
        } else if (!this.mCarConfigHelper.isSupportLcc()) {
            LogUtils.i(TAG, "Not support lcc ,no resume super lcc", false);
        } else if (!this.mCarConfigHelper.isSupportXPilotSafeExam()) {
            LogUtils.i(TAG, "Not support lcc exam,no resume super lcc.");
        } else if (this.mDataSync.isGuest()) {
            LogUtils.i(TAG, "Not login,no resume super lcc.", false);
        } else {
            boolean lccSafeExamResult = this.mDataSync.getLccSafeExamResult();
            boolean shouldUpdateSuperLccSafeExam = this.mDataSync.shouldUpdateSuperLccSafeExam();
            LogUtils.i(TAG, "shouldUpdateSuperLccSafeExam:" + shouldUpdateSuperLccSafeExam + "oldLccExamResult: " + lccSafeExamResult);
            if (lccSafeExamResult && shouldUpdateSuperLccSafeExam) {
                updateSuperLccSafeExamResult();
            }
        }
    }

    protected void resumeOrSyncNraState(boolean isResume) {
        if (!this.mCarConfigHelper.isSupportNra()) {
            LogUtils.i(TAG, "Not support nra.", false);
        } else if (!this.mDataSync.mIsDataSynced) {
            LogUtils.i(TAG, "User account data has not synced.", false);
        } else {
            int nraState = this.mDataSync.getNraState();
            LogUtils.i(TAG, "Current Nra sync value =" + nraState, false);
            this.mXpuController.setNraState(nraState);
        }
    }

    void resumeXPilotModule() {
        this.mXpuController.setLccCrossBarriersSw(false);
        this.mScuController.setFcwState(true);
        if (this.mCarConfigHelper.isSupportElk() && BaseFeatureOption.getInstance().isForceTurnOnElkWhenIgOn()) {
            this.mScuController.setElkState(true);
        }
        if (this.mCarConfigHelper.isSupportOldIsla() && BaseFeatureOption.getInstance().isSupportIslaMemoryFunc()) {
            int islaSw = this.mDataSync.getIslaSw();
            LogUtils.i(TAG, "Start to resume ISLA sw:" + islaSw, false);
            this.mScuController.setIslaSw(islaSw);
        }
        resumeOrDataSyncMemExamResult(true);
        resumeOrDataSyncLccExamResult(true);
        resumeOrSyncLccAlcState(true);
        resumeOrSyncApaState(true);
        if (this.mVcuController.getGearLevel() == 4) {
            if (this.mCarConfigHelper.isSupportMemPark() && this.mDataSync.isGuest()) {
                LogUtils.i(TAG, "Resume user not login,keep mem park off!");
                this.mScuController.setMemoryParkSw(false);
            } else if (this.mCarConfigHelper.isSupportMemPark()) {
                this.mScuController.syncXPilotMemParkSw();
            }
            if (this.mCarConfigHelper.isSupportNgp() && this.mDataSync.isGuest()) {
                LogUtils.i(TAG, "Resume user not login,keep ngp off!");
                this.mScuController.setNgpEnable(false);
                if (this.mCarConfigHelper.isSupportCNgp()) {
                    this.mXpuController.setCityNgpSw(false);
                }
            } else if (this.mCarConfigHelper.isSupportNgp()) {
                syncXPilotNGP();
            }
        } else {
            LogUtils.i(TAG, "Not P Gear,don't resume ngp and vpa");
        }
        if (this.mCarConfigHelper.isSupportDsmCamera() && BaseFeatureOption.getInstance().isSupportForceOpenDsm()) {
            LogUtils.i(TAG, "Force open DSM after IGon!");
            this.mScuController.setDsmSw(true);
        }
        if (this.mCarConfigHelper.isLssCertification()) {
            this.mScuController.setLssMode(3);
        }
        resumeOrSyncNraState(true);
    }

    public void syncXPilotNGP() {
        if (!this.mCarConfigHelper.isSupportUnity3D() || App.isMainProcess()) {
            if (this.mDataSync.isGuest()) {
                LogUtils.w(TAG, "Current not login, do not resume XPilot NGP sw", false);
                return;
            }
            int xpuXpilotState = this.mScuController.getXpuXpilotState();
            LogUtils.i(TAG, "Current xpilot st=" + xpuXpilotState, false);
            boolean z = true;
            if (xpuXpilotState != 1) {
                if (xpuXpilotState != 2) {
                    return;
                }
                this.mDataSync.setNgpSw(false);
                this.mDataSync.setCityNgpSw(false);
                return;
            }
            int ngpState = this.mScuController.getNgpState();
            LogUtils.i(TAG, "Current XPU NGP state=" + ngpState, false);
            if (ngpState == 2) {
                return;
            }
            boolean ngpSafeExamResult = this.mDataSync.getNgpSafeExamResult();
            boolean lccSw = this.mDataSync.getLccSw();
            boolean alcSw = this.mDataSync.getAlcSw();
            LogUtils.i(TAG, "Current NGP exam result=" + ngpSafeExamResult + ", LccSw=" + lccSw + ", AlcSw=" + alcSw, false);
            if (!ngpSafeExamResult || !lccSw || !alcSw) {
                z = false;
            }
            if (ngpState == 0) {
                boolean ngpSw = this.mDataSync.getNgpSw();
                LogUtils.i(TAG, "Current NGP saved sw=" + ngpSw, false);
                z &= ngpSw;
            }
            LogUtils.i(TAG, "XPilot NGP Sw, resume saved value: " + z, false);
            this.mScuController.setNgpEnable(z);
            if (this.mCarConfigHelper.isSupportCNgp()) {
                if (z) {
                    boolean cngpSafeExamResult = this.mDataSync.getCngpSafeExamResult();
                    boolean cityNgpSw = this.mDataSync.getCityNgpSw();
                    LogUtils.i(TAG, "C-NGP value, cngpExamResult: " + cngpSafeExamResult + "cngpSw: " + cityNgpSw + " lccSavedSw: " + lccSw + " alcSavedSw: " + alcSw, false);
                    this.mXpuController.setCityNgpSw(cngpSafeExamResult & lccSw & alcSw & cityNgpSw);
                    return;
                }
                this.mXpuController.setCityNgpSw(false);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.model.DataSyncModel.DataSyncChangeListener
    public void onAccountDataSynced(final CarControlSyncDataEvent event) {
        if (!this.mIsLocalIgOn) {
            LogUtils.i(TAG, "Car not local ig on, do not sync account settings", false);
            if (event.isDataEmpty()) {
                this.mIsDataSyncEmpty = true;
                LogUtils.i(TAG, "Current account has no setting saved", false);
            }
            this.mHasPendingUpdate = true;
            this.mNeedUpdateDrvSeat = checkIfNeedSaveSeatPos();
            return;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$c0Kjm5PUKVZrszItuy0BuOllxdg
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$onAccountDataSynced$9$ServiceViewModel(event);
            }
        });
    }

    public /* synthetic */ void lambda$onAccountDataSynced$9$ServiceViewModel(final CarControlSyncDataEvent event) {
        while (!this.mIsInitComplete) {
            try {
                LogUtils.i(TAG, "Wait Resume save setting complete", false);
                this.mResumeSaveSettingLock.await();
                LogUtils.i(TAG, "Resume save setting completed, resume pending sync process", false);
            } catch (InterruptedException e) {
                LogUtils.e(TAG, (String) null, e);
            }
        }
        handleSyncSettings(event);
        this.mNeedUpdateDrvSeat = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateLccSafeExamResult() {
        ((IAccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class)).updateLccSafeExamResult();
    }

    protected void updateXngpSafeExamResult() {
        ((IAccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class)).updateXngpSafeExamResult();
    }

    protected void updateApaSafeExamResult() {
        ((IAccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class)).updateApaSafeExamResult();
    }

    protected void updateSuperApaSafeExamResult() {
        ((IAccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class)).updateSuperVpaSafeExamResult();
    }

    protected void updateSuperLccSafeExamResult() {
        ((IAccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class)).updateSuperLccSafeExamResult();
    }

    private void updateEnterPGear() {
        ((IAccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class)).closeSdsEnterPGear();
    }

    private boolean isDoorClosed(int index) {
        int[] doorsState = this.mBcmController.getDoorsState();
        return doorsState != null && index >= 0 && index <= 3 && doorsState[index] == 0;
    }

    private void handleVolumeForZenMode(boolean enter) {
        if (enter) {
            LogUtils.i(TAG, "Set audio volume for entering Zen mode", false);
        } else {
            LogUtils.i(TAG, "Set audio volume for exiting Zen mode", false);
        }
        new AudioConfig(App.getInstance()).doZenVolumeProcess(enter);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public float getCarSpeed() {
        return this.mVcuController.getCarSpeed();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean isCentralLocked() {
        return this.mBcmController.getDoorLockState();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setCentralLock(boolean lock) {
        int[] doorsState = this.mBcmController.getDoorsState();
        if (lock) {
            if (doorsState != null && doorsState.length >= 4 && doorsState[0] == 0 && doorsState[1] == 0 && doorsState[2] == 0 && doorsState[3] == 0) {
                this.mBcmController.setCentralLock(true);
                return;
            } else {
                NotificationHelper.getInstance().showToast(R.string.central_lock_unable_with_door_open);
                return;
            }
        }
        this.mBcmController.setCentralLock(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean isDrvSeatOccupied() {
        return this.mBcmController.isDrvSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean isPsnSeatOccupied() {
        return this.mMsmController.isPsnSeatOccupied();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setHvacPowerMode(boolean enable) {
        this.mHvacController.setHvacPowerMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean isHvacPowerModeOn() {
        return this.mHvacController.isHvacPowerModeOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean isHvacAutoModeOn() {
        return this.mHvacController.isHvacAutoModeOn();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public int getHvacWindSpeedLevel() {
        return this.mHvacController.getHvacWindSpeedLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setHvacTempDriver(float temperature) {
        if (temperature < 18.0f || temperature > 32.0f) {
            LogUtils.w(TAG, "invalid temperature: " + temperature, false);
        } else {
            this.mHvacController.setHvacTempDriver(temperature);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean setHvacTempDrvStep(boolean isUp) {
        try {
            this.mHvacController.setHvacTempDriverStep(isUp);
            return true;
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public float getHvacDriverTemp() {
        return matchHvacTemp(this.mHvacController.getHvacTempDriver());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setHvacDriverSyncMode(boolean enable) {
        this.mHvacController.setHvacDriverSyncMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean isHvacDriverSyncMode() {
        return this.mHvacController.getHvacDriverSyncMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setHvacTempPsn(float temperature) {
        if (temperature < 18.0f || temperature > 32.0f) {
            LogUtils.w(TAG, "invalid temperature: " + temperature, false);
        } else {
            this.mHvacController.setHvacTempPsn(temperature);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean setHvacTempPsnStep(boolean isUp) {
        try {
            this.mHvacController.setHvacTempPsnStep(isUp);
            return true;
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public float getHvacPsnTemp() {
        return matchHvacTemp(this.mHvacController.getHvacTempPsn());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setHvacPsnSyncMode(boolean enable) {
        this.mHvacController.setHvacPsnSyncMode(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean isHvacPsnSyncMode() {
        return this.mHvacController.getHvacPsnSyncMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setHvacFrontDefrost(boolean enable) {
        this.mHvacController.setHvacFrontDefrost(enable);
        this.mBcmController.setFrontMirrorHeat(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean isHvacFrontDefrostOn() {
        int hvacWindBlowMode = this.mHvacController.getHvacWindBlowMode();
        return hvacWindBlowMode == 5 || hvacWindBlowMode == 7;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setMirrorHeatEnable(boolean enable) {
        this.mBcmController.setMirrorHeat(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public boolean isMirrorHeatEnabled() {
        return this.mBcmController.isMirrorHeatEnabled();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public int getHvacInnerPM25() {
        return this.mHvacController.getHvacInnerPm25();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public float getHvacExternalTemp() {
        return this.mHvacController.getHvacExternalTemp();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void requestCarLicensePlate() {
        ICarInfoController iCarInfoController = this.mCarInfoController;
        if (iCarInfoController != null) {
            iCarInfoController.requestCarLicensePlate();
        } else {
            LogUtils.e(TAG, "requestCarLicensePlate failed, mCarInfoController is null", false);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setDriveMode(int driveMode) {
        if (driveMode == 0) {
            SoundHelper.play("/system/media/audio/xiaopeng/cdu/wav/CDU_drive_normal.wav", true, false);
        } else if (driveMode == 1) {
            SoundHelper.play(SoundHelper.PATH_DRIVE_MODE_ECO, true, false);
        } else if (driveMode == 2) {
            SoundHelper.play("/system/media/audio/xiaopeng/cdu/wav/CDU_drive_sport.wav", true, false);
        }
        int driveMode2 = getDriveMode();
        if (driveMode != 5 && driveMode2 == 5) {
            NotificationHelper.getInstance().showToast(R.string.drive_mode_eco_plus_off);
        } else if (driveMode != 7 && driveMode2 == 7) {
            NotificationHelper.getInstance().showToast(R.string.drive_mode_anti_sickness_off);
        }
        if (driveMode == 1 || driveMode == 0 || driveMode == 2) {
            this.mVcuController.setDriveMode(driveMode);
            FunctionModel.getInstance().setDriveModeChangedByUser(true);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public int getDriveMode() {
        return this.mVcuController.getDriveMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void setSsDoorStateCallback(ISSDoorCallBack callback) {
        if (this.mCarConfigHelper.isSupportSdc()) {
            this.mSsDoorStateCallback = callback;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel
    public void onShowDialog(String dialogScene) {
        MutableLiveData<String> mutableLiveData;
        if (!BaseFeatureOption.getInstance().isSupportNapa() || (mutableLiveData = this.mUnityShowDialogData) == null) {
            return;
        }
        mutableLiveData.postValue(dialogScene);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleIgStatusChanged(int state) {
        if (state == 0) {
            onIgOff();
        } else if (state == 1) {
            onIgLocalOn();
        } else if (state != 2) {
            return;
        } else {
            if (this.mPreIgState != state) {
                onIgRemoteOn();
            }
        }
        this.mPreIgState = state;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleGearChanged() {
        if (this.mCarConfigHelper.isSupportMirrorMemory()) {
            CabinSmartControl.getInstance().handleReverseMirror(this.mVcuController.getGearLevel() == 3);
        }
    }

    private void syncVcuData(CarControlSyncDataEvent event) {
        int driveMode;
        if (this.mVcuController.getGearLevel() == 4) {
            if (!this.mCarConfigHelper.isSupportDriveModeNewArch()) {
                if (this.mCarConfigHelper.isSupportDriveEnergyReset()) {
                    driveMode = 0;
                } else {
                    if (!event.isXpedal()) {
                        boolean xpedal = SharedPreferenceUtil.getXpedal();
                        LogUtils.i(TAG, "Xpedal sw is null, save currentValue: " + xpedal, false);
                        this.mDataSync.setXpedal(xpedal);
                    }
                    if (!event.isAntisick()) {
                        boolean isAntiSicknessEnabled = SharedPreferenceUtil.isAntiSicknessEnabled();
                        LogUtils.i(TAG, "Antisick sw is null, save currentValue: " + isAntiSicknessEnabled, false);
                        this.mDataSync.setAntiSickness(isAntiSicknessEnabled);
                    }
                    if (!event.isDriveMode()) {
                        int driveMode2 = SharedPreferenceUtil.getDriveMode();
                        LogUtils.i(TAG, "Drive mode is null, save currentValue: " + driveMode2, false);
                        this.mDataSync.setDriveMode(driveMode2);
                    }
                    if (this.mDataSync.isXpedal()) {
                        driveMode = 5;
                    } else {
                        driveMode = this.mDataSync.isAntiSicknessEnabled() ? 7 : this.mDataSync.getDriveMode();
                    }
                }
                LogUtils.i(TAG, "Resume drive mode: " + driveMode, false);
                if (driveMode == 1001) {
                    if (this.mCarConfigHelper.isSupportXSport()) {
                        this.mVcuSmartControl.resumeDriveMode(1001);
                        return;
                    }
                    LogUtils.i(TAG, "Current Car Do not support XSport ", false);
                    driveMode = GlobalConstant.DEFAULT.DRIVER_MODE;
                    this.mDataSync.setDriveMode(driveMode);
                }
                if (driveMode == 1 && !BaseFeatureOption.getInstance().isSupportEcoDriveMode()) {
                    LogUtils.i(TAG, "Eco drive mode not support, resume Normal drive mode");
                    driveMode = GlobalConstant.DEFAULT.DRIVER_MODE;
                    this.mDataSync.setDriveMode(driveMode);
                }
                this.mVcuController.setDriveMode(driveMode);
                if (BaseFeatureOption.getInstance().isSupportXSportRacerMode() && 4 == this.mVcuController.getXSportDrivingMode()) {
                    this.mVcuController.exitXSportDriveMode();
                }
                if (driveMode == 0 || driveMode == 1 || driveMode == 2) {
                    if (!event.isRecycleGrade()) {
                        int energyRecycleGrade = SharedPreferenceUtil.getEnergyRecycleGrade();
                        LogUtils.i(TAG, "Energy recycle grade is null, save currentValue: " + energyRecycleGrade, false);
                        this.mDataSync.setRecycleGrade(energyRecycleGrade);
                    }
                    if (this.mCarConfigHelper.isSupportSnowMode()) {
                        FunctionModel.getInstance().setSnowModeEnergyCache(-1);
                        if (!this.mVcuController.getSnowMode()) {
                            if (this.mCarConfigHelper.isSupportDriveEnergyReset()) {
                                this.mVcuController.setEnergyRecycleGrade(3);
                            } else {
                                int recycleGrade = this.mDataSync.getRecycleGrade();
                                LogUtils.i(TAG, "Energy recycle grade,  resume saved value: " + recycleGrade, false);
                                this.mVcuController.setEnergyRecycleGrade(recycleGrade);
                            }
                        }
                    } else {
                        this.mVcuController.setEnergyRecycleGrade(this.mCarConfigHelper.isSupportDriveEnergyReset() ? 3 : this.mDataSync.getRecycleGrade());
                    }
                }
            } else {
                if (!event.isDriveMode()) {
                    int driveMode3 = SharedPreferenceUtil.getDriveMode();
                    LogUtils.i(TAG, "Drive mode is null, save currentValue: " + driveMode3, false);
                    this.mDataSync.setDriveMode(driveMode3);
                }
                int driveMode4 = this.mDataSync.getDriveMode();
                LogUtils.i(TAG, "Resume drive mode: " + driveMode4, false);
                if (!this.mCarConfigHelper.isSupportXSport() && driveMode4 == 1001) {
                    LogUtils.i(TAG, "Current Car Do not support XSport ", false);
                    driveMode4 = GlobalConstant.DEFAULT.DRIVER_MODE;
                    this.mDataSync.setDriveMode(driveMode4);
                }
                if (driveMode4 == 1 && !BaseFeatureOption.getInstance().isSupportEcoDriveMode()) {
                    LogUtils.i(TAG, "Eco drive mode not support, resume Normal drive mode");
                    driveMode4 = GlobalConstant.DEFAULT.DRIVER_MODE;
                    this.mDataSync.setDriveMode(driveMode4);
                }
                if (!event.isSinglePedal()) {
                    boolean isNewDriveXPedalModeEnabled = SharedPreferenceUtil.isNewDriveXPedalModeEnabled();
                    LogUtils.i(TAG, "Xpedal sw is null, save currentValue: " + isNewDriveXPedalModeEnabled, false);
                    this.mDataSync.setNewDriveXPedalMode(isNewDriveXPedalModeEnabled);
                }
                if (!event.isRecycleGrade()) {
                    int energyRecycleGrade2 = SharedPreferenceUtil.getEnergyRecycleGrade();
                    LogUtils.i(TAG, "Energy recycle grade is null, save currentValue: " + energyRecycleGrade2, false);
                    this.mDataSync.setRecycleGrade(energyRecycleGrade2);
                }
                this.mBcmController.setCustomerModeFlag(false);
                this.mVcuSmartControl.resumeDriveMode(driveMode4);
            }
        } else {
            LogUtils.w(TAG, "Not in park gear, do not sync VCU related settings", false);
        }
        boolean nGearWarningSwitchStatus = this.mVcuController.getNGearWarningSwitchStatus();
        if (!event.isNGearProtectSw()) {
            LogUtils.i(TAG, "N gear protect sw is null, save currentValue: " + nGearWarningSwitchStatus, false);
            this.mDataSync.setNGearWarningSwitch(nGearWarningSwitchStatus);
            return;
        }
        boolean nGearWarningSwitch = this.mDataSync.getNGearWarningSwitch();
        LogUtils.i(TAG, "N gear protect, resume saved value: " + nGearWarningSwitch + ", currentValue: " + nGearWarningSwitchStatus, false);
        if (nGearWarningSwitch != nGearWarningSwitchStatus) {
            this.mVcuController.setNGearWarningSwitch(nGearWarningSwitch);
        }
    }

    private void syncChassisData(CarControlSyncDataEvent event) {
        boolean z = true;
        if (this.mVcuController.getGearLevel() == 4) {
            if (!this.mCarConfigHelper.isSupportEpsTorque() || Math.abs(this.mEpsController.getTorsionBarTorque()) <= this.mCarConfigHelper.getEpsTorsionBarThreshold()) {
                int steeringEps = this.mEpsController.getSteeringEps();
                if (!event.isSteerMode()) {
                    LogUtils.i(TAG, "Steering Eps is null, save currentValue: " + steeringEps, false);
                    this.mDataSync.setSteerMode(steeringEps);
                } else {
                    int steerMode = this.mDataSync.getSteerMode();
                    LogUtils.i(TAG, "Steering Eps, resume saved value: " + steerMode + ", currentValue: " + steeringEps, false);
                    if (steerMode != steeringEps && (!this.mCarConfigHelper.isSupportXSport() || this.mDataSync.getDriveMode() != 1001)) {
                        this.mEpsController.setSteeringEps(steerMode);
                    }
                }
            } else {
                LogUtils.w(TAG, "TorsionBarTorque > threshold, not allowed to sync EPS setting", false);
            }
        } else {
            LogUtils.w(TAG, "Not in park gear, do not sync Chassis related settings", false);
        }
        if (!event.isAvh()) {
            boolean isAvhEnabled = SharedPreferenceUtil.isAvhEnabled();
            LogUtils.i(TAG, "AVH, save currentValue: " + isAvhEnabled, false);
            this.mDataSync.setAvh(isAvhEnabled);
        }
        LogUtils.i(TAG, "AVH, resume saved value: " + this.mDataSync.getAvh());
        this.mChassisSmartControl.checkAvhCondition();
        if (this.mCarConfigHelper.isSupportUnity3D()) {
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.AVH_SW_NOTIFY, true);
        }
        QuickSettingManager quickSettingManager = QuickSettingManager.getInstance();
        if (this.mEspController.getAvhFault() || !this.mDataSync.getAvh()) {
            z = false;
        }
        quickSettingManager.onSignalCallback(QuickSettingConstants.KEY_AVH_BOOL, Boolean.valueOf(z));
        if (this.mCarConfigHelper.isSupportAsWelcomeMode()) {
            boolean asWelcomeMode = this.mBcmController.getAsWelcomeMode();
            if (!event.isAsWelcomeMode()) {
                LogUtils.i(TAG, "As welcome mode sw is null, save currentValue: " + asWelcomeMode, false);
                this.mDataSync.setAsWelcomeMode(asWelcomeMode);
            } else {
                boolean asWelcomeMode2 = this.mDataSync.getAsWelcomeMode();
                LogUtils.i(TAG, "As welcome mode sw, resume saved value: " + asWelcomeMode2 + ", currentValue: " + asWelcomeMode, false);
                if (asWelcomeMode2 != asWelcomeMode) {
                    this.mBcmController.setAsWelcomeMode(asWelcomeMode2);
                }
            }
        }
        if (this.mCarConfigHelper.isSupportCdcControl()) {
            int cdcMode = this.mCdcController.getCdcMode();
            if (!event.isCdcMode()) {
                LogUtils.i(TAG, "CDC mode is null, save currentValue: " + cdcMode, false);
                this.mDataSync.setCdcMode(cdcMode);
                return;
            }
            int cdcMode2 = this.mDataSync.getCdcMode();
            LogUtils.i(TAG, "CDC mode, resume saved value: " + cdcMode2 + ", currentValue: " + cdcMode, false);
            if (cdcMode2 != cdcMode) {
                this.mCdcController.setCdcMode(cdcMode2, false);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0069, code lost:
        if (r0 != 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0070, code lost:
        if (r0 != 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0073, code lost:
        r1 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0081, code lost:
        if (r0 != 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0083, code lost:
        r1 = r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void syncIcmData(com.xiaopeng.carcontrol.model.CarControlSyncDataEvent r8) {
        /*
            Method dump skipped, instructions count: 411
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModel.syncIcmData(com.xiaopeng.carcontrol.model.CarControlSyncDataEvent):void");
    }

    private void syncBcmData(CarControlSyncDataEvent event) {
        if (this.mCarConfigHelper.isSupportAutoLampHeight()) {
            boolean isAutoLampHeight = this.mBcmController.isAutoLampHeight();
            if (!event.isAutoLampHeight()) {
                LogUtils.d(TAG, "auto lamp height, save currentValue: " + isAutoLampHeight);
                this.mDataSync.setAutoLampHeight(isAutoLampHeight);
            } else {
                boolean isAutoLampHeight2 = this.mDataSync.isAutoLampHeight();
                if (isAutoLampHeight2 != isAutoLampHeight) {
                    this.mBcmController.setAutoLampHeight(isAutoLampHeight2);
                }
            }
        }
        if (this.mCarConfigHelper.isSupportLampHeight()) {
            int lampHeightLevel = this.mBcmController.getLampHeightLevel();
            if (!event.isLampHeightLevel()) {
                LogUtils.d(TAG, "lamp height level, save currentValue: " + lampHeightLevel);
                this.mDataSync.setLampHeightLevel(lampHeightLevel);
            } else if (!this.mDataSync.isAutoLampHeight()) {
                int lampHeightLevel2 = this.mDataSync.getLampHeightLevel();
                if (lampHeightLevel2 != lampHeightLevel) {
                    this.mBcmController.setLampHeightLevel(lampHeightLevel2);
                }
            } else {
                LogUtils.d(TAG, "syncBcmData auto lamp height, skip set level " + this.mDataSync.getLampHeightLevel() + " , " + lampHeightLevel, false);
            }
        }
        boolean lightMeHome = this.mBcmController.getLightMeHome();
        if (!event.isLightMeHome()) {
            LogUtils.i(TAG, "Light me home, save currentValue: " + lightMeHome, false);
            this.mDataSync.setLightMeHome(lightMeHome);
        } else {
            boolean lightMeHome2 = this.mDataSync.getLightMeHome();
            LogUtils.i(TAG, "Light me home, resume saved value: " + lightMeHome2 + ", currentValue: " + lightMeHome, false);
            if (lightMeHome2 != lightMeHome) {
                this.mBcmController.setLightMeHome(lightMeHome2, false);
            }
        }
        int lightMeHomeTime = this.mBcmController.getLightMeHomeTime();
        if (!event.isLightMeHomeTime()) {
            LogUtils.i(TAG, "Light me home time, save currentValue: " + lightMeHomeTime, false);
            this.mDataSync.setLightMeHomeTime(lightMeHomeTime);
        } else {
            int lightMeHomeTime2 = this.mDataSync.getLightMeHomeTime();
            LogUtils.i(TAG, "Light me home time, resume saved value: " + lightMeHomeTime2 + ", currentValue: " + lightMeHomeTime, false);
            if (this.mDataSync.getLightMeHome() && lightMeHomeTime2 != lightMeHomeTime) {
                this.mBcmController.setLightMeHomeTime(lightMeHomeTime2, false);
            }
        }
        if (this.mCarConfigHelper.isSupportDomeLightIndependentCtrl()) {
            int domeLightBright = this.mBcmController.getDomeLightBright();
            if (!event.isDomeLightBright()) {
                LogUtils.i(TAG, "Dome light brightness is null, save currentValue: " + domeLightBright, false);
                this.mDataSync.setDomeLightBright(domeLightBright);
                return;
            }
            int domeLightBright2 = this.mDataSync.getDomeLightBright();
            LogUtils.i(TAG, "Dome light brightness, resume saved value: " + domeLightBright2 + ", currentValue: " + domeLightBright, false);
            if (domeLightBright2 != domeLightBright) {
                this.mBcmController.setDomeLightBright(domeLightBright2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void syncLluData(CarControlSyncDataEvent event) {
        if (this.mCarConfigHelper.isSupportLlu()) {
            if (BaseFeatureOption.getInstance().isSupportLluAllOn()) {
                this.mLluController.setLluEnable(true);
                this.mDataSync.setLluSw(true);
            } else {
                boolean isLluEnabled = this.mLluController.isLluEnabled();
                if (!event.isLluSw()) {
                    LogUtils.i(TAG, "LLU sw, save currentValue: " + isLluEnabled, false);
                    this.mDataSync.setLluSw(isLluEnabled);
                } else {
                    boolean lluSw = this.mDataSync.getLluSw();
                    LogUtils.i(TAG, "LLU sw, resume saved value: " + lluSw + ", currentValue: " + isLluEnabled, false);
                    if (lluSw != isLluEnabled) {
                        this.mLluController.setLluEnable(lluSw);
                    }
                }
            }
            boolean isLluEnabled2 = this.mLluController.isLluEnabled();
            boolean isLluWakeWaitEnable = this.mLluController.isLluWakeWaitEnable();
            if (!event.isLluUnlockSw()) {
                LogUtils.i(TAG, "LLU unlock sw, save currentValue: " + isLluWakeWaitEnable, false);
                this.mDataSync.setLluUnlockSw(isLluWakeWaitEnable);
            } else {
                boolean lluUnlockSw = this.mDataSync.getLluUnlockSw();
                LogUtils.i(TAG, "LLU unlock sw, resume saved value: " + lluUnlockSw + ", currentValue: " + isLluWakeWaitEnable, false);
                if (!isLluEnabled2) {
                    this.mLluController.setLluWakeWaitSwitch(false, true, false);
                } else if (lluUnlockSw != isLluWakeWaitEnable) {
                    this.mLluController.setLluWakeWaitSwitch(lluUnlockSw, true, false);
                }
            }
            boolean isLluSleepEnable = this.mLluController.isLluSleepEnable();
            if (!event.isLluLockSw()) {
                LogUtils.i(TAG, "LLU lock sw, save currentValue: " + isLluSleepEnable, false);
                this.mDataSync.setLluLockSw(isLluSleepEnable);
            } else {
                boolean lluLockSw = this.mDataSync.getLluLockSw();
                LogUtils.i(TAG, "LLU lock sw, resume saved value: " + lluLockSw + ", currentValue: " + isLluSleepEnable, false);
                if (!isLluEnabled2) {
                    this.mLluController.setLluSleepSwitch(false, true, false);
                } else if (lluLockSw != isLluSleepEnable) {
                    this.mLluController.setLluSleepSwitch(lluLockSw, true, false);
                }
            }
            boolean isLluChargingEnable = this.mLluController.isLluChargingEnable();
            if (!event.isLluChargeSw()) {
                LogUtils.i(TAG, "LLU charge sw, save currentValue: " + isLluChargingEnable, false);
                this.mDataSync.setLluChargeSw(isLluChargingEnable);
            } else {
                boolean lluChargeSw = this.mDataSync.getLluChargeSw();
                LogUtils.i(TAG, "LLU charge sw, resume saved value: " + lluChargeSw + ", currentValue: " + isLluChargingEnable, false);
                if (!isLluEnabled2) {
                    this.mLluController.setLluChargingSwitch(false, true, false);
                } else if (lluChargeSw != isLluChargingEnable) {
                    this.mLluController.setLluChargingSwitch(lluChargeSw, true, false);
                }
            }
            boolean isParkLampIncludeFmB = this.mBcmController.isParkLampIncludeFmB();
            if (!event.isParkLampB()) {
                LogUtils.i(TAG, "Park lamp B, save currentValue: " + isParkLampIncludeFmB, false);
                this.mDataSync.setParkLampB(isParkLampIncludeFmB);
                return;
            }
            boolean parkLampB = this.mDataSync.getParkLampB();
            LogUtils.i(TAG, "Park lamp B, resume saved value: " + parkLampB + ", currentValue: " + isParkLampIncludeFmB, false);
            if (this.mCarConfigHelper.isSupportNewParkLampFmB() || parkLampB != isParkLampIncludeFmB) {
                this.mBcmController.setParkLampIncludeFmB(parkLampB);
                return;
            }
            return;
        }
        LogUtils.d(TAG, "llu not support, force save Park lamp B to false ");
        this.mBcmController.setParkLampIncludeFmB(false, false);
    }

    protected void syncAtlData(CarControlSyncDataEvent event) {
        if (this.mCarConfigHelper.isSupportAtl()) {
            boolean isAtlSwEnabled = this.mAtlController.isAtlSwEnabled();
            if (!event.isAtlSw()) {
                LogUtils.i(TAG, "ATL, save currentValue: " + isAtlSwEnabled, false);
                this.mDataSync.setAtlSw(isAtlSwEnabled);
            } else {
                boolean atlSw = this.mDataSync.getAtlSw();
                LogUtils.i(TAG, "ATL, resume saved value: " + atlSw + ", currentValue: " + isAtlSwEnabled, false);
                if (atlSw != isAtlSwEnabled) {
                    this.mAtlController.setAtlSwEnable(atlSw);
                }
            }
            if (this.mCarConfigHelper.isSupportFullAtl()) {
                int atlBrightness = this.mAtlController.getAtlBrightness();
                if (!event.isAtlBright()) {
                    LogUtils.i(TAG, "ATL manual brightness, save currentValue: " + atlBrightness, false);
                    this.mDataSync.setAtlBright(atlBrightness);
                } else {
                    int atlBright = this.mDataSync.getAtlBright();
                    LogUtils.i(TAG, "ATL manual brightness, resume saved value: " + atlBright + ", currentValue: " + atlBrightness, false);
                    if (atlBright != atlBrightness) {
                        this.mAtlController.setAtlBrightness(atlBright, false);
                    }
                }
                String atlEffect = this.mAtlController.getAtlEffect();
                if (!event.isAtlEffect()) {
                    LogUtils.i(TAG, "ATL effect, save currentValue: " + atlEffect, false);
                    this.mDataSync.setAtlEffect(atlEffect);
                } else {
                    String atlEffect2 = this.mDataSync.getAtlEffect();
                    LogUtils.i(TAG, "ATL effect, resume saved value: " + atlEffect2 + ", currentValue: " + atlEffect, false);
                    if (!atlEffect2.equals(atlEffect)) {
                        this.mAtlController.setAtlEffect(atlEffect2, false);
                    }
                }
            }
            String str = null;
            if (this.mCarConfigHelper.isSupportFullAtl()) {
                str = this.mDataSync.getAtlEffect();
                boolean isAtlDualColor = this.mAtlController.isAtlDualColor(str);
                if (!event.isAtlDualColorSw()) {
                    LogUtils.i(TAG, "ATL dual color sw, save currentValue: " + isAtlDualColor, false);
                    this.mDataSync.setAtlDualColorSw(isAtlDualColor);
                } else {
                    boolean atlDualColorSw = this.mDataSync.getAtlDualColorSw();
                    LogUtils.i(TAG, "ATL dual color sw, resume saved value: " + atlDualColorSw + ", currentValue: " + isAtlDualColor, false);
                    if (atlDualColorSw != isAtlDualColor && this.mAtlController.isAtlDualColor(str)) {
                        this.mAtlController.setAtlDualColor(str, atlDualColorSw, false);
                    }
                }
            }
            int atlSingleColor = this.mAtlController.getAtlSingleColor(str);
            if (!event.isAtlSingleColor()) {
                LogUtils.i(TAG, "ATL single color, save currentValue: " + atlSingleColor, false);
                this.mDataSync.setAtlSingleColor(atlSingleColor);
            } else {
                int atlSingleColor2 = this.mDataSync.getAtlSingleColor();
                LogUtils.i(TAG, "ATL single color, resume saved value: " + atlSingleColor2 + ", currentValue: " + atlSingleColor, false);
                if (atlSingleColor2 != atlSingleColor) {
                    this.mAtlController.setAtlSingleColor(str, atlSingleColor2, false);
                }
            }
            if (this.mCarConfigHelper.isSupportFullAtl()) {
                int atlDualFirstColor = this.mAtlController.getAtlDualFirstColor(str);
                int atlDualSecondColor = this.mAtlController.getAtlDualSecondColor(str);
                if (!event.isAtlDualColor()) {
                    LogUtils.i(TAG, "ATL dual color, save currentValue: " + atlDualFirstColor + ", " + atlDualSecondColor, false);
                    this.mDataSync.setAtlDualColor(atlDualFirstColor, atlDualSecondColor);
                    return;
                }
                int[] atlDualColor = this.mDataSync.getAtlDualColor();
                if (atlDualColor != null && atlDualColor.length >= 2) {
                    LogUtils.i(TAG, "ATL dual color, resume saved value: " + atlDualColor[0] + ", " + atlDualColor[1] + ", currentValue: " + atlDualFirstColor + ", " + atlDualSecondColor, false);
                    if (atlDualColor[0] == atlDualFirstColor && atlDualColor[1] == atlDualSecondColor) {
                        return;
                    }
                    this.mAtlController.setAtlDualColor(str, atlDualColor[0], atlDualColor[1], false);
                    return;
                }
                LogUtils.w(TAG, "ATL dual color cfg not saved", false);
            }
        }
    }

    protected void syncLockData(CarControlSyncDataEvent event) {
        boolean parkingAutoUnlock = this.mBcmController.getParkingAutoUnlock();
        if (!event.isParkAutoUnlock()) {
            LogUtils.i(TAG, "Park auto unlock, save currentValue: " + parkingAutoUnlock, false);
            this.mDataSync.setParkAutoUnlock(parkingAutoUnlock);
        } else {
            boolean parkAutoUnlock = this.mDataSync.getParkAutoUnlock();
            LogUtils.i(TAG, "Park auto unlock, resume saved value: " + parkAutoUnlock + ", currentValue: " + parkingAutoUnlock, false);
            if (parkAutoUnlock != parkingAutoUnlock) {
                this.mBcmController.setParkingAutoUnlock(parkAutoUnlock);
            }
        }
        if (BaseFeatureOption.getInstance().isSupportUnlockResponseSettings()) {
            int unlockResponse = this.mBcmController.getUnlockResponse();
            if (!event.isUnlockResponse()) {
                LogUtils.i(TAG, "Unlock response, save currentValue: " + unlockResponse, false);
                CarBodySmartControl.getInstance().setUnlockResponse(unlockResponse);
            } else {
                int unlockResponse2 = this.mDataSync.getUnlockResponse();
                LogUtils.i(TAG, "Unlock response, resume saved value: " + unlockResponse2 + ", currentValue: " + unlockResponse, false);
                CarBodySmartControl.getInstance().setUnlockResponse(unlockResponse2);
            }
        }
        boolean isAutoDoorHandleEnabled = this.mBcmController.isAutoDoorHandleEnabled();
        if (!event.isAutoDhc()) {
            LogUtils.i(TAG, "Auto door handle, save currentValue: " + isAutoDoorHandleEnabled, false);
            this.mDataSync.setAutoDhc(isAutoDoorHandleEnabled);
        } else {
            boolean autoDhc = this.mDataSync.getAutoDhc();
            LogUtils.i(TAG, "Auto door handle, resume saved value: " + autoDhc + ", currentValue: " + isAutoDoorHandleEnabled, false);
            if (autoDhc != isAutoDoorHandleEnabled) {
                this.mBcmController.setAutoDoorHandleEnable(autoDhc, false);
            }
        }
        boolean childLeftLock = this.mBcmController.getChildLeftLock();
        if (!event.isChildLeftLock()) {
            LogUtils.i(TAG, "Child lock, save current value: " + childLeftLock, false);
            this.mDataSync.setChildLeftLock(childLeftLock);
        }
        boolean childRightLock = this.mBcmController.getChildRightLock();
        if (!event.isChildRightLock()) {
            LogUtils.i(TAG, "Child right lock, save current value: " + childRightLock, false);
            this.mDataSync.setChildRightLock(childRightLock);
        }
        boolean childLeftLock2 = this.mDataSync.getChildLeftLock();
        boolean childRightLock2 = this.mDataSync.getChildRightLock();
        if (this.mCarConfigHelper.isSupportChildMode()) {
            IBcmController iBcmController = this.mBcmController;
            iBcmController.setChildModeEnable(iBcmController.isChildModeEnable(), false);
            return;
        }
        syncChildLockStatue(childLeftLock, childRightLock, childLeftLock2, childRightLock2);
    }

    private void syncChildLockStatue(final boolean currentLeftLock, final boolean currentRightLock, final boolean targetLeftLock, final boolean targetRightLock) {
        LogUtils.i(TAG, "Child left lock, saved left value: " + targetLeftLock + ", currentLeftValue: " + currentLeftLock + ", saved right value: " + targetRightLock + ", currentRightValue: " + currentRightLock, false);
        if (currentLeftLock == targetLeftLock && currentRightLock == targetRightLock) {
            return;
        }
        if (this.mCarConfigHelper.isSupportNewChildLock()) {
            if (currentLeftLock != targetLeftLock) {
                this.mBcmController.setChildLock(true, targetLeftLock);
            }
            if (currentRightLock != targetRightLock) {
                this.mBcmController.setChildLock(false, targetRightLock);
            }
        } else if (currentLeftLock == currentRightLock && targetLeftLock == targetRightLock) {
            this.mBcmController.setChildLock(targetLeftLock ? 6 : 1);
        } else {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$9Rt9IhmWr9lvy8Eh1e4oVVQ-tTE
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceViewModel.this.lambda$syncChildLockStatue$10$ServiceViewModel(currentLeftLock, targetLeftLock, currentRightLock, targetRightLock);
                }
            });
        }
        QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_CHILD_lOCK_LEFT, Boolean.valueOf(targetLeftLock));
        QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_CHILD_LOCK_RIGHT, Boolean.valueOf(targetRightLock));
    }

    public /* synthetic */ void lambda$syncChildLockStatue$10$ServiceViewModel(final boolean currentLeftLock, final boolean targetLeftLock, final boolean currentRightLock, final boolean targetRightLock) {
        boolean z = true;
        if (currentLeftLock != targetLeftLock) {
            this.mBcmController.setChildLock(true, targetLeftLock);
        } else {
            z = false;
        }
        if (currentRightLock != targetRightLock) {
            if (z) {
                try {
                    Thread.sleep(650L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.mBcmController.setChildLock(false, targetRightLock);
        }
    }

    private void syncSeatData(CarControlSyncDataEvent event) {
        if (this.mCarConfigHelper.isSupportMsmD()) {
            if (this.mCarConfigHelper.isSupportWelcomeMode()) {
                ISeatViewModel iSeatViewModel = (ISeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
                boolean isWelcomeModeEnabled = iSeatViewModel.isWelcomeModeEnabled();
                if (!event.isWelcomeMode()) {
                    LogUtils.i(TAG, "Welcome mode, save currentValue: " + isWelcomeModeEnabled, false);
                    this.mDataSync.setWelcomeMode(isWelcomeModeEnabled);
                } else {
                    boolean welcomeMode = this.mDataSync.getWelcomeMode();
                    LogUtils.i(TAG, "Welcome mode, resume saved value: " + welcomeMode + ", currentValue: " + isWelcomeModeEnabled, false);
                    if (welcomeMode != isWelcomeModeEnabled && (!welcomeMode || !this.mVcuController.isExhibitionModeOn())) {
                        iSeatViewModel.setWelcomeMode(welcomeMode);
                    }
                }
            }
            LogUtils.i(TAG, "Seat tilt moving safe :" + this.mMsmController.isDrvTiltMovingSafe());
            if (!checkIfNeedSaveSeatPos()) {
                this.mBgHandler.removeCallbacks(this.mRestoreDrvSeatTask);
                int[] drvSeatSavedPos = this.mDataSync.getDrvSeatSavedPos();
                boolean z = this.mVcuController.getGearLevel() == 4;
                if (drvSeatSavedPos != null) {
                    boolean isDrvDoorClose = isDrvDoorClose();
                    boolean z2 = BaseFeatureOption.getInstance().shouldIgnoreDrvOccupied() || isDrvSeatOccupied();
                    LogUtils.i(TAG, "isDrvDoorClosed: " + isDrvDoorClose + ", isDrvSeatOccupied: " + z2 + ", isGearP: " + z + ", saved seat position: " + Arrays.toString(drvSeatSavedPos), false);
                    if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                        this.mMsmController.setDriverAllPositions(1, drvSeatSavedPos[0], drvSeatSavedPos[1], drvSeatSavedPos[2], drvSeatSavedPos[3], drvSeatSavedPos[4]);
                    } else {
                        this.mMsmController.setDriverAllPositionsToMcu(drvSeatSavedPos[0], drvSeatSavedPos[1], drvSeatSavedPos[2], drvSeatSavedPos[3], drvSeatSavedPos[4]);
                    }
                    if (z && isDrvDoorClose && z2 && (this.mMsmController.getDSeatHorzPos() != drvSeatSavedPos[0] || this.mMsmController.getDSeatVerPos() != drvSeatSavedPos[1] || this.mMsmController.getDSeatTiltPos() != drvSeatSavedPos[2] || this.mMsmController.getDSeatLegPos() != drvSeatSavedPos[3] || (CarBaseConfig.getInstance().isSupportDrvCushion() && this.mMsmController.getDSeatCushionPos() != drvSeatSavedPos[4]))) {
                        this.mBgHandler.postDelayed(this.mRestoreDrvSeatTask, BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome() ? 500L : 0L);
                    }
                }
            }
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_DRV_SEAT_POS_INT, Integer.valueOf(this.mDataSync.getDrvSeatPosIdx()));
        }
        if (this.mCarConfigHelper.isSupportRearSeatWelcomeMode()) {
            boolean rearSeatWelcomeMode = this.mDataSync.getRearSeatWelcomeMode();
            LogUtils.i(TAG, "Rear seat welcome mode, resume saved value: " + rearSeatWelcomeMode, false);
            this.mBcmController.setRearSeatWelcomeMode(rearSeatWelcomeMode, false);
        }
        if (this.mCarConfigHelper.isSupportEsb()) {
            boolean esbEnable = this.mMsmController.getEsbEnable();
            if (!event.isDrvSeatEsb()) {
                LogUtils.i(TAG, "Driver esb, save currentValue: " + esbEnable, false);
                this.mDataSync.setDrvSeatEsb(esbEnable);
            } else {
                LogUtils.i(TAG, "Driver esb, resume saved value: " + this.mDataSync.getDrvSeatEsb() + ", currentValue: " + esbEnable, false);
                SeatSmartControl.getInstance().onFollowedVehicleLostConfigChanged(true);
            }
        }
        if (!this.mCarConfigHelper.isSupportRearBeltWarningSwitch() || BaseFeatureOption.getInstance().isSupportRsbWarningReset()) {
            return;
        }
        if (!event.isRsbWarning()) {
            boolean isRsbWarningEnabled = SharedPreferenceUtil.isRsbWarningEnabled();
            LogUtils.i(TAG, "Rsb is null, save currentValue: " + isRsbWarningEnabled, false);
            this.mDataSync.setRsbWarning(isRsbWarningEnabled);
            return;
        }
        boolean rsbWarning = this.mDataSync.getRsbWarning();
        LogUtils.i(TAG, "Rsb, resume saved value: " + rsbWarning, false);
        this.mMsmController.setBackBeltSw(rsbWarning);
    }

    private boolean checkIfNeedSaveSeatPos() {
        int[] iArr;
        if (!this.mDataSync.isGuest() && !this.mDataSync.isDrvSeatSavedPosEmpty()) {
            LogUtils.i(TAG, "We have drv seat position, wait to resume", false);
            return false;
        }
        if (this.mMsmController.isDrvTiltMovingSafe()) {
            iArr = new int[]{this.mMsmController.getDSeatHorzPos(), this.mMsmController.getDSeatVerPos(), this.mMsmController.getDSeatTiltPos(), this.mMsmController.getDSeatLegPos(), this.mMsmController.getDSeatCushionPos()};
        } else if (this.mDataSync.isDrvSeatSavedPosEmpty()) {
            iArr = this.mCarConfigHelper.getMSMSeatDefaultPos();
        } else {
            iArr = this.mDataSync.getDrvSeatSavedPos();
        }
        if (this.mDataSync.isGuest()) {
            LogUtils.i(TAG, "In guest mode, save current seat position data: " + Arrays.toString(iArr), false);
        } else {
            LogUtils.i(TAG, "Drv seat save data is null, save current seat position data: " + Arrays.toString(iArr), false);
        }
        if (iArr != null) {
            this.mDataSync.saveDrvSeatPos(iArr);
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                this.mMsmController.setDriverAllPositions(1, iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]);
            } else {
                this.mMsmController.setDriverAllPositionsToMcu(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4]);
            }
        }
        return true;
    }

    protected void syncMirrorData(CarControlSyncDataEvent event) {
        if (this.mCarConfigHelper.isSupportMirrorMemory()) {
            boolean z = true;
            if (!event.isMirrorPos()) {
                Mirror mirror = new Mirror();
                mirror.leftHPos = this.mBcmController.getLeftMirrorLRPos(true);
                mirror.leftVPos = this.mBcmController.getLeftMirrorUDPos(true);
                mirror.rightHPos = this.mBcmController.getRightMirrorLRPos(true);
                mirror.rightVPos = this.mBcmController.getRightMirrorUDPos(true);
                String mirror2 = mirror.toString();
                LogUtils.i(TAG, "Mirror saved position is null, save current mirror data: " + mirror2, false);
                this.mDataSync.setMirrorData(mirror2);
                z = false;
            } else {
                LogUtils.i(TAG, "Mirror position, resume saved value: " + this.mDataSync.getMirrorData(), false);
                this.mDataSync.setMirrorRestoreFinished();
            }
            int reverseMirrorMode = this.mBcmController.getReverseMirrorMode();
            if (!event.isMirrorReverse()) {
                LogUtils.i(TAG, "Mirror reverse mode, save currentValue: " + reverseMirrorMode, false);
                this.mBcmController.setReverseMirrorMode(reverseMirrorMode, false);
            } else {
                int mirrorReverseMode = this.mDataSync.getMirrorReverseMode();
                LogUtils.i(TAG, "Mirror reverse mode, resume saved value: " + mirrorReverseMode + ", currentValue: " + reverseMirrorMode, false);
                this.mBcmController.setReverseMirrorMode(mirrorReverseMode, false);
            }
            CabinSmartControl.getInstance().syncAccountMirrorPos(this.mDataSync.getMirrorData(), z);
        }
        if (BaseFeatureOption.getInstance().isSupportMirrorAutoFoldOff()) {
            boolean isMirrorAutoFoldEnable = this.mBcmController.isMirrorAutoFoldEnable();
            if (!event.isMirrorAutoFold()) {
                LogUtils.i(TAG, "Mirror auto fold sw is null, save currentValue: " + isMirrorAutoFoldEnable, false);
                this.mDataSync.setMirrorAutoFoldEnable(isMirrorAutoFoldEnable);
            } else {
                boolean mirrorAutoFoldEnable = this.mDataSync.getMirrorAutoFoldEnable();
                LogUtils.i(TAG, "Mirror auto fold sw, resume saved value: " + mirrorAutoFoldEnable + ", currentValue: " + isMirrorAutoFoldEnable, false);
                if (mirrorAutoFoldEnable != isMirrorAutoFoldEnable) {
                    this.mBcmController.setMirrorAutoFoldEnable(mirrorAutoFoldEnable);
                }
            }
        }
        if (this.mCarConfigHelper.isSupportCms()) {
            if (!event.isCmsPos()) {
                float[] cmsLocation = this.mBcmController.getCmsLocation();
                LogUtils.i(TAG, "CMS sync, save location: " + GsonUtil.toJson(cmsLocation), false);
                this.mBcmController.saveCmsLocation(cmsLocation);
            } else {
                float[] savedCmsLocation = this.mBcmController.getSavedCmsLocation();
                if (savedCmsLocation != null) {
                    LogUtils.i(TAG, "CMS sync, set location: " + GsonUtil.toJson(savedCmsLocation), false);
                    this.mBcmController.setCmsLocation(savedCmsLocation);
                } else {
                    float[] cmsLocation2 = this.mBcmController.getCmsLocation();
                    LogUtils.i(TAG, "CMS sync, save location2: " + GsonUtil.toJson(cmsLocation2), false);
                    this.mBcmController.saveCmsLocation(cmsLocation2);
                }
            }
            if (event.isCmsAutoBrightSw()) {
                boolean cmsAutoBrightSw = this.mDataSync.getCmsAutoBrightSw();
                LogUtils.i(TAG, "CMS sync, set AutoBrightSw: " + cmsAutoBrightSw, false);
                this.mBcmController.setCmsAutoBrightSw(cmsAutoBrightSw);
            } else {
                boolean cmsAutoBrightSw2 = this.mBcmController.getCmsAutoBrightSw();
                LogUtils.i(TAG, "CMS sync, save AutoBrightSw: " + cmsAutoBrightSw2, false);
                this.mDataSync.setCmsAutoBrightSw(cmsAutoBrightSw2);
            }
            if (event.isCmsBright()) {
                int cmsBright = this.mDataSync.getCmsBright();
                LogUtils.i(TAG, "CMS sync, set Brightness: " + cmsBright, false);
                this.mBcmController.setCmsBright(cmsBright);
            } else {
                int cmsBright2 = this.mBcmController.getCmsBright();
                LogUtils.i(TAG, "CMS sync, save Brightness: " + cmsBright2, false);
                this.mDataSync.setCmsBright(cmsBright2);
            }
            if (event.isCmsHighSpdSw()) {
                boolean cmsHighSpdSw = this.mDataSync.getCmsHighSpdSw();
                LogUtils.i(TAG, "CMS sync, set HighSpdSw: " + cmsHighSpdSw, false);
                this.mBcmController.setCmsHighSpdAssistSw(cmsHighSpdSw);
            } else {
                boolean cmsHighSpdAssistSw = this.mBcmController.getCmsHighSpdAssistSw();
                LogUtils.i(TAG, "CMS sync, save HighSpdSw: " + cmsHighSpdAssistSw, false);
                this.mDataSync.setCmsHighSpdSw(cmsHighSpdAssistSw);
            }
            if (event.isCmsLowSpdSw()) {
                boolean cmsLowSpdSw = this.mDataSync.getCmsLowSpdSw();
                LogUtils.i(TAG, "CMS sync, set LowSpdSw: " + cmsLowSpdSw, false);
                this.mBcmController.setCmsLowSpdAssistSw(cmsLowSpdSw);
            } else {
                boolean cmsLowSpdAssistSw = this.mBcmController.getCmsLowSpdAssistSw();
                LogUtils.i(TAG, "CMS sync, save LowSpdSw: " + cmsLowSpdAssistSw, false);
                this.mDataSync.setCmsLowSpdSw(cmsLowSpdAssistSw);
            }
            if (event.isCmsObjectRecognizeSw()) {
                boolean cmsObjectRecognizeSw = this.mDataSync.getCmsObjectRecognizeSw();
                LogUtils.i(TAG, "CMS sync, set ObjectRecognizeSw: " + cmsObjectRecognizeSw, false);
                this.mBcmController.setCmsObjectRecognizeSw(cmsObjectRecognizeSw);
            } else {
                boolean cmsObjectRecognizeSw2 = this.mBcmController.getCmsObjectRecognizeSw();
                LogUtils.i(TAG, "CMS sync, save ObjectRecognizeSw: " + cmsObjectRecognizeSw2, false);
                this.mDataSync.setCmsObjectRecognizeSw(cmsObjectRecognizeSw2);
            }
            if (event.isCmsReverseSw()) {
                LogUtils.i(TAG, "CMS sync, set ReverseSw: " + this.mDataSync.getCmsReverseSw(), false);
                this.mBcmController.setCmsReverseAssistSw(this.mDataSync.getCmsReverseSw());
            } else {
                boolean cmsReverseAssistSw = this.mBcmController.getCmsReverseAssistSw();
                LogUtils.i(TAG, "CMS sync, save ReverseSw: " + cmsReverseAssistSw, false);
                this.mDataSync.setCmsReverseSw(cmsReverseAssistSw);
            }
            if (event.isCmsViewAngle()) {
                int cmsViewAngle = this.mDataSync.getCmsViewAngle();
                LogUtils.i(TAG, "CMS sync, set ViewAngle: " + cmsViewAngle, false);
                this.mBcmController.setCmsViewAngle(cmsViewAngle);
                return;
            }
            int cmsViewAngle2 = this.mBcmController.getCmsViewAngle();
            LogUtils.i(TAG, "CMS sync, save ViewAngle: " + cmsViewAngle2, false);
            this.mDataSync.setCmsViewAngle(cmsViewAngle2);
        }
    }

    private void syncTrunkData(CarControlSyncDataEvent event) {
        if (this.mCarConfigHelper.isSupportSensorTrunk()) {
            boolean isTrunkSensorEnable = this.mBcmController.isTrunkSensorEnable();
            if (!event.isSensorTrunkSw()) {
                LogUtils.i(TAG, "Sensor trunk sw is null, save currentValue: " + isTrunkSensorEnable, false);
                this.mDataSync.setSensorTrunkSw(isTrunkSensorEnable);
                return;
            }
            boolean sensorTrunkSw = this.mDataSync.getSensorTrunkSw();
            LogUtils.i(TAG, "Sensor trunk sw, resume saved value: " + sensorTrunkSw + ", currentValue: " + isTrunkSensorEnable, false);
            if (sensorTrunkSw != isTrunkSensorEnable) {
                this.mBcmController.setTrunkSensorEnable(sensorTrunkSw);
            }
        }
    }

    private void syncOtherData(CarControlSyncDataEvent event) {
        syncHighSpdCloseWin(event);
        boolean autoWindowLockSw = this.mBcmController.getAutoWindowLockSw();
        if (!event.isLockCloseWin()) {
            LogUtils.i(TAG, "Lock auto close win, save currentValue: " + autoWindowLockSw, false);
            this.mDataSync.setLockCloseWin(autoWindowLockSw);
        } else {
            boolean lockCloseWin = this.mDataSync.getLockCloseWin();
            LogUtils.i(TAG, "Lock auto close win, resume saved value: " + lockCloseWin + ", currentValue: " + autoWindowLockSw, false);
            if (lockCloseWin != autoWindowLockSw) {
                this.mBcmController.setAutoWindowLockSw(lockCloseWin);
            }
        }
        int wiperSensitivity = this.mBcmController.getWiperSensitivity();
        if (!event.isWiperSensitivity()) {
            LogUtils.i(TAG, "Wiper sensitivity, save currentValue: " + wiperSensitivity, false);
            this.mDataSync.setWiperSensitivity(wiperSensitivity);
        } else {
            int wiperSensitivity2 = this.mDataSync.getWiperSensitivity();
            LogUtils.i(TAG, "Wiper sensitivity, resume saved value: " + wiperSensitivity2 + ", currentValue: " + wiperSensitivity, false);
            if (wiperSensitivity2 != wiperSensitivity) {
                this.mBcmController.setWiperSensitivity(wiperSensitivity2, false, true);
            }
        }
        if (this.mCarConfigHelper.isSupportControlSteer()) {
            if (!event.isSteerPos()) {
                int[] steerPos = this.mBcmController.getSteerPos();
                LogUtils.i(TAG, "steer pos is null, save currentValue: " + Arrays.toString(steerPos), false);
                this.mBcmController.saveSteerPos(steerPos);
            } else {
                int[] steerSavedPos = this.mBcmController.getSteerSavedPos();
                LogUtils.i(TAG, "steer pos, resume saved value: " + Arrays.toString(steerSavedPos), false);
                if (steerSavedPos == null) {
                    this.mBcmController.saveSteerPos(this.mBcmController.getSteerPos());
                } else {
                    this.mBcmController.setSteerPos(steerSavedPos);
                    this.mBcmController.saveSteerPos(steerSavedPos);
                }
            }
        }
        if (this.mCarConfigHelper.isSupportCwc()) {
            boolean isCwcSwEnable = this.mBcmController.isCwcSwEnable();
            if (!event.isCwcSw()) {
                LogUtils.i(TAG, "CWC sw is null, save currentValue: " + isCwcSwEnable, false);
                this.mDataSync.setCwcSw(isCwcSwEnable);
            } else {
                boolean cwcSw = this.mDataSync.getCwcSw();
                LogUtils.i(TAG, "CWC sw, resume saved value: " + cwcSw + ", currentValue: " + isCwcSwEnable, false);
                if (cwcSw != isCwcSwEnable) {
                    this.mBcmController.setCwcSwEnable(cwcSw);
                }
            }
        }
        if (this.mCarConfigHelper.isSupportSdc()) {
            if (!event.isSdcBrakeCloseType()) {
                LogUtils.i(TAG, "SDC brake close door cfg sw is null, save default value: 0", false);
                this.mBcmController.setSdcBrakeCloseDoorCfg(0);
                return;
            }
            int sdcBrakeCloseCfg = this.mDataSync.getSdcBrakeCloseCfg();
            LogUtils.i(TAG, "SDC brake close door cfg, resume saved value: " + sdcBrakeCloseCfg, false);
            this.mBcmController.setSdcBrakeCloseDoorCfg(sdcBrakeCloseCfg);
        }
    }

    protected void syncHighSpdCloseWin(CarControlSyncDataEvent event) {
        boolean isHighSpdCloseWinEnabled = this.mBcmController.isHighSpdCloseWinEnabled();
        if (!event.isHighSpdCloseWin()) {
            LogUtils.i(TAG, "High speed close win is null, save currentValue: " + isHighSpdCloseWinEnabled, false);
            this.mDataSync.setHighSpdCloseWin(isHighSpdCloseWinEnabled);
            this.mBcmController.setHighSpdCloseWin(isHighSpdCloseWinEnabled);
            return;
        }
        boolean isWinHighSpdEnabled = this.mDataSync.isWinHighSpdEnabled();
        LogUtils.i(TAG, "High speed close win, resume saved value: " + isWinHighSpdEnabled, false);
        this.mBcmController.setHighSpdCloseWin(isWinHighSpdEnabled);
    }

    private void syncHvacData(CarControlSyncDataEvent event) {
        int hvacCirculationTime = this.mHvacController.getHvacCirculationTime();
        if (!event.isHvacCircle()) {
            LogUtils.i(TAG, "Hvac circulation time, save currentValue: " + hvacCirculationTime, false);
            this.mDataSync.setHvacCircle(hvacCirculationTime);
        } else {
            int hvacCircle = this.mDataSync.getHvacCircle();
            LogUtils.i(TAG, "Hvac circulation time, resume saved value: " + hvacCircle + ", currentValue: " + hvacCirculationTime, false);
            if (hvacCircle != hvacCirculationTime) {
                this.mHvacController.setHvacCirculationTime(hvacCircle);
            }
        }
        if (this.mCarConfigHelper.isSupportAqs()) {
            int hvacAqsLevel = this.mHvacController.getHvacAqsLevel();
            if (!event.isHvacAqs()) {
                LogUtils.i(TAG, "Hvac aqs, save currentValue: " + hvacAqsLevel, false);
                this.mDataSync.setHvacAqsLevel(hvacAqsLevel);
            } else {
                int hvacAqsLevel2 = this.mDataSync.getHvacAqsLevel();
                LogUtils.i(TAG, "Hvac aqs, resume saved value: " + hvacAqsLevel2 + ", currentValue: " + hvacAqsLevel, false);
                if (hvacAqsLevel2 != hvacAqsLevel) {
                    this.mHvacController.setHvacAqsLevel(hvacAqsLevel2);
                }
            }
        }
        if (this.mCarConfigHelper.isSupportSmartHvac()) {
            int airAutoProtectMode = this.mHvacController.getAirAutoProtectMode();
            if (!event.isHvacAirProtect()) {
                LogUtils.i(TAG, "Hvac air auto protect, save currentValue: " + airAutoProtectMode, false);
                this.mHvacController.setAirAutoProtectedMode(airAutoProtectMode);
            } else {
                int hvacAirProtectMode = this.mDataSync.getHvacAirProtectMode();
                LogUtils.i(TAG, "Hvac air auto protect, resume saved value: " + hvacAirProtectMode + ", currentValue: " + airAutoProtectMode, false);
                this.mHvacController.setAirAutoProtectedMode(hvacAirProtectMode);
            }
        }
        boolean isHvacSelfDryOn = this.mHvacController.isHvacSelfDryOn();
        if (!event.isHvacSelfDry()) {
            LogUtils.i(TAG, "Hvac self dry, save currentValue: " + isHvacSelfDryOn, false);
            this.mDataSync.setHvacSelfDry(isHvacSelfDryOn);
            return;
        }
        boolean hvacSelfDry = this.mDataSync.getHvacSelfDry();
        LogUtils.i(TAG, "Hvac self dry, resume saved value: " + hvacSelfDry + ", currentValue: " + isHvacSelfDryOn, false);
        if (hvacSelfDry != isHvacSelfDryOn) {
            this.mHvacController.setHvacSelfDryEnable(hvacSelfDry);
        }
    }

    private void syncAvasData(CarControlSyncDataEvent event) {
        int lowSpdSoundType = this.mAvasController.getLowSpdSoundType();
        if (!event.isAvasEffect()) {
            LogUtils.i(TAG, "AVAS effect, save currentValue: " + lowSpdSoundType, false);
            this.mDataSync.setAvasEffect(lowSpdSoundType);
        } else {
            int avasEffect = this.mDataSync.getAvasEffect();
            LogUtils.i(TAG, "AVAS effect, resume saved value: " + avasEffect + ", currentValue: " + lowSpdSoundType, false);
            if (avasEffect != lowSpdSoundType) {
                this.mAvasController.setLowSpdSoundType(avasEffect);
            }
        }
        int friendSoundType = this.mAvasController.getFriendSoundType();
        if (!event.isSayHiEffect()) {
            LogUtils.i(TAG, "Friend sayhi effect, save currentValue: " + friendSoundType, false);
            this.mDataSync.setSayHiEffect(friendSoundType);
            this.mLluController.setLluEffect(10, convertSayhiAvasToLlu(friendSoundType));
        } else {
            int sayHiEffect = this.mDataSync.getSayHiEffect();
            LogUtils.i(TAG, "Friend sayhi effect, resume saved value: " + sayHiEffect + ", currentValue: " + friendSoundType, false);
            if (sayHiEffect != friendSoundType) {
                this.mAvasController.setFriendSoundType(sayHiEffect);
            }
            this.mLluController.setLluEffect(10, convertSayhiAvasToLlu(sayHiEffect));
        }
        int bootSoundEffect = this.mAvasController.getBootSoundEffect();
        if (!event.isBootEffect()) {
            LogUtils.i(TAG, "Boot sound effect, save currentValue: " + bootSoundEffect, false);
            this.mDataSync.setBootEffect(bootSoundEffect);
        } else {
            int bootEffect = this.mDataSync.getBootEffect();
            LogUtils.i(TAG, "Boot sound effect, resume saved value: " + bootEffect + ", currentValue: " + bootSoundEffect, false);
            if (bootEffect != bootSoundEffect) {
                this.mAvasController.setBootSoundEffect(bootEffect);
            }
        }
        int bootEffectBeforeSw = this.mAvasController.getBootEffectBeforeSw();
        if (!event.isBootEffectBeforeSw()) {
            LogUtils.i(TAG, "Boot sound effect before Sw, save currentValue: " + bootEffectBeforeSw, false);
            if (bootEffectBeforeSw == 0) {
                this.mDataSync.setBootEffectBeforeSw(1);
                return;
            } else {
                this.mDataSync.setBootEffectBeforeSw(bootEffectBeforeSw);
                return;
            }
        }
        int bootEffectBeforeSw2 = this.mDataSync.getBootEffectBeforeSw();
        LogUtils.i(TAG, "Boot sound effect before Sw, resume saved value: " + bootEffectBeforeSw2 + ", currentValue: " + bootEffectBeforeSw, false);
        if (bootEffectBeforeSw2 == 0) {
            this.mAvasController.setBootEffectBeforeSw(1);
        } else if (bootEffectBeforeSw2 != bootEffectBeforeSw) {
            this.mAvasController.setBootEffectBeforeSw(bootEffectBeforeSw2);
        }
    }

    private void syncXPilotData(CarControlSyncDataEvent event) {
        LogUtils.i(TAG, "syncXPilotData start");
        resumeOrDataSyncMemExamResult(false);
        resumeOrDataSyncLccExamResult(false);
        resumeOrSyncLccAlcState(false);
        if (this.mCarConfigHelper.isSupportNgp()) {
            if (this.mDataSync.isGuest()) {
                LogUtils.i(TAG, "User not login,keep ngp off!");
                this.mScuController.setNgpEnable(false);
                if (this.mCarConfigHelper.isSupportCNgp()) {
                    LogUtils.i(TAG, "User not login,keep cngp off!");
                    this.mXpuController.setCityNgpSw(false);
                }
            } else {
                syncXPilotNGP();
            }
        }
        if (!event.isAutoParkSw()) {
            boolean z = this.mScuController.getAutoParkSw() == 1;
            LogUtils.i(TAG, "XPilot AutoPark, save currentValue: " + z, false);
            this.mDataSync.setAutoParkSw(z);
        }
        resumeOrSyncApaState(false);
        if (this.mCarConfigHelper.isSupportMemPark()) {
            if (this.mDataSync.isGuest()) {
                LogUtils.i(TAG, "User not login,keep mem park off!");
                this.mScuController.setMemoryParkSw(false);
            } else {
                this.mScuController.syncXPilotMemParkSw();
            }
        }
        resumeOrSyncNraState(false);
    }

    void handleSyncSettings(CarControlSyncDataEvent event) {
        LogUtils.i(TAG, "Start to resume account settings", false);
        syncVcuData(event);
        syncChassisData(event);
        syncIcmData(event);
        syncBcmData(event);
        syncLluData(event);
        syncAtlData(event);
        syncLockData(event);
        syncSeatData(event);
        syncMirrorData(event);
        syncTrunkData(event);
        syncOtherData(event);
        syncAvasData(event);
        if (this.mVcuController.getGearLevel() == 4) {
            syncXPilotData(event);
        }
        LogUtils.i(TAG, "End of resume account settings", false);
    }

    private void tryRestoreEcuIfFactoryReset() {
        if (TextUtils.isEmpty(Settings.System.getString(App.getInstance().getContentResolver(), GlobalConstant.PREFS.PREF_FACTORY_RESET_FLAG))) {
            if (BaseFeatureOption.getInstance().isSupportNapa() && this.mCarConfigHelper.isSupportKeyPark()) {
                this.mScuController.setKeyParkSw(false);
            }
            Settings.System.putString(App.getInstance().getContentResolver(), GlobalConstant.PREFS.PREF_FACTORY_RESET_FLAG, GlobalConstant.DEFAULT.FACTORY_RESET_VALUE);
        }
    }

    void handleFactoryReset() {
        LogUtils.i(TAG, "Save current seat and rear mirror position to Temp account", false);
        if (this.mCarConfigHelper.isSupportMsmD()) {
            int[] iArr = {this.mMsmController.getDSeatHorzPos(), this.mMsmController.getDSeatVerPos(), this.mMsmController.getDSeatTiltPos(), this.mMsmController.getDSeatLegPos(), this.mMsmController.getDSeatCushionPos()};
            LogUtils.i(TAG, "Save current seat position: " + Arrays.toString(iArr));
            this.mDataSync.saveDrvSeatPos(iArr);
        }
        if (this.mCarConfigHelper.isSupportMirrorMemory()) {
            Mirror mirror = new Mirror();
            mirror.leftHPos = this.mBcmController.getLeftMirrorLRPos(true);
            mirror.leftVPos = this.mBcmController.getLeftMirrorUDPos(true);
            mirror.rightHPos = this.mBcmController.getRightMirrorLRPos(true);
            mirror.rightVPos = this.mBcmController.getRightMirrorUDPos(true);
            String mirror2 = mirror.toString();
            LogUtils.i(TAG, "Save current mirror position: " + mirror2, false);
            this.mDataSync.setMirrorData(mirror2);
        }
    }

    private boolean isDrvDoorOpened() {
        int[] doorsState = this.mBcmController.getDoorsState();
        return doorsState != null && doorsState.length == 4 && doorsState[0] == 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isDrvDoorClose() {
        int[] doorsState = this.mBcmController.getDoorsState();
        return doorsState != null && doorsState.length == 4 && doorsState[0] == 0;
    }

    private boolean isPsnDoorOpened() {
        int[] doorsState = this.mBcmController.getDoorsState();
        return doorsState != null && doorsState.length == 4 && doorsState[1] == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSeatWelcomeMode(int[] doorState) {
        if (doorState[0] == 0 && this.mIsDrvDoorOpened) {
            this.mIsDrvDoorOpened = false;
            checkWelcomeModeGoOn();
            handleRearMirrorWhenGoOn();
        } else if (doorState[0] != 1 || this.mIsDrvDoorOpened) {
        } else {
            this.mIsDrvDoorOpened = true;
            checkWelcomeModeGoOff();
            this.mCarConfigHelper.isSupportAirSuspension();
        }
    }

    protected void checkWelcomeModeGoOff() {
        int calculateWelcomeModeGoOffPosition;
        if (this.mCarConfigHelper.isSupportControlSteer()) {
            this.mBcmController.setSteerVerPos(0);
        }
        if (!BaseFeatureOption.getInstance().isSupportCduWelcomeOff()) {
            LogUtils.i(TAG, "Cdu do not handle welcome mode go off", false);
        } else if (!this.mMcuController.getSeatWelcomeMode()) {
            LogUtils.i(TAG, "Welcome mode off, do not handle welcome go off", false);
        } else {
            int userScenarioStatus = this.mScenarioController.getUserScenarioStatus(IScenarioController.MEDITATION_MODE);
            if (userScenarioStatus == 1 || userScenarioStatus == 2) {
                LogUtils.w(TAG, "Under meditation mode, do not handle welcome go off", false);
                return;
            }
            int userScenarioStatus2 = this.mScenarioController.getUserScenarioStatus(IScenarioController.SCENARIO_5D_CINEMA_MODE);
            if (userScenarioStatus2 == 1 || userScenarioStatus2 == 2) {
                LogUtils.w(TAG, "Under SCENARIO_5D_CINEMA_MODE, do not handle welcome go off", false);
            } else if (!this.mMsmController.isDrvHeadrestNormal()) {
                LogUtils.w(TAG, "Drv headrest is moved, do not handle welcome go off", false);
            } else {
                int gearLevel = this.mVcuController.getGearLevel();
                float carSpeed = this.mVcuController.getCarSpeed();
                boolean isDrvSeatOccupied = this.mBcmController.isDrvSeatOccupied();
                boolean isDrvDoorOpened = isDrvDoorOpened();
                int drvBeltStatus = this.mMsmController.getDrvBeltStatus();
                LogUtils.i(TAG, "checkWelcomeModeGoOff currentGear=" + gearLevel + ", speed=" + carSpeed + ", driverState=" + isDrvSeatOccupied + ", doorState=" + isDrvDoorOpened + ", seatBeltState=" + drvBeltStatus, false);
                if (gearLevel == 4 && carSpeed < 3.0f && isDrvSeatOccupied && isDrvDoorOpened && drvBeltStatus == 1 && (calculateWelcomeModeGoOffPosition = calculateWelcomeModeGoOffPosition()) >= 0) {
                    if (this.mCarConfigHelper.isSupportDrvCushion()) {
                        IMsmController iMsmController = this.mMsmController;
                        iMsmController.setDriverAllPositions(calculateWelcomeModeGoOffPosition, iMsmController.getDSeatVerPos(), this.mMsmController.getDSeatTiltPos(), this.mMsmController.getDSeatLegPos(), 0);
                    } else {
                        this.mMsmController.setDSeatHorzPos(calculateWelcomeModeGoOffPosition);
                    }
                }
                this.mMsmController.setDrvWelcomeModeActiveSt(true);
            }
        }
    }

    private int calculateWelcomeModeGoOffPosition() {
        return calculateWelcomeModeGoOffPosition(true);
    }

    private int calculateWelcomeModeGoOffPosition(boolean useCurrentPos) {
        int[] drvSeatSavedPos;
        int dSeatHorzPos = this.mMsmController.getDSeatHorzPos();
        if (!useCurrentPos && (drvSeatSavedPos = this.mDataSync.getDrvSeatSavedPos()) != null) {
            dSeatHorzPos = drvSeatSavedPos[0];
        }
        if (dSeatHorzPos > 25) {
            int i = dSeatHorzPos > 45 ? dSeatHorzPos - 20 : 25;
            LogUtils.i(TAG, "calculateWelcomeModeGoOffPosition start new SeatHorzPos: " + i + ", curSeatHorzPos: " + dSeatHorzPos, false);
            return i;
        }
        LogUtils.i(TAG, "calculateWelcomeModeGoOffPosition curSeatHorzPos: " + dSeatHorzPos + ", do not need adjust seat", false);
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePsnSeatWelcomeMode(int[] doorState) {
        if (doorState[1] == 0 && this.mIsPsnDoorOpened) {
            this.mIsPsnDoorOpened = false;
            checkPsnWelcomeModeGoOn();
        } else if (doorState[1] != 1 || this.mIsPsnDoorOpened) {
        } else {
            this.mIsPsnDoorOpened = true;
            checkPsnWelcomeModeGoOff();
            this.mCarConfigHelper.isSupportAirSuspension();
        }
    }

    protected void checkPsnWelcomeModeGoOff() {
        int calculatePsnWelcomeModeGoOffPosition;
        if (!BaseFeatureOption.getInstance().isSupportCduWelcomeOff()) {
            LogUtils.i(TAG, "Cdu do not handle welcome mode go off", false);
        } else if (!this.mMsmController.getPsnSeatWelcomeMode()) {
            LogUtils.i(TAG, "PSN welcome mode off, do not handle welcome go off", false);
        } else {
            int userScenarioStatus = this.mScenarioController.getUserScenarioStatus(IScenarioController.MEDITATION_MODE);
            if (userScenarioStatus == 1 || userScenarioStatus == 2) {
                LogUtils.w(TAG, "Under meditation mode, do not handle PSN welcome go off", false);
                return;
            }
            int userScenarioStatus2 = this.mScenarioController.getUserScenarioStatus(IScenarioController.SCENARIO_5D_CINEMA_MODE);
            if (userScenarioStatus2 == 1 || userScenarioStatus2 == 2) {
                LogUtils.w(TAG, "Under SCENARIO_5D_CINEMA_MODE, do not PSN handle welcome go off", false);
            } else if (!this.mMsmController.isPsnHeadrestNormal()) {
                LogUtils.w(TAG, "Psn headrest is moved, do not handle welcome go off", false);
            } else {
                int gearLevel = this.mVcuController.getGearLevel();
                float carSpeed = this.mVcuController.getCarSpeed();
                boolean isPsnSeatOccupied = this.mMsmController.isPsnSeatOccupied();
                boolean isPsnDoorOpened = isPsnDoorOpened();
                int psnBeltStatus = this.mMsmController.getPsnBeltStatus();
                LogUtils.i(TAG, "checkPsnWelcomeModeGoOff currentGear=" + gearLevel + ", speed=" + carSpeed + ", psnState=" + isPsnSeatOccupied + ", doorState=" + isPsnDoorOpened + ", seatBeltState=" + psnBeltStatus, false);
                if (gearLevel == 4 && carSpeed < 3.0f && isPsnSeatOccupied && isPsnDoorOpened && psnBeltStatus == 1 && (calculatePsnWelcomeModeGoOffPosition = calculatePsnWelcomeModeGoOffPosition()) >= 0) {
                    if (this.mCarConfigHelper.isSupportPsnLeg()) {
                        IMsmController iMsmController = this.mMsmController;
                        iMsmController.setPsnAllPositions(calculatePsnWelcomeModeGoOffPosition, iMsmController.getPSeatVerPos(), this.mMsmController.getPSeatTiltPos(), 0);
                    } else {
                        this.mMsmController.setPSeatHorzPos(calculatePsnWelcomeModeGoOffPosition);
                    }
                }
                this.mMsmController.setPsnWelcomeModeActiveSt(true);
            }
        }
    }

    private int calculatePsnWelcomeModeGoOffPosition() {
        int pSeatHorzPos = this.mMsmController.getPSeatHorzPos();
        if (pSeatHorzPos > 25) {
            int i = pSeatHorzPos > 45 ? pSeatHorzPos - 20 : 25;
            LogUtils.i(TAG, "calculatePsnWelcomeModeGoOffPosition start new SeatHorzPos: " + i + ", curSeatHorzPos: " + pSeatHorzPos, false);
            return i;
        }
        LogUtils.i(TAG, "calculatePsnWelcomeModeGoOffPosition curSeatHorzPos: " + pSeatHorzPos + ", do not need adjust seat", false);
        return -1;
    }

    protected void checkWelcomeModeGoOn() {
        if (this.mCarConfigHelper.isSupportSdc()) {
            handleSeatBackWhenSdcDoorClosed();
        }
        if (this.mNeedUpdateDrvSeat && !this.mDataSync.getWelcomeMode() && !this.mDataSync.isGuest()) {
            AccountViewModel accountViewModel = (AccountViewModel) ViewModelManager.getInstance().getViewModelImpl(IAccountViewModel.class);
            if (accountViewModel.checkLogin()) {
                Account currentAccountInfo = accountViewModel.getCurrentAccountInfo();
                LogUtils.d(TAG, "checkLogin currentAccountInfo: " + currentAccountInfo + ", old account info: " + this.mAccountInfo, false);
                Account account = this.mAccountInfo;
                if (account == null || !account.equals(currentAccountInfo)) {
                    if (!this.mDataSync.isDrvSeatSavedPosEmpty()) {
                        int[] drvSeatSavedPos = this.mDataSync.getDrvSeatSavedPos();
                        boolean z = true;
                        boolean z2 = this.mVcuController.getGearLevel() == 4;
                        boolean isDrvDoorClose = isDrvDoorClose();
                        if (!BaseFeatureOption.getInstance().shouldIgnoreDrvOccupied() && !isDrvSeatOccupied()) {
                            z = false;
                        }
                        LogUtils.i(TAG, "Restore Seat Position when Account Changed: isDrvDoorClosed: " + isDrvDoorClose + ", isDrvSeatOccupied: " + z + ", isGearP: " + z2 + ", saved seat position: " + Arrays.toString(drvSeatSavedPos), false);
                        SeatSmartControl.getInstance().restoreSeat(drvSeatSavedPos);
                    }
                    if (this.mCarConfigHelper.isSupportControlSteer()) {
                        int[] steerData = this.mDataSync.getSteerData(false);
                        LogUtils.i(TAG, "steer pos, resume saved value: " + Arrays.toString(steerData), false);
                        if (steerData == null) {
                            this.mBcmController.saveSteerPos(this.mBcmController.getSteerPos());
                        } else {
                            this.mBcmController.setSteerPos(steerData);
                            this.mBcmController.saveSteerPos(steerData);
                        }
                    }
                    this.mNeedUpdateDrvSeat = false;
                    return;
                }
                LogUtils.i(TAG, "Account did not Change and No Need to Resume", false);
                return;
            }
            return;
        }
        LogUtils.i(TAG, "checkWelcomeModeGoOn isGuest", false);
    }

    protected void handleRearMirrorWhenGoOn() {
        if (this.mCarConfigHelper.isSupportMirrorMemory() && this.mCarConfigHelper.isSopStage() && this.mBcmController.isDrvSeatOccupied() && this.mFunctionModel.isCorrectRMirrorPosAllowed()) {
            LogUtils.i(TAG, "Resume rear mirror position: " + this.mDataSync.getMirrorData() + " when drv door closed and drv seat occupied", false);
            CabinSmartControl.getInstance().syncAccountMirrorPos(this.mDataSync.getMirrorData(), true);
            this.mFunctionModel.setRearMirrorPosCorrectTs(System.currentTimeMillis());
        }
    }

    protected void handleSeatBackWhenSdcDoorClosed() {
        boolean isDrvSeatOccupied = this.mBcmController.isDrvSeatOccupied();
        if (isDrvSeatOccupied) {
            LogUtils.i(TAG, "Do not handle seat move back when door closed and driver on seat", false);
        } else if (this.mScenarioController.getUserScenarioStatus(IScenarioController.MEDITATION_MODE) == 1 || this.mScenarioController.getUserScenarioStatus(IScenarioController.MEDITATION_MODE) == 2) {
            LogUtils.w(TAG, "Under meditation mode, do not handle seat move back off", false);
        } else {
            int userScenarioStatus = this.mScenarioController.getUserScenarioStatus(IScenarioController.SCENARIO_5D_CINEMA_MODE);
            if (userScenarioStatus == 1 || userScenarioStatus == 2) {
                LogUtils.w(TAG, "Under SCENARIO_5D_CINEMA_MODE, do not handle seat move back off", false);
            } else if (!this.mMsmController.isDrvHeadrestNormal()) {
                LogUtils.w(TAG, "Drv headrest is moved, do not handle seat move back off", false);
            } else if (!this.mMcuController.getSeatWelcomeMode()) {
                LogUtils.i(TAG, "Welcome mode off, do not handle seat move back off", false);
            } else {
                int gearLevel = this.mVcuController.getGearLevel();
                float carSpeed = this.mVcuController.getCarSpeed();
                boolean isDrvDoorClose = isDrvDoorClose();
                int drvBeltStatus = this.mMsmController.getDrvBeltStatus();
                LogUtils.i(TAG, "handleSeatBackWhenSdcDoorClosed currentGear=" + gearLevel + ", speed=" + carSpeed + ", driverOnSeat=" + isDrvSeatOccupied + ", doorState=" + isDrvDoorClose + ", seatBeltState=" + drvBeltStatus, false);
                if (gearLevel == 4 && carSpeed < 3.0f && isDrvDoorClose && drvBeltStatus == 1) {
                    int calculateWelcomeModeGoOffPosition = calculateWelcomeModeGoOffPosition(false);
                    int dSeatHorzPos = this.mMsmController.getDSeatHorzPos();
                    LogUtils.i(TAG, "adjustSeatPos: " + calculateWelcomeModeGoOffPosition + ", current horz pos: " + dSeatHorzPos, false);
                    if (calculateWelcomeModeGoOffPosition < 0 || calculateWelcomeModeGoOffPosition >= dSeatHorzPos || Math.abs(dSeatHorzPos - calculateWelcomeModeGoOffPosition) < 2) {
                        return;
                    }
                    this.mMsmController.setDSeatHorzPos(calculateWelcomeModeGoOffPosition);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkHighSpdCloseWinFunction(boolean enabled, float speed) {
        if (enabled && speed >= this.mCarConfigHelper.getHighSpdFuncThreshold()) {
            if (this.mIsHighSpdRunnablePending) {
                return;
            }
            LogUtils.i(TAG, "Current speed=" + speed + ", post delayed mHighSpdRunnable for 10s", false);
            this.mBgHandler.postDelayed(this.mHighSpdRunnable, HIGH_SPEED_WAIT_TIME);
            this.mIsHighSpdRunnablePending = true;
        } else if (this.mIsHighSpdRunnablePending) {
            LogUtils.i(TAG, "remove mHighSpdRunnable", false);
            this.mBgHandler.removeCallbacks(this.mHighSpdRunnable);
            this.mIsHighSpdRunnablePending = false;
        }
    }

    protected void checkBrakeFluid(float speed) {
        if (speed > 3.0f && this.mLastSpd <= 3.0f) {
            LogUtils.i(TAG, "checkBrakeFluid: current spd=" + speed + ", last spd=" + this.mLastSpd, false);
            boolean z = this.mMcuController.getIgStatusFromMcu() == 1;
            boolean z2 = this.mIcmController.getBrakeFluidLevelWarningMessage() == 1;
            LogUtils.i(TAG, "onRawCarSpeedChanged checkBrakeFluid: isIgOn=" + z + ", isBrakeFluidLow=" + z2, false);
            if (z && z2) {
                showEpbDialog();
            }
        }
        this.mLastSpd = speed;
    }

    protected void checkBonnetOpened(int state) {
        Handler handler;
        if (state != 0 && this.mCurrentBonnetClosed) {
            if (this.mVcuController.getGearLevel() != 4 && (handler = this.mBgHandler) != null && !handler.hasCallbacks(this.mCheckBonnetTask)) {
                this.mBgHandler.postDelayed(this.mCheckBonnetTask, 500L);
            }
        } else if (state == 0 && !this.mCurrentBonnetClosed) {
            LogUtils.i(TAG, "Bonnet closed, remove check task", false);
            SpeechHelper.getInstance().stop();
            Handler handler2 = this.mBgHandler;
            if (handler2 != null) {
                handler2.removeCallbacks(this.mCheckBonnetTask);
            }
        }
        this.mCurrentBonnetClosed = state == 0;
    }

    protected void checkBrkPedalStatus(int status) {
        if (this.mPreBrkPedalStatus != 1 && status == 1) {
            LogUtils.i(TAG, "Bcm break pedal change to active, current unlock req src: " + this.mCurrUnlockReqRes, false);
            int i = this.mCurrUnlockReqRes;
            if (i == 1 || i == 2) {
                if (this.mBgHandler != null) {
                    this.mHasCheckReadyDisableTask = true;
                    this.mCheckBrkPedalStartTime = System.currentTimeMillis();
                    this.mBgHandler.postDelayed(this.mCheckReadyDisableTask, 1000L);
                }
            } else if (this.mBgHandler != null) {
                this.mHasCheckReadyDisableTask = true;
                this.mCheckBrkPedalStartTime = System.currentTimeMillis();
                this.mBgHandler.postDelayed(this.mCheckReadyDisableTask, 5000L);
            }
        } else if (this.mPreBrkPedalStatus == 1 && status != 1 && this.mCheckBrkPedalStartTime > 0 && System.currentTimeMillis() - this.mCheckBrkPedalStartTime < 1000) {
            LogUtils.i(TAG, "Bcm break pedal change to de-active within 1000ms, stop check BcmReadyEnable", false);
            this.mCheckBrkPedalStartTime = 0L;
            this.mHasCheckReadyDisableTask = false;
            this.mBgHandler.removeCallbacks(this.mCheckReadyDisableTask);
        }
        this.mPreBrkPedalStatus = status;
    }

    private void checkReadyDisableStatus() {
        if (this.mHasCheckReadyDisableTask) {
            this.mHasCheckReadyDisableTask = false;
            this.mCheckBrkPedalStartTime = 0L;
            boolean isEvSysReady = this.mVcuController.isEvSysReady();
            int bcmReadyEnableState = this.mBcmController.getBcmReadyEnableState();
            LogUtils.i(TAG, "Unlock req res: " + this.mCurrUnlockReqRes + ", EvReady: " + isEvSysReady + ", BcmReadyEnable: " + bcmReadyEnableState + ", mIsBcmReadyEnabled: " + this.mIsBcmReadyEnabled, false);
            if (isEvSysReady || this.mIsBcmReadyEnabled || bcmReadyEnableState != 0) {
                return;
            }
            showReadyDisableDialog();
            return;
        }
        LogUtils.i(TAG, "No need to check BcmReadyEnable, because mHasCheckReadyDisableTask is false", false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showReadyDisableDialog() {
        LogUtils.i(TAG, "showReadyDisableDialog: mIsReadyDisableDialogShow=" + this.mIsReadyDisableDialogShow, false);
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$EojT9m8gDYfYLCQOxO2whFmqYqU
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$showReadyDisableDialog$12$ServiceViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$showReadyDisableDialog$12$ServiceViewModel() {
        int i;
        int i2;
        if (this.mIsReadyDisableDialogShow) {
            return;
        }
        if (this.mCurrUnlockReqRes == 2) {
            i = R.string.ready_disable_error_title_common;
            this.mReadyDisableScene = GlobalConstant.UnityDialogScene.DIALOG_READY_WITHOUT_KEY;
            i2 = R.layout.dialog_ready_disable_error_common_no_nfc;
            int i3 = R.string.ready_disable_error_repair_content_common_no_nfc;
            StatisticUtils.sendStatistic(PageEnum.CAR_KEY_PAGE, BtnEnum.BT_KEY_INVALID, new Object[0]);
        } else if (this.mCurrUnlockReqRes == 1) {
            this.mReadyDisableScene = GlobalConstant.UnityDialogScene.DIALOG_READY_TIMEOUT_NFC_KEY;
            i = R.string.ready_disable_error_title_nfc;
            i2 = R.layout.dialog_ready_disable_error_nfc;
            int i4 = R.string.ready_disable_error_repair_content_nfc;
        } else if (this.mCurrUnlockReqRes == 4) {
            this.mReadyDisableScene = GlobalConstant.UnityDialogScene.DIALOG_READY_WITHOUT_WATCH_KEY;
            i = R.string.ready_disable_error_title_bt;
            i2 = R.layout.dialog_ready_disable_error_bt;
            int i5 = R.string.ready_disable_error_repair_content_bt;
        } else {
            i = R.string.ready_disable_error_title_common;
            if (this.mCarConfigHelper.isSupportNfc()) {
                this.mReadyDisableScene = GlobalConstant.UnityDialogScene.DIALOG_READY_WITHOUT_NFC_KEY;
                i2 = R.layout.dialog_ready_disable_error_common;
                int i6 = R.string.ready_disable_error_repair_content_common;
            } else {
                this.mReadyDisableScene = GlobalConstant.UnityDialogScene.DIALOG_READY_WITHOUT_KEY;
                i2 = R.layout.dialog_ready_disable_error_common_no_nfc;
                int i7 = R.string.ready_disable_error_repair_content_common_no_nfc;
            }
            StatisticUtils.sendStatistic(PageEnum.CAR_KEY_PAGE, BtnEnum.NO_CAR_KEY, new Object[0]);
        }
        if (this.mReadyDisableDialog == null) {
            if (!BaseFeatureOption.getInstance().isSupportShowCustomDialogStyle() && (!BaseFeatureOption.getInstance().isShowReadyDisableCustomXDialog() || this.mCurrUnlockReqRes == 4)) {
                this.mReadyDisableDialog = new XDialog(App.getInstance(), R.style.XDialogView_Large);
            } else {
                this.mReadyDisableDialog = new XDialog(App.getInstance(), R.style.XDialogView_Large_Custom);
            }
            this.mReadyDisableDialog.setSystemDialog(2008);
            if (BaseFeatureOption.getInstance().isSupportChangeableDialogTitleSize() && this.mCurrUnlockReqRes == 2) {
                ((TextView) this.mReadyDisableDialog.getContentView().findViewById(R.id.x_dialog_title)).setTextSize(App.getInstance().getResources().getDimension(R.dimen.x_font_title_03_size));
            }
            this.mReadyDisableDialog.setTitle(i);
            this.mReadyDisableDialog.setCustomView(LayoutInflater.from(App.getInstance()).inflate(i2, this.mReadyDisableDialog.getContentView(), false), false);
            this.mReadyDisableDialog.setPositiveButton(ResUtils.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$_UmGGU-TiQdntcwwTNc_d_ZbP9k
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog, int i8) {
                    ServiceViewModel.this.lambda$null$11$ServiceViewModel(xDialog, i8);
                }
            });
            this.mReadyDisableDialog.setNegativeButton((CharSequence) null, (XDialogInterface.OnClickListener) null);
            this.mReadyDisableDialog.setNegativeButtonEnable(false);
            this.mReadyDisableDialog.getDialog().setCanceledOnTouchOutside(false);
        }
        this.mReadyDisableDialog.show();
        this.mIsReadyDisableDialogShow = true;
        this.mBgHandler.postDelayed(this.mAutoDismissDialogTask, 30000L);
    }

    public /* synthetic */ void lambda$null$11$ServiceViewModel(XDialog xDialog, int i) {
        dismissReadyDisableDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissReadyDisableDialog() {
        dismissReadyDisableDialog(false);
    }

    public void dismissReadyDisableDialog(boolean dismissFromNapa) {
        LogUtils.i(TAG, "dismissReadyDisableDialog: mIsReadyDisableDialogShow=" + this.mIsReadyDisableDialogShow, false);
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$q12zYloNFs0_R-VlNHBuCHk_5zE
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$dismissReadyDisableDialog$13$ServiceViewModel();
            }
        });
        this.mHasCheckReadyDisableTask = false;
        this.mCheckBrkPedalStartTime = 0L;
        this.mBgHandler.removeCallbacks(this.mCheckReadyDisableTask);
        this.mBgHandler.removeCallbacks(this.mAutoDismissDialogTask);
    }

    public /* synthetic */ void lambda$dismissReadyDisableDialog$13$ServiceViewModel() {
        XDialog xDialog = this.mReadyDisableDialog;
        if (xDialog != null) {
            xDialog.dismiss();
        }
        this.mIsReadyDisableDialogShow = false;
        this.mReadyDisableDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dismissMicrophoneDialog() {
        if (BaseFeatureOption.getInstance().isSupportNapa()) {
            this.mUnityDismissDialogData.postValue(GlobalConstant.UnityDialogScene.DIALOG_SPEECH_MUTE);
            this.mUnityDismissDialogData.postValue(GlobalConstant.UnityDialogScene.DIALOG_SPEECH_UNMUTE);
            return;
        }
        IAudioViewModel iAudioViewModel = (IAudioViewModel) ViewModelManager.getInstance().getViewModelImplSync(IAudioViewModel.class);
        if (iAudioViewModel != null) {
            iAudioViewModel.dismissAllDialogs();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTboxAcChargeStChanged(int state) {
        if (state == 1 && this.mTboxController.getIotBusinessType() == 1) {
            NotificationHelper.getInstance().showToast(R.string.tbox_ac_charge_unlock_toast);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleIotBusinesstypeChanged(int state) {
        if (state == 1 && this.mTboxController.getTboxAcChargerSt() == 1) {
            NotificationHelper.getInstance().showToast(R.string.tbox_ac_charge_unlock_toast);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class BcmCallbackImpl implements IBcmController.Callback {
        private BcmCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDoorStateChanged(int[] doorState) {
            if (doorState == null || doorState.length < 4) {
                return;
            }
            LogUtils.i(ServiceViewModel.TAG, "onDoorStateChanged mIsDrvDoorOpened=" + ServiceViewModel.this.mIsDrvDoorOpened + ", door state=" + Arrays.toString(doorState), false);
            if (ServiceViewModel.this.mIsDrvDoorOpened && doorState[0] == 0) {
                ServiceViewModel.this.mChassisSmartControl.checkAvhCondition();
            }
            ServiceViewModel.this.handleSeatWelcomeMode(doorState);
            if (ServiceViewModel.this.mCarConfigHelper.isSupportPsnWelcomeMode()) {
                LogUtils.i(ServiceViewModel.TAG, "onDoorStateChanged mIsPsnDoorOpened=" + ServiceViewModel.this.mIsPsnDoorOpened, false);
                ServiceViewModel.this.handlePsnSeatWelcomeMode(doorState);
            }
            if (!ServiceViewModel.this.mCarConfigHelper.isSupportSdc() || ServiceViewModel.this.mSsDoorStateCallback == null) {
                return;
            }
            ServiceViewModel.this.mSsDoorStateCallback.onDoorsStateChanged(doorState);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onMirrorReverseModeChanged(int mode, boolean needMove) {
            if (needMove) {
                CabinSmartControl.getInstance().handleReverseMirror(ServiceViewModel.this.mVcuController.getGearLevel() == 3);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCentralLockStateChanged(boolean locked) {
            CarControl.System.putBool(App.getInstance().getContentResolver(), CarControl.System.CENTRAL_LOCK, locked);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onBonnetStateChanged(int state) {
            ServiceViewModel.this.checkBonnetOpened(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDrvBeltWaringChanged(int mode) {
            if (mode == 0) {
                ServiceViewModel.this.mChassisSmartControl.checkAvhCondition();
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onDrvSeatOccupiedChanged(boolean occupied) {
            CarControl.System.putBool(App.getInstance().getContentResolver(), CarControl.System.DRV_OCCUPIED, occupied);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onBackDefrostChanged(boolean enabled) {
            CarControl.System.putBool(App.getInstance().getContentResolver(), CarControl.System.HVAC_BACK_DEFROST, enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcChargeStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onCwcChargeStateChanged state:" + state + ", mIsFindingKey:" + ServiceViewModel.this.mIsFindingKey, false);
            if (ServiceViewModel.this.mIsFindingKey) {
                return;
            }
            if (state == 1) {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdcharging, R.string.cwc_charging);
            } else if (state != 2) {
            } else {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdchargingcompleted, R.string.cwc_charge_done);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcChargeErrorStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onCwcChargeErrorStateChanged state:" + state, false);
            if (state == 1 || state == 2 || state == 5 || state == 6) {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdchargingabnormal, R.string.cwc_err);
            } else if (state == 4 || state == 3) {
                ServiceViewModel.this.mIsFindingKey = true;
                ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$BcmCallbackImpl$PkskL3j_JPYRa_KPvD8mGT-Mvuo
                    @Override // java.lang.Runnable
                    public final void run() {
                        ServiceViewModel.BcmCallbackImpl.this.lambda$onCwcChargeErrorStateChanged$0$ServiceViewModel$BcmCallbackImpl();
                    }
                }, ServiceViewModel.HIGH_SPEED_WAIT_TIME);
            }
        }

        public /* synthetic */ void lambda$onCwcChargeErrorStateChanged$0$ServiceViewModel$BcmCallbackImpl() {
            ServiceViewModel.this.mIsFindingKey = false;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcFRChargeStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onCwcFRChargeStateChanged state:" + state + ", mIsFindingKeyForPsn:" + ServiceViewModel.this.mIsFindingKeyForPsn, false);
            if (ServiceViewModel.this.mIsFindingKeyForPsn) {
                return;
            }
            if (state == 1) {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdcharging, R.string.cwc_fr_charging);
            } else if (state != 2) {
            } else {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdchargingcompleted, R.string.cwc_fr_charge_done);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcFRChargeErrorStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onCwcFRChargeErrorStateChanged state:" + state, false);
            if (state == 1 || state == 2 || state == 5 || state == 6) {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdchargingabnormal, R.string.cwc_fr_err);
            } else if (state == 4 || state == 3) {
                ServiceViewModel.this.mIsFindingKeyForPsn = true;
                ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$BcmCallbackImpl$f_WU8j99E3XH8EdsDuvatZ94ZQQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        ServiceViewModel.BcmCallbackImpl.this.lambda$onCwcFRChargeErrorStateChanged$1$ServiceViewModel$BcmCallbackImpl();
                    }
                }, ServiceViewModel.HIGH_SPEED_WAIT_TIME);
            }
        }

        public /* synthetic */ void lambda$onCwcFRChargeErrorStateChanged$1$ServiceViewModel$BcmCallbackImpl() {
            ServiceViewModel.this.mIsFindingKeyForPsn = false;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcRLChargeStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onCwcRLChargeStateChanged state:" + state + ", mIsFindingKeyForRL:" + ServiceViewModel.this.mIsFindingKeyForRL, false);
            if (ServiceViewModel.this.mIsFindingKeyForRL) {
                return;
            }
            if (state == 1) {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdcharging, R.string.cwc_rl_charging);
            } else if (state != 2) {
            } else {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdchargingcompleted, R.string.cwc_rl_charge_done);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcRLChargeErrorStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onCwcRLChargeErrorStateChanged state:" + state, false);
            if (state == 1 || state == 2 || state == 5 || state == 6) {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdchargingabnormal, R.string.cwc_rl_err);
            } else if (state == 4 || state == 3) {
                ServiceViewModel.this.mIsFindingKeyForRL = true;
                ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$BcmCallbackImpl$ZWSGqSFVgsolWrJeFS2vaR7Gmxk
                    @Override // java.lang.Runnable
                    public final void run() {
                        ServiceViewModel.BcmCallbackImpl.this.lambda$onCwcRLChargeErrorStateChanged$2$ServiceViewModel$BcmCallbackImpl();
                    }
                }, ServiceViewModel.HIGH_SPEED_WAIT_TIME);
            }
        }

        public /* synthetic */ void lambda$onCwcRLChargeErrorStateChanged$2$ServiceViewModel$BcmCallbackImpl() {
            ServiceViewModel.this.mIsFindingKeyForRL = false;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcRRChargeStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onCwcRRChargeStateChanged state:" + state + ", mIsFindingKeyForRR:" + ServiceViewModel.this.mIsFindingKeyForRR, false);
            if (ServiceViewModel.this.mIsFindingKeyForRR) {
                return;
            }
            if (state == 1) {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdcharging, R.string.cwc_rr_charging);
            } else if (state != 2) {
            } else {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdchargingcompleted, R.string.cwc_rr_charge_done);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCwcRRChargeErrorStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onCwcRLChargeErrorStateChanged state:" + state, false);
            if (state == 1 || state == 2 || state == 5 || state == 6) {
                NotificationHelper.showOsd(App.getInstance(), R.string.cwc_title, R.drawable.ic_mid_osdchargingabnormal, R.string.cwc_rr_err);
            } else if (state == 4 || state == 3) {
                ServiceViewModel.this.mIsFindingKeyForRR = true;
                ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$BcmCallbackImpl$oKp66oQBX-FhlKxU48tuzbR3g34
                    @Override // java.lang.Runnable
                    public final void run() {
                        ServiceViewModel.BcmCallbackImpl.this.lambda$onCwcRRChargeErrorStateChanged$3$ServiceViewModel$BcmCallbackImpl();
                    }
                }, ServiceViewModel.HIGH_SPEED_WAIT_TIME);
            }
        }

        public /* synthetic */ void lambda$onCwcRRChargeErrorStateChanged$3$ServiceViewModel$BcmCallbackImpl() {
            ServiceViewModel.this.mIsFindingKeyForRR = false;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onWiperIntervalChanged(int wiper) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_WIPER_INT, WiperInterval.fromBcmState(wiper));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onWiperSensitivityChanged(int level) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_WIPER_INT, WiperInterval.fromSensitivityBcmState(level));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onWiperSensitivityKeyUp() {
            int wiperSensitivity = ServiceViewModel.this.mBcmController.getWiperSensitivity();
            LogUtils.i(ServiceViewModel.TAG, "onWiperSensitivityKeyUp, currentValue: " + wiperSensitivity, false);
            if (wiperSensitivity < 1 || wiperSensitivity >= 4) {
                return;
            }
            int i = wiperSensitivity + 1;
            ServiceViewModel.this.mBcmController.setWiperSensitivity(i, true);
            ServiceViewModel.this.mIcmController.setWiperSensitivity(i);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onWiperSensitivityKeyDown() {
            int wiperSensitivity = ServiceViewModel.this.mBcmController.getWiperSensitivity();
            LogUtils.i(ServiceViewModel.TAG, "onWiperSensitivityKeyDown, currentValue: " + wiperSensitivity, false);
            if (wiperSensitivity <= 1 || wiperSensitivity > 4) {
                return;
            }
            int i = wiperSensitivity - 1;
            ServiceViewModel.this.mBcmController.setWiperSensitivity(i, true);
            ServiceViewModel.this.mIcmController.setWiperSensitivity(i);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onTrunkStateChanged(int state) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_TRUNK_INT, Integer.valueOf(state));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onElcTrunkStateChanged(final int state) {
            if (ServiceViewModel.this.mCarConfigHelper.isSupportElcTrunk()) {
                QuickSettingManager.getInstance().onSignalCallbackSingleThread(QuickSettingConstants.KEY_CONTROL_TRUNK_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$BcmCallbackImpl$6O2UYsh3WL6rBs12gAyxmTiv0PE
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        TrunkState convertTrunkStateToQuickSetting;
                        convertTrunkStateToQuickSetting = WindowDoorBaseViewModel.convertTrunkStateToQuickSetting(state);
                        return convertTrunkStateToQuickSetting;
                    }
                });
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onHighSpdCloseWinChanged(boolean enabled) {
            ServiceViewModel serviceViewModel = ServiceViewModel.this;
            serviceViewModel.checkHighSpdCloseWinFunction(enabled, enabled ? serviceViewModel.mVcuController.getCarSpeed() : 0.0f);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onBcmBrkPedalStateChanged(int status) {
            if (ServiceViewModel.this.mCarConfigHelper.isSupportMcuKeyOpenFailed()) {
                return;
            }
            if (ServiceViewModel.this.mVcuController == null || !ServiceViewModel.this.mVcuController.isEvSysReady()) {
                if (ServiceViewModel.this.mIsBcmReadyEnabled || ServiceViewModel.this.mBcmController.getBcmReadyEnableState() == 1) {
                    ServiceViewModel.this.dismissReadyDisableDialog();
                    ServiceViewModel.this.mPreBrkPedalStatus = status;
                    return;
                }
                LogUtils.i(ServiceViewModel.TAG, "onBcmBrkPedalStateChanged: " + status + ", mPreBrkPedalStatus: " + ServiceViewModel.this.mPreBrkPedalStatus, false);
                ServiceViewModel.this.checkBrkPedalStatus(status);
                return;
            }
            ServiceViewModel.this.mPreBrkPedalStatus = status;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onBcmReadyStateChanged(int status) {
            if (ServiceViewModel.this.mCarConfigHelper.isSupportMcuKeyOpenFailed()) {
                return;
            }
            if (status == 1) {
                ServiceViewModel.this.mIsBcmReadyEnabled = true;
                LogUtils.i(ServiceViewModel.TAG, "Bcm ready enable, dismiss [ready disable] dialog", false);
                ServiceViewModel.this.dismissReadyDisableDialog();
                return;
            }
            ServiceViewModel.this.mIsBcmReadyEnabled = false;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onUnlockReqSrcChanged(int reqSrc) {
            if (ServiceViewModel.this.mCarConfigHelper.isSupportMcuKeyOpenFailed()) {
                return;
            }
            LogUtils.i(ServiceViewModel.TAG, "onUnlockReqSrcChanged: " + reqSrc, false);
            if (reqSrc == 3) {
                ServiceViewModel.this.mCurrUnlockReqRes = 1;
                ServiceViewModel.this.mFunctionModel.setUnlockReqSrc(ServiceViewModel.this.mCurrUnlockReqRes);
            } else if (reqSrc != 4) {
            } else {
                ServiceViewModel.this.mCurrUnlockReqRes = 2;
                ServiceViewModel.this.mFunctionModel.setUnlockReqSrc(ServiceViewModel.this.mCurrUnlockReqRes);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLeftSdcSysRunningStateChanged(int state) {
            CabinSmartControl.getInstance().updateSdcRunningState(true, state);
            if (ServiceViewModel.this.mSsDoorStateCallback != null) {
                ServiceViewModel.this.mSsDoorStateCallback.onLeftSdcSysStateChanged(state);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRightSdcSysRunningStateChanged(int state) {
            CabinSmartControl.getInstance().updateSdcRunningState(false, state);
            if (ServiceViewModel.this.mSsDoorStateCallback != null) {
                ServiceViewModel.this.mSsDoorStateCallback.onRightSdcSysStateChanged(state);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLeftSdcDoorPosChanged(int pos) {
            if (ServiceViewModel.this.mSsDoorStateCallback != null) {
                ServiceViewModel.this.mSsDoorStateCallback.onLeftSdcDoorPosChanged(pos);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRightSdcDoorPosChanged(int pos) {
            if (ServiceViewModel.this.mSsDoorStateCallback != null) {
                ServiceViewModel.this.mSsDoorStateCallback.onRightSdcDoorPosChanged(pos);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onSunShadeInitializationChanged(boolean initialized) {
            if (!ServiceViewModel.this.isSunShadeInitialized && initialized) {
                NotificationHelper.getInstance().showToast(R.string.sun_shade_initialized);
            }
            ServiceViewModel.this.isSunShadeInitialized = initialized;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAsMaintainModeChanged(int mode) {
            LogUtils.d(ServiceViewModel.TAG, "onAsMaintainModeChanged: previous mode: " + ServiceViewModel.this.mPreviousAsMode + ", current mode: " + mode);
            if (ServiceViewModel.this.mPreviousAsMode == 2 && mode != 2) {
                ServiceViewModel.this.mVcuSmartControl.resumeDriveMode(ServiceViewModel.this.mDataSync.getDriveMode());
            }
            ServiceViewModel.this.mPreviousAsMode = mode;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onAsEasyLoadingModeChanged(boolean mode) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_EASY_LOAD, Boolean.valueOf(ServiceViewModel.this.mBcmController.getEasyLoadingSwitch()));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onLeftChildLockChanged(boolean locked) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_CHILD_lOCK_LEFT, Boolean.valueOf(ServiceViewModel.this.mBcmController.getChildLeftLock()));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRightChildLockChanged(boolean locked) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_CHILD_LOCK_RIGHT, Boolean.valueOf(ServiceViewModel.this.mBcmController.getChildRightLock()));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onChildLockChanged(int state) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_CHILD_lOCK_LEFT, Boolean.valueOf(ServiceViewModel.this.mBcmController.getChildLeftLock()));
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_CHILD_LOCK_RIGHT, Boolean.valueOf(ServiceViewModel.this.mBcmController.getChildRightLock()));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onTtmLampConnectStatusChanged(boolean connect) {
            if (connect) {
                ControlPanelManager.getInstance().show(GlobalConstant.ACTION.ACTION_SHOW_TRAILER_MODE_CONTROL_PANEL, 2048);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEpbDialog() {
        LogUtils.i(TAG, "showEpbDialog: mIsEpbDialogShow=" + this.mIsEpbDialogShow, false);
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$UVe2umkHtT3ntU5xCj7pcwN9T0Q
            @Override // java.lang.Runnable
            public final void run() {
                ServiceViewModel.this.lambda$showEpbDialog$15$ServiceViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$showEpbDialog$15$ServiceViewModel() {
        if (this.mIsEpbDialogShow) {
            return;
        }
        XDialog xDialog = new XDialog(App.getInstance(), R.style.XDialogView_Large);
        xDialog.setTitle(R.string.epb_title).setMessage(R.string.emergency_epb_desc).setIcon(R.drawable.popup_img_epb_parking).setPositiveButton(ResUtils.getString(R.string.btn_ok), (XDialogInterface.OnClickListener) null);
        xDialog.setSystemDialog(2008);
        xDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$aY3lT95zaQcjPGpAteb6UEt8znY
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ServiceViewModel.this.lambda$null$14$ServiceViewModel(dialogInterface);
            }
        });
        xDialog.show();
        this.mIsEpbDialogShow = true;
    }

    public /* synthetic */ void lambda$null$14$ServiceViewModel(DialogInterface dialog) {
        dismissEpbDialog();
    }

    public void dismissEpbDialog() {
        LogUtils.i(TAG, "dismissEpbDialog", false);
        this.mIsEpbDialogShow = false;
    }

    /* loaded from: classes2.dex */
    private class VcuCallbackImpl implements IVcuController.Callback {
        private VcuCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onDriveModeChanged(int mode) {
            CarControl.System.putInt(App.getInstance().getContentResolver(), CarControl.System.DRIVE_MODE, mode);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_DRIVE_MODE_INT, Integer.valueOf(mode));
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_POWER_RECYCLE_INT, Integer.valueOf(ServiceViewModel.this.mVcuController.getEnergyRecycleGrade()));
            if (mode != 5) {
                if (mode != 7) {
                    return;
                }
            } else if (BaseFeatureOption.getInstance().shouldForceTurnOnAvhForXpedal()) {
                LogUtils.i(ServiceViewModel.TAG, "Drive mode changed to ECO+ mode, so turn on AVH", false);
                ServiceViewModel.this.mVcuSmartControl.onEcoPlusMode(true);
            }
            FunctionModel.getInstance().setSnowModeEnergyCache(-1);
            if (ServiceViewModel.this.mCarConfigHelper.isSupportSnowMode()) {
                ServiceViewModel.this.mVcuController.setSnowMode(false);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onXSportDriveModeChanged(int mode) {
            if (ServiceViewModel.this.mPreviousXsportMode == 1 && mode != 1) {
                ServiceViewModel.this.mChassisSmartControl.checkAvhCondition();
            }
            ServiceViewModel.this.mPreviousXsportMode = mode;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onRecycleGradeChanged(int grade) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_POWER_RECYCLE_INT, Integer.valueOf(grade));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onGearChanged(int gear) {
            LogUtils.i(ServiceViewModel.TAG, "onGearChanged: previous gear level " + ServiceViewModel.this.mPreviousGear + ", current gear level " + gear, false);
            if (ServiceViewModel.this.mPreviousGear == 0) {
                ServiceViewModel.this.mPreviousGear = gear;
            }
            IMirrorViewModel iMirrorViewModel = (IMirrorViewModel) ViewModelManager.getInstance().getViewModelImpl(IMirrorViewModel.class);
            if (ServiceViewModel.this.mCarConfigHelper.isSupportMirrorMemory() && ServiceViewModel.this.mDataSync.mIsDataSynced && ((ServiceViewModel.this.mCarConfigHelper.isSopStage() && !iMirrorViewModel.checkCurPos()) || ServiceViewModel.this.mBcmController.getReverseMirrorMode() != 0)) {
                if (gear == 3) {
                    ServiceViewModel.this.mBgHandler.postDelayed(ServiceViewModel.this.mGearChangedRunnable, 500L);
                } else {
                    ServiceViewModel.this.mBgHandler.removeCallbacks(ServiceViewModel.this.mGearChangedRunnable);
                    if (ServiceViewModel.this.mPreviousGear == 3 && gear != 0) {
                        CabinSmartControl.getInstance().handleReverseMirror(false);
                        if (ServiceViewModel.this.mIsLocalIgOn) {
                            CabinSmartControl.getInstance().resetRecoverMirrorFlag();
                        }
                    }
                }
            }
            ServiceViewModel.this.handlePGearMenu(gear);
            if (ServiceViewModel.this.mPreviousGear != 4 || gear == 4) {
                if (ServiceViewModel.this.mPreviousGear != 4 && gear == 4) {
                    if (ServiceViewModel.this.mCarConfigHelper.isSupportCtrlBonnet() && ServiceViewModel.this.mBcmController.getBonnetState() != 0) {
                        SpeechHelper.getInstance().stop();
                    }
                    if (ServiceViewModel.this.mBgHandler != null) {
                        ServiceViewModel.this.mBgHandler.removeCallbacks(ServiceViewModel.this.mCheckBonnetTask);
                    }
                    if (ServiceViewModel.this.mCarConfigHelper.isSupportXPilotSafeExam()) {
                        ServiceViewModel.this.handleXPilotWhenEnterParkGear();
                    }
                    if (BaseFeatureOption.getInstance().isSupportCarStatusBIUpload()) {
                        CarStatusUploadHelper.getInstance().uploadResumeStatus();
                    }
                }
            } else {
                if (gear != 0 && ServiceViewModel.this.mCarConfigHelper.isSupportCtrlBonnet() && ServiceViewModel.this.mBcmController.getBonnetState() != 0 && ServiceViewModel.this.mBgHandler != null) {
                    ServiceViewModel.this.mBgHandler.postDelayed(ServiceViewModel.this.mCheckBonnetTask, 500L);
                }
                if (ServiceViewModel.this.mBcmController.getWiperRepairMode()) {
                    ServiceViewModel.this.mBcmController.setWiperRepairMode(false);
                }
                if (ServiceViewModel.this.mBcmController.getRearWiperRepairMode()) {
                    ServiceViewModel.this.mBcmController.setRearWiperRepairMode(false);
                }
            }
            if ((ServiceViewModel.this.mPreviousGear == 3 || gear == 3) && ControlPanelManager.getInstance().isShow(GlobalConstant.ACTION.ACTION_SHOW_MIRROR_CONTROL_PANEL)) {
                ControlPanelManager.getInstance().dismiss(GlobalConstant.ACTION.ACTION_SHOW_MIRROR_CONTROL_PANEL);
            }
            if (BaseFeatureOption.getInstance().isSupportNapa() && ServiceViewModel.this.mPreviousGear == 3 && gear != 3) {
                iMirrorViewModel.showMirrorCtrlPanel(false);
            }
            ServiceViewModel.this.mPreviousGear = gear;
            if (ServiceViewModel.this.mCarConfigHelper.isSupportEpbWarning()) {
                handleEpb(gear);
            }
            if (!ServiceViewModel.this.mCarConfigHelper.isSupportSdc() || ServiceViewModel.this.mSsDoorStateCallback == null) {
                return;
            }
            ServiceViewModel.this.mSsDoorStateCallback.onVcuGearChanged(gear);
        }

        private void handleEpb(int gear) {
            if (ServiceViewModel.this.mCarConfigHelper.isSupportEpbWarning()) {
                if (gear == 4) {
                    ServiceViewModel.this.mBgHandler.removeCallbacks(ServiceViewModel.this.mEpbRunnable);
                    ServiceViewModel.this.mBgHandler.postDelayed(ServiceViewModel.this.mEpbRunnable, 500L);
                    return;
                }
                ServiceViewModel.this.mBgHandler.removeCallbacks(ServiceViewModel.this.mEpbRunnable);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onElecPercentChanged(int percent) {
            CarControl.System.putInt(App.getInstance().getContentResolver(), CarControl.System.BATTERY_PERCENT, percent);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onAvailableMileageChanged(int mileage) {
            ServiceViewModel.this.mVcuSmartControl.onRemainMileageChanged(mileage);
            CarControl.System.putInt(App.getInstance().getContentResolver(), CarControl.System.AVAILABLE_DISTANCE, mileage);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onChargeStatusChanged(int status) {
            ServiceViewModel.this.mVcuSmartControl.onChargeStatusChanged(status);
            CarControl.System.putInt(App.getInstance().getContentResolver(), CarControl.System.CHARGE_STATUS, status);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onSnowModeChanged(boolean enable) {
            ServiceViewModel.this.mVcuSmartControl.onSnowModeChange(enable);
        }

        private String getSoundEffectPath(int id, String defaultPath) {
            SoundEffectResource activeSoundEffectResource;
            String[] resPath;
            SoundResourceManager soundResManager = XuiClientWrapper.getInstance().getSoundResManager();
            return (soundResManager == null || (activeSoundEffectResource = soundResManager.getActiveSoundEffectResource(id)) == null || (resPath = activeSoundEffectResource.getResPath()) == null || resPath.length == 0) ? defaultPath : resPath[0];
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onEvSysReady(int state) {
            ServiceViewModel.this.mHvacSmartControl.executeHvacDrvTempSync();
            if (state != 2) {
                return;
            }
            if (ServiceViewModel.this.mVcuController.getCarSpeed() < 1.0f) {
                SoundHelper.play(getSoundEffectPath(7, SoundHelper.PATH_SYS_READY), true, false);
            }
            ServiceViewModel.this.mChassisSmartControl.checkEspCondition();
            ServiceViewModel.this.mChassisSmartControl.checkAvhCondition();
            if (ServiceViewModel.this.mCarConfigHelper.isSupportMcuKeyOpenFailed()) {
                return;
            }
            ServiceViewModel.this.dismissReadyDisableDialog();
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onRawCarSpeedChanged(float speed) {
            ServiceViewModel serviceViewModel = ServiceViewModel.this;
            serviceViewModel.checkHighSpdCloseWinFunction(serviceViewModel.mBcmController.isHighSpdCloseWinEnabled(), speed);
            if (ServiceViewModel.this.mCarConfigHelper.isSupportEpbWarning() && ServiceViewModel.this.mCarConfigHelper.isSupportIBoosterSignal()) {
                ServiceViewModel.this.checkBrakeFluid(speed);
            }
            if (ServiceViewModel.this.mIsLocalIgOn) {
                CabinSmartControl.getInstance().checkRecoverMirror(speed);
            }
            if (speed >= 40.0f && ServiceViewModel.this.mCarConfigHelper.isSupportDsmCamera() && BaseFeatureOption.getInstance().isSupportForceOpenDsm()) {
                LogUtils.i(ServiceViewModel.TAG, "Force open DSM after speed over 40km/h!");
                ServiceViewModel.this.mScuController.setDsmSw(true);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
        public void onAccPedalStatusChanged(int value) {
            int gearLevel = ServiceViewModel.this.mVcuController.getGearLevel();
            boolean z = (gearLevel == 4 || gearLevel == 0) ? false : true;
            boolean z2 = ServiceViewModel.this.mVcuController.getCarSpeed() == 0.0f;
            if (z && z2 && value > ServiceViewModel.this.mCarConfigHelper.getEpbAccPedalThreshold()) {
                LogUtils.i(ServiceViewModel.TAG, "onAccPedalStatusChanged: value=" + value, false);
                boolean z3 = ServiceViewModel.this.mMcuController.getIgStatusFromMcu() == 1;
                boolean z4 = ServiceViewModel.this.mEspController.getApbSystemDisplayMessage() == 0;
                boolean z5 = ServiceViewModel.this.mEspController.getApbSystemStatus() == 2;
                boolean isDrvDoorClose = ServiceViewModel.this.isDrvDoorClose();
                boolean z6 = ServiceViewModel.this.mEspController.getEpbDriverOffWarningMsg() != 0;
                LogUtils.i(ServiceViewModel.TAG, "onAccPedalStatusChanged: isIgOn=" + z3 + ", isEpbMessageOff=" + z4 + ", isEpbClosed=" + z5 + ", isDrvDoorClosed=" + isDrvDoorClose + ", gearLevel=" + gearLevel + ", isEpbWarning=" + z6, false);
                if (z3 && z4 && z5 && isDrvDoorClose && z6) {
                    NotificationHelper.showOsd(App.getInstance(), R.string.epb_title, R.drawable.ic_mid_osdparking, R.string.release_epb_desc);
                }
            }
        }
    }

    protected void handlePGearMenu(int gear) {
        int i = this.mPreviousGear;
        if (i != 0 && i != 4 && gear == 4) {
            handleDelayShowPGearMenu();
            checkTelescopeMrr();
        } else if (gear != 4) {
            this.mBgHandler.removeCallbacks(this.mDelayShowPGearMenuTask);
            PopPanelManager.getInstance().dismiss("p_gear_menu");
            dismissTelescopeMrrDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$new$16() {
        if (PopPanelManager.getInstance().checkConflictWithNapa("p_gear_menu")) {
            return;
        }
        PopPanelManager.getInstance().show("p_gear_menu", 1, 2);
    }

    public /* synthetic */ void lambda$new$17$ServiceViewModel() {
        if (this.mCarConfigHelper.isSupportMrrGeoFence() && IpcRouterService.getMrrGeoFenceStatus()) {
            XpilotSmartControl.getInstance().enterTelescopeRange();
        }
    }

    private void handleDelayShowPGearMenu() {
        this.mBgHandler.removeCallbacks(this.mDelayShowPGearMenuTask);
        if (this.mVcuController.getParkDropdownMenuEnable()) {
            this.mBgHandler.postDelayed(this.mDelayShowPGearMenuTask, 1000L);
        }
    }

    private void checkTelescopeMrr() {
        Handler handler = this.mBgHandler;
        if (handler != null) {
            handler.postDelayed(this.mCheckTelescopeTask, 200L);
        }
    }

    private void dismissTelescopeMrrDialog() {
        XpilotSmartControl.getInstance().dismissCloseMrrDialog();
    }

    protected void handleXPilotWhenEnterParkGear() {
        if (this.mCarConfigHelper.isSupportCNgp() && this.mCarConfigHelper.isSupportXPilotSafeExam() && (this.mDataSync.isGuest() || !this.mDataSync.getCngpSafeExamResult())) {
            int cityNgpState = this.mXpuController.getCityNgpState();
            LogUtils.i(TAG, "Switch to Park gear, and current CNGP state: " + cityNgpState);
            if (cityNgpState == 1) {
                LogUtils.i(TAG, "Turn off CNGP by force since user logout,or user not pass exam");
                this.mXpuController.setCityNgpSw(false);
            }
        }
        if (this.mCarConfigHelper.isSupportNgp() && this.mCarConfigHelper.isSupportXPilotSafeExam() && (this.mDataSync.isGuest() || !this.mDataSync.getNgpSafeExamResult())) {
            int ngpState = this.mScuController.getNgpState();
            LogUtils.i(TAG, "Switch to Park gear, and current NGP state: " + ngpState);
            if (ngpState == 1) {
                LogUtils.i(TAG, "Turn off NGP by force since user logout,or user not pass exam");
                this.mScuController.setNgpEnable(false);
                if (this.mCarConfigHelper.isSupportCNgp()) {
                    this.mXpuController.setCityNgpSw(false);
                }
            }
        }
        boolean isXngpExamNotPass = this.mCarConfigHelper.isSupportXNgp() ? isXngpExamNotPass() : isLccExamNotPass();
        if (this.mCarConfigHelper.isSupportLcc() && this.mCarConfigHelper.isSupportXPilotSafeExam() && (this.mDataSync.isGuest() || isXngpExamNotPass)) {
            int lccState = this.mScuController.getLccState();
            LogUtils.i(TAG, "Switch to Park gear, and current LCC state: " + lccState);
            if (lccState == 1) {
                LogUtils.i(TAG, "Turn off LCC by force since user logout,or user not pass exam");
                this.mScuController.setLccState(false);
                this.mScuController.setAlcState(false);
                if (this.mCarConfigHelper.isSupportNgp()) {
                    this.mScuController.setNgpEnable(false);
                }
                if (this.mCarConfigHelper.isSupportCNgp()) {
                    this.mXpuController.setCityNgpSw(false);
                }
            }
        }
        if (this.mCarConfigHelper.isSupportMemPark() && this.mCarConfigHelper.isSupportXPilotSafeExam() && (this.mDataSync.isGuest() || isMemExamNotPass())) {
            int memoryParkSw = this.mScuController.getMemoryParkSw();
            LogUtils.i(TAG, "Switch to Park gear, and current MemPark state: " + memoryParkSw);
            if (memoryParkSw == 1) {
                LogUtils.i(TAG, "Turn off MemPark by force since user logout,or user not pass exam");
                this.mScuController.setMemoryParkSw(false);
            }
        }
        if (this.mCarConfigHelper.isSupportAutoPark() && this.mCarConfigHelper.isSupportXPilotSafeExam() && (this.mDataSync.isGuest() || !this.mDataSync.getApaSafeExamResult())) {
            int autoParkSw = this.mScuController.getAutoParkSw();
            LogUtils.i(TAG, "Switch to Park gear, and current APA state: " + autoParkSw);
            if (autoParkSw == 1) {
                LogUtils.i(TAG, "Turn off Apa by force since user logout");
                this.mScuController.setAutoParkSw(false);
                if (this.mCarConfigHelper.isSupportMemPark()) {
                    this.mScuController.setMemoryParkSw(false);
                }
            }
        }
        if (AccountViewModel.PENDING_FOR_CLOSE_SMART_DRIVE_SCORE) {
            AccountViewModel.PENDING_FOR_CLOSE_SMART_DRIVE_SCORE = false;
            updateEnterPGear();
        }
    }

    private boolean isMemExamNotPass() {
        if (this.mCarConfigHelper.isSupportLidar() && BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
            return !this.mDataSync.getMemParkSafeExamResult();
        }
        return (this.mDataSync.getMemParkSafeExamResult() || this.mDataSync.getSuperVpaSafeExamResult()) ? false : true;
    }

    private boolean isLccExamNotPass() {
        if (this.mCarConfigHelper.isSupportLidar() && BaseFeatureOption.getInstance().isSupportLidarSafeExam()) {
            return !this.mDataSync.getSuperLccSafeExamResult();
        }
        return (this.mDataSync.getLccSafeExamResult() || this.mDataSync.getSuperLccSafeExamResult()) ? false : true;
    }

    private boolean isXngpExamNotPass() {
        return !this.mDataSync.getXngpSafeExamResult();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleRemindWarningState(int state) {
        LogUtils.i(TAG, "handleRemindWarningState: " + state);
        if (state == 1 && this.mFunctionModel.isRemindWarningAllowed()) {
            LogUtils.i(TAG, "sendMessageToMessageCenter");
            NotificationHelper.getInstance().sendMessageToMessageCenter(NotificationHelper.SCENE_REMIND_WARNING, ResUtils.getString(R.string.remind_warning_title), ResUtils.getString(R.string.remind_warning_content), ResUtils.getString(R.string.remind_warning_tts), ResUtils.getString(R.string.remind_warning_wake_words), null, ResUtils.getString(R.string.remind_warning_btn_title), false, 0L, null);
            this.mFunctionModel.setRemindWarningTs(System.currentTimeMillis());
        }
    }

    /* loaded from: classes2.dex */
    private class EspCallbackImpl implements IEspController.Callback {
        private EspCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
        public void onEspChanged(boolean enabled) {
            if (App.isMainProcess()) {
                if (ServiceViewModel.this.mVcuController.isEvSysReady()) {
                    if (enabled && !FunctionModel.getInstance().getLastEspMode()) {
                        NotificationHelper.getInstance().showToast(R.string.esp_auto_on_hint);
                    }
                    if (enabled != ServiceViewModel.this.mEspController.getEspSw()) {
                        ServiceViewModel.this.mEspController.setEspSw(enabled);
                    }
                }
                FunctionModel.getInstance().setLastEspMode(enabled);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
        public void onEspSwChanged(boolean enabled) {
            if (ServiceViewModel.this.mCarConfigHelper.isSupportUnity3D() && App.isMainProcess()) {
                boolean espFault = ServiceViewModel.this.mEspController.getEspFault();
                LogUtils.i(ServiceViewModel.TAG, "onEspSwChanged: isEspFault=" + espFault + ", enable=" + enabled, false);
                if (espFault) {
                    return;
                }
                CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ESP_SW_NOTIFY, true);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
        public void onEspFaultChanged(boolean isFault) {
            if (isFault) {
                return;
            }
            ServiceViewModel.this.mChassisSmartControl.checkEspCondition();
            if (ServiceViewModel.this.mVcuController.isEvSysReady()) {
                return;
            }
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.ESP_SW_NOTIFY, true);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
        public void onAvhStatusChanged(int status) {
            if (App.isMainProcess()) {
                LogUtils.i(ServiceViewModel.TAG, "onAvhChanged to " + status + ", avhSw=" + ServiceViewModel.this.mEspController.getAvhSw(), false);
                if (ServiceViewModel.this.mChassisSmartControl.isAvhPrepared(false)) {
                    QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_AVH_BOOL, Boolean.valueOf(ChassisSmartControl.parseAvhStatus(status)));
                }
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
        public void onAvhSwChanged(boolean enabled) {
            ServiceViewModel.this.mChassisSmartControl.checkAvhCondition();
            CarControl.PrivateControl.putBool(App.getInstance().getContentResolver(), CarControl.PrivateControl.AVH_SW_NOTIFY, true);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_AVH_BOOL, Boolean.valueOf(enabled));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
        public void onAvhFaultChanged(boolean isFault) {
            if (!isFault) {
                ServiceViewModel.this.mChassisSmartControl.checkAvhCondition();
                QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_AVH_BOOL, Boolean.valueOf(ServiceViewModel.this.mEspController.getAvhSw()));
                return;
            }
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_AVH_BOOL, false);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
        public void onHdcChanged(boolean enabled) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HDC_BOOL, Boolean.valueOf(enabled));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
        public void onHdcFaultChanged(boolean isFault) {
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HDC_BOOL, Boolean.valueOf(!isFault && ServiceViewModel.this.mEspController.getHdc()));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IEspController.Callback
        public void onHbcRequestStatusChanged(int status) {
            LogUtils.i(ServiceViewModel.TAG, "onHbcRequestStatusChanged: status=" + status, false);
            if (status == 1 && ServiceViewModel.this.mCarConfigHelper.isSupportEpbWarning()) {
                boolean z = ServiceViewModel.this.mMcuController.getIgStatusFromMcu() == 1;
                boolean isIbtFault = ServiceViewModel.this.mDiagnosticController.isIbtFault();
                LogUtils.i(ServiceViewModel.TAG, "onHbcRequestStatusChanged: isIgOn=" + z + ", isIbtFault=" + isIbtFault, false);
                if (z && isIbtFault) {
                    ServiceViewModel.this.showEpbDialog();
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    private class MsmCallbackImpl implements IMsmController.Callback {
        private MsmCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
        public void onPsnOnSeatChanged(boolean occupied) {
            CarControl.System.putBool(App.getInstance().getContentResolver(), CarControl.System.PSN_OCCUPIED, occupied);
            ServiceViewModel.this.mHvacSmartControl.invokeHvacSingleMode(ServiceViewModel.this.isLocalIgOn());
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
        public void onDrvBeltWaringChanged(int mode) {
            if (mode == 0) {
                ServiceViewModel.this.mChassisSmartControl.checkAvhCondition();
            }
            if (mode == 0 && ServiceViewModel.this.mIsDrvSrsUnbelted) {
                ServiceViewModel.this.mIsDrvSrsUnbelted = false;
            } else if (mode != 1 || ServiceViewModel.this.mIsDrvSrsUnbelted) {
            } else {
                ServiceViewModel.this.mIsDrvSrsUnbelted = true;
                ServiceViewModel.this.checkWelcomeModeGoOff();
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
        public void onPsnBeltWaringChanged(int mode) {
            if (ServiceViewModel.this.mCarConfigHelper.isSupportPsnWelcomeMode()) {
                if (mode == 0 && ServiceViewModel.this.mIsPsnSrsUnbelted) {
                    ServiceViewModel.this.mIsPsnSrsUnbelted = false;
                } else if (mode != 1 || ServiceViewModel.this.mIsPsnSrsUnbelted) {
                } else {
                    ServiceViewModel.this.mIsPsnSrsUnbelted = true;
                    ServiceViewModel.this.checkPsnWelcomeModeGoOff();
                }
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
        public void onDrvSeatLumberSwitchPressStateChanged(int status) {
            LogUtils.i(ServiceViewModel.TAG, "onDrvSeatLumberSwitchPressStateChanged status:" + status, false);
            if (status == 0) {
                return;
            }
            int driverSeatLumberSwitchMode = ServiceViewModel.this.mMsmController.getDriverSeatLumberSwitchMode();
            if (driverSeatLumberSwitchMode == 0) {
                NotificationHelper.showOsd(App.getInstance(), R.string.seat_lumbar_adjustment, R.drawable.ic_mid_osddrvlumbaradjustment, R.string.seat_lumbar_adjustment_description);
            } else if (driverSeatLumberSwitchMode != 1) {
            } else {
                NotificationHelper.showOsd(App.getInstance(), R.string.seat_legreset_adjustment, R.drawable.ic_mid_osddrvlegrestadjustment, R.string.seat_legreset_adjustment_description);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
        public void onPsnSeatLumberSwitchPressStateChanged(int status) {
            LogUtils.i(ServiceViewModel.TAG, "onPsnSeatLumberSwitchPressStateChanged status:" + status, false);
            if (status == 0) {
                return;
            }
            int passengerSeatLumberSwitchMode = ServiceViewModel.this.mMsmController.getPassengerSeatLumberSwitchMode();
            if (passengerSeatLumberSwitchMode == 0) {
                NotificationHelper.showOsd(App.getInstance(), R.string.seat_lumbar_adjustment, R.drawable.ic_mid_osdpsnlumbaradjustment, R.string.seat_lumbar_adjustment_description, 1);
            } else if (passengerSeatLumberSwitchMode != 1) {
            } else {
                NotificationHelper.showOsd(App.getInstance(), R.string.seat_legreset_adjustment, R.drawable.ic_mid_osdpsnlegrestadjustment, R.string.seat_legreset_adjustment_description, 1);
            }
        }
    }

    /* loaded from: classes2.dex */
    private class HvacCallbackImpl implements IHvacController.Callback {
        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacFanSpeedAutoChanged(boolean enable) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacNIVentChanged(boolean enabled) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacNewFreshSwitchChanged(boolean enabled) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacRearAutoModeChanged(boolean enabled) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacRearPowerModeChanged(boolean enabled) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacRearTempDrvChanged(float temp) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacRearTempPsnChanged(float temp) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacRearWindSpeedLevelChanged(int level) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacThirdRowTempChanged(float temp) {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacThirdRowWindBlowModeChanged(int mode) {
        }

        private HvacCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacPowerModeChanged(boolean enabled) {
            ServiceViewModel.this.updateHvacStatus();
            if (enabled) {
                ServiceViewModel.this.mHvacSmartControl.executeHvacDrvTempSync();
                ServiceViewModel.this.mHvacSmartControl.initHvacSmartMode();
            } else {
                ServiceViewModel.this.mHvacSmartControl.resetSmartMode();
            }
            ServiceViewModel.this.mHvacSmartControl.invokeHvacSingleMode(ServiceViewModel.this.isLocalIgOn());
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacAutoModeChanged(boolean enabled) {
            CarControl.System.putBool(App.getInstance().getContentResolver(), CarControl.System.HVAC_AUTO, enabled);
            ServiceViewModel.this.mHvacSmartControl.onHvacAutoModeChange(Boolean.valueOf(enabled));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacTempDrvChanged(float temp) {
            CarControl.System.putFloat(App.getInstance().getContentResolver(), CarControl.System.HVAC_DRV_TEMP, temp);
            ServiceViewModel.this.mIcmController.setIcmDrvTemp(temp);
            ServiceViewModel.this.mHvacSmartControl.onHvacDrvTempChange(temp);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HVAC_DRIVER_TEMP_FLOAT, Float.valueOf(temp));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacTempPsnChanged(float temp) {
            CarControl.System.putFloat(App.getInstance().getContentResolver(), CarControl.System.HVAC_PSN_TEMP, temp);
            ServiceViewModel.this.mHvacSmartControl.onHvacPsnTempChange(temp);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacDrvSyncModeChanged(boolean enabled) {
            CarControl.System.putBool(App.getInstance().getContentResolver(), CarControl.System.HVAC_DRV_SYNC, enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacPsnSyncModeChanged(boolean enabled) {
            CarControl.System.putBool(App.getInstance().getContentResolver(), CarControl.System.HVAC_PSN_SYNC, enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacWindBlowModeChanged(int mode) {
            LogUtils.d(ServiceViewModel.TAG, "onHvacWindBlowModeChanged:" + mode);
            ServiceViewModel.this.mHvacSmartControl.onWindBlowModeChanged(mode);
            ServiceViewModel.this.mHvacSmartControl.invokeHvacSingleMode(ServiceViewModel.this.isLocalIgOn());
            ServiceViewModel.this.mIcmController.setIcmWindBlowMode(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacRearWindBlowModeChanged(int mode) {
            LogUtils.d(ServiceViewModel.TAG, "onHvacRearWindBlowModeChanged:" + mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacWindSpeedLevelChanged(int level) {
            LogUtils.d(ServiceViewModel.TAG, "onHvacWindSpeedLevelChanged:" + level);
            CarControl.System.putInt(App.getInstance().getContentResolver(), CarControl.System.HVAC_WIND_LEVEL, level);
            ServiceViewModel.this.mHvacSmartControl.onWindSpeedLevelChange(level);
            ServiceViewModel.this.mIcmController.setIcmWindLevel(level);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HVAC_WIND_SPEED_INT, Integer.valueOf(level));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacCirculationModeChanged(int mode) {
            ServiceViewModel.this.mHvacSmartControl.onCirculationModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacFrontDefrostChanged(boolean enabled) {
            CarControl.System.putBool(App.getInstance().getContentResolver(), CarControl.System.HVAC_FRONT_DEFROST, enabled);
            ServiceViewModel.this.mHvacSmartControl.onFrontDefrostChange(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacInnerPM25Changed(int value) {
            if (ServiceViewModel.this.isLocalIgOn()) {
                ServiceViewModel.this.mHvacSmartControl.onHvacInnerAqChanged(value);
            }
            CarControl.System.putInt(App.getInstance().getContentResolver(), CarControl.System.HVAC_PM25, value);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacWindModEconLourChanged(int value) {
            LogUtils.d(ServiceViewModel.TAG, "onHvacWindModeColour: " + value);
            CarControl.System.putInt(App.getInstance().getContentResolver(), CarControl.System.HVAC_WIND_MODE_COLOR, value);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacExternalTempChanged(float temp) {
            CarControl.System.putFloat(App.getInstance().getContentResolver(), CarControl.System.HVAC_OUT_TEMP, temp);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacEconModeChange(int status) {
            ServiceViewModel.this.mHvacSmartControl.onHvacEconModeChange(status);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacAqsModeChange(int status) {
            ServiceViewModel.this.mHvacSmartControl.onAqsModeChange(status == 1);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacEavDrvLeftVPosChanged(int pos) {
            if (pos == 14) {
                ServiceViewModel.this.mHvacSmartControl.stopHvacSmartMode();
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacEavDrvRightVPosChanged(int pos) {
            if (pos == 14) {
                ServiceViewModel.this.mHvacSmartControl.stopHvacSmartMode();
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacEavPsnLeftVPosChanged(int pos) {
            if (pos == 14) {
                ServiceViewModel.this.mHvacSmartControl.stopHvacSmartMode();
            } else {
                ServiceViewModel.this.mHvacController.setHvacSingleModeActive(false);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacEavPsnRightVPosChanged(int pos) {
            if (pos == 14) {
                ServiceViewModel.this.mHvacSmartControl.stopHvacSmartMode();
            } else {
                ServiceViewModel.this.mHvacController.setHvacSingleModeActive(false);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacAcHeatNatureChanged(int mode) {
            ServiceViewModel.this.mHvacSmartControl.onAcHeatNatureModeChange(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacAirProtectModeChanged(int mode) {
            ServiceViewModel.this.mHvacSmartControl.onAirAutoProtectModeChanged(HvacAirAutoProtect.fromValue(mode));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacRapidCoolingChanged(boolean enable) {
            ServiceViewModel.this.mHvacSmartControl.onHvacRapidCoolingChanged(enable);
            ServiceViewModel.this.mHvacSmartControl.invokeHvacSingleMode(ServiceViewModel.this.isLocalIgOn());
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_QUICK_COOL_BOOL, Boolean.valueOf(enable));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacDeodorantChanged(boolean enable) {
            ServiceViewModel.this.mHvacSmartControl.onHvacDeodorantChanged(enable);
            ServiceViewModel.this.mHvacSmartControl.invokeHvacSingleMode(ServiceViewModel.this.isLocalIgOn());
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_DEODORANT_BOOL, Boolean.valueOf(enable));
            if (!enable && !ServiceViewModel.this.mDeodorantNotToast) {
                NotificationHelper.getInstance().showToastLong(R.string.hvac_deodorant_exit_tip, true, "hvac");
            }
            ServiceViewModel.this.mDeodorantNotToast = false;
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacRapidHeatChanged(boolean enable) {
            ServiceViewModel.this.mHvacSmartControl.onHvacRapidHeatChanged(enable);
            ServiceViewModel.this.mHvacSmartControl.invokeHvacSingleMode(ServiceViewModel.this.isLocalIgOn());
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_DEODORANT_BOOL, Boolean.valueOf(enable));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacAutoDefogWorkStChanged(boolean enable) {
            LogUtils.i(ServiceViewModel.TAG, "onHvacAutoDefogWorkStChanged:" + enable);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HVAC_AUTO_DEFOG_WORK_ST, Boolean.valueOf(enable));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacQualityPurgeChanged(boolean enable) {
            ServiceViewModel.this.mHvacSmartControl.onAirAualityPurgeChanged(enable);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_HVAC_CLEAN_BOOL, Boolean.valueOf(enable));
            CarControl.System.putBool(App.getInstance().getContentResolver(), CarControl.System.HVAC_AIR_PURGE_MODE, enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacAirQualityOutside(boolean isPolluted) {
            ServiceViewModel.this.mHvacSmartControl.onAirQualityOutsideChanged(isPolluted);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacAirQualityLevel(int level) {
            ServiceViewModel.this.mHvacSmartControl.onAirQualityLevelChanged(level);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IHvacController.Callback
        public void onHvacSingleModeChanged(boolean enable) {
            if (enable) {
                HvacSmartControl.HVAC_SINGLE_MODE_ENABLE = true;
            } else {
                ServiceViewModel.this.mHvacController.setHvacSingleModeActive(false);
            }
            ServiceViewModel.this.mHvacSmartControl.invokeHvacSingleMode(ServiceViewModel.this.isLocalIgOn());
        }
    }

    protected void openDefaultAutoDefog() {
        LogUtils.i(TAG, "openDefaultAutoDefog", false);
        if (this.mFunctionModel.isDefaultAutoDefogSwitch()) {
            this.mHvacController.setAutoDefogSwitch(true);
        }
    }

    /* loaded from: classes2.dex */
    private static class AvasCallbackImpl implements IAvasController.Callback {
        private AvasCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvasController.Callback
        public void onAvasLowSpdChanged(boolean enabled) {
            if (enabled) {
                AvasSmartControl.getInstance().setSettingLowSpdEnable();
            }
        }
    }

    /* loaded from: classes2.dex */
    private class LluCallbackImpl implements ILluController.Callback {
        private LluCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.ILluController.Callback
        public void onLluEnableChanged(boolean enabled) {
            if (ServiceViewModel.this.mDataSync.mIsDataSynced) {
                if (enabled) {
                    ServiceViewModel.this.mLluController.setLluWakeWaitSwitch(ServiceViewModel.this.mLluController.getLluWakeWaitSw(), true, false);
                    ServiceViewModel.this.mLluController.setLluSleepSwitch(ServiceViewModel.this.mLluController.getLluSleepSw(), true, false);
                    ServiceViewModel.this.mLluController.setLluChargingSwitch(ServiceViewModel.this.mLluController.getLluChargingSw(), true, false);
                } else if (enabled) {
                } else {
                    ServiceViewModel.this.mLluController.setLluWakeWaitSwitch(false, true, true);
                    ServiceViewModel.this.mLluController.setLluSleepSwitch(false, true, true);
                    ServiceViewModel.this.mLluController.setLluChargingSwitch(false, true, true);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class McuCallbackImpl implements IMcuController.Callback {
        private McuCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
        public void onIgStatusChanged(int state) {
            ServiceViewModel.this.handleIgStatusChanged(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
        public void onKeyOpenFailed(int value) {
            if (ServiceViewModel.this.isLocalIgOn()) {
                if (value == 0) {
                    ServiceViewModel.this.dismissReadyDisableDialog();
                    ServiceViewModel.this.mCurrUnlockReqRes = 0;
                    ServiceViewModel.this.mFunctionModel.setUnlockReqSrc(ServiceViewModel.this.mCurrUnlockReqRes);
                } else if (value == 1) {
                    ServiceViewModel.this.dismissReadyDisableDialog();
                    ServiceViewModel.this.mCurrUnlockReqRes = 3;
                    ServiceViewModel.this.mFunctionModel.setUnlockReqSrc(ServiceViewModel.this.mCurrUnlockReqRes);
                    ServiceViewModel.this.showReadyDisableDialog();
                } else if (value == 2) {
                    ServiceViewModel.this.dismissReadyDisableDialog();
                    ServiceViewModel.this.mCurrUnlockReqRes = 2;
                    ServiceViewModel.this.mFunctionModel.setUnlockReqSrc(ServiceViewModel.this.mCurrUnlockReqRes);
                    ServiceViewModel.this.showReadyDisableDialog();
                } else if (value != 4) {
                } else {
                    ServiceViewModel.this.dismissReadyDisableDialog();
                    ServiceViewModel.this.mCurrUnlockReqRes = 4;
                    ServiceViewModel.this.mFunctionModel.setUnlockReqSrc(ServiceViewModel.this.mCurrUnlockReqRes);
                    ServiceViewModel.this.showReadyDisableDialog();
                }
            }
        }

        public /* synthetic */ void lambda$onRemindWarning$0$ServiceViewModel$McuCallbackImpl(final int state) {
            ServiceViewModel.this.handleRemindWarningState(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
        public void onRemindWarning(final int state) {
            ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$McuCallbackImpl$nH1-sKqrXbkvTqQ0Ttz5UcjXFro
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceViewModel.McuCallbackImpl.this.lambda$onRemindWarning$0$ServiceViewModel$McuCallbackImpl(state);
                }
            });
        }
    }

    /* loaded from: classes2.dex */
    private class TboxCallbackImpl implements ITboxController.Callback {
        private TboxCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController.Callback
        public void onAcChargeUnlockStChanged(int state) {
            ServiceViewModel.this.handleTboxAcChargeStChanged(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController.Callback
        public void onIotBusinessTypeChanged(int state) {
            ServiceViewModel.this.handleIotBusinesstypeChanged(state);
        }
    }

    /* loaded from: classes2.dex */
    private class DiagnosticCallbackImpl implements IDiagnosticController.Callback {
        private DiagnosticCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IDiagnosticController.Callback
        public void onIbtFaultChanged(boolean isFault) {
            if (isFault && ServiceViewModel.this.mCarConfigHelper.isSupportEpbWarning()) {
                LogUtils.i(ServiceViewModel.TAG, "onIbtFaultChanged: isFault", false);
                boolean z = ServiceViewModel.this.mMcuController.getIgStatusFromMcu() == 1;
                boolean z2 = ServiceViewModel.this.mEspController.getHbcRequestStatus() == 1;
                LogUtils.i(ServiceViewModel.TAG, "onIbtFaultChanged: isIgOn=" + z + ", isHbcRequest=" + z2, false);
                if (z && z2) {
                    ServiceViewModel.this.showEpbDialog();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ScuCallbackImpl implements IScuController.Callback {
        private ScuCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
        public void onXpuXPilotStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onXpuXPilotStateChanged: " + state, false);
            if (ServiceViewModel.this.mDataSync.mIsDataSynced) {
                ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$ScuCallbackImpl$FQNm-jSMr8H-LdPUSiN5yOSgFxU
                    @Override // java.lang.Runnable
                    public final void run() {
                        ServiceViewModel.ScuCallbackImpl.this.lambda$onXpuXPilotStateChanged$0$ServiceViewModel$ScuCallbackImpl();
                    }
                }, 1000L);
            } else {
                LogUtils.w(ServiceViewModel.TAG, "Account data not synced yet, do not syncXPilotNgpSw now", false);
            }
            QuickSettingManager.getInstance().onSignalCallbackSingleThread(QuickSettingConstants.KEY_IHB_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$ScuCallbackImpl$adUEBgep_KiAvfWU-VN1KcCa_aM
                @Override // java.util.function.Supplier
                public final Object get() {
                    return ServiceViewModel.ScuCallbackImpl.this.lambda$onXpuXPilotStateChanged$1$ServiceViewModel$ScuCallbackImpl();
                }
            });
            if (ServiceViewModel.this.mCarConfigHelper.isSupportAirSuspension()) {
                if (state == 1) {
                    ServiceViewModel.this.mBcmController.setAsLocationSw(ServiceViewModel.this.mDataSync.getAsLocationStatus());
                } else if (state == 0) {
                    ServiceViewModel.this.mBcmController.setAsLocationValue(3);
                } else if (state == 2) {
                    ServiceViewModel.this.mBcmController.setAsLocationValue(2);
                } else if (state == 3) {
                    ServiceViewModel.this.mBcmController.setAsLocationValue(4);
                } else {
                    ServiceViewModel.this.mBcmController.setAsLocationValue(0);
                }
            }
        }

        public /* synthetic */ void lambda$onXpuXPilotStateChanged$0$ServiceViewModel$ScuCallbackImpl() {
            if (ServiceViewModel.this.mIsLocalIgOn) {
                if (ServiceViewModel.this.mCarConfigHelper.isSupportNgp()) {
                    ServiceViewModel.this.syncXPilotNGP();
                }
                if (ServiceViewModel.this.mCarConfigHelper.isSupportMemPark()) {
                    ServiceViewModel.this.mScuController.syncXPilotMemParkSw();
                    return;
                }
                return;
            }
            LogUtils.w(ServiceViewModel.TAG, "MCU is not local ig on, do not handle XPILOT state changed", false);
        }

        public /* synthetic */ ScuResponse lambda$onXpuXPilotStateChanged$1$ServiceViewModel$ScuCallbackImpl() {
            return ScuResponse.fromScuState(ServiceViewModel.this.mScuController.getIhbState());
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
        public void onIhbStateChanged(final int state) {
            QuickSettingManager.getInstance().onSignalCallbackSingleThread(QuickSettingConstants.KEY_IHB_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$ScuCallbackImpl$fE6MqNrc0F3mO6Z8EterNYr3zMA
                @Override // java.util.function.Supplier
                public final Object get() {
                    ScuResponse fromScuState;
                    fromScuState = ScuResponse.fromScuState(state);
                    return fromScuState;
                }
            });
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
        public void onMirrorCtrl(int ctrl) {
            LogUtils.i(ServiceViewModel.TAG, "onMirrorCtrl: lastMirrorCtrl=" + ServiceViewModel.this.mLastMirrorCtrl + ", currentMirrorCtrl=" + ctrl, false);
            if (ServiceViewModel.this.mLastMirrorCtrl == 0 || ServiceViewModel.this.mLastMirrorCtrl != ctrl) {
                ServiceViewModel.this.mLastMirrorCtrl = ctrl;
                ServiceViewModel.this.mBgHandler.removeCallbacks(ServiceViewModel.this.mScuMirrorTask);
                if (ServiceViewModel.this.mLastMirrorCtrl == 1 || ServiceViewModel.this.mLastMirrorCtrl == 2) {
                    ServiceViewModel.this.mBgHandler.postDelayed(ServiceViewModel.this.mScuMirrorTask, 200L);
                }
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
        public void onShowSdcObstacleTips(int state) {
            ServiceViewModel.this.handleSdcObstacleTips(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IScuController.Callback
        public void onShowSdcNarrowSpaceTips(int state) {
            if (state == 1 && ServiceViewModel.this.mFunctionModel.isShowSdcNarrowSpaceAllowed()) {
                ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$ScuCallbackImpl$UnRElAV-QjnTZly1qCenTIujy0c
                    @Override // java.lang.Runnable
                    public final void run() {
                        ServiceViewModel.ScuCallbackImpl.this.lambda$onShowSdcNarrowSpaceTips$3$ServiceViewModel$ScuCallbackImpl();
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onShowSdcNarrowSpaceTips$3$ServiceViewModel$ScuCallbackImpl() {
            if (BaseFeatureOption.getInstance().isSupportNapa()) {
                ServiceViewModel.this.mUnityShowDialogData.postValue(GlobalConstant.UnityDialogScene.DIALOG_NARROW_SPACE);
            } else {
                if (ServiceViewModel.this.mNarrowSpaceWarnDialog == null) {
                    ServiceViewModel.this.mNarrowSpaceWarnDialog = new XDialog(App.getInstance()).setTitle(R.string.sdc_narrow_space_dlg_title).setMessage(R.string.sdc_narrow_space_content).setNegativeButton((CharSequence) null, (XDialogInterface.OnClickListener) null).setPositiveButton(ResUtils.getString(R.string.btn_confirm_open), (XDialogInterface.OnClickListener) null);
                    ServiceViewModel.this.mNarrowSpaceWarnDialog.setSystemDialog(2008);
                }
                if (!ServiceViewModel.this.mNarrowSpaceWarnDialog.getDialog().isShowing()) {
                    ServiceViewModel.this.mNarrowSpaceWarnDialog.show();
                }
            }
            SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.sdc_narrow_space_content));
            ServiceViewModel.this.mFunctionModel.setShowSdcNarrowSpaceTs(System.currentTimeMillis());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class XpuCallbackImpl implements IXpuController.Callback {
        private XpuCallbackImpl() {
        }

        public /* synthetic */ ScuResponse lambda$onNedcStateChanged$0$ServiceViewModel$XpuCallbackImpl() {
            return ScuResponse.fromScuState(ServiceViewModel.this.mScuController.getIhbState());
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IXpuController.Callback
        public void onNedcStateChanged(int state) {
            QuickSettingManager.getInstance().onSignalCallbackSingleThread(QuickSettingConstants.KEY_IHB_INT, new Supplier() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$XpuCallbackImpl$4GfHwvptixqC8VRFHfFaDjHK1ow
                @Override // java.util.function.Supplier
                public final Object get() {
                    return ServiceViewModel.XpuCallbackImpl.this.lambda$onNedcStateChanged$0$ServiceViewModel$XpuCallbackImpl();
                }
            });
        }
    }

    /* loaded from: classes2.dex */
    private class AvmCallbackImpl implements IAvmController.Callback {
        private AvmCallbackImpl() {
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IAvmController.Callback
        public void onAvmWorkStateChanged(int state) {
            LogUtils.i(ServiceViewModel.TAG, "onAvmWorkStateChanged, state = " + state);
            if (ServiceViewModel.this.mSsDoorStateCallback != null) {
                ServiceViewModel.this.mSsDoorStateCallback.onAvmWorkStateChanged(state);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSdcObstacleTips(int state) {
        if (state == 1) {
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$W3HWxDhU18Eys-objdi3aFYnVh4
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceViewModel.this.lambda$handleSdcObstacleTips$18$ServiceViewModel();
                }
            });
        } else if (state == 0) {
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$4spXhm4hPIf_D-tMMR2uz_JV9hE
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceViewModel.this.lambda$handleSdcObstacleTips$19$ServiceViewModel();
                }
            });
        }
    }

    public /* synthetic */ void lambda$handleSdcObstacleTips$18$ServiceViewModel() {
        if (this.mSdcObstacleDialog == null) {
            XDialog canceledOnTouchOutside = new XDialog(App.getInstance()).setTitle(R.string.sdc_obstacle_danger_dialog_title).setMessage(R.string.sdc_obstacle_danger_dialog_content).setCloseVisibility(true).setIcon(R.drawable.ic_sdc_obstacle_osd).setCanceledOnTouchOutside(false);
            this.mSdcObstacleDialog = canceledOnTouchOutside;
            canceledOnTouchOutside.setSystemDialog(2008);
        }
        if (this.mSdcObstacleDialog.isShowing()) {
            return;
        }
        this.mSdcObstacleDialog.show();
    }

    public /* synthetic */ void lambda$handleSdcObstacleTips$19$ServiceViewModel() {
        XDialog xDialog = this.mSdcObstacleDialog;
        if (xDialog == null || !xDialog.isShowing()) {
            return;
        }
        this.mSdcObstacleDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLocalIgOn() {
        return this.mMcuController.getIgStatusFromMcu() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBossKeyDown() {
        if (this.mIcmController.getDoorBossKeySw()) {
            this.mBgHandler.removeCallbacks(this.mBossKeyUpTask);
            if (!this.mIsBossKeyPressed) {
                LogUtils.i(TAG, "handleBossKeyDown", false);
                this.mBgHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$6mruwtIsgOw9JlDsaBFPFeaXtXI
                    @Override // java.lang.Runnable
                    public final void run() {
                        SeatSmartControl.getInstance().controlPsnSeatMoveForwardStart();
                    }
                });
            }
            this.mIsBossKeyPressed = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBossKeyUp() {
        this.mBgHandler.postDelayed(this.mBossKeyUpTask, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class DoorBossKeyListener extends IXPKeyListener.Stub {
        private DoorBossKeyListener() {
        }

        public int notify(KeyEvent keyEvent, String s) {
            if (keyEvent != null) {
                int keyCode = keyEvent.getKeyCode();
                if (keyCode == 164) {
                    if (keyEvent.getAction() != 0) {
                        return 0;
                    }
                    ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.service.-$$Lambda$ServiceViewModel$DoorBossKeyListener$OKgNOd-wZmNUZRGaFBGBSMatEHY
                        @Override // java.lang.Runnable
                        public final void run() {
                            StatisticUtils.sendKeyEventStatistic(PageEnum.KEY_EVENT_PAGE, BtnEnum.KEY_EVENT_PAGE_DOOR_KEY, 2);
                        }
                    }, 500L);
                    return 0;
                } else if (keyCode != 1092) {
                    if (keyCode != 1093) {
                        return 0;
                    }
                    int action = keyEvent.getAction();
                    if (action == 0) {
                        ServiceViewModel.this.handleBossKeyDown();
                        return 0;
                    } else if (action != 1) {
                        return 0;
                    } else {
                        ServiceViewModel.this.handleBossKeyUp();
                        return 0;
                    }
                } else {
                    int action2 = keyEvent.getAction();
                    if (action2 != 0) {
                        if (action2 != 1) {
                            return 0;
                        }
                        ServiceViewModel.this.handleBossKeyUp();
                        return 0;
                    } else if (keyEvent.getRepeatCount() > 0) {
                        ServiceViewModel.this.handleBossKeyDown();
                        return 0;
                    } else {
                        return 0;
                    }
                }
            }
            return 0;
        }
    }
}
