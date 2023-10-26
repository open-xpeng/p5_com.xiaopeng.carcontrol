package com.O000000o.O000000o.O00000Oo;

import java.util.Objects;

/* compiled from: $Gson$Preconditions.java */
/* loaded from: classes.dex */
public final class O000000o {
    public static <T> T O000000o(T t) {
        Objects.requireNonNull(t);
        return t;
    }

    public static void O000000o(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }
}
