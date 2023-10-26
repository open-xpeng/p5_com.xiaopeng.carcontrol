package com.xiaopeng.lib.framework.netchannelmodule.http.statistic;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes2.dex */
public class TrafficData {
    @SerializedName("fail")
    private long mFailed;
    @SerializedName("rx")
    private long mReceived;
    @SerializedName("tx")
    private long mSent;
    @SerializedName("succ")
    private long mSucceed;

    public TrafficData() {
        reset();
    }

    public void setCount(long j, long j2) {
        this.mFailed = j2;
        this.mSucceed = j;
    }

    public void setSize(long j, long j2) {
        this.mReceived = j;
        this.mSent = j2;
    }

    public void increaseSucceed(long j) {
        this.mSucceed++;
        this.mReceived += j;
    }

    public void increaseFailed(long j) {
        this.mFailed++;
        this.mReceived += j;
    }

    public void addReceivedSize(long j) {
        this.mReceived += j;
    }

    public void addSentSize(long j) {
        this.mSent += j;
    }

    public long succeed() {
        return this.mSucceed;
    }

    public long failed() {
        return this.mFailed;
    }

    public long receivedSize() {
        return this.mReceived;
    }

    public long sentSize() {
        return this.mSent;
    }

    public void reset() {
        this.mSent = 0L;
        this.mReceived = 0L;
        this.mFailed = 0L;
        this.mSucceed = 0L;
    }

    public String toString() {
        return "[ succeed:" + this.mSucceed + ", failed:" + this.mFailed + ", rx:" + this.mReceived + ", tx:" + this.mSent + " ]";
    }
}
