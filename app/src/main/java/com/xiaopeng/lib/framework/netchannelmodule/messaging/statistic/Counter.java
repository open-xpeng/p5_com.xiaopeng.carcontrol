package com.xiaopeng.lib.framework.netchannelmodule.messaging.statistic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.netchannelmodule.common.BackgroundHandler;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.framework.netchannelmodule.messaging.protocol.MqttChannel;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public class Counter {
    private static final boolean COMMIT_ACCORDING_TO_DATE = false;
    private static final boolean DEBUG = GlobalConfig.debuggable();
    private static final String EVENT_NAME = "net_mqtt_stat";
    private static final String KEY_INIT_FAILED = "net_mqtt_init_failed";
    private static final String KEY_LAST_DATE = "net_mqtt_date";
    private static final String KEY_PUBLISHED = "net_mqtt_pub";
    private static final String KEY_PUBLISHED_SIZE = "net_mqtt_pub_size";
    private static final String KEY_PUBLISH_FAILED = "net_mqtt_pub_failed";
    private static final String KEY_RECEIVED = "net_mqtt_recv";
    private static final String KEY_TRY_PUBLISH = "net_mqtt_try_pub";
    private static final long MAX_VALUE_THRESHOLD = 4611686018427387903L;
    private static final long MESSAGE_APPLY_DELAY = 10000;
    private static final long MIN_COMMIT_COUNT = 10;
    private static final String MOLE_TRAFFIC_MQTT_BUTTON_ID = "B003";
    private static final long ONE_DAY_MILLS = 86400000;
    private static final String STAT_KEY_INIT_FAILED = "init_fail";
    private static final String STAT_KEY_LAST_DATE = "date";
    private static final String STAT_KEY_NETWORK_TYPE = "net";
    private static final String STAT_KEY_PACKAGE_NAME = "pack";
    private static final String STAT_KEY_PUBLISHED = "pub";
    private static final String STAT_KEY_PUBLISHED_SIZE_KB = "pub_size";
    private static final String STAT_KEY_PUBLISH_FAILED = "fail";
    private static final String STAT_KEY_RECEIVED = "recv";
    private static final String STAT_KEY_TRY_PUBLISH = "try";
    private static final String TAG = "NetChannel-Counter";
    private static final long UNPUBLISHED_COUNT_THRESHOLD = 128;
    private final Runnable mApplyRunnable = new Runnable() { // from class: com.xiaopeng.lib.framework.netchannelmodule.messaging.statistic.Counter.1
        @Override // java.lang.Runnable
        public void run() {
            Counter.this.tryToCommitData(false);
            Counter.this.mEditor.apply();
        }
    };
    private final SharedPreferences.Editor mEditor;
    private final BackgroundHandler mHandler;
    private final AtomicLong mInitFailedCount;
    private final AtomicLong mLastDate;
    private final boolean mNeedToPost;
    private final AtomicLong mPublishFailedCount;
    private final AtomicLong mPublishedCount;
    private final AtomicLong mPublishedSize;
    private final AtomicLong mReceivedCount;
    private final AtomicLong mTryToPublishCount;

    public Counter(Context context, BackgroundHandler backgroundHandler, boolean z) {
        this.mNeedToPost = z;
        this.mHandler = backgroundHandler;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEditor = defaultSharedPreferences.edit();
        this.mLastDate = new AtomicLong(defaultSharedPreferences.getLong(KEY_LAST_DATE, System.currentTimeMillis() / 86400000));
        AtomicLong atomicLong = new AtomicLong(0L);
        this.mTryToPublishCount = atomicLong;
        AtomicLong atomicLong2 = new AtomicLong(0L);
        this.mPublishFailedCount = atomicLong2;
        AtomicLong atomicLong3 = new AtomicLong(0L);
        this.mPublishedCount = atomicLong3;
        AtomicLong atomicLong4 = new AtomicLong(0L);
        this.mReceivedCount = atomicLong4;
        AtomicLong atomicLong5 = new AtomicLong(0L);
        this.mInitFailedCount = atomicLong5;
        AtomicLong atomicLong6 = new AtomicLong(0L);
        this.mPublishedSize = atomicLong6;
        try {
            atomicLong.set(defaultSharedPreferences.getLong(KEY_TRY_PUBLISH, 0L));
            atomicLong2.set(defaultSharedPreferences.getLong(KEY_PUBLISH_FAILED, 0L));
            atomicLong3.set(defaultSharedPreferences.getLong(KEY_PUBLISHED, 0L));
            atomicLong4.set(defaultSharedPreferences.getLong(KEY_RECEIVED, 0L));
            atomicLong5.set(defaultSharedPreferences.getLong(KEY_INIT_FAILED, 0L));
            atomicLong6.set(defaultSharedPreferences.getLong(KEY_PUBLISHED_SIZE, 0L));
        } catch (Exception unused) {
        }
        debugInfo();
    }

    public void increaseTryingToPublish() {
        long incrementAndGet = this.mTryToPublishCount.incrementAndGet();
        long j = incrementAndGet - this.mPublishedCount.get();
        if (j >= 128 && ((j >> 6) << 6) == j) {
            MqttChannel.getInstance().disconnectedDueToException(new Throwable("un publish too large"));
        }
        this.mEditor.putLong(KEY_TRY_PUBLISH, incrementAndGet);
        if (((incrementAndGet >> 2) << 2) == incrementAndGet) {
            apply();
        }
        debugInfo();
    }

    public void increasePublishedCountWithSize(long j) {
        this.mEditor.putLong(KEY_PUBLISHED, this.mPublishedCount.incrementAndGet());
        this.mEditor.putLong(KEY_PUBLISHED_SIZE, this.mPublishedSize.addAndGet(j));
        debugInfo();
    }

    public void increaseReceivedCount() {
        long incrementAndGet = this.mReceivedCount.incrementAndGet();
        this.mEditor.putLong(KEY_RECEIVED, incrementAndGet);
        if (((incrementAndGet >> 2) << 2) == incrementAndGet) {
            apply();
        }
        debugInfo();
    }

    public void increasePublishFailureCount() {
        this.mEditor.putLong(KEY_PUBLISH_FAILED, this.mPublishFailedCount.incrementAndGet());
        debugInfo();
    }

    public void increaseInitFailedCount() {
        this.mEditor.putLong(KEY_INIT_FAILED, this.mInitFailedCount.incrementAndGet());
        debugInfo();
    }

    public void commitForcibly() {
        if (this.mHandler.hasCallbacks(this.mApplyRunnable)) {
            return;
        }
        this.mHandler.postDelayed(this.mApplyRunnable, MESSAGE_APPLY_DELAY);
    }

    private void apply() {
        if (this.mHandler.hasCallbacks(this.mApplyRunnable)) {
            return;
        }
        this.mHandler.postDelayed(this.mApplyRunnable, MESSAGE_APPLY_DELAY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryToCommitData(boolean z) {
        long currentTimeMillis = System.currentTimeMillis() / 86400000;
        long j = this.mTryToPublishCount.get();
        long j2 = this.mReceivedCount.get();
        long j3 = this.mPublishedSize.get();
        if (z || j3 >= MAX_VALUE_THRESHOLD || this.mLastDate.get() < currentTimeMillis) {
            long j4 = this.mPublishedCount.get();
            long j5 = this.mPublishFailedCount.get();
            long j6 = this.mInitFailedCount.get();
            long j7 = this.mLastDate.get();
            clearCounters();
            this.mLastDate.set(currentTimeMillis);
            this.mEditor.putLong(KEY_LAST_DATE, this.mLastDate.get());
            if (!this.mNeedToPost || j + j2 <= MIN_COMMIT_COUNT || DeviceInfoUtils.isInternationalVer()) {
                return;
            }
            IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
            iDataLog.sendStatData(iDataLog.buildMoleEvent().setEvent(EVENT_NAME).setPageId(GlobalConfig.MOLE_PAGE_ID).setButtonId(MOLE_TRAFFIC_MQTT_BUTTON_ID).setProperty(STAT_KEY_PACKAGE_NAME, GlobalConfig.getApplicationSimpleName()).setProperty(STAT_KEY_TRY_PUBLISH, Long.valueOf(j)).setProperty(STAT_KEY_PUBLISH_FAILED, Long.valueOf(j5)).setProperty(STAT_KEY_PUBLISHED, Long.valueOf(j4)).setProperty(STAT_KEY_PUBLISHED_SIZE_KB, Long.valueOf(j3 >> MIN_COMMIT_COUNT)).setProperty(STAT_KEY_RECEIVED, Long.valueOf(j2)).setProperty(STAT_KEY_INIT_FAILED, Long.valueOf(j6)).setProperty("date", Long.valueOf(j7)).setProperty(STAT_KEY_NETWORK_TYPE, Integer.valueOf(GlobalConfig.getNetworkType())).build());
        }
    }

    public void clearCounters() {
        this.mTryToPublishCount.set(0L);
        this.mEditor.putLong(KEY_TRY_PUBLISH, 0L);
        this.mPublishedCount.set(0L);
        this.mEditor.putLong(KEY_PUBLISHED, 0L);
        this.mPublishFailedCount.set(0L);
        this.mEditor.putLong(KEY_PUBLISH_FAILED, 0L);
        this.mPublishedSize.set(0L);
        this.mEditor.putLong(KEY_PUBLISHED_SIZE, 0L);
        this.mReceivedCount.set(0L);
        this.mEditor.putLong(KEY_RECEIVED, 0L);
        this.mInitFailedCount.set(0L);
        this.mEditor.putLong(KEY_INIT_FAILED, 0L).apply();
    }

    private void debugInfo() {
        if (DEBUG) {
            LogUtils.v(TAG, "[MQTTSTATUS] try to pub:" + this.mTryToPublishCount.get() + " published:" + this.mPublishedCount.get() + " publish fail:" + this.mPublishFailedCount.get() + " received:" + this.mReceivedCount.get() + " init fail:" + this.mInitFailedCount.get() + " published size(KB):" + (this.mPublishedSize.get() >> MIN_COMMIT_COUNT) + " package: " + GlobalConfig.getApplicationSimpleName() + " net:" + GlobalConfig.getNetworkType());
        }
    }
}
