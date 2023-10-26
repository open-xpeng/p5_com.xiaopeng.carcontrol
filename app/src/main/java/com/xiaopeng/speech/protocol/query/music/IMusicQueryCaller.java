package com.xiaopeng.speech.protocol.query.music;

import com.xiaopeng.speech.IQueryCaller;

/* loaded from: classes2.dex */
public interface IMusicQueryCaller extends IQueryCaller {
    String getHistoryPlayInfo(int i);

    String getPlayAlbum();

    String getPlayArtist();

    String getPlayCategory();

    String getPlayInfo();

    String getPlayLyric();

    String getPlayTitle();

    int getPlayType();

    int getUsbState();

    boolean hasBluetoothMusicList();

    boolean isBtConnected();

    boolean isCanCollected();

    boolean isCollectListEmpty(int i);

    boolean isHistoryEmpty(int i);

    boolean isKuGouAuthed();

    default boolean isMusicAccountLogin() {
        return false;
    }

    boolean isPlaySimilar();

    boolean isPlaying();

    default boolean isQualityPageOpend() {
        return false;
    }

    default boolean isXimalayaAccountLogin() {
        return false;
    }
}
