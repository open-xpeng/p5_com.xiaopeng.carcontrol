package com.alibaba.sdk.android.httpdns;

/* loaded from: classes.dex */
public class h extends Exception {
    private int b;

    public h(int i, String str) {
        super(str);
        this.b = i;
    }

    public int getErrorCode() {
        return this.b;
    }
}
