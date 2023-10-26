package brave;

import brave.propagation.TraceContext;

/* loaded from: classes.dex */
public abstract class ScopedSpan {
    public abstract ScopedSpan annotate(String str);

    public abstract TraceContext context();

    public abstract ScopedSpan error(Throwable th);

    public abstract void finish();

    public abstract boolean isNoop();

    public abstract ScopedSpan tag(String str, String str2);
}
