package com.xiaopeng.carcontrol.bean.selfcheck;

/* loaded from: classes.dex */
public abstract class CheckBaseItem {
    private int mLevel;
    private volatile CheckDataResponse mResult = new CheckDataResponse();
    protected String mTitle;

    public void cancelCheck() {
    }

    public void startCheck() throws Exception {
    }

    public void stopCheck() {
    }

    public boolean hasIssue() {
        return this.mResult.hasIssue();
    }

    public CheckDataResponse getResult() {
        return this.mResult;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public int getLevel() {
        return this.mLevel;
    }

    public void setLevel(int level) {
        this.mLevel = level;
    }

    public void setResult(CheckDataResponse result) {
        this.mResult = result;
    }

    public void setHasIssue(boolean hasIssue) {
        this.mResult.setHasIssue(hasIssue);
    }

    public void addResulValueItem(String sigName, int sigValue) {
        this.mResult.addDetailResult(sigName, sigValue);
    }
}
