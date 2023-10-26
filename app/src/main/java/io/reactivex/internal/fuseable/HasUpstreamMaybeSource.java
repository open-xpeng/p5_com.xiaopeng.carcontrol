package io.reactivex.internal.fuseable;

import io.reactivex.MaybeSource;

/* loaded from: classes2.dex */
public interface HasUpstreamMaybeSource<T> {
    MaybeSource<T> source();
}
