package com.xiaopeng.carcontrol.bean.selfcheck;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CheckDataResponse {
    private volatile int mErrorCode = 0;
    private volatile boolean mHasIssue = false;
    private volatile List<CheckResult> mDetailResult = new ArrayList();

    public boolean hasIssue() {
        return this.mHasIssue;
    }

    public void setHasIssue(boolean hasIssue) {
        this.mHasIssue = hasIssue;
    }

    public void addDetailResult(String name, int value) {
        this.mDetailResult.add(new CheckResult(name, value));
    }

    public void setDetailResult(List<CheckResult> list) {
        this.mDetailResult = list;
    }

    public List<CheckResult> getDetailResult() {
        return this.mDetailResult;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        this.mErrorCode = errorCode;
    }
}
