package com.xiaopeng.xui.app.delegate;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public interface XActivityDismiss {
    public static final int DISMISS_CAUSE_BACK = 1;
    public static final int DISMISS_CAUSE_OUTSIDE = 3;
    public static final int DISMISS_CAUSE_PAUSE = 2;
    public static final int DISMISS_CAUSE_TIMEOUT = 4;
    public static final int DISMISS_CAUSE_USER = 0;
    public static final int DISMISS_TYPE_FINISH = 0;
    public static final int DISMISS_TYPE_MOVE_BACK = 1;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface DismissCause {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface DismissType {
    }

    /* loaded from: classes2.dex */
    public interface OnDismissListener {
        boolean onDismiss(int i, int i2);
    }

    void dismiss(int i);

    void setDismissDelay(long j);

    void setDismissType(int i);

    void setOnDismissListener(OnDismissListener onDismissListener);
}
