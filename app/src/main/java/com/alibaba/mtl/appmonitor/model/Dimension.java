package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class Dimension implements Parcelable {
    public static final Parcelable.Creator<Dimension> CREATOR = new Parcelable.Creator<Dimension>() { // from class: com.alibaba.mtl.appmonitor.model.Dimension.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: b */
        public Dimension createFromParcel(Parcel parcel) {
            return Dimension.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a */
        public Dimension[] newArray(int i) {
            return new Dimension[i];
        }
    };
    protected String name;
    protected String y;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Dimension() {
        this.y = "null";
    }

    public Dimension(String str) {
        this(str, null);
    }

    public Dimension(String str, String str2) {
        this.y = "null";
        this.name = str;
        this.y = str2 == null ? "null" : str2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getConstantValue() {
        return this.y;
    }

    public void setConstantValue(String str) {
        this.y = str;
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
            Dimension dimension = (Dimension) obj;
            String str = this.name;
            if (str == null) {
                if (dimension.name != null) {
                    return false;
                }
            } else if (!str.equals(dimension.name)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.y);
        parcel.writeString(this.name);
    }

    static Dimension a(Parcel parcel) {
        try {
            return new Dimension(parcel.readString(), parcel.readString());
        } catch (Throwable unused) {
            return null;
        }
    }
}
