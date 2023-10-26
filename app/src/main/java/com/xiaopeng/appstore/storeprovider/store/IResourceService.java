package com.xiaopeng.appstore.storeprovider.store;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.appstore.storeprovider.store.IRMDownloadCallback;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceBean;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceContainerBean;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo;
import java.util.List;

/* loaded from: classes.dex */
public interface IResourceService extends IInterface {

    /* loaded from: classes.dex */
    public static class Default implements IResourceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean cancelDownload(String str) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean cancelResDownload(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public long enqueue(String str, String str2) throws RemoteException {
            return 0L;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public int fetchDownloadStatusById(long j) throws RemoteException {
            return 0;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public int fetchDownloadStatusByUrl(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public int getDownloadStatusById(long j) throws RemoteException {
            return 0;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public int getDownloadStatusByUrl(String str) throws RemoteException {
            return 0;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public long getDownloadedBytesById(long j) throws RemoteException {
            return 0L;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public long getDownloadedBytesByUrl(String str) throws RemoteException {
            return 0L;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public String getLocalFilePath(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public long getTotalBytesById(long j) throws RemoteException {
            return 0L;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public long getTotalBytesByUrl(String str) throws RemoteException {
            return 0L;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean pause(String str) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean pauseDownload(String str) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean pauseResDownload(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public List<ResourceDownloadInfo> queryAllInfo() throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public List<ResourceDownloadInfo> queryDownloadInfo(String[] strArr) throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public ResourceContainerBean queryResourceData(String str) throws RemoteException {
            return null;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public void registerDownloadListener(IRMDownloadCallback iRMDownloadCallback) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean remove(String str) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public void removeLocalDataById(long j) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public void removeLocalDataByUrl(String str) throws RemoteException {
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean resume(String str) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean resumeDownload(String str) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean resumeResDownload(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public boolean start(ResourceBean resourceBean) throws RemoteException {
            return false;
        }

        @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
        public void unregisterDownloadListener(IRMDownloadCallback iRMDownloadCallback) throws RemoteException {
        }
    }

    void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException;

    boolean cancelDownload(String str) throws RemoteException;

    boolean cancelResDownload(String str, String str2) throws RemoteException;

    long enqueue(String str, String str2) throws RemoteException;

    int fetchDownloadStatusById(long j) throws RemoteException;

    int fetchDownloadStatusByUrl(String str) throws RemoteException;

    int getDownloadStatusById(long j) throws RemoteException;

    int getDownloadStatusByUrl(String str) throws RemoteException;

    long getDownloadedBytesById(long j) throws RemoteException;

    long getDownloadedBytesByUrl(String str) throws RemoteException;

    String getLocalFilePath(String str) throws RemoteException;

    long getTotalBytesById(long j) throws RemoteException;

    long getTotalBytesByUrl(String str) throws RemoteException;

    boolean pause(String str) throws RemoteException;

    boolean pauseDownload(String str) throws RemoteException;

    boolean pauseResDownload(String str, String str2) throws RemoteException;

    List<ResourceDownloadInfo> queryAllInfo() throws RemoteException;

    List<ResourceDownloadInfo> queryDownloadInfo(String[] strArr) throws RemoteException;

    ResourceContainerBean queryResourceData(String str) throws RemoteException;

    void registerDownloadListener(IRMDownloadCallback iRMDownloadCallback) throws RemoteException;

    boolean remove(String str) throws RemoteException;

    void removeLocalDataById(long j) throws RemoteException;

    void removeLocalDataByUrl(String str) throws RemoteException;

    boolean resume(String str) throws RemoteException;

    boolean resumeDownload(String str) throws RemoteException;

    boolean resumeResDownload(String str, String str2) throws RemoteException;

    boolean start(ResourceBean resourceBean) throws RemoteException;

    void unregisterDownloadListener(IRMDownloadCallback iRMDownloadCallback) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IResourceService {
        private static final String DESCRIPTOR = "com.xiaopeng.appstore.storeprovider.store.IResourceService";
        static final int TRANSACTION_basicTypes = 1;
        static final int TRANSACTION_cancelDownload = 14;
        static final int TRANSACTION_cancelResDownload = 11;
        static final int TRANSACTION_enqueue = 16;
        static final int TRANSACTION_fetchDownloadStatusById = 17;
        static final int TRANSACTION_fetchDownloadStatusByUrl = 18;
        static final int TRANSACTION_getDownloadStatusById = 20;
        static final int TRANSACTION_getDownloadStatusByUrl = 19;
        static final int TRANSACTION_getDownloadedBytesById = 21;
        static final int TRANSACTION_getDownloadedBytesByUrl = 22;
        static final int TRANSACTION_getLocalFilePath = 28;
        static final int TRANSACTION_getTotalBytesById = 23;
        static final int TRANSACTION_getTotalBytesByUrl = 24;
        static final int TRANSACTION_pause = 9;
        static final int TRANSACTION_pauseDownload = 13;
        static final int TRANSACTION_pauseResDownload = 12;
        static final int TRANSACTION_queryAllInfo = 25;
        static final int TRANSACTION_queryDownloadInfo = 3;
        static final int TRANSACTION_queryResourceData = 2;
        static final int TRANSACTION_registerDownloadListener = 4;
        static final int TRANSACTION_remove = 8;
        static final int TRANSACTION_removeLocalDataById = 27;
        static final int TRANSACTION_removeLocalDataByUrl = 26;
        static final int TRANSACTION_resume = 7;
        static final int TRANSACTION_resumeDownload = 15;
        static final int TRANSACTION_resumeResDownload = 10;
        static final int TRANSACTION_start = 6;
        static final int TRANSACTION_unregisterDownloadListener = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IResourceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResourceService)) {
                return (IResourceService) queryLocalInterface;
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
                    basicTypes(parcel.readInt(), parcel.readLong(), parcel.readInt() != 0, parcel.readFloat(), parcel.readDouble(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    ResourceContainerBean queryResourceData = queryResourceData(parcel.readString());
                    parcel2.writeNoException();
                    if (queryResourceData != null) {
                        parcel2.writeInt(1);
                        queryResourceData.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<ResourceDownloadInfo> queryDownloadInfo = queryDownloadInfo(parcel.createStringArray());
                    parcel2.writeNoException();
                    parcel2.writeTypedList(queryDownloadInfo);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerDownloadListener(IRMDownloadCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterDownloadListener(IRMDownloadCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean start = start(parcel.readInt() != 0 ? ResourceBean.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(start ? 1 : 0);
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean resume = resume(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(resume ? 1 : 0);
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean remove = remove(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(remove ? 1 : 0);
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean pause = pause(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(pause ? 1 : 0);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean resumeResDownload = resumeResDownload(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(resumeResDownload ? 1 : 0);
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean cancelResDownload = cancelResDownload(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(cancelResDownload ? 1 : 0);
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean pauseResDownload = pauseResDownload(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(pauseResDownload ? 1 : 0);
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean pauseDownload = pauseDownload(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(pauseDownload ? 1 : 0);
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean cancelDownload = cancelDownload(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(cancelDownload ? 1 : 0);
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean resumeDownload = resumeDownload(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(resumeDownload ? 1 : 0);
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    long enqueue = enqueue(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeLong(enqueue);
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    int fetchDownloadStatusById = fetchDownloadStatusById(parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeInt(fetchDownloadStatusById);
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    int fetchDownloadStatusByUrl = fetchDownloadStatusByUrl(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(fetchDownloadStatusByUrl);
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    int downloadStatusByUrl = getDownloadStatusByUrl(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(downloadStatusByUrl);
                    return true;
                case 20:
                    parcel.enforceInterface(DESCRIPTOR);
                    int downloadStatusById = getDownloadStatusById(parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeInt(downloadStatusById);
                    return true;
                case 21:
                    parcel.enforceInterface(DESCRIPTOR);
                    long downloadedBytesById = getDownloadedBytesById(parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeLong(downloadedBytesById);
                    return true;
                case 22:
                    parcel.enforceInterface(DESCRIPTOR);
                    long downloadedBytesByUrl = getDownloadedBytesByUrl(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeLong(downloadedBytesByUrl);
                    return true;
                case 23:
                    parcel.enforceInterface(DESCRIPTOR);
                    long totalBytesById = getTotalBytesById(parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeLong(totalBytesById);
                    return true;
                case 24:
                    parcel.enforceInterface(DESCRIPTOR);
                    long totalBytesByUrl = getTotalBytesByUrl(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeLong(totalBytesByUrl);
                    return true;
                case 25:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<ResourceDownloadInfo> queryAllInfo = queryAllInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(queryAllInfo);
                    return true;
                case 26:
                    parcel.enforceInterface(DESCRIPTOR);
                    removeLocalDataByUrl(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 27:
                    parcel.enforceInterface(DESCRIPTOR);
                    removeLocalDataById(parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 28:
                    parcel.enforceInterface(DESCRIPTOR);
                    String localFilePath = getLocalFilePath(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(localFilePath);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements IResourceService {
            public static IResourceService sDefaultImpl;
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

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
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

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public ResourceContainerBean queryResourceData(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryResourceData(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? ResourceContainerBean.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public List<ResourceDownloadInfo> queryDownloadInfo(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryDownloadInfo(strArr);
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ResourceDownloadInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public void registerDownloadListener(IRMDownloadCallback iRMDownloadCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iRMDownloadCallback != null ? iRMDownloadCallback.asBinder() : null);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerDownloadListener(iRMDownloadCallback);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public void unregisterDownloadListener(IRMDownloadCallback iRMDownloadCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iRMDownloadCallback != null ? iRMDownloadCallback.asBinder() : null);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterDownloadListener(iRMDownloadCallback);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean start(ResourceBean resourceBean) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (resourceBean != null) {
                        obtain.writeInt(1);
                        resourceBean.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().start(resourceBean);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean resume(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resume(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean remove(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().remove(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean pause(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().pause(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean resumeResDownload(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resumeResDownload(str, str2);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean cancelResDownload(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelResDownload(str, str2);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean pauseResDownload(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().pauseResDownload(str, str2);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean pauseDownload(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().pauseDownload(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean cancelDownload(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().cancelDownload(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public boolean resumeDownload(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resumeDownload(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public long enqueue(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enqueue(str, str2);
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public int fetchDownloadStatusById(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().fetchDownloadStatusById(j);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public int fetchDownloadStatusByUrl(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().fetchDownloadStatusByUrl(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public int getDownloadStatusByUrl(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDownloadStatusByUrl(str);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public int getDownloadStatusById(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(20, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDownloadStatusById(j);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public long getDownloadedBytesById(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDownloadedBytesById(j);
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public long getDownloadedBytesByUrl(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(22, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDownloadedBytesByUrl(str);
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public long getTotalBytesById(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(23, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTotalBytesById(j);
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public long getTotalBytesByUrl(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(24, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTotalBytesByUrl(str);
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public List<ResourceDownloadInfo> queryAllInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().queryAllInfo();
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ResourceDownloadInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public void removeLocalDataByUrl(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(26, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeLocalDataByUrl(str);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public void removeLocalDataById(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(27, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeLocalDataById(j);
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.appstore.storeprovider.store.IResourceService
            public String getLocalFilePath(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(28, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLocalFilePath(str);
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IResourceService iResourceService) {
            if (Proxy.sDefaultImpl == null) {
                if (iResourceService != null) {
                    Proxy.sDefaultImpl = iResourceService;
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("setDefaultImpl() called twice");
        }

        public static IResourceService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
