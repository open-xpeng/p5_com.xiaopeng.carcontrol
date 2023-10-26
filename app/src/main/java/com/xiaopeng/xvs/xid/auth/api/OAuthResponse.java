package com.xiaopeng.xvs.xid.auth.api;

import com.xiaopeng.xvs.xid.base.IResponse;

/* loaded from: classes2.dex */
public final class OAuthResponse extends OAuthException implements IResponse {
    private String mContent;

    public OAuthResponse(int i) {
        super(i);
    }

    public OAuthResponse(int i, String str) {
        super(i, str);
    }

    public OAuthResponse(String str) {
        this.mContent = str;
    }

    @Override // com.xiaopeng.xvs.xid.base.AbsException, com.xiaopeng.xvs.xid.base.IException
    public String toString() {
        return "OAuthResponse{mContent='" + this.mContent + "'} " + super.toString();
    }

    @Override // com.xiaopeng.xvs.xid.base.IResponse
    public String getContent() {
        return this.mContent;
    }
}
