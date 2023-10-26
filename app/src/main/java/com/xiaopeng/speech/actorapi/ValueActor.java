package com.xiaopeng.speech.actorapi;

import android.os.Parcel;
import com.xiaopeng.speech.actor.Actor;
import com.xiaopeng.speech.common.bean.Value;

/* loaded from: classes2.dex */
public class ValueActor extends Actor {
    public static final String NAME = "ValueActor";
    protected String mEvent;
    protected Value mValue;

    public ValueActor(String str) {
        super(NAME);
        this.mEvent = "";
        this.mEvent = str;
    }

    public ValueActor(String str, Parcel parcel) {
        super(str, parcel);
        this.mEvent = "";
        this.mEvent = parcel.readString();
        this.mValue = (Value) parcel.readParcelable(Value.class.getClassLoader());
    }

    @Override // com.xiaopeng.speech.actor.Actor, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mEvent);
        parcel.writeParcelable(this.mValue, 0);
    }

    public Value getValue() {
        return this.mValue;
    }

    public ValueActor setValue(Object obj) {
        this.mValue = new Value(obj);
        return this;
    }

    public String getEvent() {
        return this.mEvent;
    }

    public ValueActor setEvent(String str) {
        this.mEvent = str;
        return this;
    }
}
