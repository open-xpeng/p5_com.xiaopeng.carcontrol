package com.xiaopeng.carcontrol.viewmodel.sfs;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface ISfsViewModel extends IBaseViewModel {
    SfsChannel getActivatedChannel();

    SfsType[] getTypesInChannel();

    boolean isSwEnabled();

    void setActiveChannel(SfsChannel sfsChannel);

    void setSwEnable(boolean enable);
}
