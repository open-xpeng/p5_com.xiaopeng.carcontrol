package zipkin2.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class Proto3Fields {
    static final int WIRETYPE_FIXED32 = 5;
    static final int WIRETYPE_FIXED64 = 1;
    static final int WIRETYPE_LENGTH_DELIMITED = 2;
    static final int WIRETYPE_VARINT = 0;

    Proto3Fields() {
    }

    /* loaded from: classes3.dex */
    static class Field {
        final int fieldNumber;
        final int key;
        final int wireType;

        Field(int i) {
            this(i >>> 3, i & 7, i);
        }

        Field(int i, int i2, int i3) {
            this.fieldNumber = i;
            this.wireType = i2;
            this.key = i3;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static int fieldNumber(int i, int i2) {
            int i3 = i >>> 3;
            if (i3 != 0) {
                return i3;
            }
            throw new IllegalArgumentException("Malformed: fieldNumber was zero at byte " + i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static int wireType(int i, int i2) {
            int i3 = i & 7;
            if (i3 == 0 || i3 == 1 || i3 == 2 || i3 == 5) {
                return i3;
            }
            throw new IllegalArgumentException("Malformed: invalid wireType " + i3 + " at byte " + i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static boolean skipValue(Buffer buffer, int i) {
            int remaining = buffer.remaining();
            if (i == 0) {
                for (int i2 = 0; i2 < remaining; i2++) {
                    if (buffer.readByte() >= 0) {
                        return true;
                    }
                }
                return false;
            } else if (i == 1) {
                return buffer.skip(8);
            } else {
                if (i != 2) {
                    if (i == 5) {
                        return buffer.skip(4);
                    }
                    throw new IllegalArgumentException("Malformed: invalid wireType " + i + " at byte " + buffer.pos);
                }
                return buffer.skip(buffer.readVarint32());
            }
        }
    }

    /* loaded from: classes3.dex */
    static abstract class LengthDelimitedField<T> extends Field {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        abstract T readValue(Buffer buffer, int i);

        abstract int sizeOfValue(T t);

        abstract void writeValue(Buffer buffer, T t);

        /* JADX INFO: Access modifiers changed from: package-private */
        public LengthDelimitedField(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final int sizeInBytes(T t) {
            int sizeOfValue;
            if (t == null || (sizeOfValue = sizeOfValue(t)) == 0) {
                return 0;
            }
            return Proto3Fields.sizeOfLengthDelimitedField(sizeOfValue);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final void write(Buffer buffer, T t) {
            int sizeOfValue;
            if (t == null || (sizeOfValue = sizeOfValue(t)) == 0) {
                return;
            }
            buffer.writeByte(this.key);
            buffer.writeVarint(sizeOfValue);
            writeValue(buffer, t);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final T readLengthPrefixAndValue(Buffer buffer) {
            int readLengthPrefix = readLengthPrefix(buffer);
            if (readLengthPrefix == 0) {
                return null;
            }
            return readValue(buffer, readLengthPrefix);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final int readLengthPrefix(Buffer buffer) {
            int readVarint32 = buffer.readVarint32();
            Proto3Fields.ensureLength(buffer, readVarint32);
            return readVarint32;
        }
    }

    /* loaded from: classes3.dex */
    static class BytesField extends LengthDelimitedField<byte[]> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public BytesField(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public int sizeOfValue(byte[] bArr) {
            return bArr.length;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public void writeValue(Buffer buffer, byte[] bArr) {
            buffer.write(bArr);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public byte[] readValue(Buffer buffer, int i) {
            byte[] bArr = new byte[i];
            System.arraycopy(buffer.toByteArray(), buffer.pos, bArr, 0, i);
            buffer.pos += i;
            return bArr;
        }
    }

    /* loaded from: classes3.dex */
    static class HexField extends LengthDelimitedField<String> {
        static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        /* JADX INFO: Access modifiers changed from: package-private */
        public HexField(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public int sizeOfValue(String str) {
            if (str == null) {
                return 0;
            }
            return str.length() / 2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public void writeValue(Buffer buffer, String str) {
            int length = str.length();
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                buffer.writeByte((byte) ((decodeLowerHex(str.charAt(i)) << 4) + decodeLowerHex(str.charAt(i2))));
                i = i2 + 1;
            }
        }

        static int decodeLowerHex(char c) {
            if (c < '0' || c > '9') {
                if (c < 'a' || c > 'f') {
                    throw new AssertionError("not lowerHex " + c);
                }
                return (c - 'a') + 10;
            }
            return c - '0';
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public String readValue(Buffer buffer, int i) {
            int i2 = i * 2;
            char[] cArr = new char[i2];
            for (int i3 = 0; i3 < i2; i3 += 2) {
                byte readByte = buffer.readByte();
                char[] cArr2 = HEX_DIGITS;
                cArr[i3 + 0] = cArr2[(readByte >> 4) & 15];
                cArr[i3 + 1] = cArr2[readByte & 15];
            }
            return new String(cArr);
        }
    }

    /* loaded from: classes3.dex */
    static class Utf8Field extends LengthDelimitedField<String> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public Utf8Field(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public int sizeOfValue(String str) {
            if (str != null) {
                return Buffer.utf8SizeInBytes(str);
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public void writeValue(Buffer buffer, String str) {
            buffer.writeUtf8(str);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // zipkin2.internal.Proto3Fields.LengthDelimitedField
        public String readValue(Buffer buffer, int i) {
            String str = new String(buffer.toByteArray(), buffer.pos, i, JsonCodec.UTF_8);
            buffer.pos += i;
            return str;
        }
    }

    /* loaded from: classes3.dex */
    static final class Fixed64Field extends Field {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* JADX INFO: Access modifiers changed from: package-private */
        public int sizeInBytes(long j) {
            return j == 0 ? 0 : 9;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Fixed64Field(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void write(Buffer buffer, long j) {
            if (j == 0) {
                return;
            }
            buffer.writeByte(this.key);
            buffer.writeLongLe(j);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public long readValue(Buffer buffer) {
            Proto3Fields.ensureLength(buffer, 8);
            return buffer.readLongLe();
        }
    }

    /* loaded from: classes3.dex */
    static class VarintField extends Field {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* JADX INFO: Access modifiers changed from: package-private */
        public VarintField(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int sizeInBytes(int i) {
            if (i != 0) {
                return Buffer.varintSizeInBytes(i) + 1;
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void write(Buffer buffer, int i) {
            if (i == 0) {
                return;
            }
            buffer.writeByte(this.key);
            buffer.writeVarint(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int sizeInBytes(long j) {
            if (j != 0) {
                return Buffer.varintSizeInBytes(j) + 1;
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void write(Buffer buffer, long j) {
            if (j == 0) {
                return;
            }
            buffer.writeByte(this.key);
            buffer.writeVarint(j);
        }
    }

    /* loaded from: classes3.dex */
    static final class BooleanField extends Field {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* JADX INFO: Access modifiers changed from: package-private */
        public int sizeInBytes(boolean z) {
            return z ? 2 : 0;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public BooleanField(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void write(Buffer buffer, boolean z) {
            if (z) {
                buffer.writeByte(this.key);
                buffer.writeByte(1);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public boolean read(Buffer buffer) {
            byte readByte = buffer.readByte();
            if (readByte < 0 || readByte > 1) {
                throw new IllegalArgumentException("Malformed: invalid boolean value at byte " + buffer.pos);
            }
            return readByte == 1;
        }
    }

    /* loaded from: classes3.dex */
    static final class Fixed32Field extends Field {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        int sizeInBytes(int i) {
            return i == 0 ? 0 : 5;
        }

        Fixed32Field(int i) {
            super(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int sizeOfLengthDelimitedField(int i) {
        return Buffer.varintSizeInBytes(i) + 1 + i;
    }

    static void ensureLength(Buffer buffer, int i) {
        if (i > buffer.remaining()) {
            throw new IllegalArgumentException("Truncated: length " + i + " > bytes remaining " + buffer.remaining());
        }
    }
}
