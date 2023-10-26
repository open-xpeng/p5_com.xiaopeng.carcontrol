package com.alibaba.sdk.android.oss.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes.dex */
public class CopyObjectRequest extends OSSRequest {
    private String destinationBucketName;
    private String destinationKey;
    private Date modifiedSinceConstraint;
    private ObjectMetadata newObjectMetadata;
    private String serverSideEncryption;
    private String sourceBucketName;
    private String sourceKey;
    private Date unmodifiedSinceConstraint;
    private List<String> matchingETagConstraints = new ArrayList();
    private List<String> nonmatchingEtagConstraints = new ArrayList();

    public CopyObjectRequest(String str, String str2, String str3, String str4) {
        setSourceBucketName(str);
        setSourceKey(str2);
        setDestinationBucketName(str3);
        setDestinationKey(str4);
    }

    public String getSourceBucketName() {
        return this.sourceBucketName;
    }

    public void setSourceBucketName(String str) {
        this.sourceBucketName = str;
    }

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String str) {
        this.sourceKey = str;
    }

    public String getDestinationBucketName() {
        return this.destinationBucketName;
    }

    public void setDestinationBucketName(String str) {
        this.destinationBucketName = str;
    }

    public String getDestinationKey() {
        return this.destinationKey;
    }

    public void setDestinationKey(String str) {
        this.destinationKey = str;
    }

    public ObjectMetadata getNewObjectMetadata() {
        return this.newObjectMetadata;
    }

    public void setNewObjectMetadata(ObjectMetadata objectMetadata) {
        this.newObjectMetadata = objectMetadata;
    }

    public List<String> getMatchingETagConstraints() {
        return this.matchingETagConstraints;
    }

    public void setMatchingETagConstraints(List<String> list) {
        this.matchingETagConstraints.clear();
        if (list == null || list.isEmpty()) {
            return;
        }
        this.matchingETagConstraints.addAll(list);
    }

    public void clearMatchingETagConstraints() {
        this.matchingETagConstraints.clear();
    }

    public List<String> getNonmatchingEtagConstraints() {
        return this.nonmatchingEtagConstraints;
    }

    public void setNonmatchingETagConstraints(List<String> list) {
        this.nonmatchingEtagConstraints.clear();
        if (list == null || list.isEmpty()) {
            return;
        }
        this.nonmatchingEtagConstraints.addAll(list);
    }

    public void clearNonmatchingETagConstraints() {
        this.nonmatchingEtagConstraints.clear();
    }

    public Date getUnmodifiedSinceConstraint() {
        return this.unmodifiedSinceConstraint;
    }

    public void setUnmodifiedSinceConstraint(Date date) {
        this.unmodifiedSinceConstraint = date;
    }

    public Date getModifiedSinceConstraint() {
        return this.modifiedSinceConstraint;
    }

    public void setModifiedSinceConstraint(Date date) {
        this.modifiedSinceConstraint = date;
    }

    public String getServerSideEncryption() {
        return this.serverSideEncryption;
    }

    public void setServerSideEncryption(String str) {
        this.serverSideEncryption = str;
    }
}
