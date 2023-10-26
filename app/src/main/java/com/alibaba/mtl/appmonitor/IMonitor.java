package com.alibaba.mtl.appmonitor;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import java.util.Map;

/* loaded from: classes.dex */
public interface IMonitor extends IInterface {
    boolean alarm_checkSampled(String str, String str2) throws RemoteException;

    void alarm_commitFail1(String str, String str2, String str3, String str4, Map map) throws RemoteException;

    void alarm_commitFail2(String str, String str2, String str3, String str4, String str5, Map map) throws RemoteException;

    void alarm_commitSuccess1(String str, String str2, Map map) throws RemoteException;

    void alarm_commitSuccess2(String str, String str2, String str3, Map map) throws RemoteException;

    void alarm_setSampling(int i) throws RemoteException;

    void alarm_setStatisticsInterval(int i) throws RemoteException;

    boolean counter_checkSampled(String str, String str2) throws RemoteException;

    void counter_commit1(String str, String str2, double d, Map map) throws RemoteException;

    void counter_commit2(String str, String str2, String str3, double d, Map map) throws RemoteException;

    void counter_setSampling(int i) throws RemoteException;

    void counter_setStatisticsInterval(int i) throws RemoteException;

    void destroy() throws RemoteException;

    void enableLog(boolean z) throws RemoteException;

    void init() throws RemoteException;

    boolean offlinecounter_checkSampled(String str, String str2) throws RemoteException;

    void offlinecounter_commit(String str, String str2, double d) throws RemoteException;

    void offlinecounter_setSampling(int i) throws RemoteException;

    void offlinecounter_setStatisticsInterval(int i) throws RemoteException;

    void register1(String str, String str2, MeasureSet measureSet) throws RemoteException;

    void register2(String str, String str2, MeasureSet measureSet, boolean z) throws RemoteException;

    void register3(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet) throws RemoteException;

    void register4(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) throws RemoteException;

    void setChannel(String str) throws RemoteException;

    void setRequestAuthInfo(boolean z, String str, String str2, String str3) throws RemoteException;

    void setSampling(int i) throws RemoteException;

    void setStatisticsInterval1(int i) throws RemoteException;

    void setStatisticsInterval2(int i, int i2) throws RemoteException;

    void stat_begin(String str, String str2, String str3) throws RemoteException;

    boolean stat_checkSampled(String str, String str2) throws RemoteException;

    void stat_commit1(String str, String str2, double d, Map map) throws RemoteException;

    void stat_commit2(String str, String str2, DimensionValueSet dimensionValueSet, double d, Map map) throws RemoteException;

    void stat_commit3(String str, String str2, DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet, Map map) throws RemoteException;

    void stat_end(String str, String str2, String str3) throws RemoteException;

    void stat_setSampling(int i) throws RemoteException;

    void stat_setStatisticsInterval(int i) throws RemoteException;

    void transaction_begin(Transaction transaction, String str) throws RemoteException;

    void transaction_end(Transaction transaction, String str) throws RemoteException;

    void triggerUpload() throws RemoteException;

    void turnOffRealTimeDebug() throws RemoteException;

    void turnOnRealTimeDebug(Map map) throws RemoteException;

    void updateMeasure(String str, String str2, String str3, double d, double d2, double d3) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IMonitor {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.alibaba.mtl.appmonitor.IMonitor");
        }

