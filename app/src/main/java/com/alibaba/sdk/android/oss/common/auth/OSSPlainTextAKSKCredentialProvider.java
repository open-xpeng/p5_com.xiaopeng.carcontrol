package com.alibaba.sdk.android.oss.common.auth;

@Deprecated
/* loaded from: classes.dex */
public class OSSPlainTextAKSKCredentialProvider implements OSSCredentialProvider {
    private String accessKeyId;
    private String accessKeySecret;

    @Override // com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
    public OSSFederationToken getFederationToken() {
        return null;
    }

    public OSSPlainTextAKSKCredentialProvider(String str, String str2) {
        setAccessKeyId(str.trim());
        setAccessKeySecret(str2.trim());
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeyId(String str) {
        this.accessKeyId = str;
    }

    public String getAccessKeySecret() {
        return this.accessKeySecret;
    }

    public void setAccessKeySecret(String str) {
        this.accessKeySecret = str;
    }
}
