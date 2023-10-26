package com.xiaopeng.speech.actorapi;

import android.os.Parcel;
import com.xiaopeng.speech.actor.Actor;

/* loaded from: classes2.dex */
public class DataActor extends Actor {
    public static final String NAME = "DataActor";
    protected String mEvent;
    protected boolean mIsSupport;
    protected String mResultData;

    public DataActor(String str) {
        super(NAME);
        this.mEvent = "";
        this.mIsSupport = true;
        this.mEvent = str;
    }

    public DataActor(String str, Parcel parcel) {
        super(str, parcel);
        this.mEvent = "";
        this.mIsSupport = true;
        this.mEvent = parcel.readString();
        this.mResultData = parcel.readString();
        this.mIsSupport = parcel.readByte() != 0;
    }

    @Override // com.xiaopeng.speech.actor.Actor, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(getEvent());
        parcel.writeString(this.mResultData);
        parcel.writeByte(this.mIsSupport ? (byte) 1 : (byte) 0);
    }

    public String getEvent() {
        return this.mEvent;
    }

    public DataActor setResult(String str) {
        this.mResultData = str;
        return this;
    }

    public String getResultData() {
        return this.mResultData;
    }

    public boolean isSupport() {
        return this.mIsSupport;
    }

    public DataActor setSupport(boolean z) {
        this.mIsSupport = z;
        return this;
    }

    public String toString() {
        return "DataActor{mEvent='" + this.mEvent + "', mResultData='" + this.mResultData + "', mIsSupport=" + this.mIsSupport + '}';
    }
}
