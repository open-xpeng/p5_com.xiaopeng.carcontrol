package com.airbnb.lottie;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class LottieResult<V> {
    private final Throwable exception;
    private final V value;

    public LottieResult(V v) {
        this.value = v;
        this.exception = null;
    }

    public LottieResult(Throwable th) {
        this.exception = th;
        this.value = null;
    }

    public V getValue() {
        return this.value;
    }

    public Throwable getException() {
        return this.exception;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LottieResult) {
            LottieResult lottieResult = (LottieResult) obj;
            if (getValue() == null || !getValue().equals(lottieResult.getValue())) {
                if (getException() == null || lottieResult.getException() == null) {
                    return false;
                }
                return getException().toString().equals(getException().toString());
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{getValue(), getException()});
    }
}
