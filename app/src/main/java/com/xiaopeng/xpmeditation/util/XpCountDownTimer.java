package com.xiaopeng.xpmeditation.util;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public abstract class XpCountDownTimer {
    private static boolean DEBUG = false;
    private static final int MSG_PAUSE = 2;
    private static final int MSG_RUN = 1;
    private static String TAG = "XpCountDownTimer";
    private final long mCountdownInterval;
    private long mRemainTime;
    private long mStopTimeInFuture;
    private String mTimerTag;
    private long mTotalTime;
    private boolean mCancelled = false;
    private boolean mIsPaused = false;
    private boolean mIsFinished = false;
    private final Handler mHandler = new Handler() { // from class: com.xiaopeng.xpmeditation.util.XpCountDownTimer.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            long j;
            synchronized (XpCountDownTimer.this) {
                if (XpCountDownTimer.this.mCancelled) {
                    return;
                }
                if (msg.what == 1) {
                    long elapsedRealtime = XpCountDownTimer.this.mStopTimeInFuture - SystemClock.elapsedRealtime();
                    long j2 = 0;
                    if (elapsedRealtime <= 0) {
                        XpCountDownTimer.this.mIsFinished = true;
                        XpCountDownTimer.this.onFinish();
                    } else {
                        long elapsedRealtime2 = SystemClock.elapsedRealtime();
                        XpCountDownTimer.this.onTick(elapsedRealtime);
                        if (XpCountDownTimer.DEBUG) {
                            LogUtils.d(XpCountDownTimer.TAG, XpCountDownTimer.this.mTimerTag + " onTick:  " + elapsedRealtime);
                        }
                        long elapsedRealtime3 = SystemClock.elapsedRealtime() - elapsedRealtime2;
                        if (elapsedRealtime < XpCountDownTimer.this.mCountdownInterval) {
                            j = elapsedRealtime - elapsedRealtime3;
                            if (j < 0) {
                                XpCountDownTimer.this.mRemainTime = elapsedRealtime;
                                sendMessageDelayed(obtainMessage(1), j2);
                            }
                        } else {
                            j = XpCountDownTimer.this.mCountdownInterval - elapsedRealtime3;
                            while (j < 0) {
                                j += XpCountDownTimer.this.mCountdownInterval;
                            }
                        }
                        j2 = j;
                        XpCountDownTimer.this.mRemainTime = elapsedRealtime;
                        sendMessageDelayed(obtainMessage(1), j2);
                    }
                }
            }
        }
    };

    public abstract void onCancel();

    public abstract void onFinish();

    public abstract void onTick(long millisUntilFinished);

    public XpCountDownTimer(long millisInFuture, long countDownInterval, String tag) {
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
        this.mStopTimeInFuture = SystemClock.elapsedRealtime() + this.mRemainTime;
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

    public final synchronized XpCountDownTimer start() {
        LogUtils.d(TAG, this.mTimerTag + " start " + this.mTotalTime);
        if (this.mRemainTime <= 0) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mIsFinished = true;
            onFinish();
            return this;
        }
        this.mCancelled = false;
        this.mIsPaused = false;
        this.mStopTimeInFuture = SystemClock.elapsedRealtime() + this.mTotalTime;
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(1));
        return this;
    }

    public boolean isTiming() {
        return (this.mIsFinished && this.mCancelled && this.mIsPaused) ? false : true;
    }
}
