package com.xiaopeng.appstore.storeprovider;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class SimpleRequest extends AssembleRequest {
    public static final Parcelable.Creator<SimpleRequest> CREATOR = new Parcelable.Creator<SimpleRequest>() { // from class: com.xiaopeng.appstore.storeprovider.SimpleRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimpleRequest createFromParcel(Parcel parcel) {
            return new SimpleRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimpleRequest[] newArray(int i) {
            return new SimpleRequest[i];
        }
    };
    private final String mKey;
    private final int mResType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SimpleRequest(int i, int i2, String str) {
        super(i);
        this.mResType = i2;
        this.mKey = str;
    }

    @Override // com.xiaopeng.appstore.storeprovider.AssembleRequest
    public String toString() {
        return "SimpleRequest{action=" + this.mAction + ", key='" + this.mKey + "', resType=" + this.mResType + '}';
    }

    public String getKey() {
        return this.mKey;
    }

    public int getResType() {
        return this.mResType;
    }

    protected SimpleRequest(Parcel parcel) {
        super(parcel);
        this.mResType = parcel.readInt();
        this.mKey = parcel.readString();
    }

    @Override // com.xiaopeng.appstore.storeprovider.AssembleRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mResType);
        parcel.writeString(this.mKey);
    }
}
