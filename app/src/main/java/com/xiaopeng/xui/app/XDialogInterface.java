package com.xiaopeng.xui.app;

/* loaded from: classes2.dex */
public interface XDialogInterface {
    public static final int BUTTON_NEGATIVE = -2;
    public static final int BUTTON_POSITIVE = -1;

    /* loaded from: classes2.dex */
    public interface OnClickListener {
        void onClick(XDialog xDialog, int i);
    }

    /* loaded from: classes2.dex */
    public interface OnCloseListener {
        boolean onClose(XDialog xDialog);
    }

    /* loaded from: classes2.dex */
    public interface OnCountDownListener {
        boolean onCountDown(XDialog xDialog, int i);
    }
}
