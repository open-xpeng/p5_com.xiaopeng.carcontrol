package com.xiaopeng.lib.bughunter.anr;

import android.content.Context;
import android.os.Build;
import android.view.Choreographer;
import com.xiaopeng.lib.bughunter.StackTraceCollector;

/* loaded from: classes2.dex */
public class Caton {
    static final long DEFAULT_COLLECT_INTERVAL = 1000;
    static MonitorMode DEFAULT_MODE = MonitorMode.LOOPER;
    static final long DEFAULT_THRESHOLD_TIME = 3000;
    static final long MAX_COLLECT_INTERVAL = 400;
    static final long MAX_THRESHOLD_TIME = 400;
    static final long MIN_COLLECT_INTERVAL = 200;
    static final long MIN_THRESHOLD_TIME = 200;
    private static volatile Caton sCaton;

    /* loaded from: classes2.dex */
    public interface Callback {
        void onBlockOccurs(String[] strArr, boolean z, long... jArr);
    }

    private Caton(Context context, long j, long j2, MonitorMode monitorMode, boolean z, Callback callback) {
        long min = Math.min(Math.max(j, 200L), 400L);
        long min2 = Math.min(Math.max(j2, 200L), 400L);
        Config.LOG_ENABLED = z;
        Config.THRESHOLD_TIME = min;
        BlockHandler blockHandler = new BlockHandler(context, new StackTraceCollector(min2), callback);
        if (monitorMode == MonitorMode.LOOPER) {
            new UILooperObserver(blockHandler);
        } else if (monitorMode == MonitorMode.FRAME) {
            if (Build.VERSION.SDK_INT >= 16) {
                Choreographer.getInstance().postFrameCallback(new FPSFrameCallBack(context, blockHandler));
                return;
            }
            new UILooperObserver(blockHandler);
        }
    }

    public static void initialize(Context context) {
        initialize(new Builder(context));
    }

    public static void initialize(Builder builder) {
        if (sCaton == null) {
            synchronized (Caton.class) {
                if (sCaton == null) {
                    sCaton = builder.build();
                }
            }
        }
    }

    public static void setLoggingEnabled(boolean z) {
        Config.LOG_ENABLED = z;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private Callback mCallback;
        private Context mContext;
        private long mThresholdTime = Caton.DEFAULT_THRESHOLD_TIME;
        private long mCollectInterval = 1000;
        private MonitorMode mMonitorMode = Caton.DEFAULT_MODE;
        private boolean loggingEnabled = true;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder thresholdTime(long j) {
            this.mThresholdTime = j;
            return this;
        }

        public Builder collectInterval(long j) {
            this.mCollectInterval = j;
            return this;
        }

        public Builder monitorMode(MonitorMode monitorMode) {
            this.mMonitorMode = monitorMode;
            return this;
        }

        public Builder loggingEnabled(boolean z) {
            this.loggingEnabled = z;
            return this;
        }

        public Builder callback(Callback callback) {
            this.mCallback = callback;
            return this;
        }

        Caton build() {
            return new Caton(this.mContext, this.mThresholdTime, this.mCollectInterval, this.mMonitorMode, this.loggingEnabled, this.mCallback);
        }
    }

    /* loaded from: classes2.dex */
    public enum MonitorMode {
        LOOPER(0),
        FRAME(1);
        
        int value;

        MonitorMode(int i) {
            this.value = i;
        }
    }
}
