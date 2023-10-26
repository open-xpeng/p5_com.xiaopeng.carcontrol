package com.xiaopeng.carcontrol.viewmodel.endurance;

/* loaded from: classes2.dex */
abstract class HvacConsumptionTable {
    static final String TAG = "HvacConsumptionTable";

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int getHvacStableConsumption(boolean isInnerCircle, float targetTemp, float outerTemp);

    /* loaded from: classes2.dex */
    private static final class SingleHolder {
        private static final HvacConsumptionTable INSTANCE = createInstance();

        private SingleHolder() {
        }

        private static HvacConsumptionTable createInstance() {
            return new D55HvacConsumptionTable();
        }
    }

    public static HvacConsumptionTable getInstance() {
        return SingleHolder.INSTANCE;
    }
}
