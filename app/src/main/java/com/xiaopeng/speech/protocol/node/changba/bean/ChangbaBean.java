package com.xiaopeng.speech.protocol.node.changba.bean;

import com.xiaopeng.speech.common.bean.BaseBean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ChangbaBean extends BaseBean {
    private String artist;
    private String song;

    public static ChangbaBean fromJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            ChangbaBean changbaBean = new ChangbaBean();
            changbaBean.setArtist(jSONObject.optString("artist"));
            changbaBean.setSong(jSONObject.optString("song"));
            return changbaBean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String str) {
        this.artist = str;
    }

    public String getSong() {
        return this.song;
    }

    public void setSong(String str) {
        this.song = str;
    }

    public String toString() {
        return "ChangbaBean{artist='" + this.artist + "', song='" + this.song + "'}";
    }
}
