package org.tukaani.xz;

/* loaded from: classes3.dex */
interface FilterEncoder extends FilterCoder {
    long getFilterID();

    byte[] getFilterProps();

    FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream);

    boolean supportsFlushing();
}
