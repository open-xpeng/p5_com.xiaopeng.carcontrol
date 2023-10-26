package com.xiaopeng.appstore.storeprovider.store;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo;

/* loaded from: classes.dex */
public interface IRMDownloadCallback extends IInterface {

    /* loaded from: classes.dex */
    public static class Default implements IRMDownloadCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
        public void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
        public void onDownloadCallback(int i, ResourceDownloadInfo resourceDownloadInfo) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
        public void onMenuOpenCallback(String str) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
        public void unbindService() throws RemoteException {
        }
    }

    void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException;

    void onDownloadCallback(int i, ResourceDownloadInfo resourceDownloadInfo) throws RemoteException;

    void onMenuOpenCallback(String str) throws RemoteException;

    void unbindService() throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IRMDownloadCallback {
        private static final String DESCRIPTOR = "com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback";
        static final int TRANSACTION_basicTypes = 1;
        static final int TRANSACTION_onDownloadCallback = 2;
        static final int TRANSACTION_onMenuOpenCallback = 3;
        static final int TRANSACTION_unbindService = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRMDownloadCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRMDownloadCallback)) {
                return (IRMDownloadCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                basicTypes(parcel.readInt(), parcel.readLong(), parcel.readInt() != 0, parcel.readFloat(), parcel.readDouble(), parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                onDownloadCallback(parcel.readInt(), parcel.readInt() != 0 ? ResourceDownloadInfo.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                onMenuOpenCallback(parcel.readString());
                parcel2.writeNoException();
                return true;
            } else if (i != 4) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel.enforceInterface(DESCRIPTOR);
                unbindService();
                parcel2.writeNoException();
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements IRMDownloadCallback {
            public static IRMDownloadCallback sDefaultImpl;
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

            @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
            public void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeFloat(f);
                    obtain.writeDouble(d);
                    obtain.writeString(str);
                    try {
                        if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().basicTypes(i, j, z, f, d, str);
                            obtain2.recycle();
                            obtain.recycle();
                            return;
                        }
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        th = th;
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
            public void onDownloadCallback(int i, ResourceDownloadInfo resourceDownloadInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (resourceDownloadInfo != null) {
                        obtain.writeInt(1);
                        resourceDownloadInfo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDownloadCallback(i, resourceDownloadInfo);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
            public void onMenuOpenCallback(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMenuOpenCallback(str);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback
            public void unbindService() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unbindService();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRMDownloadCallback iRMDownloadCallback) {
            if (Proxy.sDefaultImpl == null) {
                if (iRMDownloadCallback != null) {
                    Proxy.sDefaultImpl = iRMDownloadCallback;
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("setDefaultImpl() called twice");
        }

        public static IRMDownloadCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
