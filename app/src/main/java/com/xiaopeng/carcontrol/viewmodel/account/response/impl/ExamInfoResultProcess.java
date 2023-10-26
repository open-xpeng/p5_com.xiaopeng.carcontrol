package com.xiaopeng.carcontrol.viewmodel.account.response.impl;

import com.xiaopeng.carcontrol.viewmodel.account.bean.TaskInfoResponse;
import com.xiaopeng.carcontrol.viewmodel.account.response.AbsExamResponseProcess;
import com.xiaopeng.carcontrol.viewmodel.account.response.ExamInfoResponseCallback;

/* loaded from: classes2.dex */
public class ExamInfoResultProcess extends AbsExamResponseProcess<ExamInfoResponseCallback> {
    public ExamInfoResultProcess(ExamInfoResponseCallback callback) {
        super(callback);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.response.AbsExamResponseProcess
    public void processExamSuccess(String data) {
        TaskInfoResponse taskInfoResponse = (TaskInfoResponse) this.mGson.fromJson(data, (Class<Object>) TaskInfoResponse.class);
        if (taskInfoResponse == null) {
            ((ExamInfoResponseCallback) this.mExamCallBack).onExamFailure(-3, "Response parse failure!");
        } else {
            ((ExamInfoResponseCallback) this.mExamCallBack).onExamInfoSuccess(taskInfoResponse.getTaskId(), taskInfoResponse.getTaskParam());
        }
    }
}
