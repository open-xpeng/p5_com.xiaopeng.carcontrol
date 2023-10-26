package com.xiaopeng.lib.framework.netchannelmodule.http.statistic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.framework.netchannelmodule.remotestorage.statistic.StorageCounter;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import com.xiaopeng.xvs.xid.base.AbsException;
import java.util.HashMap;

/* loaded from: classes2.dex */
public abstract class BaseHttpCounter implements Handler.Callback {
    private static final long HALF_DAY_MILLS = 43200000;
    private static final short MAX_DOMAINS_IN_DETAIL_LIST = 15;
    private static final long MAX_VALUE_THRESHOLD = 104857600;
    private static final int MESSAGE_APPLY = 1;
    private static final long MESSAGE_APPLY_DELAY = 10000;
    private static final String STAT_KEY_DETAILS = "details";
    private static final String STAT_KEY_FAILED = "fail";
    private static final String STAT_KEY_LAST_DATE = "date";
    private static final String STAT_KEY_PACKAGE_NAME = "pack";
    private static final String STAT_KEY_REQUEST = "req";
    private static final String STAT_KEY_RX_SIZE_IN_KB = "rx";
    private static final String STAT_KEY_SUCCEED = "suc";
    private static final String STAT_KEY_TX_SIZE_IN_KB = "tx";
    private static final String TAG = "NetChannel.Http.Counter";
    private int MIN_SAVING_INTERVAL;
    private SharedPreferences.Editor mEditor;
    private boolean mEnabled;
    private final Handler mHandler;
    private Gson mJsonConverter;
    private long mLastDate;
    private long mLastDetailSavingTime;
    private final String mMoleButtonId;
    private long mRequestCount;
    private final String mStatEventName;
    private TrafficData mTotalData;
    private HashMap<String, TrafficData> mTrafficDetails;
    private static final boolean DEBUG = GlobalConfig.debuggable();
    protected static String sKeyFailed = "net_http_failed";
    protected static String sKeySucceed = "net_http_succeed";
    protected static String sKeyRequest = "net_http_request";
    protected static String sKeyTrafficRx = "net_http_rx";
    protected static String sKeyTrafficTx = "net_http_tx";
    protected static String sKeyDetails = "net_http_details";
    protected static String sKeyLastDate = "net_http_date";

    private BaseHttpCounter() {
        this.mEnabled = false;
        this.MIN_SAVING_INTERVAL = AbsException.ERROR_CODE_BASE_LOGIN;
        this.mStatEventName = "";
        this.mMoleButtonId = "";
        this.mHandler = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseHttpCounter(String str, String str2) {
        this.mEnabled = false;
        this.MIN_SAVING_INTERVAL = AbsException.ERROR_CODE_BASE_LOGIN;
        this.mStatEventName = str;
        this.mMoleButtonId = str2;
        this.mHandler = new Handler(ThreadUtils.getLooper(0), this);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what == 1) {
            synchronized (this) {
                commitData();
                this.mEditor.apply();
            }
        }
        return true;
    }

