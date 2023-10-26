package com.xiaopeng.speech.protocol.query.music.bean;

import com.xiaopeng.speech.common.util.DontProguardClass;

@DontProguardClass
/* loaded from: classes2.dex */
public class PlayInfo {
    public static final int ERROR_CURRENT_NOT_PLAY = 20001;
    public static final int ERROR_SUPPORT_PLAY_TYPE_GET = 20002;
    public static final int TYPE_AUDIO_BOOK = 2;
    public static final int TYPE_BT_FM = 5;
    public static final int TYPE_FM = 3;
    public static final int TYPE_NETWORK_FM = 4;
    public static final int TYPE_SONG = 1;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_USB_MUSIC = 6;
    private String album;
    private String artist;
    private int error;
    private long playTime;
    private int playType;
    private long remainingTime;
    private String songId;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String str) {
        this.artist = str;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String str) {
        this.album = str;
    }

    public long getPlayTime() {
        return this.playTime;
    }

    public void setPlayTime(long j) {
        this.playTime = j;
    }

    public long getRemainingTime() {
        return this.remainingTime;
    }

    public void setRemainingTime(long j) {
        this.remainingTime = j;
    }

    public int getPlayType() {
        return this.playType;
    }

    public void setPlayType(int i) {
        this.playType = i;
    }

    public int getError() {
        return this.error;
    }

    public void setError(int i) {
        this.error = i;
    }

    public String getSongId() {
        return this.songId;
    }

    public void setSongId(String str) {
        this.songId = str;
    }

    public String toString() {
        return "PlayInfo{title='" + this.title + "', artist='" + this.artist + "', album='" + this.album + "', playTime=" + this.playTime + ", remainingTime=" + this.remainingTime + ", playType=" + this.playType + ", error=" + this.error + ", songId=" + this.songId + '}';
    }
}
