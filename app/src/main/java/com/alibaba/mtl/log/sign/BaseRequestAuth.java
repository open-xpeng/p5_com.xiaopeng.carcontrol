package com.alibaba.mtl.log.sign;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.j;

/* loaded from: classes.dex */
public class BaseRequestAuth implements IRequestAuth {
    private boolean D;
    private String Y;
    private String g;

    @Override // com.alibaba.mtl.log.sign.IRequestAuth
    public String getAppkey() {
        return this.g;
    }

    public String getAppSecret() {
        return this.Y;
    }

    public BaseRequestAuth(String str, String str2) {
        this.g = null;
        this.Y = null;
        this.D = false;
        this.g = str;
        this.Y = str2;
    }

    public BaseRequestAuth(String str, String str2, boolean z) {
        this.g = null;
        this.Y = null;
        this.D = false;
        this.g = str;
        this.Y = str2;
        this.D = z;
    }

    public boolean isEncode() {
        return this.D;
    }

    @Override // com.alibaba.mtl.log.sign.IRequestAuth
    public String getSign(String str) {
        if (this.g == null || this.Y == null) {
            i.a("BaseRequestAuth", "There is no appkey,please check it!");
            return null;
        } else if (str == null) {
            return null;
        } else {
            return j.a(j.m29a((str + this.Y).getBytes()));
        }
    }
}
