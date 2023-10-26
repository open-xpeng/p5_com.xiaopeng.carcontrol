package org.tukaani.xz.common;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import org.tukaani.xz.CorruptedInputException;
import org.tukaani.xz.UnsupportedOptionsException;
import org.tukaani.xz.XZ;
import org.tukaani.xz.XZFormatException;

/* loaded from: classes3.dex */
public class DecoderUtil extends Util {
    public static boolean areStreamFlagsEqual(StreamFlags streamFlags, StreamFlags streamFlags2) {
        return streamFlags.checkType == streamFlags2.checkType;
    }

    private static StreamFlags decodeStreamFlags(byte[] bArr, int i) throws UnsupportedOptionsException {
        if (bArr[i] == 0) {
            int i2 = i + 1;
            if ((bArr[i2] & 255) < 16) {
                StreamFlags streamFlags = new StreamFlags();
                streamFlags.checkType = bArr[i2];
                return streamFlags;
            }
        }
        throw new UnsupportedOptionsException();
    }

    public static StreamFlags decodeStreamFooter(byte[] bArr) throws IOException {
        if (bArr[10] == XZ.FOOTER_MAGIC[0] && bArr[11] == XZ.FOOTER_MAGIC[1]) {
            if (isCRC32Valid(bArr, 4, 6, 0)) {
                try {
                    StreamFlags decodeStreamFlags = decodeStreamFlags(bArr, 8);
                    decodeStreamFlags.backwardSize = 0L;
                    for (int i = 0; i < 4; i++) {
                        decodeStreamFlags.backwardSize |= (bArr[i + 4] & 255) << (i * 8);
                    }
                    decodeStreamFlags.backwardSize = (decodeStreamFlags.backwardSize + 1) * 4;
                    return decodeStreamFlags;
                } catch (UnsupportedOptionsException unused) {
                    throw new UnsupportedOptionsException("Unsupported options in XZ Stream Footer");
                }
            }
            throw new CorruptedInputException("XZ Stream Footer is corrupt");
        }
        throw new CorruptedInputException("XZ Stream Footer is corrupt");
    }

    public static StreamFlags decodeStreamHeader(byte[] bArr) throws IOException {
        for (int i = 0; i < XZ.HEADER_MAGIC.length; i++) {
            if (bArr[i] != XZ.HEADER_MAGIC[i]) {
                throw new XZFormatException();
            }
        }
        if (isCRC32Valid(bArr, XZ.HEADER_MAGIC.length, 2, XZ.HEADER_MAGIC.length + 2)) {
            try {
                return decodeStreamFlags(bArr, XZ.HEADER_MAGIC.length);
            } catch (UnsupportedOptionsException unused) {
                throw new UnsupportedOptionsException("Unsupported options in XZ Stream Header");
            }
        }
        throw new CorruptedInputException("XZ Stream Header is corrupt");
    }

    public static long decodeVLI(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            long j = read & 127;
            int i = 0;
            while ((read & 128) != 0) {
                i++;
                if (i >= 9) {
                    throw new CorruptedInputException();
                }
                read = inputStream.read();
                if (read == -1) {
                    throw new EOFException();
                }
                if (read == 0) {
                    throw new CorruptedInputException();
                }
                j |= (read & 127) << (i * 7);
            }
            return j;
        }
        throw new EOFException();
    }

    public static boolean isCRC32Valid(byte[] bArr, int i, int i2, int i3) {
        CRC32 crc32 = new CRC32();
        crc32.update(bArr, i, i2);
        long value = crc32.getValue();
        for (int i4 = 0; i4 < 4; i4++) {
            if (((byte) (value >>> (i4 * 8))) != bArr[i3 + i4]) {
                return false;
            }
        }
        return true;
    }
}
