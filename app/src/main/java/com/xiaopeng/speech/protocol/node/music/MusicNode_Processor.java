package com.xiaopeng.speech.protocol.node.music;

import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.speech.annotation.ICommandProcessor;

/* loaded from: classes2.dex */
public class MusicNode_Processor implements ICommandProcessor {
    private MusicNode mTarget;

    public MusicNode_Processor(MusicNode musicNode) {
        this.mTarget = musicNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2013000544:
                if (str.equals("command://music.control.loop.all")) {
                    c = 0;
                    break;
                }
                break;
            case -2002174621:
                if (str.equals("command://music.speed.set")) {
                    c = 1;
                    break;
                }
                break;
            case -1938308159:
                if (str.equals("command://music.speed.down")) {
                    c = 2;
                    break;
                }
                break;
            case -1829687228:
                if (str.equals("native://music.playmode.support")) {
                    c = 3;
                    break;
                }
                break;
            case -1728394409:
                if (str.equals("command://music.settime")) {
                    c = 4;
                    break;
                }
                break;
            case -1627989588:
                if (str.equals("command://music.list.play")) {
                    c = 5;
                    break;
                }
                break;
            case -1425904452:
                if (str.equals("command://music.control.next")) {
                    c = 6;
                    break;
                }
                break;
            case -1425838851:
                if (str.equals("command://music.control.play")) {
                    c = 7;
                    break;
                }
                break;
            case -1425832964:
                if (str.equals("command://music.control.prev")) {
                    c = '\b';
                    break;
                }
                break;
            case -1425741365:
                if (str.equals("command://music.control.stop")) {
                    c = '\t';
                    break;
                }
                break;
            case -1353272389:
                if (str.equals("command://music.backward")) {
                    c = '\n';
                    break;
                }
                break;
            case -1311512198:
                if (str.equals("command://music.speed.up")) {
                    c = 11;
                    break;
                }
                break;
            case -1292964230:
                if (str.equals("command://music.control.sim.cancel")) {
                    c = '\f';
                    break;
                }
                break;
            case -1251639987:
                if (str.equals("command://music.control.pause")) {
                    c = '\r';
                    break;
                }
                break;
            case -1236424121:
                if (str.equals("command://music.control.collect.cancel")) {
                    c = 14;
                    break;
                }
                break;
            case -782232511:
                if (str.equals("command://music.control.playlist.history.play")) {
                    c = 15;
                    break;
                }
                break;
            case -679814637:
                if (str.equals("command://music.dailyrec.play")) {
                    c = 16;
                    break;
                }
                break;
            case -496865250:
                if (str.equals("command://music.soundeffect.stereo")) {
                    c = 17;
                    break;
                }
                break;
            case -447517996:
                if (str.equals("command://music.control.bluetooth.play.random")) {
                    c = 18;
                    break;
                }
                break;
            case -362899903:
                if (str.equals("command://music.control.collect.play")) {
                    c = 19;
                    break;
                }
                break;
            case -151956081:
                if (str.equals("command://music.soundeffect.vocal")) {
                    c = 20;
                    break;
                }
                break;
            case -102819689:
                if (str.equals("command://music.news.play")) {
                    c = 21;
                    break;
                }
                break;
            case -96514067:
                if (str.equals("command://music.forward")) {
                    c = 22;
                    break;
                }
                break;
            case -89098164:
                if (str.equals("command://music.control.random")) {
                    c = 23;
                    break;
                }
                break;
            case -85238858:
                if (str.equals("command://music.control.resume")) {
                    c = 24;
                    break;
                }
                break;
            case -28153286:
                if (str.equals("command://music.1212.novel.play")) {
                    c = 25;
                    break;
                }
                break;
            case 554746164:
                if (str.equals("native://music.search")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 899591924:
                if (str.equals("command://music.soundeffect.superbass")) {
                    c = 27;
                    break;
                }
                break;
            case 1260718851:
                if (str.equals("command://music.audiobook.subscribe")) {
                    c = 28;
                    break;
                }
                break;
            case 1313205723:
                if (str.equals("command://music.audiobook.play")) {
                    c = 29;
                    break;
                }
                break;
            case 1504460481:
                if (str.equals("command://music.control.collect")) {
                    c = 30;
                    break;
                }
                break;
            case 1616575982:
                if (str.equals("command://music.control.sim")) {
                    c = 31;
                    break;
                }
                break;
            case 1657363090:
                if (str.equals("command://music.soundeffect.live")) {
                    c = ' ';
                    break;
                }
                break;
            case 1841863913:
                if (str.equals("command://music.control.loop.single")) {
                    c = '!';
                    break;
                }
                break;
            case 1903252755:
                if (str.equals("command://music.control.play.usb")) {
                    c = '\"';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onPlayLoopAll(str, str2);
                return;
            case 1:
                this.mTarget.onMusicSpeedSet(str, str2);
                return;
            case 2:
                this.mTarget.onMusicSpeedDown(str, str2);
                return;
            case 3:
                this.mTarget.onSupportPlayModeChange(str, str2);
                return;
            case 4:
                this.mTarget.onMusicSettime(str, str2);
                return;
            case 5:
                this.mTarget.onMusicListPlay(str, str2);
                return;
            case 6:
                this.mTarget.onNext(str, str2);
                return;
            case 7:
                this.mTarget.onPlay(str, str2);
                return;
            case '\b':
                this.mTarget.onPrev(str, str2);
                return;
            case '\t':
                this.mTarget.onStop(str, str2);
                return;
            case '\n':
                this.mTarget.onMusicBackward(str, str2);
                return;
            case 11:
                this.mTarget.onMusicSpeedUp(str, str2);
                return;
            case '\f':
                this.mTarget.onCancelPlaySimilar(str, str2);
                return;
            case '\r':
                this.mTarget.onPause(str, str2);
                return;
            case 14:
                this.mTarget.onDelCollect(str, str2);
                return;
            case 15:
                this.mTarget.onPlayHistoryList(str, str2);
                return;
            case 16:
                this.mTarget.onMusicDailyrecPlay(str, str2);
                return;
            case 17:
                this.mTarget.onSoundEffectStereo(str, str2);
                return;
            case 18:
                this.mTarget.onPlayBlueTooth(str, str2);
                return;
            case 19:
                this.mTarget.onPlayCollect(str, str2);
                return;
            case 20:
                this.mTarget.onSoundEffectVocal(str, str2);
                return;
            case 21:
                this.mTarget.onMusicNewsPlay(str, str2);
                return;
            case 22:
                this.mTarget.onMusicForward(str, str2);
                return;
            case 23:
                this.mTarget.onPlayLoopRandom(str, str2);
                return;
            case 24:
                this.mTarget.onResume(str, str2);
                return;
            case 25:
                this.mTarget.onTwelveNovelPlay(str, str2);
                return;
            case 26:
                this.mTarget.onSearch(str, str2);
                return;
            case 27:
                this.mTarget.onSoundEffectSuperbass(str, str2);
                return;
            case 28:
                this.mTarget.onAudioBookSubscribe(str, str2);
                return;
            case 29:
                this.mTarget.onAudioBookPlay(str, str2);
                return;
            case 30:
                this.mTarget.onControlCollect(str, str2);
                return;
            case 31:
                this.mTarget.onPlaySimilar(str, str2);
                return;
            case ' ':
                this.mTarget.onSoundEffectLive(str, str2);
                return;
            case '!':
                this.mTarget.onPlayLoopSingle(str, str2);
                return;
            case '\"':
                this.mTarget.onPlayUsb(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{"command://music.control.play", "command://music.control.loop.single", "command://music.control.loop.all", "command://music.control.random", "native://music.search", "command://music.control.pause", "command://music.control.resume", "command://music.control.prev", "command://music.control.next", "command://music.control.stop", "command://music.control.bluetooth.play.random", "native://music.playmode.support", "command://music.audiobook.play", "command://music.list.play", "command://music.1212.novel.play", "command://music.control.collect", "command://music.audiobook.subscribe", "command://music.soundeffect.stereo", "command://music.soundeffect.live", "command://music.soundeffect.vocal", "command://music.soundeffect.superbass", "command://music.control.collect.cancel", "command://music.control.collect.play", "command://music.control.sim", "command://music.control.sim.cancel", "command://music.control.playlist.history.play", "command://music.forward", "command://music.backward", "command://music.settime", "command://music.speed.up", "command://music.speed.down", "command://music.speed.set", "command://music.news.play", "command://music.dailyrec.play", "command://music.control.play.usb"};
    }
}
