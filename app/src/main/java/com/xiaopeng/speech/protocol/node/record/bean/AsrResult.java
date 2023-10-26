package com.xiaopeng.speech.protocol.node.record.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.lzy.okgo.model.Progress;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AsrResult implements Parcelable {
    public static final Parcelable.Creator<AsrResult> CREATOR = new Parcelable.Creator<AsrResult>() { // from class: com.xiaopeng.speech.protocol.node.record.bean.AsrResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AsrResult createFromParcel(Parcel parcel) {
            return new AsrResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AsrResult[] newArray(int i) {
            return new AsrResult[i];
        }
    };
    public String filePath;
    public String text;
    public String token;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static AsrResult fromJson(String str) {
        AsrResult asrResult = new AsrResult();
        try {
            JSONObject jSONObject = new JSONObject(str);
            asrResult.token = jSONObject.optString("token");
            asrResult.text = jSONObject.optString("text");
            asrResult.filePath = jSONObject.optString(Progress.FILE_PATH);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return asrResult;
    }

    public AsrResult() {
    }

    protected AsrResult(Parcel parcel) {
        this.text = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.text);
    }
}
