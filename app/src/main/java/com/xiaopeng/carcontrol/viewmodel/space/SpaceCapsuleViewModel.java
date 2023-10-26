package com.xiaopeng.carcontrol.viewmodel.space;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.CapsuleMusicService;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.IHvacController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.carmanager.controller.ITboxController;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.AlarmHelper;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.util.Utils;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.libtheme.ThemeController;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xuimanager.XUIServiceNotConnectedException;
import com.xiaopeng.xuimanager.mediacenter.MediaCenterManager;
import java.util.Date;

/* loaded from: classes2.dex */
public class SpaceCapsuleViewModel implements ISpaceCapsuleViewModel {
    public static final String CONNECTED_BLE_DEVICE = "connected_ble_device";
    private static final int DAY_NIGHT_DELAY = 2000;
    private static final String MEDITATION_COMPONENT = "com.xiaopeng.meditation.media.MeditationMediaService";
    public static final String MEDITATION_PACKAGE = "com.xiaopeng.homespace.meditation";
    private static final int MUSIC_COUNT_DOWN_TIME_INTERVAL = 30000;
    public static final String SLEEP_CAPSULE_PACKAGE = "com.xiaopeng.homespace.capsule";
    private static final int SLEEP_OVER_TIME_REMIND_DELAY = 28800000;
    public static final int SPEECH_END = 2;
    public static final int SPEECH_START = 1;
    private static final String TAG = "SpaceCapsuleViewModel";
    private String from;
    private final AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;
    private AudioManager mAudioManager;
    private IBcmController mBcmController;
    private final MutableLiveData<String> mBtDeviceNameData;
    private ContentObserver mBtDeviceNameObserver;
    MediaCenterManager.BTStatusListener mBtStatusListener;
    private final ServiceConnection mConnection;
    ICountdownTimeListener mCountdownTimeListener;
    private ContentObserver mDayNightObserver;
    private Handler mHandler;
    private IHvacController mHvacController;
    private CapsuleMusicService.CapsuleMusicListener mListener;
    private IMcuController.Callback mMcuCallback;
    private IMcuController mMcuController;
    private CapsuleMusicService mMusicService;
    private final MutableLiveData<VipSeatStatus> mRLSeatStatus;
    private final MutableLiveData<VipSeatStatus> mRRSeatStatus;
    private final MutableLiveData<VipSeatStatus> mRearSeatStatus;
    private Handler mSleepMsgHandler;
    private final MutableLiveData<VipSeatStatus> mSleepSecSeatStatus;
    private final SpaceCapsuleControl mSpaceCapsuleControl;
    private final MutableLiveData<Integer> mStartMoveStatus;
    ITboxController.Callback mTboxCallBack;
    private final ITboxController mTboxController;
    BroadcastReceiver mTimeReceiver;
    private final Runnable mTimeUpdateRunnable;
    private Timer mTimer;
    private Handler mTimerHandler;
    private final VipCapsuleSeatControl mVipCapsuleSeatControl;
    private final MutableLiveData<VipSeatStatus> mVipCapsuleSeatStatus;
    private final VipCapsuleSingleSeatControl mVipCapsuleSingleSeatControl;
    private final MutableLiveData<VipSeatStatus> mVipCapsuleSingleSeatStatus;
    private final VipDrvSeatControl mVipDrvSeatControl;
    private final MutableLiveData<VipSeatStatus> mVipDrvSeatStatus;
    private final VipPsnSeatControl mVipPsnSeatControl;
    private final MutableLiveData<VipSeatStatus> mVipPsnSeatStatus;
    private final VipRLSeatControl mVipRLSeatControl;
    private final VipRRSeatControl mVipRRSeatControl;
    private final VipCapsuleRearSeatControl mVipRearSeatControl;
    private final VipSleepSecSeatControl mVipSleepSecSeatControl;
    private MutableLiveData<String> mDatetimeModelMutableLiveData = new MutableLiveData<>();
    private String mTime = "";
    private IntentFilter mTimeIntentFilter = new IntentFilter();
    private boolean mIsAlreadyRegister = false;
    private boolean mIsRestoreAutoPowerConfig = false;
    private boolean mDayNightSwitch = false;
    private int mCurrentSubMode = -1;
    private int mSunShadeSavedPos = -1;
    private boolean mIsOpenSfs = false;
    private boolean mIsChangedDayNight = false;
    private boolean mMoveFlat = false;
    private boolean mServiceConnected = false;
    private int mSleepMode = 0;
    private final int MAX_RETRY_DAYNIGHT = 10;
    private int mDayNightRetryCount = 0;
    private int mDayNightRestoreRetry = 0;
    private final Runnable mDayNightChange2NightRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$5nECYbvaIdAvDt6Ru4F4KRYUBfg
        @Override // java.lang.Runnable
        public final void run() {
            SpaceCapsuleViewModel.this.changeDay2NightTry();
        }
    };
    private final Runnable mDayNightChangeRestoreRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$ewfBj_RzSIV3LlOuyPWffeGvWd0
        @Override // java.lang.Runnable
        public final void run() {
            SpaceCapsuleViewModel.this.restoreDayNightTry();
        }
    };
    private final Runnable mCapsuleSleepOverTimeRemindRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$SpaceCapsuleViewModel$Z_n-KT_hDTKmYgezbc7dPqT5fGQ
        @Override // java.lang.Runnable
        public final void run() {
            SpaceCapsuleViewModel.this.doCapsuleSleepOverTimeRemind();
        }
    };
    private final MutableLiveData<Boolean> mIgOffStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSpeechStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> mA2dpConnectLiveData = new MutableLiveData<>();

    /* loaded from: classes2.dex */
    public interface ICountdownTimeListener {
        void onTimeStop();

        void onUpdateTime(long remainingTime);
    }

    private int getBatteryLevel(int elecPercent) {
        if (elecPercent <= 0) {
            return 0;
        }
        if (elecPercent < 1 || elecPercent >= 11) {
            if (elecPercent < 11 || elecPercent >= 21) {
                if (elecPercent < 21 || elecPercent >= 40) {
                    if (elecPercent < 40 || elecPercent >= 50) {
                        if (elecPercent < 50 || elecPercent >= 60) {
                            if (elecPercent < 60 || elecPercent >= 70) {
                                if (elecPercent < 70 || elecPercent >= 80) {
                                    if (elecPercent < 80 || elecPercent >= 90) {
                                        return (elecPercent < 90 || elecPercent >= 98) ? 10 : 9;
                                    }
                                    return 8;
                                }
                                return 7;
                            }
                            return 6;
                        }
                        return 5;
                    }
                    return 4;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public boolean isSourceInBt(int status) {
        return status == 6;
    }

    public void setMusicListener(CapsuleMusicService.CapsuleMusicListener listener) {
        this.mListener = listener;
    }

    public SpaceCapsuleViewModel() {
        final MutableLiveData<VipSeatStatus> mutableLiveData = new MutableLiveData<>();
        this.mVipDrvSeatStatus = mutableLiveData;
        final MutableLiveData<VipSeatStatus> mutableLiveData2 = new MutableLiveData<>();
        this.mVipPsnSeatStatus = mutableLiveData2;
        MutableLiveData<VipSeatStatus> mutableLiveData3 = new MutableLiveData<>();
        this.mVipCapsuleSeatStatus = mutableLiveData3;
        this.mVipCapsuleSingleSeatStatus = new MutableLiveData<>();
        final MutableLiveData<VipSeatStatus> mutableLiveData4 = new MutableLiveData<>();
        this.mRLSeatStatus = mutableLiveData4;
        final MutableLiveData<VipSeatStatus> mutableLiveData5 = new MutableLiveData<>();
        this.mRRSeatStatus = mutableLiveData5;
        final MutableLiveData<VipSeatStatus> mutableLiveData6 = new MutableLiveData<>();
        this.mRearSeatStatus = mutableLiveData6;
        final MutableLiveData<VipSeatStatus> mutableLiveData7 = new MutableLiveData<>();
        this.mSleepSecSeatStatus = mutableLiveData7;
        this.mStartMoveStatus = new MutableLiveData<>();
        this.mBtDeviceNameData = new MutableLiveData<>();
        this.mMcuCallback = new IMcuController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.1
            @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
            public void onIgStatusChanged(int state) {
                SpaceCapsuleViewModel.this.mIgOffStatusLiveData.postValue(Boolean.valueOf(state == 0));
            }
        };
        this.from = "";
        this.mConnection = new ServiceConnection() { // from class: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtils.d(SpaceCapsuleViewModel.TAG, "onServiceConnected - name " + name);
                if (!BaseFeatureOption.getInstance().isMeditationIndependent() || SpaceCapsuleViewModel.this.from.equals(SpaceCapsuleViewModel.SLEEP_CAPSULE_PACKAGE)) {
                    SpaceCapsuleViewModel.this.mMusicService = ((CapsuleMusicService.CapsuleMusicServiceBinder) service).getService();
                    LogUtils.i(SpaceCapsuleViewModel.TAG, "Connect music player service.");
                    SpaceCapsuleViewModel.this.mMusicService.setMusicListener(SpaceCapsuleViewModel.this.mListener);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                LogUtils.i(SpaceCapsuleViewModel.TAG, "Disconnect music player service.");
            }
        };
        this.mTimeReceiver = new BroadcastReceiver() { // from class: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                LogUtils.d(SpaceCapsuleViewModel.TAG, "xpdisplay time intent:" + intent);
                SpaceCapsuleViewModel.this.setDateAndTime();
            }
        };
        this.mTboxCallBack = new ITboxController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.6
            @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController.Callback
            public void onSoldierStateChanged(int status) {
            }

            @Override // com.xiaopeng.carcontrol.carmanager.controller.ITboxController.Callback
            public void onAutoPowerOffConfigStatusChanged(boolean status) {
                LogUtils.d(SpaceCapsuleViewModel.TAG, "auto power off config status:" + status);
            }
        };
        this.mBtStatusListener = new MediaCenterManager.BTStatusListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.7
            public void onBtStatusChanged(int status) {
                LogUtils.i(SpaceCapsuleViewModel.TAG, "mBtStatusListener onBtStatusChanged:" + status);
                SpaceCapsuleViewModel.this.mA2dpConnectLiveData.postValue(Integer.valueOf(status));
            }
        };
        this.mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$SpaceCapsuleViewModel$SEpI13X0sX99ixcHc0jl0ME7e6M
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i) {
                LogUtils.i(SpaceCapsuleViewModel.TAG, "requestAudioFocus: " + i, false);
            }
        };
        this.mTimeUpdateRunnable = new TimeUpdateRunnable();
        this.mTimeIntentFilter.addAction("android.intent.action.TIME_TICK");
        this.mTimeIntentFilter.addAction("android.intent.action.TIME_SET");
        this.mTimeIntentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        VipDrvSeatControl vipDrvSeatControl = VipDrvSeatControl.getInstance();
        this.mVipDrvSeatControl = vipDrvSeatControl;
        vipDrvSeatControl.setOnStatusChangedListener(new VipSeatControl.OnStatusChangedListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$SpaceCapsuleViewModel$BUOF95ThcwFO7LR0WH5eu76CN-A
            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public final void onStatusChanged(VipSeatStatus vipSeatStatus) {
                MutableLiveData.this.postValue(vipSeatStatus);
            }
        });
        VipPsnSeatControl vipPsnSeatControl = VipPsnSeatControl.getInstance();
        this.mVipPsnSeatControl = vipPsnSeatControl;
        vipPsnSeatControl.setOnStatusChangedListener(new VipSeatControl.OnStatusChangedListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$SpaceCapsuleViewModel$BUOF95ThcwFO7LR0WH5eu76CN-A
            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public final void onStatusChanged(VipSeatStatus vipSeatStatus) {
                MutableLiveData.this.postValue(vipSeatStatus);
            }
        });
        VipCapsuleSeatControl vipCapsuleSeatControl = VipCapsuleSeatControl.getInstance();
        this.mVipCapsuleSeatControl = vipCapsuleSeatControl;
        vipCapsuleSeatControl.setOnStatusChangedListener(new VipSeatControl.OnStatusChangedListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.3
            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public void onStatusChanged(VipSeatStatus vipSeatStatus) {
                SpaceCapsuleViewModel.this.mVipCapsuleSeatStatus.postValue(vipSeatStatus);
                if (vipSeatStatus == VipSeatStatus.Normal) {
                    SpaceCapsuleViewModel.this.mMoveFlat = false;
                } else if (vipSeatStatus == VipSeatStatus.Flat) {
                    SpaceCapsuleViewModel.this.mMoveFlat = true;
                }
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public void onSeatStartMove(boolean isLayFlat) {
                LogUtils.d(SpaceCapsuleViewModel.TAG, "onSeatStartMove, isLayFlat: " + isLayFlat + ", mMoveFlat: " + SpaceCapsuleViewModel.this.mMoveFlat, false);
                if (!isLayFlat || SpaceCapsuleViewModel.this.mMoveFlat) {
                    if (isLayFlat || !SpaceCapsuleViewModel.this.mMoveFlat) {
                        return;
                    }
                    SpaceCapsuleViewModel.this.mMoveFlat = false;
                    SpaceCapsuleViewModel.this.mStartMoveStatus.postValue(2);
                    SpaceCapsuleViewModel.this.restoreSpaceCapsuleSettings();
                    SpaceCapsuleViewModel.this.mStartMoveStatus.postValue(null);
                    return;
                }
                SpaceCapsuleViewModel.this.mMoveFlat = true;
                SpaceCapsuleViewModel.this.mStartMoveStatus.postValue(1);
                SpaceCapsuleViewModel.this.executeSpaceCapsuleSettings();
                SpaceCapsuleViewModel.this.mStartMoveStatus.postValue(null);
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public void onPlayLayFlatWaitingTTS() {
                LogUtils.d(SpaceCapsuleViewModel.TAG, "onPlayLayFlatWaitingTTS", false);
                SpeechHelper.getInstance().speak(ResUtils.getString(SpaceCapsuleViewModel.this.mCurrentSubMode == 3 ? R.string.start_space_capsule_sleep_tts : R.string.start_space_capsule_cinema_tts), true);
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public void onSeatHasFlat() {
                SpaceCapsuleViewModel.this.executeSpaceCapsuleSettings();
            }
        });
        VipCapsuleSingleSeatControl vipCapsuleSingleSeatControl = VipCapsuleSingleSeatControl.getInstance();
        this.mVipCapsuleSingleSeatControl = vipCapsuleSingleSeatControl;
        vipCapsuleSingleSeatControl.setOnStatusChangedListener(new VipSeatControl.OnStatusChangedListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.4
            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public void onStatusChanged(VipSeatStatus vipSeatStatus) {
                SpaceCapsuleViewModel.this.mVipCapsuleSingleSeatStatus.postValue(vipSeatStatus);
                if (vipSeatStatus == VipSeatStatus.Normal) {
                    SpaceCapsuleViewModel.this.mMoveFlat = false;
                } else if (vipSeatStatus == VipSeatStatus.Flat) {
                    SpaceCapsuleViewModel.this.mMoveFlat = true;
                }
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public void onSeatStartMove(boolean isLayFlat) {
                LogUtils.d(SpaceCapsuleViewModel.TAG, "onSeatStartMove, isLayFlat: " + isLayFlat + ", mMoveFlat: " + SpaceCapsuleViewModel.this.mMoveFlat, false);
                if (!isLayFlat || SpaceCapsuleViewModel.this.mMoveFlat) {
                    if (isLayFlat || !SpaceCapsuleViewModel.this.mMoveFlat) {
                        return;
                    }
                    SpaceCapsuleViewModel.this.mMoveFlat = false;
                    SpaceCapsuleViewModel.this.mStartMoveStatus.postValue(2);
                    SpaceCapsuleViewModel.this.restoreSpaceCapsuleSettings();
                    SpaceCapsuleViewModel.this.mStartMoveStatus.postValue(null);
                    return;
                }
                SpaceCapsuleViewModel.this.mMoveFlat = true;
                SpaceCapsuleViewModel.this.mStartMoveStatus.postValue(1);
                SpaceCapsuleViewModel.this.executeSpaceCapsuleSettings();
                SpaceCapsuleViewModel.this.mStartMoveStatus.postValue(null);
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public void onPlayLayFlatWaitingTTS() {
                LogUtils.d(SpaceCapsuleViewModel.TAG, "onPlayLayFlatWaitingTTS", false);
                SpeechHelper.getInstance().speak(ResUtils.getString(SpaceCapsuleViewModel.this.mCurrentSubMode == 3 ? R.string.start_space_capsule_sleep_tts : R.string.start_space_capsule_cinema_tts), true);
            }

            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public void onSeatHasFlat() {
                SpaceCapsuleViewModel.this.executeSpaceCapsuleSettings();
            }
        });
        VipRLSeatControl vipRLSeatControl = VipRLSeatControl.getInstance();
        this.mVipRLSeatControl = vipRLSeatControl;
        vipRLSeatControl.setOnStatusChangedListener(new VipSeatControl.OnStatusChangedListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$SpaceCapsuleViewModel$BUOF95ThcwFO7LR0WH5eu76CN-A
            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public final void onStatusChanged(VipSeatStatus vipSeatStatus) {
                MutableLiveData.this.postValue(vipSeatStatus);
            }
        });
        VipRRSeatControl vipRRSeatControl = VipRRSeatControl.getInstance();
        this.mVipRRSeatControl = vipRRSeatControl;
        vipRRSeatControl.setOnStatusChangedListener(new VipSeatControl.OnStatusChangedListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$SpaceCapsuleViewModel$BUOF95ThcwFO7LR0WH5eu76CN-A
            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public final void onStatusChanged(VipSeatStatus vipSeatStatus) {
                MutableLiveData.this.postValue(vipSeatStatus);
            }
        });
        VipCapsuleRearSeatControl vipCapsuleRearSeatControl = VipCapsuleRearSeatControl.getInstance();
        this.mVipRearSeatControl = vipCapsuleRearSeatControl;
        vipCapsuleRearSeatControl.setOnStatusChangedListener(new VipSeatControl.OnStatusChangedListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$SpaceCapsuleViewModel$BUOF95ThcwFO7LR0WH5eu76CN-A
            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public final void onStatusChanged(VipSeatStatus vipSeatStatus) {
                MutableLiveData.this.postValue(vipSeatStatus);
            }
        });
        VipSleepSecSeatControl vipSleepSecSeatControl = VipSleepSecSeatControl.getInstance();
        this.mVipSleepSecSeatControl = vipSleepSecSeatControl;
        vipSleepSecSeatControl.setOnStatusChangedListener(new VipSeatControl.OnStatusChangedListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$SpaceCapsuleViewModel$BUOF95ThcwFO7LR0WH5eu76CN-A
            @Override // com.xiaopeng.carcontrol.viewmodel.space.VipSeatControl.OnStatusChangedListener
            public final void onStatusChanged(VipSeatStatus vipSeatStatus) {
                MutableLiveData.this.postValue(vipSeatStatus);
            }
        });
        mutableLiveData.postValue(VipSeatStatus.Normal);
        mutableLiveData2.postValue(VipSeatStatus.Normal);
        mutableLiveData3.postValue(VipSeatStatus.Normal);
        mutableLiveData4.postValue(VipSeatStatus.Normal);
        mutableLiveData5.postValue(VipSeatStatus.Normal);
        this.mSpaceCapsuleControl = SpaceCapsuleControl.getInstance();
        ITboxController iTboxController = (ITboxController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_TBOX_SERVICE);
        this.mTboxController = iTboxController;
        iTboxController.registerCallback(this.mTboxCallBack);
        IMcuController iMcuController = (IMcuController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_MCU_SERVICE);
        this.mMcuController = iMcuController;
        iMcuController.registerCallback(this.mMcuCallback);
        this.mBcmController = (IBcmController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mHvacController = (IHvacController) CarClientWrapper.getInstance().getController("hvac");
        this.mAudioManager = (AudioManager) App.getInstance().getApplicationContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
    }

    public MutableLiveData<String> getDatetimeModelMutableLiveData() {
        return this.mDatetimeModelMutableLiveData;
    }

    public MutableLiveData<Integer> getSpeechStatus() {
        return this.mSpeechStatus;
    }

    public MutableLiveData<Boolean> getIgOffStatusLiveData() {
        return this.mIgOffStatusLiveData;
    }

    public void onCreate() {
        registerTimeReceiver();
        setDateAndTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDateAndTime() {
        String format = DateFormat.getTimeFormat(App.getInstance().getApplicationContext()).format(new Date());
        this.mTime = format;
        this.mDatetimeModelMutableLiveData.postValue(format);
    }

    private void registerTimeReceiver() {
        if (this.mIsAlreadyRegister) {
            return;
        }
        App.getInstance().getApplicationContext().registerReceiver(this.mTimeReceiver, this.mTimeIntentFilter, null, null);
        this.mIsAlreadyRegister = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeSpaceCapsuleSettings() {
        openHvacAuto();
        enterSpaceSunShadeMode();
    }

    public void restoreSpaceCapsuleSettings() {
        exitSpaceSunShadeMode();
    }

    private void openHvacAuto() {
        if (this.mHvacController.isHvacAutoModeOn()) {
            return;
        }
        this.mHvacController.setHvacAutoMode(true);
    }

    private void enterSpaceSunShadeMode() {
        if (this.mSunShadeSavedPos == -1) {
            this.mSunShadeSavedPos = this.mBcmController.getSunShadePos();
            if (!this.mBcmController.isSunShadeInitialized()) {
                NotificationHelper.getInstance().showToast(R.string.sun_shade_initializing);
                return;
            } else if (this.mBcmController.isSunShadeAntiPinch() || this.mBcmController.isSunShadeIceBreak()) {
                NotificationHelper.getInstance().showToast(R.string.sun_shade_block);
                return;
            } else {
                this.mBcmController.controlSunShade(1);
            }
        }
        LogUtils.d(TAG, "enterSpaceSunShadeMode, mSunShadeSavedPos: " + this.mSunShadeSavedPos, false);
    }

    private void exitSpaceSunShadeMode() {
        if (this.mSunShadeSavedPos != -1) {
            this.mSunShadeSavedPos = -1;
            if (!this.mBcmController.isSunShadeInitialized()) {
                NotificationHelper.getInstance().showToast(R.string.sun_shade_initializing);
            } else if (this.mBcmController.isSunShadeHotProtect()) {
                NotificationHelper.getInstance().showToast(R.string.sun_shade_hot_protect);
            } else if (this.mBcmController.isSunShadeAntiPinch() || this.mBcmController.isSunShadeIceBreak()) {
                NotificationHelper.getInstance().showToast(R.string.sun_shade_block);
            } else {
                LogUtils.d(TAG, "exitSpaceSunShadeMode, setSunShadePos:" + this.mSunShadeSavedPos, false);
                this.mBcmController.setSunShadePos(this.mSunShadeSavedPos);
            }
        }
    }

    public void onDestroy() {
        unregisterTimeReceiver();
    }

    private void unregisterTimeReceiver() {
        if (this.mIsAlreadyRegister) {
            App.getInstance().getApplicationContext().unregisterReceiver(this.mTimeReceiver);
            this.mIsAlreadyRegister = false;
        }
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel$10  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct;

        static {
            int[] iArr = new int[VipSeatAct.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct = iArr;
            try {
                iArr[VipSeatAct.LayFlat.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[VipSeatAct.LayFlatPause.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[VipSeatAct.Restore.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[VipSeatAct.RestorePause.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[VipSeatAct.Stop.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[VipSeatAct.Flat.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setDrvVipSeatAct(VipSeatAct vipSeatAct) {
        if (this.mVipDrvSeatControl != null) {
            switch (AnonymousClass10.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[vipSeatAct.ordinal()]) {
                case 1:
                    this.mVipDrvSeatControl.laySeatFlat();
                    return;
                case 2:
                    this.mVipDrvSeatControl.pauseFlatMove();
                    return;
                case 3:
                    this.mVipDrvSeatControl.restoreSeat();
                    return;
                case 4:
                    this.mVipDrvSeatControl.pauseRestoreMove();
                    return;
                case 5:
                    this.mVipDrvSeatControl.stopSeatMove();
                    return;
                case 6:
                    this.mVipDrvSeatControl.setFlatStatus();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setPsnVipSeatAct(VipSeatAct vipSeatAct) {
        if (this.mVipPsnSeatControl != null) {
            switch (AnonymousClass10.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[vipSeatAct.ordinal()]) {
                case 1:
                    this.mVipPsnSeatControl.laySeatFlat();
                    return;
                case 2:
                    this.mVipPsnSeatControl.pauseFlatMove();
                    return;
                case 3:
                    this.mVipPsnSeatControl.restoreSeat();
                    return;
                case 4:
                    this.mVipPsnSeatControl.pauseRestoreMove();
                    return;
                case 5:
                    this.mVipPsnSeatControl.stopSeatMove();
                    return;
                case 6:
                    this.mVipPsnSeatControl.setFlatStatus();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setCapsuleVipSeatAct(VipSeatAct vipSeatAct) {
        if (this.mVipCapsuleSeatControl != null) {
            switch (AnonymousClass10.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[vipSeatAct.ordinal()]) {
                case 1:
                    this.mVipCapsuleSeatControl.laySeatFlat();
                    return;
                case 2:
                    this.mVipCapsuleSeatControl.pauseFlatMove();
                    return;
                case 3:
                    this.mVipCapsuleSeatControl.restoreSeat();
                    return;
                case 4:
                    this.mVipCapsuleSeatControl.pauseRestoreMove();
                    return;
                case 5:
                    this.mVipCapsuleSeatControl.stopSeatMove();
                    return;
                case 6:
                    this.mVipCapsuleSeatControl.setFlatStatus();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setCapsuleSingleVipSeatAct(VipSeatAct vipSeatAct) {
        if (this.mVipCapsuleSeatControl != null) {
            switch (AnonymousClass10.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[vipSeatAct.ordinal()]) {
                case 1:
                    this.mVipCapsuleSingleSeatControl.laySeatFlat();
                    return;
                case 2:
                    this.mVipCapsuleSingleSeatControl.pauseFlatMove();
                    return;
                case 3:
                    this.mVipCapsuleSingleSeatControl.restoreSeat();
                    return;
                case 4:
                    this.mVipCapsuleSingleSeatControl.pauseRestoreMove();
                    return;
                case 5:
                    this.mVipCapsuleSingleSeatControl.stopSeatMove();
                    return;
                case 6:
                    this.mVipCapsuleSingleSeatControl.setFlatStatus();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setRlSeatAct(VipSeatAct vipSeatAct) {
        if (this.mVipRLSeatControl != null) {
            switch (AnonymousClass10.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[vipSeatAct.ordinal()]) {
                case 1:
                    this.mVipRLSeatControl.laySeatFlat();
                    return;
                case 2:
                    this.mVipRLSeatControl.pauseFlatMove();
                    return;
                case 3:
                    this.mVipRLSeatControl.restoreSeat();
                    return;
                case 4:
                    this.mVipRLSeatControl.pauseRestoreMove();
                    return;
                case 5:
                    this.mVipRLSeatControl.stopSeatMove();
                    return;
                case 6:
                    this.mVipRLSeatControl.setFlatStatus();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setRrSeatAct(VipSeatAct vipSeatAct) {
        if (this.mVipRRSeatControl != null) {
            switch (AnonymousClass10.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[vipSeatAct.ordinal()]) {
                case 1:
                    this.mVipRRSeatControl.laySeatFlat();
                    return;
                case 2:
                    this.mVipRRSeatControl.pauseFlatMove();
                    return;
                case 3:
                    this.mVipRRSeatControl.restoreSeat();
                    return;
                case 4:
                    this.mVipRRSeatControl.pauseRestoreMove();
                    return;
                case 5:
                    this.mVipRRSeatControl.stopSeatMove();
                    return;
                case 6:
                    this.mVipRRSeatControl.setFlatStatus();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setRearSeatAct(VipSeatAct vipSeatAct) {
        if (this.mVipRearSeatControl != null) {
            switch (AnonymousClass10.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[vipSeatAct.ordinal()]) {
                case 1:
                    this.mVipRearSeatControl.laySeatFlat();
                    return;
                case 2:
                    this.mVipRearSeatControl.pauseFlatMove();
                    return;
                case 3:
                    this.mVipRearSeatControl.restoreSeat();
                    return;
                case 4:
                    this.mVipRearSeatControl.pauseRestoreMove();
                    return;
                case 5:
                    this.mVipRearSeatControl.stopSeatMove();
                    return;
                case 6:
                    this.mVipRearSeatControl.setFlatStatus();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setVipSleepSecSeatControl(VipSeatAct vipSeatAct) {
        if (this.mVipCapsuleSeatControl != null) {
            switch (AnonymousClass10.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$space$VipSeatAct[vipSeatAct.ordinal()]) {
                case 1:
                    this.mVipSleepSecSeatControl.laySeatFlat();
                    return;
                case 2:
                    this.mVipSleepSecSeatControl.pauseFlatMove();
                    return;
                case 3:
                    this.mVipSleepSecSeatControl.restoreSeat();
                    return;
                case 4:
                    this.mVipSleepSecSeatControl.pauseRestoreMove();
                    return;
                case 5:
                    this.mVipSleepSecSeatControl.stopSeatMove();
                    return;
                case 6:
                    this.mVipSleepSecSeatControl.setFlatStatus();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public VipSeatStatus getDrvVipSeatStatus() {
        return this.mVipDrvSeatControl.mSeatStatus;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public VipSeatStatus getPsnVipSeatStatus() {
        return this.mVipPsnSeatControl.mSeatStatus;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public VipSeatStatus getCapsuleVipSeatStatus() {
        return this.mVipCapsuleSeatControl.mSeatStatus;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public VipSeatStatus getCapsuleSingleVipSeatStatus() {
        return this.mVipCapsuleSingleSeatControl.mSeatStatus;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public VipSeatStatus getRlSeatStatus() {
        return this.mVipRLSeatControl.mSeatStatus;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public VipSeatStatus getRrSeatStatus() {
        return this.mVipRRSeatControl.mSeatStatus;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public VipSeatStatus getRearSeatStatus() {
        return this.mVipRearSeatControl.mSeatStatus;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public VipSeatStatus getSleepSecSeatStatus() {
        return this.mVipSleepSecSeatControl.mSeatStatus;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void stopDrvSeatMove() {
        this.mVipDrvSeatControl.stopSeat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void stopPsnSeatMove() {
        this.mVipPsnSeatControl.stopSeat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void stopCapsuleSeatMove() {
        stopDrvSeatMove();
        stopPsnSeatMove();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void stopRlSeatMove() {
        this.mVipRLSeatControl.stopSeat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void stopRrSeatMove() {
        this.mVipRRSeatControl.stopSeat();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void stopRearSeatMove() {
        this.mVipRearSeatControl.stopSeat();
    }

    public void setCurrentSubMode(int mode) {
        this.mCurrentSubMode = mode;
    }

    public int getCurrentSubMode() {
        return this.mCurrentSubMode;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public boolean isFirstEnterSpaceCapsule() {
        return SharedPreferenceUtil.isFirstStartSpaceCapsuleMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setNotFirstEnterSpaceCapsule() {
        SharedPreferenceUtil.setFirstStartSpaceCapsuleMode(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setSpaceCapsuleSleepMusic(String uri) {
        SharedPreferenceUtil.setSpaceCapsuleSleepMusic(uri);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public String getSpaceCapsuleSleepMusic() {
        return SharedPreferenceUtil.getSpaceCapsuleSleepMusic();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setSpaceCapsuleSleepBgmOpen(boolean isOpen) {
        SharedPreferenceUtil.setSpaceCapsuleSleepBgmOpen(isOpen);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public boolean isSpaceCapsuleSleepBgmOpen() {
        return SharedPreferenceUtil.isSpaceCapsuleSleepBgmOpen();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setSpeechStart() {
        this.mSpeechStatus.postValue(1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void setSpeechStop() {
        this.mSpeechStatus.postValue(2);
    }

    public LiveData<VipSeatStatus> getVipDrvSeatStatusData() {
        return this.mVipDrvSeatStatus;
    }

    public LiveData<VipSeatStatus> getVipPsnSeatStatusData() {
        return this.mVipPsnSeatStatus;
    }

    public LiveData<VipSeatStatus> getVipCapsuleSeatStatusData() {
        return this.mVipCapsuleSeatStatus;
    }

    public LiveData<VipSeatStatus> getVipCapsuleSingleSeatStatusData() {
        return this.mVipCapsuleSingleSeatStatus;
    }

    public LiveData<VipSeatStatus> getVipRlSeatData() {
        return this.mRLSeatStatus;
    }

    public LiveData<VipSeatStatus> getVipRrSeatData() {
        return this.mRRSeatStatus;
    }

    public LiveData<VipSeatStatus> getVipRearSeatData() {
        return this.mRearSeatStatus;
    }

    public MutableLiveData<VipSeatStatus> getVipSleepSecSeatStatusData() {
        return this.mSleepSecSeatStatus;
    }

    public LiveData<Integer> getStartMoveStatusData() {
        return this.mStartMoveStatus;
    }

    public MutableLiveData<String> getBtDeviceNameData() {
        return this.mBtDeviceNameData;
    }

    public void bindService(Context context, String from) {
        LogUtils.d(TAG, "bind music Service");
        context.bindService(getIntent(context, from), this.mConnection, 1);
        this.from = from;
        this.mServiceConnected = true;
    }

    public void unBindService(Context context) {
        if (this.mServiceConnected) {
            context.unbindService(this.mConnection);
            CapsuleMusicService capsuleMusicService = this.mMusicService;
            if (capsuleMusicService != null) {
                capsuleMusicService.stopSelf();
            }
        }
    }

    public void playMusicSingle(Context context, String musicSource, String from) {
        Intent intent = getIntent(context, from);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_SOURCE, musicSource);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_TYPE, 2);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_COMMAND, 0);
        if (BaseFeatureOption.getInstance().isMeditationIndependent() && !from.equals(SLEEP_CAPSULE_PACKAGE)) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public void playMusic(Context context, String musicSource, String from) {
        Intent intent = getIntent(context, from);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_SOURCE, musicSource);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_TYPE, 0);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_COMMAND, 0);
        if (BaseFeatureOption.getInstance().isMeditationIndependent() && !from.equals(SLEEP_CAPSULE_PACKAGE)) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public void playMusic(Context context, String musicSource, String from, int loopMode) {
        Intent intent = getIntent(context, from);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_SOURCE, musicSource);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_TYPE, 0);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_COMMAND, 0);
        intent.putExtra(CapsuleMusicService.EXTRA_LOOP_MODE, loopMode);
        if (BaseFeatureOption.getInstance().isMeditationIndependent() && !from.equals(SLEEP_CAPSULE_PACKAGE)) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public void resumeMusic(Context context, String musicSource, String from) {
        Intent intent = getIntent(context, from);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_SOURCE, musicSource);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_TYPE, 0);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_COMMAND, 2);
        if (BaseFeatureOption.getInstance().isMeditationIndependent() && !from.equals(SLEEP_CAPSULE_PACKAGE)) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public void resumeMusic(Context context, String musicSource, String from, int loopMode) {
        Intent intent = getIntent(context, from);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_SOURCE, musicSource);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_TYPE, 0);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_COMMAND, 2);
        intent.putExtra(CapsuleMusicService.EXTRA_LOOP_MODE, loopMode);
        if (BaseFeatureOption.getInstance().isMeditationIndependent() && !from.equals(SLEEP_CAPSULE_PACKAGE)) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public void pauseMusic(Context context, String musicSource, String from) {
        Intent intent = getIntent(context, from);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_SOURCE, musicSource);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_TYPE, 0);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_COMMAND, 1);
        if (BaseFeatureOption.getInstance().isMeditationIndependent() && !from.equals(SLEEP_CAPSULE_PACKAGE)) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public void switchMediaScreenID(Context context, int screenID, String from) {
        if (!BaseFeatureOption.getInstance().isMeditationIndependent() || from.equals(SLEEP_CAPSULE_PACKAGE)) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(MEDITATION_PACKAGE, MEDITATION_COMPONENT);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_SCREEN_ID, screenID);
        context.startForegroundService(intent);
    }

    private Intent getIntent(Context context, String from) {
        if (BaseFeatureOption.getInstance().isMeditationIndependent() && !from.equals(SLEEP_CAPSULE_PACKAGE)) {
            Intent intent = new Intent();
            intent.setClassName(MEDITATION_PACKAGE, MEDITATION_COMPONENT);
            return intent;
        }
        return new Intent(context, CapsuleMusicService.class);
    }

    public void setAlarmTime(Context context, String alarmSource, long triggerTime) {
        Intent intent = new Intent(context, CapsuleMusicService.class);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_SOURCE, alarmSource);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_TYPE, 1);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_COMMAND, 0);
        AlarmHelper.createOrUpdateServiceIntent(context, triggerTime, intent, CapsuleMusicService.ALARM_REQUEST_CODE);
    }

    public void stopAlarm(Context context, String alarmSource) {
        Intent intent = new Intent(context, CapsuleMusicService.class);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_SOURCE, alarmSource);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_TYPE, 1);
        intent.putExtra(CapsuleMusicService.EXTRA_MEDIA_COMMAND, 1);
        context.startService(intent);
    }

    public void cancelAlarm(Context context) {
        AlarmHelper.cancelServiceIntent(context, new Intent(context, CapsuleMusicService.class), CapsuleMusicService.ALARM_REQUEST_CODE);
    }

    public void setSoundFieldToMiddle(boolean isEnter) {
        this.mSpaceCapsuleControl.setSoundFieldToMiddle(isEnter);
    }

    public void changeMediaVolume(boolean bEnter) {
        this.mSpaceCapsuleControl.changeMediaVolume(bEnter);
    }

    public void changeAlarmVolume(boolean bEnter) {
        this.mSpaceCapsuleControl.changeAlarmVolume(bEnter);
    }

    public void setCapsuleBrightnessLow() {
        this.mSpaceCapsuleControl.setCapsuleBrightnessLow();
    }

    public void restoreCapsuleBrightness() {
        this.mSpaceCapsuleControl.restoreCapsuleBrightness();
    }

    public void setAutoPowerOffConfigOff() {
        boolean autoPowerOffConfig = this.mTboxController.getAutoPowerOffConfig();
        LogUtils.d(TAG, "get auto power off " + autoPowerOffConfig);
        if (autoPowerOffConfig) {
            this.mTboxController.setAutoPowerOffConfig(false);
            this.mIsRestoreAutoPowerConfig = true;
        }
    }

    public void restoreAutoPowerOffConfig() {
        if (this.mIsRestoreAutoPowerConfig) {
            this.mTboxController.setAutoPowerOffConfig(true);
            this.mIsRestoreAutoPowerConfig = false;
            LogUtils.d(TAG, "set auto power restore ");
        }
    }

    public void changeSoundEffectStyleCinema(boolean isEnter) {
        this.mSpaceCapsuleControl.changeSoundEffectStyleCinema(isEnter);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel
    public void onModeStart(boolean isVipMode, boolean mIsVoiceSource) {
        if (isVipMode) {
            this.mVipDrvSeatControl.onModeStart(mIsVoiceSource);
            this.mVipPsnSeatControl.onModeStart(mIsVoiceSource);
            return;
        }
        this.mVipCapsuleSeatControl.onModeStart(mIsVoiceSource);
        this.mVipCapsuleSingleSeatControl.onModeStart(mIsVoiceSource);
    }

    public MutableLiveData<Integer> getA2dpConnectLiveData() {
        return this.mA2dpConnectLiveData;
    }

    public boolean isCanSwitchBtSource() {
        return XuiClientWrapper.getInstance().getCurrentBtStatus() >= 2;
    }

    public boolean isBtSource() {
        return XuiClientWrapper.getInstance().getCurrentBtStatus() == 6;
    }

    public boolean isA2dpConnected() {
        return XuiClientWrapper.getInstance().getCurrentBtStatus() == 5;
    }

    public void registerBtStatusListener() {
        MediaCenterManager mediaCenterManager = XuiClientWrapper.getInstance().getMediaCenterManager();
        if (mediaCenterManager != null) {
            try {
                mediaCenterManager.registerBtStatusListener(this.mBtStatusListener);
            } catch (XUIServiceNotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterBtStatusListener() {
        MediaCenterManager mediaCenterManager = XuiClientWrapper.getInstance().getMediaCenterManager();
        if (mediaCenterManager != null) {
            try {
                mediaCenterManager.unRegisterBtStatusListener(this.mBtStatusListener);
            } catch (XUIServiceNotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    public void requestMediaButton(boolean request) {
        LogUtils.i(TAG, "requestMediaButton: " + request, false);
        XuiClientWrapper.getInstance().requestMediaButton(request);
    }

    public void setOpenSfs(boolean open) {
        this.mIsOpenSfs = open;
    }

    public boolean isOpenSfs() {
        return this.mIsOpenSfs;
    }

    public void reqSfsOpen() {
        this.mSpaceCapsuleControl.reqSfsOpen();
    }

    public void reqSfsForSleep(boolean enter) {
        this.mSpaceCapsuleControl.reqSfsForSleep(enter);
    }

    public boolean isThemeWorking() {
        return ThemeController.getInstance(App.getInstance()).isThemeWorking();
    }

    public void changeDay2NightTry() {
        LogUtils.i(TAG, "changeDay2NightTry delay working:" + isThemeWorking() + " mDayNightRetryCount:" + this.mDayNightRetryCount);
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        this.mHandler.removeCallbacks(this.mDayNightChangeRestoreRunnable);
        this.mDayNightRestoreRetry = 0;
        if (isThemeWorking()) {
            if (this.mDayNightRetryCount >= 10) {
                LogUtils.i(TAG, "changeDay2NightTry retry end");
                changeDay2Night();
                return;
            }
            this.mHandler.removeCallbacks(this.mDayNightChange2NightRunnable);
            this.mHandler.postDelayed(this.mDayNightChange2NightRunnable, 2000L);
            this.mDayNightRetryCount++;
            return;
        }
        changeDay2Night();
    }

    private void changeDay2Night() {
        this.mDayNightRetryCount = 0;
        int dayNightMode = Utils.getDayNightMode(App.getInstance().getApplicationContext());
        LogUtils.i(TAG, "changeDay2Night mode:" + dayNightMode);
        if (dayNightMode != 2) {
            if (dayNightMode == 1) {
                System.setProperty(GlobalConstant.SPACE.SPACE_CAPSULE_DAY_NIGHT_MODE, "1");
            } else if (dayNightMode == 0) {
                System.setProperty(GlobalConstant.SPACE.SPACE_CAPSULE_DAY_NIGHT_MODE, "0");
            }
            Utils.setDayNightMode(App.getInstance().getApplicationContext(), 2);
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$SpaceCapsuleViewModel$eSQNDOnRNsgfVjG-rtOhQtSMdPQ
                @Override // java.lang.Runnable
                public final void run() {
                    SpaceCapsuleViewModel.this.lambda$changeDay2Night$0$SpaceCapsuleViewModel();
                }
            }, 1000L);
            this.mIsChangedDayNight = false;
        }
    }

    public void restoreDayNightTry() {
        LogUtils.i(TAG, "restoreDayNightTry delay working:" + isThemeWorking() + " mDayNightRestoreRetry:" + this.mDayNightRestoreRetry);
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        this.mHandler.removeCallbacks(this.mDayNightChange2NightRunnable);
        this.mDayNightRetryCount = 0;
        if (isThemeWorking()) {
            if (this.mDayNightRestoreRetry >= 10) {
                LogUtils.i(TAG, "restoreDayNightTry retry end!");
                restoreDayNight();
                return;
            }
            this.mHandler.removeCallbacks(this.mDayNightChangeRestoreRunnable);
            this.mHandler.postDelayed(this.mDayNightChangeRestoreRunnable, 2000L);
            this.mDayNightRestoreRetry++;
            return;
        }
        restoreDayNight();
    }

    private void restoreDayNight() {
        this.mDayNightRestoreRetry = 0;
        unRegisterDayNightSwitch();
        if (this.mIsChangedDayNight) {
            LogUtils.i(TAG, "restoreDayNight mIsChangedDayNight:" + this.mIsChangedDayNight + " return!!");
            System.setProperty(GlobalConstant.SPACE.SPACE_CAPSULE_DAY_NIGHT_MODE, "");
            return;
        }
        int dayNightMode = Utils.getDayNightMode(App.getInstance().getApplicationContext());
        String property = System.getProperty(GlobalConstant.SPACE.SPACE_CAPSULE_DAY_NIGHT_MODE);
        LogUtils.i(TAG, "restoreDayNight currentMode:" + dayNightMode + " save mode:" + property);
        if (dayNightMode == 2 && !TextUtils.isEmpty(property)) {
            if ("1".equals(property)) {
                Utils.setDayNightMode(App.getInstance().getApplicationContext(), 1);
            } else if ("0".equals(property)) {
                Utils.setDayNightMode(App.getInstance().getApplicationContext(), 0);
            }
        }
        System.setProperty(GlobalConstant.SPACE.SPACE_CAPSULE_DAY_NIGHT_MODE, "");
    }

    /* renamed from: registerDayNightSwitch */
    public void lambda$changeDay2Night$0$SpaceCapsuleViewModel() {
        if (this.mDayNightObserver == null) {
            this.mDayNightObserver = new ContentObserver(new Handler()) { // from class: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.8
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    super.onChange(selfChange, uri);
                    if (uri.equals(Settings.Secure.getUriFor("ui_night_mode"))) {
                        LogUtils.d(SpaceCapsuleViewModel.TAG, "mDayNightObserver");
                        SpaceCapsuleViewModel.this.mIsChangedDayNight = true;
                    }
                }
            };
        }
        App.getInstance().getContentResolver().registerContentObserver(Settings.Secure.getUriFor("ui_night_mode"), true, this.mDayNightObserver);
    }

    public void unRegisterDayNightSwitch() {
        if (this.mDayNightObserver != null) {
            App.getInstance().getContentResolver().unregisterContentObserver(this.mDayNightObserver);
            this.mDayNightObserver = null;
        }
    }

    public void switchBluetoothSource() {
        XuiClientWrapper.getInstance().playBtMedia();
    }

    public int getElecPercent(int percent) {
        LogUtils.d(TAG, "getElecPercent " + percent);
        return getBatteryLevel(percent);
    }

    public void requestAudioFocus() {
        this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 1);
    }

    public void setBtVolume(int volume) {
        XuiClientWrapper.getInstance().setBtVolume(volume);
    }

    public void abandonAudioFocus() {
        LogUtils.i(TAG, "abandonAudioFocus ", false);
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            audioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
        }
    }

    public void sendCapsuleSleepOverTimeRemind() {
        if (this.mSleepMsgHandler == null) {
            this.mSleepMsgHandler = ThreadUtils.getHandler(0);
        }
        this.mSleepMsgHandler.removeCallbacks(this.mCapsuleSleepOverTimeRemindRunnable);
        this.mSleepMsgHandler.postDelayed(this.mCapsuleSleepOverTimeRemindRunnable, 28800000L);
    }

    public void cancelCapsuleSleepOverTimeRemind() {
        Handler handler = this.mSleepMsgHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mCapsuleSleepOverTimeRemindRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCapsuleSleepOverTimeRemind() {
        NotificationHelper.getInstance().sendMessageToMessageCenter(NotificationHelper.SCENE_CAPSULE_SLEEP_OVER_TIME, ResUtils.getString(R.string.space_capsule_sleep_msg_title), null, ResUtils.getString(R.string.space_capsule_sleep_msg_tts), null, null, ResUtils.getString(R.string.space_capsule_sleep_msg_btn_title), true, 0L, true, null);
    }

    public boolean isProjectorConnected() {
        String connectedBtDeviceName = getConnectedBtDeviceName();
        if (TextUtils.isEmpty(connectedBtDeviceName)) {
            return false;
        }
        return connectedBtDeviceName.contains(ResUtils.getString(R.string.space_capsule_cinema_projector_name)) || connectedBtDeviceName.contains(ResUtils.getString(R.string.space_capsule_cinema_projector_name_new)) || connectedBtDeviceName.contains(ResUtils.getString(R.string.space_capsule_cinema_projector_name_common));
    }

    public String getConnectedBtDeviceName() {
        String string = Settings.Secure.getString(App.getInstance().getContentResolver(), CONNECTED_BLE_DEVICE);
        LogUtils.d(TAG, "xpsettings settingprovider getConnectedBleDevice name:" + string, false);
        return string;
    }

    public void registerBtDeviceNameObserver() {
        if (this.mBtDeviceNameObserver == null) {
            this.mBtDeviceNameObserver = new ContentObserver(ThreadUtils.getHandler(0)) { // from class: com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModel.9
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    super.onChange(selfChange, uri);
                    if (uri.equals(Settings.Secure.getUriFor(SpaceCapsuleViewModel.CONNECTED_BLE_DEVICE))) {
                        String string = Settings.Secure.getString(App.getInstance().getContentResolver(), SpaceCapsuleViewModel.CONNECTED_BLE_DEVICE);
                        LogUtils.d(SpaceCapsuleViewModel.TAG, "BtDeviceNameObserver getConnectedBleDevice name:" + string, false);
                        SpaceCapsuleViewModel.this.mBtDeviceNameData.postValue(string);
                    }
                }
            };
        }
        App.getInstance().getContentResolver().registerContentObserver(Settings.Secure.getUriFor(CONNECTED_BLE_DEVICE), true, this.mBtDeviceNameObserver);
    }

    public void unRegisterBtDeviceNameObserver() {
        if (this.mBtDeviceNameObserver != null) {
            App.getInstance().getContentResolver().unregisterContentObserver(this.mBtDeviceNameObserver);
            this.mBtDeviceNameObserver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Handler getTimeHandler() {
        if (this.mTimerHandler == null) {
            this.mTimerHandler = new Handler(Looper.getMainLooper());
        }
        return this.mTimerHandler;
    }

    public void startMusicTimer(long time, ICountdownTimeListener listener) {
        this.mTimer = new Timer(time);
        getTimeHandler().post(this.mTimeUpdateRunnable);
        this.mCountdownTimeListener = listener;
    }

    public void stopMusicTimer() {
        getTimeHandler().removeCallbacks(this.mTimeUpdateRunnable);
        this.mTimer = null;
        this.mCountdownTimeListener = null;
        this.mTimerHandler = null;
    }

    public void setSleepMode(int mode) {
        this.mSleepMode = mode;
    }

    public int getSleepMode() {
        return this.mSleepMode;
    }

    /* loaded from: classes2.dex */
    private class TimeUpdateRunnable implements Runnable {
        private TimeUpdateRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (updateTime()) {
                LogUtils.d(SpaceCapsuleViewModel.TAG, "TimeUpdateRunnable stopMusic");
                if (SpaceCapsuleViewModel.this.mCountdownTimeListener != null) {
                    SpaceCapsuleViewModel.this.mCountdownTimeListener.onTimeStop();
                    return;
                }
                return;
            }
            long max = Math.max(0L, (elapsedRealtime + 30000) - SystemClock.elapsedRealtime());
            LogUtils.d(SpaceCapsuleViewModel.TAG, "TimeUpdateRunnable postDelayed:" + max);
            SpaceCapsuleViewModel.this.getTimeHandler().postDelayed(this, max);
        }

        private boolean updateTime() {
            if (SpaceCapsuleViewModel.this.mTimer != null) {
                long remainingTime = SpaceCapsuleViewModel.this.mTimer.getRemainingTime();
                LogUtils.d(SpaceCapsuleViewModel.TAG, "updateTime remainingTime:" + remainingTime);
                if (remainingTime <= 0) {
                    return true;
                }
                if (SpaceCapsuleViewModel.this.mCountdownTimeListener != null) {
                    SpaceCapsuleViewModel.this.mCountdownTimeListener.onUpdateTime(remainingTime);
                    return false;
                }
                return false;
            }
            return false;
        }
    }

    /* loaded from: classes2.dex */
    public class Timer {
        private final long lastStartTime = now();
        private final long remainingTime;

        public Timer(long remainingTime) {
            this.remainingTime = remainingTime;
        }

        public long getRemainingTime() {
            return this.remainingTime - Math.max(0L, now() - this.lastStartTime);
        }

        private long now() {
            return SystemClock.elapsedRealtime();
        }
    }
}
