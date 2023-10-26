package com.alibaba.sdk.android.oss.model;

import com.alibaba.sdk.android.oss.common.HttpMethod;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class GeneratePresignedUrlRequest {
    private String bucketName;
    private String contentMD5;
    private String contentType;
    private long expiration;
    private String key;
    private HttpMethod method;
    private String process;
    private Map<String, String> queryParam;

    public GeneratePresignedUrlRequest(String str, String str2) {
        this(str, str2, 3600L);
    }

    public GeneratePresignedUrlRequest(String str, String str2, long j) {
        this(str, str2, 3600L, HttpMethod.GET);
    }

    public GeneratePresignedUrlRequest(String str, String str2, long j, HttpMethod httpMethod) {
        this.queryParam = new HashMap();
        this.bucketName = str;
        this.key = str2;
        this.expiration = j;
        this.method = httpMethod;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String str) {
        this.contentType = str;
    }

    public String getContentMD5() {
        return this.contentMD5;
    }

    public void setContentMD5(String str) {
        this.contentMD5 = str;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setMethod(HttpMethod httpMethod) {
        if (httpMethod != HttpMethod.GET && httpMethod != HttpMethod.PUT) {
            throw new IllegalArgumentException("Only GET or PUT is supported!");
        }
        this.method = httpMethod;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public long getExpiration() {
        return this.expiration;
    }

    public void setExpiration(long j) {
        this.expiration = j;
    }

    public Map<String, String> getQueryParameter() {
        return this.queryParam;
    }

    public void setQueryParameter(Map<String, String> map) {
        Objects.requireNonNull(map, "The argument 'queryParameter' is null.");
        Map<String, String> map2 = this.queryParam;
        if (map2 != null && map2.size() > 0) {
            this.queryParam.clear();
        }
        this.queryParam.putAll(map);
    }

    public void addQueryParameter(String str, String str2) {
        this.queryParam.put(str, str2);
    }

    public String getProcess() {
        return this.process;
    }

    public void setProcess(String str) {
        this.process = str;
    }
}
