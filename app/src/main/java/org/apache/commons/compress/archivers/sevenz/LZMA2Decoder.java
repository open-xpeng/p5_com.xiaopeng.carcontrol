package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.LZMA2InputStream;
import org.tukaani.xz.LZMA2Options;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class LZMA2Decoder extends CoderBase {
    /* JADX INFO: Access modifiers changed from: package-private */
    public LZMA2Decoder() {
        super(LZMA2Options.class, Number.class);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
        try {
            return new LZMA2InputStream(inputStream, getDictionarySize(coder));
        } catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public OutputStream encode(OutputStream outputStream, Object obj) throws IOException {
        return getOptions(obj).getOutputStream(new FinishableWrapperOutputStream(outputStream));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public byte[] getOptionsAsProperties(Object obj) {
        int dictSize = getDictSize(obj);
        int numberOfLeadingZeros = Integer.numberOfLeadingZeros(dictSize);
        return new byte[]{(byte) (((19 - numberOfLeadingZeros) * 2) + ((dictSize >>> (30 - numberOfLeadingZeros)) - 2))};
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public Object getOptionsFromCoder(Coder coder, InputStream inputStream) {
        return Integer.valueOf(getDictionarySize(coder));
    }

    private int getDictSize(Object obj) {
        if (obj instanceof LZMA2Options) {
            return ((LZMA2Options) obj).getDictSize();
        }
        return numberOptionOrDefault(obj);
    }

    private int getDictionarySize(Coder coder) throws IllegalArgumentException {
        int i = coder.properties[0] & 255;
        if ((i & (-64)) == 0) {
            if (i <= 40) {
                if (i == 40) {
                    return -1;
                }
                return ((i & 1) | 2) << ((i / 2) + 11);
            }
            throw new IllegalArgumentException("Dictionary larger than 4GiB maximum size");
        }
        throw new IllegalArgumentException("Unsupported LZMA2 property bits");
    }

    private LZMA2Options getOptions(Object obj) throws IOException {
        if (obj instanceof LZMA2Options) {
            return (LZMA2Options) obj;
        }
        LZMA2Options lZMA2Options = new LZMA2Options();
        lZMA2Options.setDictSize(numberOptionOrDefault(obj));
        return lZMA2Options;
    }

    private int numberOptionOrDefault(Object obj) {
        return numberOptionOrDefault(obj, 8388608);
    }
}
