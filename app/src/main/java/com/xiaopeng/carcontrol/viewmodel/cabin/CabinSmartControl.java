package com.xiaopeng.carcontrol.viewmodel.cabin;

import android.os.Handler;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuResponse;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public final class CabinSmartControl {
    private static final String TAG = "CabinSmartControl";
    private boolean mCurrentPrevent;
    private Handler mHandler;
    private IMirrorViewModel mMirrorViewModel;
    private Runnable mPreventRunnable;
    private IWindowDoorViewModel mWindowViewModel;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static CabinSmartControl sInstance = new CabinSmartControl();

        private SingleHolder() {
        }
    }

    public static CabinSmartControl getInstance() {
        return SingleHolder.sInstance;
    }

    private CabinSmartControl() {
        this.mPreventRunnable = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.CabinSmartControl.1
            @Override // java.lang.Runnable
            public void run() {
                if (CabinSmartControl.this.mWindowViewModel.getLeftSdcSysRunningState() == 6 || CabinSmartControl.this.mWindowViewModel.getRightSdcSysRunningState() == 6) {
                    CabinSmartControl.this.mCurrentPrevent = true;
                    SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.sdc_prevent_playing_tts));
                    CabinSmartControl.this.mHandler.postDelayed(this, 30000L);
                    return;
                }
                CabinSmartControl.this.mCurrentPrevent = false;
            }
        };
        this.mMirrorViewModel = (IMirrorViewModel) ViewModelManager.getInstance().getViewModelImpl(IMirrorViewModel.class);
        this.mWindowViewModel = (IWindowDoorViewModel) ViewModelManager.getInstance().getViewModelImpl(IWindowDoorViewModel.class);
        Handler handler = ThreadUtils.getHandler(0);
        this.mHandler = handler;
        handler.post(this.mPreventRunnable);
    }

    public void handleReverseMirror(boolean isRearGear) {
        if (isRearGear) {
            this.mMirrorViewModel.reverseMirror();
        } else {
            this.mMirrorViewModel.recoverMirror();
        }
    }

    public void setWinHighSpdSw(boolean enable) {
        this.mWindowViewModel.setHighSpdCloseWin(enable);
    }

    public void handleHighSpeed() {
        if (this.mWindowViewModel.isHighSpdCloseWinEnabled() && FunctionModel.getInstance().isHighSpdCloseWinFuncAllowed()) {
            if (!this.mWindowViewModel.isWindowOpened()) {
                LogUtils.i(TAG, "All windows are closed, no need to close window for high speed", false);
            } else if (this.mWindowViewModel.isWindowLockActive()) {
                LogUtils.w(TAG, "Window lock is active, do not close win for high speed", false);
                if (FunctionModel.getInstance().isWinLockTipAllowed()) {
                    NotificationHelper.getInstance().showToast(R.string.high_spd_close_win_failed_hint);
                    FunctionModel.getInstance().setWinLockTipTs(System.currentTimeMillis());
                }
            } else {
                SpeechHelper.getInstance().speak(ResUtils.getString(R.string.high_spd_close_win_tts_hint));
                NotificationHelper.getInstance().showToastLonger(R.string.high_spd_close_win_hint_new);
                ThreadUtils.postDelayed(0, new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$CabinSmartControl$9psaBmiP2q9yUkzu8kmLEBMO_SE
                    @Override // java.lang.Runnable
                    public final void run() {
                        CabinSmartControl.this.lambda$handleHighSpeed$0$CabinSmartControl();
                    }
                }, 8000L);
            }
        }
    }

    public /* synthetic */ void lambda$handleHighSpeed$0$CabinSmartControl() {
        this.mWindowViewModel.controlWindowAuto(false);
        FunctionModel.getInstance().setHighSpdCloseWinTs(System.currentTimeMillis());
    }

    public void syncAccountMirrorPos(String mirrorData, boolean needMoveMirror) {
        this.mMirrorViewModel.syncAccountMirrorPos(mirrorData, needMoveMirror);
    }

    public void unlockTrunk() {
        if (CarBaseConfig.getInstance().isSupportElcTrunk()) {
            this.mWindowViewModel.controlRearTrunk(5);
        } else {
            this.mWindowViewModel.openRearTrunk();
        }
    }

    public void resetMirrorFoldState() {
        this.mMirrorViewModel.resetMirrorFoldState();
    }

    public boolean checkIfMirrorControlPanelAvailable() {
        ScuResponse accStateForMirror = ((IScuViewModel) ViewModelManager.getInstance().getViewModelImpl(IScuViewModel.class)).getAccStateForMirror();
        LogUtils.d(TAG, "AccState: " + accStateForMirror, false);
        if (accStateForMirror == ScuResponse.ON) {
            return false;
        }
        boolean isCruiseActive = ((IVcuViewModel) ViewModelManager.getInstance().getViewModelImpl(IVcuViewModel.class)).isCruiseActive();
        LogUtils.d(TAG, "CruiseState: " + isCruiseActive, false);
        return !isCruiseActive;
    }

    public void initTrunkState() {
        this.mWindowViewModel.initTrunkState();
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x003f, code lost:
        if (r3.mWindowViewModel.getRightSdcSysRunningState() == 6) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void updateSdcRunningState(boolean r4, int r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "CabinSmartControl"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7a
            r1.<init>()     // Catch: java.lang.Throwable -> L7a
            java.lang.String r2 = "updateSdcRunningState, left: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L7a
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch: java.lang.Throwable -> L7a
            java.lang.String r2 = ", state: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L7a
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch: java.lang.Throwable -> L7a
            java.lang.String r2 = "mCurrentPrevent: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L7a
            boolean r2 = r3.mCurrentPrevent     // Catch: java.lang.Throwable -> L7a
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L7a
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L7a
            r2 = 0
            com.xiaopeng.carcontrol.util.LogUtils.d(r0, r1, r2)     // Catch: java.lang.Throwable -> L7a
            r0 = 6
            r1 = 1
            if (r4 == 0) goto L45
            if (r5 != r0) goto L38
            r4 = r1
            goto L39
        L38:
            r4 = r2
        L39:
            com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel r5 = r3.mWindowViewModel     // Catch: java.lang.Throwable -> L7a
            int r5 = r5.getRightSdcSysRunningState()     // Catch: java.lang.Throwable -> L7a
            if (r5 != r0) goto L43
        L41:
            r5 = r1
            goto L53
        L43:
            r5 = r2
            goto L53
        L45:
            com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel r4 = r3.mWindowViewModel     // Catch: java.lang.Throwable -> L7a
            int r4 = r4.getLeftSdcSysRunningState()     // Catch: java.lang.Throwable -> L7a
            if (r4 != r0) goto L4f
            r4 = r1
            goto L50
        L4f:
            r4 = r2
        L50:
            if (r5 != r0) goto L43
            goto L41
        L53:
            if (r4 != 0) goto L57
            if (r5 == 0) goto L58
        L57:
            r2 = r1
        L58:
            boolean r4 = r3.mCurrentPrevent     // Catch: java.lang.Throwable -> L7a
            if (r2 != r4) goto L65
            java.lang.String r4 = "CabinSmartControl"
            java.lang.String r5 = "TargetPreventState == CurrentPreventState, do nothing"
            com.xiaopeng.carcontrol.util.LogUtils.i(r4, r5)     // Catch: java.lang.Throwable -> L7a
            monitor-exit(r3)
            return
        L65:
            r3.mCurrentPrevent = r2     // Catch: java.lang.Throwable -> L7a
            if (r2 == 0) goto L71
            android.os.Handler r4 = r3.mHandler     // Catch: java.lang.Throwable -> L7a
            java.lang.Runnable r5 = r3.mPreventRunnable     // Catch: java.lang.Throwable -> L7a
            r4.post(r5)     // Catch: java.lang.Throwable -> L7a
            goto L78
        L71:
            android.os.Handler r4 = r3.mHandler     // Catch: java.lang.Throwable -> L7a
            java.lang.Runnable r5 = r3.mPreventRunnable     // Catch: java.lang.Throwable -> L7a
            r4.removeCallbacks(r5)     // Catch: java.lang.Throwable -> L7a
        L78:
            monitor-exit(r3)
            return
        L7a:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.cabin.CabinSmartControl.updateSdcRunningState(boolean, int):void");
    }

    public void controlMirrorFold(boolean isFold) {
        this.mMirrorViewModel.controlMirror(isFold);
    }

    public void checkRecoverMirror(float speed) {
        this.mMirrorViewModel.checkRecoverMirror(speed);
    }

    public void resetRecoverMirrorFlag() {
        this.mMirrorViewModel.resetRecoverMirrorFlag();
    }
}
