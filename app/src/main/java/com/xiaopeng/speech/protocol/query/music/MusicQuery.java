package com.xiaopeng.speech.protocol.query.music;

import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MusicQuery extends SpeechQuery<IMusicQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public String getPlayInfo(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayInfo();
    }

    protected String getPlaylistHistory(String str, String str2) {
        int i = 1;
        try {
            i = new JSONObject(str2).optInt("count", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((IMusicQueryCaller) this.mQueryCaller).getHistoryPlayInfo(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getInfoTite(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayTitle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getInfoArtist(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayArtist();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getInfoAlbum(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayAlbum();
    }

    protected String getInfoLyric(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayLyric();
    }

    protected String getInfoCategory(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayCategory();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPlayType(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).getPlayType();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isPlaying(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).isPlaying();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasBluetoothMusicList(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).hasBluetoothMusicList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isHistoryEmpty(String str, String str2) {
        int i;
        try {
            i = new JSONObject(str2).optInt(VuiConstants.ELEMENT_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
            i = 0;
        }
        return ((IMusicQueryCaller) this.mQueryCaller).isHistoryEmpty(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isPlaySimilar(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).isPlaySimilar();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCollectListEmpty(String str, String str2) {
        int i;
        try {
            i = new JSONObject(str2).optInt(VuiConstants.ELEMENT_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
            i = 0;
        }
        return ((IMusicQueryCaller) this.mQueryCaller).isCollectListEmpty(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCanCollected(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).isCanCollected();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isBtConnected(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).isBtConnected();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isKuGouAuthed(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).isKuGouAuthed();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getUsbState(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).getUsbState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isMusicAccountLogin(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).isMusicAccountLogin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isQualityPageOpend(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).isQualityPageOpend();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isXimalayaAccountLogin(String str, String str2) {
        return ((IMusicQueryCaller) this.mQueryCaller).isXimalayaAccountLogin();
    }
}
