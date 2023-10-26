package io.reactivex;

/* loaded from: classes2.dex */
public interface SingleConverter<T, R> {
    R apply(Single<T> single);
}
