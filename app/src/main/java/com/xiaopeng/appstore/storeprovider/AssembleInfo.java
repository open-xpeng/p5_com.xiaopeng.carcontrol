package com.xiaopeng.appstore.storeprovider;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class AssembleInfo implements Parcelable {
    public static final Parcelable.Creator<AssembleInfo> CREATOR = new Parcelable.Creator<AssembleInfo>() { // from class: com.xiaopeng.appstore.storeprovider.AssembleInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssembleInfo createFromParcel(Parcel parcel) {
            return new AssembleInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssembleInfo[] newArray(int i) {
            return new AssembleInfo[i];
        }
    };
    public static final int STATE_CANCELLED = 1150;
    public static final int STATE_COMPLETE = 1100;
    public static final int STATE_ERROR = 1000;
    public static final int STATE_LOADING = 1;
    public static final int STATE_PAUSED = 200;
    public static final int STATE_PENDING = 2;
    public static final int STATE_RUNNING = 100;
    public static final int STATE_RUNNING_DOWNLOAD = 101;
    public static final int STATE_RUNNING_INSTALLING = 102;
    public static final int STATE_UNKNOWN = 0;
    private int mCanPause;
    private int mCanStop;
    private final Bundle mExtra;
    private final String mKey;
    private String mName;
    private int mNotificationVisibility;
    private float mProgress;
    private final int mResourceType;
    private int mState;

    public static boolean isCancelled(int i) {
        return i >= 1150 && i < 1200;
    }

    public static boolean isCompleted(int i) {
        return i >= 1100 && i < 1150;
    }

    public static boolean isError(int i) {
        return i >= 1000 && i < 1100;
    }

    public static boolean isFinished(int i) {
        return i >= 1000 && i <= 1200;
    }

    public static boolean isPaused(int i) {
        return i >= 200 && i < 300;
    }

    public static boolean isPreparing(int i) {
        return i >= 1 && i < 100;
    }

    public static boolean isRunning(int i) {
        return i >= 100 && i < 200;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String stateToStr(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 200) {
                        if (i != 1000) {
                            if (i != 1100) {
                                if (i != 1150) {
                                    switch (i) {
                                        case 100:
                                            return "STATE_RUNNING";
                                        case 101:
                                            return "STATE_RUNNING_DOWNLOAD";
                                        case 102:
                                            return "STATE_RUNNING_INSTALLING";
                                        default:
                                            return i + "";
                                    }
                                }
                                return "STATE_CANCELLED";
                            }
                            return "STATE_COMPLETE";
                        }
                        return "STATE_ERROR";
                    }
                    return "STATE_PAUSED";
                }
                return "STATE_PENDING";
            }
            return "STATE_LOADING";
        }
        return "STATE_UNKNOWN";
    }

    public AssembleInfo(int i, String str) {
        this.mState = 0;
        this.mNotificationVisibility = 100;
        this.mResourceType = i;
        this.mKey = str;
        this.mExtra = new Bundle();
    }

    public AssembleInfo copy(String str) {
        AssembleInfo assembleInfo = new AssembleInfo(getResourceType(), str);
        assembleInfo.setNotificationVisibility(getNotificationVisibility());
        assembleInfo.setProgress(getProgress());
        assembleInfo.setState(getState());
        assembleInfo.setCanStop(canStop());
        assembleInfo.setCanPause(canPause());
        assembleInfo.mExtra.putAll(this.mExtra);
        return assembleInfo;
    }

    public long getExtraLong(String str) {
        return this.mExtra.getLong(str);
    }

    public String getExtraString(String str) {
        return this.mExtra.getString(str);
    }

    public void putExtra(String str, long j) {
        this.mExtra.putLong(str, j);
    }

    public void putExtra(String str, String str2) {
        this.mExtra.putString(str, str2);
    }

    public void putExtraAll(Bundle bundle) {
        this.mExtra.putAll(bundle);
    }

    public void setState(int i) {
        this.mState = i;
    }

    public void setProgress(float f) {
        this.mProgress = f;
    }

    public int getResourceType() {
        return this.mResourceType;
    }

    public String getKey() {
        return this.mKey;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public int getState() {
        return this.mState;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public boolean canPause() {
        return this.mCanPause == 1;
    }

    public void setCanPause(boolean z) {
        this.mCanPause = z ? 1 : 0;
    }

    public boolean canStop() {
        return this.mCanStop == 1;
    }

    public void setCanStop(boolean z) {
        this.mCanStop = z ? 1 : 0;
    }

    public int getNotificationVisibility() {
        return this.mNotificationVisibility;
    }

    public void setNotificationVisibility(int i) {
        this.mNotificationVisibility = i;
    }

    public String toString() {
        return "AssembleInfo{key='" + this.mKey + "', resType=" + this.mResourceType + ", name=" + this.mName + ", state=" + stateToStr(this.mState) + ", prog=" + this.mProgress + ", visible=" + this.mNotificationVisibility + ", canPause=" + this.mCanPause + ", canStop=" + this.mCanStop + ", extra=" + this.mExtra + '}';
    }

    protected AssembleInfo(Parcel parcel) {
        this.mState = 0;
        this.mNotificationVisibility = 100;
        this.mKey = parcel.readString();
        this.mResourceType = parcel.readInt();
        this.mName = parcel.readString();
        this.mState = parcel.readInt();
        this.mProgress = parcel.readFloat();
        this.mNotificationVisibility = parcel.readInt();
        this.mCanPause = parcel.readInt();
        this.mCanStop = parcel.readInt();
        this.mExtra = parcel.readBundle(getClass().getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mKey);
        parcel.writeInt(this.mResourceType);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mState);
        parcel.writeFloat(this.mProgress);
        parcel.writeInt(this.mNotificationVisibility);
        parcel.writeInt(this.mCanPause);
        parcel.writeInt(this.mCanStop);
        parcel.writeBundle(this.mExtra);
    }
}
