package com.xiaopeng.xvs.xid.base;

/* loaded from: classes2.dex */
public interface IEvent<T, K> {
    T getData();

    K getType();

    IEvent setData(T t);

    IEvent setType(K k);
}
