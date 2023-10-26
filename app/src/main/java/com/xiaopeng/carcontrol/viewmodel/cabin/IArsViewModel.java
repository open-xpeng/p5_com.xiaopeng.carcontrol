package com.xiaopeng.carcontrol.viewmodel.cabin;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IArsViewModel extends IBaseViewModel {
    public static final int ERROR_STATUS_ARS_FAULT = 6;
    public static final int ERROR_STATUS_ARS_FOLD_DIALOG_CONFIRMING = 10;
    public static final int ERROR_STATUS_ARS_INITIALIZE_DIALOG_CONFIRMING = 4;
    public static final int ERROR_STATUS_ARS_INITIALIZING = 3;
    public static final int ERROR_STATUS_ARS_MOVING = 5;
    public static final int ERROR_STATUS_ARS_RESETTING = 7;
    public static final int ERROR_STATUS_ARS_RESET_DIALOG_CONFIRMING = 8;
    public static final int ERROR_STATUS_ARS_UNFOLD_DIALOG_CONFIRMING = 9;
    public static final int ERROR_STATUS_ARS_UNITIALIZED = 2;
    public static final int ERROR_STATUS_NONE = 0;
    public static final int ERROR_STATUS_TRUNK_OPENED = 1;

    int checkError();

    void fold();

    void fold(boolean warning);

    ArsFaultState getArsFaultState();

    MutableLiveData<ArsFaultState> getArsFaultStateLiveData();

    ArsFoldState getArsFoldState();

    MutableLiveData<ArsFoldState> getArsFoldStateLiveData();

    ArsInitState getArsInitState();

    MutableLiveData<ArsInitState> getArsInitStateLiveData();

    int getArsPosition();

    MutableLiveData<Integer> getArsPositionLiveData();

    ArsWorkMode getArsWorkMode();

    MutableLiveData<ArsWorkMode> getArsWorkModeLiveData();

    ArsWorkState getArsWorkState();

    MutableLiveData<ArsWorkState> getArsWorkStateLiveData();

    void handleArsFaultStateChanged(int status);

    void handleArsInitStateChanged(int status);

    void handleArsPositionChanged(int position);

    void handleArsWorkModeChanged(int workMode);

    void handleArsWorkStateChanged(int status);

    void setArsWorkMode(ArsWorkMode mode);

    void startInitialize(boolean withWarning);

    void startReset(boolean withWarning);

    void unfold();

    void unfold(boolean warning);
}
