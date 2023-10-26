package com.xiaopeng.appstore.storeprovider;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class Resource implements Parcelable {
    public static final Parcelable.Creator<Resource> CREATOR = new Parcelable.Creator<Resource>() { // from class: com.xiaopeng.appstore.storeprovider.Resource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Resource createFromParcel(Parcel parcel) {
            return new Resource(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Resource[] newArray(int i) {
            return new Resource[i];
        }
    };
    private long mCreateTime;
    private String mDes;
    private String mDownloadUrl;
    private String mExpandContent;
    private String mExpandInstalledContent;
    private Bundle mLocalData;
    private String mPrice;
    private int mResType;
    private String mRscIcon;
    private String mRscId;
    private String mRscName;
    private long mUpdateTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Resource() {
    }

    public String toString() {
        return "Res{url='" + this.mDownloadUrl + "', rscId='" + this.mRscId + "', rscName='" + this.mRscName + "', rscIcon='" + this.mRscIcon + "', create='" + this.mCreateTime + "', update=" + this.mUpdateTime + ", expandContent='" + this.mExpandContent + "', expandInstalled='" + this.mExpandInstalledContent + "', des='" + this.mDes + "', price='" + this.mPrice + "', type='" + this.mResType + "'}";
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }

    public void setDownloadUrl(String str) {
        this.mDownloadUrl = str;
    }

    public String getRscId() {
        return this.mRscId;
    }

    public void setRscId(String str) {
        this.mRscId = str;
    }

    public String getRscName() {
        return this.mRscName;
    }

    public void setRscName(String str) {
        this.mRscName = str;
    }

    public String getRscIcon() {
        return this.mRscIcon;
    }

    public void setRscIcon(String str) {
        this.mRscIcon = str;
    }

    public long getCreateTime() {
        return this.mCreateTime;
    }

    public void setCreateTime(long j) {
        this.mCreateTime = j;
    }

    public long getUpdateTime() {
        return this.mUpdateTime;
    }

    public void setUpdateTime(long j) {
        this.mUpdateTime = j;
    }

    public String getExpandContent() {
        return this.mExpandContent;
    }

    public void setExpandContent(String str) {
        this.mExpandContent = str;
    }

    public String getExpandInstalledContent() {
        return this.mExpandInstalledContent;
    }

    public void setExpandInstalledContent(String str) {
        this.mExpandInstalledContent = str;
    }

    public String getDes() {
        return this.mDes;
    }

    public void setDes(String str) {
        this.mDes = str;
    }

    public String getPrice() {
        return this.mPrice;
    }

    public void setPrice(String str) {
        this.mPrice = str;
    }

    public int getResType() {
        return this.mResType;
    }

    public void setResType(int i) {
        this.mResType = i;
    }

    public Bundle getLocalData() {
        return this.mLocalData;
    }

    public void setLocalData(Bundle bundle) {
        this.mLocalData = bundle;
    }

    protected Resource(Parcel parcel) {
        this.mDownloadUrl = parcel.readString();
        this.mRscId = parcel.readString();
        this.mRscName = parcel.readString();
        this.mRscIcon = parcel.readString();
        this.mCreateTime = parcel.readLong();
        this.mUpdateTime = parcel.readLong();
        this.mExpandContent = parcel.readString();
        this.mExpandInstalledContent = parcel.readString();
        this.mDes = parcel.readString();
        this.mPrice = parcel.readString();
        this.mResType = parcel.readInt();
        this.mLocalData = parcel.readBundle(getClass().getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mDownloadUrl);
        parcel.writeString(this.mRscId);
        parcel.writeString(this.mRscName);
        parcel.writeString(this.mRscIcon);
        parcel.writeLong(this.mCreateTime);
        parcel.writeLong(this.mUpdateTime);
        parcel.writeString(this.mExpandContent);
        parcel.writeString(this.mExpandInstalledContent);
        parcel.writeString(this.mDes);
        parcel.writeString(this.mPrice);
        parcel.writeInt(this.mResType);
        parcel.writeBundle(this.mLocalData);
    }
}
