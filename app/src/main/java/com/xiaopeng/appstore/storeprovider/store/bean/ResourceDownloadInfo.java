package com.xiaopeng.appstore.storeprovider.store.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ResourceDownloadInfo implements Parcelable {
    public static final Parcelable.Creator<ResourceDownloadInfo> CREATOR = new Parcelable.Creator<ResourceDownloadInfo>() { // from class: com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceDownloadInfo createFromParcel(Parcel parcel) {
            return new ResourceDownloadInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceDownloadInfo[] newArray(int i) {
            return new ResourceDownloadInfo[i];
        }
    };
    private long mDownloadId;
    private long mDownloadedBytes;
    private String mExpandInstalledContent;
    private String mFileUri;
    private String mRscId;
    private long mSpeed;
    private int mStatus;
    private String mTitle;
    private long mTotalBytes;
    private String mUrl;
    private int mVisibility;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ResourceDownloadInfo() {
        this.mStatus = 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ResInfo{");
        sb.append("download=").append(this.mDownloadId);
        sb.append(", resId='").append(this.mRscId).append('\'');
        sb.append(", title='").append(this.mTitle).append('\'');
        sb.append(", status=").append(this.mStatus);
        sb.append(", total=").append(this.mTotalBytes);
        sb.append(", downloaded=").append(this.mDownloadedBytes);
        sb.append(", speed=").append(this.mSpeed);
        sb.append('}');
        return sb.toString();
    }

    public long getDownloadId() {
        return this.mDownloadId;
    }

    public void setDownloadId(long j) {
        this.mDownloadId = j;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public long getSpeed() {
        return this.mSpeed;
    }

    public void setSpeed(long j) {
        this.mSpeed = j;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public void setVisibility(int i) {
        this.mVisibility = i;
    }

    public static Parcelable.Creator<ResourceDownloadInfo> getCREATOR() {
        return CREATOR;
    }

    public String getExpandInstalledContent() {
        return this.mExpandInstalledContent;
    }

    public void setExpandInstalledContent(String str) {
        this.mExpandInstalledContent = str;
    }

    public String getRscId() {
        return this.mRscId;
    }

    public void setRscId(String str) {
        this.mRscId = str;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int i) {
        this.mStatus = i;
    }

    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    public void setTotalBytes(long j) {
        this.mTotalBytes = j;
    }

    public long getDownloadedBytes() {
        return this.mDownloadedBytes;
    }

    public void setDownloadedBytes(long j) {
        this.mDownloadedBytes = j;
    }

    public String getFileUri() {
        return this.mFileUri;
    }

    public void setFileUri(String str) {
        this.mFileUri = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mDownloadId);
        parcel.writeString(this.mRscId);
        parcel.writeString(this.mUrl);
        parcel.writeString(this.mTitle);
        parcel.writeInt(this.mStatus);
        parcel.writeLong(this.mTotalBytes);
        parcel.writeLong(this.mDownloadedBytes);
        parcel.writeLong(this.mSpeed);
        parcel.writeInt(this.mVisibility);
        parcel.writeString(this.mFileUri);
        parcel.writeString(this.mExpandInstalledContent);
    }

    protected ResourceDownloadInfo(Parcel parcel) {
        this.mStatus = 1;
        this.mDownloadId = parcel.readLong();
        this.mRscId = parcel.readString();
        this.mUrl = parcel.readString();
        this.mTitle = parcel.readString();
        this.mStatus = parcel.readInt();
        this.mTotalBytes = parcel.readLong();
        this.mDownloadedBytes = parcel.readLong();
        this.mSpeed = parcel.readLong();
        this.mVisibility = parcel.readInt();
        this.mFileUri = parcel.readString();
        this.mExpandInstalledContent = parcel.readString();
    }
}
