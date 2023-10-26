package org.tukaani.xz.common;

/* loaded from: classes3.dex */
public class Util {
    public static final long BACKWARD_SIZE_MAX = 17179869184L;
    public static final int BLOCK_HEADER_SIZE_MAX = 1024;
    public static final int STREAM_HEADER_SIZE = 12;
    public static final long VLI_MAX = Long.MAX_VALUE;
    public static final int VLI_SIZE_MAX = 9;

    public static int getVLISize(long j) {
        int i = 0;
        do {
            i++;
            j >>= 7;
        } while (j != 0);
        return i;
    }
}
