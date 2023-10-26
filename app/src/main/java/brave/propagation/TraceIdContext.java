package brave.propagation;

import brave.internal.HexCodec;
import brave.internal.InternalPropagation;
import brave.internal.Nullable;

/* loaded from: classes.dex */
public final class TraceIdContext extends SamplingFlags {
    final long traceId;
    final long traceIdHigh;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long traceIdHigh() {
        return this.traceIdHigh;
    }

    public long traceId() {
        return this.traceId;
    }

    public Builder toBuilder() {
        Builder builder = new Builder();
        builder.flags = this.flags;
        builder.traceIdHigh = this.traceIdHigh;
        builder.traceId = this.traceId;
        return builder;
    }

    @Override // brave.propagation.SamplingFlags
    public String toString() {
        long j = this.traceIdHigh;
        int i = 0;
        boolean z = j != 0;
        char[] cArr = new char[z ? 32 : 16];
        if (z) {
            HexCodec.writeHexLong(cArr, 0, j);
            i = 16;
        }
        HexCodec.writeHexLong(cArr, i, this.traceId);
        return new String(cArr);
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        int flags;
        long traceId;
        long traceIdHigh;

        public Builder traceIdHigh(long j) {
            this.traceIdHigh = j;
            return this;
        }

        public Builder traceId(long j) {
            this.traceId = j;
            return this;
        }

        public Builder sampled(boolean z) {
            this.flags = InternalPropagation.sampled(z, this.flags);
            return this;
        }

        public Builder sampled(@Nullable Boolean bool) {
            if (bool == null) {
                this.flags &= -7;
                return this;
            }
            return sampled(bool.booleanValue());
        }

        public Builder debug(boolean z) {
            this.flags = SamplingFlags.debug(z, this.flags);
            return this;
        }

        public final TraceIdContext build() {
            if (this.traceId == 0) {
                throw new IllegalStateException("Missing: traceId");
            }
            return new TraceIdContext(this.flags, this.traceIdHigh, this.traceId);
        }

        Builder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TraceIdContext(int i, long j, long j2) {
        super(i);
        this.traceIdHigh = j;
        this.traceId = j2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof TraceIdContext) {
            TraceIdContext traceIdContext = (TraceIdContext) obj;
            return this.traceIdHigh == traceIdContext.traceIdHigh && this.traceId == traceIdContext.traceId;
        }
        return false;
    }

    public int hashCode() {
        long j = this.traceIdHigh;
        long j2 = this.traceId;
        return ((((int) (j ^ (j >>> 32))) ^ 1000003) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2));
    }
}
