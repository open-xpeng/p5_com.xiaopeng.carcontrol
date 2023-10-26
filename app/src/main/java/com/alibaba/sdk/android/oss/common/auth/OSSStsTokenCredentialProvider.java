package com.alibaba.sdk.android.oss.common.auth;

import org.tukaani.xz.common.Util;

/* loaded from: classes.dex */
public class OSSStsTokenCredentialProvider implements OSSCredentialProvider {
    private String accessKeyId;
    private String secretKeyId;
    private String securityToken;

    public OSSStsTokenCredentialProvider(String str, String str2, String str3) {
        setAccessKeyId(str.trim());
        setSecretKeyId(str2.trim());
        setSecurityToken(str3.trim());
    }

    public OSSStsTokenCredentialProvider(OSSFederationToken oSSFederationToken) {
        setAccessKeyId(oSSFederationToken.getTempAK().trim());
        setSecretKeyId(oSSFederationToken.getTempSK().trim());
        setSecurityToken(oSSFederationToken.getSecurityToken().trim());
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeyId(String str) {
        this.accessKeyId = str;
    }

    public String getSecretKeyId() {
        return this.secretKeyId;
    }

    public void setSecretKeyId(String str) {
        this.secretKeyId = str;
    }

    public String getSecurityToken() {
        return this.securityToken;
    }

    public void setSecurityToken(String str) {
        this.securityToken = str;
    }

    @Override // com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
    public OSSFederationToken getFederationToken() {
        return new OSSFederationToken(this.accessKeyId, this.secretKeyId, this.securityToken, (long) Util.VLI_MAX);
    }
}
