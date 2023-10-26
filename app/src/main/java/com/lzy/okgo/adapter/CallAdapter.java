package com.lzy.okgo.adapter;

/* loaded from: classes.dex */
public interface CallAdapter<T, R> {
    R adapt(Call<T> call, AdapterParam adapterParam);
}
