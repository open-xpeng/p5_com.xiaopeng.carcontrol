package com.xiaopeng.carcontrol.carmanager.impl.oldarch;

import android.car.Car;
import com.xiaopeng.carcontrol.carmanager.impl.AvasController;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.List;

/* loaded from: classes.dex */
public class AvasOldController extends AvasController {
    @Override // com.xiaopeng.carcontrol.carmanager.impl.AvasController
    protected void addExtPropertyIds(List<Integer> propertyIds) {
    }

    public AvasOldController(Car carClient) {
        super(carClient);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.AvasController, com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setLowSpdSoundEnable(final boolean enable) {
        super.setLowSpdSoundEnable(enable);
        this.mDataSync.setAvasSw(enable);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$AvasOldController$gm29Mgi-d3NHgs_xlvawLRyxfsQ
            @Override // java.lang.Runnable
            public final void run() {
                AvasOldController.this.lambda$setLowSpdSoundEnable$0$AvasOldController(enable);
            }
        });
    }

    public /* synthetic */ void lambda$setLowSpdSoundEnable$0$AvasOldController(final boolean enable) {
        handleLowSpdEnableUpdate(enable ? 1 : 0);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.AvasController, com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public boolean isLowSpdSoundEnabled() {
        return this.mDataSync.getAvasSw();
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.AvasController, com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public void setLowSpdSoundType(final int type) {
        super.setLowSpdSoundType(type);
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$AvasOldController$8Bxpj62H8BnpJk9aXPUhZYg_El4
            @Override // java.lang.Runnable
            public final void run() {
                AvasOldController.this.lambda$setLowSpdSoundType$1$AvasOldController(type);
            }
        });
    }

    public /* synthetic */ void lambda$setLowSpdSoundType$1$AvasOldController(final int type) {
        handleLowSpdEffectUpdate(type);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.AvasController, com.xiaopeng.carcontrol.carmanager.controller.IAvasController
    public int getLowSpdSoundType() {
        return this.mDataSync.getAvasEffect();
    }
}
