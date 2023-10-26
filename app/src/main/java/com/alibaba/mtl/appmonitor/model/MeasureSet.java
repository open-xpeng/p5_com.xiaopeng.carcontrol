package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public class MeasureSet implements Parcelable {
    public static final Parcelable.Creator<MeasureSet> CREATOR = new Parcelable.Creator<MeasureSet>() { // from class: com.alibaba.mtl.appmonitor.model.MeasureSet.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: b */
        public MeasureSet createFromParcel(Parcel parcel) {
            return MeasureSet.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a */
        public MeasureSet[] newArray(int i) {
            return new MeasureSet[i];
        }
    };
    private List<Measure> d = new ArrayList(3);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static MeasureSet create() {
        return new MeasureSet();
    }

    public static MeasureSet create(Collection<String> collection) {
        MeasureSet measureSet = new MeasureSet();
        if (collection != null) {
            for (String str : collection) {
                measureSet.addMeasure(str);
            }
        }
        return measureSet;
    }

    public static MeasureSet create(String[] strArr) {
        MeasureSet measureSet = new MeasureSet();
        if (strArr != null) {
            for (String str : strArr) {
                measureSet.addMeasure(str);
            }
        }
        return measureSet;
    }

    private MeasureSet() {
    }

    public boolean valid(MeasureValueSet measureValueSet) {
        if (this.d != null) {
            if (measureValueSet != null) {
                for (int i = 0; i < this.d.size(); i++) {
                    Measure measure = this.d.get(i);
                    if (measure != null) {
                        String name = measure.getName();
                        if (!measureValueSet.containValue(name) || !measure.valid(measureValueSet.getValue(name))) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public MeasureSet addMeasure(Measure measure) {
        if (!this.d.contains(measure)) {
            this.d.add(measure);
        }
        return this;
    }

    public MeasureSet addMeasure(String str) {
        return addMeasure(new Measure(str));
    }

    public Measure getMeasure(String str) {
        for (Measure measure : this.d) {
            if (measure.getName().equals(str)) {
                return measure;
            }
        }
        return null;
    }

    public List<Measure> getMeasures() {
        return this.d;
    }

    public void setConstantValue(MeasureValueSet measureValueSet) {
        List<Measure> list = this.d;
        if (list == null || measureValueSet == null) {
            return;
        }
        for (Measure measure : list) {
            if (measure.getConstantValue() != null && measureValueSet.getValue(measure.getName()) == null) {
                measureValueSet.setValue(measure.getName(), measure.getConstantValue().doubleValue());
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        List<Measure> list = this.d;
        if (list != null) {
            try {
                Object[] array = list.toArray();
                Measure[] measureArr = null;
                if (array != null) {
                    measureArr = new Measure[array.length];
                    for (int i2 = 0; i2 < array.length; i2++) {
                        measureArr[i2] = (Measure) array[i2];
                    }
                }
                parcel.writeParcelableArray(measureArr, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static MeasureSet a(Parcel parcel) {
        MeasureSet create = create();
        try {
            Parcelable[] readParcelableArray = parcel.readParcelableArray(MeasureSet.class.getClassLoader());
            if (readParcelableArray != null) {
                ArrayList arrayList = new ArrayList(readParcelableArray.length);
                for (Parcelable parcelable : readParcelableArray) {
                    arrayList.add((Measure) parcelable);
                }
                create.d = arrayList;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return create;
    }

    public void upateMeasures(List<Measure> list) {
        int size = this.d.size();
        int size2 = list.size();
        for (int i = 0; i < size; i++) {
            for (int i2 = 0; i2 < size2; i2++) {
                if (TextUtils.equals(this.d.get(i).name, list.get(i2).name)) {
                    this.d.get(i).setMax(list.get(i2).getMax());
                    this.d.get(i).setMin(list.get(i2).getMin());
                }
            }
        }
    }

    public void upateMeasure(Measure measure) {
        int size = this.d.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(this.d.get(i).name, measure.name)) {
                this.d.get(i).setMax(measure.getMax());
                this.d.get(i).setMin(measure.getMin());
                this.d.get(i).setConstantValue(measure.getConstantValue());
            }
        }
    }
}
