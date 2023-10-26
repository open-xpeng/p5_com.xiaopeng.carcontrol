package com.xiaopeng.carcontrol.bean.selfcheck;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes.dex */
public class CheckResult {
    @SerializedName("sigName")
    private String mSigName;
    @SerializedName("sigVal")
    private int mSigVal;

    public CheckResult() {
    }

    public CheckResult(String sigName, int sigVal) {
        this.mSigName = sigName;
        this.mSigVal = sigVal;
    }

    public String getSigName() {
        return this.mSigName;
    }

    public void setSigName(String sigName) {
        this.mSigName = sigName;
    }

    public int getSigVal() {
        return this.mSigVal;
    }

    public void setSigVal(int sigVal) {
        this.mSigVal = sigVal;
    }
}
