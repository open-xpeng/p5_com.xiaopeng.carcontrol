package com.xiaopeng.carcontrol.viewmodel.cabin;

import android.os.Handler;
import com.xiaopeng.carcontrol.bean.Mirror;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.carmanager.controller.IMcuController;
import com.xiaopeng.carcontrol.carmanager.controller.IVcuController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.lang.TriggerJob;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class MirrorMemoryStrategy {
    private static final long MIRROR_ADJUST_TIMEOUT = 4000;
    private static final long MIRROR_SAVE_DELAY_TIME = 150;
    private final String TAG = getClass().getSimpleName();
    private boolean isWaitingExitRGear;
    private IBcmController mBcmController;
    private final Handler mHandler;
    private volatile boolean mIsUserAdjust;
    private final IMcuController mMcuController;
    protected final Mirror mMirrorMemoryPos;
    private int mPreviousGear;
    private final Runnable mRecoveringTask;
    private volatile MirrorReverseState mReverseState;
    private IMirrorReverseStateListener mReverseStateListener;
    private final Runnable mReversingTask;
    private final Runnable mSaveCmsPosTask;
    private TriggerJob mSaveNormalPositionJob;
    private TriggerJob mSaveReversePositionJob;
    protected final int[] mTargetReversePos;
    private IVcuController mVcuController;
    private final Runnable mWaitingExitRGearTask;

    /* loaded from: classes2.dex */
    public interface IMirrorReverseStateListener {
        void updateReverseState(MirrorReverseState reverseState);
    }

    public /* synthetic */ void lambda$new$0$MirrorMemoryStrategy() {
        updateReverseState(MirrorReverseState.Reversed);
    }

    public /* synthetic */ void lambda$new$1$MirrorMemoryStrategy() {
        updateReverseState(MirrorReverseState.Normal);
    }

    public /* synthetic */ void lambda$new$2$MirrorMemoryStrategy() {
        int[] rearViewMirrorsAdjustStates = this.mBcmController.getRearViewMirrorsAdjustStates();
        LogUtils.d(this.TAG, "execute mWaitingExitRGearTask: isWaitingExitRGear=" + this.isWaitingExitRGear + ", mPreviousGear=" + this.mPreviousGear + ", currentGear=" + this.mVcuController.getGearLevel() + ", mirrorAdjustState=" + Arrays.toString(rearViewMirrorsAdjustStates), false);
        if (!this.isWaitingExitRGear || this.mPreviousGear == 3 || this.mVcuController.getGearLevel() == 3 || isMirrorAdjustingByHw(rearViewMirrorsAdjustStates)) {
            return;
        }
        recoverMirror();
        this.isWaitingExitRGear = false;
    }

    public MirrorMemoryStrategy() {
        Mirror mirror = new Mirror();
        this.mMirrorMemoryPos = mirror;
        this.mTargetReversePos = new int[4];
        this.mReverseState = MirrorReverseState.Normal;
        this.mHandler = ThreadUtils.getHandler(0);
        this.mReversingTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$MirrorMemoryStrategy$Fi8fw1udhXGZjilCdaoBPsx9dGc
            @Override // java.lang.Runnable
            public final void run() {
                MirrorMemoryStrategy.this.lambda$new$0$MirrorMemoryStrategy();
            }
        };
        this.mRecoveringTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$MirrorMemoryStrategy$obckVdOctpVBtX82ZVM0aqMp88s
            @Override // java.lang.Runnable
            public final void run() {
                MirrorMemoryStrategy.this.lambda$new$1$MirrorMemoryStrategy();
            }
        };
        this.mSaveNormalPositionJob = new TriggerJob(150L, new $$Lambda$MirrorMemoryStrategy$dLS4rurNIgFe35N2QyHmCVM2JKI(this));
        this.mSaveReversePositionJob = new TriggerJob(150L, new $$Lambda$MirrorMemoryStrategy$oxHtKUU9xGPrUAj793zsvaxqiS8(this));
        this.mPreviousGear = 0;
        this.isWaitingExitRGear = false;
        this.mWaitingExitRGearTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$MirrorMemoryStrategy$JaGU2nKOyet921xucni8FsaDdMo
            @Override // java.lang.Runnable
            public final void run() {
                MirrorMemoryStrategy.this.lambda$new$2$MirrorMemoryStrategy();
            }
        };
        this.mSaveCmsPosTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$MirrorMemoryStrategy$MXu2xbcgdhrRugyI3jcHRkfgE_8
            @Override // java.lang.Runnable
            public final void run() {
                MirrorMemoryStrategy.this.saveCmsPos();
            }
        };
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mBcmController = (IBcmController) carClientWrapper.getController(CarClientWrapper.XP_BCM_SERVICE);
        this.mMcuController = (IMcuController) carClientWrapper.getController(CarClientWrapper.XP_MCU_SERVICE);
        IVcuController iVcuController = (IVcuController) carClientWrapper.getController(CarClientWrapper.XP_VCU_SERVICE);
        this.mVcuController = iVcuController;
        iVcuController.registerCallback(new IVcuController.Callback() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.MirrorMemoryStrategy.1
            {
                MirrorMemoryStrategy.this = this;
            }

            @Override // com.xiaopeng.carcontrol.carmanager.controller.IVcuController.Callback
            public void onGearChanged(int gear) {
                LogUtils.d(MirrorMemoryStrategy.this.TAG, "onGearChanged---->isWaitingExitRGear=" + MirrorMemoryStrategy.this.isWaitingExitRGear + ", mPreviousGear=" + MirrorMemoryStrategy.this.mPreviousGear + ", currentGear=" + gear, false);
                if (MirrorMemoryStrategy.this.mPreviousGear == 3 && gear != 3) {
                    MirrorMemoryStrategy.this.isWaitingExitRGear = true;
                } else if (gear == 3) {
                    MirrorMemoryStrategy.this.mHandler.removeCallbacks(MirrorMemoryStrategy.this.mWaitingExitRGearTask);
                    MirrorMemoryStrategy.this.isWaitingExitRGear = false;
                }
                MirrorMemoryStrategy.this.mPreviousGear = gear;
            }
        });
        this.mPreviousGear = this.mVcuController.getGearLevel();
        mirror.updateAllPosition(this.mBcmController.getMirrorSavedPos());
    }

    private MirrorReverseMode getReverseMode() {
        return MirrorReverseMode.fromBcmMirrorState(this.mBcmController.getReverseMirrorMode());
    }

    public void onMirrorReverseModeChanged(MirrorReverseMode mode) {
        LogUtils.d(this.TAG, "onMirrorReverseModeChanged: " + mode, false);
    }

    public void syncAccountMirrorPos(String mirrorPosition, boolean needMoveMirror) {
        if (mirrorPosition != null) {
            this.mSaveNormalPositionJob.untrigger();
            this.mSaveReversePositionJob.untrigger();
            LogUtils.i(this.TAG, "syncAccountMirrorPos: " + mirrorPosition, false);
            this.mMirrorMemoryPos.updateAllPosition(mirrorPosition);
            if (needMoveMirror) {
                this.mBcmController.setMirrorLocation(this.mMirrorMemoryPos.leftHPos, this.mMirrorMemoryPos.leftVPos, this.mMirrorMemoryPos.rightHPos, this.mMirrorMemoryPos.rightVPos);
            }
        }
    }

    public void reverseMirror() {
        this.mHandler.removeCallbacks(this.mReversingTask);
        this.mHandler.removeCallbacks(this.mRecoveringTask);
        updateTargetReversePos();
        LogUtils.d(this.TAG, String.format("reverseMirror with location: %1s, current reverseState=%3s", Arrays.toString(this.mTargetReversePos), this.mReverseState.name()), false);
        IBcmController iBcmController = this.mBcmController;
        int[] iArr = this.mTargetReversePos;
        iBcmController.setMirrorLocation(iArr[0], iArr[1], iArr[2], iArr[3]);
        updateReverseState(MirrorReverseState.Reversing);
        this.mHandler.postDelayed(this.mReversingTask, MIRROR_ADJUST_TIMEOUT);
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.cabin.MirrorMemoryStrategy$2 */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode;

        static {
            int[] iArr = new int[MirrorReverseMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode = iArr;
            try {
                iArr[MirrorReverseMode.Both.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[MirrorReverseMode.Left.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[MirrorReverseMode.Right.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[MirrorReverseMode.Off.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void updateTargetReversePos() {
        if (CarBaseConfig.getInstance().isSopStage()) {
            int i = AnonymousClass2.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[getReverseMode().ordinal()];
            if (i == 1) {
                this.mTargetReversePos[0] = this.mMirrorMemoryPos.leftHPosR;
                this.mTargetReversePos[1] = this.mMirrorMemoryPos.leftVPosR;
                this.mTargetReversePos[2] = this.mMirrorMemoryPos.rightHPosR;
                this.mTargetReversePos[3] = this.mMirrorMemoryPos.rightVPosR;
                return;
            } else if (i == 2) {
                this.mTargetReversePos[0] = this.mMirrorMemoryPos.leftHPosR;
                this.mTargetReversePos[1] = this.mMirrorMemoryPos.leftVPosR;
                this.mTargetReversePos[2] = this.mMirrorMemoryPos.rightHPos;
                this.mTargetReversePos[3] = this.mMirrorMemoryPos.rightVPos;
                return;
            } else if (i == 3) {
                this.mTargetReversePos[0] = this.mMirrorMemoryPos.leftHPos;
                this.mTargetReversePos[1] = this.mMirrorMemoryPos.leftVPos;
                this.mTargetReversePos[2] = this.mMirrorMemoryPos.rightHPosR;
                this.mTargetReversePos[3] = this.mMirrorMemoryPos.rightVPosR;
                return;
            } else {
                this.mTargetReversePos[0] = this.mMirrorMemoryPos.leftHPos;
                this.mTargetReversePos[1] = this.mMirrorMemoryPos.leftVPos;
                this.mTargetReversePos[2] = this.mMirrorMemoryPos.rightHPos;
                this.mTargetReversePos[3] = this.mMirrorMemoryPos.rightVPos;
                return;
            }
        }
        int i2 = AnonymousClass2.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[getReverseMode().ordinal()];
        if (i2 == 1) {
            this.mTargetReversePos[0] = this.mMirrorMemoryPos.leftHPosR;
            this.mTargetReversePos[1] = this.mMirrorMemoryPos.leftVPosR;
            this.mTargetReversePos[2] = this.mMirrorMemoryPos.rightHPosR;
            this.mTargetReversePos[3] = this.mMirrorMemoryPos.rightVPosR;
        } else if (i2 == 2) {
            this.mTargetReversePos[0] = this.mMirrorMemoryPos.leftHPosR;
            this.mTargetReversePos[1] = this.mMirrorMemoryPos.leftVPosR;
            this.mTargetReversePos[2] = this.mBcmController.getRightMirrorLRPos(true);
            this.mTargetReversePos[3] = this.mBcmController.getRightMirrorUDPos(true);
        } else if (i2 == 3) {
            this.mTargetReversePos[0] = this.mBcmController.getLeftMirrorLRPos(true);
            this.mTargetReversePos[1] = this.mBcmController.getLeftMirrorUDPos(true);
            this.mTargetReversePos[2] = this.mMirrorMemoryPos.rightHPosR;
            this.mTargetReversePos[3] = this.mMirrorMemoryPos.rightVPosR;
        } else {
            this.mTargetReversePos[0] = this.mBcmController.getLeftMirrorLRPos(true);
            this.mTargetReversePos[1] = this.mBcmController.getLeftMirrorUDPos(true);
            this.mTargetReversePos[2] = this.mBcmController.getRightMirrorLRPos(true);
            this.mTargetReversePos[3] = this.mBcmController.getRightMirrorUDPos(true);
        }
    }

    public void recoverMirror() {
        this.mHandler.removeCallbacks(this.mReversingTask);
        this.mHandler.removeCallbacks(this.mRecoveringTask);
        LogUtils.d(this.TAG, String.format("recoverMirror with location: [%1s, %2s, %3s, %4s], current reverseState=%5s", Integer.valueOf(this.mMirrorMemoryPos.leftHPos), Integer.valueOf(this.mMirrorMemoryPos.leftVPos), Integer.valueOf(this.mMirrorMemoryPos.rightHPos), Integer.valueOf(this.mMirrorMemoryPos.rightVPos), this.mReverseState.name()), false);
        this.mBcmController.setMirrorLocation(this.mMirrorMemoryPos.leftHPos, this.mMirrorMemoryPos.leftVPos, this.mMirrorMemoryPos.rightHPos, this.mMirrorMemoryPos.rightVPos);
        updateReverseState(MirrorReverseState.Recovering);
        this.mHandler.postDelayed(this.mRecoveringTask, MIRROR_ADJUST_TIMEOUT);
    }

    void recoverMirrorIfNeeded() {
        this.mHandler.removeCallbacks(this.mReversingTask);
        this.mHandler.removeCallbacks(this.mRecoveringTask);
        int[] mirrorPosition = this.mBcmController.getMirrorPosition(true);
        Mirror mirror = this.mMirrorMemoryPos;
        if (mirror == null || mirror.isEqualsToPosition(mirrorPosition)) {
            return;
        }
        LogUtils.i(this.TAG, String.format("recoverMirror with location: [%1s, %2s, %3s, %4s], current reverseState=%5s", Integer.valueOf(this.mMirrorMemoryPos.leftHPos), Integer.valueOf(this.mMirrorMemoryPos.leftVPos), Integer.valueOf(this.mMirrorMemoryPos.rightHPos), Integer.valueOf(this.mMirrorMemoryPos.rightVPos), this.mReverseState.name()), false);
        this.mBcmController.setMirrorLocation(this.mMirrorMemoryPos.leftHPos, this.mMirrorMemoryPos.leftVPos, this.mMirrorMemoryPos.rightHPos, this.mMirrorMemoryPos.rightVPos);
        updateReverseState(MirrorReverseState.Recovering);
        this.mHandler.postDelayed(this.mRecoveringTask, MIRROR_ADJUST_TIMEOUT);
    }

    public void onMirrorPosChanged(int[] position) {
        if (this.mMcuController.getIgStatusFromMcu() != 1) {
            LogUtils.w(this.TAG, "Current is not local ig on, do not save mirror position");
        } else if (position == null || position.length < 4) {
            LogUtils.w(this.TAG, "error mirror position array:" + Arrays.toString(position));
        } else {
            updateReverseStateByState(position);
            checkUpdateMirrorMemoryPosition(position[0], position[1], position[2], position[3]);
        }
    }

    public void onCmsPosChanged(float[] position) {
        ThreadUtils.removeRunnable(this.mSaveCmsPosTask);
        if (this.mIsUserAdjust) {
            ThreadUtils.postDelayed(this.mSaveCmsPosTask, 150L);
        }
    }

    public void saveCmsPos() {
        this.mBcmController.saveCmsLocation(this.mBcmController.getCmsLocation());
    }

    private void updateReverseState(MirrorReverseState reverseState) {
        this.mReverseState = reverseState;
        IMirrorReverseStateListener iMirrorReverseStateListener = this.mReverseStateListener;
        if (iMirrorReverseStateListener != null) {
            iMirrorReverseStateListener.updateReverseState(reverseState);
        }
    }

    private void updateReverseStateByState(int[] position) {
        if (this.mReverseState == MirrorReverseState.Reversing) {
            if (Mirror.isEquals(this.mTargetReversePos, position)) {
                LogUtils.i(this.TAG, "updateReverseState: Reversing->Reversed", false);
                updateReverseState(MirrorReverseState.Reversed);
            }
        } else if (this.mReverseState == MirrorReverseState.Recovering) {
            if (this.mMirrorMemoryPos.isEqualsToPosition(position)) {
                LogUtils.i(this.TAG, "updateReverseState: Recovering->Normal", false);
                updateReverseState(MirrorReverseState.Normal);
            }
        } else {
            boolean z = this.mVcuController.getGearLevel() == 3;
            if (this.mReverseState == MirrorReverseState.Reversed && !z) {
                LogUtils.i(this.TAG, "updateReverseState: Reversed->Normal, current not in rear gear, should reset Reversed state to Normal");
                updateReverseState(MirrorReverseState.Normal);
            } else if (this.mReverseState == MirrorReverseState.Normal && z) {
                LogUtils.i(this.TAG, "updateReverseState: Normal->Reversed, current in rear gear, should reset Reversed state to Reversed");
                updateReverseState(MirrorReverseState.Reversed);
            }
        }
    }

    public void updateUserAdjust(boolean isUserAdjust) {
        LogUtils.d(this.TAG, "updateUserAdjust: " + isUserAdjust);
        if (BaseFeatureOption.getInstance().isDelaySaveMirrorPos()) {
            this.mIsUserAdjust = isUserAdjust;
        } else if (isUserAdjust) {
        } else {
            ThreadUtils.execute(this.mVcuController.getGearLevel() == 3 ? new $$Lambda$MirrorMemoryStrategy$oxHtKUU9xGPrUAj793zsvaxqiS8(this) : new $$Lambda$MirrorMemoryStrategy$dLS4rurNIgFe35N2QyHmCVM2JKI(this));
        }
    }

    private void checkUpdateMirrorMemoryPosition(int leftHorPos, int leftVerPos, int rightHorPos, int rightVerPos) {
        if (BaseFeatureOption.getInstance().isDelaySaveMirrorPos()) {
            if (this.mReverseState == MirrorReverseState.Reversing || this.mReverseState == MirrorReverseState.Recovering) {
                LogUtils.i(this.TAG, "startUpdateMirrorMemoryPosition failed for ReverseState=" + this.mReverseState, false);
            } else if (!this.mIsUserAdjust) {
                LogUtils.i(this.TAG, "startUpdateMirrorMemoryPosition failed for mIsUserAdjust false", false);
            } else {
                boolean z = this.mVcuController.getGearLevel() == 3;
                if (this.mReverseState == MirrorReverseState.Normal && !z) {
                    LogUtils.d(this.TAG, String.format("startUpdateMirrorMemoryPosition: ReverseState=%1s, position=(%3s,%4s,%5s,%6s)", this.mReverseState, Integer.valueOf(leftHorPos), Integer.valueOf(leftVerPos), Integer.valueOf(rightHorPos), Integer.valueOf(rightVerPos)));
                    this.mSaveNormalPositionJob.trigger();
                }
                if (this.mReverseState == MirrorReverseState.Reversed && z) {
                    LogUtils.d(this.TAG, String.format("startUpdateMirrorMemoryPosition: ReverseState=%1s, position=(%3s,%4s,%5s,%6s)", this.mReverseState, Integer.valueOf(leftHorPos), Integer.valueOf(leftVerPos), Integer.valueOf(rightHorPos), Integer.valueOf(rightVerPos)));
                    this.mSaveReversePositionJob.trigger();
                }
            }
        }
    }

    public void saveNormalPos() {
        if (this.mVcuController.getGearLevel() == 3) {
            return;
        }
        int[] mirrorPosition = this.mBcmController.getMirrorPosition(true);
        if (isPosValid(mirrorPosition)) {
            this.mMirrorMemoryPos.updateNormalPosition(mirrorPosition);
            this.mBcmController.saveMirrorPos(this.mMirrorMemoryPos.toString());
        }
    }

    public void saveReversePos() {
        if (this.mVcuController.getGearLevel() == 3) {
            int[] mirrorPosition = this.mBcmController.getMirrorPosition(true);
            if (isPosValid(mirrorPosition)) {
                int i = AnonymousClass2.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[getReverseMode().ordinal()];
                if (i == 1) {
                    this.mMirrorMemoryPos.leftHPosR = mirrorPosition[0];
                    this.mMirrorMemoryPos.leftVPosR = mirrorPosition[1];
                    this.mMirrorMemoryPos.rightHPosR = mirrorPosition[2];
                    this.mMirrorMemoryPos.rightVPosR = mirrorPosition[3];
                } else if (i == 2) {
                    this.mMirrorMemoryPos.leftHPosR = mirrorPosition[0];
                    this.mMirrorMemoryPos.leftVPosR = mirrorPosition[1];
                } else if (i == 3) {
                    this.mMirrorMemoryPos.rightHPosR = mirrorPosition[2];
                    this.mMirrorMemoryPos.rightVPosR = mirrorPosition[3];
                }
                this.mBcmController.saveMirrorPos(this.mMirrorMemoryPos.toString());
            }
        }
    }

    private boolean isPosChanged(int prePos, int curPos) {
        return Math.abs(prePos - curPos) > 2;
    }

    private boolean isPosValid(int[] pos) {
        LogUtils.d(this.TAG, "isPosValid:" + Arrays.toString(pos));
        if (pos == null || pos.length < 4) {
            return false;
        }
        if (BaseFeatureOption.getInstance().needCheckValidMirrorPos()) {
            return (pos[0] == 0 && pos[1] == 0 && pos[2] == 0 && pos[3] == 0) ? false : true;
        }
        return true;
    }

    boolean isMirrorAdjustingByHw(int[] mirrorStates) {
        if (mirrorStates == null || mirrorStates.length < 4) {
            return false;
        }
        return (mirrorStates[0] == 0 && mirrorStates[1] == 0 && mirrorStates[2] == 0 && mirrorStates[3] == 0) ? false : true;
    }

    public void onMirrorAdjust(int[] mirrorStates) {
        if (isMirrorAdjustingByHw(mirrorStates)) {
            return;
        }
        if (CarBaseConfig.getInstance().isSupportMirrorDown()) {
            int igStatusFromMcu = this.mMcuController.getIgStatusFromMcu();
            int gearLevel = this.mVcuController.getGearLevel();
            LogUtils.d(this.TAG, "onMirrorAdjust: isWaitingExitRGear=" + this.isWaitingExitRGear + ", igState=" + igStatusFromMcu + ", gear=" + gearLevel);
            if (this.isWaitingExitRGear) {
                this.mHandler.removeCallbacks(this.mWaitingExitRGearTask);
                this.mHandler.postDelayed(this.mWaitingExitRGearTask, 400L);
            } else if (igStatusFromMcu != 1 || gearLevel == 3) {
            } else {
                saveNormalPos();
            }
        } else if (this.mReverseState == MirrorReverseState.Normal) {
            saveNormalPos();
        } else if (this.mReverseState == MirrorReverseState.Reversed) {
            saveReversePos();
        }
    }

    public boolean checkCurPos() {
        int[] mirrorPosition = this.mBcmController.getMirrorPosition(true);
        int gearLevel = this.mVcuController.getGearLevel();
        MirrorReverseMode reverseMode = getReverseMode();
        LogUtils.d(this.TAG, "checkCurPos: ReverseMode=" + reverseMode);
        LogUtils.d(this.TAG, "checkCurPos: curPos=" + Arrays.toString(mirrorPosition) + ", mMirrorMemoryPos=" + this.mMirrorMemoryPos);
        if (gearLevel == 3) {
            int i = AnonymousClass2.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$cabin$MirrorReverseMode[reverseMode.ordinal()];
            return Mirror.isEquals(mirrorPosition, i != 1 ? i != 2 ? i != 3 ? i != 4 ? mirrorPosition : new int[]{this.mMirrorMemoryPos.leftHPos, this.mMirrorMemoryPos.leftVPos, this.mMirrorMemoryPos.rightHPos, this.mMirrorMemoryPos.rightVPos} : new int[]{this.mMirrorMemoryPos.leftHPos, this.mMirrorMemoryPos.leftVPos, this.mMirrorMemoryPos.rightHPosR, this.mMirrorMemoryPos.rightVPosR} : new int[]{this.mMirrorMemoryPos.leftHPosR, this.mMirrorMemoryPos.leftVPosR, this.mMirrorMemoryPos.rightHPos, this.mMirrorMemoryPos.rightVPos} : new int[]{this.mMirrorMemoryPos.leftHPosR, this.mMirrorMemoryPos.leftVPosR, this.mMirrorMemoryPos.rightHPosR, this.mMirrorMemoryPos.rightVPosR});
        }
        return this.mMirrorMemoryPos.isEqualsToPosition(mirrorPosition);
    }

    public boolean isUserAdjusting() {
        return this.mIsUserAdjust;
    }

    public void setReverseStateListener(IMirrorReverseStateListener listener) {
        if (this.mReverseStateListener != null) {
            return;
        }
        this.mReverseStateListener = listener;
        listener.updateReverseState(this.mReverseState);
    }
}
