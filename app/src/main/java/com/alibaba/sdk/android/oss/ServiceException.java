package com.alibaba.sdk.android.oss;

import com.alibaba.sdk.android.oss.common.OSSLog;

/* loaded from: classes.dex */
public class ServiceException extends Exception {
    private static final long serialVersionUID = 430933593095358673L;
    private String errorCode;
    private String hostId;
    private String partEtag;
    private String partNumber;
    private String rawMessage;
    private String requestId;
    private int statusCode;

    public String getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(String str) {
        this.partNumber = str;
    }

    public String getPartEtag() {
        return this.partEtag;
    }

    public void setPartEtag(String str) {
        this.partEtag = str;
    }

    public ServiceException(int i, String str, String str2, String str3, String str4, String str5) {
        super(str);
        this.statusCode = i;
        this.errorCode = str2;
        this.requestId = str3;
        this.hostId = str4;
        this.rawMessage = str5;
        OSSLog.logThrowable2Local(this);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getHostId() {
        return this.hostId;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "[StatusCode]: " + this.statusCode + ", [Code]: " + getErrorCode() + ", [Message]: " + getMessage() + ", [Requestid]: " + getRequestId() + ", [HostId]: " + getHostId() + ", [RawMessage]: " + getRawMessage();
    }

    public String getRawMessage() {
        return this.rawMessage;
    }
}
