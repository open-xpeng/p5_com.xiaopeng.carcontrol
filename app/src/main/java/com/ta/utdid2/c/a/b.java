package com.ta.utdid2.c.a;

import java.util.Map;

/* compiled from: MySharedPreferences.java */
/* loaded from: classes.dex */
public interface b {

    /* compiled from: MySharedPreferences.java */
    /* loaded from: classes.dex */
    public interface a {
        a a(String str);

        a a(String str, float f);

        a a(String str, int i);

        a a(String str, long j);

        a a(String str, String str2);

        a a(String str, boolean z);

        a b();

        boolean commit();
    }

    /* compiled from: MySharedPreferences.java */
    /* renamed from: com.ta.utdid2.c.a.b$b  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public interface InterfaceC0016b {
        void a(b bVar, String str);
    }

    a a();

    /* renamed from: a  reason: collision with other method in class */
    boolean mo75a();

    Map<String, ?> getAll();

    long getLong(String str, long j);

    String getString(String str, String str2);
}
