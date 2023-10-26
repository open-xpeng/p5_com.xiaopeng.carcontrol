package com.xiaopeng.appstore.storeprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

/* loaded from: classes.dex */
public class ResourceProviderContract {
    public static final String ASSEMBLE_PATH = "assemble";
    public static final String AUTHORITY = "resource_service";
    public static final String COLUMN_KEY = "key";
    public static final int INDEX_ASSEMBLE_CALLING = 2;
    public static final int INDEX_ASSEMBLE_ICON_URL = 4;
    public static final int INDEX_ASSEMBLE_KEY = 0;
    public static final int INDEX_ASSEMBLE_NAME = 3;
    public static final int INDEX_ASSEMBLE_PROGRESS = 6;
    public static final int INDEX_ASSEMBLE_STATE = 5;
    public static final int INDEX_ASSEMBLE_TYPE = 1;
    public static final int LOCAL_STATE_INDEX_KEY = 1;
    public static final int LOCAL_STATE_INDEX_STATE = 2;
    public static final int LOCAL_STATE_INDEX_TYPE = 0;
    public static final String LOCAL_STATE_URI_PATH_STATES = "local_states";
    public static final int STATE_DEFAULT = 0;
    public static final int STATE_NEW_INSTALLED = 1001;
    private static final String TAG = "ResourceProviderContrac";
    private static final String URI_PATH_ROOT = "content://resource_service/";
    public static final Uri ASSEMBLE_URI = Uri.parse("content://resource_service/assemble");
    public static final Uri LOCAL_STATE_URI = Uri.parse("content://resource_service/local_states");

    public static void clearState(Context context, int i, String str) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues(2);
        contentValues.put("key", str);
        try {
            contentResolver.update(ContentUris.withAppendedId(LOCAL_STATE_URI, i), contentValues, null, null);
        } catch (Exception e) {
            Log.w(TAG, "clearState: update ex:" + e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean queryIsAssembling(android.content.Context r6, java.lang.String r7, int r8) {
        /*
            android.content.ContentResolver r0 = r6.getContentResolver()
            android.net.Uri r6 = com.xiaopeng.appstore.storeprovider.ResourceProviderContract.ASSEMBLE_URI
            long r1 = (long) r8
            android.net.Uri r1 = android.content.ContentUris.withAppendedId(r6, r1)
            r6 = 1
            java.lang.String[] r4 = new java.lang.String[r6]
            r8 = 0
            r4[r8] = r7
            r2 = 0
            java.lang.String r3 = "key LIKE ?"
            r5 = 0
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5)
            java.lang.String r1 = "ResourceProviderContrac"
            if (r0 == 0) goto L68
            int r2 = r0.getCount()     // Catch: java.lang.Throwable -> L66
            if (r2 == 0) goto L68
            boolean r2 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L66
            if (r2 == 0) goto L7e
            r2 = 5
            int r2 = r0.getInt(r2)     // Catch: java.lang.Throwable -> L66
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L66
            r3.<init>()     // Catch: java.lang.Throwable -> L66
            java.lang.String r4 = "queryIsAssembling finish: key="
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> L66
            java.lang.StringBuilder r7 = r3.append(r7)     // Catch: java.lang.Throwable -> L66
            java.lang.String r3 = " state="
            java.lang.StringBuilder r7 = r7.append(r3)     // Catch: java.lang.Throwable -> L66
            java.lang.String r3 = com.xiaopeng.appstore.storeprovider.AssembleInfo.stateToStr(r2)     // Catch: java.lang.Throwable -> L66
            java.lang.StringBuilder r7 = r7.append(r3)     // Catch: java.lang.Throwable -> L66
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L66
            android.util.Log.d(r1, r7)     // Catch: java.lang.Throwable -> L66
            boolean r7 = com.xiaopeng.appstore.storeprovider.AssembleInfo.isRunning(r2)     // Catch: java.lang.Throwable -> L66
            if (r7 != 0) goto L60
            boolean r7 = com.xiaopeng.appstore.storeprovider.AssembleInfo.isPreparing(r2)     // Catch: java.lang.Throwable -> L66
            if (r7 == 0) goto L5f
            goto L60
        L5f:
            r6 = r8
        L60:
            if (r0 == 0) goto L65
            r0.close()
        L65:
            return r6
        L66:
            r6 = move-exception
            goto L84
        L68:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L66
            r6.<init>()     // Catch: java.lang.Throwable -> L66
            java.lang.String r2 = "queryIsAssembling finish: no data relative to "
            java.lang.StringBuilder r6 = r6.append(r2)     // Catch: java.lang.Throwable -> L66
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> L66
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L66
            android.util.Log.d(r1, r6)     // Catch: java.lang.Throwable -> L66
        L7e:
            if (r0 == 0) goto L83
            r0.close()
        L83:
            return r8
        L84:
            throw r6     // Catch: java.lang.Throwable -> L85
        L85:
            r7 = move-exception
            if (r0 == 0) goto L96
            if (r6 == 0) goto L93
            r0.close()     // Catch: java.lang.Throwable -> L8e
            goto L96
        L8e:
            r8 = move-exception
            r6.addSuppressed(r8)
            goto L96
        L93:
            r0.close()
        L96:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.appstore.storeprovider.ResourceProviderContract.queryIsAssembling(android.content.Context, java.lang.String, int):boolean");
    }
}
