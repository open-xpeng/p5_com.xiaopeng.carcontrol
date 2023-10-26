package com.xiaopeng.speech.common.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class SliceData implements Parcelable {
    public static final Parcelable.Creator<SliceData> CREATOR = new Parcelable.Creator<SliceData>() { // from class: com.xiaopeng.speech.common.bean.SliceData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SliceData createFromParcel(Parcel parcel) {
            return new SliceData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SliceData[] newArray(int i) {
            return new SliceData[i];
        }
    };
    private byte[] data;
    private int totalLength;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SliceData(byte[] bArr, int i) {
        this.data = bArr;
        this.totalLength = i;
    }

    protected SliceData(Parcel parcel) {
        this.data = parcel.readBlob();
        this.totalLength = parcel.readInt();
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public int getTotalLength() {
        return this.totalLength;
    }

    public void setTotalLength(int i) {
        this.totalLength = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBlob(this.data);
        parcel.writeInt(this.totalLength);
    }

    public String toString() {
        return "SliceData{data=" + Arrays.toString(this.data) + ", totalLength=" + this.totalLength + '}';
    }
}
