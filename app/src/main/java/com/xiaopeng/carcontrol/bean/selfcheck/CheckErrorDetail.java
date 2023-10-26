package com.xiaopeng.carcontrol.bean.selfcheck;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CheckErrorDetail {
    @SerializedName("ecu")
    private String mEcu;
    @SerializedName("ecuName")
    private String mEcuName;
    @SerializedName("errorName")
    private String mErrorName;
    @SerializedName("detail")
    private List<CheckResult> mItemResult = new ArrayList();
    @SerializedName("key")
    private String mKey;
    @SerializedName("level")
    private String mLevel;
    @SerializedName(NotificationCompat.CATEGORY_SYSTEM)
    private String mSys;

    public String getSys() {
        return this.mSys;
    }

    public void setSys(String sys) {
        this.mSys = sys;
    }

    public String getEcu() {
        return this.mEcu;
    }

    public void setEcu(String ecu) {
        this.mEcu = ecu;
    }

    public String getEcuName() {
        return this.mEcuName;
    }

    public void setEcuName(String ecuName) {
        this.mEcuName = ecuName;
    }

    public String getLevel() {
        return this.mLevel;
    }

    public void setLevel(String level) {
        this.mLevel = level;
    }

    public void addResultItem(String sigName, int sigVal) {
        this.mItemResult.add(new CheckResult(sigName, sigVal));
    }

    public String getErrorName() {
        return this.mErrorName;
    }

    public void setErrorName(String errorName) {
        this.mErrorName = errorName;
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }
}
