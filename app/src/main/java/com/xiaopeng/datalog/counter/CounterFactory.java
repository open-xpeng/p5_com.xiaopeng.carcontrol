package com.xiaopeng.datalog.counter;

import android.content.Context;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounter;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounterFactory;

/* loaded from: classes2.dex */
public class CounterFactory implements ICounterFactory {
    private static final long ONE_DAY_MILLS = 86400000;
    private static final long ONE_HOUR_MILLS = 3600000;

    private CounterFactory() {
    }

    /* loaded from: classes2.dex */
    private static final class Holder {
        private static final CounterFactory INSTANCE = new CounterFactory();

        private Holder() {
        }
    }

    public static CounterFactory getInstance() {
        return Holder.INSTANCE;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounterFactory
    public ICounter createHourlyCounter(Context context, String str) {
        return new CounterImpl(context, str, 3600000L);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounterFactory
    public ICounter createDailyCounter(Context context, String str) {
        return new CounterImpl(context, str, 86400000L);
    }
}
