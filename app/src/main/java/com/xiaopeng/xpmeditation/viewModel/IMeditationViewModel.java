package com.xiaopeng.xpmeditation.viewModel;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface IMeditationViewModel extends IBaseViewModel {
    int getLastPlayIndex();

    default boolean getPnsState() {
        return false;
    }

    default void handlePnsStateChange(boolean state) {
    }

    void init();

    void pause();

    void play();

    void play(int index);

    void playNext();

    void playOrPause();

    void playPre();

    void release();

    void setLastPlayIndex(int index);
}
