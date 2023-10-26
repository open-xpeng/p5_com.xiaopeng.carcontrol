package com.xiaopeng.carcontrol.viewmodel.hvac;

import com.xiaopeng.carcontrol.config.DxCarConfig;

/* loaded from: classes2.dex */
public class D2HvacViewModel extends HvacViewModel {
    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void enterHvacSingleMode() {
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacWindBlowWindow() {
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.hvac.D2HvacViewModel$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode;

        static {
            int[] iArr = new int[HvacWindBlowMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode = iArr;
            try {
                iArr[HvacWindBlowMode.Face.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.Foot.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FaceAndFoot.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[HvacWindBlowMode.FootWindshield.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel
    public void setHvacWindMode(HvacWindBlowMode mode) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacWindBlowMode[mode.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            setHvacWindBlowModeGroup(HvacWindBlowMode.toHvacCmdD21(mode));
            return;
        }
        throw new IllegalArgumentException("Illegal HvacWindBlowMode for D21: " + mode);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacCirculationOut() {
        setHvacCirculationMode(2);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void setHvacCirculationInner() {
        setHvacCirculationMode(1);
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
    public void setHvacWindBlowWinFoot() {
        setHvacWindBlowModeGroup(4);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void openHvacWindModeFace() {
        setHvacWindBlowModeGroup(1);
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void closeHvacWindBlowFace() {
        setHvacWindBlowFoot();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void closeHvacWindBlowFoot() {
        setHvacWindBlowFace();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public void closeHvacWindBlowWin() {
        setHvacWindBlowFace();
    }

    @Override // com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel, com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel
    public boolean isSupportXFreeBreath() {
        return DxCarConfig.getInstance().isSupportInnerPm25();
    }
}
