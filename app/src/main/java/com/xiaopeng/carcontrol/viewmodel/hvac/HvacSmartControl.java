package com.xiaopeng.carcontrol.viewmodel.hvac;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.IpcRouterService;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IHvacController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.utils.VuiUtils;

/* loaded from: classes2.dex */
public class HvacSmartControl {
    static final int AQ_AVAILABLE_MINIMUM = 5;
    static final int AQ_THRESHOLD = 100;
    private static final float HVAC_COOL_TEMP_THRESHOLD = 30.0f;
    private static final float HVAC_EXTERNAL_TEMP_ERROR1 = -40.0f;
    private static final float HVAC_EXTERNAL_TEMP_ERROR2 = 87.5f;
    private static final float HVAC_HEAT_TEMP_THRESHOLD = 10.0f;
    public static boolean HVAC_SINGLE_MODE_ENABLE = true;
    private static final int HVAC_SMART_MODE_CIRCULATION_TIME = 120;
    static final int HVAC_SMART_MODE_COMMAND_DELAY = 200;
    private static final int HVAC_SMART_MODE_COUNTDOWN_DELAY = 200;
    static final int HVAC_SMART_MODE_COUNTDOWN_TIME = 180;
    private static final int HVAC_SMART_MODE_PROTECT_TIME = 1000;
    private static final int HVAC_SMART_MODE_STATUS_CHECK_TIME = 177;
    static final int HVAC_SMART_MOSE_COUNTDOWN_MILLI = 1000;
    private static final String TAG = "HvacSmartControl";
    private int mCountDownTime;
    long mDeodorantTime;
    boolean mNeedMemoryHvacStatus;
    private boolean mNeedRecoverDeodorant;
    private boolean mNeedRecoverRapidCooling;
    long mRapidCoolingTime;
    private final int HVAC_SMART_MODE_MSG_COOLING = 1;
    private final int HVAC_SMART_MODE_MSG_DEODORANT = 2;
    private final int HVAC_SMART_MODE_MSG_HEAT = 3;
    private final Handler mCountDownHandler = new Handler(ThreadUtils.getHandler(0).getLooper()) { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 1) {
                if (HvacSmartControl.this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
                    int i2 = msg.arg1;
                    LogUtils.d(HvacSmartControl.TAG, "HvacRapidCoolingCountDown:" + i2);
                    HvacSmartControl.this.mHvacBaseVm.setHvacRapidCoolingCountdownTime(i2);
                    if (i2 == HvacSmartControl.HVAC_SMART_MODE_STATUS_CHECK_TIME) {
                        HvacSmartControl.this.hvacRapidCoolingStatusCheck();
                    } else if (i2 == 120) {
                        HvacSmartControl.this.mHvacBaseVm.setHvacCirculationInner();
                    } else if (i2 == 0) {
                        HvacSmartControl.this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
                    }
                    if (i2 > 0) {
                        HvacSmartControl.this.sendCountDownMsg(1, i2);
                    }
                }
            } else if (i == 2) {
                if (HvacSmartControl.this.mHvacBaseVm.isHvacDeodorantEnable()) {
                    int i3 = msg.arg1;
                    LogUtils.d(HvacSmartControl.TAG, "HvacDeodorantCountdown" + i3);
                    HvacSmartControl.this.mHvacBaseVm.setHvacDeodorantCountdownTime(i3);
                    if (i3 == HvacSmartControl.HVAC_SMART_MODE_STATUS_CHECK_TIME) {
                        HvacSmartControl.this.hvacDeodorantStatusCheck();
                    } else if (i3 == 0) {
                        HvacSmartControl.this.mHvacBaseVm.setHvacDeodorantEnable(false);
                    }
                    if (i3 > 0) {
                        HvacSmartControl.this.sendCountDownMsg(2, i3);
                    }
                }
            } else if (i == 3 && HvacSmartControl.this.mHvacBaseVm.isHvacRapidHeatEnable()) {
                int i4 = msg.arg1;
                LogUtils.d(HvacSmartControl.TAG, "HvacRapidHeatCountdown" + i4);
                HvacSmartControl.this.mHvacBaseVm.setHvacRapidHeatCountdownTime(i4);
                if (i4 == 0) {
                    HvacSmartControl.this.mHvacBaseVm.setHvacRapidHeatEnable(false);
                } else if (i4 > 0) {
                    HvacSmartControl.this.sendCountDownMsg(3, i4);
                }
            }
        }
    };
    private Runnable singleModeCloseRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$2F5gX6wSQ3wQk_SIBR9c2_L_UxU
        @Override // java.lang.Runnable
        public final void run() {
            HvacSmartControl.this.hvacSingleModeClosePsnVent();
        }
    };
    private Runnable drvTempSyncRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl.3
        @Override // java.lang.Runnable
        public void run() {
            LogUtils.i(HvacSmartControl.TAG, "executeHvacDrvTempSync power:" + HvacSmartControl.this.mHvacBaseVm.isHvacPowerModeOn() + ",drvSyncMode:" + HvacSmartControl.this.mHvacBaseVm.isHvacDriverSyncMode() + ",igLocalOn:" + HvacSmartControl.this.mSeatViewModel.isLocalIgOn() + ",evHightVolOn:" + HvacSmartControl.this.mVcuViewModel.isEvHighVolOn(), false);
            if (FunctionModel.getInstance().isHvacDrvTempSyncAllowed() && HvacSmartControl.this.mHvacBaseVm.isHvacPowerModeOn() && HvacSmartControl.this.mSeatViewModel.isLocalIgOn() && HvacSmartControl.this.mVcuViewModel.isEvHighVolOn()) {
                FunctionModel.getInstance().setHvacDrvTempSyncTime();
                if (HvacSmartControl.this.mHvacBaseVm.isHvacDriverSyncMode()) {
                    return;
                }
                HvacSmartControl.this.mHvacBaseVm.setHvacDriverSyncMode(true);
            }
        }
    };
    IHvacViewModel mHvacBaseVm = (IHvacViewModel) ViewModelManager.getInstance().getViewModelImpl(IHvacViewModel.class);
    IWindowDoorViewModel mWindowBaseVm = (IWindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
    private ISeatViewModel mSeatViewModel = (ISeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
    private IVcuViewModel mVcuViewModel = (IVcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
    protected IMcuController mMcuController = (IMcuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_MCU_SERVICE);
    private HvacAirAutoProtect mAirAutoProtectMode = this.mHvacBaseVm.getAirAutoProtectMode();
    private AirPurgeControl mAirPurgeControl = new AirPurgeControl(this.mHvacBaseVm);
    protected CarBaseConfig mCarBaseConfig = CarBaseConfig.getInstance();
    protected FunctionModel mFunctionModel = FunctionModel.getInstance();

    public void onAirQualityOutsideChanged(boolean isPolluted) {
    }

    public void onHvacRapidHeatChanged(boolean enable) {
    }

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static HvacSmartControl sInstance = createInstance();

        private SingleHolder() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private static HvacSmartControl createInstance() {
            char c;
            String xpCduType = CarStatusUtils.getXpCduType();
            int hashCode = xpCduType.hashCode();
            if (hashCode != 2577) {
                switch (hashCode) {
                    case 2560:
                        if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q1)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2561:
                        if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q2)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2562:
                        if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q3)) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        switch (hashCode) {
                            case 2564:
                                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q5)) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 2565:
                                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q6)) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 2566:
                                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q7)) {
                                    c = 6;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 2567:
                                if (xpCduType.equals(VuiUtils.CAR_PLATFORM_Q8)) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 2568:
                                if (xpCduType.equals("Q9")) {
                                    c = 7;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                }
            } else {
                if (xpCduType.equals("QB")) {
                    c = '\b';
                }
                c = 65535;
            }
            if (c == 0 || c == 1 || c == 2) {
                return new D2HvacSmartControl();
            }
            if (c == 3) {
                return new HvacSmartControl();
            }
            return new XpHvacSmartControl();
        }
    }

    public static HvacSmartControl getInstance() {
        return SingleHolder.sInstance;
    }

    public void onAirAutoProtectModeChanged(HvacAirAutoProtect mode) {
        if (this.mAirAutoProtectMode == HvacAirAutoProtect.Off && mode != HvacAirAutoProtect.Off) {
            FunctionModel.getInstance().setAirProtectTs(0L);
        }
        this.mAirAutoProtectMode = mode;
    }

    public void onHvacInnerAqChanged(int aqValue) {
        this.mAirPurgeControl.onInnerAqChange(aqValue);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void airProtectRemind() {
        NotificationHelper.getInstance().sendMessageToMessageCenter(getAirProtectRemindNoId(), ResUtils.getString(R.string.hvac_auto_air_protect_title), ResUtils.getString(R.string.hvac_auto_air_protect_content), ResUtils.getString(R.string.hvac_auto_air_protect_prompt_tts), ResUtils.getString(R.string.hvac_auto_air_protect_wake_words), ResUtils.getString(R.string.hvac_auto_air_protect_response_tts), ResUtils.getString(R.string.hvac_auto_air_protect_btn_title), true, 0L, new IpcRouterService.ICallback() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$1yRLATTw_0LO7g9xhuUJNak7SUY
            @Override // com.xiaopeng.carcontrol.IpcRouterService.ICallback
            public final void onCallback(String str) {
                HvacSmartControl.this.lambda$airProtectRemind$0$HvacSmartControl(str);
            }
        });
    }

    public /* synthetic */ void lambda$airProtectRemind$0$HvacSmartControl(String content) {
        doAirProtect(false);
    }

    public void doAirProtect(boolean auto) {
        if (CarBaseConfig.getInstance().isSupportInnerPm25()) {
            this.mHvacBaseVm.setHvacAutoMode(true);
            if (CarBaseConfig.getInstance().isSupportAqs() && this.mHvacBaseVm.getHvacAqsMode() != 1) {
                this.mHvacBaseVm.setHvacAqsMode(1);
            }
            String string = ResUtils.getString(auto ? R.string.hvac_auto_air_auto_toast : R.string.hvac_auto_air_remind_toast);
            NotificationHelper.getInstance().showToast(string);
            if (auto) {
                SpeechHelper.getInstance().speak(string);
            } else {
                SoundHelper.play(this.mHvacBaseVm.getAirAutoProtectSound(), false, false);
            }
        }
    }

    public void onSmartHvacSwChanged(boolean enabled) {
        if (enabled) {
            FunctionModel.getInstance().setSmartHvacTs(0L);
            smartControlHvac();
        }
    }

    public void onAqsModeChange(boolean enable) {
        LogUtils.d(TAG, "onAqsModeChange:" + enable + ",purge:" + this.mHvacBaseVm.isHvacQualityPurgeEnable(), false);
        if (this.mAirPurgeControl == null || !this.mHvacBaseVm.isHvacQualityPurgeEnable() || enable || this.mAirPurgeControl.isPurgeProtectTime()) {
            return;
        }
        this.mHvacBaseVm.setHvacQualityPurgeMode(false);
    }

    public void onAirQualityLevelChanged(int level) {
        if (CarBaseConfig.getInstance().isSupportAqs() && this.mHvacBaseVm.getHvacAqsMode() == 0 && level >= 3 && level <= 10 && this.mWindowBaseVm.isAllDoorsClosed() && FunctionModel.getInstance().isOffgasProtectFuncAllowed()) {
            FunctionModel.getInstance().setOffgasProtectTs(System.currentTimeMillis());
            LogUtils.d(TAG, "mAirAutoProtectMode:" + this.mAirAutoProtectMode, false);
            int i = AnonymousClass4.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacAirAutoProtect[this.mAirAutoProtectMode.ordinal()];
            if (i == 1) {
                doAirPollutedProtect(true);
            } else if (i != 2) {
            } else {
                airAirPollutedProtectRemind();
            }
        }
    }

    void doAirPollutedProtect(boolean auto) {
        boolean isSupportAqs = CarBaseConfig.getInstance().isSupportAqs();
        if (isSupportAqs) {
            if (this.mHvacBaseVm.getHvacAqsMode() != 1) {
                this.mHvacBaseVm.setHvacAqsMode(1);
            }
        } else {
            this.mHvacBaseVm.setHvacCirculationInner();
        }
        SoundHelper.play(this.mHvacBaseVm.getAirAutoProtectSound(), false, false);
        NotificationHelper.getInstance().showToast(auto ? isSupportAqs ? R.string.hvac_air_quality_out_aqs_auto : R.string.hvac_air_quality_out_protect_auto : isSupportAqs ? R.string.hvac_air_quality_out_aqs_manual : R.string.hvac_air_quality_out_protect_manual);
    }

    void airAirPollutedProtectRemind() {
        String string;
        String string2;
        String string3;
        String string4;
        boolean isSupportAqs = CarBaseConfig.getInstance().isSupportAqs();
        NotificationHelper notificationHelper = NotificationHelper.getInstance();
        String string5 = ResUtils.getString(R.string.hvac_air_quality_out_protect_title);
        if (isSupportAqs) {
            string = ResUtils.getString(R.string.hvac_air_quality_out_aqs_sub);
        } else {
            string = ResUtils.getString(R.string.hvac_air_quality_out_protect_sub);
        }
        String str = string;
        if (isSupportAqs) {
            string2 = ResUtils.getString(R.string.hvac_air_quality_out_aqs_tts);
        } else {
            string2 = ResUtils.getString(R.string.hvac_air_quality_out_protect_tts);
        }
        String str2 = string2;
        String string6 = ResUtils.getString(R.string.hvac_auto_air_protect_wake_words);
        if (isSupportAqs) {
            string3 = ResUtils.getString(R.string.hvac_air_quality_out_aqs_response_tts);
        } else {
            string3 = ResUtils.getString(R.string.hvac_air_quality_out_protect_response_tts);
        }
        String str3 = string3;
        if (isSupportAqs) {
            string4 = ResUtils.getString(R.string.hvac_auto_air_protect_btn_title);
        } else {
            string4 = ResUtils.getString(R.string.hvac_air_quality_out_btn_title);
        }
        notificationHelper.sendMessageToMessageCenter(NotificationHelper.SCENE_OUTSIDE_AIR_QUALITY, string5, str, str2, string6, str3, string4, false, 0L, new IpcRouterService.ICallback() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$NJ27BCgtt_uvwx2snwlg5jBgkVs
            @Override // com.xiaopeng.carcontrol.IpcRouterService.ICallback
            public final void onCallback(String str4) {
                HvacSmartControl.this.lambda$airAirPollutedProtectRemind$1$HvacSmartControl(str4);
            }
        });
    }

    public /* synthetic */ void lambda$airAirPollutedProtectRemind$1$HvacSmartControl(String content) {
        doAirPollutedProtect(false);
    }

    public void smartControlHvac() {
        if (CarBaseConfig.getInstance().isSupportInnerPm25() && this.mHvacBaseVm.isSmartHvacEnabled() && FunctionModel.getInstance().isSmartHvacFuncAllowed()) {
            FunctionModel.getInstance().setSmartHvacTs(System.currentTimeMillis());
            boolean isHvacPowerModeOn = this.mHvacBaseVm.isHvacPowerModeOn();
            int seatHeatLevel = this.mHvacBaseVm.getSeatHeatLevel();
            int seatVentLevel = this.mHvacBaseVm.getSeatVentLevel();
            String str = TAG;
            LogUtils.d(str, "smartControlHvac isHvacOn: " + isHvacPowerModeOn + ", seatHeatLevel: " + seatHeatLevel + ", seatVentLevel: " + seatVentLevel, false);
            if (!isHvacPowerModeOn && seatHeatLevel == SeatHeatLevel.Off.ordinal() && seatVentLevel == SeatVentLevel.Off.ordinal()) {
                float hvacExternalTemp = this.mHvacBaseVm.getHvacExternalTemp();
                LogUtils.d(str, "smartControlHvac inner temp: " + hvacExternalTemp, false);
                if (hvacExternalTemp < HVAC_HEAT_TEMP_THRESHOLD && hvacExternalTemp != HVAC_EXTERNAL_TEMP_ERROR1) {
                    this.mHvacBaseVm.setHvacAutoMode(true);
                    this.mHvacBaseVm.setSeatHeatLevel(SeatHeatLevel.Level2.ordinal());
                } else if (hvacExternalTemp <= HVAC_COOL_TEMP_THRESHOLD || hvacExternalTemp == HVAC_EXTERNAL_TEMP_ERROR2) {
                } else {
                    this.mHvacBaseVm.setHvacAutoMode(true);
                    this.mHvacBaseVm.setSeatVentLevel(SeatVentLevel.Level2.ordinal());
                }
            }
        }
    }

    public void handleEconMode(boolean isEcoMode) {
        if (this.mHvacBaseVm.isHvacPowerModeOn()) {
            HvacSwitchStatus fromHvacStatus = HvacSwitchStatus.fromHvacStatus(this.mHvacBaseVm.getHvacEconMode());
            if (isEcoMode && fromHvacStatus == HvacSwitchStatus.OFF) {
                this.mHvacBaseVm.setHvacEconMode(1);
            } else if (isEcoMode || fromHvacStatus != HvacSwitchStatus.ON) {
            } else {
                this.mHvacBaseVm.setHvacEconMode(0);
            }
        }
    }

    public void initHvacSmartMode() {
        if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            return;
        }
        String str = TAG;
        LogUtils.d(str, "initHvacSmartMode:" + this.mNeedRecoverRapidCooling + ",mNeedRecoverDeodorant:" + this.mNeedRecoverDeodorant, false);
        if (this.mNeedRecoverRapidCooling) {
            this.mNeedRecoverRapidCooling = false;
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$vbUsxILk2GMAcnwKQjcbhnusVDs
                @Override // java.lang.Runnable
                public final void run() {
                    HvacSmartControl.this.recoverHvacRapidCooling();
                }
            }, 200L);
        } else if (this.mNeedRecoverDeodorant) {
            this.mNeedRecoverDeodorant = false;
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$13zZuHs3myFpVI-YngyDUsLW7Oc
                @Override // java.lang.Runnable
                public final void run() {
                    HvacSmartControl.this.recoverHvacDeodorant();
                }
            }, 200L);
        } else {
            FunctionModel functionModel = FunctionModel.getInstance();
            boolean isHvacRapidCoolingEnable = functionModel.isHvacRapidCoolingEnable();
            boolean isHvacDeodorantEnable = functionModel.isHvacDeodorantEnable();
            LogUtils.d(str, "hvacRapidCoolingEnable:" + isHvacRapidCoolingEnable + ",hvacDeodorantEnable:" + isHvacDeodorantEnable, false);
            if (isHvacRapidCoolingEnable) {
                ((IHvacController.Callback) this.mHvacBaseVm).onHvacRapidCoolingChanged(true);
                this.mRapidCoolingTime = functionModel.getHvacRapidCoolingTime();
                double currentTimeMillis = (System.currentTimeMillis() - this.mRapidCoolingTime) / 1000.0d;
                LogUtils.d(str, "hvacRapidCoolingEnable:" + currentTimeMillis, false);
                if (currentTimeMillis >= 180.0d || currentTimeMillis < 0.0d) {
                    this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
                } else {
                    startHvacRapidCoolingCountDownDelay((int) (180.0d - currentTimeMillis));
                }
            }
            if (isHvacDeodorantEnable) {
                ((IHvacController.Callback) this.mHvacBaseVm).onHvacDeodorantChanged(true);
                this.mDeodorantTime = functionModel.getHvacDeodorantTime();
                long currentTimeMillis2 = (System.currentTimeMillis() - this.mDeodorantTime) / 1000;
                LogUtils.d(str, "mDeodorantTime:" + this.mDeodorantTime + ",hvacDeodorantEnable:" + currentTimeMillis2, false);
                if (currentTimeMillis2 >= 180 || currentTimeMillis2 < 0) {
                    this.mHvacBaseVm.setHvacDeodorantEnable(false);
                } else {
                    startHvacDeodorantCountdownDelay((int) (180 - currentTimeMillis2));
                }
            }
        }
        initAirPurgeMode();
    }

    protected void initAirPurgeMode() {
        this.mAirPurgeControl.initAirPurgeMode();
    }

    public void onHvacRapidCoolingChanged(boolean enable) {
        if (enable) {
            if (this.mHvacBaseVm.isHvacDeodorantEnable()) {
                this.mHvacBaseVm.setHvacDeodorantEnable(false);
                if (this.mHvacBaseVm.isHvacPowerModeOn()) {
                    recoverEavMode();
                }
            } else {
                memoryHvacSmartStatus();
            }
            controlRapidCoolingOpen();
            startHvacRapidCoolingCountDownDelay(HVAC_SMART_MODE_COUNTDOWN_TIME);
            return;
        }
        if (!this.mHvacBaseVm.isHvacDeodorantEnable() && this.mHvacBaseVm.isHvacPowerModeOn()) {
            recoverHvacRapidCooling();
        }
        stopHvacRapidCoolingCountDownDelay();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void startHvacRapidCoolingCountDownDelay(int time) {
        stopHvacDeodorantCountdownDelay();
        stopHvacRapidCoolingCountDownDelay();
        stopHvacRapidHeatCountdownDelay();
        this.mCountDownTime = time;
        Message obtain = Message.obtain();
        obtain.what = 1;
        obtain.arg1 = this.mCountDownTime;
        this.mCountDownHandler.sendMessageDelayed(obtain, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCountDownMsg(int what, int second) {
        Message obtain = Message.obtain();
        obtain.what = what;
        obtain.arg1 = second - 1;
        this.mCountDownHandler.sendMessageDelayed(obtain, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stopHvacRapidCoolingCountDownDelay() {
        Handler handler = this.mCountDownHandler;
        if (handler != null) {
            handler.removeMessages(1);
        }
    }

    private void recoverEavMode() {
        FunctionModel functionModel = FunctionModel.getInstance();
        if (this.mHvacBaseVm.isHvacVentOpen(0) && this.mHvacBaseVm.isHvacVentOpen(1)) {
            this.mHvacBaseVm.setHvacEavDriverWindMode(functionModel.getHvacSmartDrvVentMode());
            if (HvacEavWindMode.Mirror == HvacEavWindMode.fromHvacState(functionModel.getHvacSmartDrvVentMode())) {
                String hvacSmartDrvVentPosition = functionModel.getHvacSmartDrvVentPosition();
                if (!TextUtils.isEmpty(hvacSmartDrvVentPosition)) {
                    final String[] split = hvacSmartDrvVentPosition.split(",");
                    if (split.length == 4) {
                        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$XSfJqnEtmy22YancxUjbG6LUch8
                            @Override // java.lang.Runnable
                            public final void run() {
                                HvacSmartControl.this.lambda$recoverEavMode$2$HvacSmartControl(split);
                            }
                        }, 300L);
                    }
                }
            }
        }
        if (this.mHvacBaseVm.isHvacVentOpen(2) && this.mHvacBaseVm.isHvacVentOpen(3)) {
            this.mHvacBaseVm.setHvacEavPsnWindMode(functionModel.getHvacSmartPsnVentMode());
            if (HvacEavWindMode.Mirror == HvacEavWindMode.fromHvacState(functionModel.getHvacSmartPsnVentMode())) {
                String hvacSmartPsnVentPosition = functionModel.getHvacSmartPsnVentPosition();
                if (TextUtils.isEmpty(hvacSmartPsnVentPosition)) {
                    return;
                }
                final String[] split2 = hvacSmartPsnVentPosition.split(",");
                if (split2.length == 4) {
                    ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$LaOPf896CYBl1BXPvk9vJYVOzao
                        @Override // java.lang.Runnable
                        public final void run() {
                            HvacSmartControl.this.lambda$recoverEavMode$3$HvacSmartControl(split2);
                        }
                    }, 300L);
                }
            }
        }
    }

    public /* synthetic */ void lambda$recoverEavMode$2$HvacSmartControl(final String[] positions) {
        try {
            this.mHvacBaseVm.setHvacEAVDriverLeftHPosDirect(Integer.parseInt(positions[0]));
            int parseInt = Integer.parseInt(positions[1]);
            if (parseInt != 14 || !this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
                this.mHvacBaseVm.setHvacEAVDriverLeftVPosDirect(parseInt);
            }
            this.mHvacBaseVm.setHvacEAVDriverRightHPosDirect(Integer.parseInt(positions[2]));
            int parseInt2 = Integer.parseInt(positions[3]);
            if (parseInt2 == 14 && this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
                return;
            }
            this.mHvacBaseVm.setHvacEAVDriverRightVPosDirect(parseInt2);
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
    }

    public /* synthetic */ void lambda$recoverEavMode$3$HvacSmartControl(final String[] positions) {
        try {
            this.mHvacBaseVm.setHvacEAVPsnLeftHPosDirect(Integer.parseInt(positions[0]));
            int parseInt = Integer.parseInt(positions[1]);
            if (parseInt != 14 || !this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
                this.mHvacBaseVm.setHvacEAVPsnLeftVPosDirect(parseInt);
            }
            this.mHvacBaseVm.setHvacEAVPsnRightHPosDirect(Integer.parseInt(positions[2]));
            int parseInt2 = Integer.parseInt(positions[3]);
            if (parseInt2 == 14 && this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
                return;
            }
            this.mHvacBaseVm.setHvacEAVPsnRightVPosDirect(parseInt2);
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
    }

    public void onHvacDeodorantChanged(boolean enable) {
        if (enable) {
            if (this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
                this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
                if (this.mHvacBaseVm.isHvacPowerModeOn()) {
                    FunctionModel functionModel = FunctionModel.getInstance();
                    int hvacCmd = AcHeatNatureMode.toHvacCmd(AcHeatNatureMode.fromHvacMode(functionModel.getHvacSmartAcPtcMode()));
                    if (hvacCmd != -1) {
                        this.mHvacBaseVm.setAcHeatNatureMode(hvacCmd);
                    }
                    this.mHvacBaseVm.setHvacTempDriver(functionModel.getHvacSmartDrvTemp());
                    this.mHvacBaseVm.setHvacTempPsn(functionModel.getHvacSmartPsnTemp());
                    if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
                        this.mHvacBaseVm.setSeatVentLevel(this.mFunctionModel.getHvacSmartDrvSeatVent());
                    }
                }
            } else {
                memoryHvacSmartStatus();
            }
            controlDeodorantOpen();
            startHvacDeodorantCountdownDelay(HVAC_SMART_MODE_COUNTDOWN_TIME);
            return;
        }
        if (!this.mHvacBaseVm.isHvacRapidCoolingEnable() && this.mHvacBaseVm.isHvacPowerModeOn()) {
            recoverHvacDeodorant();
        }
        stopHvacDeodorantCountdownDelay();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void startHvacDeodorantCountdownDelay(int time) {
        stopHvacRapidCoolingCountDownDelay();
        stopHvacDeodorantCountdownDelay();
        stopHvacRapidHeatCountdownDelay();
        this.mCountDownTime = time;
        Message obtain = Message.obtain();
        obtain.what = 2;
        obtain.arg1 = this.mCountDownTime;
        this.mCountDownHandler.sendMessageDelayed(obtain, 200L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stopHvacDeodorantCountdownDelay() {
        Handler handler = this.mCountDownHandler;
        if (handler != null) {
            handler.removeMessages(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void startHvacRapidHeatCountdownDelay(int time) {
        stopHvacRapidCoolingCountDownDelay();
        stopHvacDeodorantCountdownDelay();
        stopHvacRapidHeatCountdownDelay();
        this.mCountDownTime = time;
        Message obtain = Message.obtain();
        obtain.what = 3;
        obtain.arg1 = this.mCountDownTime;
        this.mCountDownHandler.sendMessageDelayed(obtain, 200L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stopHvacRapidHeatCountdownDelay() {
        this.mCountDownHandler.removeMessages(3);
    }

    void controlRapidCoolingOpen() {
        this.mRapidCoolingTime = FunctionModel.getInstance().getHvacRapidCoolingTime();
        if (!this.mHvacBaseVm.isHvacPowerModeOn()) {
            this.mNeedMemoryHvacStatus = true;
            this.mHvacBaseVm.setHvacPowerMode(true);
        } else if (this.mHvacBaseVm.isHvacAutoModeOn()) {
            this.mHvacBaseVm.setAcHeatNatureMode(AcHeatNatureMode.toHvacCmd(AcHeatNatureMode.HVAC_NATURE_ON));
            this.mNeedMemoryHvacStatus = false;
        } else if (this.mHvacBaseVm.isHvacFrontDefrostOn()) {
            this.mNeedMemoryHvacStatus = true;
            this.mHvacBaseVm.setHvacFrontDefrost(false);
        } else {
            this.mNeedMemoryHvacStatus = false;
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$MHbmKn4XETl8yXbIv20ccUbzw1w
            @Override // java.lang.Runnable
            public final void run() {
                HvacSmartControl.this.lambda$controlRapidCoolingOpen$4$HvacSmartControl();
            }
        }, 200L);
    }

    public /* synthetic */ void lambda$controlRapidCoolingOpen$4$HvacSmartControl() {
        String str = TAG;
        LogUtils.i(str, "controlRapidCoolingOpen delay isHvacRapidCoolingEnable:" + this.mFunctionModel.isHvacRapidCoolingEnable(), false);
        if (!this.mFunctionModel.isHvacRapidCoolingEnable()) {
            LogUtils.i(str, "isHvacRapidCoolingEnable is false", false);
            return;
        }
        if (this.mNeedMemoryHvacStatus) {
            memoryHvacSmartStatus();
        }
        if (this.mHvacBaseVm.isHvacAutoModeOn()) {
            this.mHvacBaseVm.setAcHeatNatureMode(AcHeatNatureMode.toHvacCmd(AcHeatNatureMode.HVAC_NATURE_ON));
            try {
                Thread.sleep(200L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IHvacViewModel iHvacViewModel = this.mHvacBaseVm;
        iHvacViewModel.setHvacWindSpeedLevel(iHvacViewModel.getFanMaxLevel());
        this.mHvacBaseVm.setHvacAcMode(true);
        this.mHvacBaseVm.setHvacTempDriver(18.0f);
        this.mHvacBaseVm.setHvacTempPsn(18.0f);
        if (this.mHvacBaseVm.getHvacEconMode() != 0) {
            this.mHvacBaseVm.setHvacEconMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.OFF));
        }
        this.mHvacBaseVm.setHvacWindBlowFaceFoot();
        openHvacVent();
        this.mHvacBaseVm.setHvacCirculationOut();
        if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
            this.mHvacBaseVm.setSeatVentLevel(3);
        }
        setSeatHeatOff();
        if (this.mHvacBaseVm.getHvacAqsMode() == HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.ON)) {
            this.mHvacBaseVm.setHvacAqsMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.OFF));
        }
    }

    void controlDeodorantOpen() {
        this.mDeodorantTime = FunctionModel.getInstance().getHvacDeodorantTime();
        LogUtils.d(TAG, "controlDeodorantOpen:" + this.mDeodorantTime, false);
        if (!this.mHvacBaseVm.isHvacPowerModeOn()) {
            this.mNeedMemoryHvacStatus = true;
            this.mHvacBaseVm.setHvacPowerMode(true);
        } else if (this.mHvacBaseVm.isHvacAutoModeOn()) {
            this.mHvacBaseVm.setAcHeatNatureMode(AcHeatNatureMode.toHvacCmd(AcHeatNatureMode.HVAC_NATURE_ON));
            this.mNeedMemoryHvacStatus = false;
        } else if (this.mHvacBaseVm.isHvacFrontDefrostOn()) {
            this.mNeedMemoryHvacStatus = true;
            this.mHvacBaseVm.setHvacFrontDefrost(false);
        } else {
            this.mNeedMemoryHvacStatus = false;
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$UTZQ2QxqJmCgf-OLvTaIe487EOM
            @Override // java.lang.Runnable
            public final void run() {
                HvacSmartControl.this.lambda$controlDeodorantOpen$7$HvacSmartControl();
            }
        }, 200L);
    }

    public /* synthetic */ void lambda$controlDeodorantOpen$7$HvacSmartControl() {
        if (this.mNeedMemoryHvacStatus) {
            memoryHvacSmartStatus();
        }
        if (this.mHvacBaseVm.isHvacAutoModeOn()) {
            this.mHvacBaseVm.setAcHeatNatureMode(AcHeatNatureMode.toHvacCmd(AcHeatNatureMode.HVAC_NATURE_ON));
            try {
                Thread.sleep(200L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IHvacViewModel iHvacViewModel = this.mHvacBaseVm;
        iHvacViewModel.setHvacWindSpeedLevel(iHvacViewModel.getFanMaxLevel());
        if (this.mHvacBaseVm.getHvacEconMode() != 0) {
            this.mHvacBaseVm.setHvacEconMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.OFF));
        }
        this.mHvacBaseVm.setHvacWindBlowFaceFoot();
        this.mHvacBaseVm.setHvacCirculationOut();
        if (this.mHvacBaseVm.getHvacAqsMode() == HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.ON)) {
            this.mHvacBaseVm.setHvacAqsMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.OFF));
        }
        openHvacVent();
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$_XJAMeF6FDkN2J3BAj3tC7Fwzwk
            @Override // java.lang.Runnable
            public final void run() {
                HvacSmartControl.this.lambda$null$5$HvacSmartControl();
            }
        }, 300L);
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$ooIwGwb9Wx_XEHrokqrJRPVUoGw
            @Override // java.lang.Runnable
            public final void run() {
                HvacSmartControl.this.lambda$null$6$HvacSmartControl();
            }
        }, 500L);
        if (!this.mWindowBaseVm.isWindowLockActive()) {
            this.mWindowBaseVm.controlWindowVent();
            return;
        }
        LogUtils.d(TAG, "WindowLockActive");
        NotificationHelper.getInstance().showToast(R.string.win_lock_is_active, true, "hvac");
    }

    public /* synthetic */ void lambda$null$5$HvacSmartControl() {
        this.mHvacBaseVm.setHvacEavDriverWindMode(2);
        this.mHvacBaseVm.setHvacEavPsnWindMode(2);
    }

    public /* synthetic */ void lambda$null$6$HvacSmartControl() {
        this.mHvacBaseVm.setHvacEAVDriverLeftHPosDirect(3);
        this.mHvacBaseVm.setHvacEAVDriverLeftVPosDirect(3);
        this.mHvacBaseVm.setHvacEAVDriverRightHPosDirect(3);
        this.mHvacBaseVm.setHvacEAVDriverRightVPosDirect(3);
        this.mHvacBaseVm.setHvacEAVPsnLeftHPosDirect(3);
        this.mHvacBaseVm.setHvacEAVPsnLeftVPosDirect(3);
        this.mHvacBaseVm.setHvacEAVPsnRightHPosDirect(3);
        this.mHvacBaseVm.setHvacEAVPsnRightVPosDirect(3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setSeatHeatOff() {
        if (this.mCarBaseConfig.isSupportDrvSeatHeat()) {
            this.mHvacBaseVm.setSeatHeatLevel(0);
        }
        if (this.mCarBaseConfig.isSupportPsnSeatHeat()) {
            this.mHvacBaseVm.setPsnSeatHeatLevel(0);
        }
        if (this.mCarBaseConfig.isSupportRearSeatHeat()) {
            this.mHvacBaseVm.setRRSeatHeatLevel(0);
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl.2
                @Override // java.lang.Runnable
                public void run() {
                    HvacSmartControl.this.mHvacBaseVm.setRLSeatHeatLevel(0);
                }
            }, 150L);
        }
    }

    private void openHvacVent() {
        if (!this.mHvacBaseVm.isHvacVentOpen(0)) {
            this.mHvacBaseVm.setHvacVentStatus(0, true, false);
        }
        if (!this.mHvacBaseVm.isHvacVentOpen(1)) {
            this.mHvacBaseVm.setHvacVentStatus(1, true, false);
        }
        if (!this.mHvacBaseVm.isHvacVentOpen(2)) {
            this.mHvacBaseVm.setHvacVentStatus(2, true, false);
        }
        if (this.mHvacBaseVm.isHvacVentOpen(3)) {
            return;
        }
        this.mHvacBaseVm.setHvacVentStatus(3, true, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void memoryHvacSmartStatus() {
        this.mNeedMemoryHvacStatus = false;
        if (this.mHvacBaseVm.isHvacPowerModeOn()) {
            FunctionModel functionModel = FunctionModel.getInstance();
            functionModel.setHvacSmartAutoEnable(this.mHvacBaseVm.isHvacAutoModeOn());
            functionModel.setHvacSmartAcPtcMode(this.mHvacBaseVm.getAcHeatNatureMode());
            functionModel.setHvacSmartDrvTemp(this.mHvacBaseVm.getHvacDriverTemp());
            functionModel.setHvacSmartPsnTemp(this.mHvacBaseVm.getHvacPsnTemp());
            functionModel.setHvacSmartFanSpeed(this.mHvacBaseVm.getHvacWindSpeedLevel());
            functionModel.setHvacSmartEconMode(this.mHvacBaseVm.getHvacEconMode());
            functionModel.setHvacSmartBlowMode(this.mHvacBaseVm.getHvacWindBlowMode());
            functionModel.setHvacSmartCirculation(this.mHvacBaseVm.getHvacCirculationMode());
            functionModel.setHvacSmartAqsMode(this.mHvacBaseVm.getHvacAqsMode());
            functionModel.setHvacSmartDrvVentMode(this.mHvacBaseVm.getHvacEavDriverWindMode());
            functionModel.setHvacSmartDrvVentPosition(getDrvEAVPosition());
            functionModel.setHvacSmartPsnVentMode(this.mHvacBaseVm.getHvacEavPsnWindMode());
            functionModel.setHvacSmartPsnVentPosition(getPsnEAVPosition());
            if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
                functionModel.setHvacSmartDrvSeatVent(this.mHvacBaseVm.getSeatVentLevel());
            }
            LogUtils.d(TAG, "AUTO:" + this.mHvacBaseVm.isHvacAutoModeOn() + ",AC:" + this.mHvacBaseVm.getAcHeatNatureMode() + ",DrvTemp:" + this.mHvacBaseVm.getHvacDriverTemp() + ",PsnTemp:" + this.mHvacBaseVm.getHvacPsnTemp() + ",speed:" + this.mHvacBaseVm.getHvacWindSpeedLevel() + ",econ:" + this.mHvacBaseVm.getHvacEconMode() + ",blowMode:" + this.mHvacBaseVm.getHvacWindBlowMode() + ",circulation:" + this.mHvacBaseVm.getHvacCirculationMode() + ",Aqs:" + this.mHvacBaseVm.getHvacAqsMode() + ",drvEav:" + this.mHvacBaseVm.getHvacEavDriverWindMode() + ",DrvPosition:" + getDrvEAVPosition() + ",psnEav:" + this.mHvacBaseVm.getHvacEavPsnWindMode() + ",psnPosition:" + getPsnEAVPosition() + ",seatVent:" + this.mHvacBaseVm.getSeatVentLevel(), false);
            return;
        }
        LogUtils.d(TAG, "memoryHvacSmartStatus power is off memory on power on", false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void recoverHvacRapidCooling() {
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$5y0JufYfNma-dpqv80Eg-x5c6-k
            @Override // java.lang.Runnable
            public final void run() {
                HvacSmartControl.this.recoverHvacRapidCoolingDelay();
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void recoverHvacRapidCoolingDelay() {
        int hvacCmd;
        FunctionModel functionModel = FunctionModel.getInstance();
        float hvacSmartDrvTemp = functionModel.getHvacSmartDrvTemp();
        if (hvacSmartDrvTemp != this.mHvacBaseVm.getHvacDriverTemp()) {
            this.mHvacBaseVm.setHvacTempDriver(hvacSmartDrvTemp);
        }
        float hvacSmartPsnTemp = functionModel.getHvacSmartPsnTemp();
        if (hvacSmartPsnTemp != this.mHvacBaseVm.getHvacPsnTemp() && !this.mHvacBaseVm.isHvacDriverSyncMode()) {
            this.mHvacBaseVm.setHvacTempPsn(hvacSmartPsnTemp);
        }
        if (functionModel.getHvacSmartEconMode() != this.mHvacBaseVm.getHvacEconMode()) {
            this.mHvacBaseVm.setHvacEconMode(functionModel.getHvacSmartEconMode());
        }
        if (this.mCarBaseConfig.isSupportDrvSeatVent()) {
            this.mHvacBaseVm.setSeatVentLevel(this.mFunctionModel.getHvacSmartDrvSeatVent());
        }
        if (functionModel.isHvacSmartAutoEnable() && this.mHvacBaseVm.getHvacWindSpeedLevel() == this.mHvacBaseVm.getFanMaxLevel() && this.mHvacBaseVm.getHvacWindBlowMode() == 2 && this.mHvacBaseVm.getAcHeatNatureMode() == 1 && !this.mHvacBaseVm.isHvacFrontDefrostOn()) {
            if (!this.mHvacBaseVm.isHvacAutoModeOn()) {
                this.mHvacBaseVm.setHvacAutoMode(true);
            }
        } else if (!this.mHvacBaseVm.isHvacAutoModeOn() && !this.mHvacBaseVm.isHvacFrontDefrostOn()) {
            int hvacSmartAcPtcMode = functionModel.getHvacSmartAcPtcMode();
            if (this.mHvacBaseVm.getAcHeatNatureMode() != hvacSmartAcPtcMode && (hvacCmd = AcHeatNatureMode.toHvacCmd(AcHeatNatureMode.fromHvacMode(hvacSmartAcPtcMode))) != -1) {
                this.mHvacBaseVm.setAcHeatNatureMode(hvacCmd);
            }
            int hvacSmartFanSpeed = functionModel.getHvacSmartFanSpeed();
            if (hvacSmartFanSpeed != this.mHvacBaseVm.getHvacWindSpeedLevel() && hvacSmartFanSpeed != 14) {
                this.mHvacBaseVm.setHvacWindSpeedLevel(hvacSmartFanSpeed);
            }
            HvacWindBlowMode fromHvacState = HvacWindBlowMode.fromHvacState(functionModel.getHvacSmartBlowMode());
            if (fromHvacState != null) {
                switch (AnonymousClass4.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[fromHvacState.ordinal()]) {
                    case 1:
                        this.mHvacBaseVm.setHvacWindBlowFace();
                        break;
                    case 2:
                        this.mHvacBaseVm.setHvacWindBlowFoot();
                        break;
                    case 3:
                        this.mHvacBaseVm.setHvacWindBlowFaceFoot();
                        break;
                    case 4:
                    case 5:
                        this.mHvacBaseVm.setHvacWindBlowWindow();
                        break;
                    case 6:
                        this.mHvacBaseVm.setHvacWindBlowWinFoot();
                        break;
                }
            }
            HvacCirculationMode fromHvacState2 = HvacCirculationMode.fromHvacState(functionModel.getHvacSmartCirculation());
            HvacCirculationMode fromHvacState3 = HvacCirculationMode.fromHvacState(this.mHvacBaseVm.getHvacCirculationMode());
            if (HvacCirculationMode.Outside == fromHvacState2 && fromHvacState3 != fromHvacState2) {
                this.mHvacBaseVm.setHvacCirculationOut();
            } else if (HvacCirculationMode.Inner == fromHvacState2 && fromHvacState3 != fromHvacState2) {
                this.mHvacBaseVm.setHvacCirculationInner();
            }
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$sBGCexErCV4Vgf_MgzvaA_gbhJ4
            @Override // java.lang.Runnable
            public final void run() {
                HvacSmartControl.this.lambda$recoverHvacRapidCoolingDelay$8$HvacSmartControl();
            }
        }, 300L);
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.HvacSmartControl$4  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacAirAutoProtect;
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode;

        static {
            int[] iArr = new int[HvacWindBlowMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode = iArr;
            try {
                iArr[HvacWindBlowMode.Face.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.Foot.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FaceAndFoot.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.Windshield.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FrontDefrost.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FootWindshield.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[HvacAirAutoProtect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacAirAutoProtect = iArr2;
            try {
                iArr2[HvacAirAutoProtect.Auto.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacAirAutoProtect[HvacAirAutoProtect.Remind.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacAirAutoProtect[HvacAirAutoProtect.Off.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public /* synthetic */ void lambda$recoverHvacRapidCoolingDelay$8$HvacSmartControl() {
        if (this.mHvacBaseVm.getHvacAqsMode() == HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.ON)) {
            this.mHvacBaseVm.setHvacAqsMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.OFF));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void recoverHvacDeodorant() {
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$i4GBBjKgzx77OrgK2l8LAEhYYts
            @Override // java.lang.Runnable
            public final void run() {
                HvacSmartControl.this.recoverHvacDeodorantDelay();
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void recoverHvacDeodorantDelay() {
        final FunctionModel functionModel = FunctionModel.getInstance();
        if (functionModel.getHvacSmartEconMode() != this.mHvacBaseVm.getHvacEconMode()) {
            this.mHvacBaseVm.setHvacEconMode(functionModel.getHvacSmartEconMode());
        }
        recoverEavMode();
        if (functionModel.isHvacSmartAutoEnable() && this.mHvacBaseVm.getHvacWindSpeedLevel() == this.mHvacBaseVm.getFanMaxLevel() && this.mHvacBaseVm.getHvacWindBlowMode() == 2 && this.mHvacBaseVm.getHvacCirculationMode() == 2 && !this.mHvacBaseVm.isHvacFrontDefrostOn()) {
            if (!this.mHvacBaseVm.isHvacAutoModeOn()) {
                this.mHvacBaseVm.setHvacAutoMode(true);
            }
        } else if (!this.mHvacBaseVm.isHvacAutoModeOn() && !this.mHvacBaseVm.isHvacFrontDefrostOn()) {
            int hvacSmartFanSpeed = functionModel.getHvacSmartFanSpeed();
            if (hvacSmartFanSpeed != this.mHvacBaseVm.getHvacWindSpeedLevel() && hvacSmartFanSpeed != 14) {
                this.mHvacBaseVm.setHvacWindSpeedLevel(hvacSmartFanSpeed);
            }
            HvacWindBlowMode fromHvacState = HvacWindBlowMode.fromHvacState(functionModel.getHvacSmartBlowMode());
            if (fromHvacState != null) {
                switch (AnonymousClass4.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[fromHvacState.ordinal()]) {
                    case 1:
                        this.mHvacBaseVm.setHvacWindBlowFace();
                        break;
                    case 2:
                        this.mHvacBaseVm.setHvacWindBlowFoot();
                        break;
                    case 3:
                        this.mHvacBaseVm.setHvacWindBlowFaceFoot();
                        break;
                    case 4:
                    case 5:
                        this.mHvacBaseVm.setHvacWindBlowWindow();
                        break;
                    case 6:
                        this.mHvacBaseVm.setHvacWindBlowWinFoot();
                        break;
                }
            }
            HvacCirculationMode fromHvacState2 = HvacCirculationMode.fromHvacState(functionModel.getHvacSmartCirculation());
            HvacCirculationMode fromHvacState3 = HvacCirculationMode.fromHvacState(this.mHvacBaseVm.getHvacCirculationMode());
            if (HvacCirculationMode.Outside == fromHvacState2 && fromHvacState3 != fromHvacState2) {
                this.mHvacBaseVm.setHvacCirculationOut();
            } else if (HvacCirculationMode.Inner == fromHvacState2 && fromHvacState3 != fromHvacState2) {
                this.mHvacBaseVm.setHvacCirculationInner();
            }
            int hvacSmartAqsMode = functionModel.getHvacSmartAqsMode();
            if (this.mHvacBaseVm.getHvacAqsMode() != hvacSmartAqsMode && HvacSwitchStatus.fromHvacStatus(hvacSmartAqsMode) == HvacSwitchStatus.ON) {
                this.mHvacBaseVm.setHvacAqsMode(HvacSwitchStatus.toHvacCmd(HvacSwitchStatus.ON));
            }
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$5R0Rwqi3FniP-eS6PxbdBZVAdKM
            @Override // java.lang.Runnable
            public final void run() {
                HvacSmartControl.this.lambda$recoverHvacDeodorantDelay$9$HvacSmartControl(functionModel);
            }
        }, 300L);
    }

    public /* synthetic */ void lambda$recoverHvacDeodorantDelay$9$HvacSmartControl(final FunctionModel functionModel) {
        int hvacSmartAqsMode = functionModel.getHvacSmartAqsMode();
        if (this.mHvacBaseVm.getHvacAqsMode() != hvacSmartAqsMode) {
            this.mHvacBaseVm.setHvacAqsMode(hvacSmartAqsMode);
        }
    }

    private String getDrvEAVPosition() {
        return this.mHvacBaseVm.getHvacEAVDriverLeftHPos() + "," + this.mHvacBaseVm.getHvacEAVDriverLeftVPos() + "," + this.mHvacBaseVm.getHvacEAVDriverRightHPos() + "," + this.mHvacBaseVm.getHvacEAVDriverRightVPos();
    }

    private String getPsnEAVPosition() {
        return this.mHvacBaseVm.getHvacEAVPsnLeftHPos() + "," + this.mHvacBaseVm.getHvacEAVPsnLeftVPos() + "," + this.mHvacBaseVm.getHvacEAVPsnRightHPos() + "," + this.mHvacBaseVm.getHvacEAVPsnRightVPos();
    }

    public void resetSmartMode() {
        if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            return;
        }
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable()) {
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
            this.mNeedRecoverRapidCooling = true;
            this.mHvacBaseVm.setSeatVentLevel(0);
            FunctionModel.getInstance().setHvacRapidCoolingTime(0L);
            stopHvacRapidCoolingCountDownDelay();
        }
        if (this.mHvacBaseVm.isHvacDeodorantEnable()) {
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
            this.mNeedRecoverDeodorant = true;
            FunctionModel.getInstance().setHvacDeodorantTime(0L);
            stopHvacDeodorantCountdownDelay();
        }
        resetAirPurge();
        LogUtils.d(TAG, "resetSmartMode" + this.mNeedRecoverRapidCooling + ",mNeedRecoverDeodorant:" + this.mNeedRecoverDeodorant, false);
    }

    protected void resetAirPurge() {
        if (this.mHvacBaseVm.isHvacQualityPurgeEnable()) {
            this.mHvacBaseVm.setHvacQualityPurgeMode(false);
        }
    }

    public void onCirculationModeChanged(int mode) {
        HvacCirculationMode fromHvacState = HvacCirculationMode.fromHvacState(mode);
        LogUtils.d(TAG, "hvacCirculationMode:" + fromHvacState, false);
        if (this.mHvacBaseVm.isHvacDeodorantEnable() && isDeodorantCountdown() && HvacCirculationMode.Outside != fromHvacState) {
            FunctionModel.getInstance().setHvacSmartCirculation(mode);
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
        }
    }

    public void onWindBlowModeChanged(int mode) {
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown() && mode != 2) {
            FunctionModel.getInstance().setHvacSmartBlowMode(mode);
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
        }
        if (this.mHvacBaseVm.isHvacDeodorantEnable() && isDeodorantCountdown() && mode != 2) {
            FunctionModel.getInstance().setHvacSmartBlowMode(mode);
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
        }
    }

    public void onAirAualityPurgeChanged(boolean enable) {
        if (enable) {
            this.mAirPurgeControl.enterAirPurge();
        } else {
            this.mAirPurgeControl.exitAirPurge();
        }
    }

    public void onWindSpeedLevelChange(int level) {
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown() && level != this.mHvacBaseVm.getFanMaxLevel()) {
            FunctionModel.getInstance().setHvacSmartFanSpeed(level);
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
        }
        if (this.mHvacBaseVm.isHvacDeodorantEnable() && isDeodorantCountdown() && level != this.mHvacBaseVm.getFanMaxLevel()) {
            FunctionModel.getInstance().setHvacSmartFanSpeed(level);
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
        }
    }

    public void stopHvacSmartMode() {
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown()) {
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
        }
        if (this.mHvacBaseVm.isHvacDeodorantEnable() && isDeodorantCountdown()) {
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
        }
    }

    public void resetHvacVentStatus() {
        if (!this.mHvacBaseVm.isHvacVentOpen(0)) {
            this.mHvacBaseVm.setHvacVentStatus(0, true, false);
        }
        if (!this.mHvacBaseVm.isHvacVentOpen(1)) {
            this.mHvacBaseVm.setHvacVentStatus(1, true, false);
        }
        if (!this.mHvacBaseVm.isHvacVentOpen(2)) {
            this.mHvacBaseVm.setHvacVentStatus(2, true, false);
        }
        if (this.mHvacBaseVm.isHvacVentOpen(3)) {
            return;
        }
        this.mHvacBaseVm.setHvacVentStatus(3, true, false);
    }

    public boolean isRapidCoolingCountdown() {
        LogUtils.d(TAG, "System.currentTimeMillis():" + System.currentTimeMillis() + ",mRapidCoolingTime:" + this.mRapidCoolingTime, false);
        return System.currentTimeMillis() - this.mRapidCoolingTime > 1000;
    }

    public boolean isDeodorantCountdown() {
        LogUtils.d(TAG, "System.currentTimeMillis():" + System.currentTimeMillis() + ",mDeodorantTime:" + this.mDeodorantTime, false);
        return System.currentTimeMillis() - this.mDeodorantTime > 1000;
    }

    void hvacRapidCoolingStatusCheck() {
        boolean z;
        boolean z2 = true;
        if (this.mHvacBaseVm.getAcHeatNatureMode() != 1) {
            this.mFunctionModel.setHvacSmartAcPtcMode(this.mHvacBaseVm.getAcHeatNatureMode());
            z = true;
        } else {
            z = false;
        }
        if (this.mHvacBaseVm.getHvacDriverTemp() != 18.0f) {
            this.mFunctionModel.setHvacSmartDrvTemp(this.mHvacBaseVm.getHvacDriverTemp());
            z = true;
        }
        if (this.mHvacBaseVm.getHvacPsnTemp() != 18.0f) {
            this.mFunctionModel.setHvacSmartPsnTemp(this.mHvacBaseVm.getHvacPsnTemp());
        } else {
            z2 = z;
        }
        if (z2 || isSmartModeStatusError()) {
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
            LogUtils.i(TAG, "hvacRapidCoolingStatusCheck status error", false);
        }
    }

    void hvacDeodorantStatusCheck() {
        if (isSmartModeStatusError() || HvacCirculationMode.Outside != HvacCirculationMode.fromHvacState(this.mHvacBaseVm.getHvacCirculationMode())) {
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
            LogUtils.i(TAG, "hvacDeodorantStatusCheck status error", false);
        }
    }

    boolean isSmartModeStatusError() {
        boolean z;
        if (this.mHvacBaseVm.getHvacWindSpeedLevel() != this.mHvacBaseVm.getFanMaxLevel()) {
            this.mFunctionModel.setHvacSmartFanSpeed(this.mHvacBaseVm.getHvacWindSpeedLevel());
            z = true;
        } else {
            z = false;
        }
        if (HvacWindBlowMode.FaceAndFoot != HvacWindBlowMode.fromHvacState(this.mHvacBaseVm.getHvacWindBlowMode())) {
            this.mFunctionModel.setHvacSmartBlowMode(this.mHvacBaseVm.getHvacWindBlowMode());
            z = true;
        }
        return (!z && this.mHvacBaseVm.isHvacPowerModeOn() && !this.mHvacBaseVm.isHvacAutoModeOn() && !this.mHvacBaseVm.isHvacFrontDefrostOn() && 1 != this.mHvacBaseVm.getHvacEconMode() && this.mHvacBaseVm.isHvacVentOpen(0) && this.mHvacBaseVm.isHvacVentOpen(1) && this.mHvacBaseVm.isHvacVentOpen(2) && this.mHvacBaseVm.isHvacVentOpen(3)) ? false : true;
    }

    public synchronized void invokeHvacSingleMode(boolean isLocalIgOn) {
        if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            return;
        }
        if (!BaseFeatureOption.getInstance().isSupportSingleMode()) {
            LogUtils.i(TAG, "car type not support single mode");
            return;
        }
        String str = TAG;
        LogUtils.i(str, "singleMode:" + this.mHvacBaseVm.isHvacSingleMode() + "invokeHvacSingleMode:" + this.mHvacBaseVm.isHvacPowerModeOn() + ",HVAC_SINGLE_MODE_ENABLE:" + HVAC_SINGLE_MODE_ENABLE + ",isLocalIgOn:" + isLocalIgOn + ",rapidCooling:" + this.mHvacBaseVm.isHvacRapidCoolingEnable() + ",deodorant:" + this.mHvacBaseVm.isHvacDeodorantEnable() + ",psnOccupied:" + this.mSeatViewModel.isPsnSeatOccupied(), false);
        if (this.mHvacBaseVm.isHvacSingleMode() && this.mHvacBaseVm.isHvacPowerModeOn() && HVAC_SINGLE_MODE_ENABLE && isLocalIgOn && !this.mHvacBaseVm.isHvacRapidCoolingEnable() && !this.mHvacBaseVm.isHvacDeodorantEnable()) {
            if (this.mSeatViewModel.isPsnSeatOccupied()) {
                LogUtils.i(str, "single mode open psn vent", false);
                removeSingleModeRunnable();
                ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.hvac.-$$Lambda$HvacSmartControl$mZlYAbma5IA0ehUM6c3kRxAnFQM
                    @Override // java.lang.Runnable
                    public final void run() {
                        HvacSmartControl.this.lambda$invokeHvacSingleMode$10$HvacSmartControl();
                    }
                }, 200L);
            } else {
                LogUtils.i(str, "single mode delay close psn vent", false);
                removeSingleModeRunnable();
                if (this.mHvacBaseVm.isHvacVentOpen(2) || this.mHvacBaseVm.isHvacVentOpen(3)) {
                    ThreadUtils.postDelayed(2, this.singleModeCloseRun, 30000L);
                }
            }
        } else {
            removeSingleModeRunnable();
        }
    }

    public /* synthetic */ void lambda$invokeHvacSingleMode$10$HvacSmartControl() {
        this.mHvacBaseVm.setHvacVentStatus(2, true, false);
        this.mHvacBaseVm.setHvacVentStatus(3, true, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hvacSingleModeClosePsnVent() {
        if (this.mHvacBaseVm.isHvacSingleMode() && this.mHvacBaseVm.isHvacPowerModeOn() && !this.mSeatViewModel.isPsnSeatOccupied() && !this.mHvacBaseVm.isHvacRapidCoolingEnable() && !this.mHvacBaseVm.isHvacDeodorantEnable() && (this.mHvacBaseVm.isHvacVentOpen(2) || this.mHvacBaseVm.isHvacVentOpen(3))) {
            this.mHvacBaseVm.enterHvacSingleMode();
        } else {
            LogUtils.i(TAG, "close vent failed", false);
        }
    }

    public void removeSingleModeRunnable() {
        if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            return;
        }
        ThreadUtils.removeRunnable(this.singleModeCloseRun);
    }

    public synchronized void executeHvacDrvTempSync() {
        if (BaseFeatureOption.getInstance().isSupportSmartOSFive()) {
            return;
        }
        ThreadUtils.removeRunnable(this.drvTempSyncRun);
        ThreadUtils.postDelayed(2, this.drvTempSyncRun, 500L);
    }

    public void onFrontDefrostChange(boolean enable) {
        if (enable) {
            if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown()) {
                this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
                LogUtils.d(TAG, "onHvacFrontDefrostChanged rapid cooling");
            }
            if (this.mHvacBaseVm.isHvacDeodorantEnable() && isDeodorantCountdown()) {
                this.mHvacBaseVm.setHvacDeodorantEnable(false);
                LogUtils.d(TAG, "onHvacFrontDefrostChanged deodorant");
            }
        }
        if (enable && this.mHvacBaseVm.isHvacQualityPurgeEnable()) {
            this.mHvacBaseVm.setHvacQualityPurgeMode(false);
        }
    }

    public void onHvacAutoModeChange(Boolean enabled) {
        if (enabled.booleanValue()) {
            if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown()) {
                this.mFunctionModel.setHvacSmartAutoEnable(true);
                this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
                LogUtils.d(TAG, "onHvacAutoModeChanged rapid cooling:");
            }
            if (this.mHvacBaseVm.isHvacDeodorantEnable() && isDeodorantCountdown()) {
                this.mFunctionModel.setHvacSmartAutoEnable(true);
                this.mHvacBaseVm.setHvacDeodorantEnable(false);
                LogUtils.d(TAG, "onHvacAutoModeChanged deodorant:");
            }
        }
    }

    public void onHvacDrvTempChange(float temp) {
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown() && 18.0f != temp) {
            if (this.mHvacBaseVm.isHvacPsnSyncMode()) {
                this.mFunctionModel.setHvacSmartPsnTemp(temp);
            }
            this.mFunctionModel.setHvacSmartDrvTemp(temp);
            LogUtils.d(TAG, "onHvacTempDrvChanged:" + temp);
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
        }
    }

    public void onHvacPsnTempChange(float temp) {
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown() && 18.0f != temp) {
            if (this.mHvacBaseVm.isHvacDriverSyncMode()) {
                this.mFunctionModel.setHvacSmartDrvTemp(temp);
            }
            this.mFunctionModel.setHvacSmartPsnTemp(temp);
            LogUtils.d(TAG, "onHvacTempPsnChanged:" + temp);
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
        }
    }

    public void onHvacEconModeChange(int status) {
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && status == 1 && isRapidCoolingCountdown()) {
            this.mFunctionModel.setHvacSmartEconMode(status);
            LogUtils.d(TAG, "onHvacEconModeChange rapid cooling:" + status);
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
        }
        if (this.mHvacBaseVm.isHvacDeodorantEnable() && isDeodorantCountdown() && status == 1) {
            this.mFunctionModel.setHvacSmartEconMode(status);
            LogUtils.d(TAG, "onHvacEconModeChange deodorant:" + status);
            this.mHvacBaseVm.setHvacDeodorantEnable(false);
        }
    }

    public void onAcHeatNatureModeChange(int mode) {
        if (this.mHvacBaseVm.isHvacRapidCoolingEnable() && isRapidCoolingCountdown() && 1 != mode) {
            this.mFunctionModel.setHvacSmartAcPtcMode(mode);
            LogUtils.d(TAG, "onHvacAcHeatNatureChanged:" + mode);
            this.mHvacBaseVm.setHvacRapidCoolingEnable(false);
        }
    }

    public int getAirProtectRemindNoId() {
        return BaseFeatureOption.getInstance().isSupportIpcModule() ? NotificationHelper.D21_SCENE_XFREE_BREATH : NotificationHelper.SCENE_XFREE_BREATH;
    }

    public boolean isLocalIgOn() {
        return this.mMcuController.getIgStatusFromMcu() == 1;
    }
}
