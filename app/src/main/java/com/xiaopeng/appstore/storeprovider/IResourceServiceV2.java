package com.xiaopeng.appstore.storeprovider;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.appstore.storeprovider.IAssembleListener;
import java.util.List;

/* loaded from: classes.dex */
public interface IResourceServiceV2 extends IInterface {

    /* loaded from: classes.dex */
    public static class Default implements IResourceServiceV2 {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
        public AssembleResult assembleAction(String str, SimpleRequest simpleRequest) throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
        public AssembleResult assembleEnqueue(String str, EnqueueRequest enqueueRequest) throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
        public List<AssembleInfo> getAssembleInfoList(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
        public ResourceContainer query(String str, ResourceRequest resourceRequest) throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
        public void registerAssembleListener(int i, String str, IAssembleListener iAssembleListener) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
        public void unregisterAssembleListener(IAssembleListener iAssembleListener) throws RemoteException {
        }
    }

    AssembleResult assembleAction(String str, SimpleRequest simpleRequest) throws RemoteException;

    AssembleResult assembleEnqueue(String str, EnqueueRequest enqueueRequest) throws RemoteException;

    List<AssembleInfo> getAssembleInfoList(int i, String str) throws RemoteException;

    ResourceContainer query(String str, ResourceRequest resourceRequest) throws RemoteException;

    void registerAssembleListener(int i, String str, IAssembleListener iAssembleListener) throws RemoteException;

    void unregisterAssembleListener(IAssembleListener iAssembleListener) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IResourceServiceV2 {
        private static final String DESCRIPTOR = "com.xiaopeng.appstore.storeprovider.IResourceServiceV2";
        static final int TRANSACTION_assembleAction = 2;
        static final int TRANSACTION_assembleEnqueue = 1;
        static final int TRANSACTION_getAssembleInfoList = 3;
        static final int TRANSACTION_query = 6;
        static final int TRANSACTION_registerAssembleListener = 4;
        static final int TRANSACTION_unregisterAssembleListener = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IResourceServiceV2 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResourceServiceV2)) {
                return (IResourceServiceV2) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    AssembleResult assembleEnqueue = assembleEnqueue(parcel.readString(), parcel.readInt() != 0 ? EnqueueRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (assembleEnqueue != null) {
                        parcel2.writeInt(1);
                        assembleEnqueue.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    AssembleResult assembleAction = assembleAction(parcel.readString(), parcel.readInt() != 0 ? SimpleRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (assembleAction != null) {
                        parcel2.writeInt(1);
                        assembleAction.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<AssembleInfo> assembleInfoList = getAssembleInfoList(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeTypedList(assembleInfoList);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerAssembleListener(parcel.readInt(), parcel.readString(), IAssembleListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterAssembleListener(IAssembleListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    ResourceContainer query = query(parcel.readString(), parcel.readInt() != 0 ? ResourceRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (query != null) {
                        parcel2.writeInt(1);
                        query.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements IResourceServiceV2 {
            public static IResourceServiceV2 sDefaultImpl;
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

            @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
            public AssembleResult assembleEnqueue(String str, EnqueueRequest enqueueRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (enqueueRequest != null) {
                        obtain.writeInt(1);
                        enqueueRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().assembleEnqueue(str, enqueueRequest);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? AssembleResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
            public AssembleResult assembleAction(String str, SimpleRequest simpleRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (simpleRequest != null) {
                        obtain.writeInt(1);
                        simpleRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().assembleAction(str, simpleRequest);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? AssembleResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
            public List<AssembleInfo> getAssembleInfoList(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAssembleInfoList(i, str);
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AssembleInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
            public void registerAssembleListener(int i, String str, IAssembleListener iAssembleListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iAssembleListener != null ? iAssembleListener.asBinder() : null);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerAssembleListener(i, str, iAssembleListener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
            public void unregisterAssembleListener(IAssembleListener iAssembleListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iAssembleListener != null ? iAssembleListener.asBinder() : null);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAssembleListener(iAssembleListener);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.IResourceServiceV2
            public ResourceContainer query(String str, ResourceRequest resourceRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (resourceRequest != null) {
                        obtain.writeInt(1);
                        resourceRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().query(str, resourceRequest);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? ResourceContainer.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IResourceServiceV2 iResourceServiceV2) {
            if (Proxy.sDefaultImpl == null) {
                if (iResourceServiceV2 != null) {
                    Proxy.sDefaultImpl = iResourceServiceV2;
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("setDefaultImpl() called twice");
        }

        public static IResourceServiceV2 getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