        public static IMonitor asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.alibaba.mtl.appmonitor.IMonitor");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMonitor)) {
                return (IMonitor) queryLocalInterface;
            }
            return new a(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString("com.alibaba.mtl.appmonitor.IMonitor");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    init();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    enableLog(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setRequestAuthInfo(parcel.readInt() != 0, parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setChannel(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    triggerUpload();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setStatisticsInterval1(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    setStatisticsInterval2(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    register1(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? MeasureSet.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    register2(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? MeasureSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    register3(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? MeasureSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? DimensionSet.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    register4(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? MeasureSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? DimensionSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    turnOnRealTimeDebug(parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 14:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    turnOffRealTimeDebug();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    destroy();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    counter_setStatisticsInterval(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    counter_setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 18:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    boolean counter_checkSampled = counter_checkSampled(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(counter_checkSampled ? 1 : 0);
                    return true;
                case 19:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    counter_commit1(parcel.readString(), parcel.readString(), parcel.readDouble(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 20:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    counter_commit2(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readDouble(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 21:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    offlinecounter_setStatisticsInterval(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 22:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    offlinecounter_setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 23:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    boolean offlinecounter_checkSampled = offlinecounter_checkSampled(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(offlinecounter_checkSampled ? 1 : 0);
                    return true;
                case 24:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    offlinecounter_commit(parcel.readString(), parcel.readString(), parcel.readDouble());
                    parcel2.writeNoException();
                    return true;
                case 25:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_setStatisticsInterval(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 26:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 27:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    boolean alarm_checkSampled = alarm_checkSampled(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(alarm_checkSampled ? 1 : 0);
                    return true;
                case 28:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_commitSuccess1(parcel.readString(), parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 29:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_commitSuccess2(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 30:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_commitFail1(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 31:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    alarm_commitFail2(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 32:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_begin(parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 33:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_end(parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 34:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_setStatisticsInterval(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 35:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_setSampling(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 36:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    boolean stat_checkSampled = stat_checkSampled(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(stat_checkSampled ? 1 : 0);
                    return true;
                case 37:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_commit1(parcel.readString(), parcel.readString(), parcel.readDouble(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 38:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_commit2(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? DimensionValueSet.CREATOR.createFromParcel(parcel) : null, parcel.readDouble(), parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 39:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    stat_commit3(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? DimensionValueSet.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? MeasureValueSet.CREATOR.createFromParcel(parcel) : null, parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    return true;
                case 40:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    transaction_begin(parcel.readInt() != 0 ? Transaction.CREATOR.createFromParcel(parcel) : null, parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 41:
                    parcel.enforceInterface("com.alibaba.mtl.appmonitor.IMonitor");
                    transaction_end(parcel.readInt() != 0 ? Transaction.CREATOR.createFromParcel(parcel) : null, parcel.readString());
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* loaded from: classes.dex */
        private static class a implements IMonitor {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void init() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void enableLog(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setRequestAuthInfo(boolean z, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setChannel(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void triggerUpload() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setStatisticsInterval1(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void setStatisticsInterval2(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void register1(String str, String str2, MeasureSet measureSet) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (measureSet != null) {
                        obtain.writeInt(1);
                        measureSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void register2(String str, String str2, MeasureSet measureSet, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    int i = 1;
                    if (measureSet != null) {
                        obtain.writeInt(1);
                        measureSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void register3(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (measureSet != null) {
                        obtain.writeInt(1);
                        measureSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (dimensionSet != null) {
                        obtain.writeInt(1);
                        dimensionSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void register4(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    int i = 1;
                    if (measureSet != null) {
                        obtain.writeInt(1);
                        measureSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (dimensionSet != null) {
                        obtain.writeInt(1);
                        dimensionSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void turnOnRealTimeDebug(Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeMap(map);
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void turnOffRealTimeDebug() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    this.a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void destroy() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    this.a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void counter_setStatisticsInterval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void counter_setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public boolean counter_checkSampled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void counter_commit1(String str, String str2, double d, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeDouble(d);
                    obtain.writeMap(map);
                    this.a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void counter_commit2(String str, String str2, String str3, double d, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeDouble(d);
                    obtain.writeMap(map);
                    this.a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void offlinecounter_setStatisticsInterval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void offlinecounter_setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public boolean offlinecounter_checkSampled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void offlinecounter_commit(String str, String str2, double d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeDouble(d);
                    this.a.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_setStatisticsInterval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public boolean alarm_checkSampled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_commitSuccess1(String str, String str2, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeMap(map);
                    this.a.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_commitSuccess2(String str, String str2, String str3, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeMap(map);
                    this.a.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_commitFail1(String str, String str2, String str3, String str4, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeMap(map);
                    this.a.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void alarm_commitFail2(String str, String str2, String str3, String str4, String str5, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeMap(map);
                    this.a.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_begin(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.a.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_end(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.a.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_setStatisticsInterval(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_setSampling(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeInt(i);
                    this.a.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public boolean stat_checkSampled(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_commit1(String str, String str2, double d, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeDouble(d);
                    obtain.writeMap(map);
                    this.a.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_commit2(String str, String str2, DimensionValueSet dimensionValueSet, double d, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (dimensionValueSet != null) {
                        obtain.writeInt(1);
                        dimensionValueSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeDouble(d);
                    obtain.writeMap(map);
                    this.a.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void stat_commit3(String str, String str2, DimensionValueSet dimensionValueSet, MeasureValueSet measureValueSet, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (dimensionValueSet != null) {
                        obtain.writeInt(1);
                        dimensionValueSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (measureValueSet != null) {
                        obtain.writeInt(1);
                        measureValueSet.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeMap(map);
                    this.a.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void transaction_begin(Transaction transaction, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    if (transaction != null) {
                        obtain.writeInt(1);
                        transaction.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    this.a.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void transaction_end(Transaction transaction, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.alibaba.mtl.appmonitor.IMonitor");
                    if (transaction != null) {
                        obtain.writeInt(1);
                        transaction.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    this.a.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.alibaba.mtl.appmonitor.IMonitor
            public void updateMeasure(String str, String str2, String str3, double d, double d2, double d3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeDouble(d);
                    obtain.writeDouble(d2);
                    obtain.writeDouble(d3);
                    this.a.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
