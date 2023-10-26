package com.xiaopeng.carcontrol.viewmodel.cabin;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.provider.CarControl;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorMemoryStrategy;
import com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public abstract class MirrorBaseViewModel implements IMirrorViewModel {
    private static final float RECOVER_MIRROR_SPEED_THRESHOLD = 10.0f;
    private static final String TAG = "MirrorBaseViewModel";
    protected final IBcmController mBcmController;
    private ContentObserver mContentObserver;
    private final MirrorControlStrategy mControlStrategy;
    private final MirrorMemoryStrategy mMemoryStrategy;
    private final IVcuController mVcuController;
    private MirrorFoldState mMirrorFoldState = MirrorFoldState.Unfolded;
    private final Handler mMirrorAdjustHandler = ThreadUtils.getHandler(0);
    private boolean mShouldRecoverMirror = false;
    final IBcmController.Callback mBcmCallback = new IBcmController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel.1
        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onMirrorPosChanged(int[] position) {
            if (CarBaseConfig.getInstance().isSupportMirrorMemory()) {
                MirrorBaseViewModel.this.mMemoryStrategy.onMirrorPosChanged(position);
            }
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onMirrorAutoDownChanged(boolean enabled) {
            MirrorBaseViewModel.this.handleMirrorAutoDownChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onMirrorAutoFoldStateChanged(boolean enabled) {
            MirrorBaseViewModel.this.handleAutoFoldStateChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onMirrorReverseModeChanged(int mode, boolean needMove) {
            MirrorBaseViewModel.this.mMemoryStrategy.onMirrorReverseModeChanged(MirrorReverseMode.fromBcmMirrorState(mode));
            MirrorBaseViewModel.this.handleReverseModeChanged(MirrorReverseMode.fromBcmMirrorState(mode));
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onMirrorAdjust(int[] mirrorStates) {
            MirrorBaseViewModel.this.mMemoryStrategy.onMirrorAdjust(mirrorStates);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCmsObjectRecognizeSwChanged(boolean enabled) {
            MirrorBaseViewModel.this.handleCmsObjectRecognizeSwChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCmsReverseSwChanged(boolean enabled) {
            MirrorBaseViewModel.this.handleCmsReverseSwChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCmsTurnSwChanged(boolean enabled) {
            MirrorBaseViewModel.this.handleCmsTurnSwChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCmsHighSpdSwChanged(boolean enabled) {
            MirrorBaseViewModel.this.handleCmsHighSpdSwChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCmsLowSpdSwChanged(boolean enabled) {
            MirrorBaseViewModel.this.handleCmsLowSpdSwChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCmsAutoBrightSwChanged(boolean enabled) {
            MirrorBaseViewModel.this.handleCmsAutoBrightSwChanged(enabled);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCmsBrightChanged(int brightness) {
            MirrorBaseViewModel.this.handleCmsBrightChanged(brightness);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCmsViewAngleChanged(int viewAngle) {
            MirrorBaseViewModel.this.handleCmsViewAngleChanged(viewAngle);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onCmsPosChanged(float[] pos) {
            MirrorBaseViewModel.this.mMemoryStrategy.onCmsPosChanged(pos);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onRearScreenStateChanged(int state) {
            MirrorBaseViewModel.this.handleRearScreenStateChanged(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onImsAutoVisionSwChanged(boolean enable) {
            MirrorBaseViewModel.this.handleImsAutoVisionSwChanged(enable);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onImsBrightLevelChanged(int level) {
            MirrorBaseViewModel.this.handleImsBrightLevelChanged(level);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onImsModeChanged(int mode) {
            MirrorBaseViewModel.this.handleImsModeChanged(mode);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onImsSystemStChanged(int state) {
            MirrorBaseViewModel.this.handleImsSystemStChanged(state);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onImsVisionAngleLevelChanged(int level) {
            MirrorBaseViewModel.this.handleImsVisionAngleLevelChanged(level);
        }

        @Override // com.xiaopeng.carcontrol.carmanager.controller.IBcmController.Callback
        public void onImsVisionVerticalLevelChanged(int level) {
            MirrorBaseViewModel.this.handleImsVisionVerticalLevelChanged(level);
        }
    };

    protected long getMirrorWaitTimeout() {
        return 3000L;
    }

    protected abstract void handleAutoFoldStateChanged(boolean enabled);

    protected abstract void handleCmsAutoBrightSwChanged(boolean enabled);

    protected abstract void handleCmsBrightChanged(int brightness);

    protected abstract void handleCmsHighSpdSwChanged(boolean enabled);

    protected abstract void handleCmsLowSpdSwChanged(boolean enabled);

    protected abstract void handleCmsObjectRecognizeSwChanged(boolean enabled);

    protected abstract void handleCmsReverseSwChanged(boolean enabled);

    protected abstract void handleCmsTurnSwChanged(boolean enabled);

    protected abstract void handleCmsViewAngleChanged(int angle);

    protected abstract void handleImsAutoVisionSwChanged(boolean enabled);

    protected abstract void handleImsBrightLevelChanged(int level);

    protected abstract void handleImsModeChanged(int mode);

    protected abstract void handleImsSystemStChanged(int status);

    protected abstract void handleImsVisionAngleLevelChanged(int level);

    protected abstract void handleImsVisionVerticalLevelChanged(int level);

    protected abstract void handleMirrorAutoDownChanged(boolean enabled);

    protected abstract void handleRearScreenStateChanged(int state);

    protected abstract void handleReverseModeChanged(MirrorReverseMode mode);

    /* JADX INFO: Access modifiers changed from: package-private */
    public MirrorBaseViewModel() {
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        final MirrorMemoryStrategy mirrorMemoryStrategy = new MirrorMemoryStrategy();
        this.mMemoryStrategy = mirrorMemoryStrategy;
        final MirrorControlStrategy mirrorControlStrategy = new MirrorControlStrategy();
        this.mControlStrategy = mirrorControlStrategy;
        mirrorMemoryStrategy.setReverseStateListener(new MirrorMemoryStrategy.IMirrorReverseStateListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$XEZO-uP9ZlEXTICbqr8Jrbv8ztI
            @Override // com.xiaopeng.carcontrol.viewmodel.cabin.MirrorMemoryStrategy.IMirrorReverseStateListener
            public final void updateReverseState(MirrorReverseState mirrorReverseState) {
                MirrorControlStrategy.this.updateReverseState(mirrorReverseState);
            }
        });
        mirrorControlStrategy.setUserControlListener(new AbstractSWCStrategy.IUserControlListener() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$-FJZ_dnfSeXF2srCmEe5FCYy7o8
            @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy.IUserControlListener
            public final void onUserControl(boolean z) {
                MirrorMemoryStrategy.this.updateUserAdjust(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void registerContentObserver() {
        if (App.isMainProcess()) {
            return;
        }
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(this.mMirrorAdjustHandler) { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.MirrorBaseViewModel.2
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    LogUtils.d(MirrorBaseViewModel.TAG, "onChange: " + uri);
                    String lastPathSegment = uri.getLastPathSegment();
                    if (lastPathSegment != null && CarControl.Quick.MIRROR_FOLD_STATE.equals(lastPathSegment)) {
                        MirrorBaseViewModel.this.handleFoldStateChanged(MirrorFoldState.fromValue(MirrorBaseViewModel.this.getIntValue(lastPathSegment)));
                    }
                }
            };
        }
        LogUtils.d(TAG, "registerContentObserver");
        App.getInstance().getContentResolver().registerContentObserver(CarControl.Quick.CONTENT_URI, true, this.mContentObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getIntValue(String key) {
        return CarControl.Quick.getInt(App.getInstance().getContentResolver(), key, 3);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean checkCurPos() {
        return this.mMemoryStrategy.checkCurPos();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void controlMirror(final boolean isFold) {
        if (App.isMainProcess()) {
            if (isFold && this.mVcuController.getCarSpeed() >= 3.0f) {
                NotificationHelper.getInstance().showToast(R.string.mirror_state_unable_with_running);
                QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_UNFOLD_MIRROR_INT, this.mMirrorFoldState);
                QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_FOLD_MIRROR_INT, this.mMirrorFoldState);
                return;
            }
            this.mBcmController.controlMirror(isFold);
            LogUtils.d(TAG, (isFold ? "foldMirror" : "unfoldMirror") + " start", false);
            handleFoldStateChanged(MirrorFoldState.Middle);
            ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$MirrorBaseViewModel$JskBPZDVJdmLmp6jjJgBwFjARis
                @Override // java.lang.Runnable
                public final void run() {
                    MirrorBaseViewModel.this.lambda$controlMirror$0$MirrorBaseViewModel(isFold);
                }
            }, getMirrorWaitTimeout());
            return;
        }
        CarControl.Quick.putInt(App.getInstance().getContentResolver(), CarControl.Quick.MIRROR_FOLD, isFold ? 1 : 2);
    }

    public /* synthetic */ void lambda$controlMirror$0$MirrorBaseViewModel(final boolean isFold) {
        handleFoldStateChanged(isFold ? MirrorFoldState.Folded : MirrorFoldState.Unfolded);
        LogUtils.d(TAG, (isFold ? "foldMirror" : "unfoldMirror") + " end", false);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public MirrorFoldState getMirrorFoldState() {
        if (App.isMainProcess()) {
            return this.mMirrorFoldState;
        }
        return MirrorFoldState.fromValue(CarControl.Quick.getInt(App.getInstance().getContentResolver(), CarControl.Quick.MIRROR_FOLD_STATE, MirrorFoldState.Unfolded.ordinal()));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void syncAccountMirrorPos(String mirrorPosition, boolean needMoveMirror) {
        this.mMemoryStrategy.syncAccountMirrorPos(mirrorPosition, needMoveMirror);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public MirrorReverseMode getReverseMirrorMode() {
        return MirrorReverseMode.fromBcmMirrorState(this.mBcmController.getReverseMirrorMode());
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setReverseMirrorMode(int mode) {
        if (!App.isMainProcess()) {
            LogUtils.w(TAG, "Can not set Mirror reverse mode in none main process");
        } else {
            this.mBcmController.setReverseMirrorMode(mode);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setLMReverseSw(boolean on) {
        if (!App.isMainProcess()) {
            LogUtils.w(TAG, "Can not set Mirror reverse mode in none main process");
            return;
        }
        int reverseMirrorMode = this.mBcmController.getReverseMirrorMode();
        boolean isUserAdjusting = this.mMemoryStrategy.isUserAdjusting();
        LogUtils.i(TAG, "setLMReverseSw: " + on + ", userAdjusting: " + isUserAdjusting);
        if (on) {
            if (reverseMirrorMode == 0) {
                this.mBcmController.setReverseMirrorMode(1, !isUserAdjusting);
            } else if (reverseMirrorMode == 2) {
                this.mBcmController.setReverseMirrorMode(3, !isUserAdjusting);
            }
        } else if (reverseMirrorMode == 3) {
            this.mBcmController.setReverseMirrorMode(2, !isUserAdjusting);
        } else if (reverseMirrorMode == 1) {
            this.mBcmController.setReverseMirrorMode(0, !isUserAdjusting);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setRMReverseSw(boolean on) {
        if (!App.isMainProcess()) {
            LogUtils.w(TAG, "Can not set Mirror reverse mode in none main process");
            return;
        }
        int reverseMirrorMode = this.mBcmController.getReverseMirrorMode();
        boolean isUserAdjusting = this.mMemoryStrategy.isUserAdjusting();
        LogUtils.i(TAG, "setRMReverseSw: " + on + ", userAdjusting: " + isUserAdjusting);
        if (on) {
            if (reverseMirrorMode == 0) {
                this.mBcmController.setReverseMirrorMode(2, !isUserAdjusting);
            } else if (reverseMirrorMode == 1) {
                this.mBcmController.setReverseMirrorMode(3, !isUserAdjusting);
            }
        } else if (reverseMirrorMode == 3) {
            this.mBcmController.setReverseMirrorMode(1, !isUserAdjusting);
        } else if (reverseMirrorMode == 2) {
            this.mBcmController.setReverseMirrorMode(0, !isUserAdjusting);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean getLMReverseSw() {
        int reverseMirrorMode = this.mBcmController.getReverseMirrorMode();
        return reverseMirrorMode == 3 || reverseMirrorMode == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean getRMReverseSw() {
        int reverseMirrorMode = this.mBcmController.getReverseMirrorMode();
        return reverseMirrorMode == 3 || reverseMirrorMode == 2;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean shouldReverseMirror() {
        return getReverseMirrorMode() != MirrorReverseMode.Off;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void reverseMirror() {
        this.mMemoryStrategy.reverseMirror();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void recoverMirror() {
        this.mMemoryStrategy.recoverMirror();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean isMirrorAutoDown() {
        return this.mBcmController.isMirrorAutoDown();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setMirrorAutoDown(boolean enable) {
        this.mBcmController.setMirrorAutoDown(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean isMirrorAutoFoldEnable() {
        return this.mBcmController.isMirrorAutoFoldEnable();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setMirrorAutoFoldEnable(boolean enable) {
        this.mBcmController.setMirrorAutoFoldEnable(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void checkRecoverMirror(float speed) {
        if (getReverseMirrorMode() == MirrorReverseMode.Off || !BaseFeatureOption.getInstance().shouldRecoverMirror() || !this.mShouldRecoverMirror || speed <= RECOVER_MIRROR_SPEED_THRESHOLD) {
            return;
        }
        LogUtils.i(TAG, "Recover mirror position as speed > 10km/h", false);
        this.mMemoryStrategy.recoverMirror();
        this.mShouldRecoverMirror = false;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void resetRecoverMirrorFlag() {
        this.mShouldRecoverMirror = true;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setCmsViewRecovery(boolean enable) {
        this.mBcmController.setCmsViewRecovery(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setCmsObjectRecognizeSw(boolean enable) {
        this.mBcmController.setCmsObjectRecognizeSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean getCmsObjectRecognizeSw() {
        return this.mBcmController.getCmsObjectRecognizeSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setCmsReverseAssistSw(boolean enable) {
        this.mBcmController.setCmsReverseAssistSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean getCmsReverseAssistSw() {
        return this.mBcmController.getCmsReverseAssistSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setCmsTurnAssistSw(boolean enable) {
        this.mBcmController.setCmsTurnAssistSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean getCmsTurnAssistSw() {
        return this.mBcmController.getCmsTurnAssistSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setCmsHighSpdAssistSw(boolean enable) {
        this.mBcmController.setCmsHighSpdAssistSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean getCmsHighSpdAssistSw() {
        return this.mBcmController.getCmsHighSpdAssistSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setCmsLowSpdAssistSw(boolean enable) {
        this.mBcmController.setCmsLowSpdAssistSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean getCmsLowSpdAssistSw() {
        return this.mBcmController.getCmsLowSpdAssistSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setCmsAutoBrightSw(boolean enable) {
        this.mBcmController.setCmsAutoBrightSw(enable);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean getCmsAutoBrightSw() {
        return this.mBcmController.getCmsAutoBrightSw();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setCmsBright(int brightness) {
        this.mBcmController.setCmsBright(brightness);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public int getCmsBright() {
        return this.mBcmController.getCmsBright();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setCmsViewAngle(int viewAngle) {
        this.mBcmController.setCmsViewAngle(viewAngle);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public int getCmsViewAngle() {
        return this.mBcmController.getCmsViewAngle();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setImsAutoVisionSw(boolean on) {
        this.mBcmController.setImsAutoVisionSw(on ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean getImsAutoVisionSw() {
        return this.mBcmController.getImsAutoVisionSw() == 1;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setImsBrightLevel(int level) {
        this.mBcmController.setImsBrightLevel(level);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public int getImsBrightLevel() {
        return this.mBcmController.getImsBrightLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setImsMode(int mode) {
        this.mBcmController.setImsMode(mode, true);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public int getImsMode() {
        return this.mBcmController.getImsMode();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public int getImsSystemSt() {
        return this.mBcmController.getImsSystemSt();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void setImsVisionCtrl(int control, int direction) {
        this.mBcmController.setImsVisionCtrl(control, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public int getImsVisionAngleLevel() {
        return this.mBcmController.getImsVisionAngleLevel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public int getImsVisionVerticalLevel() {
        return this.mBcmController.getImsVisionVerticalLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleFoldStateChanged(MirrorFoldState state) {
        if (App.isMainProcess()) {
            this.mMirrorFoldState = state;
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_UNFOLD_MIRROR_INT, state);
            QuickSettingManager.getInstance().onSignalCallback(QuickSettingConstants.KEY_FOLD_MIRROR_INT, state);
            CarControl.Quick.putInt(App.getInstance().getContentResolver(), CarControl.Quick.MIRROR_FOLD_STATE, state.ordinal());
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void resetMirrorFoldState() {
        handleFoldStateChanged(MirrorFoldState.Unfolded);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void enterScene() {
        this.mControlStrategy.enterScene();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void exitScene() {
        this.mControlStrategy.exitScene();
    }

    public void cancelPendingTask() {
        this.mControlStrategy.cancel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void controlMirrorMoveOld(boolean isLeft, MirrorDirection direction, boolean isLongControl) {
        this.mControlStrategy.controlMirrorMoveOld(isLeft, direction, isLongControl);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean onKey(int keyCode, int action) {
        LogUtils.i(TAG, "onKey keyCode = " + keyCode + " action = " + action, false);
        return this.mControlStrategy.onKey(keyCode, action);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public boolean onKeyIms(int keyCode, int action) {
        LogUtils.i(TAG, "onKeyIms keyCode = " + keyCode + " action = " + action, false);
        return this.mControlStrategy.onKeyIms(keyCode, action);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel
    public void cancelAllControlMirror() {
        this.mControlStrategy.cancel();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void registerBusiness(String... keys) {
        this.mBcmController.registerBusiness(keys);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.IBaseViewModel
    public void unregisterBusiness(String... keys) {
        this.mBcmController.unregisterBusiness(keys);
    }
}
