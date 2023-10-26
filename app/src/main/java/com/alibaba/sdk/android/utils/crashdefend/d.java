package com.alibaba.sdk.android.utils.crashdefend;

import android.util.Log;

/* compiled from: CrashDefendSDKInfo.java */
/* loaded from: classes.dex */
public class d implements Cloneable {
    public int a;

    /* renamed from: a  reason: collision with other field name */
    public long f127a;

    /* renamed from: a  reason: collision with other field name */
    public String f129a;
    public int b;

    /* renamed from: b  reason: collision with other field name */
    public long f130b;

    /* renamed from: b  reason: collision with other field name */
    public String f131b;

    /* renamed from: c  reason: collision with other field name */
    public long f132c;
    public int crashCount;
    public volatile int c = 0;
    public int d = 0;
    public volatile boolean e = false;
    public boolean f = false;

    /* renamed from: a  reason: collision with other field name */
    public SDKMessageCallback f128a = null;

    public Object clone() {
        try {
            return (d) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("CrashSDK", "clone fail:", e);
            return null;
        }
    }
}
