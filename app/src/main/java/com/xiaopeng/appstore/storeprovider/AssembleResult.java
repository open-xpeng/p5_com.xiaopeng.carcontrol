package com.xiaopeng.appstore.storeprovider;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class AssembleResult implements Parcelable {
    public static final Parcelable.Creator<AssembleResult> CREATOR = new Parcelable.Creator<AssembleResult>() { // from class: com.xiaopeng.appstore.storeprovider.AssembleResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssembleResult createFromParcel(Parcel parcel) {
            return new AssembleResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssembleResult[] newArray(int i) {
            return new AssembleResult[i];
        }
    };
    public static final int ERROR_EXECUTE = 10002;
    public static final int ERROR_FORCE_UPDATE = 201;
    public static final int ERROR_HTTP_WRONG = 301;
    public static final int ERROR_INVALID_ARGUMENT = 10001;
    public static final int ERROR_NOT_DONE_AGREEMENT = 105;
    public static final int ERROR_NOT_LOGIN = 101;
    public static final int ERROR_REPEAT_TASK = 401;
    public static final int ERROR_SUSPEND = 201;
    public static final int SUCCESS_CODE = 1;
    private final int mCode;
    private final String mDesc;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static AssembleResult createSuccess() {
        return new AssembleResult(1, "Success");
    }

    public static AssembleResult createFail(int i, String str) {
        return new AssembleResult(i, str);
    }

    private AssembleResult(int i, String str) {
        this.mCode = i;
        this.mDesc = str;
    }

    protected AssembleResult(Parcel parcel) {
        this.mCode = parcel.readInt();
        this.mDesc = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCode);
        parcel.writeString(this.mDesc);
    }

    public boolean isSuccessful() {
        return this.mCode == 1;
    }

    public int getCode() {
        return this.mCode;
    }

    public String getDesc() {
        return this.mDesc;
    }

    public String toString() {
        return "AssembleResult{code=" + this.mCode + ", desc='" + this.mDesc + "'}";
    }
}
