package com.xiaopeng.carcontrol.viewmodel.cabin;

import android.content.Context;
import android.content.DialogInterface;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.impl.BcmController;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.lang.NoConcurrenceRunnable;
import com.xiaopeng.carcontrol.util.ContextUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class ArsViewModel implements IArsViewModel {
    static final String TAG = "Ars";
    private static int TASK_TIMEOUT = 45000;
    private BcmController mBcmController;
    private WeakReference<XDialog> mFoldConfirmDialog;
    private boolean mFoldConfirming;
    private WeakReference<XDialog> mInitConfirmDialog;
    private boolean mInitializeConfirming;
    private boolean mInitializing;
    private WeakReference<XDialog> mResetConfirmDialog;
    private boolean mResetConfirming;
    private boolean mResetting;
    private WeakReference<XDialog> mUnfoldConfirmDialog;
    private boolean mUnfoldConfirming;
    private MutableLiveData<ArsFoldState> mFoldState = new MutableLiveData<>();
    private MutableLiveData<ArsInitState> mInitState = new MutableLiveData<>();
    private MutableLiveData<ArsFaultState> mFaultState = new MutableLiveData<>();
    private MutableLiveData<ArsWorkState> mWorkState = new MutableLiveData<>();
    private MutableLiveData<ArsWorkMode> mWorkMode = new MutableLiveData<>();
    private MutableLiveData<Integer> mPosition = new MutableLiveData<>();
    private NoConcurrenceRunnable mCheckFoldedTask = new NoConcurrenceRunnable(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$UrXNEscZCcu8yeE2Bov_Z7N-Q94
        @Override // java.lang.Runnable
        public final void run() {
            ArsViewModel.this.handleFoldError();
        }
    });
    private NoConcurrenceRunnable mCheckUnFoldedTask = new NoConcurrenceRunnable(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$BUSw02Khya43uZSYIUWP-aBC0t8
        @Override // java.lang.Runnable
        public final void run() {
            ArsViewModel.this.handleUnfoldError();
        }
    });
    private NoConcurrenceRunnable mCheckInitTask = new NoConcurrenceRunnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.ArsViewModel.1
        @Override // com.xiaopeng.carcontrol.lang.NoConcurrenceRunnable
        protected void run() {
            ArsViewModel.this.mInitializing = false;
            ArsInitState arsInitState = ArsViewModel.this.getArsInitState();
            LogUtils.i(ArsViewModel.TAG, "check ars init timeout task: " + arsInitState);
            if (arsInitState != ArsInitState.INITIALIZED) {
                LogUtils.i(ArsViewModel.TAG, "mCheckInitTask error showToast");
                NotificationHelper.getInstance().showToast(R.string.ars_control_init_error_tips);
            }
            ArsViewModel.this.recheckFoldState();
            ArsViewModel.this.recheckInitState();
        }
    };
    private NoConcurrenceRunnable mCheckResetTask = new NoConcurrenceRunnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.ArsViewModel.2
        @Override // com.xiaopeng.carcontrol.lang.NoConcurrenceRunnable
        protected void run() {
            ArsViewModel.this.mResetting = false;
            ArsFaultState arsFaultState = ArsViewModel.this.getArsFaultState();
            ArsInitState arsInitState = ArsViewModel.this.getArsInitState();
            LogUtils.i(ArsViewModel.TAG, "check ars reset timeout task: " + arsFaultState);
            if (arsFaultState != ArsFaultState.NO_FAULT || arsInitState != ArsInitState.INITIALIZED) {
                LogUtils.i(ArsViewModel.TAG, "mCheckResetTask error showToast");
                NotificationHelper.getInstance().showToast(R.string.ars_control_reset_error_tips);
            }
            ArsViewModel.this.recheckFoldState();
            ArsViewModel.this.recheckFaultState();
            ArsViewModel.this.recheckInitState();
        }
    };

    private boolean isPositionFullFolded(int position) {
        return position == 0;
    }

    private boolean isPositionFullUnfolded(int position) {
        return position == 100;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$null$0(XDialog xDialog, int i) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$null$13(XDialog xDialog, int i) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$null$4(XDialog xDialog, int i) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$null$9(XDialog xDialog, int i) {
    }

    public ArsViewModel() {
        BcmController bcmController = (BcmController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mBcmController = bcmController;
        bcmController.registerCallback(new IBcmController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.ArsViewModel.3
            @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
            public void onArsInitStChanged(int state) {
                ArsViewModel.this.handleArsInitStateChanged(state);
            }

            @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
            public void onArsPosChanged(int position) {
                ArsViewModel.this.handleArsPositionChanged(position);
            }

            @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
            public void onArsWorkModeChanged(int mode) {
                ArsViewModel.this.handleArsWorkModeChanged(mode);
            }

            @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
            public void onArsWorkStChanged(int state) {
                ArsViewModel.this.handleArsWorkStateChanged(state);
            }

            @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
            public void onArsFaultStateChange(int state) {
                ArsViewModel.this.handleArsFaultStateChanged(state);
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public MutableLiveData<ArsFoldState> getArsFoldStateLiveData() {
        return this.mFoldState;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public ArsFoldState getArsFoldState() {
        ArsFoldState value = this.mFoldState.getValue();
        return value != null ? value : ArsFoldState.fromBcmValue(this.mBcmController.getArsWorkSt(), this.mBcmController.getArsPosition());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public MutableLiveData<ArsInitState> getArsInitStateLiveData() {
        return this.mInitState;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public ArsInitState getArsInitState() {
        ArsInitState value = this.mInitState.getValue();
        return value != null ? value : ArsInitState.fromBcmValue(this.mBcmController.getArsInitSt());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public MutableLiveData<ArsFaultState> getArsFaultStateLiveData() {
        return this.mFaultState;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public ArsFaultState getArsFaultState() {
        ArsFaultState value = this.mFaultState.getValue();
        return value != null ? value : ArsFaultState.fromBcmValue(this.mBcmController.getArsFaultState());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public MutableLiveData<ArsWorkState> getArsWorkStateLiveData() {
        return this.mWorkState;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public ArsWorkState getArsWorkState() {
        ArsWorkState value = this.mWorkState.getValue();
        return value != null ? value : ArsWorkState.fromBcmValue(this.mBcmController.getArsWorkSt());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public MutableLiveData<ArsWorkMode> getArsWorkModeLiveData() {
        return this.mWorkMode;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public ArsWorkMode getArsWorkMode() {
        ArsWorkMode value = this.mWorkMode.getValue();
        return value != null ? value : ArsWorkMode.fromBcmValue(this.mBcmController.getArsWorkMode());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public int checkError() {
        if (((WindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class)).isTrunkClosed()) {
            if (this.mInitializeConfirming) {
                return 4;
            }
            if (this.mResetConfirming) {
                return 8;
            }
            if (this.mFoldConfirming) {
                return 10;
            }
            if (this.mUnfoldConfirming) {
                return 9;
            }
            if (this.mResetting) {
                return 7;
            }
            if (getArsFaultState() == ArsFaultState.FAULT) {
                return 6;
            }
            ArsInitState arsInitState = getArsInitState();
            if (arsInitState == ArsInitState.INITIALIZING) {
                return 3;
            }
            if (arsInitState == ArsInitState.UNINITIALIZED) {
                return 2;
            }
            return getArsFoldState() == ArsFoldState.MOVING ? 5 : 0;
        }
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void fold() {
        fold(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void fold(boolean warning) {
        if (warning) {
            int checkError = checkError();
            LogUtils.i(TAG, "ArsControl: fold, checkStatus: " + checkError);
            if (checkError == 1) {
                NotificationHelper.getInstance().showToast(R.string.ars_control_precheck_close_trunk);
                return;
            } else if (checkError == 5 || checkError == 3 || checkError == 7) {
                NotificationHelper.getInstance().showToast(R.string.ars_control_precheck_moving);
                return;
            } else if (checkError == 2) {
                startInitialize(true);
                return;
            } else if (checkError == 6) {
                startReset(true);
                return;
            } else if (checkError == 10 || checkError == 9 || checkError == 8 || checkError == 4) {
                return;
            } else {
                this.mFoldConfirming = true;
                ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$xp2Wqo32jMvHBaYxm_tnbQXluIw
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArsViewModel.this.lambda$fold$3$ArsViewModel();
                    }
                });
                return;
            }
        }
        this.mCheckFoldedTask.cancel();
        this.mCheckUnFoldedTask.cancel();
        hideDialogs();
        LogUtils.i(TAG, "ArsControl: fold");
        this.mBcmController.setARSFoldOrUnfold(true);
        this.mFoldState.postValue(ArsFoldState.MOVING);
        ThreadUtils.runOnMainThreadDelay(this.mCheckFoldedTask.newSession(), TASK_TIMEOUT);
        if (getArsWorkMode() == ArsWorkMode.AUTO) {
            setArsWorkMode(ArsWorkMode.MANUAL);
            NotificationHelper.getInstance().showToast(R.string.ars_auto_mode_closed_tip, false);
        }
    }

    public /* synthetic */ void lambda$fold$3$ArsViewModel() {
        Context context = ContextUtils.getContext();
        XDialog showDialog = NotificationHelper.getInstance().showDialog(context.getString(R.string.ars_control_warning_title), context.getString(R.string.ars_control_warning_fold_content), context.getString(R.string.btn_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$BVScAQu6bQPhW1ovwSoRuQLzgFw
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ArsViewModel.lambda$null$0(xDialog, i);
            }
        }, context.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$PmBBxH-YsGIEBVZgE5oKNJJpL3M
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ArsViewModel.this.lambda$null$1$ArsViewModel(xDialog, i);
            }
        }, "ARSFold");
        showDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$8C7hbEkXW_hJ9tICzp4b6fzlUUw
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ArsViewModel.this.lambda$null$2$ArsViewModel(dialogInterface);
            }
        });
        this.mFoldConfirmDialog = new WeakReference<>(showDialog);
    }

    public /* synthetic */ void lambda$null$1$ArsViewModel(XDialog xDialog, int i) {
        xDialog.dismiss();
        fold(false);
    }

    public /* synthetic */ void lambda$null$2$ArsViewModel(DialogInterface d) {
        this.mFoldConfirming = false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void unfold() {
        unfold(false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void unfold(boolean warning) {
        if (warning) {
            int checkError = checkError();
            LogUtils.i(TAG, "ArsControl: unfold, checkStatus: " + checkError);
            if (checkError == 1) {
                NotificationHelper.getInstance().showToast(R.string.ars_control_precheck_close_trunk);
                return;
            } else if (checkError == 5 || checkError == 3 || checkError == 7) {
                NotificationHelper.getInstance().showToast(R.string.ars_control_precheck_moving);
                return;
            } else if (checkError == 2) {
                startInitialize(true);
                return;
            } else if (checkError == 6) {
                startReset(true);
                return;
            } else if (checkError == 10 || checkError == 9 || checkError == 8 || checkError == 4) {
                return;
            } else {
                this.mUnfoldConfirming = true;
                ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$zaY0N0ao3kbXx5jM6pav9kYMqvk
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArsViewModel.this.lambda$unfold$7$ArsViewModel();
                    }
                });
                return;
            }
        }
        this.mCheckFoldedTask.cancel();
        this.mCheckUnFoldedTask.cancel();
        hideDialogs();
        LogUtils.i(TAG, "ArsControl: unfold");
        this.mBcmController.setARSFoldOrUnfold(false);
        this.mFoldState.postValue(ArsFoldState.MOVING);
        ThreadUtils.runOnMainThreadDelay(this.mCheckUnFoldedTask.newSession(), TASK_TIMEOUT);
        if (getArsWorkMode() == ArsWorkMode.AUTO) {
            setArsWorkMode(ArsWorkMode.MANUAL);
            NotificationHelper.getInstance().showToast(R.string.ars_auto_mode_closed_tip, false);
        }
    }

    public /* synthetic */ void lambda$unfold$7$ArsViewModel() {
        Context context = ContextUtils.getContext();
        XDialog showDialog = NotificationHelper.getInstance().showDialog(context.getString(R.string.ars_control_warning_title), context.getString(R.string.ars_control_warning_unfold_content), context.getString(R.string.btn_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$3EFfuCIKBPfNLWVpOxxazr1nOR8
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ArsViewModel.lambda$null$4(xDialog, i);
            }
        }, context.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$P582AonLXJdaU6Hu0yYnRKUFuYg
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ArsViewModel.this.lambda$null$5$ArsViewModel(xDialog, i);
            }
        }, "ARSFold");
        showDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$hXPV4U5etXKMIf2QSi-uRUFFy3I
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ArsViewModel.this.lambda$null$6$ArsViewModel(dialogInterface);
            }
        });
        this.mUnfoldConfirmDialog = new WeakReference<>(showDialog);
    }

    public /* synthetic */ void lambda$null$5$ArsViewModel(XDialog xDialog, int i) {
        xDialog.dismiss();
        unfold(false);
    }

    public /* synthetic */ void lambda$null$6$ArsViewModel(DialogInterface dialog1) {
        this.mUnfoldConfirming = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recheckFoldState() {
        handleArsFoldStateChange(this.mBcmController.getArsWorkSt(), this.mBcmController.getArsPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recheckFaultState() {
        handleArsFaultStateChanged(this.mBcmController.getArsFaultState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recheckInitState() {
        handleArsInitStateChanged(this.mBcmController.getArsInitSt());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void handleArsPositionChanged(int arsPosition) {
        LogUtils.i(TAG, "handleArsPositionChanged: " + arsPosition);
        this.mPosition.postValue(Integer.valueOf(arsPosition));
        handleArsFoldStateChange(this.mBcmController.getArsWorkSt(), arsPosition);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void handleArsInitStateChanged(int status) {
        ArsInitState fromBcmValue = ArsInitState.fromBcmValue(status);
        this.mInitState.postValue(fromBcmValue);
        if (fromBcmValue == ArsInitState.INITIALIZED) {
            this.mCheckInitTask.cancel();
            this.mCheckResetTask.cancel();
            if (this.mInitializing) {
                this.mInitializing = false;
                NotificationHelper.getInstance().showToast(R.string.ars_control_init_success);
                recheckFoldState();
            } else if (this.mResetting) {
                this.mResetting = false;
                NotificationHelper.getInstance().showToast(R.string.ars_control_reset_success);
                recheckFoldState();
                recheckFaultState();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void handleArsFaultStateChanged(int state) {
        LogUtils.i(TAG, "handleArsFaultStateChanged: " + state);
        this.mFaultState.postValue(ArsFaultState.fromBcmValue(state));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void handleArsWorkStateChanged(int status) {
        LogUtils.i(TAG, "handleArsWorkStateChanged: " + status);
        this.mWorkState.postValue(ArsWorkState.fromBcmValue(status));
        handleArsFoldStateChange(status, this.mBcmController.getArsPosition());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void handleArsWorkModeChanged(int workMode) {
        this.mWorkMode.postValue(ArsWorkMode.fromBcmValue(workMode));
    }

    private void handleArsFoldStateChange(int workState, int position) {
        ArsFoldState fromBcmValue = this.mInitializing | this.mResetting ? ArsFoldState.MOVING : ArsFoldState.fromBcmValue(workState, position);
        LogUtils.i(TAG, "handleArsFoldStateChange, position: " + position + ", workSt: " + workState + ", resetting: " + this.mResetting + ", initializing: " + this.mInitializing + ", result: " + fromBcmValue);
        this.mFoldState.postValue(fromBcmValue);
        if (fromBcmValue != ArsFoldState.MOVING) {
            final int session = this.mCheckFoldedTask.getSession();
            final int session2 = this.mCheckUnFoldedTask.getSession();
            ThreadUtils.postDelayed(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$pi7q3LTN90Hji5r1YOBVSjjqDSI
                @Override // java.lang.Runnable
                public final void run() {
                    ArsViewModel.this.lambda$handleArsFoldStateChange$8$ArsViewModel(session, session2);
                }
            }, 800L);
        }
    }

    public /* synthetic */ void lambda$handleArsFoldStateChange$8$ArsViewModel(final int foldTask, final int unfoldTask) {
        this.mCheckFoldedTask.trigger(foldTask);
        this.mCheckUnFoldedTask.trigger(unfoldTask);
    }

    private boolean isPositionAtBoundary(int position) {
        return isPositionFullFolded(position) || isPositionFullUnfolded(position);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public int getArsPosition() {
        Integer value = this.mPosition.getValue();
        return value != null ? value.intValue() : this.mBcmController.getArsPosition();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public MutableLiveData<Integer> getArsPositionLiveData() {
        return this.mPosition;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void startInitialize(boolean withWarning) {
        if (withWarning) {
            this.mInitializeConfirming = true;
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$wjAOFm3DrxBxWKMSRa1jYU2eWZo
                @Override // java.lang.Runnable
                public final void run() {
                    ArsViewModel.this.lambda$startInitialize$12$ArsViewModel();
                }
            });
            return;
        }
        this.mCheckFoldedTask.cancel();
        this.mCheckUnFoldedTask.cancel();
        hideDialogs();
        this.mInitializing = true;
        this.mFoldState.postValue(ArsFoldState.MOVING);
        this.mInitState.postValue(ArsInitState.INITIALIZING);
        this.mBcmController.startArsInit();
        ThreadUtils.runOnMainThreadDelay(this.mCheckInitTask.newSession(), TASK_TIMEOUT);
    }

    public /* synthetic */ void lambda$startInitialize$12$ArsViewModel() {
        Context context = ContextUtils.getContext();
        XDialog showConfirmDialog = NotificationHelper.getInstance().showConfirmDialog(context.getString(R.string.ars_control_init_title), context.getString(R.string.ars_control_init_content), context.getString(R.string.btn_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$AOyhYUY6GcXR9VJWby9cZLaf1QE
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ArsViewModel.lambda$null$9(xDialog, i);
            }
        }, context.getString(R.string.btn_ok), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$p8shkYLn8bKe9WAmjJTDyRjjX7U
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ArsViewModel.this.lambda$null$10$ArsViewModel(xDialog, i);
            }
        }, "ARSInit", 5);
        showConfirmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$K8RV5D0P5c_GscqScaYdxElRXCM
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ArsViewModel.this.lambda$null$11$ArsViewModel(dialogInterface);
            }
        });
        this.mInitConfirmDialog = new WeakReference<>(showConfirmDialog);
    }

    public /* synthetic */ void lambda$null$10$ArsViewModel(XDialog xDialog, int i) {
        startInitialize(false);
        xDialog.dismiss();
    }

    public /* synthetic */ void lambda$null$11$ArsViewModel(DialogInterface dialog1) {
        this.mInitializeConfirming = false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void startReset(boolean warning) {
        if (warning) {
            this.mResetConfirming = true;
            ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$oMT2bZuhgqlknCrpxI8bFaVYgDY
                @Override // java.lang.Runnable
                public final void run() {
                    ArsViewModel.this.lambda$startReset$16$ArsViewModel();
                }
            });
            return;
        }
        this.mCheckFoldedTask.cancel();
        this.mCheckUnFoldedTask.cancel();
        hideDialogs();
        this.mResetting = true;
        this.mFoldState.postValue(ArsFoldState.MOVING);
        this.mInitState.postValue(ArsInitState.INITIALIZING);
        this.mFaultState.postValue(ArsFaultState.NO_FAULT);
        this.mBcmController.startArsInit();
        ThreadUtils.runOnMainThreadDelay(this.mCheckResetTask.newSession(), TASK_TIMEOUT);
    }

    public /* synthetic */ void lambda$startReset$16$ArsViewModel() {
        Context context = ContextUtils.getContext();
        XDialog showConfirmDialog = NotificationHelper.getInstance().showConfirmDialog(context.getString(R.string.ars_control_reset_title), context.getString(R.string.ars_control_reset_content), context.getString(R.string.btn_cancel), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$arBaunCtFFvchBQVm97vUlc-XE4
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ArsViewModel.lambda$null$13(xDialog, i);
            }
        }, context.getString(R.string.btn_reset), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$R7u_air3FQpnmYasn2yIjS2TZXU
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                ArsViewModel.this.lambda$null$14$ArsViewModel(xDialog, i);
            }
        }, "ARSReset", 5);
        showConfirmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$ri1Hjz686--f-lRxnSkV1kZ9TmI
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                ArsViewModel.this.lambda$null$15$ArsViewModel(dialogInterface);
            }
        });
        this.mResetConfirmDialog = new WeakReference<>(showConfirmDialog);
    }

    public /* synthetic */ void lambda$null$14$ArsViewModel(XDialog xDialog, int i) {
        startReset(false);
        xDialog.dismiss();
    }

    public /* synthetic */ void lambda$null$15$ArsViewModel(DialogInterface dialog1) {
        this.mResetConfirming = false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel
    public void setArsWorkMode(ArsWorkMode mode) {
        LogUtils.i(TAG, "setArsWorkMode: " + mode);
        this.mBcmController.setArsWorkMode(mode.toBcmValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFoldError() {
        ArsFoldState value = this.mFoldState.getValue();
        ArsFaultState value2 = this.mFaultState.getValue();
        int arsPosition = getArsPosition();
        LogUtils.i(TAG, "check ars fold task, foldState: " + value + ", errorState: " + value2 + ", position: " + arsPosition);
        if (value2 == ArsFaultState.NO_FAULT && value == ArsFoldState.FOLDED && isPositionFullFolded(arsPosition)) {
            return;
        }
        if (value == ArsFoldState.MOVING) {
            this.mFoldState.postValue(ArsFoldState.UNFOLDED);
        }
        if (value2 == ArsFaultState.FAULT) {
            startReset(true);
            return;
        }
        LogUtils.i(TAG, "handleFoldError showToast");
        NotificationHelper.getInstance().showToast(R.string.ars_control_error_operation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUnfoldError() {
        ArsFoldState value = this.mFoldState.getValue();
        ArsFaultState value2 = this.mFaultState.getValue();
        int arsPosition = getArsPosition();
        LogUtils.i(TAG, "check ars unfold task, foldState: " + value + ", errorState: " + value2 + ", position: " + arsPosition);
        if (value2 == ArsFaultState.NO_FAULT && value == ArsFoldState.UNFOLDED && isPositionFullUnfolded(arsPosition)) {
            return;
        }
        if (value == ArsFoldState.MOVING) {
            this.mFoldState.postValue(ArsFoldState.FOLDED);
        }
        if (value2 == ArsFaultState.FAULT) {
            startReset(true);
            return;
        }
        LogUtils.i(TAG, "handleUnfoldError showToast");
        NotificationHelper.getInstance().showToast(R.string.ars_control_error_operation);
    }

    private void hideDialogs() {
        ThreadUtils.runOnMainThread(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$ArsViewModel$EsLeDp5BFaZqX3T7ISKdjIHJ_4M
            @Override // java.lang.Runnable
            public final void run() {
                ArsViewModel.this.lambda$hideDialogs$17$ArsViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$hideDialogs$17$ArsViewModel() {
        WeakReference<XDialog> weakReference = this.mFoldConfirmDialog;
        XDialog xDialog = weakReference != null ? weakReference.get() : null;
        if (xDialog != null && xDialog.isShowing()) {
            xDialog.dismiss();
        }
        WeakReference<XDialog> weakReference2 = this.mUnfoldConfirmDialog;
        XDialog xDialog2 = weakReference2 != null ? weakReference2.get() : null;
        if (xDialog2 != null && xDialog2.isShowing()) {
            xDialog2.dismiss();
        }
        WeakReference<XDialog> weakReference3 = this.mResetConfirmDialog;
        XDialog xDialog3 = weakReference3 != null ? weakReference3.get() : null;
        if (xDialog3 != null && xDialog3.isShowing()) {
            xDialog3.dismiss();
        }
        WeakReference<XDialog> weakReference4 = this.mInitConfirmDialog;
        XDialog xDialog4 = weakReference4 != null ? weakReference4.get() : null;
        if (xDialog4 == null || !xDialog4.isShowing()) {
            return;
        }
        xDialog4.dismiss();
    }
}
