package io.reactivex.internal.fuseable;

import io.reactivex.FlowableSubscriber;

/* loaded from: classes2.dex */
public interface ConditionalSubscriber<T> extends FlowableSubscriber<T> {
    boolean tryOnNext(T t);
}
