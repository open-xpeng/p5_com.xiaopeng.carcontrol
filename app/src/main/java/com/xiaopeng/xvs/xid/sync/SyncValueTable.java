package com.xiaopeng.xvs.xid.sync;

import android.app.Application;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import com.xiaopeng.xvs.xid.utils.L;

/* loaded from: classes2.dex */
public final class SyncValueTable extends Settings.NameValueTable {
    private static final String TAG = "SyncValueTable";
    public final String mAppID;
    private Application mApplication;
    private Bundle mExtras;
    private ContentResolver mResolver;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SyncValueTable(Application application, String str) {
        this.mApplication = application;
        this.mAppID = str;
        Bundle bundle = new Bundle();
        this.mExtras = bundle;
        bundle.putString(ISync.EXTRA_NAME_APP_ID, str);
        this.mResolver = application.getContentResolver();
    }

    public String get(String str) {
        L.d(TAG, "getString() called with: key = [" + str + "]");
        try {
            this.mExtras.putString(ISync.EXTRA_NAME_KEY, str);
            Bundle call = this.mResolver.call(ISync.SYNC_CONTENT_URI, ISync.SYNC_CALL_METHOD_GET, this.mAppID, this.mExtras);
            if (call != null) {
                return call.getString(ISync.EXTRA_NAME_VALUE);
            }
            return null;
        } catch (Exception e) {
            L.d(TAG, "getString: e=" + e.getMessage());
            return null;
        }
    }

    public boolean put(String str, String str2) {
        L.d(TAG, "put: key=" + str);
        try {
            Bundle bundle = new Bundle();
            bundle.putString(ISync.EXTRA_NAME_KEY, str);
            bundle.putString(ISync.EXTRA_NAME_VALUE, str2);
            this.mResolver.call(ISync.SYNC_CONTENT_URI, ISync.SYNC_CALL_METHOD_PUT, this.mAppID, bundle);
            return true;
        } catch (Exception e) {
            Log.w(TAG, "put: Cannot put " + this.mAppID + " value; msg=" + e.getMessage());
            return false;
        }
    }

    public boolean putIndex(int i, String str, String str2) {
        L.d(TAG, "put: key=" + str);
        try {
            Bundle bundle = new Bundle();
            bundle.putString(ISync.EXTRA_NAME_KEY, str);
            bundle.putString(ISync.EXTRA_NAME_VALUE, str2);
            bundle.putInt(ISync.EXTRA_NAME_GROUP_INDEX, i);
            this.mResolver.call(ISync.SYNC_CONTENT_URI, ISync.SYNC_CALL_METHOD_PUT_INDEX, this.mAppID, bundle);
            return true;
        } catch (Exception e) {
            Log.w(TAG, "put: Cannot put " + this.mAppID + " value; msg=" + e.getMessage());
            return false;
        }
    }

    public String isInit() {
        L.d(TAG, "isInit:");
        try {
            Bundle call = this.mResolver.call(ISync.SYNC_CONTENT_URI, ISync.SYNC_CALL_METHOD_GET_INIT_COMPLETE, this.mAppID, this.mExtras);
            if (call != null) {
                return call.getString(ISync.EXTRA_NAME_VALUE);
            }
            return null;
        } catch (Exception e) {
            L.d(TAG, "getString: e=" + e.getMessage());
            return null;
        }
    }

    public static Uri getUriFor(String str) {
        return getUriFor(ISync.SYNC_CONTENT_URI, str);
    }

    public void registerContentObserver(ContentObserver contentObserver) {
        L.d(TAG, "registerContentObserver: ");
        this.mApplication.getContentResolver().registerContentObserver(ISync.SYNC_CONTENT_URI, true, contentObserver);
    }
}
