package com.xiaopeng.appstore.storeprovider;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

/* loaded from: classes.dex */
public class RequestContinuation implements Parcelable {
    public static final Parcelable.Creator<RequestContinuation> CREATOR = new Parcelable.Creator<RequestContinuation>() { // from class: com.xiaopeng.appstore.storeprovider.RequestContinuation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequestContinuation createFromParcel(Parcel parcel) {
            return new RequestContinuation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequestContinuation[] newArray(int i) {
            return new RequestContinuation[i];
        }
    };
    private String mDownloadUrl;
    private final Bundle mExtraData;
    private String mIconUrl;
    private int mNotificationVisibility;
    private final RequestContinuation mParent;
    private final int mResourceType;
    private final String mTaskKey;
    private final String mTaskName;
    private int mUseSystemUidDownload;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RequestContinuation(int i, String str) {
        this(i, str, null);
    }

    public RequestContinuation(int i, String str, String str2) {
        this(i, str, str2, null);
    }

    RequestContinuation(int i, String str, String str2, RequestContinuation requestContinuation) {
        this.mNotificationVisibility = 100;
        this.mUseSystemUidDownload = 0;
        this.mResourceType = i;
        this.mParent = requestContinuation;
        this.mTaskKey = str;
        this.mExtraData = new Bundle();
        str2 = TextUtils.isEmpty(str2) ? getNameFromUrl(this.mDownloadUrl) : str2;
        this.mTaskName = TextUtils.isEmpty(str2) ? str : str2;
    }

    public RequestContinuation getParent() {
        return this.mParent;
    }

    public String getTaskKey() {
        return this.mTaskKey;
    }

    public String getTaskName() {
        return this.mTaskName;
    }

    public int getResourceType() {
        return this.mResourceType;
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }

    public RequestContinuation setDownloadUrl(String str) {
        this.mDownloadUrl = str;
        return this;
    }

    public int getNotificationVisibility() {
        return this.mNotificationVisibility;
    }

    public RequestContinuation setNotificationVisibility(int i) {
        this.mNotificationVisibility = i;
        return this;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public RequestContinuation setIconUrl(String str) {
        this.mIconUrl = str;
        return this;
    }

    public boolean isUseSystemUidDownload() {
        return this.mUseSystemUidDownload == 1;
    }

    public RequestContinuation setUseSystemUidDownload(boolean z) {
        this.mUseSystemUidDownload = z ? 1 : 0;
        return this;
    }

    public Bundle getExtraData() {
        return this.mExtraData;
    }

    public RequestContinuation putExtra(String str, String str2) {
        this.mExtraData.putString(str, str2);
        return this;
    }

    public RequestContinuation putExtra(String str, long j) {
        this.mExtraData.putLong(str, j);
        return this;
    }

    public RequestContinuation putExtra(String str, int i) {
        this.mExtraData.putInt(str, i);
        return this;
    }

    public RequestContinuation putExtra(String str, boolean z) {
        this.mExtraData.putBoolean(str, z);
        return this;
    }

    public RequestContinuation then(int i) {
        return new RequestContinuation(i, getTaskKey(), getTaskName(), this);
    }

    public EnqueueRequest request() {
        return new EnqueueRequest(this);
    }

    public String toString() {
        return "RequestContin{type=" + this.mResourceType + ", key=" + this.mTaskKey + ", name=" + this.mTaskKey + ", url='" + this.mDownloadUrl + "', parent='" + this.mParent + "', useSystemDownload=" + this.mUseSystemUidDownload + '}';
    }

    protected RequestContinuation(Parcel parcel) {
        this.mNotificationVisibility = 100;
        this.mUseSystemUidDownload = 0;
        this.mResourceType = parcel.readInt();
        this.mTaskKey = (String) Objects.requireNonNull(parcel.readString());
        this.mTaskName = parcel.readString();
        this.mDownloadUrl = parcel.readString();
        this.mNotificationVisibility = parcel.readInt();
        this.mIconUrl = parcel.readString();
        this.mUseSystemUidDownload = parcel.readInt();
        this.mExtraData = (Bundle) Objects.requireNonNull(parcel.readBundle(getClass().getClassLoader()));
        this.mParent = (RequestContinuation) parcel.readParcelable(getClass().getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResourceType);
        parcel.writeString(this.mTaskKey);
        parcel.writeString(this.mTaskName);
        parcel.writeString(this.mDownloadUrl);
        parcel.writeInt(this.mNotificationVisibility);
        parcel.writeString(this.mIconUrl);
        parcel.writeInt(this.mUseSystemUidDownload);
        parcel.writeBundle(this.mExtraData);
        parcel.writeParcelable(this.mParent, 0);
    }

    private static String getNameFromUrl(String str) {
        return TextUtils.isEmpty(str) ? "" : Uri.parse(str).getLastPathSegment();
    }
}
