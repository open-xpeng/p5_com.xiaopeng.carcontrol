package com.xiaopeng.speech;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.speech.IActorBridge;
import com.xiaopeng.speech.ICarSystemProperty;
import com.xiaopeng.speech.IQueryInjector;
import com.xiaopeng.speech.ISoundLockState;
import com.xiaopeng.speech.ISpeechState;
import com.xiaopeng.speech.IWindowEngine;
import com.xiaopeng.speech.asr.IRecognizer;
import com.xiaopeng.speech.coreapi.IASREngine;
import com.xiaopeng.speech.coreapi.IAgent;
import com.xiaopeng.speech.coreapi.IAppMgr;
import com.xiaopeng.speech.coreapi.IHotwordEngine;
import com.xiaopeng.speech.coreapi.IRecordEngine;
import com.xiaopeng.speech.coreapi.ISubscriber;
import com.xiaopeng.speech.coreapi.ITTSEngine;
import com.xiaopeng.speech.coreapi.IVADEngine;
import com.xiaopeng.speech.coreapi.IWakeupEngine;

/* loaded from: classes2.dex */
public interface ISpeechEngine extends IInterface {
    IASREngine getASREngine() throws RemoteException;

    IActorBridge getActorBridge() throws RemoteException;

    IAgent getAgent() throws RemoteException;

    IAppMgr getAppMgr() throws RemoteException;

    ICarSystemProperty getCarSystemProperty() throws RemoteException;

    IHotwordEngine getHotwordEngine() throws RemoteException;

    IQueryInjector getQueryInjector() throws RemoteException;

    IRecognizer getRecognizer() throws RemoteException;

    IRecordEngine getRecordEngine() throws RemoteException;

    ISoundLockState getSoundLockState() throws RemoteException;

    ISpeechState getSpeechState() throws RemoteException;

    ISubscriber getSubscriber() throws RemoteException;

    ITTSEngine getTTSEngine() throws RemoteException;

    IVADEngine getVadEngine() throws RemoteException;

    IWakeupEngine getWakeupEngine() throws RemoteException;

    IWindowEngine getWindowEngine() throws RemoteException;

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements ISpeechEngine {
        private static final String DESCRIPTOR = "com.xiaopeng.speech.ISpeechEngine";
        static final int TRANSACTION_getASREngine = 9;
        static final int TRANSACTION_getActorBridge = 1;
        static final int TRANSACTION_getAgent = 5;
        static final int TRANSACTION_getAppMgr = 6;
        static final int TRANSACTION_getCarSystemProperty = 15;
        static final int TRANSACTION_getHotwordEngine = 14;
        static final int TRANSACTION_getQueryInjector = 10;
        static final int TRANSACTION_getRecognizer = 13;
        static final int TRANSACTION_getRecordEngine = 11;
        static final int TRANSACTION_getSoundLockState = 8;
        static final int TRANSACTION_getSpeechState = 7;
        static final int TRANSACTION_getSubscriber = 2;
        static final int TRANSACTION_getTTSEngine = 3;
        static final int TRANSACTION_getVadEngine = 16;
        static final int TRANSACTION_getWakeupEngine = 4;
        static final int TRANSACTION_getWindowEngine = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISpeechEngine asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpeechEngine)) {
                return (ISpeechEngine) queryLocalInterface;
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
                    IActorBridge actorBridge = getActorBridge();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(actorBridge != null ? actorBridge.asBinder() : null);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    ISubscriber subscriber = getSubscriber();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(subscriber != null ? subscriber.asBinder() : null);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    ITTSEngine tTSEngine = getTTSEngine();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(tTSEngine != null ? tTSEngine.asBinder() : null);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    IWakeupEngine wakeupEngine = getWakeupEngine();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(wakeupEngine != null ? wakeupEngine.asBinder() : null);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    IAgent agent = getAgent();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(agent != null ? agent.asBinder() : null);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    IAppMgr appMgr = getAppMgr();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(appMgr != null ? appMgr.asBinder() : null);
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    ISpeechState speechState = getSpeechState();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(speechState != null ? speechState.asBinder() : null);
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    ISoundLockState soundLockState = getSoundLockState();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(soundLockState != null ? soundLockState.asBinder() : null);
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    IASREngine aSREngine = getASREngine();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(aSREngine != null ? aSREngine.asBinder() : null);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    IQueryInjector queryInjector = getQueryInjector();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(queryInjector != null ? queryInjector.asBinder() : null);
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    IRecordEngine recordEngine = getRecordEngine();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(recordEngine != null ? recordEngine.asBinder() : null);
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    IWindowEngine windowEngine = getWindowEngine();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(windowEngine != null ? windowEngine.asBinder() : null);
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    IRecognizer recognizer = getRecognizer();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(recognizer != null ? recognizer.asBinder() : null);
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    IHotwordEngine hotwordEngine = getHotwordEngine();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(hotwordEngine != null ? hotwordEngine.asBinder() : null);
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    ICarSystemProperty carSystemProperty = getCarSystemProperty();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(carSystemProperty != null ? carSystemProperty.asBinder() : null);
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    IVADEngine vadEngine = getVadEngine();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(vadEngine != null ? vadEngine.asBinder() : null);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* loaded from: classes2.dex */
        private static class Proxy implements ISpeechEngine {
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

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IActorBridge getActorBridge() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return IActorBridge.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public ISubscriber getSubscriber() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISubscriber.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public ITTSEngine getTTSEngine() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return ITTSEngine.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IWakeupEngine getWakeupEngine() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return IWakeupEngine.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IAgent getAgent() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return IAgent.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IAppMgr getAppMgr() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return IAppMgr.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public ISpeechState getSpeechState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISpeechState.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public ISoundLockState getSoundLockState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISoundLockState.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IASREngine getASREngine() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return IASREngine.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IQueryInjector getQueryInjector() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return IQueryInjector.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IRecordEngine getRecordEngine() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return IRecordEngine.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IWindowEngine getWindowEngine() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return IWindowEngine.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IRecognizer getRecognizer() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return IRecognizer.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IHotwordEngine getHotwordEngine() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return IHotwordEngine.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public ICarSystemProperty getCarSystemProperty() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICarSystemProperty.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.xiaopeng.speech.ISpeechEngine
            public IVADEngine getVadEngine() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return IVADEngine.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
