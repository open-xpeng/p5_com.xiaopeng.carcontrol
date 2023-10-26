package com.xiaopeng.carcontrol.viewmodel.account.response.impl;

import com.google.gson.reflect.TypeToken;
import com.xiaopeng.carcontrol.viewmodel.account.bean.TaskListResponse;
import com.xiaopeng.carcontrol.viewmodel.account.response.AbsExamResponseProcess;
import com.xiaopeng.carcontrol.viewmodel.account.response.ExamResultResponseCallback;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ExamGroupResultProcess extends AbsExamResponseProcess<ExamResultResponseCallback> {
    private final Map<Long, Integer> mExamResultMap;
    private final int mRequestOrigin;

    public ExamGroupResultProcess(ExamResultResponseCallback callback, int requestOrigin) {
        super(callback);
        this.mExamResultMap = new HashMap();
        this.mRequestOrigin = requestOrigin;
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.account.response.AbsExamResponseProcess
    public void processExamSuccess(String data) {
        List list = (List) this.mGson.fromJson(data, new TypeToken<List<TaskListResponse>>() { // from class: com.xiaopeng.carcontrol.viewmodel.account.response.impl.ExamGroupResultProcess.1
        }.getType());
        this.mExamResultMap.clear();
        for (int i = 0; i < list.size(); i++) {
            TaskListResponse taskListResponse = (TaskListResponse) list.get(i);
            if (taskListResponse == null) {
                ((ExamResultResponseCallback) this.mExamCallBack).onExamFailure(-3, "Response parse failure!");
                return;
            }
            if (taskListResponse.getTaskId() == 8 && taskListResponse.getState() == 1) {
                this.mExamResultMap.put(8L, 1);
                this.mExamResultMap.put(2L, 1);
            } else if (taskListResponse.getTaskId() == 7 && taskListResponse.getState() == 1) {
                this.mExamResultMap.put(7L, 1);
                this.mExamResultMap.put(3L, 1);
            } else if (taskListResponse.getTaskId() == 2) {
                if (this.mExamResultMap.get(2L) == null) {
                    this.mExamResultMap.put(2L, Integer.valueOf(taskListResponse.getState()));
                }
            } else if (taskListResponse.getTaskId() == 3) {
                if (this.mExamResultMap.get(3L) == null) {
                    this.mExamResultMap.put(3L, Integer.valueOf(taskListResponse.getState()));
                }
            } else {
                this.mExamResultMap.put(Long.valueOf(taskListResponse.getTaskId()), Integer.valueOf(taskListResponse.getState()));
            }
        }
        for (Map.Entry<Long, Integer> entry : this.mExamResultMap.entrySet()) {
            ((ExamResultResponseCallback) this.mExamCallBack).onExamTaskSuccess(entry.getKey().longValue(), entry.getValue().intValue(), this.mRequestOrigin);
        }
    }
}
