package zipkin2.internal;

import java.io.EOFException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import zipkin2.Span;
import zipkin2.internal.Buffer;
import zipkin2.v1.V1Span;
import zipkin2.v1.V1SpanConverter;

/* loaded from: classes3.dex */
public final class ThriftCodec {
    static final int MAX_SKIP_DEPTH = Integer.MAX_VALUE;
    static final Charset UTF_8 = Charset.forName("UTF-8");
    final V1ThriftSpanWriter writer = new V1ThriftSpanWriter();

    public int sizeInBytes(Span span) {
        return this.writer.sizeInBytes(span);
    }

    public byte[] write(Span span) {
        return this.writer.write(span);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> int listSizeInBytes(Buffer.Writer<T> writer, List<T> list) {
        int size = list.size();
        int i = 5;
        for (int i2 = 0; i2 < size; i2++) {
            i += writer.sizeInBytes(list.get(i2));
        }
        return i;
    }

    public static boolean read(byte[] bArr, Collection<Span> collection) {
        if (bArr.length == 0) {
            return false;
        }
        try {
            V1SpanConverter.create().convert(new V1ThriftSpanReader().read(ByteBuffer.wrap(bArr)), collection);
            return true;
        } catch (Exception e) {
            throw exceptionReading("Span", e);
        }
    }

    @Nullable
    public static Span readOne(byte[] bArr) {
        if (bArr.length == 0) {
            return null;
        }
        try {
            V1Span read = new V1ThriftSpanReader().read(ByteBuffer.wrap(bArr));
            ArrayList arrayList = new ArrayList(1);
            V1SpanConverter.create().convert(read, arrayList);
            return (Span) arrayList.get(0);
        } catch (Exception e) {
            throw exceptionReading("Span", e);
        }
    }

    public static boolean readList(byte[] bArr, Collection<Span> collection) {
        if (bArr.length == 0) {
            return false;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        try {
            int readListLength = readListLength(wrap);
            if (readListLength == 0) {
                return false;
            }
            V1ThriftSpanReader v1ThriftSpanReader = new V1ThriftSpanReader();
            V1SpanConverter create = V1SpanConverter.create();
            for (int i = 0; i < readListLength; i++) {
                create.convert(v1ThriftSpanReader.read(wrap), collection);
            }
            return true;
        } catch (Exception e) {
            throw exceptionReading("List<Span>", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int readListLength(ByteBuffer byteBuffer) {
        byteBuffer.get();
        return guardLength(byteBuffer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> void writeList(Buffer.Writer<T> writer, List<T> list, Buffer buffer) {
        int size = list.size();
        writeListBegin(buffer, size);
        for (int i = 0; i < size; i++) {
            writer.write(list.get(i), buffer);
        }
    }

    static IllegalArgumentException exceptionReading(String str, Exception exc) {
        String message = exc.getMessage() == null ? "Error" : exc.getMessage();
        if (exc instanceof EOFException) {
            message = "EOF";
        }
        throw new IllegalArgumentException(String.format("%s reading %s from TBinary", ((exc instanceof IllegalStateException) || (exc instanceof BufferUnderflowException)) ? "Malformed" : "Malformed", str), exc);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void skip(ByteBuffer byteBuffer, byte b) {
        skip(byteBuffer, b, Integer.MAX_VALUE);
    }

    static void skip(ByteBuffer byteBuffer, byte b, int i) {
        if (i <= 0) {
            throw new IllegalStateException("Maximum skip depth exceeded");
        }
        int i2 = 0;
        switch (b) {
            case 2:
            case 3:
                skip(byteBuffer, 1);
                return;
            case 4:
            case 10:
                skip(byteBuffer, 8);
                return;
            case 5:
            case 7:
            case 9:
            default:
                return;
            case 6:
                skip(byteBuffer, 2);
                return;
            case 8:
                skip(byteBuffer, 4);
                return;
            case 11:
                skip(byteBuffer, guardLength(byteBuffer));
                return;
            case 12:
                break;
            case 13:
                byte b2 = byteBuffer.get();
                byte b3 = byteBuffer.get();
                int guardLength = guardLength(byteBuffer);
                while (i2 < guardLength) {
                    int i3 = i - 1;
                    skip(byteBuffer, b2, i3);
                    skip(byteBuffer, b3, i3);
                    i2++;
                }
                return;
            case 14:
            case 15:
                byte b4 = byteBuffer.get();
                int guardLength2 = guardLength(byteBuffer);
                while (i2 < guardLength2) {
                    skip(byteBuffer, b4, i - 1);
                    i2++;
                }
                return;
        }
        while (true) {
            ThriftField read = ThriftField.read(byteBuffer);
            if (read.type == 0) {
                return;
            }
            skip(byteBuffer, read.type, i - 1);
        }
    }

    static void skip(ByteBuffer byteBuffer, int i) {
        for (int i2 = 0; i2 < i && byteBuffer.hasRemaining(); i2++) {
            byteBuffer.get();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte[] readByteArray(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[guardLength(byteBuffer)];
        byteBuffer.get(bArr);
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String readUtf8(ByteBuffer byteBuffer) {
        return new String(readByteArray(byteBuffer), UTF_8);
    }

    static int guardLength(ByteBuffer byteBuffer) {
        int i = byteBuffer.getInt();
        if (i <= byteBuffer.remaining()) {
            return i;
        }
        throw new IllegalArgumentException("Truncated: length " + i + " > bytes remaining " + byteBuffer.remaining());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void writeListBegin(Buffer buffer, int i) {
        buffer.writeByte(12);
        writeInt(buffer, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void writeLengthPrefixed(Buffer buffer, String str) {
        Buffer.utf8SizeInBytes(str);
        writeInt(buffer, Buffer.utf8SizeInBytes(str));
        buffer.writeUtf8(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void writeInt(Buffer buffer, int i) {
        buffer.writeByte((byte) ((i >>> 24) & 255));
        buffer.writeByte((byte) ((i >>> 16) & 255));
        buffer.writeByte((byte) ((i >>> 8) & 255));
        buffer.writeByte((byte) (i & 255));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void writeLong(Buffer buffer, long j) {
        buffer.writeByte((byte) ((j >>> 56) & 255));
        buffer.writeByte((byte) ((j >>> 48) & 255));
        buffer.writeByte((byte) ((j >>> 40) & 255));
        buffer.writeByte((byte) ((j >>> 32) & 255));
        buffer.writeByte((byte) ((j >>> 24) & 255));
        buffer.writeByte((byte) ((j >>> 16) & 255));
        buffer.writeByte((byte) ((j >>> 8) & 255));
        buffer.writeByte((byte) (j & 255));
    }
}
