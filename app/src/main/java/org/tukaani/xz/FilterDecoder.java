package org.tukaani.xz;

import java.io.InputStream;

/* loaded from: classes3.dex */
interface FilterDecoder extends FilterCoder {
    InputStream getInputStream(InputStream inputStream);

    int getMemoryUsage();
}
