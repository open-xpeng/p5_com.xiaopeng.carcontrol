package com.xiaopeng.speech.protocol.node.music.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xiaopeng.speech.common.bean.BaseBean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MusicBean extends BaseBean {
    public static final Parcelable.Creator<MusicBean> CREATOR = new Parcelable.Creator<MusicBean>() { // from class: com.xiaopeng.speech.protocol.node.music.bean.MusicBean.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MusicBean createFromParcel(Parcel parcel) {
            return new MusicBean(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MusicBean[] newArray(int i) {
            return new MusicBean[i];
        }
    };
    private String extData;
    private String keyWord;
    private int listed;
    private String metaDataList;
    private String metadata;
    private String page;
    private String params;
    private int searchType;
    private String source;
    private String tracks;

    public MusicBean() {
    }

    protected MusicBean(Parcel parcel) {
        super(parcel);
        this.params = parcel.readString();
        this.tracks = parcel.readString();
        this.page = parcel.readString();
        this.metadata = parcel.readString();
        this.keyWord = parcel.readString();
        this.source = parcel.readString();
        this.searchType = parcel.readInt();
        this.listed = parcel.readInt();
        this.extData = parcel.readString();
        this.metaDataList = parcel.readString();
    }

    public static MusicBean fromJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("param")) {
                jSONObject = new JSONObject(jSONObject.optString("param"));
            } else if (jSONObject.has("from") && "dui_xp".equals(jSONObject.optString("from"))) {
                return null;
            }
            return fromJson(jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MusicBean fromJson(JSONObject jSONObject) {
        MusicBean musicBean = new MusicBean();
        if (jSONObject != null) {
            musicBean.setParams(jSONObject.optString(IpcConfig.DeviceCommunicationConfig.KEY_APP_MESSAGE_PARAMS));
            musicBean.setTracks(jSONObject.optString("tracks"));
            musicBean.setPage(jSONObject.optString("page"));
            musicBean.setMetadata(jSONObject.optString("metadata"));
            musicBean.setSource(jSONObject.optString("source"));
            musicBean.setKeyWord(jSONObject.optString("keyWord"));
            musicBean.setSearchType(jSONObject.optInt("searchType"));
            musicBean.setListed(jSONObject.optInt("listed"));
            musicBean.setExtData(jSONObject.optString("extData"));
            musicBean.setMetaDataList(jSONObject.optString("metaDataList"));
        }
        return musicBean;
    }

    @Override // com.xiaopeng.speech.common.bean.BaseBean, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.params);
        parcel.writeString(this.tracks);
        parcel.writeString(this.page);
        parcel.writeString(this.metadata);
        parcel.writeString(this.keyWord);
        parcel.writeString(this.source);
        parcel.writeInt(this.searchType);
        parcel.writeInt(this.listed);
        parcel.writeString(this.extData);
        parcel.writeString(this.metaDataList);
    }

    public String getParams() {
        return this.params;
    }

    public String getTracks() {
        return this.tracks;
    }

    public String getPage() {
        return this.page;
    }

    public void setParams(String str) {
        this.params = str;
    }

    public void setTracks(String str) {
        this.tracks = str;
    }

    public void setPage(String str) {
        this.page = str;
    }

    public String getMetadata() {
        return this.metadata;
    }

    public void setMetadata(String str) {
        this.metadata = str;
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public void setKeyWord(String str) {
        this.keyWord = str;
    }

    public int getSearchType() {
        return this.searchType;
    }

    public void setSearchType(int i) {
        this.searchType = i;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public int getListed() {
        return this.listed;
    }

    public void setListed(int i) {
        this.listed = i;
    }

    public String getExtData() {
        return this.extData;
    }

    public void setExtData(String str) {
        this.extData = str;
    }

    public String getMetaDataList() {
        return this.metaDataList;
    }

    public void setMetaDataList(String str) {
        this.metaDataList = str;
    }

    public String toString() {
        return "MusicBean{params='" + this.params + "', tracks='" + this.tracks + "', page='" + this.page + "', metadata='" + this.metadata + "', keyWord='" + this.keyWord + "', source='" + this.source + "', searchType=" + this.searchType + ", listed=" + this.listed + ", extData=" + this.extData + '}';
    }
}
