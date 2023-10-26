package zipkin2.internal;

import com.xiaopeng.libtheme.ThemeManager;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* loaded from: classes3.dex */
public final class Buffer {
    static final byte[] DIGITS = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57};
    static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final byte[] buf;
    int pos;

    /* loaded from: classes3.dex */
    public interface Writer<T> {
        int sizeInBytes(T t);

        void write(T t, Buffer buffer);
    }

    public static int asciiSizeInBytes(long j) {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        int i2 = 1;
        if (i == 0) {
            return 1;
        }
        if (j == Long.MIN_VALUE) {
            return 20;
        }
        boolean z = false;
        if (i < 0) {
            j = -j;
            z = true;
        }
        if (j >= 100000000) {
            i2 = j < 1000000000000L ? j < 10000000000L ? j < 1000000000 ? 9 : 10 : j < 100000000000L ? 11 : 12 : j < 1000000000000000L ? j < 10000000000000L ? 13 : j < 100000000000000L ? 14 : 15 : j < 100000000000000000L ? j < 10000000000000000L ? 16 : 17 : j < 1000000000000000000L ? 18 : 19;
        } else if (j >= 10000) {
            i2 = j < 1000000 ? j < 100000 ? 5 : 6 : j < 10000000 ? 7 : 8;
        } else if (j >= 100) {
            i2 = j < 1000 ? 3 : 4;
        } else if (j >= 10) {
            i2 = 2;
        }
        return z ? i2 + 1 : i2;
    }

    public static int varintSizeInBytes(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int varintSizeInBytes(long j) {
        if (((-128) & j) == 0) {
            return 1;
        }
        if (((-16384) & j) == 0) {
            return 2;
        }
        if (((-2097152) & j) == 0) {
            return 3;
        }
        if (((-268435456) & j) == 0) {
            return 4;
        }
        if (((-34359738368L) & j) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public Buffer(int i) {
        this.buf = new byte[i];
    }

    public Buffer(byte[] bArr, int i) {
        this.buf = bArr;
        this.pos = i;
    }

    public Buffer writeByte(int i) {
        byte[] bArr = this.buf;
        int i2 = this.pos;
        this.pos = i2 + 1;
        bArr[i2] = (byte) i;
        return this;
    }

    public Buffer write(byte[] bArr) {
        System.arraycopy(bArr, 0, this.buf, this.pos, bArr.length);
        this.pos += bArr.length;
        return this;
    }

    public static int utf8SizeInBytes(String str) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt < 128) {
                i2++;
                while (i < length - 1) {
                    int i3 = i + 1;
                    if (str.charAt(i3) >= 128) {
                        break;
                    }
                    i2++;
                    i = i3;
                }
            } else if (charAt < 2048) {
                i2 += 2;
            } else if (charAt < 55296 || charAt > 57343) {
                i2 += 3;
            } else {
                int i4 = i + 1;
                char charAt2 = i4 < length ? str.charAt(i4) : (char) 0;
                if (charAt > 56319 || charAt2 < 56320 || charAt2 > 57343) {
                    i2++;
                } else {
                    i2 += 4;
                    i = i4;
                }
            }
            i++;
        }
        return i2;
    }

    public Buffer writeAscii(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            byte[] bArr = this.buf;
            int i2 = this.pos;
            this.pos = i2 + 1;
            bArr[i2] = (byte) str.charAt(i);
        }
        return this;
    }

    public Buffer writeUtf8(String str) {
        int i;
        char charAt;
        int length = str.length();
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt2 = str.charAt(i2);
            if (charAt2 < 128) {
                byte[] bArr = this.buf;
                int i3 = this.pos;
                this.pos = i3 + 1;
                bArr[i3] = (byte) charAt2;
                while (i2 < length - 1 && (charAt = str.charAt((i = i2 + 1))) < 128) {
                    byte[] bArr2 = this.buf;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    bArr2[i4] = (byte) charAt;
                    i2 = i;
                }
            } else if (charAt2 < 2048) {
                byte[] bArr3 = this.buf;
                int i5 = this.pos;
                int i6 = i5 + 1;
                this.pos = i6;
                bArr3[i5] = (byte) ((charAt2 >> 6) | ThemeManager.UI_MODE_THEME_MASK);
                this.pos = i6 + 1;
                bArr3[i6] = (byte) ((charAt2 & '?') | 128);
            } else {
                if (charAt2 < 55296 || charAt2 > 57343) {
                    byte[] bArr4 = this.buf;
                    int i7 = this.pos;
                    int i8 = i7 + 1;
                    this.pos = i8;
                    bArr4[i7] = (byte) ((charAt2 >> '\f') | 224);
                    int i9 = i8 + 1;
                    this.pos = i9;
                    bArr4[i8] = (byte) ((63 & (charAt2 >> 6)) | 128);
                    this.pos = i9 + 1;
                    bArr4[i9] = (byte) ((charAt2 & '?') | 128);
                } else if (!Character.isHighSurrogate(charAt2)) {
                    byte[] bArr5 = this.buf;
                    int i10 = this.pos;
                    this.pos = i10 + 1;
                    bArr5[i10] = 63;
                } else if (i2 == length - 1) {
                    byte[] bArr6 = this.buf;
                    int i11 = this.pos;
                    this.pos = i11 + 1;
                    bArr6[i11] = 63;
                    break;
                } else {
                    i2++;
                    char charAt3 = str.charAt(i2);
                    if (!Character.isLowSurrogate(charAt3)) {
                        byte[] bArr7 = this.buf;
                        int i12 = this.pos;
                        int i13 = i12 + 1;
                        this.pos = i13;
                        bArr7[i12] = 63;
                        this.pos = i13 + 1;
                        bArr7[i13] = (byte) (Character.isHighSurrogate(charAt3) ? '?' : charAt3);
                    } else {
                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                        byte[] bArr8 = this.buf;
                        int i14 = this.pos;
                        int i15 = i14 + 1;
                        this.pos = i15;
                        bArr8[i14] = (byte) ((codePoint >> 18) | 240);
                        int i16 = i15 + 1;
                        this.pos = i16;
                        bArr8[i15] = (byte) (((codePoint >> 12) & 63) | 128);
                        int i17 = i16 + 1;
                        this.pos = i17;
                        bArr8[i16] = (byte) (((codePoint >> 6) & 63) | 128);
                        this.pos = i17 + 1;
                        bArr8[i17] = (byte) ((codePoint & 63) | 128);
                    }
                }
            }
            i2++;
        }
        return this;
    }

    public Buffer writeAscii(long j) {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i == 0) {
            return writeByte(48);
        }
        if (j == Long.MIN_VALUE) {
            return writeAscii("-9223372036854775808");
        }
        int asciiSizeInBytes = this.pos + asciiSizeInBytes(j);
        this.pos = asciiSizeInBytes;
        boolean z = false;
        if (i < 0) {
            z = true;
            j = -j;
        }
        while (j != 0) {
            asciiSizeInBytes--;
            this.buf[asciiSizeInBytes] = DIGITS[(int) (j % 10)];
            j /= 10;
        }
        if (z) {
            this.buf[asciiSizeInBytes - 1] = 45;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeVarint(int i) {
        while ((i & (-128)) != 0) {
            byte[] bArr = this.buf;
            int i2 = this.pos;
            this.pos = i2 + 1;
            bArr[i2] = (byte) ((i & 127) | 128);
            i >>>= 7;
        }
        byte[] bArr2 = this.buf;
        int i3 = this.pos;
        this.pos = i3 + 1;
        bArr2[i3] = (byte) i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeVarint(long j) {
        while (((-128) & j) != 0) {
            byte[] bArr = this.buf;
            int i = this.pos;
            this.pos = i + 1;
            bArr[i] = (byte) ((127 & j) | 128);
            j >>>= 7;
        }
        byte[] bArr2 = this.buf;
        int i2 = this.pos;
        this.pos = i2 + 1;
        bArr2[i2] = (byte) j;
    }

    public Buffer writeLongHex(long j) {
        writeHexByte(this.buf, this.pos + 0, (byte) ((j >>> 56) & 255));
        writeHexByte(this.buf, this.pos + 2, (byte) ((j >>> 48) & 255));
        writeHexByte(this.buf, this.pos + 4, (byte) ((j >>> 40) & 255));
        writeHexByte(this.buf, this.pos + 6, (byte) ((j >>> 32) & 255));
        writeHexByte(this.buf, this.pos + 8, (byte) ((j >>> 24) & 255));
        writeHexByte(this.buf, this.pos + 10, (byte) ((j >>> 16) & 255));
        writeHexByte(this.buf, this.pos + 12, (byte) ((j >>> 8) & 255));
        writeHexByte(this.buf, this.pos + 14, (byte) (j & 255));
        this.pos += 16;
        return this;
    }

    static void writeHexByte(byte[] bArr, int i, byte b) {
        char[] cArr = HEX_DIGITS;
        bArr[i + 0] = (byte) cArr[(b >> 4) & 15];
        bArr[i + 1] = (byte) cArr[b & 15];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeLongLe(long j) {
        byte[] bArr = this.buf;
        int i = this.pos;
        int i2 = i + 1;
        this.pos = i2;
        bArr[i] = (byte) (j & 255);
        int i3 = i2 + 1;
        this.pos = i3;
        bArr[i2] = (byte) ((j >> 8) & 255);
        int i4 = i3 + 1;
        this.pos = i4;
        bArr[i3] = (byte) ((j >> 16) & 255);
        int i5 = i4 + 1;
        this.pos = i5;
        bArr[i4] = (byte) ((j >> 24) & 255);
        int i6 = i5 + 1;
        this.pos = i6;
        bArr[i5] = (byte) ((j >> 32) & 255);
        int i7 = i6 + 1;
        this.pos = i7;
        bArr[i6] = (byte) ((j >> 40) & 255);
        int i8 = i7 + 1;
        this.pos = i8;
        bArr[i7] = (byte) ((j >> 48) & 255);
        this.pos = i8 + 1;
        bArr[i8] = (byte) ((j >> 56) & 255);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long readLongLe() {
        byte[] bArr = this.buf;
        int i = this.pos;
        int i2 = i + 1;
        this.pos = i2;
        int i3 = i2 + 1;
        this.pos = i3;
        int i4 = i3 + 1;
        this.pos = i4;
        int i5 = i4 + 1;
        this.pos = i5;
        int i6 = i5 + 1;
        this.pos = i6;
        int i7 = i6 + 1;
        this.pos = i7;
        int i8 = i7 + 1;
        this.pos = i8;
        this.pos = i8 + 1;
        return (bArr[i] & 255) | ((bArr[i2] & 255) << 8) | ((bArr[i3] & 255) << 16) | ((bArr[i4] & 255) << 24) | ((bArr[i5] & 255) << 32) | ((bArr[i6] & 255) << 40) | ((bArr[i7] & 255) << 48) | ((bArr[i8] & 255) << 56);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte readByte() {
        byte[] bArr = this.buf;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int readVarint32() {
        int i;
        int length = this.buf.length - 1;
        checkNotTruncated(this.pos, length);
        byte[] bArr = this.buf;
        int i2 = this.pos;
        int i3 = i2 + 1;
        this.pos = i3;
        byte b = bArr[i2];
        if (b >= 0) {
            return b;
        }
        int i4 = b & Byte.MAX_VALUE;
        checkNotTruncated(i3, length);
        byte[] bArr2 = this.buf;
        int i5 = this.pos;
        int i6 = i5 + 1;
        this.pos = i6;
        byte b2 = bArr2[i5];
        if (b2 >= 0) {
            i = b2 << 7;
        } else {
            i4 |= (b2 & Byte.MAX_VALUE) << 7;
            checkNotTruncated(i6, length);
            byte[] bArr3 = this.buf;
            int i7 = this.pos;
            int i8 = i7 + 1;
            this.pos = i8;
            byte b3 = bArr3[i7];
            if (b3 >= 0) {
                i = b3 << MqttWireMessage.MESSAGE_TYPE_DISCONNECT;
            } else {
                i4 |= (b3 & Byte.MAX_VALUE) << 14;
                checkNotTruncated(i8, length);
                byte[] bArr4 = this.buf;
                int i9 = this.pos;
                int i10 = i9 + 1;
                this.pos = i10;
                byte b4 = bArr4[i9];
                if (b4 >= 0) {
                    i = b4 << 21;
                } else {
                    i4 |= (b4 & Byte.MAX_VALUE) << 21;
                    checkNotTruncated(i10, length);
                    byte b5 = this.buf[this.pos];
                    if ((b5 & 240) != 0) {
                        throw new IllegalArgumentException("Greater than 32-bit varint at position " + this.pos);
                    }
                    i = b5 << 28;
                }
            }
        }
        return i | i4;
    }

    static void checkNotTruncated(int i, int i2) {
        if (i > i2) {
            throw new IllegalArgumentException("Truncated reading position " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long readVarint64() {
        int length = this.buf.length - 1;
        checkNotTruncated(this.pos, length);
        byte[] bArr = this.buf;
        int i = this.pos;
        this.pos = i + 1;
        byte b = bArr[i];
        if (b >= 0) {
            return b;
        }
        long j = b & Byte.MAX_VALUE;
        for (int i2 = 1; b < 0 && i2 < 10; i2++) {
            checkNotTruncated(this.pos, length);
            byte[] bArr2 = this.buf;
            int i3 = this.pos;
            this.pos = i3 + 1;
            b = bArr2[i3];
            if (i2 == 9 && (b & 240) != 0) {
                throw new IllegalArgumentException("Greater than 64-bit varint at position " + (this.pos - 1));
            }
            j |= (b & Byte.MAX_VALUE) << (i2 * 7);
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int remaining() {
        return this.buf.length - this.pos;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean skip(int i) {
        int i2 = this.pos + i;
        byte[] bArr = this.buf;
        if (i2 > bArr.length) {
            this.pos = bArr.length;
            return false;
        }
        this.pos = i2;
        return true;
    }

    public int pos() {
        return this.pos;
    }

    public byte[] toByteArray() {
        return this.buf;
    }
}
