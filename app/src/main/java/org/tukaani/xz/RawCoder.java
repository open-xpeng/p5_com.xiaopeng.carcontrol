package org.tukaani.xz;

/* loaded from: classes3.dex */
class RawCoder {
    RawCoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void validate(FilterCoder[] filterCoderArr) throws UnsupportedOptionsException {
        for (int i = 0; i < filterCoderArr.length - 1; i++) {
            if (!filterCoderArr[i].nonLastOK()) {
                throw new UnsupportedOptionsException("Unsupported XZ filter chain");
            }
        }
        if (!filterCoderArr[filterCoderArr.length - 1].lastOK()) {
            throw new UnsupportedOptionsException("Unsupported XZ filter chain");
        }
        int i2 = 0;
        for (FilterCoder filterCoder : filterCoderArr) {
            if (filterCoder.changesSize()) {
                i2++;
            }
        }
        if (i2 > 3) {
            throw new UnsupportedOptionsException("Unsupported XZ filter chain");
        }
    }
}
