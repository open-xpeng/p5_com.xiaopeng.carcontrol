package com.xiaopeng.carcontrol.carmanager.impl.oldarch;

import android.car.Car;
import com.xiaopeng.carcontrol.carmanager.impl.MsmController;
import java.util.List;

/* loaded from: classes.dex */
public class MsmOldController extends MsmController {
    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController
    protected void addExtPropertyIds(List<Integer> propertyIds) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean getBackBeltWStatus() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDSeatLegPos() {
        return -1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getDrvBeltStatus() {
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean getEsbEnable() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public int getPsnBeltStatus() {
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController
    protected boolean isMsbSupport() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public boolean isPsnSeatOccupied() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController
    protected boolean isSrsSupport() {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController
    protected void mockExtMsmProperty() {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setBackBeltSw(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatLegMove(int control, int direction) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatLegPos(int pos) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatLumbHorzMove(int control, int direction) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setDSeatLumbVerMove(int control, int direction) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setEsbEnable(boolean enable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setEsbEnable(boolean enable, boolean saveEnable) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatHorzMove(int control, int direction) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatTiltMove(int control, int direction) {
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.MsmController, com.xiaopeng.carcontrol.carmanager.controller.IMsmController
    public void setPSeatVerMove(int control, int direction) {
    }

    public MsmOldController(Car carClient) {
        super(carClient);
    }
}
