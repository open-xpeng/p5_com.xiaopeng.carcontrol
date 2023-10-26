package com.alibaba.mtl.log.c;

import java.util.List;

/* compiled from: ILogStore.java */
/* loaded from: classes.dex */
public interface a {
    int a(List<com.alibaba.mtl.log.model.a> list);

    List<com.alibaba.mtl.log.model.a> a(String str, int i);

    /* renamed from: a  reason: collision with other method in class */
    boolean mo25a(List<com.alibaba.mtl.log.model.a> list);

    void c(String str, String str2);

    void clear();

    void e(int i);

    int g();
}
