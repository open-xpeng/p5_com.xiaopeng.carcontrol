package zipkin2.internal;

import java.util.Collection;
import java.util.List;
import zipkin2.Span;

/* loaded from: classes3.dex */
public final class Proto3Codec {
    final Proto3SpanWriter writer = new Proto3SpanWriter();

    public int sizeInBytes(Span span) {
        return this.writer.sizeInBytes(span);
    }

    public byte[] write(Span span) {
        return this.writer.write(span);
    }

    public byte[] writeList(List<Span> list) {
        return this.writer.writeList(list);
    }

    public int writeList(List<Span> list, byte[] bArr, int i) {
        return this.writer.writeList(list, bArr, i);
    }

    public static boolean read(byte[] bArr, Collection<Span> collection) {
        if (bArr.length == 0) {
            return false;
        }
        try {
            Span read = Proto3ZipkinFields.SPAN.read(new Buffer(bArr, 0));
            if (read == null) {
                return false;
            }
            collection.add(read);
            return true;
        } catch (Exception e) {
            throw exceptionReading("Span", e);
        }
    }

    @Nullable
    public static Span readOne(byte[] bArr) {
        return Proto3ZipkinFields.SPAN.read(new Buffer(bArr, 0));
    }

    public static boolean readList(byte[] bArr, Collection<Span> collection) {
        int length = bArr.length;
        if (length == 0) {
            return false;
        }
        Buffer buffer = new Buffer(bArr, 0);
        while (buffer.pos < length) {
            try {
                Span read = Proto3ZipkinFields.SPAN.read(buffer);
                if (read == null) {
                    return false;
                }
                collection.add(read);
            } catch (Exception e) {
                throw exceptionReading("List<Span>", e);
            }
        }
        return true;
    }

    static IllegalArgumentException exceptionReading(String str, Exception exc) {
        String message = exc.getMessage() == null ? "Error" : exc.getMessage();
        if (message.indexOf("Malformed") != -1) {
            message = "Malformed";
        }
        throw new IllegalArgumentException(String.format("%s reading %s from proto3", message, str), exc);
    }
}
