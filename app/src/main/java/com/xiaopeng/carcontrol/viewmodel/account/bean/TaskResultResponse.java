package com.xiaopeng.carcontrol.viewmodel.account.bean;

/* loaded from: classes2.dex */
public class TaskResultResponse {
    private Object sid;
    private int state;
    private int taskId;
    private Object taskParam;
    private Object type;

    public Object getSid() {
        return this.sid;
    }

    public void setSid(Object sid) {
        this.sid = sid;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Object getType() {
        return this.type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getTaskParam() {
        return this.taskParam;
    }

    public void setTaskParam(Object taskParam) {
        this.taskParam = taskParam;
    }
}
