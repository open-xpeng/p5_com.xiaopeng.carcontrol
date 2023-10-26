package brave.propagation;

import brave.internal.InternalPropagation;
import brave.internal.Lists;
import brave.internal.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class TraceContextOrSamplingFlags {
    final List<Object> extra;
    final int type;
    final SamplingFlags value;
    public static final TraceContextOrSamplingFlags EMPTY = new TraceContextOrSamplingFlags(3, SamplingFlags.EMPTY, Collections.emptyList());
    public static final TraceContextOrSamplingFlags NOT_SAMPLED = new TraceContextOrSamplingFlags(3, SamplingFlags.NOT_SAMPLED, Collections.emptyList());
    public static final TraceContextOrSamplingFlags SAMPLED = new TraceContextOrSamplingFlags(3, SamplingFlags.SAMPLED, Collections.emptyList());
    public static final TraceContextOrSamplingFlags DEBUG = new TraceContextOrSamplingFlags(3, SamplingFlags.DEBUG, Collections.emptyList());

    public static Builder newBuilder() {
        return new Builder();
    }

    @Nullable
    public Boolean sampled() {
        return this.value.sampled();
    }

    public final boolean sampledLocal() {
        return (this.value.flags & 32) == 32;
    }

    @Deprecated
    public TraceContextOrSamplingFlags sampled(@Nullable Boolean bool) {
        if (bool != null) {
            return sampled(bool.booleanValue());
        }
        int i = this.value.flags & (-5) & (-3);
        return i == this.value.flags ? this : withFlags(i);
    }

    public TraceContextOrSamplingFlags sampled(boolean z) {
        int sampled = InternalPropagation.sampled(z, this.value.flags);
        return sampled == this.value.flags ? this : withFlags(sampled);
    }

    @Nullable
    public TraceContext context() {
        if (this.type == 1) {
            return (TraceContext) this.value;
        }
        return null;
    }

    @Nullable
    public TraceIdContext traceIdContext() {
        if (this.type == 2) {
            return (TraceIdContext) this.value;
        }
        return null;
    }

    @Nullable
    public SamplingFlags samplingFlags() {
        if (this.type == 3) {
            return this.value;
        }
        return null;
    }

    public final List<Object> extra() {
        return this.extra;
    }

    public final Builder toBuilder() {
        Builder builder = new Builder();
        builder.type = this.type;
        builder.value = this.value;
        builder.extra = this.extra;
        return builder;
    }

    public String toString() {
        return "{value=" + this.value + ", extra=" + this.extra + "}";
    }

    public static TraceContextOrSamplingFlags create(TraceContext traceContext) {
        return new TraceContextOrSamplingFlags(1, traceContext, Collections.emptyList());
    }

    public static TraceContextOrSamplingFlags create(TraceIdContext traceIdContext) {
        return new TraceContextOrSamplingFlags(2, traceIdContext, Collections.emptyList());
    }

    public static TraceContextOrSamplingFlags create(SamplingFlags samplingFlags) {
        return samplingFlags == SamplingFlags.SAMPLED ? SAMPLED : samplingFlags == SamplingFlags.EMPTY ? EMPTY : samplingFlags == SamplingFlags.NOT_SAMPLED ? NOT_SAMPLED : samplingFlags == SamplingFlags.DEBUG ? DEBUG : new TraceContextOrSamplingFlags(3, samplingFlags, Collections.emptyList());
    }

    public static TraceContextOrSamplingFlags create(@Nullable Boolean bool, boolean z) {
        if (z) {
            return DEBUG;
        }
        if (bool == null) {
            return EMPTY;
        }
        return bool.booleanValue() ? SAMPLED : NOT_SAMPLED;
    }

    TraceContextOrSamplingFlags(int i, SamplingFlags samplingFlags, List<Object> list) {
        Objects.requireNonNull(samplingFlags, "value == null");
        Objects.requireNonNull(list, "extra == null");
        this.type = i;
        this.value = samplingFlags;
        this.extra = list;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        List<Object> extra = Collections.emptyList();
        boolean sampledLocal = false;
        int type;
        SamplingFlags value;

        public final Builder context(TraceContext traceContext) {
            Objects.requireNonNull(traceContext, "context == null");
            this.type = 1;
            this.value = traceContext;
            return this;
        }

        public final Builder traceIdContext(TraceIdContext traceIdContext) {
            Objects.requireNonNull(traceIdContext, "traceIdContext == null");
            this.type = 2;
            this.value = traceIdContext;
            return this;
        }

        public Builder sampledLocal() {
            this.sampledLocal = true;
            return this;
        }

        public final Builder samplingFlags(SamplingFlags samplingFlags) {
            Objects.requireNonNull(samplingFlags, "samplingFlags == null");
            this.type = 3;
            this.value = samplingFlags;
            return this;
        }

        public final Builder extra(List<Object> list) {
            Objects.requireNonNull(list, "extra == null");
            this.extra = list;
            return this;
        }

        public final Builder addExtra(Object obj) {
            Objects.requireNonNull(obj, "extra == null");
            if (!(this.extra instanceof ArrayList)) {
                this.extra = new ArrayList(this.extra);
            }
            this.extra.add(obj);
            return this;
        }

        public final TraceContextOrSamplingFlags build() {
            TraceContextOrSamplingFlags traceContextOrSamplingFlags;
            TraceContext withExtra;
            if (!this.extra.isEmpty() && this.type == 1) {
                TraceContext traceContext = (TraceContext) this.value;
                if (traceContext.extra().isEmpty()) {
                    withExtra = InternalPropagation.instance.withExtra(traceContext, Lists.ensureImmutable(this.extra));
                } else {
                    withExtra = InternalPropagation.instance.withExtra(traceContext, Lists.concatImmutableLists(traceContext.extra(), this.extra));
                }
                traceContextOrSamplingFlags = new TraceContextOrSamplingFlags(this.type, withExtra, Collections.emptyList());
            } else {
                traceContextOrSamplingFlags = new TraceContextOrSamplingFlags(this.type, this.value, Lists.ensureImmutable(this.extra));
            }
            return !this.sampledLocal ? traceContextOrSamplingFlags : traceContextOrSamplingFlags.withFlags(this.value.flags | 32);
        }

        Builder() {
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof TraceContextOrSamplingFlags) {
            TraceContextOrSamplingFlags traceContextOrSamplingFlags = (TraceContextOrSamplingFlags) obj;
            return this.type == traceContextOrSamplingFlags.type && this.value.equals(traceContextOrSamplingFlags.value) && this.extra.equals(traceContextOrSamplingFlags.extra);
        }
        return false;
    }

    public int hashCode() {
        return ((((this.type ^ 1000003) * 1000003) ^ this.value.hashCode()) * 1000003) ^ this.extra.hashCode();
    }

    TraceContextOrSamplingFlags withFlags(int i) {
        int i2 = this.type;
        if (i2 == 1) {
            return new TraceContextOrSamplingFlags(this.type, InternalPropagation.instance.withFlags((TraceContext) this.value, i), this.extra);
        } else if (i2 == 2) {
            return new TraceContextOrSamplingFlags(this.type, idContextWithFlags(i), this.extra);
        } else if (i2 == 3) {
            SamplingFlags samplingFlags = SamplingFlags.toSamplingFlags(i);
            return this.extra.isEmpty() ? create(samplingFlags) : new TraceContextOrSamplingFlags(this.type, samplingFlags, this.extra);
        } else {
            throw new AssertionError("programming error");
        }
    }

    TraceIdContext idContextWithFlags(int i) {
        TraceIdContext traceIdContext = (TraceIdContext) this.value;
        return new TraceIdContext(i, traceIdContext.traceIdHigh, traceIdContext.traceId);
    }
}
