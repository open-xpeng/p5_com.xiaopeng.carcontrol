package com.alibaba.mtl.appmonitor.d;

import android.text.TextUtils;
import java.util.Set;

/* compiled from: AccurateSampleCondition.java */
/* loaded from: classes.dex */
public class b {
    private a a;
    private Set<String> c;

    /* compiled from: AccurateSampleCondition.java */
    /* loaded from: classes.dex */
    private enum a {
        IN,
        NOT_IN
    }

    public boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean contains = this.c.contains(str);
        return this.a == a.IN ? contains : !contains;
    }
}
