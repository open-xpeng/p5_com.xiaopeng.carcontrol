package com.xiaopeng.xui.sound;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.AndroidRuntimeException;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xui.sound.XSoundEffect;
import com.xiaopeng.xui.utils.XLogUtils;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class SoundEffectPool extends AbsSoundEffect {
    private static final String TAG = "xpui-SoundEffectPool";
    private XSoundEffect.SoundLoader mISoundLoader;
    private boolean mMustPlay;
    private int mSoundID;
    private SoundPool mSoundPool;
    private int mStreamID;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SoundEffectPool(SoundEffectResource soundEffectResource) {
        super(soundEffectResource);
        log("constructor");
        int location = soundEffectResource.getLocation();
        if (location == 0) {
            this.mISoundLoader = new LoaderAssets();
        } else if (location == 1) {
            this.mISoundLoader = new LoaderSystem();
        }
        init();
    }

    private void init() {
        log("init");
        this.mSoundPool = createSoundPool();
        load();
    }

    @Override // com.xiaopeng.xui.sound.XSoundEffect
    public void play() {
        log("play");
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            this.mMustPlay = true;
            int i = this.mSoundID;
            if (i == 0) {
                load();
                return;
            } else {
                this.mStreamID = soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
                return;
            }
        }
        init();
    }

    @Override // com.xiaopeng.xui.sound.XSoundEffect
    public void pause() {
        log("pause");
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            soundPool.pause(this.mStreamID);
        }
    }

    @Override // com.xiaopeng.xui.sound.XSoundEffect
    public void resume() {
        log("resume");
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            soundPool.resume(this.mStreamID);
        }
    }

    @Override // com.xiaopeng.xui.sound.XSoundEffect
    public void release() {
        log("release");
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            soundPool.release();
            this.mSoundPool = null;
        }
    }

    private SoundPool createSoundPool() {
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(1);
        AudioAttributes.Builder builder2 = new AudioAttributes.Builder();
        builder2.setLegacyStreamType(this.mSoundEffectResource.getStreamType());
        log("createSoundPool mSoundID : " + this.mSoundID + ",StreamType " + this.mSoundEffectResource.getStreamType());
        builder.setAudioAttributes(builder2.build());
        return builder.build();
    }

    private void load() {
        XSoundEffect.SoundLoader soundLoader = this.mISoundLoader;
        if (soundLoader != null) {
            this.mSoundID = soundLoader.load(this.mSoundEffectResource);
            log("load mSoundID : " + this.mSoundID);
        } else {
            log("load mISoundLoader is null");
        }
        this.mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.xiaopeng.xui.sound.-$$Lambda$SoundEffectPool$zOIrsH-oQrJmreAPnlTpV39ypa4
            @Override // android.media.SoundPool.OnLoadCompleteListener
            public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                SoundEffectPool.this.lambda$load$0$SoundEffectPool(soundPool, i, i2);
            }
        });
    }

    public /* synthetic */ void lambda$load$0$SoundEffectPool(SoundPool soundPool, int i, int i2) {
        log("load onLoadComplete--,sampleId:" + i + ",status:" + i2);
        if (this.mMustPlay) {
            this.mMustPlay = false;
            play();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        XLogUtils.d(TAG, str);
    }

    /* loaded from: classes2.dex */
    private class LoaderAssets implements XSoundEffect.SoundLoader {
        private LoaderAssets() {
        }

        @Override // com.xiaopeng.xui.sound.XSoundEffect.SoundLoader
        public int load(SoundEffectResource soundEffectResource) {
            if (SoundEffectPool.this.mSoundPool == null) {
                SoundEffectPool.this.log("LoaderAssets--resource:" + soundEffectResource + ",mSoundPool is null");
                return 0;
            }
            AssetFileDescriptor assetFileDescriptor = null;
            try {
                assetFileDescriptor = Xui.getContext().getAssets().openFd(SoundEffectPool.this.mSoundEffectResource.getPath());
                SoundEffectPool.this.log("LoaderAssets--resource:" + soundEffectResource + ",path:" + SoundEffectPool.this.mSoundEffectResource.getPath() + ", is success");
            } catch (IOException e) {
                SoundEffectPool.this.log("LoaderAssets--resource:" + soundEffectResource + ",path:" + SoundEffectPool.this.mSoundEffectResource.getPath() + ", is error");
                e.printStackTrace();
            }
            try {
                return SoundEffectPool.this.mSoundPool.load(assetFileDescriptor, 1);
            } catch (AndroidRuntimeException e2) {
                e2.printStackTrace();
                return 0;
            }
        }
    }

    /* loaded from: classes2.dex */
    private class LoaderSystem implements XSoundEffect.SoundLoader {
        private LoaderSystem() {
        }

        @Override // com.xiaopeng.xui.sound.XSoundEffect.SoundLoader
        public int load(SoundEffectResource soundEffectResource) {
            if (SoundEffectPool.this.mSoundPool != null) {
                return SoundEffectPool.this.mSoundPool.load(SoundEffectPool.this.mSoundEffectResource.getPath(), 1);
            }
            SoundEffectPool.this.log("LoaderSystem--resource:" + soundEffectResource + ",mSoundPool is null");
            return 0;
        }
    }
}
