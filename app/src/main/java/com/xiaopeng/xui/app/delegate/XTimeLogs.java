package com.xiaopeng.xui.app.delegate;

import android.os.Handler;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.xui.utils.XLogUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
class XTimeLogs implements Runnable {
    private long mEndTime;
    private String mName;
    private long mStartTime;
    private long mTempTime;
    private Handler mHandler = new Handler();
    private LinkedHashMap<String, Long> mTagTimeMap = new LinkedHashMap<>();

    private XTimeLogs() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static XTimeLogs create() {
        return new XTimeLogs();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void start(String str) {
        this.mName = str;
        long nanoTime = System.nanoTime();
        this.mStartTime = nanoTime;
        this.mTempTime = nanoTime;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void record(String str) {
        this.mTagTimeMap.put(str, Long.valueOf(System.nanoTime() - this.mTempTime));
        this.mTempTime = System.nanoTime();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void end() {
        this.mEndTime = System.nanoTime() - this.mStartTime;
        this.mHandler.post(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        Set<Map.Entry<String, Long>> entrySet = this.mTagTimeMap.entrySet();
        StringBuilder sb = new StringBuilder();
        sb.append("total:");
        sb.append(this.mEndTime / 1000);
        sb.append("μs");
        for (Map.Entry<String, Long> entry : entrySet) {
            sb.append(", ");
            sb.append(entry.getKey());
            sb.append(QuickSettingConstants.JOINER);
            sb.append(entry.getValue().longValue() / 1000);
        }
        this.mTagTimeMap.clear();
        XLogUtils.d(this.mName, sb.toString());
    }
}
