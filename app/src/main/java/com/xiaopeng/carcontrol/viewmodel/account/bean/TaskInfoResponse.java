package com.xiaopeng.carcontrol.viewmodel.account.bean;

import com.xiaopeng.carcontrol.util.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class TaskInfoResponse {
    private static final String TAG = "TaskInfoResponse";
    public static final int TASK_FAILURE = 0;
    public static final int TASK_SUCCESS = 1;
    private String sid;
    private int state;
    private int taskId;
    private String taskParam;
    private int type;

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTaskParam() {
        String str = this.taskParam;
        if (str != null) {
            return parseUrl(str);
        }
        LogUtils.e(TAG, "task param is null,please check the response.");
        return null;
    }

    public void setTaskParam(String taskParam) {
        this.taskParam = taskParam;
    }

    private String parseUrl(String param) {
        try {
            return new JSONObject(param).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return param;
        }
    }
}
