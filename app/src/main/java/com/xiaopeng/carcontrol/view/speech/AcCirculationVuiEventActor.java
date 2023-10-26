package com.xiaopeng.carcontrol.view.speech;

import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacCirculationMode;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacSwitchStatus;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.speech.vui.actor.BaseVuiEventActor;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiEvent;

/* loaded from: classes2.dex */
public class AcCirculationVuiEventActor extends BaseVuiEventActor {
    private HvacViewModel mViewModel;
    private HvacCirculationMode mode;

    public AcCirculationVuiEventActor(HvacViewModel viewModel, VuiEvent vuiEvent) {
        this.mViewModel = viewModel;
        boolean booleanValue = ((Boolean) vuiEvent.getEventValue(vuiEvent)).booleanValue();
        String[] split = vuiEvent.getHitVuiElement().getId().split("_");
        if (split == null || split.length <= 0) {
            return;
        }
        String str = split[split.length - 1];
        if (VuiUtils.isNumer(str)) {
            int intValue = Integer.valueOf(str).intValue();
            if (intValue == 1) {
                if (booleanValue) {
                    this.mode = HvacCirculationMode.Inner;
                } else {
                    this.mode = HvacCirculationMode.Outside;
                }
            } else if (intValue == 2) {
                if (booleanValue) {
                    this.mode = HvacCirculationMode.Outside;
                } else {
                    this.mode = HvacCirculationMode.Inner;
                }
            } else if (intValue != 3) {
            } else {
                if (!booleanValue) {
                    this.mode = HvacCirculationMode.Outside;
                } else {
                    this.mode = null;
                }
            }
        }
    }

    public HvacCirculationMode getMode() {
        return this.mode;
    }

    @Override // com.xiaopeng.speech.vui.actor.IVuiEventActor
    public void execute() {
        if (CarBaseConfig.getInstance().isSupportAqs() && this.mViewModel.getHvacAqsStatus() == HvacSwitchStatus.ON) {
            this.mViewModel.setHvacAqsStatus(HvacSwitchStatus.OFF);
        }
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.view.speech.-$$Lambda$AcCirculationVuiEventActor$dgdadvcbrUBckHftUkrBx2VYCfc
            @Override // java.lang.Runnable
            public final void run() {
                AcCirculationVuiEventActor.this.lambda$execute$0$AcCirculationVuiEventActor();
            }
        }, 200L);
    }

    /* renamed from: com.xiaopeng.carcontrol.view.speech.AcCirculationVuiEventActor$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationMode;

        static {
            int[] iArr = new int[HvacCirculationMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationMode = iArr;
            try {
                iArr[HvacCirculationMode.Auto.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationMode[HvacCirculationMode.Inner.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationMode[HvacCirculationMode.Outside.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public /* synthetic */ void lambda$execute$0$AcCirculationVuiEventActor() {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$hvac$HvacCirculationMode[this.mode.ordinal()];
        if (i == 1 || i == 2) {
            this.mViewModel.setHvacCirculationInner();
        } else if (i != 3) {
        } else {
            this.mViewModel.setHvacCirculationOut();
        }
    }
}
