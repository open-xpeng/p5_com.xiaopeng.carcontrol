package org.tukaani.xz;

import java.io.InputStream;

/* loaded from: classes3.dex */
public class DeltaOptions extends FilterOptions {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int DISTANCE_MAX = 256;
    public static final int DISTANCE_MIN = 1;
    static /* synthetic */ Class class$org$tukaani$xz$DeltaOptions;
    private int distance = 1;

    static {
        if (class$org$tukaani$xz$DeltaOptions == null) {
            class$org$tukaani$xz$DeltaOptions = class$("org.tukaani.xz.DeltaOptions");
        }
        $assertionsDisabled = true;
    }

    public DeltaOptions() {
    }

    public DeltaOptions(int i) throws UnsupportedOptionsException {
        setDistance(i);
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if ($assertionsDisabled) {
                throw new RuntimeException();
            }
            throw new AssertionError();
        }
    }

    @Override // org.tukaani.xz.FilterOptions
    public int getDecoderMemoryUsage() {
        return 1;
    }

    public int getDistance() {
        return this.distance;
    }

    @Override // org.tukaani.xz.FilterOptions
    public int getEncoderMemoryUsage() {
        return DeltaOutputStream.getMemoryUsage();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.tukaani.xz.FilterOptions
    public FilterEncoder getFilterEncoder() {
        return new DeltaEncoder(this);
    }

    @Override // org.tukaani.xz.FilterOptions
    public InputStream getInputStream(InputStream inputStream) {
        return new DeltaInputStream(inputStream, this.distance);
    }

    @Override // org.tukaani.xz.FilterOptions
    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return new DeltaOutputStream(finishableOutputStream, this);
    }

    public void setDistance(int i) throws UnsupportedOptionsException {
        if (i < 1 || i > 256) {
            throw new UnsupportedOptionsException(new StringBuffer().append("Delta distance must be in the range [1, 256]: ").append(i).toString());
        }
        this.distance = i;
    }
}
