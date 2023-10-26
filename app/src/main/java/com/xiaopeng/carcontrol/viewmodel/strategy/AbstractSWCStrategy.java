package com.xiaopeng.carcontrol.viewmodel.strategy;

import android.os.Handler;
import android.provider.Settings;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.CarConfig;
import com.xiaopeng.carcontrol.lang.SessionIndex;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.strategy.AbstractSWCStrategy;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public abstract class AbstractSWCStrategy {
    private static final int FLAG_ENTER = 1;
    private static final int FLAG_EXIT = 0;
    protected final ScheduledThreadPoolExecutor mThreadPool;
    private IUserControlListener mUserControlListener;
    protected final String TAG = getClass().getSimpleName();
    private final ConcurrentHashMap<Direction, ControlTask> mControlTaskCache = new ConcurrentHashMap<>();
    protected final Handler mHandler = ThreadUtils.getHandler(0);
    private final Runnable mDelayResetUserControlTask = new Runnable() { // from class: com.xiaopeng.carcontrol.viewmodel.strategy.-$$Lambda$AbstractSWCStrategy$1EGCqKXEtqrpNE4wWX4Ntx2NgE4
        @Override // java.lang.Runnable
        public final void run() {
            AbstractSWCStrategy.this.resetUserControlFlag();
        }
    };

    /* loaded from: classes2.dex */
    public interface IUserControlListener {
        void onUserControl(boolean isUserControl);
    }

    protected abstract String getScene();

    protected abstract void pendingControl(Direction direction);

    protected abstract void startControl(Direction direction);

    protected abstract void stopControl(Direction direction);

    public AbstractSWCStrategy() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        this.mThreadPool = scheduledThreadPoolExecutor;
        scheduledThreadPoolExecutor.setMaximumPoolSize(2);
        exitScene();
    }

    public final void setUserControlListener(IUserControlListener listener) {
        this.mUserControlListener = listener;
        updateUserControlFlag(false);
    }

    public boolean onKey(int keyCode, int action) {
        if (action == 0) {
            return handleDownKeyEvent(keyCode);
        }
        if (1 == action) {
            return handleUpKeyEvent(keyCode);
        }
        return false;
    }

    private boolean handleDownKeyEvent(int keyCode) {
        LogUtils.d(this.TAG, "handleDownKeyEvent::keyCode = " + keyCode, false);
        switch (keyCode) {
            case CarConfig.KEYCODE_NEW_LONG_LEFT_UP /* 1220 */:
                control(Direction.LU);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_LEFT_DOWN /* 1221 */:
                control(Direction.LD);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_LEFT_LEFT /* 1222 */:
                control(Direction.LL);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_LEFT_RIGHT /* 1223 */:
                control(Direction.LR);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_RIGHT_UP /* 1224 */:
                control(Direction.RU);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_RIGHT_DOWN /* 1225 */:
                control(Direction.RD);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_RIGHT_LEFT /* 1226 */:
                control(Direction.RL);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_RIGHT_RIGHT /* 1227 */:
                control(Direction.RR);
                return true;
            default:
                return false;
        }
    }

    private boolean handleUpKeyEvent(int keyCode) {
        LogUtils.d(this.TAG, "handleUpKeyEvent::keyCode = " + keyCode, false);
        switch (keyCode) {
            case CarConfig.KEYCODE_NEW_LONG_LEFT_UP /* 1220 */:
                cancel(Direction.LU);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_LEFT_DOWN /* 1221 */:
                cancel(Direction.LD);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_LEFT_LEFT /* 1222 */:
                cancel(Direction.LL);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_LEFT_RIGHT /* 1223 */:
                cancel(Direction.LR);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_RIGHT_UP /* 1224 */:
                cancel(Direction.RU);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_RIGHT_DOWN /* 1225 */:
                cancel(Direction.RD);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_RIGHT_LEFT /* 1226 */:
                cancel(Direction.RL);
                return true;
            case CarConfig.KEYCODE_NEW_LONG_RIGHT_RIGHT /* 1227 */:
                cancel(Direction.RR);
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void control(Direction direction) {
        cancelOppositeTask(direction);
        if (checkSameSideTaskRunning(direction)) {
            return;
        }
        ControlTask controlTask = this.mControlTaskCache.get(direction);
        LogUtils.d(this.TAG, "control direction:" + direction + ", task=" + controlTask, false);
        if (controlTask == null) {
            ControlTask controlTask2 = new ControlTask(direction);
            this.mThreadPool.execute(controlTask2);
            this.mControlTaskCache.put(direction, controlTask2);
            return;
        }
        controlTask.newSession();
        this.mThreadPool.execute(controlTask);
    }

    private void cancelOppositeTask(Direction direction) {
        Direction opposite = direction.opposite();
        ControlTask controlTask = opposite != null ? this.mControlTaskCache.get(opposite) : null;
        if (controlTask != null) {
            controlTask.stop();
        }
    }

    private boolean checkSameSideTaskRunning(Direction direction) {
        for (ControlTask controlTask : this.mControlTaskCache.values()) {
            if (controlTask != null && (controlTask.mDirection == direction || controlTask.mDirection.isLeft() == direction.isLeft())) {
                if (controlTask.isRunning()) {
                    LogUtils.d(this.TAG, "checkSameSideTaskRunning target direction:+" + direction + ", running direction:" + controlTask.mDirection);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void cancel(Direction direction) {
        ControlTask controlTask = this.mControlTaskCache.get(direction);
        LogUtils.d(this.TAG, "cancel direction:" + direction + ", task=" + controlTask, false);
        if (controlTask != null) {
            controlTask.stop();
        }
    }

    public final void cancel() {
        this.mControlTaskCache.forEach(new BiConsumer() { // from class: com.xiaopeng.carcontrol.viewmodel.strategy.-$$Lambda$AbstractSWCStrategy$2ePahiArdM_Ok2CGNIXaEiULAlc
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AbstractSWCStrategy.lambda$cancel$0((Direction) obj, (AbstractSWCStrategy.ControlTask) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$cancel$0(Direction direction, ControlTask task) {
        if (task.isRunning()) {
            task.stop();
        }
    }

    protected void onPendingHoldOn(Direction direction) {
        try {
            Thread.sleep(50L);
        } catch (Exception e) {
            LogUtils.e(this.TAG, "onPendingHoldOn", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void pendingHoldOn(Direction direction) {
        onPendingHoldOn(direction);
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

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateUserControlFlag(boolean flag) {
        IUserControlListener iUserControlListener = this.mUserControlListener;
        if (iUserControlListener != null) {
            iUserControlListener.onUserControl(flag);
        }
    }

    public final void enterScene() {
        Settings.System.putInt(App.getInstance().getContentResolver(), getScene(), 1);
    }

    public final void exitScene() {
        Settings.System.putInt(App.getInstance().getContentResolver(), getScene(), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class ControlTask implements Runnable {
        private final Direction mDirection;
        private volatile boolean isRunning = false;
        private SessionIndex mSessionIndex = new SessionIndex();
        private int mLastSession = -1;

        ControlTask(Direction direction) {
            this.mDirection = direction;
            newSession();
        }

        public void newSession() {
            this.mLastSession = this.mSessionIndex.increase();
        }

        @Override // java.lang.Runnable
        public void run() {
            AbstractSWCStrategy.this.mHandler.removeCallbacks(AbstractSWCStrategy.this.mDelayResetUserControlTask);
            this.isRunning = true;
            if (this.mSessionIndex.check(this.mLastSession)) {
                AbstractSWCStrategy.this.updateUserControlFlag(true);
                AbstractSWCStrategy.this.startControl(this.mDirection);
                AbstractSWCStrategy.this.pendingHoldOn(this.mDirection);
                while (this.mSessionIndex.check(this.mLastSession)) {
                    AbstractSWCStrategy.this.pendingControl(this.mDirection);
                    AbstractSWCStrategy.this.pendingHoldOn(this.mDirection);
                }
                AbstractSWCStrategy.this.stopControl(this.mDirection);
            }
            this.isRunning = false;
            AbstractSWCStrategy.this.mHandler.postDelayed(AbstractSWCStrategy.this.mDelayResetUserControlTask, 1500L);
        }

        boolean isRunning() {
            return this.isRunning && this.mSessionIndex.check(this.mLastSession);
        }

        void stop() {
            this.mSessionIndex.increase();
        }

        public String toString() {
            return "ControlTask{loop=" + this.isRunning + '}';
        }
    }
}
