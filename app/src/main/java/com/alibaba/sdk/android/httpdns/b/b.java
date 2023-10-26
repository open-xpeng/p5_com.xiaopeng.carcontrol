package com.alibaba.sdk.android.httpdns.b;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class b {
    private static f a = null;

    /* renamed from: a  reason: collision with other field name */
    private static com.alibaba.sdk.android.httpdns.c.a f80a = null;

    /* renamed from: a  reason: collision with other field name */
    private static boolean f81a = false;

    public static List<e> a() {
        ArrayList arrayList = new ArrayList();
        if (f81a) {
            arrayList.addAll(a.a());
            return arrayList;
        }
        return arrayList;
    }

    public static void a(Context context) {
        if (context != null) {
            f80a.m39b(context);
        }
    }

    public static void a(Context context, com.alibaba.sdk.android.httpdns.c.a aVar) {
        a = new a(context);
        f80a = aVar;
        if (aVar == null) {
            f80a = com.alibaba.sdk.android.httpdns.c.a.a();
        }
    }

    public static void a(e eVar) {
        if (eVar == null) {
            return;
        }
        a.a(eVar);
    }

    public static void a(boolean z) {
        f81a = z;
    }

    /* renamed from: a  reason: collision with other method in class */
    public static boolean m34a() {
        return f81a;
    }

    public static void b(e eVar) {
        if (eVar == null) {
            return;
        }
        a.b(eVar);
    }

    public static String g() {
        return f80a.g();
    }

    public static void init(Context context) {
        a(context, null);
    }
}
