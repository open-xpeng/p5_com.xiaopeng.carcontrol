package org.tukaani.xz.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;

/* loaded from: classes3.dex */
public class EncoderUtil extends Util {
    public static void encodeVLI(OutputStream outputStream, long j) throws IOException {
        while (j >= 128) {
            outputStream.write((byte) (128 | j));
            j >>>= 7;
        }
        outputStream.write((byte) j);
    }

    public static void writeCRC32(OutputStream outputStream, byte[] bArr) throws IOException {
        CRC32 crc32 = new CRC32();
        crc32.update(bArr);
        long value = crc32.getValue();
        for (int i = 0; i < 4; i++) {
            outputStream.write((byte) (value >>> (i * 8)));
        }
    }
}
