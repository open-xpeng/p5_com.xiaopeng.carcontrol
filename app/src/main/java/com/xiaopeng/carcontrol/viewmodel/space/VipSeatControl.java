package com.xiaopeng.carcontrol.viewmodel.space;

import android.os.Handler;
import android.os.Message;
import androidx.lifecycle.Observer;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.GearLevel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;

/* loaded from: classes2.dex */
public abstract class VipSeatControl {
    protected static final int HANDLER_WHAT_FLAT = 1;
    protected static final int HANDLER_WHAT_RESTORE = 4;
    protected static final int MESSAGE_DELAY_TIME = 2000;
    public static final int SEAT_MOVE_SAFE_HOR = 95;
    public static final int SEAT_MOVE_SAFE_TILT = 55;
    public static final int SLEEP_REST_SEAT_SAFE_DRV_HOR_POS;
    public static final int SLEEP_REST_SEAT_SAFE_DRV_TILT_POS;
    public static final int SLEEP_REST_SEAT_SAFE_DRV_VER_POS;
    public static final int SLEEP_REST_SEAT_SAFE_PSN_HOR_POS;
    public static final int SLEEP_REST_SEAT_SAFE_PSN_TILT_POS;
    public static final int SLEEP_REST_SEAT_SAFE_PSN_VER_POS;
    protected static final int START_MOVE_CHAIR_DELAY = 3000;
    public static final int VIP_SEAT_HOR_POS = 100;
    public static final int VIP_SEAT_TILT_POS = 0;
    public static final int VIP_SEAT_VER_POS;
    private Observer<GearLevel> mGearObserver;
    private Observer<Boolean> mHeadrestObserver;
    private XDialog mLayFlatDialog;
    private Observer<Integer> mMcuIgObserver;
    private Observer<Boolean> mRearOccupiedObserver;
    private XDialog mRestoreDialog;
    private Observer<Boolean> mSeatOccupiedObserver;
    protected SeatViewModel mSeatViewModel;
    protected OnStatusChangedListener mStatusChangedListener;
    protected VcuViewModel mVcuViewModel;
    protected final String TAG = getClass().getSimpleName();
    protected VipSeatStatus mSeatStatus = VipSeatStatus.Normal;
    private boolean mFrontOccupiedConfirm = false;
    private boolean mIsVoiceSource = false;
    protected boolean mIsDrvMoveHor = false;
    protected boolean mIsPsnMoveHor = false;
    protected boolean mIsInit = false;
    protected Handler mHandler = new Handler(ThreadUtils.getHandler(0).getLooper(), new Handler.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$fIVR0gP8suRDiZgqsBv9o_Gk_-I
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return VipSeatControl.this.lambda$new$0$VipSeatControl(message);
        }
    });
    Runnable moveSeatFlatRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$q-G3EpDJmbmoxYjMeFsaY-hLO7s
        @Override // java.lang.Runnable
        public final void run() {
            VipSeatControl.this.moveSeatFlat();
        }
    };
    Runnable restoreRun = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$j160j3Qfi99_C5nHnie022rVY3g
        @Override // java.lang.Runnable
        public final void run() {
            VipSeatControl.this.restore();
        }
    };

    /* loaded from: classes2.dex */
    public interface OnStatusChangedListener {
        default void onPlayLayFlatWaitingTTS() {
        }

        default void onSeatHasFlat() {
        }

        default void onSeatStartMove(boolean isLayFlat) {
        }

        void onStatusChanged(VipSeatStatus vipSeatStatus);
    }

    protected abstract boolean checkFrontSeatOccupied();

    protected abstract boolean checkSeatMoveAble();

    protected abstract boolean isLayFlatCorrectly();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void moveSeatFlat();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void restore();

    protected abstract void restoreFinish();

    /* JADX INFO: Access modifiers changed from: protected */
    public void seatFlatSpeak() {
    }

    protected abstract void stopSeat();

    static {
        VIP_SEAT_VER_POS = BaseFeatureOption.getInstance().isEcuDoSeatLayFlat() ? 100 : 32;
        SLEEP_REST_SEAT_SAFE_DRV_HOR_POS = CarBaseConfig.getInstance().getSleepRestSeatDrv()[0];
        SLEEP_REST_SEAT_SAFE_DRV_VER_POS = CarBaseConfig.getInstance().getSleepRestSeatDrv()[1];
        SLEEP_REST_SEAT_SAFE_DRV_TILT_POS = CarBaseConfig.getInstance().getSleepRestSeatDrv()[2];
        SLEEP_REST_SEAT_SAFE_PSN_HOR_POS = CarBaseConfig.getInstance().getSleepRestSeatPsn()[0];
        SLEEP_REST_SEAT_SAFE_PSN_VER_POS = CarBaseConfig.getInstance().getSleepRestSeatPsn()[1];
        SLEEP_REST_SEAT_SAFE_PSN_TILT_POS = CarBaseConfig.getInstance().getSleepRestSeatPsn()[2];
    }

    public /* synthetic */ boolean lambda$new$0$VipSeatControl(Message msg) {
        int i = msg.what;
        if (i == 1) {
            onVipSeatLayFlatFinish();
        } else if (i == 4) {
            restoreFinish();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init() {
        LogUtils.d(this.TAG, "SeatControl init");
        this.mVcuViewModel = (VcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class);
        this.mSeatViewModel = (SeatViewModel) ViewModelManager.getInstance().getViewModelImpl(ISeatViewModel.class);
        observerRearSeat();
    }

    public void onModeStart(boolean isVoiceSource) {
        init();
        this.mFrontOccupiedConfirm = true;
        this.mIsVoiceSource = isVoiceSource;
    }

    private void stopMoveObserver() {
        if (this.mMcuIgObserver == null) {
            this.mMcuIgObserver = new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$e9fM9hytolB39PX76ro4r8f4ZNs
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    VipSeatControl.this.lambda$stopMoveObserver$1$VipSeatControl((Integer) obj);
                }
            };
            this.mGearObserver = new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$p1mAFCLsKthmUAzQUKyEFtHKa8M
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    VipSeatControl.this.lambda$stopMoveObserver$2$VipSeatControl((GearLevel) obj);
                }
            };
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$xpMvQXXGav9LxJBKsBRanbHZW9M
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$stopMoveObserver$3$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$stopMoveObserver$1$VipSeatControl(Integer integer) {
        if (integer.intValue() == 0) {
            if (this.mSeatStatus == VipSeatStatus.FlatMoving || this.mSeatStatus == VipSeatStatus.RestoreMoving) {
                LogUtils.d(this.TAG, "ig off stop seat move");
                stopSeatMove();
            }
        }
    }

    public /* synthetic */ void lambda$stopMoveObserver$2$VipSeatControl(GearLevel gearLevel) {
        if ((this.mSeatStatus == VipSeatStatus.FlatMoving || this.mSeatStatus == VipSeatStatus.RestoreMoving) && gearLevel != GearLevel.P) {
            LogUtils.i(this.TAG, "exit gear p pause vip seat move");
            stopSeatMove();
        }
    }

    public /* synthetic */ void lambda$stopMoveObserver$3$VipSeatControl() {
        this.mSeatViewModel.getMcuIgState().removeObserver(this.mMcuIgObserver);
        this.mVcuViewModel.getGearLevelData().removeObserver(this.mGearObserver);
        this.mSeatViewModel.getMcuIgState().observeForever(this.mMcuIgObserver);
        this.mVcuViewModel.getGearLevelData().observeForever(this.mGearObserver);
    }

    private void unObserverStopMove() {
        if (this.mMcuIgObserver == null) {
            return;
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$_EcXEHdefkKlbXgFjlgTS1uWg34
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$unObserverStopMove$4$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$unObserverStopMove$4$VipSeatControl() {
        this.mSeatViewModel.getMcuIgState().removeObserver(this.mMcuIgObserver);
        this.mVcuViewModel.getGearLevelData().removeObserver(this.mGearObserver);
    }

    protected void observerRearSeat() {
        if (this.mRearOccupiedObserver == null) {
            this.mRearOccupiedObserver = new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$2nh4X7s5YUqxeSz43yNWpo4XFSI
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    VipSeatControl.this.onRearSeatOccupied(((Boolean) obj).booleanValue());
                }
            };
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$Q0NsFBYCQCfyUL0uoNZWU4MYBXE
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$observerRearSeat$5$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$observerRearSeat$5$VipSeatControl() {
        this.mSeatViewModel.getRrSeatOccupiedData().removeObserver(this.mRearOccupiedObserver);
        this.mSeatViewModel.getRmSeatOccupiedData().removeObserver(this.mRearOccupiedObserver);
        this.mSeatViewModel.getRlSeatOccupiedData().removeObserver(this.mRearOccupiedObserver);
        this.mSeatViewModel.getRrSeatOccupiedData().observeForever(this.mRearOccupiedObserver);
        this.mSeatViewModel.getRmSeatOccupiedData().observeForever(this.mRearOccupiedObserver);
        this.mSeatViewModel.getRlSeatOccupiedData().observeForever(this.mRearOccupiedObserver);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void observerDrvHeadrest() {
        if (this.mHeadrestObserver == null) {
            this.mHeadrestObserver = new $$Lambda$DqcSUZfXOyzpnNsHvB7uYK_NrgQ(this);
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$eicRW3CbALsZV2V19UguXrV7aEs
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$observerDrvHeadrest$6$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$observerDrvHeadrest$6$VipSeatControl() {
        this.mSeatViewModel.getDrvHeadrestNormal().removeObserver(this.mHeadrestObserver);
        this.mSeatViewModel.getDrvHeadrestNormal().observeForever(this.mHeadrestObserver);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void observerPsnHeadrest() {
        if (this.mHeadrestObserver == null) {
            this.mHeadrestObserver = new $$Lambda$DqcSUZfXOyzpnNsHvB7uYK_NrgQ(this);
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$dGk5aZttI0cC2ioBmrYJoxMxPMc
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$observerPsnHeadrest$7$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$observerPsnHeadrest$7$VipSeatControl() {
        this.mSeatViewModel.getPsnHeadrestNormal().removeObserver(this.mHeadrestObserver);
        this.mSeatViewModel.getPsnHeadrestNormal().observeForever(this.mHeadrestObserver);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void observerDrvSeatMove() {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$m3uOKrHE6eJspC3Kkg-aT0SR_To
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$observerDrvSeatMove$13$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$observerDrvSeatMove$13$VipSeatControl() {
        this.mSeatViewModel.getDriverSeatData(2).observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$GNUVqCHujrywr5elsu_ACPkWzgk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControl.this.lambda$null$8$VipSeatControl((Integer) obj);
            }
        });
        this.mSeatViewModel.getDriverSeatData(1).observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$HK8uvoZAVfsIdDGxpzExFM--3_8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControl.this.lambda$null$9$VipSeatControl((Integer) obj);
            }
        });
        this.mSeatViewModel.getDriverSeatData(3).observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$uwHckjPi8Wnl0aIQwulbikd95wI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControl.this.lambda$null$10$VipSeatControl((Integer) obj);
            }
        });
        if (CarBaseConfig.getInstance().isSupportDrvLeg()) {
            this.mSeatViewModel.getDriverSeatData(4).observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$GiIxmCEnZsami7N37aXa7BUp5ZU
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    VipSeatControl.this.lambda$null$11$VipSeatControl((Integer) obj);
                }
            });
        }
        if (CarBaseConfig.getInstance().isSupportDrvCushion()) {
            this.mSeatViewModel.getDriverSeatData(7).observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$0RQCx-QalMVm5gPtlAg_2Hm8fdQ
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    VipSeatControl.this.lambda$null$12$VipSeatControl((Integer) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$8$VipSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    public /* synthetic */ void lambda$null$9$VipSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    public /* synthetic */ void lambda$null$10$VipSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    public /* synthetic */ void lambda$null$11$VipSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    public /* synthetic */ void lambda$null$12$VipSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void observerPsnSeatMove() {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$spQlNic7qIj6aDnDVlSKPmROP7U
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$observerPsnSeatMove$18$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$observerPsnSeatMove$18$VipSeatControl() {
        this.mSeatViewModel.getPsnSeatHorzData().observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$BOrILmF8dSzOZjcrMlSIUPiddi8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControl.this.lambda$null$14$VipSeatControl((Integer) obj);
            }
        });
        this.mSeatViewModel.getPsnSeatTiltData().observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$2hpS59JCPkeLY_LS_Sj4DGf8COo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VipSeatControl.this.lambda$null$15$VipSeatControl((Integer) obj);
            }
        });
        if (BaseFeatureOption.getInstance().isSupportPsnSeatVerControl()) {
            this.mSeatViewModel.getPsnSeatVerData().observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$9rATX4aCUAaq7HYCi4wd-tFlWTc
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    VipSeatControl.this.lambda$null$16$VipSeatControl((Integer) obj);
                }
            });
        }
        if (CarBaseConfig.getInstance().isSupportPsnLeg()) {
            this.mSeatViewModel.getPsnSeatLegData().observeForever(new Observer() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$PmjKvjwy1G8a6oVeOfPSM2KnpvA
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    VipSeatControl.this.lambda$null$17$VipSeatControl((Integer) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$14$VipSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    public /* synthetic */ void lambda$null$15$VipSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    public /* synthetic */ void lambda$null$16$VipSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    public /* synthetic */ void lambda$null$17$VipSeatControl(Integer integer) {
        onSeatPosChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void observerDrvSeatOccupied() {
        if (this.mSeatOccupiedObserver == null) {
            this.mSeatOccupiedObserver = new $$Lambda$L5j_QEbveG9sCZKt4HxS3fA7CQ(this);
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$nv0WOGwo8BzZmqPrw2n22mKrL_k
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$observerDrvSeatOccupied$19$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$observerDrvSeatOccupied$19$VipSeatControl() {
        this.mSeatViewModel.getDriverSeatOccupiedData().removeObserver(this.mSeatOccupiedObserver);
        this.mSeatViewModel.getDriverSeatOccupiedData().observeForever(this.mSeatOccupiedObserver);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void observerPsnSeatOccupied() {
        if (this.mSeatOccupiedObserver == null) {
            this.mSeatOccupiedObserver = new $$Lambda$L5j_QEbveG9sCZKt4HxS3fA7CQ(this);
        }
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$MShhHMokiNNTO_Bg1g0dNTGEwe8
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$observerPsnSeatOccupied$20$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$observerPsnSeatOccupied$20$VipSeatControl() {
        this.mSeatViewModel.getPsnSeatOccupiedData().removeObserver(this.mSeatOccupiedObserver);
        this.mSeatViewModel.getPsnSeatOccupiedData().observeForever(this.mSeatOccupiedObserver);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHeadrestChanged(Boolean aBoolean) {
        LogUtils.i(this.TAG, "headrest status changed:" + aBoolean + ",seatStatus:" + this.mSeatStatus);
        if (this.mSeatStatus == VipSeatStatus.FlatWaiting && !aBoolean.booleanValue()) {
            laySeatFlat();
        } else if (this.mSeatStatus == VipSeatStatus.FlatMoving && aBoolean.booleanValue()) {
            callbackStatus(VipSeatStatus.FlatWaiting);
            removeFlatHandler();
            stopSeat();
            speakHeadrestTts();
        } else if (this.mSeatStatus != VipSeatStatus.RestoreMoving || aBoolean.booleanValue()) {
        } else {
            callbackStatus(VipSeatStatus.RestoreWaiting);
            removeRestoreHandler();
            stopSeat();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatOccupiedChanged(Boolean aBoolean) {
        LogUtils.i(this.TAG, "Seat Occupied Status Changed: " + aBoolean + ",seatStatus:" + this.mSeatStatus);
        if (!aBoolean.booleanValue()) {
            if (this.mSeatStatus == VipSeatStatus.FlatWaiting) {
                laySeatFlat();
            }
        } else if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
            callbackStatus(VipSeatStatus.FlatWaiting);
            removeFlatHandler();
            stopSeat();
            speakSeatOccupiedTts();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void callbackStatus(VipSeatStatus vipSeatStatus) {
        LogUtils.i(this.TAG, "callbackStatus:" + vipSeatStatus + ",current:" + this.mSeatStatus);
        if (vipSeatStatus == VipSeatStatus.FlatWaiting || vipSeatStatus == VipSeatStatus.FlatMoving || vipSeatStatus == VipSeatStatus.RestoreWaiting || vipSeatStatus == VipSeatStatus.RestoreMoving) {
            stopMoveObserver();
        } else {
            unObserverStopMove();
        }
        if (this.mSeatStatus == vipSeatStatus) {
            return;
        }
        this.mSeatStatus = vipSeatStatus;
        OnStatusChangedListener onStatusChangedListener = this.mStatusChangedListener;
        if (onStatusChangedListener != null) {
            onStatusChangedListener.onStatusChanged(vipSeatStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearSeatOccupied(boolean b) {
        LogUtils.i(this.TAG, "onRearSeatOccupied:" + b + ",status:" + this.mSeatStatus);
        if (b) {
            if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
                LogUtils.i(this.TAG, "rear seat occupied pause vip seat move");
                removeFlatHandler();
                callbackStatus(VipSeatStatus.FlatWaiting);
                stopSeat();
                speakRearSeatOccupied(true);
            } else if (this.mSeatStatus == VipSeatStatus.RestoreMoving) {
                LogUtils.i(this.TAG, "rear seat occupied pause vip seat restore");
                removeRestoreHandler();
                callbackStatus(VipSeatStatus.RestoreWaiting);
                stopSeat();
                speakRearSeatOccupied(false);
            }
        } else if (this.mSeatStatus == VipSeatStatus.FlatWaiting) {
            laySeatFlat();
        } else if (this.mSeatStatus == VipSeatStatus.RestoreWaiting) {
            restoreSeat();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSeatPosChanged() {
        if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 2000L);
        } else if (this.mSeatStatus == VipSeatStatus.RestoreMoving) {
            this.mHandler.removeMessages(4);
            this.mHandler.sendEmptyMessageDelayed(4, 2000L);
        }
    }

    public void laySeatFlat() {
        init();
        LogUtils.i(this.TAG, "enterSpaceCapsule drvHeadrest:" + this.mSeatViewModel.isDrvHeadrestNormal() + ",psnHeadrest:" + this.mSeatViewModel.isPsnHeadrestNormal() + ",rlOccupied:" + this.mSeatViewModel.isRlSeatOccupied() + ",rrOccupied:" + this.mSeatViewModel.isRrSeatOccupied() + ",rcOccupied:" + this.mSeatViewModel.isRmSeatOccupied());
        XDialog xDialog = this.mLayFlatDialog;
        if (xDialog != null && xDialog.isShowing()) {
            LogUtils.i(this.TAG, "Flat confirm dialog is showing");
        } else if (isLayFlatCorrectly()) {
            seatFlatSpeak();
            callbackStatus(VipSeatStatus.Flat);
            OnStatusChangedListener onStatusChangedListener = this.mStatusChangedListener;
            if (onStatusChangedListener != null) {
                onStatusChangedListener.onSeatHasFlat();
            }
        } else {
            boolean z = this.mSeatStatus != VipSeatStatus.FlatWaiting;
            if (!checkSeatMoveAble()) {
                if (z) {
                    callbackStatus(VipSeatStatus.FlatWaiting);
                    layFlatWaitingTTS();
                }
            } else if (checkFrontSeatOccupied() && this.mFrontOccupiedConfirm) {
                showFlatConfirmDialog();
            } else {
                moveSeatDelay();
            }
        }
    }

    protected void layFlatWaitingTTS() {
        SpeechHelper.getInstance().speak(App.getInstance().getString(!BaseFeatureOption.getInstance().isSupportSeatFlatFrontOccupied() ? R.string.vip_seat_notice_flat_waiting_front_ccupied : R.string.vip_seat_notice_flat_waiting), true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setFlatStatus() {
        this.mSeatStatus = VipSeatStatus.Flat;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean checkRearSeatOccupied() {
        return (this.mSeatViewModel.isRlSeatOccupied() || this.mSeatViewModel.isRmSeatOccupied() || this.mSeatViewModel.isRrSeatOccupied()) ? false : true;
    }

    protected void showFlatConfirmDialog() {
        LogUtils.i(this.TAG, "showFlatConfirmDialog:" + this.mIsVoiceSource);
        this.mFrontOccupiedConfirm = false;
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$AD_egS-HRLngw5P9FUp4KQttWPY
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$showFlatConfirmDialog$23$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$showFlatConfirmDialog$23$VipSeatControl() {
        if (!this.mIsVoiceSource) {
            if (this.mLayFlatDialog == null) {
                this.mLayFlatDialog = new XDialog(App.getInstance()).setTitle(R.string.vip_seat_confirm_dialog_title).setMessage(R.string.vip_seat_confirm_dialog_msg).setPositiveButton(App.getInstance().getString(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$491_SzlLbkqNduO9f9tEYd4my6U
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        VipSeatControl.this.lambda$null$21$VipSeatControl(xDialog, i);
                    }
                }).setNegativeButton(App.getInstance().getString(R.string.btn_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$3qcHVMYZp6TgAn3XL30EHsmoqaU
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        VipSeatControl.this.lambda$null$22$VipSeatControl(xDialog, i);
                    }
                }).setCanceledOnTouchOutside(false).setSystemDialog(2048);
            }
            if (!this.mLayFlatDialog.isShowing()) {
                this.mLayFlatDialog.show();
            }
            SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.vip_seat_confirm_tts), true);
            return;
        }
        moveSeatDelay();
    }

    public /* synthetic */ void lambda$null$21$VipSeatControl(XDialog xDialog, int i) {
        moveSeatDelay();
    }

    public /* synthetic */ void lambda$null$22$VipSeatControl(XDialog xDialog, int i) {
        SpeechHelper.getInstance().stop();
        callbackStatus(VipSeatStatus.FlatPause);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void moveSeatDelay() {
        if (checkSeatMoveAble()) {
            startLayFlatMoveNotice();
            this.mHandler.removeCallbacks(this.moveSeatFlatRun);
            this.mHandler.postDelayed(this.moveSeatFlatRun, 3000L);
            return;
        }
        layFlatWaitingTTS();
        callbackStatus(VipSeatStatus.FlatWaiting);
    }

    protected void startLayFlatMoveNotice() {
        if (this.mSeatStatus == VipSeatStatus.Normal || this.mSeatStatus == VipSeatStatus.FlatWaiting) {
            SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.vip_seat_moving_toast), true);
        }
        callbackStatus(VipSeatStatus.FlatMoving);
    }

    protected void startRestoreNotice() {
        if (this.mSeatStatus == VipSeatStatus.Flat || this.mSeatStatus == VipSeatStatus.RestoreWaiting) {
            SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.vip_seat_restore_tts), true);
        }
        callbackStatus(VipSeatStatus.RestoreMoving);
    }

    public void pauseFlatMove() {
        LogUtils.i(this.TAG, "pause seat move :" + this.mSeatStatus);
        init();
        SpeechHelper.getInstance().stop();
        if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
            callbackStatus(VipSeatStatus.FlatPause);
        }
        removeFlatHandler();
        stopSeat();
    }

    public void pauseRestoreMove() {
        LogUtils.i(this.TAG, "pause restore :" + this.mSeatStatus);
        init();
        SpeechHelper.getInstance().stop();
        if (this.mSeatStatus == VipSeatStatus.RestoreMoving) {
            callbackStatus(VipSeatStatus.RestorePause);
        }
        removeRestoreHandler();
        stopSeat();
    }

    protected void onVipSeatLayFlatFinish() {
        LogUtils.i(this.TAG, "onVipSeatLayFlatEnd:" + this.mSeatStatus);
        if (this.mSeatStatus == VipSeatStatus.FlatMoving) {
            if (isLayFlatCorrectly()) {
                callbackStatus(VipSeatStatus.Flat);
                seatFlatSpeak();
                return;
            }
            callbackStatus(VipSeatStatus.FlatPause);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void stopSeatMove() {
        LogUtils.i(this.TAG, "stop seat move :" + this.mSeatStatus);
        init();
        removeFlatHandler();
        removeRestoreHandler();
        callbackStatus(VipSeatStatus.Normal);
        stopSeat();
    }

    private void showRestoreSeatDialog() {
        this.mFrontOccupiedConfirm = false;
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$8BTOK7pArtzRQH_1tkrtC16NztA
            @Override // java.lang.Runnable
            public final void run() {
                VipSeatControl.this.lambda$showRestoreSeatDialog$26$VipSeatControl();
            }
        });
    }

    public /* synthetic */ void lambda$showRestoreSeatDialog$26$VipSeatControl() {
        if (!this.mIsVoiceSource) {
            if (this.mRestoreDialog == null) {
                this.mRestoreDialog = new XDialog(App.getInstance()).setTitle(R.string.vip_seat_confirm_dialog_title).setMessage(R.string.vip_seat_restore_dialog_msg).setPositiveButton(App.getInstance().getString(R.string.btn_confirm), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$XrsJrDMc7OAWohqleX9tS4_dH80
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        VipSeatControl.this.lambda$null$24$VipSeatControl(xDialog, i);
                    }
                }).setNegativeButton(App.getInstance().getString(R.string.btn_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.space.-$$Lambda$VipSeatControl$f1QTeteBCb6yI4jXJVBcMELQZKY
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog, int i) {
                        VipSeatControl.this.lambda$null$25$VipSeatControl(xDialog, i);
                    }
                }).setCanceledOnTouchOutside(false).setSystemDialog(2048);
            }
            if (!this.mRestoreDialog.isShowing()) {
                this.mRestoreDialog.show();
            }
            SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.vip_seat_restore_confirm_tts), true);
            return;
        }
        restoreSeatDelay();
    }

    public /* synthetic */ void lambda$null$24$VipSeatControl(XDialog xDialog, int i) {
        restoreSeatDelay();
    }

    public /* synthetic */ void lambda$null$25$VipSeatControl(XDialog xDialog, int i) {
        SpeechHelper.getInstance().stop();
        callbackStatus(VipSeatStatus.RestorePause);
    }

    public void restoreSeat() {
        init();
        LogUtils.i(this.TAG, "restoreSeat mStatus:" + this.mSeatStatus + ",rLSeatOccupied:" + this.mSeatViewModel.isRlSeatOccupied() + ",rMSeatOccupied:" + this.mSeatViewModel.isRmSeatOccupied() + ",rRSeatOccupied:" + this.mSeatViewModel.isRrSeatOccupied());
        XDialog xDialog = this.mRestoreDialog;
        if (xDialog != null && xDialog.isShowing()) {
            LogUtils.i(this.TAG, "Restore seat dialog is showing");
            return;
        }
        boolean z = this.mSeatStatus != VipSeatStatus.RestoreWaiting;
        if (!checkRearSeatOccupied()) {
            if (z) {
                callbackStatus(VipSeatStatus.RestoreWaiting);
                SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.vip_seat_notice_rear_seat_occupied_restore), true);
            }
        } else if (checkFrontSeatOccupied() && this.mFrontOccupiedConfirm) {
            showRestoreSeatDialog();
        } else {
            restoreSeatDelay();
        }
    }

    private void restoreSeatDelay() {
        if (checkRearSeatOccupied()) {
            startRestoreNotice();
            this.mHandler.removeCallbacks(this.restoreRun);
            this.mHandler.postDelayed(this.restoreRun, 3000L);
            return;
        }
        callbackStatus(VipSeatStatus.RestoreWaiting);
        SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.vip_seat_notice_rear_seat_occupied_restore), true);
    }

    private void speakHeadrestTts() {
        SpeechHelper.getInstance().speak(ResUtils.getString(R.string.vip_seat_notice_headrest), true);
    }

    private void speakRearSeatOccupied(boolean layFlat) {
        SpeechHelper.getInstance().speak(ResUtils.getString(layFlat ? R.string.vip_seat_notice_rear_seat_occupied : R.string.vip_seat_notice_rear_seat_occupied_restore), true);
    }

    private void speakSeatOccupiedTts() {
        SpeechHelper.getInstance().speak(ResUtils.getString(R.string.vip_seat_notice_front_seat_occupied), true);
    }

    public VipSeatStatus getSeatStatus() {
        return this.mSeatStatus;
    }

    private void removeFlatHandler() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.moveSeatFlatRun);
            this.mHandler.removeMessages(1);
        }
    }

    private void removeRestoreHandler() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.restoreRun);
            this.mHandler.removeMessages(4);
        }
    }

    public void setOnStatusChangedListener(OnStatusChangedListener onStatusChangedListener) {
        this.mStatusChangedListener = onStatusChangedListener;
    }
}
