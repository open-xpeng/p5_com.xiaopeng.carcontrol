package com.xiaopeng.speech.protocol.node.music.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.speech.common.bean.BaseBean;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SearchMusic extends BaseBean {
    public static final Parcelable.Creator<SearchMusic> CREATOR = new Parcelable.Creator<SearchMusic>() { // from class: com.xiaopeng.speech.protocol.node.music.bean.SearchMusic.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchMusic createFromParcel(Parcel parcel) {
            return new SearchMusic(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchMusic[] newArray(int i) {
            return new SearchMusic[i];
        }
    };
    private String age;
    private String album;
    private String artist;
    private String genre;
    private String language;
    private String mode;
    private String module;
    private String mood;
    private String region;
    private String theme;
    private String title;
    private String type;

    @Override // com.xiaopeng.speech.common.bean.BaseBean, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SearchMusic() {
    }

    protected SearchMusic(Parcel parcel) {
        super(parcel);
        this.artist = parcel.readString();
        this.title = parcel.readString();
        this.album = parcel.readString();
        this.module = parcel.readString();
        this.genre = parcel.readString();
        this.age = parcel.readString();
        this.region = parcel.readString();
        this.mood = parcel.readString();
        this.theme = parcel.readString();
        this.language = parcel.readString();
        this.type = parcel.readString();
        this.mode = parcel.readString();
    }

    public static SearchMusic fromJson(String str) {
        try {
            return fromJson(new JSONObject(str));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchMusic fromJson(JSONObject jSONObject) {
        SearchMusic searchMusic = new SearchMusic();
        try {
            searchMusic.setAlbum(jSONObject.optString("album"));
            searchMusic.setArtist(jSONObject.optString("artist"));
            searchMusic.setTitle(jSONObject.optString(SpeechWidget.WIDGET_TITLE));
            searchMusic.setModule(jSONObject.optString("module"));
            searchMusic.setAge(jSONObject.optString("age"));
            searchMusic.setGenre(jSONObject.optString("gen"));
            searchMusic.setMood(jSONObject.optString("mood"));
            searchMusic.setRegion(jSONObject.optString("region"));
            searchMusic.setTheme(jSONObject.optString(ThemeManager.AttributeSet.THEME));
            searchMusic.setLanguage(jSONObject.optString("lan"));
            searchMusic.setType(jSONObject.optString("typ"));
            searchMusic.setMode(jSONObject.optString("mode"));
            if (TextUtils.isEmpty(searchMusic.getAlbum()) && TextUtils.isEmpty(searchMusic.getArtist()) && TextUtils.isEmpty(searchMusic.getTitle()) && TextUtils.isEmpty(searchMusic.getModule()) && TextUtils.isEmpty(searchMusic.getAge()) && TextUtils.isEmpty(searchMusic.getGenre()) && TextUtils.isEmpty(searchMusic.getMood()) && TextUtils.isEmpty(searchMusic.getRegion()) && TextUtils.isEmpty(searchMusic.getTheme()) && TextUtils.isEmpty(searchMusic.getLanguage()) && TextUtils.isEmpty(searchMusic.getType())) {
                throw new IllegalArgumentException("Album, artist, module, title and age are all empty string !");
            }
            return searchMusic;
        } catch (IllegalArgumentException e) {
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

    @Override // com.xiaopeng.speech.common.bean.BaseBean
    public String getTitle() {
        return this.title;
    }

    @Override // com.xiaopeng.speech.common.bean.BaseBean
    public void setTitle(String str) {
        this.title = str;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String str) {
        this.album = str;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String str) {
        this.module = str;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String str) {
        this.genre = str;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String str) {
        this.age = str;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String str) {
        this.region = str;
    }

    public String getMood() {
        return this.mood;
    }

    public void setMood(String str) {
        this.mood = str;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String str) {
        this.theme = str;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String str) {
        this.mode = str;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(SpeechWidget.WIDGET_TITLE, this.title);
            jSONObject.put("artist", this.artist);
            jSONObject.put("album", this.album);
            jSONObject.put("module", this.module);
            jSONObject.put("gen", this.genre);
            jSONObject.put("age", this.age);
            jSONObject.put("mood", this.mood);
            jSONObject.put("region", this.region);
            jSONObject.put(ThemeManager.AttributeSet.THEME, this.theme);
            jSONObject.put("lan", this.language);
            jSONObject.put("typ", this.type);
            jSONObject.put("mode", this.mode);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return "SearchMusic{artist='" + this.artist + "', title='" + this.title + "', album='" + this.album + "', module='" + this.module + "', genre='" + this.genre + "', language='" + this.language + "', age='" + this.age + "', region='" + this.region + "', mood='" + this.mood + "', theme='" + this.theme + "', type='" + this.type + "', mode='" + this.mode + "'}";
    }

    @Override // com.xiaopeng.speech.common.bean.BaseBean, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.artist);
        parcel.writeString(this.title);
        parcel.writeString(this.album);
        parcel.writeString(this.module);
        parcel.writeString(this.genre);
        parcel.writeString(this.language);
        parcel.writeString(this.age);
        parcel.writeString(this.region);
        parcel.writeString(this.mood);
        parcel.writeString(this.theme);
        parcel.writeString(this.type);
        parcel.writeString(this.mode);
    }
}
