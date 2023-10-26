package brave.propagation;

import brave.internal.InternalPropagation;
import brave.internal.Nullable;
import java.util.List;

/* loaded from: classes.dex */
public class SamplingFlags {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final SamplingFlags DEBUG;
    static final SamplingFlags DEBUG_SAMPLED_LOCAL;
    public static final SamplingFlags EMPTY = new SamplingFlags(0);
    static final SamplingFlags EMPTY_SAMPLED_LOCAL;
    public static final SamplingFlags NOT_SAMPLED;
    static final SamplingFlags NOT_SAMPLED_SAMPLED_LOCAL;
    public static final SamplingFlags SAMPLED;
    static final SamplingFlags SAMPLED_SAMPLED_LOCAL;
    final int flags;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int debug(boolean z, int i) {
        return z ? i | 14 : i & (-9);
    }

    static boolean debug(int i) {
        return (i & 8) == 8;
    }

    static {
        SamplingFlags samplingFlags = new SamplingFlags(4);
        NOT_SAMPLED = samplingFlags;
        SamplingFlags samplingFlags2 = new SamplingFlags(samplingFlags.flags | 2);
        SAMPLED = samplingFlags2;
        SamplingFlags samplingFlags3 = new SamplingFlags(samplingFlags2.flags | 8);
        DEBUG = samplingFlags3;
        InternalPropagation.instance = new InternalPropagation() { // from class: brave.propagation.SamplingFlags.1
            @Override // brave.internal.InternalPropagation
            public int flags(SamplingFlags samplingFlags4) {
                return samplingFlags4.flags;
            }

            @Override // brave.internal.InternalPropagation
            public TraceContext newTraceContext(int i, long j, long j2, long j3, long j4, long j5, List<Object> list) {
                return new TraceContext(i, j, j2, j3, j4, j5, list);
            }

            @Override // brave.internal.InternalPropagation
            public TraceContext withExtra(TraceContext traceContext, List<Object> list) {
                return traceContext.withExtra(list);
            }

            @Override // brave.internal.InternalPropagation
            public TraceContext withFlags(TraceContext traceContext, int i) {
                return traceContext.withFlags(i);
            }
        };
        EMPTY_SAMPLED_LOCAL = new SamplingFlags(32);
        NOT_SAMPLED_SAMPLED_LOCAL = new SamplingFlags(samplingFlags.flags | 32);
        SAMPLED_SAMPLED_LOCAL = new SamplingFlags(samplingFlags2.flags | 32);
        DEBUG_SAMPLED_LOCAL = new SamplingFlags(samplingFlags3.flags | 32);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SamplingFlags(int i) {
        this.flags = i;
    }

    @Nullable
    public final Boolean sampled() {
        int i = this.flags;
        if ((i & 4) == 4) {
            return Boolean.valueOf((i & 2) == 2);
        }
        return null;
    }

    public final boolean sampledLocal() {
        return (this.flags & 32) == 32;
    }

    public final boolean debug() {
        return debug(this.flags);
    }

    public String toString() {
        return "SamplingFlags(sampled=" + sampled() + ", sampledLocal=" + sampledLocal() + ", debug=" + debug() + ")";
    }

    @Deprecated
    /* loaded from: classes.dex */
    public static final class Builder {
        int flags = 0;

        public Builder sampled(@Nullable Boolean bool) {
            if (bool == null) {
                this.flags &= -7;
                return this;
            }
            this.flags = InternalPropagation.sampled(bool.booleanValue(), this.flags);
            return this;
        }

        public Builder debug(boolean z) {
            this.flags = SamplingFlags.debug(z, this.flags);
            return this;
        }

        public static SamplingFlags build(@Nullable Boolean bool) {
            if (bool != null) {
                return bool.booleanValue() ? SamplingFlags.SAMPLED : SamplingFlags.NOT_SAMPLED;
            }
            return SamplingFlags.EMPTY;
        }

        public SamplingFlags build() {
            return SamplingFlags.toSamplingFlags(this.flags);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static SamplingFlags toSamplingFlags(int i) {
        if (i != 0) {
            if (i != 4) {
                if (i != 6) {
                    if (i != 14) {
                        if (i != 32) {
                            if (i != 36) {
                                if (i != 38) {
                                    if (i == 46) {
                                        return DEBUG_SAMPLED_LOCAL;
                                    }
                                    return new SamplingFlags(i);
                                }
                                return SAMPLED_SAMPLED_LOCAL;
                            }
                            return NOT_SAMPLED_SAMPLED_LOCAL;
                        }
                        return EMPTY_SAMPLED_LOCAL;
                    }
                    return DEBUG;
                }
                return SAMPLED;
            }
            return NOT_SAMPLED;
        }
        return EMPTY;
    }
}
