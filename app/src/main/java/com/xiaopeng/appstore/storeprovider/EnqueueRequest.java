package com.xiaopeng.appstore.storeprovider;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class EnqueueRequest extends AssembleRequest {
    public static final Parcelable.Creator<EnqueueRequest> CREATOR = new Parcelable.Creator<EnqueueRequest>() { // from class: com.xiaopeng.appstore.storeprovider.EnqueueRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnqueueRequest createFromParcel(Parcel parcel) {
            return new EnqueueRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnqueueRequest[] newArray(int i) {
            return new EnqueueRequest[i];
        }
    };
    private final RequestContinuation mContinuation;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RequestContinuation getContinuation() {
        return this.mContinuation;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EnqueueRequest(RequestContinuation requestContinuation) {
        super(100);
        this.mContinuation = requestContinuation;
    }

    @Override // com.xiaopeng.appstore.storeprovider.AssembleRequest
    public String toString() {
        return "EnqueueRequest{contin='" + this.mContinuation + '}';
    }

    protected EnqueueRequest(Parcel parcel) {
        super(parcel);
        this.mContinuation = (RequestContinuation) parcel.readParcelable(RequestContinuation.class.getClassLoader());
    }

    @Override // com.xiaopeng.appstore.storeprovider.AssembleRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.mContinuation, i);
    }
}
