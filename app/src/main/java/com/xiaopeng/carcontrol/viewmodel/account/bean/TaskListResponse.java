package com.xiaopeng.carcontrol.viewmodel.account.bean;

/* loaded from: classes2.dex */
public class TaskListResponse {
    private String createTime;
    private String nickName;
    private String sid;
    private int state;
    private long taskId;
    private String taskParam;
    private String type;
    private String uid;

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTaskParam() {
        return this.taskParam;
    }

    public void setTaskParam(String taskParam) {
        this.taskParam = taskParam;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        return "TaskResponse{sid='" + this.sid + "', taskId=" + this.taskId + ", type='" + this.type + "', state=" + this.state + ", taskParam='" + this.taskParam + "', uid='" + this.uid + "', nickName='" + this.nickName + "', createTime='" + this.createTime + "'}";
    }
}
