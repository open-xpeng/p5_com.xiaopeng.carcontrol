package io.reactivex;

/* loaded from: classes2.dex */
public interface MaybeConverter<T, R> {
    R apply(Maybe<T> maybe);
}
