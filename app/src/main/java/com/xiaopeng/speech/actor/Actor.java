package com.xiaopeng.speech.actor;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaopeng.speech.actorapi.AvatarActor;
import com.xiaopeng.speech.actorapi.DataActor;
import com.xiaopeng.speech.actorapi.DialogActor;
import com.xiaopeng.speech.actorapi.ResultActor;
import com.xiaopeng.speech.actorapi.ShowActor;
import com.xiaopeng.speech.actorapi.SupportActor;
import com.xiaopeng.speech.actorapi.TextActor;
import com.xiaopeng.speech.actorapi.ValueActor;
import com.xiaopeng.speech.common.util.LogUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class Actor implements Parcelable {
    protected String mName;
    public static Map<String, Class<? extends Actor>> sActorMap = new HashMap<String, Class<? extends Actor>>() { // from class: com.xiaopeng.speech.actor.Actor.1
        {
            put(AvatarActor.NAME, AvatarActor.class);
            put(DialogActor.NAME, DialogActor.class);
            put(ResultActor.NAME, ResultActor.class);
            put(TextActor.NAME, TextActor.class);
            put(ShowActor.NAME, ShowActor.class);
            put(SupportActor.NAME, SupportActor.class);
            put(DataActor.NAME, DataActor.class);
            put(ValueActor.NAME, ValueActor.class);
        }
    };
    public static final Parcelable.Creator<Actor> CREATOR = new Parcelable.Creator<Actor>() { // from class: com.xiaopeng.speech.actor.Actor.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Actor createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            try {
                return Actor.sActorMap.get(readString).getConstructor(String.class, Parcel.class).newInstance(readString, parcel);
            } catch (Exception e) {
                LogUtils.e(this, "createFromParcel error:", e);
                return new Actor(readString, parcel);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Actor[] newArray(int i) {
            return new Actor[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Actor() {
    }

    public Actor(String str) {
        this.mName = str;
    }

    public String getName() {
        return this.mName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Actor(String str, Parcel parcel) {
        this.mName = str;
    }
}
