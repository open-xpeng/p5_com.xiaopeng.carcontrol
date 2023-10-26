package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class Measure implements Parcelable {
    public static final Parcelable.Creator<Measure> CREATOR = new Parcelable.Creator<Measure>() { // from class: com.alibaba.mtl.appmonitor.model.Measure.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: b */
        public Measure createFromParcel(Parcel parcel) {
            return Measure.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a */
        public Measure[] newArray(int i) {
            return new Measure[i];
        }
    };
    protected Double a;
    protected Double b;
    protected Double c;
    protected String name;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Measure(String str) {
        this(str, Double.valueOf(0.0d));
    }

    public Measure(String str, Double d) {
        this(str, d, Double.valueOf(0.0d), null);
    }

    public Measure(String str, Double d, Double d2, Double d3) {
        Double valueOf = Double.valueOf(0.0d);
        this.a = valueOf;
        this.b = valueOf;
        this.c = valueOf;
        this.a = d2;
        this.b = d3;
        this.name = str;
        this.c = Double.valueOf(d != null ? d.doubleValue() : 0.0d);
    }

    public void setRange(Double d, Double d2) {
        this.a = d;
        this.b = d2;
    }

    public Double getMin() {
        return this.a;
    }

    public void setMin(Double d) {
        this.a = d;
    }

    public Double getMax() {
        return this.b;
    }

    public void setMax(Double d) {
        this.b = d;
    }

    public String getName() {
        return this.name;
    }

    public Double getConstantValue() {
        return this.c;
    }

    public void setConstantValue(Double d) {
        this.c = d;
    }

    public boolean valid(MeasureValue measureValue) {
        Double valueOf = Double.valueOf(measureValue.getValue());
        return valueOf != null && (this.a == null || valueOf.doubleValue() >= this.a.doubleValue()) && (this.b == null || valueOf.doubleValue() <= this.b.doubleValue());
    }

    public int hashCode() {
        String str = this.name;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Measure measure = (Measure) obj;
            String str = this.name;
            if (str == null) {
                if (measure.name != null) {
                    return false;
                }
            } else if (!str.equals(measure.name)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        try {
            int i2 = 0;
            parcel.writeInt(this.b == null ? 0 : 1);
            Double d = this.b;
            if (d != null) {
                parcel.writeDouble(d.doubleValue());
            }
            parcel.writeInt(this.a == null ? 0 : 1);
            Double d2 = this.a;
            if (d2 != null) {
                parcel.writeDouble(d2.doubleValue());
            }
            parcel.writeString(this.name);
            if (this.c != null) {
                i2 = 1;
            }
            parcel.writeInt(i2);
            Double d3 = this.c;
            if (d3 != null) {
                parcel.writeDouble(d3.doubleValue());
            }
        } catch (Throwable unused) {
        }
    }

    static Measure a(Parcel parcel) {
        try {
            boolean z = true;
            Double valueOf = !(parcel.readInt() == 0) ? Double.valueOf(parcel.readDouble()) : null;
            Double valueOf2 = !(parcel.readInt() == 0) ? Double.valueOf(parcel.readDouble()) : null;
            String readString = parcel.readString();
            if (parcel.readInt() != 0) {
                z = false;
            }
            return new Measure(readString, !z ? Double.valueOf(parcel.readDouble()) : null, valueOf2, valueOf);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
