package com.xiaopeng.speech.protocol.node.music;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.node.music.bean.CollectHistoryMusic;
import com.xiaopeng.speech.protocol.node.music.bean.SearchMusic;

/* loaded from: classes2.dex */
public interface MusicListener extends INodeListener {
    void onAudioBookPlay(String str);

    void onAudioBookSubscribe();

    void onCancelPlaySimilar();

    void onControlCollect();

    void onDelCollect();

    void onExit();

    void onMusicBackward(int i);

    void onMusicDailyrecPlay();

    void onMusicForward(int i);

    void onMusicListPlay(String str);

    void onMusicNewsPlay();

    void onMusicSettime(int i);

    void onMusicSpeedDown();

    void onMusicSpeedSet(float f);

    void onMusicSpeedUp();

    void onNext();

    void onPause();

    void onPlay();

    void onPlayBluetooth();

    void onPlayCollect();

    void onPlayCollect(CollectHistoryMusic collectHistoryMusic);

    void onPlayHistoryList(CollectHistoryMusic collectHistoryMusic);

    void onPlayMode(String str);

    void onPlaySimilar();

    void onPlayUsb();

    void onPrev();

    void onResume();

    void onSearch(String str, SearchMusic searchMusic);

    void onSoundEffectLive();

    void onSoundEffectStereo();

    void onSoundEffectSuperbass();

    void onSoundEffectVocal();

    void onStop();

    void onSupportPlayModeChange(String str);

    void onTwelveNovelPlay(String str);
}
