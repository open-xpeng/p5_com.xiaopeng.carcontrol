package com.xiaopeng.lludancemanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class LluDanceViewData implements Parcelable {
    public static final Parcelable.Creator<LluDanceViewData> CREATOR = new Parcelable.Creator<LluDanceViewData>() { // from class: com.xiaopeng.lludancemanager.bean.LluDanceViewData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LluDanceViewData createFromParcel(Parcel in) {
            return new LluDanceViewData(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LluDanceViewData[] newArray(int size) {
            return new LluDanceViewData[size];
        }
    };
    private static final String THUMB_NAIL_POSTFIX = "?x-oss-process=image/resize,h_160";
    private String mAuthor;
    private String mDownloadUrl;
    private double mDownloadedPercentage;
    private long mDuration;
    private String mEffectName;
    private String mId;
    private String mImgUrl;
    private int mState;
    private String mTitle;
    private String mVideoPath;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LluDanceViewData(String title, String author, String imgUrl, String id, String downloadUrl, int state, long duration, double downloadedPercentage, String effectName, String videoPath) {
        this.mTitle = title;
        this.mAuthor = author;
        this.mImgUrl = imgUrl;
        this.mId = id;
        this.mDownloadUrl = downloadUrl;
        this.mState = state;
        this.mDuration = duration;
        this.mDownloadedPercentage = downloadedPercentage;
        this.mEffectName = effectName;
        this.mVideoPath = videoPath;
    }

    public String getVideoPath() {
        return this.mVideoPath;
    }

    public void setVideoPath(String videoPath) {
        this.mVideoPath = videoPath;
    }

    public String getEffectName() {
        return this.mEffectName;
    }

    public void setEffectName(String effectname) {
        this.mEffectName = effectname;
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public String getAuthor() {
        return this.mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getImgUrl() {
        return this.mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.mImgUrl = imgUrl;
    }

    public String getId() {
        return this.mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public double getDownloadedPercentage() {
        return this.mDownloadedPercentage;
    }

    public void setDownloadedPercentage(double downloadedPercentage) {
        this.mDownloadedPercentage = downloadedPercentage;
    }

    public String getImageThumbnailUrl() {
        String str = this.mImgUrl;
        if (str != null) {
            return str.concat(THUMB_NAIL_POSTFIX);
        }
        return null;
    }

    protected LluDanceViewData(Parcel in) {
        this.mTitle = in.readStringNoHelper();
        this.mAuthor = in.readStringNoHelper();
        this.mImgUrl = in.readStringNoHelper();
        this.mId = in.readStringNoHelper();
        this.mDownloadUrl = in.readStringNoHelper();
        this.mVideoPath = in.readStringNoHelper();
        this.mState = in.readInt();
        this.mDuration = in.readLong();
        this.mDownloadedPercentage = in.readDouble();
        this.mEffectName = in.readStringNoHelper();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringNoHelper(this.mTitle);
        dest.writeStringNoHelper(this.mAuthor);
        dest.writeStringNoHelper(this.mImgUrl);
        dest.writeStringNoHelper(this.mId);
        dest.writeStringNoHelper(this.mDownloadUrl);
        dest.writeStringNoHelper(this.mVideoPath);
        dest.writeInt(this.mState);
        dest.writeLong(this.mDuration);
        dest.writeDouble(this.mDownloadedPercentage);
        dest.writeStringNoHelper(this.mEffectName);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LluDanceViewData{");
        sb.append("mTitle='").append(this.mTitle).append('\'');
        sb.append(", mAuthor='").append(this.mAuthor).append('\'');
        sb.append(", mImgUrl='").append(this.mImgUrl).append('\'');
        sb.append(", mId='").append(this.mId).append('\'');
        sb.append(", mDownloadUrl='").append(this.mDownloadUrl).append('\'');
        sb.append(", mState=").append(this.mState);
        sb.append(", mDuration=").append(this.mDuration);
        sb.append(", mDownloadedPercentage=").append(this.mDownloadedPercentage);
        sb.append('}');
        return sb.toString();
    }
}
