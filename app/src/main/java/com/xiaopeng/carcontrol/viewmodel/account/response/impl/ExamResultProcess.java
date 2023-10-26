package com.xiaopeng.carcontrol.viewmodel.account.response.impl;

import com.xiaopeng.carcontrol.viewmodel.account.bean.TaskResultResponse;
import com.xiaopeng.carcontrol.viewmodel.account.response.AbsExamResponseProcess;
import com.xiaopeng.carcontrol.viewmodel.account.response.ExamResultResponseCallback;

/* loaded from: classes2.dex */
public class ExamResultProcess extends AbsExamResponseProcess<ExamResultResponseCallback> {
    private final int mRequestOrigin;

    public ExamResultProcess(ExamResultResponseCallback callback, int requestOrigin) {
        super(callback);
        this.mRequestOrigin = requestOrigin;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.response.AbsExamResponseProcess
    public void processExamSuccess(String data) {
        TaskResultResponse taskResultResponse = (TaskResultResponse) this.mGson.fromJson(data, (Class<Object>) TaskResultResponse.class);
        if (taskResultResponse == null) {
            ((ExamResultResponseCallback) this.mExamCallBack).onExamFailure(-3, "Response parse failure!");
        } else {
            ((ExamResultResponseCallback) this.mExamCallBack).onExamTaskSuccess(taskResultResponse.getTaskId(), taskResultResponse.getState(), this.mRequestOrigin);
        }
    }
}
