package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class BucketLifecycleRule {
    private String mArchiveDays;
    private String mArchiveExpireDate;
    private String mDays;
    private String mExpireDate;
    private String mIADays;
    private String mIAExpireDate;
    private String mIdentifier;
    private String mMultipartDays;
    private String mMultipartExpireDate;
    private String mPrefix;
    private boolean mStatus;

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public void setIdentifier(String str) {
        this.mIdentifier = str;
    }

    public String getPrefix() {
        return this.mPrefix;
    }

    public void setPrefix(String str) {
        this.mPrefix = str;
    }

    public boolean getStatus() {
        return this.mStatus;
    }

    public void setStatus(boolean z) {
        this.mStatus = z;
    }

    public String getDays() {
        return this.mDays;
    }

    public void setDays(String str) {
        this.mDays = str;
    }

    public String getExpireDate() {
        return this.mExpireDate;
    }

    public void setExpireDate(String str) {
        this.mExpireDate = str;
    }

    public String getMultipartDays() {
        return this.mMultipartDays;
    }

    public void setMultipartDays(String str) {
        this.mMultipartDays = str;
    }

    public String getMultipartExpireDate() {
        return this.mMultipartExpireDate;
    }

    public void setMultipartExpireDate(String str) {
        this.mMultipartExpireDate = str;
    }

    public String getIADays() {
        return this.mIADays;
    }

    public void setIADays(String str) {
        this.mIADays = str;
    }

    public String getIAExpireDate() {
        return this.mIAExpireDate;
    }

    public void setIAExpireDate(String str) {
        this.mIAExpireDate = str;
    }

    public String getArchiveDays() {
        return this.mArchiveDays;
    }

    public void setArchiveDays(String str) {
        this.mArchiveDays = str;
    }

    public String getArchiveExpireDate() {
        return this.mArchiveExpireDate;
    }

    public void setArchiveExpireDate(String str) {
        this.mArchiveExpireDate = str;
    }
}
