package com.xiaopeng.xui.sound;

import android.util.SparseArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class SoundEffectFactory {
    private static final String PATH_BASE = "system/media/audio/xiaopeng/cdu/wav/";
    private static final String PATH_DEL = "CDU_delete_4.wav";
    private static final String PATH_SWITCH_OFF = "CDU_switch_off_2.wav";
    private static final String PATH_SWITCH_ON = "CDU_switch_on_2.wav";
    private static final String PATH_TOUCH = "CDU_touch.wav";
    private static final String PATH_WHEEL_SCROLL = "CDU_wheel_scroll_7.wav";
    private static SparseArray<SoundEffectResource> msDefaultMap;
    private static SparseArray<SoundEffectResource> msMap;

    SoundEffectFactory() {
    }

    static {
        SparseArray<SoundEffectResource> sparseArray = new SparseArray<>();
        msMap = sparseArray;
        sparseArray.put(-1, new DefaultSoundEffectResource(PATH_TOUCH));
        msMap.put(1, new DefaultSoundEffectResource(PATH_TOUCH));
        msMap.put(2, new DefaultSoundEffectResource(PATH_WHEEL_SCROLL));
        msMap.put(3, new DefaultSoundEffectResource(PATH_SWITCH_ON));
        msMap.put(4, new DefaultSoundEffectResource(PATH_SWITCH_OFF));
        msMap.put(5, new DefaultSoundEffectResource(PATH_DEL));
        msDefaultMap = msMap.clone();
    }

    /* loaded from: classes2.dex */
    static class DefaultSoundEffectResource extends SoundEffectResource {
        DefaultSoundEffectResource(String str) {
            super(SoundEffectFactory.PATH_BASE + str, 1, 5);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static XSoundEffect create(int i) {
        return new SoundEffectPool(msMap.get(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void resetResource(int i, String str, int i2, int i3) {
        msMap.put(i, new SoundEffectResource(str, i2, i3));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void restoreResource(int i) {
        msMap.put(i, msDefaultMap.get(i));
    }
}
