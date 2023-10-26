package com.xiaopeng.carcontrol.viewmodel.audio;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IAudioViewModel extends IBaseViewModel {
    void confirmMuteMicrophone();

    void confirmMuteMicrophone(int displayId);

    void confirmUnmuteMicrophone(boolean shouldDismiss, int displayId);

    void dismissAllDialogs();

    boolean isMicrophoneMute();

    void setMicrophoneMute(boolean mute);
}
