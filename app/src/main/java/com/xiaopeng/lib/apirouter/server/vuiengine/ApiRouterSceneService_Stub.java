package com.xiaopeng.lib.apirouter.server.vuiengine;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.core.app.NotificationCompat;
import com.xiaopeng.lib.apirouter.server.TransactTranslator;
import com.xiaopeng.speech.apirouter.ApiRouterSceneService;
import com.xiaopeng.speech.vui.constants.VuiConstants;

/* loaded from: classes2.dex */
public class ApiRouterSceneService_Stub extends Binder implements IInterface {
    public ApiRouterSceneService provider = new ApiRouterSceneService();
    public ApiRouterSceneService_Manifest manifest = new ApiRouterSceneService_Manifest();

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 0) {
            parcel.enforceInterface(ApiRouterSceneService_Manifest.DESCRIPTOR);
            Uri uri = (Uri) Uri.CREATOR.createFromParcel(parcel);
            try {
                String elementState = this.provider.getElementState((String) TransactTranslator.read(uri.getQueryParameter(VuiConstants.SCENE_ID), "java.lang.String"), (String) TransactTranslator.read(uri.getQueryParameter("elementId"), "java.lang.String"));
                parcel2.writeNoException();
                TransactTranslator.reply(parcel2, elementState);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                parcel2.writeException(new IllegalStateException(e.getMessage()));
                return true;
            }
        } else if (i != 1) {
            if (i == 1598968902) {
                parcel2.writeString(ApiRouterSceneService_Manifest.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        } else {
            parcel.enforceInterface(ApiRouterSceneService_Manifest.DESCRIPTOR);
            Uri uri2 = (Uri) Uri.CREATOR.createFromParcel(parcel);
            try {
                this.provider.onEvent((String) TransactTranslator.read(uri2.getQueryParameter(NotificationCompat.CATEGORY_EVENT), "java.lang.String"), (String) TransactTranslator.read(uri2.getQueryParameter("data"), "java.lang.String"));
                parcel2.writeNoException();
                TransactTranslator.reply(parcel2, null);
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                parcel2.writeException(new IllegalStateException(e2.getMessage()));
                return true;
            }
        }
    }
}
