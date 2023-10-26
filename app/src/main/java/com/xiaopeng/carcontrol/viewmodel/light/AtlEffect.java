package com.xiaopeng.carcontrol.viewmodel.light;

import com.xiaopeng.carcontrol.carmanager.controller.IAtlController;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public enum AtlEffect {
    None("stable_effect", "stable"),
    Speed(IAtlController.ATL_EFFECT_SPEED, "speed"),
    Breathing(IAtlController.ATL_EFFECT_BREATH, "breath"),
    Music("music", "music");
    
    List<String> alias;
    String commandNew;
    String commandOld;

    AtlEffect(String commandOld, String commandNew) {
        ArrayList arrayList = new ArrayList();
        this.alias = arrayList;
        this.commandOld = commandOld;
        this.commandNew = commandNew;
        arrayList.add(commandOld);
        this.alias.add(commandNew);
    }

    public List<String> getAlias() {
        return this.alias;
    }

    public boolean match(String text) {
        for (String str : this.alias) {
            if (str.equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

    public static AtlEffect fromAtlStatus(String effect) {
        AtlEffect[] values;
        for (AtlEffect atlEffect : values()) {
            if (atlEffect.match(effect)) {
                return atlEffect;
            }
        }
        return None;
    }

    public String toAtlCmd() {
        return CarBaseConfig.getInstance().isSupportAmbientService() ? this.commandNew : this.commandOld;
    }

    /* renamed from: com.xiaopeng.carcontrol.viewmodel.light.AtlEffect$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect;

        static {
            int[] iArr = new int[AtlEffect.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect = iArr;
            try {
                iArr[AtlEffect.Speed.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect[AtlEffect.Breathing.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect[AtlEffect.Music.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static int toStatistic(AtlEffect atlEffect) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$viewmodel$light$AtlEffect[atlEffect.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return i != 3 ? 1 : 4;
            }
            return 2;
        }
        return 3;
    }
}
