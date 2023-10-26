package com.xiaopeng.speech.protocol.query.music;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryMusicEvent;

/* loaded from: classes2.dex */
public class MusicQuery_Processor implements IQueryProcessor {
    private MusicQuery mTarget;

    public MusicQuery_Processor(MusicQuery musicQuery) {
        this.mTarget = musicQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1560481235:
                if (str.equals("music.is.kugou.authed")) {
                    c = 0;
                    break;
                }
                break;
            case -1316092175:
                if (str.equals("music.info.query")) {
                    c = 1;
                    break;
                }
                break;
            case -697358738:
                if (str.equals("music.is.collect.empty")) {
                    c = 2;
                    break;
                }
                break;
            case -565545210:
                if (str.equals("music.get.usb.state")) {
                    c = 3;
                    break;
                }
                break;
            case -481069680:
                if (str.equals("music.is.can.collected")) {
                    c = 4;
                    break;
                }
                break;
            case -289541544:
                if (str.equals("music.is.history.empty")) {
                    c = 5;
                    break;
                }
                break;
            case 196105311:
                if (str.equals(QueryMusicEvent.MUSIC_ACCOUNT_LOGIN)) {
                    c = 6;
                    break;
                }
                break;
            case 291286140:
                if (str.equals(QueryMusicEvent.IS_XIMALAYA_ACCOUNT_LOGIN)) {
                    c = 7;
                    break;
                }
                break;
            case 506081708:
                if (str.equals("music.is.play.similar")) {
                    c = '\b';
                    break;
                }
                break;
            case 1084784151:
                if (str.equals("music.playtype")) {
                    c = '\t';
                    break;
                }
                break;
            case 1356318952:
                if (str.equals("music.is.bt.connected")) {
                    c = '\n';
                    break;
                }
                break;
            case 1377565924:
                if (str.equals("music.info.query.artist")) {
                    c = 11;
                    break;
                }
                break;
            case 1429715250:
                if (str.equals("music.info.query.album")) {
                    c = '\f';
                    break;
                }
                break;
            case 1447189787:
                if (str.equals("music.info.query.title")) {
                    c = '\r';
                    break;
                }
                break;
            case 1732874385:
                if (str.equals(QueryMusicEvent.IS_CAN_OPEN_QUALITY_PAGE)) {
                    c = 14;
                    break;
                }
                break;
            case 2071296723:
                if (str.equals("music.is.playing")) {
                    c = 15;
                    break;
                }
                break;
            case 2146212582:
                if (str.equals("music.has.bluetooth.musiclist")) {
                    c = 16;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Boolean.valueOf(this.mTarget.isKuGouAuthed(str, str2));
            case 1:
                return this.mTarget.getPlayInfo(str, str2);
            case 2:
                return Boolean.valueOf(this.mTarget.isCollectListEmpty(str, str2));
            case 3:
                return Integer.valueOf(this.mTarget.getUsbState(str, str2));
            case 4:
                return Boolean.valueOf(this.mTarget.isCanCollected(str, str2));
            case 5:
                return Boolean.valueOf(this.mTarget.isHistoryEmpty(str, str2));
            case 6:
                return Boolean.valueOf(this.mTarget.isMusicAccountLogin(str, str2));
            case 7:
                return Boolean.valueOf(this.mTarget.isXimalayaAccountLogin(str, str2));
            case '\b':
                return Boolean.valueOf(this.mTarget.isPlaySimilar(str, str2));
            case '\t':
                return Integer.valueOf(this.mTarget.getPlayType(str, str2));
            case '\n':
                return Boolean.valueOf(this.mTarget.isBtConnected(str, str2));
            case 11:
                return this.mTarget.getInfoArtist(str, str2);
            case '\f':
                return this.mTarget.getInfoAlbum(str, str2);
            case '\r':
                return this.mTarget.getInfoTite(str, str2);
            case 14:
                return Boolean.valueOf(this.mTarget.isQualityPageOpend(str, str2));
            case 15:
                return Boolean.valueOf(this.mTarget.isPlaying(str, str2));
            case 16:
                return Boolean.valueOf(this.mTarget.hasBluetoothMusicList(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{"music.info.query", "music.info.query.title", "music.info.query.artist", "music.info.query.album", "music.playtype", "music.is.playing", "music.has.bluetooth.musiclist", "music.is.history.empty", "music.is.play.similar", "music.is.collect.empty", "music.is.can.collected", "music.is.bt.connected", "music.is.kugou.authed", "music.get.usb.state", QueryMusicEvent.MUSIC_ACCOUNT_LOGIN, QueryMusicEvent.IS_CAN_OPEN_QUALITY_PAGE, QueryMusicEvent.IS_XIMALAYA_ACCOUNT_LOGIN};
    }
}
