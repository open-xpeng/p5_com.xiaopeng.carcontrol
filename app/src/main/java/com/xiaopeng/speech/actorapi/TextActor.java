package com.xiaopeng.speech.actorapi;

import android.os.Parcel;
import com.xiaopeng.speech.actor.Actor;

/* loaded from: classes2.dex */
public class TextActor extends Actor {
    public static final String NAME = "TextActor";
    private boolean mEof;
    private boolean mIsOutput;
    private String mText;

    public TextActor() {
        super(NAME);
        this.mIsOutput = false;
        this.mEof = false;
    }

    public TextActor(String str, Parcel parcel) {
        super(str, parcel);
        this.mIsOutput = false;
        this.mEof = false;
        this.mText = parcel.readString();
        this.mIsOutput = parcel.readByte() != 0;
        this.mEof = parcel.readByte() != 0;
    }

    public String getText() {
        return this.mText;
    }

    public void setText(String str) {
        this.mText = str;
    }

    public boolean isOutput() {
        return this.mIsOutput;
    }

    public void setIsOutput(boolean z) {
        this.mIsOutput = z;
    }

    public boolean isEof() {
        return this.mEof;
    }

    public void setEof(boolean z) {
        this.mEof = z;
    }

    @Override // com.xiaopeng.speech.actor.Actor, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mText);
        parcel.writeByte(this.mIsOutput ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mEof ? (byte) 1 : (byte) 0);
    }
}
