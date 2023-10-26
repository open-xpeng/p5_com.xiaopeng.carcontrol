package com.xiaopeng.xpmeditation.moreless;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class MorelessToken implements Serializable {
    private String access_token;
    private long expires_in;
    private String scope;
    private String token_type;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getExpires_in() {
        return this.expires_in;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return this.scope;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getToken_type() {
        return this.token_type;
    }
}
