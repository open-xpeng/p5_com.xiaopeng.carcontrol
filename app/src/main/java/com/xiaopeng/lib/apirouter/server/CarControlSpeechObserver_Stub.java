package com.xiaopeng.lib.apirouter.server;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.carcontrol.CarControlSpeechObserver;

/* loaded from: classes2.dex */
public class CarControlSpeechObserver_Stub extends Binder implements IInterface {
    public CarControlSpeechObserver provider = new CarControlSpeechObserver();
    public CarControlSpeechObserver_Manifest manifest = new CarControlSpeechObserver_Manifest();

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == 0) {
            data.enforceInterface(CarControlSpeechObserver_Manifest.DESCRIPTOR);
            Uri uri = (Uri) Uri.CREATOR.createFromParcel(data);
            try {
                this.provider.onEvent((String) TransactTranslator.read(uri.getQueryParameter(NotificationCompat.CATEGORY_EVENT), "java.lang.String"), (String) TransactTranslator.read(uri.getQueryParameter("data"), "java.lang.String"));
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
                reply.writeString(CarControlSpeechObserver_Manifest.DESCRIPTOR);
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        } else {
            data.enforceInterface(CarControlSpeechObserver_Manifest.DESCRIPTOR);
            Uri uri2 = (Uri) Uri.CREATOR.createFromParcel(data);
            try {
                this.provider.onQuery((String) TransactTranslator.read(uri2.getQueryParameter(NotificationCompat.CATEGORY_EVENT), "java.lang.String"), (String) TransactTranslator.read(uri2.getQueryParameter("data"), "java.lang.String"), (String) TransactTranslator.read(uri2.getQueryParameter("callback"), "java.lang.String"));
                reply.writeNoException();
                TransactTranslator.reply(reply, null);
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                reply.writeException(new IllegalStateException(e2.getMessage()));
                return true;
            }
        }
    }
}
