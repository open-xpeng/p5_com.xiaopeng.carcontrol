package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface IAvasController extends ILifeCycle {
    public static final int AVAS_ANALOG_SOUND_GEAR_1 = 0;
    public static final int AVAS_ANALOG_SOUND_GEAR_2 = 1;
    public static final int AVAS_ANALOG_SOUND_GEAR_3 = 2;
    public static final int AVAS_ANALOG_SOUND_GEAR_4 = 3;

    boolean isFault() throws Exception;

    void setAnalogSoundEffect(int i) throws Exception;

    void setAnalogSoundEnable(boolean z) throws Exception;
}
