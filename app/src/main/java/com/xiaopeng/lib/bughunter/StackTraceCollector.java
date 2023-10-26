package com.xiaopeng.lib.bughunter;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.xiaopeng.lib.bughunter.anr.Collector;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class StackTraceCollector implements Collector {
    private static final String CATON_STACK_INFO = "caton_stack_info";
    private static final int COLLECT_SPACE_TIME = 3000;
    private static final int MIN_COLLECT_COUNT = 3;
    private static final int MSG_BEGIN_WATCH = 54;
    private static final int MSG_COLLECT_CONTINUE = 55;
    private static final String TAG = "StackTraceCollector";
    private static final String THREAD_TAG = "-----";
    private long mCollectInterval;
    private volatile CollectorHandler mCollectorHandler;
    private volatile boolean mIsWatching;
    private StackTraceElement[] mLastStackTrace;
    private int mLimitLength;
    private Thread mMainThread;
    private int[] mRepeatTimes;
    private ArrayList<StackTraceElement[]> mStackQueue;

    public StackTraceCollector(long j) {
        this.mCollectInterval = j;
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.setPriority(10);
        handlerThread.start();
        this.mCollectorHandler = new CollectorHandler(handlerThread.getLooper());
        this.mLimitLength = 3;
        this.mStackQueue = new ArrayList<>(this.mLimitLength);
        this.mRepeatTimes = new int[this.mLimitLength];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reset() {
        synchronized (this.mStackQueue) {
            if (!this.mStackQueue.isEmpty()) {
                this.mLastStackTrace = null;
                this.mStackQueue.clear();
                Arrays.fill(this.mRepeatTimes, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void increaseRepeatTimes() {
        synchronized (this.mStackQueue) {
            int size = this.mStackQueue.size() - 1;
            int[] iArr = this.mRepeatTimes;
            iArr[size] = iArr[size] + 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trigger(int i) {
        Message obtainMessage = this.mCollectorHandler.obtainMessage();
        obtainMessage.obj = this;
        obtainMessage.what = i;
        this.mCollectorHandler.sendMessageDelayed(obtainMessage, this.mCollectInterval);
    }

    public boolean isWatching() {
        return this.mIsWatching;
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public void start() {
        this.mIsWatching = true;
        trigger(54);
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public void stop() {
        this.mIsWatching = false;
        this.mCollectorHandler.removeMessages(54);
        this.mCollectorHandler.removeMessages(55);
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public int[] getStackTraceRepeats() {
        int[] copyOf;
        synchronized (this.mStackQueue) {
            int[] iArr = this.mRepeatTimes;
            copyOf = Arrays.copyOf(iArr, iArr.length);
        }
        return copyOf;
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public StackTraceElement[][] getStackTraceInfo() {
        StackTraceElement[][] stackTraceElementArr;
        synchronized (this.mStackQueue) {
            stackTraceElementArr = (StackTraceElement[][]) this.mStackQueue.toArray((StackTraceElement[][]) Array.newInstance(StackTraceElement.class, 0, 0));
        }
        return stackTraceElementArr;
    }

    @Override // com.xiaopeng.lib.bughunter.anr.Collector
    public void add(StackTraceElement[] stackTraceElementArr) {
        synchronized (this.mStackQueue) {
            this.mLastStackTrace = stackTraceElementArr;
            int size = this.mStackQueue.size();
            int i = this.mLimitLength;
            if (size >= i) {
                int i2 = i - 1;
                int i3 = this.mRepeatTimes[i2];
                int i4 = i2;
                for (int i5 = i2 - 1; i5 >= 1; i5--) {
                    int[] iArr = this.mRepeatTimes;
                    if (i3 > iArr[i5]) {
                        i3 = iArr[i5];
                        i4 = i5;
                    }
                }
                this.mStackQueue.remove(i4);
                while (i4 < i2) {
                    int[] iArr2 = this.mRepeatTimes;
                    int i6 = i4 + 1;
                    iArr2[i4] = iArr2[i6];
                    i4 = i6;
                }
                this.mRepeatTimes[i2] = 0;
            }
            this.mStackQueue.add(stackTraceElementArr);
            this.mRepeatTimes[this.mStackQueue.size() - 1] = 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class CollectorHandler extends Handler {
        public CollectorHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 54 || message.what == 55) {
                if (message.what == 54) {
                    StackTraceCollector.this.reset();
                }
                StackTraceElement[] mainThreadStackInfo = StackTraceCollector.this.getMainThreadStackInfo();
                if (StackTraceCollector.isEqualsAndNotNull(mainThreadStackInfo, StackTraceCollector.this.mLastStackTrace)) {
                    StackTraceCollector.this.increaseRepeatTimes();
                } else {
                    StackTraceCollector.this.add(mainThreadStackInfo);
                }
                if (StackTraceCollector.this.isWatching()) {
                    StackTraceCollector.this.trigger(55);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public StackTraceElement[] getMainThreadStackInfo() {
        if (this.mMainThread == null) {
            this.mMainThread = Looper.getMainLooper().getThread();
        }
        return this.mMainThread.getStackTrace();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isEqualsAndNotNull(StackTraceElement[] stackTraceElementArr, StackTraceElement[] stackTraceElementArr2) {
        int length;
        if (stackTraceElementArr == null || stackTraceElementArr2 == null || (length = stackTraceElementArr.length) != stackTraceElementArr2.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!stackTraceElementArr[i].equals(stackTraceElementArr2[i])) {
                return false;
            }
        }
        return true;
    }
}
