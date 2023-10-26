package com.alibaba.mtl.appmonitor;

import android.app.Application;
import android.os.RemoteException;
import com.alibaba.mtl.appmonitor.AppMonitorDelegate;
import com.alibaba.mtl.appmonitor.IMonitor;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.log.e.i;
import java.util.Map;

/* loaded from: classes.dex */
public class Monitor extends IMonitor.Stub {
    private Application b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Monitor(Application application) {
        this.b = application;
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void init() throws RemoteException {
        AppMonitorDelegate.init(this.b);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void destroy() throws RemoteException {
        AppMonitorDelegate.destroy();
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void triggerUpload() throws RemoteException {
        AppMonitorDelegate.triggerUpload();
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setSampling(int i) throws RemoteException {
        AppMonitorDelegate.setSampling(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void enableLog(boolean z) throws RemoteException {
        AppMonitorDelegate.enableLog(z);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setStatisticsInterval2(int i, int i2) throws RemoteException {
        AppMonitorDelegate.setStatisticsInterval(a(i), i2);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setRequestAuthInfo(boolean z, String str, String str2, String str3) throws RemoteException {
        AppMonitorDelegate.setRequestAuthInfo(z, str, str2, str3);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setChannel(String str) throws RemoteException {
        AppMonitorDelegate.setChannel(str);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void turnOnRealTimeDebug(Map map) throws RemoteException {
        AppMonitorDelegate.turnOnRealTimeDebug(map);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void turnOffRealTimeDebug() throws RemoteException {
        AppMonitorDelegate.turnOffRealTimeDebug();
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void counter_setStatisticsInterval(int i) throws RemoteException {
        AppMonitorDelegate.Counter.setStatisticsInterval(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void counter_setSampling(int i) throws RemoteException {
        AppMonitorDelegate.Counter.setSampling(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public boolean counter_checkSampled(String str, String str2) throws RemoteException {
        return AppMonitorDelegate.Counter.checkSampled(str, str2);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void counter_commit1(String str, String str2, double d, Map map) throws RemoteException {
        AppMonitorDelegate.Counter.commit(str, str2, d, map);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void counter_commit2(String str, String str2, String str3, double d, Map map) throws RemoteException {
        AppMonitorDelegate.Counter.commit(str, str2, str3, d, map);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_setStatisticsInterval(int i) throws RemoteException {
        AppMonitorDelegate.Alarm.setStatisticsInterval(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_setSampling(int i) throws RemoteException {
        AppMonitorDelegate.Alarm.setSampling(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public boolean alarm_checkSampled(String str, String str2) throws RemoteException {
        return AppMonitorDelegate.Alarm.checkSampled(str, str2);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_commitSuccess1(String str, String str2, Map map) throws RemoteException {
        AppMonitorDelegate.Alarm.commitSuccess(str, str2, map);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_commitSuccess2(String str, String str2, String str3, Map map) throws RemoteException {
        AppMonitorDelegate.Alarm.commitSuccess(str, str2, str3, map);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_commitFail1(String str, String str2, String str3, String str4, Map map) throws RemoteException {
        AppMonitorDelegate.Alarm.commitFail(str, str2, str3, str4, map);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_commitFail2(String str, String str2, String str3, String str4, String str5, Map map) throws RemoteException {
        AppMonitorDelegate.Alarm.commitFail(str, str2, str3, str4, str5, map);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void offlinecounter_setStatisticsInterval(int i) throws RemoteException {
        AppMonitorDelegate.OffLineCounter.setStatisticsInterval(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void offlinecounter_setSampling(int i) throws RemoteException {
        AppMonitorDelegate.OffLineCounter.setSampling(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public boolean offlinecounter_checkSampled(String str, String str2) throws RemoteException {
        return AppMonitorDelegate.OffLineCounter.checkSampled(str, str2);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void offlinecounter_commit(String str, String str2, double d) throws RemoteException {
        AppMonitorDelegate.OffLineCounter.commit(str, str2, d);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setStatisticsInterval1(int i) throws RemoteException {
        AppMonitorDelegate.setStatisticsInterval(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void register1(String str, String str2, MeasureSet measureSet) throws RemoteException {
        AppMonitorDelegate.register(str, str2, measureSet);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void register2(String str, String str2, MeasureSet measureSet, boolean z) throws RemoteException {
        AppMonitorDelegate.register(str, str2, measureSet, z);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void register3(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet) throws RemoteException {
        AppMonitorDelegate.register(str, str2, measureSet, dimensionSet);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void register4(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) throws RemoteException {
        AppMonitorDelegate.register(str, str2, measureSet, dimensionSet, z);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_begin(String str, String str2, String str3) throws RemoteException {
        AppMonitorDelegate.Stat.begin(str, str2, str3);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_end(String str, String str2, String str3) throws RemoteException {
        AppMonitorDelegate.Stat.end(str, str2, str3);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_setStatisticsInterval(int i) throws RemoteException {
        AppMonitorDelegate.Stat.setStatisticsInterval(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_setSampling(int i) throws RemoteException {
        AppMonitorDelegate.Stat.setSampling(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public boolean stat_checkSampled(String str, String str2) throws RemoteException {
        return AppMonitorDelegate.Stat.checkSampled(str, str2);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_commit1(String str, String str2, double d, Map map) throws RemoteException {
        AppMonitorDelegate.Stat.commit(str, str2, d, map);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_commit2(String str, String str2, DimensionValueSet dimensionValueSet, double d, Map map) throws RemoteException {
        AppMonitorDelegate.Stat.commit(str, str2, dimensionValueSet, d, map);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_commit3(String str, String str2, DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet, Map map) throws RemoteException {
        i.a("Monitor", "[stat_commit3]");
        AppMonitorDelegate.Stat.commit(str, str2, dimensionValueSet, measureValueSet, map);
    }

    private f a(int i) {
        return f.a(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void transaction_begin(Transaction transaction, String str) throws RemoteException {
        TransactionDelegate.begin(transaction, str);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void transaction_end(Transaction transaction, String str) throws RemoteException {
        TransactionDelegate.end(transaction, str);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void updateMeasure(String str, String str2, String str3, double d, double d2, double d3) throws RemoteException {
        AppMonitorDelegate.updateMeasure(str, str2, str3, d, d2, d3);
    }
}
