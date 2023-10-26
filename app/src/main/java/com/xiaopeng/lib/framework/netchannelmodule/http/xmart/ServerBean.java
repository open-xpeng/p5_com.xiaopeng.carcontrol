package com.xiaopeng.lib.framework.netchannelmodule.http.xmart;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IServerBean;

/* loaded from: classes2.dex */
public class ServerBean implements IServerBean {
    @SerializedName("code")
    private int mCode;
    @SerializedName("data")
    private String mData;
    @SerializedName(NotificationCompat.CATEGORY_MESSAGE)
    private String mMsg;

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IServerBean
    public int code() {
        return this.mCode;
    }

    public void code(int i) {
        this.mCode = i;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IServerBean
    public String data() {
        return this.mData;
    }

    public void data(String str) {
        this.mData = str;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.xmart.IServerBean
    public String message() {
        return this.mMsg;
    }

    public void message(String str) {
        this.mMsg = str;
    }
}
