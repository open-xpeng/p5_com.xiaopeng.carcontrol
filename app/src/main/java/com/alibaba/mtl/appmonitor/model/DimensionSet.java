package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.mtl.log.e.i;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public class DimensionSet implements Parcelable {
    public static final Parcelable.Creator<DimensionSet> CREATOR = new Parcelable.Creator<DimensionSet>() { // from class: com.alibaba.mtl.appmonitor.model.DimensionSet.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: b */
        public DimensionSet createFromParcel(Parcel parcel) {
            return DimensionSet.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a */
        public DimensionSet[] newArray(int i) {
            return new DimensionSet[i];
        }
    };
    private List<Dimension> c = new ArrayList(3);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static DimensionSet create() {
        return new DimensionSet();
    }

    public static DimensionSet create(Collection<String> collection) {
        DimensionSet dimensionSet = new DimensionSet();
        if (collection != null) {
            for (String str : collection) {
                dimensionSet.addDimension(new Dimension(str));
            }
        }
        return dimensionSet;
    }

    public static DimensionSet create(String[] strArr) {
        DimensionSet dimensionSet = new DimensionSet();
        if (strArr != null) {
            for (String str : strArr) {
                dimensionSet.addDimension(new Dimension(str));
            }
        }
        return dimensionSet;
    }

    private DimensionSet() {
    }

    public boolean valid(DimensionValueSet dimensionValueSet) {
        List<Dimension> list = this.c;
        if (list != null) {
            if (dimensionValueSet != null) {
                for (Dimension dimension : list) {
                    if (!dimensionValueSet.containValue(dimension.getName())) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public DimensionSet addDimension(Dimension dimension) {
        if (this.c.contains(dimension)) {
            return this;
        }
        this.c.add(dimension);
        return this;
    }

    public DimensionSet addDimension(String str) {
        return addDimension(new Dimension(str));
    }

    public DimensionSet addDimension(String str, String str2) {
        return addDimension(new Dimension(str, str2));
    }

    public Dimension getDimension(String str) {
        for (Dimension dimension : this.c) {
            if (dimension.getName().equals(str)) {
                return dimension;
            }
        }
        return null;
    }

    public void setConstantValue(DimensionValueSet dimensionValueSet) {
        List<Dimension> list = this.c;
        if (list == null || dimensionValueSet == null) {
            return;
        }
        for (Dimension dimension : list) {
            if (dimension.getConstantValue() != null && dimensionValueSet.getValue(dimension.getName()) == null) {
                dimensionValueSet.setValue(dimension.getName(), dimension.getConstantValue());
            }
        }
    }

    public List<Dimension> getDimensions() {
        return this.c;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        List<Dimension> list = this.c;
        if (list != null) {
            try {
                Object[] array = list.toArray();
                Dimension[] dimensionArr = null;
                if (array != null) {
                    dimensionArr = new Dimension[array.length];
                    for (int i2 = 0; i2 < array.length; i2++) {
                        dimensionArr[i2] = (Dimension) array[i2];
                    }
                }
                parcel.writeParcelableArray(dimensionArr, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static DimensionSet a(Parcel parcel) {
        DimensionSet create = create();
        try {
            Parcelable[] readParcelableArray = parcel.readParcelableArray(DimensionSet.class.getClassLoader());
            if (readParcelableArray != null) {
                if (create.c == null) {
                    create.c = new ArrayList();
                }
                for (int i = 0; i < readParcelableArray.length; i++) {
                    if (readParcelableArray[i] == null || !(readParcelableArray[i] instanceof Dimension)) {
                        i.a("DimensionSet", "parcelables[i]:", readParcelableArray[i]);
                    } else {
                        create.c.add((Dimension) readParcelableArray[i]);
                    }
                }
            }
        } catch (Throwable th) {
            i.a("DimensionSet", "[readFromParcel]", th);
        }
        return create;
    }
}
