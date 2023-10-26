package com.xiaopeng.carcontrol.viewmodel.service;

import com.xiaopeng.carcontrol.viewmodel.IBaseViewModel;

/* loaded from: classes2.dex */
public interface ISelfCheckViewModel extends IBaseViewModel {
    void getAllFaultList(IDiagnosticCallback callback);

    void getFaultDetail(int type, String code, IDiagnosticCallback callback);

    void getMobilePhone(IDiagnosticCallback callback);

    void handleAllFaultData(String result);

    void handleFaultDetailData(String result);

    boolean isAutoSelfCheck();

    void reserveRemoteDiagnose(String phone, IDiagnosticCallback callback);

    void setAutoSelfCheck(boolean auto);

    void startSelfCheck();

    void stopSelfCheck();
}
