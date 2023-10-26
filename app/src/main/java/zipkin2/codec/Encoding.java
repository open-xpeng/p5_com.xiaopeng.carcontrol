package zipkin2.codec;

import java.util.List;

/* loaded from: classes3.dex */
public enum Encoding {
    JSON { // from class: zipkin2.codec.Encoding.1
        @Override // zipkin2.codec.Encoding
        public int listSizeInBytes(int i) {
            return i + 2;
        }

        @Override // zipkin2.codec.Encoding
        public int listSizeInBytes(List<byte[]> list) {
            int size = list.size();
            int i = 2;
            int i2 = 0;
            while (i2 < size) {
                int i3 = i2 + 1;
                i += list.get(i2).length;
                if (i3 < size) {
                    i++;
                }
                i2 = i3;
            }
            return i;
        }
    },
    THRIFT { // from class: zipkin2.codec.Encoding.2
        @Override // zipkin2.codec.Encoding
        public int listSizeInBytes(int i) {
            return i + 5;
        }

        @Override // zipkin2.codec.Encoding
        public int listSizeInBytes(List<byte[]> list) {
            int size = list.size();
            int i = 5;
            for (int i2 = 0; i2 < size; i2++) {
                i += list.get(i2).length;
            }
            return i;
        }
    },
    PROTO3 { // from class: zipkin2.codec.Encoding.3
        @Override // zipkin2.codec.Encoding
        public int listSizeInBytes(int i) {
            return i;
        }

        @Override // zipkin2.codec.Encoding
        public int listSizeInBytes(List<byte[]> list) {
            int size = list.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                i += list.get(i2).length;
            }
            return i;
        }
    };

    public abstract int listSizeInBytes(int i);

    public abstract int listSizeInBytes(List<byte[]> list);
}
