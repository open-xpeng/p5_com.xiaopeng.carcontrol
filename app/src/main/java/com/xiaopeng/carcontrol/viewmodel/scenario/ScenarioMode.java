package com.xiaopeng.carcontrol.viewmodel.scenario;

import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;

/* loaded from: classes2.dex */
public enum ScenarioMode {
    Meditation,
    SpaceCapsule,
    SpaceCapsuleSleep,
    SpaceCapsuleCinema,
    VipSeat,
    Rescue,
    ViceMeditation,
    Normal;

    public static ScenarioMode fromScenario(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1943876483:
                if (str.equals(IScenarioController.SCENARIO_TRAILED_MODE)) {
                    c = 0;
                    break;
                }
                break;
            case -960131973:
                if (str.equals(IScenarioController.SPACE_CAPSULE_MODE)) {
                    c = 1;
                    break;
                }
                break;
            case -631527828:
                if (str.equals(IScenarioController.SPACE_CAPSULE_MOVIE_MODE)) {
                    c = 2;
                    break;
                }
                break;
            case -626092525:
                if (str.equals(IScenarioController.SPACE_CAPSULE_SLEEP_MODE)) {
                    c = 3;
                    break;
                }
                break;
            case -352180192:
                if (str.equals(IScenarioController.SCENARIO_VIPSEAT_MODE)) {
                    c = 4;
                    break;
                }
                break;
            case -261345797:
                if (str.equals(IScenarioController.SCENARIO_NORMAL)) {
                    c = 5;
                    break;
                }
                break;
            case 885092324:
                if (str.equals(IScenarioController.MEDITATION_MODE)) {
                    c = 6;
                    break;
                }
                break;
            case 1378112983:
                if (str.equals(IScenarioController.VICE_MEDITATION_MODE)) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Rescue;
            case 1:
                return SpaceCapsule;
            case 2:
                return SpaceCapsuleCinema;
            case 3:
                return SpaceCapsuleSleep;
            case 4:
                return VipSeat;
            case 5:
                return Normal;
            case 6:
                return Meditation;
            case 7:
                return ViceMeditation;
            default:
                throw new IllegalArgumentException("Unknown Scenario Mode: " + str);
        }
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode;

        static {
            int[] iArr = new int[ScenarioMode.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode = iArr;
            try {
                iArr[ScenarioMode.Meditation.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode[ScenarioMode.ViceMeditation.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode[ScenarioMode.SpaceCapsule.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode[ScenarioMode.SpaceCapsuleSleep.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode[ScenarioMode.SpaceCapsuleCinema.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode[ScenarioMode.VipSeat.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode[ScenarioMode.Rescue.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode[ScenarioMode.Normal.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public String toScenarioStr() {
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$scenario$ScenarioMode[ordinal()]) {
            case 1:
                return IScenarioController.MEDITATION_MODE;
            case 2:
                return IScenarioController.VICE_MEDITATION_MODE;
            case 3:
                return IScenarioController.SPACE_CAPSULE_MODE;
            case 4:
                return IScenarioController.SPACE_CAPSULE_SLEEP_MODE;
            case 5:
                return IScenarioController.SPACE_CAPSULE_MOVIE_MODE;
            case 6:
                return IScenarioController.SCENARIO_VIPSEAT_MODE;
            case 7:
                return IScenarioController.SCENARIO_TRAILED_MODE;
            case 8:
                return IScenarioController.SCENARIO_NORMAL;
            default:
                throw new IllegalArgumentException("Unknown Scenario Mode: " + this);
        }
    }
}
