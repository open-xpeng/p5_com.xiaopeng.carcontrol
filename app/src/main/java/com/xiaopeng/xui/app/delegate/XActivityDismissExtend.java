package com.xiaopeng.xui.app.delegate;

import android.app.Activity;
import android.os.Handler;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.xui.app.delegate.XActivityDismiss;
import com.xiaopeng.xui.app.delegate.XActivityDismissExtend;
import com.xiaopeng.xui.utils.XActivityUtils;
import com.xiaopeng.xui.utils.XLogUtils;

/* loaded from: classes2.dex */
abstract class XActivityDismissExtend implements XActivityDismiss, XActivityLifecycle {

    /* loaded from: classes2.dex */
    interface OnDismissListenerEx {
        void afterDismiss(int i);

        void beforeDismiss(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void setOnDismissListenerEx(OnDismissListenerEx onDismissListenerEx);

    XActivityDismissExtend() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static XActivityDismissExtend create(Activity activity) {
        return new XActivityDismissExtendImpl(activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class XActivityDismissExtendImpl extends XActivityDismissExtend {
        private static final String TAG = "XActivityDismiss";
        private Activity mActivity;
        private Runnable mDelayRunnable;
        private XActivityDismiss.OnDismissListener mOnDismissListener;
        private OnDismissListenerEx mOnDismissListenerEx;
        private int mDismissType = 0;
        private long mDismissDelay = 0;
        private final Handler mHandler = new Handler();

        private String convertCauseToString(int i) {
            return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "user" : "timeout" : "outside" : "pause" : GlobalConstant.EXTRA.KEY_BACK;
        }

        private String convertTypeToString(int i) {
            return i != 1 ? "finish" : "moveToBack";
        }

        XActivityDismissExtendImpl(Activity activity) {
            this.mActivity = activity;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss
        public void setDismissDelay(long j) {
            this.mDismissDelay = j;
            XLogUtils.i(TAG, "setDismissDelay: " + j);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss
        public void setOnDismissListener(XActivityDismiss.OnDismissListener onDismissListener) {
            this.mOnDismissListener = onDismissListener;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismissExtend
        void setOnDismissListenerEx(OnDismissListenerEx onDismissListenerEx) {
            this.mOnDismissListenerEx = onDismissListenerEx;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss
        public void setDismissType(int i) {
            this.mDismissType = i;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityDismiss
        public void dismiss(final int i) {
            XActivityDismiss.OnDismissListener onDismissListener = this.mOnDismissListener;
            if (onDismissListener != null && onDismissListener.onDismiss(i, this.mDismissType)) {
                XLogUtils.i(TAG, "dismiss: intercept for " + convertCauseToString(i));
                return;
            }
            OnDismissListenerEx onDismissListenerEx = this.mOnDismissListenerEx;
            if (onDismissListenerEx != null) {
                onDismissListenerEx.beforeDismiss(i);
            }
            if (this.mDismissDelay == 0) {
                _dismiss(i);
                return;
            }
            XLogUtils.i(TAG, "dismiss: type : " + convertTypeToString(this.mDismissType) + " , cause : " + convertCauseToString(i) + " " + this.mDelayRunnable);
            if (this.mDelayRunnable == null) {
                Runnable runnable = new Runnable() { // from class: com.xiaopeng.xui.app.delegate.-$$Lambda$XActivityDismissExtend$XActivityDismissExtendImpl$88suYoVCKDXANih9pewktN1TrnQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        XActivityDismissExtend.XActivityDismissExtendImpl.this.lambda$dismiss$0$XActivityDismissExtend$XActivityDismissExtendImpl(i);
                    }
                };
                this.mDelayRunnable = runnable;
                this.mHandler.postDelayed(runnable, this.mDismissDelay);
            }
        }

        public /* synthetic */ void lambda$dismiss$0$XActivityDismissExtend$XActivityDismissExtendImpl(int i) {
            _dismiss(i);
            this.mDelayRunnable = null;
            OnDismissListenerEx onDismissListenerEx = this.mOnDismissListenerEx;
            if (onDismissListenerEx != null) {
                onDismissListenerEx.afterDismiss(i);
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onDestroy() {
            Runnable runnable = this.mDelayRunnable;
            if (runnable != null) {
                this.mHandler.removeCallbacks(runnable);
                this.mDelayRunnable = null;
            }
        }

        private void _dismiss(int i) {
            XLogUtils.i(TAG, "_dismiss: type : " + convertTypeToString(this.mDismissType) + " , cause : " + convertCauseToString(i));
            int i2 = this.mDismissType;
            if (i2 == 0) {
                XActivityUtils.finish(this.mActivity);
            } else if (i2 != 1) {
            } else {
                XActivityUtils.moveTaskToBack(this.mActivity, true);
            }
        }

        public String toString() {
            return "{mDismissType=" + this.mDismissType + ", mDismissDelay=" + this.mDismissDelay + '}';
        }
    }
}
