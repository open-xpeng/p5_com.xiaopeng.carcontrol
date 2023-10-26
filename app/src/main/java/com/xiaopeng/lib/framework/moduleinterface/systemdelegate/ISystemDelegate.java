package com.xiaopeng.lib.framework.moduleinterface.systemdelegate;

import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISystemDelegate {
    String getCertificate() throws RemoteException;

    void setSystemProperty(String str, String str2) throws RemoteException;
}
