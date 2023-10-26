package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.mtl.appmonitor.c.a;
import com.alibaba.mtl.appmonitor.c.b;

/* loaded from: classes.dex */
public class MeasureValue implements Parcelable, b {
    public static final Parcelable.Creator<MeasureValue> CREATOR = new Parcelable.Creator<MeasureValue>() { // from class: com.alibaba.mtl.appmonitor.model.MeasureValue.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: b */
        public MeasureValue createFromParcel(Parcel parcel) {
            return MeasureValue.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a */
        public MeasureValue[] newArray(int i) {
            return new MeasureValue[i];
        }
    };
    private Double d;
    private boolean n;
    private double value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public MeasureValue() {
    }

    @Deprecated
    public MeasureValue(double d) {
        this.value = d;
    }

    @Deprecated
    public MeasureValue(double d, double d2) {
        this.d = Double.valueOf(d2);
        this.value = d;
        this.n = false;
    }

    public static MeasureValue create() {
        return (MeasureValue) a.a().a(MeasureValue.class, new Object[0]);
    }

    public static MeasureValue create(double d) {
        return (MeasureValue) a.a().a(MeasureValue.class, Double.valueOf(d));
    }

    public static MeasureValue create(double d, double d2) {
        return (MeasureValue) a.a().a(MeasureValue.class, Double.valueOf(d), Double.valueOf(d2));
    }

    public Double getOffset() {
        return this.d;
    }

    public boolean isFinish() {
        return this.n;
    }

    public void setFinish(boolean z) {
        this.n = z;
    }

    public void setOffset(double d) {
        this.d = Double.valueOf(d);
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double d) {
        this.value = d;
    }

    public synchronized void merge(MeasureValue measureValue) {
        if (measureValue == null) {
            return;
        }
        try {
            this.value += measureValue.getValue();
            if (measureValue.getOffset() != null) {
                if (this.d == null) {
                    this.d = Double.valueOf(0.0d);
                }
                this.d = Double.valueOf(this.d.doubleValue() + measureValue.getOffset().doubleValue());
            }
        } catch (Throwable unused) {
        }
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public synchronized void clean() {
        this.value = 0.0d;
        this.d = null;
        this.n = false;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public synchronized void fill(Object... objArr) {
        if (objArr == null) {
            return;
        }
        if (objArr.length > 0) {
            this.value = ((Double) objArr[0]).doubleValue();
        }
        if (objArr.length > 1) {
            this.d = (Double) objArr[1];
            this.n = false;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeInt(this.n ? 1 : 0);
            Double d = this.d;
            parcel.writeDouble(d == null ? 0.0d : d.doubleValue());
            parcel.writeDouble(this.value);
        } catch (Throwable unused) {
        }
    }

    static MeasureValue a(Parcel parcel) {
        MeasureValue measureValue = null;
        try {
            boolean z = parcel.readInt() != 0;
            Double valueOf = Double.valueOf(parcel.readDouble());
            double readDouble = parcel.readDouble();
            measureValue = create();
            measureValue.n = z;
            measureValue.d = valueOf;
            measureValue.value = readDouble;
            return measureValue;
        } catch (Throwable th) {
            th.printStackTrace();
            return measureValue;
        }
    }
}
