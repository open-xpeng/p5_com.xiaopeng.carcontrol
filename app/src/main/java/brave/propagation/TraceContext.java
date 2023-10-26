package brave.propagation;

import brave.internal.HexCodec;
import brave.internal.InternalPropagation;
import brave.internal.Lists;
import brave.internal.Nullable;
import brave.internal.Platform;
import brave.propagation.Propagation;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class TraceContext extends SamplingFlags {
    final List<Object> extra;
    volatile int hashCode;
    final long localRootId;
    volatile String localRootIdString;
    final long parentId;
    volatile String parentIdString;
    final long spanId;
    volatile String spanIdString;
    final long traceId;
    final long traceIdHigh;
    volatile String traceIdString;

    /* loaded from: classes.dex */
    public interface Extractor<C> {
        TraceContextOrSamplingFlags extract(C c);
    }

    /* loaded from: classes.dex */
    public interface Injector<C> {
        void inject(TraceContext traceContext, C c);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long traceIdHigh() {
        return this.traceIdHigh;
    }

    public long traceId() {
        return this.traceId;
    }

    public long localRootId() {
        return this.localRootId;
    }

    public boolean isLocalRoot() {
        return (this.flags & 64) == 64;
    }

    @Nullable
    public final Long parentId() {
        long j = this.parentId;
        if (j != 0) {
            return Long.valueOf(j);
        }
        return null;
    }

    public long parentIdAsLong() {
        return this.parentId;
    }

    public long spanId() {
        return this.spanId;
    }

    public boolean shared() {
        return (this.flags & 16) == 16;
    }

    public List<Object> extra() {
        return this.extra;
    }

    @Nullable
    public <T> T findExtra(Class<T> cls) {
        return (T) findExtra(cls, this.extra);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String traceIdString() {
        String str = this.traceIdString;
        if (str == null) {
            long j = this.traceIdHigh;
            if (j != 0) {
                char[] cArr = new char[32];
                HexCodec.writeHexLong(cArr, 0, j);
                HexCodec.writeHexLong(cArr, 16, this.traceId);
                str = new String(cArr);
            } else {
                str = HexCodec.toLowerHex(this.traceId);
            }
            this.traceIdString = str;
        }
        return str;
    }

    @Nullable
    public String parentIdString() {
        String str = this.parentIdString;
        if (str == null) {
            long j = this.parentId;
            if (j != 0) {
                String lowerHex = HexCodec.toLowerHex(j);
                this.parentIdString = lowerHex;
                return lowerHex;
            }
            return str;
        }
        return str;
    }

    @Nullable
    public String localRootIdString() {
        String str = this.localRootIdString;
        if (str == null) {
            long j = this.localRootId;
            if (j != 0) {
                String lowerHex = HexCodec.toLowerHex(j);
                this.localRootIdString = lowerHex;
                return lowerHex;
            }
            return str;
        }
        return str;
    }

    public String spanIdString() {
        String str = this.spanIdString;
        if (str == null) {
            String lowerHex = HexCodec.toLowerHex(this.spanId);
            this.spanIdString = lowerHex;
            return lowerHex;
        }
        return str;
    }

    @Override // brave.propagation.SamplingFlags
    public String toString() {
        long j = this.traceIdHigh;
        int i = 0;
        boolean z = j != 0;
        char[] cArr = new char[((z ? 3 : 2) * 16) + 1];
        if (z) {
            HexCodec.writeHexLong(cArr, 0, j);
            i = 16;
        }
        HexCodec.writeHexLong(cArr, i, this.traceId);
        int i2 = i + 16;
        cArr[i2] = '/';
        HexCodec.writeHexLong(cArr, i2 + 1, this.spanId);
        return new String(cArr);
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        List<Object> extra;
        int flags;
        long localRootId;
        long parentId;
        long spanId;
        long traceId;
        long traceIdHigh;

        Builder(TraceContext traceContext) {
            this.extra = Collections.emptyList();
            this.traceIdHigh = traceContext.traceIdHigh;
            this.traceId = traceContext.traceId;
            this.localRootId = traceContext.localRootId;
            this.parentId = traceContext.parentId;
            this.spanId = traceContext.spanId;
            this.flags = traceContext.flags;
            this.extra = traceContext.extra;
        }

        public Builder traceIdHigh(long j) {
            this.traceIdHigh = j;
            return this;
        }

        public Builder traceId(long j) {
            this.traceId = j;
            return this;
        }

        public Builder parentId(long j) {
            this.parentId = j;
            return this;
        }

        public Builder parentId(@Nullable Long l) {
            if (l == null) {
                l = 0L;
            }
            this.parentId = l.longValue();
            return this;
        }

        public Builder spanId(long j) {
            this.spanId = j;
            return this;
        }

        public Builder sampledLocal(boolean z) {
            if (z) {
                this.flags |= 32;
            } else {
                this.flags &= -33;
            }
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

        public Builder shared(boolean z) {
            if (z) {
                this.flags |= 16;
            } else {
                this.flags &= -17;
            }
            return this;
        }

        public final Builder extra(List<Object> list) {
            this.extra = list;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final boolean parseTraceId(String str, Object obj) {
            if (isNull(obj, str)) {
                return false;
            }
            int length = str.length();
            if (invalidIdLength(obj, length, 32)) {
                return false;
            }
            int max = Math.max(0, length - 16);
            if (max > 0) {
                long lenientLowerHexToUnsignedLong = HexCodec.lenientLowerHexToUnsignedLong(str, 0, max);
                this.traceIdHigh = lenientLowerHexToUnsignedLong;
                if (lenientLowerHexToUnsignedLong == 0) {
                    maybeLogNotLowerHex(str);
                    return false;
                }
            }
            long lenientLowerHexToUnsignedLong2 = HexCodec.lenientLowerHexToUnsignedLong(str, max, length);
            this.traceId = lenientLowerHexToUnsignedLong2;
            if (lenientLowerHexToUnsignedLong2 == 0) {
                maybeLogNotLowerHex(str);
                return false;
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final <C, K> boolean parseParentId(Propagation.Getter<C, K> getter, C c, K k) {
            String str = getter.get(c, k);
            if (str == null) {
                return true;
            }
            int length = str.length();
            if (invalidIdLength(k, length, 16)) {
                return false;
            }
            long lenientLowerHexToUnsignedLong = HexCodec.lenientLowerHexToUnsignedLong(str, 0, length);
            this.parentId = lenientLowerHexToUnsignedLong;
            if (lenientLowerHexToUnsignedLong != 0) {
                return true;
            }
            maybeLogNotLowerHex(str);
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final <C, K> boolean parseSpanId(Propagation.Getter<C, K> getter, C c, K k) {
            String str = getter.get(c, k);
            if (isNull(k, str)) {
                return false;
            }
            int length = str.length();
            if (invalidIdLength(k, length, 16)) {
                return false;
            }
            long lenientLowerHexToUnsignedLong = HexCodec.lenientLowerHexToUnsignedLong(str, 0, length);
            this.spanId = lenientLowerHexToUnsignedLong;
            if (lenientLowerHexToUnsignedLong == 0) {
                maybeLogNotLowerHex(str);
                return false;
            }
            return true;
        }

        boolean invalidIdLength(Object obj, int i, int i2) {
            if (i <= 1 || i > i2) {
                Platform.get().log(i2 == 32 ? "{0} should be a 1 to 32 character lower-hex string with no prefix" : "{0} should be a 1 to 16 character lower-hex string with no prefix", obj, null);
                return true;
            }
            return false;
        }

        boolean isNull(Object obj, String str) {
            if (str != null) {
                return false;
            }
            Platform.get().log("{0} was null", obj, null);
            return true;
        }

        void maybeLogNotLowerHex(String str) {
            Platform.get().log("{0} is not a lower-hex string", str, null);
        }

        public final TraceContext build() {
            String str = this.traceId == 0 ? " traceId" : "";
            if (this.spanId == 0) {
                str = str + " spanId";
            }
            if (!"".equals(str)) {
                throw new IllegalStateException("Missing: " + str);
            }
            return new TraceContext(this.flags, this.traceIdHigh, this.traceId, this.localRootId, this.parentId, this.spanId, Lists.ensureImmutable(this.extra));
        }

        Builder() {
            this.extra = Collections.emptyList();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TraceContext withExtra(List<Object> list) {
        return new TraceContext(this.flags, this.traceIdHigh, this.traceId, this.localRootId, this.parentId, this.spanId, list);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TraceContext withFlags(int i) {
        return new TraceContext(i, this.traceIdHigh, this.traceId, this.localRootId, this.parentId, this.spanId, this.extra);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TraceContext(int i, long j, long j2, long j3, long j4, long j5, List<Object> list) {
        super(i);
        this.traceIdHigh = j;
        this.traceId = j2;
        this.localRootId = j3;
        this.parentId = j4;
        this.spanId = j5;
        this.extra = list;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof WeakReference) {
            obj = ((WeakReference) obj).get();
        }
        if (obj instanceof TraceContext) {
            TraceContext traceContext = (TraceContext) obj;
            return this.traceIdHigh == traceContext.traceIdHigh && this.traceId == traceContext.traceId && this.spanId == traceContext.spanId && (this.flags & 16) == (traceContext.flags & 16);
        }
        return false;
    }

    public int hashCode() {
        int i = this.hashCode;
        if (i == 0) {
            long j = this.traceIdHigh;
            long j2 = this.traceId;
            long j3 = this.spanId;
            int i2 = ((((((((int) (j ^ (j >>> 32))) ^ 1000003) * 1000003) ^ ((int) (j2 ^ (j2 >>> 32)))) * 1000003) ^ ((int) ((j3 >>> 32) ^ j3))) * 1000003) ^ (this.flags & 16);
            this.hashCode = i2;
            return i2;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> T findExtra(Class<T> cls, List<Object> list) {
        Objects.requireNonNull(cls, "type == null");
        int size = list.size();
        for (int i = 0; i < size; i++) {
            T t = (T) list.get(i);
            if (t.getClass() == cls) {
                return t;
            }
        }
        return null;
    }
}
