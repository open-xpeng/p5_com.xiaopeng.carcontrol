package zipkin2.internal;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import zipkin2.DependencyLink;
import zipkin2.internal.Buffer;

/* loaded from: classes3.dex */
public final class Dependencies {
    final long endTs;
    final List<DependencyLink> links;
    final long startTs;
    static final ThriftField START_TS = new ThriftField((byte) 10, 1);
    static final ThriftField END_TS = new ThriftField((byte) 10, 2);
    static final ThriftField LINKS = new ThriftField((byte) 15, 3);
    static final DependencyLinkAdapter DEPENDENCY_LINK_ADAPTER = new DependencyLinkAdapter();

    public List<DependencyLink> links() {
        return this.links;
    }

    public static Dependencies fromThrift(ByteBuffer byteBuffer) {
        List emptyList = Collections.emptyList();
        long j = 0;
        long j2 = 0;
        while (true) {
            ThriftField read = ThriftField.read(byteBuffer);
            if (read.type != 0) {
                if (read.isEqualTo(START_TS)) {
                    j = byteBuffer.getLong();
                } else if (read.isEqualTo(END_TS)) {
                    j2 = byteBuffer.getLong();
                } else if (read.isEqualTo(LINKS)) {
                    int readListLength = ThriftCodec.readListLength(byteBuffer);
                    if (readListLength != 0) {
                        emptyList = new ArrayList(readListLength);
                        for (int i = 0; i < readListLength; i++) {
                            emptyList.add(DependencyLinkAdapter.read(byteBuffer));
                        }
                    }
                } else {
                    ThriftCodec.skip(byteBuffer, read.type);
                }
            } else {
                return create(j, j2, emptyList);
            }
        }
    }

    public ByteBuffer toThrift() {
        Buffer buffer = new Buffer(sizeInBytes());
        write(buffer);
        return ByteBuffer.wrap(buffer.toByteArray());
    }

    int sizeInBytes() {
        return 22 + ThriftCodec.listSizeInBytes(DEPENDENCY_LINK_ADAPTER, this.links) + 3 + 1;
    }

    void write(Buffer buffer) {
        START_TS.write(buffer);
        ThriftCodec.writeLong(buffer, this.startTs);
        END_TS.write(buffer);
        ThriftCodec.writeLong(buffer, this.endTs);
        LINKS.write(buffer);
        ThriftCodec.writeList(DEPENDENCY_LINK_ADAPTER, this.links, buffer);
        buffer.writeByte(0);
    }

    public static Dependencies create(long j, long j2, List<DependencyLink> list) {
        return new Dependencies(j, j2, list);
    }

    Dependencies(long j, long j2, List<DependencyLink> list) {
        this.startTs = j;
        this.endTs = j2;
        Objects.requireNonNull(list, "links == null");
        this.links = list;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Dependencies) {
            Dependencies dependencies = (Dependencies) obj;
            return this.startTs == dependencies.startTs && this.endTs == dependencies.endTs && this.links.equals(dependencies.links);
        }
        return false;
    }

    public int hashCode() {
        long j = this.startTs;
        int i = (((int) (1000003 ^ (j ^ (j >>> 32)))) ^ 1000003) * 1000003;
        long j2 = this.endTs;
        return this.links.hashCode() ^ ((i ^ ((int) (i ^ ((j2 >>> 32) ^ j2)))) * 1000003);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class DependencyLinkAdapter implements Buffer.Writer<DependencyLink> {
        static final ThriftField PARENT = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 1);
        static final ThriftField CHILD = new ThriftField(MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 2);
        static final ThriftField CALL_COUNT = new ThriftField((byte) 10, 4);
        static final ThriftField ERROR_COUNT = new ThriftField((byte) 10, 5);

        DependencyLinkAdapter() {
        }

        static DependencyLink read(ByteBuffer byteBuffer) {
            DependencyLink.Builder newBuilder = DependencyLink.newBuilder();
            while (true) {
                ThriftField read = ThriftField.read(byteBuffer);
                if (read.type != 0) {
                    if (read.isEqualTo(PARENT)) {
                        newBuilder.parent(ThriftCodec.readUtf8(byteBuffer));
                    } else if (read.isEqualTo(CHILD)) {
                        newBuilder.child(ThriftCodec.readUtf8(byteBuffer));
                    } else if (read.isEqualTo(CALL_COUNT)) {
                        newBuilder.callCount(byteBuffer.getLong());
                    } else if (read.isEqualTo(ERROR_COUNT)) {
                        newBuilder.errorCount(byteBuffer.getLong());
                    } else {
                        ThriftCodec.skip(byteBuffer, read.type);
                    }
                } else {
                    return newBuilder.build();
                }
            }
        }

        @Override // zipkin2.internal.Buffer.Writer
        public int sizeInBytes(DependencyLink dependencyLink) {
            int utf8SizeInBytes = Buffer.utf8SizeInBytes(dependencyLink.parent()) + 7 + 0 + Buffer.utf8SizeInBytes(dependencyLink.child()) + 7 + 11;
            if (dependencyLink.errorCount() > 0) {
                utf8SizeInBytes += 11;
            }
            return utf8SizeInBytes + 1;
        }

        @Override // zipkin2.internal.Buffer.Writer
        public void write(DependencyLink dependencyLink, Buffer buffer) {
            PARENT.write(buffer);
            ThriftCodec.writeLengthPrefixed(buffer, dependencyLink.parent());
            CHILD.write(buffer);
            ThriftCodec.writeLengthPrefixed(buffer, dependencyLink.child());
            CALL_COUNT.write(buffer);
            ThriftCodec.writeLong(buffer, dependencyLink.callCount());
            if (dependencyLink.errorCount() > 0) {
                ERROR_COUNT.write(buffer);
                ThriftCodec.writeLong(buffer, dependencyLink.errorCount());
            }
            buffer.writeByte(0);
        }
    }
}
