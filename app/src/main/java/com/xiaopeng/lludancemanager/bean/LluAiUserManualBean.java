package com.xiaopeng.lludancemanager.bean;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes2.dex */
public class LluAiUserManualBean {
    @SerializedName("id")
    private int mIndex;
    @SerializedName("text")
    private String mText;

    public int getIndex() {
        return this.mIndex;
    }

    public String getText() {
        return this.mText;
    }
}
