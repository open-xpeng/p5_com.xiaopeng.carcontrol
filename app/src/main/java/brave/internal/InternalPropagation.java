package brave.internal;

import brave.propagation.SamplingFlags;
import brave.propagation.TraceContext;
import java.util.List;

/* loaded from: classes.dex */
public abstract class InternalPropagation {
    public static final int FLAG_DEBUG = 8;
    public static final int FLAG_LOCAL_ROOT = 64;
    public static final int FLAG_SAMPLED = 2;
    public static final int FLAG_SAMPLED_LOCAL = 32;
    public static final int FLAG_SAMPLED_SET = 4;
    public static final int FLAG_SHARED = 16;
    public static InternalPropagation instance;

    public static int sampled(boolean z, int i) {
        return z ? i | 6 : (i | 4) & (-3);
    }

    public abstract int flags(SamplingFlags samplingFlags);

    public abstract TraceContext newTraceContext(int i, long j, long j2, long j3, long j4, long j5, List<Object> list);

    public abstract TraceContext withExtra(TraceContext traceContext, List<Object> list);

    public abstract TraceContext withFlags(TraceContext traceContext, int i);
}
