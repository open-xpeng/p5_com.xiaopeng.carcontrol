package com.xiaopeng.lib.bughunter.anr;

import android.content.Context;
import android.view.Choreographer;
import android.view.WindowManager;

/* loaded from: classes2.dex */
public class FPSFrameCallBack implements Choreographer.FrameCallback {
    private static long SKIPPED_FRAME_ANR_TRIGGER = 0;
    private static long SKIPPED_FRAME_WARNING_LIMIT = 0;
    private static final String TAG = "FPSFrameCallBack";
    private BlockHandler mBlockHandler;
    private long mFrameIntervalNanos;
    private long mLastFrameTimeNanos;

    public FPSFrameCallBack(Context context, BlockHandler blockHandler) {
        this.mFrameIntervalNanos = 1.0E9f / getRefreshRate(context);
        long j = this.mFrameIntervalNanos;
        SKIPPED_FRAME_WARNING_LIMIT = ((Config.THRESHOLD_TIME * 1000) * 1000) / j;
        SKIPPED_FRAME_ANR_TRIGGER = 5000000000L / j;
        Config.log(TAG, "SKIPPED_FRAME_WARNING_LIMIT : " + SKIPPED_FRAME_WARNING_LIMIT + " ,SKIPPED_FRAME_ANR_TRIGGER : " + SKIPPED_FRAME_ANR_TRIGGER);
        this.mBlockHandler = blockHandler;
    }

    private float getRefreshRate(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRefreshRate();
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long j) {
        long j2 = this.mLastFrameTimeNanos;
        if (j2 == 0) {
            this.mLastFrameTimeNanos = j;
            Choreographer.getInstance().postFrameCallback(this);
            return;
        }
        long j3 = j - j2;
        long j4 = this.mFrameIntervalNanos;
        if (j3 >= j4) {
            long j5 = j3 / j4;
            if (j5 >= SKIPPED_FRAME_WARNING_LIMIT) {
                Config.log(TAG, "Skipped " + j5 + " frames!  The application may be doing too much work on its main thread.");
                this.mBlockHandler.notifyBlockOccurs(j5 >= SKIPPED_FRAME_ANR_TRIGGER, j5);
            }
        }
        this.mLastFrameTimeNanos = j;
        Choreographer.getInstance().postFrameCallback(this);
    }
}
