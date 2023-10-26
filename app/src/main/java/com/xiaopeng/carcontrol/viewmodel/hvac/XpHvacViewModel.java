package com.xiaopeng.carcontrol.viewmodel.hvac;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;

/* loaded from: classes2.dex */
public class XpHvacViewModel extends HvacViewModel {
    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void enterHvacSingleMode() {
        if (BaseFeatureOption.getInstance().isSupportSingleMode()) {
            super.enterHvacSingleMode();
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSupportXFreeBreath() {
        return CarBaseConfig.getInstance().isSupportInnerPm25();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowFace() {
        setHvacWindBlowModeGroup(1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowFoot() {
        setHvacWindBlowModeGroup(3);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowFaceFoot() {
        setHvacWindBlowModeGroup(2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowWindow() {
        setHvacWindBlowModeGroup(6);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowWinFoot() {
        setHvacWindBlowModeGroup(4);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void openHvacWindModeFace() {
        int hvacWindBlowMode = getHvacWindBlowMode();
        if (1 == hvacWindBlowMode || 2 == hvacWindBlowMode || 8 == hvacWindBlowMode || 9 == hvacWindBlowMode) {
            return;
        }
        setHvacWindBlowMode(2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void closeHvacWindBlowFace() {
        int hvacWindBlowMode = getHvacWindBlowMode();
        if (hvacWindBlowMode == 1 || hvacWindBlowMode == 2 || hvacWindBlowMode == 8 || hvacWindBlowMode == 9) {
            if (hvacWindBlowMode == 1) {
                setHvacWindBlowFoot();
            } else {
                setHvacWindBlowMode(2);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void closeHvacWindBlowFoot() {
        int hvacWindBlowMode = getHvacWindBlowMode();
        if (hvacWindBlowMode == 3 || hvacWindBlowMode == 2 || hvacWindBlowMode == 4 || hvacWindBlowMode == 9) {
            if (hvacWindBlowMode == 3) {
                setHvacWindBlowFace();
            } else {
                setHvacWindBlowMode(3);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void closeHvacWindBlowWin() {
        int hvacWindBlowMode = getHvacWindBlowMode();
        if (hvacWindBlowMode == 6 || hvacWindBlowMode == 4 || hvacWindBlowMode == 8 || hvacWindBlowMode == 5 || hvacWindBlowMode == 9) {
            if (hvacWindBlowMode == 6 || hvacWindBlowMode == 5) {
                setHvacWindBlowFoot();
            } else {
                setHvacWindBlowMode(1);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacBaseViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacHeatMode(boolean enable) {
        setHvacAcMode(enable);
    }
}
