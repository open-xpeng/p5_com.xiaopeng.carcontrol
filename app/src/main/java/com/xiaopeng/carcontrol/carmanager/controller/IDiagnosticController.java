package com.xiaopeng.carcontrol.carmanager.controller;

import android.car.diagnostic.XpDiagnosticManager;
import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.carmanager.IBaseCarController;

/* loaded from: classes.dex */
public interface IDiagnosticController extends IBaseCarController<Callback> {

    /* loaded from: classes.dex */
    public interface Callback extends IBaseCallback {
        default void onAbsFaultChanged(boolean isFault) {
        }

        void onIbtFaultChanged(boolean isFault);
    }

    XpDiagnosticManager getManager();

    boolean isAbsFault();

    boolean isEpbFault();

    boolean isIbtFault();
}
