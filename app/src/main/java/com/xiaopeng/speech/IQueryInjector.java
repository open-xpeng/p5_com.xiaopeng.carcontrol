package com.xiaopeng.speech;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.speech.IRemoteDataSensor;
import com.xiaopeng.speech.common.bean.Value;

/* loaded from: classes2.dex */
public interface IQueryInjector extends IInterface {
    boolean isQueryInject(String str) throws RemoteException;

    Value queryApiRouterData(String str, String str2) throws RemoteException;

    Value queryData(String str, String str2) throws RemoteException;

    void registerDataSensor(String[] strArr, IRemoteDataSensor iRemoteDataSensor) throws RemoteException;

    void unRegisterDataSensor(String[] strArr) throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IQueryInjector {
        private static final String DESCRIPTOR = "com.xiaopeng.speech.IQueryInjector";
        static final int TRANSACTION_isQueryInject = 4;
        static final int TRANSACTION_queryApiRouterData = 5;
        static final int TRANSACTION_queryData = 3;
        static final int TRANSACTION_registerDataSensor = 1;
        static final int TRANSACTION_unRegisterDataSensor = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IQueryInjector asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IQueryInjector)) {
                return (IQueryInjector) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                registerDataSensor(parcel.createStringArray(), IRemoteDataSensor.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                unRegisterDataSensor(parcel.createStringArray());
                parcel2.writeNoException();
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                Value queryData = queryData(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (queryData != null) {
                    parcel2.writeInt(1);
                    queryData.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            } else if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean isQueryInject = isQueryInject(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(isQueryInject ? 1 : 0);
                return true;
            } else if (i != 5) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel.enforceInterface(DESCRIPTOR);
                Value queryApiRouterData = queryApiRouterData(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                if (queryApiRouterData != null) {
                    parcel2.writeInt(1);
                    queryApiRouterData.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            }
        }

        /* loaded from: classes2.dex */
        private static class Proxy implements IQueryInjector {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaopeng.speech.IQueryInjector
            public void registerDataSensor(String[] strArr, IRemoteDataSensor iRemoteDataSensor) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongBinder(iRemoteDataSensor != null ? iRemoteDataSensor.asBinder() : null);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.IQueryInjector
            public void unRegisterDataSensor(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.IQueryInjector
            public Value queryData(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? Value.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.IQueryInjector
            public boolean isQueryInject(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.IQueryInjector
            public Value queryApiRouterData(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? Value.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
