package com.xiaopeng.speech.actorapi;

import android.os.Parcel;
import com.xiaopeng.speech.actor.Actor;
import com.xiaopeng.speech.speechwidget.SpeechWidget;

/* loaded from: classes2.dex */
public class ResultActor extends Actor {
    public static final String NAME = "ResultActor";
    protected String mEvent;
    protected String mResultData;

    public ResultActor(String str) {
        super(NAME);
        this.mEvent = "";
        this.mEvent = str;
    }

    public ResultActor(String str, Parcel parcel) {
        super(str, parcel);
        this.mEvent = "";
        this.mEvent = parcel.readString();
        this.mResultData = parcel.readString();
    }

    @Override // com.xiaopeng.speech.actor.Actor, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(getEvent());
        parcel.writeString(this.mResultData);
    }

    public String getEvent() {
        return this.mEvent;
    }

    public ResultActor setResult(SpeechWidget speechWidget) {
        return setResult(speechWidget.toString());
    }

    public ResultActor setResult(String str) {
        this.mResultData = str;
        return this;
    }

    public String getResultData() {
        return this.mResultData;
    }
}
