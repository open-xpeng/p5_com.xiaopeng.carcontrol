package com.xiaopeng.speech.actorapi;

import android.os.Parcel;
import com.xiaopeng.speech.actor.Actor;

/* loaded from: classes2.dex */
public class DialogActor extends Actor {
    public static final String KEY_STATE = "state";
    public static final String NAME = "DialogActor";
    public static final int STATE_CONTINUE = 4;
    public static final int STATE_END = 3;
    public static final int STATE_ERROR = 2;
    public static final int STATE_START = 1;
    private int mState;

    public DialogActor() {
        super(NAME);
    }

    public DialogActor(String str, Parcel parcel) {
        super(str, parcel);
        this.mState = parcel.readInt();
    }

    @Override // com.xiaopeng.speech.actor.Actor, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(getState());
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public String toString() {
        return "DialogActor{state='" + this.mState + "'}";
    }
}
