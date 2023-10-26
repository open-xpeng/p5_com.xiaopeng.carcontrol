package com.xiaopeng.lib.framework.netchannelmodule.http.tracing;

import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.utils.LogUtils;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

/* loaded from: classes2.dex */
public class TracingReporter implements Reporter<Span> {
    private static final String TAG = "Tracer";

    /* JADX INFO: Access modifiers changed from: package-private */
    public TracingReporter() {
        Module.register(DataLogModuleEntry.class, new DataLogModuleEntry(GlobalConfig.getApplicationContext()));
    }

    @Override // zipkin2.reporter.Reporter
    public void report(Span span) {
        String span2 = span.toString();
        LogUtils.d(TAG, "report:" + span2);
        IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
        iDataLog.sendStatData(iDataLog.buildStat().setEventName("data_tracing").setProperty("data", "[" + span2 + "]").build());
    }
}
