package zipkin2.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import zipkin2.internal.Buffer;
import zipkin2.internal.gson.stream.JsonToken;

/* loaded from: classes3.dex */
public final class JsonCodec {
    static final Charset UTF_8 = Charset.forName("UTF-8");

    /* loaded from: classes3.dex */
    public interface JsonReaderAdapter<T> {
        T fromJson(JsonReader jsonReader) throws IOException;
    }

    /* loaded from: classes3.dex */
    public static final class JsonReader {
        final zipkin2.internal.gson.stream.JsonReader delegate;

        /* JADX INFO: Access modifiers changed from: package-private */
        public JsonReader(byte[] bArr) {
            this.delegate = new zipkin2.internal.gson.stream.JsonReader(new InputStreamReader(new ByteArrayInputStream(bArr), JsonCodec.UTF_8));
        }

        public void beginArray() throws IOException {
            this.delegate.beginArray();
        }

        public boolean hasNext() throws IOException {
            return this.delegate.hasNext();
        }

        public void endArray() throws IOException {
            this.delegate.endArray();
        }

        public void beginObject() throws IOException {
            this.delegate.beginObject();
        }

        public void endObject() throws IOException {
            this.delegate.endObject();
        }

        public String nextName() throws IOException {
            return this.delegate.nextName();
        }

        public String nextString() throws IOException {
            return this.delegate.nextString();
        }

        public void skipValue() throws IOException {
            this.delegate.skipValue();
        }

        public long nextLong() throws IOException {
            return this.delegate.nextLong();
        }

        public String getPath() {
            return this.delegate.getPath();
        }

        public boolean nextBoolean() throws IOException {
            return this.delegate.nextBoolean();
        }

        public int nextInt() throws IOException {
            return this.delegate.nextInt();
        }

        public boolean peekString() throws IOException {
            return this.delegate.peek() == JsonToken.STRING;
        }

        public boolean peekBoolean() throws IOException {
            return this.delegate.peek() == JsonToken.BOOLEAN;
        }

        public boolean peekNull() throws IOException {
            return this.delegate.peek() == JsonToken.NULL;
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

    public static <T> boolean read(JsonReaderAdapter<T> jsonReaderAdapter, byte[] bArr, Collection<T> collection) {
        if (bArr.length == 0) {
            return false;
        }
        try {
            collection.add(jsonReaderAdapter.fromJson(new JsonReader(bArr)));
            return true;
        } catch (Exception e) {
            throw exceptionReading(jsonReaderAdapter.toString(), e);
        }
    }

    @Nullable
    public static <T> T readOne(JsonReaderAdapter<T> jsonReaderAdapter, byte[] bArr) {
        ArrayList arrayList = new ArrayList(1);
        if (read(jsonReaderAdapter, bArr, arrayList)) {
            return (T) arrayList.get(0);
        }
        return null;
    }

    public static <T> boolean readList(JsonReaderAdapter<T> jsonReaderAdapter, byte[] bArr, Collection<T> collection) {
        if (bArr.length == 0) {
            return false;
        }
        JsonReader jsonReader = new JsonReader(bArr);
        try {
            jsonReader.beginArray();
            if (jsonReader.hasNext()) {
                while (jsonReader.hasNext()) {
                    collection.add(jsonReaderAdapter.fromJson(jsonReader));
                }
                jsonReader.endArray();
                return true;
            }
            return false;
        } catch (Exception e) {
            throw exceptionReading("List<" + jsonReaderAdapter + ">", e);
        }
    }

    static <T> int sizeInBytes(Buffer.Writer<T> writer, List<T> list) {
        int size = list.size();
        int i = size > 1 ? 2 + (size - 1) : 2;
        for (int i2 = 0; i2 < size; i2++) {
            i += writer.sizeInBytes(list.get(i2));
        }
        return i;
    }

    public static <T> byte[] write(Buffer.Writer<T> writer, T t) {
        byte[] bArr;
        Buffer buffer = new Buffer(writer.sizeInBytes(t));
        try {
            writer.write(t, buffer);
            return buffer.toByteArray();
        } catch (RuntimeException e) {
            byte[] byteArray = buffer.toByteArray();
            int length = byteArray.length;
            int i = 0;
            while (true) {
                if (i >= byteArray.length) {
                    break;
                } else if (byteArray[i] == 0) {
                    length = i;
                    break;
                } else {
                    i++;
                }
            }
            if (length == byteArray.length) {
                bArr = byteArray;
            } else {
                bArr = new byte[length];
                System.arraycopy(byteArray, 0, bArr, 0, length);
            }
            throw Platform.get().assertionError(String.format("Bug found using %s to write %s as json. Wrote %s/%s bytes: %s", writer.getClass().getSimpleName(), t.getClass().getSimpleName(), Integer.valueOf(length), Integer.valueOf(byteArray.length), new String(bArr, UTF_8)), e);
        }
    }

    public static <T> byte[] writeList(Buffer.Writer<T> writer, List<T> list) {
        if (list.isEmpty()) {
            return new byte[]{91, 93};
        }
        Buffer buffer = new Buffer(sizeInBytes(writer, list));
        writeList(writer, list, buffer);
        return buffer.toByteArray();
    }

    public static <T> int writeList(Buffer.Writer<T> writer, List<T> list, byte[] bArr, int i) {
        if (list.isEmpty()) {
            bArr[i] = 91;
            bArr[i + 1] = 93;
            return 2;
        }
        Buffer buffer = new Buffer(bArr, i);
        writeList(writer, list, buffer);
        return buffer.pos - i;
    }

    public static <T> void writeList(Buffer.Writer<T> writer, List<T> list, Buffer buffer) {
        buffer.writeByte(91);
        int size = list.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            writer.write(list.get(i), buffer);
            if (i2 < size) {
                buffer.writeByte(44);
            }
            i = i2;
        }
        buffer.writeByte(93);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static IllegalArgumentException exceptionReading(String str, Exception exc) {
        String message = exc.getMessage() == null ? "Error" : exc.getMessage();
        if (message.indexOf("malformed") != -1) {
            message = "Malformed";
        }
        throw new IllegalArgumentException(String.format("%s reading %s from json", message, str), exc);
    }
}
