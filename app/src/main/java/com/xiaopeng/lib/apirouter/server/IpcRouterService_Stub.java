package com.xiaopeng.lib.apirouter.server;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaopeng.carcontrol.IpcRouterService;

/* loaded from: classes2.dex */
public class IpcRouterService_Stub extends Binder implements IInterface {
    public IpcRouterService provider = new IpcRouterService();
    public IpcRouterService_Manifest manifest = new IpcRouterService_Manifest();

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == 0) {
            data.enforceInterface(IpcRouterService_Manifest.DESCRIPTOR);
            Uri uri = (Uri) Uri.CREATOR.createFromParcel(data);
            try {
                this.provider.onReceiverData(((Integer) TransactTranslator.read(uri.getQueryParameter("id"), "int")).intValue(), (String) TransactTranslator.read(uri.getQueryParameter("bundle"), "java.lang.String"));
                reply.writeNoException();
                TransactTranslator.reply(reply, null);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                reply.writeException(new IllegalStateException(e.getMessage()));
                return true;
            }
        } else if (code != 1) {
            if (code == 1598968902) {
                reply.writeString(IpcRouterService_Manifest.DESCRIPTOR);
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        } else {
            data.enforceInterface(IpcRouterService_Manifest.DESCRIPTOR);
            Uri uri2 = (Uri) Uri.CREATOR.createFromParcel(data);
            try {
                String onGetData = this.provider.onGetData(((Integer) TransactTranslator.read(uri2.getQueryParameter("id"), "int")).intValue(), (String) TransactTranslator.read(uri2.getQueryParameter("bundle"), "java.lang.String"));
                reply.writeNoException();
                TransactTranslator.reply(reply, onGetData);
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                reply.writeException(new IllegalStateException(e2.getMessage()));
                return true;
            }
        }
    }
}
