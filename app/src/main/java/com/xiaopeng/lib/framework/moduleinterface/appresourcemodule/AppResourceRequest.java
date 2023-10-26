package com.xiaopeng.lib.framework.moduleinterface.appresourcemodule;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AppResourceRequest {
    private static long longInc = -1;
    private boolean mCheckUpdate;
    private long mEffectiveDate;
    private long mId;
    private String mLocalPath;
    private String mOperate;
    private String mRemoteUri;
    private boolean mUnzip;
    private int mUpdatePolicy;
    private String mUriPath;
    private String mShared = "SHARED";
    private boolean mCoverRecord = true;

    public AppResourceRequest() {
        long j = longInc + 1;
        longInc = j;
        this.mId = j;
    }

    public AppResourceRequest uriPath(String str) {
        this.mUriPath = str;
        return this;
    }

    public AppResourceRequest updatePolicy(int i) {
        this.mUpdatePolicy = i;
        return this;
    }

    public AppResourceRequest localPath(String str) {
        this.mLocalPath = str;
        return this;
    }

    public AppResourceRequest shared(String str) {
        this.mShared = str;
        return this;
    }

    public AppResourceRequest remoteUri(String str) {
        this.mRemoteUri = str;
        return this;
    }

    public AppResourceRequest effectiveDate(long j) {
        this.mEffectiveDate = j;
        return this;
    }

    public AppResourceRequest unzip(boolean z) {
        this.mUnzip = z;
        return this;
    }

    public AppResourceRequest checkUpdate(boolean z) {
        this.mCheckUpdate = z;
        return this;
    }

    public AppResourceRequest coverRecord(boolean z) {
        this.mCoverRecord = z;
        return this;
    }

    public int updatePolicy() {
        return this.mUpdatePolicy;
    }

    public String localPath() {
        return this.mLocalPath;
    }

    public String uriPath() {
        return this.mUriPath;
    }

    public String remoteUri() {
        return this.mRemoteUri;
    }

    public String shared() {
        return this.mShared;
    }

    public String operate() {
        return this.mOperate;
    }

    public boolean isUnzip() {
        return this.mUnzip;
    }

    public boolean checkUpdate() {
        return this.mCheckUpdate;
    }

    public boolean isCoverRecord() {
        return this.mCoverRecord;
    }

    public AppResourceRequest operate(String str) {
        this.mOperate = str;
        return this;
    }

    public long effectiveDate() {
        return this.mEffectiveDate;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("update_policy", this.mUpdatePolicy);
            jSONObject.put("local_path", this.mLocalPath);
            jSONObject.put("uri_path", this.mUriPath);
            jSONObject.put("remote_uri", this.mRemoteUri);
            jSONObject.put("effective", this.mEffectiveDate);
            jSONObject.put("unzip", this.mUnzip);
            jSONObject.put("shared", this.mShared);
            jSONObject.put("operate", this.mOperate);
            jSONObject.put("id", this.mId);
            jSONObject.put("check_update", this.mCheckUpdate);
            jSONObject.put("cover_record", this.mCoverRecord);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof AppResourceRequest) {
            AppResourceRequest appResourceRequest = (AppResourceRequest) obj;
            return this.mUpdatePolicy == appResourceRequest.updatePolicy() && this.mEffectiveDate == appResourceRequest.mEffectiveDate && stringEqual(this.mLocalPath, appResourceRequest.mLocalPath) && stringEqual(this.mUriPath, appResourceRequest.mUriPath) && stringEqual(this.mRemoteUri, appResourceRequest.mRemoteUri) && stringEqual(this.mOperate, appResourceRequest.mOperate) && stringEqual(this.mShared, appResourceRequest.mShared) && this.mCheckUpdate == appResourceRequest.mCheckUpdate && this.mCoverRecord == appResourceRequest.mCoverRecord && this.mUnzip == appResourceRequest.mUnzip && this.mId == appResourceRequest.mId;
        }
        return false;
    }

    private boolean stringEqual(String str, String str2) {
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            return true;
        }
        return str != null && str.equals(str2);
    }

    public static AppResourceRequest from(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            AppResourceRequest appResourceRequest = new AppResourceRequest();
            appResourceRequest.mId = jSONObject.optLong("id");
            appResourceRequest.mUpdatePolicy = jSONObject.optInt("update_policy");
            appResourceRequest.mLocalPath = jSONObject.optString("local_path");
            appResourceRequest.mUriPath = jSONObject.optString("uri_path");
            appResourceRequest.mRemoteUri = jSONObject.optString("remote_uri");
            appResourceRequest.mShared = jSONObject.optString("shared", "SHARED");
            appResourceRequest.mEffectiveDate = jSONObject.optLong("effective");
            appResourceRequest.mUnzip = jSONObject.optBoolean("unzip");
            appResourceRequest.mOperate = jSONObject.optString("operate");
            appResourceRequest.mCoverRecord = jSONObject.optBoolean("cover_record", true);
            appResourceRequest.mCheckUpdate = jSONObject.optBoolean("check_update");
            return appResourceRequest;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
