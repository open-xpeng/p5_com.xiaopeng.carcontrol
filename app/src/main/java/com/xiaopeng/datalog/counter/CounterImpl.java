package com.xiaopeng.datalog.counter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.datalog.DataLogModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounter;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IDataLog;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEvent;
import com.xiaopeng.lib.framework.moduleinterface.datalogmodule.IMoleEventBuilder;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.ThreadUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes2.dex */
public class CounterImpl implements ICounter, Handler.Callback {
    private static final String KEY_TIME = "time";
    private static final int MESSAGE_APPLY = 1;
    private static final long MESSAGE_APPLY_DELAY = 10000;
    private static final String TAG = "CounterImpl";
    private final Map<String, AtomicInteger> mCountMap;
    private boolean mDebug;
    private final SharedPreferences.Editor mEditor;
    private Handler mHandler;
    private final long mInterval;
    private final String mName;
    private final SharedPreferences mPreferences;
    private AtomicLong mTime;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CounterImpl(Context context, String str, long j) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("name can't be empty!");
        }
        this.mName = str;
        this.mInterval = j;
        this.mCountMap = new HashMap();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mPreferences = defaultSharedPreferences;
        this.mEditor = defaultSharedPreferences.edit();
        this.mTime = new AtomicLong(defaultSharedPreferences.getLong(generatePrefKey(KEY_TIME), System.currentTimeMillis()));
        this.mHandler = new Handler(ThreadUtils.getLooper(0), this);
        initValuesFromPref();
        report(false);
    }

    private void initValuesFromPref() {
        Map<String, ?> all = this.mPreferences.getAll();
        LogUtils.d(TAG, "initValuesFromPref prefMap:" + all);
        for (String str : all.keySet()) {
            String generateKeyFromPrefKey = generateKeyFromPrefKey(str);
            if (!TextUtils.isEmpty(generateKeyFromPrefKey) && !generateKeyFromPrefKey.equals(KEY_TIME)) {
                Object obj = all.get(str);
                if (obj instanceof Integer) {
                    LogUtils.d(TAG, "initValuesFromPref key:" + generateKeyFromPrefKey + " value:" + obj);
                    this.mCountMap.put(generateKeyFromPrefKey, new AtomicInteger(((Integer) obj).intValue()));
                }
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what == 1) {
            if (this.mDebug) {
                LogUtils.d(TAG, "mEditor.apply()");
            }
            this.mEditor.apply();
        }
        return true;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounter
    public void debug(boolean z) {
        this.mDebug = z;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounter
    public synchronized int count(String str) {
        return count(str, 1);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.datalogmodule.ICounter
    public synchronized int count(String str, int i) {
        int i2;
        int i3;
        AtomicInteger atomicInteger = this.mCountMap.get(str);
        String generatePrefKey = generatePrefKey(str);
        if (atomicInteger == null) {
            try {
                i2 = this.mPreferences.getInt(generatePrefKey, 0);
            } catch (ClassCastException e) {
                LogUtils.w(TAG, "mPreferences.getInt(" + generatePrefKey + ") error!", e);
                i2 = 0;
            }
            if (this.mDebug) {
                LogUtils.v(TAG, "count " + this.mName + ", load key:" + generatePrefKey + " from pref, value is " + i2);
            }
            AtomicInteger atomicInteger2 = new AtomicInteger(i2);
            this.mCountMap.put(str, atomicInteger2);
            atomicInteger = atomicInteger2;
        }
        i3 = atomicInteger.get() + i;
        atomicInteger.set(i3);
        this.mEditor.putInt(generatePrefKey, i3);
        if (this.mDebug) {
            LogUtils.v(TAG, "count " + this.mName + " " + str + QuickSettingConstants.JOINER + i3 + ", cache is:" + this.mCountMap);
        }
        if (!this.mHandler.hasMessages(1)) {
            this.mHandler.sendEmptyMessageDelayed(1, MESSAGE_APPLY_DELAY);
        }
        report(i3 == Integer.MAX_VALUE);
        return i3;
    }

    private String generatePrefKey(String str) {
        return this.mName + "_" + str;
    }

    private String generateKeyFromPrefKey(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String str2 = this.mName + "_";
        if (str.startsWith(str2)) {
            return str.substring(str2.length());
        }
        return null;
    }

    private void report(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        final long j = this.mTime.get();
        long j2 = this.mInterval;
        if (j / j2 < currentTimeMillis / j2 || z) {
            final HashMap hashMap = new HashMap();
            for (String str : this.mCountMap.keySet()) {
                AtomicInteger atomicInteger = this.mCountMap.get(str);
                hashMap.put(str, Integer.valueOf(atomicInteger.get()));
                atomicInteger.set(0);
                this.mEditor.putInt(generatePrefKey(str), 0);
            }
            ThreadUtils.post(2, new Runnable() { // from class: com.xiaopeng.datalog.counter.CounterImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    LogUtils.i(CounterImpl.TAG, "[" + CounterImpl.this.mName + " counter] send count event, name:" + CounterImpl.this.mName + " values:" + hashMap);
                    IDataLog iDataLog = (IDataLog) Module.get(DataLogModuleEntry.class).get(IDataLog.class);
                    IMoleEventBuilder event = iDataLog.buildMoleEvent().setPageId("P00012").setButtonId("B001").setEvent(CounterImpl.this.mName);
                    for (String str2 : hashMap.keySet()) {
                        event.setProperty(str2, (Number) hashMap.get(str2));
                    }
                    event.setProperty(CounterImpl.KEY_TIME, Long.valueOf(j));
                    IMoleEvent build = event.build();
                    LogUtils.d(CounterImpl.TAG, "start sendStatData:" + build.toJson());
                    iDataLog.sendStatData(build);
                }
            });
            if (!this.mHandler.hasMessages(1)) {
                this.mHandler.sendEmptyMessageDelayed(1, MESSAGE_APPLY_DELAY);
            }
        }
        this.mTime.set(currentTimeMillis);
        this.mEditor.putLong(generatePrefKey(KEY_TIME), currentTimeMillis);
    }
}
