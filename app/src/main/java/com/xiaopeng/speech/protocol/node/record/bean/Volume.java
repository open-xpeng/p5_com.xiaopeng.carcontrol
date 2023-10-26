package com.xiaopeng.speech.protocol.node.record.bean;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class Volume implements Parcelable {
    public static final Parcelable.Creator<Volume> CREATOR = new Parcelable.Creator<Volume>() { // from class: com.xiaopeng.speech.protocol.node.record.bean.Volume.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Volume createFromParcel(Parcel parcel) {
            return new Volume(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Volume[] newArray(int i) {
            return new Volume[i];
        }
    };
    public double volume;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static Volume fromJson(String str) {
        Volume volume = new Volume();
        try {
            volume.volume = new JSONObject(str).optDouble("volume");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return volume;
    }

    public Volume() {
    }

    protected Volume(Parcel parcel) {
        this.volume = parcel.readDouble();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.volume);
    }
}
