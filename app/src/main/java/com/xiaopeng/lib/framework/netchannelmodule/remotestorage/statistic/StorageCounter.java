package com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public final class StorageCounter implements Handler.Callback {
    private static final long DAILY_TRAFFIC_QUOTA = 307200;
    private static final boolean DEBUG = GlobalConfig.debuggable();
    private static final String EVENT_NAME = "net_rs_stat";
    private static final String KEY_ERROR_CODES = "net_rs_codes";
    private static final String KEY_FAILED = "net_rs_failed";
    private static final String KEY_LAST_DATE = "net_rs_date";
    private static final String KEY_REJECT = "net_rs_rej";
    private static final String KEY_REQUEST = "net_rs_request";
    private static final String KEY_SUCCEED = "net_rs_succeed";
    private static final String KEY_TRAFFIC_SIZE = "net_rs_size";
    private static final short MAX_ERROR_CODES = 10;
    private static final long MAX_VALUE_THRESHOLD = 4611686018427387903L;
    private static final int MESSAGE_APPLY = 1;
    private static final long MESSAGE_APPLY_DELAY = 10000;
    private static final String MOLE_TRAFFIC_OSS_BUTTON_ID = "B005";
    private static final long ONE_DAY_MILLS = 86400000;
    private static final String STAT_KEY_ERROR_CODES = "codes";
    private static final String STAT_KEY_FAILED = "fail";
    private static final String STAT_KEY_LAST_DATE = "date";
    private static final String STAT_KEY_PACKAGE_NAME = "pack";
    private static final String STAT_KEY_REJECT = "rej";
    private static final String STAT_KEY_REQUEST = "req";
    private static final String STAT_KEY_SUCCEED = "suc";
    private static final String STAT_KEY_TRAFFIC_SIZE_IN_KB = "size";
    private static final String TAG = "NetChannel.RemoteStorage.Counter";
    private SharedPreferences.Editor mEditor;
    private HashMap<String, Long> mErrorCodes;
    private volatile boolean mExceedTrafficQuota;
    private AtomicLong mFailedCount;
    private final Handler mHandler;
    private Gson mJsonConverter;
    private AtomicLong mLastDate;
    private AtomicInteger mRejectCount;
    private AtomicLong mRequestCount;
    private AtomicLong mSucceedCount;
    private AtomicLong mTrafficSize;

    private StorageCounter() {
        this.mExceedTrafficQuota = false;
        this.mHandler = new Handler(ThreadUtils.getLooper(0), this);
    }

    public static StorageCounter getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEditor = defaultSharedPreferences.edit();
        this.mLastDate = new AtomicLong(defaultSharedPreferences.getLong(KEY_LAST_DATE, System.currentTimeMillis() / 86400000));
        this.mRequestCount = new AtomicLong(0L);
        this.mFailedCount = new AtomicLong(0L);
        this.mSucceedCount = new AtomicLong(0L);
        this.mTrafficSize = new AtomicLong(0L);
        this.mRejectCount = new AtomicInteger(0);
        this.mJsonConverter = new Gson();
        try {
            this.mRequestCount.set(defaultSharedPreferences.getLong(KEY_REQUEST, 0L));
            this.mSucceedCount.set(defaultSharedPreferences.getLong(KEY_SUCCEED, 0L));
            this.mFailedCount.set(defaultSharedPreferences.getLong(KEY_FAILED, 0L));
            this.mTrafficSize.set(defaultSharedPreferences.getLong(KEY_TRAFFIC_SIZE, 0L));
            this.mRejectCount.set(defaultSharedPreferences.getInt(KEY_REJECT, 0));
            this.mErrorCodes = initFromPreference(defaultSharedPreferences);
        } catch (Exception unused) {
        }
        debugInfo();
    }

    public void increaseRequestCount() {
        ensureInitialized();
        this.mEditor.putLong(KEY_REQUEST, this.mRequestCount.incrementAndGet());
    }

    public void increaseFailureWithCode(String str, long j) {
        this.mEditor.putLong(KEY_FAILED, this.mFailedCount.incrementAndGet());
        this.mExceedTrafficQuota = this.mTrafficSize.addAndGet(j >> 10) > DAILY_TRAFFIC_QUOTA;
        updateErrorCodes(str);
        apply();
        debugInfo();
    }

    public void increaseSucceedWithSize(long j) {
        long incrementAndGet = this.mSucceedCount.incrementAndGet();
        long addAndGet = this.mTrafficSize.addAndGet(j >> 10);
        this.mExceedTrafficQuota = addAndGet > DAILY_TRAFFIC_QUOTA;
        this.mEditor.putLong(KEY_SUCCEED, incrementAndGet);
        this.mEditor.putLong(KEY_TRAFFIC_SIZE, addAndGet);
        apply();
        debugInfo();
    }

    public void increaseRejectCount() {
        this.mEditor.putInt(KEY_REJECT, this.mRejectCount.incrementAndGet());
        apply();
        debugInfo();
    }

    public boolean isExceedTrafficQuota() {
        if (this.mExceedTrafficQuota) {
            if (this.mLastDate.get() < System.currentTimeMillis() / 86400000) {
                this.mExceedTrafficQuota = false;
            }
        }
        return this.mExceedTrafficQuota;
    }

    private void apply() {
        Handler handler = this.mHandler;
        if (handler == null || handler.hasMessages(1)) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(1, MESSAGE_APPLY_DELAY);
    }

    private void commitData() {
        long currentTimeMillis = System.currentTimeMillis() / 86400000;
        long j = this.mRequestCount.get();
        long j2 = this.mTrafficSize.get();
        if (j >= MAX_VALUE_THRESHOLD || j2 >= MAX_VALUE_THRESHOLD || this.mLastDate.get() < currentTimeMillis) {
            long j3 = this.mSucceedCount.get();
            long j4 = this.mFailedCount.get();
            long j5 = this.mLastDate.get();
            long j6 = this.mRejectCount.get();
            String errorCodesInString = getErrorCodesInString();
            clearCounters();
            this.mLastDate.set(currentTimeMillis);
            this.mEditor.putLong(KEY_LAST_DATE, this.mLastDate.get());
            if (DeviceInfoUtils.isInternationalVer()) {
                return;
            }
            IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
            iDataLog.sendStatData(iDataLog.buildMoleEvent().setEvent(EVENT_NAME).setPageId(GlobalConfig.MOLE_PAGE_ID).setButtonId(MOLE_TRAFFIC_OSS_BUTTON_ID).setProperty(STAT_KEY_PACKAGE_NAME, GlobalConfig.getApplicationSimpleName()).setProperty(STAT_KEY_REQUEST, Long.valueOf(j)).setProperty(STAT_KEY_FAILED, Long.valueOf(j4)).setProperty(STAT_KEY_SUCCEED, Long.valueOf(j3)).setProperty(STAT_KEY_REJECT, Long.valueOf(j6)).setProperty(STAT_KEY_TRAFFIC_SIZE_IN_KB, Long.valueOf(j2)).setProperty(STAT_KEY_ERROR_CODES, errorCodesInString).setProperty("date", Long.valueOf(j5)).build());
        }
    }

    public void clearCounters() {
        this.mRequestCount.set(0L);
        this.mEditor.putLong(KEY_REQUEST, 0L);
        this.mFailedCount.set(0L);
        this.mEditor.putLong(KEY_FAILED, 0L);
        this.mSucceedCount.set(0L);
        this.mEditor.putLong(KEY_SUCCEED, 0L);
        this.mTrafficSize.set(0L);
        this.mEditor.putLong(KEY_TRAFFIC_SIZE, 0L);
        this.mRejectCount.set(0);
        this.mEditor.putInt(KEY_REJECT, 0);
        this.mExceedTrafficQuota = false;
        synchronized (this) {
            this.mErrorCodes.clear();
        }
        this.mEditor.putString(KEY_ERROR_CODES, "");
    }

    private void debugInfo() {
        if (DEBUG) {
            LogUtils.v(TAG, "[RemoteStorage] Request:" + this.mRequestCount.get() + " Failed:" + this.mFailedCount.get() + " Succeed:" + this.mSucceedCount.get() + " Size(KB):" + this.mTrafficSize.get() + " ErrorCodes:" + getErrorCodesInString() + " Package:" + GlobalConfig.getApplicationSimpleName() + " Net:" + GlobalConfig.getNetworkType());
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what == 1) {
            commitData();
            this.mEditor.apply();
        }
        return true;
    }

    /* loaded from: classes2.dex */
    private static final class Holder {
        private static final StorageCounter INSTANCE = new StorageCounter();

        private Holder() {
        }
    }

    private void ensureInitialized() {
        if (this.mEditor == null) {
            throw new RuntimeException("StorageCounter has not been initialized yet!");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.HashMap<java.lang.String, java.lang.Long> initFromPreference(android.content.SharedPreferences r3) {
        /*
            r2 = this;
            java.lang.String r0 = "net_rs_codes"
            java.lang.String r1 = ""
            java.lang.String r3 = r3.getString(r0, r1)
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L20
            com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic.StorageCounter$1 r0 = new com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic.StorageCounter$1
            r0.<init>()
            java.lang.reflect.Type r0 = r0.getType()
            com.google.gson.Gson r1 = r2.mJsonConverter     // Catch: java.lang.Exception -> L20
            java.lang.Object r3 = r1.fromJson(r3, r0)     // Catch: java.lang.Exception -> L20
            java.util.HashMap r3 = (java.util.HashMap) r3     // Catch: java.lang.Exception -> L20
            goto L21
        L20:
            r3 = 0
        L21:
            if (r3 != 0) goto L2a
            java.util.HashMap r3 = new java.util.HashMap
            r0 = 10
            r3.<init>(r0)
        L2a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic.StorageCounter.initFromPreference(android.content.SharedPreferences):java.util.HashMap");
    }

    private void updateErrorCodes(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this) {
            boolean containsKey = this.mErrorCodes.containsKey(str);
            if (containsKey || this.mErrorCodes.size() < 10) {
                this.mErrorCodes.put(str, Long.valueOf((containsKey ? this.mErrorCodes.get(str).longValue() : 0L) + 1));
            }
            if (this.mJsonConverter == null) {
                this.mJsonConverter = new Gson();
            }
            this.mEditor.putString(KEY_ERROR_CODES, this.mJsonConverter.toJson(this.mErrorCodes));
        }
    }

    private String getErrorCodesInString() {
        StringBuilder sb = new StringBuilder();
        synchronized (this) {
            for (String str : this.mErrorCodes.keySet()) {
                sb.append(str + QuickSettingConstants.JOINER + this.mErrorCodes.get(str) + ";");
            }
        }
        return sb.toString();
    }

    public static boolean isInternationVersion() {
        boolean isInternationalVer = DeviceInfoUtils.isInternationalVer();
        Log.i("netChannel", "isInternationVersion :\t" + isInternationalVer);
        return isInternationalVer;
    }
}
