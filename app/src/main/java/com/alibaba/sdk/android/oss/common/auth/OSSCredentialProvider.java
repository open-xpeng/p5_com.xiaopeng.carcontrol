package com.alibaba.sdk.android.oss.common.auth;

import com.alibaba.sdk.android.oss.ClientException;

/* loaded from: classes.dex */
public interface OSSCredentialProvider {
    OSSFederationToken getFederationToken() throws ClientException;
}
