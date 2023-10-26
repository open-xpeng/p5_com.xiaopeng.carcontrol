package com.xiaopeng.carcontrol.bean.selfcheck;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class CheckUploadData {
    @SerializedName("cduId")
    private String mCduId;
    @SerializedName("errorDetail")
    private ArrayList<CheckErrorDetail> mErrorDetail = new ArrayList<>();
    @SerializedName("status")
    private int mStatus;
    @SerializedName("ts")
    private long mTs;
    @SerializedName("vin")
    private String mVin;

    public long getTs() {
        return this.mTs;
    }

    public void setTs(long ts) {
        this.mTs = ts;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    public ArrayList<CheckErrorDetail> getErrorDetail() {
        return this.mErrorDetail;
    }

    public void setErrorDetail(ArrayList<CheckErrorDetail> errorDetail) {
        this.mErrorDetail = errorDetail;
    }

    public void addErrorDetailAll(ArrayList<CheckErrorDetail> item) {
        this.mErrorDetail.addAll(item);
    }

    public String getVin() {
        return this.mVin;
    }

    public void setVin(String vin) {
        this.mVin = vin;
    }

    public String getCduId() {
        return this.mCduId;
    }

    public void setCduId(String cduId) {
        this.mCduId = cduId;
    }
}
