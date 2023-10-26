package com.xiaopeng.carcontrol.bean.xpilot.map;

import java.util.Objects;

/* loaded from: classes.dex */
public abstract class CngpMapItem {
    public static final int TYPE_CITY = 2;
    public static final int TYPE_TITLE = 1;
    private boolean mActivated;
    private int mDownloadedPercentage;
    private int mId;
    private int mState;
    private String mTitle;
    private int mTitleColor;
    private int mType;

    public CngpMapItem(int id, int type, String title, int titleColor) {
        this.mActivated = true;
        this.mId = id;
        this.mType = type;
        this.mTitle = title;
        this.mTitleColor = titleColor;
    }

    public CngpMapItem(int id, int type, String title, int titleColor, int state, int progress) {
        this.mActivated = true;
        this.mId = id;
        this.mType = type;
        this.mTitle = title;
        this.mTitleColor = titleColor;
        this.mState = state;
        this.mDownloadedPercentage = progress;
    }

    public CngpMapItem(CngpMapItem item) {
        this.mActivated = true;
        this.mId = item.mId;
        this.mType = item.mType;
        this.mTitle = item.mTitle;
        this.mTitleColor = item.mTitleColor;
        this.mState = item.mState;
        this.mDownloadedPercentage = item.mDownloadedPercentage;
        this.mActivated = item.mActivated;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String mName) {
        this.mTitle = mName;
    }

    public int getTitleColor() {
        return this.mTitleColor;
    }

    public void setTitleColor(int mTitleColor) {
        this.mTitleColor = mTitleColor;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int mState) {
        this.mState = mState;
    }

    public int getDownloadedPercentage() {
        return this.mDownloadedPercentage;
    }

    public void setDownloadedPercentage(int mDownloadedPercentage) {
        this.mDownloadedPercentage = mDownloadedPercentage;
    }

    public boolean isActivated() {
        return this.mActivated;
    }

    public void setActivated(boolean mActivated) {
        this.mActivated = mActivated;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CngpMapItem cngpMapItem = (CngpMapItem) o;
        return this.mActivated == cngpMapItem.mActivated && this.mType == cngpMapItem.mType && this.mTitleColor == cngpMapItem.mTitleColor && this.mState == cngpMapItem.mState && this.mDownloadedPercentage == cngpMapItem.mDownloadedPercentage && Objects.equals(Integer.valueOf(this.mId), Integer.valueOf(cngpMapItem.mId)) && Objects.equals(this.mTitle, cngpMapItem.mTitle);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mId), Integer.valueOf(this.mType), this.mTitle, Integer.valueOf(this.mTitleColor), Integer.valueOf(this.mState), Integer.valueOf(this.mDownloadedPercentage), Boolean.valueOf(this.mActivated));
    }

    public String toString() {
        return this.mId + " " + this.mTitle + " " + this.mState + " " + this.mDownloadedPercentage;
    }
}
