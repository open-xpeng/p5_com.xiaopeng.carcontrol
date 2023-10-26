package com.xiaopeng.speech.protocol.node.music.bean;

import android.os.Parcel;
import com.xiaopeng.speech.common.bean.BaseBean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SearchMusicBean extends BaseBean {
    private boolean canDownload;
    private boolean canPlay;
    private String singers;
    private String songName;
    private boolean vip;

    public static SearchMusicBean fromJson(String str) {
        try {
            return fromJson(new JSONObject(str));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchMusicBean fromJson(JSONObject jSONObject) {
        SearchMusicBean searchMusicBean = new SearchMusicBean();
        if (jSONObject != null) {
            searchMusicBean.setSingers(jSONObject.optString("singers"));
            searchMusicBean.setSongName(jSONObject.optString("songName"));
            searchMusicBean.setVip(jSONObject.optBoolean("vip"));
            searchMusicBean.setCanDownload(jSONObject.optBoolean("canDownload"));
            searchMusicBean.setCanPlay(jSONObject.optBoolean("canPlay"));
        }
        return searchMusicBean;
    }

    @Override // com.xiaopeng.speech.common.bean.BaseBean, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.singers);
        parcel.writeString(this.songName);
        parcel.writeByte(this.vip ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.canDownload ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.canPlay ? (byte) 1 : (byte) 0);
    }

    public String getSingers() {
        return this.singers;
    }

    public void setSingers(String str) {
        this.singers = str;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String str) {
        this.songName = str;
    }

    public boolean isVip() {
        return this.vip;
    }

    public void setVip(boolean z) {
        this.vip = z;
    }

    public boolean isCanDownload() {
        return this.canDownload;
    }

    public void setCanDownload(boolean z) {
        this.canDownload = z;
    }

    public boolean isCanPlay() {
        return this.canPlay;
    }

    public void setCanPlay(boolean z) {
        this.canPlay = z;
    }

    public String toString() {
        return "MusicBean{singers='" + this.singers + "', songName='" + this.songName + "', vip='" + this.vip + "', canDownload='" + this.canDownload + "', canPlay='" + this.canPlay + "'}";
    }
}
