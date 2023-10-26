package com.lzy.okgo.adapter;

/* loaded from: classes.dex */
public class DefaultCallAdapter<T> implements CallAdapter<T, Call<T>> {
    @Override // com.lzy.okgo.adapter.CallAdapter
    public Call<T> adapt(Call<T> call, AdapterParam adapterParam) {
        return call;
    }
}
