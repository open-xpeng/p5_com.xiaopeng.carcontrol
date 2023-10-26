package com.xiaopeng.xpmeditation.util;

import android.os.Handler;
import android.os.Message;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public abstract class XpTickDownTimer {
    private static boolean DEBUG = false;
    private static final int MSG_PAUSE = 2;
    private static final int MSG_RUN = 1;
    private static String TAG = "XpTickDownTimer";
    private final long mCountdownInterval;
    private long mRemainTime;
    private String mTimerTag;
    private long mTotalTime;
    private boolean mCancelled = false;
    private boolean mIsPaused = false;
    private boolean mIsFinished = false;
    private final Handler mHandler = new Handler() { // from class: com.xiaopeng.xpmeditation.util.XpTickDownTimer.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            synchronized (XpTickDownTimer.this) {
                if (XpTickDownTimer.this.mCancelled) {
                    return;
                }
                if (msg.what == 1) {
                    long j = XpTickDownTimer.this.mRemainTime;
                    if (j <= 0) {
                        XpTickDownTimer.this.mIsFinished = true;
                        XpTickDownTimer.this.onFinish();
                        LogUtils.i(XpTickDownTimer.TAG, XpTickDownTimer.this.mTimerTag + " onFinish");
                    } else {
                        XpTickDownTimer.this.onTick(j);
                        if (XpTickDownTimer.DEBUG) {
                            LogUtils.d(XpTickDownTimer.TAG, XpTickDownTimer.this.mTimerTag + " onTick:  " + j);
                        }
                        XpTickDownTimer xpTickDownTimer = XpTickDownTimer.this;
                        xpTickDownTimer.mRemainTime = j - xpTickDownTimer.mCountdownInterval;
                        sendMessageDelayed(obtainMessage(1, Long.valueOf(XpTickDownTimer.this.mRemainTime)), XpTickDownTimer.this.mCountdownInterval);
                    }
                }
            }
        }
    };

    public abstract void onCancel();

    public abstract void onFinish();

    public abstract void onTick(long millisUntilFinished);

    public XpTickDownTimer(long millisInFuture, long countDownInterval, String tag) {
        this.mTimerTag = "";
        this.mTimerTag = tag;
        this.mTotalTime = millisInFuture;
        this.mCountdownInterval = countDownInterval;
        this.mRemainTime = millisInFuture;
    }

    public final void seek(int value) {
        LogUtils.d(TAG, this.mTimerTag + " seek to " + value);
        synchronized (this) {
            this.mRemainTime = ((100 - value) * this.mTotalTime) / 100;
        }
    }

    public final void cancel() {
        LogUtils.d(TAG, this.mTimerTag + " cancel ");
        this.mCancelled = true;
        this.mIsPaused = true;
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        onCancel();
    }

    public final void resume() {
        LogUtils.d(TAG, this.mTimerTag + " resume " + this.mRemainTime);
        this.mIsPaused = false;
        this.mCancelled = false;
        this.mHandler.removeMessages(2);
        Handler handler = this.mHandler;
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(1));
    }

    public final void pause() {
        LogUtils.d(TAG, this.mTimerTag + " pause " + this.mRemainTime);
        this.mIsPaused = true;
        this.mHandler.removeMessages(1);
        Handler handler = this.mHandler;
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(2));
    }

    public final synchronized XpTickDownTimer start() {
        LogUtils.d(TAG, this.mTimerTag + " start " + this.mTotalTime);
        long j = this.mTotalTime;
        this.mRemainTime = j;
        if (j <= 0) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mIsFinished = true;
            onFinish();
            return this;
        }
        this.mCancelled = false;
        this.mIsPaused = false;
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1, Long.valueOf(j)));
        return this;
    }

    public boolean isTiming() {
        return (this.mIsFinished && this.mCancelled && this.mIsPaused) ? false : true;
    }
}
