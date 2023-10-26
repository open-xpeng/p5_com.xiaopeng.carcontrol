package zipkin2;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Objects;
import zipkin2.codec.DependencyLinkBytesDecoder;
import zipkin2.codec.DependencyLinkBytesEncoder;

/* loaded from: classes3.dex */
public final class DependencyLink implements Serializable {
    static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final long serialVersionUID = 0;
    final long callCount;
    final String child;
    final long errorCount;
    final String parent;

    public static Builder newBuilder() {
        return new Builder();
    }

    public String parent() {
        return this.parent;
    }

    public String child() {
        return this.child;
    }

    public long callCount() {
        return this.callCount;
    }

    public long errorCount() {
        return this.errorCount;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        long callCount;
        String child;
        long errorCount;
        String parent;

        Builder() {
        }

        Builder(DependencyLink dependencyLink) {
            this.parent = dependencyLink.parent;
            this.child = dependencyLink.child;
            this.callCount = dependencyLink.callCount;
            this.errorCount = dependencyLink.errorCount;
        }

        public Builder parent(String str) {
            Objects.requireNonNull(str, "parent == null");
            this.parent = str.toLowerCase(Locale.ROOT);
            return this;
        }

        public Builder child(String str) {
            Objects.requireNonNull(str, "child == null");
            this.child = str.toLowerCase(Locale.ROOT);
            return this;
        }

        public Builder callCount(long j) {
            this.callCount = j;
            return this;
        }

        public Builder errorCount(long j) {
            this.errorCount = j;
            return this;
        }

        public DependencyLink build() {
            String str = this.parent == null ? " parent" : "";
            if (this.child == null) {
                str = str + " child";
            }
            if (!"".equals(str)) {
                throw new IllegalStateException("Missing :" + str);
            }
            return new DependencyLink(this);
        }
    }

    public String toString() {
        return new String(DependencyLinkBytesEncoder.JSON_V1.encode(this), UTF_8);
    }

    DependencyLink(Builder builder) {
        this.parent = builder.parent;
        this.child = builder.child;
        this.callCount = builder.callCount;
        this.errorCount = builder.errorCount;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof DependencyLink) {
            DependencyLink dependencyLink = (DependencyLink) obj;
            return this.parent.equals(dependencyLink.parent) && this.child.equals(dependencyLink.child) && this.callCount == dependencyLink.callCount && this.errorCount == dependencyLink.errorCount;
        }
        return false;
    }

    public int hashCode() {
        long j = this.callCount;
        long j2 = this.errorCount;
        return ((((((this.parent.hashCode() ^ 1000003) * 1000003) ^ this.child.hashCode()) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) (j2 ^ (j2 >>> 32)));
    }

    final Object writeReplace() throws ObjectStreamException {
        return new SerializedForm(DependencyLinkBytesEncoder.JSON_V1.encode(this));
    }

    /* loaded from: classes3.dex */
    private static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final byte[] bytes;

        SerializedForm(byte[] bArr) {
            this.bytes = bArr;
        }

        Object readResolve() throws ObjectStreamException {
            try {
                return DependencyLinkBytesDecoder.JSON_V1.decodeOne(this.bytes);
            } catch (IllegalArgumentException e) {
                throw new StreamCorruptedException(e.getMessage());
            }
        }
    }
}
