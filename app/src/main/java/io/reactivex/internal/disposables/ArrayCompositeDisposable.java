package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes.dex */
public final class ArrayCompositeDisposable extends AtomicReferenceArray<Disposable> implements Disposable {
    private static final long serialVersionUID = 2746389416410565408L;

    public ArrayCompositeDisposable(int i) {
        super(i);
    }

    public boolean setResource(int i, Disposable disposable) {
        Disposable disposable2;
        do {
            disposable2 = get(i);
            if (disposable2 == DisposableHelper.DISPOSED) {
                disposable.dispose();
                return false;
            }
        } while (!compareAndSet(i, disposable2, disposable));
        if (disposable2 != null) {
            disposable2.dispose();
            return true;
        }
        return true;
    }

    public Disposable replaceResource(int i, Disposable disposable) {
        Disposable disposable2;
        do {
            disposable2 = get(i);
            if (disposable2 == DisposableHelper.DISPOSED) {
                disposable.dispose();
                return null;
            }
        } while (!compareAndSet(i, disposable2, disposable));
        return disposable2;
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        Disposable andSet;
        if (get(0) != DisposableHelper.DISPOSED) {
            int length = length();
            for (int i = 0; i < length; i++) {
                if (get(i) != DisposableHelper.DISPOSED && (andSet = getAndSet(i, DisposableHelper.DISPOSED)) != DisposableHelper.DISPOSED && andSet != null) {
                    andSet.dispose();
                }
            }
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return get(0) == DisposableHelper.DISPOSED;
    }
}
