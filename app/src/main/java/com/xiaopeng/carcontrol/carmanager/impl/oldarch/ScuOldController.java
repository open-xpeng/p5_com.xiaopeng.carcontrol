package com.xiaopeng.carcontrol.carmanager.impl.oldarch;

import android.car.Car;
import android.car.hardware.CarPropertyValue;
import com.xiaopeng.carcontrol.carmanager.controller.IScuController;
import com.xiaopeng.carcontrol.carmanager.impl.ScuController;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.Iterator;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class ScuOldController extends ScuController {
    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController
    protected int convertBsdState(int state) {
        if (state != 1) {
            return state != 5 ? 2 : 0;
        }
        return 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController
    protected int convertDowState(int state) {
        return (state == 0 || state == 1 || state == 2) ? state : (state == 3 || state == 4) ? 2 : 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController
    protected int convertFcwState(int state) {
        if (state == 2 || state == 3) {
            return 2;
        }
        return state != 5 ? 1 : 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController
    protected int convertIslaState(int state) {
        if (state == 1 || state == 2) {
            return 2;
        }
        return state != 3 ? 0 : 1;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController
    protected int convertIslcState(int state) {
        if (state == 1 || state == 2 || state == 3 || state == 4 || state == 5) {
            return 1;
        }
        return state != 8 ? 2 : 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController
    protected int convertLdwState(int state) {
        if (state == 4 || state == 5) {
            return 2;
        }
        return state != 6 ? 1 : 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController
    protected int convertRctaState(int state) {
        if (state != 0) {
            if (state != 1) {
                return state != 2 ? 1 : 4;
            }
            return 3;
        }
        return 5;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController
    protected int convertSlaState(int state) {
        if (state == 1 || state == 2 || state == 3 || state == 4) {
            return 1;
        }
        return (state == 6 || state == 7) ? 2 : 0;
    }

    public ScuOldController(Car carClient) {
        super(carClient);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.BaseCarController
    public void handleEventsUpdate(CarPropertyValue<?> value) {
        if (value.getPropertyId() == 557852202) {
            handleSideReverseWarning(((Integer) getValue(value)).intValue());
        } else {
            super.handleEventsUpdate(value);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getRctaState() {
        int i;
        try {
            try {
                i = getIntProperty(557852202);
            } catch (Exception e) {
                LogUtils.e("ScuController", "getMediaSw: " + e.getMessage(), false);
                i = 1;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getSideReversingWarning();
        }
        return convertRctaState(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setRctaState(boolean enable) {
        try {
            this.mCarManager.setSideReversingWarning(enable ? 1 : 0);
            if (this.mIsMainProcess) {
                this.mDataSync.setSideReversingWarning(enable);
            }
        } catch (Exception e) {
            LogUtils.e("ScuController", "setRctaState: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setLdwState(boolean enable) {
        try {
            this.mCarManager.setLaneDepartureWarning(enable ? 1 : 0);
            if (this.mIsMainProcess) {
                this.mDataSync.setLdwSw(enable);
            }
        } catch (Exception e) {
            LogUtils.e("ScuController", "setLdwState: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setBsdState(boolean enable) {
        try {
            this.mCarManager.setBlindAreaDetectionWarning(enable ? 1 : 0);
            if (this.mIsMainProcess) {
                this.mDataSync.setBsdSw(enable);
            }
        } catch (Exception e) {
            LogUtils.e("ScuController", "setBsdState: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getDowState() {
        return this.mDataSync.getDowSw() ? 1 : 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setDowSw(boolean enable, boolean needSp) {
        try {
            this.mCarManager.setDoorOpenWarning(enable ? 1 : 0);
            if (needSp && this.mIsMainProcess) {
                this.mDataSync.setDowSw(enable);
            }
        } catch (Exception e) {
            LogUtils.e("ScuController", "setDowSw: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setLssMode(int mode) {
        super.setLssMode(mode);
        saveLssState(mode);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getLccState() {
        return this.mDataSync.getLccSw() ? 1 : 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setLccState(final boolean enable, boolean needSave) {
        int i = enable ? 1 : 0;
        try {
            if (this.mIsMainProcess) {
                this.mDataSync.setLccSw(enable, needSave);
            }
            this.mCarManager.setLaneAlignmentAssist(i);
            this.mCarManager.setLaneChangeAssist(i);
            if (needSave) {
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$ScuOldController$5RRECAuEA__mLgXgHnrOfprkXMY
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScuOldController.this.lambda$setLccState$0$ScuOldController(enable);
                    }
                });
            }
        } catch (Exception e) {
            LogUtils.e("ScuController", "setLccState: " + e.getMessage(), false);
        }
    }

    public /* synthetic */ void lambda$setLccState$0$ScuOldController(final boolean enable) {
        handleSignal(Integer.valueOf(enable ? 1 : 0), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$rCotx7YN3eSrSMvJl7EsAMtmls8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((IScuController.Callback) obj).onLccStateChanged(((Integer) obj2).intValue());
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getAlcState() {
        return this.mDataSync.getAlcSw() ? 1 : 0;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setAlcState(final boolean enable, boolean needSave) {
        int i = enable ? 1 : 0;
        try {
            if (this.mIsMainProcess) {
                this.mDataSync.setAlcSw(enable, needSave);
            }
            this.mCarManager.setAssLineChanged(i);
            if (needSave) {
                ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$ScuOldController$zAZIqycVTqPCUg2XsL08E_mkGSI
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScuOldController.this.lambda$setAlcState$1$ScuOldController(enable);
                    }
                });
            }
        } catch (Exception e) {
            LogUtils.e("ScuController", "setAlcState: " + e.getMessage(), false);
        }
    }

    public /* synthetic */ void lambda$setAlcState$1$ScuOldController(final boolean enable) {
        handleSignal(Integer.valueOf(enable ? 1 : 0), new BiConsumer() { // from class: com.xiaopeng.carcontrol.carmanager.impl.oldarch.-$$Lambda$oiDnkgTtDegHsU11ifY61jsgjkA
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((IScuController.Callback) obj).onLcsStateChanged(((Integer) obj2).intValue());
            }
        });
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public void setIslcState(boolean enable) {
        try {
            this.mCarManager.setIntelligentSpeedLimit(enable ? 1 : 0);
            if (this.mIsMainProcess && this.mCarConfig.isSupportIslc()) {
                this.mDataSync.setIslcSw(enable);
            }
        } catch (Exception e) {
            LogUtils.e("ScuController", "setIslcState: " + e.getMessage(), false);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getSlaState() {
        int i;
        try {
            try {
                i = getIntProperty(557852173);
            } catch (Exception e) {
                LogUtils.e("ScuController", "getSlaState failed: " + e.getMessage(), false);
                i = 6;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getIntelligentSpeedLimit();
        }
        return convertSlaState(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getIslaState() {
        int i;
        try {
            try {
                i = getIntProperty(557852378);
            } catch (Exception e) {
                LogUtils.e("ScuController", "getIslaState failed: " + e.getMessage(), false);
                i = 1;
            }
        } catch (Exception unused) {
            i = this.mCarManager.getSpeedLimitSwitchState();
        }
        return convertIslaState(i);
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getIslaSpdRange() {
        int islaSpdRange = this.mIsMainProcess ? this.mDataSync.getIslaSpdRange() : 2;
        LogUtils.i("ScuController", "getIslaSpdRange: " + islaSpdRange, false);
        return islaSpdRange;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.impl.ScuController, com.xiaopeng.carcontrol.carmanager.controller.IScuController
    public int getIslaConfirmMode() {
        boolean islaConfirmMode = this.mIsMainProcess ? this.mDataSync.getIslaConfirmMode() : true;
        LogUtils.i("ScuController", "getIslaConfirmMode: " + islaConfirmMode, false);
        return islaConfirmMode ? 2 : 1;
    }

    private void handleSideReverseWarning(int state) {
        int convertRctaState = convertRctaState(state);
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((IScuController.Callback) it.next()).onRctaSwChanged(convertRctaState);
            }
        }
    }
}
