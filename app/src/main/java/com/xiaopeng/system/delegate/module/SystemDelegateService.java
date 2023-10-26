package com.xiaopeng.system.delegate.module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.systemdelegate.ISystemDelegate;
import com.xiaopeng.lib.utils.LogUtils;

/* loaded from: classes2.dex */
public class SystemDelegateService implements ISystemDelegate {
    private static final String TAG = "SystemDelegateService";
    private Context mContext;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SystemDelegateService(Context context) {
        this.mContext = context;
        Module.register(SystemDelegateModuleEntry.class, new SystemDelegateModuleEntry(context));
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.systemdelegate.ISystemDelegate
    public String getCertificate() throws RemoteException {
        LogUtils.d(TAG, "start getCertificate!");
        Cursor query = this.mContext.getContentResolver().query(Uri.parse(SystemDelegateConstants.URI_SSL_CERT), null, null, null, null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    String string = query.getString(0);
                    LogUtils.w(TAG, "query result success");
                    return string;
                }
            } finally {
                if (query != null) {
                    query.close();
                }
            }
        }
        LogUtils.w(TAG, "cursor is empty!");
        if (query != null) {
            query.close();
        }
        return null;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.systemdelegate.ISystemDelegate
    public void setSystemProperty(String str, String str2) throws RemoteException {
        LogUtils.d(TAG, "setSystemProperty " + str + QuickSettingConstants.JOINER + str2);
        Uri parse = Uri.parse(SystemDelegateConstants.URI_SET_PROP);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, str2);
        this.mContext.getContentResolver().update(parse, contentValues, null, null);
    }
}
