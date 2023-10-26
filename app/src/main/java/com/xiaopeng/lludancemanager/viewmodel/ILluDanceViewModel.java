package com.xiaopeng.lludancemanager.viewmodel;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;
import com.xiaopeng.lludancemanager.bean.LluDanceViewData;
import java.util.List;

/* loaded from: classes2.dex */
public interface ILluDanceViewModel extends IBaseViewModel {
    void destroy();

    void getAllData();

    void preliminaryWork();

    int startPlay(LluDanceViewData lluDanceViewData);

    void updateAllData(List<LluDanceViewData> list);

    void updateLoadingState(boolean isLoading);
}
