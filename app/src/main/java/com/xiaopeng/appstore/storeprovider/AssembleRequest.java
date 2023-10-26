package com.xiaopeng.appstore.storeprovider;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public abstract class AssembleRequest implements Parcelable {
    public static final int ASSEMBLE_ACTION_CANCEL = 400;
    public static final int ASSEMBLE_ACTION_ENQUEUE = 100;
    public static final int ASSEMBLE_ACTION_PAUSE = 200;
    public static final int ASSEMBLE_ACTION_RESUME = 300;
    protected final int mAction;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface AssembleAction {
    }

    public static RequestContinuation enqueue(int i, String str, String str2) {
        return new RequestContinuation(i, str, str2);
    }

    public static SimpleRequest pause(int i, String str) {
        return new SimpleRequest(200, i, str);
    }

    public static SimpleRequest resume(int i, String str) {
        return new SimpleRequest(300, i, str);
    }

    public static SimpleRequest cancel(int i, String str) {
        return new SimpleRequest(ASSEMBLE_ACTION_CANCEL, i, str);
    }

    public AssembleRequest(int i) {
        this.mAction = i;
    }

    public String toString() {
        return "AssembleRequest{action=" + this.mAction + '}';
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AssembleRequest(Parcel parcel) {
        this.mAction = parcel.readInt();
    }

    public int getAction() {
        return this.mAction;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAction);
    }
}
