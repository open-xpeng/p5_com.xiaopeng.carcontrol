package com.xiaopeng.appstore.storeprovider.store.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ResourceBean implements Parcelable {
    public static final Parcelable.Creator<ResourceBean> CREATOR = new Parcelable.Creator<ResourceBean>() { // from class: com.xiaopeng.appstore.storeprovider.store.bean.ResourceBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceBean createFromParcel(Parcel parcel) {
            return new ResourceBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceBean[] newArray(int i) {
            return new ResourceBean[i];
        }
    };
    private String mCreateTime;
    private String mDes;
    private String mDownloadUrl;
    private String mExpandContent;
    private String mExpandInstalledContent;
    private String mPrice;
    private String mRscIcon;
    private String mRscId;
    private String mRscName;
    private int mSource;
    private int mStatus;
    private String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ResourceBean() {
        this.mStatus = 1;
    }

    protected ResourceBean(Parcel parcel) {
        this.mStatus = 1;
        this.mDownloadUrl = parcel.readString();
        this.mRscId = parcel.readString();
        this.mRscName = parcel.readString();
        this.mRscIcon = parcel.readString();
        this.mExpandContent = parcel.readString();
        this.mDes = parcel.readString();
        this.mPrice = parcel.readString();
        this.mStatus = parcel.readInt();
        this.mCreateTime = parcel.readString();
        this.mType = parcel.readString();
        this.mExpandInstalledContent = parcel.readString();
        this.mSource = parcel.readInt();
    }

    public int getSource() {
        return this.mSource;
    }

    public void setSource(int i) {
        this.mSource = i;
    }

    public String getExpandInstalledContent() {
        return this.mExpandInstalledContent;
    }

    public void setExpandInstalledContent(String str) {
        this.mExpandInstalledContent = str;
    }

    public String getExpandContent() {
        return this.mExpandContent;
    }

    public void setExpandContent(String str) {
        this.mExpandContent = str;
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

    public String getType() {
        return this.mType;
    }

    public void setType(String str) {
        this.mType = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mDownloadUrl);
        parcel.writeString(this.mRscId);
        parcel.writeString(this.mRscName);
        parcel.writeString(this.mRscIcon);
        parcel.writeString(this.mExpandContent);
        parcel.writeString(this.mDes);
        parcel.writeString(this.mPrice);
        parcel.writeInt(this.mStatus);
        parcel.writeString(this.mCreateTime);
        parcel.writeString(this.mType);
        parcel.writeString(this.mExpandInstalledContent);
        parcel.writeInt(this.mSource);
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

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int i) {
        this.mStatus = i;
    }

    public String getCreateTime() {
        return this.mCreateTime;
    }

    public void setCreateTime(String str) {
        this.mCreateTime = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ResourceBean{");
        sb.append("mDownloadUrl='").append(this.mDownloadUrl).append('\'');
        sb.append(", mRscId='").append(this.mRscId).append('\'');
        sb.append(", mRscName='").append(this.mRscName).append('\'');
        sb.append(", mRscIcon='").append(this.mRscIcon).append('\'');
        sb.append(", mStatus=").append(this.mStatus);
        sb.append(", mExpandContent=").append(this.mExpandContent);
        sb.append(", mExpandInstalledContent=").append(this.mExpandInstalledContent);
        sb.append(", mCreateTime='").append(this.mCreateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
