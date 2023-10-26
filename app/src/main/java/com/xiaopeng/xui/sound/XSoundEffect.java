package com.xiaopeng.xui.sound;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public interface XSoundEffect {
    public static final int FX_DEL = 5;
    public static final int FX_DEMO = -1;
    public static final int FX_SWITCH_OFF = 4;
    public static final int FX_SWITCH_ON = 3;
    public static final int FX_TOUCH = 1;
    public static final int FX_WHEEL_SCROLL = 2;
    public static final int LOCATION_ASSETS = 0;
    public static final int LOCATION_RES = 2;
    public static final int LOCATION_SYSTEM = 1;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ID {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Location {
    }

    /* loaded from: classes2.dex */
    public interface SoundLoader {
        int load(SoundEffectResource soundEffectResource);
    }

    void pause();

    void play();

    void release();

    void resume();
}
