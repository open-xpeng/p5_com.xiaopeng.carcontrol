package com.xiaopeng.lib.framework.moduleinterface.appresourcemodule;

import android.content.res.AssetFileDescriptor;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.lzy.okgo.model.Progress;
import com.xiaopeng.speech.protocol.bean.recommend.RecommendBean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AppResourceResponse {
    private long mCode;
    private String mData;
    private Object mExtra;
    private String mMsg;
    private AppResourceRequest mRequest;
    private String mResult;

    public AppResourceResponse request(AppResourceRequest appResourceRequest) {
        this.mRequest = appResourceRequest;
        return this;
    }

    public AppResourceResponse result(String str) {
        this.mResult = str;
        return this;
    }

    public AppResourceResponse data(String str) {
        this.mData = str;
        return this;
    }

    public AppResourceResponse code(long j) {
        this.mCode = j;
        return this;
    }

    public AppResourceResponse msg(String str) {
        this.mMsg = str;
        return this;
    }

    public String msg() {
        return this.mMsg;
    }

    public Object extra() {
        return this.mExtra;
    }

    public AppResourceResponse extra(Object obj) {
        this.mExtra = obj;
        return this;
    }

    public long code() {
        return this.mCode;
    }

    public AppResourceRequest request() {
        return this.mRequest;
    }

    public String result() {
        return this.mResult;
    }

    public String data() {
        return this.mData;
    }

    public AssetFileDescriptor getFileDescriptor() {
        Object obj = this.mExtra;
        if (obj instanceof AssetFileDescriptor) {
            return (AssetFileDescriptor) obj;
        }
        return null;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Progress.REQUEST, this.mRequest);
            jSONObject.put(RecommendBean.SHOW_TIME_RESULT, this.mResult);
            jSONObject.put("data", this.mData);
            jSONObject.put("code", this.mCode);
            jSONObject.put(NotificationCompat.CATEGORY_MESSAGE, this.mMsg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static AppResourceResponse from(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            AppResourceResponse appResourceResponse = new AppResourceResponse();
            appResourceResponse.mRequest = AppResourceRequest.from(jSONObject.optString(Progress.REQUEST));
            appResourceResponse.mResult = jSONObject.optString(RecommendBean.SHOW_TIME_RESULT);
            appResourceResponse.mData = jSONObject.optString("data");
            appResourceResponse.mCode = jSONObject.optLong("code");
            appResourceResponse.mMsg = jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE);
            return appResourceResponse;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
