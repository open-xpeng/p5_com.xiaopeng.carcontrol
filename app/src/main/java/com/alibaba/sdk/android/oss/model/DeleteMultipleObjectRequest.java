package com.alibaba.sdk.android.oss.model;

import java.util.List;

/* loaded from: classes.dex */
public class DeleteMultipleObjectRequest extends OSSRequest {
    private String bucketName;
    private boolean isQuiet;
    private List<String> objectKeys;

    public DeleteMultipleObjectRequest(String str, List<String> list, Boolean bool) {
        setBucketName(str);
        setObjectKeys(list);
        setQuiet(bool);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public List<String> getObjectKeys() {
        return this.objectKeys;
    }

    public void setObjectKeys(List<String> list) {
        this.objectKeys = list;
    }

    public Boolean getQuiet() {
        return Boolean.valueOf(this.isQuiet);
    }

    public void setQuiet(Boolean bool) {
        this.isQuiet = bool.booleanValue();
    }
}
