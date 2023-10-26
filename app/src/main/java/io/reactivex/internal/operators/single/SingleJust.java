package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposables;

/* loaded from: classes3.dex */
public final class SingleJust<T> extends Single<T> {
    final T value;

    public SingleJust(T t) {
        this.value = t;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        singleObserver.onSubscribe(Disposables.disposed());
        singleObserver.onSuccess((T) this.value);
    }
}
