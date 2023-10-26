package com.xiaopeng.carcontrol.viewmodel.carsettings;

import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.carmanager.controller.IBcmController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.lang.NoConcurrenceRunnable;
import com.xiaopeng.carcontrol.viewmodel.carsettings.SteerControlStrategy;
import com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy;
import com.xiaopeng.carcontrol.viewmodel.strategy.Direction;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class SteerControlStrategy extends AbstractSWCStrategy {
    private static final long STEER_CONTROL_INTERVAL = 50;
    private static final int STEER_CONTROL_MAX_TIMES = 5;
    private static final int STEER_CONTROL_TIMES = 2;
    private final ConcurrentHashMap<Direction, ControlTask> mControlTaskCache = new ConcurrentHashMap<>();
    private final NoConcurrenceRunnable mDelayResetUserControlTask = new NoConcurrenceRunnable(new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.-$$Lambda$SteerControlStrategy$yVr9vwazwSOBrxgKry9btAyYFpY
        @Override // java.lang.Runnable
        public final void run() {
            SteerControlStrategy.this.resetUserControlFlag();
        }
    });
    private final IBcmController mBcmController = (IBcmController) CarClientWrapper.getInstance().getController(CarClientWrapper.XP_BCM_SERVICE);
    private final boolean isSupportSTWVR = CarBaseConfig.getInstance().isSupportSTWVR();

    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    protected String getScene() {
        return "key_xp_steering_flag";
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    protected void startControl(Direction direction) {
        realControl(1, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    protected void stopControl(Direction direction) {
        realControl(3, direction);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    protected void pendingControl(Direction direction) {
        realControl(2, direction);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy
    public void updateUserControlFlag(boolean isUserControl) {
        if (isUserControl) {
            return;
        }
        IBcmController iBcmController = this.mBcmController;
        iBcmController.saveSteerPos(iBcmController.getSteerPos());
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void realControl(int r5, com.xiaopeng.carcontrol.viewmodel.strategy.Direction r6) {
        /*
            r4 = this;
            java.lang.String r0 = r4.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "readControl---->controlType="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.String r2 = ", direction="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r1 = r1.toString()
            com.xiaopeng.carcontrol.util.LogUtils.d(r0, r1)
            int[] r0 = com.xiaopeng.carcontrol.viewmodel.carsettings.SteerControlStrategy.AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction
            int r1 = r6.ordinal()
            r0 = r0[r1]
            r1 = 2
            r2 = 1
            if (r0 == r2) goto L38
            if (r0 == r1) goto L39
            r3 = 3
            if (r0 == r3) goto L38
            r2 = 4
            if (r0 == r2) goto L39
            r1 = -1
            goto L39
        L38:
            r1 = r2
        L39:
            boolean r6 = r6.isLeft()
            if (r6 == 0) goto L45
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r6 = r4.mBcmController
            r6.controlVerSteer(r5, r1)
            goto L4a
        L45:
            com.xiaopeng.carcontrol.carmanager.controller.IBcmController r6 = r4.mBcmController
            r6.controlInOutSteer(r5, r1)
        L4a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.viewmodel.carsettings.SteerControlStrategy.realControl(int, com.xiaopeng.carcontrol.viewmodel.strategy.Direction):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.carcontrol.viewmodel.carsettings.SteerControlStrategy$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction;

        static {
            int[] iArr = new int[Direction.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction = iArr;
            try {
                iArr[Direction.LU.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.LD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.RU.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[Direction.RD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public boolean onSteerControl(int keyCode) {
        Direction direction;
        if (keyCode == 1000) {
            direction = Direction.LU;
        } else if (keyCode == 1001) {
            direction = Direction.LD;
        } else if (keyCode == 1011) {
            direction = Direction.RU;
        } else {
            direction = keyCode != 1012 ? null : Direction.RD;
        }
        if (direction != null) {
            if (this.isSupportSTWVR) {
                startControl(direction);
                this.mHandler.postDelayed(this.mDelayResetUserControlTask.newSession(), 1500L);
                updateUserControlFlag(true);
            } else {
                ControlTask controlTask = this.mControlTaskCache.get(direction);
                if (controlTask == null) {
                    stopTask(direction);
                    ControlTask controlTask2 = new ControlTask(direction);
                    controlTask2.setRunning();
                    this.mThreadPool.execute(controlTask2);
                    this.mControlTaskCache.put(direction, controlTask2);
                } else if (controlTask.isLooping()) {
                    controlTask.increase();
                } else if (!controlTask.isRunning()) {
                    stopTask(direction);
                    controlTask.setRunning();
                    this.mThreadPool.execute(controlTask);
                }
            }
            return true;
        }
        return false;
    }

    private void stopTask(Direction direction) {
        for (ControlTask controlTask : this.mControlTaskCache.values()) {
            if (controlTask.direction != direction) {
                int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$strategy$Direction[direction.ordinal()];
                if (i == 1 || i == 2) {
                    if (controlTask.direction.isLeft()) {
                        controlTask.stop();
                    }
                } else if (i == 3 || i == 4) {
                    if (!controlTask.direction.isLeft()) {
                        controlTask.stop();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetUserControlFlag() {
        boolean z;
        Iterator<ControlTask> it = this.mControlTaskCache.values().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            } else if (it.next().isRunning()) {
                z = false;
                break;
            }
        }
        if (z) {
            updateUserControlFlag(false);
        }
    }

    public void cancelControl() {
        this.mControlTaskCache.forEach(new BiConsumer() { // from class: com.xiaopeng.carcontrol.viewmodel.carsettings.-$$Lambda$SteerControlStrategy$Rccj5dluNTIwpOLLksegh8PzrL8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Direction direction = (Direction) obj;
                ((SteerControlStrategy.ControlTask) obj2).stop();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ControlTask implements Runnable {
        private final Direction direction;
        private final AtomicInteger leftTime = new AtomicInteger(2);
        private volatile boolean isRunning = false;

        ControlTask(Direction direction) {
            this.direction = direction;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void stop() {
            this.leftTime.set(0);
        }

        void increase() {
            if (this.leftTime.get() < 5) {
                this.leftTime.addAndGet(2);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.leftTime.set(2);
            SteerControlStrategy.this.updateUserControlFlag(true);
            SteerControlStrategy.this.startControl(this.direction);
            SteerControlStrategy.this.pendingHoldOn(this.direction);
            while (isLooping()) {
                this.leftTime.decrementAndGet();
                SteerControlStrategy.this.pendingControl(this.direction);
                SteerControlStrategy.this.pendingHoldOn(this.direction);
            }
            SteerControlStrategy.this.stopControl(this.direction);
            SteerControlStrategy.this.mHandler.postDelayed(SteerControlStrategy.this.mDelayResetUserControlTask.newSession(), 1500L);
            this.isRunning = false;
        }

        boolean isLooping() {
            return this.leftTime.get() > 0;
        }

        void setRunning() {
            this.isRunning = true;
        }

        boolean isRunning() {
            return this.isRunning;
        }
    }
}
