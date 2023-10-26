package com.xiaopeng.carcontrol.viewmodel.selfcheck;

import com.xiaopeng.carcontrol.bean.selfcheck.CheckDetailItem;

/* loaded from: classes2.dex */
public interface ICheckCarListener {
    void onCheckCancel();

    void onCheckComplete();

    void onCheckError(Throwable e);

    void onCheckStart();

    void onCheckStop();

    void onChecking(CheckDetailItem item);

    void onMockChecking(int progress);
}
