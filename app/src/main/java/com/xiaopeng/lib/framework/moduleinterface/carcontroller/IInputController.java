package com.xiaopeng.lib.framework.moduleinterface.carcontroller;

import android.view.KeyEvent;

/* loaded from: classes2.dex */
public interface IInputController extends ILifeCycle, com.xiaopeng.lib.framework.moduleinterface.carcontroller.v2extend.IInputController {
    public static final int KEYCODE_BACK_BUTTON = 512;
    public static final int KEYCODE_KNOB_BACKLIGHT_DOWN = 503;
    public static final int KEYCODE_KNOB_BACKLIGHT_UP = 502;
    public static final int KEYCODE_KNOB_BT = 508;
    public static final int KEYCODE_KNOB_FM = 507;
    public static final int KEYCODE_KNOB_MUSIC = 510;
    public static final int KEYCODE_KNOB_TALKING_BOOK = 527;
    public static final int KEYCODE_KNOB_TEMP_DOWN = 505;
    public static final int KEYCODE_KNOB_TEMP_UP = 504;
    public static final int KEYCODE_KNOB_TEM_EXTRA = 1000001;
    public static final int KEYCODE_KNOB_USB_MUSIC = 526;
    public static final int KEYCODE_KNOB_VOL_DOWN = 521;
    public static final int KEYCODE_KNOB_VOL_UP = 520;
    public static final int KEYCODE_KNOB_WEB_RADIO = 509;
    public static final int KEYCODE_KNOB_WIND_SPD_DOWN = 501;
    public static final int KEYCODE_KNOB_WIND_SPD_UP = 500;
    public static final int KEYCODE_LEFT_OK_BUTTON = 516;
    public static final int KEYCODE_NEXT_BUTTON = 514;
    public static final int KEYCODE_PREVIOUS_BUTTON = 513;
    public static final int KEYCODE_RIGHT_OK_BUTTON = 517;
    public static final int KEYCODE_USER_DEF_BUTTON = 511;
    public static final int KEYCODE_VOICE_BUTTON = 515;
    public static final int KEYCODE_WIND_EXIT_MODE = 524;

    /* loaded from: classes2.dex */
    public static class InputAudioSwitchEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputBackLightEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputBlowModeEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputDriverTempDownValueEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputDriverTempUpValueEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputExtraValueEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputRightOKButtonEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputSpeechEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputVolumeEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputWindDownLevelEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputWindUpLevelEventMsg extends AbstractEventMsg<KeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class UserDefButtonEventMsg extends AbstractEventMsg<KeyEvent> {
    }
}
