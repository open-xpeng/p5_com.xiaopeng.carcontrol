package org.tukaani.xz.simple;

/* loaded from: classes3.dex */
public final class X86 implements SimpleFilter {
    private static final boolean[] MASK_TO_ALLOWED_STATUS = {true, true, true, false, true, false, false, false};
    private static final int[] MASK_TO_BIT_NUMBER = {0, 1, 2, 2, 3, 3, 3, 3};
    private final boolean isEncoder;
    private int pos;
    private int prevMask = 0;

    public X86(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i + 5;
    }

    private static boolean test86MSByte(byte b) {
        int i = b & 255;
        return i == 0 || i == 255;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x003c, code lost:
        if (test86MSByte(r11[(r1 + 4) - org.tukaani.xz.simple.X86.MASK_TO_BIT_NUMBER[r0]]) != false) goto L26;
     */
    @Override // org.tukaani.xz.simple.SimpleFilter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int code(byte[] r11, int r12, int r13) {
        /*
            Method dump skipped, instructions count: 205
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.simple.X86.code(byte[], int, int):int");
    }
}
