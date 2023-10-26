package com.alibaba.sdk.android.httpdns;

import org.json.JSONObject;

/* loaded from: classes.dex */
class g {
    private int b;
    private String c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(int i, String str) {
        this.b = i;
        this.c = new JSONObject(str).getString("code");
    }

    public String a() {
        return this.c;
    }

    public int getErrorCode() {
        return this.b;
    }
}
