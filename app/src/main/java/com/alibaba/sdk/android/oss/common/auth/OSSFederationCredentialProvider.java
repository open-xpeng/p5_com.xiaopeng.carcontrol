package com.alibaba.sdk.android.oss.common.auth;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.utils.DateUtil;

/* loaded from: classes.dex */
public abstract class OSSFederationCredentialProvider implements OSSCredentialProvider {
    private volatile OSSFederationToken cachedToken;

    @Override // com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
    public abstract OSSFederationToken getFederationToken() throws ClientException;

    public synchronized OSSFederationToken getValidFederationToken() throws ClientException {
        if (this.cachedToken == null || DateUtil.getFixedSkewedTimeMillis() / 1000 > this.cachedToken.getExpiration() - 300) {
            if (this.cachedToken != null) {
                OSSLog.logDebug("token expired! current time: " + (DateUtil.getFixedSkewedTimeMillis() / 1000) + " token expired: " + this.cachedToken.getExpiration());
            }
            this.cachedToken = getFederationToken();
        }
        return this.cachedToken;
    }

    public OSSFederationToken getCachedToken() {
        return this.cachedToken;
    }
}
