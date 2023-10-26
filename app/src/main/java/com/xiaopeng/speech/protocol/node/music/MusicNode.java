package com.xiaopeng.speech.protocol.node.music;

import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.protocol.SpeechUtils;
import com.xiaopeng.speech.protocol.node.context.AbsContextListener;
import com.xiaopeng.speech.protocol.node.context.ContextNode;
import com.xiaopeng.speech.protocol.node.music.bean.CollectHistoryMusic;
import com.xiaopeng.speech.protocol.node.music.bean.SearchMusic;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import java.math.BigDecimal;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MusicNode extends SpeechNode<MusicListener> {
    private static final String TAG = "MusicNode";

    public MusicNode() {
        SpeechUtils.subscribe(ContextNode.class, new AbsContextListener() { // from class: com.xiaopeng.speech.protocol.node.music.MusicNode.1
            @Override // com.xiaopeng.speech.protocol.node.context.AbsContextListener, com.xiaopeng.speech.protocol.node.context.ContextListener
            public void onWidgetText(String str) {
                JSONObject optJSONObject;
                super.onWidgetText(str);
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (!"music_list_play".equals(jSONObject.optString("function")) || (optJSONObject = jSONObject.optJSONObject(SpeechWidget.WIDGET_EXTRA)) == null) {
                        return;
                    }
                    MusicNode.this.onMusicListPlay("command://music.list.play", optJSONObject.optString("param"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlay(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPlay();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayLoopSingle(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPlayMode("single");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayLoopAll(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPlayMode("order");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayLoopRandom(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPlayMode("random");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSearch(String str, String str2) {
        SearchMusic fromJson = SearchMusic.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onSearch(str, fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPause(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPause();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onResume(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onResume();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPrev(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPrev();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onNext(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onNext();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStop(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onStop();
            }
        }
    }

    protected void onExit(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onExit();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayBlueTooth(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPlayBluetooth();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSupportPlayModeChange(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onSupportPlayModeChange(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAudioBookPlay(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onAudioBookPlay(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onMusicListPlay(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (SpeechUtils.isJson(str2)) {
            try {
                JSONObject jSONObject = new JSONObject(str2);
                if (jSONObject.has("from") && "dui_xp".equals(jSONObject.optString("from"))) {
                    LogUtils.i(TAG, "is from dui_xp");
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (Object obj : collectCallbacks) {
            ((MusicListener) obj).onMusicListPlay(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTwelveNovelPlay(String str, String str2) {
        String str3;
        try {
            str3 = new JSONObject(str2).optString("param");
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = null;
        }
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onTwelveNovelPlay(str3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onControlCollect(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onControlCollect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAudioBookSubscribe(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onAudioBookSubscribe();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSoundEffectStereo(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onSoundEffectStereo();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSoundEffectLive(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onSoundEffectLive();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSoundEffectVocal(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onSoundEffectVocal();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onSoundEffectSuperbass(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onSoundEffectSuperbass();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDelCollect(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onDelCollect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayCollect(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        CollectHistoryMusic fromJson = CollectHistoryMusic.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPlayCollect(fromJson);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlaySimilar(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPlaySimilar();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCancelPlaySimilar(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onCancelPlaySimilar();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayHistoryList(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        CollectHistoryMusic fromJson = CollectHistoryMusic.fromJson(str2);
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPlayHistoryList(fromJson);
            }
        }
    }

    public void startMusicSearch() {
        SpeechClient.instance().getWakeupEngine().startDialogFrom("music");
    }

    public void onMusicForward(String str, String str2) {
        try {
            Object[] collectCallbacks = this.mListenerList.collectCallbacks();
            int optInt = new JSONObject(str2).optInt("value");
            if (collectCallbacks != null) {
                for (Object obj : collectCallbacks) {
                    ((MusicListener) obj).onMusicForward(optInt);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onMusicBackward(String str, String str2) {
        try {
            Object[] collectCallbacks = this.mListenerList.collectCallbacks();
            int optInt = new JSONObject(str2).optInt("value");
            if (collectCallbacks != null) {
                for (Object obj : collectCallbacks) {
                    ((MusicListener) obj).onMusicBackward(optInt);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onMusicSettime(String str, String str2) {
        try {
            Object[] collectCallbacks = this.mListenerList.collectCallbacks();
            int optInt = new JSONObject(str2).optInt("value");
            if (collectCallbacks != null) {
                for (Object obj : collectCallbacks) {
                    ((MusicListener) obj).onMusicSettime(optInt);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onMusicSpeedUp(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onMusicSpeedUp();
            }
        }
    }

    public void onMusicSpeedDown(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onMusicSpeedDown();
            }
        }
    }

    public void onMusicSpeedSet(String str, String str2) {
        try {
            Object[] collectCallbacks = this.mListenerList.collectCallbacks();
            float floatValue = BigDecimal.valueOf(new JSONObject(str2).optDouble("value")).floatValue();
            if (collectCallbacks != null) {
                for (Object obj : collectCallbacks) {
                    ((MusicListener) obj).onMusicSpeedSet(floatValue);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onMusicNewsPlay(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onMusicNewsPlay();
            }
        }
    }

    public void onMusicDailyrecPlay(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onMusicDailyrecPlay();
            }
        }
    }

    public void onPlayUsb(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((MusicListener) obj).onPlayUsb();
            }
        }
    }
}
