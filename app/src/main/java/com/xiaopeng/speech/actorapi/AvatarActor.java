package com.xiaopeng.speech.actorapi;

import android.os.Parcel;
import com.xiaopeng.speech.actor.Actor;

/* loaded from: classes2.dex */
public class AvatarActor extends Actor {
    public static final String KEY_STATE = "state";
    public static final String NAME = "AvatarActor";
    public static final int STATE_LISTENING = 2;
    public static final int STATE_SILENCE = 1;
    public static final int STATE_SPEAKING = 4;
    public static final int STATE_STANDBY = 5;
    public static final int STATE_UNDERSTANDING = 3;
    private int mState;

    public AvatarActor() {
        super(NAME);
    }

    public AvatarActor(String str, Parcel parcel) {
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

    public AvatarActor setState(int i) {
        this.mState = i;
        return this;
    }

    public String toString() {
        return "AvatarActor{state='" + this.mState + "'}";
    }
}
