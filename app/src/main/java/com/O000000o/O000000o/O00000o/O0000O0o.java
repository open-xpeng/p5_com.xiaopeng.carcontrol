package com.O000000o.O000000o.O00000o;

import java.io.IOException;

/* compiled from: MalformedJsonException.java */
/* loaded from: classes.dex */
public final class O0000O0o extends IOException {
    private static final long O000000o = 1;

    public O0000O0o(String str) {
        super(str);
    }

    public O0000O0o(String str, Throwable th) {
        super(str);
        initCause(th);
    }

    public O0000O0o(Throwable th) {
        initCause(th);
    }
}
