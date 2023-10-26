package io.reactivex.parallel;

/* loaded from: classes3.dex */
public interface ParallelFlowableConverter<T, R> {
    R apply(ParallelFlowable<T> parallelFlowable);
}
