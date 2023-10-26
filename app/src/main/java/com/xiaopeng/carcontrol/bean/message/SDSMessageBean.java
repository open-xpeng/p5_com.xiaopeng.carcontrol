package com.xiaopeng.carcontrol.bean.message;

import java.util.List;

/* loaded from: classes.dex */
public class SDSMessageBean {
    private List<Long> taskIdList;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public List<Long> getTaskIdList() {
        return this.taskIdList;
    }

    public void setTaskIdList(List<Long> taskIdList) {
        this.taskIdList = taskIdList;
    }
}