    public synchronized void init() {
        Context applicationContext = GlobalConfig.getApplicationContext();
        boolean z = applicationContext != null;
        this.mEnabled = z;
        if (z) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
            this.mEditor = defaultSharedPreferences.edit();
            this.mLastDate = defaultSharedPreferences.getLong(sKeyLastDate, System.currentTimeMillis() / HALF_DAY_MILLS);
            this.mRequestCount = 0L;
            this.mTotalData = new TrafficData();
            this.mJsonConverter = new Gson();
            this.mTrafficDetails = new HashMap<>(15);
            try {
                this.mRequestCount = defaultSharedPreferences.getLong(sKeyRequest, 0L);
                this.mTotalData.setCount(defaultSharedPreferences.getLong(sKeySucceed, 0L), defaultSharedPreferences.getLong(sKeyFailed, 0L));
                this.mTotalData.setSize(defaultSharedPreferences.getLong(sKeyTrafficRx, 0L), defaultSharedPreferences.getLong(sKeyTrafficTx, 0L));
                this.mTrafficDetails = initFromPreference(defaultSharedPreferences);
            } catch (Exception e) {
                LogUtils.e(TAG, "Failure to load from preference due to " + e);
            }
            debugInfo();
        }
    }

    public synchronized void increaseRequest(String str, long j) {
        if (this.mEnabled) {
            ensureInitialized();
            long j2 = this.mRequestCount + 1;
            this.mRequestCount = j2;
            this.mEditor.putLong(sKeyRequest, j2);
            this.mTotalData.addSentSize(j);
            TrafficData trafficDataByDomain = getTrafficDataByDomain(str);
            if (trafficDataByDomain != null) {
                trafficDataByDomain.addSentSize(j);
                saveDetailData();
            }
        }
    }

    public synchronized void increaseFailureWithSize(String str, long j) {
        TrafficData trafficDataByDomain;
        if (this.mEnabled) {
            this.mTotalData.increaseFailed(j);
            this.mEditor.putLong(sKeyFailed, this.mTotalData.failed());
            this.mEditor.putLong(sKeyTrafficRx, this.mTotalData.receivedSize());
            if (str != null && (trafficDataByDomain = getTrafficDataByDomain(str)) != null) {
                trafficDataByDomain.increaseFailed(j);
                saveDetailData();
            }
            apply();
            debugInfo();
        }
    }

    public synchronized void increaseSucceedWithSize(String str, long j) {
        TrafficData trafficDataByDomain;
        if (this.mEnabled) {
            this.mTotalData.increaseSucceed(j);
            this.mEditor.putLong(sKeySucceed, this.mTotalData.succeed());
            this.mEditor.putLong(sKeyTrafficRx, this.mTotalData.receivedSize());
            if (str != null && (trafficDataByDomain = getTrafficDataByDomain(str)) != null) {
                trafficDataByDomain.increaseSucceed(j);
                saveDetailData();
            }
            apply();
            debugInfo();
        }
    }

    public synchronized void addReceivedSize(String str, long j) {
        TrafficData trafficDataByDomain;
        if (this.mEnabled) {
            this.mTotalData.addReceivedSize(j);
            this.mEditor.putLong(sKeyTrafficRx, this.mTotalData.receivedSize());
            if (str != null && (trafficDataByDomain = getTrafficDataByDomain(str)) != null) {
                trafficDataByDomain.addReceivedSize(j);
                saveDetailData();
            }
            apply();
            debugInfo();
        }
    }

    public synchronized void addSentSize(String str, long j) {
        TrafficData trafficDataByDomain;
        if (this.mEnabled) {
            this.mTotalData.addSentSize(j);
            this.mEditor.putLong(sKeyTrafficTx, this.mTotalData.sentSize());
            if (str != null && (trafficDataByDomain = getTrafficDataByDomain(str)) != null) {
                trafficDataByDomain.addSentSize(j);
                saveDetailData();
            }
            apply();
            debugInfo();
        }
    }

    private void apply() {
        Handler handler = this.mHandler;
        if (handler == null || handler.hasMessages(1)) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(1, MESSAGE_APPLY_DELAY);
    }

    private synchronized void commitData() {
        long currentTimeMillis = System.currentTimeMillis() / HALF_DAY_MILLS;
        if (this.mTotalData.receivedSize() >= MAX_VALUE_THRESHOLD || this.mTotalData.sentSize() >= MAX_VALUE_THRESHOLD || this.mLastDate < currentTimeMillis) {
            String json = this.mJsonConverter.toJson(this.mTrafficDetails);
            if (!StorageCounter.isInternationVersion()) {
                IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                iDataLog.sendStatData(iDataLog.buildMoleEvent().setEvent(this.mStatEventName).setPageId(GlobalConfig.MOLE_PAGE_ID).setButtonId(this.mMoleButtonId).setProperty(STAT_KEY_PACKAGE_NAME, GlobalConfig.getApplicationSimpleName()).setProperty(STAT_KEY_REQUEST, Long.valueOf(this.mRequestCount)).setProperty(STAT_KEY_FAILED, Long.valueOf(this.mTotalData.failed())).setProperty(STAT_KEY_SUCCEED, Long.valueOf(this.mTotalData.succeed())).setProperty(STAT_KEY_RX_SIZE_IN_KB, Long.valueOf(this.mTotalData.receivedSize() >> 10)).setProperty(STAT_KEY_TX_SIZE_IN_KB, Long.valueOf(this.mTotalData.sentSize() >> 10)).setProperty(STAT_KEY_DETAILS, json).setProperty("date", Long.valueOf(this.mLastDate)).build());
            }
            this.mLastDate = currentTimeMillis;
            this.mEditor.putLong(sKeyLastDate, currentTimeMillis);
            clearCounters();
        }
    }

    public synchronized void clearCounters() {
        if (this.mEnabled) {
            this.mRequestCount = 0L;
            this.mEditor.putLong(sKeyRequest, 0L);
            this.mEditor.putLong(sKeyFailed, 0L);
            this.mEditor.putLong(sKeySucceed, 0L);
            this.mEditor.putLong(sKeyTrafficRx, 0L);
            this.mEditor.putLong(sKeyTrafficTx, 0L);
            this.mTotalData.reset();
            this.mTrafficDetails.clear();
            this.mEditor.putString(sKeyDetails, "");
        }
    }

    private synchronized void debugInfo() {
        if (DEBUG) {
            LogUtils.v(TAG, "[Http (" + this.mStatEventName + ")]: Request:" + this.mRequestCount + " Total:" + this.mTotalData + " Details:" + this.mJsonConverter.toJson(this.mTrafficDetails) + " Package:" + GlobalConfig.getApplicationSimpleName() + " Net:" + GlobalConfig.getNetworkType());
        }
    }

    private void ensureInitialized() {
        if (this.mEditor == null) {
            throw new RuntimeException("StorageCounter has not been initialized yet!");
        }
    }

    private void saveDetailData() {
        if (System.currentTimeMillis() - this.mLastDetailSavingTime < this.MIN_SAVING_INTERVAL) {
            return;
        }
        this.mEditor.putString(sKeyDetails, this.mJsonConverter.toJson(this.mTrafficDetails));
        this.mLastDetailSavingTime = System.currentTimeMillis();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.HashMap<java.lang.String, com.xiaopeng.lib.framework.netchannelmodule.http.statistic.TrafficData> initFromPreference(android.content.SharedPreferences r3) {
        /*
            r2 = this;
            java.lang.String r0 = com.xiaopeng.lib.framework.netchannelmodule.http.statistic.BaseHttpCounter.sKeyDetails
            java.lang.String r1 = ""
            java.lang.String r3 = r3.getString(r0, r1)
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L20
            com.xiaopeng.lib.framework.netchannelmodule.http.statistic.BaseHttpCounter$1 r0 = new com.xiaopeng.lib.framework.netchannelmodule.http.statistic.BaseHttpCounter$1
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
            r0 = 15
            r3.<init>(r0)
        L2a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.framework.netchannelmodule.http.statistic.BaseHttpCounter.initFromPreference(android.content.SharedPreferences):java.util.HashMap");
    }

    private TrafficData getTrafficDataByDomain(String str) {
        if (this.mTrafficDetails.containsKey(str)) {
            return this.mTrafficDetails.get(str);
        }
        if (this.mTrafficDetails.size() < 15) {
            TrafficData trafficData = new TrafficData();
            this.mTrafficDetails.put(str, trafficData);
            return trafficData;
        }
        return null;
    }
}
