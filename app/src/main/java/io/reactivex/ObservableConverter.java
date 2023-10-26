package io.reactivex;

/* loaded from: classes2.dex */
public interface ObservableConverter<T, R> {
    R apply(Observable<T> observable);
}
