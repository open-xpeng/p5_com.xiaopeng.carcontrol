package com.xiaopeng.carcontrol.viewmodel.account.response;

/* loaded from: classes2.dex */
public interface ExamResultResponseCallback extends ResponseCallback {
    public static final int REQUEST_FORM_LOGIN = 0;
    public static final int REQUEST_FROM_LOOP = 2;
    public static final int REQUEST_FROM_OPERATION = 1;

    void onExamTaskSuccess(long taskId, int result, int origin);
}
