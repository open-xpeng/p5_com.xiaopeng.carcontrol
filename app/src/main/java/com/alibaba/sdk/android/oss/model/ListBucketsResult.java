package com.alibaba.sdk.android.oss.model;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ListBucketsResult extends OSSResult {
    private List<OSSBucketSummary> buckets = new ArrayList();
    private boolean isTruncated;
    private String marker;
    private int maxKeys;
    private String nextMarker;
    private String ownerDisplayName;
    private String ownerId;
    private String prefix;

    public void addBucket(OSSBucketSummary oSSBucketSummary) {
        this.buckets.add(oSSBucketSummary);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public String getMarker() {
        return this.marker;
    }

    public void setMarker(String str) {
        this.marker = str;
    }

    public int getMaxKeys() {
        return this.maxKeys;
    }

    public void setMaxKeys(int i) {
        this.maxKeys = i;
    }

    public boolean getTruncated() {
        return this.isTruncated;
    }

    public void setTruncated(boolean z) {
        this.isTruncated = z;
    }

    public String getNextMarker() {
        return this.nextMarker;
    }

    public void setNextMarker(String str) {
        this.nextMarker = str;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String str) {
        this.ownerId = str;
    }

    public String getOwnerDisplayName() {
        return this.ownerDisplayName;
    }

    public void setOwnerDisplayName(String str) {
        this.ownerDisplayName = str;
    }

    public List<OSSBucketSummary> getBuckets() {
        return this.buckets;
    }

    public void setBuckets(List<OSSBucketSummary> list) {
        this.buckets = list;
    }

    public void clearBucketList() {
        this.buckets.clear();
    }
}
