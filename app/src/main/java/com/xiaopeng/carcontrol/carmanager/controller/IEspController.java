package com.xiaopeng.carcontrol.carmanager.controller;

import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IEspController extends IBaseCarController<Callback> {
    public static final int APB_SYSTEM_DISPLAY_MESSAGE_INSUFFICIENT_PARK_REPLACE_PARK = 2;
    public static final int APB_SYSTEM_DISPLAY_MESSAGE_NO_RELEASE_TIP_FOR_P_FILE = 3;
    public static final int APB_SYSTEM_DISPLAY_MESSAGE_NO_RELEASE_TIP_FOR_SHIFT = 1;
    public static final int APB_SYSTEM_DISPLAY_MESSAGE_OFF = 0;
    public static final int APB_SYSTEM_STATUS_CLOSED = 2;
    public static final int APB_SYSTEM_STATUS_LOCKING = 4;
    public static final int APB_SYSTEM_STATUS_RELEASED = 1;
    public static final int APB_SYSTEM_STATUS_RELEASING = 3;
    public static final int APB_SYSTEM_STATUS_UNDEFINED = 0;
    public static final int AVH_STATUS_ACTIVE = 1;
    public static final int AVH_STATUS_STANDBY = 2;
    public static final int EPB_DRIVER_OFF_WARNING_OFF = 0;
    public static final int EPB_DRIVER_OFF_WARNING_RELEASE_PARKING_BRAKE_BY_EPB_BUTTON = 1;
    public static final int ESC_ESP_NORMAL = 1;
    public static final int ESC_ESP_OFF = 3;
    public static final int ESC_ESP_ON = 4;
    public static final int ESC_ESP_SPORT = 2;
    public static final int ESP_BPF_AUTO = 4;
    public static final int ESP_BPF_ECO = 2;
    public static final int ESP_BPF_NORMAL = 1;
    public static final int ESP_BPF_SPORT = 3;
    public static final int ESP_STATUS_OFF = 0;
    public static final int ESP_STATUS_ON = 1;
    public static final int HAS_HBC_REQUEST = 1;
    public static final int NO_HBC_REQUEST = 0;

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onApbSystemStatusChanged(int status) {
        }

        void onAvhFaultChanged(boolean isFault);

        void onAvhStatusChanged(int status);

        void onAvhSwChanged(boolean enabled);

        default void onEpbFaultChanged(boolean isFault) {
        }

        void onEspChanged(boolean enabled);

        void onEspFaultChanged(boolean isFault);

        default void onEspOffRoadStatusChanged(boolean on) {
        }

        void onEspSwChanged(boolean enabled);

        default void onHbcRequestStatusChanged(int status) {
        }

        void onHdcChanged(boolean enabled);

        default void onHdcFaultChanged(boolean isFault) {
        }
    }

    int getApbSystemDisplayMessage();

    int getApbSystemStatus();

    int getAvh();

    boolean getAvhFault();

    boolean getAvhSw();

    boolean getDtcFaultStatus();

    int getEpbDriverOffWarningMsg();

    boolean getEpsWarningState();

    int getEsp();

    int getEspBpfMode();

    boolean getEspCstSw();

    boolean getEspFault();

    boolean getEspMudStatus();

    boolean getEspSw();

    int getEspTsmSwitchStatus();

    int getHbcRequestStatus();

    boolean getHdc();

    boolean getHdcFault();

    boolean getTsmFaultStatus();

    void setAvh(boolean enable);

    void setAvhSw(boolean enable);

    void setEpbSystemStatus(boolean enable);

    void setEsp(boolean enable);

    void setEspBpfMode(int mode);

    void setEspCstSw(boolean enable);

    void setEspModeSport();

    void setEspMudStatus(boolean enable);

    void setEspSw(boolean enable);

    void setEspTsmSwitchStatus(boolean enable);

    void setHdc(boolean enable);
}
