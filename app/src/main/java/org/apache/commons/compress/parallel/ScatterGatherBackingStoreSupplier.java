package org.apache.commons.compress.parallel;

import java.io.IOException;

/* loaded from: classes3.dex */
public interface ScatterGatherBackingStoreSupplier {
    ScatterGatherBackingStore get() throws IOException;
}
