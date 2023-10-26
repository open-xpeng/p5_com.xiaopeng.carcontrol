package com.xiaopeng.carcontrol.viewmodel.selfcheck;

import com.xiaopeng.carcontrol.bean.selfcheck.CheckCategoryItem;
import com.xiaopeng.carcontrol.bean.selfcheck.CheckDetailItem;

/* loaded from: classes2.dex */
public interface ICheckCarMode {
    String generateJsonForUi(int status, boolean needDetail);

    String generateMockExtrasForInfoflow(int checkedCount);

    CheckCategoryItem getCategoryItem(int index);

    int getCategoryItemSize();

    int getCheckSize();

    int getCheckedCount();

    int getFaultCount();

    String getFaultStr();

    CheckDetailItem getFirstFaultItem();

    String getTTs();

    boolean hasIssue();

    boolean isCheckComplete();

    boolean isChecking();

    boolean isSeverity();

    void receiveGreet();

    void setViewListener(ICheckCarListener viewListener);

    void startCheck();

    void stopCheck();

    void uploadForAIAssistant(String jsonStr);
}
