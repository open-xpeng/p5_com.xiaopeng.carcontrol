package com.xiaopeng.speech.actorapi;

import android.os.Parcel;
import com.xiaopeng.speech.actor.Actor;
import com.xiaopeng.speech.speechwidget.SpeechWidget;

/* loaded from: classes2.dex */
public class ShowActor extends Actor {
    public static final String NAME = "ShowActor";
    private String mWidgetData;

    public ShowActor() {
        super(NAME);
    }

    public ShowActor(String str, Parcel parcel) {
        super(str, parcel);
        this.mWidgetData = parcel.readString();
    }

    @Override // com.xiaopeng.speech.actor.Actor, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mWidgetData);
    }

    public ShowActor setWidget(SpeechWidget speechWidget) {
        this.mWidgetData = speechWidget.toString();
        return this;
    }

    public ShowActor setWidgetData(String str) {
        this.mWidgetData = str;
        return this;
    }

    public SpeechWidget getWidget() {
        SpeechWidget speechWidget = new SpeechWidget();
        speechWidget.fromJson(this.mWidgetData);
        return speechWidget;
    }

    public String getWidgetData() {
        return this.mWidgetData;
    }
}
