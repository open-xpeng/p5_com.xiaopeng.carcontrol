package org.tukaani.xz;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public abstract class FilterOptions implements Cloneable {
    public static int getDecoderMemoryUsage(FilterOptions[] filterOptionsArr) {
        int i = 0;
        for (FilterOptions filterOptions : filterOptionsArr) {
            i += filterOptions.getDecoderMemoryUsage();
        }
        return i;
    }

    public static int getEncoderMemoryUsage(FilterOptions[] filterOptionsArr) {
        int i = 0;
        for (FilterOptions filterOptions : filterOptionsArr) {
            i += filterOptions.getEncoderMemoryUsage();
        }
        return i;
    }

    public abstract int getDecoderMemoryUsage();

    public abstract int getEncoderMemoryUsage();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract FilterEncoder getFilterEncoder();

    public abstract InputStream getInputStream(InputStream inputStream) throws IOException;

    public abstract FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream);
}
