package com.xiaopeng.appstore.storeprovider;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ResourceRequest implements Parcelable {
    public static final int ACTION_QUERY_ALL = 100;
    public static final int ACTION_QUERY_LOCAL = 300;
    public static final int ACTION_QUERY_REMOTE = 200;
    public static final Parcelable.Creator<ResourceRequest> CREATOR = new Parcelable.Creator<ResourceRequest>() { // from class: com.xiaopeng.appstore.storeprovider.ResourceRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceRequest createFromParcel(Parcel parcel) {
            return new ResourceRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceRequest[] newArray(int i) {
            return new ResourceRequest[i];
        }
    };
    private final int mAction;
    private final Bundle mExtra;
    @Deprecated
    private final long mResId;
    private final int mResourceType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ResourceRequest(int i, int i2) {
        this(i, i2, -1L);
    }

    public ResourceRequest(int i, int i2, long j) {
        this(i, i2, j, new Bundle());
    }

    private ResourceRequest(int i, int i2, long j, Bundle bundle) {
        this.mResourceType = i;
        this.mAction = i2;
        this.mResId = j;
        this.mExtra = bundle;
    }

    public int getResourceType() {
        return this.mResourceType;
    }

    public int getAction() {
        return this.mAction;
    }

    @Deprecated
    public long getResId() {
        return this.mResId;
    }

    public Bundle getExtra() {
        return this.mExtra;
    }

    public String toString() {
        return "ResourceRequest{resType=" + this.mResourceType + ", action=" + this.mAction + ", resId=" + this.mResId + '}';
    }

    protected ResourceRequest(Parcel parcel) {
        this.mResourceType = parcel.readInt();
        this.mAction = parcel.readInt();
        this.mResId = parcel.readLong();
        this.mExtra = parcel.readBundle(getClass().getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResourceType);
        parcel.writeInt(this.mAction);
        parcel.writeLong(this.mResId);
        parcel.writeBundle(this.mExtra);
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private final int mAction;
        private final Bundle mExtra = new Bundle();
        private final int mResourceType;

        public Builder(int i, int i2) {
            this.mResourceType = i;
            this.mAction = i2;
        }

        public Builder putExtra(String str, int i) {
            this.mExtra.putInt(str, i);
            return this;
        }

        public Builder putExtra(String str, long j) {
            this.mExtra.putLong(str, j);
            return this;
        }

        public ResourceRequest build() {
            return new ResourceRequest(this.mResourceType, this.mAction, -1L, this.mExtra);
        }
    }
}
