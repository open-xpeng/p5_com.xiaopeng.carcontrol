package zipkin2.internal;

import java.util.List;
import zipkin2.Span;
import zipkin2.internal.Buffer;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class Proto3SpanWriter implements Buffer.Writer<Span> {
    static final byte[] EMPTY_ARRAY = new byte[0];

    public String toString() {
        return "Span";
    }

    @Override // zipkin2.internal.Buffer.Writer
    public int sizeInBytes(Span span) {
        return Proto3ZipkinFields.SPAN.sizeInBytes(span);
    }

    @Override // zipkin2.internal.Buffer.Writer
    public void write(Span span, Buffer buffer) {
        Proto3ZipkinFields.SPAN.write(buffer, span);
    }

    public byte[] writeList(List<Span> list) {
        int size = list.size();
        if (size == 0) {
            return EMPTY_ARRAY;
        }
        if (size == 1) {
            return write(list.get(0));
        }
        int[] iArr = new int[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int sizeOfValue = Proto3ZipkinFields.SPAN.sizeOfValue(list.get(i2));
            iArr[i2] = sizeOfValue;
            i += Proto3Fields.sizeOfLengthDelimitedField(sizeOfValue);
        }
        Buffer buffer = new Buffer(i);
        for (int i3 = 0; i3 < size; i3++) {
            writeSpan(list.get(i3), iArr[i3], buffer);
        }
        return buffer.toByteArray();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte[] write(Span span) {
        int sizeOfValue = Proto3ZipkinFields.SPAN.sizeOfValue(span);
        Buffer buffer = new Buffer(Proto3Fields.sizeOfLengthDelimitedField(sizeOfValue));
        writeSpan(span, sizeOfValue, buffer);
        return buffer.toByteArray();
    }

    void writeSpan(Span span, int i, Buffer buffer) {
        buffer.writeByte(Proto3ZipkinFields.SPAN.key);
        buffer.writeVarint(i);
        Proto3ZipkinFields.SPAN.writeValue(buffer, span);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int writeList(List<Span> list, byte[] bArr, int i) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        Buffer buffer = new Buffer(bArr, i);
        for (int i2 = 0; i2 < size; i2++) {
            Proto3ZipkinFields.SPAN.write(buffer, list.get(i2));
        }
        return buffer.pos - i;
    }
}
