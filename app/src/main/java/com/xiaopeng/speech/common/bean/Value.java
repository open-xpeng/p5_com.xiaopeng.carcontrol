package com.xiaopeng.speech.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class Value implements Parcelable {
    private static final int BOOLEAN_TYPE = 5;
    private static final int DOUBLE_TYPE = 2;
    private static final int FLOAT_ARRAY_TYPE = 7;
    private static final int INTEGER_ARRAY_TYPE = 6;
    private static final int INTEGER_TYPE = 1;
    private static final int STRING_TYPE = 4;
    private int classType;
    private Boolean mBoolean;
    private Double mDouble;
    private float[] mFloatArray;
    private Integer mInteger;
    private int[] mIntegerArray;
    private String mString;
    public static final Value VOID = new Value();
    public static final Value INTERRUPT = new Value();
    public static final Parcelable.Creator<Value> CREATOR = new Parcelable.Creator<Value>() { // from class: com.xiaopeng.speech.common.bean.Value.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Value createFromParcel(Parcel parcel) {
            return new Value(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Value[] newArray(int i) {
            return new Value[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Value() {
    }

    public Value(Object obj) {
        initValue(obj);
    }

    private void initValue(Object obj) {
        if (obj instanceof Double) {
            this.classType = 2;
            this.mDouble = (Double) obj;
        } else if (obj instanceof String) {
            this.classType = 4;
            this.mString = (String) obj;
        } else if (obj instanceof Boolean) {
            this.classType = 5;
            this.mBoolean = (Boolean) obj;
        } else if (obj instanceof int[]) {
            this.classType = 6;
            this.mIntegerArray = (int[]) obj;
        } else if (obj instanceof float[]) {
            this.classType = 7;
            this.mFloatArray = (float[]) obj;
        } else if (obj instanceof Integer) {
            this.classType = 1;
            this.mInteger = (Integer) obj;
        } else if (obj instanceof Float) {
            this.classType = 2;
            this.mDouble = Double.valueOf(((Float) obj).doubleValue());
        } else {
            this.classType = 0;
        }
    }

    protected Value(Parcel parcel) {
        int readInt = parcel.readInt();
        this.classType = readInt;
        if (readInt == 2) {
            this.mDouble = Double.valueOf(parcel.readDouble());
        } else if (readInt == 4) {
            this.mString = parcel.readString();
        } else {
            if (readInt == 5) {
                this.mBoolean = Boolean.valueOf(parcel.readInt() == 1);
            } else if (readInt == 6) {
                this.mIntegerArray = parcel.createIntArray();
            } else if (readInt == 7) {
                this.mFloatArray = parcel.createFloatArray();
            } else if (readInt == 1) {
                this.mInteger = Integer.valueOf(parcel.readInt());
            }
        }
    }

    public boolean isDouble() {
        return this.classType == 2;
    }

    public double getDouble() {
        return this.mDouble.doubleValue();
    }

    public boolean isString() {
        return this.classType == 4;
    }

    public String getString() {
        return this.mString;
    }

    public boolean isBoolean() {
        return this.classType == 5;
    }

    public boolean getBoolean() {
        return this.mBoolean.booleanValue();
    }

    public boolean isInteger() {
        return this.classType == 1;
    }

    public Integer getInteger() {
        return this.mInteger;
    }

    public boolean isNumber() {
        int i = this.classType;
        return i == 1 || i == 2;
    }

    public Number getNumber() {
        int i = this.classType;
        if (i == 1) {
            return this.mInteger;
        }
        if (i == 2) {
            return this.mDouble;
        }
        return 0;
    }

    public boolean isArray() {
        int i = this.classType;
        return i == 6 || i == 7;
    }

    public Object getDataFromArray(int i) {
        int i2 = this.classType;
        if (i2 == 6) {
            int[] iArr = this.mIntegerArray;
            if (i < iArr.length) {
                return Integer.valueOf(iArr[i]);
            }
            return null;
        } else if (i2 == 7) {
            float[] fArr = this.mFloatArray;
            if (i < fArr.length) {
                return Float.valueOf(fArr[i]);
            }
            return null;
        } else {
            return null;
        }
    }

    public boolean isFloatArray() {
        return this.classType == 7;
    }

    public float[] getFloatArray() {
        return this.mFloatArray;
    }

    public boolean isIntegerArray() {
        return this.classType == 6;
    }

    public int[] getIntegerArray() {
        return this.mIntegerArray;
    }

    public Object getArgs() {
        int i = this.classType;
        if (i == 1) {
            return this.mInteger;
        }
        if (i == 4) {
            return this.mString;
        }
        if (i == 2) {
            return this.mDouble;
        }
        if (i == 5) {
            return this.mBoolean;
        }
        if (i == 7) {
            return this.mFloatArray;
        }
        if (i == 6) {
            return this.mIntegerArray;
        }
        return null;
    }

    public void setIntegerArray(int[] iArr) {
        this.classType = 6;
        this.mIntegerArray = iArr;
    }

    public void setFloatArray(float[] fArr) {
        this.classType = 7;
        this.mFloatArray = fArr;
    }

    public void setDouble(Double d) {
        this.classType = 2;
        this.mDouble = d;
    }

    public void setString(String str) {
        this.classType = 4;
        this.mString = str;
    }

    public void setBoolean(Boolean bool) {
        this.classType = 5;
        this.mBoolean = bool;
    }

    public void setInteger(Integer num) {
        this.classType = 1;
        this.mInteger = num;
    }

    public void setValue(Object obj) {
        initValue(obj);
    }

    public int getClassType() {
        return this.classType;
    }

    public void setClassType(int i) {
        this.classType = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.classType);
        int i2 = this.classType;
        if (i2 == 2) {
            parcel.writeDouble(this.mDouble.doubleValue());
        } else if (i2 == 4) {
            parcel.writeString(this.mString);
        } else if (i2 == 5) {
            parcel.writeInt(this.mBoolean.booleanValue() ? 1 : 0);
        } else if (i2 == 6) {
            parcel.writeIntArray(this.mIntegerArray);
        } else if (i2 == 7) {
            parcel.writeFloatArray(this.mFloatArray);
        } else if (i2 == 1) {
            parcel.writeInt(this.mInteger.intValue());
        }
    }

    public String toString() {
        return "Value{mInteger=" + this.mInteger + ", mDouble=" + this.mDouble + ", mString='" + this.mString + "', mBoolean=" + this.mBoolean + ", classType=" + this.classType + '}';
    }

    public String toObjAddress() {
        return getClass().getName() + '@' + Integer.toHexString(hashCode());
    }
}
