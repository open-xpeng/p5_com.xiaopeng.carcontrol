package com.xiaopeng.carcontrol.viewmodel.account.bean;

/* loaded from: classes2.dex */
public class TaskResultRequest extends TaskInfoRequest {
    public static int TASK_COMPLETE = 1;
    private int state;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
