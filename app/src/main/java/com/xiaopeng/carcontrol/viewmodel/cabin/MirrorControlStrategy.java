package com.xiaopeng.carcontrol.viewmodel.cabin;

import android.os.Handler;
import android.util.SparseArray;
import com.xiaopeng.carcontrol.CarConfig;
import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.lang.NoConcurrenceRunnable;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy;
import com.xiaopeng.carcontrol.viewmodel.strategy.Direction;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class MirrorControlStrategy extends AbstractSWCStrategy {
    private static final long MIRROR_CONTROL_INTERVAL = 50;
    private static final int MIRROR_CONTROL_TIMES = 2;
    private static final int MIRROR_SIDE_LEFT = 1;
    private static final int MIRROR_SIDE_RIGHT = 2;
    private static final String TAG = "MirrorControlStrategy";
    private volatile MirrorReverseState mReverseState;
    private final Handler mMirrorAdjustHandler = ThreadUtils.getHandler(0);
    private final SparseArray<MirrorControlTaskOld> mTaskCacheOld = new SparseArray<>();
    private final NoConcurrenceRunnable mDelayResetUserControlTask = new NoConcurrenceRunnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.MirrorControlStrategy.1
        @Override // com.xiaopeng.carcontrol.lang.NoConcurrenceRunnable
        public void run() {
            MirrorControlStrategy.this.updateUserControlFlag(true);
            if (MirrorControlStrategy.this.mTaskCacheOld.size() > 0 || MirrorControlStrategy.this.isNewSteer) {
                return;
            }
            MirrorControlStrategy.this.updateUserControlFlag(false);
        }
    };
    protected final IBcmController mBcmController = (IBcmController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_BCM_SERVICE);
    private final boolean isNewSteer = FunctionModel.getInstance().isNewSteerKey();
    private final boolean isSupportSTWVR = CarBaseConfig.getInstance().isSupportSTWVR();

    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    protected String getScene() {
        return "key_xp_mirror_flag";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    public void control(Direction direction) {
        if (this.mReverseState == MirrorReverseState.Reversing || this.mReverseState == MirrorReverseState.Recovering) {
            LogUtils.e(TAG, "control: can not control mirror when " + this.mReverseState.name(), false);
        } else {
            super.control(direction);
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    protected void startControl(Direction direction) {
        controlMirrorMove(direction.isLeft(), 1, convertDirection(direction));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    protected void pendingControl(Direction direction) {
        controlMirrorMove(direction.isLeft(), 2, convertDirection(direction));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    protected void stopControl(Direction direction) {
        controlMirrorMove(direction.isLeft(), 3, convertDirection(direction));
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    public boolean onKey(int keyCode, int action) {
        if (!this.isSupportSTWVR) {
            return this.isNewSteer ? super.onKey(keyCode, action) : handleOldDownKeyEvent(keyCode, action);
        } else if (keyCode == 1000) {
            handleVerKeyEvent(Direction.LU, action);
            return true;
        } else if (keyCode == 1001) {
            handleVerKeyEvent(Direction.LD, action);
            return true;
        } else if (keyCode == 1011) {
            handleVerKeyEvent(Direction.RU, action);
            return true;
        } else if (keyCode == 1012) {
            handleVerKeyEvent(Direction.RD, action);
            return true;
        } else if (keyCode == 1222) {
            handleHorKeyEvent(Direction.LL, action);
            return true;
        } else if (keyCode == 1223) {
            handleHorKeyEvent(Direction.LR, action);
            return true;
        } else if (keyCode == 1226) {
            handleHorKeyEvent(Direction.RL, action);
            return true;
        } else if (keyCode != 1227) {
            return false;
        } else {
            handleHorKeyEvent(Direction.RR, action);
            return true;
        }
    }

    public boolean onKeyIms(int keyCode, int action) {
        if (keyCode == 1000) {
            handleImsVerKeyEvent(Direction.LU, action);
            return true;
        } else if (keyCode == 1001) {
            handleImsVerKeyEvent(Direction.LD, action);
            return true;
        } else if (keyCode == 1011) {
            handleImsVerKeyEvent(Direction.RU, action);
            return true;
        } else if (keyCode != 1012) {
            return false;
        } else {
            handleImsVerKeyEvent(Direction.RD, action);
            return true;
        }
    }

    private void handleVerKeyEvent(Direction direction, int action) {
        if (action != 0) {
            return;
        }
        startControl(direction);
        this.mMirrorAdjustHandler.postDelayed(this.mDelayResetUserControlTask.newSession(), 1500L);
        updateUserControlFlag(true);
    }

    private void handleImsVerKeyEvent(Direction direction, int action) {
        if (action == 0 && convertImsDirection(direction) != 0) {
            this.mBcmController.setImsVisionCtrl(1, convertImsDirection(direction));
        }
    }

    private void handleHorKeyEvent(Direction direction, int action) {
        if (action == 0) {
            control(direction);
        } else if (action == 1) {
            cancel(direction);
        }
    }

    private boolean handleOldDownKeyEvent(int keyCode, int action) {
        if (action != 0) {
            return false;
        }
        LogUtils.d(TAG, "handleOldDownKeyEvent::keyCode = " + keyCode, false);
        switch (keyCode) {
            case 1000:
                controlMirrorMoveOld(true, MirrorDirection.Up, false);
                break;
            case 1001:
                controlMirrorMoveOld(true, MirrorDirection.Down, false);
                break;
            case 1002:
                controlMirrorMoveOld(true, MirrorDirection.Left, false);
                break;
            case 1003:
                controlMirrorMoveOld(true, MirrorDirection.Right, false);
                break;
            default:
                switch (keyCode) {
                    case 1011:
                        controlMirrorMoveOld(false, MirrorDirection.Up, false);
                        break;
                    case 1012:
                        controlMirrorMoveOld(false, MirrorDirection.Down, false);
                        break;
                    case 1013:
                        controlMirrorMoveOld(false, MirrorDirection.Left, false);
                        break;
                    case 1014:
                        controlMirrorMoveOld(false, MirrorDirection.Right, false);
                        break;
                    default:
                        switch (keyCode) {
                            case 1020:
                                controlMirrorMoveOld(true, MirrorDirection.Up, true);
                                break;
                            case 1021:
                                controlMirrorMoveOld(true, MirrorDirection.Down, true);
                                break;
                            case CarConfig.KEYCODE_LONG_LEFT_LEFT /* 1022 */:
                                controlMirrorMoveOld(true, MirrorDirection.Left, true);
                                break;
                            case 1023:
                                controlMirrorMoveOld(true, MirrorDirection.Right, true);
                                break;
                            default:
                                switch (keyCode) {
                                    case CarConfig.KEYCODE_LONG_RIGHT_UP /* 1031 */:
                                        controlMirrorMoveOld(false, MirrorDirection.Up, true);
                                        break;
                                    case CarConfig.KEYCODE_LONG_RIGHT_DOWN /* 1032 */:
                                        controlMirrorMoveOld(false, MirrorDirection.Down, true);
                                        break;
                                    case CarConfig.KEYCODE_LONG_RIGHT_LEFT /* 1033 */:
                                        controlMirrorMoveOld(false, MirrorDirection.Left, true);
                                        break;
                                    case CarConfig.KEYCODE_LONG_RIGHT_RIGHT /* 1034 */:
                                        controlMirrorMoveOld(false, MirrorDirection.Right, true);
                                        break;
                                    default:
                                        return false;
                                }
                        }
                }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.cabin.MirrorControlStrategy$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction;

        static {
            int[] iArr = new int[Direction.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction = iArr;
            try {
                iArr[Direction.LL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.RL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.LR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.RR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.LU.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.RU.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.LD.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.RD.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private MirrorDirection convertDirection(Direction direction) {
        switch (AnonymousClass2.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[direction.ordinal()]) {
            case 1:
            case 2:
                return MirrorDirection.Left;
            case 3:
            case 4:
                return MirrorDirection.Right;
            case 5:
            case 6:
                return MirrorDirection.Up;
            case 7:
            case 8:
                return MirrorDirection.Down;
            default:
                return null;
        }
    }

    private int convertImsDirection(Direction direction) {
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[direction.ordinal()];
        if (i != 5) {
            if (i != 6) {
                if (i != 7) {
                    return i != 8 ? 0 : 2;
                }
                return 4;
            }
            return 1;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateReverseState(MirrorReverseState reverseState) {
        LogUtils.d(TAG, "updateReverseState:" + reverseState);
        this.mReverseState = reverseState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class MirrorControlTaskOld implements Runnable {
        private final MirrorDirection direction;
        private final boolean isLeft;
        private boolean isLongControl;
        private final int key;
        private boolean isStart = false;
        private int leftTime = 0;

        MirrorControlTaskOld(int key, boolean isLeft, MirrorDirection direction) {
            this.key = key;
            this.isLeft = isLeft;
            this.direction = direction;
        }

        void increase() {
            if (this.isStart) {
                this.leftTime += 2;
            }
        }

        void setLongControl(boolean isLongControl) {
            this.isLongControl = isLongControl;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.isStart) {
                MirrorControlStrategy.this.controlMirrorMove(this.isLeft, 1, this.direction);
                MirrorControlStrategy.this.mMirrorAdjustHandler.postDelayed(this, MirrorControlStrategy.MIRROR_CONTROL_INTERVAL);
                this.isStart = true;
                this.leftTime = 2;
                Object[] objArr = new Object[3];
                objArr[0] = this.isLeft ? "left" : "right";
                objArr[1] = this.direction;
                objArr[2] = 2;
                LogUtils.i(MirrorControlStrategy.TAG, String.format("MirrorControlTaskOld Start: side is %s, direction is %s, leftTime is %s", objArr), false);
                this.leftTime--;
                return;
            }
            int i = this.leftTime;
            if (i > 0) {
                Object[] objArr2 = new Object[3];
                objArr2[0] = this.isLeft ? "left" : "right";
                objArr2[1] = this.direction;
                objArr2[2] = Integer.valueOf(i);
                LogUtils.i(MirrorControlStrategy.TAG, String.format("MirrorControlTaskOld Pending: side is %s, direction is %s, leftTime is %s", objArr2));
                MirrorControlStrategy.this.controlMirrorMove(this.isLeft, 2, this.direction);
                MirrorControlStrategy.this.mMirrorAdjustHandler.postDelayed(this, MirrorControlStrategy.MIRROR_CONTROL_INTERVAL);
                this.leftTime--;
                return;
            }
            Object[] objArr3 = new Object[2];
            objArr3[0] = this.isLeft ? "left" : "right";
            objArr3[1] = this.direction;
            LogUtils.i(MirrorControlStrategy.TAG, String.format("MirrorControlTaskOld Finish: side is %s, direction is %s", objArr3));
            this.isStart = false;
            MirrorControlStrategy.this.mMirrorAdjustHandler.removeCallbacks(this);
            MirrorControlStrategy.this.mTaskCacheOld.remove(this.key);
            MirrorControlStrategy.this.controlMirrorMove(this.isLeft, 3, this.direction);
            MirrorControlStrategy.this.mMirrorAdjustHandler.postDelayed(MirrorControlStrategy.this.mDelayResetUserControlTask.newSession(), 1500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void controlMirrorMoveOld(boolean isLeft, final MirrorDirection direction, boolean isLongControl) {
        if (direction == null) {
            LogUtils.i(TAG, "controlMirrorMoveOld: direction is null");
            return;
        }
        int i = isLeft ? 1 : 2;
        final MirrorControlTaskOld mirrorControlTaskOld = this.mTaskCacheOld.get(i);
        Object[] objArr = new Object[3];
        objArr[0] = isLeft ? "left" : "right";
        objArr[1] = direction;
        objArr[2] = Boolean.valueOf(isLongControl);
        LogUtils.i(TAG, String.format("controlMirrorMoveOld: control mirror : side is %s, target direction is %s, long control is %s", objArr), false);
        if (mirrorControlTaskOld != null) {
            Object[] objArr2 = new Object[3];
            objArr2[0] = isLeft ? "left" : "right";
            objArr2[1] = mirrorControlTaskOld.direction;
            objArr2[2] = Boolean.valueOf(isLongControl);
            LogUtils.i(TAG, String.format("controlMirrorMoveOld: control task existed: side is %s, current direction is %s, long control is %s", objArr2), false);
            if (mirrorControlTaskOld.direction != direction) {
                Object[] objArr3 = new Object[3];
                objArr3[0] = mirrorControlTaskOld.isLeft ? "left" : "right";
                objArr3[1] = mirrorControlTaskOld.direction;
                objArr3[2] = Boolean.valueOf(mirrorControlTaskOld.isLongControl);
                LogUtils.i(TAG, String.format("controlMirrorMoveOld: different direction, stop current task: side is %s, target direction is %s, long control is %s", objArr3), false);
                this.mTaskCacheOld.remove(i);
                this.mMirrorAdjustHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.cabin.-$$Lambda$MirrorControlStrategy$VMyE70jk_GVVUZEqraHf4_htGbk
                    @Override // java.lang.Runnable
                    public final void run() {
                        MirrorControlStrategy.this.lambda$controlMirrorMoveOld$0$MirrorControlStrategy(mirrorControlTaskOld, direction);
                    }
                });
                MirrorControlTaskOld mirrorControlTaskOld2 = new MirrorControlTaskOld(i, isLeft, direction);
                mirrorControlTaskOld2.setLongControl(isLongControl);
                this.mMirrorAdjustHandler.post(mirrorControlTaskOld2);
                this.mTaskCacheOld.put(i, mirrorControlTaskOld2);
            } else {
                LogUtils.i(TAG, "controlMirrorMove: same direction, add pending time", false);
                mirrorControlTaskOld.setLongControl(isLongControl);
                mirrorControlTaskOld.increase();
            }
        } else {
            MirrorControlTaskOld mirrorControlTaskOld3 = new MirrorControlTaskOld(i, isLeft, direction);
            mirrorControlTaskOld3.setLongControl(isLongControl);
            this.mMirrorAdjustHandler.post(mirrorControlTaskOld3);
            this.mTaskCacheOld.put(i, mirrorControlTaskOld3);
        }
        this.mDelayResetUserControlTask.cancel();
        updateUserControlFlag(true);
    }

    public /* synthetic */ void lambda$controlMirrorMoveOld$0$MirrorControlStrategy(final MirrorControlTaskOld finalTask, final MirrorDirection direction) {
        this.mMirrorAdjustHandler.removeCallbacks(finalTask);
        controlMirrorMove(finalTask.isLeft, 3, direction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void controlMirrorMove(boolean isLeft, int controlType, MirrorDirection direction) {
        if (this.mReverseState == MirrorReverseState.Reversing || this.mReverseState == MirrorReverseState.Recovering) {
            LogUtils.e(TAG, "controlMirrorMove: can not control mirror when " + this.mReverseState.name(), false);
            return;
        }
        try {
            if (isLeft) {
                this.mBcmController.controlLeftMirrorMove(controlType, direction.toBcmDirection());
            } else {
                this.mBcmController.controlRightMirrorMove(controlType, direction.toBcmDirection());
            }
            if (this.isNewSteer) {
                Thread.sleep(MIRROR_CONTROL_INTERVAL);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, (String) null, e);
        }
    }
}
