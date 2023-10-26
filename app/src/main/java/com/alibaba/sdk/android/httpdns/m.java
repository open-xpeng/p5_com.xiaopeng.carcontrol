package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import java.util.concurrent.Callable;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class m implements Callable<String[]> {
    private static Context a;

    /* renamed from: a  reason: collision with other field name */
    private o f92a;
    private String hostName;
    private static d hostManager = d.a();

    /* renamed from: a  reason: collision with other field name */
    private static final Object f91a = new Object();
    private int d = 1;
    private String e = null;

    /* renamed from: e  reason: collision with other field name */
    private String[] f95e = f.d;

    /* renamed from: d  reason: collision with other field name */
    private boolean f94d = false;

    /* renamed from: d  reason: collision with other field name */
    private long f93d = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(String str, o oVar) {
        this.hostName = str;
        this.f92a = oVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setContext(Context context) {
        a = context;
    }

    public void a(int i) {
        if (i >= 0) {
            this.d = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0400 A[Catch: all -> 0x042d, TryCatch #5 {all -> 0x042d, blocks: (B:76:0x03f2, B:78:0x0400, B:79:0x0407), top: B:110:0x03f2 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0407 A[Catch: all -> 0x042d, TRY_LEAVE, TryCatch #5 {all -> 0x042d, blocks: (B:76:0x03f2, B:78:0x0400, B:79:0x0407), top: B:110:0x03f2 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x040f  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0414 A[Catch: IOException -> 0x0418, TRY_ENTER, TryCatch #2 {IOException -> 0x0418, blocks: (B:62:0x03cc, B:64:0x03d1, B:83:0x0414, B:87:0x041c), top: B:107:0x0049 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x041c A[Catch: IOException -> 0x0418, TRY_LEAVE, TryCatch #2 {IOException -> 0x0418, blocks: (B:62:0x03cc, B:64:0x03d1, B:83:0x0414, B:87:0x041c), top: B:107:0x0049 }] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v107 */
    /* JADX WARN: Type inference failed for: r3v108 */
    /* JADX WARN: Type inference failed for: r3v46 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v7 */
    @Override // java.util.concurrent.Callable
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String[] call() {
        /*
            Method dump skipped, instructions count: 1093
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.m.call():java.lang.String[]");
    }
}
