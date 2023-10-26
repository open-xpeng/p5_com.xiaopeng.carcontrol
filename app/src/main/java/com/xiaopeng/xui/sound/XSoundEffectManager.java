package com.xiaopeng.xui.sound;

import android.media.AudioManager;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xui.utils.XLogUtils;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class XSoundEffectManager {
    private static final String TAG = "xpui-XSoundManager";
    private ExecutorService mExecutorService;
    private boolean mIsDestroy;
    private ConcurrentHashMap<Integer, XSoundEffect> mPoolHashMap;
    private ConcurrentHashMap<Integer, Boolean> mReleaseMap;

    private int useAudioManager(int i) {
        if (i != 2) {
            if (i != 3) {
                if (i != 4) {
                    return i != 5 ? -1 : 14;
                }
                return 15;
            }
            return 16;
        }
        return 17;
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final XSoundEffectManager INSTANCE = new XSoundEffectManager();

        private SingletonHolder() {
        }
    }

    private XSoundEffectManager() {
        this.mPoolHashMap = new ConcurrentHashMap<>();
        this.mExecutorService = Executors.newSingleThreadExecutor();
        this.mReleaseMap = new ConcurrentHashMap<>();
    }

    public static XSoundEffectManager get() {
        return SingletonHolder.INSTANCE;
    }

    public synchronized void play(int i) {
        int useAudioManager = useAudioManager(i);
        if (useAudioManager > 0) {
            XAudioManager.get().playSoundEffect(useAudioManager);
        } else {
            playLocal(i);
        }
    }

    private void playLocal(final int i) {
        this.mIsDestroy = false;
        this.mReleaseMap.put(Integer.valueOf(i), false);
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$huGJR5ZOzeh1S69x-AbXgHZTsuw
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$playLocal$0$XSoundEffectManager(i);
            }
        });
    }

    public /* synthetic */ void lambda$playLocal$0$XSoundEffectManager(int i) {
        if (this.mIsDestroy) {
            log(String.format("play-not for destroy resource:%s", Integer.valueOf(i)));
            return;
        }
        Boolean bool = this.mReleaseMap.get(Integer.valueOf(i));
        if (bool != null && bool.booleanValue()) {
            log(String.format("play-not for release resource:%s", Integer.valueOf(i)));
            return;
        }
        System.currentTimeMillis();
        XSoundEffect xSoundEffect = this.mPoolHashMap.get(Integer.valueOf(i));
        if (xSoundEffect == null) {
            xSoundEffect = SoundEffectFactory.create(i);
            this.mPoolHashMap.put(Integer.valueOf(i), xSoundEffect);
        }
        xSoundEffect.play();
    }

    public synchronized void release(final int i) {
        this.mReleaseMap.put(Integer.valueOf(i), true);
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$8Wd81vpyuA-X8mGVcrX3xX1SDRo
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$release$1$XSoundEffectManager(i);
            }
        });
    }

    public /* synthetic */ void lambda$release$1$XSoundEffectManager(int i) {
        XSoundEffect xSoundEffect = this.mPoolHashMap.get(Integer.valueOf(i));
        if (xSoundEffect != null) {
            xSoundEffect.release();
        }
        log(String.format("release resource:%s", Integer.valueOf(i)));
    }

    public synchronized void destroy() {
        this.mIsDestroy = true;
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$jJ-CPGjWuwske6TDKYuJyluIjmE
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$destroy$2$XSoundEffectManager();
            }
        });
    }

    public /* synthetic */ void lambda$destroy$2$XSoundEffectManager() {
        long currentTimeMillis = System.currentTimeMillis();
        for (XSoundEffect xSoundEffect : this.mPoolHashMap.values()) {
            if (xSoundEffect != null) {
                xSoundEffect.release();
            }
        }
        this.mPoolHashMap.clear();
        this.mReleaseMap.clear();
        log("destroy time : " + (System.currentTimeMillis() - currentTimeMillis));
    }

    public synchronized void resetResource(int i, String str, int i2) {
        resetResource(i, str, i2, 5);
    }

    public synchronized void resetResource(final int i, final String str, final int i2, final int i3) {
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$zG7Nx8cWSQRLaBCfVwA7fqBp5YA
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$resetResource$3$XSoundEffectManager(i, str, i2, i3);
            }
        });
    }

    public /* synthetic */ void lambda$resetResource$3$XSoundEffectManager(int i, String str, int i2, int i3) {
        SoundEffectFactory.resetResource(i, str, i2, i3);
        XSoundEffect xSoundEffect = this.mPoolHashMap.get(Integer.valueOf(i));
        if (xSoundEffect != null) {
            xSoundEffect.release();
            this.mPoolHashMap.remove(Integer.valueOf(i));
        }
        log(String.format("resetResource--resource:%s,path:%s,location:%s,streamType:%s", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3)));
    }

    public synchronized void restoreResource(final int i) {
        this.mExecutorService.execute(new Runnable() { // from class: com.xiaopeng.xui.sound.-$$Lambda$XSoundEffectManager$xMqojCGb_wGqPP-iwnFxA7uh2Ko
            @Override // java.lang.Runnable
            public final void run() {
                XSoundEffectManager.this.lambda$restoreResource$4$XSoundEffectManager(i);
            }
        });
    }

    public /* synthetic */ void lambda$restoreResource$4$XSoundEffectManager(int i) {
        SoundEffectFactory.restoreResource(i);
        XSoundEffect xSoundEffect = this.mPoolHashMap.get(Integer.valueOf(i));
        if (xSoundEffect != null) {
            xSoundEffect.release();
            this.mPoolHashMap.remove(Integer.valueOf(i));
        }
        log(String.format("restoreResource resource:%s", Integer.valueOf(i)));
    }

    private void log(String str) {
        XLogUtils.d(TAG, str);
    }

    /* loaded from: classes2.dex */
    public static class XAudioManager {
        public static final int FX_DEL = 14;
        public static final int FX_SWITCH_OFF = 15;
        public static final int FX_SWITCH_ON = 16;
        public static final int FX_WHEEL_SCROLL = 17;
        private AudioManager mAudioManager;

        private XAudioManager() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class SingletonHolder {
            private static final XAudioManager INSTANCE = new XAudioManager();

            private SingletonHolder() {
            }
        }

        public static XAudioManager get() {
            return SingletonHolder.INSTANCE;
        }

        private AudioManager getAudioManager() {
            if (this.mAudioManager == null) {
                this.mAudioManager = (AudioManager) Xui.getContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
            }
            return this.mAudioManager;
        }

        public void playSoundEffect(int i) {
            getAudioManager().playSoundEffect(i);
        }
    }
}
