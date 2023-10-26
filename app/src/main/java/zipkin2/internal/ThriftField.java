package zipkin2.internal;

import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
final class ThriftField {
    static final byte TYPE_BOOL = 2;
    static final byte TYPE_BYTE = 3;
    static final byte TYPE_DOUBLE = 4;
    static final byte TYPE_I16 = 6;
    static final byte TYPE_I32 = 8;
    static final byte TYPE_I64 = 10;
    static final byte TYPE_LIST = 15;
    static final byte TYPE_MAP = 13;
    static final byte TYPE_SET = 14;
    static final byte TYPE_STOP = 0;
    static final byte TYPE_STRING = 11;
    static final byte TYPE_STRUCT = 12;
    final int id;
    final byte type;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ThriftField(byte b, int i) {
        this.type = b;
        this.id = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void write(Buffer buffer) {
        buffer.writeByte(this.type);
        buffer.writeByte((this.id >>> 8) & 255);
        buffer.writeByte(this.id & 255);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ThriftField read(ByteBuffer byteBuffer) {
        byte b = byteBuffer.get();
        return new ThriftField(b, b == 0 ? (short) 0 : byteBuffer.getShort());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isEqualTo(ThriftField thriftField) {
        return this.type == thriftField.type && this.id == thriftField.id;
    }
}
