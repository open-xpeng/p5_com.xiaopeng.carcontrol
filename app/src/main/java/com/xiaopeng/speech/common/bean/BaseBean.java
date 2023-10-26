package com.xiaopeng.speech.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class BaseBean implements Parcelable {
    public static final Parcelable.Creator<BaseBean> CREATOR = new Parcelable.Creator<BaseBean>() { // from class: com.xiaopeng.speech.common.bean.BaseBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseBean createFromParcel(Parcel parcel) {
            return new BaseBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseBean[] newArray(int i) {
            return new BaseBean[i];
        }
    };
    private String outPut;
    private String title;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getOutPut() {
        return this.outPut;
    }

    public void setOutPut(String str) {
        this.outPut = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.outPut);
    }

    public BaseBean() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseBean(Parcel parcel) {
        this.title = parcel.readString();
        this.outPut = parcel.readString();
    }
}
