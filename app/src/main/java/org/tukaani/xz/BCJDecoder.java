package org.tukaani.xz;

import java.io.InputStream;
import org.tukaani.xz.simple.ARM;
import org.tukaani.xz.simple.ARMThumb;
import org.tukaani.xz.simple.IA64;
import org.tukaani.xz.simple.PowerPC;
import org.tukaani.xz.simple.SPARC;
import org.tukaani.xz.simple.SimpleFilter;
import org.tukaani.xz.simple.X86;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class BCJDecoder extends BCJCoder implements FilterDecoder {
    static final /* synthetic */ boolean $assertionsDisabled;
    static /* synthetic */ Class class$org$tukaani$xz$BCJDecoder;
    private final long filterID;
    private final int startOffset;

    static {
        if (class$org$tukaani$xz$BCJDecoder == null) {
            class$org$tukaani$xz$BCJDecoder = class$("org.tukaani.xz.BCJDecoder");
        }
        $assertionsDisabled = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BCJDecoder(long j, byte[] bArr) throws UnsupportedOptionsException {
        if (!$assertionsDisabled && !isBCJFilterID(j)) {
            throw new AssertionError();
        }
        this.filterID = j;
        if (bArr.length == 0) {
            this.startOffset = 0;
        } else if (bArr.length != 4) {
            throw new UnsupportedOptionsException("Unsupported BCJ filter properties");
        } else {
            int i = 0;
            for (int i2 = 0; i2 < 4; i2++) {
                i |= (bArr[i2] & 255) << (i2 * 8);
            }
            this.startOffset = i;
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // org.tukaani.xz.FilterDecoder
    public InputStream getInputStream(InputStream inputStream) {
        SimpleFilter simpleFilter;
        long j = this.filterID;
        if (j == 4) {
            simpleFilter = new X86(false, this.startOffset);
        } else if (j == 5) {
            simpleFilter = new PowerPC(false, this.startOffset);
        } else if (j == 6) {
            simpleFilter = new IA64(false, this.startOffset);
        } else if (j == 7) {
            simpleFilter = new ARM(false, this.startOffset);
        } else if (j == 8) {
            simpleFilter = new ARMThumb(false, this.startOffset);
        } else if (j == 9) {
            simpleFilter = new SPARC(false, this.startOffset);
        } else if (!$assertionsDisabled) {
            throw new AssertionError();
        } else {
            simpleFilter = null;
        }
        return new SimpleInputStream(inputStream, simpleFilter);
    }

    @Override // org.tukaani.xz.FilterDecoder
    public int getMemoryUsage() {
        return SimpleInputStream.getMemoryUsage();
    }
}
