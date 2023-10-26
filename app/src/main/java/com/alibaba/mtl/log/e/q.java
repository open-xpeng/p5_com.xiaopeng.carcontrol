package com.alibaba.mtl.log.e;

import com.xiaopeng.xvs.xid.sync.api.ISync;

/* compiled from: SystemProperties.java */
/* loaded from: classes.dex */
public class q {
    private static final String TAG = "q";

    public static String get(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(ISync.SYNC_CALL_METHOD_GET, String.class).invoke(cls.newInstance(), str);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String get(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(ISync.SYNC_CALL_METHOD_GET, String.class, String.class).invoke(cls.newInstance(), str, str2);
        } catch (Exception unused) {
            return "";
        }
    }
}
