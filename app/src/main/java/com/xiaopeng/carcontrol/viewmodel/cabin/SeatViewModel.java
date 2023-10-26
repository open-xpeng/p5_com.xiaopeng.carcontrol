package com.xiaopeng.carcontrol.viewmodel.cabin;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.bean.Seat;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.util.AmsUtils;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.view.dialog.dropdownmenu.DropDownMenuManager;
import com.xiaopeng.carcontrol.view.dialog.poppanel.PopPanelManager;
import com.xiaopeng.carcontrol.viewmodel.space.VipCapsuleSeatControl;
import com.xiaopeng.carcontrol.viewmodel.space.VipCapsuleSingleSeatControl;
import com.xiaopeng.carcontrol.viewmodel.space.VipPsnSeatControl;
import com.xiaopeng.carcontrol.viewmodel.space.VipSeatStatus;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xpmeditation.util.XpTickDownTimer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class SeatViewModel extends SeatBaseViewModel {
    static final long COUNTDOWN_TIME = 900000;
    private static final long SEAT_ADJUST_INTERVAL_TIME = 50;
    private static final long SEAT_ADJUST_MAX_COUNT = 1000;
    private static final int SEAT_CONTROL_DISABLE_SPEED = 3;
    protected static final int SEAT_RESTORE_TILT_POS = 50;
    private static final long SEAT_SPEECH_HOR_ADJUST_COUNT = 40;
    private static final long SEAT_SPEECH_TILT_ADJUST_COUNT = 64;
    private static final long SEAT_SPEECH_VER_ADJUST_COUNT = 26;
    protected int[] mDriverSeatSavedPos;
    protected Runnable mDropDownRun;
    private final MutableLiveData<Boolean> mDrvLumbData;
    private final MutableLiveData<SeatMassage> mDrvMassageData;
    private final MutableLiveData<Long> mDrvMassageTimeData;
    private SeatMassagerTimer mDrvMassageTimer;
    private final MutableLiveData<SeatMassage> mDrvRearMassageData;
    private final MutableLiveData<Long> mDrvRearMassageTimeData;
    private SeatMassagerTimer mDrvRearMassageTimer;
    private final MutableLiveData<SeatRhythm> mDrvRhythmData;
    private final MutableLiveData<String> mEsbConfigMode;
    final MutableLiveData<Boolean> mEsbMode;
    private final MutableLiveData<Boolean> mInPsnSelectProcessData;
    private final MutableLiveData<Boolean> mInRLSelectProcessData;
    private final MutableLiveData<Boolean> mInRRSelectProcessData;
    protected boolean mIsDrvRestoreSafeChairBack;
    protected boolean mIsPsnRestoreSafeChairBack;
    protected Runnable mPsnDropDownRun;
    private final MutableLiveData<Boolean> mPsnLumbData;
    private final MutableLiveData<SeatMassage> mPsnMassageData;
    private final MutableLiveData<Long> mPsnMassageTimeData;
    private SeatMassagerTimer mPsnMassageTimer;
    private final MutableLiveData<SeatMassage> mPsnRearMassageData;
    private final MutableLiveData<Long> mPsnRearMassageTimeData;
    private SeatMassagerTimer mPsnRearMassageTimer;
    private final MutableLiveData<SeatRhythm> mPsnRhythmData;
    protected int[] mPsnSeatSavedPos;
    protected Runnable mRLDropDownRun;
    protected int[] mRLSeatSavedPos;
    protected Runnable mRRDropDownRun;
    protected int[] mRRSeatSavedPos;
    private final Runnable mSavePsnPosTask;
    protected Handler mSeatControlHandler;
    private long mSeatCtrOsdTime;
    private final MutableLiveData<Boolean> mSeatLumbShowData;
    private final Runnable resumeDrvRestoreSafeChairRun;
    private final Runnable resumePsnRestoreSafeChairRun;
    protected final Seat mDriverSeatData = new Seat(true);
    private final MutableLiveData<Boolean> mDriverSeatOccupied = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDriverSeatHorz = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDriverSeatVert = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDriverSeatTilt = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDriverSeatLeg = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDriverSeatCushion = new MutableLiveData<>();
    private final Seat mPsnSeatData = new Seat(false);
    private final MutableLiveData<Boolean> mPsnSeatOccupied = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPsnSeatHorz = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPsnSeatVer = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPsnSeatTilt = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPsnSeatLeg = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRLSeatTilt = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRRSeatTilt = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRLSeatLeg = new MutableLiveData<>();
    private final MutableLiveData<Integer> mRRSeatLeg = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowLtSeatHorz = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowLtSeatLeSuHeig = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowLtSeatHeadHorz = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowLtSeatHeadHeig = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowRtSeatHorz = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowRtSeatLeSuHeig = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowRtSeatHeadHorz = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowRtSeatHeadHeig = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowLtSeatAngle = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowRtSeatAngle = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowLtEzOrZeroGStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> mSecRowRtEzOrZeroGStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTrdRowLtFoldStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTrdRowRtFoldStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTrdRowStowStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTrdRowLtSeatTiltPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTrdRowRtSeatTiltPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTrdRowLtSeatHeadVerticalPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTrdRowRtSeatHeadVerticalPos = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTrdRowMidSeatHeadVerticalPos = new MutableLiveData<>();
    private final Seat mRearLeftSeatData = new Seat(false);
    private final Seat mRearRightSeatData = new Seat(false);
    private final Seat mTrdRowLeftSeatData = new Seat(false);
    private final Seat mTrdRowRightSeatData = new Seat(false);
    private final Seat mTrdRowMidSeatData = new Seat(false);
    private final MutableLiveData<Boolean> mRlSeatOccupied = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRmSeatOccupied = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRrSeatOccupied = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDvrBeltWarn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDrvHeadrestNormal = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mPsnHeadrestNormal = new MutableLiveData<>();
    private final MutableLiveData<Integer> mMcuIgState = new MutableLiveData<>();
    private ManualAdjustSeatTimer mDriverSeatAdjustTimer = null;
    private ManualAdjustSeatTimer mPsnSeatAdjustTimer = null;
    private ManualAdjustSeatTimer mRearSeatLeftAdjustTimer = null;
    private ManualAdjustSeatTimer mRearSeatRightAdjustTimer = null;
    private ManualAdjustSeatTimer mTrdRowLeftAdjustTimer = null;
    private ManualAdjustSeatTimer mTrdRowRightAdjustTimer = null;
    private ManualAdjustSeatTimer mTrdRowMidAdjustTimer = null;
    protected boolean mIsDriverSeatManualMoving = false;
    private boolean mIsPsnSeatManualMoving = false;
    private boolean mIsPsnSeatControlByBossKey = false;
    private boolean mIsRearLeftSeatManualMoving = false;
    private boolean mIsRearRightSeatManualMoving = false;
    private boolean mIsTrdRowLeftSeatManualMoving = false;
    private boolean mIsTrdRowRightSeatManualMoving = false;
    private boolean mIsTrdRowMidSeatManualMoving = false;
    protected final MutableLiveData<Boolean> mSeatWelcomeMode = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> mPsnSeatWelcomeMode = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> mRearSeatWelcomeMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mBackBeltWaring = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mPsnSrsEnableData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDrvSeatDropDownShowData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mPsnSeatDropDownShowData = new MutableLiveData<>();

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatAllPositionsToMcu() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatAllPositionsToMcu() {
    }

    public MutableLiveData<Integer> getSecRowLtSeatHorz() {
        return this.mSecRowLtSeatHorz;
    }

    public MutableLiveData<Integer> getSecRowLtSeatLeSuHeig() {
        return this.mSecRowLtSeatLeSuHeig;
    }

    public MutableLiveData<Integer> getSecRowRtSeatHorz() {
        return this.mSecRowRtSeatHorz;
    }

    public MutableLiveData<Integer> getSecRowRtSeatLeSuHeig() {
        return this.mSecRowRtSeatLeSuHeig;
    }

    public MutableLiveData<Integer> getSecRowLtSeatHeadHorz() {
        return this.mSecRowLtSeatHeadHorz;
    }

    public MutableLiveData<Integer> getSecRowLtSeatHeadHeig() {
        return this.mSecRowLtSeatHeadHeig;
    }

    public MutableLiveData<Integer> getSecRowRtSeatHeadHorz() {
        return this.mSecRowRtSeatHeadHorz;
    }

    public MutableLiveData<Integer> getSecRowRtSeatHeadHeig() {
        return this.mSecRowRtSeatHeadHeig;
    }

    public MutableLiveData<Integer> getSecRowLtSeatAngle() {
        return this.mSecRowLtSeatAngle;
    }

    public MutableLiveData<Integer> getSecRowRtSeatAngle() {
        return this.mSecRowRtSeatAngle;
    }

    public MutableLiveData<Integer> getSecRowLtEzOrZeroGStatus() {
        return this.mSecRowLtEzOrZeroGStatus;
    }

    public MutableLiveData<Integer> getSecRowRtEzOrZeroGStatus() {
        return this.mSecRowRtEzOrZeroGStatus;
    }

    public MutableLiveData<Integer> getTrdRowLtFoldStatus() {
        return this.mTrdRowLtFoldStatus;
    }

    public MutableLiveData<Integer> getTrdRowRtFoldStatus() {
        return this.mTrdRowRtFoldStatus;
    }

    public MutableLiveData<Integer> getTrdRowStowStatus() {
        return this.mTrdRowStowStatus;
    }

    public MutableLiveData<Integer> getTrdRowLtSeatTilt() {
        return this.mTrdRowLtSeatTiltPos;
    }

    public MutableLiveData<Integer> getTrdRowRtSeatTilt() {
        return this.mTrdRowRtSeatTiltPos;
    }

    public MutableLiveData<Integer> getTrdRowLtSeatHeadVertical() {
        return this.mTrdRowLtSeatHeadVerticalPos;
    }

    public MutableLiveData<Integer> getTrdRowRtSeatHeadVertical() {
        return this.mTrdRowRtSeatHeadVerticalPos;
    }

    public MutableLiveData<Integer> getTrdRowMidSeatHeadVertical() {
        return this.mTrdRowMidSeatHeadVerticalPos;
    }

    public MutableLiveData<Boolean> getInPsnSelectProcessData() {
        return this.mInPsnSelectProcessData;
    }

    public MutableLiveData<Boolean> getInRLSelectProcessData() {
        return this.mInRLSelectProcessData;
    }

    public MutableLiveData<Boolean> getInRRSelectProcessData() {
        return this.mInRRSelectProcessData;
    }

    public SeatViewModel() {
        MutableLiveData<SeatMassage> mutableLiveData = new MutableLiveData<>();
        this.mDrvMassageData = mutableLiveData;
        MutableLiveData<SeatMassage> mutableLiveData2 = new MutableLiveData<>();
        this.mDrvRearMassageData = mutableLiveData2;
        MutableLiveData<SeatMassage> mutableLiveData3 = new MutableLiveData<>();
        this.mPsnMassageData = mutableLiveData3;
        MutableLiveData<SeatMassage> mutableLiveData4 = new MutableLiveData<>();
        this.mPsnRearMassageData = mutableLiveData4;
        this.mDrvMassageTimeData = new MutableLiveData<>();
        this.mDrvRearMassageTimeData = new MutableLiveData<>();
        this.mPsnMassageTimeData = new MutableLiveData<>();
        this.mPsnRearMassageTimeData = new MutableLiveData<>();
        MutableLiveData<SeatRhythm> mutableLiveData5 = new MutableLiveData<>();
        this.mDrvRhythmData = mutableLiveData5;
        MutableLiveData<SeatRhythm> mutableLiveData6 = new MutableLiveData<>();
        this.mPsnRhythmData = mutableLiveData6;
        this.mInPsnSelectProcessData = new MutableLiveData<>();
        this.mInRLSelectProcessData = new MutableLiveData<>();
        this.mInRRSelectProcessData = new MutableLiveData<>();
        this.mEsbMode = new MutableLiveData<>();
        this.mEsbConfigMode = new MutableLiveData<>();
        this.mDrvLumbData = new MutableLiveData<>();
        this.mPsnLumbData = new MutableLiveData<>();
        this.mSeatLumbShowData = new MutableLiveData<>();
        this.resumeDrvRestoreSafeChairRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel.1
            @Override // java.lang.Runnable
            public void run() {
                SeatViewModel.this.mIsDrvRestoreSafeChairBack = false;
            }
        };
        this.resumePsnRestoreSafeChairRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel.2
            @Override // java.lang.Runnable
            public void run() {
                SeatViewModel.this.mIsPsnRestoreSafeChairBack = false;
            }
        };
        this.mSavePsnPosTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$FGmQtHBFlmGFkIW1D3o7BP2Fb-s
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$new$4$SeatViewModel();
            }
        };
        this.mDropDownRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$Tx6XuTMz6mrSTktTeULTNTU3IM0
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$new$8$SeatViewModel();
            }
        };
        this.mPsnDropDownRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$YJ2LcN71qsoEorqa4PAO47ho2Rw
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$new$9$SeatViewModel();
            }
        };
        this.mRLDropDownRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$BWYFtiX6akCWsNf-jPXajn1XWGU
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$new$10$SeatViewModel();
            }
        };
        this.mRRDropDownRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$J9AKnH2nVjtF4xwRTrM_p0w22_k
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$new$11$SeatViewModel();
            }
        };
        this.mDriverSeatData.setSeatHorzValue(getDSeatHorzPos());
        this.mDriverSeatData.setSeatVertValue(getDSeatVerPos());
        this.mDriverSeatData.setSeatTiltValue(getDSeatTiltPos());
        this.mDriverSeatData.setSeatLegValue(getDSeatLegPos());
        if (CarBaseConfig.getInstance().isSupportMsmP()) {
            this.mPsnSeatData.setSeatHorzValue(getPSeatHorzPos());
            this.mPsnSeatData.setSeatTiltValue(getPSeatTiltPos());
        }
        mutableLiveData.postValue(new SeatMassage(0, this.mMsmController.getMassagerEffectId(0), this.mMsmController.getMassagerIntensity(0), this.mMsmController.isMassagerRunning(0)));
        mutableLiveData3.postValue(new SeatMassage(1, this.mMsmController.getMassagerEffectId(1), this.mMsmController.getMassagerIntensity(1), this.mMsmController.isMassagerRunning(1)));
        mutableLiveData2.postValue(new SeatMassage(2, this.mMsmController.getMassagerEffectId(2), this.mMsmController.getMassagerIntensity(2), this.mMsmController.isMassagerRunning(2)));
        mutableLiveData4.postValue(new SeatMassage(3, this.mMsmController.getMassagerEffectId(3), this.mMsmController.getMassagerIntensity(3), this.mMsmController.isMassagerRunning(3)));
        mutableLiveData5.postValue(new SeatRhythm(0, this.mMsmController.getRhythmMode(), this.mMsmController.getRhythmIntensity(0), this.mMsmController.isRhythmRunning(0)));
        mutableLiveData6.postValue(new SeatRhythm(1, this.mMsmController.getRhythmMode(), this.mMsmController.getRhythmIntensity(1), this.mMsmController.isRhythmRunning(1)));
        this.mMsmController.registerCallback(this);
        this.mMcuController.registerCallback(this);
        this.mBcmController.registerCallback(this.mBcmCallback);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handleDrvSeatOccupied(boolean occupied) {
        this.mDriverSeatOccupied.postValue(Boolean.valueOf(occupied));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handleBackBeltWaringChanged(boolean status) {
        this.mBackBeltWaring.postValue(Boolean.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    boolean isSupportEsbConfig() {
        return CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_SCU) && CarBaseConfig.hasFeature(CarBaseConfig.PROPERTY_MSB);
    }

    public LiveData<Boolean> getWelcomeModeData() {
        return this.mSeatWelcomeMode;
    }

    public LiveData<Boolean> getPsnSeatWelcomeMode() {
        return this.mPsnSeatWelcomeMode;
    }

    public LiveData<Boolean> getRearSeatWelcomeMode() {
        return this.mRearSeatWelcomeMode;
    }

    public LiveData<Boolean> getDriverSeatOccupiedData() {
        return this.mDriverSeatOccupied;
    }

    public LiveData<Integer> getDriverSeatData(int type) {
        if (type != 1) {
            if (type != 2) {
                if (type != 3) {
                    if (type != 4) {
                        if (type != 7) {
                            return null;
                        }
                        return this.mDriverSeatCushion;
                    }
                    return this.mDriverSeatLeg;
                }
                return this.mDriverSeatTilt;
            }
            return this.mDriverSeatVert;
        }
        return this.mDriverSeatHorz;
    }

    public LiveData<Boolean> getPsnSeatOccupiedData() {
        return this.mPsnSeatOccupied;
    }

    public LiveData<Boolean> getRlSeatOccupiedData() {
        return this.mRlSeatOccupied;
    }

    public LiveData<Boolean> getRmSeatOccupiedData() {
        return this.mRmSeatOccupied;
    }

    public LiveData<Boolean> getRrSeatOccupiedData() {
        return this.mRrSeatOccupied;
    }

    public MutableLiveData<Boolean> getDvrBeltWarnData() {
        return this.mDvrBeltWarn;
    }

    public LiveData<Integer> getPsnSeatHorzData() {
        return this.mPsnSeatHorz;
    }

    public LiveData<Integer> getPsnSeatTiltData() {
        return this.mPsnSeatTilt;
    }

    public LiveData<Integer> getPsnSeatVerData() {
        return this.mPsnSeatVer;
    }

    public MutableLiveData<Integer> getPsnSeatLegData() {
        return this.mPsnSeatLeg;
    }

    public LiveData<Integer> getRLSeatData() {
        return this.mRLSeatTilt;
    }

    public LiveData<Integer> getRRSeatData() {
        return this.mRRSeatTilt;
    }

    public MutableLiveData<Integer> getRLSeatLegData() {
        return this.mRLSeatLeg;
    }

    public MutableLiveData<Integer> getRRSeatLegData() {
        return this.mRRSeatLeg;
    }

    public boolean controlDriverSeatStart(int controlType, int direction) {
        return controlDriverSeatStart(controlType, direction, 1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean controlDriverSeatStart(int controlType, int direction, int controlLocus) {
        boolean z = false;
        if (getCarSpeed() > 3.0f) {
            showSeatDisableToast();
            return false;
        }
        boolean z2 = controlLocus == 3;
        int adjustingType = this.mDriverSeatData.getAdjustingType();
        if (adjustingType == 0) {
            this.mDriverSeatData.setAdjustingParam(controlType, direction);
        } else {
            boolean z3 = this.mIsDriverSeatManualMoving;
            if (z3 && (adjustingType != controlType || z2)) {
                LogUtils.w(this.TAG, "controlDriverSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            } else if (z3) {
                LogUtils.w(this.TAG, "controlDriverSeatStart failed, adjust " + adjustingType + " not finished", false);
                controlDriverSeatEnd(this.mDriverSeatData.getAdjustingType());
                z = true;
            }
        }
        this.mDriverSeatData.setAdjustingParam(controlType, direction);
        if (z) {
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$7qTjIIRzgYJ5PHm-kl0EFF3J0eM
                @Override // java.lang.Runnable
                public final void run() {
                    SeatViewModel.this.lambda$controlDriverSeatStart$0$SeatViewModel();
                }
            }, 100L);
        } else {
            controlDriverSeatStart(z2);
        }
        return true;
    }

    public /* synthetic */ void lambda$controlDriverSeatStart$0$SeatViewModel() {
        controlDriverSeatStart(false);
    }

    public void controlDriverSeatEnd(int type) {
        int adjustingType = this.mDriverSeatData.getAdjustingType();
        LogUtils.d(this.TAG, "controlDriverSeatEnd:" + type + ",adjustType:" + adjustingType + ",mIsDriverSeatManualMoving:" + this.mIsDriverSeatManualMoving, false);
        if (this.mIsDriverSeatManualMoving && adjustingType == type) {
            this.mIsDriverSeatManualMoving = false;
            this.mDriverSeatData.setAdjustingType(0);
            ManualAdjustSeatTimer manualAdjustSeatTimer = this.mDriverSeatAdjustTimer;
            if (manualAdjustSeatTimer != null) {
                manualAdjustSeatTimer.cancel();
                this.mDriverSeatAdjustTimer = null;
            }
            int adjustingDirection = this.mDriverSeatData.getAdjustingDirection();
            LogUtils.d(this.TAG, "end controlType " + adjustingType + ",direction " + adjustingDirection, false);
            switch (adjustingType) {
                case 1:
                    setDSeatHorzMove(3, adjustingDirection);
                    return;
                case 2:
                    setDSeatVerMove(3, adjustingDirection);
                    return;
                case 3:
                    setDSeatTiltMove(3, adjustingDirection);
                    return;
                case 4:
                    setDSeatLegMove(3, adjustingDirection);
                    return;
                case 5:
                    setDSeatLumbHorzMove(3, adjustingDirection);
                    return;
                case 6:
                    setDSeatLumbVerMove(3, adjustingDirection);
                    return;
                case 7:
                    setDSeatCushionMove(3, adjustingDirection);
                    return;
                default:
                    return;
            }
        }
    }

    public boolean controlPsnSeatStart(int controlType, int direction) {
        return controlPsnSeatStart(controlType, direction, 1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean controlPsnSeatStart(int controlType, int direction, int controlLocus) {
        boolean z = false;
        boolean z2 = controlLocus == 2;
        boolean z3 = controlLocus == 3;
        int adjustingType = this.mPsnSeatData.getAdjustingType();
        if (adjustingType == 0) {
            this.mPsnSeatData.setAdjustingParam(controlType, direction);
        } else if (this.mIsPsnSeatControlByBossKey && !z2) {
            LogUtils.w(this.TAG, "controlPsnSeatStart failed, seat is being controlled by boss key", false);
            return false;
        } else {
            boolean z4 = this.mIsPsnSeatManualMoving;
            if (z4 && (adjustingType != controlType || z3)) {
                LogUtils.w(this.TAG, "controlPsnSeatStart failed, seat is being controlled", false);
                return false;
            } else if (z4) {
                LogUtils.w(this.TAG, "controlPsnSeatStart failed, adjust " + adjustingType + " not finished", false);
                controlPsnSeatEnd(this.mPsnSeatData.getAdjustingType());
                z = true;
            }
        }
        if (z2) {
            this.mIsPsnSeatControlByBossKey = true;
        }
        this.mPsnSeatData.setAdjustingParam(controlType, direction);
        if (z) {
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$OyGd2I1Xi8MYGiwtcXTus0RPAXI
                @Override // java.lang.Runnable
                public final void run() {
                    SeatViewModel.this.lambda$controlPsnSeatStart$1$SeatViewModel();
                }
            }, 100L);
        } else {
            controlPsnSeatStart(z3);
        }
        return true;
    }

    public /* synthetic */ void lambda$controlPsnSeatStart$1$SeatViewModel() {
        controlPsnSeatStart(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void controlPsnSeatEnd(int type) {
        int adjustingType = this.mPsnSeatData.getAdjustingType();
        LogUtils.d(this.TAG, "controlPsnSeatEnd:" + type + ",adjustType:" + adjustingType + ",mIsPsnSeatManualMoving:" + this.mIsPsnSeatManualMoving, false);
        this.mIsPsnSeatControlByBossKey = false;
        if (this.mIsPsnSeatManualMoving && adjustingType == type) {
            this.mIsPsnSeatManualMoving = false;
            this.mPsnSeatData.setAdjustingType(0);
            ManualAdjustSeatTimer manualAdjustSeatTimer = this.mPsnSeatAdjustTimer;
            if (manualAdjustSeatTimer != null) {
                manualAdjustSeatTimer.cancel();
                this.mPsnSeatAdjustTimer = null;
            }
            int adjustingDirection = this.mPsnSeatData.getAdjustingDirection();
            switch (adjustingType) {
                case 1:
                    setPSeatHorzMove(3, adjustingDirection);
                    return;
                case 2:
                    setPSeatVerMove(3, adjustingDirection);
                    return;
                case 3:
                    setPSeatTiltMove(3, adjustingDirection);
                    return;
                case 4:
                    setPSeatLegMove(3, adjustingDirection);
                    return;
                case 5:
                    setPSeatLumbHorzMove(3, adjustingDirection);
                    return;
                case 6:
                    setPSeatLumbVerMove(3, adjustingDirection);
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlDriverSeat() {
        int adjustingType = this.mDriverSeatData.getAdjustingType();
        int adjustingDirection = this.mDriverSeatData.getAdjustingDirection();
        LogUtils.d(this.TAG, "controlType " + adjustingType + ",direction " + adjustingDirection, false);
        switch (adjustingType) {
            case 1:
                if (isCanSetSeatPos(adjustingDirection, adjustingType)) {
                    setDSeatHorzMove(2, adjustingDirection);
                    return;
                } else {
                    controlDriverSeatEnd(adjustingType);
                    return;
                }
            case 2:
                if (isCanSetSeatPos(adjustingDirection, adjustingType)) {
                    setDSeatVerMove(2, adjustingDirection);
                    return;
                } else {
                    controlDriverSeatEnd(adjustingType);
                    return;
                }
            case 3:
                if (isCanSetSeatPosForTilt(adjustingDirection, adjustingType)) {
                    setDSeatTiltMove(2, adjustingDirection);
                    return;
                } else {
                    controlDriverSeatEnd(adjustingType);
                    return;
                }
            case 4:
                if (isCanSetSeatPos(adjustingDirection, adjustingType)) {
                    setDSeatLegMove(2, adjustingDirection);
                    return;
                } else {
                    controlDriverSeatEnd(adjustingType);
                    return;
                }
            case 5:
                setDSeatLumbHorzMove(2, adjustingDirection);
                return;
            case 6:
                setDSeatLumbVerMove(2, adjustingDirection);
                return;
            case 7:
                if (isCanSetSeatPos(adjustingDirection, adjustingType)) {
                    setDSeatCushionMove(2, adjustingDirection);
                    return;
                } else {
                    controlDriverSeatEnd(adjustingType);
                    return;
                }
            default:
                return;
        }
    }

    protected int getSeatPosByDirection(int controlType) {
        if (controlType != 1) {
            if (controlType != 2) {
                if (controlType != 3) {
                    if (controlType != 4) {
                        if (controlType != 7) {
                            return -1;
                        }
                        return getDSeatCushionPos();
                    }
                    return getDSeatLegPos();
                }
                return getDSeatTiltPos();
            }
            return getDSeatVerPos();
        }
        return getDSeatHorzPos();
    }

    private boolean isCanSetSeatPos(int direction, int controlType) {
        boolean isSupportMsmD;
        if (controlType == 1 || controlType == 2 || controlType == 3 || controlType == 4) {
            isSupportMsmD = CarBaseConfig.getInstance().isSupportMsmD();
        } else {
            isSupportMsmD = controlType != 7 ? false : CarBaseConfig.getInstance().isSupportDrvCushion();
        }
        if (isSupportMsmD) {
            int seatPosByDirection = getSeatPosByDirection(controlType);
            if (seatPosByDirection < 0 || seatPosByDirection > 100) {
                return false;
            }
            return (direction == 1 && seatPosByDirection < 98) || (direction == 2 && seatPosByDirection > 2);
        }
        return true;
    }

    protected boolean isCanSetSeatPosForTilt(int direction, int controlType) {
        if (CarBaseConfig.getInstance().isSupportMsmD()) {
            int seatPosByDirection = getSeatPosByDirection(controlType);
            if (seatPosByDirection < 0 || seatPosByDirection > 100) {
                return false;
            }
            if (CarBaseConfig.getInstance().isSupportTiltPosReversed()) {
                if (direction != 1 || seatPosByDirection >= 98) {
                    return direction == 2 && seatPosByDirection > 2;
                }
                return true;
            } else if (direction != 2 || seatPosByDirection >= 98) {
                return direction == 1 && seatPosByDirection > 2;
            } else {
                return true;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x004d, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0053, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0059, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0065, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006b, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x006d, code lost:
        r3 = 40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0047, code lost:
        if (r9 != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void controlDriverSeatStart(boolean r9) {
        /*
            r8 = this;
            r0 = 1
            r8.mIsDriverSeatManualMoving = r0
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel$ManualAdjustSeatTimer r1 = r8.mDriverSeatAdjustTimer
            if (r1 == 0) goto Ld
            r1.cancel()
            r1 = 0
            r8.mDriverSeatAdjustTimer = r1
        Ld:
            com.xiaopeng.carcontrol.bean.Seat r1 = r8.mDriverSeatData
            int r1 = r1.getAdjustingType()
            com.xiaopeng.carcontrol.bean.Seat r2 = r8.mDriverSeatData
            int r2 = r2.getAdjustingDirection()
            r3 = 1000(0x3e8, double:4.94E-321)
            java.lang.String r5 = r8.TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "start controlType "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = ",direction "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r6 = r6.toString()
            r7 = 0
            com.xiaopeng.carcontrol.util.LogUtils.d(r5, r6, r7)
            r5 = 40
            switch(r1) {
                case 1: goto L68;
                case 2: goto L62;
                case 3: goto L5c;
                case 4: goto L56;
                case 5: goto L50;
                case 6: goto L4a;
                case 7: goto L44;
                default: goto L43;
            }
        L43:
            goto L6e
        L44:
            r8.setDSeatCushionMove(r0, r2)
            if (r9 == 0) goto L6e
            goto L6d
        L4a:
            r8.setDSeatLumbVerMove(r0, r2)
            if (r9 == 0) goto L6e
            goto L6d
        L50:
            r8.setDSeatLumbHorzMove(r0, r2)
            if (r9 == 0) goto L6e
            goto L6d
        L56:
            r8.setDSeatLegMove(r0, r2)
            if (r9 == 0) goto L6e
            goto L6d
        L5c:
            r8.setDSeatTiltMove(r0, r2)
            if (r9 == 0) goto L6e
            goto L6d
        L62:
            r8.setDSeatVerMove(r0, r2)
            if (r9 == 0) goto L6e
            goto L6d
        L68:
            r8.setDSeatHorzMove(r0, r2)
            if (r9 == 0) goto L6e
        L6d:
            r3 = r5
        L6e:
            com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$q44saSeeL6lovYTDFexVHLSqu_E r9 = new com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$q44saSeeL6lovYTDFexVHLSqu_E
            r9.<init>()
            com.xiaopeng.carcontrol.util.ThreadUtils.runOnMainThread(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel.controlDriverSeatStart(boolean):void");
    }

    public /* synthetic */ void lambda$controlDriverSeatStart$2$SeatViewModel(final long finalAdjustCount) {
        ManualAdjustSeatTimer manualAdjustSeatTimer = new ManualAdjustSeatTimer(true, false, finalAdjustCount);
        this.mDriverSeatAdjustTimer = manualAdjustSeatTimer;
        manualAdjustSeatTimer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlPsnSeat() {
        int adjustingType = this.mPsnSeatData.getAdjustingType();
        int adjustingDirection = this.mPsnSeatData.getAdjustingDirection();
        switch (adjustingType) {
            case 1:
                setPSeatHorzMove(2, adjustingDirection);
                return;
            case 2:
                setPSeatVerMove(2, adjustingDirection);
                return;
            case 3:
                setPSeatTiltMove(2, adjustingDirection);
                return;
            case 4:
                setPSeatLegMove(2, adjustingDirection);
                return;
            case 5:
                setPSeatLumbHorzMove(2, adjustingDirection);
                return;
            case 6:
                setPSeatLumbVerMove(2, adjustingDirection);
                return;
            default:
                return;
        }
    }

    private void controlPsnSeatStart(boolean isSpeech) {
        this.mIsPsnSeatManualMoving = true;
        ManualAdjustSeatTimer manualAdjustSeatTimer = this.mPsnSeatAdjustTimer;
        if (manualAdjustSeatTimer != null) {
            manualAdjustSeatTimer.cancel();
            this.mPsnSeatAdjustTimer = null;
        }
        int adjustingType = this.mPsnSeatData.getAdjustingType();
        int adjustingDirection = this.mPsnSeatData.getAdjustingDirection();
        final long j = 1000;
        LogUtils.d(this.TAG, "controlPsnSeatStart, controlType: " + adjustingType + ", direction: " + adjustingDirection);
        switch (adjustingType) {
            case 1:
                setPSeatHorzMove(1, adjustingDirection);
                if (isSpeech) {
                    j = SEAT_SPEECH_HOR_ADJUST_COUNT;
                    break;
                }
                break;
            case 2:
                setPSeatVerMove(1, adjustingDirection);
                if (isSpeech) {
                    j = SEAT_SPEECH_VER_ADJUST_COUNT;
                    break;
                }
                break;
            case 3:
                setPSeatTiltMove(1, adjustingDirection);
                if (isSpeech) {
                    j = 64;
                    break;
                }
                break;
            case 4:
                setPSeatLegMove(1, adjustingDirection);
                break;
            case 5:
                setPSeatLumbHorzMove(1, adjustingDirection);
                break;
            case 6:
                setPSeatLumbVerMove(1, adjustingDirection);
                break;
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$xn2e5RB-Y5h0yRx2aQi5p1qkAls
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$controlPsnSeatStart$3$SeatViewModel(j);
            }
        });
    }

    public /* synthetic */ void lambda$controlPsnSeatStart$3$SeatViewModel(final long finalAdjustCount) {
        ManualAdjustSeatTimer manualAdjustSeatTimer = new ManualAdjustSeatTimer(false, false, finalAdjustCount);
        this.mPsnSeatAdjustTimer = manualAdjustSeatTimer;
        manualAdjustSeatTimer.start();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean controlDriverSeatAuto(int[] position) {
        if (this.mIsDriverSeatManualMoving) {
            LogUtils.w(this.TAG, "controlDriverSeatAuto failed, the seat is still being adjusted");
            return false;
        }
        setDrvSeatAllPositions(position[0], position[1], position[2], position[3], position[4]);
        return true;
    }

    protected boolean controlPsnSeatAuto(int[] position) {
        if (this.mIsPsnSeatManualMoving) {
            LogUtils.w(this.TAG, "controlPsnSeatAuto failed, the seat is still being adjusted");
            return false;
        }
        setPsnSeatAllPositions(position[0], position[1], position[2], position[3]);
        return true;
    }

    public boolean memoryDrvSeatData() {
        if (memoryDrvSeatData(this.mMsmController.getDrvSeatPosIdx())) {
            NotificationHelper.getInstance().showToast(R.string.seat_saved);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean memoryDrvSeatData(int index) {
        if (!isSupportMsm()) {
            LogUtils.w(this.TAG, "Current car does not support MSMD feature", false);
            return false;
        } else if (this.mIsDriverSeatManualMoving) {
            LogUtils.w(this.TAG, "The driver seat is under adjusting, can not save seat data");
            return false;
        } else if ((index < 0 && index != -1) || index >= 3) {
            LogUtils.w(this.TAG, "The memory drv seat index is invalid: " + index, false);
            return false;
        } else {
            LogUtils.d(this.TAG, "mDriverSeatData:" + this.mDriverSeatData.toString() + ",index:" + index);
            this.mMsmController.saveDrvSeatPos(index, this.mDriverSeatData.getSeatPosition());
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setDriverAllPositionsToDomain();
            } else {
                setDriverAllPositionsToMcu();
            }
            this.mDriverSeatSavedPos = this.mDriverSeatData.getSeatPosition();
            return true;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean memorySelectPsnSeatData(int index, int selectId) {
        if (!isSupportMSMP()) {
            LogUtils.w(this.TAG, "Current car does not support MSMD feature", false);
            return false;
        } else if (this.mIsPsnSeatManualMoving) {
            LogUtils.w(this.TAG, "The driver seat is under adjusting, can not save seat data");
            return false;
        } else if ((index < 0 && index != -1) || index >= 3) {
            LogUtils.w(this.TAG, "The memory drv seat index is invalid: " + index, false);
            return false;
        } else {
            Handler handler = this.mSeatControlHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mPsnDropDownRun);
            }
            LogUtils.d(this.TAG, "mPsnSeatData:" + this.mPsnSeatData.toString() + ",index:" + index);
            this.mMsmController.savePsnSeatPos(this.mPsnSeatData.getSeatPosition(), index, selectId);
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setPsnAllPositionsToDomain();
            } else {
                setPsnSeatAllPositionsToMcu();
            }
            this.mPsnSeatSavedPos = this.mPsnSeatData.getSeatPosition();
            return true;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean memorySelectRLSeatData(int selectId) {
        if (!CarBaseConfig.getInstance().isSupportRearSeatCtrl() || !CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            LogUtils.w(this.TAG, "Current car does not support SecRowMemory feature", false);
            return false;
        } else if (this.mIsRearLeftSeatManualMoving) {
            LogUtils.w(this.TAG, "The RearLeft seat is under adjusting, can not save seat data");
            return false;
        } else {
            Handler handler = this.mSeatControlHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mRLDropDownRun);
            }
            LogUtils.d(this.TAG, "mRearLeftSeatData:" + this.mRearLeftSeatData.toSecRowString());
            this.mMsmController.saveRLSeatPos(this.mRearLeftSeatData.getSecRowPosition(), selectId);
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setRLAllPositionsToDomain();
            } else {
                setRLSeatAllPositionsToMcu();
            }
            this.mRLSeatSavedPos = this.mRearLeftSeatData.getSecRowPosition();
            return true;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean memorySelectRRSeatData(int selectId) {
        if (!CarBaseConfig.getInstance().isSupportRearSeatCtrl() || !CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            LogUtils.w(this.TAG, "Current car does not support MSMD feature", false);
            return false;
        } else if (this.mIsRearRightSeatManualMoving) {
            LogUtils.w(this.TAG, "The driver seat is under adjusting, can not save seat data");
            return false;
        } else {
            Handler handler = this.mSeatControlHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mRRDropDownRun);
            }
            LogUtils.d(this.TAG, "mRearRightSeatData:" + this.mRearRightSeatData.toSecRowString());
            this.mMsmController.saveRRSeatPos(this.mRearRightSeatData.getSecRowPosition(), selectId);
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setRRAllPositionsToDomain();
            } else {
                setRRSeatAllPositionsToMcu();
            }
            this.mRRSeatSavedPos = this.mRearRightSeatData.getSecRowPosition();
            return true;
        }
    }

    public /* synthetic */ void lambda$new$4$SeatViewModel() {
        VipSeatStatus seatStatus = VipPsnSeatControl.getInstance().getSeatStatus();
        int pSeatHorzPos = this.mMsmController.getPSeatHorzPos();
        LogUtils.d(this.TAG, "mSavePsnPosTask: current vip psn seat status is " + seatStatus + ", current psn pos is [" + pSeatHorzPos + ", " + this.mMsmController.getPSeatTiltPos() + "], mIsPsnSeatManualMoving = " + this.mIsPsnSeatManualMoving);
        if (this.mIsPsnSeatManualMoving || seatStatus != VipSeatStatus.Normal || isPsnSeatEqualMemory()) {
            return;
        }
        memoryPsnSeatData();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onDSeatHorzPosChanged(int pos) {
        Integer value = this.mDriverSeatHorz.getValue();
        this.mDriverSeatData.setSeatHorzValue(pos);
        this.mDriverSeatHorz.postValue(Integer.valueOf(pos));
        if (App.isMainProcess()) {
            if (value != null) {
                handleDropDownMenu();
            } else {
                LogUtils.d(this.TAG, " onDSeatHorzPosChanged lastPos is null");
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onDSeatVerPosChanged(int pos) {
        Integer value = this.mDriverSeatVert.getValue();
        this.mDriverSeatData.setSeatVertValue(pos);
        this.mDriverSeatVert.postValue(Integer.valueOf(pos));
        if (App.isMainProcess()) {
            if (value != null) {
                handleDropDownMenu();
            } else {
                LogUtils.d(this.TAG, " onDSeatVerPosChanged lastPos is null");
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onDSeatTiltPosChanged(int pos) {
        int[] iArr;
        Integer value = this.mDriverSeatTilt.getValue();
        this.mDriverSeatData.setSeatTiltValue(pos);
        this.mDriverSeatTilt.postValue(Integer.valueOf(pos));
        if (App.isMainProcess()) {
            if (value != null) {
                handleDropDownMenu();
            } else {
                LogUtils.d(this.TAG, " onDSeatTiltPosChanged lastPos is null");
            }
        }
        if (BaseFeatureOption.getInstance().isSupportSeatSectionalRecovery()) {
            if (this.mDriverSeatSavedPos == null) {
                this.mDriverSeatSavedPos = this.mMsmController.getDrvSeatPos(0);
            }
            if (this.mIsDrvRestoreSafeChairBack && (iArr = this.mDriverSeatSavedPos) != null && Math.abs(Math.min(50, iArr[2]) - pos) < 2) {
                if (!isDrvHeadrestNormal() && !this.mIsPsnRestoreSafeChairBack) {
                    headrestNotice();
                }
                controlDriverSeatAuto(this.mDriverSeatSavedPos);
                this.mIsDrvRestoreSafeChairBack = false;
            }
            if (this.mSeatControlHandler == null) {
                this.mSeatControlHandler = new Handler(Looper.getMainLooper());
            }
            this.mSeatControlHandler.removeCallbacks(this.resumeDrvRestoreSafeChairRun);
            if (this.mIsDrvRestoreSafeChairBack) {
                this.mSeatControlHandler.postDelayed(this.resumeDrvRestoreSafeChairRun, 1000L);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onDSeatLegPosChanged(int pos) {
        Integer value = this.mDriverSeatLeg.getValue();
        this.mDriverSeatData.setSeatLegValue(pos);
        this.mDriverSeatLeg.postValue(Integer.valueOf(pos));
        if (App.isMainProcess()) {
            if (value != null) {
                handleDropDownMenu();
            } else {
                LogUtils.d(this.TAG, " onDSeatLegPosChanged lastPos is null");
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onDSeatCushionPosChanged(int pos) {
        Integer value = this.mDriverSeatCushion.getValue();
        this.mDriverSeatData.setSeatCushionValue(pos);
        this.mDriverSeatCushion.postValue(Integer.valueOf(pos));
        if (App.isMainProcess()) {
            if (value != null) {
                handleDropDownMenu();
            } else {
                LogUtils.d(this.TAG, "onDSeatLegHorPosChanged last pos is null");
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onPsnOnSeatChanged(boolean occupied) {
        this.mPsnSeatOccupied.postValue(Boolean.valueOf(occupied));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRlOnSeatChanged(boolean occupied) {
        this.mRlSeatOccupied.postValue(Boolean.valueOf(occupied));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRmOnSeatChanged(boolean occupied) {
        this.mRmSeatOccupied.postValue(Boolean.valueOf(occupied));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRrOnSeatChanged(boolean occupied) {
        this.mRrSeatOccupied.postValue(Boolean.valueOf(occupied));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onDrvBeltWaringChanged(int mode) {
        this.mDvrBeltWarn.postValue(Boolean.valueOf(mode == 1));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onPSeatHorzPosChanged(int pos) {
        Integer value = this.mPsnSeatHorz.getValue();
        this.mPsnSeatData.setSeatHorzValue(pos);
        this.mPsnSeatHorz.postValue(Integer.valueOf(pos));
        if (value == null || !App.isMainProcess()) {
            return;
        }
        if (BaseFeatureOption.getInstance().isSupportPsnSeatManualSave()) {
            handlePsnDropDownMenu();
        } else {
            handlePSeatPosChange();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onPSeatTiltPosChanged(int pos) {
        int[] iArr;
        Integer value = this.mPsnSeatTilt.getValue();
        this.mPsnSeatData.setSeatTiltValue(pos);
        this.mPsnSeatTilt.postValue(Integer.valueOf(pos));
        if (value != null && App.isMainProcess()) {
            if (BaseFeatureOption.getInstance().isSupportPsnSeatManualSave()) {
                handlePsnDropDownMenu();
            } else {
                handlePSeatPosChange();
            }
        }
        if (BaseFeatureOption.getInstance().isSupportSeatSectionalRecovery()) {
            if (this.mPsnSeatSavedPos == null) {
                this.mPsnSeatSavedPos = this.mMsmController.getPsnSeatPos();
            }
            if (this.mIsPsnRestoreSafeChairBack && (iArr = this.mPsnSeatSavedPos) != null && Math.abs(Math.min(50, iArr[2]) - getPSeatTiltPos()) < 2) {
                if (!isPsnHeadrestNormal() && !this.mIsDrvRestoreSafeChairBack) {
                    headrestNotice();
                }
                controlPsnSeatAuto(this.mPsnSeatSavedPos);
                this.mIsPsnRestoreSafeChairBack = false;
            }
            if (this.mSeatControlHandler == null) {
                this.mSeatControlHandler = new Handler(Looper.getMainLooper());
            }
            this.mSeatControlHandler.removeCallbacks(this.resumePsnRestoreSafeChairRun);
            if (this.mIsPsnRestoreSafeChairBack) {
                this.mSeatControlHandler.postDelayed(this.resumePsnRestoreSafeChairRun, 1000L);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onPSeatVerPosChanged(int pos) {
        Integer value = this.mPsnSeatVer.getValue();
        this.mPsnSeatData.setSeatVertValue(pos);
        this.mPsnSeatVer.postValue(Integer.valueOf(pos));
        if (value == null || !App.isMainProcess()) {
            return;
        }
        if (BaseFeatureOption.getInstance().isSupportPsnSeatManualSave()) {
            handlePsnDropDownMenu();
        } else {
            handlePSeatPosChange();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onPSeatLegPosChanged(int pos) {
        Integer value = this.mPsnSeatLeg.getValue();
        this.mPsnSeatData.setSeatLegValue(pos);
        this.mPsnSeatLeg.postValue(Integer.valueOf(pos));
        if (value == null || !App.isMainProcess()) {
            return;
        }
        if (BaseFeatureOption.getInstance().isSupportPsnSeatManualSave()) {
            handlePsnDropDownMenu();
        } else {
            handlePSeatPosChange();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRLSeatTiltPosChanged(int pos) {
        this.mRLSeatTilt.getValue();
        this.mRearLeftSeatData.setSeatTiltValue(pos);
        this.mRLSeatTilt.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRRSeatTiltPosChanged(int pos) {
        this.mRRSeatTilt.getValue();
        this.mRearRightSeatData.setSeatTiltValue(pos);
        this.mRRSeatTilt.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRLSeatLegPosChanged(int pos) {
        this.mRLSeatLeg.getValue();
        this.mRearLeftSeatData.setSeatLegValue(pos);
        this.mRLSeatLeg.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRRSeatLegPosChanged(int pos) {
        this.mRRSeatLeg.getValue();
        this.mRearRightSeatData.setSeatLegValue(pos);
        this.mRRSeatLeg.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowLtSeatHorzPosChanged(int pos) {
        this.mSecRowLtSeatHorz.getValue();
        this.mRearLeftSeatData.setSeatHorzValue(pos);
        this.mSecRowLtSeatHorz.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowRtSeatHorzPosChanged(int pos) {
        this.mSecRowRtSeatHorz.getValue();
        this.mRearRightSeatData.setSeatHorzValue(pos);
        this.mSecRowRtSeatHorz.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowLtSeatLeSuHeigPosChanged(int pos) {
        this.mSecRowLtSeatLeSuHeig.getValue();
        this.mRearLeftSeatData.setSeatCushionValue(pos);
        this.mSecRowLtSeatLeSuHeig.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowRtSeatLeSuHeigPosChanged(int pos) {
        this.mSecRowRtSeatLeSuHeig.getValue();
        this.mRearRightSeatData.setSeatCushionValue(pos);
        this.mSecRowRtSeatLeSuHeig.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowLtSeatHeReHeigPosChanged(int pos) {
        this.mSecRowLtSeatHeadHeig.getValue();
        this.mRearLeftSeatData.setSeatHeadVer(pos);
        this.mSecRowLtSeatHeadHeig.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowLtSeatHeReHorzPosChanged(int pos) {
        this.mSecRowLtSeatHeadHorz.getValue();
        this.mRearLeftSeatData.setSeatHeadHor(pos);
        this.mSecRowLtSeatHeadHorz.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowRtSeatHeReHeigPosChanged(int pos) {
        this.mSecRowRtSeatHeadHeig.getValue();
        this.mRearRightSeatData.setSeatHeadVer(pos);
        this.mSecRowRtSeatHeadHeig.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowRtSeatHeReHorzPosChanged(int pos) {
        this.mSecRowRtSeatHeadHorz.getValue();
        this.mRearRightSeatData.setSeatHeadHor(pos);
        this.mSecRowRtSeatHeadHorz.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowLtSeatZeroGravPosChanged(int pos) {
        this.mSecRowLtSeatAngle.getValue();
        this.mRearLeftSeatData.setSeatZeroAngle(pos);
        this.mSecRowLtSeatAngle.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowRtSeatZeroGravPosChanged(int pos) {
        this.mSecRowRtSeatAngle.getValue();
        this.mRearRightSeatData.setSeatZeroAngle(pos);
        this.mSecRowRtSeatAngle.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowLtEzOrZeroGStatusChanged(int status) {
        LogUtils.d(this.TAG, "onSecRowLtEzOrZeroGStatusChanged   status = " + status);
        this.mSecRowLtEzOrZeroGStatus.postValue(Integer.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onSecRowRtEzOrZeroGStatusChanged(int status) {
        LogUtils.d(this.TAG, "onSecRowRtEzOrZeroGStatusChanged   status = " + status);
        this.mSecRowRtEzOrZeroGStatus.postValue(Integer.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onTrdRowLtFoldStatusChanged(int status) {
        LogUtils.d(this.TAG, "onTrdRowLtFoldStatusChanged   status = " + status);
        this.mTrdRowLtFoldStatus.postValue(Integer.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onTrdRowRtFoldStatusChanged(int status) {
        LogUtils.d(this.TAG, "onTrdRowRtFoldStatusChanged   status = " + status);
        this.mTrdRowRtFoldStatus.postValue(Integer.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onTrdRowStowStatusChanged(int status) {
        LogUtils.d(this.TAG, "onTrdRowStowStatusChanged   status = " + status);
        this.mTrdRowStowStatus.postValue(Integer.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onTrdRowLtSeatTiltPosChangedpos(int pos) {
        LogUtils.d(this.TAG, "onTrdRowLtSeatTiltPosChangedpos   pos = " + pos);
        this.mTrdRowLtSeatTiltPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onTrdRowRtSeatTiltPosChanged(int pos) {
        LogUtils.d(this.TAG, "onTrdRowRtSeatTiltPosChanged   pos = " + pos);
        this.mTrdRowRtSeatTiltPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onTrdRowLtSeatHeadVerticalPosChanged(int pos) {
        LogUtils.d(this.TAG, "onTrdRowLtSeatHeadVerticalPosChanged   pos = " + pos);
        this.mTrdRowLtSeatHeadVerticalPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onTrdRowRtSeatHeadVerticalPosChanged(int pos) {
        LogUtils.d(this.TAG, "onTrdRowRtSeatHeadVerticalPosChanged   pos = " + pos);
        this.mTrdRowRtSeatHeadVerticalPos.postValue(Integer.valueOf(pos));
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onTrdRowMidSeatHeadVerticalPosChanged(int pos) {
        LogUtils.d(this.TAG, "onTrdRowMidSeatHeadVerticalPosChanged   pos = " + pos);
        this.mTrdRowMidSeatHeadVerticalPos.postValue(Integer.valueOf(pos));
    }

    private void handlePSeatPosChange() {
        boolean z = VipPsnSeatControl.getInstance().getSeatStatus() == VipSeatStatus.Normal && VipCapsuleSeatControl.getInstance().getSeatStatus() == VipSeatStatus.Normal && VipCapsuleSingleSeatControl.getInstance().getSeatStatus() == VipSeatStatus.Normal;
        int pSeatHorzPos = this.mMsmController.getPSeatHorzPos();
        int pSeatTiltPos = this.mMsmController.getPSeatTiltPos();
        LogUtils.d(this.TAG, "handlePSeatPosChange: current vip psn seat status is " + z + ", current psn pos is [" + pSeatHorzPos + ", " + pSeatTiltPos + "], mIsPsnSeatManualMoving=" + this.mIsPsnSeatManualMoving);
        if (this.mIsPsnSeatManualMoving || !z) {
            return;
        }
        this.mPsnSeatData.setSeatHorzValue(pSeatHorzPos);
        this.mPsnSeatData.setSeatTiltValue(pSeatTiltPos);
        if (this.mSeatControlHandler == null) {
            this.mSeatControlHandler = new Handler(Looper.getMainLooper());
        }
        this.mSeatControlHandler.removeCallbacks(this.mSavePsnPosTask);
        this.mSeatControlHandler.postDelayed(this.mSavePsnPosTask, 1000L);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onDrvHeadrestChanged(boolean normal) {
        this.mDrvHeadrestNormal.postValue(Boolean.valueOf(normal));
        if (normal) {
            return;
        }
        if (BaseFeatureOption.getInstance().isSupportPopPanel()) {
            PopPanelManager.getInstance().dismiss("seat_mirror_menu");
        } else {
            DropDownMenuManager.getInstance().dismiss("seat_mirror_menu");
        }
    }

    public LiveData<Boolean> getDrvHeadrestNormal() {
        return this.mDrvHeadrestNormal;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onPsnHeadrestChanged(boolean normal) {
        this.mPsnHeadrestNormal.postValue(Boolean.valueOf(normal));
        if (BaseFeatureOption.getInstance().isSupportPopPanel()) {
            PopPanelManager.getInstance().dismiss("psn_seat_menu");
        } else {
            DropDownMenuManager.getInstance().dismiss("psn_seat_menu");
        }
    }

    public LiveData<Boolean> getPsnHeadrestNormal() {
        return this.mPsnHeadrestNormal;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onBackBeltWaringChanged(boolean status) {
        this.mBackBeltWaring.postValue(Boolean.valueOf(status));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handlePsnSrsEnableChanged(boolean enable) {
        this.mPsnSrsEnableData.postValue(Boolean.valueOf(enable));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    public void handlePsnSrsNoResponse() {
        NotificationHelper.getInstance().showToast(R.string.psn_srs_error);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handleWelcomeModeChanged(boolean enabled) {
        this.mSeatWelcomeMode.postValue(Boolean.valueOf(enabled));
        if (App.isMainProcess()) {
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setDriverAllPositionsToDomain();
            } else {
                setDriverAllPositionsToMcu();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handlePsnWelcomeModeChanged(boolean enabled) {
        this.mPsnSeatWelcomeMode.postValue(Boolean.valueOf(enabled));
        if (App.isMainProcess()) {
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setPsnAllPositionsToDomain();
            } else {
                setPsnSeatAllPositionsToMcu();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handleRearSeatWelcomeModeChanged(boolean enabled) {
        this.mRearSeatWelcomeMode.postValue(Boolean.valueOf(enabled));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    public void handleEsbChanged(boolean enabled) {
        this.mEsbMode.postValue(Boolean.valueOf(enabled));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handleEsbConfigChanged(String configMode) {
        this.mEsbConfigMode.postValue(configMode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handleDrvLumbControlEnableChanged(boolean enable) {
        this.mDrvLumbData.postValue(Boolean.valueOf(enable));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.SeatBaseViewModel
    protected void handlePsnLumbControlEnableChanged(boolean enable) {
        this.mPsnLumbData.postValue(Boolean.valueOf(enable));
    }

    public void handleSeatLumbControlViewShow(boolean show) {
        this.mSeatLumbShowData.postValue(Boolean.valueOf(show));
    }

    public LiveData<Boolean> getEsbModeData() {
        return this.mEsbMode;
    }

    public LiveData<String> getEsbConfigModeData() {
        return this.mEsbConfigMode;
    }

    public LiveData<Boolean> getBackBeltWaringData() {
        return this.mBackBeltWaring;
    }

    public LiveData<Boolean> getPsnSrsEnableData() {
        return this.mPsnSrsEnableData;
    }

    public LiveData<Boolean> getDrvSeatDropDownShowData() {
        return this.mDrvSeatDropDownShowData;
    }

    public LiveData<Boolean> getPsnSeatDropDownShowData() {
        return this.mPsnSeatDropDownShowData;
    }

    public MutableLiveData<Boolean> getDrvLumbData() {
        return this.mDrvLumbData;
    }

    public MutableLiveData<Boolean> getPsnLumbData() {
        return this.mPsnLumbData;
    }

    public MutableLiveData<Boolean> getSeatLumbShowData() {
        return this.mSeatLumbShowData;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreDrvSeatPos() {
        if (restoreDrvSeatPos(this.mMsmController.getDrvSeatPosIdx())) {
            NotificationHelper.getInstance().showToast(R.string.seat_resuming);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restorePsnSelectSeatPos(int selectId, boolean isSelect) {
        if (restorePsnSeatPos(this.mMsmController.getPsnSeatPosIdx(selectId), selectId, isSelect)) {
            if (isSelect) {
                NotificationHelper.getInstance().showToast(R.string.seat_selecting);
                return true;
            }
            NotificationHelper.getInstance().showToast(R.string.seat_resuming);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreRLSelectSeatPos(int selectId, boolean isSelect) {
        if (restoreRLSeatPos(selectId, isSelect)) {
            if (isSelect) {
                NotificationHelper.getInstance().showToast(R.string.seat_selecting);
                return true;
            }
            NotificationHelper.getInstance().showToast(R.string.seat_resuming);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreRRSelectSeatPos(int selectId, boolean isSelect) {
        if (restoreRRSeatPos(selectId, isSelect)) {
            if (isSelect) {
                NotificationHelper.getInstance().showToast(R.string.seat_selecting);
                return true;
            }
            NotificationHelper.getInstance().showToast(R.string.seat_resuming);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean saveSelectPsnSeatData(int selectId) {
        if (memorySelectPsnSeatData(this.mMsmController.getPsnSeatPosIdx(selectId), selectId)) {
            NotificationHelper.getInstance().showToast(R.string.seat_saved);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean saveSelectRLSeatData(int selectId) {
        if (memorySelectRLSeatData(selectId)) {
            NotificationHelper.getInstance().showToast(R.string.seat_saved);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean saveSelectRRSeatData(int selectId) {
        if (memorySelectRRSeatData(selectId)) {
            NotificationHelper.getInstance().showToast(R.string.seat_saved);
            return true;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void savePsnSeatName(String posName, int selectId) {
        this.mMsmController.savePsnSeatName(posName, selectId);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void saveRLSeatName(String posName, int selectId) {
        this.mMsmController.saveRLSeatName(posName, selectId);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void saveRRSeatName(String posName, int selectId) {
        this.mMsmController.saveRRSeatName(posName, selectId);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getCurrentSelectedPsnHabit() {
        return this.mMsmController.getCurrentSelectedPsnHabit();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getCurrentSelectedRLHabit() {
        return this.mMsmController.getCurrentSelectedRLHabit();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getCurrentSelectedRRHabit() {
        return this.mMsmController.getCurrentSelectedRRHabit();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public String getPsnSeatName(int selectId) {
        return this.mMsmController.getPsnSeatName(selectId);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public String getRLSeatName(int selectId) {
        return this.mMsmController.getRLSeatName(selectId);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public String getRRSeatName(int selectId) {
        return this.mMsmController.getRRSeatName(selectId);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreDrvSeatPos(boolean ignoreDrvDoorState, boolean ignoreDrvState) {
        return restoreDrvSeatPos(this.mMsmController.getDrvSeatPosIdx(), ignoreDrvDoorState, ignoreDrvState);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreDrvSeatPos(int index) {
        if (!isSupportMsm()) {
            LogUtils.w(this.TAG, "Current car does not support MSMD feature", false);
            return false;
        } else if (checkChairMovable()) {
            if ((index < 0 && index != -1) || index >= 3) {
                LogUtils.w(this.TAG, "restoreDrvSeatPos The memory drv seat index is invalid: " + index, false);
                return false;
            }
            int[] drvSeatPos = this.mMsmController.getDrvSeatPos(index);
            if (drvSeatPos == null && !CarStatusUtils.isE28CarType()) {
                LogUtils.d(this.TAG, "restoreDrvSeatPos mDriverSeatSavedPos no save seat data index=" + index);
                return true;
            }
            Handler handler = this.mSeatControlHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mDropDownRun);
            }
            if (BaseFeatureOption.getInstance().isSupportPopPanel()) {
                PopPanelManager.getInstance().dismiss("seat_mirror_menu");
            } else {
                DropDownMenuManager.getInstance().dismiss("seat_mirror_menu");
            }
            this.mDriverSeatSavedPos = Seat.fromPosition(drvSeatPos).getSeatPosition();
            LogUtils.i(this.TAG, "restoreDrvSeatPos mDriverSeatSavedPos index=" + index + ", position=" + Arrays.toString(this.mDriverSeatSavedPos), false);
            int min = Math.min(50, this.mDriverSeatSavedPos[2]);
            if (CarBaseConfig.getInstance().isSupportVipSeat() && getDSeatTiltPos() < 50 && Math.abs(min - getDSeatTiltPos()) >= 2) {
                this.mIsDrvRestoreSafeChairBack = true;
                setDSeatTiltPos(min);
                return true;
            }
            return controlDriverSeatAuto(this.mDriverSeatSavedPos);
        } else {
            return false;
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restorePsnSeatPos(int index, int selectId, boolean isSelected) {
        if (!isSupportMSMP()) {
            LogUtils.w(this.TAG, "Current car does not support MSM feature", false);
            return false;
        }
        int[] psnSelectSeatPos = this.mMsmController.getPsnSelectSeatPos(selectId);
        if (psnSelectSeatPos == null) {
            LogUtils.d(this.TAG, "restorePsnSeatPos mPsnSeatSavedPos no save seat data");
            return true;
        }
        Handler handler = this.mSeatControlHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mPsnDropDownRun);
        }
        this.mPsnSeatSavedPos = Seat.fromPosition(psnSelectSeatPos).getSeatPosition();
        if (isSelected) {
            this.mInPsnSelectProcessData.postValue(true);
            this.mMsmController.savePsnSeatPos(this.mPsnSeatSavedPos, index, selectId);
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setPsnAllPositionsToDomain();
            } else {
                setPsnSeatAllPositionsToMcu();
            }
        }
        LogUtils.d(this.TAG, "restorePsnSeatPos mPsnSeatSavedPoshor:" + this.mPsnSeatSavedPos[0] + ",ver:" + this.mPsnSeatSavedPos[1] + ",tilt:" + this.mPsnSeatSavedPos[2] + ",leg:" + this.mPsnSeatSavedPos[3]);
        if (this.mIsPsnSeatManualMoving) {
            LogUtils.w(this.TAG, "controlPsnSeatAuto failed, the seat is still being adjusted");
            return false;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$Mbtmf5F8p404rxAdPeZXEAz2fME
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$restorePsnSeatPos$5$SeatViewModel();
            }
        }, 500L);
        return true;
    }

    public /* synthetic */ void lambda$restorePsnSeatPos$5$SeatViewModel() {
        int[] iArr = this.mPsnSeatSavedPos;
        setPsnSeatAllPositions(iArr[0], iArr[1], iArr[2], iArr[3]);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreRLSeatPos(int selectId, boolean isSelected) {
        if (!CarBaseConfig.getInstance().isSupportRearSeatCtrl() || !CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            LogUtils.w(this.TAG, "Current car does not support MSM feature", false);
            return false;
        }
        int[] rLSelectSeatPos = this.mMsmController.getRLSelectSeatPos(selectId);
        if (rLSelectSeatPos == null) {
            LogUtils.d(this.TAG, "restorePsnSeatPos mPsnSeatSavedPos no save seat data");
            return true;
        }
        Handler handler = this.mSeatControlHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mRLDropDownRun);
        }
        this.mRLSeatSavedPos = Seat.fromSecRowPosition(rLSelectSeatPos).getSecRowPosition();
        if (isSelected) {
            this.mInRLSelectProcessData.postValue(true);
            this.mMsmController.saveRLSeatPos(this.mRLSeatSavedPos, selectId);
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setRLAllPositionsToDomain();
            } else {
                setRLSeatAllPositionsToMcu();
            }
        }
        LogUtils.d(this.TAG, "restoreRLSeatPos mRLSeatSavedPos:" + this.mRLSeatSavedPos[0] + ",angle:" + this.mRLSeatSavedPos[1] + ",tilt:" + this.mRLSeatSavedPos[2] + ",cushion:" + this.mRLSeatSavedPos[3] + ",leg:" + this.mRLSeatSavedPos[4] + ",headVer:" + this.mRLSeatSavedPos[5] + ",headHor:" + this.mRLSeatSavedPos[6]);
        if (this.mIsPsnSeatManualMoving) {
            LogUtils.w(this.TAG, "restoreRLSeatPos failed, the seat is still being adjusted");
            return false;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$LMGnr-2yeIl6wC5GmPRv8XdSbcM
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$restoreRLSeatPos$6$SeatViewModel();
            }
        }, 500L);
        return true;
    }

    public /* synthetic */ void lambda$restoreRLSeatPos$6$SeatViewModel() {
        int[] iArr = this.mRLSeatSavedPos;
        setRLSeatAllPositions(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], iArr[6]);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreRRSeatPos(int selectId, boolean isSelected) {
        if (!CarBaseConfig.getInstance().isSupportRearSeatCtrl() || !CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            LogUtils.w(this.TAG, "Current car does not support Rear feature", false);
            return false;
        }
        int[] rRSelectSeatPos = this.mMsmController.getRRSelectSeatPos(selectId);
        if (rRSelectSeatPos == null) {
            LogUtils.d(this.TAG, "restoreRRSeatPos RearRight no save seat data");
            return true;
        }
        Handler handler = this.mSeatControlHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mRRDropDownRun);
        }
        this.mRRSeatSavedPos = Seat.fromSecRowPosition(rRSelectSeatPos).getSecRowPosition();
        if (isSelected) {
            this.mInRLSelectProcessData.postValue(true);
            this.mMsmController.saveRRSeatPos(this.mRRSeatSavedPos, selectId);
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setRRAllPositionsToDomain();
            } else {
                setRRSeatAllPositionsToMcu();
            }
        }
        LogUtils.d(this.TAG, "restoreRRSeatPos RearRightPos:" + this.mRRSeatSavedPos[0] + ",ver:" + this.mRRSeatSavedPos[1] + ",tilt:" + this.mRRSeatSavedPos[2] + ",leg:" + this.mRRSeatSavedPos[3] + ",cushion:" + this.mRRSeatSavedPos[4]);
        if (this.mIsRearRightSeatManualMoving) {
            LogUtils.w(this.TAG, "restoreRRSeatPos failed, the seat is still being adjusted");
            return false;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$OAOka9qU3D6eM2QJBvkSt34R90Y
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$restoreRRSeatPos$7$SeatViewModel();
            }
        }, 500L);
        return true;
    }

    public /* synthetic */ void lambda$restoreRRSeatPos$7$SeatViewModel() {
        int[] iArr = this.mRRSeatSavedPos;
        setRRSeatAllPositions(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], iArr[6]);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restoreDrvSeatPos(int index, boolean ignoreDrvDoorState, boolean ignoreDrvState) {
        if (!isSupportMsm()) {
            LogUtils.w(this.TAG, "Current car does not support MSMD feature", false);
            return false;
        } else if (!checkChairMovable(ignoreDrvDoorState, ignoreDrvState)) {
            LogUtils.w(this.TAG, "checkChairMovable: False", false);
            return false;
        } else if ((index < 0 && index != -1) || index >= 3) {
            LogUtils.w(this.TAG, "restoreDrvSeatPos The memory drv seat index is invalid: " + index, false);
            return false;
        } else {
            int[] drvSeatPos = this.mMsmController.getDrvSeatPos(index);
            Handler handler = this.mSeatControlHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mDropDownRun);
            }
            if (BaseFeatureOption.getInstance().isSupportPopPanel()) {
                PopPanelManager.getInstance().dismiss("seat_mirror_menu");
            } else {
                DropDownMenuManager.getInstance().dismiss("seat_mirror_menu");
            }
            this.mDriverSeatSavedPos = Seat.fromPosition(drvSeatPos).getSeatPosition();
            LogUtils.i(this.TAG, "restoreDrvSeatPos mDriverSeatSavedPos index=" + index + ", position=" + Arrays.toString(this.mDriverSeatSavedPos), false);
            int min = Math.min(50, this.mDriverSeatSavedPos[2]);
            if (CarBaseConfig.getInstance().isSupportVipSeat() && getDSeatTiltPos() < 50 && Math.abs(min - getDSeatTiltPos()) >= 2) {
                this.mIsDrvRestoreSafeChairBack = true;
                setDSeatTiltPos(min);
                return true;
            }
            return controlDriverSeatAuto(this.mDriverSeatSavedPos);
        }
    }

    private void showSeatDisableToast() {
        NotificationHelper.getInstance().showToast(R.string.seat_cdu_control_disable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isDrvSeatEqualMemory() {
        if (isSupportMsm()) {
            int[] drvSeatPos = this.mMsmController.getDrvSeatPos(this.mMsmController.getDrvSeatPosIdx());
            if (drvSeatPos == null || this.mDriverSeatData == null) {
                return false;
            }
            LogUtils.d(this.TAG, "isDrvSeatEqualMemory: saved drv pos is " + Arrays.toString(drvSeatPos) + ",current" + this.mDriverSeatData.toString());
            return this.mDriverSeatData.isPositionEqual(drvSeatPos);
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isPsnSeatEqualMemory() {
        boolean z = false;
        if (isSupportMSMP()) {
            int[] psnSeatPos = this.mMsmController.getPsnSeatPos();
            boolean z2 = psnSeatPos != null && Math.abs(psnSeatPos[0] - this.mMsmController.getPSeatHorzPos()) < 2 && Math.abs(psnSeatPos[2] - this.mMsmController.getPSeatTiltPos()) < 2;
            if (BaseFeatureOption.getInstance().isSupportPsnSeatVerControl()) {
                z2 = z2 && Math.abs(psnSeatPos[1] - this.mMsmController.getPSeatVerPos()) < 2;
            }
            if (CarBaseConfig.getInstance().isSupportPsnLeg()) {
                if (z2 && Math.abs(psnSeatPos[3] - this.mMsmController.getPSeatLegPos()) < 2) {
                    z = true;
                }
                z2 = z;
            }
            LogUtils.d(this.TAG, "isPsnSeatEqualMemory: saved psn pos is " + Arrays.toString(psnSeatPos) + ",current" + this.mPsnSeatData.toString() + ", result: " + z2);
            return z2;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isRLSeatEqualMemory() {
        boolean z = false;
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            int[] rLSeatPos = this.mMsmController.getRLSeatPos();
            boolean z2 = rLSeatPos != null && Math.abs(rLSeatPos[0] - this.mMsmController.getRLSeatHorPos()) < 2 && Math.abs(rLSeatPos[2] - this.mMsmController.getRLSeatTiltPos()) < 2;
            if (CarBaseConfig.getInstance().isSupportSecRowLegVer()) {
                z2 = z2 && Math.abs(rLSeatPos[3] - this.mMsmController.getRLSeatLegVerPos()) < 2;
            }
            if (CarBaseConfig.getInstance().isSupportSecRowHor()) {
                z2 = z2 && Math.abs(rLSeatPos[4] - this.mMsmController.getRLSeatLegHorPos()) < 2;
            }
            if (CarBaseConfig.getInstance().isSupportSecRowHeadRest()) {
                if ((z2 && Math.abs(rLSeatPos[5] - this.mMsmController.getRLSeatHeadVerPos()) < 2) && Math.abs(rLSeatPos[6] - this.mMsmController.getRLSeatHeadHorPos()) < 2) {
                    z = true;
                }
                z2 = z;
            }
            LogUtils.d(this.TAG, "isRLSeatEqualMemory: saved RL pos is " + Arrays.toString(rLSeatPos) + ",current" + this.mRearLeftSeatData.toSecRowString() + ", result: " + z2);
            return z2;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isRRSeatEqualMemory() {
        boolean z = false;
        if (CarBaseConfig.getInstance().isSupportSecRowMemory()) {
            int[] rRSeatPos = this.mMsmController.getRRSeatPos();
            boolean z2 = rRSeatPos != null && Math.abs(rRSeatPos[0] - this.mMsmController.getRRSeatHorPos()) < 2 && Math.abs(rRSeatPos[2] - this.mMsmController.getRRSeatTiltPos()) < 2;
            if (CarBaseConfig.getInstance().isSupportSecRowLegVer()) {
                z2 = z2 && Math.abs(rRSeatPos[3] - this.mMsmController.getRRSeatLegVerPos()) < 2;
            }
            if (CarBaseConfig.getInstance().isSupportSecRowHor()) {
                z2 = z2 && Math.abs(rRSeatPos[4] - this.mMsmController.getRRSeatLegHorPos()) < 2;
            }
            if (CarBaseConfig.getInstance().isSupportSecRowHeadRest()) {
                if ((z2 && Math.abs(rRSeatPos[5] - this.mMsmController.getRRSeatHeadVerPos()) < 2) && Math.abs(rRSeatPos[6] - this.mMsmController.getRRSeatHeadHorPos()) < 2) {
                    z = true;
                }
                z2 = z;
            }
            LogUtils.d(this.TAG, "isRRSeatEqualMemory: saved RR pos is " + Arrays.toString(rRSeatPos) + ",current" + this.mRearRightSeatData.toSecRowString() + ", result: " + z2);
            return z2;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean restorePsnSeatPos() {
        if (!isSupportMSMP()) {
            LogUtils.w(this.TAG, "Current car does not support MSMD feature", false);
            return false;
        }
        Handler handler = this.mSeatControlHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mPsnDropDownRun);
        }
        if (BaseFeatureOption.getInstance().isSupportPopPanel()) {
            PopPanelManager.getInstance().dismiss("psn_seat_menu");
        }
        int[] psnSeatPos = this.mMsmController.getPsnSeatPos();
        LogUtils.i(this.TAG, "restorePsnSeatPos:" + Arrays.toString(psnSeatPos));
        this.mPsnSeatSavedPos = Seat.fromPosition(psnSeatPos).getSeatPosition();
        this.mMsmController.savePsnSeatPos(this.mPsnSeatSavedPos);
        LogUtils.d(this.TAG, "restorePsnSeatPos mPsnSeatSavedPoshor:" + this.mPsnSeatSavedPos[0] + ",ver:" + this.mPsnSeatSavedPos[1] + ",tilt:" + this.mPsnSeatSavedPos[2] + ",leg:" + this.mPsnSeatSavedPos[3]);
        int min = Math.min(50, this.mPsnSeatSavedPos[2]);
        if (CarBaseConfig.getInstance().isSupportVipSeat() && getPSeatTiltPos() < 50 && Math.abs(min - getPSeatTiltPos()) >= 2) {
            this.mIsPsnRestoreSafeChairBack = true;
            setPSeatTiltPos(min);
            return true;
        }
        return controlPsnSeatAuto(this.mPsnSeatSavedPos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean memoryPsnSeatData() {
        if (isPsnHeadrestNormal() && isPsnTiltMovingSafe()) {
            this.mPsnSeatSavedPos = this.mPsnSeatData.getSeatPosition();
            this.mMsmController.savePsnSeatPos(this.mPsnSeatSavedPos);
            if (BaseFeatureOption.getInstance().isSupportDomainControllerSeatWelcome()) {
                setPsnAllPositionsToDomain();
                return true;
            }
            setPsnSeatAllPositionsToMcu();
            return true;
        }
        LogUtils.w(this.TAG, "The psn seat headrest is moved, cant save seat data");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ManualAdjustSeatTimer extends CountDownTimer {
        private final boolean mIsDriverSite;
        private final boolean mIsRearSeat;
        private boolean mIsTrdRowLeft;
        private boolean mIsTrdRowMid;
        private boolean mIsTrdRowRight;

        ManualAdjustSeatTimer(boolean isDriverSite, boolean isRearSeat, long count) {
            super(count * SeatViewModel.SEAT_ADJUST_INTERVAL_TIME, SeatViewModel.SEAT_ADJUST_INTERVAL_TIME);
            this.mIsTrdRowLeft = false;
            this.mIsTrdRowRight = false;
            this.mIsTrdRowMid = false;
            this.mIsDriverSite = isDriverSite;
            this.mIsRearSeat = isRearSeat;
        }

        ManualAdjustSeatTimer(boolean isDriverSite, boolean isRearSeat, boolean isTrdRowLeft, boolean isTrdRowRight, boolean isTrdRowMid, long count) {
            super(count * SeatViewModel.SEAT_ADJUST_INTERVAL_TIME, SeatViewModel.SEAT_ADJUST_INTERVAL_TIME);
            this.mIsTrdRowLeft = false;
            this.mIsTrdRowRight = false;
            this.mIsTrdRowMid = false;
            this.mIsDriverSite = isDriverSite;
            this.mIsRearSeat = isRearSeat;
            this.mIsTrdRowLeft = isTrdRowLeft;
            this.mIsTrdRowRight = isTrdRowRight;
            this.mIsTrdRowMid = isTrdRowMid;
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            if (this.mIsTrdRowLeft) {
                if (SeatViewModel.this.mMcuController.getIgStatusFromMcu() == 1) {
                    SeatViewModel.this.controlTrdRowLeftSeat();
                    return;
                }
                SeatViewModel seatViewModel = SeatViewModel.this;
                seatViewModel.controlTrdRowLeftSeatEnd(seatViewModel.mTrdRowLeftSeatData.getAdjustingType());
            } else if (this.mIsTrdRowRight) {
                if (SeatViewModel.this.mMcuController.getIgStatusFromMcu() == 1) {
                    SeatViewModel.this.controlTrdRowRightSeat();
                    return;
                }
                SeatViewModel seatViewModel2 = SeatViewModel.this;
                seatViewModel2.controlTrdRowRightSeatEnd(seatViewModel2.mTrdRowRightSeatData.getAdjustingType());
            } else if (this.mIsTrdRowMid) {
                if (SeatViewModel.this.mMcuController.getIgStatusFromMcu() == 1) {
                    SeatViewModel.this.controlTrdRowMidSeat();
                    return;
                }
                SeatViewModel seatViewModel3 = SeatViewModel.this;
                seatViewModel3.controlTrdRowMidSeatEnd(seatViewModel3.mTrdRowMidSeatData.getAdjustingType());
            } else if (this.mIsDriverSite) {
                if (this.mIsRearSeat) {
                    if (SeatViewModel.this.mMcuController.getIgStatusFromMcu() == 1) {
                        SeatViewModel.this.controlRearLeftSeat();
                        return;
                    }
                    SeatViewModel seatViewModel4 = SeatViewModel.this;
                    seatViewModel4.controlRearLeftSeatEnd(seatViewModel4.mRearLeftSeatData.getAdjustingType());
                } else if (SeatViewModel.this.mMcuController.getIgStatusFromMcu() == 1) {
                    SeatViewModel.this.controlDriverSeat();
                } else {
                    SeatViewModel seatViewModel5 = SeatViewModel.this;
                    seatViewModel5.controlDriverSeatEnd(seatViewModel5.mDriverSeatData.getAdjustingType());
                }
            } else if (this.mIsRearSeat) {
                if (SeatViewModel.this.mMcuController.getIgStatusFromMcu() == 1) {
                    SeatViewModel.this.controlRearRightSeat();
                    return;
                }
                SeatViewModel seatViewModel6 = SeatViewModel.this;
                seatViewModel6.controlRearRightSeatEnd(seatViewModel6.mRearRightSeatData.getAdjustingType());
            } else if (SeatViewModel.this.mMcuController.getIgStatusFromMcu() == 1) {
                SeatViewModel.this.controlPsnSeat();
            } else {
                SeatViewModel seatViewModel7 = SeatViewModel.this;
                seatViewModel7.controlPsnSeatEnd(seatViewModel7.mPsnSeatData.getAdjustingType());
            }
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            if (this.mIsTrdRowLeft) {
                SeatViewModel seatViewModel = SeatViewModel.this;
                seatViewModel.controlTrdRowLeftSeatEnd(seatViewModel.mTrdRowLeftSeatData.getAdjustingType());
            } else if (this.mIsTrdRowRight) {
                SeatViewModel seatViewModel2 = SeatViewModel.this;
                seatViewModel2.controlTrdRowRightSeatEnd(seatViewModel2.mTrdRowRightSeatData.getAdjustingType());
            } else if (this.mIsTrdRowMid) {
                SeatViewModel seatViewModel3 = SeatViewModel.this;
                seatViewModel3.controlTrdRowMidSeatEnd(seatViewModel3.mTrdRowMidSeatData.getAdjustingType());
            } else if (this.mIsDriverSite) {
                if (this.mIsRearSeat) {
                    SeatViewModel seatViewModel4 = SeatViewModel.this;
                    seatViewModel4.controlRearLeftSeatEnd(seatViewModel4.mRearLeftSeatData.getAdjustingType());
                    return;
                }
                SeatViewModel seatViewModel5 = SeatViewModel.this;
                seatViewModel5.controlDriverSeatEnd(seatViewModel5.mDriverSeatData.getAdjustingType());
            } else if (this.mIsRearSeat) {
                SeatViewModel seatViewModel6 = SeatViewModel.this;
                seatViewModel6.controlRearRightSeatEnd(seatViewModel6.mRearRightSeatData.getAdjustingType());
            } else {
                SeatViewModel seatViewModel7 = SeatViewModel.this;
                seatViewModel7.controlPsnSeatEnd(seatViewModel7.mPsnSeatData.getAdjustingType());
            }
        }
    }

    private void handleDropDownMenu() {
        if (!isSupportMsm()) {
            LogUtils.d(this.TAG, "handlerDropDownMenu NOT SUPPORT MSMD");
        } else if (!this.mIsDriverSeatManualMoving && !this.mIsSeatUiCtrlResume && !isWelcomeModeState()) {
            if (this.mSeatControlHandler == null) {
                this.mSeatControlHandler = new Handler(Looper.getMainLooper());
            }
            this.mSeatControlHandler.removeCallbacks(this.mDropDownRun);
            this.mSeatControlHandler.postDelayed(this.mDropDownRun, 1000L);
        } else {
            LogUtils.d(this.TAG, " handlerDropDownMenu mIsDriverSeatManualMoving=" + this.mIsDriverSeatManualMoving + " mIsSeatUiCtrlResume=" + this.mIsSeatUiCtrlResume);
            Handler handler = this.mSeatControlHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mDropDownRun);
            }
        }
    }

    private boolean isInViceMeditation() {
        int userScenarioStatus = this.mScenarioController.getUserScenarioStatus(IScenarioController.VICE_MEDITATION_MODE);
        return userScenarioStatus == 1 || userScenarioStatus == 2 || userScenarioStatus == 3;
    }

    private boolean isInCosmeticSpace() {
        int userScenarioStatus = this.mScenarioController.getUserScenarioStatus(IScenarioController.SCENARIO_COSMETIC_SPACE_MODE);
        return userScenarioStatus == 1 || userScenarioStatus == 2 || userScenarioStatus == 3;
    }

    public /* synthetic */ void lambda$new$8$SeatViewModel() {
        if (this.mMcuController.getIgStatusFromMcu() != 1) {
            LogUtils.w(this.TAG, "Local IG not on, do not show drop down menu to save/restore seat position");
        } else if (!this.mMsmController.isInSeatMenuShowTime(true)) {
            LogUtils.i(this.TAG, "Not InSeatMenuShowTime and Do not show Drv SeatDropDownMenu");
        } else if (!isDrvSeatEqualMemory() && !isWelcomeModeState() && !this.mIsSeatUiCtrlResume && seatSaveOutMode()) {
            if (!isDrvHeadrestNormal()) {
                NotificationHelper.getInstance().showToast(R.string.seat_save_headrest_notice, true, "seat_adjust");
            } else if (BaseFeatureOption.getInstance().isSupportPopPanel()) {
                if (PopPanelManager.getInstance().checkConflictWithNapa("seat_mirror_menu")) {
                    return;
                }
                PopPanelManager.getInstance().show("seat_mirror_menu", 1, ResUtils.getInt(R.integer.drv_region_id));
            } else {
                DropDownMenuManager.getInstance().show("seat_mirror_menu");
            }
        } else {
            LogUtils.d(this.TAG, " mDropDownRun drv seat equal memory or welcome mode or seat ui resume");
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void removeDropDownRun() {
        Handler handler = this.mSeatControlHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mDropDownRun);
        }
        if (BaseFeatureOption.getInstance().isSupportPopPanel()) {
            PopPanelManager.getInstance().dismiss("seat_mirror_menu");
        } else {
            DropDownMenuManager.getInstance().dismiss("seat_mirror_menu");
        }
    }

    private void handlePsnDropDownMenu() {
        if (!CarBaseConfig.getInstance().isSupportMsmP()) {
            LogUtils.d(this.TAG, "handlerPsnDropDownMenu NOT SUPPORT MSMP");
        } else if (!this.mIsPsnSeatManualMoving && !this.mIsSeatUiCtrlResume && !isPsnWelcomeModeState()) {
            if (this.mSeatControlHandler == null) {
                this.mSeatControlHandler = new Handler(Looper.getMainLooper());
            }
            this.mSeatControlHandler.removeCallbacks(this.mPsnDropDownRun);
            this.mSeatControlHandler.postDelayed(this.mPsnDropDownRun, 1000L);
        } else {
            LogUtils.d(this.TAG, " handlerPsnDropDownMenu mIsPsnSeatManualMoving=" + this.mIsPsnSeatManualMoving + " mIsSeatUiCtrlResume=" + this.mIsSeatUiCtrlResume);
            Handler handler = this.mSeatControlHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mPsnDropDownRun);
            }
        }
    }

    public /* synthetic */ void lambda$new$9$SeatViewModel() {
        if (this.mMcuController.getIgStatusFromMcu() != 1) {
            LogUtils.w(this.TAG, "Local IG not on, do not show drop down menu to save/restore seat position");
        } else if (!this.mMsmController.isInSeatMenuShowTime(false)) {
            LogUtils.i(this.TAG, "Not In SeatMenuShowTime and Do not show Psn SeatDropDownMenu");
        } else if (!isPsnSeatEqualMemory() && !isPsnWelcomeModeState() && !this.mIsSeatUiCtrlResume && seatSaveOutMode() && !isInCosmeticSpace() && !isInViceMeditation() && !isPsnWelcomeModeState()) {
            LogUtils.w(this.TAG, "Show Psn seat drop down");
            if (!isPsnHeadrestNormal()) {
                NotificationHelper.getInstance().showToast(R.string.seat_save_headrest_notice, true, "seat_adjust");
            } else if (PopPanelManager.getInstance().checkConflictWithNapa("psn_seat_menu")) {
            } else {
                if (BaseFeatureOption.getInstance().isSupportDualScreen()) {
                    PopPanelManager.getInstance().show("psn_seat_menu", 1, 4);
                } else {
                    PopPanelManager.getInstance().show("psn_seat_menu", 1, ResUtils.getInt(R.integer.psn_region_id));
                }
            }
        } else {
            LogUtils.d(this.TAG, " mPsnDropDownRun psn seat equal memory or welcome mode or seat ui resume");
        }
    }

    private void handleRLDropDownMenu() {
        if (!CarBaseConfig.getInstance().isSupportRearSeatCtrl()) {
            LogUtils.d(this.TAG, "handleRLDropDownMenu NOT SUPPORT RearSeatCtrl");
        } else if (!this.mIsRearLeftSeatManualMoving && !this.mIsSeatUiCtrlResume && seatSaveOutMode() && !isInCosmeticSpace() && !isInViceMeditation()) {
            if (this.mSeatControlHandler == null) {
                this.mSeatControlHandler = new Handler(Looper.getMainLooper());
            }
            this.mSeatControlHandler.removeCallbacks(this.mRLDropDownRun);
            this.mSeatControlHandler.postDelayed(this.mRLDropDownRun, 1000L);
        } else {
            LogUtils.d(this.TAG, " handleRLDropDownMenu mIsRearLeftSeatManualMoving=" + this.mIsPsnSeatManualMoving + " mIsSeatUiCtrlResume=" + this.mIsSeatUiCtrlResume);
            Handler handler = this.mSeatControlHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mRLDropDownRun);
            }
        }
    }

    public /* synthetic */ void lambda$new$10$SeatViewModel() {
        if (this.mMcuController.getIgStatusFromMcu() != 1) {
            LogUtils.w(this.TAG, "Local IG not on, do not show drop down menu to save/restore seat position");
        } else if (!isRLSeatEqualMemory() && !isPsnWelcomeModeState() && !AmsUtils.isTopActivityFullscreen() && !this.mIsSeatUiCtrlResume) {
            LogUtils.w(this.TAG, "Show RL seat drop down");
        } else {
            LogUtils.d(this.TAG, " mRLDropDownRun  seat equal memory or welcome mode or seat ui resume");
        }
    }

    public /* synthetic */ void lambda$new$11$SeatViewModel() {
        if (this.mMcuController.getIgStatusFromMcu() != 1) {
            LogUtils.w(this.TAG, "Local IG not on, do not show drop down menu to save/restore seat position");
        } else if (!isRRSeatEqualMemory() && !isPsnWelcomeModeState() && !AmsUtils.isTopActivityFullscreen() && !this.mIsSeatUiCtrlResume) {
            LogUtils.w(this.TAG, "Show RR seat drop down");
        } else {
            LogUtils.d(this.TAG, " mRRDropDownRun  seat equal memory or welcome mode or seat ui resume");
        }
    }

    public boolean isWelcomeModeState() {
        long currentTimeMillis = System.currentTimeMillis() - this.mDrvDoorClosedTime;
        boolean z = isWelcomeModeEnabled() && getStallState() == 4 && isDrvDoorOpened() && currentTimeMillis > 1000;
        LogUtils.i(this.TAG, "isWelcomeModeState:" + z + ", doorCloseTime:" + currentTimeMillis);
        return z;
    }

    public boolean isPsnWelcomeModeState() {
        long currentTimeMillis = System.currentTimeMillis() - this.mPsnDoorClosedTime;
        boolean z = isPsnWelcomeEnabled() && getStallState() == 4 && isPsnDoorOpened() && currentTimeMillis > 1000;
        LogUtils.i(this.TAG, "isPsnWelcomeModeState:" + z + ", psnDoorCloseTime:" + currentTimeMillis);
        return z;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setDrvSeatAllPositions(int horizonPos, int verticalPos, int tiltPos, int legPos, int cushionPos) {
        LogUtils.d(this.TAG, "setDrvSeatAllPositions [" + horizonPos + "," + verticalPos + "," + tiltPos + "," + legPos + "," + cushionPos + "]");
        this.mMsmController.setDriverAllPositions(horizonPos, verticalPos, tiltPos, legPos, cushionPos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void restoreDrvSeatAllPositions(int[] pos) {
        if (pos == null) {
            return;
        }
        int min = Math.min(50, pos[2]);
        if (CarBaseConfig.getInstance().isSupportVipSeat() && getDSeatTiltPos() < 50 && Math.abs(min - getDSeatTiltPos()) >= 2) {
            this.mIsDrvRestoreSafeChairBack = true;
            this.mDriverSeatSavedPos = pos;
            setDSeatTiltPos(min);
            return;
        }
        this.mMsmController.setDriverAllPositions(pos[0], pos[1], pos[2], pos[3], pos[4]);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPsnSeatAllPositions(int horizonPos, int verticalPos, int tiltPos, int legPos) {
        LogUtils.d(this.TAG, " setPsnSeatAllPositions [" + horizonPos + "," + verticalPos + "," + tiltPos + "," + legPos + "]");
        this.mMsmController.setPsnAllPositions(horizonPos, verticalPos, tiltPos, legPos);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLSeatAllPositions(int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor) {
        LogUtils.d(this.TAG, " setRLSeatAllPositions [" + horizonPos + "," + anglePos + "," + tiltPos + "," + cushionPos + "," + legPos + "," + headVer + "," + headHor + "]");
        this.mMsmController.setRLAllPositions(horizonPos, anglePos, tiltPos, cushionPos, legPos, headVer, headHor);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRSeatAllPositions(int horizonPos, int anglePos, int tiltPos, int cushionPos, int legPos, int headVer, int headHor) {
        LogUtils.d(this.TAG, " setRRSeatAllPositions [" + horizonPos + "," + anglePos + "," + tiltPos + "," + cushionPos + "," + legPos + "," + headVer + "," + headHor + "]");
        this.mMsmController.setRRAllPositions(horizonPos, anglePos, tiltPos, cushionPos, legPos, headVer, headHor);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPsnAllPositionsToDomain() {
        int[] psnSeatPos;
        if (!isPsnWelcomeEnabled() || (psnSeatPos = this.mMsmController.getPsnSeatPos()) == null || psnSeatPos.length < 4) {
            return;
        }
        LogUtils.d(this.TAG, "setPsnSeatAllPositionsToMcu saved seat position hor:" + psnSeatPos[0] + ",ver:" + psnSeatPos[1] + ",tilt:" + psnSeatPos[2] + ",leg:" + psnSeatPos[3]);
        this.mMsmController.setPsnAllPositions(1, psnSeatPos[0], psnSeatPos[1], psnSeatPos[2], psnSeatPos[3]);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRLAllPositionsToDomain() {
        int[] rLSeatPos = this.mMsmController.getRLSeatPos();
        if (rLSeatPos == null || rLSeatPos.length < 4) {
            return;
        }
        LogUtils.d(this.TAG, "setRLAllPositionsToDomain mRLSeatSavedPos:" + rLSeatPos[0] + ",angle:" + rLSeatPos[1] + ",tilt:" + rLSeatPos[2] + ",cushion:" + rLSeatPos[3] + ",leg:" + rLSeatPos[4] + ",headVer:" + rLSeatPos[5] + ",headHor:" + rLSeatPos[6]);
        this.mMsmController.setRLAllPositions(1, rLSeatPos[0], rLSeatPos[1], rLSeatPos[2], rLSeatPos[3], rLSeatPos[4], rLSeatPos[5], rLSeatPos[6]);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRRAllPositionsToDomain() {
        int[] rRSeatPos = this.mMsmController.getRRSeatPos();
        if (rRSeatPos == null || rRSeatPos.length < 4) {
            return;
        }
        LogUtils.d(this.TAG, "setRRAllPositionsToDomain mRRSeatSavedPos:" + rRSeatPos[0] + ",angle:" + rRSeatPos[1] + ",tilt:" + rRSeatPos[2] + ",cushion:" + rRSeatPos[3] + ",leg:" + rRSeatPos[4] + ",headVer:" + rRSeatPos[5] + ",headHor:" + rRSeatPos[6]);
        this.mMsmController.setRRAllPositions(1, rRSeatPos[0], rRSeatPos[1], rRSeatPos[2], rRSeatPos[3], rRSeatPos[4], rRSeatPos[5], rRSeatPos[6]);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setPsnSeatAllPositionsToMcu() {
        int[] psnSeatPos;
        if (!isPsnWelcomeEnabled() || (psnSeatPos = this.mMsmController.getPsnSeatPos()) == null || psnSeatPos.length < 4) {
            return;
        }
        LogUtils.d(this.TAG, "setPsnSeatAllPositionsToMcu saved seat position hor:" + psnSeatPos[0] + ",ver:" + psnSeatPos[1] + ",tilt:" + psnSeatPos[2] + ",leg:" + psnSeatPos[3]);
        this.mMsmController.setPsnAllPositionsToMcu(psnSeatPos[0], psnSeatPos[1], psnSeatPos[2], psnSeatPos[3]);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void moveToExhibitionPos() {
        setDrvSeatAllPositions(34, 50, 79, 0, 0);
        setPsnSeatAllPositions(34, 50, 79, 0);
        this.mMsmController.setRLSeatTiltPos(100);
        this.mMsmController.setRLSeatLegPos(0);
        this.mMsmController.setRRSeatTiltPos(100);
        this.mMsmController.setRRSeatLegPos(0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void stopSeatControl() {
        Seat seat = this.mDriverSeatData;
        if (seat != null) {
            controlDriverSeatEnd(seat.getAdjustingType());
        }
        Seat seat2 = this.mPsnSeatData;
        if (seat2 != null) {
            controlPsnSeatEnd(seat2.getAdjustingType());
        }
        if (CarBaseConfig.getInstance().isSupportRearSeatCtrl()) {
            Seat seat3 = this.mRearLeftSeatData;
            if (seat3 != null) {
                controlRearLeftSeatEnd(seat3.getAdjustingType());
            }
            Seat seat4 = this.mRearRightSeatData;
            if (seat4 != null) {
                controlRearRightSeatEnd(seat4.getAdjustingType());
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void exitMeditationState() {
        int[] drvSeatPos = this.mMsmController.getDrvSeatPos(this.mMsmController.getDrvSeatPosIdx());
        if (isDrvDoorOpened() && this.mMcuController.getSeatWelcomeMode()) {
            if (checkChairMovable(true, true)) {
                int calculateWelcomeModeGoOffPosition = calculateWelcomeModeGoOffPosition();
                if (drvSeatPos == null) {
                    LogUtils.i(this.TAG, "Saved seat position is null, move seat back and recover tilt position to default", false);
                    this.mMsmController.setDriverAllPositions(calculateWelcomeModeGoOffPosition, this.mMsmController.getDSeatVerPos(), CarBaseConfig.getInstance().getMSMSeatDefaultPos()[2], this.mMsmController.getDSeatLegPos(), this.mMsmController.getDSeatCushionPos());
                    return;
                }
                this.mMsmController.setDriverAllPositions(calculateWelcomeModeGoOffPosition, drvSeatPos[1], drvSeatPos[2], drvSeatPos[3], drvSeatPos[4]);
            }
        } else if (checkChairMovable(true, true)) {
            if (drvSeatPos == null) {
                LogUtils.i(this.TAG, "Saved seat position is null, recover tilt position to default", false);
                this.mMsmController.setDSeatTiltPos(CarBaseConfig.getInstance().getMSMSeatDefaultPos()[2]);
                return;
            }
            this.mMsmController.setDriverAllPositions(drvSeatPos[0], drvSeatPos[1], drvSeatPos[2], drvSeatPos[3], drvSeatPos[4]);
        }
    }

    private int calculateWelcomeModeGoOffPosition() {
        int dSeatHorzPos = this.mMsmController.getDSeatHorzPos();
        if (dSeatHorzPos > 25) {
            int i = dSeatHorzPos > 45 ? dSeatHorzPos - 20 : 25;
            LogUtils.i(this.TAG, "checkEnterWelcomeModeGoOff start new SeatHorzPos: " + i + ", curSeatHorzPos: " + dSeatHorzPos);
            return i;
        }
        LogUtils.i(this.TAG, "checkEnterWelcomeModeGoOff curSeatHorzPos: " + dSeatHorzPos + ", do not need adjust seat");
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMcuController.Callback
    public void onIgStatusChanged(int state) {
        this.mMcuIgState.postValue(Integer.valueOf(state));
    }

    public MutableLiveData<Integer> getMcuIgState() {
        return this.mMcuIgState;
    }

    public boolean controlRearLeftSeatStart(int controlType, int direction) {
        return controlRearLeftSeatStart(controlType, direction, 1);
    }

    public boolean controlRearLeftSeatStart(int controlType, int direction, int controlLocus) {
        int adjustingType = this.mRearLeftSeatData.getAdjustingType();
        boolean z = controlLocus == 3;
        if (adjustingType == 0) {
            this.mRearLeftSeatData.setAdjustingParam(controlType, direction);
        } else {
            boolean z2 = this.mIsRearLeftSeatManualMoving;
            if (z2 && (adjustingType != controlType || z)) {
                LogUtils.w(this.TAG, "controlRearLeftSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            } else if (z2) {
                LogUtils.w(this.TAG, "controlRearLeftSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            }
        }
        controlRearLeftSeatStart(z);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x004d, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0053, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0059, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0065, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006b, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0071, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0073, code lost:
        r3 = 40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0047, code lost:
        if (r9 != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void controlRearLeftSeatStart(boolean r9) {
        /*
            r8 = this;
            r0 = 1
            r8.mIsRearLeftSeatManualMoving = r0
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel$ManualAdjustSeatTimer r1 = r8.mRearSeatLeftAdjustTimer
            if (r1 == 0) goto Ld
            r1.cancel()
            r1 = 0
            r8.mRearSeatLeftAdjustTimer = r1
        Ld:
            com.xiaopeng.carcontrol.bean.Seat r1 = r8.mRearLeftSeatData
            int r1 = r1.getAdjustingType()
            com.xiaopeng.carcontrol.bean.Seat r2 = r8.mRearLeftSeatData
            int r2 = r2.getAdjustingDirection()
            r3 = 1000(0x3e8, double:4.94E-321)
            java.lang.String r5 = r8.TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "start controlType "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = ",direction "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r6 = r6.toString()
            r7 = 0
            com.xiaopeng.carcontrol.util.LogUtils.d(r5, r6, r7)
            r5 = 40
            switch(r1) {
                case 1: goto L6e;
                case 2: goto L43;
                case 3: goto L68;
                case 4: goto L62;
                case 5: goto L5c;
                case 6: goto L56;
                case 7: goto L50;
                case 8: goto L4a;
                case 9: goto L44;
                default: goto L43;
            }
        L43:
            goto L74
        L44:
            r8.setRLSeatHeadRestVerMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L4a:
            r8.setRLSeatHeadRestHorzMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L50:
            r8.setRLSeatLegVerMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L56:
            r8.setRLSeatLumbVerMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L5c:
            r8.setRLSeatLumbHorzMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L62:
            r8.setRLSeatLegMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L68:
            r8.setRLSeatTiltMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L6e:
            r8.setRLSeaHorzMove(r0, r2)
            if (r9 == 0) goto L74
        L73:
            r3 = r5
        L74:
            com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$vL7laJ7lYuHpH0XmyDu7JflaWGA r9 = new com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$vL7laJ7lYuHpH0XmyDu7JflaWGA
            r9.<init>()
            com.xiaopeng.carcontrol.util.ThreadUtils.runOnMainThread(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel.controlRearLeftSeatStart(boolean):void");
    }

    public /* synthetic */ void lambda$controlRearLeftSeatStart$12$SeatViewModel(final long finalAdjustCount) {
        ManualAdjustSeatTimer manualAdjustSeatTimer = new ManualAdjustSeatTimer(true, true, finalAdjustCount);
        this.mRearSeatLeftAdjustTimer = manualAdjustSeatTimer;
        manualAdjustSeatTimer.start();
    }

    public void controlRearLeftSeatEnd(int type) {
        int adjustingType = this.mRearLeftSeatData.getAdjustingType();
        LogUtils.d(this.TAG, "controlRearLeftSeatEnd:" + type + ",adjustType:" + adjustingType + ",mIsRearLeftSeatManualMoving:" + this.mIsRearLeftSeatManualMoving, false);
        if (this.mIsRearLeftSeatManualMoving && adjustingType == type) {
            this.mIsRearLeftSeatManualMoving = false;
            this.mRearLeftSeatData.setAdjustingType(0);
            ManualAdjustSeatTimer manualAdjustSeatTimer = this.mRearSeatLeftAdjustTimer;
            if (manualAdjustSeatTimer != null) {
                manualAdjustSeatTimer.cancel();
                this.mRearSeatLeftAdjustTimer = null;
            }
            int adjustingDirection = this.mRearLeftSeatData.getAdjustingDirection();
            LogUtils.d(this.TAG, "end controlType " + adjustingType + ",direction " + adjustingDirection, false);
            switch (adjustingType) {
                case 1:
                    setRLSeaHorzMove(3, adjustingDirection);
                    return;
                case 2:
                default:
                    return;
                case 3:
                    setRLSeatTiltMove(3, adjustingDirection);
                    return;
                case 4:
                    setRLSeatLegMove(3, adjustingDirection);
                    return;
                case 5:
                    setRLSeatLumbHorzMove(3, adjustingDirection);
                    return;
                case 6:
                    setRLSeatLumbVerMove(3, adjustingDirection);
                    return;
                case 7:
                    setRLSeatLegVerMove(3, adjustingDirection);
                    return;
                case 8:
                    setRLSeatHeadRestHorzMove(3, adjustingDirection);
                    return;
                case 9:
                    setRLSeatHeadRestVerMove(3, adjustingDirection);
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlRearLeftSeat() {
        int adjustingType = this.mRearLeftSeatData.getAdjustingType();
        int adjustingDirection = this.mRearLeftSeatData.getAdjustingDirection();
        LogUtils.d(this.TAG, "controlType " + adjustingType + ",direction " + adjustingDirection, false);
        switch (adjustingType) {
            case 1:
                setRLSeaHorzMove(2, adjustingDirection);
                return;
            case 2:
            default:
                return;
            case 3:
                if (isCanSetSeatPosForTilt(adjustingDirection, adjustingType)) {
                    setRLSeatTiltMove(2, adjustingDirection);
                    return;
                } else {
                    controlRearLeftSeatEnd(adjustingType);
                    return;
                }
            case 4:
                setRLSeatLegMove(2, adjustingDirection);
                return;
            case 5:
                setRLSeatLumbHorzMove(2, adjustingDirection);
                return;
            case 6:
                setRLSeatLumbVerMove(2, adjustingDirection);
                return;
            case 7:
                setRLSeatLegVerMove(2, adjustingDirection);
                return;
            case 8:
                setRLSeatHeadRestHorzMove(2, adjustingDirection);
                return;
            case 9:
                setRLSeatHeadRestVerMove(2, adjustingDirection);
                return;
        }
    }

    public boolean controlRearRightSeatStart(int controlType, int direction) {
        return controlRearRightSeatStart(controlType, direction, 1);
    }

    public boolean controlRearRightSeatStart(int controlType, int direction, int controlLocus) {
        int adjustingType = this.mRearRightSeatData.getAdjustingType();
        boolean z = controlLocus == 3;
        if (adjustingType == 0) {
            this.mRearRightSeatData.setAdjustingParam(controlType, direction);
        } else {
            boolean z2 = this.mIsRearRightSeatManualMoving;
            if (z2 && (adjustingType != controlType || z)) {
                LogUtils.w(this.TAG, "controlRearRightSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            } else if (z2) {
                LogUtils.w(this.TAG, "controlRearRightSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            }
        }
        controlRearRightSeatStart(z);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x004d, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0053, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0059, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0065, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006b, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0071, code lost:
        if (r9 != false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0073, code lost:
        r3 = 40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0047, code lost:
        if (r9 != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void controlRearRightSeatStart(boolean r9) {
        /*
            r8 = this;
            r0 = 1
            r8.mIsRearRightSeatManualMoving = r0
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel$ManualAdjustSeatTimer r1 = r8.mRearSeatRightAdjustTimer
            if (r1 == 0) goto Ld
            r1.cancel()
            r1 = 0
            r8.mRearSeatRightAdjustTimer = r1
        Ld:
            com.xiaopeng.carcontrol.bean.Seat r1 = r8.mRearRightSeatData
            int r1 = r1.getAdjustingType()
            com.xiaopeng.carcontrol.bean.Seat r2 = r8.mRearRightSeatData
            int r2 = r2.getAdjustingDirection()
            r3 = 1000(0x3e8, double:4.94E-321)
            java.lang.String r5 = r8.TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "start controlType "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = ",direction "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r6 = r6.toString()
            r7 = 0
            com.xiaopeng.carcontrol.util.LogUtils.d(r5, r6, r7)
            r5 = 40
            switch(r1) {
                case 1: goto L6e;
                case 2: goto L43;
                case 3: goto L68;
                case 4: goto L62;
                case 5: goto L5c;
                case 6: goto L56;
                case 7: goto L50;
                case 8: goto L4a;
                case 9: goto L44;
                default: goto L43;
            }
        L43:
            goto L74
        L44:
            r8.setRRSeatHeadRestVerMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L4a:
            r8.setRRSeatHeadRestHorzMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L50:
            r8.setRRSeatLegVerMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L56:
            r8.setRRSeatLumbVerMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L5c:
            r8.setRRSeatLumbHorzMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L62:
            r8.setRRSeatLegMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L68:
            r8.setRRSeatTiltMove(r0, r2)
            if (r9 == 0) goto L74
            goto L73
        L6e:
            r8.setRRSeaHorzMove(r0, r2)
            if (r9 == 0) goto L74
        L73:
            r3 = r5
        L74:
            com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$vs0isZuUONoBjGHZwxTs5z6-ezU r9 = new com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$vs0isZuUONoBjGHZwxTs5z6-ezU
            r9.<init>()
            com.xiaopeng.carcontrol.util.ThreadUtils.runOnMainThread(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel.controlRearRightSeatStart(boolean):void");
    }

    public /* synthetic */ void lambda$controlRearRightSeatStart$13$SeatViewModel(final long finalAdjustCount) {
        ManualAdjustSeatTimer manualAdjustSeatTimer = new ManualAdjustSeatTimer(false, true, finalAdjustCount);
        this.mRearSeatRightAdjustTimer = manualAdjustSeatTimer;
        manualAdjustSeatTimer.start();
    }

    public void controlRearRightSeatEnd(int type) {
        int adjustingType = this.mRearRightSeatData.getAdjustingType();
        LogUtils.d(this.TAG, "controlRearRightSeatEnd:" + type + ",adjustType:" + adjustingType + ",mIsRearRightSeatManualMoving:" + this.mIsRearRightSeatManualMoving, false);
        if (this.mIsRearRightSeatManualMoving && adjustingType == type) {
            this.mIsRearRightSeatManualMoving = false;
            this.mRearRightSeatData.setAdjustingType(0);
            ManualAdjustSeatTimer manualAdjustSeatTimer = this.mRearSeatRightAdjustTimer;
            if (manualAdjustSeatTimer != null) {
                manualAdjustSeatTimer.cancel();
                this.mRearSeatRightAdjustTimer = null;
            }
            int adjustingDirection = this.mRearRightSeatData.getAdjustingDirection();
            LogUtils.d(this.TAG, "end controlType " + adjustingType + ",direction " + adjustingDirection, false);
            switch (adjustingType) {
                case 1:
                    setRRSeaHorzMove(3, adjustingDirection);
                    return;
                case 2:
                default:
                    return;
                case 3:
                    setRRSeatTiltMove(3, adjustingDirection);
                    return;
                case 4:
                    setRRSeatLegMove(3, adjustingDirection);
                    return;
                case 5:
                    setRRSeatLumbHorzMove(3, adjustingDirection);
                    return;
                case 6:
                    setRRSeatLumbVerMove(3, adjustingDirection);
                    return;
                case 7:
                    setRRSeatLegVerMove(3, adjustingDirection);
                    return;
                case 8:
                    setRRSeatHeadRestHorzMove(3, adjustingDirection);
                    return;
                case 9:
                    setRRSeatHeadRestVerMove(3, adjustingDirection);
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlRearRightSeat() {
        int adjustingType = this.mRearRightSeatData.getAdjustingType();
        int adjustingDirection = this.mRearRightSeatData.getAdjustingDirection();
        LogUtils.d(this.TAG, "controlType " + adjustingType + ",direction " + adjustingDirection, false);
        switch (adjustingType) {
            case 1:
                setRRSeaHorzMove(2, adjustingDirection);
                return;
            case 2:
            default:
                return;
            case 3:
                if (isCanSetSeatPosForTilt(adjustingDirection, adjustingType)) {
                    setRRSeatTiltMove(2, adjustingDirection);
                    return;
                } else {
                    controlRearRightSeatEnd(adjustingType);
                    return;
                }
            case 4:
                setRRSeatLegMove(2, adjustingDirection);
                return;
            case 5:
                setRRSeatLumbHorzMove(2, adjustingDirection);
                return;
            case 6:
                setRRSeatLumbVerMove(2, adjustingDirection);
                return;
            case 7:
                setRRSeatLegVerMove(2, adjustingDirection);
                return;
            case 8:
                setRRSeatHeadRestHorzMove(2, adjustingDirection);
                return;
            case 9:
                setRRSeatHeadRestVerMove(2, adjustingDirection);
                return;
        }
    }

    public boolean controlTrdRowLeftSeatStart(int controlType, int direction) {
        return controlTrdRowLeftSeatStart(controlType, direction, 1);
    }

    public boolean controlTrdRowLeftSeatStart(int controlType, int direction, int controlLocus) {
        int adjustingType = this.mTrdRowLeftSeatData.getAdjustingType();
        boolean z = controlLocus == 3;
        if (adjustingType == 0) {
            this.mTrdRowLeftSeatData.setAdjustingParam(controlType, direction);
        } else {
            boolean z2 = this.mIsTrdRowLeftSeatManualMoving;
            if (z2 && (adjustingType != controlType || z)) {
                LogUtils.w(this.TAG, "controlTrdRowLeftSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            } else if (z2) {
                LogUtils.w(this.TAG, "controlTrdRowLeftSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            }
        }
        controlTrdRowLeftSeatStart(z);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x004b, code lost:
        if (r9 != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0051, code lost:
        if (r9 != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0053, code lost:
        r3 = 40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void controlTrdRowLeftSeatStart(boolean r9) {
        /*
            r8 = this;
            r0 = 1
            r8.mIsTrdRowLeftSeatManualMoving = r0
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel$ManualAdjustSeatTimer r1 = r8.mTrdRowLeftAdjustTimer
            if (r1 == 0) goto Ld
            r1.cancel()
            r1 = 0
            r8.mTrdRowLeftAdjustTimer = r1
        Ld:
            com.xiaopeng.carcontrol.bean.Seat r1 = r8.mTrdRowLeftSeatData
            int r1 = r1.getAdjustingType()
            com.xiaopeng.carcontrol.bean.Seat r2 = r8.mTrdRowLeftSeatData
            int r2 = r2.getAdjustingDirection()
            r3 = 1000(0x3e8, double:4.94E-321)
            java.lang.String r5 = r8.TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "start controlType "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = ",direction "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r6 = r6.toString()
            r7 = 0
            com.xiaopeng.carcontrol.util.LogUtils.d(r5, r6, r7)
            r5 = 3
            r6 = 40
            if (r1 == r5) goto L4e
            r5 = 8
            if (r1 == r5) goto L48
            goto L54
        L48:
            r8.setTrdRowLeftSeatHeadVerMove(r0, r2)
            if (r9 == 0) goto L54
            goto L53
        L4e:
            r8.setTrdRowLeftSeatTiltMove(r0, r2)
            if (r9 == 0) goto L54
        L53:
            r3 = r6
        L54:
            com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$0OirvmFryYyOdsoyNGPVYEdnMfA r9 = new com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$0OirvmFryYyOdsoyNGPVYEdnMfA
            r9.<init>()
            com.xiaopeng.carcontrol.util.ThreadUtils.runOnMainThread(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel.controlTrdRowLeftSeatStart(boolean):void");
    }

    public /* synthetic */ void lambda$controlTrdRowLeftSeatStart$14$SeatViewModel(final long finalAdjustCount) {
        ManualAdjustSeatTimer manualAdjustSeatTimer = new ManualAdjustSeatTimer(false, false, true, false, false, finalAdjustCount);
        this.mTrdRowLeftAdjustTimer = manualAdjustSeatTimer;
        manualAdjustSeatTimer.start();
    }

    public void controlTrdRowLeftSeatEnd(int type) {
        int adjustingType = this.mTrdRowLeftSeatData.getAdjustingType();
        LogUtils.d(this.TAG, "controlTrdRowLeftSeatEnd:" + type + ",adjustType:" + adjustingType + ",mIsTrdRowLeftSeatManualMoving:" + this.mIsTrdRowLeftSeatManualMoving, false);
        if (this.mIsTrdRowLeftSeatManualMoving && adjustingType == type) {
            this.mIsTrdRowLeftSeatManualMoving = false;
            this.mTrdRowLeftSeatData.setAdjustingType(0);
            ManualAdjustSeatTimer manualAdjustSeatTimer = this.mTrdRowLeftAdjustTimer;
            if (manualAdjustSeatTimer != null) {
                manualAdjustSeatTimer.cancel();
                this.mTrdRowLeftAdjustTimer = null;
            }
            int adjustingDirection = this.mTrdRowLeftSeatData.getAdjustingDirection();
            LogUtils.d(this.TAG, "end controlType " + adjustingType + ",direction " + adjustingDirection, false);
            if (adjustingType == 3) {
                setTrdRowLeftSeatTiltMove(3, adjustingDirection);
            } else if (adjustingType != 8) {
            } else {
                setTrdRowLeftSeatHeadVerMove(3, adjustingDirection);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlTrdRowLeftSeat() {
        int adjustingType = this.mTrdRowLeftSeatData.getAdjustingType();
        int adjustingDirection = this.mTrdRowLeftSeatData.getAdjustingDirection();
        LogUtils.d(this.TAG, "controlType " + adjustingType + ",direction " + adjustingDirection, false);
        if (adjustingType == 3) {
            setTrdRowLeftSeatTiltMove(2, adjustingDirection);
        } else if (adjustingType != 8) {
        } else {
            setTrdRowLeftSeatHeadVerMove(2, adjustingDirection);
        }
    }

    public boolean controlTrdRowRightSeatStart(int controlType, int direction) {
        return controlTrdRowRightSeatStart(controlType, direction, 1);
    }

    public boolean controlTrdRowRightSeatStart(int controlType, int direction, int controlLocus) {
        int adjustingType = this.mTrdRowRightSeatData.getAdjustingType();
        boolean z = controlLocus == 3;
        if (adjustingType == 0) {
            this.mTrdRowRightSeatData.setAdjustingParam(controlType, direction);
        } else {
            boolean z2 = this.mIsTrdRowRightSeatManualMoving;
            if (z2 && (adjustingType != controlType || z)) {
                LogUtils.w(this.TAG, "controlTrdRowRightSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            } else if (z2) {
                LogUtils.w(this.TAG, "controlTrdRowRightSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            }
        }
        controlTrdRowRightSeatStart(z);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x004b, code lost:
        if (r9 != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0051, code lost:
        if (r9 != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0053, code lost:
        r3 = 40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void controlTrdRowRightSeatStart(boolean r9) {
        /*
            r8 = this;
            r0 = 1
            r8.mIsTrdRowRightSeatManualMoving = r0
            com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel$ManualAdjustSeatTimer r1 = r8.mTrdRowRightAdjustTimer
            if (r1 == 0) goto Ld
            r1.cancel()
            r1 = 0
            r8.mTrdRowRightAdjustTimer = r1
        Ld:
            com.xiaopeng.carcontrol.bean.Seat r1 = r8.mTrdRowRightSeatData
            int r1 = r1.getAdjustingType()
            com.xiaopeng.carcontrol.bean.Seat r2 = r8.mTrdRowRightSeatData
            int r2 = r2.getAdjustingDirection()
            r3 = 1000(0x3e8, double:4.94E-321)
            java.lang.String r5 = r8.TAG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "start controlType "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = ",direction "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r6 = r6.toString()
            r7 = 0
            com.xiaopeng.carcontrol.util.LogUtils.d(r5, r6, r7)
            r5 = 3
            r6 = 40
            if (r1 == r5) goto L4e
            r5 = 8
            if (r1 == r5) goto L48
            goto L54
        L48:
            r8.setTrdRowRightSeatHeadVerMove(r0, r2)
            if (r9 == 0) goto L54
            goto L53
        L4e:
            r8.setTrdRowRightSeatTiltMove(r0, r2)
            if (r9 == 0) goto L54
        L53:
            r3 = r6
        L54:
            com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$mgasm1nzQ2lAjJKFNCj8guRlWM0 r9 = new com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$mgasm1nzQ2lAjJKFNCj8guRlWM0
            r9.<init>()
            com.xiaopeng.carcontrol.util.ThreadUtils.runOnMainThread(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel.controlTrdRowRightSeatStart(boolean):void");
    }

    public /* synthetic */ void lambda$controlTrdRowRightSeatStart$15$SeatViewModel(final long finalAdjustCount) {
        ManualAdjustSeatTimer manualAdjustSeatTimer = new ManualAdjustSeatTimer(false, false, false, true, false, finalAdjustCount);
        this.mTrdRowRightAdjustTimer = manualAdjustSeatTimer;
        manualAdjustSeatTimer.start();
    }

    public void controlTrdRowRightSeatEnd(int type) {
        int adjustingType = this.mTrdRowRightSeatData.getAdjustingType();
        LogUtils.d(this.TAG, "controlTrdRowRightSeatEnd:" + type + ",adjustType:" + adjustingType + ",mIsTrdRowRightSeatManualMoving:" + this.mIsTrdRowRightSeatManualMoving, false);
        if (this.mIsTrdRowRightSeatManualMoving && adjustingType == type) {
            this.mIsTrdRowRightSeatManualMoving = false;
            this.mTrdRowRightSeatData.setAdjustingType(0);
            ManualAdjustSeatTimer manualAdjustSeatTimer = this.mTrdRowRightAdjustTimer;
            if (manualAdjustSeatTimer != null) {
                manualAdjustSeatTimer.cancel();
                this.mTrdRowRightAdjustTimer = null;
            }
            int adjustingDirection = this.mTrdRowRightSeatData.getAdjustingDirection();
            LogUtils.d(this.TAG, "end controlType " + adjustingType + ",direction " + adjustingDirection, false);
            if (adjustingType == 3) {
                setTrdRowRightSeatTiltMove(3, adjustingDirection);
            } else if (adjustingType != 8) {
            } else {
                setTrdRowRightSeatHeadVerMove(3, adjustingDirection);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlTrdRowRightSeat() {
        int adjustingType = this.mTrdRowRightSeatData.getAdjustingType();
        int adjustingDirection = this.mTrdRowRightSeatData.getAdjustingDirection();
        LogUtils.d(this.TAG, "controlType " + adjustingType + ",direction " + adjustingDirection, false);
        if (adjustingType == 3) {
            setTrdRowRightSeatTiltMove(2, adjustingDirection);
        } else if (adjustingType != 8) {
        } else {
            setTrdRowRightSeatHeadVerMove(2, adjustingDirection);
        }
    }

    public boolean controlTrdRowMidSeatStart(int controlType, int direction) {
        return controlTrdRowMidSeatStart(controlType, direction, 1);
    }

    public boolean controlTrdRowMidSeatStart(int controlType, int direction, int controlLocus) {
        int adjustingType = this.mTrdRowMidSeatData.getAdjustingType();
        boolean z = controlLocus == 3;
        if (adjustingType == 0) {
            this.mTrdRowMidSeatData.setAdjustingParam(controlType, direction);
        } else {
            boolean z2 = this.mIsTrdRowMidSeatManualMoving;
            if (z2 && (adjustingType != controlType || z)) {
                LogUtils.w(this.TAG, "controlTrdRowMidSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            } else if (z2) {
                LogUtils.w(this.TAG, "controlTrdRowMidSeatStart failed, adjust " + adjustingType + " not finished", false);
                return false;
            }
        }
        controlTrdRowMidSeatStart(z);
        return true;
    }

    private void controlTrdRowMidSeatStart(boolean isSpeech) {
        this.mIsTrdRowMidSeatManualMoving = true;
        ManualAdjustSeatTimer manualAdjustSeatTimer = this.mTrdRowMidAdjustTimer;
        if (manualAdjustSeatTimer != null) {
            manualAdjustSeatTimer.cancel();
            this.mTrdRowMidAdjustTimer = null;
        }
        int adjustingType = this.mTrdRowMidSeatData.getAdjustingType();
        int adjustingDirection = this.mTrdRowMidSeatData.getAdjustingDirection();
        final long j = 1000;
        LogUtils.d(this.TAG, "start controlType " + adjustingType + ",direction " + adjustingDirection, false);
        if (adjustingType == 8) {
            setTrdRowMidSeatHeadVerMove(1, adjustingDirection);
            if (isSpeech) {
                j = SEAT_SPEECH_HOR_ADJUST_COUNT;
            }
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$OiY62a_lg9NTF8TOqabczukVYpI
            @Override // java.lang.Runnable
            public final void run() {
                SeatViewModel.this.lambda$controlTrdRowMidSeatStart$16$SeatViewModel(j);
            }
        });
    }

    public /* synthetic */ void lambda$controlTrdRowMidSeatStart$16$SeatViewModel(final long finalAdjustCount) {
        ManualAdjustSeatTimer manualAdjustSeatTimer = new ManualAdjustSeatTimer(false, false, false, false, true, finalAdjustCount);
        this.mTrdRowMidAdjustTimer = manualAdjustSeatTimer;
        manualAdjustSeatTimer.start();
    }

    public void controlTrdRowMidSeatEnd(int type) {
        int adjustingType = this.mTrdRowMidSeatData.getAdjustingType();
        LogUtils.d(this.TAG, "controlTrdRowMidSeatEnd:" + type + ",adjustType:" + adjustingType + ",mIsTrdRowMidSeatManualMoving:" + this.mIsTrdRowMidSeatManualMoving, false);
        if (this.mIsTrdRowMidSeatManualMoving && adjustingType == type) {
            this.mIsTrdRowMidSeatManualMoving = false;
            this.mTrdRowMidSeatData.setAdjustingType(0);
            ManualAdjustSeatTimer manualAdjustSeatTimer = this.mTrdRowMidAdjustTimer;
            if (manualAdjustSeatTimer != null) {
                manualAdjustSeatTimer.cancel();
                this.mTrdRowMidAdjustTimer = null;
            }
            int adjustingDirection = this.mTrdRowMidSeatData.getAdjustingDirection();
            LogUtils.d(this.TAG, "end controlType " + adjustingType + ",direction " + adjustingDirection, false);
            if (adjustingType != 8) {
                return;
            }
            setTrdRowMidSeatHeadVerMove(3, adjustingDirection);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlTrdRowMidSeat() {
        int adjustingType = this.mTrdRowMidSeatData.getAdjustingType();
        int adjustingDirection = this.mTrdRowMidSeatData.getAdjustingDirection();
        LogUtils.d(this.TAG, "controlType " + adjustingType + ",direction " + adjustingDirection, false);
        if (adjustingType != 8) {
            return;
        }
        setTrdRowMidSeatHeadVerMove(2, adjustingDirection);
    }

    private void headrestNotice() {
        NotificationHelper.getInstance().showToast(R.string.vip_seat_restore_toast);
        SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.vip_seat_restore_toast), true);
    }

    private Set<Integer> makeMassagerIds(int index) {
        HashSet hashSet = new HashSet();
        if (index == 4) {
            hashSet.add(0);
            hashSet.add(1);
            hashSet.add(2);
            hashSet.add(3);
        } else {
            hashSet.add(Integer.valueOf(index));
        }
        return hashSet;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void startMassager(int index, String effectId) {
        if (index == 4) {
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            for (int i = 0; i <= 3; i++) {
                if (this.mMsmController.isMassagerRunning(i)) {
                    hashSet.add(Integer.valueOf(i));
                } else {
                    hashSet2.add(Integer.valueOf(i));
                }
            }
            if (hashSet.size() > 0) {
                this.mMsmController.setMassagerEffect(hashSet, effectId);
            }
            if (hashSet2.size() > 0) {
                this.mMsmController.startMassager(hashSet2, effectId);
            }
        } else if (this.mMsmController.isMassagerRunning(index)) {
            this.mMsmController.setMassagerEffect(makeMassagerIds(index), effectId);
        } else {
            this.mMsmController.startMassager(index, getMassagerIntensity(index), effectId);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void startMassager(int index, int intensity, String effectId) {
        if (index == 4) {
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            for (int i = 0; i <= 3; i++) {
                if (this.mMsmController.isMassagerRunning(i)) {
                    hashSet.add(Integer.valueOf(i));
                } else {
                    hashSet2.add(Integer.valueOf(i));
                }
            }
            if (hashSet.size() > 0) {
                this.mMsmController.setMassagerEffect(hashSet, effectId);
                this.mMsmController.setMassagerIntensity(hashSet, intensity);
            }
            if (hashSet2.size() > 0) {
                this.mMsmController.startMassager(hashSet2, effectId);
                this.mMsmController.setMassagerIntensity(hashSet2, intensity);
            }
        } else if (this.mMsmController.isMassagerRunning(index)) {
            this.mMsmController.setMassagerEffect(makeMassagerIds(index), effectId);
            this.mMsmController.setMassagerIntensity(makeMassagerIds(index), intensity);
        } else {
            this.mMsmController.startMassager(index, intensity, effectId);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void stopMassager(int index) {
        this.mMsmController.stopMassager(makeMassagerIds(index));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setMassagerEffect(Set<Integer> ids, String effectId) {
        this.mMsmController.setMassagerEffect(ids, effectId);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setMassagerEffect(int index, String effectId) {
        this.mMsmController.setMassagerEffect(makeMassagerIds(index), effectId);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onMassageFailed(int index, String effect, int opCode, int errCode) {
        if (opCode == 0) {
            HashMap hashMap = new HashMap(1);
            hashMap.put("running", String.valueOf(this.mMsmController.isMassagerRunning(index)));
            updateMassageData(index, hashMap);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onMassagerStatusChange(int index, String effectId, int intensity, boolean running) {
        HashMap hashMap = new HashMap(3);
        hashMap.put("effectId", effectId);
        hashMap.put("intensity", String.valueOf(intensity));
        hashMap.put("running", String.valueOf(running));
        updateMassageData(index, hashMap);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onMassagerEffectChange(int index, String effectId) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("effectId", String.valueOf(effectId));
        updateMassageData(index, hashMap);
    }

    public LiveData<SeatMassage> getMassageData(int index) {
        if (index != 0) {
            if (index != 1) {
                if (index != 2) {
                    if (index != 3) {
                        return null;
                    }
                    return this.mPsnRearMassageData;
                }
                return this.mDrvRearMassageData;
            }
            return this.mPsnMassageData;
        }
        return this.mDrvMassageData;
    }

    public MutableLiveData<Long> getMassageTimeData(int index) {
        if (index != 0) {
            if (index == 1) {
                return this.mPsnMassageTimeData;
            }
            if (index == 2) {
                return this.mDrvRearMassageTimeData;
            }
            if (index == 3) {
                return this.mPsnRearMassageTimeData;
            }
            if (index != 4) {
                return null;
            }
        }
        return this.mDrvMassageTimeData;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setMassagerIntensity(int index, int intensity) {
        LogUtils.d(this.TAG, "setMassagerIntensity(" + index + ")= " + intensity, false);
        this.mMsmController.setMassagerIntensity(makeMassagerIds(index), intensity);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getMassagerIntensity(int index) {
        if (index == 4) {
            index = 0;
        }
        return this.mMsmController.getMassagerIntensity(index);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onMassagerIntensityChange(int index, int intensity) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("intensity", String.valueOf(intensity));
        updateMassageData(index, hashMap);
    }

    private void updateMassageData(int index, Map<String, String> params) {
        MutableLiveData<SeatMassage> mutableLiveData;
        SeatMassage value;
        if (index == 0) {
            mutableLiveData = this.mDrvMassageData;
        } else if (index == 1) {
            mutableLiveData = this.mPsnMassageData;
        } else if (index == 2) {
            mutableLiveData = this.mDrvRearMassageData;
        } else {
            mutableLiveData = index != 3 ? null : this.mPsnRearMassageData;
        }
        if (mutableLiveData == null || (value = mutableLiveData.getValue()) == null) {
            return;
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value2 = entry.getValue();
            key.hashCode();
            char c = 65535;
            switch (key.hashCode()) {
                case -1017208180:
                    if (key.equals("effectId")) {
                        c = 0;
                        break;
                    }
                    break;
                case 499324979:
                    if (key.equals("intensity")) {
                        c = 1;
                        break;
                    }
                    break;
                case 1550783935:
                    if (key.equals("running")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    value.setEffect(value2);
                    break;
                case 1:
                    value.setIntensity(Integer.parseInt(value2));
                    break;
                case 2:
                    if (!value.isRunning() && Boolean.parseBoolean(value2)) {
                        updateMassageTimer(value.getIndex(), true);
                    } else if (value.isRunning() && !Boolean.parseBoolean(value2)) {
                        updateMassageTimer(value.getIndex(), false);
                    }
                    value.setRunning(Boolean.parseBoolean(value2));
                    break;
            }
        }
        mutableLiveData.postValue(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class SeatMassagerTimer extends XpTickDownTimer {
        int seat;

        @Override // com.xiaopeng.xpmeditation.util.XpTickDownTimer
        public void onCancel() {
        }

        public SeatMassagerTimer(int seat) {
            super(SeatViewModel.COUNTDOWN_TIME, 1000L, "seat_massage_" + seat);
            this.seat = seat;
        }

        @Override // com.xiaopeng.xpmeditation.util.XpTickDownTimer
        public void onTick(long time) {
            int i = this.seat;
            if (i == 0) {
                SeatViewModel.this.mDrvMassageTimeData.postValue(Long.valueOf(time));
            } else if (i == 1) {
                SeatViewModel.this.mPsnMassageTimeData.postValue(Long.valueOf(time));
            } else if (i == 2) {
                SeatViewModel.this.mDrvRearMassageTimeData.postValue(Long.valueOf(time));
            } else if (i != 3) {
            } else {
                SeatViewModel.this.mPsnRearMassageTimeData.postValue(Long.valueOf(time));
            }
        }

        @Override // com.xiaopeng.xpmeditation.util.XpTickDownTimer
        public void onFinish() {
            LogUtils.i(SeatViewModel.this.TAG, "seat_massage_" + this.seat + ", time up!");
            SeatViewModel.this.stopMassager(this.seat);
        }
    }

    private void updateMassageTimer(int index, boolean isRunning) {
        if (CarStatusUtils.isEURegion()) {
            if (index == 0) {
                SeatMassagerTimer seatMassagerTimer = this.mDrvMassageTimer;
                if (seatMassagerTimer != null) {
                    seatMassagerTimer.cancel();
                }
                if (isRunning) {
                    if (this.mDrvMassageTimer == null) {
                        this.mDrvMassageTimer = new SeatMassagerTimer(0);
                    }
                    this.mDrvMassageTimer.start();
                }
            } else if (index == 1) {
                SeatMassagerTimer seatMassagerTimer2 = this.mPsnMassageTimer;
                if (seatMassagerTimer2 != null) {
                    seatMassagerTimer2.cancel();
                }
                if (isRunning) {
                    if (this.mPsnMassageTimer == null) {
                        this.mPsnMassageTimer = new SeatMassagerTimer(1);
                    }
                    this.mPsnMassageTimer.start();
                }
            } else if (index == 2) {
                SeatMassagerTimer seatMassagerTimer3 = this.mDrvRearMassageTimer;
                if (seatMassagerTimer3 != null) {
                    seatMassagerTimer3.cancel();
                }
                if (isRunning) {
                    if (this.mDrvRearMassageTimer == null) {
                        this.mDrvRearMassageTimer = new SeatMassagerTimer(2);
                    }
                    this.mDrvRearMassageTimer.start();
                }
            } else if (index != 3) {
            } else {
                SeatMassagerTimer seatMassagerTimer4 = this.mPsnRearMassageTimer;
                if (seatMassagerTimer4 != null) {
                    seatMassagerTimer4.cancel();
                }
                if (isRunning) {
                    if (this.mPsnRearMassageTimer == null) {
                        this.mPsnRearMassageTimer = new SeatMassagerTimer(3);
                    }
                    this.mPsnRearMassageTimer.start();
                }
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public String getMassagerRunningEffectId(int index) {
        if (isMassagerRunning(index)) {
            return this.mMsmController.getMassagerEffectId(index);
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isMassagerRunning(int index) {
        return this.mMsmController.isMassagerRunning(index);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public List<String> getMassagerEffectIDs() {
        return this.mMsmController.getMassagerEffectIDs();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void startRhythm(int index, int mode) {
        startRhythm(index, mode, false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void startRhythm(int index, int mode, boolean playEffect) {
        if (mode >= 0) {
            this.mMsmController.setRhythmMode(mode);
            this.mMsmController.setRhythmEnable(makeRhythmIds(index), true);
            final String str = null;
            if (mode == 0) {
                str = SoundHelper.PATH_SEAT_RHYTHM_MODE_1;
            } else if (mode == 1) {
                str = SoundHelper.PATH_SEAT_RHYTHM_MODE_2;
            }
            if (!playEffect || TextUtils.isEmpty(str)) {
                return;
            }
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$SeatViewModel$2dryqM-kiT5ZVmr70O6jpY6XREc
                @Override // java.lang.Runnable
                public final void run() {
                    SoundHelper.play(str, true, 3);
                }
            }, 500L);
        }
    }

    private Set<Integer> makeRhythmIds(int index) {
        HashSet hashSet = new HashSet();
        if (index == 2) {
            hashSet.add(0);
            hashSet.add(1);
        } else {
            hashSet.add(Integer.valueOf(index));
        }
        return hashSet;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void stopRhythm(int index) {
        this.mMsmController.setRhythmEnable(makeRhythmIds(index), false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public boolean isRhythmRunning(int index) {
        return this.mMsmController.isRhythmRunning(index);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRhythmStatusChange(Set<Integer> ids, boolean running) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("running", String.valueOf(running));
        for (Integer num : ids) {
            updateRhythmData(num.intValue(), hashMap);
        }
    }

    private void updateRhythmData(int index, Map<String, String> params) {
        MutableLiveData<SeatRhythm> mutableLiveData;
        SeatRhythm value;
        if (index == 0) {
            mutableLiveData = this.mDrvRhythmData;
        } else {
            mutableLiveData = index != 1 ? null : this.mPsnRhythmData;
        }
        if (mutableLiveData == null || (value = mutableLiveData.getValue()) == null) {
            return;
        }
        boolean z = false;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value2 = entry.getValue();
            key.hashCode();
            char c = 65535;
            switch (key.hashCode()) {
                case 3357091:
                    if (key.equals("mode")) {
                        c = 0;
                        break;
                    }
                    break;
                case 499324979:
                    if (key.equals("intensity")) {
                        c = 1;
                        break;
                    }
                    break;
                case 1550783935:
                    if (key.equals("running")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    int parseInt = Integer.parseInt(value2);
                    if (parseInt != value.getMode()) {
                        value.setMode(parseInt);
                        break;
                    } else {
                        continue;
                    }
                case 1:
                    int parseInt2 = Integer.parseInt(value2);
                    if (parseInt2 != value.getIntensity()) {
                        value.setIntensity(parseInt2);
                        break;
                    } else {
                        continue;
                    }
                case 2:
                    boolean parseBoolean = Boolean.parseBoolean(value2);
                    if (parseBoolean != value.isRunning()) {
                        value.setRunning(parseBoolean);
                        break;
                    } else {
                        continue;
                    }
            }
            z = true;
        }
        if (z) {
            mutableLiveData.postValue(value);
        }
    }

    public LiveData<SeatRhythm> getRhythmData(int index) {
        if (index != 0) {
            if (index != 1) {
                return null;
            }
            return this.mPsnRhythmData;
        }
        return this.mDrvRhythmData;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRhythmIntensity(int index, int intensity) {
        LogUtils.d(this.TAG, "setRhythmIntensity(" + index + ")=" + intensity, false);
        this.mMsmController.setRhythmIntensity(makeRhythmIds(index), intensity);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRhythmIntensity(int index) {
        return this.mMsmController.getRhythmIntensity(index);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRhythmIntensityChange(Set<Integer> ids, int intensity) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("intensity", String.valueOf(intensity));
        for (Integer num : ids) {
            updateRhythmData(num.intValue(), hashMap);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setRhythmMode(int mode) {
        this.mMsmController.setRhythmMode(mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getRhythmMode() {
        return this.mMsmController.getRhythmMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public int getVibrateIntensity(int index) {
        return this.mMsmController.getVibrateIntensity(index);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void setVibrateIntensity(int index, int intensity) {
        this.mMsmController.setVibrateIntensity(index, intensity);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public List<String> getVibrateEffectIDs() {
        return this.mMsmController.getVibrateEffectIDs();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public void doVibrate(int index, String effect, int intensity) {
        this.mMsmController.doVibrate(index, effect, -1, intensity);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel
    public String getMassageEffect(int index) {
        return this.mMsmController.getMassagerEffectId(index);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.controller.IMsmController.Callback
    public void onRhythmModeChange(int mode) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("mode", String.valueOf(mode));
        updateRhythmData(0, hashMap);
        updateRhythmData(1, hashMap);
    }
}
