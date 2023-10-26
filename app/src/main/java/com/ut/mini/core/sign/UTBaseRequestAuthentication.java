package com.ut.mini.core.sign;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.j;

/* loaded from: classes.dex */
public class UTBaseRequestAuthentication implements IUTRequestAuthentication {
    private boolean D;
    private String Y;
    private String g;

    @Override // com.ut.mini.core.sign.IUTRequestAuthentication
    public String getAppkey() {
        return this.g;
    }

    public String getAppSecret() {
        return this.Y;
    }

    public UTBaseRequestAuthentication(String str, String str2) {
        this.g = null;
        this.Y = null;
        this.D = false;
        this.g = str;
        this.Y = str2;
    }

    public UTBaseRequestAuthentication(String str, String str2, boolean z) {
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

    @Override // com.ut.mini.core.sign.IUTRequestAuthentication
    public String getSign(String str) {
        if (this.g == null || this.Y == null) {
            i.a("UTBaseRequestAuthentication", "There is no appkey,please check it!");
            return null;
        } else if (str == null) {
            return null;
        } else {
            return j.a(j.m29a((str + this.Y).getBytes()));
        }
    }
}
