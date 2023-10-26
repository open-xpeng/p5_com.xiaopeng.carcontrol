package io.reactivex;

/* loaded from: classes2.dex */
public interface ObservableSource<T> {
    void subscribe(Observer<? super T> observer);
}
