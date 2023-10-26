package com.xiaopeng.xvs.tools.tranfer;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class SyncTransferTools {
    private static final String KYE_SYNC_TRANSFER_UID_ARRAY = "kye_sync_transfer_uid_array";
    private static final String KYE_SYNC_TRANSFER_UID_INIT_FLAG = "kye_sync_transfer_uid_init_flag_v1";
    private static final String KYE_SYNC_TRANSFER_UID_SEPARATOR = ",";
    private static final String TAG = "SyncTransferTools";
    private static volatile SyncTransferTools sInstance;
    private DataAccessor mDataAccessor;
    private DataCenter mDataCenter;
    private volatile String mUidArray = "";

    @Deprecated
    public void release() {
    }

    public static SyncTransferTools getInstance() {
        if (sInstance == null) {
            synchronized (SyncTransferTools.class) {
                if (sInstance == null) {
                    sInstance = new SyncTransferTools();
                }
            }
        }
        return sInstance;
    }

    private SyncTransferTools() {
    }

    public void initDb(final Application application) {
        Log.i(TAG, "initDb: packageName=" + application.getPackageName());
        new Thread(new Runnable() { // from class: com.xiaopeng.xvs.tools.tranfer.SyncTransferTools.1
            @Override // java.lang.Runnable
            public void run() {
                if (!SyncTransferTools.isDatabaseExist(application, "sync_db_v1.db")) {
                    Log.w(SyncTransferTools.TAG, "initDb: isDatabaseExist not exit! not need init database!");
                    return;
                }
                SyncTransferTools.this.mDataCenter = new DataCenter();
                SyncTransferTools.this.mDataCenter.init(application);
                SyncTransferTools.this.mDataAccessor = new DataAccessor(SyncTransferTools.this.mDataCenter.getSyncDataDao());
                ToolsSPUtils.getInstance().init(application);
                SyncTransferTools syncTransferTools = SyncTransferTools.this;
                syncTransferTools.mUidArray = syncTransferTools.readTransferUidArray();
            }
        }).start();
    }

    public Map<String, String> getUidSyncDbOnWorkThread(long j) {
        HashMap hashMap = new HashMap();
        if (!this.mUidArray.contains(String.valueOf(j))) {
            Log.i(TAG, "getUidSyncDbOnWorkThread: current uid has no sync db; uid=" + j);
            return hashMap;
        }
        List<SyncDataEntity> queryUid = this.mDataAccessor.queryUid(j);
        if (queryUid != null && !queryUid.isEmpty()) {
            for (int i = 0; i < queryUid.size(); i++) {
                SyncDataEntity syncDataEntity = queryUid.get(i);
                if (syncDataEntity != null) {
                    hashMap.put(syncDataEntity.key, syncDataEntity.value);
                }
            }
            Log.i(TAG, "getUidSyncDbOnWorkThread: uid=" + j + "; result.size()=" + hashMap.size());
            eraseDb(j);
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String readTransferUidArray() {
        String string;
        boolean z = ToolsSPUtils.getInstance().getBoolean(KYE_SYNC_TRANSFER_UID_INIT_FLAG, false);
        Log.i(TAG, "readTransferUidArray: isInit=" + z);
        if (!z) {
            string = readOnceAllSyncDataToUidArray();
            ToolsSPUtils.getInstance().putString(KYE_SYNC_TRANSFER_UID_ARRAY, string);
            ToolsSPUtils.getInstance().putBoolean(KYE_SYNC_TRANSFER_UID_INIT_FLAG, true);
        } else {
            string = ToolsSPUtils.getInstance().getString(KYE_SYNC_TRANSFER_UID_ARRAY, "");
        }
        Log.i(TAG, "readTransferUidArray: uidArray=" + string);
        return string;
    }

    private void eraseDb(long j) {
        Log.i(TAG, "eraseDb: uid=" + j);
        if (this.mUidArray.contains(j + ",")) {
            ToolsSPUtils.getInstance().putString(KYE_SYNC_TRANSFER_UID_ARRAY, this.mUidArray.replace(j + ",", ""));
            this.mDataAccessor.erase(j);
        }
    }

    private String readOnceAllSyncDataToUidArray() {
        StringBuilder sb = new StringBuilder();
        List<Long> queryUid = this.mDataAccessor.queryUid();
        if (queryUid != null && !queryUid.isEmpty()) {
            for (int i = 0; i < queryUid.size(); i++) {
                sb.append(queryUid.get(i).longValue()).append(",");
            }
        }
        Log.i(TAG, "readOnceAllSyncDataToUidArray: uidArray=" + ((Object) sb));
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDatabaseExist(Context context, String str) {
        return context.getDatabasePath(str).exists();
    }
}
