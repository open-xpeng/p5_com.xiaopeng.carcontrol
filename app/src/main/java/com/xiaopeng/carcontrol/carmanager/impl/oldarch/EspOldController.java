package com.xiaopeng.carcontrol.carmanager.impl.oldarch;

import android.car.Car;
import com.xiaopeng.carcontrol.carmanager.impl.EspController;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes.dex */
public class EspOldController extends EspController {
    @Override // com.xiaopeng.carcontrol.carmanager.impl.EspController
    protected int convertToEspCmd(boolean enable) {
        return enable ? 4 : 3;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.EspController
    protected boolean parseEspStatus(int status) {
        return status == 4;
    }

    public EspOldController(Car carClient) {
        super(carClient);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.EspController, com.xiaopeng.carcontrol.carmanager.controller.IEspController
    public void setEsp(boolean enable) {
        super.setEsp(enable);
        try {
            throw new IllegalStateException("setEsp Debug");
        } catch (Exception e) {
            LogUtils.e("EspController", "setEsp: " + enable + " from ", (Throwable) e, false);
        }
    }
}
