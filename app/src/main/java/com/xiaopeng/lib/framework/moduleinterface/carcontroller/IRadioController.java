package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

/* loaded from: classes2.dex */
public interface IRadioController extends ILifeCycle {
    public static final int BAND_AM = 3;
    public static final int BAND_FM = 0;
    public static final int TEF663x_PCHANNEL = 256;
    public static final int X_SOUND_BASS = 7;
    public static final int X_SOUND_CLASSICAL = 2;
    public static final int X_SOUND_CUSTOMIZE = 10;
    public static final int X_SOUND_DEFAULT = 0;
    public static final int X_SOUND_DYNAMIC = 8;
    public static final int X_SOUND_ELEGANT = 12;
    public static final int X_SOUND_HIFI = 11;
    public static final int X_SOUND_JAZZ = 4;
    public static final int X_SOUND_POPULAR = 5;
    public static final int X_SOUND_PUREVOICE = 9;
    public static final int X_SOUND_ROCK = 1;
    public static final int X_SOUND_SURROUND = 6;
    public static final int X_SOUND_VOICE = 3;

    /* loaded from: classes2.dex */
    public static class AudioDspInfoEventMsg extends AbstractEventMsg<String> {
    }

    /* loaded from: classes2.dex */
    public static class RadioInfoEventMsg extends AbstractEventMsg<String> {
    }

    void enableAudioDpsInfoCallback() throws Exception;

    String getAudioDspInfo() throws Exception;

    int[] getAudioMode() throws Exception;

    int getRadioBand() throws Exception;

    int[] getRadioFrequency() throws Exception;

    String getRadioInfo() throws Exception;

    int getRadioVolumeAutoFocus() throws Exception;

    int[] getRadioVolumePercent() throws Exception;

    boolean getTunerPowerStatus() throws Exception;

    int getXSoundType() throws Exception;

    void setAudioBalanceFader(int i, int i2) throws Exception;

    void setAudioGEQParams(int i, int i2, int i3, int i4) throws Exception;

    void setExhibitionModeVolume(int i) throws Exception;

    void setFmVolume(int i, int i2) throws Exception;

    void setMainAudioMode(int i, int i2) throws Exception;

    void setPowerOffTuner() throws Exception;

    void setPowerOnTuner() throws Exception;

    void setRadioBand(int i) throws Exception;

    void setRadioFrequency(int i, int i2) throws Exception;

    void setRadioSearchDown() throws Exception;

    void setRadioSearchUp() throws Exception;

    void setRadioVolumeAutoFocus(int i) throws Exception;

    void setRadioVolumePercent(int i, int i2) throws Exception;

    void setStartFullBandScan() throws Exception;

    void setStopFullBandScan() throws Exception;

    void setXSoundType(int i) throws Exception;
}
